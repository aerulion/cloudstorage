package net.aerulion.cloudstorage.utils;

import net.aerulion.cloudstorage.Main;
import net.aerulion.nucleus.api.item.ItemUtils;
import net.aerulion.nucleus.api.item.SkullUtils;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.string.StringUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public enum Items {
    ACCESS_POINT(ItemUtils.buildItemStack(SkullUtils.getSkull("http://textures.minecraft.net/texture/3e9fc3c04c4d5a2a9291ebaad9fe8f85cb2f7719af8e47475a05405099471b2e", UUID.fromString("9e87186f-14bb-43fb-b0f9-9fcad97899f5")), Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Cloud Access Point", null, false)),
    BACK(ItemUtils.buildItemStack(Material.DARK_OAK_DOOR, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Zurück", null, false)),
    CLOUD_INTERFACE(ItemUtils.buildItemStack(SkullUtils.getSkull("http://textures.minecraft.net/texture/d4dff0c31510dc52c21a27b099d0bdf1a9f45c4bb6f8e9c2e98c897f2082d26", UUID.fromString("e58d68f0-b0d6-4593-b456-eea6d0599559")), Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Cloud Interface", null, false)),
    GUI_BUTTON_ACCESS_PRIVATE(ItemUtils.buildGuiButton(Material.REPEATING_COMMAND_BLOCK, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Zugriff: Privat", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Nur du kannst auf", Main.LIGHT_ACCENT_COLOR + "diesen CSS zugreifen.", Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2)),
    GUI_BUTTON_ACCESS_PUBLIC(ItemUtils.buildGuiButton(Material.COMMAND_BLOCK, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Zugriff: Öffentlich", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Jeder mit Rechten auf", Main.LIGHT_ACCENT_COLOR + "dieser Region kann auf", Main.LIGHT_ACCENT_COLOR + "diesen CSS zugreifen.", Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2)),
    GUI_BUTTON_INTERFACE_ALL(ItemUtils.buildGuiButton(Material.STRUCTURE_VOID, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + CloudInterfaceMode.ALL.getDisplay(), Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Alle Items in deinem", Main.LIGHT_ACCENT_COLOR + "Inventar einlagern.", Main.LIGHT_ACCENT_COLOR + "%divider"), true, 2)),
    GUI_BUTTON_INTERFACE_HOTBAR(ItemUtils.buildGuiButton(Material.STRUCTURE_VOID, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + CloudInterfaceMode.HOTBAR_ONLY.getDisplay(), Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Nur die Items in deiner", Main.LIGHT_ACCENT_COLOR + "Hotbar einlagern.", Main.LIGHT_ACCENT_COLOR + "%divider"), true, 2)),
    GUI_BUTTON_INTERFACE_INVENTORY(ItemUtils.buildGuiButton(Material.STRUCTURE_VOID, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + CloudInterfaceMode.INVENTORY_ONLY.getDisplay(), Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Nur die Items in deinem", Main.LIGHT_ACCENT_COLOR + "oberen Inventar einlagern.", Main.LIGHT_ACCENT_COLOR + "%divider"), true, 2)),
    GUI_BUTTON_INVENTORY(ItemUtils.buildGuiButton(Material.CHEST, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Gesamtes Inventar", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Linksklick:", Main.LIGHT_ACCENT_COLOR + "Alle Items einsortieren", Main.LIGHT_ACCENT_COLOR + "Rechtsklick:", Main.LIGHT_ACCENT_COLOR + "Gesamtes Inventar füllen", Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2)),
    GUI_BUTTON_UPGRADES(ItemUtils.buildGuiButton(Material.EMERALD, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Upgrades", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Hier kannst du", Main.LIGHT_ACCENT_COLOR + "Upgrades für diesen", Main.LIGHT_ACCENT_COLOR + "CSS kaufen.", Main.LIGHT_ACCENT_COLOR + "%divider"), true, 2)),
    GUI_BUTTON_UPGRADE_1(getUpgradeItem(Upgrade.UPGRADE_1)),
    GUI_BUTTON_UPGRADE_10(getUpgradeItem(Upgrade.UPGRADE_10)),
    GUI_BUTTON_UPGRADE_10_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_10)),
    GUI_BUTTON_UPGRADE_11(getUpgradeItem(Upgrade.UPGRADE_11)),
    GUI_BUTTON_UPGRADE_11_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_11)),
    GUI_BUTTON_UPGRADE_12(getUpgradeItem(Upgrade.UPGRADE_12)),
    GUI_BUTTON_UPGRADE_12_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_12)),
    GUI_BUTTON_UPGRADE_13(getUpgradeItem(Upgrade.UPGRADE_13)),
    GUI_BUTTON_UPGRADE_13_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_13)),
    GUI_BUTTON_UPGRADE_14(getUpgradeItem(Upgrade.UPGRADE_14)),
    GUI_BUTTON_UPGRADE_14_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_14)),
    GUI_BUTTON_UPGRADE_15(getUpgradeItem(Upgrade.UPGRADE_15)),
    GUI_BUTTON_UPGRADE_15_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_15)),
    GUI_BUTTON_UPGRADE_1_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_1)),
    GUI_BUTTON_UPGRADE_2(getUpgradeItem(Upgrade.UPGRADE_2)),
    GUI_BUTTON_UPGRADE_2_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_2)),
    GUI_BUTTON_UPGRADE_3(getUpgradeItem(Upgrade.UPGRADE_3)),
    GUI_BUTTON_UPGRADE_3_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_3)),
    GUI_BUTTON_UPGRADE_4(getUpgradeItem(Upgrade.UPGRADE_4)),
    GUI_BUTTON_UPGRADE_4_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_4)),
    GUI_BUTTON_UPGRADE_5(getUpgradeItem(Upgrade.UPGRADE_5)),
    GUI_BUTTON_UPGRADE_5_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_5)),
    GUI_BUTTON_UPGRADE_6(getUpgradeItem(Upgrade.UPGRADE_6)),
    GUI_BUTTON_UPGRADE_6_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_6)),
    GUI_BUTTON_UPGRADE_7(getUpgradeItem(Upgrade.UPGRADE_7)),
    GUI_BUTTON_UPGRADE_7_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_7)),
    GUI_BUTTON_UPGRADE_8(getUpgradeItem(Upgrade.UPGRADE_8)),
    GUI_BUTTON_UPGRADE_8_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_8)),
    GUI_BUTTON_UPGRADE_9(getUpgradeItem(Upgrade.UPGRADE_9)),
    GUI_BUTTON_UPGRADE_9_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_9)),
    GUI_BUTTON_SHOP_CLOUD_STORAGE_SLOT(ItemUtils.buildGuiButton(SkullUtils.getSkull("http://textures.minecraft.net/texture/3e9fc3c04c4d5a2a9291ebaad9fe8f85cb2f7719af8e47475a05405099471b2e", UUID.fromString("9e87186f-14bb-43fb-b0f9-9fcad97899f5")), Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Cloud Storage Slot", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Kaufe einen zusätzlichen Cloud", Main.LIGHT_ACCENT_COLOR + "Storage Slot für dein Cloud", Main.LIGHT_ACCENT_COLOR + "Netzwerk. Der erste Access Point", Main.LIGHT_ACCENT_COLOR + "ist im Preis inbegriffen.", Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Preis:", Main.PRIMARY_COLOR + "500 Ct", Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2)),
    GUI_BUTTON_SHOP_CLOUD_INTERFACE(ItemUtils.buildGuiButton(SkullUtils.getSkull("http://textures.minecraft.net/texture/d4dff0c31510dc52c21a27b099d0bdf1a9f45c4bb6f8e9c2e98c897f2082d26", UUID.fromString("e58d68f0-b0d6-4593-b456-eea6d0599559")), Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Cloud Interface", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Kaufe ein Cloud Interface", Main.LIGHT_ACCENT_COLOR + "für dein Cloud Netzwerk.", Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Preis:", Main.PRIMARY_COLOR + "25.000 Ct", Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2)),
    GUI_BUTTON_SHOP_WIRELESS_CLOUD_INTERFACE(ItemUtils.buildGuiButton(Material.STRUCTURE_VOID, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Wireless Cloud Interface", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Kaufe ein Wireless Cloud Interface", Main.LIGHT_ACCENT_COLOR + "für dein Cloud Netzwerk.", Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Preis:", Main.PRIMARY_COLOR + "50.000 Ct", Main.LIGHT_ACCENT_COLOR + "%divider"), true, 2)),
    GUI_BUTTON_SHOP_CLOUD_STORAGE_SLOT_NO_PERMISSION(ItemUtils.buildGuiButton(SkullUtils.getSkull("http://textures.minecraft.net/texture/3e9fc3c04c4d5a2a9291ebaad9fe8f85cb2f7719af8e47475a05405099471b2e", UUID.fromString("9e87186f-14bb-43fb-b0f9-9fcad97899f5")), Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Cloud Storage Slot", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Kaufe einen zusätzlichen Cloud", Main.LIGHT_ACCENT_COLOR + "Storage Slot für dein Cloud", Main.LIGHT_ACCENT_COLOR + "Netzwerk. Der erste Access Point", Main.LIGHT_ACCENT_COLOR + "ist im Preis inbegriffen.", Main.LIGHT_ACCENT_COLOR + "%divider", Main.ERROR_COLOR + "Du hast nicht die benötigten", Main.ERROR_COLOR + "Rechte, dies zu kaufen.", Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2)),
    GUI_BUTTON_SHOP_CLOUD_INTERFACE_NO_PERMISSION(ItemUtils.buildGuiButton(SkullUtils.getSkull("http://textures.minecraft.net/texture/d4dff0c31510dc52c21a27b099d0bdf1a9f45c4bb6f8e9c2e98c897f2082d26", UUID.fromString("e58d68f0-b0d6-4593-b456-eea6d0599559")), Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Cloud Interface", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Kaufe ein Cloud Interface", Main.LIGHT_ACCENT_COLOR + "für dein Cloud Netzwerk.", Main.LIGHT_ACCENT_COLOR + "%divider", Main.ERROR_COLOR + "Du hast nicht die benötigten", Main.ERROR_COLOR + "Rechte, dies zu kaufen.", Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2)),
    GUI_BUTTON_SHOP_WIRELESS_CLOUD_INTERFACE_NO_PERMISSION(ItemUtils.buildGuiButton(Material.STRUCTURE_VOID, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Wireless Cloud Interface", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Kaufe ein Wireless Cloud Interface", Main.LIGHT_ACCENT_COLOR + "für dein Cloud Netzwerk.", Main.LIGHT_ACCENT_COLOR + "%divider", Main.ERROR_COLOR + "Du hast nicht die benötigten", Main.ERROR_COLOR + "Rechte, dies zu kaufen.", Main.LIGHT_ACCENT_COLOR + "%divider"), true, 2)),
    GUI_BUTTON_UPGRADE_ADDITIONAL_ACCESS_POINT(ItemUtils.buildGuiButton(SkullUtils.getSkull("http://textures.minecraft.net/texture/3e9fc3c04c4d5a2a9291ebaad9fe8f85cb2f7719af8e47475a05405099471b2e", UUID.fromString("9e87186f-14bb-43fb-b0f9-9fcad97899f5")), Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Zusätzlicher AP", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Kaufe einen zusätzlichen AP,", Main.LIGHT_ACCENT_COLOR + "welcher mit diesem synchronisiert", Main.LIGHT_ACCENT_COLOR + "ist, um von verschiedenen Orten", Main.LIGHT_ACCENT_COLOR + "darauf zugreifen zu können.", Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Linksklick: Access Point", Main.PRIMARY_COLOR + "5.000 Ct", Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Rechtsklick: Wireless Access Point", Main.PRIMARY_COLOR + "10.000 Ct", Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2)),
    GUI_NO_ITEM(ItemUtils.buildGuiButton(Material.LIGHT_BLUE_STAINED_GLASS_PANE, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Dieser CSS ist leer", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Klicke auf ein Item in", Main.LIGHT_ACCENT_COLOR + "deinem Inventar, um", Main.LIGHT_ACCENT_COLOR + "es einzulagern.", Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2)),
    GUI_SPACER(ItemUtils.buildItemStack(Material.BLACK_STAINED_GLASS_PANE, ChatColor.RESET.toString(), null, false)),
    WIRELESS_CLOUD_ACCESS_POINT(ItemUtils.buildItemStack(Material.HEART_OF_THE_SEA, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Wireless Cloud Access Point", null, true)),
    WIRELESS_CLOUD_INTERFACE(ItemUtils.buildItemStack(Material.STRUCTURE_VOID, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Wireless Cloud Interface", null, true));

    private final ItemStack itemStack;

    Items(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack get() {
        return itemStack.clone();
    }

    private static ItemStack getUpgradeItem(Upgrade upgrade) {
        return ItemUtils.buildGuiButton(upgrade.getMaterial(), Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Upgrade " + StringUtils.getRomanNumber(upgrade.getNumber()), Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Maximale Kapazität:", Main.PRIMARY_COLOR + Messages.decimalFormat.format(upgrade.getCapacity()) + " Items", Main.LIGHT_ACCENT_COLOR + "Preis:", Main.PRIMARY_COLOR + Messages.decimalFormat.format(upgrade.getPrice()) + " Ct", Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2);
    }

    private static ItemStack getUpgradeItemActive(Upgrade upgrade) {
        return ItemUtils.buildGuiButton(upgrade.getMaterial(), Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Upgrade " + StringUtils.getRomanNumber(upgrade.getNumber()), Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + ChatColor.ITALIC.toString() + "Bereits erworben", Main.LIGHT_ACCENT_COLOR + "%divider"), true, 2);
    }

    public static ItemStack getCloudInterface(String uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        ItemStack itemStack = Items.CLOUD_INTERFACE.get().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null)
            itemMeta.setLore(Collections.singletonList(Main.LIGHT_ACCENT_COLOR + "Eigentümer: " + Main.PRIMARY_COLOR + offlinePlayer.getName()));
        itemStack.setItemMeta(itemMeta);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get(), uuid);
        return itemStack;
    }

    public static ItemStack getWirelessCloudInterface(String uuid, CloudInterfaceMode cloudInterfaceMode) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        ItemStack itemStack = Items.WIRELESS_CLOUD_INTERFACE.get().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null)
            itemMeta.setLore(Arrays.asList(Main.LIGHT_ACCENT_COLOR + "Eigentümer: " + Main.PRIMARY_COLOR + offlinePlayer.getName(), Main.LIGHT_ACCENT_COLOR + "Modus: " + Main.PRIMARY_COLOR + cloudInterfaceMode.getDisplay()));
        itemStack.setItemMeta(itemMeta);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get(), uuid);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_MODE.get(), cloudInterfaceMode.name());
        return itemStack;
    }

    public static ItemStack getCloudAccessPoint(String uuid, String owner) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(owner));
        ItemStack itemStack = Items.ACCESS_POINT.get().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null)
            itemMeta.setLore(Collections.singletonList(Main.LIGHT_ACCENT_COLOR + "Eigentümer: " + Main.PRIMARY_COLOR + offlinePlayer.getName()));
        itemStack.setItemMeta(itemMeta);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get(), uuid);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_OWNER_UUID.get(), owner);
        return itemStack;
    }

    public static ItemStack getWirelessCloudAccessPoint(String uuid, String owner) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(owner));
        ItemStack itemStack = Items.WIRELESS_CLOUD_ACCESS_POINT.get().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null)
            itemMeta.setLore(Collections.singletonList(Main.LIGHT_ACCENT_COLOR + "Eigentümer: " + Main.PRIMARY_COLOR + offlinePlayer.getName()));
        itemStack.setItemMeta(itemMeta);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get(), uuid);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_OWNER_UUID.get(), owner);
        return itemStack;
    }
}