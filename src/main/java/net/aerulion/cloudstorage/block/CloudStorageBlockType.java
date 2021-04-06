package net.aerulion.cloudstorage.block;

import lombok.Getter;
import net.aerulion.cloudstorage.utils.Item;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@Getter
public enum CloudStorageBlockType {
    CLOUD_EXPERIENCE_TERMINAL(Material.LECTERN, Item.EXPERIENCE_TERMINAL),
    CLOUD_ACCESS_POINT(Material.DISPENSER, Item.ACCESS_POINT),
    CLOUD_INTERFACE(Material.LECTERN, Item.CLOUD_INTERFACE),
    CLOUD_IMPORT_BUS(Material.BARREL, Item.IMPORT_BUS),
    CLOUD_EXPORT_BUS(Material.BARREL, Item.EXPORT_BUS);

    private final Material blockMaterial;
    private final Item item;

    CloudStorageBlockType(Material blockMaterial, Item item) {
        this.blockMaterial = blockMaterial;
        this.item = item;
    }

    public static @Nullable CloudStorageBlockType of(ItemStack itemStack) {
        CloudStorageBlockType cloudStorageBlockType;
        try {
            return valueOf(NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_BLOCK_TYPE.get()));
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }
}