package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.inventory.AccessPointInventory;
import net.aerulion.cloudstorage.inventory.UpgradeInventory;
import net.aerulion.cloudstorage.utils.CloudStorageSlot;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.cloudstorage.utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class OpenAccessPointTask extends BukkitRunnable {

    private final CloudStorageSlot CLOUDSTORAGESLOT;
    private final Player PLAYER;
    private final Inventory INVENTORY;

    public OpenAccessPointTask(CloudStorageSlot cloudStorageSlot, Player player, Inventory inventory) {
        this.CLOUDSTORAGESLOT = cloudStorageSlot;
        this.PLAYER = player;
        this.INVENTORY = inventory;
        this.runTask(Main.plugin);
    }

    @Override
    public void run() {
        if (CLOUDSTORAGESLOT.isPrivate()) {
            if (!PLAYER.getUniqueId().toString().equals(CLOUDSTORAGESLOT.getOwnerUUID())) {
                PLAYER.sendMessage(Messages.MESSAGE_NO_ACCESS.get());
                PLAYER.closeInventory();
                return;
            }
        }
        if (INVENTORY.equals(Inventory.ACCESS_POINT)) {
            PLAYER.openInventory(AccessPointInventory.create(CLOUDSTORAGESLOT));
            Main.openGUIs.put(PLAYER.getUniqueId().toString(), CLOUDSTORAGESLOT.getUUID());
        }
        if (INVENTORY.equals(Inventory.UPGRADE)) {
            PLAYER.openInventory(UpgradeInventory.create(CLOUDSTORAGESLOT));
        }
    }
}