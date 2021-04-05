package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Item;
import net.aerulion.cloudstorage.utils.ItemCache;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.Upgrade;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class BuyCloudStorageSlotTask extends BukkitRunnable {

    private final Player PLAYER;

    public BuyCloudStorageSlotTask(Player PLAYER) {
        this.PLAYER = PLAYER;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            String uuid = UUID.randomUUID().toString();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO aerulion_cloudstorage_slots (UUID, OWNER, ITEM, AMOUNT, CAPACITY, PRIVATE) VALUES (?,?,'','0','2048', '0') ON DUPLICATE KEY UPDATE OWNER=?, ITEM='', AMOUNT='0', CAPACITY='512', PRIVATE='0'");
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, PLAYER.getUniqueId().toString());
            preparedStatement.setString(3, PLAYER.getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            SoundUtils.playSound(PLAYER, SoundType.SUCCESS);
            PLAYER.sendMessage(Messages.MESSAGE_CLOUD_STORAGE_SLOT_BOUGHT.get());
            if (PLAYER.getInventory().firstEmpty() == -1) {
                ItemCache.addItemToCache(PLAYER, Base64Utils.encodeItemStack(Item.getCloudAccessPoint(uuid, PLAYER.getUniqueId().toString())), 1);
                PLAYER.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
            } else {
                PLAYER.getInventory().addItem(Item.getCloudAccessPoint(uuid, PLAYER.getUniqueId().toString()));
            }
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_BUYING_CLOUD_STORAGE_SLOT.get());
            SoundUtils.playSound(PLAYER, SoundType.ERROR);
            EconomyResponse economyResponse = Main.economy.depositPlayer(PLAYER, Upgrade.CLOUD_STORAGE_SLOT_BASE.getPrice());
            if (!economyResponse.transactionSuccess()) {
                PLAYER.sendMessage(Messages.ERROR_REFUNDING_PLAYER.get());
            }
        }
    }
}
