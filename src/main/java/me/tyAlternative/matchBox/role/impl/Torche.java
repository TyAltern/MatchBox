package me.tyalternative.matchbox.role.impl;

import me.tyalternative.matchbox.abilities.impl.EtouffementDeFlammeAbility;
import me.tyalternative.matchbox.abilities.impl.RayonnementAbility;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.AbstractRole;
import me.tyalternative.matchbox.role.RoleTeam;
import me.tyalternative.matchbox.role.RoleType;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class Torche extends AbstractRole {
    public static final String ID = "TORCHE";
    public static final String DISPLAY_NAME = "§c§lLa Torche";
    public static final String DESCRIPTION = "Être de lumière, il semble que les bâtons ne veulent pas de votre amour. Alors, embrasez les tous et gagner seul. Cela devrait leur faire changer d'avis!";


    public Torche() {
        super(ID, DISPLAY_NAME, DESCRIPTION,
                RoleType.FLAMME, RoleTeam.SOLITAIRE, Material.TORCH);

        registerAbility(new RayonnementAbility());
        registerAbility(new EtouffementDeFlammeAbility());
    }

//    @Override
//    public void onAssigned(Player player, PlayerData data) {
//
//        double radius = rayonnementAbility.getRayonnementRadius();
//
//        player.sendMessage(Component.text("§8§m§l----------§r§8§l / Role / §m----------").appendNewline()
//                .appendNewline()
//                .append(Component.text("§r§8§l- §r§7Vous êtes §c§nLa Torche")).appendNewline()
//                .append(Component.text("§r§8§l- §r§7Objectif : §rÊtre de lumière, il semble que les bâtons ne veulent pas de votre amour. Alors, embrasez les tous et gagner seul. Cela devrait leur faire changer d'avis!")).appendNewline()
//                .appendNewline()
//                .append(Component.text("§r§8§l- §r§7Pour ce faire vous disposez de deux capacitées:")).appendNewline()
//                .append(Component.text("§6§n§lRayonnement:§r§f A chaque phase de Gameplay, le joueur étant resté le plus longtemps dans un rayon de §7" +  radius + "§r blocks de vous sera éliminé à la fin de cette phase. §8(Passif)")).appendNewline()
//                .append(Component.text("§6§n§lÉtouffement de flamme:§r§f Vous pouvez décider d'abandonner votre §6Rayonnement §fpour un tour. §8(Actif) ")).append(getSpecialAbilityButton()).appendNewline()
//                .appendNewline());
//    }
}
