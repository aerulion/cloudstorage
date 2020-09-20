package net.aerulion.cloudstorage;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.aerulion.cloudstorage.cmd.CMD_cloudstorage;
import net.aerulion.cloudstorage.file.ConfigFile;
import net.aerulion.cloudstorage.listener.*;
import net.aerulion.cloudstorage.utils.CloudStorageInventoryHolder;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.console.ConsoleUtils;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    public static Main plugin;
    public static Economy economy;
    public static WorldGuardPlugin worldGuard;
    public final static ChatColor ERROR_COLOR = ChatColor.of(new Color(252, 60, 60));
    public final static ChatColor PRIMARY_COLOR = ChatColor.of(new Color(25, 181, 254));
    public final static ChatColor DARK_ACCENT_COLOR = ChatColor.of(new Color(34, 49, 63));
    public final static ChatColor LIGHT_ACCENT_COLOR = ChatColor.of(new Color(141, 149, 157));
    public static CloudStorageInventoryHolder cloudStorageInventoryHolder;
    public static final HashMap<String, String> openGUIs = new HashMap<>();
    public static List<String> DISABLED_WORLDS;
    public static boolean MAINTENANCE_MODE = true;

    @Override
    public void onEnable() {
        ConsoleUtils.sendColoredConsoleMessage(Messages.CONSOLE_ENABLING.get());
        ConfigFile.copyDefault();
        DISABLED_WORLDS = ConfigFile.getFileConfiguration().getStringList("DisabledWorlds");
        plugin = this;
        cloudStorageInventoryHolder = new CloudStorageInventoryHolder();
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
        pluginManager.registerEvents(new BreakCloudAccessPointListener(), this);
        pluginManager.registerEvents(new BreakCloudInterfaceListener(), this);
        pluginManager.registerEvents(new CloudAccessPointGUIListener(), this);
        pluginManager.registerEvents(new CloudInterfaceGUIListener(), this);
        pluginManager.registerEvents(new CloudShopGUIListener(), this);
        pluginManager.registerEvents(new GUICloseListener(), this);
        pluginManager.registerEvents(new OpenCloudAccessPointListener(), this);
        pluginManager.registerEvents(new OpenCloudInterfaceListener(), this);
        pluginManager.registerEvents(new PlaceCloudAccessPointListener(), this);
        pluginManager.registerEvents(new PlaceCloudInterfaceListener(), this);
        pluginManager.registerEvents(new PreventDispenseListener(), this);
        pluginManager.registerEvents(new PreventExplodeListener(), this);
        pluginManager.registerEvents(new PreventGrindstoneListener(), this);
        pluginManager.registerEvents(new PreventHopperListener(), this);
        pluginManager.registerEvents(new UpgradeGUIListener(), this);
        pluginManager.registerEvents(new WirelessCloudAccessPointListener(), this);
        pluginManager.registerEvents(new WirelessCloudInterfaceListener(), this);

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