package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.task.BuyUpgradeTask;
import net.aerulion.cloudstorage.task.FetchCloudStorageSlotTask;
import net.aerulion.cloudstorage.utils.*;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UpgradeGUIListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder().equals(Main.cloudStorageInventoryHolder)) {
            if (NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_GUI_TYPE.get()).equals(NBT.VALUE_GUI_TYPE_UPGRADE.get())) {
                event.setCancelled(true);
                if (event.getSlot() == 10)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_1, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), null);
                if (event.getSlot() == 11)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_2, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(10));
                if (event.getSlot() == 12)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_3, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(11));
                if (event.getSlot() == 13)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_4, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(12));
                if (event.getSlot() == 14)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_5, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(13));
                if (event.getSlot() == 15)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_6, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(14));
                if (event.getSlot() == 16)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_7, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(15));
                if (event.getSlot() == 19)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_8, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(16));
                if (event.getSlot() == 20)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_9, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(19));
                if (event.getSlot() == 21)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_10, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(20));
                if (event.getSlot() == 22)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_11, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(21));
                if (event.getSlot() == 23)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_12, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(22));
                if (event.getSlot() == 24)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_13, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(23));
                if (event.getSlot() == 25)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_14, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(24));
                if (event.getSlot() == 31)
                    handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_15, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getCurrentItem(), event.getInventory().getItem(25));
                if (event.getSlot() == 37) {
                    Player player = (Player) event.getWhoClicked();
                    if (event.getClick().equals(ClickType.LEFT)) {
                        if (!player.hasPermission(Permission.BUY_CLOUD_ACCESS_POINT.get())) {
                            player.sendMessage(Messages.ERROR_NO_PERMISSION_BUY.get());
                            SoundUtils.playSound(player, SoundType.ERROR);
                            return;
                        }
                        if (!Main.economy.has(player, 5000D)) {
                            player.sendMessage(Messages.ERROR_NOT_ENOUGH_MONEY.get());
                            SoundUtils.playSound(player, SoundType.ERROR);
                            return;
                        }
                        EconomyResponse economyResponse = Main.economy.withdrawPlayer(player, 5000D);
                        if (economyResponse.transactionSuccess()) {
                            player.sendMessage(Messages.MESSAGE_CLOUD_ACCESS_POINT_BOUGHT.get());
                            SoundUtils.playSound(player, SoundType.SUCCESS);
                            if (player.getInventory().firstEmpty() == -1) {
                                ItemCache.addItemToCache(player, Base64Utils.encodeItemStack(Items.getCloudAccessPoint(NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), player.getUniqueId().toString())), 1);
                                player.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
                            } else {
                                player.getInventory().addItem(Items.getCloudAccessPoint(NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), player.getUniqueId().toString()));
                            }
                            player.closeInventory();
                        } else {
                            player.sendMessage(Messages.ERROR_TRANSACTION.get());
                            SoundUtils.playSound(player, SoundType.ERROR);
                        }
                        return;
                    }
                    if (event.getClick().equals(ClickType.RIGHT)) {
                        if (!player.hasPermission(Permission.BUY_WIRELESS_CLOUD_ACCESS_POINT.get())) {
                            player.sendMessage(Messages.ERROR_NO_PERMISSION_BUY.get());
                            SoundUtils.playSound(player, SoundType.ERROR);
                            return;
                        }
                        if (!Main.economy.has(player, 5000D)) {
                            player.sendMessage(Messages.ERROR_NOT_ENOUGH_MONEY.get());
                            SoundUtils.playSound(player, SoundType.ERROR);
                            return;
                        }
                        EconomyResponse economyResponse = Main.economy.withdrawPlayer(player, 5000D);
                        if (economyResponse.transactionSuccess()) {
                            player.sendMessage(Messages.MESSAGE_WIRELESS_CLOUD_ACCESS_POINT_BOUGHT.get());
                            SoundUtils.playSound(player, SoundType.SUCCESS);
                            if (player.getInventory().firstEmpty() == -1) {
                                ItemCache.addItemToCache(player, Base64Utils.encodeItemStack(Items.getWirelessCloudAccessPoint(NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), player.getUniqueId().toString())), 1);
                                player.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
                            } else {
                                player.getInventory().addItem(Items.getWirelessCloudAccessPoint(NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), player.getUniqueId().toString()));
                            }
                            player.closeInventory();
                        } else {
                            player.sendMessage(Messages.ERROR_TRANSACTION.get());
                            SoundUtils.playSound(player, SoundType.ERROR);
                        }
                        return;
                    }
                }
                if (event.getSlot() == 43) {
                    SoundUtils.playSound(event.getWhoClicked(), SoundType.UI_CLICK);
                    new FetchCloudStorageSlotTask(NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), (Player) event.getWhoClicked(), Inventory.ACCESS_POINT);
                }
            }
        }
    }

    private void handleUpgrade(HumanEntity humanEntity, Upgrade upgrade, String cloudStorageSlotID, ItemStack upgradeItem, ItemStack previousUpgradeItem) {
        if (upgradeItem == null || (previousUpgradeItem == null && !upgrade.equals(Upgrade.UPGRADE_1)))
            return;
        Player player = (Player) humanEntity;
        if (!player.hasPermission(Permission.BUY_UPGRADE.get())) {
            player.sendMessage(Messages.ERROR_NO_PERMISSION_BUY.get());
            SoundUtils.playSound(player, SoundType.ERROR);
            return;
        }

        boolean hasPreviousUpgrade = false;
        boolean hasClickedUpgrade = false;

        if (upgrade.equals(Upgrade.UPGRADE_1)) {
            hasPreviousUpgrade = true;
        } else {
            ItemMeta itemMeta = previousUpgradeItem.getItemMeta();
            if (itemMeta != null && itemMeta.hasEnchants())
                hasPreviousUpgrade = true;
        }

        ItemMeta itemMeta = upgradeItem.getItemMeta();
        if (itemMeta != null && itemMeta.hasEnchants())
            hasClickedUpgrade = true;

        if (hasPreviousUpgrade && !hasClickedUpgrade) {
            if (!Main.economy.has(player, upgrade.getPrice())) {
                player.sendMessage(Messages.ERROR_NOT_ENOUGH_MONEY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return;
            }
            EconomyResponse economyResponse = Main.economy.withdrawPlayer(player, upgrade.getPrice());
            if (economyResponse.transactionSuccess()) {
                new BuyUpgradeTask(player, cloudStorageSlotID, upgrade);
            } else {
                player.sendMessage(Messages.ERROR_TRANSACTION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
            }
        }
    }
}