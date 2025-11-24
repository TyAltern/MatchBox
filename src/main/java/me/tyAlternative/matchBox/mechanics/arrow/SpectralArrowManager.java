package me.tyalternative.matchbox.mechanics.arrow;

import me.tyalternative.matchbox.core.GameManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SpectralArrowManager {

    private final GameManager gameManager;
    private final RevealHandler revealHandler;
    private final Set<UUID> revealedPlayers;

    public SpectralArrowManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.revealHandler = new RevealHandler(gameManager);
        this.revealedPlayers = new HashSet<>();
    }

    public void reveal(UUID playerId) {
        if (revealedPlayers.contains(playerId)) return;

        revealedPlayers.add(playerId);
        revealHandler.reveal(playerId);
    }

    public boolean isRevealed(UUID playerId) {
        return revealedPlayers.contains(playerId);
    }

    public void clearAll() {
        revealedPlayers.clear();
    }
}
