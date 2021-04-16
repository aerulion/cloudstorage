package net.aerulion.cloudstorage.gui.guis;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.block.CloudStorageBlockType;
import net.aerulion.cloudstorage.gui.GUI;
import net.aerulion.cloudstorage.task.BuyCloudExperienceTerminalTask;
import net.aerulion.cloudstorage.task.BuyCloudStorageSlotTask;
import net.aerulion.cloudstorage.utils.*;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Collections;

public class CloudShopGUI extends GUI {

    public CloudShopGUI(Player player) {
        super(player);
    }

    @Override
    public Component getTitle() {
        return Component.text("Cloud Shop").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD);
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void setContent() {
        inventory.setItem(10, dataContainer.getOwningPlayer().hasPermission(Permission.BUY_CLOUD_STORAGE_SLOT.get()) ? Item.GUI_BUTTON_SHOP_CLOUD_STORAGE_SLOT.get() : Item.GUI_BUTTON_SHOP_CLOUD_STORAGE_SLOT_NO_PERMISSION.get());
        inventory.setItem(12, dataContainer.getOwningPlayer().hasPermission(Permission.BUY_CLOUD_EXPERIENCE_TERMINAL.get()) ? Item.GUI_BUTTON_SHOP_CLOUD_EXPERIENCE_TERMINAL.get() : Item.GUI_BUTTON_SHOP_CLOUD_EXPERIENCE_TERMINAL_NO_PERMISSION.get());
        inventory.setItem(13, dataContainer.getOwningPlayer().hasPermission(Permission.BUY_CLOUD_INTERFACE.get()) ? Item.GUI_BUTTON_SHOP_CLOUD_INTERFACE.get() : Item.GUI_BUTTON_SHOP_CLOUD_INTERFACE_NO_PERMISSION.get());
        inventory.setItem(14, dataContainer.getOwningPlayer().hasPermission(Permission.BUY_WIRELESS_CLOUD_INTERFACE.get()) ? Item.GUI_BUTTON_SHOP_WIRELESS_CLOUD_INTERFACE.get() : Item.GUI_BUTTON_SHOP_WIRELESS_CLOUD_INTERFACE_NO_PERMISSION.get());
        inventory.setItem(15, dataContainer.getOwningPlayer().hasPermission(Permission.BUY_CLOUD_IMPORT_BUS.get()) ? Item.GUI_BUTTON_SHOP_CLOUD_IMPORT_BUS.get() : Item.GUI_BUTTON_SHOP_CLOUD_IMPORT_BUS_NO_PERMISSION.get());
        inventory.setItem(16, dataContainer.getOwningPlayer().hasPermission(Permission.BUY_CLOUD_EXPORT_BUS.get()) ? Item.GUI_BUTTON_SHOP_CLOUD_EXPORT_BUS.get() : Item.GUI_BUTTON_SHOP_CLOUD_EXPORT_BUS_NO_PERMISSION.get());
        fillSpacers();
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        if (event.getRawSlot() == 10) {
            Player player = (Player) event.getWhoClicked();
            if (player.hasCooldown(Material.PLAYER_HEAD))
                return;
            if (Main.MAINTENANCE_MODE) {
                player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(player, SoundType.INFO);
                return;
            }
            if (!player.hasPermission(Permission.BUY_CLOUD_STORAGE_SLOT.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION_BUY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            if (!Main.economy.has(player, Upgrade.CLOUD_STORAGE_SLOT_BASE.getPrice())) {
                player.sendMessage(Messages.ERROR_NOT_ENOUGH_MONEY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            EconomyResponse economyResponse = Main.economy.withdrawPlayer(player, Upgrade.CLOUD_STORAGE_SLOT_BASE.getPrice());
            if (economyResponse.transactionSuccess()) {
                new BuyCloudStorageSlotTask(player);
                player.setCooldown(Material.PLAYER_HEAD, 20);
            } else {
                player.sendMessage(Messages.ERROR_TRANSACTION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
            }
            return;
        }
        if (event.getRawSlot() == 12) {
            Player player = (Player) event.getWhoClicked();
            if (player.hasCooldown(Material.PLAYER_HEAD))
                return;
            if (Main.MAINTENANCE_MODE) {
                player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(player, SoundType.INFO);
                return;
            }
            if (!player.hasPermission(Permission.BUY_CLOUD_EXPERIENCE_TERMINAL.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION_BUY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            if (!Main.economy.has(player, Upgrade.EXPERIENCE_TERMINAL_BASE.getPrice())) {
                player.sendMessage(Messages.ERROR_NOT_ENOUGH_MONEY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            EconomyResponse economyResponse = Main.economy.withdrawPlayer(player, Upgrade.EXPERIENCE_TERMINAL_BASE.getPrice());
            if (economyResponse.transactionSuccess()) {
                new BuyCloudExperienceTerminalTask(player);
                player.setCooldown(Material.PLAYER_HEAD, 20);
            } else {
                player.sendMessage(Messages.ERROR_TRANSACTION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
            }
            return;
        }
        if (event.getRawSlot() == 13) {
            Player player = (Player) event.getWhoClicked();
            if (player.hasCooldown(Material.PLAYER_HEAD))
                return;
            if (Main.MAINTENANCE_MODE) {
                player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(player, SoundType.INFO);
                return;
            }
            if (!player.hasPermission(Permission.BUY_CLOUD_INTERFACE.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION_BUY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            if (!Main.economy.has(player, 25000D)) {
                player.sendMessage(Messages.ERROR_NOT_ENOUGH_MONEY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            EconomyResponse economyResponse = Main.economy.withdrawPlayer(player, 25000D);
            if (economyResponse.transactionSuccess()) {
                player.sendMessage(Messages.MESSAGE_CLOUD_INTERFACE_BOUGHT.get());
                SoundUtils.playSound(player, SoundType.SUCCESS);
                if (player.getInventory().firstEmpty() == -1) {
                    ItemCache.addItemToCache(player, Base64Utils.encodeItemStack(
                            Item.getBlockWithMetaData(CloudStorageBlockType.CLOUD_INTERFACE, Collections.singletonList(
                                    new MetaData(NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get(), player.getUniqueId().toString())))
                    ), 1);
                    player.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
                } else {
                    player.getInventory().addItem(
                            Item.getBlockWithMetaData(CloudStorageBlockType.CLOUD_INTERFACE, Collections.singletonList(
                                    new MetaData(NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get(), player.getUniqueId().toString())))
                    );
                }
                player.setCooldown(Material.PLAYER_HEAD, 20);
            } else {
                player.sendMessage(Messages.ERROR_TRANSACTION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
            }
            return;
        }
        if (event.getRawSlot() == 14) {
            Player player = (Player) event.getWhoClicked();
            if (player.hasCooldown(Material.STRUCTURE_VOID))
                return;
            if (Main.MAINTENANCE_MODE) {
                player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(player, SoundType.INFO);
                return;
            }
            if (!player.hasPermission(Permission.BUY_WIRELESS_CLOUD_INTERFACE.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION_BUY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            if (!Main.economy.has(player, 50000D)) {
                player.sendMessage(Messages.ERROR_NOT_ENOUGH_MONEY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            EconomyResponse economyResponse = Main.economy.withdrawPlayer(player, 50000D);
            if (economyResponse.transactionSuccess()) {
                player.sendMessage(Messages.MESSAGE_WIRELESS_CLOUD_INTERFACE_BOUGHT.get());
                SoundUtils.playSound(player, SoundType.SUCCESS);
                if (player.getInventory().firstEmpty() == -1) {
                    ItemCache.addItemToCache(player, Base64Utils.encodeItemStack(Item.getWirelessCloudInterface(player.getUniqueId().toString(), CloudInterfaceMode.ALL)), 1);
                    player.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
                } else {
                    player.getInventory().addItem(Item.getWirelessCloudInterface(player.getUniqueId().toString(), CloudInterfaceMode.ALL));
                }
                player.setCooldown(Material.STRUCTURE_VOID, 20);
            } else {
                player.sendMessage(Messages.ERROR_TRANSACTION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
            }
            return;
        }
        if (event.getRawSlot() == 15) {
            Player player = (Player) event.getWhoClicked();
            if (player.hasCooldown(Material.PLAYER_HEAD))
                return;
            if (Main.MAINTENANCE_MODE) {
                player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(player, SoundType.INFO);
                return;
            }
            if (!player.hasPermission(Permission.BUY_CLOUD_IMPORT_BUS.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION_BUY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            if (!Main.economy.has(player, 25000D)) {
                player.sendMessage(Messages.ERROR_NOT_ENOUGH_MONEY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            EconomyResponse economyResponse = Main.economy.withdrawPlayer(player, 25000D);
            if (economyResponse.transactionSuccess()) {
                player.sendMessage(Messages.MESSAGE_CLOUD_IMPORT_BUS_BOUGHT.get());
                SoundUtils.playSound(player, SoundType.SUCCESS);
                if (player.getInventory().firstEmpty() == -1) {
                    ItemCache.addItemToCache(player, Base64Utils.encodeItemStack(
                            Item.getBlockWithMetaData(CloudStorageBlockType.CLOUD_IMPORT_BUS, Collections.singletonList(
                                    new MetaData(NBT.KEY_CLOUD_IMPORT_BUS_OWNER_UUID.get(), player.getUniqueId().toString())))
                    ), 1);
                    player.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
                } else {
                    player.getInventory().addItem(
                            Item.getBlockWithMetaData(CloudStorageBlockType.CLOUD_IMPORT_BUS, Collections.singletonList(
                                    new MetaData(NBT.KEY_CLOUD_IMPORT_BUS_OWNER_UUID.get(), player.getUniqueId().toString())))
                    );
                }
                player.setCooldown(Material.PLAYER_HEAD, 20);
            } else {
                player.sendMessage(Messages.ERROR_TRANSACTION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
            }
            return;
        }
        if (event.getRawSlot() == 16) {
            Player player = (Player) event.getWhoClicked();
            if (player.hasCooldown(Material.PLAYER_HEAD))
                return;
            if (Main.MAINTENANCE_MODE) {
                player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(player, SoundType.INFO);
                return;
            }
            if (!player.hasPermission(Permission.BUY_CLOUD_EXPORT_BUS.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION_BUY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            if (!Main.economy.has(player, 25000D)) {
                player.sendMessage(Messages.ERROR_NOT_ENOUGH_MONEY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            EconomyResponse economyResponse = Main.economy.withdrawPlayer(player, 25000D);
            if (economyResponse.transactionSuccess()) {
                player.sendMessage(Messages.MESSAGE_CLOUD_EXPORT_BUS_BOUGHT.get());
                SoundUtils.playSound(player, SoundType.SUCCESS);
                if (player.getInventory().firstEmpty() == -1) {
                    ItemCache.addItemToCache(player, Base64Utils.encodeItemStack(
                            Item.getBlockWithMetaData(CloudStorageBlockType.CLOUD_EXPORT_BUS, Collections.singletonList(
                                    new MetaData(NBT.KEY_CLOUD_EXPORT_BUS_OWNER_UUID.get(), player.getUniqueId().toString())))
                    ), 1);
                    player.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
                } else {
                    player.getInventory().addItem(
                            Item.getBlockWithMetaData(CloudStorageBlockType.CLOUD_EXPORT_BUS, Collections.singletonList(
                                    new MetaData(NBT.KEY_CLOUD_EXPORT_BUS_OWNER_UUID.get(), player.getUniqueId().toString())))
                    );
                }
                player.setCooldown(Material.PLAYER_HEAD, 20);
            } else {
                player.sendMessage(Messages.ERROR_TRANSACTION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
            }
        }
    }

    @Override
    public void handleClose(InventoryCloseEvent event) {
    }
}
