package me.tyalternative.matchbox.role.impl;

import me.tyalternative.matchbox.role.AbstractRole;
import me.tyalternative.matchbox.role.RoleType;
import org.bukkit.Material;

public class Baton extends AbstractRole {
    public Baton() {
        super("BATON", "§e§lLe Bâton",
                "Simple mais déterminé. Pas de capacité spéciale.",
                RoleType.BATON, Material.STICK);
    }
}
