package net.aerulion.cloudstorage.gui.guis;

import net.aerulion.cloudstorage.Main;
import net.aerulion.cloudstorage.gui.GUI;
import net.aerulion.cloudstorage.task.FetchCloudExperienceTerminalTask;
import net.aerulion.cloudstorage.task.StoreExperienceTask;
import net.aerulion.cloudstorage.task.ToggleCloudExperienceTerminalAccessTask;
import net.aerulion.cloudstorage.task.WithdrawExperienceTask;
import net.aerulion.cloudstorage.utils.CloudExperienceTerminal;
import net.aerulion.cloudstorage.utils.Inventory;
import net.aerulion.cloudstorage.utils.Items;
import net.aerulion.cloudstorage.utils.Messages;
import net.aerulion.nucleus.api.experience.ExperienceUtils;
import net.aerulion.nucleus.api.item.ItemUtils;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.aerulion.nucleus.api.string.StringUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CloudExperienceTerminalGUI extends GUI {

    public CloudExperienceTerminalGUI(Player player, CloudExperienceTerminal cloudExperienceTerminal) {
        super(player);
        dataContainer.setCloudExperienceTerminal(cloudExperienceTerminal);
    }

    @Override
    public String getTitle() {
        return Main.PRIMARY_COLOR + "§lCloud Experience Terminal";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void setContent() {
        ItemStack storedExperience = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta storedExperienceMeta = storedExperience.getItemMeta();
        if (storedExperienceMeta != null) {
            List<String> lore = new ArrayList<>();
            String title = Main.PRIMARY_COLOR.toString() + ChatColor.BOLD + "Erfahrung";
            String amountString1 = Main.LIGHT_ACCENT_COLOR + "Eingelagert:";
            String amountString2 = Main.PRIMARY_COLOR + Messages.decimalFormat.format(dataContainer.getCloudExperienceTerminal().getStoredAmount()) + Main.LIGHT_ACCENT_COLOR + "/" + Main.PRIMARY_COLOR + Messages.decimalFormat.format(dataContainer.getCloudExperienceTerminal().getCAPACITY());
            String amountString3 = Main.LIGHT_ACCENT_COLOR + "~" + Main.PRIMARY_COLOR + Messages.decimalFormat.format(ExperienceUtils.getLevelEquivalent(dataContainer.getCloudExperienceTerminal().getStoredAmount())) + Main.LIGHT_ACCENT_COLOR + " Level";
            int pixelLength = Math.max(Math.max(StringUtils.getPixelLength(amountString1), StringUtils.getPixelLength(amountString2)), Math.max(StringUtils.getPixelLength(amountString3), StringUtils.getPixelLength(title))) + 20;
            lore.add(Main.LIGHT_ACCENT_COLOR + StringUtils.generateLine((int) Math.round(pixelLength / 4D)));
            storedExperienceMeta.setDisplayName(StringUtils.generateCenteredString(title, (int) Math.round(pixelLength / 2D)) + "§r  ");
            lore.add(StringUtils.generateCenteredString(amountString1, (int) Math.round(pixelLength / 2D)) + "§r  ");
            lore.add(StringUtils.generateCenteredString(amountString2, (int) Math.round(pixelLength / 2D)) + "§r  ");
            lore.add(StringUtils.generateCenteredString(amountString3, (int) Math.round(pixelLength / 2D)) + "§r  ");
            lore.add(Main.LIGHT_ACCENT_COLOR + StringUtils.generateLine((int) Math.round(pixelLength / 4D)));
            storedExperienceMeta.setLore(lore);
            storedExperience.setItemMeta(storedExperienceMeta);
        }
        inventory.setItem(13, storedExperience);
        if (dataContainer.getCloudExperienceTerminal().getOWNER_UUID().equals(dataContainer.getOWNER_UUID()))
            inventory.setItem(28, Items.GUI_BUTTON_CET_UPGRADES.get());
        inventory.setItem(30, Items.GUI_BUTTON_1_LEVEL.get());
        inventory.setItem(31, Items.GUI_BUTTON_10_LEVEL.get());
        inventory.setItem(32, Items.GUI_BUTTON_30_LEVEL.get());
        if (dataContainer.getCloudExperienceTerminal().getOWNER_UUID().equals(dataContainer.getOWNER_UUID()))
            inventory.setItem(34, dataContainer.getCloudExperienceTerminal().isPRIVATE() ? Items.GUI_BUTTON_CET_ACCESS_PRIVATE.get() : Items.GUI_BUTTON_CET_ACCESS_PUBLIC.get());
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(dataContainer.getCloudExperienceTerminal().getOWNER_UUID()));
        ItemStack itemStack = ItemUtils.buildGuiButton(Material.KNOWLEDGE_BOOK, Main.PRIMARY_COLOR + ChatColor.BOLD.toString() + "Cloud Info", Arrays.asList(Main.LIGHT_ACCENT_COLOR + "%divider", Main.LIGHT_ACCENT_COLOR + "Netzwerk Eigentümer:", Main.PRIMARY_COLOR + offlinePlayer.getName(), Main.LIGHT_ACCENT_COLOR + "%divider"), false, 2);
        inventory.setItem(44, itemStack);
        fillSpacers();
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getRawSlot() == 13) {
            if (event.getClick().equals(ClickType.LEFT)) {
                int amount = ExperienceUtils.getTotalExperience(player);
                if (amount > 0) {
                    amount = ((amount + dataContainer.getCloudExperienceTerminal().getStoredAmount()) <= dataContainer.getCloudExperienceTerminal().getCAPACITY()) ? amount : dataContainer.getCloudExperienceTerminal().getCAPACITY() - dataContainer.getCloudExperienceTerminal().getStoredAmount();
                    ExperienceUtils.setTotalExperience(player, ExperienceUtils.getTotalExperience(player) - amount);
                    new StoreExperienceTask(player, amount, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), false);
                    return;
                }
            }
            if (event.getClick().equals(ClickType.RIGHT)) {
                if (dataContainer.getCloudExperienceTerminal().getStoredAmount() > 0)
                    new WithdrawExperienceTask(player, dataContainer.getCloudExperienceTerminal().getStoredAmount(), dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), false);
                return;
            }
            if (event.getClick().equals(ClickType.SHIFT_RIGHT)) {
                if (dataContainer.getCloudExperienceTerminal().getStoredAmount() > 0) {
                    int amount = Math.min(dataContainer.getCloudExperienceTerminal().getStoredAmount(), 448);
                    amount = amount - (amount % 7);
                    if (amount > 0)
                        new WithdrawExperienceTask(player, amount, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), true);
                }
                return;
            }
            return;
        }
        if (event.getRawSlot() == 28) {
            ItemStack itemStack = event.getInventory().getItem(28);
            if (itemStack != null && !itemStack.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                SoundUtils.playSound(player, SoundType.UI_CLICK);
                new FetchCloudExperienceTerminalTask(dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), player, Inventory.UPGRADE);
            }
            return;
        }
        if (event.getRawSlot() == 30) {
            if (event.getClick().equals(ClickType.LEFT)) {
                int amount = calculateWithdrawLevelAmount(player, 1);
                if (dataContainer.getCloudExperienceTerminal().getStoredAmount() + amount > dataContainer.getCloudExperienceTerminal().getCAPACITY())
                    amount = dataContainer.getCloudExperienceTerminal().getCAPACITY() - dataContainer.getCloudExperienceTerminal().getStoredAmount();
                if (amount > 0) {
                    ExperienceUtils.setTotalExperience(player, ExperienceUtils.getTotalExperience(player) - amount);
                    new StoreExperienceTask(player, amount, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), false);
                }
                return;
            }
            if (event.getClick().equals(ClickType.RIGHT)) {
                int amount = calculateAddLevelAmount(player, 1);
                if (dataContainer.getCloudExperienceTerminal().getStoredAmount() < amount)
                    amount = dataContainer.getCloudExperienceTerminal().getStoredAmount();
                if (amount > 0)
                    new WithdrawExperienceTask(player, amount, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), false);
                return;
            }
            return;
        }
        if (event.getRawSlot() == 31) {
            if (event.getClick().equals(ClickType.LEFT)) {
                int amount = calculateWithdrawLevelAmount(player, 10);
                if (dataContainer.getCloudExperienceTerminal().getStoredAmount() + amount > dataContainer.getCloudExperienceTerminal().getCAPACITY())
                    amount = dataContainer.getCloudExperienceTerminal().getCAPACITY() - dataContainer.getCloudExperienceTerminal().getStoredAmount();
                if (amount > 0) {
                    ExperienceUtils.setTotalExperience(player, ExperienceUtils.getTotalExperience(player) - amount);
                    new StoreExperienceTask(player, amount, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), false);
                }
                return;
            }
            if (event.getClick().equals(ClickType.RIGHT)) {
                int amount = calculateAddLevelAmount(player, 10);
                if (dataContainer.getCloudExperienceTerminal().getStoredAmount() < amount)
                    amount = dataContainer.getCloudExperienceTerminal().getStoredAmount();
                if (amount > 0)
                    new WithdrawExperienceTask(player, amount, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), false);
                return;
            }
            return;
        }
        if (event.getRawSlot() == 32) {
            if (event.getClick().equals(ClickType.LEFT)) {
                int amount = calculateWithdrawLevelAmount(player, 30);
                if (dataContainer.getCloudExperienceTerminal().getStoredAmount() + amount > dataContainer.getCloudExperienceTerminal().getCAPACITY())
                    amount = dataContainer.getCloudExperienceTerminal().getCAPACITY() - dataContainer.getCloudExperienceTerminal().getStoredAmount();
                if (amount > 0) {
                    ExperienceUtils.setTotalExperience(player, ExperienceUtils.getTotalExperience(player) - amount);
                    new StoreExperienceTask(player, amount, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), false);
                }
                return;
            }
            if (event.getClick().equals(ClickType.RIGHT)) {
                int amount = calculateAddLevelAmount(player, 30);
                if (dataContainer.getCloudExperienceTerminal().getStoredAmount() < amount)
                    amount = dataContainer.getCloudExperienceTerminal().getStoredAmount();
                if (amount > 0)
                    new WithdrawExperienceTask(player, amount, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), false);
                return;
            }
            return;
        }
        if (event.getRawSlot() == 34) {
            ItemStack itemStack = event.getInventory().getItem(34);
            if (itemStack != null && !itemStack.getType().equals(Material.BLACK_STAINED_GLASS_PANE))
                new ToggleCloudExperienceTerminalAccessTask(dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), player);
            return;
        }
        if ((event.getRawSlot() >= 0 && event.getRawSlot() > event.getView().getTopInventory().getSize() - 1)) {
            if (event.getCurrentItem() != null && event.getCurrentItem().isSimilar(new ItemStack(Material.EXPERIENCE_BOTTLE))) {
                int amount = (event.getCurrentItem().getAmount() * 7) + dataContainer.getCloudExperienceTerminal().getStoredAmount() <= dataContainer.getCloudExperienceTerminal().getCAPACITY() ? event.getCurrentItem().getAmount() : (dataContainer.getCloudExperienceTerminal().getCAPACITY() - dataContainer.getCloudExperienceTerminal().getStoredAmount()) / 7;
                event.getCurrentItem().setAmount(event.getCurrentItem().getAmount() - amount);
                new StoreExperienceTask(player, amount * 7, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), false);
            }
            return;
        }
    }

    private int calculateAddLevelAmount(Player player, int level) {
        return ExperienceUtils.getTotalExperience(player.getLevel() + level) - ExperienceUtils.getTotalExperience(player);
    }

    private int calculateWithdrawLevelAmount(Player player, int level) {
        int levelToReach = player.getLevel() - level;
        if (levelToReach < 0)
            return ExperienceUtils.getTotalExperience(player);
        return ExperienceUtils.getTotalExperience(player) - ExperienceUtils.getTotalExperience(levelToReach);
    }

    @Override
    public void handleClose(InventoryCloseEvent event) {
        Main.openGUIs.remove(event.getPlayer().getUniqueId().toString());
    }
}
