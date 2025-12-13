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
        super(ID, "§8Calciné",
                "Retarde le premier Embrasement",
                AbilityType.PASSIVE, AbilityTrigger.AUTOMATIC);
    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext context) {
        return true;
    }

    @Override
    public AbilityResult execute(Player player, PlayerData data, AbilityContext context) {
        return AbilityResult.success();
    }

    public EliminationCause getCause() {
        return cause;
    }
    public void setCause(EliminationCause cause) {
        this.cause = cause;
    }

    public boolean isUsed() {
        return used;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }
}
