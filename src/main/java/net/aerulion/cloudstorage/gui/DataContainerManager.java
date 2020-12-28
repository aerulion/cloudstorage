package net.aerulion.cloudstorage.gui;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class DataContainerManager {

    private static final HashMap<String, DataContainer> DATA_CONTAINERS = new HashMap<>();

    public static DataContainer getDataContainer(Player player) {
        if (!DATA_CONTAINERS.containsKey(player.getUniqueId().toString()))
            DATA_CONTAINERS.put(player.getUniqueId().toString(), new DataContainer(player));
        return DATA_CONTAINERS.get(player.getUniqueId().toString());
    }
}