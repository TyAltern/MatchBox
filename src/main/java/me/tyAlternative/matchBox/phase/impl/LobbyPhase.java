package me.tyalternative.matchbox.phase.impl;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.phase.GamePhase;
import me.tyalternative.matchbox.phase.PhaseContext;
import me.tyalternative.matchbox.phase.PhaseType;

public class LobbyPhase implements GamePhase {

    @Override
    public void onStart(GameManager gm, PhaseContext ctx) {
        // Rien de spécial en lobby
    }

    @Override
    public void onEnd(GameManager gm, PhaseContext ctx) {
        // Nettoyage avant de commencer
    }

    @Override
    public PhaseType getType() {
        return PhaseType.LOBBY;
    }

    @Override
    public long getDuration(GameManager gm) {
        return Long.MAX_VALUE; // Infini
    }
}
