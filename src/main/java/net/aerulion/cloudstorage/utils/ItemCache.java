package net.aerulion.cloudstorage.utils;

import net.aerulion.nucleus.api.base64.Base64Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ItemCache {

    public final static HashMap<String, HashMap<String, Integer>> CACHE = new HashMap<>();

    public static void addItemToCache(Player player, String item, int amount) {
        HashMap<String, Integer> playerCache = CACHE.containsKey(player.getUniqueId().toString()) ? CACHE.get(player.getUniqueId().toString()) : new HashMap<>();
        if (playerCache.containsKey(item))
            playerCache.put(item, playerCache.get(item) + amount);
        else
            playerCache.put(item, amount);
        CACHE.put(player.getUniqueId().toString(), playerCache);
    }

    public static void getItemsFromCache(Player player) {
        int itemsGiven = 0;
        boolean skipped = false;
        for (String s : CACHE.get(player.getUniqueId().toString()).keySet()) {
            ItemStack itemStack = Base64Utils.decodeItemStack(s);
            int amount = CACHE.get(player.getUniqueId().toString()).get(s);
            int given = 0;
            while (given < amount) {
                if (player.getInventory().firstEmpty() == -1) {
                    skipped = true;
                    break;
                }
                int stackSize = Math.min(amount - given, itemStack.getMaxStackSize());
                itemStack.setAmount(stackSize);
                player.getInventory().addItem(itemStack.clone());
                given += stackSize;
                itemsGiven += stackSize;
            }
            if (amount == given) {
                CACHE.get(player.getUniqueId().toString()).remove(s);
            } else {
                HashMap<String, Integer> temp = CACHE.get(player.getUniqueId().toString());
                temp.put(s, amount - given);
                CACHE.put(player.getUniqueId().toString(), temp);
            }
        }
        if (CACHE.get(player.getUniqueId().toString()).isEmpty())
            CACHE.remove(player.getUniqueId().toString());
        player.sendMessage(Messages.PREFIX.getRaw() + "§a§l" + itemsGiven + Messages.MESSAGE_CACHED_ITEMS_ADDED.getRaw() + (skipped ? Messages.MESSAGE_CACHED_ITEMS_LEFT.getRaw() : ""));
    }
}