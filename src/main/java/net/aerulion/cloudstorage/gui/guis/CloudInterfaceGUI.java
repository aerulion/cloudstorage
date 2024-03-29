package net.aerulion.cloudstorage.gui.guis;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.gui.GUI;
import net.aerulion.cloudstorage.task.CloudInterfaceTask;
import net.aerulion.cloudstorage.utils.CloudInterfaceMode;
import net.aerulion.cloudstorage.utils.Item;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.item.GuiButtonBuilder;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.UUID;

public class CloudInterfaceGUI extends GUI {

    public CloudInterfaceGUI(Player player, String cloudInterfaceOwner) {
        super(player);
        dataContainer.setCloudInterfaceOwner(cloudInterfaceOwner);
    }

    @Override
    public Component getTitle() {
        return Component.text("Cloud Interface").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD);
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void setContent() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(dataContainer.getCloudInterfaceOwner()));
        inventory.setItem(26, GuiButtonBuilder.of(Material.KNOWLEDGE_BOOK).withDisplayName(Component.text("Cloud Info").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Component.text("%divider").color(Main.LIGHT_ACCENT_COLOR), Component.text("Netzwerk Eigentümer:").color(Main.LIGHT_ACCENT_COLOR), Component.text(offlinePlayer.getName() == null ? "ERROR" : offlinePlayer.getName()).color(Main.PRIMARY_COLOR), Component.text("%divider").color(Main.LIGHT_ACCENT_COLOR)).build());
        inventory.setItem(11, Item.GUI_BUTTON_INTERFACE_HOTBAR.get());
        inventory.setItem(13, Item.GUI_BUTTON_INTERFACE_ALL.get());
        inventory.setItem(15, Item.GUI_BUTTON_INTERFACE_INVENTORY.get());
        fillSpacers();
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!player.hasCooldown(Material.STRUCTURE_VOID)) {
            if (event.getSlot() == 11) {
                if (Main.MAINTENANCE_MODE) {
                    player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                    SoundUtils.playSound(player, SoundType.INFO);
                    return;
                }
                new CloudInterfaceTask(player, dataContainer.getCloudInterfaceOwner(), CloudInterfaceMode.HOTBAR_ONLY);
                player.setCooldown(Material.STRUCTURE_VOID, 40);
                return;
            }
            if (event.getSlot() == 13) {
                if (Main.MAINTENANCE_MODE) {
                    player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                    SoundUtils.playSound(player, SoundType.INFO);
                    return;
                }
                new CloudInterfaceTask(player, dataContainer.getCloudInterfaceOwner(), CloudInterfaceMode.ALL);
                player.setCooldown(Material.STRUCTURE_VOID, 40);
                return;
            }
            if (event.getSlot() == 15) {
                if (Main.MAINTENANCE_MODE) {
                    player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                    SoundUtils.playSound(player, SoundType.INFO);
                    return;
                }
                new CloudInterfaceTask(player, dataContainer.getCloudInterfaceOwner(), CloudInterfaceMode.INVENTORY_ONLY);
                player.setCooldown(Material.STRUCTURE_VOID, 40);
            }
        }
    }

    @Override
    public void handleClose(InventoryCloseEvent event) {
    }
}
