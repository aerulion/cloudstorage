package net.aerulion.cloudstorage.cmd;

import net.aerulion.cloudstorage.utils.ItemCache;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.Permission;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CMD_itemcache implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Messages.ERROR_NO_PLAYER.get());
            return true;
        }
        Player player = (Player) commandSender;
        if (!player.hasPermission(Permission.ITEM_CACHE.get())) {
            player.sendMessage(Messages.ERROR_NO_PERMISSION.get());
            return true;
        }
        if (args.length != 0) {
            player.sendMessage(Messages.ERROR_TOO_MANY_ARGUMENTS.get());
            SoundUtils.playSound(player, SoundType.ERROR);
            return true;
        }
        if (!ItemCache.CACHE.containsKey(player.getUniqueId().toString())) {
            player.sendMessage(Messages.MESSAGE_NO_CACHED_ITEMS.get());
            return true;
        }
        ItemCache.getItemsFromCache(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
