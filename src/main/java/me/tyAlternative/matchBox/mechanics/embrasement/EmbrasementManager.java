package me.tyalternative.matchbox.mechanics.embrasement;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;

import java.util.UUID;

public class EmbrasementManager {
    private final GameManager gameManager;
    private final EmbrasementQueue queue;

    public EmbrasementManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.queue = new EmbrasementQueue();
    }

    public boolean embrase(UUID playerId, EmbrasementCause cause) {
        queue.add(playerId, cause);
        return true;
    }

    public boolean isEmbrased(UUID playerId) {
        return queue.contains(playerId);
    }

    public EmbrasementQueue getQueue() {
        return queue;
    }

    public EmbrasementCause getCause(UUID playerId) {
        return queue.getCause(playerId);
    }

    public void removeEmbrasement(UUID playerId) {
        queue.remove(playerId);
    }

    public void removeByCause(EmbrasementCause cause) {
        for (UUID playerId : queue.getAll()) {
            if (queue.getCause(playerId) == cause) {
                queue.remove(playerId);
            }
        }
    }

    public void clearAll() {
        queue.clear();
    }
}
