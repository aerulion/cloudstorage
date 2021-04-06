package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;

public class CloudExportBusTask extends BukkitRunnable {

    private final BlockInventoryHolder blockInventoryHolder;

    public CloudExportBusTask(BlockInventoryHolder blockInventoryHolder) {
        this.blockInventoryHolder = blockInventoryHolder;
        this.runTask(Main.plugin);
    }

    @Override
    public void run() {
        Location location = blockInventoryHolder.getBlock().getLocation();
        location.getNearbyEntities(10, 10, 10).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .forEach(entity -> {
                    entity.playSound(location, Sound.BLOCK_PISTON_CONTRACT, 1F, 0.8F);
                    entity.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 0.5F);
                });
    }
}