package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.CloudExperienceTerminal;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FetchCloudExperienceTerminalTask extends BukkitRunnable {

    private final String UUID;
    private final Player PLAYER;
    private final Inventory INVENTORY;

    public FetchCloudExperienceTerminalTask(String UUID, Player player, Inventory inventory) {
        this.UUID = UUID;
        this.PLAYER = player;
        this.INVENTORY = inventory;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cloudstorage_experience_terminals WHERE OWNER = ?");
            preparedStatement.setString(1, UUID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                if (!resultSet.next()) {
                    throw new SQLException();
                } else {
                    do {
                        new OpenCloudExperienceTerminalTask(new CloudExperienceTerminal(resultSet.getString("OWNER"), resultSet.getInt("AMOUNT"), resultSet.getInt("CAPACITY"), resultSet.getBoolean("PRIVATE")), PLAYER, INVENTORY);
                    } while (resultSet.next());
                }
            }
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_LOADING_DATA.get());
        }
    }
}