package net.aerulion.cloudstorage.gui.guis;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.gui.GUI;
import net.aerulion.cloudstorage.utils.Item;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.BlockInventoryHolder;

import java.util.ArrayList;
import java.util.Arrays;

public class CloudImportBusGUI extends GUI {

    private final Block block;
    private final String ownerUUID;

    public CloudImportBusGUI(Player player, String ownerUUID, Block block) {
        super(player);
        this.ownerUUID = ownerUUID;
        this.block = block;
        Main.importExportHandler.OPEN_IMPORT_GUIS.computeIfAbsent((BlockInventoryHolder) block.getState(), inventoryHolder -> new ArrayList<>()).add(this);
    }

    @Override
    public Component getTitle() {
        return Component.text("Cloud Import Bus")
                .color(Main.PRIMARY_COLOR)
                .decorate(TextDecoration.BOLD);
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void setContent() {
        inventory.clear();
        inventory.setItem(0, Item.GUI_SPACER_IMPORT_BUS.get());
        for (int i = 1; i < 9; i++)
            inventory.setItem(i, Item.GUI_SPACER_INVISIBLE.get());
        for (int i = 36; i < 45; i++)
            inventory.setItem(i, Item.GUI_SPACER_INVISIBLE.get());
        BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) block.getState();
        Arrays.stream(blockInventoryHolder.getInventory().getContents())
                .filter(itemStack -> itemStack != null &&
                        NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_BLOCK_TYPE.get()).equals(""))
                .forEach(itemStack -> inventory.addItem(itemStack.clone()));
        inventory.setItem(44, Item.getNetworkInfoBook(ownerUUID));
        dataContainer.getOwningPlayer().updateInventory();
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
    }

    @Override
    public void handleClose(InventoryCloseEvent event) {
        Main.importExportHandler.OPEN_IMPORT_GUIS.get(
                ((BlockInventoryHolder) block.getState()).getInventory().getHolder()
        ).remove(this);
    }
}