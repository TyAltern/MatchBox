package me.tyalternative.matchbox.role.impl;

import me.tyalternative.matchbox.abilities.impl.CendreAbility;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.AbstractRole;
import me.tyalternative.matchbox.role.RoleTeam;
import me.tyalternative.matchbox.role.RoleType;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Cendre extends AbstractRole {

    public static String ID = "CENDRE";

    public Cendre() {
        super(ID, "§7§lLa Cendre",
                "Vous êtes l'âme d'une ancienne §cEtincelle§f, certains de ses pouvoirs ne vous font pas effet. Eliminez §cles Flammes§f et gagnez avec les autres §eBâtons§f.",
                RoleType.BATON, RoleTeam.BATONS, Material.GUNPOWDER);

        registerAbility(new CendreAbility());
    }

//    @Override
//    public void onAssigned(Player player, PlayerData data) {
//        player.sendMessage(Component.text("§8§m§l----------§r§8§l / Role / §m----------").appendNewline()
//                .appendNewline()
//                .append(Component.text("§r§8§l- §r§7Vous êtes §8§nLa Cendre")).appendNewline()
//                .append(Component.text("§r§8§l- §r§7Objectif : §r§fVous êtes l'âme d'une ancienne §cEtincelle§f, certains de ses pouvoirs ne vous font pas effet. Eliminez §cles Flammes§f et gagnez avec les autres §eBâtons§f.")).appendNewline()
//                .appendNewline()
//                .append(Component.text("§r§8§l- §r§7Pour ce faire vous disposez d'une capacité:")).appendNewline()
//                .append(Component.text("§6§n§lCendre:§r§f Vous possédez une protection contre la §6Poudre de cheminée§f de §cl'Etincelle§f. §8(passif)")).appendNewline()
//                .appendNewline()
//        );
//    }
}
