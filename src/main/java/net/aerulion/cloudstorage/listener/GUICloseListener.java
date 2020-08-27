package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GUICloseListener implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder().equals(Main.cloudStorageInventoryHolder)) {
            Main.openGUIs.remove(event.getPlayer().getUniqueId().toString());
        }
    }
}
