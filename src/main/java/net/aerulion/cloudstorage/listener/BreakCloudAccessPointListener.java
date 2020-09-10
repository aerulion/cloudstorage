package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BreakCloudAccessPointListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (Main.worldGuard.createProtectionQuery().testBlockBreak(event.getPlayer(), event.getBlock())) {
            if (event.getBlock().getType().equals(Material.DISPENSER)) {
                Dispenser dispenser = (Dispenser) event.getBlock().getState();
                ItemStack itemStack = dispenser.getInventory().getItem(0);
                if (itemStack != null) {
                    if (itemStack.getType().equals(Material.PAPER)) {
                        String uuid = NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get());
                        String owner = NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_OWNER_UUID.get());
                        if (!uuid.equals("") && !owner.equals("")) {
                            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), Items.getCloudAccessPoint(uuid, owner));
                            event.setDropItems(false);
                            dispenser.getInventory().clear();
                        }
                    }
                }
            }
        }
    }
}