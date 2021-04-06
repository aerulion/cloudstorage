package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.gui.guis.CloudImportBusGUI;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Objects;

public class CloudImportBusTask extends BukkitRunnable {

    private final BlockInventoryHolder blockInventoryHolder;

    public CloudImportBusTask(BlockInventoryHolder blockInventoryHolder) {
        this.blockInventoryHolder = blockInventoryHolder;
        this.runTask(Main.plugin);
    }

    @Override
    public void run() {
        ItemStack metaItem = blockInventoryHolder.getInventory().getItem(0);
        if (metaItem == null) {
            Main.importExportHandler.ACTIVE_IMPORT_BUSES.remove(blockInventoryHolder);
            return;
        }
        String ownerUUID = NbtUtils.getNBTString(metaItem, NBT.KEY_CLOUD_IMPORT_BUS_OWNER_UUID.get());
        Location location = blockInventoryHolder.getBlock().getLocation();
        location.getNearbyEntities(10, 10, 10).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .forEach(entity -> {
                    entity.playSound(location, Sound.BLOCK_PISTON_EXTEND, 1F, 0.8F);
                    entity.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 0.5F);
                });
        Arrays.stream(blockInventoryHolder.getInventory().getContents())
                .skip(1)
                .filter(Objects::nonNull)
                .forEach(itemStack -> {
                    new StoreItemOrDropTask(
                            itemStack.clone(),
                            ownerUUID,
                            location.add(0, 1, 0));
                    itemStack.setAmount(0);
                });
        CloudImportBusGUI cloudImportBusGUI = Main.importExportHandler.OPEN_IMPORT_GUIS.get(blockInventoryHolder);
        if (cloudImportBusGUI != null)
            cloudImportBusGUI.setContent();
    }
}