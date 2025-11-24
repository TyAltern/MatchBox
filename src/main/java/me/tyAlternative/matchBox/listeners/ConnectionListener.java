package me.tyalternative.matchbox.listeners;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
    private final GameManager gameManager;

    public ConnectionListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!gameManager.isGameRunning()) {
            PlayerData data = gameManager.getPlayerManager().getOrCreate(player);
            data.setState(PlayerState.LOBBY);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!gameManager.isGameRunning()) return;

        PlayerData data = gameManager.getPlayerManager().get(player);
        if (data != null && data.isAlive()) {
            data.setState(PlayerState.DISCONNECTED);
            gameManager.broadcastMessage("§e" + player.getName() + " §7s'est déconnecté.");

            gameManager.getVictoryManager().checkVictory();
        }
    }


}
