package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.task.CloudInterfaceTask;
import net.aerulion.cloudstorage.utils.*;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class WirelessCloudInterfaceListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack itemStack = event.getItem();
            if (itemStack != null) {
                if (itemStack.getType().equals(Material.STRUCTURE_VOID)) {
                    String uuid = NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get());
                    if (!uuid.equals("")) {
                        event.setCancelled(true);
                        if (Main.DISABLED_WORLDS.contains(event.getPlayer().getWorld().getName()) && !event.getPlayer().hasPermission(Permission.BYPASS_WORLD.get())) {
                            event.getPlayer().sendMessage(Messages.ERROR_DISABLED_WORLD.get());
                            SoundUtils.playSound(event.getPlayer(), SoundType.ERROR);
                            return;
                        }
                        if (!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !event.getPlayer().hasPermission(Permission.BYPASS_GAMEMODE.get())) {
                            event.getPlayer().sendMessage(Messages.ERROR_GAME_MODE.get());
                            SoundUtils.playSound(event.getPlayer(), SoundType.ERROR);
                            return;
                        }
                        CloudInterfaceMode currentMode;
                        try {
                            currentMode = CloudInterfaceMode.valueOf(NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_INTERFACE_MODE.get()));
                        } catch (IllegalArgumentException exception) {
                            currentMode = CloudInterfaceMode.ALL;
                        }
                        if (event.getPlayer().isSneaking()) {
                            CloudInterfaceMode nextMode;
                            if (currentMode.equals(CloudInterfaceMode.ALL))
                                nextMode = CloudInterfaceMode.HOTBAR_ONLY;
                            else if (currentMode.equals(CloudInterfaceMode.HOTBAR_ONLY))
                                nextMode = CloudInterfaceMode.INVENTORY_ONLY;
                            else if (currentMode.equals(CloudInterfaceMode.INVENTORY_ONLY))
                                nextMode = CloudInterfaceMode.ALL;
                            else
                                nextMode = CloudInterfaceMode.ALL;
                            ItemStack wirelessCloudInterface = Items.getWirelessCloudInterface(uuid, nextMode);
                            wirelessCloudInterface.setAmount(itemStack.getAmount());
                            EquipmentSlot hand = event.getHand();
                            if (hand != null) {
                                if (event.getHand().equals(EquipmentSlot.HAND))
                                    event.getPlayer().getInventory().setItemInMainHand(wirelessCloudInterface);
                                if (event.getHand().equals(EquipmentSlot.OFF_HAND))
                                    event.getPlayer().getInventory().setItemInOffHand(wirelessCloudInterface);
                            }
                            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("Modus: ").color(Main.DARK_ACCENT_COLOR).append(nextMode.getDisplay()).color(Main.PRIMARY_COLOR).bold(true).create());
                            SoundUtils.playSound(event.getPlayer(), SoundType.UI_CLICK);
                            return;
                        }
                        if (!event.getPlayer().hasCooldown(Material.STRUCTURE_VOID)) {
                            event.getPlayer().setCooldown(Material.STRUCTURE_VOID, 100);
                            new CloudInterfaceTask(event.getPlayer(), uuid, currentMode);
                        }
                    }
                }
            }
        }
    }
}