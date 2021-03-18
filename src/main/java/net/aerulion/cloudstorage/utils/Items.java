package net.aerulion.cloudstorage.utils;

import net.aerulion.cloudstorage.Main;
import net.aerulion.nucleus.api.item.GuiButtonBuilder;
import net.aerulion.nucleus.api.item.ItemBuilder;
import net.aerulion.nucleus.api.item.SkullUtils;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.string.StringUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public enum Items {
    ACCESS_POINT(ItemBuilder.of(SkullUtils.getSkull("https://textures.minecraft.net/texture/3e9fc3c04c4d5a2a9291ebaad9fe8f85cb2f7719af8e47475a05405099471b2e", UUID.fromString("9e87186f-14bb-43fb-b0f9-9fcad97899f5"))).withDisplayName(Component.text("Cloud Access Point").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)).build()),
    BACK(ItemBuilder.of(Material.DARK_OAK_DOOR).withDisplayName(Component.text("Zurück").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)).build()),
    CLOUD_INTERFACE(ItemBuilder.of(SkullUtils.getSkull("https://textures.minecraft.net/texture/d4dff0c31510dc52c21a27b099d0bdf1a9f45c4bb6f8e9c2e98c897f2082d26", UUID.fromString("e58d68f0-b0d6-4593-b456-eea6d0599559"))).withDisplayName(Component.text("Cloud Interface").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)).build()),
    EXPERIENCE_TERMINAL(ItemBuilder.of(SkullUtils.getSkull("https://textures.minecraft.net/texture/6a5589702f0d0a8f8aa3e9328b618f02055bf31e5adc625b9adb161a98d70b7f", UUID.fromString("3e2f0a9b-14ff-4731-88d8-75c13f937c2c"))).withDisplayName(Component.text("Cloud Experience Terminal").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)).build()),
    GUI_BUTTON_10_LEVEL(GuiButtonBuilder.of(Material.LAPIS_LAZULI).ofAmount(10).withDisplayName(Component.text("10 Level").color(Main.PRIMARY_COLOR)).withLore(Messages.DIVIDER.getRaw(), Component.text("Linksklick:").color(Main.LIGHT_ACCENT_COLOR), Component.text("Level einlagern").color(Main.LIGHT_ACCENT_COLOR), Component.text("Rechtsklick:").color(Main.LIGHT_ACCENT_COLOR), Component.text("Level auslagern").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).withGlow().build()),
    GUI_BUTTON_1_LEVEL(GuiButtonBuilder.of(Material.LAPIS_LAZULI).ofAmount(1).withDisplayName(Component.text("1 Level").color(Main.PRIMARY_COLOR)).withLore(Messages.DIVIDER.getRaw(), Component.text("Linksklick:").color(Main.LIGHT_ACCENT_COLOR), Component.text("Level einlagern").color(Main.LIGHT_ACCENT_COLOR), Component.text("Rechtsklick:").color(Main.LIGHT_ACCENT_COLOR), Component.text("Level auslagern").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).withGlow().build()),
    GUI_BUTTON_30_LEVEL(GuiButtonBuilder.of(Material.LAPIS_LAZULI).ofAmount(30).withDisplayName(Component.text("30 Level").color(Main.PRIMARY_COLOR)).withLore(Messages.DIVIDER.getRaw(), Component.text("Linksklick:").color(Main.LIGHT_ACCENT_COLOR), Component.text("Level einlagern").color(Main.LIGHT_ACCENT_COLOR), Component.text("Rechtsklick:").color(Main.LIGHT_ACCENT_COLOR), Component.text("Level auslagern").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).withGlow().build()),
    GUI_BUTTON_CET_ACCESS_PRIVATE(GuiButtonBuilder.of(Material.REPEATING_COMMAND_BLOCK).withDisplayName(Component.text("Zugriff: Privat").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Nur du kannst auf").color(Main.LIGHT_ACCENT_COLOR), Component.text("dieses CET zugreifen.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_BUTTON_CET_ACCESS_PUBLIC(GuiButtonBuilder.of(Material.COMMAND_BLOCK).withDisplayName(Component.text("Zugriff: Öffentlich").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Jeder mit Rechten auf").color(Main.LIGHT_ACCENT_COLOR), Component.text("dieser Region kann auf").color(Main.LIGHT_ACCENT_COLOR), Component.text("dieses CET zugreifen.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_BUTTON_CET_UPGRADES(GuiButtonBuilder.of(Material.EMERALD).withDisplayName(Component.text("Upgrades").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Hier kannst du").color(Main.LIGHT_ACCENT_COLOR), Component.text("Upgrades für dieses").color(Main.LIGHT_ACCENT_COLOR), Component.text("CET kaufen.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).withGlow().build()),
    GUI_BUTTON_CSS_ACCESS_PRIVATE(GuiButtonBuilder.of(Material.REPEATING_COMMAND_BLOCK).withDisplayName(Component.text("Zugriff: Privat").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Nur du kannst auf").color(Main.LIGHT_ACCENT_COLOR), Component.text("diesen CSS zugreifen.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_BUTTON_CSS_ACCESS_PUBLIC(GuiButtonBuilder.of(Material.COMMAND_BLOCK).withDisplayName(Component.text("Zugriff: Öffentlich").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Jeder mit Rechten auf").color(Main.LIGHT_ACCENT_COLOR), Component.text("dieser Region kann auf").color(Main.LIGHT_ACCENT_COLOR), Component.text("diesen CSS zugreifen.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_BUTTON_CSS_UPGRADES(GuiButtonBuilder.of(Material.EMERALD).withDisplayName(Component.text("Upgrades").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Hier kannst du").color(Main.LIGHT_ACCENT_COLOR), Component.text("Upgrades für diesen").color(Main.LIGHT_ACCENT_COLOR), Component.text("CSS kaufen.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).withGlow().build()),
    GUI_BUTTON_EXP_BOTTLES(GuiButtonBuilder.of(Material.HOPPER).withDisplayName(Component.text("Erfahrungsfläschchen").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Linksklick:").color(Main.LIGHT_ACCENT_COLOR), Component.text("64 Fläschchen auslagern").color(Main.LIGHT_ACCENT_COLOR), Component.text("Rechtsklick:").color(Main.LIGHT_ACCENT_COLOR), Component.text("Gesamtes Inventar füllen").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).withGlow().build()),
    GUI_BUTTON_INTERFACE_ALL(GuiButtonBuilder.of(Material.STRUCTURE_VOID).withDisplayName(Component.text(CloudInterfaceMode.ALL.getDisplay()).color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Alle Items in deinem").color(Main.LIGHT_ACCENT_COLOR), Component.text("Inventar einlagern.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).withGlow().build()),
    GUI_BUTTON_INTERFACE_HOTBAR(GuiButtonBuilder.of(Material.STRUCTURE_VOID).withDisplayName(Component.text(CloudInterfaceMode.HOTBAR_ONLY.getDisplay()).color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Nur die Items in deiner").color(Main.LIGHT_ACCENT_COLOR), Component.text("Hotbar einlagern.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).withGlow().build()),
    GUI_BUTTON_INTERFACE_INVENTORY(GuiButtonBuilder.of(Material.STRUCTURE_VOID).withDisplayName(Component.text(CloudInterfaceMode.INVENTORY_ONLY.getDisplay()).color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Nur die Items in deinem").color(Main.LIGHT_ACCENT_COLOR), Component.text("oberen Inventar einlagern.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).withGlow().build()),
    GUI_BUTTON_SHOP_CLOUD_EXPERIENCE_TERMINAL(GuiButtonBuilder.of(SkullUtils.getSkull("https://textures.minecraft.net/texture/6a5589702f0d0a8f8aa3e9328b618f02055bf31e5adc625b9adb161a98d70b7f", UUID.fromString("3e2f0a9b-14ff-4731-88d8-75c13f937c2c"))).withDisplayName(Component.text("Cloud Experience Terminal").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Kaufe ein Cloud Experience Terminal").color(Main.LIGHT_ACCENT_COLOR), Component.text("für dein Cloud Netzwerk.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw(), Component.text("Preis:").color(Main.LIGHT_ACCENT_COLOR), Component.text(Messages.decimalFormat.format(Upgrade.EXPERIENCE_TERMINAL_BASE.getPrice()) + " CT").color(Main.PRIMARY_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_BUTTON_SHOP_CLOUD_EXPERIENCE_TERMINAL_NO_PERMISSION(GuiButtonBuilder.of(SkullUtils.getSkull("https://textures.minecraft.net/texture/6a5589702f0d0a8f8aa3e9328b618f02055bf31e5adc625b9adb161a98d70b7f", UUID.fromString("3e2f0a9b-14ff-4731-88d8-75c13f937c2c"))).withDisplayName(Component.text("Cloud Experience Terminal").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Kaufe ein Cloud Experience Terminal").color(Main.LIGHT_ACCENT_COLOR), Component.text("für dein Cloud Netzwerk.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw(), Component.text("Du hast nicht die benötigten").color(Main.ERROR_COLOR), Component.text("Rechte, dies zu kaufen.").color(Main.ERROR_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_BUTTON_SHOP_CLOUD_INTERFACE(GuiButtonBuilder.of(SkullUtils.getSkull("https://textures.minecraft.net/texture/d4dff0c31510dc52c21a27b099d0bdf1a9f45c4bb6f8e9c2e98c897f2082d26", UUID.fromString("e58d68f0-b0d6-4593-b456-eea6d0599559"))).withDisplayName(Component.text("Cloud Interface").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Kaufe ein Cloud Interface").color(Main.LIGHT_ACCENT_COLOR), Component.text("für dein Cloud Netzwerk.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw(), Component.text("Preis:").color(Main.LIGHT_ACCENT_COLOR), Component.text("25.000 CT").color(Main.PRIMARY_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_BUTTON_SHOP_CLOUD_INTERFACE_NO_PERMISSION(GuiButtonBuilder.of(SkullUtils.getSkull("https://textures.minecraft.net/texture/d4dff0c31510dc52c21a27b099d0bdf1a9f45c4bb6f8e9c2e98c897f2082d26", UUID.fromString("e58d68f0-b0d6-4593-b456-eea6d0599559"))).withDisplayName(Component.text("Cloud Interface").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Kaufe ein Cloud Interface").color(Main.LIGHT_ACCENT_COLOR), Component.text("für dein Cloud Netzwerk.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw(), Component.text("Du hast nicht die benötigten").color(Main.ERROR_COLOR), Component.text("Rechte, dies zu kaufen.").color(Main.ERROR_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_BUTTON_SHOP_CLOUD_STORAGE_SLOT(GuiButtonBuilder.of(SkullUtils.getSkull("https://textures.minecraft.net/texture/3e9fc3c04c4d5a2a9291ebaad9fe8f85cb2f7719af8e47475a05405099471b2e", UUID.fromString("9e87186f-14bb-43fb-b0f9-9fcad97899f5"))).withDisplayName(Component.text("Cloud Storage Slot").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Kaufe einen zusätzlichen Cloud").color(Main.LIGHT_ACCENT_COLOR), Component.text("Storage Slot für dein Cloud").color(Main.LIGHT_ACCENT_COLOR), Component.text("Netzwerk. Der erste Access Point").color(Main.LIGHT_ACCENT_COLOR), Component.text("ist im Preis inbegriffen.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw(), Component.text("Preis:").color(Main.LIGHT_ACCENT_COLOR), Component.text(Messages.decimalFormat.format(Upgrade.CLOUD_STORAGE_SLOT_BASE.getPrice()) + " CT").color(Main.PRIMARY_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_BUTTON_SHOP_CLOUD_STORAGE_SLOT_NO_PERMISSION(GuiButtonBuilder.of(SkullUtils.getSkull("https://textures.minecraft.net/texture/3e9fc3c04c4d5a2a9291ebaad9fe8f85cb2f7719af8e47475a05405099471b2e", UUID.fromString("9e87186f-14bb-43fb-b0f9-9fcad97899f5"))).withDisplayName(Component.text("Cloud Storage Slot").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Kaufe einen zusätzlichen Cloud").color(Main.LIGHT_ACCENT_COLOR), Component.text("Storage Slot für dein Cloud").color(Main.LIGHT_ACCENT_COLOR), Component.text("Netzwerk. Der erste Access Point").color(Main.LIGHT_ACCENT_COLOR), Component.text("ist im Preis inbegriffen.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw(), Component.text("Du hast nicht die benötigten").color(Main.ERROR_COLOR), Component.text("Rechte, dies zu kaufen.").color(Main.ERROR_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_BUTTON_SHOP_WIRELESS_CLOUD_INTERFACE(GuiButtonBuilder.of(Material.STRUCTURE_VOID).withDisplayName(Component.text("Wireless Cloud Interface").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Kaufe ein Wireless Cloud Interface").color(Main.LIGHT_ACCENT_COLOR), Component.text("für dein Cloud Netzwerk.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw(), Component.text("Preis:").color(Main.LIGHT_ACCENT_COLOR), Component.text("50.000 CT").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).withGlow().build()),
    GUI_BUTTON_SHOP_WIRELESS_CLOUD_INTERFACE_NO_PERMISSION(GuiButtonBuilder.of(Material.STRUCTURE_VOID).withDisplayName(Component.text("Wireless Cloud Interface").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Kaufe ein Wireless Cloud Interface").color(Main.LIGHT_ACCENT_COLOR), Component.text("für dein Cloud Netzwerk.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw(), Component.text("Du hast nicht die benötigten").color(Main.ERROR_COLOR), Component.text("Rechte, dies zu kaufen.").color(Main.ERROR_COLOR), Messages.DIVIDER.getRaw()).withGlow().build()),
    GUI_BUTTON_UPGRADE_ADDITIONAL_ACCESS_POINT(GuiButtonBuilder.of(SkullUtils.getSkull("https://textures.minecraft.net/texture/3e9fc3c04c4d5a2a9291ebaad9fe8f85cb2f7719af8e47475a05405099471b2e", UUID.fromString("9e87186f-14bb-43fb-b0f9-9fcad97899f5"))).withDisplayName(Component.text("Zusätzlicher AP").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Kaufe einen zusätzlichen AP,").color(Main.LIGHT_ACCENT_COLOR), Component.text("welcher mit diesem synchronisiert").color(Main.LIGHT_ACCENT_COLOR), Component.text("ist, um von verschiedenen Orten").color(Main.LIGHT_ACCENT_COLOR), Component.text("darauf zugreifen zu können.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw(), Component.text("Linksklick: Access Point").color(Main.LIGHT_ACCENT_COLOR), Component.text("5.000 CT").color(Main.PRIMARY_COLOR), Messages.DIVIDER.getRaw(), Component.text("Rechtsklick: Wireless Access Point").color(Main.LIGHT_ACCENT_COLOR), Component.text("10.000 CT").color(Main.PRIMARY_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_BUTTON_UPGRADE_EXPERIENCE_1(getUpgradeExperience(Upgrade.UPGRADE_1)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_10(getUpgradeExperience(Upgrade.UPGRADE_10)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_10_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_10)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_11(getUpgradeExperience(Upgrade.UPGRADE_11)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_11_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_11)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_12(getUpgradeExperience(Upgrade.UPGRADE_12)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_12_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_12)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_13(getUpgradeExperience(Upgrade.UPGRADE_13)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_13_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_13)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_14(getUpgradeExperience(Upgrade.UPGRADE_14)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_14_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_14)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_15(getUpgradeExperience(Upgrade.UPGRADE_15)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_15_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_15)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_1_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_1)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_2(getUpgradeExperience(Upgrade.UPGRADE_2)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_2_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_2)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_3(getUpgradeExperience(Upgrade.UPGRADE_3)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_3_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_3)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_4(getUpgradeExperience(Upgrade.UPGRADE_4)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_4_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_4)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_5(getUpgradeExperience(Upgrade.UPGRADE_5)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_5_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_5)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_6(getUpgradeExperience(Upgrade.UPGRADE_6)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_6_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_6)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_7(getUpgradeExperience(Upgrade.UPGRADE_7)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_7_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_7)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_8(getUpgradeExperience(Upgrade.UPGRADE_8)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_8_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_8)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_9(getUpgradeExperience(Upgrade.UPGRADE_9)),
    GUI_BUTTON_UPGRADE_EXPERIENCE_9_ACTIVE(getUpgradeExperienceActive(Upgrade.UPGRADE_9)),
    GUI_BUTTON_UPGRADE_ITEM_1(getUpgradeItem(Upgrade.UPGRADE_1)),
    GUI_BUTTON_UPGRADE_ITEM_10(getUpgradeItem(Upgrade.UPGRADE_10)),
    GUI_BUTTON_UPGRADE_ITEM_10_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_10)),
    GUI_BUTTON_UPGRADE_ITEM_11(getUpgradeItem(Upgrade.UPGRADE_11)),
    GUI_BUTTON_UPGRADE_ITEM_11_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_11)),
    GUI_BUTTON_UPGRADE_ITEM_12(getUpgradeItem(Upgrade.UPGRADE_12)),
    GUI_BUTTON_UPGRADE_ITEM_12_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_12)),
    GUI_BUTTON_UPGRADE_ITEM_13(getUpgradeItem(Upgrade.UPGRADE_13)),
    GUI_BUTTON_UPGRADE_ITEM_13_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_13)),
    GUI_BUTTON_UPGRADE_ITEM_14(getUpgradeItem(Upgrade.UPGRADE_14)),
    GUI_BUTTON_UPGRADE_ITEM_14_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_14)),
    GUI_BUTTON_UPGRADE_ITEM_15(getUpgradeItem(Upgrade.UPGRADE_15)),
    GUI_BUTTON_UPGRADE_ITEM_15_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_15)),
    GUI_BUTTON_UPGRADE_ITEM_1_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_1)),
    GUI_BUTTON_UPGRADE_ITEM_2(getUpgradeItem(Upgrade.UPGRADE_2)),
    GUI_BUTTON_UPGRADE_ITEM_2_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_2)),
    GUI_BUTTON_UPGRADE_ITEM_3(getUpgradeItem(Upgrade.UPGRADE_3)),
    GUI_BUTTON_UPGRADE_ITEM_3_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_3)),
    GUI_BUTTON_UPGRADE_ITEM_4(getUpgradeItem(Upgrade.UPGRADE_4)),
    GUI_BUTTON_UPGRADE_ITEM_4_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_4)),
    GUI_BUTTON_UPGRADE_ITEM_5(getUpgradeItem(Upgrade.UPGRADE_5)),
    GUI_BUTTON_UPGRADE_ITEM_5_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_5)),
    GUI_BUTTON_UPGRADE_ITEM_6(getUpgradeItem(Upgrade.UPGRADE_6)),
    GUI_BUTTON_UPGRADE_ITEM_6_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_6)),
    GUI_BUTTON_UPGRADE_ITEM_7(getUpgradeItem(Upgrade.UPGRADE_7)),
    GUI_BUTTON_UPGRADE_ITEM_7_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_7)),
    GUI_BUTTON_UPGRADE_ITEM_8(getUpgradeItem(Upgrade.UPGRADE_8)),
    GUI_BUTTON_UPGRADE_ITEM_8_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_8)),
    GUI_BUTTON_UPGRADE_ITEM_9(getUpgradeItem(Upgrade.UPGRADE_9)),
    GUI_BUTTON_UPGRADE_ITEM_9_ACTIVE(getUpgradeItemActive(Upgrade.UPGRADE_9)),
    GUI_BUTTON_WHOLE_INVENTORY(GuiButtonBuilder.of(Material.CHEST).withDisplayName(Component.text("Gesamtes Inventar").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Linksklick:").color(Main.LIGHT_ACCENT_COLOR), Component.text("Alle Items einsortieren").color(Main.LIGHT_ACCENT_COLOR), Component.text("Rechtsklick:").color(Main.LIGHT_ACCENT_COLOR), Component.text("Gesamtes Inventar füllen").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_NO_ITEM(GuiButtonBuilder.of(Material.LIGHT_BLUE_STAINED_GLASS_PANE).withDisplayName(Component.text("Dieser CSS ist leer").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Klicke auf ein Item in").color(Main.LIGHT_ACCENT_COLOR), Component.text("deinem Inventar, um").color(Main.LIGHT_ACCENT_COLOR), Component.text("es einzulagern.").color(Main.LIGHT_ACCENT_COLOR), Messages.DIVIDER.getRaw()).build()),
    GUI_SPACER(ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).withDisplayName(Component.empty()).build()),
    GUI_SPACER_ACCESS_POINT(ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).withCustomModelData(100).withDisplayName(Component.empty()).build()),
    GUI_SPACER_EXPERIENCE_TERMINAL(ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).withCustomModelData(101).withDisplayName(Component.empty()).build()),
    GUI_SPACER_INVISIBLE(ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).withCustomModelData(1).withDisplayName(Component.empty()).build()),
    WIRELESS_CLOUD_ACCESS_POINT(ItemBuilder.of(Material.HEART_OF_THE_SEA).withDisplayName(Component.text("Wireless Cloud Access Point").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)).withUnsafeEnchantment(Enchantment.DURABILITY, 1).build()),
    WIRELESS_CLOUD_INTERFACE(ItemBuilder.of(Material.STRUCTURE_VOID).withDisplayName(Component.text("Wireless Cloud Interface").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)).withUnsafeEnchantment(Enchantment.DURABILITY, 1).build());

    private final ItemStack itemStack;

    Items(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack get() {
        return itemStack.clone();
    }

    private static ItemStack getUpgradeItem(Upgrade upgrade) {
        return GuiButtonBuilder.of(upgrade.getMaterial()).withDisplayName(Component.text("Upgrade " + StringUtils.getRomanNumber(upgrade.getNumber())).color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Maximale Kapazität:").color(Main.LIGHT_ACCENT_COLOR), Component.text(Messages.decimalFormat.format(upgrade.getCapacityItem()) + " Items").color(Main.PRIMARY_COLOR), Component.text("Preis:").color(Main.LIGHT_ACCENT_COLOR), Component.text(Messages.decimalFormat.format(upgrade.getPrice()) + " CT").color(Main.PRIMARY_COLOR), Messages.DIVIDER.getRaw()).build();
    }

    private static ItemStack getUpgradeItemActive(Upgrade upgrade) {
        return GuiButtonBuilder.of(upgrade.getMaterial()).withDisplayName(Component.text("Upgrade " + StringUtils.getRomanNumber(upgrade.getNumber())).color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Bereits erworben").color(Main.LIGHT_ACCENT_COLOR).decorate(TextDecoration.ITALIC), Messages.DIVIDER.getRaw()).withGlow().build();
    }

    private static ItemStack getUpgradeExperience(Upgrade upgrade) {
        return GuiButtonBuilder.of(upgrade.getMaterial()).withDisplayName(Component.text("Upgrade " + StringUtils.getRomanNumber(upgrade.getNumber())).color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Maximale Kapazität:").color(Main.LIGHT_ACCENT_COLOR), Component.text(Messages.decimalFormat.format(upgrade.getCapacityExperience()) + " XP").color(Main.PRIMARY_COLOR), Component.text("Preis:").color(Main.LIGHT_ACCENT_COLOR), Component.text(Messages.decimalFormat.format(upgrade.getPrice()) + " CT").color(Main.PRIMARY_COLOR), Messages.DIVIDER.getRaw()).build();
    }

    private static ItemStack getUpgradeExperienceActive(Upgrade upgrade) {
        return GuiButtonBuilder.of(upgrade.getMaterial()).withDisplayName(Component.text("Upgrade " + StringUtils.getRomanNumber(upgrade.getNumber())).color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Messages.DIVIDER.getRaw(), Component.text("Bereits erworben").color(Main.LIGHT_ACCENT_COLOR).decorate(TextDecoration.ITALIC), Messages.DIVIDER.getRaw()).withGlow().build();
    }

    public static ItemStack getCloudExperienceTerminal(String uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        ItemStack itemStack = Items.EXPERIENCE_TERMINAL.get();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && offlinePlayer.getName() != null)
            itemMeta.lore(Collections.singletonList(Component.text("Eigentümer: ").color(Main.LIGHT_ACCENT_COLOR).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE).append(Component.text(offlinePlayer.getName()).color(Main.PRIMARY_COLOR))));
        itemStack.setItemMeta(itemMeta);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_EXPERIENCE_TERMINAL_OWNER_UUID.get(), uuid);
        return itemStack;
    }

    public static ItemStack getCloudInterface(String uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        ItemStack itemStack = Items.CLOUD_INTERFACE.get().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && offlinePlayer.getName() != null)
            itemMeta.lore(Collections.singletonList(Component.text("Eigentümer: ").color(Main.LIGHT_ACCENT_COLOR).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE).append(Component.text(offlinePlayer.getName()).color(Main.PRIMARY_COLOR))));
        itemStack.setItemMeta(itemMeta);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get(), uuid);
        return itemStack;
    }

    public static ItemStack getWirelessCloudInterface(String uuid, CloudInterfaceMode cloudInterfaceMode) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        ItemStack itemStack = Items.WIRELESS_CLOUD_INTERFACE.get().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && offlinePlayer.getName() != null)
            itemMeta.lore(Arrays.asList(Component.text("Eigentümer: ").color(Main.LIGHT_ACCENT_COLOR).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE).append(Component.text(offlinePlayer.getName()).color(Main.PRIMARY_COLOR)), Component.text("Modus: ").color(Main.LIGHT_ACCENT_COLOR).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE).append(Component.text(cloudInterfaceMode.getDisplay()).color(Main.PRIMARY_COLOR))));
        itemStack.setItemMeta(itemMeta);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get(), uuid);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_MODE.get(), cloudInterfaceMode.name());
        return itemStack;
    }

    public static ItemStack getCloudAccessPoint(String uuid, String owner) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(owner));
        ItemStack itemStack = Items.ACCESS_POINT.get().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && offlinePlayer.getName() != null)
            itemMeta.lore(Collections.singletonList(Component.text("Eigentümer: ").color(Main.LIGHT_ACCENT_COLOR).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE).append(Component.text(offlinePlayer.getName()).color(Main.PRIMARY_COLOR))));
        itemStack.setItemMeta(itemMeta);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get(), uuid);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_OWNER_UUID.get(), owner);
        return itemStack;
    }

    public static ItemStack getWirelessCloudAccessPoint(String uuid, String owner) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(owner));
        ItemStack itemStack = Items.WIRELESS_CLOUD_ACCESS_POINT.get().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && offlinePlayer.getName() != null)
            itemMeta.lore(Collections.singletonList(Component.text("Eigentümer: ").color(Main.LIGHT_ACCENT_COLOR).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE).append(Component.text(offlinePlayer.getName()).color(Main.PRIMARY_COLOR))));
        itemStack.setItemMeta(itemMeta);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get(), uuid);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_OWNER_UUID.get(), owner);
        return itemStack;
    }
}