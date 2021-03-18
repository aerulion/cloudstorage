package net.aerulion.cloudstorage.gui.guis;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.gui.GUI;
import net.aerulion.cloudstorage.task.BuyCloudExperienceTerminalUpgradeTask;
import net.aerulion.cloudstorage.task.FetchCloudExperienceTerminalTask;
import net.aerulion.cloudstorage.utils.*;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CloudExperienceTerminalUpgradeGUI extends GUI {

    public CloudExperienceTerminalUpgradeGUI(Player player, CloudExperienceTerminal cloudExperienceTerminal) {
        super(player);
        dataContainer.setCloudExperienceTerminal(cloudExperienceTerminal);
    }

    @Override
    public Component getTitle() {
        return Component.text("CET Upgrades").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD);
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void setContent() {
        inventory.setItem(10, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_1.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_1_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_1.get());
        inventory.setItem(11, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_2.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_2_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_2.get());
        inventory.setItem(12, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_3.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_3_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_3.get());
        inventory.setItem(13, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_4.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_4_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_4.get());
        inventory.setItem(14, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_5.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_5_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_5.get());
        inventory.setItem(15, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_6.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_6_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_6.get());
        inventory.setItem(16, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_7.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_7_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_7.get());
        inventory.setItem(19, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_8.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_8_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_8.get());
        inventory.setItem(20, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_9.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_9_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_9.get());
        inventory.setItem(21, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_10.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_10_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_10.get());
        inventory.setItem(22, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_11.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_11_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_11.get());
        inventory.setItem(23, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_12.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_12_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_12.get());
        inventory.setItem(24, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_13.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_13_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_13.get());
        inventory.setItem(25, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_14.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_14_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_14.get());
        inventory.setItem(31, dataContainer.getCloudExperienceTerminal().getCAPACITY() >= Upgrade.UPGRADE_15.getCapacityExperience() ? Items.GUI_BUTTON_UPGRADE_EXPERIENCE_15_ACTIVE.get() : Items.GUI_BUTTON_UPGRADE_EXPERIENCE_15.get());
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
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_1, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), null);
        if (event.getRawSlot() == 11)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_2, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(10));
        if (event.getRawSlot() == 12)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_3, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(11));
        if (event.getRawSlot() == 13)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_4, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(12));
        if (event.getRawSlot() == 14)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_5, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(13));
        if (event.getRawSlot() == 15)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_6, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(14));
        if (event.getRawSlot() == 16)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_7, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(15));
        if (event.getRawSlot() == 19)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_8, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(16));
        if (event.getRawSlot() == 20)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_9, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(19));
        if (event.getRawSlot() == 21)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_10, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(20));
        if (event.getRawSlot() == 22)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_11, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(21));
        if (event.getRawSlot() == 23)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_12, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(22));
        if (event.getRawSlot() == 24)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_13, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(23));
        if (event.getRawSlot() == 25)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_14, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(24));
        if (event.getRawSlot() == 31)
            handleUpgrade(event.getWhoClicked(), Upgrade.UPGRADE_15, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), event.getCurrentItem(), event.getInventory().getItem(25));
        if (event.getRawSlot() == 43) {
            SoundUtils.playSound(event.getWhoClicked(), SoundType.UI_CLICK);
            new FetchCloudExperienceTerminalTask(dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), (Player) event.getWhoClicked(), Inventory.EXPERIENCE_TERMINAL);
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
                new BuyCloudExperienceTerminalUpgradeTask(player, cloudStorageSlotID, upgrade);
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
