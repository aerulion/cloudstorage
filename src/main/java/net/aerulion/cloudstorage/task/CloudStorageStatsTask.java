package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.chat.ChatUtils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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
                ChatUtils.sendChatDividingLine(PLAYER, "§7");
                PLAYER.sendMessage("");
                ChatUtils.sendCenteredChatMessage(PLAYER, ChatColor.of(Main.PRIMARY_COLOR) + "§lDeine Cloud Statistik:");
                PLAYER.sendMessage("");
                ChatUtils.sendCenteredChatMessage(PLAYER, "§7Cloud Storage Slots: " + ChatColor.of(Main.PRIMARY_COLOR) + Messages.decimalFormat.format(resultSet.getInt("STORAGESLOTS")));
                ChatUtils.sendCenteredChatMessage(PLAYER, "§7Gesamtkapazität: " + ChatColor.of(Main.PRIMARY_COLOR) + Messages.decimalFormat.format(resultSet.getLong("TOTALCAPACITY")));
                ChatUtils.sendCenteredChatMessage(PLAYER, "§7Eingelagerte Items: " + ChatColor.of(Main.PRIMARY_COLOR) + Messages.decimalFormat.format(resultSet.getLong("STOREDAMOUNT")));
                PLAYER.sendMessage("");

                TextComponent storageBar = new TextComponent();
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                StringBuilder stringBuilderFilled = new StringBuilder();
                StringBuilder stringBuilderEmpty = new StringBuilder();
                for (int i = 1; i < (((double) resultSet.getLong("STOREDAMOUNT") / resultSet.getLong("TOTALCAPACITY")) * 160D); i++)
                    stringBuilderFilled.append("|");
                for (int i = 1; i < (160 - stringBuilderFilled.length()); i++)
                    stringBuilderEmpty.append("|");
                TextComponent barFilled = new TextComponent(stringBuilderFilled.toString());
                barFilled.setColor(ChatColor.of(Main.PRIMARY_COLOR));
                barFilled.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder(decimalFormat.format(((double) resultSet.getLong("STOREDAMOUNT") / resultSet.getLong("TOTALCAPACITY")) * 100D)).color(ChatColor.of(Main.PRIMARY_COLOR)).append("%§7 genutzt").create())));
                TextComponent barEmpty = new TextComponent(stringBuilderEmpty.toString());
                barEmpty.setColor(ChatColor.of(Main.ACCENT_COLOR));
                barEmpty.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder(decimalFormat.format(((double) (resultSet.getLong("TOTALCAPACITY") - resultSet.getLong("STOREDAMOUNT")) / resultSet.getLong("TOTALCAPACITY")) * 100D)).color(ChatColor.of(Main.PRIMARY_COLOR)).append("%§7 verfügbar").create())));
                storageBar.addExtra(barFilled);
                storageBar.addExtra(barEmpty);
                PLAYER.spigot().sendMessage(storageBar);
                PLAYER.sendMessage("");
                ChatUtils.sendChatDividingLine(PLAYER, "§7");
            }
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_LOADING_DATA.get());
            SoundUtils.playSound(PLAYER, SoundType.ERROR);
        }
    }
}