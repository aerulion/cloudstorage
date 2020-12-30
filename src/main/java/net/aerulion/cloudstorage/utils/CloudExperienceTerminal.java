package net.aerulion.cloudstorage.utils;

import lombok.Getter;
import lombok.Setter;

public class CloudExperienceTerminal {

    @Getter
    private final String OWNER_UUID;
    @Getter
    @Setter
    private int storedAmount;
    @Getter
    private final int CAPACITY;
    @Getter
    private final boolean PRIVATE;

    public CloudExperienceTerminal(String OWNER_UUID, int storedAmount, int CAPACITY, boolean PRIVATE) {
        this.OWNER_UUID = OWNER_UUID;
        this.storedAmount = storedAmount;
        this.CAPACITY = CAPACITY;
        this.PRIVATE = PRIVATE;
    }
}
