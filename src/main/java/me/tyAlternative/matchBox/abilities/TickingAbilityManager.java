package me.tyalternative.matchbox.abilities;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TickingAbilityManager {
    private final GameManager gameManager;

    private BukkitTask task;
    private int currentTick;

    public TickingAbilityManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }


//    public void registerTickingAbility(Ability tickingAbility) {
//        if (tickingAbility.getTrigger() != AbilityTrigger.TICKS || tickingAbility.getTicks() <= 0) return;
//        int ticks = tickingAbility.getTicks();
//
//        tickingAbilities.put(tickingAbility, ticks);
//    }
//
//    public void removeTickingAbility(Ability tickingAbility) {
//        tickingAbilities.remove(tickingAbility);
//    }
//

    public void startTicker() {

        currentTick = 0;

        task = Bukkit.getScheduler().runTaskTimer(MatchBox.getInstance(),() -> {
            currentTick++;
            for (PlayerData playerData : gameManager.getPlayerManager().getAlive()) {
                if (!playerData.hasRole()) continue;

                for (Ability ability : playerData.getRole().getAbilities()) {
                    if (ability.getTrigger() != AbilityTrigger.TICKS || ability.getTicks() <= 0) continue;
                    int ticks = ability.getTicks();


                    if (currentTick % ticks == 0) {

                        AbilityContext context = AbilityContext.noTarget();

                        if (ability.canUseAbility(playerData.getPlayer(), playerData, context)) {

                            ability.executeAbility(playerData.getPlayer(), playerData, context);
                        }
                    }
                }

            }
        },0, 1L);
    }

    public void stopTicker() {
        if (task != null && !task.isCancelled()) task.cancel();
    }

}
