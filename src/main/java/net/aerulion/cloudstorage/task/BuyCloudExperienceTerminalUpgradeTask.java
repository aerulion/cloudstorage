package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.Upgrade;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BuyCloudExperienceTerminalUpgradeTask extends BukkitRunnable {

    private final Player PLAYER;
    private final String UUID;
    private final Upgrade UPGRADE;

    public BuyCloudExperienceTerminalUpgradeTask(Player PLAYER, String UUID, Upgrade UPGRADE) {
        this.PLAYER = PLAYER;
        this.UUID = UUID;
        this.UPGRADE = UPGRADE;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `cloudstorage_experience_terminals` SET `CAPACITY` = IF(`CAPACITY` < ?, ?, `CAPACITY`) WHERE `OWNER` = ?");
            preparedStatement.setInt(1, UPGRADE.getCapacityExperience());
            preparedStatement.setInt(2, UPGRADE.getCapacityExperience());
            preparedStatement.setString(3, UUID);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1)
                throw new SQLException();
            preparedStatement.close();
            SoundUtils.playSound(PLAYER, SoundType.SUCCESS);
            new FetchCloudExperienceTerminalTask(UUID, PLAYER, Inventory.UPGRADE);
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_BUYING_UPGRADE.get());
            SoundUtils.playSound(PLAYER, SoundType.ERROR);
            EconomyResponse economyResponse = Main.economy.depositPlayer(PLAYER, UPGRADE.getPrice());
            if (!economyResponse.transactionSuccess()) {
                PLAYER.sendMessage(Messages.ERROR_REFUNDING_PLAYER.get());
            }
        }
    }
}
