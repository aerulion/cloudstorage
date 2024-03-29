package net.aerulion.cloudstorage.gui;

import net.aerulion.cloudstorage.utils.Item;
import net.aerulion.nucleus.api.component.ComponentUtils;
import net.aerulion.nucleus.api.string.CenterPixel;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class GUI implements InventoryHolder {

    protected final DataContainer dataContainer;
    protected Inventory inventory;
    protected final ItemStack spacer = Item.GUI_SPACER.get();

    public GUI(Player player) {
        dataContainer = DataContainerManager.getDataContainer(player);
    }

    public abstract Component getTitle();

    public abstract int getSlots();

    public abstract void setContent();

    public abstract void handleClick(InventoryClickEvent event);

    public abstract void handleClose(InventoryCloseEvent event);

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), ComponentUtils.generateCenteredComponent(getTitle(), CenterPixel.INVENTORY_TITLE));
        this.setContent();
        dataContainer.getOwningPlayer().openInventory(inventory);
    }

    public void fillSpacers() {
        fillSpacers(spacer);
    }

    public void fillSpacers(ItemStack itemStack) {
        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null)
                inventory.setItem(i, itemStack);
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
