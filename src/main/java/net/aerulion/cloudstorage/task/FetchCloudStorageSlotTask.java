package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.CloudStorageSlot;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FetchCloudStorageSlotTask extends BukkitRunnable {

    private final String UUID;
    private final Player PLAYER;
    private final Inventory INVENTORY;

    public FetchCloudStorageSlotTask(String UUID, Player player, Inventory inventory) {
        this.UUID = UUID;
        this.PLAYER = player;
        this.INVENTORY = inventory;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM aerulion_cloudstorage_slots WHERE UUID = ?");
            preparedStatement.setString(1, UUID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                if (!resultSet.next()) {
                    throw new SQLException();
                } else {
                    do {
                        new OpenAccessPointTask(new CloudStorageSlot(resultSet.getString("UUID"), resultSet.getString("OWNER"), resultSet.getInt("AMOUNT"), resultSet.getInt("CAPACITY"), resultSet.getString("ITEM").equals("") ? null : Base64Utils.decodeItemStack(resultSet.getString("ITEM")), resultSet.getBoolean("PRIVATE")), PLAYER, INVENTORY);
                    } while (resultSet.next());
                }
            }
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_LOADING_DATA.get());
        }
    }
}