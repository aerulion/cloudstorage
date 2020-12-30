package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WithdrawExperienceTask extends BukkitRunnable {

    private final Player PLAYER;
    private final int AMOUNT;
    private final String UUID;

    public WithdrawExperienceTask(Player player, int amount, String UUID) {
        this.PLAYER = player;
        this.AMOUNT = amount;
        this.UUID = UUID;
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
            PLAYER.giveExp(AMOUNT);
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