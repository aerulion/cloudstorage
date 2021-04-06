package net.aerulion.cloudstorage.utils;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.block.CloudStorageBlockType;
import net.aerulion.cloudstorage.gui.guis.CloudExportBusGUI;
import net.aerulion.cloudstorage.gui.guis.CloudImportBusGUI;
import net.aerulion.cloudstorage.task.CloudExportBusTask;
import net.aerulion.cloudstorage.task.CloudImportBusTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ImportExportHandler extends BukkitRunnable implements Listener {

    public HashMap<InventoryHolder, CloudImportBusGUI> OPEN_IMPORT_GUIS = new HashMap<>();
    public HashMap<InventoryHolder, CloudExportBusGUI> OPEN_EXPORT_GUIS = new HashMap<>();
    public Set<BlockInventoryHolder> ACTIVE_IMPORT_BUSES = new HashSet<>();
    public Set<BlockInventoryHolder> ACTIVE_EXPORT_BUSES = new HashSet<>();

    public ImportExportHandler() {
        this.runTaskTimer(Main.plugin, 600L, 600L);
    }

    @Override
    public void run() {
        ACTIVE_IMPORT_BUSES.forEach(CloudImportBusTask::new);
        ACTIVE_EXPORT_BUSES.forEach(CloudExportBusTask::new);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void listen(InventoryMoveItemEvent event) {
        for (InventoryHolder inventoryHolder : OPEN_IMPORT_GUIS.keySet())
            if (inventoryHolder.equals(event.getDestination().getHolder()))
                OPEN_IMPORT_GUIS.get(inventoryHolder).setContent();
        for (InventoryHolder inventoryHolder : OPEN_EXPORT_GUIS.keySet())
            if (inventoryHolder.equals(event.getSource().getHolder()))
                OPEN_EXPORT_GUIS.get(inventoryHolder).setContent();
        if (event.getSource().getHolder() instanceof BlockInventoryHolder) {
            BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) event.getSource().getHolder();
            CloudStorageBlockType cloudStorageBlockType = CloudStorageBlockType.of(blockInventoryHolder.getInventory().getItem(0));
            if (cloudStorageBlockType != null && cloudStorageBlockType.equals(CloudStorageBlockType.CLOUD_EXPORT_BUS))
                ACTIVE_EXPORT_BUSES.add(blockInventoryHolder);
        }
        if (event.getDestination().getHolder() instanceof BlockInventoryHolder) {
            BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) event.getDestination().getHolder();
            CloudStorageBlockType cloudStorageBlockType = CloudStorageBlockType.of(blockInventoryHolder.getInventory().getItem(0));
            if (cloudStorageBlockType != null && cloudStorageBlockType.equals(CloudStorageBlockType.CLOUD_IMPORT_BUS))
                ACTIVE_IMPORT_BUSES.add(blockInventoryHolder);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void listen(ChunkUnloadEvent event) {
        ACTIVE_IMPORT_BUSES.removeIf(blockInventoryHolder -> blockInventoryHolder.getBlock().getChunk().equals(event.getChunk()));
        ACTIVE_EXPORT_BUSES.removeIf(blockInventoryHolder -> blockInventoryHolder.getBlock().getChunk().equals(event.getChunk()));
    }
}