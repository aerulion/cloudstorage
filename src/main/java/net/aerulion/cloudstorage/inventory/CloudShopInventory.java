package net.aerulion.cloudstorage.inventory;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.cloudstorage.utils.Permission;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.string.CenterPixel;
import net.aerulion.nucleus.api.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CloudShopInventory {
    public static Inventory create(Player player) {
        Inventory inventory = Bukkit.createInventory(Main.cloudStorageInventoryHolder, 27, StringUtils.generateCenteredString(Main.PRIMARY_COLOR + "§lCloud Shop", CenterPixel.INVENTORY_TITLE));
        ItemStack spacer = NbtUtils.setNBTString(Items.GUI_SPACER.get().clone(), NBT.KEY_GUI_TYPE.get(), NBT.VALUE_GUI_TYPE_CLOUD_SHOP.get());
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, spacer);
        inventory.setItem(11, player.hasPermission(Permission.BUY_CLOUD_STORAGE_SLOT.get()) ? Items.GUI_BUTTON_SHOP_CLOUD_STORAGE_SLOT.get() : Items.GUI_BUTTON_SHOP_CLOUD_STORAGE_SLOT_NO_PERMISSION.get());
        inventory.setItem(13, player.hasPermission(Permission.BUY_CLOUD_INTERFACE.get()) ? Items.GUI_BUTTON_SHOP_CLOUD_INTERFACE.get() : Items.GUI_BUTTON_SHOP_CLOUD_INTERFACE_NO_PERMISSION.get());
        inventory.setItem(15, player.hasPermission(Permission.BUY_WIRELESS_CLOUD_INTERFACE.get()) ? Items.GUI_BUTTON_SHOP_WIRELESS_CLOUD_INTERFACE.get() : Items.GUI_BUTTON_SHOP_WIRELESS_CLOUD_INTERFACE_NO_PERMISSION.get());
        return inventory;
    }
}
