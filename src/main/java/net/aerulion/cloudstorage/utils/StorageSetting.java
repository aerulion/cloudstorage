package net.aerulion.cloudstorage.utils;

public enum StorageSetting {
    ALL(0, 35),
    INVENTORY_ONLY(9, 35),
    HOTBAR_ONLY(0, 8);

    private int start_slot;
    private int end_slot;

    StorageSetting(int start_slot, int end_slot) {
        this.start_slot = start_slot;
        this.end_slot = end_slot;
    }

    public int getStartSlot() {
        return start_slot;
    }

    public int getEndSlot() {
        return end_slot;
    }
}
