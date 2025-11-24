package me.tyalternative.matchbox.phase.impl;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.phase.GamePhase;
import me.tyalternative.matchbox.phase.PhaseContext;
import me.tyalternative.matchbox.phase.PhaseType;
import me.tyalternative.matchbox.player.PlayerData;

public class GameplayPhase implements GamePhase {
    @Override
    public void onStart(GameManager gameManager, PhaseContext context) {

        if (gameManager.getSettings().shouldHideSkins()) {
            gameManager.getAnonymityManager().hideAllSkins();
        }
        if (gameManager.getSettings().shouldHideNametags()) {
            gameManager.getAnonymityManager().hideAllNametags();
        }

        // Détruire les anciens panneaux
        gameManager.getSignManager().clearAll();

        // Notifier les rôles
        for (PlayerData data : gameManager.getPlayerManager().getAlive()) {
            if (data.getPlayer() != null && data.hasRole()) {
                data.getRole().onGameplayPhaseStart(data.getPlayer(), data);
            }
        }

        gameManager.broadcastMessage("§bPhase de Gameplay !");
        gameManager.getSoundManager().playToAll("phase_change");

    }

    @Override
    public void onEnd(GameManager gameManager, PhaseContext context) {

        // Notifier les rôles
        for (PlayerData data : gameManager.getPlayerManager().getAlive()) {
            if (data.getPlayer() != null && data.hasRole()) {
                data.getRole().onGameplayPhaseEnd(data.getPlayer(), data);
            }
        }

        // Traiter les embrasements
        gameManager.getEliminationManager().processEmbrasements();
    }

    @Override
    public PhaseType getType() {
        return PhaseType.GAMEPLAY;
    }

    // TODO: ADD RANDOM DURATION
    @Override
    public long getDuration(GameManager gameManager) {
        return gameManager.getSettings().getGameplayDuration() * 1000L;
    }
}
