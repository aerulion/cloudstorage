package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.block.CloudStorageBlockType;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LegacyConverter implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void listen(BlockBreakEvent event) {
        if (event.getBlock().getState() instanceof BlockInventoryHolder)
            convertLegacyMetaItem((BlockInventoryHolder) event.getBlock().getState());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void listen(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && event.getClickedBlock() != null
                && event.getClickedBlock().getState() instanceof BlockInventoryHolder) {
            convertLegacyMetaItem((BlockInventoryHolder) event.getClickedBlock().getState());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void listen(InventoryClickEvent event) {
        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null) return;
        ItemStack convertedItem = convertLegacyMetaItem(itemStack);
        if (!convertedItem.isSimilar(itemStack))
            event.setCurrentItem(convertedItem);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void listen(BlockDispenseEvent event) {
        convertLegacyMetaItem((BlockInventoryHolder) event.getBlock().getState());
    }

    private void convertLegacyMetaItem(BlockInventoryHolder blockInventoryHolder) {
        ItemStack metaItem = blockInventoryHolder.getInventory().getItem(0);
        if (metaItem != null) {
            if (!NbtUtils.getNBTString(metaItem, NBT.KEY_CLOUD_STORAGE_BLOCK_TYPE.get()).equals("")) {
                blockInventoryHolder.getInventory().setItem(blockInventoryHolder.getInventory().getSize() - 1, metaItem.clone());
                metaItem.setAmount(0);
                return;
            }
            ItemStack converted = convertLegacyMetaItem(metaItem);
            if (!converted.isSimilar(metaItem)) {
                blockInventoryHolder.getInventory().setItem(blockInventoryHolder.getInventory().getSize() - 1, converted.clone());
                metaItem.setAmount(0);
            }
        }
    }

    private ItemStack convertLegacyMetaItem(@NotNull ItemStack itemStack) {
        if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_BLOCK_TYPE.get()).equals("")) return itemStack;
        if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()).equals(""))
            itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_BLOCK_TYPE.get(), CloudStorageBlockType.CLOUD_ACCESS_POINT.name());
        if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_EXPERIENCE_TERMINAL_OWNER_UUID.get()).equals(""))
            itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_BLOCK_TYPE.get(), CloudStorageBlockType.CLOUD_EXPERIENCE_TERMINAL.name());
        if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get()).equals(""))
            itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_BLOCK_TYPE.get(), CloudStorageBlockType.CLOUD_INTERFACE.name());
        return itemStack;
    }
}
