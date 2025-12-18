package me.tyalternative.matchbox.role.impl;

import me.tyalternative.matchbox.abilities.impl.SouffleAbility;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.AbstractRole;
import me.tyalternative.matchbox.role.RoleTeam;
import me.tyalternative.matchbox.role.RoleType;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class Souffle extends AbstractRole {

    public static final String ID = "SOUFFLE";
    public static final String DISPLAY_NAME = "§b§lLe Souffle";
    public static final String DESCRIPTION = "Vous êtes l'allié le plus fort pour contrer §cles Flammes§f. Eliminez §cles Flammes§f et gagnez avec les autres §eBâtons§f.";

    public Souffle() {
        super(ID, DISPLAY_NAME, DESCRIPTION,
                RoleType.BATON, RoleTeam.BATONS, Material.WIND_CHARGE);


        registerAbility(new SouffleAbility());

    }

//    @Override
//    public void onAssigned(Player player, PlayerData data) {
//        player.sendMessage(Component.text("§8§m§l----------§r§8§l / Role / §m----------").appendNewline()
//                .appendNewline()
//                .append(Component.text("§r§8§l- §r§7Vous êtes §b§nLe Souffle")).appendNewline()
//                .append(Component.text("§r§8§l- §r§7Objectif : §rVous êtes l'allié le plus fort pour contrer §cles Flammes§f. Eliminez §cles Flammes§f et gagnez avec les autres §eBâtons§f.")).appendNewline()
//                .appendNewline()
//                .append(Component.text("§r§8§l- §r§7Pour ce faire vous disposez d'une capacité:")).appendNewline()
//                .append(Component.text("§6§n§lSouffle:§r§f A chaque phase de Gameplay, vous pouvez click droit sur un joueur avec une main vide. Ce dernier sera protégé contre l'§cEmbrasement§r§f des §cFlammes§f. §8(Actif)")).appendNewline()
//                .appendNewline()
//        );
//    }


}
