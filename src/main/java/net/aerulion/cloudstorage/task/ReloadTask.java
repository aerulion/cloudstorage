package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.file.ConfigFile;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class ReloadTask extends BukkitRunnable {

    private final CommandSender COMMAND_SENDER;

    public ReloadTask(CommandSender COMMAND_SENDER) {
        this.COMMAND_SENDER = COMMAND_SENDER;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        Main.DISABLED_WORLDS = ConfigFile.getFileConfiguration().getStringList("DisabledWorlds");
        COMMAND_SENDER.sendMessage(Messages.MESSAGE_RELOADED.get());
        SoundUtils.playSound(COMMAND_SENDER, SoundType.SUCCESS);
    }
}