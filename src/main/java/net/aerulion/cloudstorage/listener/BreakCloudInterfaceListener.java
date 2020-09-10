package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.Material;
import org.bukkit.block.Lectern;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BreakCloudInterfaceListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (Main.worldGuard.createProtectionQuery().testBlockBreak(event.getPlayer(), event.getBlock())) {
            if (event.getBlock().getType().equals(Material.LECTERN)) {
                Lectern lectern = (Lectern) event.getBlock().getState();
                ItemStack itemStack = lectern.getInventory().getItem(0);
                if (itemStack != null) {
                    if (itemStack.getType().equals(Material.WRITTEN_BOOK)) {
                        String uuid = NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get());
                        if (!uuid.equals("")) {
                            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), Items.getCloudInterface(uuid));
                            event.setDropItems(false);
                            lectern.getInventory().clear();
                        }
                    }
                }
            }
        }
    }
}