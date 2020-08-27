package net.aerulion.cloudstorage.utils;

public enum Permission {
    BUY("buy"),
    BUY_INTERFACE("buyinterface"),
    DELETE_PLAYER_DATA("deleteplayerdata"),
    INTERFACE("interface"),
    ITEM_CACHE("itemcache"),
    STATS("stats");


    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String get() {
        return "cloudstorage." + permission;
    }
}