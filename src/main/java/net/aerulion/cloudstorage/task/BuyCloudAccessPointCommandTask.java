package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Item;
import net.aerulion.cloudstorage.utils.ItemCache;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyCloudAccessPointCommandTask extends BukkitRunnable {

    private final Player PLAYER;
    private final String UUID;

    public BuyCloudAccessPointCommandTask(Player PLAYER, String UUID) {
        this.PLAYER = PLAYER;
        this.UUID = UUID;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `OWNER` FROM `aerulion_cloudstorage_slots` WHERE `UUID` = ?");
            preparedStatement.setString(1, UUID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                if (!resultSet.next()) {
                    throw new SQLException();
                } else {
                    do {
                        if (resultSet.getString("OWNER").equals(PLAYER.getUniqueId().toString())) {
                            if (!Main.economy.has(PLAYER, 5000D)) {
                                PLAYER.sendMessage(Messages.ERROR_NOT_ENOUGH_MONEY.get());
                                SoundUtils.playSound(PLAYER, SoundType.ERROR);
                                return;
                            }
                            EconomyResponse economyResponse = Main.economy.withdrawPlayer(PLAYER, 5000D);
                            if (economyResponse.transactionSuccess()) {
                                PLAYER.sendMessage(Messages.MESSAGE_CLOUD_ACCESS_POINT_BOUGHT.get());
                                SoundUtils.playSound(PLAYER, SoundType.SUCCESS);
                                if (PLAYER.getInventory().firstEmpty() == -1) {
                                    ItemCache.addItemToCache(PLAYER, Base64Utils.encodeItemStack(Item.getCloudAccessPoint(UUID, PLAYER.getUniqueId().toString())), 1);
                                    PLAYER.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
                                } else {
                                    PLAYER.getInventory().addItem(Item.getCloudAccessPoint(UUID, PLAYER.getUniqueId().toString()));
                                }
                            } else {
                                PLAYER.sendMessage(Messages.ERROR_TRANSACTION.get());
                                SoundUtils.playSound(PLAYER, SoundType.ERROR);
                            }
                        } else {
                            PLAYER.sendMessage(Messages.ERROR_ONLY_OWNER.get());
                            SoundUtils.playSound(PLAYER, SoundType.ERROR);
                        }
                    } while (resultSet.next());
                }
                resultSet.close();
            }
            preparedStatement.close();
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_LOADING_DATA.get());
            SoundUtils.playSound(PLAYER, SoundType.ERROR);
        }
    }
}