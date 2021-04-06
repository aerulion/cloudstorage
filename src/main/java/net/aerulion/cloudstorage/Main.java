package net.aerulion.cloudstorage;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.aerulion.cloudstorage.block.BlockManager;
import net.aerulion.cloudstorage.cmd.CMD_cloudstorage;
import net.aerulion.cloudstorage.file.ConfigFile;
import net.aerulion.cloudstorage.gui.GuiHandler;
import net.aerulion.cloudstorage.listener.LegacyConverter;
import net.aerulion.cloudstorage.listener.PreventGrindstoneListener;
import net.aerulion.cloudstorage.listener.WirelessCloudAccessPointListener;
import net.aerulion.cloudstorage.listener.WirelessCloudInterfaceListener;
import net.aerulion.cloudstorage.utils.ImportExportHandler;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.console.ConsoleUtils;
import net.kyori.adventure.text.format.TextColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    public static Main plugin;
    public static Economy economy;
    public static WorldGuardPlugin worldGuard;
    public final static TextColor ERROR_COLOR = TextColor.color(252, 60, 60);
    public final static TextColor PRIMARY_COLOR = TextColor.color(25, 181, 254);
    public final static TextColor DARK_ACCENT_COLOR = TextColor.color(34, 49, 63);
    public final static TextColor LIGHT_ACCENT_COLOR = TextColor.color(141, 149, 157);
    public static final HashMap<String, String> openGUIs = new HashMap<>();
    public static List<String> DISABLED_WORLDS;
    public static boolean MAINTENANCE_MODE = true;
    public static ImportExportHandler importExportHandler;

    @Override
    public void onEnable() {
        ConsoleUtils.sendColoredConsoleMessage(Messages.CONSOLE_ENABLING.get());
        plugin = this;
        ConfigFile.copyDefault();
        DISABLED_WORLDS = ConfigFile.getFileConfiguration().getStringList("DisabledWorlds");
        importExportHandler = new ImportExportHandler();
        PluginManager pluginManager = getServer().getPluginManager();
        if (!setupEconomy()) {
            ConsoleUtils.sendColoredConsoleMessage(Messages.CONSOLE_VAULT_NOT_FOUND.get());
            pluginManager.disablePlugin(this);
            return;
        }
        if (!setupWorldGuard()) {
            ConsoleUtils.sendColoredConsoleMessage(Messages.CONSOLE_WORLD_GUARD_NOT_FOUND.get());
            pluginManager.disablePlugin(this);
            return;
        }
        pluginManager.registerEvents(new BlockManager(), this);
        pluginManager.registerEvents(new GuiHandler(), this);
        pluginManager.registerEvents(importExportHandler, this);
        pluginManager.registerEvents(new PreventGrindstoneListener(), this);
        pluginManager.registerEvents(new WirelessCloudAccessPointListener(), this);
        pluginManager.registerEvents(new WirelessCloudInterfaceListener(), this);
        pluginManager.registerEvents(new LegacyConverter(), this);

        PluginCommand cmdCloudStorage = getCommand("cloudstorage");
        if (cmdCloudStorage != null)
            cmdCloudStorage.setExecutor(new CMD_cloudstorage());

        MAINTENANCE_MODE = false;
        ConsoleUtils.sendColoredConsoleMessage(Messages.CONSOLE_PLUGIN_ENABLED.get());
    }

    @Override
    public void onDisable() {
        ConsoleUtils.sendColoredConsoleMessage(Messages.CONSOLE_DISABLING.get());
        ConsoleUtils.sendColoredConsoleMessage(Messages.CONSOLE_PLUGIN_DISABLED.get());
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> registeredServiceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (registeredServiceProvider == null)
            return false;
        economy = registeredServiceProvider.getProvider();
        return true;
    }

    private boolean setupWorldGuard() {
        worldGuard = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");
        return worldGuard != null;
    }
}