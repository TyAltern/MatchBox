package me.tyalternative.matchbox.abilities;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.entity.Player;

/**
 * Classe de base pour toutes les capacités
 */
public abstract class Ability {

    protected final GameManager gameManager;

    protected final String id;
    protected final String name;
    protected final String description;
    protected boolean hasCooldown;
    protected final AbilityType type;
    protected final AbilityTrigger trigger;

    protected boolean isDrunk;

    protected int ticks = -1;

    // Limites d'utilisation
    protected UsageLimit perRoundLimit;
    protected UsageLimit perGameLimit;

    public Ability(String id, String name, String description, AbilityType type, AbilityTrigger trigger) {

        this.gameManager = MatchBox.getInstance().getGameManager();

        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.trigger = trigger;

        // Par défaut : illimité
        this.perRoundLimit = UsageLimit.unlimited();
        this.perGameLimit = UsageLimit.unlimited();

    }

    public Ability(String id, String name, String description, AbilityType type, AbilityTrigger trigger, int ticks) {

        this.gameManager = MatchBox.getInstance().getGameManager();

        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.trigger = trigger;

        // Par défaut : illimité
        this.perRoundLimit = UsageLimit.unlimited();
        this.perGameLimit = UsageLimit.unlimited();

        if (trigger == AbilityTrigger.TICKS) {
            this.ticks = ticks;
        }
    }

    /**
     * Vérifie si la capacité peut être utilisée
     */
    public boolean canUseAbility(Player player, PlayerData data, AbilityContext context) {

        return hasUsageToUse(player) && canUse(player, data, context);
    }

    protected abstract boolean canUse(Player player, PlayerData data, AbilityContext context);

    /**
     * Exécute la capacité
     * @return true si la capacité a été exécutée avec succès
     */

    public AbilityResult executeAbility(Player player, PlayerData data, AbilityContext context) {
        if (isDrunk()) {
            return executeDrunk(player, data, context);
        }

        return execute(player, data, context);

    }

    protected abstract AbilityResult execute(Player player, PlayerData data, AbilityContext context);


    // ========== DRUNK MANAGEMENT ==========

    protected AbilityResult executeDrunk(Player player, PlayerData data, AbilityContext context) {
        return execute(player, data, context);
    }
    /**
     * Définit si la capacité peut être fausse. Par défaut retourne False. Override si ce n'est pas le cas.
     */
    public boolean canBeDrunk() {
        return false;
    }

    public boolean isDrunk() {
        return canBeDrunk() && this.isDrunk;
    }

    public void setDrunk(boolean isDrunk) {
        this.isDrunk = isDrunk;
    }



    /**
     * Message d'erreur si la capacité ne peut pas être utilisée
     */
    public String getCannotUseMessage(Player player, PlayerData data) {
        return "§cVous ne pouvez pas utiliser cette capacité maintenant.";
    }

    /**
     * Définit les limites d'utilisation
     */
    protected void setUsageLimits(UsageLimit perRound, UsageLimit perGame) {
        this.perRoundLimit = perRound;
        this.perGameLimit = perGame;
    }
    /**
     * Définit les limites d'utilisation par Round
     */
    protected void setUsageRoundLimits(int perRound) {
        if (perRound >= 0) {
            this.perRoundLimit = UsageLimit.perRound(perRound);
        } else {
            this.perRoundLimit = UsageLimit.unlimited();
        }
        this.perGameLimit = UsageLimit.unlimited();
    }
    /**
     * Définit les limites d'utilisation par Game
     */
    protected void setUsageGameLimits(int perGame) {
        this.perRoundLimit = UsageLimit.unlimited();
        if (perGame >= 0) {
            this.perGameLimit = UsageLimit.perGame(perGame);
        } else {
            this.perGameLimit = UsageLimit.unlimited();
        }
    }

    public boolean hasUsageToUse(Player player) {

        if (gameManager.getAbilityUsageManager().getRemainingUses(player.getUniqueId(),id,perRoundLimit) == 0) return false;
        if (gameManager.getAbilityUsageManager().getRemainingUses(player.getUniqueId(),id,perGameLimit) == 0) return false;

        return true;
    }


    /**
     * Message d'erreur pour limite d'utilisation
     */
    public String getUsageLimitMessage(Player player, PlayerData data) {
        if (gameManager.getAbilityUsageManager().getRemainingUses(player.getUniqueId(),id,perRoundLimit) == 0 && !perRoundLimit.isUnlimited()) {
            return "§cVous avez épuisé cette capacité pour cette manche ! (0/" + perRoundLimit.getMaxUses() + ")";
        }
        if (gameManager.getAbilityUsageManager().getRemainingUses(player.getUniqueId(),id,perGameLimit) == 0 && !perGameLimit.isUnlimited()) {
            return "§cVous avez épuisé cette capacité pour cette partie ! (0/" + perGameLimit.getMaxUses() + ")";
        }
        return "§cCapacité indisponible.";
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean hasCooldown() { return hasCooldown; }
    public AbilityType getType() { return type; }
    public AbilityTrigger getTrigger() { return trigger; }

    public int getTicks() { return ticks; }

    public UsageLimit getPerRoundLimit() { return perRoundLimit; }
    public UsageLimit getPerGameLimit() { return perGameLimit; }

    public boolean hasUsageLimit() {
        return (perRoundLimit != null && !perRoundLimit.isUnlimited()) ||
                (perGameLimit != null && !perGameLimit.isUnlimited());
    }






    // ========== EVENTS LISTENERS ==========


    public void onAssigned(Player player, PlayerData data) {
        // Override si nécessaire
    }

    public void onGameplayPhaseStart(Player player, PlayerData data) {
        // Override si nécessaire
    }

    public void onGameplayPhaseEnd(Player player, PlayerData data) {
        // Override si nécessaire
    }

    public void onVotePhaseStart(Player player, PlayerData data) {
        // Override si nécessaire
    }

    public void onVotePhaseEnd(Player player, PlayerData data) {
        // Override si nécessaire
    }

    public void onEliminated(Player player, PlayerData data, String cause) {
        // Override si nécessaire
    }

    public void onOtherEliminated(Player self, Player eliminated, String cause) {
        // Override si nécessaire
    }
}
