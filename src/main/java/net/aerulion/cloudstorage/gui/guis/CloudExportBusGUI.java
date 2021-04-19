package net.aerulion.cloudstorage.gui.guis;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.gui.GUI;
import net.aerulion.cloudstorage.utils.Item;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class CloudExportBusGUI extends GUI {

    private final Block block;
    private final String ownerUUID;

    public CloudExportBusGUI(Player player, String ownerUUID, Block block) {
        super(player);
        this.ownerUUID = ownerUUID;
        this.block = block;
        Main.importExportHandler.OPEN_EXPORT_GUIS.computeIfAbsent((BlockInventoryHolder) block.getState(), inventoryHolder -> new ArrayList<>()).add(this);
    }

    @Override
    public Component getTitle() {
        return Component.text("Cloud Export Bus")
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
        inventory.setItem(0, Item.GUI_SPACER_EXPORT_BUS.get());
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
        if (event.getRawSlot() >= inventory.getSize()) {
            ItemStack itemStack = event.getCurrentItem();
            if (itemStack == null) return;
            String slotUUID = NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get());
            String ownerUUID = NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_OWNER_UUID.get());
            if (slotUUID.equals("")) {
                event.getWhoClicked().sendMessage(Messages.MESSAGE_NOT_A_STORAGE_SLOT.get());
                SoundUtils.playSound(event.getWhoClicked(), SoundType.INFO);
                return;
            }
            if (!ownerUUID.equals(this.ownerUUID)) {
                event.getWhoClicked().sendMessage(Messages.ERROR_OWNER_NOT_MATCHING.get());
                SoundUtils.playSound(event.getWhoClicked(), SoundType.ERROR);
                return;
            }
            BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) block.getState();
            ItemStack metaItem = blockInventoryHolder.getInventory().getItem(blockInventoryHolder.getInventory().getSize() - 1);
            if (metaItem == null) return;
            blockInventoryHolder.getInventory().setItem(blockInventoryHolder.getInventory().getSize() - 1,
                    NbtUtils.setNBTString(metaItem, NBT.KEY_CLOUD_EXPORT_BUS_ASSOCIATED_SLOT_UUID.get(), slotUUID));
            event.getWhoClicked().sendMessage(Messages.MESSAGE_ASSIGNED_EXPORT_BUS.get());
            SoundUtils.playSound(event.getWhoClicked(), SoundType.SUCCESS);
        }
    }

    @Override
    public void handleClose(InventoryCloseEvent event) {
        Main.importExportHandler.OPEN_EXPORT_GUIS.get(
                ((BlockInventoryHolder) block.getState()).getInventory().getHolder()
        ).remove(this);
    }
}