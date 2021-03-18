package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeletePlayerDataTask extends BukkitRunnable {

    private final CommandSender COMMANDSENDER;
    private final String UUID;

    public DeletePlayerDataTask(CommandSender COMMANDSENDER, String UUID) {
        this.COMMANDSENDER = COMMANDSENDER;
        this.UUID = UUID;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `aerulion_cloudstorage_slots` WHERE `OWNER` = ?");
            preparedStatement.setString(1, UUID);
            int affectedRows = preparedStatement.executeUpdate();
            preparedStatement.close();
            COMMANDSENDER.sendMessage(Messages.MESSAGE_ENTRIES_DELETED.get().replaceText(TextReplacementConfig.builder().replacement(String.valueOf(affectedRows)).match("%amount%").build()));
            SoundUtils.playSound(COMMANDSENDER, SoundType.SUCCESS);
        } catch (SQLException exception) {
            COMMANDSENDER.sendMessage(Messages.ERROR_DELETING_DATA.get());
            SoundUtils.playSound(COMMANDSENDER, SoundType.ERROR);
        }
    }
}
