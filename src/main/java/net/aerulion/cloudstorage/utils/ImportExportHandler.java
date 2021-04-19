package net.aerulion.cloudstorage.utils;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.block.CloudStorageBlockType;
import net.aerulion.cloudstorage.gui.guis.CloudExportBusGUI;
import net.aerulion.cloudstorage.gui.guis.CloudImportBusGUI;
import net.aerulion.cloudstorage.task.CloudExportBusTask;
import net.aerulion.cloudstorage.task.CloudImportBusTask;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.InventoryHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ImportExportHandler implements Listener {

    public final HashMap<InventoryHolder, List<CloudImportBusGUI>> OPEN_IMPORT_GUIS = new HashMap<>();
    public final HashMap<InventoryHolder, List<CloudExportBusGUI>> OPEN_EXPORT_GUIS = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void listen(InventoryMoveItemEvent event) {
        for (InventoryHolder inventoryHolder : OPEN_IMPORT_GUIS.keySet())
            if (inventoryHolder.equals(event.getDestination().getHolder()))
                Bukkit.getScheduler().runTaskLater(Main.plugin, () -> OPEN_IMPORT_GUIS.get(inventoryHolder).forEach(CloudImportBusGUI::setContent), 1L);
        for (InventoryHolder inventoryHolder : OPEN_EXPORT_GUIS.keySet())
            if (inventoryHolder.equals(event.getSource().getHolder()))
                Bukkit.getScheduler().runTaskLater(Main.plugin, () -> OPEN_EXPORT_GUIS.get(inventoryHolder).forEach(CloudExportBusGUI::setContent), 1L);
        if (event.getSource().getHolder() instanceof BlockInventoryHolder) {
            BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) event.getSource().getHolder();
            CloudStorageBlockType cloudStorageBlockType = CloudStorageBlockType.of(blockInventoryHolder.getInventory().getItem(blockInventoryHolder.getInventory().getSize() - 1));
            if (cloudStorageBlockType != null
                    && cloudStorageBlockType.equals(CloudStorageBlockType.CLOUD_EXPORT_BUS)
                    && Arrays.stream(blockInventoryHolder.getInventory().getContents()).filter(Objects::nonNull).count() == 1) {
                new CloudExportBusTask(blockInventoryHolder);
            }
        }
        if (event.getDestination().getHolder() instanceof BlockInventoryHolder) {
            BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) event.getDestination().getHolder();
            CloudStorageBlockType cloudStorageBlockType = CloudStorageBlockType.of(blockInventoryHolder.getInventory().getItem(blockInventoryHolder.getInventory().getSize() - 1));
            if (cloudStorageBlockType != null
                    && cloudStorageBlockType.equals(CloudStorageBlockType.CLOUD_IMPORT_BUS)
                    && blockInventoryHolder.getInventory().firstEmpty() < 0)
                new CloudImportBusTask(blockInventoryHolder);
        }
    }
}