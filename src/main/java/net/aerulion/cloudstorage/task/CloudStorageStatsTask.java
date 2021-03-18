package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.chat.ChatUtils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class CloudStorageStatsTask extends BukkitRunnable {

    private final Player PLAYER;

    public CloudStorageStatsTask(Player PLAYER) {
        this.PLAYER = PLAYER;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(`UUID`) AS `STORAGESLOTS`, SUM(`CAPACITY`) AS `TOTALCAPACITY`, SUM(`AMOUNT`) AS `STOREDAMOUNT` FROM `aerulion_cloudstorage_slots` WHERE `OWNER` = ?");
            preparedStatement.setString(1, PLAYER.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ChatUtils.sendChatDividingLine(PLAYER, Main.LIGHT_ACCENT_TEXT_COLOR);
                PLAYER.sendMessage("");
                ChatUtils.sendCenteredChatMessage(PLAYER, Component.text("Deine Cloud Statistik:").color(Main.PRIMARY_TEXT_COLOR).decorate(TextDecoration.BOLD));
                PLAYER.sendMessage("");
                ChatUtils.sendCenteredChatMessage(PLAYER, Component.text("Cloud Storage Slots: ").color(Main.LIGHT_ACCENT_TEXT_COLOR).append(Component.text(Messages.decimalFormat.format(resultSet.getInt("STORAGESLOTS"))).color(Main.PRIMARY_TEXT_COLOR)));
                ChatUtils.sendCenteredChatMessage(PLAYER, Component.text("Gesamtkapazität: ").color(Main.LIGHT_ACCENT_TEXT_COLOR).append(Component.text(Messages.decimalFormat.format(resultSet.getLong("TOTALCAPACITY"))).color(Main.PRIMARY_TEXT_COLOR)));
                ChatUtils.sendCenteredChatMessage(PLAYER, Component.text("Eingelagerte Items: ").color(Main.LIGHT_ACCENT_TEXT_COLOR).append(Component.text(Messages.decimalFormat.format(resultSet.getLong("STOREDAMOUNT"))).color(Main.PRIMARY_TEXT_COLOR)));
                PLAYER.sendMessage("");

                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                StringBuilder stringBuilderFilled = new StringBuilder();
                StringBuilder stringBuilderEmpty = new StringBuilder();
                for (int i = 1; i < (((double) resultSet.getLong("STOREDAMOUNT") / resultSet.getLong("TOTALCAPACITY")) * 160D); i++)
                    stringBuilderFilled.append("|");
                for (int i = 1; i < (160 - stringBuilderFilled.length()); i++)
                    stringBuilderEmpty.append("|");

                Component partFilled = Component.text(stringBuilderFilled.toString()).color(Main.PRIMARY_TEXT_COLOR)
                        .hoverEvent(HoverEvent.showText(
                                Component.text(decimalFormat.format(((double) resultSet.getLong("STOREDAMOUNT") / resultSet.getLong("TOTALCAPACITY")) * 100D) + "%")
                                        .color(Main.PRIMARY_TEXT_COLOR)
                                        .append(Component.text(" genutzt").color(Main.LIGHT_ACCENT_TEXT_COLOR))));
                Component partEmpty = Component.text(stringBuilderEmpty.toString()).color(Main.PRIMARY_TEXT_COLOR)
                        .hoverEvent(HoverEvent.showText(
                                Component.text(decimalFormat.format(((double) (resultSet.getLong("TOTALCAPACITY") - resultSet.getLong("STOREDAMOUNT")) / resultSet.getLong("TOTALCAPACITY")) * 100D) + "%")
                                        .color(Main.PRIMARY_TEXT_COLOR)
                                        .append(Component.text(" verfügbar").color(Main.LIGHT_ACCENT_TEXT_COLOR))));

                PLAYER.sendMessage(partFilled.append(partEmpty));
                PLAYER.sendMessage("");
                ChatUtils.sendChatDividingLine(PLAYER, Main.LIGHT_ACCENT_TEXT_COLOR);
            }
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_LOADING_DATA.get());
            SoundUtils.playSound(PLAYER, SoundType.ERROR);
        }
    }
}