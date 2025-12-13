package me.tyalternative.matchbox.listeners;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.phase.PhaseType;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        if (block.getType().toString().equalsIgnoreCase("OAK_SIGN") || block.getType().toString().equalsIgnoreCase("OAK_WALL_SIGN")) {
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
        if (block.getType().toString().equalsIgnoreCase("OAK_SIGN") || block.getType().toString().equalsIgnoreCase("OAK_WALL_SIGN")) {
            gameManager.getSignManager().remove(block.getLocation());
            event.setDropItems(false);
            Inventory inv = player.getInventory();
            if (!inv.contains(Material.OAK_SIGN, 16)) {
                inv.addItem(new ItemStack(Material.OAK_SIGN));
            }
        } else {
            event.setCancelled(true);
        }

    }


    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (!gameManager.isGameRunning()) return;

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock == null) return;
            if (clickedBlock.getType().toString().toUpperCase().contains("TRAPDOOR")) {
                event.setCancelled(true);
            }
        }
    }


}
