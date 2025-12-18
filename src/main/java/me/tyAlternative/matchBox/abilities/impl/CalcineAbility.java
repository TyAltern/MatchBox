package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.abilities.*;
import me.tyalternative.matchbox.elimination.EliminationCause;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.entity.Player;

public class CalcineAbility extends Ability {

    public static String ID = "calcine";
    private EliminationCause cause;
    private boolean used = false;

    public CalcineAbility() {
        super(ID, "Calciné",
                "Vous retardez votre §cEmbrasement§f par §cles Flammes§f d'une manche. Vous n'êtes cependant pas au courant de votre §cEmbrasement§f.",
                AbilityCategory.CAPACITY, AbilityUseType.PASSIVE, AbilityTrigger.AUTOMATIC);
    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext context) {
        return true;
    }

    @Override
    protected AbilityResult execute(Player player, PlayerData data, AbilityContext context) {
        setUsed(true);
        return AbilityResult.success();
    }

    @Override
    protected AbilityResult executeDrunk(Player player, PlayerData data, AbilityContext context) {
        return AbilityResult.failure("Drunk");
    }

    @Override
    public boolean canBeDrunk() {
        return true;
    }

    public boolean registerEmbrasement(PlayerData data, EliminationCause cause) {
        if (isDrunk()) return false;
        setUsed(true);
        setCause(cause);
        return true;
    }

    public boolean wasRegistered(PlayerData data) {
        if (isDrunk()) return false;
        return used;
    }


    public EliminationCause getCause() {
        return cause;
    }
    public void setCause(EliminationCause cause) {
        this.cause = cause;
    }

//    public boolean isUsed() {
//        if (isDrunk()) return false;
//        return used;
//    }
    public void setUsed(boolean used) {
        this.used = used;
    }
}
