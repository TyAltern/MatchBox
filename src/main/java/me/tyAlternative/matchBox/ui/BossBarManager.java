package me.tyalternative.matchbox.ui;

import me.tyalternative.matchbox.abilities.Ability;
import me.tyalternative.matchbox.abilities.AbilityContext;
import me.tyalternative.matchbox.abilities.impl.ClairvoyanceAbility;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.phase.PhaseType;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.Role;
import me.tyalternative.matchbox.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Boss;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Gestionnaire de BossBar avec affichage différencié selon les rôles
 */
public class BossBarManager {

    private final GameManager gameManager;
    private final Map<UUID, BossBar> playerBossBars;
    private BukkitTask updateTask;

    public BossBarManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.playerBossBars = new HashMap<>();
    }

    /**
     * Démarre la mise à jour automatique
     */
    public void start() {
        if (!gameManager.getSettings().isBossBarEnabled()) {
            return;
        }

        // Update toutes les secondes
        updateTask = Bukkit.getScheduler().runTaskTimer(
                gameManager.getPlugin(),
                this::update,
                0L,
                20L
        );
    }

    /**
     * Met à jour toutes les BossBars
     */
    public void update() {
        if (!gameManager.getSettings().isBossBarEnabled()) return;
        PhaseType phase = gameManager.getPhaseManager().getCurrentType();

        if (!phase.isInGame()) {
            removeAll();
            return;
        }

        for (PlayerData data : gameManager.getPlayerManager().getAlive()) {
            Player player = data.getPlayer();
            if (player == null) continue;

            updatePlayerBossBar(player, data, phase);
        }
    }

    /**
     * Met à jour la BossBar d'un joueur spécifique
     */
    private void updatePlayerBossBar(Player player, PlayerData data, PhaseType phase) {
        BossBar bossBar = playerBossBars.get(player.getUniqueId());

        if (bossBar == null) {
            bossBar = createBossBar(phase);
            playerBossBars.put(player.getUniqueId(), bossBar);
            bossBar.addPlayer(player);
        }

        // détermine si le joueur peut voir le time
        boolean showTimer = canSeeTimer(data, phase);

        // Construire le titre
        String title = buildTitle(phase, showTimer);

        // Calculer la progression
        float progress = calculateProgress(phase, showTimer);

        bossBar.setTitle(title);
        bossBar.setProgress(progress);

        // Modifie la couleur en fonction de la phase
        changeBossBarColor(player, phase);
    }

    /**
     * Vérifie si un joueur peut voir le timer
     */
    private boolean canSeeTimer(PlayerData data, PhaseType phase) {
        // En phase de vote : tout le monde voit le timer
        if (phase == PhaseType.VOTE) return true;

        // En phase de gameplay : seulement ceux avec Clairvoyance
        if (phase == PhaseType.GAMEPLAY) {
            Role role = data.getRole();
            if (role == null) return false;

            Ability ability = role.getAbility(ClairvoyanceAbility.ID);
            if (ability == null) return false;

            return ability.canUseAbility(data.getPlayer(), data, AbilityContext.noTarget());

        }

        return false;
    }

    /**
     * Construit le titre de la BossBar
     */
    private String buildTitle(PhaseType phase, boolean showTimer) {
        String baseName = phase.getDisplayName();

        if (showTimer) {
            int remaining = gameManager.getPhaseManager().getRemainingSeconds();
            String time = TimeUtil.formatSeconds(remaining);
            return baseName + " §7- §f" + time;
        } else {
            return "§7Phase de " + baseName + "§7 en cours";
        }

    }

    /**
     * Calcule la progression de la barre
     */
    private float calculateProgress(PhaseType phase, boolean showTimer) {
        if (!showTimer) return 1.0f;

        int remaining = gameManager.getPhaseManager().getRemainingSeconds();
        int total = switch (phase) {
            case GAMEPLAY -> gameManager.getSettings().getGameplayDuration();
            case VOTE -> gameManager.getSettings().getVoteDuration();
            case null, default -> remaining;
        };
        return Math.max(0f, Math.min(1f, (float) remaining/total));
    }

    /**
     * Crée une nouvelle BossBar
     */
    private BossBar createBossBar(PhaseType phase) {
        BarColor color;
        try {
            color = switch (phase) {
                case GAMEPLAY -> BarColor.valueOf(gameManager.getSettings().getBossBarColorGameplay());
                case VOTE -> BarColor.valueOf(gameManager.getSettings().getBossBarColorVote());
                case null, default -> BarColor.WHITE;
            };
        } catch (IllegalArgumentException exception) {
            color = BarColor.WHITE;
        }
        return Bukkit.createBossBar("", color, BarStyle.SOLID);
    }

    /**
     * Modifie la couleur de la BossBar
     */
    private void changeBossBarColor(Player player, PhaseType phase) {
        BossBar bossBar = playerBossBars.get(player.getUniqueId());
        if (bossBar == null) return;

        BarColor color;
        try {
            color = switch (phase) {
                case GAMEPLAY -> BarColor.valueOf(gameManager.getSettings().getBossBarColorGameplay());
                case VOTE -> BarColor.valueOf(gameManager.getSettings().getBossBarColorVote());
                case null, default -> BarColor.WHITE;
            };
        } catch (IllegalArgumentException exception) {
            color = BarColor.WHITE;
        }

        bossBar.setColor(color);

    }

    /**
     * Retire la BossBar d'un joueur
     */
    public void removePlayer(UUID playerId) {
        BossBar bossBar = playerBossBars.remove(playerId);
        if (bossBar != null) {
            bossBar.removeAll();
        }
    }

    /**
     * Retire toutes les BossBars
     */
    public void removeAll() {
        playerBossBars.values().forEach(BossBar::removeAll);
        playerBossBars.clear();
    }

    /**
     * Arrête les mises à jour
     */
    public void stop() {
        if (updateTask != null && !updateTask.isCancelled()) {
            updateTask.cancel();
        }
        removeAll();
    }

}
