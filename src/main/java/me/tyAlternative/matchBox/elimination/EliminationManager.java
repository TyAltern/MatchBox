package me.tyalternative.matchbox.elimination;

import me.tyalternative.matchbox.abilities.Ability;
import me.tyalternative.matchbox.abilities.AbilityContext;
import me.tyalternative.matchbox.abilities.AbilityResult;
import me.tyalternative.matchbox.abilities.impl.CalcineAbility;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.events.PlayerEliminatedEvent;
import me.tyalternative.matchbox.mechanics.embrasement.EmbrasementCause;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.player.PlayerState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class EliminationManager {

    private final GameManager gameManager;

    public EliminationManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Traite les embrasements de fin de phase Gameplay
     */
    public void processEmbrasements() {
        Map<UUID, EmbrasementCause> embrased = gameManager.getEmbrasementManager().getQueue().getAllWithCauses();


        for (PlayerData data : gameManager.getPlayerManager().getAlive()) {

            if (!data.hasRole() && !data.isAlive()) continue;

            // Traiter les Calcinés en attente

            Ability ability = data.getRole().getAbility(CalcineAbility.ID);
            if (ability instanceof CalcineAbility calcineAbility && calcineAbility.wasRegistered(data)) {
                EliminationCause cause = calcineAbility.getCause();
                if (cause == null) cause = EliminationCause.UNKNOWN;

                eliminate(data.getPlayerId(), cause);
                calcineAbility.setUsed(false);
                continue;
            }


            if (!embrased.containsKey(data.getPlayerId())) continue;
            if (gameManager.getProtectionManager().isProtected(data.getPlayerId())) continue;

            EliminationCause cause = getEliminationCause(embrased.get(data.getPlayerId()));

            // Process des abilities

            // Retarde les Calcinés embrasés
            ability = data.getRole().getAbility(CalcineAbility.ID);
            if (ability instanceof CalcineAbility calcineAbility) {
                boolean registered = calcineAbility.registerEmbrasement(data, cause);
                if (registered) continue;

            }


            eliminate(data.getPlayerId(), cause);
        }

    }

    /**
     * Traite le vote de fin de phase Vote
     */
    public void processVote() {
        UUID eliminated = gameManager.getVoteManager().calculateResults();

        if (eliminated != null) {
            eliminate(eliminated, EliminationCause.VOTE);
        } else {
            gameManager.broadcastMessage("§7Aucun joueur éliminé au vote.");
        }
    }


    /**
     * Élimine un joueur
     */
    public void eliminate(UUID playerId, EliminationCause cause) {
        PlayerData data = gameManager.getPlayerManager().get(playerId);
        if (data == null || !data.isAlive()) return;

        Player player = data.getPlayer();
        if(player == null) return;

        // Hook du rôle
        if (data.hasRole()) {
            data.getRole().onEliminated(player, data, cause.getDisplayName());
        }

        // Notifier les autres rôles
        for (PlayerData otherData : gameManager.getPlayerManager().getAlive()) {
            if (otherData.getPlayer() != null && otherData.hasRole()) {
                otherData.getRole().onOtherEliminated(otherData.getPlayer(), player, cause.getDisplayName());
            }
        }

        // Événement
        gameManager.getEventBus().call(new PlayerEliminatedEvent(player, cause));

        // Changer l'état
        data.setState(PlayerState.DEAD);
        player.setGameMode(GameMode.SPECTATOR);
        gameManager.getBossBarManager().removePlayer(playerId);

        // Message
        String roleName = data.hasRole() ? data.getRole().getDisplayName() : "Inconnu";
        String deathCause = switch (cause) {
            case VOTE, DISCONNECT -> "§8(" + cause.getDisplayName() + ")";
            case null, default -> "";
        };
        if (gameManager.getSettings().shouldRevealRoleOnDeath()) {
            gameManager.broadcastMessage("§e" + player.getName() + " §7(" + roleName + "§7) a été éliminé ! " + deathCause);
        } else {
            gameManager.broadcastMessage("§e" + player.getName() + " §7a été éliminé ! " + deathCause);
        }

        gameManager.getSoundManager().playToAll("elimination");

    }

    public EliminationCause getEliminationCause(EmbrasementCause cause) {
        return switch (cause) {
            case ETINCELLE -> EliminationCause.EMBRASEMENT_ETINCELLE;
            case TORCHE -> EliminationCause.EMBRASEMENT_TORCHE;
            case null, default -> EliminationCause.DISCONNECT;
        };
    }
}
