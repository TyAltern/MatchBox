package me.tyalternative.matchbox.events;

import me.tyalternative.matchbox.role.Role;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RoleAssignedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Role role;

    public RoleAssignedEvent(Player player, Role role) {
        this.player = player;
        this.role = role;
    }

    public Player getPlayer() {
        return player;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
