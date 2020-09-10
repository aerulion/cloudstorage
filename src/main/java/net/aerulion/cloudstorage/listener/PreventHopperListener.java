package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class PreventHopperListener implements Listener {
    @EventHandler
    public void onMove(InventoryMoveItemEvent event) {
        if (event.getSource().getType().equals(InventoryType.DISPENSER)) {
            ItemStack itemStack = event.getSource().getItem(0);
            if (itemStack == null)
                itemStack = event.getItem();
            if (itemStack.getType().equals(Material.PAPER)) {
                if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()).equals("")) {
                    event.setCancelled(true);
                }
            }
        }
        if (event.getDestination().getType().equals(InventoryType.DISPENSER)) {
            ItemStack itemStack = event.getDestination().getItem(0);
            if (itemStack != null) {
                if (itemStack.getType().equals(Material.PAPER)) {
                    if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()).equals("")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}