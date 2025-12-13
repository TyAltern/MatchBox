package me.tyalternative.matchbox.core;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.abilities.AbilityUsageManager;
import me.tyalternative.matchbox.abilities.CooldownManager;
import me.tyalternative.matchbox.composition.CompositionManager;
import me.tyalternative.matchbox.config.GameSettings;
import me.tyalternative.matchbox.elimination.EliminationManager;
import me.tyalternative.matchbox.events.GameEndEvent;
import me.tyalternative.matchbox.events.GameStartEvent;
import me.tyalternative.matchbox.listeners.DoubleSwapDetector;
import me.tyalternative.matchbox.mechanics.anonymity.AnonymityManager;
import me.tyalternative.matchbox.mechanics.arrow.SpectralArrowManager;
import me.tyalternative.matchbox.mechanics.embrasement.EmbrasementManager;
import me.tyalternative.matchbox.mechanics.glowing.GlowingManager;
import me.tyalternative.matchbox.mechanics.protection.ProtectionManager;
import me.tyalternative.matchbox.mechanics.sign.SignManager;
import me.tyalternative.matchbox.mechanics.vote.VoteManager;
import me.tyalternative.matchbox.phase.PhaseManager;
import me.tyalternative.matchbox.phase.PhaseType;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.player.PlayerManager;
import me.tyalternative.matchbox.player.PlayerState;
import me.tyalternative.matchbox.role.Role;
import me.tyalternative.matchbox.ui.ActionBarManager;
import me.tyalternative.matchbox.ui.BossBarManager;
import me.tyalternative.matchbox.ui.SoundManager;
import me.tyalternative.matchbox.victory.VictoryManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Gestionnaire principal du jeu
 * Orchestre tous les sous-systèmes
 */
public class GameManager {

    private final MatchBox plugin;
    private final EventBus eventBus;

    // Managers principaux
    private final PlayerManager playerManager;
    private final PhaseManager phaseManager;
    private final CompositionManager compositionManager;

    // Mécaniques
    private final EmbrasementManager embrasementManager;
    private final ProtectionManager protectionManager;
    private final VoteManager voteManager;
    private final SpectralArrowManager arrowManager;
    private final SignManager signManager;
    private final AnonymityManager anonymityManager;
    private final GlowingManager glowingManager;
    private final CooldownManager cooldownManager;
    private final AbilityUsageManager abilityUsageManager;
    private final DoubleSwapDetector doubleSwapDetector;

    // Systèmes
    private final EliminationManager eliminationManager;
    private final VictoryManager victoryManager;

    // UI
    private final BossBarManager bossBarManager;
    private final ActionBarManager actionBarManager;
    private final SoundManager soundManager;

    // État
    private boolean gameRunning;
    private int roundNumber;

    public GameManager(MatchBox plugin) {
        this.plugin = plugin;
        this.eventBus = new EventBus(plugin);

        // Init managers
        this.playerManager = new PlayerManager(plugin);
        this.compositionManager = new CompositionManager(plugin);
        this.phaseManager = new PhaseManager(this);

        // Init mécaniques
        this.embrasementManager = new EmbrasementManager(this);
        this.protectionManager = new ProtectionManager(this);
        this.voteManager = new VoteManager(this);
        this.arrowManager = new SpectralArrowManager(this);
        this.signManager = new SignManager(this);
        this.anonymityManager = new AnonymityManager(this);
        this.glowingManager = new GlowingManager();
        this.cooldownManager = new CooldownManager();
        this.abilityUsageManager = new AbilityUsageManager(this);
        this.doubleSwapDetector = new DoubleSwapDetector(this);

        // Init systèmes
        this.eliminationManager = new EliminationManager(this);
        this.victoryManager = new VictoryManager(this);

        // Init UI
        this.bossBarManager = new BossBarManager(this);
        this.actionBarManager = new ActionBarManager(this);
        this.soundManager = new SoundManager(this);

        this.gameRunning = false;
        this.roundNumber = 0;
    }

    // ========== CONTRÔLE DE LA PARTIE ==========
    public boolean startGame() {
        if (gameRunning) {
            plugin.getLogger().warning("Une partie est déjà en cours !");
            return false;
        }


        // Vérifier la composition
        Map<Role, Integer> composition = compositionManager.getComposition();
        if (composition.isEmpty()) {
            plugin.getLogger().warning("Aucune composition définie !");
            return false;
        }

        // Vérifier les joueurs
        List<Player> players = List.copyOf(Bukkit.getOnlinePlayers());
        if (players.isEmpty()) {
            plugin.getLogger().warning("Aucun joueur en ligne !");
            return false;
        }

        int totalRoles = composition.values().stream().mapToInt(Integer::intValue).sum();
        if (players.size() != totalRoles) {
            plugin.getLogger().warning(String.format(
                    "Nombre de joueurs incorrect ! Attendu: %d, Actuel: %d",
                    totalRoles, players.size()
            ));
            return false;
        }

        plugin.getLogger().info("Démarrage de la partie avec " + players.size() + " joueurs...");

        // Initialisation des joueurs
        for (Player player : players) {
            PlayerData data = playerManager.getOrCreate(player);
            data.setState(PlayerState.PLAYING);
            data.setSpectralArrowsRemaining(getSettings().getDefaultSpectralArrows());
        }

        // Distribuer les rôles
        if (!distributeRoles(players, composition)) {
            plugin.getLogger().severe("Échec de la distribution des rôles !");
            return false;
        }

        gameRunning = true;
        roundNumber = 1;

        // Événements
        eventBus.call(new GameStartEvent(players.size()));

        // Démarrage de la première phase
        phaseManager.startPhase(PhaseType.GAMEPLAY, 1);

        // UI
        actionBarManager.start();

        broadcastMessage("§7La partie commence !");
        soundManager.playToAll("phase_change");

        getBossBarManager().start();

        return true;
    }

