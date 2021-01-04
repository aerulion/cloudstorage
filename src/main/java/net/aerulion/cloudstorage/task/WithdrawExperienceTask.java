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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WithdrawExperienceTask extends BukkitRunnable {

    private final Player PLAYER;
    private final int AMOUNT;
    private final String UUID;
    private final boolean BOTTLED;

    public WithdrawExperienceTask(Player player, int amount, String UUID, boolean bottled) {
        this.PLAYER = player;
        this.AMOUNT = amount;
        this.UUID = UUID;
        this.BOTTLED = bottled;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cloudstorage_experience_terminals SET AMOUNT = IF(AMOUNT >= ?, AMOUNT - ?, AMOUNT) WHERE OWNER = ?");
            preparedStatement.setInt(1, AMOUNT);
            preparedStatement.setInt(2, AMOUNT);
            preparedStatement.setString(3, UUID);
            int returnValue = preparedStatement.executeUpdate();
            if (returnValue < 1)
                throw new SQLException();
            if (!BOTTLED)
                PLAYER.giveExp(AMOUNT);
            else {
                int bottleAmount = AMOUNT / 7;
                int itemsGiven = 0;
                boolean cached = false;
                while (itemsGiven < bottleAmount) {
                    if (PLAYER.getInventory().firstEmpty() == -1) {
                        cached = true;
                        ItemCache.addItemToCache(PLAYER, Base64Utils.encodeItemStack(new ItemStack(Material.EXPERIENCE_BOTTLE)), bottleAmount - itemsGiven);
                        break;
                    }
                    ItemStack itemStack = new ItemStack(Material.EXPERIENCE_BOTTLE);
                    int stackSize = Math.min(bottleAmount - itemsGiven, 64);
                    itemStack.setAmount(stackSize);
                    PLAYER.getInventory().addItem(itemStack.clone());
                    itemsGiven += stackSize;
                }
                if (cached)
                    PLAYER.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
            }
            for (String s : Main.openGUIs.keySet()) {
                if (Main.openGUIs.get(s).equals(UUID)) {
                    Player player = Bukkit.getPlayer(java.util.UUID.fromString(s));
                    if (player != null)
                        new FetchCloudExperienceTerminalTask(UUID, player, Inventory.EXPERIENCE_TERMINAL);
                }
            }
            SoundUtils.playSound(PLAYER, SoundType.ITEM_PICKUP);
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_WITHDRAWING_EXPERIENCE.get());
        }
    }
}