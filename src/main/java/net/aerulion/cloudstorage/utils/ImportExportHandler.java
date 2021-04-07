package net.aerulion.cloudstorage.utils;

import net.aerulion.cloudstorage.block.CloudStorageBlockType;
import net.aerulion.cloudstorage.gui.guis.CloudExportBusGUI;
import net.aerulion.cloudstorage.gui.guis.CloudImportBusGUI;
import net.aerulion.cloudstorage.task.CloudExportBusTask;
import net.aerulion.cloudstorage.task.CloudImportBusTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.List;

public class ImportExportHandler implements Listener {

    public final HashMap<InventoryHolder, List<CloudImportBusGUI>> OPEN_IMPORT_GUIS = new HashMap<>();
    public final HashMap<InventoryHolder, List<CloudExportBusGUI>> OPEN_EXPORT_GUIS = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void listen(InventoryMoveItemEvent event) {
        for (InventoryHolder inventoryHolder : OPEN_IMPORT_GUIS.keySet())
            if (inventoryHolder.equals(event.getDestination().getHolder()))
                OPEN_IMPORT_GUIS.get(inventoryHolder).forEach(CloudImportBusGUI::setContent);
        for (InventoryHolder inventoryHolder : OPEN_EXPORT_GUIS.keySet())
            if (inventoryHolder.equals(event.getSource().getHolder()))
                OPEN_EXPORT_GUIS.get(inventoryHolder).forEach(CloudExportBusGUI::setContent);
        if (event.getSource().getHolder() instanceof BlockInventoryHolder) {
            BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) event.getSource().getHolder();
            CloudStorageBlockType cloudStorageBlockType = CloudStorageBlockType.of(blockInventoryHolder.getInventory().getItem(0));
            if (cloudStorageBlockType != null
                    && cloudStorageBlockType.equals(CloudStorageBlockType.CLOUD_EXPORT_BUS)
                    && blockInventoryHolder.getInventory().firstEmpty() < 0)
                new CloudExportBusTask(blockInventoryHolder);
        }
        if (event.getDestination().getHolder() instanceof BlockInventoryHolder) {
            BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) event.getDestination().getHolder();
            CloudStorageBlockType cloudStorageBlockType = CloudStorageBlockType.of(blockInventoryHolder.getInventory().getItem(0));
            if (cloudStorageBlockType != null
                    && cloudStorageBlockType.equals(CloudStorageBlockType.CLOUD_IMPORT_BUS)
                    && blockInventoryHolder.getInventory().firstEmpty() < 0)
                new CloudImportBusTask(blockInventoryHolder);
        }
    }
}