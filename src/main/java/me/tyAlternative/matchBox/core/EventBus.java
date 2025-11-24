package me.tyalternative.matchbox.core;

import me.tyalternative.matchbox.MatchBox;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

/**
 * Système d'événements custom simplifié
 */
public class EventBus {

    private final MatchBox plugin;

    public EventBus(MatchBox plugin) {
        this.plugin = plugin;
    }

    /**
     * Appelle un événement custom
     */
    public <T extends Event> T call(T event) {
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }

    /**
     * Appelle un événement de manière asynchrone
     */
    public <T extends Event> void callAsync(T event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Bukkit.getPluginManager().callEvent(event);
        });
    }
}
