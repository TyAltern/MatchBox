package me.tyalternative.matchbox.phase;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.events.PhaseChangeEvent;
import me.tyalternative.matchbox.phase.GamePhase;
import me.tyalternative.matchbox.phase.PhaseContext;
import me.tyalternative.matchbox.phase.PhaseType;
import me.tyalternative.matchbox.phase.impl.EndPhase;
import me.tyalternative.matchbox.phase.impl.GameplayPhase;
import me.tyalternative.matchbox.phase.impl.LobbyPhase;
import me.tyalternative.matchbox.phase.impl.VotePhase;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire des phases de jeu
 */
public class PhaseManager {

    private final GameManager gameManager;
    private final Map<PhaseType, GamePhase> phases;

    private GamePhase currentPhase;
    private PhaseContext context;
    private BukkitTask phaseTask;

    public PhaseManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.phases = new HashMap<>();

        // Enregistrer les phases
        registerPhase(new LobbyPhase());
        registerPhase(new GameplayPhase());
        registerPhase(new VotePhase());
        registerPhase(new EndPhase());

        // Phase initiale
        this.currentPhase = phases.get(PhaseType.LOBBY);

    }

    private void registerPhase(GamePhase phase) {
        phases.put(phase.getType(), phase);
    }

    /**
     * Démarre une phase
     */
    public void startPhase(PhaseType type) {
        startPhase(type, context != null ? context.getRoundNumber() : 1);
    }

    public void startPhase(PhaseType type, int roundNumber) {
        // Arrêter la phase actuelle
        if (currentPhase != null && phaseTask != null) {
            phaseTask.cancel();
            if (context != null) {
                currentPhase.onEnd(gameManager, context);
            }
        }

        // Changer de phase
        PhaseType oldType = currentPhase != null ? currentPhase.getType() : PhaseType.LOBBY;
        currentPhase = phases.get(type);

        if (currentPhase == null) {
            gameManager.getPlugin().getLogger().severe("Phase invalide: " + type);
            return;
        }

        // Créer le contexte
        context = new PhaseContext(roundNumber);
        context.setPhaseStartTime(System.currentTimeMillis());
        context.setPhaseDuration(currentPhase.getDuration(gameManager));

        // Démarrer la phase
        currentPhase.onStart(gameManager, context);

        // Événements
        gameManager.getEventBus().call(new PhaseChangeEvent(oldType, type));

        // Programmer la fin

        gameManager.debugMessage("time set: " + context.getPhaseDuration() / 1000.0);
        if (type.isInGame()) {
            long durationTicks = context.getPhaseDuration() / 50;
            phaseTask = Bukkit.getScheduler().runTaskLater(
                    gameManager.getPlugin(),
                    this::endCurrentPhase,
                    durationTicks
            );
        }

    }


    /**
     * Termine la phase actuelle
     */
    public void endCurrentPhase() {
        if (currentPhase == null) return;

        currentPhase.onEnd(gameManager, context);

        // Déterminer la prochaine phase
        PhaseType nextPhase = determineNextPhase();

        if (nextPhase != null) {
            startPhase(nextPhase, context.getRoundNumber());
        }
    }


    /**
     * Détermine la prochaine phase selon la logique du jeu
     */
    private PhaseType determineNextPhase() {
        PhaseType current = currentPhase.getType();

        switch (current) {
            case GAMEPLAY:
                // Après Gameplay: Vérifier victoire puis vote
                if (gameManager.getVictoryManager().checkVictory()) {
                    gameManager.stopGame("Win");
                    return PhaseType.END;
                }
                return PhaseType.VOTE;

            case VOTE:
                // Après Vote: Vérifier victoire puis Gameplay
                if (gameManager.getVictoryManager().checkVictory()) {
                    gameManager.stopGame("Win");
                    return PhaseType.END;
                }
                // Nouvelle manche
                gameManager.nextRound();
                return PhaseType.GAMEPLAY;

            case END:
                return PhaseType.LOBBY; // Fin de partie

            default:
                return null;
        }

    }

    /**
     * Force le passage à la phase suivante
     */
    public void skipPhase() {
        if (phaseTask != null) {
            phaseTask.cancel();
        }
        endCurrentPhase();
    }


    /**
     * Arrête toutes les phases
     */
    public void stop() {
        if (phaseTask != null) {
            phaseTask.cancel();
        }
        currentPhase = phases.get(PhaseType.LOBBY);
        context = null;

    }

    // Getters

    public PhaseType getCurrentType() {
        return currentPhase != null ? currentPhase.getType() : PhaseType.LOBBY;
    }

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public PhaseContext getContext() {
        return context;
    }

    public long getRemainingTime() {
        return context != null ? context.getRemainingTime() : 0;
    }

    public int getRemainingSeconds() {
        return context != null ? context.getRemainingSeconds() : 0;
    }
}
