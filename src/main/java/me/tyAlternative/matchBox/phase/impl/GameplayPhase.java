package me.tyalternative.matchbox.phase.impl;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.abilities.Ability;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.phase.GamePhase;
import me.tyalternative.matchbox.phase.PhaseContext;
import me.tyalternative.matchbox.phase.PhaseType;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.Role;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

public class GameplayPhase implements GamePhase {


    @Override
    public void onStart(GameManager gameManager, PhaseContext context) {

        if (gameManager.getSettings().shouldHideSkins()) {
            gameManager.getAnonymityManager().hideAllSkins();
        }
        if (gameManager.getSettings().shouldHideNametags()) {
            gameManager.getAnonymityManager().hideAllPlayersNametag();
        }

        // Détruire les anciens panneaux
        gameManager.getSignManager().clearAll();

        // Notifier les rôles
        for (PlayerData data : gameManager.getPlayerManager().getAlive()) {
            if (data.getPlayer() != null && data.hasRole()) {
                clearRoundItem(data);
                giveRoundItem(data);
                Role role = data.getRole();
                role.onGameplayPhaseStart(data.getPlayer(), data);
//                for (Ability ability : role.getAbilities()) {
//                    if (ability.hasCooldown()) {
//                        ability.
//                    }
//                }

            }
        }

        gameManager.getAbilityUsageManager().resetRoundForAll();

        // Lancement du ticking des TickingAbility
        gameManager.getTickingAbilityManager().startTicker();

        gameManager.broadcastMessage("§bPhase de Gameplay !");
        gameManager.getSoundManager().playToAll("phase_change");

    }

    @Override
    public void onEnd(GameManager gameManager, PhaseContext context) {
        // Arret du ticking des TickingAbility
        gameManager.getTickingAbilityManager().stopTicker();

        // Notifier les rôles
        for (PlayerData data : gameManager.getPlayerManager().getAlive()) {
            if (data.getPlayer() != null && data.hasRole()) {
                clearRoundItem(data);
                data.getRole().onGameplayPhaseEnd(data.getPlayer(), data);
            }
        }

        // Restaurer les Skins/Nametags
        gameManager.getAnonymityManager().restoreAll();

        // Traiter les embrasements
        gameManager.getEliminationManager().processEmbrasements();
    }

    @Override
    public PhaseType getType() {
        return PhaseType.GAMEPLAY;
    }

    // TODO: ADD RANDOM DURATION
    @Override
    public long getDuration(GameManager gameManager) {
        return gameManager.getSettings().getGameplayDuration() * 1000L;
    }

    private void giveRoundItem(PlayerData data) {
        Player player = data.getPlayer();
        if (player == null) return;

        ItemStack signs = new ItemStack(Material.OAK_SIGN, 16);
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        ItemStack crossbow = new ItemStack(Material.CROSSBOW);
        ItemStack spyglass = new ItemStack(Material.SPYGLASS);
        ItemStack arrow = new ItemStack(Material.SPECTRAL_ARROW);

        ItemMeta axeMeta = axe.getItemMeta();
        axeMeta.setUnbreakable(true);
        axeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        axe.setItemMeta(axeMeta);

        ItemMeta crossbowMeta = axe.getItemMeta();
        crossbowMeta.setUnbreakable(true);
        crossbowMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        axe.setItemMeta(crossbowMeta);

        int arrowAmount = data.getSpectralArrowsRemaining();
        if (arrowAmount > 0) {
            arrow.setAmount(arrowAmount);
            player.getInventory().setItem(7, arrow);
        }

        player.getInventory().setItem(0, signs);
        player.getInventory().setItem(1, axe);
        player.getInventory().setItem(6, spyglass);
        player.getInventory().setItem(8, crossbow);



    }

    private void clearRoundItem(PlayerData data) {
        Player player = data.getPlayer();
        if (player == null) return;

        player.getInventory().clear();
    }



}
