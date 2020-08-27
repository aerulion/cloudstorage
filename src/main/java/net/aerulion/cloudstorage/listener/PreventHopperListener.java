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
            ItemStack itemStack = event.getSource().getItem(0) == null ? event.getItem() : event.getSource().getItem(0);
            if (itemStack.getType().equals(Material.PAPER)) {
                if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()).equals("")) {
                    event.setCancelled(true);
                }
            }
        }
        if (event.getDestination().getType().equals(InventoryType.DISPENSER)) {
            if (event.getDestination().getItem(0) != null) {
                if (event.getDestination().getItem(0).getType().equals(Material.PAPER)) {
                    if (!NbtUtils.getNBTString(event.getDestination().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()).equals("")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}