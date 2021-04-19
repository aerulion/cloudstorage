package net.aerulion.cloudstorage.block.blocks;

import net.aerulion.cloudstorage.block.CloudStorageBlock;
import net.aerulion.cloudstorage.block.CloudStorageBlockType;
import net.aerulion.cloudstorage.gui.guis.CloudExportBusGUI;
import net.aerulion.cloudstorage.utils.MetaData;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class CloudExportBusBlock extends CloudStorageBlock {
    @Override
    public @NotNull CloudStorageBlockType getType() {
        return CloudStorageBlockType.CLOUD_EXPORT_BUS;
    }

    @Override
    public @NotNull List<MetaData> getMetaData(ItemStack itemStack) {
        return Arrays.asList(
                new MetaData(
                        NBT.KEY_CLOUD_EXPORT_BUS_OWNER_UUID.get(),
                        NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_EXPORT_BUS_OWNER_UUID.get())),
                new MetaData(
                        NBT.KEY_CLOUD_EXPORT_BUS_ASSOCIATED_SLOT_UUID.get(),
                        NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_EXPORT_BUS_ASSOCIATED_SLOT_UUID.get()))
        );
    }

    @Override
    public void openGUI(Player player, Block block, ItemStack itemStack) {
        new CloudExportBusGUI(player, getMetaData(itemStack).get(0).getValue(), block).open();
    }
}