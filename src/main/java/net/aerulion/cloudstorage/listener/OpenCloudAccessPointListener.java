package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.task.FetchCloudStorageSlotTask;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OpenCloudAccessPointListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (!(event.getPlayer().isSneaking() && event.getItem() != null)) {
                if (event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Dispenser) {
                    if (Main.worldGuard.createProtectionQuery().testBlockBreak(event.getPlayer(), event.getClickedBlock())) {
                        Dispenser dispenser = (Dispenser) event.getClickedBlock().getState();
                        ItemStack itemStack = dispenser.getInventory().getItem(0);
                        if (itemStack != null) {
                            if (itemStack.getType().equals(Material.PAPER)) {
                                if (!NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()).equals("")) {
                                    event.setCancelled(true);
                                    SoundUtils.playSound(event.getPlayer(), event.getClickedBlock().getLocation(), SoundType.OPEN_CONTAINER);
                                    new FetchCloudStorageSlotTask(NbtUtils.getNBTString(itemStack, NBT.KEY_CLOUD_STORAGE_SLOT_ID.get()), event.getPlayer(), Inventory.ACCESS_POINT);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}