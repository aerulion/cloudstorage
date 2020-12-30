package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.CloudExperienceTerminal;
import net.aerulion.cloudstorage.utils.CloudInterfaceMode;
import net.aerulion.cloudstorage.utils.CloudStorageSlot;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.experience.ExperienceUtils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
            int totalXP = ExperienceUtils.getTotalExperience(PLAYER);
            int storedExperienceAmount = 0;
            if (totalXP > 0) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM `cloudstorage_experience_terminals` WHERE OWNER = ?");
                preparedStatement2.setString(1, CLOUD_OWNER_UUID);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                if (resultSet2 != null) {
                    if (!resultSet2.next()) {
                        PLAYER.sendMessage(Messages.MESSAGE_INTERFACE_NO_AVAILABLE_EXPERIENCE_TERMINAL.get());
                        SoundUtils.playSound(PLAYER, SoundType.ALERT);
                    } else {
                        do {
                            CloudExperienceTerminal cloudExperienceTerminal = new CloudExperienceTerminal(resultSet2.getString("OWNER"), resultSet2.getInt("AMOUNT"), resultSet2.getInt("CAPACITY"), resultSet2.getBoolean("PRIVATE"));
                            if ((!cloudExperienceTerminal.isPRIVATE() || cloudExperienceTerminal.getOWNER_UUID().equals(PLAYER.getUniqueId().toString())) && cloudExperienceTerminal.getStoredAmount() < cloudExperienceTerminal.getCAPACITY()) {
                                storedExperienceAmount = ((totalXP + cloudExperienceTerminal.getStoredAmount()) <= cloudExperienceTerminal.getCAPACITY()) ? totalXP : cloudExperienceTerminal.getCAPACITY() - cloudExperienceTerminal.getStoredAmount();
                                ExperienceUtils.setTotalExperience(PLAYER, ExperienceUtils.getTotalExperience(PLAYER) - storedExperienceAmount);
                                new StoreExperienceTask(PLAYER, storedExperienceAmount, CLOUD_OWNER_UUID, true);
                            }
                        } while (resultSet2.next());
                    }
                }
            }
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `aerulion_cloudstorage_slots` WHERE ((`ITEM` != '') AND (`AMOUNT` < `CAPACITY`) AND (`OWNER` = ?))");
            preparedStatement.setString(1, CLOUD_OWNER_UUID);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<ItemStack, CloudStorageSlot> possibleItems = new HashMap<>();
            if (resultSet != null) {
                if (!resultSet.next()) {
                    PLAYER.sendMessage(Messages.MESSAGE_INTERFACE_NO_AVAILABLE_SLOTS.get());
                    SoundUtils.playSound(PLAYER, SoundType.ALERT);
                    return;
                } else {
                    do {
                        ItemStack storedItem = resultSet.getString("ITEM").equals("") ? null : Base64Utils.decodeItemStack(resultSet.getString("ITEM"));
                        possibleItems.put(storedItem, new CloudStorageSlot(resultSet.getString("UUID"), resultSet.getString("OWNER"), resultSet.getInt("AMOUNT"), resultSet.getInt("CAPACITY"), storedItem, resultSet.getBoolean("PRIVATE")));
                    } while (resultSet.next());
                }
            }
            int storedItemAmount = 0;
            for (int i = CLOUD_INTERFACE_MODE.getStartSlot(); i <= CLOUD_INTERFACE_MODE.getEndSlot(); i++) {
                ItemStack itemStack = PLAYER.getInventory().getItem(i);
                if (itemStack != null) {
                    ItemStack keyItemStack = itemStack.clone();
                    keyItemStack.setAmount(1);
                    if (possibleItems.containsKey(keyItemStack)) {
                        CloudStorageSlot cloudStorageSlot = possibleItems.get(keyItemStack);
                        int amount = (cloudStorageSlot.getStoredAmount() + itemStack.getAmount() <= cloudStorageSlot.getCapacity()) ? itemStack.getAmount() : (cloudStorageSlot.getCapacity() - cloudStorageSlot.getStoredAmount());
                        if (amount > 0) {
                            storedItemAmount += amount;
                            cloudStorageSlot.setStoredAmount(cloudStorageSlot.getStoredAmount() + amount);
                            itemStack.setAmount(itemStack.getAmount() - amount);
                            new StoreItemTask(PLAYER, cloudStorageSlot.getStoredItem(), amount, cloudStorageSlot.getUUID(), true);
                        }
                    }
                }
            }
            if (storedItemAmount == 0 && storedExperienceAmount == 0) {
                PLAYER.sendMessage(Messages.MESSAGE_INTERFACE_NOTHING_STORED.get());
                SoundUtils.playSound(PLAYER, SoundType.ALERT);
                return;
            }
            PLAYER.sendMessage(Messages.MESSAGE_INTERFACE_ITEMS_STORED.get().replaceAll("%itemAmount", String.valueOf(storedItemAmount)).replaceAll("%expAmount", String.valueOf(storedExperienceAmount)));
            SoundUtils.playSound(PLAYER, SoundType.SUCCESS);
        } catch (
                SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_LOADING_DATA.get());
            SoundUtils.playSound(PLAYER, SoundType.ERROR);
        }
    }
}