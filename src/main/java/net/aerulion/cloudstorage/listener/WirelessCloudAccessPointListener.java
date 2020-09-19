package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.task.FetchCloudStorageSlotTask;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.cloudstorage.utils.Permission;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WirelessCloudAccessPointListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack itemStack = event.getItem();
            if (itemStack != null) {
                if (itemStack.getType().equals(Material.HEART_OF_THE_SEA)) {
                    String uuid = NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get());
                    if (!uuid.equals("")) {
                        event.setCancelled(true);
                        if (Main.MAINTENANCE_MODE) {
                            event.getPlayer().sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                            SoundUtils.playSound(event.getPlayer(), SoundType.ALERT);
                            return;
                        }
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
                        if (!event.getPlayer().hasCooldown(Material.HEART_OF_THE_SEA)) {
                            event.getPlayer().setCooldown(Material.HEART_OF_THE_SEA, 40);
                            SoundUtils.playSound(event.getPlayer(), SoundType.OPEN_CONTAINER);
                            new FetchCloudStorageSlotTask(uuid, event.getPlayer(), Inventory.ACCESS_POINT);
                        }
                    }
                }
            }
        }
    }
}