package me.tyalternative.matchbox;

import me.tyalternative.matchbox.commands.MatchBoxCommand;
import me.tyalternative.matchbox.composition.CompositionClickHandler;
import me.tyalternative.matchbox.composition.CompositionCommand;
import me.tyalternative.matchbox.composition.CompositionGUI;
import me.tyalternative.matchbox.config.ConfigManager;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.listeners.*;
import me.tyalternative.matchbox.role.RoleRegistry;
import me.tyalternative.matchbox.role.impl.*;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Classe principale du plugin MatchBox
 * @author TyAlternative
 * @version 2.0.0
 */
public final class MatchBox extends JavaPlugin {

    private static MatchBox instance;
    private ConfigManager configManager;
    private GameManager gameManager;
    private RoleRegistry roleRegistry;


    @Override
    public void onEnable() {
        instance = this;
        gameManager = new GameManager(this);

        getLogger().info("======================================");
        getLogger().info("   MatchBox v" + getPluginMeta().getVersion());
        getLogger().info("   Chargement en cours...");
        getLogger().info("======================================");

        saveDefaultConfig();
        configManager = new ConfigManager(this);

        // Vérifier dépendances
        if (!checkDependencies()) {
            getLogger().severe("Dépendances manquantes ! Désactivation.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Initialisation
        roleRegistry = new RoleRegistry();
        registerRoles();


        registerCommands();
        registerListeners();

        getLogger().info("======================================");
        getLogger().info("   MatchBox activé avec succès !");
        getLogger().info("   " + roleRegistry.count() + " rôles chargés");
        getLogger().info("======================================");
    }

    @Override
    public void onDisable() {
        if (gameManager != null && gameManager.isGameRunning()) {
            gameManager.forceStopGame();
        }

        getLogger().info("MatchBox désactivé.");
    }


    private boolean checkDependencies() {
        boolean skinsRestorer = getServer().getPluginManager().getPlugin("SkinsRestorer") != null;

        if (!skinsRestorer) {
            getLogger().severe("SkinsRestorer n'est pas installé !");
            getLogger().severe("Téléchargez-le: https://www.spigotmc.org/resources/skinsrestorer.2124/");
            return false;
        }


        return true;
    }


    private void registerRoles() {
        getLogger().info("Enregistrement des rôles...");

        // Flammes
        roleRegistry.register(new Etincelle());
//        roleRegistry.register(new Torche());

        // Bâtons
        roleRegistry.register(new Souffle());
        roleRegistry.register(new Cendre());
        roleRegistry.register(new Calcine());
        roleRegistry.register(new Aurore());
        roleRegistry.register(new Baton());

        getLogger().info("→ " + roleRegistry.count() + " rôles enregistrés");
    }
    private void registerCommands() {
        getLogger().info("Enregistrement des commandes...");

        getCommand("matchbox").setExecutor(new MatchBoxCommand(gameManager));
        getCommand("compo").setExecutor(new CompositionCommand(gameManager));

        getLogger().info("→ Commandes enregistrées");
    }


    private void registerListeners() {
        getLogger().info("Enregistrement des listeners...");

        getServer().getPluginManager().registerEvents(new PlayerInteractionListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new BlockListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new ProjectileListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new ConnectionListener(gameManager), this);

        // Enregistrer le listener du GUI de composition
        CompositionCommand compoCmd = (CompositionCommand) getCommand("compo").getExecutor();
        CompositionClickHandler clickHandler = new CompositionClickHandler(getGameManager(), new CompositionGUI(gameManager));
        getServer().getPluginManager().registerEvents(new CompositionListener(clickHandler), this);

        getLogger().info("→ Listeners enregistrés");
    }



    // Getters statiques
    public static MatchBox getInstance() { return instance; }

    public ConfigManager getConfigManager() { return configManager; }
    public GameManager getGameManager() {
        return gameManager;
    }
    public RoleRegistry getRoleRegistry() { return roleRegistry; }
}
