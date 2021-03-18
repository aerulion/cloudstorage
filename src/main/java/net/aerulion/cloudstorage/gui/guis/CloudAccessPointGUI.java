package net.aerulion.cloudstorage.gui.guis;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.gui.GUI;
import net.aerulion.cloudstorage.task.*;
import net.aerulion.cloudstorage.utils.CloudStorageSlot;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.component.ComponentUtils;
import net.aerulion.nucleus.api.item.GuiButtonBuilder;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.aerulion.nucleus.api.string.StringUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CloudAccessPointGUI extends GUI {
    public CloudAccessPointGUI(Player player, CloudStorageSlot cloudStorageSlot) {
        super(player);
        dataContainer.setCloudStorageSlot(cloudStorageSlot);
    }

    @Override
    public Component getTitle() {
        return Component.text("Cloud Access Point").color(Main.PRIMARY_TEXT_COLOR).decorate(TextDecoration.BOLD);
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void setContent() {
        ItemStack storedItem = dataContainer.getCloudStorageSlot().getStoredItem() == null ? Items.GUI_NO_ITEM.get().clone() : dataContainer.getCloudStorageSlot().getStoredItem().clone();
        if (dataContainer.getCloudStorageSlot().getStoredItem() != null) {
            ItemMeta storedItemMeta = storedItem.getItemMeta();
            if (storedItemMeta != null) {
                List<Component> lore = storedItemMeta.hasLore() ? storedItemMeta.lore() : new ArrayList<>();
                if (lore != null) {
                    Component amountComponent1 = Component.text("Eingelagert:").color(Main.PRIMARY_TEXT_COLOR).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);
                    Component amountComponent2 = Component.text(Messages.decimalFormat.format(dataContainer.getCloudStorageSlot().getStoredAmount())).color(Main.PRIMARY_TEXT_COLOR).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE).append(Component.text("/").color(Main.LIGHT_ACCENT_TEXT_COLOR)).append(Component.text(Messages.decimalFormat.format(dataContainer.getCloudStorageSlot().getCapacity())).color(Main.PRIMARY_TEXT_COLOR));
                    int pixelLength = Math.max(StringUtils.getPixelLength(LegacyComponentSerializer.legacySection().serialize(amountComponent1)), StringUtils.getPixelLength(LegacyComponentSerializer.legacySection().serialize(amountComponent2))) + 20;
                    lore.add(Component.text(StringUtils.generateLine((int) Math.round(pixelLength / 4D))).color(Main.LIGHT_ACCENT_TEXT_COLOR));
                    lore.add(ComponentUtils.generateCenteredComponent(amountComponent1, (int) Math.round(pixelLength / 2D)).append(Component.text("  ")));
                    lore.add(ComponentUtils.generateCenteredComponent(amountComponent2, (int) Math.round(pixelLength / 2D)).append(Component.text("  ")));
                    lore.add(Component.text(StringUtils.generateLine((int) Math.round(pixelLength / 4D))).color(Main.LIGHT_ACCENT_TEXT_COLOR));
                    storedItemMeta.lore(lore);
                    storedItem.setItemMeta(storedItemMeta);
                }
            }
        }
        inventory.setItem(13, storedItem);
        inventory.setItem(29, Items.GUI_BUTTON_CSS_UPGRADES.get().clone());
        inventory.setItem(31, Items.GUI_BUTTON_WHOLE_INVENTORY.get().clone());
        if (dataContainer.getCloudStorageSlot().getOwnerUUID().equals(dataContainer.getOWNER_UUID()))
            inventory.setItem(33, dataContainer.getCloudStorageSlot().isPrivate() ? Items.GUI_BUTTON_CSS_ACCESS_PRIVATE.get() : Items.GUI_BUTTON_CSS_ACCESS_PUBLIC.get());
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(dataContainer.getCloudStorageSlot().getOwnerUUID()));
        inventory.setItem(44, GuiButtonBuilder.of(Material.KNOWLEDGE_BOOK).withDisplayName(Component.text("Cloud Info").color(Main.PRIMARY_TEXT_COLOR).decorate(TextDecoration.BOLD)).withLore(Component.text("%divider").color(Main.LIGHT_ACCENT_TEXT_COLOR), Component.text("Netzwerk Eigentümer:").color(Main.LIGHT_ACCENT_TEXT_COLOR), Component.text(offlinePlayer.getName() == null ? "ERROR" : offlinePlayer.getName()).color(Main.PRIMARY_TEXT_COLOR), Component.text("%divider").color(Main.LIGHT_ACCENT_TEXT_COLOR)).build());
        fillSpacers();
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && !event.getCurrentItem().getType().equals(Material.AIR)) {
            Player player = (Player) event.getWhoClicked();
            if (Main.MAINTENANCE_MODE) {
                player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(player, SoundType.ALERT);
                return;
            }
            if ((event.getRawSlot() >= 0 && event.getRawSlot() <= event.getView().getTopInventory().getSize() - 1)) {
                if (event.getRawSlot() == 13) {
                    if (!Items.GUI_NO_ITEM.get().isSimilar(event.getInventory().getItem(13))) {
                        if (event.getClick().equals(ClickType.LEFT)) {
                            if (player.getInventory().firstEmpty() != -1) {
                                if (dataContainer.getCloudStorageSlot().getStoredItem() != null)
                                    new WithdrawItemTask(player, 1, dataContainer.getCloudStorageSlot().getUUID());
                                return;
                            }
                        }
                        if (event.getClick().equals(ClickType.RIGHT)) {
                            if (player.getInventory().firstEmpty() != -1) {
                                if (dataContainer.getCloudStorageSlot().getStoredItem() != null)
                                    new WithdrawItemTask(player, Math.min(dataContainer.getCloudStorageSlot().getStoredAmount(), dataContainer.getCloudStorageSlot().getStoredItem().getMaxStackSize()), dataContainer.getCloudStorageSlot().getUUID());
                                return;
                            }
                        }
                    }
                    return;
                }
                if (event.getRawSlot() == 29) {
                    SoundUtils.playSound(player, SoundType.UI_CLICK);
                    new FetchCloudStorageSlotTask(dataContainer.getCloudStorageSlot().getUUID(), player, Inventory.UPGRADE);
                    return;
                }
                if (event.getRawSlot() == 31) {
                    if (!Items.GUI_NO_ITEM.get().isSimilar(event.getInventory().getItem(13))) {
                        if (event.getClick().equals(ClickType.LEFT)) {
                            int amount = 0;
                            for (ItemStack itemStack : event.getView().getBottomInventory()) {
                                if (itemStack != null && itemStack.isSimilar(dataContainer.getCloudStorageSlot().getStoredItem())) {
                                    int stackAmount = ((itemStack.getAmount() + amount + dataContainer.getCloudStorageSlot().getStoredAmount()) <= dataContainer.getCloudStorageSlot().getCapacity()) ? itemStack.getAmount() : (dataContainer.getCloudStorageSlot().getCapacity() - (dataContainer.getCloudStorageSlot().getStoredAmount() + amount));
                                    itemStack.setAmount(itemStack.getAmount() - stackAmount);
                                    amount += stackAmount;
                                }
                            }
                            if (amount > 0)
                                new StoreItemTask(player, dataContainer.getCloudStorageSlot().getStoredItem(), amount, dataContainer.getCloudStorageSlot().getUUID(), false);
                            return;
                        }
                        if (event.getClick().equals(ClickType.RIGHT)) {
                            int amount = 0;
                            for (int i = 0; i < 36; i++) {
                                ItemStack itemStack = event.getView().getBottomInventory().getItem(i);
                                if (itemStack == null && dataContainer.getCloudStorageSlot().getStoredItem() != null)
                                    amount += dataContainer.getCloudStorageSlot().getStoredItem().getMaxStackSize();
                            }
                            if (amount > dataContainer.getCloudStorageSlot().getStoredAmount())
                                amount = dataContainer.getCloudStorageSlot().getStoredAmount();
                            if (amount == dataContainer.getCloudStorageSlot().getStoredAmount())
                                amount -= 1;
                            if (amount > 0)
                                new WithdrawItemTask(player, amount, dataContainer.getCloudStorageSlot().getUUID());
                            return;
                        }
                    }
                    return;
                }
                if (event.getRawSlot() == 33) {
                    ItemStack itemStack = event.getInventory().getItem(33);
                    if (itemStack != null && !itemStack.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                        new ToggleCloudStorageSlotAccessTask(dataContainer.getCloudStorageSlot().getUUID(), player);
                    }
                }
            } else {
                if (event.getCurrentItem().isSimilar(dataContainer.getCloudStorageSlot().getStoredItem()) || Items.GUI_NO_ITEM.get().isSimilar(event.getInventory().getItem(13))) {
                    if (dataContainer.getCloudStorageSlot().getStoredAmount() < dataContainer.getCloudStorageSlot().getCapacity()) {
                        int amount = (dataContainer.getCloudStorageSlot().getStoredAmount() + event.getCurrentItem().getAmount() <= dataContainer.getCloudStorageSlot().getCapacity()) ? event.getCurrentItem().getAmount() : (dataContainer.getCloudStorageSlot().getCapacity() - dataContainer.getCloudStorageSlot().getStoredAmount());
                        ItemStack itemStack = event.getCurrentItem().clone();
                        itemStack.setAmount(1);
                        if (Items.GUI_NO_ITEM.get().isSimilar(event.getInventory().getItem(13)))
                            new StoreInitialItemTask(player, itemStack, amount, dataContainer.getCloudStorageSlot().getUUID(), dataContainer.getCloudStorageSlot().getOwnerUUID(), event.getSlot());
                        else {
                            event.getCurrentItem().setAmount(event.getCurrentItem().getAmount() - amount);
                            new StoreItemTask(player, itemStack, amount, dataContainer.getCloudStorageSlot().getUUID(), false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void handleClose(InventoryCloseEvent event) {
        Main.openGUIs.remove(event.getPlayer().getUniqueId().toString());
    }
}
