package net.aerulion.cloudstorage.utils;

import net.aerulion.cloudstorage.Main;
import net.md_5.bungee.api.ChatColor;

import java.text.DecimalFormat;
import java.util.Locale;

public enum Messages {
    CONSOLE_DISABLING(ChatColor.GREEN + "Deaktiviere Plugin..."),
    CONSOLE_ENABLING(ChatColor.GREEN + "Aktiviere Plugin..."),
    CONSOLE_INVENTORY_HOLDER_INITIALIZED(ChatColor.GREEN + "Der InventoryHolder wurde initialisiert."),
    CONSOLE_PLUGIN_DISABLED(ChatColor.GREEN + "Das Plugin wurde deaktiviert."),
    CONSOLE_PLUGIN_ENABLED(ChatColor.GREEN + "Das Plugin wurde aktiviert."),
    CONSOLE_VAULT_NOT_FOUND(ChatColor.RED + "Vault konnte nicht gefunden werden, deaktiviere Plugin..."),

    ERROR_BUYING_CLOUD_STORAGE_SLOT(Main.ERROR_COLOR + "Fehler: Die der Cloud Storage Slot konnte nicht gekauft werden. Dein Geld wurde zurückerstattet."),
    ERROR_DELETING_DATA(Main.ERROR_COLOR + "Fehler: Die Daten konnten nicht gelöscht werden. Bitte versuche es später erneut."),
    ERROR_LOADING_DATA(Main.ERROR_COLOR + "Fehler: Die Daten konnten nicht geladen werden. Bitte versuche es später erneut."),
    ERROR_NOT_ENOUGH_MONEY(Main.ERROR_COLOR + "Fehler: Du hast nicht genügend Geld."),
    ERROR_NO_PERMISSION(Main.ERROR_COLOR + "Fehler: Du hast keine Rechte diesen Befehl zu nutzen."),
    ERROR_NO_PERMISSION_BUY(Main.ERROR_COLOR + "Fehler: Du hast keine Rechte dies zu kaufen."),
    ERROR_NO_PLAYER(Main.ERROR_COLOR + "Fehler: Dieser Befehl kann nur als Spieler genutzt werden."),
    ERROR_NO_VALID_NUMBER(Main.ERROR_COLOR + "Fehler: Keine gültige Zahl angegeben."),
    ERROR_NO_VALID_STORAGE_SETTING(Main.ERROR_COLOR + "Fehler: Keine gültige Inventar Einstellung angegeben."),
    ERROR_PLAYER_NOT_FOUND(Main.ERROR_COLOR + "Fehler: Dieser Spieler konnte nicht gefunden werden."),
    ERROR_REFUNDING_PLAYER(Main.ERROR_COLOR + "Fehler: Der Betrag konnte nicht zurückerstattet werden. Bitte wende dich an den Staff."),
    ERROR_STORING_ITEMS(Main.ERROR_COLOR + "Fehler: Die Items konnten nicht eingelagert werden. Deine Items wurden temporär zwischengelagert. Gib /itemcache ein, um deine Items zu erhalten."),
    ERROR_TOGGLE_ACCESS(Main.ERROR_COLOR + "Fehler: Die Zugriffseinstellung konnte nicht aktualisiert werden."),
    ERROR_TOGGLE_ACCESS_NO_OWNER(Main.ERROR_COLOR + "Fehler: Nur der Besitzer kann diese Einstellung ändern."),
    ERROR_TOO_MANY_ARGUMENTS(Main.ERROR_COLOR + "Fehler: Zu viele Argumente."),
    ERROR_TRANSACTION(Main.ERROR_COLOR + "Fehler: Die Transaktion konnte nicht ausgeführt werden."),
    ERROR_UNKNOWN_COMMAND(Main.ERROR_COLOR + "Fehler: Unbekannter Befehl oder falsche Argumente."),
    ERROR_WITHDRAWING_ITEMS(Main.ERROR_COLOR + "Fehler: Die Items konnten nicht ausgelagert werden. Bitte versuche es später erneut."),

    MESSAGE_CACHED_INVENTORY_FULL(Main.ERROR_COLOR + "Dein Inventar ist voll. Deine Items wurden temporär zwischengelagert. Gib /itemcache ein, um deine Items zu erhalten."),
    MESSAGE_CACHED_ITEMS_ADDED(Main.LIGHT_ACCENT_COLOR + " Items wurden in dein Inventar gelegt."),
    MESSAGE_CACHED_ITEMS_LEFT(Main.LIGHT_ACCENT_COLOR + " Es sind weitere Items zwischengelagert."),
    MESSAGE_CLOUD_INTERFACE_BOUGHT(Main.LIGHT_ACCENT_COLOR + "Du hast erfolgreich ein Cloud Interface gekauft."),
    MESSAGE_CLOUD_STORAGE_SLOT_BOUGHT(Main.LIGHT_ACCENT_COLOR + "Du hast erfolgreich einen zusätzlichen Cloud Storage Slot gekauft. Der damit verbundene Access Point wurde dein Inventar gelegt."),
    MESSAGE_ENTRIES_DELETED(Main.LIGHT_ACCENT_COLOR + " Einträge wurden erfolgreich gelöscht."),
    MESSAGE_INTERFACE_ITEMS_STORED(Main.LIGHT_ACCENT_COLOR + " Items wurden erfolgreich eingelagert."),
    MESSAGE_INTERFACE_NO_AVAILABLE_SLOTS(Main.LIGHT_ACCENT_COLOR + "Es wurden keine verfügbaren Cloud Storage Slots gefunden."),
    MESSAGE_INTERFACE_NO_ITEMS_STORED(Main.LIGHT_ACCENT_COLOR + "Es wurden keine passenden Items in deinem Inventar gefunden."),
    MESSAGE_NO_ACCESS(Main.LIGHT_ACCENT_COLOR + "Du hast nicht die benötigten Zugriffsrechte."),
    MESSAGE_NO_CACHED_ITEMS(Main.LIGHT_ACCENT_COLOR + "Es wurden keine Items zwischengelagert."),
    MESSAGE_NO_CLOUD_STORAGE_SLOT(Main.LIGHT_ACCENT_COLOR + "Du besitzt keine Cloud Storage Slots."),
    MESSAGE_WIRELESS_CLOUD_INTERFACE_BOUGHT(Main.LIGHT_ACCENT_COLOR + "Du hast erfolgreich ein Wireless Cloud Interface gekauft."),

    PREFIX(Main.DARK_ACCENT_COLOR + "[" + Main.PRIMARY_COLOR + ChatColor.BOLD + "CloudStorage" + Main.DARK_ACCENT_COLOR + "] " + Main.LIGHT_ACCENT_COLOR);

    private final String message;
    public final static DecimalFormat decimalFormat = (DecimalFormat) (DecimalFormat.getInstance(Locale.GERMAN));

    Messages(String message) {
        this.message = message;
    }

    public String get() {
        return Main.DARK_ACCENT_COLOR + "[" + Main.PRIMARY_COLOR + ChatColor.BOLD + "CloudStorage" + Main.DARK_ACCENT_COLOR + "] " + Main.LIGHT_ACCENT_COLOR + message;
    }

    public String getRaw() {
        return message;
    }
}