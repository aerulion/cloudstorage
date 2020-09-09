package net.aerulion.cloudstorage.utils;

public enum CloudInterfaceMode {
    ALL(0, 35, "Alles"),
    INVENTORY_ONLY(9, 35, "Nur Inventar"),
    HOTBAR_ONLY(0, 8, "Nur Hotbar");

    private final int start_slot;
    private final int end_slot;
    private final String display;

    CloudInterfaceMode(int start_slot, int end_slot, String display) {
        this.start_slot = start_slot;
        this.end_slot = end_slot;
        this.display = display;
    }

    public int getStartSlot() {
        return start_slot;
    }

    public int getEndSlot() {
        return end_slot;
    }

    public String getDisplay() {
        return display;
    }
}
