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

public class StoreExperienceTask extends BukkitRunnable {

    private final Player PLAYER;
    private final int AMOUNT;
    private final String UUID;
    private final boolean SILENT;

    public StoreExperienceTask(Player player, int amount, String UUID, boolean silent) {
        this.PLAYER = player;
        this.AMOUNT = amount;
        this.UUID = UUID;
        this.SILENT = silent;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cloudstorage_experience_terminals SET AMOUNT = IF((CAPACITY >= (AMOUNT + ?)), AMOUNT + ?, AMOUNT) WHERE OWNER = ?");
            preparedStatement.setInt(1, AMOUNT);
            preparedStatement.setInt(2, AMOUNT);
            preparedStatement.setString(3, UUID);
            int returnValue = preparedStatement.executeUpdate();
            if (returnValue < 1) {
                throw new SQLException();
            }
            for (String s : Main.openGUIs.keySet()) {
                if (Main.openGUIs.get(s).equals(UUID)) {
                    Player player = Bukkit.getPlayer(java.util.UUID.fromString(s));
                    if (player != null)
                        new FetchCloudExperienceTerminalTask(UUID, player, Inventory.EXPERIENCE_TERMINAL);
                }
            }
            if (!SILENT)
                SoundUtils.playSound(PLAYER, SoundType.ITEM_PICKUP);
        } catch (SQLException exception) {
            PLAYER.giveExp(AMOUNT);
            PLAYER.sendMessage(Messages.ERROR_STORING_EXPERIENCE.get());
        }
    }
}