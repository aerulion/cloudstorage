package net.aerulion.cloudstorage.block;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Item;
import net.aerulion.cloudstorage.utils.MetaData;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class CloudStorageBlock {

    public abstract @NotNull CloudStorageBlockType getType();

    public abstract @NotNull List<MetaData> getMetaData(ItemStack itemStack);

    public abstract void openGUI(Player player, Block block, ItemStack itemStack);

    public ItemStack getItem(List<MetaData> metaData) {
        return Item.getBlockWithMetaData(getType(), metaData);
    }

    public void handleBreak(@NotNull Location location, @NotNull ItemStack itemStack) {
        location.getWorld().dropItemNaturally(location, getItem(getMetaData(itemStack)));
    }

    public void handlePlace(@NotNull ItemStack itemStack, Block block, Player player) {
        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            block.setType(getType().getBlockMaterial(), true);
            if (block.getBlockData() instanceof Directional) {
                BlockData blockData = block.getBlockData();
                Directional directional = (Directional) blockData;
                directional.setFacing(getType().getBlockMaterial().equals(Material.DISPENSER)
                        ? BlockFace.DOWN
                        : getDirection(player));
                block.setBlockData(blockData);
            }
            if (block.getState() instanceof BlockInventoryHolder) {
                ItemStack metaItem = new ItemStack(getType().getBlockMaterial().equals(Material.LECTERN)
                        ? Material.WRITTEN_BOOK
                        : Material.PAPER);
                metaItem = NbtUtils.setNBTString(metaItem, NBT.KEY_CLOUD_STORAGE_BLOCK_TYPE.get(), getType().name());
                for (MetaData metaData : getMetaData(itemStack))
                    metaItem = NbtUtils.setNBTString(metaItem, metaData.getKey(), metaData.getValue());
                BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) block.getState();
                blockInventoryHolder.getInventory().setItem(0, metaItem);
            }
        }, 1L);
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

    public void handleOpenGUI(Player player, Block block, ItemStack itemStack) {
        SoundUtils.playSound(player, block.getLocation(), SoundType.OPEN_CONTAINER);
        openGUI(player, block, itemStack);
    }
}