package net.aerulion.cloudstorage.gui.guis;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.gui.GUI;
import net.aerulion.cloudstorage.task.BuyCloudStorageSlotUpgradeTask;
import net.aerulion.cloudstorage.task.FetchCloudStorageSlotTask;
import net.aerulion.cloudstorage.utils.*;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CloudAccessPointUpgradeGUI extends GUI {

    public CloudAccessPointUpgradeGUI(Player player, CloudStorageSlot cloudStorageSlot) {
        super(player);
        dataContainer.setCloudStorageSlot(cloudStorageSlot);
    }

    @Override
    public Component getTitle() {
        return Component.text("CSS Upgrades").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD);
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void setContent() {
        inventory.setItem(10, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_1.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_1_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_1.get());
        inventory.setItem(11, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_2.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_2_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_2.get());
        inventory.setItem(12, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_3.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_3_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_3.get());
        inventory.setItem(13, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_4.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_4_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_4.get());
        inventory.setItem(14, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_5.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_5_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_5.get());
        inventory.setItem(15, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_6.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_6_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_6.get());
        inventory.setItem(16, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_7.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_7_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_7.get());
        inventory.setItem(19, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_8.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_8_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_8.get());
        inventory.setItem(20, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_9.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_9_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_9.get());
        inventory.setItem(21, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_10.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_10_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_10.get());
        inventory.setItem(22, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_11.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_11_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_11.get());
        inventory.setItem(23, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_12.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_12_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_12.get());
        inventory.setItem(24, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_13.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_13_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_13.get());
        inventory.setItem(25, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_14.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_14_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_14.get());
        inventory.setItem(31, dataContainer.getCloudStorageSlot().getCapacity() >= Upgrade.UPGRADE_15.getCapacityItem() ? Items.GUI_BUTTON_UPGRADE_ITEM_15_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_ITEM_15.get());
        if (dataContainer.getCloudStorageSlot().getOwnerUUID().equals(dataContainer.getOWNER_UUID()))
            inventory.setItem(37, Items.GUI_BUTTON_UPGRADE_ADDITIONAL_ACCESS_POINT.get());
        inventory.setItem(43, Items.BACK.get());
        fillSpacers();
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        if (Main.MAINTENANCE_MODE) {
            event.getWhoClicked().sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
            SoundUtils.playSound(event.getWhoClicked(), SoundType.ALERT);
            return;
        }
        if (event.getRawSlot() == 10)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_1, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), null);
        if (event.getRawSlot() == 11)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_2, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(10));
        if (event.getRawSlot() == 12)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_3, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(11));
        if (event.getRawSlot() == 13)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_4, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(12));
        if (event.getRawSlot() == 14)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_5, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(13));
        if (event.getRawSlot() == 15)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_6, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(14));
        if (event.getRawSlot() == 16)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_7, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(15));
        if (event.getRawSlot() == 19)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_8, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(16));
        if (event.getRawSlot() == 20)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_9, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(19));
        if (event.getRawSlot() == 21)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_10, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(20));
        if (event.getRawSlot() == 22)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_11, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(21));
        if (event.getRawSlot() == 23)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_12, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(22));
        if (event.getRawSlot() == 24)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_13, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(23));
        if (event.getRawSlot() == 25)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_14, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(24));
        if (event.getRawSlot() == 31)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_15, dataContainer.getCloudStorageSlot().getUUID(), event.getCurrentItem(), event.getInventory().getItem(25));
        if (event.getRawSlot() == 37) {
            ItemStack itemStack = event.getInventory().getItem(33);
            if (itemStack != null && !itemStack.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
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
                            ItemCache.addItemToCache(player, Base64Utils.encodeItemStack(Items.getCloudAccessPoint(dataContainer.getCloudStorageSlot().getUUID(), player.getUniqueId().toString())), 1);
                            player.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
                        } else {
                            player.getInventory().addItem(Items.getCloudAccessPoint(dataContainer.getCloudStorageSlot().getUUID(), player.getUniqueId().toString()));
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
                            ItemCache.addItemToCache(player, Base64Utils.encodeItemStack(Items.getWirelessCloudAccessPoint(dataContainer.getCloudStorageSlot().getUUID(), player.getUniqueId().toString())), 1);
                            player.sendMessage(Messages.MESSAGE_CACHED_INVENTORY_FULL.get());
                        } else {
                            player.getInventory().addItem(Items.getWirelessCloudAccessPoint(dataContainer.getCloudStorageSlot().getUUID(), player.getUniqueId().toString()));
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
        if (event.getRawSlot() == 43) {
            SoundUtils.playSound(event.getWhoClicked(), SoundType.UI_CLICK);
            new FetchCloudStorageSlotTask(dataContainer.getCloudStorageSlot().getUUID(), (Player) event.getWhoClicked(), Inventory.ACCESS_POINT);
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
                new BuyCloudStorageSlotUpgradeTask(player, cloudStorageSlotID, upgrade);
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
