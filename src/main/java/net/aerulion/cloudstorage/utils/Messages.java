package net.aerulion.cloudstorage.utils;

import net.aerulion.cloudstorage.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.text.DecimalFormat;
import java.util.Locale;

public enum Messages {
    CONSOLE_DISABLING(Component.text("Deaktiviere Plugin...").color(NamedTextColor.GREEN)),
    CONSOLE_ENABLING(Component.text("Aktiviere Plugin...").color(NamedTextColor.GREEN)),
    CONSOLE_PLUGIN_DISABLED(Component.text("Das Plugin wurde deaktiviert.").color(NamedTextColor.GREEN)),
    CONSOLE_PLUGIN_ENABLED(Component.text("Das Plugin wurde aktiviert.").color(NamedTextColor.GREEN)),
    CONSOLE_VAULT_NOT_FOUND(Component.text("Vault konnte nicht gefunden werden, deaktiviere Plugin...").color(NamedTextColor.RED)),
    CONSOLE_WORLD_GUARD_NOT_FOUND(Component.text("WorldGuard konnte nicht gefunden werden, deaktiviere Plugin...").color(NamedTextColor.RED)),

    DIVIDER(Component.text("%divider").color(Main.LIGHT_ACCENT_COLOR)),

    ERROR_BUYING_CLOUD_EXPERIENCE_TERMINAL(Component.text("Fehler: Das Cloud Experience Terminal konnte nicht gekauft werden. Dein Geld wurde zurückerstattet.").color(Main.ERROR_COLOR)),
    ERROR_BUYING_CLOUD_STORAGE_SLOT(Component.text("Fehler: Der Cloud Storage Slot konnte nicht gekauft werden. Dein Geld wurde zurückerstattet.").color(Main.ERROR_COLOR)),
    ERROR_BUYING_UPGRADE(Component.text("Fehler: Das Upgrade konnte nicht gekauft werden. Dein Geld wurde zurückerstattet.").color(Main.ERROR_COLOR)),
    ERROR_DELETING_DATA(Component.text("Fehler: Die Daten konnten nicht gelöscht werden. Bitte versuche es später erneut.").color(Main.ERROR_COLOR)),
    ERROR_DISABLED_WORLD(Component.text("Fehler: Du kannst diese Funktion nicht in dieser Welt nutzen.").color(Main.ERROR_COLOR)),
    ERROR_GAME_MODE(Component.text("Fehler: Du kannst diese Funktion nur im Survival Modus nutzen.").color(Main.ERROR_COLOR)),
    ERROR_LOADING_DATA(Component.text("Fehler: Die Daten konnten nicht geladen werden. Bitte versuche es später erneut.").color(Main.ERROR_COLOR)),
    ERROR_MAINTENANCE_MODE(Component.text("Das CloudStorage System befindet sich zur Zeit im Wartungsmodus. Bitte versuche es zu einem späteren Zeitpunkt erneut.").color(Main.ERROR_COLOR)),
    ERROR_NOT_ENOUGH_MONEY(Component.text("Fehler: Du hast nicht genügend Geld.").color(Main.ERROR_COLOR)),
    ERROR_NO_PERMISSION(Component.text("Fehler: Du hast keine Rechte diesen Befehl zu nutzen.").color(Main.ERROR_COLOR)),
    ERROR_NO_PERMISSION_BUY(Component.text("Fehler: Du hast keine Rechte dies zu kaufen.").color(Main.ERROR_COLOR)),
    ERROR_NO_PLAYER(Component.text("Fehler: Dieser Befehl kann nur als Spieler genutzt werden.").color(Main.ERROR_COLOR)),
    ERROR_NO_VALID_NUMBER(Component.text("Fehler: Keine gültige Zahl angegeben.").color(Main.ERROR_COLOR)),
    ERROR_NO_VALID_STORAGE_SETTING(Component.text("Fehler: Keine gültige Inventar Einstellung angegeben.").color(Main.ERROR_COLOR)),
    ERROR_ONLY_OWNER(Component.text("Fehler: Dies kann nur vom Eigentümer durchgeführt werden.").color(Main.ERROR_COLOR)),
    ERROR_PLAYER_NOT_FOUND(Component.text("Fehler: Dieser Spieler konnte nicht gefunden werden.").color(Main.ERROR_COLOR)),
    ERROR_REFUNDING_PLAYER(Component.text("Fehler: Der Betrag konnte nicht zurückerstattet werden. Bitte wende dich an den Staff.").color(Main.ERROR_COLOR)),
    ERROR_STORING_EXPERIENCE(Component.text("Fehler: Die Erfahrungspunkte konnten nicht eingelagert werden und wurden zurückerstattet.").color(Main.ERROR_COLOR)),
    ERROR_STORING_ITEMS(Component.text("Fehler: Die Item konnten nicht eingelagert werden. Deine Item wurden temporär zwischengelagert. Gib '/cloud itemcache' ein, um deine Item zu erhalten.").color(Main.ERROR_COLOR)),
    ERROR_TOGGLE_ACCESS(Component.text("Fehler: Die Zugriffseinstellung konnte nicht aktualisiert werden.").color(Main.ERROR_COLOR)),
    ERROR_TOGGLE_ACCESS_NO_OWNER(Component.text("Fehler: Nur der Eigentümer kann diese Einstellung ändern.").color(Main.ERROR_COLOR)),
    ERROR_TRANSACTION(Component.text("Fehler: Die Transaktion konnte nicht ausgeführt werden.").color(Main.ERROR_COLOR)),
    ERROR_UNKNOWN_COMMAND(Component.text("Fehler: Unbekannter Befehl oder falsche Argumente.").color(Main.ERROR_COLOR)),
    ERROR_WITHDRAWING_EXPERIENCE(Component.text("Fehler: Die Erfahrungspunkte konnten nicht ausgelagert werden. Bitte versuche es später erneut.").color(Main.ERROR_COLOR)),
    ERROR_WITHDRAWING_ITEMS(Component.text("Fehler: Die Item konnten nicht ausgelagert werden. Bitte versuche es später erneut.").color(Main.ERROR_COLOR)),

