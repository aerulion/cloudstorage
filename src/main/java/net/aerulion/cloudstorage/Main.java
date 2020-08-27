package net.aerulion.cloudstorage;

import net.aerulion.cloudstorage.cmd.CMD_cloudstorage;
import net.aerulion.cloudstorage.cmd.CMD_itemcache;
import net.aerulion.cloudstorage.listener.*;
import net.aerulion.cloudstorage.utils.CloudStorageInventoryHolder;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.console.ConsoleUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.HashMap;

public class Main extends JavaPlugin {

    public static Main plugin;
    public static Economy economy;
    public final static Color PRIMARY_COLOR = new Color(25, 181, 254);
    public final static Color ACCENT_COLOR = new Color(34, 49, 63);
    public static CloudStorageInventoryHolder cloudStorageInventoryHolder;
    public static HashMap<String, String> openGUIs = new HashMap<>();

    @Override
    public void onEnable() {
        ConsoleUtils.sendColoredConsoleMessage(Messages.CONSOLE_ENABLING.get());
        plugin = this;
        cloudStorageInventoryHolder = new CloudStorageInventoryHolder();
        PluginManager pluginManager = getServer().getPluginManager();
        if (!setupEconomy()) {
            ConsoleUtils.sendColoredConsoleMessage(Messages.CONSOLE_VAULT_NOT_FOUND.get());
            pluginManager.disablePlugin(this);
            return;
        }
        pluginManager.registerEvents(new AccessPointGUIListener(), this);
        pluginManager.registerEvents(new BreakAccessPointListener(), this);
        pluginManager.registerEvents(new BreakCloudInterfaceListener(), this);
        pluginManager.registerEvents(new GUICloseListener(), this);
        pluginManager.registerEvents(new OpenAccessPointListener(), this);
        pluginManager.registerEvents(new OpenCloudInterfaceListener(), this);
        pluginManager.registerEvents(new PlaceAccessPointListener(), this);
        pluginManager.registerEvents(new PlaceCloudInterfaceListener(), this);
        pluginManager.registerEvents(new PreventDispenseListener(), this);
        pluginManager.registerEvents(new PreventExplodeListener(), this);
        pluginManager.registerEvents(new PreventHopperListener(), this);
        pluginManager.registerEvents(new UpgradeGUIListener(), this);
        pluginManager.registerEvents(new CloudInterfaceGUIListener(), this);

        getCommand("cloudstorage").setExecutor(new CMD_cloudstorage());
        getCommand("itemcache").setExecutor(new CMD_itemcache());

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
}