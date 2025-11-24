package me.tyalternative.matchbox.listeners;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.phase.PhaseType;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileListener implements Listener {
    private final GameManager gameManager;

    public ProjectileListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof SpectralArrow arrow)) return;
        if (event.getHitBlock() != null) {

            arrow.remove();
            if (!(arrow.getShooter() instanceof Player shooter)) return;

            PlayerData shooterData = gameManager.getPlayerManager().get(shooter);
            if (shooterData == null || !shooterData.isAlive()) return;

            shooterData.useSpectralArrow();


        }
    }

    @EventHandler
    public void onSpectralArrowHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof SpectralArrow arrow)) return;
        if (!(event.getEntity() instanceof Player target)) return;
        if (!(arrow.getShooter() instanceof Player shooter)) return;

        event.setCancelled(true);

        if (!gameManager.isGameRunning()) return;
        if (gameManager.getCurrentPhase() != PhaseType.GAMEPLAY) return;

        PlayerData shooterData = gameManager.getPlayerManager().get(shooter);
        if (shooterData == null || !shooterData.isAlive()) return;

        if (shooterData.useSpectralArrow()) {
            gameManager.getArrowManager().reveal(target.getUniqueId());
            arrow.remove();
        }


    }
}
