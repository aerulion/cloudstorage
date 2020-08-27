package net.aerulion.cloudstorage.utils;

import net.aerulion.nucleus.api.console.ConsoleUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CloudStorageInventoryHolder implements InventoryHolder {

    public CloudStorageInventoryHolder() {
        ConsoleUtils.sendColoredConsoleMessage(Messages.CONSOLE_INVENTORY_HOLDER_INITIALIZED.get());
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}