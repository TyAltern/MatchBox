package me.tyalternative.matchbox.ui;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.abilities.AbilityType;
import me.tyalternative.matchbox.abilities.CooldownManager;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.abilities.Ability;
import me.tyalternative.matchbox.abilities.UsageLimit;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * Gestionnaire d'ActionBar avec affichage des cooldowns
 */
public class ActionBarManager {
    private final GameManager gameManager;
    private BukkitTask updateTask;

    // Cache pour éviter de recalculer si rien n'a changé
//    private final Map<UUID, String> lastMessages;

    public ActionBarManager(GameManager gameManager) {
        this.gameManager = gameManager;
//        this.lastMessages = new HashMap<>();
    }

    /**
     * Démarre les mises à jour
     */
    public void start() {
        if (!gameManager.getSettings().isActionBarEnabled()) return;

        // Update toutes les secondes
        updateTask = Bukkit.getScheduler().runTaskTimer(
                gameManager.getPlugin(),
                this::update,
                0L,
                20L
        );
    }

    /**
     * Met à jour toutes les ActionBars
     */
    private void update() {
        for (PlayerData data : gameManager.getPlayerManager().getAlive()) {
            Player player = data.getPlayer();
            if (player == null || !data.hasRole()) continue;

            String message = buildActionBarMessage(player, data);

//            // Ne mettre à jour que si le message a changé
//            String lastMessage = lastMessages.get(player.getUniqueId());
//            if (message.equals(lastMessage)) continue;

            player.sendActionBar(Component.text(message));
//            lastMessages.put(player.getUniqueId(), message);
        }
    }


    /**
     * Construit le message d'ActionBar pour un joueur
     */
    private String buildActionBarMessage(Player player, PlayerData data) {
        List<String> parts = new ArrayList<>();

        // Informations de base
        parts.add("§7Manche " + gameManager.getRoundNumber());

        // Cooldowns des capacités
        if (gameManager.getSettings().shouldShowCooldowns()) {
            List<String> cooldownParts = buildCooldownInfo(player, data);
            if (!cooldownParts.isEmpty()) parts.addAll(cooldownParts);
        }

        // Joindre avec un séparateur
        return String.join (" §8•§7 ", parts);
    }



    /**
     * Construit les informations de cooldown
     */
    private List<String> buildCooldownInfo(Player player, PlayerData data) {
        List<String> cooldowns = new ArrayList<>();

        for (Ability ability : data.getRole().getAbilities()) {
            // Ignore les capacités passives
            if (ability.getType() == AbilityType.PASSIVE) continue;

            String cooldownInfo = getCooldownInfo(player.getUniqueId(), ability);
            cooldowns.add(cooldownInfo);
        }

        // Limiter à 3 capacités max pour ne pas surcharger
        if (cooldowns.size() > 3) {
            return cooldowns.subList(0,3);
        }
        return cooldowns;
    }


    /**
     * Obtient l'info de cooldown d'une capacité
     */
    private String getCooldownInfo(UUID playerId, Ability ability) {
        // Vérifier cooldown classique
        CooldownManager cooldownManager = gameManager.getCooldownManager();

        if (cooldownManager != null && cooldownManager.hasCooldown(playerId, ability.getId())) {
            int remaining = cooldownManager.getRemainingSeconds(playerId, ability.getId());
            return "§6" + ability.getName() + "§7: §c" + remaining + "s";
        }

        // Vérifier limites d'utilisation
        UsageLimit perRound = ability.getPerRoundLimit();
        UsageLimit perGame = ability.getPerGameLimit();

        if (ability.hasUsageLimit()) {
            int remainingRound = gameManager.getAbilityUsageManager().getRemainingUses(playerId, ability.getId(), perRound);
            int remainingGame = gameManager.getAbilityUsageManager().getRemainingUses(playerId, ability.getId(), perGame);

            // Si épuisé
            if ((remainingRound == 0 && !perRound.isUnlimited()) || (remainingGame == 0 && !perGame.isUnlimited())) {
                return "§6" + ability.getName() + "§7: §c✘";
            }

            // Si limité, mais encore disponible
            if (!perRound.isUnlimited() && remainingRound > 0) {
                return "§6" + ability.getName() + "§7: §a" + remainingRound + "/" + perRound.getMaxUses() + "";
            }
        }

        // Capacité disponible
        return "§6" + ability.getName() + "§7: §a✔";
    }


    /**
     * Force une mise à jour immédiate pour un joueur
     */
    public void updatePlayer(Player player) {
        PlayerData data = gameManager.getPlayerManager().get(player);
        if (data == null) return;

        String message = buildActionBarMessage(player, data);
        player.sendActionBar(Component.text(message));
//        lastMessages.put(player.getUniqueId(), message);
    }


    /**
     * Arrête les mises à jour
     */
    public void stopAll() {
        if (updateTask != null && !updateTask.isCancelled()) updateTask.cancel();

//        lastMessages.clear();
    }


}
