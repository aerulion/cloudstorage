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

public class ToggleAccessTask extends BukkitRunnable {

    private final String UUID;
    private final Player PLAYER;

    public ToggleAccessTask(String UUID, Player PLAYER) {
        this.UUID = UUID;
        this.PLAYER = PLAYER;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE aerulion_cloudstorage_slots SET PRIVATE = IF(OWNER = ?, IF(PRIVATE = 0, 1, 0), PRIVATE) WHERE UUID = ?");
            preparedStatement.setString(1, PLAYER.getUniqueId().toString());
            preparedStatement.setString(2, UUID);
            int returnValue = preparedStatement.executeUpdate();
            if (returnValue < 1) {
                PLAYER.sendMessage(Messages.ERROR_TOGGLE_ACCESS_NO_OWNER.get());
                SoundUtils.playSound(PLAYER, SoundType.ERROR);
                return;
            }
            for (String s : Main.openGUIs.keySet()) {
                if (Main.openGUIs.get(s).equals(UUID)) {
                    Player player = Bukkit.getPlayer(java.util.UUID.fromString(s));
                    if (player != null)
                        new FetchCloudStorageSlotTask(UUID, player, Inventory.ACCESS_POINT);
                }
            }
            SoundUtils.playSound(PLAYER, SoundType.UI_CLICK);
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_TOGGLE_ACCESS.get());
            SoundUtils.playSound(PLAYER, SoundType.ERROR);
        }
    }
}