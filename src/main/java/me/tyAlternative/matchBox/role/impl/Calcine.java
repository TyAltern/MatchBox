package me.tyalternative.matchbox.role.impl;

import me.tyalternative.matchbox.abilities.impl.CalcineAbility;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.AbstractRole;
import me.tyalternative.matchbox.role.RoleTeam;
import me.tyalternative.matchbox.role.RoleType;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Calcine extends AbstractRole {

    public static String ID = "CALCINE";

    public Calcine() {
        super(ID, "§4§lLe Calciné",
                "Retarde son premier Embrasement d'une manche.",
                RoleType.BATON, RoleTeam.BATONS, Material.GRAY_CONCRETE_POWDER);

        registerAbility(new CalcineAbility());
    }

    @Override
    public void onAssigned(Player player, PlayerData data) {
        player.sendMessage(Component.text("§8§m§l----------§r§8§l / Role / §m----------").appendNewline()
                .appendNewline()
                .append(Component.text("§r§8§l- §r§7Vous êtes §4§nLe Calciné")).appendNewline()
                .append(Component.text("§r§8§l- §r§7Objectif : §r§fRescapé d'un précédent §cEmbrasement§f, la douleur vous est maintenant supportable. Cependant, pas pour longtemps. Vengez vous, éliminez §cles Flammes§f et gagnez avec les autres §eBâtons§f.")).appendNewline()
                .appendNewline()
                .append(Component.text("§r§8§l- §r§7Pour ce faire vous disposez d'une capacitée:")).appendNewline()
                .append(Component.text("§6§n§lCalciné:§r§f Vous retardez votre §cEmbrasement§f par §cles Flammes§f d'une manche. Vous n'êtes cependant pas au courant de votre §cEmbrasement§f. §8(Passif)")).appendNewline()
                .appendNewline()
        );
    }
}
