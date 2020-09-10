package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.task.FetchCloudStorageSlotTask;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
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