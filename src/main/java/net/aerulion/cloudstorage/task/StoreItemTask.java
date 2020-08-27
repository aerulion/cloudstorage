package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.cloudstorage.utils.ItemCache;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StoreItemTask extends BukkitRunnable {

    private final Player PLAYER;
    private final ItemStack ITEM;
    private final int AMOUNT;
    private final String UUID;
    private final boolean SILENT;

    public StoreItemTask(Player player, ItemStack item, int amount, String UUID, boolean silent) {
        this.PLAYER = player;
        this.ITEM = item;
        this.AMOUNT = amount;
        this.UUID = UUID;
        this.SILENT = silent;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            final String ITEM = Base64Utils.encodeItemStack(this.ITEM);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE aerulion_cloudstorage_slots SET ITEM = IF((ITEM = ? OR ITEM = '') AND (CAPACITY >= (AMOUNT + ?)), ?, ITEM), AMOUNT = IF((ITEM = ? OR ITEM = '') AND (CAPACITY >= (AMOUNT + ?)), AMOUNT + ?, AMOUNT) WHERE UUID = ?");
            preparedStatement.setString(1, ITEM);
            preparedStatement.setInt(2, AMOUNT);
            preparedStatement.setString(3, ITEM);
            preparedStatement.setString(4, ITEM);
            preparedStatement.setInt(5, AMOUNT);
            preparedStatement.setInt(6, AMOUNT);
            preparedStatement.setString(7, UUID);
            int returnValue = preparedStatement.executeUpdate();
            if (returnValue < 1) {
                throw new SQLException();
            }
            for (String s : Main.openGUIs.keySet()) {
                if (Main.openGUIs.get(s).equals(UUID)) {
                    Player player = Bukkit.getPlayer(java.util.UUID.fromString(s));
                    if (player != null)
                        new FetchCloudStorageSlotTask(UUID, player, Inventory.ACCESS_POINT);
                }
            }
            if (!SILENT)
                SoundUtils.playSound(PLAYER, SoundType.ITEM_PICKUP);
        } catch (SQLException exception) {
            ItemCache.addItemToCache(PLAYER, Base64Utils.encodeItemStack(ITEM), AMOUNT);
            PLAYER.sendMessage(Messages.ERROR_STORING_ITEMS.get());
        }
    }
}