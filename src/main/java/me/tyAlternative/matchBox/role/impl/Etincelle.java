package me.tyalternative.matchbox.role.impl;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.AbstractRole;
import me.tyalternative.matchbox.role.RoleType;
import me.tyalternative.matchbox.abilities.impl.*;
import me.tyalternative.matchbox.role.RoleTeam;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Etincelle extends AbstractRole {

    public static String ID = "ETINCELLE";

    public Etincelle() {
        super(ID, "§c§lL'Étincelle",
                "Le traître de la partie. Vous devez embraser tous les Bâtons.",
                RoleType.FLAMME, RoleTeam.SOLITAIRE, Material.BLAZE_POWDER);

        registerAbility(new EmbrasementAbility());
        registerAbility(new PoudreChemineeAbility());
        registerAbility(new ClairvoyanceAbility());
    }

    @Override
    public void onAssigned(Player player, PlayerData data) {
        int cooldown = MatchBox.getInstance().getGameManager().getSettings().getPoudreChemineeCooldown();
        player.sendMessage(Component.text("§8§m§l----------§r§8§l / Role / §m----------").appendNewline()
                .appendNewline()
                .append(Component.text("§r§8§l- §r§7Vous êtes §c§nL'Etincelle")).appendNewline()
                .append(Component.text("§r§8§l- §r§7Objectif : §rVous êtes le traître de la partie. Embrasez tous les autres §eBâtons§f et gagner seul.")).appendNewline()
                .appendNewline()
                .append(Component.text("§r§8§l- §r§7Pour ce faire vous disposez de trois capacités:")).appendNewline()
                .append(Component.text("§6§n§lEmbrasement:§r§f A chaque phase de Gameplay, vous pouvez click droit sur un joueur avec une main vide. Ce dernier sera éliminé à la fin de cette phase. §8(Actif)")).appendNewline()
                .append(Component.text("§6§n§lPoudre de cheminée:§r§f Vous pouvez échanger votre position avec celle d'un autre joueur toutes les §6" + cooldown + " §fsecondes. §8(Actif) ")).append(getSpecialAbilityButton()).appendNewline()
                .append(Component.text("§6§n§lClairvoyance:§r§f Vous connaissez la durée restante de la phase de Gameplay en cours. §8(Passif)")).appendNewline()
                .appendNewline()
        );
    }

}
