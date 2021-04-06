package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.block.CloudStorageBlockType;
import net.aerulion.cloudstorage.utils.*;
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
import java.util.Collections;

public class BuyCloudExperienceTerminalTask extends BukkitRunnable {

    private final Player PLAYER;

    public BuyCloudExperienceTerminalTask(Player PLAYER) {
        this.PLAYER = PLAYER;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cloudstorage_experience_terminals (OWNER, AMOUNT, CAPACITY, PRIVATE) VALUES (?,'0',?,'0') ON DUPLICATE KEY UPDATE OWNER=OWNER");
            preparedStatement.setString(1, PLAYER.getUniqueId().toString());
            preparedStatement.setInt(2, Upgrade.EXPERIENCE_TERMINAL_BASE.getCapacityItem());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            SoundUtils.playSound(PLAYER, SoundType.SUCCESS);
            PLAYER.sendMessage(Messages.MESSAGE_CLOUD_EXPERIENCE_TERMINAL_BOUGHT.get());
            if (PLAYER.getInventory().firstEmpty() == -1) {
                ItemCache.addItemToCache(PLAYER, Base64Utils.encodeItemStack(
                        Item.getBlockWithMetaData(CloudStorageBlockType.CLOUD_EXPERIENCE_TERMINAL, Collections.singletonList(
                                new MetaData(NBT.KEY_CLOUD_EXPERIENCE_TERMINAL_OWNER_UUID.get(), PLAYER.getUniqueId().toString())))
                ), 1);
                PLAYER.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
            } else {
                PLAYER.getInventory().addItem(
                        Item.getBlockWithMetaData(CloudStorageBlockType.CLOUD_EXPERIENCE_TERMINAL, Collections.singletonList(
                                new MetaData(NBT.KEY_CLOUD_EXPERIENCE_TERMINAL_OWNER_UUID.get(), PLAYER.getUniqueId().toString())))
                );
            }
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_BUYING_CLOUD_EXPERIENCE_TERMINAL.get());
            SoundUtils.playSound(PLAYER, SoundType.ERROR);
            EconomyResponse economyResponse = Main.economy.depositPlayer(PLAYER, Upgrade.EXPERIENCE_TERMINAL_BASE.getPrice());
            if (!economyResponse.transactionSuccess()) {
                PLAYER.sendMessage(Messages.ERROR_REFUNDING_PLAYER.get());
            }
        }
    }
}
