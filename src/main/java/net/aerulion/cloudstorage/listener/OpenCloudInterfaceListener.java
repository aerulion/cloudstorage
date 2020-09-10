package net.aerulion.cloudstorage.listener;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.inventory.CloudInterfaceInventory;
import net.aerulion.cloudstorage.utils.NBT;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.Material;
import org.bukkit.block.Lectern;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OpenCloudInterfaceListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (!(event.getPlayer().isSneaking() && event.getItem() != null)) {
                if (event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Lectern) {
                    if (Main.worldGuard.createProtectionQuery().testBlockBreak(event.getPlayer(), event.getClickedBlock())) {
                        Lectern lectern = (Lectern) event.getClickedBlock().getState();
                        ItemStack itemStack = lectern.getInventory().getItem(0);
                        if (itemStack != null) {
                            if (itemStack.getType().equals(Material.WRITTEN_BOOK)) {
                                String uuid = NbtUtils.getNBTString(lectern.getInventory().getItem(0), NBT.KEY_CLOUD_INTERFACE_OWNER_UUID.get());
                                if (!uuid.equals("")) {
                                    event.setCancelled(true);
                                    SoundUtils.playSound(event.getPlayer(), event.getClickedBlock().getLocation(), SoundType.OPEN_CONTAINER);
                                    event.getPlayer().openInventory(CloudInterfaceInventory.create(uuid));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}