package me.tyalternative.matchbox.role.impl;

import me.tyalternative.matchbox.abilities.impl.ClairvoyanceAbility;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.AbstractRole;
import me.tyalternative.matchbox.role.RoleTeam;
import me.tyalternative.matchbox.role.RoleType;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Aurore extends AbstractRole {

    public static String ID = "AURORE";

    public Aurore() {
        super(ID, "§d§lL'Aurore",
                "Vous êtes un être rempli de sagesse. Eliminez §cles Flammes§f et gagnez avec les autres §eBâtons§f.",
                RoleType.BATON, RoleTeam.BATONS, Material.MAGENTA_STAINED_GLASS_PANE);

        registerAbility(new ClairvoyanceAbility());
    }

//    @Override
//    public void onAssigned(Player player, PlayerData data) {
//        player.sendMessage(Component.text("§8§m§l----------§r§8§l / Role / §m----------").appendNewline()
//                .appendNewline()
//                .append(Component.text("§r§8§l- §r§7Vous êtes §d§nL'Aurore")).appendNewline()
//                .append(Component.text("§r§8§l- §r§7Objectif : §r§fVous êtes un être rempli de sagesse. Eliminez §cles Flammes§f et gagnez avec les autres §eBâtons§f.")).appendNewline()
//                .appendNewline()
//                .append(Component.text("§r§8§l- §r§7Pour ce faire vous disposez d'une capacité:")).appendNewline()
//                .append(Component.text("§6§n§lClairvoyance:§r§f Vous connaissez la durée restante de la phase de Gameplay en cours. §8(Passif)")).appendNewline()
//                .appendNewline()
//        );
//    }
}
