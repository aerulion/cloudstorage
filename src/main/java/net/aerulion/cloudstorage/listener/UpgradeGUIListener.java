package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.task.FetchCloudStorageSlotTask;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UpgradeGUIListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder().equals(Main.cloudStorageInventoryHolder)) {
            if (NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_GUI_TYPE.get()).equals(NBT.VALUE_GUI_TYPE_UPGRADE.get())) {
                event.setCancelled(true);
                if (event.getSlot() == 43) {
                    SoundUtils.playSound(event.getWhoClicked(), SoundType.UI_CLICK);
                    new FetchCloudStorageSlotTask(NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), (Player) event.getWhoClicked(), Inventory.ACCESS_POINT);
                    return;
                }
            }
        }
    }
}