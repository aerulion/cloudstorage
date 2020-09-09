package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.task.BuyCloudStorageSlotTask;
import net.aerulion.cloudstorage.utils.*;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CloudShopGUIListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder().equals(Main.cloudStorageInventoryHolder)) {
            if (NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_GUI_TYPE.get()).equals(NBT.VALUE_GUI_TYPE_CLOUD_SHOP.get())) {
                event.setCancelled(true);
                if (event.getSlot() == 11) {
                    Player player = (Player) event.getWhoClicked();
                    if (!player.hasPermission(Permission.BUY_STORAGE_SLOT.get())) {
                        player.sendMessage(Messages.ERROR_NO_PERMISSION_BUY.get());
                        SoundUtils.playSound(player, SoundType.ERROR);
                        return;
                    }
                    if (!Main.economy.has(player, Upgrade.BASE.getPrice())) {
                        player.sendMessage(Messages.ERROR_NOT_ENOUGH_MONEY.get());
                        SoundUtils.playSound(player, SoundType.ERROR);
                        return;
                    }
                    EconomyResponse economyResponse = Main.economy.withdrawPlayer(player, Upgrade.BASE.getPrice());
                    if (economyResponse.transactionSuccess()) {
                        new BuyCloudStorageSlotTask(player);
                        player.closeInventory();
                    } else {
                        player.sendMessage(Messages.ERROR_TRANSACTION.get());
                        SoundUtils.playSound(player, SoundType.ERROR);
                    }
                    return;
                }
                if (event.getSlot() == 13) {
                    Player player = (Player) event.getWhoClicked();
                    if (!player.hasPermission(Permission.BUY_INTERFACE.get())) {
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
                        player.closeInventory();
                    } else {
                        player.sendMessage(Messages.ERROR_TRANSACTION.get());
                        SoundUtils.playSound(player, SoundType.ERROR);
                    }
                    return;
                }
                if (event.getSlot() == 15) {
                    Player player = (Player) event.getWhoClicked();
                    if (!player.hasPermission(Permission.BUY_WIRELESS_INTERFACE.get())) {
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
                        player.closeInventory();
                    } else {
                        player.sendMessage(Messages.ERROR_TRANSACTION.get());
                        SoundUtils.playSound(player, SoundType.ERROR);
                    }
                    return;
                }
            }
        }
    }
}