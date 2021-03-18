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
import net.aerulion.nucleus.api.item.GuiButtonBuilder;
import net.aerulion.nucleus.api.sound.SoundType;
import net.aerulion.nucleus.api.sound.SoundUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CloudExperienceTerminalGUI extends GUI {

    public CloudExperienceTerminalGUI(Player player, CloudExperienceTerminal cloudExperienceTerminal) {
        super(player);
        dataContainer.setCloudExperienceTerminal(cloudExperienceTerminal);
    }

    @Override
    public Component getTitle() {
        return Component.text("Cloud Experience Terminal").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD);
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void setContent() {
        inventory.setItem(13, GuiButtonBuilder.of(Material.EXPERIENCE_BOTTLE).withDisplayName(Component.text("Erfahrung").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(
                Component.text("%divider").color(Main.LIGHT_ACCENT_COLOR),
                Component.text("Eingelagert:").color(Main.LIGHT_ACCENT_COLOR),
                Component.text(Messages.decimalFormat.format(dataContainer.getCloudExperienceTerminal().getStoredAmount())).color(Main.PRIMARY_COLOR).append(Component.text("/").color(Main.LIGHT_ACCENT_COLOR)).append(Component.text(Messages.decimalFormat.format(dataContainer.getCloudExperienceTerminal().getCAPACITY())).color(Main.PRIMARY_COLOR)),
                Component.text("~").color(Main.LIGHT_ACCENT_COLOR).append(Component.text(Messages.decimalFormat.format(ExperienceUtils.getLevelEquivalent(dataContainer.getCloudExperienceTerminal().getStoredAmount()))).color(Main.PRIMARY_COLOR)).append(Component.text(" Level").color(Main.LIGHT_ACCENT_COLOR)),
                Component.text("%divider").color(Main.LIGHT_ACCENT_COLOR),
                Component.text("Linksklick:").color(Main.LIGHT_ACCENT_COLOR),
                Component.text("Eigene Erfahrung einlagern").color(Main.LIGHT_ACCENT_COLOR),
                Component.text("Rechtsklick:").color(Main.LIGHT_ACCENT_COLOR),
                Component.text("Gesamte Erfahrung auslagern").color(Main.LIGHT_ACCENT_COLOR),
                Component.text("%divider").color(Main.LIGHT_ACCENT_COLOR)
        ).build());
        inventory.setItem(16, Items.GUI_BUTTON_EXP_BOTTLES.get());
        if (dataContainer.getCloudExperienceTerminal().getOWNER_UUID().equals(dataContainer.getOWNER_UUID()))
            inventory.setItem(28, Items.GUI_BUTTON_CET_UPGRADES.get());
        inventory.setItem(30, Items.GUI_BUTTON_1_LEVEL.get());
        inventory.setItem(31, Items.GUI_BUTTON_10_LEVEL.get());
        inventory.setItem(32, Items.GUI_BUTTON_30_LEVEL.get());
        inventory.setItem(34, dataContainer.getCloudExperienceTerminal().isPRIVATE() ? Items.GUI_BUTTON_CET_ACCESS_PRIVATE.get() : Items.GUI_BUTTON_CET_ACCESS_PUBLIC.get());
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(dataContainer.getCloudExperienceTerminal().getOWNER_UUID()));
        inventory.setItem(44, GuiButtonBuilder.of(Material.KNOWLEDGE_BOOK).withCustomModelData(1).withDisplayName(Component.text("Cloud Info").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).withLore(Component.text("%divider").color(Main.LIGHT_ACCENT_COLOR), Component.text("Netzwerk Eigentümer:").color(Main.LIGHT_ACCENT_COLOR), Component.text(offlinePlayer.getName() == null ? "ERROR" : offlinePlayer.getName()).color(Main.PRIMARY_COLOR), Component.text("%divider").color(Main.LIGHT_ACCENT_COLOR)).build());
        inventory.setItem(0, Items.GUI_SPACER_EXPERIENCE_TERMINAL.get());
        fillSpacers(Items.GUI_SPACER_INVISIBLE.get());
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
            return;
        }
        if (event.getRawSlot() == 16) {
            if (event.getClick().equals(ClickType.LEFT)) {
                if (dataContainer.getCloudExperienceTerminal().getStoredAmount() > 0) {
                    int amount = Math.min(dataContainer.getCloudExperienceTerminal().getStoredAmount(), 448);
                    amount = amount - (amount % 7);
                    if (amount > 0 && event.getWhoClicked().getInventory().firstEmpty() > -1)
                        new WithdrawExperienceTask(player, amount, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), true);
                }
                return;
            }
            if (event.getClick().equals(ClickType.RIGHT)) {
                if (dataContainer.getCloudExperienceTerminal().getStoredAmount() > 0) {
                    int amount = 0;
                    for (int i = 0; i < 36; i++)
                        if (event.getView().getBottomInventory().getItem(i) == null)
                            amount += 448;
                    amount = Math.min(dataContainer.getCloudExperienceTerminal().getStoredAmount(), amount);
                    amount = amount - (amount % 7);
                    if (amount > 0)
                        new WithdrawExperienceTask(player, amount, dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), true);
                }
                return;
            }
            return;
        }
        if (event.getRawSlot() == 28) {
            SoundUtils.playSound(player, SoundType.UI_CLICK);
            new FetchCloudExperienceTerminalTask(dataContainer.getCloudExperienceTerminal().getOWNER_UUID(), player, Inventory.UPGRADE);
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
