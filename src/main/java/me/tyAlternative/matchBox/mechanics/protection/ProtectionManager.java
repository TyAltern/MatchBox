package me.tyalternative.matchbox.mechanics.protection;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.mechanics.embrasement.EmbrasementCause;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProtectionManager {
    private final GameManager gameManager;
    private final Map<UUID, ProtectionType> protectedPlayers;

    public ProtectionManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.protectedPlayers = new HashMap<>();
    }

    public boolean protect(UUID playerId, ProtectionType type) {
        if (isProtected(playerId)) {
            return false;
        }
        protectedPlayers.put(playerId, type);
        return true;
    }

    public boolean isProtected(UUID playerId) {
        return protectedPlayers.containsKey(playerId);
    }

    public ProtectionType getType(UUID playerId) {
        return protectedPlayers.get(playerId);
    }

    public void removeProtection(UUID playerId) {
        protectedPlayers.remove(playerId);
    }

    public void removeByType(ProtectionType type) {
        for (UUID playerId : protectedPlayers.keySet()) {
            if (protectedPlayers.get(playerId) == type) {
                protectedPlayers.remove(playerId);
            }
        }
    }

    public void clearAll() {
        protectedPlayers.clear();
    }

}
