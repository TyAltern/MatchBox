package me.tyalternative.matchbox.role.impl;

import me.tyalternative.matchbox.abilities.impl.ClairvoyanceAbility;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.AbstractRole;
import me.tyalternative.matchbox.role.RoleType;
import me.tyalternative.matchbox.role.RoleTeam;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class Baton extends AbstractRole {

    public static final String ID = "BATON";
    public static final String DISPLAY_NAME = "§e§lLe Bâton";
    public static final String DESCRIPTION = "Vous êtes l'objet inflammable par excellence. Vous n'êtes bon qu'à être brulé. Cependant essayer d'éliminez §cles Flammes§f et gagnez avec les autres §eBâtons§f.";

    public Baton() {
        super(ID, DISPLAY_NAME, DESCRIPTION,
                RoleType.BATON, RoleTeam.BATONS, Material.STICK);
    }

//    @Override
//    public void onAssigned(Player player, PlayerData data) {
//        player.sendMessage(Component.text("§8§m§l----------§r§8§l / Role / §m----------").appendNewline()
//                .appendNewline()
//                .append(Component.text("§r§8§l- §r§7Vous êtes §e§nUn Bâton")).appendNewline()
//                .append(Component.text("§r§8§l- §r§7Objectif : §r§fVous êtes l'objet inflammable par excellence. Vous n'êtes bon qu'à être brulé. Cependant essayer d'éliminez §cles Flammes§f et gagnez avec les autres §eBâtons§f.")).appendNewline()
//                .appendNewline()
//        );
//    }
}
