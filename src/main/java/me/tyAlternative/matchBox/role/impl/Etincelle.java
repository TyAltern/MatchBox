package me.tyalternative.matchbox.role.impl;

import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.AbstractRole;
import me.tyalternative.matchbox.role.RoleType;
import me.tyalternative.matchbox.abilities.impl.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Etincelle extends AbstractRole {
    public Etincelle() {
        super("ETINCELLE", "§c§lL'Étincelle",
                "Le traître de la partie. Vous devez embraser tous les Bâtons.",
                RoleType.FLAMME, Material.BLAZE_POWDER);

        registerAbility(new EmbrasementAbility());
//        registerAbility(new PoudreChemineeAbility());
        registerAbility(new ClairvoyanceAbility());
    }

    @Override
    public void onAssigned(Player player, PlayerData data) {
        player.sendMessage("§8" + "=".repeat(50));
        player.sendMessage("§c§l⚡ L'ÉTINCELLE ⚡");
        player.sendMessage("");
        player.sendMessage("§7Vous êtes le §c§ltraître§7 de cette partie.");
        player.sendMessage("§7Objectif : §cEmbraser tous les Bâtons§7.");
        player.sendMessage("");
        player.sendMessage("§6§lCapacités :");
        player.sendMessage("§6 • Embrasement§7 : Clic droit main vide");
        player.sendMessage("§6 • Poudre de cheminée§7 : §d[F]§7 Téléportation (CD: 20s)");
        player.sendMessage("§6 • Clairvoyance§7 : Voit le temps restant");
        player.sendMessage("§8" + "=".repeat(50));
    }

}
