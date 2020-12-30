package net.aerulion.cloudstorage.utils;

import org.bukkit.Material;

public enum Upgrade {
    EXPERIENCE_TERMINAL_BASE(0,2048,5000D, Material.EXPERIENCE_BOTTLE),
    CLOUD_STORAGE_SLOT_BASE(0, 2048, 500D, Material.CHEST),
    UPGRADE_1(1, 4096, 2500D, Material.GOLD_NUGGET),
    UPGRADE_2(2, 8192, 4000D, Material.GOLD_NUGGET),
    UPGRADE_3(3, 16384, 6000D, Material.GOLD_NUGGET),
    UPGRADE_4(4, 32768, 9000D, Material.GOLD_NUGGET),
    UPGRADE_5(5, 65536, 13500D, Material.GOLD_NUGGET),
    UPGRADE_6(6, 131072, 20000D, Material.GOLD_INGOT),
    UPGRADE_7(7, 262144, 30000D, Material.GOLD_INGOT),
    UPGRADE_8(8, 524288, 45000D, Material.GOLD_INGOT),
    UPGRADE_9(9, 1048576, 67500D, Material.GOLD_INGOT),
    UPGRADE_10(10, 2097152, 101500D, Material.GOLD_INGOT),
    UPGRADE_11(11, 4194304, 152000D, Material.GOLD_BLOCK),
    UPGRADE_12(12, 8388608, 228000D, Material.GOLD_BLOCK),
    UPGRADE_13(13, 16777216, 342000D, Material.GOLD_BLOCK),
    UPGRADE_14(14, 33554432, 513000D, Material.GOLD_BLOCK),
    UPGRADE_15(15, 2147483647, 1000000D, Material.GOLDEN_APPLE);

    private final int number;
    private final int capacity;
    private final double price;
    private final Material material;

    Upgrade(int number, int capacity, double price, Material material) {
        this.number = number;
        this.capacity = capacity;
        this.price = price;
        this.material = material;
    }

    public int getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    public Material getMaterial() {
        return material;
    }
}
