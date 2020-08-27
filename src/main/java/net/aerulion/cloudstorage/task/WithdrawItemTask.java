package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.ItemCache;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.Inventory;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class WithdrawItemTask extends BukkitRunnable {

    private final Player PLAYER;
    private final int AMOUNT;
    private final String UUID;

    public WithdrawItemTask(Player player, int amount, String UUID) {
        this.PLAYER = player;
        this.AMOUNT = amount;
        this.UUID = UUID;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ITEM FROM aerulion_cloudstorage_slots WHERE UUID = ?");
            preparedStatement.setString(1, UUID);
            ResultSet resultSet = preparedStatement.executeQuery();
            ItemStack itemStack;
            String encodedItem;
            if (resultSet != null) {
                if (resultSet.next()) {
                    if (resultSet.getString("ITEM").equals("")) {
                        throw new SQLException();
                    }
                    encodedItem = resultSet.getString("ITEM");
                    itemStack = Base64Utils.decodeItemStack(encodedItem);
                } else {
                    throw new SQLException();
                }
            } else {
                throw new SQLException();
            }
            PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE aerulion_cloudstorage_slots SET AMOUNT = IF(AMOUNT >= ?, AMOUNT - ?, AMOUNT), ITEM = IF(AMOUNT = 0, '', ITEM) WHERE UUID = ?");
            preparedStatement2.setInt(1, AMOUNT);
            preparedStatement2.setInt(2, AMOUNT);
            preparedStatement2.setString(3, UUID);
            int returnValue = preparedStatement2.executeUpdate();
            if (returnValue < 1) {
                throw new SQLException();
            }
            int itemsGiven = 0;
            boolean cached = false;
            while (itemsGiven < AMOUNT) {
                if (PLAYER.getInventory().firstEmpty() == -1) {
                    cached = true;
                    ItemCache.addItemToCache(PLAYER, encodedItem, AMOUNT - itemsGiven);
                    break;
                }
                int stackSize = Math.min(AMOUNT - itemsGiven, itemStack.getMaxStackSize());
                itemStack.setAmount(stackSize);
                PLAYER.getInventory().addItem(itemStack.clone());
                itemsGiven += stackSize;
            }
            if (cached)
                PLAYER.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
            for (String s : Main.openGUIs.keySet()) {
                if (Main.openGUIs.get(s).equals(UUID)) {
                    Player player = Bukkit.getPlayer(java.util.UUID.fromString(s));
                    if (player != null)
                        new FetchCloudStorageSlotTask(UUID, player, Inventory.ACCESS_POINT);
                }
            }
            SoundUtils.playSound(PLAYER, SoundType.ITEM_PICKUP);
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_WITHDRAWING_ITEMS.get());
        }
    }
}