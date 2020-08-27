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
    ACCESS_POINT(ItemUtils.buildItemStack(SkullUtils.getSkull("http://textures.minecraft.net/texture/3d963be53bddc22bbcfba8f291942d2f672bd383e6e733b949e9ba708a7774c8", UUID.fromString("9e87186f-14bb-43fb-b0f9-9fcad97899f5")), ChatColor.of(Main.PRIMARY_COLOR) + "§lCloud Access Point", null, false)),
    BACK(ItemUtils.buildItemStack(Material.DARK_OAK_DOOR, "§e§lZurück", null, false)),
    CLOUD_INTERFACE(ItemUtils.buildItemStack(SkullUtils.getSkull("http://textures.minecraft.net/texture/cca45ef5821a8b107cbfba7d66e997fb6abe5521c155cee2f24b34b3d91a5", UUID.fromString("e58d68f0-b0d6-4593-b456-eea6d0599559")), ChatColor.of(Main.PRIMARY_COLOR) + "§lCloud Interface", null, false)),
    GUI_BUTTON_ACCESS_PUBLIC(ItemUtils.buildGuiButton(Material.COMMAND_BLOCK, "§a§lZugriff: Öffentlich", Arrays.asList("§7%divider", "§7Jeder mit Rechten auf", "§7dieser Region kann auf", "§7diesen CSS zugreifen.", "§7%divider"), false, 2)),
    GUI_BUTTON_ACCESS_PRIVATE(ItemUtils.buildGuiButton(Material.REPEATING_COMMAND_BLOCK, "§a§lZugriff: Privat", Arrays.asList("§7%divider", "§7Nur du kannst auf", "§7diesen CSS zugreifen.", "§7%divider"), false, 2)),
    GUI_BUTTON_INVENTORY(ItemUtils.buildGuiButton(Material.CHEST, "§e§lGesamtes Inventar", Arrays.asList("§7%divider", "§7Linksklick:", "§7Alle Items einsortieren", "§7Rechtsklick:", "§7Gesamtes Inventar füllen", "§7%divider"), false, 2)),
    GUI_BUTTON_UPGRADES(ItemUtils.buildGuiButton(Material.EMERALD, "§a§lUpgrades", Arrays.asList("§7%divider", "§7Hier kannst du", "§7Upgrades für diesen", "§7CSS kaufen.", "§7%divider"), true, 2)),
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
    GUI_BUTTON_UPGRADE_LINKED_ASU(ItemUtils.buildGuiButton(Material.ENDER_CHEST, ChatColor.of(Main.PRIMARY_COLOR) + "§lZusätzlicher AP", Arrays.asList("§7%divider", "§7Erhalte einen zusätzlichen AP,", "§7welcher mit diesem synchronisiert", "§7ist, um von verschiedenen Orten", "§7darauf zugreifen zu können.", "§7%divider", "§7Preis:", ChatColor.of(Main.PRIMARY_COLOR) + "50.000 Ct", "§7%divider"), false, 2)),
    GUI_BUTTON_INTERFACE_ALL(ItemUtils.buildGuiButton(Material.STRUCTURE_VOID, ChatColor.of(Main.PRIMARY_COLOR) + "§lAlles", Arrays.asList("§7%divider", "§7Alle Items in deinem", "§7Inventar einlagern.", "§7%divider"), false, 2)),
    GUI_BUTTON_INTERFACE_INVENTORY(ItemUtils.buildGuiButton(Material.STRUCTURE_VOID, ChatColor.of(Main.PRIMARY_COLOR) + "§lNur Inventar", Arrays.asList("§7%divider", "§7Nur die Items in deinem", "§7oberen Inventar einlagern.", "§7%divider"), false, 2)),
    GUI_BUTTON_INTERFACE_HOTBAR(ItemUtils.buildGuiButton(Material.STRUCTURE_VOID, ChatColor.of(Main.PRIMARY_COLOR) + "§lNur Hotbar", Arrays.asList("§7%divider", "§7Die die Items in deiner", "§7Hotbar einlagern.", "§7%divider"), false, 2)),
    GUI_NO_ITEM(ItemUtils.buildGuiButton(Material.LIME_STAINED_GLASS_PANE, "§a§lDieser CSS ist leer", Arrays.asList("§7%divider", "§7Klicke auf ein Item in", "§7deinem Inventar, um", "§7es einzulagern.", "§7%divider"), false, 2)),
    GUI_SPACER(ItemUtils.buildItemStack(Material.BLACK_STAINED_GLASS_PANE, "§r", null, false));

    private final ItemStack itemStack;

    Items(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack get() {
        return itemStack.clone();
    }

    private static ItemStack getUpgradeItem(Upgrade upgrade) {
        return ItemUtils.buildGuiButton(upgrade.getMaterial(), "§e§lUpgrade " + StringUtils.getRomanNumber(upgrade.getNumber()), Arrays.asList("§7%divider", "§7Maximale Kapazität:", "§e" + Messages.decimalFormat.format(upgrade.getCapacity()) + " Items", "§7Preis:", "§e" + Messages.decimalFormat.format(upgrade.getPrice()) + " Ct", "§7%divider"), false, 2);
    }

    private static ItemStack getUpgradeItemActive(Upgrade upgrade) {
        return ItemUtils.buildGuiButton(upgrade.getMaterial(), "§a§lUpgrade " + StringUtils.getRomanNumber(upgrade.getNumber()), Arrays.asList("§7%divider", "§7§oBereits erworben", "§7%divider"), true, 2);
    }

    public static ItemStack getCloudInterface(String uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        ItemStack itemStack = Items.CLOUD_INTERFACE.get().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Collections.singletonList("§7Besitzer: " + ChatColor.of(Main.PRIMARY_COLOR) + offlinePlayer.getName()));
        itemStack.setItemMeta(itemMeta);
        itemStack = NbtUtils.setNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get(), uuid);
        return itemStack;
    }
}