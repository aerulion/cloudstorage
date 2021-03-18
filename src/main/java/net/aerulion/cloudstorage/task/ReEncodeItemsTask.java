package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReEncodeItemsTask extends BukkitRunnable {

    private final CommandSender COMMAND_SENDER;

    public ReEncodeItemsTask(CommandSender COMMAND_SENDER) {
        this.COMMAND_SENDER = COMMAND_SENDER;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `UUID`, `ITEM` FROM `aerulion_cloudstorage_slots`");
            ResultSet resultSet = preparedStatement.executeQuery();
            int changedValues = 0;
            if (resultSet != null) {
                if (!resultSet.next()) {
                    throw new SQLException();
                } else {
                    do {
                        ItemStack storedItem = resultSet.getString("ITEM").equals("") ? null : Base64Utils.decodeItemStack(resultSet.getString("ITEM"));
                        if (storedItem != null) {
                            PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE `aerulion_cloudstorage_slots` SET `ITEM` = ? WHERE `UUID` = ?");
                            preparedStatement2.setString(1, Base64Utils.encodeItemStack(storedItem));
                            preparedStatement2.setString(2, resultSet.getString("UUID"));
                            changedValues += preparedStatement2.executeUpdate();
                            preparedStatement2.close();
                        }
                    } while (resultSet.next());
                }
                resultSet.close();
            }
            preparedStatement.close();
            COMMAND_SENDER.sendMessage(Messages.MESSAGE_ENTRIES_UPDATED.get().replaceText(TextReplacementConfig.builder().replacement(String.valueOf(changedValues)).match("%amount%").build()));
            SoundUtils.playSound(COMMAND_SENDER, SoundType.SUCCESS);
        } catch (SQLException exception) {
            COMMAND_SENDER.sendMessage(Messages.ERROR_LOADING_DATA.get());
            SoundUtils.playSound(COMMAND_SENDER, SoundType.ERROR);
        }
    }
}