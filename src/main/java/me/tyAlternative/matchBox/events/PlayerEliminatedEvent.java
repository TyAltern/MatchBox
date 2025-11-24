package me.tyalternative.matchbox.events;

import me.tyalternative.matchbox.elimination.EliminationCause;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerEliminatedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final EliminationCause cause;

    public PlayerEliminatedEvent(Player player, EliminationCause cause) {
        this.player = player;
        this.cause = cause;
    }

    public Player getPlayer() {
        return player;
    }

    public EliminationCause getCause() {
        return cause;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
