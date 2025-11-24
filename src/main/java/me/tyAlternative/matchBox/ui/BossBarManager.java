package me.tyalternative.matchbox.ui;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.phase.PhaseType;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarManager {

    private final GameManager gameManager;
    private BossBar bossBar;

    public BossBarManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void update() {
        if (!gameManager.getSettings().isBossBarEnabled()) return;

        PhaseType phase = gameManager.getCurrentPhase();
        if (phase == PhaseType.LOBBY || phase == PhaseType.END) {
            remove();
            return;
        }

        if (bossBar == null) create();

        // Mettre à jour le texte et la progression
        int remaining = gameManager.getPhaseManager().getRemainingSeconds();
        int total = phase == PhaseType.GAMEPLAY ?
                gameManager.getSettings().getGameplayDuration() :
                gameManager.getSettings().getVoteDuration();

        float progress = Math.max(0f, Math.min(1f, (float) remaining / total));
        String title = phase.getDisplayName() + " §7- §f" + formatTime(remaining);

        bossBar.setTitle(title);
        bossBar.setProgress(progress);
    }

    public void create() {
        PhaseType phase = gameManager.getCurrentPhase();
        BarColor color = phase == PhaseType.GAMEPLAY ? BarColor.BLUE : BarColor.PINK;

        bossBar = Bukkit.createBossBar("", color, BarStyle.SOLID);

        for (Player player : gameManager.getPlayerManager().getAlivePlayer()) {
            bossBar.addPlayer(player);
        }
    }

    public void remove() {
        if (bossBar != null) {
            bossBar.removeAll();
            bossBar = null;
        }
    }

    public void removeAll() {
        remove();
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
}
