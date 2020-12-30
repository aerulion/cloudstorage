package net.aerulion.cloudstorage.gui.guis;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.gui.GUI;
import net.aerulion.cloudstorage.task.BuyCloudExperienceTerminalTask;
import net.aerulion.cloudstorage.task.BuyCloudStorageSlotTask;
import net.aerulion.cloudstorage.utils.*;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloudShopGUI extends GUI {

    public CloudShopGUI(Player player) {
        super(player);
    }

    @Override
    public String getTitle() {
        return Main.PRIMARY_COLOR + "§lCloud Shop";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void setContent() {
        inventory.setItem(11, dataContainer.getOwningPlayer().hasPermission(Permission.BUY_CLOUD_STORAGE_SLOT.get()) ? Items.GUI_BUTTON_SHOP_CLOUD_STORAGE_SLOT.get() : Items.GUI_BUTTON_SHOP_CLOUD_STORAGE_SLOT_NO_PERMISSION.get());
        inventory.setItem(12, dataContainer.getOwningPlayer().hasPermission(Permission.BUY_CLOUD_EXPERIENCE_TERMINAL.get()) ? Items.GUI_BUTTON_SHOP_CLOUD_EXPERIENCE_TERMINAL.get() : Items.GUI_BUTTON_SHOP_CLOUD_EXPERIENCE_TERMINAL_NO_PERMISSION.get());
        inventory.setItem(14, dataContainer.getOwningPlayer().hasPermission(Permission.BUY_CLOUD_INTERFACE.get()) ? Items.GUI_BUTTON_SHOP_CLOUD_INTERFACE.get() : Items.GUI_BUTTON_SHOP_CLOUD_INTERFACE_NO_PERMISSION.get());
        inventory.setItem(15, dataContainer.getOwningPlayer().hasPermission(Permission.BUY_WIRELESS_CLOUD_INTERFACE.get()) ? Items.GUI_BUTTON_SHOP_WIRELESS_CLOUD_INTERFACE.get() : Items.GUI_BUTTON_SHOP_WIRELESS_CLOUD_INTERFACE_NO_PERMISSION.get());
        fillSpacers();
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        if (event.getRawSlot() == 11) {
            Player player = (Player) event.getWhoClicked();
            if (player.hasCooldown(Material.PLAYER_HEAD))
                return;
            if (Main.MAINTENANCE_MODE) {
                player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(player, SoundType.ALERT);
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
                SoundUtils.playSound(player, SoundType.ALERT);
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
        if (event.getRawSlot() == 14) {
            Player player = (Player) event.getWhoClicked();
            if (player.hasCooldown(Material.PLAYER_HEAD))
                return;
            if (Main.MAINTENANCE_MODE) {
                player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(player, SoundType.ALERT);
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
                    ItemCache.addItemToCache(player, Base64Utils.encodeItemStack(Items.getCloudInterface(player.getUniqueId().toString())), 1);
                    player.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
                } else {
                    player.getInventory().addItem(Items.getCloudInterface(player.getUniqueId().toString()));
                }
                player.setCooldown(Material.PLAYER_HEAD, 20);
            } else {
                player.sendMessage(Messages.ERROR_TRANSACTION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
            }
            return;
        }
        if (event.getRawSlot() == 15) {
            Player player = (Player) event.getWhoClicked();
            if (player.hasCooldown(Material.STRUCTURE_VOID))
                return;
            if (Main.MAINTENANCE_MODE) {
                player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(player, SoundType.ALERT);
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
                    ItemCache.addItemToCache(player, Base64Utils.encodeItemStack(Items.getWirelessCloudInterface(player.getUniqueId().toString(), CloudInterfaceMode.ALL)), 1);
                    player.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
                } else {
                    player.getInventory().addItem(Items.getWirelessCloudInterface(player.getUniqueId().toString(), CloudInterfaceMode.ALL));
                }
                player.setCooldown(Material.STRUCTURE_VOID, 20);
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
