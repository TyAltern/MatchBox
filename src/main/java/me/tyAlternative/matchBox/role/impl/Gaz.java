package me.tyalternative.matchbox.role.impl;

import me.tyalternative.matchbox.abilities.impl.GazAbility;
import me.tyalternative.matchbox.abilities.impl.PriseDeFeuAbility;
import me.tyalternative.matchbox.abilities.impl.SolitudeAbility;
import me.tyalternative.matchbox.role.AbstractRole;
import me.tyalternative.matchbox.role.RoleTeam;
import me.tyalternative.matchbox.role.RoleType;
import org.bukkit.Material;

public class Gaz extends AbstractRole {

    public static final String ID = "GAZ";
    public static final String DISPLAY_NAME = "§d§lLe Gaz";
    public static final String DESCRIPTION = "Votre chance peut s'avérer être un malédiction. Eliminez §cles Flammes§f et gagnez avec les autres §eBâtons§f.";

    public Gaz() {
        super(ID, DISPLAY_NAME, DESCRIPTION,
                RoleType.BATON, RoleTeam.BATONS, Material.DRAGON_BREATH);

        registerAbility(new GazAbility());
        registerAbility(new SolitudeAbility());
        registerAbility(new PriseDeFeuAbility());
    }


}