    MESSAGE_CACHED_INVENTORY_FULL(Component.text("Dein Inventar ist voll. Deine Item wurden temporär zwischengelagert. Gib '/cloud itemcache' ein, um deine Item zu erhalten.").color(Main.ERROR_COLOR)),
    MESSAGE_CACHED_ITEMS_ADDED(Component.text("%amount% Item wurden in dein Inventar gelegt.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_CACHED_ITEMS_LEFT(Component.text(" Es sind weitere Item zwischengelagert.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_CLOUD_ACCESS_POINT_BOUGHT(Component.text("Du hast erfolgreich einen Cloud Access Point gekauft.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_CLOUD_EXPERIENCE_TERMINAL_BOUGHT(Component.text("Du hast erfolgreich ein Cloud Experience Terminal gekauft.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_CLOUD_INTERFACE_BOUGHT(Component.text("Du hast erfolgreich ein Cloud Interface gekauft.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_CLOUD_STORAGE_SLOT_BOUGHT(Component.text("Du hast erfolgreich einen zusätzlichen Cloud Storage Slot gekauft. Der damit verbundene Access Point wurde in dein Inventar gelegt.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_ENTRIES_DELETED(Component.text("%amount% Einträge wurden erfolgreich gelöscht.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_ENTRIES_UPDATED(Component.text("%amount% Einträge wurden erfolgreich aktualisiert.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_INTERFACE_ITEMS_STORED(Component.text("%itemAmount%").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD).append(Component.text(" Item und ").color(Main.LIGHT_ACCENT_COLOR).decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)).append(Component.text("%expAmount%").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).append(Component.text(" Erfahrungspunkte wurden erfolgreich eingelagert.").color(Main.LIGHT_ACCENT_COLOR).decoration(TextDecoration.BOLD, TextDecoration.State.FALSE))),
    MESSAGE_INTERFACE_NOTHING_STORED(Component.text("Es wurden keine Item oder Erfahrungspunkte aus deinem Inventar eingelagert.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_INTERFACE_NO_AVAILABLE_EXPERIENCE_TERMINAL(Component.text("Es wurde kein verfügbares Cloud Experience Terminal gefunden.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_INTERFACE_NO_AVAILABLE_SLOTS(Component.text("Es wurden keine verfügbaren Cloud Storage Slots gefunden.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_ITEM_ALREADY_STORED(Component.text("Dieses Item ist bereits in einem anderen Storage Slot gelagert.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_MAINTENANCE_MODE_DISABLED(Component.text("Der Wartungsmodus wurde deaktiviert.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_MAINTENANCE_MODE_ENABLED(Component.text("Der Wartungsmodus wurde aktiviert.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_NO_ACCESS(Component.text("Du hast nicht die benötigten Zugriffsrechte.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_NO_CACHED_ITEMS(Component.text("Es wurden keine Item zwischengelagert.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_NO_CLOUD_STORAGE_SLOT(Component.text("Du besitzt keine Cloud Storage Slots.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_NO_GRINDSTONE(Component.text("Du kannst dieses Item nicht in einem Schleifstein benutzen.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_RELOADED(Component.text("Die Config Datei wurde neu geladen.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_WIRELESS_CLOUD_ACCESS_POINT_BOUGHT(Component.text("Du hast erfolgreich einen Wireless Cloud Access Point gekauft.").color(Main.LIGHT_ACCENT_COLOR)),
    MESSAGE_WIRELESS_CLOUD_INTERFACE_BOUGHT(Component.text("Du hast erfolgreich ein Wireless Cloud Interface gekauft.").color(Main.LIGHT_ACCENT_COLOR)),

    PREFIX(Component.text("[").color(Main.DARK_ACCENT_COLOR).append(Component.text("CloudStorage").color(Main.PRIMARY_COLOR).decorate(TextDecoration.BOLD)).append(Component.text("] ").color(Main.DARK_ACCENT_COLOR)));

    private final Component message;
    public final static DecimalFormat decimalFormat = (DecimalFormat) (DecimalFormat.getInstance(Locale.GERMAN));

    Messages(Component message) {
        this.message = message;
    }

    public Component get() {
        return PREFIX.getRaw().append(message);
    }

    public Component getRaw() {
        return message;
    }
}