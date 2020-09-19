package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.task.CloudInterfaceTask;
import net.aerulion.cloudstorage.utils.CloudInterfaceMode;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
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
                        if (Main.MAINTENANCE_MODE) {
                            player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                            SoundUtils.playSound(player, SoundType.ALERT);
                            return;
                        }
                        new CloudInterfaceTask(player, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get()), CloudInterfaceMode.HOTBAR_ONLY);
                        player.setCooldown(Material.STRUCTURE_VOID, 100);
                        return;
                    }
                    if (event.getSlot() == 13) {
                        if (Main.MAINTENANCE_MODE) {
                            player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                            SoundUtils.playSound(player, SoundType.ALERT);
                            return;
                        }
                        new CloudInterfaceTask(player, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get()), CloudInterfaceMode.ALL);
                        player.setCooldown(Material.STRUCTURE_VOID, 100);
                        return;
                    }
                    if (event.getSlot() == 15) {
                        if (Main.MAINTENANCE_MODE) {
                            player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                            SoundUtils.playSound(player, SoundType.ALERT);
                            return;
                        }
                        new CloudInterfaceTask(player, NbtUtils.getNBTString(event.getInventory().getItem(0), NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get()), CloudInterfaceMode.INVENTORY_ONLY);
                        player.setCooldown(Material.STRUCTURE_VOID, 100);
                        return;
                    }
                }
            }
        }
    }
}