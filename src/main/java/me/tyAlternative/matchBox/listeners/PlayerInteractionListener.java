package me.tyalternative.matchbox.listeners;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.phase.PhaseType;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.abilities.Ability;
import me.tyalternative.matchbox.abilities.AbilityContext;
import me.tyalternative.matchbox.abilities.AbilityResult;
import me.tyalternative.matchbox.abilities.AbilityTrigger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;


public class PlayerInteractionListener implements Listener {

    private final GameManager gameManager;

    public PlayerInteractionListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (!(event.getEntity() instanceof Player target)) return;

        event.setCancelled(true);

        if (!gameManager.isGameRunning()) return;

        PlayerData attackerData = gameManager.getPlayerManager().get(attacker);
        if (attackerData == null || ! attackerData.isAlive()) return;

        // Phase de Vote : clic pour voter
        if (gameManager.getCurrentPhase() == PhaseType.VOTE) {
            handleVoteClick(attackerData, target);
            return;
        }

        // Phase de Gameplay : Capacités
        if (gameManager.getCurrentPhase() == PhaseType.GAMEPLAY) {
            boolean emptyHand = attacker.getInventory().getItemInMainHand().isEmpty();
            handleAbilityTrigger(attackerData, target, AbilityTrigger.LEFT_CLICK_PLAYER, emptyHand);
        }


    }

    @EventHandler
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!(event.getRightClicked() instanceof Player target)) return;

        Player player = event.getPlayer();

        PlayerData playerData = gameManager.getPlayerManager().get(player);
        PlayerData targetData = gameManager.getPlayerManager().get(target);
        if (playerData == null || !playerData.isAlive()) return;
        if (targetData == null || !targetData.isAlive()) return;

        // Phase de Gameplay
        if (gameManager.getCurrentPhase() == PhaseType.GAMEPLAY) {
            boolean emptyHand = player.getInventory().getItemInMainHand().isEmpty();
            handleAbilityTrigger(playerData, target, AbilityTrigger.RIGHT_CLICK_PLAYER, emptyHand);
        } else if(gameManager.getCurrentPhase() == PhaseType.VOTE) {
            handleVoteClick(playerData, target);

        }





    }

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        if (!gameManager.isGameRunning()) return;
        if (gameManager.getCurrentPhase() != PhaseType.GAMEPLAY) return;

        PlayerData playerData = gameManager.getPlayerManager().get(player);
        if (playerData == null || !playerData.isAlive()) return;


        // Détecter double swap
        DoubleSwapDetector detector = gameManager.getDoubleSwapDetector();
        boolean isDoubleSwap = detector.detectDoubleSwap(player.getUniqueId());

        boolean emptyHand = player.getInventory().getItemInMainHand().isEmpty();

        if (isDoubleSwap) {
            // Double swap détecté
            event.setCancelled(true);
            handleAbilityTrigger(playerData, null, AbilityTrigger.DOUBLE_SWAP_HAND, emptyHand);
            player.sendMessage("Double swap");
        } else {
            // premier swap, on annule et on attend
            event.setCancelled(true);

            // Planifier le simple swap
            int delay = gameManager.getSettings().getDoubleSwapMaxDelayMs() / 50;
            Bukkit.getScheduler().runTaskLater(
                    gameManager.getPlugin(),
                    () -> {
                                // Si toujours pas double swap, alors éxécuter simple swap
                                if (!detector.hasPendingSwap(player.getUniqueId())) {
                                    return;
                                }
                                player.sendMessage("Simple swap");
                                handleAbilityTrigger(playerData, null, AbilityTrigger.SWAP_HAND, emptyHand);
                            },
                    delay
            );
        }

    }

    private void handleVoteClick(PlayerData voterData, Player target) {
        UUID votedId = voterData.getVotedPlayer();

        if (votedId != null && votedId.equals(target.getUniqueId())) {
            gameManager.getVoteManager().removeVote(voterData.getPlayerId());
            voterData.getPlayer().sendMessage("§7Vote retiré.");
        }else {
            gameManager.getVoteManager().castVote(voterData.getPlayerId(), target.getUniqueId());
            voterData.getPlayer().sendMessage("§eVous avez voté contre " + target.getName());
        }

    }

    private void handleAbilityTrigger(PlayerData playerData, Player target, AbilityTrigger trigger, boolean emptyHand) {
        if (!playerData.hasRole()) return;

        for (Ability ability : playerData.getRole().getAbilities()) {
            if (ability.getTrigger() != trigger) continue;

            AbilityContext context = target != null ?
                    (emptyHand ? AbilityContext.ofEmpty(target) : AbilityContext.of(target)) :
                    AbilityContext.noTarget();

            if (ability.canUseAbility(playerData.getPlayer(), playerData, context)) {
                AbilityResult result = ability.executeAbility(playerData.getPlayer(), playerData, context);

                if (result.isSuccess() && result.hasMessage()) {
                    playerData.getPlayer().sendMessage(result.getMessage());
                }
                if (result.isSuccess()) {
                    gameManager.getSoundManager().play(playerData.getPlayer(), "ability_used");
                }
            } else {
                String message;
                if (!ability.hasUsageToUse(playerData.getPlayer())) {
                    message = ability.getUsageLimitMessage(playerData.getPlayer(), playerData);
                } else {
                    message = ability.getCannotUseMessage(playerData.getPlayer(), playerData);
                }
                playerData.getPlayer().sendMessage(message);
            }
        }
    }
}