    public void stopGame(String reason) {
        if (!gameRunning) return;

        if (!reason.isEmpty()) plugin.getLogger().info("Arrêt de la partie: " + reason);

        gameRunning = false;
        // Arrêter BossBarManager
        bossBarManager.stop();

        // Arrêter phaseManger
        phaseManager.stop();


        // Événements
        eventBus.call(new GameEndEvent(null, reason));

        // Nettoyer
        cleanup();

        broadcastMessage("§7Partie terminée: " + reason);
        soundManager.playToAll("phase_change");
    }

    public void forceStopGame() {
        if (gameRunning) {
            stopGame("Plugin désactivé");
        }
    }



    // ========== DISTRIBUTION DES RÔLES ==========

    private boolean distributeRoles(List<Player> players, Map<Role, Integer> composition) {
        List<Role> rolesToAssign = compositionManager.createRoleList(composition);

        if (rolesToAssign.size() != players.size()) return false;

        // Shuffle

        Collections.shuffle(rolesToAssign);

        // Assigner
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Role role = rolesToAssign.get(i);

            PlayerData data = playerManager.get(player);
            if (data == null) continue;

            data.setRole(role);
            role.onAssigned(player, data);

            plugin.getLogger().info("→ " + player.getName() + " = " + role.getDisplayName());

        }

        return true;
    }

    // ========== GESTION DES MANCHES ==========

    public void nextRound() {
        roundNumber++;

        plugin.getLogger().info("Début de la manche " + roundNumber);

        // Reset manche
        playerManager.resetAllForNewRound();
        embrasementManager.clearAll();
        protectionManager.clearAll();
        voteManager.clearAll();


        // Nouvelle phase sera démarrée par PhaseManager

    }


    // ========== NETTOYAGE ==========

    private void cleanup() {
        // Remettre les joueurs en survie
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SURVIVAL);

        }

        // Nettoyer managers
        playerManager.clear();
        embrasementManager.clearAll();
        protectionManager.clearAll();
        voteManager.clearAll();
        signManager.clearAll();
//        anonymityManager.restoreAll();

        // UI
        bossBarManager.removeAll();
        actionBarManager.stopAll();

        roundNumber = 0;
    }



    // ========== MESSAGES ==========

    public void broadcastMessage(String message) {
        String prefix = getSettings().getPrefix();
        Bukkit.broadcast(Component.text(prefix + " " + message));
    }

    public void broadcastToAlive(String message) {
        String prefix = getSettings().getPrefix();
        for (Player player : playerManager.getAlivePlayer()) {
            player.sendMessage(Component.text(prefix + " " + message));
        }
    }

    public void debugMessage(String message) {
        if (getSettings().isDebug()) {
            MatchBox.getInstance().getLogger().info(message);
        }
    }










    // ========== GETTERS ==========

    public MatchBox getPlugin() { return plugin; }
    public EventBus getEventBus() { return eventBus; }
    public GameSettings getSettings() { return plugin.getConfigManager().getSettings(); }

    public PlayerManager getPlayerManager() { return playerManager; }
    public PhaseManager getPhaseManager() { return phaseManager; }
    public CompositionManager getCompositionManager() { return compositionManager; }

    public EmbrasementManager getEmbrasementManager() { return embrasementManager; }
    public ProtectionManager getProtectionManager() { return protectionManager; }
    public VoteManager getVoteManager() { return voteManager; }
    public SpectralArrowManager getArrowManager() { return arrowManager; }
    public SignManager getSignManager() { return signManager; }
    public AnonymityManager getAnonymityManager() { return anonymityManager; }
    public GlowingManager getGlowingManager() { return glowingManager; }
    public CooldownManager getCooldownManager() { return cooldownManager; }
    public AbilityUsageManager getAbilityUsageManager() { return abilityUsageManager; }
    public DoubleSwapDetector getDoubleSwapDetector() { return doubleSwapDetector; }

    public EliminationManager getEliminationManager() { return eliminationManager; }
    public VictoryManager getVictoryManager() { return victoryManager; }

    public BossBarManager getBossBarManager() { return bossBarManager; }
    public ActionBarManager getActionBarManager() { return actionBarManager; }
    public SoundManager getSoundManager() { return soundManager; }

    public boolean isGameRunning() { return gameRunning; }
    public int getRoundNumber() { return roundNumber; }
    public PhaseType getCurrentPhase() { return phaseManager.getCurrentType(); }
}
