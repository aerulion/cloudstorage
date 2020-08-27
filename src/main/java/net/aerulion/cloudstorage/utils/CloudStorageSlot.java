package net.aerulion.cloudstorage.utils;

import org.bukkit.inventory.ItemStack;

public class CloudStorageSlot {
    private final String UUID;
    private final String OWNER_UUID;
    private final int STORED_AMOUNT;
    private final int CAPACITY;
    private final ItemStack STORED_ITEM;
    private final boolean PRIVATE;

    public CloudStorageSlot(String UUID, String OWNER_UUID, int STORED_AMOUNT, int CAPACITY, ItemStack STORED_ITEM, boolean PRIVATE) {
        this.UUID = UUID;
        this.OWNER_UUID = OWNER_UUID;
        this.STORED_AMOUNT = STORED_AMOUNT;
        this.CAPACITY = CAPACITY;
        this.STORED_ITEM = STORED_ITEM;
        this.PRIVATE = PRIVATE;
    }

    public String getUUID() {
        return UUID;
    }

    public String getOwnerUUID() {
        return OWNER_UUID;
    }

    public int getStoredAmount() {
        return STORED_AMOUNT;
    }

    public int getCapacity() {
        return CAPACITY;
    }

    public ItemStack getStoredItem() {
        return STORED_ITEM;
    }

    public boolean isPrivate() {
        return PRIVATE;
    }
}
