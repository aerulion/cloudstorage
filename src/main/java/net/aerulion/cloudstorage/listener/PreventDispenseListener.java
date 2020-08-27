package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;

public class PreventDispenseListener implements Listener {
    @EventHandler
    public void onDispense(BlockDispenseEvent event) {
        if (event.getBlock().getState() instanceof Dispenser) {
            Dispenser dispenser = (Dispenser) event.getBlock().getState();
            ItemStack itemStack = dispenser.getInventory().getItem(0) == null ? event.getItem() : dispenser.getInventory().getItem(0);
            if (itemStack.getType().equals(Material.PAPER)) {
                if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()).equals("")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}