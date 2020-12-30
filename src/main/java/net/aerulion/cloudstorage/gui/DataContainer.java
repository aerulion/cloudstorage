package net.aerulion.cloudstorage.gui;

import lombok.Getter;
import lombok.Setter;
import net.aerulion.cloudstorage.utils.CloudExperienceTerminal;
import net.aerulion.cloudstorage.utils.CloudStorageSlot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DataContainer {

    @Getter
    private final String OWNER_UUID;
    @Getter
    @Setter
    private CloudStorageSlot cloudStorageSlot;
    @Getter
    @Setter
    private String cloudInterfaceOwner;
    @Getter
    @Setter
    private CloudExperienceTerminal cloudExperienceTerminal;

    public DataContainer(Player owner) {
        this.OWNER_UUID = owner.getUniqueId().toString();
    }

    public Player getOwningPlayer() {
        return Bukkit.getPlayer(UUID.fromString(OWNER_UUID));
    }
}
