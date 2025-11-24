package me.tyalternative.matchbox.events;

import me.tyalternative.matchbox.phase.PhaseType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PhaseChangeEvent extends Event{

    private static final HandlerList HANDLERS = new HandlerList();
    private final PhaseType oldPhase;
    private final PhaseType newPhase;

    public PhaseChangeEvent(PhaseType oldPhase, PhaseType newPhase) {
        this.oldPhase = oldPhase;
        this.newPhase = newPhase;
    }

    public PhaseType getOldPhase() {
        return oldPhase;
    }

    public PhaseType getNewPhase() {
        return newPhase;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
