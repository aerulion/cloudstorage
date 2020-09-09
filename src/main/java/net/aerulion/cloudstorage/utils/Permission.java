package net.aerulion.cloudstorage.utils;

public enum Permission {
    BUY_INTERFACE("buy.interface"),
    BUY_STORAGE_SLOT("buy.storageslot"),
    BUY_WIRELESS_ACCESS_POINT("buy.wirelessaccesspoint"),
    BUY_WIRELESS_INTERFACE("buy.wirelessinterface"),
    BYPASS_PRIVATE("bypass.private"),
    DELETE_PLAYER_DATA("deleteplayerdata"),
    INTERFACE("interface"),
    ITEM_CACHE("itemcache"),
    LIST("list"),
    STATS("stats");


    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String get() {
        return "cloudstorage." + permission;
    }
}