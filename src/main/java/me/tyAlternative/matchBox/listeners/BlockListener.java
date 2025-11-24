package me.tyalternative.matchbox.listeners;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.phase.PhaseType;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
    private final GameManager gameManager;

    public BlockListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!gameManager.isGameRunning()) return;
        if (gameManager.getCurrentPhase() != PhaseType.GAMEPLAY) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        PlayerData data = gameManager.getPlayerManager().get(player);
        if (data == null || !data.isAlive()) {
            event.setCancelled(true);
            return;
        }

        Block block = event.getBlock();
        if (block.getType().toString().contains("SIGN")) {
            gameManager.getSignManager().add(block.getLocation());
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!gameManager.isGameRunning()) return;
        if (gameManager.getCurrentPhase() != PhaseType.GAMEPLAY) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        PlayerData data = gameManager.getPlayerManager().get(player);
        if (data == null || !data.isAlive()) {
            event.setCancelled(true);
            return;
        }

        Block block = event.getBlock();
        if (block.getType().toString().contains("SIGN")) {
            gameManager.getSignManager().remove(block.getLocation());
        } else {
            event.setCancelled(true);
        }

    }


}
