package net.aerulion.cloudstorage.utils;

import org.bukkit.inventory.ItemStack;

public class CloudStorageSlot {
    private final String UUID;
    private final String OWNER_UUID;
    private int storedAmount;
    private final int CAPACITY;
    private final ItemStack STORED_ITEM;
    private final boolean PRIVATE;

    public CloudStorageSlot(String UUID, String OWNER_UUID, int storedAmount, int CAPACITY, ItemStack STORED_ITEM, boolean PRIVATE) {
        this.UUID = UUID;
        this.OWNER_UUID = OWNER_UUID;
        this.storedAmount = storedAmount;
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
        return storedAmount;
    }

    public void setStoredAmount(int storedAmount) {
        this.storedAmount = storedAmount;
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
