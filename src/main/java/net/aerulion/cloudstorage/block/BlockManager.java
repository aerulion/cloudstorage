package net.aerulion.cloudstorage.block;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.block.blocks.*;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.Permission;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BlockManager implements Listener {

    public static HashMap<CloudStorageBlockType, CloudStorageBlock> BLOCKS = new HashMap<>();

    static {
        BLOCKS.put(CloudStorageBlockType.CLOUD_ACCESS_POINT, new CloudAccessPointBlock());
        BLOCKS.put(CloudStorageBlockType.CLOUD_EXPERIENCE_TERMINAL, new CloudExperienceTerminalBlock());
        BLOCKS.put(CloudStorageBlockType.CLOUD_EXPORT_BUS, new CloudExportBusBlock());
        BLOCKS.put(CloudStorageBlockType.CLOUD_IMPORT_BUS, new CloudImportBusBlock());
        BLOCKS.put(CloudStorageBlockType.CLOUD_INTERFACE, new CloudInterfaceBlock());
    }

    //Handle Block Breaking
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getState() instanceof BlockInventoryHolder) {
            BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) event.getBlock().getState();
            ItemStack itemStack = blockInventoryHolder.getInventory().getItem(0);
            if (itemStack == null || !(itemStack.getType().equals(Material.PAPER) || itemStack.getType().equals(Material.WRITTEN_BOOK)))
                return;
            CloudStorageBlockType cloudStorageBlockType = CloudStorageBlockType.of(itemStack);
            if (cloudStorageBlockType == null) return;
            BLOCKS.get(cloudStorageBlockType).handleBreak(event.getBlock().getLocation(), itemStack);
            blockInventoryHolder.getInventory().setItem(0, null);
            Arrays.stream(blockInventoryHolder.getInventory().getContents())
                    .skip(1)
                    .filter(Objects::nonNull)
                    .forEach(drop -> event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop));
            event.setDropItems(false);
            blockInventoryHolder.getInventory().clear();
        }
    }

    //Handle Block Placement
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getItemInHand().getType().equals(Material.PLAYER_HEAD)) {
            CloudStorageBlockType cloudStorageBlockType = CloudStorageBlockType.of(event.getItemInHand());
            if (cloudStorageBlockType == null) return;
            BLOCKS.get(cloudStorageBlockType).handlePlace(
                    event.getItemInHand().clone(),
                    event.getBlockPlaced(),
                    event.getPlayer());
        }
    }

    //Handle Open GUI
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && event.getClickedBlock() != null
                && !(event.getPlayer().isSneaking() && event.getItem() != null)
                && event.getClickedBlock().getState() instanceof BlockInventoryHolder) {
            BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) event.getClickedBlock().getState();
            ItemStack itemStack = blockInventoryHolder.getInventory().getItem(0);
            if (itemStack == null || !(itemStack.getType().equals(Material.PAPER) || itemStack.getType().equals(Material.WRITTEN_BOOK)))
                return;
            CloudStorageBlockType cloudStorageBlockType = CloudStorageBlockType.of(itemStack);
            if (cloudStorageBlockType == null) return;
            event.setCancelled(true);
            if (Main.MAINTENANCE_MODE) {
                event.getPlayer().sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(event.getPlayer(), SoundType.ALERT);
                return;
            }
            if (Main.DISABLED_WORLDS.contains(event.getPlayer().getWorld().getName())
                    && !event.getPlayer().hasPermission(Permission.BYPASS_WORLD.get())) {
                event.getPlayer().sendMessage(Messages.ERROR_DISABLED_WORLD.get());
                SoundUtils.playSound(event.getPlayer(), SoundType.ERROR);
                return;
            }
            if (!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)
                    && !event.getPlayer().hasPermission(Permission.BYPASS_GAMEMODE.get())) {
                event.getPlayer().sendMessage(Messages.ERROR_GAME_MODE.get());
                SoundUtils.playSound(event.getPlayer(), SoundType.ERROR);
                return;
            }
            BLOCKS.get(cloudStorageBlockType).handleOpenGUI(
                    event.getPlayer(),
                    event.getClickedBlock(),
                    itemStack);
        }
    }

    //Prevent Hopper Interaction
    @EventHandler(ignoreCancelled = true)
    public void onHopper(InventoryMoveItemEvent event) {
        CloudStorageBlockType destinationType = CloudStorageBlockType.of(event.getDestination().getItem(0));
        CloudStorageBlockType sourceType = CloudStorageBlockType.of(event.getSource().getItem(0));
        if (sourceType != null)
            event.setCancelled(true);
        if (destinationType != null && !destinationType.equals(CloudStorageBlockType.CLOUD_IMPORT_BUS))
            event.setCancelled(true);
    }

    //Prevent Dispense
    @EventHandler(ignoreCancelled = true)
    public void onDispense(BlockDispenseEvent event) {
        if (event.getBlock().getState() instanceof BlockInventoryHolder) {
            BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) event.getBlock().getState();
            CloudStorageBlockType cloudStorageBlockType = CloudStorageBlockType.of(blockInventoryHolder.getInventory().getItem(0));
            if (cloudStorageBlockType != null)
                event.setCancelled(true);
        }
    }

    //Prevent BlockExplode
    @EventHandler(ignoreCancelled = true)
    public void onBlockExplode(BlockExplodeEvent event) {
        removeCloudBlocks(event.blockList());
    }

    //Prevent BlockExplode
    @EventHandler(ignoreCancelled = true)
    public void onBlockExplode(EntityExplodeEvent event) {
        removeCloudBlocks(event.blockList());
    }

    private void removeCloudBlocks(List<Block> blockList) {
        Iterator<Block> blockIterator = blockList.iterator();
        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();
            if (block.getState() instanceof BlockInventoryHolder) {
                BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) block.getState();
                CloudStorageBlockType cloudStorageBlockType = CloudStorageBlockType.of(blockInventoryHolder.getInventory().getItem(0));
                if (cloudStorageBlockType != null)
                    blockIterator.remove();
            }
        }
    }
}