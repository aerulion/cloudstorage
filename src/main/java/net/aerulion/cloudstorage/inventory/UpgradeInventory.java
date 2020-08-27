package net.aerulion.cloudstorage.inventory;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.CloudStorageSlot;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.cloudstorage.utils.Upgrade;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.string.CenterPixel;
import net.aerulion.nucleus.api.string.StringUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UpgradeInventory {

    public static Inventory create(CloudStorageSlot cloudStorageSlot) {
        Inventory inventory = Bukkit.createInventory(Main.cloudStorageInventoryHolder, 45, StringUtils.generateCenteredString(ChatColor.of(Main.PRIMARY_COLOR) + "§lCSS Upgrades", CenterPixel.INVENTORY_TITLE));
        ItemStack spacer = NbtUtils.setNBTString(NbtUtils.setNBTString(Items.GUI_SPACER.get().clone(), NBT.KEY_GUI_TYPE.get(), NBT.VALUE_GUI_TYPE_UPGRADE.get()), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get(), cloudStorageSlot.getUUID());
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, spacer);
        }
        inventory.setItem(10, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_1.getCapacity() ? Items.GUI_BUTTON_UPGRADE_1_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_1.get().clone());
        inventory.setItem(11, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_2.getCapacity() ? Items.GUI_BUTTON_UPGRADE_2_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_2.get().clone());
        inventory.setItem(12, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_3.getCapacity() ? Items.GUI_BUTTON_UPGRADE_3_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_3.get().clone());
        inventory.setItem(13, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_4.getCapacity() ? Items.GUI_BUTTON_UPGRADE_4_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_4.get().clone());
        inventory.setItem(14, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_5.getCapacity() ? Items.GUI_BUTTON_UPGRADE_5_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_5.get().clone());
        inventory.setItem(15, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_6.getCapacity() ? Items.GUI_BUTTON_UPGRADE_6_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_6.get().clone());
        inventory.setItem(16, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_7.getCapacity() ? Items.GUI_BUTTON_UPGRADE_7_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_7.get().clone());
        inventory.setItem(19, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_8.getCapacity() ? Items.GUI_BUTTON_UPGRADE_8_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_8.get().clone());
        inventory.setItem(20, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_9.getCapacity() ? Items.GUI_BUTTON_UPGRADE_9_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_9.get().clone());
        inventory.setItem(21, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_10.getCapacity() ? Items.GUI_BUTTON_UPGRADE_10_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_10.get().clone());
        inventory.setItem(22, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_11.getCapacity() ? Items.GUI_BUTTON_UPGRADE_11_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_11.get().clone());
        inventory.setItem(23, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_12.getCapacity() ? Items.GUI_BUTTON_UPGRADE_12_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_12.get().clone());
        inventory.setItem(24, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_13.getCapacity() ? Items.GUI_BUTTON_UPGRADE_13_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_13.get().clone());
        inventory.setItem(25, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_14.getCapacity() ? Items.GUI_BUTTON_UPGRADE_14_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_14.get().clone());
        inventory.setItem(31, cloudStorageSlot.getCapacity() >= Upgrade.UPGRADE_15.getCapacity() ? Items.GUI_BUTTON_UPGRADE_15_ACTIVE.get().clone() : Items.GUI_BUTTON_UPGRADE_15.get().clone());
        inventory.setItem(37, Items.GUI_BUTTON_UPGRADE_LINKED_ASU.get().clone());
        inventory.setItem(43, Items.BACK.get().clone());

        return inventory;
    }
}