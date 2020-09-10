package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.task.*;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CloudAccessPointGUIListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder().equals(Main.cloudStorageInventoryHolder)) {
            if (NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_GUI_TYPE.get()).equals(NBT.VALUE_GUI_TYPE_CLOUD_ACCESS_POINT.get())) {
                event.setCancelled(true);
                if (event.getCurrentItem() != null && !event.getCurrentItem().getType().equals(Material.AIR)) {
                    Player player = (Player) event.getWhoClicked();
                    int capacity = NbtUtils.getNBTInt(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_CAPACITY.get());
                    int storedAmount = NbtUtils.getNBTInt(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_STORED_AMOUNT.get());
                    ItemStack storedItem = event.getInventory().getItem(13);
                    if (storedItem != null) {
                        storedItem = storedItem.clone();
                        if (!storedItem.isSimilar(Items.GUI_NO_ITEM.get())) {
                            ItemMeta storedItemMeta = storedItem.getItemMeta();
                            if (storedItemMeta != null) {
                                List<String> lore = storedItemMeta.getLore();
                                if (lore != null) {
                                    if (lore.size() == 1)
                                        lore = null;
                                    else {
                                        lore.remove(lore.size() - 1);
                                        lore.remove(lore.size() - 1);
                                        lore.remove(lore.size() - 1);
                                        lore.remove(lore.size() - 1);
                                    }
                                    storedItemMeta.setLore(lore);
                                    storedItem.setItemMeta(storedItemMeta);
                                }
                            }
                        }
                    }
                    if ((event.getRawSlot() >= 0 && event.getRawSlot() <= event.getView().getTopInventory().getSize() - 1)) {
                        if (event.getSlot() == 13) {
                            if (!Items.GUI_NO_ITEM.get().isSimilar(event.getInventory().getItem(13))) {
                                if (event.getClick().equals(ClickType.LEFT)) {
                                    new WithdrawItemTask(player, 1, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()));
                                    return;
                                }
                                if (event.getClick().equals(ClickType.RIGHT)) {
                                    if (storedItem != null)
                                        new WithdrawItemTask(player, Math.min(storedAmount, storedItem.getMaxStackSize()), NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()));
                                    return;
                                }
                            }
                            return;
                        }
                        if (event.getSlot() == 29) {
                            ItemStack itemStack = event.getInventory().getItem(29);
                            if (itemStack != null && !itemStack.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                                SoundUtils.playSound(player, SoundType.UI_CLICK);
                                new FetchCloudStorageSlotTask(NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), player, Inventory.UPGRADE);
                                return;
                            }
                        }
                        if (event.getSlot() == 31) {
                            if (!Items.GUI_NO_ITEM.get().isSimilar(event.getInventory().getItem(13))) {
                                if (event.getClick().equals(ClickType.LEFT)) {
                                    int amount = 0;
                                    for (ItemStack itemStack : event.getView().getBottomInventory()) {
                                        if (itemStack != null && itemStack.isSimilar(storedItem)) {
                                            int stackAmount = ((itemStack.getAmount() + amount + storedAmount) <= capacity) ? itemStack.getAmount() : (capacity - (storedAmount + amount));
                                            itemStack.setAmount(itemStack.getAmount() - stackAmount);
                                            amount += stackAmount;
                                        }
                                    }
                                    if (amount > 0)
                                        new StoreItemTask(player, storedItem, amount, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), false);
                                    return;
                                }
                                if (event.getClick().equals(ClickType.RIGHT)) {
                                    int amount = 0;
                                    for (int i = 0; i < 36; i++) {
                                        ItemStack itemStack = event.getView().getBottomInventory().getItem(i);
                                        if (itemStack == null && storedItem != null)
                                            amount += storedItem.getMaxStackSize();
                                    }
                                    if (amount > storedAmount)
                                        amount = storedAmount;
                                    if (amount == storedAmount)
                                        amount -= 1;
                                    if (amount > 0)
                                        new WithdrawItemTask(player, amount, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()));
                                    return;
                                }
                            }
                            return;
                        }
                        if (event.getSlot() == 33) {
                            ItemStack itemStack = event.getInventory().getItem(33);
                            if (itemStack != null && !itemStack.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                                new ToggleAccessTask(NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), player);
                            }
                        }
                    } else {
                        if (event.getCurrentItem().isSimilar(storedItem) || Items.GUI_NO_ITEM.get().isSimilar(event.getInventory().getItem(13))) {
                            if (storedAmount < capacity) {
                                int amount = (storedAmount + event.getCurrentItem().getAmount() <= capacity) ? event.getCurrentItem().getAmount() : (capacity - storedAmount);
                                ItemStack itemStack = event.getCurrentItem().clone();
                                itemStack.setAmount(1);
                                if (Items.GUI_NO_ITEM.get().isSimilar(event.getInventory().getItem(13)))
                                    new StoreInitialItemTask(player, itemStack, amount, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_OWNER_UUID.get()), event.getSlot());
                                else {
                                    event.getCurrentItem().setAmount(event.getCurrentItem().getAmount() - amount);
                                    new StoreItemTask(player, itemStack, amount, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}