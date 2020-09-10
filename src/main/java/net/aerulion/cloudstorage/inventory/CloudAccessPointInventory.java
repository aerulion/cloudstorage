package net.aerulion.cloudstorage.inventory;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.utils.CloudStorageSlot;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.item.ItemUtils;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.string.CenterPixel;
import net.aerulion.nucleus.api.string.StringUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CloudAccessPointInventory {
    public static Inventory create(CloudStorageSlot cloudStorageSlot, boolean isOwner) {
        Inventory inventory = Bukkit.createInventory(Main.cloudStorageInventoryHolder, 45, StringUtils.generateCenteredString(Main.PRIMARY_COLOR + "§lCloud Access Point", CenterPixel.INVENTORY_TITLE));
        ItemStack spacer = NbtUtils.setNBTString(NbtUtils.setNBTString(NbtUtils.setNBTInt(NbtUtils.setNBTInt(NbtUtils.setNBTString(Items.GUI_SPACER.get().clone(), NBT.KEY_GUI_TYPE.get(), NBT.VALUE_GUI_TYPE_CLOUD_ACCESS_POINT.get()), NBT.KEY_CLOUD_STORAGE_SLOT_STORED_AMOUNT.get(), cloudStorageSlot.getStoredAmount()), NBT.KEY_CLOUD_STORAGE_SLOT_CAPACITY.get(), cloudStorageSlot.getCapacity()), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get(), cloudStorageSlot.getUUID()), NBT.KEY_CLOUD_STORAGE_SLOT_OWNER_UUID.get(), cloudStorageSlot.getOwnerUUID());
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, spacer);
        }
        ItemStack storedItem = cloudStorageSlot.getStoredItem() == null ? Items.GUI_NO_ITEM.get().clone() : cloudStorageSlot.getStoredItem().clone();
        if (cloudStorageSlot.getStoredItem() != null) {
            ItemMeta storedItemMeta = storedItem.getItemMeta();
            if (storedItemMeta != null) {
                List<String> lore = storedItemMeta.hasLore() ? storedItemMeta.getLore() : new ArrayList<>();
                if (lore != null) {
                    String amountString1 = "§7Eingelagert:";
                    String amountString2 = Main.PRIMARY_COLOR + Messages.decimalFormat.format(cloudStorageSlot.getStoredAmount()) + "§7/" + Main.PRIMARY_COLOR + Messages.decimalFormat.format(cloudStorageSlot.getCapacity());
                    int pixelLength = Math.max(StringUtils.getPixelLength(amountString1), StringUtils.getPixelLength(amountString2)) + 20;
                    lore.add("§7" + StringUtils.generateLine((int) Math.round(pixelLength / 4D)));
                    lore.add(StringUtils.generateCenteredString(amountString1, (int) Math.round(pixelLength / 2D)) + "§r  ");
                    lore.add(StringUtils.generateCenteredString(amountString2, (int) Math.round(pixelLength / 2D)) + "§r  ");
                    lore.add("§7" + StringUtils.generateLine((int) Math.round(pixelLength / 4D)));
                    storedItemMeta.setLore(lore);
                    storedItem.setItemMeta(storedItemMeta);
                }
            }
        }
        inventory.setItem(13, storedItem);
        if (isOwner)
            inventory.setItem(29, Items.GUI_BUTTON_UPGRADES.get().clone());
        inventory.setItem(31, Items.GUI_BUTTON_INVENTORY.get().clone());
        if (isOwner)
            inventory.setItem(33, cloudStorageSlot.isPrivate() ? Items.GUI_BUTTON_ACCESS_PRIVATE.get() : Items.GUI_BUTTON_ACCESS_PUBLIC.get());
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(cloudStorageSlot.getOwnerUUID()));
        ItemStack itemStack = ItemUtils.buildGuiButton(Material.KNOWLEDGE_BOOK, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Cloud Info", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Netzwerk Eigentümer:", Main.PRIMARY_COLOR + offlinePlayer.getName(), Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2);
        inventory.setItem(44, itemStack);
        return inventory;
    }
}