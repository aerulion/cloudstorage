package net.aerulion.cloudstorage.utils;

public enum NBT {
    KEY_CLOUD_INTERFACE_OWNER_UUID("CLOUD_INTERFACE_OWNER"),
    KEY_CLOUD_STORAGE_SLOT_CAPACITY("CSS_CAPACITY"),
    KEY_CLOUD_STORAGE_SLOT_ID("CLOUD_STORAGE_SLOT"),
    KEY_CLOUD_STORAGE_SLOT_STORED_AMOUNT("CSS_STORED_AMOUNT"),
    KEY_GUI_TYPE("GUI_TYPE"),
    VALUE_GUI_TYPE_ACCESS_POINT("GUI_TYPE_ACCESS_POINT"),
    VALUE_GUI_TYPE_CLOUD_INTERFACE("GUI_TYPE_CLOUD_INTERFACE"),
    VALUE_GUI_TYPE_UPGRADE("GUI_TYPE_UPGRADE");

    private final String NBT;

    NBT(String NBT) {
        this.NBT = NBT;
    }

    public String get() {
        return NBT;
    }
}