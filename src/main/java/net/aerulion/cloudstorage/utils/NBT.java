package net.aerulion.cloudstorage.utils;

public enum NBT {
    KEY_CLOUD_EXPERIENCE_TERMINAL_OWNER_UUID("CLOUD_EXPERIENCE_TERMINAL_OWNER"),
    KEY_CLOUD_INTERFACE_MODE("CLOUD_INTERFACE_MODE"),
    KEY_CLOUD_INTERFACE_OWNER_UUID("CLOUD_INTERFACE_OWNER"),
    KEY_CLOUD_STORAGE_SLOT_ID("CLOUD_STORAGE_SLOT"),
    KEY_CLOUD_STORAGE_SLOT_OWNER_UUID("CLOUD_STORAGE_SLOT_OWNER");

    private final String NBT;

    NBT(String NBT) {
        this.NBT = NBT;
    }

    public String get() {
        return NBT;
    }
}