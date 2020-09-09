package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.CloudStorageSlot;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.chat.ChatUtils;
import net.aerulion.nucleus.api.json.JsonUtils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListCloudStorageSlotsTask extends BukkitRunnable {

    private final Player PLAYER;
    private int page;

    public ListCloudStorageSlotsTask(Player player, int page) {
        this.PLAYER = player;
        this.page = page;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `aerulion_cloudstorage_slots` WHERE `OWNER` = ? ORDER BY `AMOUNT` DESC");
            preparedStatement.setString(1, PLAYER.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CloudStorageSlot> cloudStorageSlotList = new ArrayList<>();
            if (resultSet != null) {
                if (!resultSet.next()) {
                    PLAYER.sendMessage(Messages.MESSAGE_NO_CLOUD_STORAGE_SLOT.get());
                    SoundUtils.playSound(PLAYER, SoundType.ALERT);
                    return;
                } else {
                    do {
                        cloudStorageSlotList.add(new CloudStorageSlot(resultSet.getString("UUID"), resultSet.getString("OWNER"), resultSet.getInt("AMOUNT"), resultSet.getInt("CAPACITY"), resultSet.getString("ITEM").equals("") ? null : Base64Utils.decodeItemStack(resultSet.getString("ITEM")), resultSet.getBoolean("PRIVATE")));
                    } while (resultSet.next());
                }
            }
            if (Math.ceil(cloudStorageSlotList.size() / 10D) < page)
                page = (int) Math.ceil(cloudStorageSlotList.size() / 10D);
            int from = 10 * page - 10;
            int to = 10 * page;
            if (to > cloudStorageSlotList.size())
                to = cloudStorageSlotList.size();
            List<CloudStorageSlot> toBeSent = cloudStorageSlotList.subList(from, to);
            int count = from + 1;

            ChatUtils.sendChatDividingLine(PLAYER, Main.LIGHT_ACCENT_COLOR.toString());
            PLAYER.sendMessage("");
            ChatUtils.sendCenteredChatMessage(PLAYER, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Deine Cloud Storage Slots:");
            PLAYER.sendMessage("");
            for (CloudStorageSlot cloudStorageSlot : toBeSent) {
                TextComponent mainMessage = new TextComponent("> ");
                mainMessage.setColor(Main.LIGHT_ACCENT_COLOR);
                for (BaseComponent baseComponent : new ComponentBuilder("Cloud Storage Slot ").color(Main.PRIMARY_COLOR).event(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(JsonUtils.convertItemStackToJson(cloudStorageSlot.getStoredItem())).create())).append("#").color(Main.DARK_ACCENT_COLOR).append(String.valueOf(count)).color(Main.PRIMARY_COLOR).create())
                    mainMessage.addExtra(baseComponent);
                mainMessage.addExtra(new TextComponent("    "));
                for (BaseComponent baseComponent : new ComponentBuilder("[").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder(String.valueOf(cloudStorageSlot.getStoredAmount())).color(Main.PRIMARY_COLOR).append("/").color(Main.DARK_ACCENT_COLOR).append(String.valueOf(cloudStorageSlot.getCapacity())).color(Main.PRIMARY_COLOR).create()))).color(Main.DARK_ACCENT_COLOR).append("Info").color(Main.PRIMARY_COLOR).bold(true).append("]").color(Main.DARK_ACCENT_COLOR).bold(false).create())
                    mainMessage.addExtra(baseComponent);
                mainMessage.addExtra(new TextComponent("    "));
                for (BaseComponent baseComponent : new ComponentBuilder("[").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder("Klicke Hier").create()))).color(Main.DARK_ACCENT_COLOR).append("AP-Kaufen").color(Main.PRIMARY_COLOR).bold(true).append("]").color(Main.DARK_ACCENT_COLOR).bold(false).create())
                    mainMessage.addExtra(baseComponent);
                PLAYER.spigot().sendMessage(mainMessage);
                count++;
            }
            if (cloudStorageSlotList.size() > 10) {
                PLAYER.sendMessage("");
                ChatUtils.sendCenteredChatMessage(PLAYER, Main.LIGHT_ACCENT_COLOR + "Seite " + Main.PRIMARY_COLOR + page + Main.DARK_ACCENT_COLOR + "/" + Main.PRIMARY_COLOR + (int) Math.ceil(cloudStorageSlotList.size() / 10D));
                TextComponent pageButtons = new TextComponent("                           ");
                for (BaseComponent baseComponent : page == 1 ? new ComponentBuilder("    ").create() : new ComponentBuilder("<<<").color(Main.PRIMARY_COLOR).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cloudstorage:cloudstorage list " + (page - 1))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder("< Vorherige Seite").color(Main.PRIMARY_COLOR).create()))).create())
                    pageButtons.addExtra(baseComponent);
                pageButtons.addExtra("                ");
                for (BaseComponent baseComponent : page == Math.ceil(cloudStorageSlotList.size() / 10D) ? new ComponentBuilder("    ").create() : new ComponentBuilder(">>>").color(Main.PRIMARY_COLOR).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cloudstorage:cloudstorage list " + (page + 1))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder("> Nächste Seite").color(Main.PRIMARY_COLOR).create()))).create())
                    pageButtons.addExtra(baseComponent);
                PLAYER.spigot().sendMessage(pageButtons);
            }
            PLAYER.sendMessage("");
            ChatUtils.sendChatDividingLine(PLAYER, Main.LIGHT_ACCENT_COLOR.toString());
            preparedStatement.close();
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_LOADING_DATA.get());
            SoundUtils.playSound(PLAYER, SoundType.ERROR);
        }
    }
}