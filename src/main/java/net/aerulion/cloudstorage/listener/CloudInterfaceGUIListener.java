package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.task.CloudInterfaceTask;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.cloudstorage.utils.StorageSetting;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CloudInterfaceGUIListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder().equals(Main.cloudStorageInventoryHolder)) {
            if (NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_GUI_TYPE.get()).equals(NBT.VALUE_GUI_TYPE_CLOUD_INTERFACE.get())) {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                if (!player.hasCooldown(Material.STRUCTURE_VOID)) {
                    if (event.getSlot() == 11) {
                        new CloudInterfaceTask(player, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get()), StorageSetting.HOTBAR_ONLY);
                        player.setCooldown(Material.STRUCTURE_VOID, 100);
                        return;
                    }
                    if (event.getSlot() == 13) {
                        new CloudInterfaceTask(player, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get()), StorageSetting.ALL);
                        player.setCooldown(Material.STRUCTURE_VOID, 100);
                        return;
                    }
                    if (event.getSlot() == 15) {
                        new CloudInterfaceTask(player, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get()), StorageSetting.INVENTORY_ONLY);
                        player.setCooldown(Material.STRUCTURE_VOID, 100);
                        return;
                    }
                }
            }
        }
    }
}