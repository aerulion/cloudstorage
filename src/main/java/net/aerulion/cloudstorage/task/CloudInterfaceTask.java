package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.CloudInterfaceMode;
import net.aerulion.cloudstorage.utils.CloudStorageSlot;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CloudInterfaceTask extends BukkitRunnable {

    private final Player PLAYER;
    private final String CLOUD_OWNER_UUID;
    private final CloudInterfaceMode CLOUD_INTERFACE_MODE;

    public CloudInterfaceTask(Player player, String cloudOwnerUUID, CloudInterfaceMode cloudInterfaceMode) {
        this.PLAYER = player;
        this.CLOUD_OWNER_UUID = cloudOwnerUUID;
        this.CLOUD_INTERFACE_MODE = cloudInterfaceMode;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `aerulion_cloudstorage_slots` WHERE ((`ITEM` != '') AND (`AMOUNT` < `CAPACITY`) AND (`OWNER` = ?))");
            preparedStatement.setString(1, CLOUD_OWNER_UUID);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CloudStorageSlot> possibleItems = new ArrayList<>();
            if (resultSet != null) {
                if (!resultSet.next()) {
                    PLAYER.sendMessage(Messages.MESSAGE_INTERFACE_NO_AVAILABLE_SLOTS.get());
                    SoundUtils.playSound(PLAYER, SoundType.ALERT);
                    return;
                } else {
                    do {
                        possibleItems.add(new CloudStorageSlot(resultSet.getString("UUID"), resultSet.getString("OWNER"), resultSet.getInt("AMOUNT"), resultSet.getInt("CAPACITY"), resultSet.getString("ITEM").equals("") ? null : Base64Utils.decodeItemStack(resultSet.getString("ITEM")), resultSet.getBoolean("PRIVATE")));
                    } while (resultSet.next());
                }
            }
            int storedAmount = 0;
            for (int i = CLOUD_INTERFACE_MODE.getStartSlot(); i <= CLOUD_INTERFACE_MODE.getEndSlot(); i++) {
                ItemStack itemStack = PLAYER.getInventory().getItem(i);
                if (itemStack != null) {
                    for (CloudStorageSlot cloudStorageSlot : possibleItems) {
                        if (cloudStorageSlot.getStoredItem().isSimilar(itemStack)) {
                            int amount = (cloudStorageSlot.getStoredAmount() + itemStack.getAmount() <= cloudStorageSlot.getCapacity()) ? itemStack.getAmount() : (cloudStorageSlot.getCapacity() - cloudStorageSlot.getStoredAmount());
                            storedAmount += amount;
                            itemStack.setAmount(itemStack.getAmount() - amount);
                            new StoreItemTask(PLAYER, cloudStorageSlot.getStoredItem(), amount, cloudStorageSlot.getUUID(), true);
                        }
                    }
                }
            }
            if (storedAmount == 0) {
                PLAYER.sendMessage(Messages.MESSAGE_INTERFACE_NO_ITEMS_STORED.get());
                SoundUtils.playSound(PLAYER, SoundType.ALERT);
                return;
            }
            PLAYER.sendMessage(Messages.PREFIX.getRaw() + Main.PRIMARY_COLOR + ChatColor.BOLD + storedAmount + Messages.MESSAGE_INTERFACE_ITEMS_STORED.getRaw());
            SoundUtils.playSound(PLAYER, SoundType.SUCCESS);
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_LOADING_DATA.get());
            SoundUtils.playSound(PLAYER, SoundType.ERROR);
        }
    }
}