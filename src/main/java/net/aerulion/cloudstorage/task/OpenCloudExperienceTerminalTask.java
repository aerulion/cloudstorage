package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.gui.guis.CloudExperienceTerminalGUI;
import net.aerulion.cloudstorage.gui.guis.CloudExperienceTerminalUpgradeGUI;
import net.aerulion.cloudstorage.utils.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class OpenCloudExperienceTerminalTask extends BukkitRunnable {

    private final CloudExperienceTerminal CLOUDEXPERIENCETERMINAL;
    private final Player PLAYER;
    private final Inventory INVENTORY;

    public OpenCloudExperienceTerminalTask(CloudExperienceTerminal cloudExperienceTerminal, Player player, Inventory inventory) {
        this.CLOUDEXPERIENCETERMINAL = cloudExperienceTerminal;
        this.PLAYER = player;
        this.INVENTORY = inventory;
        this.runTask(Main.plugin);
    }

    @Override
    public void run() {
        if (CLOUDEXPERIENCETERMINAL.isPRIVATE() && !PLAYER.hasPermission(Permission.BYPASS_PRIVATE.get())) {
            if (!PLAYER.getUniqueId().toString().equals(CLOUDEXPERIENCETERMINAL.getOWNER_UUID())) {
                PLAYER.sendMessage(Messages.MESSAGE_NO_ACCESS.get());
                PLAYER.closeInventory();
                return;
            }
        }
        if (INVENTORY.equals(Inventory.EXPERIENCE_TERMINAL)) {
            new CloudExperienceTerminalGUI(PLAYER, CLOUDEXPERIENCETERMINAL).open();
            Main.openGUIs.put(PLAYER.getUniqueId().toString(), CLOUDEXPERIENCETERMINAL.getOWNER_UUID());
        }
        if (INVENTORY.equals(Inventory.UPGRADE)) {
            new CloudExperienceTerminalUpgradeGUI(PLAYER, CLOUDEXPERIENCETERMINAL).open();
        }
    }
}