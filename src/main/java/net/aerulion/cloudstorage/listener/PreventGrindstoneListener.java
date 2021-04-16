package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class PreventGrindstoneListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getType().equals(InventoryType.GRINDSTONE)) {
            if (event.getCurrentItem() != null && (event.getCurrentItem().getType().equals(Material.HEART_OF_THE_SEA) || event.getCurrentItem().getType().equals(Material.STRUCTURE_VOID))) {
                if (!NbtUtils.getNBTString(event.getCurrentItem(), NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get()).equals("") || !NbtUtils.getNBTString(event.getCurrentItem(), NBT.KEY_CLOUD_STORAGE_SLOT_OWNER_UUID.get()).equals("")) {
                    event.setCancelled(true);
                    event.getWhoClicked().sendMessage(Messages.MESSAGE_NO_GRINDSTONE.get());
                    SoundUtils.playSound(event.getWhoClicked(), SoundType.INFO);
                }
            }
        }
    }
}
