package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.abilities.*;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.Role;
import me.tyalternative.matchbox.role.impl.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AmnesieAbility extends Ability {

    public static String ID = "amnesie";
    private Role amnesiqueRole;

    public AmnesieAbility() {
        super(ID, "Amnesie",
                "Vous ne vous souvenez plus de qui vous êtes. Ce message vous est même caché",
                AbilityCategory.CURSE, AbilityUseType.PASSIVE, AbilityTrigger.AUTOMATIC);
    }

    @Override
    public void onAssigned(Player player, PlayerData data) {
        super.onAssigned(player, data);

        Role role = data.getRole();
        if (role == null) return;

        List<String> possibleRoles = gameManager.getSettings().getAmnesiaRolePickChoices();

        Collections.shuffle(possibleRoles);
        String pickedRole = possibleRoles.getFirst();

        switch (pickedRole) {
            case Torche.ID -> amnesiqueRole = new Torche();
            case Souffle.ID -> amnesiqueRole = new Souffle();
            case Calcine.ID -> amnesiqueRole = new Calcine();
            default -> amnesiqueRole = new Baton();
        }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
        List<Ability> abilities = new ArrayList<>();
        for (Ability ability : amnesiqueRole.getAbilities()) {
            abilities.add(role.registerDrunkAbility(ability));
        }
        for (Ability ability : abilities) {
            MatchBox.getInstance().getLogger().info(ability.getName());
        }


        gameManager.getMessageManager().sendRoleDescription(player, amnesiqueRole.getDisplayName(), amnesiqueRole.getDescription(), abilities);

    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext context) {
        return true;
    }

    @Override
    protected AbilityResult execute(Player player, PlayerData data, AbilityContext context) {
        return null;
    }

    public Role getAmnesiqueRole() {
        if (this.amnesiqueRole != null) return this.amnesiqueRole;
        else return gameManager.getPlugin().getRoleRegistry().get(Baton.ID);
    }
}
