package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class PlaceAccessPointListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getItemInHand().getType().equals(Material.PLAYER_HEAD)) {
            if (!NbtUtils.getNBTString(event.getItemInHand(), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()).equals("")) {
                ItemStack itemStack = event.getItemInHand().clone();
                Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                    event.getBlockPlaced().setType(Material.DISPENSER, true);
                    BlockData blockData = event.getBlockPlaced().getBlockData();
                    Directional directional = (Directional) blockData;
                    directional.setFacing(BlockFace.DOWN);
                    event.getBlockPlaced().setBlockData(blockData);
                    Dispenser dispenser = (Dispenser) event.getBlockPlaced().getState();
                    dispenser.getInventory().setItem(0, NbtUtils.setNBTString(new ItemStack(Material.PAPER), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get(), NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get())));
                }, 1L);
            }
        }
    }
}