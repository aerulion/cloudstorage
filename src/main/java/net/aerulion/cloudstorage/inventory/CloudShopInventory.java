package net.aerulion.cloudstorage.inventory;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.string.CenterPixel;
import net.aerulion.nucleus.api.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CloudShopInventory {
    public static Inventory create() {
        Inventory inventory = Bukkit.createInventory(Main.cloudStorageInventoryHolder, 27, StringUtils.generateCenteredString(Main.PRIMARY_COLOR + "§lCloud Shop", CenterPixel.INVENTORY_TITLE));
        ItemStack spacer = NbtUtils.setNBTString(Items.GUI_SPACER.get().clone(), NBT.KEY_GUI_TYPE.get(), NBT.VALUE_GUI_TYPE_CLOUD_SHOP.get());
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, spacer);
        inventory.setItem(11, Items.GUI_BUTTON_SHOP_CLOUD_STORAGE_SLOT.get());
        inventory.setItem(13, Items.GUI_BUTTON_SHOP_CLOUD_INTERFACE.get());
        inventory.setItem(15, Items.GUI_BUTTON_SHOP_WIRELESS_CLOUD_INTERFACE.get());
        return inventory;
    }
}
