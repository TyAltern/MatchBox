package me.tyalternative.matchbox.phase.impl;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.phase.GamePhase;
import me.tyalternative.matchbox.phase.PhaseContext;
import me.tyalternative.matchbox.phase.PhaseType;

public class EndPhase implements GamePhase{
    @Override
    public void onStart(GameManager gameManager, PhaseContext context) {
        // Afficher écran de fin
        // TODO: Implémenter titre/credits
    }

    @Override
    public void onEnd(GameManager gameManager, PhaseContext context) {
        // Cleanup final
    }

    @Override
    public PhaseType getType() {
        return PhaseType.END;
    }

    @Override
    public long getDuration(GameManager gameManager) {
        return 10 * 1000L;
    }
}
