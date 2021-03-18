package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.CloudStorageSlot;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.chat.ChatUtils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
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

            ChatUtils.sendChatDividingLine(PLAYER, Main.LIGHT_ACCENT_COLOR);
            PLAYER.sendMessage("");
            ChatUtils.sendCenteredChatMessage(PLAYER, Component.text("Deine Cloud Storage Slots:").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD));
            PLAYER.sendMessage("");
            for (CloudStorageSlot cloudStorageSlot : toBeSent) {
                Component slotName = Component.text("Cloud Storage Slot ").color(Main.PRIMARY_COLOR)
                        .append(Component.text("#").color(Main.DARK_ACCENT_COLOR))
                        .append(Component.text(String.valueOf(count)).color(Main.PRIMARY_COLOR))
                        .hoverEvent(cloudStorageSlot.getStoredItem() == null ?
                                HoverEvent.showText(Component.text("Leer").color(Main.PRIMARY_COLOR)) :
                                cloudStorageSlot.getStoredItem().asHoverEvent());
                Component spacer = Component.text("    ");
                Component infoButton = Component.text("[").color(Main.DARK_ACCENT_COLOR)
                        .append(Component.text("Info").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD))
                        .append(Component.text("]").color(Main.DARK_ACCENT_COLOR))
                        .hoverEvent(HoverEvent.showText(
                                Component.text(String.valueOf(cloudStorageSlot.getStoredAmount())).color(Main.PRIMARY_COLOR)
                                        .append(Component.text("/").color(Main.DARK_ACCENT_COLOR))
                                        .append(Component.text(String.valueOf(cloudStorageSlot.getCapacity())).color(Main.PRIMARY_COLOR))));
                Component buyAPButton = Component.text("[").color(Main.DARK_ACCENT_COLOR)
                        .append(Component.text("AP-Kaufen").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD))
                        .append(Component.text("]").color(Main.DARK_ACCENT_COLOR))
                        .hoverEvent(HoverEvent.showText(
                                Component.text("Klicke hier, um einen Access Point zu kaufen. Kosten: ").color(Main.LIGHT_ACCENT_COLOR)
                                        .append(Component.text("5.000 CT").color(Main.PRIMARY_COLOR))))
                        .clickEvent(ClickEvent.suggestCommand("/cloud buy_access_point " + cloudStorageSlot.getUUID()));
                Component message = Component.text("> ").color(Main.LIGHT_ACCENT_COLOR)
                        .append(slotName)
                        .append(spacer)
                        .append(infoButton)
                        .append(spacer)
                        .append(buyAPButton);
                PLAYER.sendMessage(message);
                count++;
            }
            if (cloudStorageSlotList.size() > 10) {
                PLAYER.sendMessage("");
                ChatUtils.sendCenteredChatMessage(PLAYER, Component.text("Seite ").color(Main.LIGHT_ACCENT_COLOR).append(Component.text(String.valueOf(page)).color(Main.PRIMARY_COLOR)).append(Component.text("/").color(Main.DARK_ACCENT_COLOR)).append(Component.text(String.valueOf((int) Math.ceil(cloudStorageSlotList.size() / 10D))).color(Main.PRIMARY_COLOR)));
                Component spacerFront = Component.text("                           ");
                Component button1 = page == 1 ? Component.text("    ") :
                        Component.text("<<<").color(Main.PRIMARY_COLOR)
                                .hoverEvent(HoverEvent.showText(Component.text("< Vorherige Seite").color(Main.PRIMARY_COLOR)))
                                .clickEvent(ClickEvent.runCommand("/cloudstorage:cloudstorage list " + (page - 1)));
                Component spacerMiddle = Component.text("                ");
                Component button2 = page == Math.ceil(cloudStorageSlotList.size() / 10D) ? Component.text("    ") :
                        Component.text(">>>").color(Main.PRIMARY_COLOR)
                                .hoverEvent(HoverEvent.showText(Component.text("> Nächste Seite").color(Main.PRIMARY_COLOR)))
                                .clickEvent(ClickEvent.runCommand("/cloudstorage:cloudstorage list " + (page + 1)));
                PLAYER.sendMessage(spacerFront.append(button1).append(spacerMiddle).append(button2));
            }
            PLAYER.sendMessage("");
            ChatUtils.sendChatDividingLine(PLAYER, Main.LIGHT_ACCENT_COLOR);
            preparedStatement.close();
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_LOADING_DATA.get());
            SoundUtils.playSound(PLAYER, SoundType.ERROR);
        }
    }
}