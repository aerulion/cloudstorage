package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Lectern;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PreventExplodeListener implements Listener {
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (!event.isCancelled()) {
            List<Block> blocks = new ArrayList<>();
            for (Block block : event.blockList()) {
                if (block.getState() instanceof Dispenser) {
                    Dispenser dispenser = (Dispenser) block.getState();
                    ItemStack itemStack = dispenser.getInventory().getItem(0);
                    if (itemStack != null && itemStack.getType().equals(Material.PAPER)) {
                        if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()).equals("")) {
                            blocks.add(block);
                        }
                    }
                }
                if (block.getState() instanceof Lectern) {
                    Lectern lectern = (Lectern) block.getState();
                    ItemStack itemStack = lectern.getInventory().getItem(0);
                    if (itemStack != null && itemStack.getType().equals(Material.WRITTEN_BOOK)) {
                        if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get()).equals("")) {
                            blocks.add(block);
                        }
                    }
                }
            }
            for (Block block : blocks)
                event.blockList().remove(block);
        }
    }

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent event) {
        if (!event.isCancelled()) {
            List<Block> blocks = new ArrayList<>();
            for (Block block : event.blockList()) {
                if (block.getState() instanceof Dispenser) {
                    Dispenser dispenser = (Dispenser) block.getState();
                    ItemStack itemStack = dispenser.getInventory().getItem(0);
                    if (itemStack != null && itemStack.getType().equals(Material.PAPER)) {
                        if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()).equals("")) {
                            blocks.add(block);
                        }
                    }
                }
                if (block.getState() instanceof Lectern) {
                    Lectern lectern = (Lectern) block.getState();
                    ItemStack itemStack = lectern.getInventory().getItem(0);
                    if (itemStack != null && itemStack.getType().equals(Material.WRITTEN_BOOK)) {
                        if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get()).equals("")) {
                            blocks.add(block);
                        }
                    }
                }
            }
            for (Block block : blocks)
                event.blockList().remove(block);
        }
    }
}