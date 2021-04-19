package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CloudExportBusTask extends BukkitRunnable {

    private final BlockInventoryHolder blockInventoryHolder;

    public CloudExportBusTask(BlockInventoryHolder blockInventoryHolder) {
        this.blockInventoryHolder = blockInventoryHolder;
        this.runTask(Main.plugin);
    }

    @Override
    public void run() {
        ItemStack metaItem = blockInventoryHolder.getInventory().getItem(blockInventoryHolder.getInventory().getSize() - 1);
        if (metaItem == null) return;
        String ownerUUID = NbtUtils.getNBTString(metaItem, NBT.KEY_CLOUD_EXPORT_BUS_OWNER_UUID.get());
        String associatedSlot = NbtUtils.getNBTString(metaItem, NBT.KEY_CLOUD_EXPORT_BUS_ASSOCIATED_SLOT_UUID.get());
        if (ownerUUID.equals("") || associatedSlot.equals("")) return;
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ITEM, AMOUNT FROM aerulion_cloudstorage_slots WHERE UUID=?");
            preparedStatement.setString(1, associatedSlot);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet == null || !resultSet.next()) return;
            ItemStack itemStack = Base64Utils.decodeItemStack(resultSet.getString("ITEM"));
            int amount = resultSet.getInt("AMOUNT");
            if (amount <= 1) return;
            int withdrawAmount = Math.min(26 * itemStack.getMaxStackSize(), amount - 1);
            PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE aerulion_cloudstorage_slots SET AMOUNT = IF(AMOUNT >= ?, AMOUNT - ?, AMOUNT), ITEM = IF(AMOUNT = 0, '', ITEM) WHERE UUID = ?");
            preparedStatement2.setInt(1, withdrawAmount);
            preparedStatement2.setInt(2, withdrawAmount);
            preparedStatement2.setString(3, associatedSlot);
            int returnValue = preparedStatement2.executeUpdate();
            if (returnValue < 1) return;
            while (withdrawAmount > 0) {
                int addedAmount = Math.min(itemStack.getMaxStackSize(), withdrawAmount);
                blockInventoryHolder.getInventory().addItem(itemStack.clone().asQuantity(addedAmount));
                withdrawAmount -= addedAmount;
            }
        } catch (SQLException ignored) {
        }
        Location location = blockInventoryHolder.getBlock().getLocation();
        location.getNearbyEntities(10, 10, 10).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .forEach(entity -> {
                    entity.playSound(location, Sound.BLOCK_PISTON_CONTRACT, 1F, 0.8F);
                    entity.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 0.5F);
                });
    }
}