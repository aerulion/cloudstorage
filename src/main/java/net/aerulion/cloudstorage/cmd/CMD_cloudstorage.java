package net.aerulion.cloudstorage.cmd;

import net.aerulion.cloudstorage.task.BuyCloudStorageSlotTask;
import net.aerulion.cloudstorage.task.CloudInterfaceTask;
import net.aerulion.cloudstorage.task.CloudStorageStatsTask;
import net.aerulion.cloudstorage.task.DeletePlayerDataTask;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.cloudstorage.utils.Permission;
import net.aerulion.cloudstorage.utils.StorageSetting;
import net.aerulion.nucleus.api.command.CommandUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import org.bukkit.Bukkit;
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
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(Messages.ERROR_NO_PLAYER.get());
                return true;
            }
            Player player = (Player) commandSender;
            if (!player.hasPermission(Permission.STATS.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return true;
            }
            new CloudStorageStatsTask(player);
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("buy")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(Messages.ERROR_NO_PLAYER.get());
                return true;
            }
            Player player = (Player) commandSender;
            if (!player.hasPermission(Permission.BUY.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return true;
            }
            new BuyCloudStorageSlotTask(player);
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("buyinterface")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(Messages.ERROR_NO_PLAYER.get());
                return true;
            }
            Player player = (Player) commandSender;
            if (!player.hasPermission(Permission.BUY_INTERFACE.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return true;
            }
            player.getInventory().addItem(Items.getCloudInterface(player.getUniqueId().toString()));
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("deleteplayerdata")) {
            if (!commandSender.hasPermission(Permission.DELETE_PLAYER_DATA.get())) {
                commandSender.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(commandSender, SoundType.ERROR);
                return true;
            }
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                commandSender.sendMessage(Messages.ERROR_PLAYER_NOT_FOUND.get());
                SoundUtils.playSound(commandSender, SoundType.ERROR);
                return true;
            }
            new DeletePlayerDataTask(commandSender, player.getUniqueId().toString());
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("interface")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(Messages.ERROR_NO_PLAYER.get());
                return true;
            }
            Player player = (Player) commandSender;
            if (!player.hasPermission(Permission.INTERFACE.get())) {
                player.sendMessage(Messages.ERROR_NO_PERMISSION.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return true;
            }
            StorageSetting storageSetting;
            try {
                storageSetting = StorageSetting.valueOf(args[1]);
            } catch (IllegalArgumentException exception) {
                player.sendMessage(Messages.ERROR_NO_VALID_STORAGE_SETTING.get());
                SoundUtils.playSound(player, SoundType.ERROR);
                return true;
            }
            new CloudInterfaceTask(player, player.getUniqueId().toString(), storageSetting);
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
            if (commandSender.hasPermission(Permission.STATS.get()))
                subCommands.add("stats");
            if (commandSender.hasPermission(Permission.BUY.get()))
                subCommands.add("buy");
            if (commandSender.hasPermission(Permission.BUY_INTERFACE.get()))
                subCommands.add("buyinterface");
            if (commandSender.hasPermission(Permission.DELETE_PLAYER_DATA.get()))
                subCommands.add("deleteplayerdata");
            if (commandSender.hasPermission(Permission.INTERFACE.get()))
                subCommands.add("interface");
            return CommandUtils.filterForTabCompleter(subCommands, args[0]);
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("deleteplayerdata") && commandSender.hasPermission(Permission.DELETE_PLAYER_DATA.get()))
            return null;
        if (args.length == 2 && args[0].equalsIgnoreCase("interface") && commandSender.hasPermission(Permission.INTERFACE.get()))
            return CommandUtils.filterForTabCompleter(new ArrayList<>(Arrays.asList(StorageSetting.ALL.name(), StorageSetting.HOTBAR_ONLY.name(), StorageSetting.INVENTORY_ONLY.name())), args[1]);
        return Collections.emptyList();
    }
}