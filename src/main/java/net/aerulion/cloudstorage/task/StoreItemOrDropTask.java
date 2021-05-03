package net.aerulion.cloudstorage.task;

import net.aerulion.cloudstorage.Main;
import net.aerulion.nucleus.api.base64.Base64Utils;
import net.aerulion.nucleus.api.mysql.MySQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StoreItemOrDropTask extends BukkitRunnable {

    private final ItemStack itemStack;
    private final String ownerUUID;
    private final Location location;

    public StoreItemOrDropTask(ItemStack itemStack, String ownerUUID, Location location) {
        this.itemStack = itemStack;
        this.ownerUUID = ownerUUID;
        this.location = location;
        this.runTaskAsynchronously(Main.plugin);
    }

    @Override
    public void run() {
        try (Connection connection = MySQLUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE aerulion_cloudstorage_slots SET AMOUNT=LEAST(CAPACITY,(AMOUNT+?)) WHERE (OWNER=? AND ITEM=?)");
            preparedStatement.setInt(1, itemStack.getAmount());
            preparedStatement.setString(2, ownerUUID);
            preparedStatement.setString(3, Base64Utils.encodeItemStack(itemStack.clone().asOne()));
            if (preparedStatement.executeUpdate() == 0)
                Bukkit.getScheduler().runTask(Main.plugin, () -> location.getWorld().dropItem(location.clone().toCenterLocation(), itemStack));
            preparedStatement.close();
        } catch (SQLException exception) {
            Bukkit.getScheduler().runTask(Main.plugin, () -> location.getWorld().dropItem(location.clone().toCenterLocation(), itemStack));
        }
    }
}