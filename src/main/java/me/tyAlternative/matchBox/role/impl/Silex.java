package me.tyalternative.matchbox.role.impl;

import me.tyalternative.matchbox.abilities.impl.AmnesieAbility;
import me.tyalternative.matchbox.role.AbstractRole;
import me.tyalternative.matchbox.role.RoleTeam;
import me.tyalternative.matchbox.role.RoleType;
import org.bukkit.Material;

import java.util.List;

public class Silex extends AbstractRole {

    public static final String ID = "SILEX";
    public static final String DISPLAY_NAME = "§8§lLe Silex";
    public static final String DESCRIPTION = "Un être ayant perdu son passé. Il doit malgré tout s'allier avec l'Acier afin d'éliminer tous les autres.";

    public Silex() {
        super(ID, DISPLAY_NAME, DESCRIPTION,
                RoleType.FLAMME, RoleTeam.SILEX_ACIER, Material.FLINT);

        registerHiddenAbility(new AmnesieAbility());
    }
}
