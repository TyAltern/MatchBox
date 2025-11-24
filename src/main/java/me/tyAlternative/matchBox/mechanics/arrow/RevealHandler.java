package me.tyalternative.matchbox.mechanics.arrow;

import me.tyalternative.matchbox.core.GameManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RevealHandler {
    private final GameManager gameManager;

    public RevealHandler(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void reveal(UUID playerId) {
        Player target = gameManager.getPlayerManager().get(playerId).getPlayer();
        if (target == null) return;

        // Afficher le vrai non à tous
        gameManager.getAnonymityManager().showToAllNametag(target, target.getName());
    }
}
