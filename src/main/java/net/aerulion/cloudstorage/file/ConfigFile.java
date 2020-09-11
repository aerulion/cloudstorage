package net.aerulion.cloudstorage.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class ConfigFile {
    public static void copyDefault() {
        FileConfiguration fileConfiguration = getFileConfiguration();
        fileConfiguration.options().copyDefaults(true);
        fileConfiguration.addDefault("DisabledWorlds", Collections.singletonList("Creative"));
        try {
            fileConfiguration.save(getFile());
        } catch (IOException ignored) {
        }
    }

    public static File getFile() {
        return new File("plugins/CloudStorage", "config.yml");
    }

    public static FileConfiguration getFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getFile());
    }
}