package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.base64.Base64Utils;
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
import java.util.ArrayList;
import java.util.List;

public class StoreInitialItemTask extends BukkitRunnable {

    private final Player PLAYER;
    private final ItemStack ITEM;
    private final int AMOUNT;
    private final String UUID;
    private final String OWNER;
    private final int SLOT;

    public StoreInitialItemTask(Player PLAYER, ItemStack ITEM, int AMOUNT, String UUID, String OWNER, int SLOT) {
        this.PLAYER = PLAYER;
        this.ITEM = ITEM;
        this.AMOUNT = AMOUNT;
        this.UUID = UUID;
        this.OWNER = OWNER;
        this.SLOT = SLOT;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `ITEM` FROM `aerulion_cloudstorage_slots` WHERE `OWNER` = ?");
            preparedStatement.setString(1, OWNER);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> storedItems = new ArrayList<>();
            if (resultSet != null) {
                if (!resultSet.next()) {
                    throw new SQLException();
                } else {
                    do {
                        storedItems.add(resultSet.getString("ITEM"));
                    } while (resultSet.next());
                }
                resultSet.close();
            }
            preparedStatement.close();
            if (!storedItems.contains(Base64Utils.encodeItemStack(ITEM))) {
                ItemStack itemStack = PLAYER.getInventory().getItem(SLOT);
                if (itemStack != null && itemStack.isSimilar(ITEM) && itemStack.getAmount() >= AMOUNT) {
                    itemStack.setAmount(itemStack.getAmount() - AMOUNT);
                    new StoreItemTask(PLAYER, ITEM, AMOUNT, UUID, false);
                }
            } else {
                PLAYER.sendMessage(Messages.MESSAGE_ITEM_ALREADY_STORED.get());
                SoundUtils.playSound(PLAYER, SoundType.INFO);
            }
        } catch (SQLException exception) {
            PLAYER.sendMessage(Messages.ERROR_LOADING_DATA.get());
            SoundUtils.playSound(PLAYER, SoundType.ERROR);
        }
    }
}