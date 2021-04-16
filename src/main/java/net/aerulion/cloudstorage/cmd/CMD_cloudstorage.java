package net.aerulion.cloudstorage.cmd;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.gui.guis.CloudShopGUI;
import net.aerulion.cloudstorage.task.*;
import net.aerulion.cloudstorage.utils.CloudInterfaceMode;
import net.aerulion.cloudstorage.utils.ItemCache;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.Permission;
import net.aerulion.nucleus.api.command.CommandUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CMD_cloudstorage implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("stats"))) {
            if (Main.MAINTENANCE_MODE) {
                commandSender.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(commandSender, SoundType.INFO);
                return true;
            }
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(Messages.ERROR_NO_PLAYER.get());
                return true;
            }
            Player player = (Player) commandSender;
            if (!player.hasPermission(Permission.COMMAND_STATS.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return true;
            }
            new CloudStorageStatsTask(player);
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!commandSender.hasPermission(Permission.COMMAND_RELOAD.get())) {
                commandSender.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(commandSender, SoundType.ERROR);
                return true;
            }
            new ReloadTask(commandSender);
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("reencode_items")) {
            if (!commandSender.hasPermission(Permission.COMMAND_RE_ENCODE_ITEMS.get())) {
                commandSender.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(commandSender, SoundType.ERROR);
                return true;
            }
            new ReEncodeItemsTask(commandSender);
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("shop")) {
            if (Main.MAINTENANCE_MODE) {
                commandSender.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(commandSender, SoundType.INFO);
                return true;
            }
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(Messages.ERROR_NO_PLAYER.get());
                return true;
            }
            Player player = (Player) commandSender;
            if (!(player.hasPermission(Permission.BUY_CLOUD_STORAGE_SLOT.get()) || player.hasPermission(Permission.BUY_CLOUD_INTERFACE.get()) || player.hasPermission(Permission.BUY_WIRELESS_CLOUD_INTERFACE.get()))) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return true;
            }
            new CloudShopGUI(player).open();
            SoundUtils.playSound(player, SoundType.OPEN_CONTAINER);
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("delete_player_data")) {
            if (!commandSender.hasPermission(Permission.COMMAND_DELETE_PLAYER_DATA.get())) {
                commandSender.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(commandSender, SoundType.ERROR);
                return true;
            }
            //noinspection deprecation
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
            if (!offlinePlayer.hasPlayedBefore()) {
                commandSender.sendMessage(Messages.ERROR_PLAYER_NOT_FOUND.get());
                SoundUtils.playSound(commandSender, SoundType.ERROR);
                return true;
            }
            new DeletePlayerDataTask(commandSender, offlinePlayer.getUniqueId().toString());
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("interface")) {
            if (Main.MAINTENANCE_MODE) {
                commandSender.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(commandSender, SoundType.INFO);
                return true;
            }
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(Messages.ERROR_NO_PLAYER.get());
                return true;
            }
            Player player = (Player) commandSender;
            if (!player.hasPermission(Permission.COMMAND_INTERFACE.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return true;
            }
            CloudInterfaceMode cloudInterfaceMode;
            try {
                cloudInterfaceMode = CloudInterfaceMode.valueOf(args[1]);
            } catch (IllegalArgumentException exception) {
                player.sendMessage(Messages.ERROR_NO_VALID_STORAGE_SETTING.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return true;
            }
            new CloudInterfaceTask(player, player.getUniqueId().toString(), cloudInterfaceMode);
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("itemcache")) {
            if (Main.MAINTENANCE_MODE) {
                commandSender.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(commandSender, SoundType.INFO);
                return true;
            }
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(Messages.ERROR_NO_PLAYER.get());
                return true;
            }
            Player player = (Player) commandSender;
            if (!player.hasPermission(Permission.COMMAND_ITEM_CACHE.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                return true;
            }
            if (!ItemCache.CACHE.containsKey(player.getUniqueId().toString())) {
                player.sendMessage(Messages.MESSAGE_NO_CACHED_ITEMS.get());
                return true;
            }
            ItemCache.getItemsFromCache(player);
            return true;
        }
        if ((args.length == 1 || args.length == 2) && args[0].equalsIgnoreCase("list")) {
            if (Main.MAINTENANCE_MODE) {
                commandSender.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(commandSender, SoundType.INFO);
                return true;
            }
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(Messages.ERROR_NO_PLAYER.get());
                return true;
            }
            Player player = (Player) commandSender;
            if (!player.hasPermission(Permission.COMMAND_LIST.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return true;
            }
            int page = 1;
            if (args.length == 2) {
                try {
                    page = Integer.parseInt(args[1]);
                } catch (NumberFormatException exception) {
                    player.sendMessage(Messages.ERROR_NO_VALID_NUMBER.get());
                    SoundUtils.playSound(player, SoundType.ERROR);
                    return true;
                }
            }
            new ListCloudStorageSlotsTask(player, page);
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("buy_access_point")) {
            if (Main.MAINTENANCE_MODE) {
                commandSender.sendMessage(Messages.ERROR_MAINTENANCE_MODE.get());
                SoundUtils.playSound(commandSender, SoundType.INFO);
                return true;
            }
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(Messages.ERROR_NO_PLAYER.get());
                return true;
            }
            Player player = (Player) commandSender;
            if (!player.hasPermission(Permission.BUY_CLOUD_ACCESS_POINT.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION_BUY.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return true;
            }
            new BuyCloudAccessPointCommandTask(player, args[1]);
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("maintenance_mode")) {
            if (!commandSender.hasPermission(Permission.COMMAND_MAINTENANCE_MODE.get())) {
                commandSender.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(commandSender, SoundType.ERROR);
                return true;
            }
            Main.MAINTENANCE_MODE = !Main.MAINTENANCE_MODE;
            commandSender.sendMessage(Main.MAINTENANCE_MODE ? Messages.MESSAGE_MAINTENANCE_MODE_ENABLED.get() : Messages.MESSAGE_MAINTENANCE_MODE_DISABLED.get());
            SoundUtils.playSound(commandSender, SoundType.SUCCESS);
            return true;
        }

        commandSender.sendMessage(Messages.ERROR_UNKNOWN_COMMAND.get());
        SoundUtils.playSound(commandSender, SoundType.ERROR);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 1) {
            ArrayList<String> subCommands = new ArrayList<>();
            if (commandSender.hasPermission(Permission.COMMAND_STATS.get()))
                subCommands.add("stats");
            if (commandSender.hasPermission(Permission.BUY_CLOUD_STORAGE_SLOT.get()) || commandSender.hasPermission(Permission.BUY_CLOUD_INTERFACE.get()) || commandSender.hasPermission(Permission.BUY_WIRELESS_CLOUD_INTERFACE.get()))
                subCommands.add("shop");
            if (commandSender.hasPermission(Permission.COMMAND_DELETE_PLAYER_DATA.get()))
                subCommands.add("delete_player_data");
            if (commandSender.hasPermission(Permission.COMMAND_RE_ENCODE_ITEMS.get()))
                subCommands.add("reencode_items");
            if (commandSender.hasPermission(Permission.COMMAND_INTERFACE.get()))
                subCommands.add("interface");
            if (commandSender.hasPermission(Permission.COMMAND_LIST.get()))
                subCommands.add("list");
            if (commandSender.hasPermission(Permission.COMMAND_ITEM_CACHE.get()))
                subCommands.add("itemcache");
            if (commandSender.hasPermission(Permission.COMMAND_RELOAD.get()))
                subCommands.add("reload");
            if (commandSender.hasPermission(Permission.COMMAND_MAINTENANCE_MODE.get()))
                subCommands.add("maintenance_mode");
            return CommandUtils.filterForTabCompleter(subCommands, args[0]);
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("delete_player_data") && commandSender.hasPermission(Permission.COMMAND_DELETE_PLAYER_DATA.get()))
            return null;
        if (args.length == 2 && args[0].equalsIgnoreCase("interface") && commandSender.hasPermission(Permission.COMMAND_INTERFACE.get()))
            return CommandUtils.filterForTabCompleter(new ArrayList<>(Arrays.asList(CloudInterfaceMode.ALL.name(), CloudInterfaceMode.HOTBAR_ONLY.name(), CloudInterfaceMode.INVENTORY_ONLY.name())), args[1]);
        return Collections.emptyList();
    }
}