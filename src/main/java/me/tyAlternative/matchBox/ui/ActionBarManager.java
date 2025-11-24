package me.tyalternative.matchbox.ui;

import me.tyalternative.matchbox.core.GameManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class ActionBarManager {
    private final GameManager gameManager;
    private BukkitTask updateTask;

    public ActionBarManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void start() {
        if (!gameManager.getSettings().isActionBarEnabled()) return;

        updateTask = Bukkit.getScheduler().runTaskTimer(
                gameManager.getPlugin(),
                this::update,
                0L,
                20L
        );
    }

    private void update() {
        for (Player player : gameManager.getPlayerManager().getAlivePlayer()) {
            String message = buildMessage(player);
            player.sendActionBar(Component.text(message));
        }
    }

    private String buildMessage(Player player) {
        // Message personnalisé selon le rôle
        return "§7Manche " + gameManager.getRoundNumber();
    }

    public void stopAll() {
        if (updateTask != null) {
            updateTask.cancel();
        }
    }
}
