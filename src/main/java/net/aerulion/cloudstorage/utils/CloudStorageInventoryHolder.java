package net.aerulion.cloudstorage.utils;

import net.aerulion.cloudstorage.Main;
import net.aerulion.nucleus.api.console.ConsoleUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CloudStorageInventoryHolder implements InventoryHolder {

    public CloudStorageInventoryHolder() {
        ConsoleUtils.sendColoredConsoleMessage(Messages.CONSOLE_INVENTORY_HOLDER_INITIALIZED.get());
    }

    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(this, 9, Main.ERROR_COLOR + ChatColor.BOLD.toString() + "ERROR");
    }
}