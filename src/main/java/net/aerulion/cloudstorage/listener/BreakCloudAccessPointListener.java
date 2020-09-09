package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakCloudAccessPointListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.DISPENSER)) {
            Dispenser dispenser = (Dispenser) event.getBlock().getState();
            if (dispenser.getInventory().getItem(0) != null) {
                if (dispenser.getInventory().getItem(0).getType().equals(Material.PAPER)) {
                    if (!NbtUtils.getNBTString(dispenser.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()).equals("")) {
                        String uuid = NbtUtils.getNBTString(dispenser.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get());
                        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), NbtUtils.setNBTString(Items.ACCESS_POINT.get().clone(), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get(), uuid));
                        event.setDropItems(false);
                        dispenser.getInventory().clear();
                    }
                }
            }
        }
    }
}