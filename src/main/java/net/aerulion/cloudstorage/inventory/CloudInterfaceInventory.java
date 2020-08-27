package net.aerulion.cloudstorage.inventory;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.string.CenterPixel;
import net.aerulion.nucleus.api.string.StringUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CloudInterfaceInventory {

    public static Inventory create(String ownerUUID) {
        Inventory inventory = Bukkit.createInventory(Main.cloudStorageInventoryHolder, 27, StringUtils.generateCenteredString(ChatColor.of(Main.PRIMARY_COLOR) + "§lCloud Interface", CenterPixel.INVENTORY_TITLE));
        ItemStack spacer = NbtUtils.setNBTString(NbtUtils.setNBTString(Items.GUI_SPACER.get().clone(), NBT.KEY_GUI_TYPE.get(), NBT.VALUE_GUI_TYPE_CLOUD_INTERFACE.get()), NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get(), ownerUUID);
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, spacer);
        }
        inventory.setItem(11, Items.GUI_BUTTON_INTERFACE_HOTBAR.get());
        inventory.setItem(13, Items.GUI_BUTTON_INTERFACE_ALL.get());
        inventory.setItem(15, Items.GUI_BUTTON_INTERFACE_INVENTORY.get());
        return inventory;
    }
}