package net.aerulion.cloudstorage.gui.guis;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.gui.GUI;
import net.aerulion.cloudstorage.task.CloudInterfaceTask;
import net.aerulion.cloudstorage.utils.CloudInterfaceMode;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.item.ItemUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public class CloudInterfaceGUI extends GUI {

    public CloudInterfaceGUI(Player player, String cloudInterfaceOwner) {
        super(player);
        dataContainer.setCloudInterfaceOwner(cloudInterfaceOwner);
    }

    @Override
    public String getTitle() {
        return Main.PRIMARY_COLOR + "§lCloud Interface";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void setContent() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(dataContainer.getCloudInterfaceOwner()));
        ItemStack itemStack = ItemUtils.buildGuiButton(Material.KNOWLEDGE_BOOK, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Cloud Info", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Netzwerk Eigentümer:", Main.PRIMARY_COLOR + offlinePlayer.getName(), Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2);
        inventory.setItem(26, itemStack);
        inventory.setItem(11, Items.GUI_BUTTON_INTERFACE_HOTBAR.get());
        inventory.setItem(13, Items.GUI_BUTTON_INTERFACE_ALL.get());
        inventory.setItem(15, Items.GUI_BUTTON_INTERFACE_INVENTORY.get());
        fillSpacers();
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!player.hasCooldown(Material.STRUCTURE_VOID)) {
            if (event.getSlot() == 11) {
                if (Main.MAINTENANCE_MODE) {
                    player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                    SoundUtils.playSound(player, SoundType.ALERT);
                    return;
                }
                new CloudInterfaceTask(player, dataContainer.getCloudInterfaceOwner(), CloudInterfaceMode.HOTBAR_ONLY);
                player.setCooldown(Material.STRUCTURE_VOID, 40);
                return;
            }
            if (event.getSlot() == 13) {
                if (Main.MAINTENANCE_MODE) {
                    player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                    SoundUtils.playSound(player, SoundType.ALERT);
                    return;
                }
                new CloudInterfaceTask(player, dataContainer.getCloudInterfaceOwner(), CloudInterfaceMode.ALL);
                player.setCooldown(Material.STRUCTURE_VOID, 40);
                return;
            }
            if (event.getSlot() == 15) {
                if (Main.MAINTENANCE_MODE) {
                    player.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                    SoundUtils.playSound(player, SoundType.ALERT);
                    return;
                }
                new CloudInterfaceTask(player, dataContainer.getCloudInterfaceOwner(), CloudInterfaceMode.INVENTORY_ONLY);
                player.setCooldown(Material.STRUCTURE_VOID, 40);
                return;
            }
        }
    }

    @Override
    public void handleClose(InventoryCloseEvent event) {
    }
}
