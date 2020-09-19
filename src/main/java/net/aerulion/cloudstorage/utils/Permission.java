package net.aerulion.cloudstorage.utils;

public enum Permission {
    BUY_CLOUD_ACCESS_POINT("buy.cloudaccesspoint"),
    BUY_CLOUD_INTERFACE("buy.cloudinterface"),
    BUY_CLOUD_STORAGE_SLOT("buy.cloudstorageslot"),
    BUY_UPGRADE("buy.upgrade"),
    BUY_WIRELESS_CLOUD_ACCESS_POINT("buy.wirelesscloudaccesspoint"),
    BUY_WIRELESS_CLOUD_INTERFACE("buy.wirelesscloudinterface"),
    BYPASS_GAMEMODE("bypass.gamemode"),
    BYPASS_PRIVATE("bypass.private"),
    BYPASS_WORLD("bypass.world"),
    COMMAND_DELETE_PLAYER_DATA("cmd.deleteplayerdata"),
    COMMAND_RE_ENCODE_ITEMS("cmd.reencodeitems"),
    COMMAND_INTERFACE("cmd.interface"),
    COMMAND_ITEM_CACHE("cmd.itemcache"),
    COMMAND_LIST("cmd.list"),
    COMMAND_RELOAD("cmd.reload"),
    COMMAND_STATS("cmd.stats");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String get() {
        return "cloudstorage." + permission;
    }
}