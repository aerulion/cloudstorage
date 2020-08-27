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

    ERROR_BUYING_CLOUD_STORAGE_SLOT(ChatColor.RED + "Fehler: Die der Cloud Storage Slot konnte nicht gekauft werden. Dein Geld wurde zurückerstattet."),
    ERROR_DELETING_DATA(ChatColor.RED + "Fehler: Die Daten konnten nicht gelöscht werden. Bitte versuche es später erneut."),
    ERROR_LOADING_DATA(ChatColor.RED + "Fehler: Die Daten konnten nicht geladen werden. Bitte versuche es später erneut."),
    ERROR_NO_PERMISSION(ChatColor.RED + "Fehler: Du hast keine Rechte diesen Befehl zu nutzen."),
    ERROR_NO_PLAYER(ChatColor.RED + "Fehler: Dieser Befehl kann nur als Spieler genutzt werden."),
    ERROR_NO_VALID_STORAGE_SETTING(ChatColor.RED + "Fehler: Keine gültige Inventar Einstellung angegeben."),
    ERROR_PLAYER_NOT_FOUND(ChatColor.RED + "Fehler: Dieser Spieler konnte nicht gefunden werden."),
    ERROR_REFUNDING_PLAYER(ChatColor.RED + "Fehler: Der Betrag konnte nicht zurückerstattet werden. Bitte wende dich an den Staff."),
    ERROR_STORING_ITEMS(ChatColor.RED + "Fehler: Die Items konnten nicht eingelagert werden. Deine Items wurden temporär zwischengelagert. Gib /itemcache ein, um deine Items zu erhalten."),
    ERROR_TOGGLE_ACCESS(ChatColor.RED + "Fehler: Die Zugriffseinstellung konnte nicht aktualisiert werden."),
    ERROR_TOGGLE_ACCESS_NO_OWNER(ChatColor.RED + "Fehler: Nur der Besitzer kann diese Einstellung ändern."),
    ERROR_TOO_MANY_ARGUMENTS(ChatColor.RED + "Fehler: Zu viele Argumente."),
    ERROR_UNKNOWN_COMMAND(ChatColor.RED + "Fehler: Unbekannter Befehl oder falsche Argumente."),
    ERROR_WITHDRAWING_ITEMS(ChatColor.RED + "Fehler: Die Items konnten nicht ausgelagert werden. Bitte versuche es später erneut."),
    MESSAGE_CACHED_INVENTORY_FULL(ChatColor.RED + "Dein Inventar ist voll. Deine Items wurden temporär zwischengelagert. Gib /itemcache ein, um deine Items zu erhalten."),
    MESSAGE_CACHED_ITEMS_ADDED(ChatColor.GRAY + " Items wurden in dein Inventar gelegt."),
    MESSAGE_CACHED_ITEMS_LEFT(ChatColor.GRAY + " Es sind weitere Items zwischengelagert."),
    MESSAGE_CLOUD_STORAGE_SLOT_BOUGHT(ChatColor.GRAY + "Du hast erfolgreich einen zusätzlichen Cloud Storage Slot gekauft. Der damit verbundene Access Point wurde dein Inventar gelegt."),
    MESSAGE_ENTRIES_DELETED(ChatColor.GRAY + " Einträge wurden erfolgreich gelöscht."),
    MESSAGE_INTERFACE_ITEMS_STORED(ChatColor.GRAY + " Items wurden erfolgreich eingelagert."),
    MESSAGE_NO_ACCESS(ChatColor.GRAY + "Du hast nicht die benötigten Zugriffsrechte."),
    MESSAGE_NO_CACHED_ITEMS(ChatColor.GRAY + "Es wurden keine Items zwischengelagert."),

    PREFIX(ChatColor.GRAY + "[" + ChatColor.of(Main.PRIMARY_COLOR) + ChatColor.BOLD + "CloudStorage" + ChatColor.GRAY + "] ");

    private final String message;
    public final static DecimalFormat decimalFormat = (DecimalFormat) (DecimalFormat.getInstance(Locale.GERMAN));

    Messages(String message) {
        this.message = message;
    }

    public String get() {
        return ChatColor.GRAY + "[" + ChatColor.of(Main.PRIMARY_COLOR) + ChatColor.BOLD + "CloudStorage" + ChatColor.GRAY + "] " + message;
    }

    public String getRaw() {
        return message;
    }
}