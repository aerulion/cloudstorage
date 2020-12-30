package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Lectern;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class PlaceCloudExperienceTerminalListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getItemInHand().getType().equals(Material.PLAYER_HEAD)) {
            String ownerUUID = NbtUtils.getNBTString(event.getItemInHand(), NBT.KEY_CLOUD_EXPERIENCE_TERMINAL_OWNER_UUID.get());
            if (!ownerUUID.equals("")) {
                if (Main.worldGuard.createProtectionQuery().testBlockPlace(event.getPlayer(), event.getBlock().getLocation(), Material.LECTERN)) {
                    Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                        event.getBlockPlaced().setType(Material.LECTERN, true);
                        BlockData blockData = event.getBlockPlaced().getBlockData();
                        Directional directional = (Directional) blockData;
                        directional.setFacing(getDirection(event.getPlayer()));
                        event.getBlockPlaced().setBlockData(blockData);
                        Lectern lectern = (Lectern) event.getBlockPlaced().getState();
                        lectern.getInventory().setItem(0, NbtUtils.setNBTString(new ItemStack(Material.WRITTEN_BOOK), NBT.KEY_CLOUD_EXPERIENCE_TERMINAL_OWNER_UUID.get(), ownerUUID));
                    }, 1L);
                }
            }
        }
    }

    private BlockFace getDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90.0F) % 360.0F;
        if (rotation < 0.0D)
            rotation += 360.0D;
        if ((0.0D <= rotation) && (rotation < 45.0D))
            return BlockFace.EAST;
        if ((45.0D <= rotation) && (rotation < 135.0D))
            return BlockFace.SOUTH;
        if ((135.0D <= rotation) && (rotation < 225.0D))
            return BlockFace.WEST;
        if ((225.0D <= rotation) && (rotation < 315.0D))
            return BlockFace.NORTH;
        if ((315.0D <= rotation) && (rotation < 360.0D))
            return BlockFace.EAST;
        return BlockFace.NORTH;
    }
}