package net.aerulion.cloudstorage.inventory;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.CloudStorageSlot;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.string.CenterPixel;
import net.aerulion.nucleus.api.string.StringUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AccessPointInventory {

    public static Inventory create(CloudStorageSlot cloudStorageSlot) {
        Inventory inventory = Bukkit.createInventory(Main.cloudStorageInventoryHolder, 45, StringUtils.generateCenteredString(ChatColor.of(Main.PRIMARY_COLOR) + "§lCloud Access Point", CenterPixel.INVENTORY_TITLE));
        ItemStack spacer = NbtUtils.setNBTString(NbtUtils.setNBTInt(NbtUtils.setNBTInt(NbtUtils.setNBTString(Items.GUI_SPACER.get().clone(), NBT.KEY_GUI_TYPE.get(), NBT.VALUE_GUI_TYPE_ACCESS_POINT.get()), NBT.KEY_CLOUD_STORAGE_SLOT_STORED_AMOUNT.get(), cloudStorageSlot.getStoredAmount()), NBT.KEY_CLOUD_STORAGE_SLOT_CAPACITY.get(), cloudStorageSlot.getCapacity()), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get(), cloudStorageSlot.getUUID());
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, spacer);
        }
        ItemStack storedItem = cloudStorageSlot.getStoredItem() == null ? Items.GUI_NO_ITEM.get().clone() : cloudStorageSlot.getStoredItem().clone();
        if (cloudStorageSlot.getStoredItem() != null) {
            ItemMeta storedItemMeta = storedItem.getItemMeta();
            List<String> lore = storedItemMeta.hasLore() ? storedItemMeta.getLore() : new ArrayList<>();
            String amountString1 = "§7Eingelagert:";
            String amountString2 = ChatColor.of(Main.PRIMARY_COLOR) + Messages.decimalFormat.format(cloudStorageSlot.getStoredAmount()) + "§7/" + ChatColor.of(Main.PRIMARY_COLOR) + Messages.decimalFormat.format(cloudStorageSlot.getCapacity());
            int pixelLength = Math.max(StringUtils.getPixelLength(amountString1), StringUtils.getPixelLength(amountString2)) + 20;
            lore.add("§7" + StringUtils.generateLine((int) Math.round(pixelLength / 4D)));
            lore.add(StringUtils.generateCenteredString(amountString1, (int) Math.round(pixelLength / 2D)) + "§r  ");
            lore.add(StringUtils.generateCenteredString(amountString2, (int) Math.round(pixelLength / 2D)) + "§r  ");
            lore.add("§7" + StringUtils.generateLine((int) Math.round(pixelLength / 4D)));
            storedItemMeta.setLore(lore);
            storedItem.setItemMeta(storedItemMeta);
        }
        inventory.setItem(13, storedItem);
        inventory.setItem(29, Items.GUI_BUTTON_UPGRADES.get().clone());
        inventory.setItem(31, Items.GUI_BUTTON_INVENTORY.get().clone());
        inventory.setItem(33, cloudStorageSlot.isPrivate() ? Items.GUI_BUTTON_ACCESS_PRIVATE.get() : Items.GUI_BUTTON_ACCESS_PUBLIC.get());
        return inventory;
    }
}