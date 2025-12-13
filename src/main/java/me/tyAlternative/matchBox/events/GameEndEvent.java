package me.tyalternative.matchbox.events;

import me.tyalternative.matchbox.role.RoleTeam;
import me.tyalternative.matchbox.role.RoleType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameEndEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final RoleTeam winner;
    private final String reason;

    public GameEndEvent(RoleTeam winner, String reason) {
        this.winner = winner;
        this.reason = reason;
    }

    public RoleTeam getWinner() {
        return winner;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
