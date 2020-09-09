package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.task.FetchCloudStorageSlotTask;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OpenCloudAccessPointListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (!(event.getPlayer().isSneaking() && event.getItem() != null)) {
                if (event.getClickedBlock().getState() instanceof Dispenser) {
                    Dispenser dispenser = (Dispenser) event.getClickedBlock().getState();
                    if (dispenser.getInventory().getItem(0) != null) {
                        if (dispenser.getInventory().getItem(0).getType().equals(Material.PAPER)) {
                            if (!NbtUtils.getNBTString(dispenser.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()).equals("")) {
                                event.setCancelled(true);
                                SoundUtils.playSound(event.getPlayer(), event.getClickedBlock().getLocation(), SoundType.OPEN_CONTAINER);
                                new FetchCloudStorageSlotTask(NbtUtils.getNBTString(dispenser.getInventory().getItem(0), NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getPlayer(), Inventory.ACCESS_POINT);
                            }
                        }
                    }
                }
            }
        }
    }
}