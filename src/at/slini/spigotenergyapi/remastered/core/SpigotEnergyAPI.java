package at.slini.spigotenergyapi.remastered.core;

import at.slini.spigotenergyapi.remastered.api.BlockWrapperInstance;
//import at.slini.spigotenergyapi.remastered.api.spigotenergyapi.Blocks.Managers.BlockWrapperManager;
import at.slini.spigotenergyapi.remastered.core.Blocks.Managers.BlockWrapperManager;
import at.slini.spigotenergyapi.remastered.core.Commands.GetCustomBlockData;
import at.slini.spigotenergyapi.remastered.core.Listeners.BlockListener;
import at.slini.spigotenergyapi.remastered.core.Managers.ModuleRegistry;
import at.slini.spigotenergyapi.remastered.core.Network.SteamNetworkEngine;
import at.slini.spigotenergyapi.remastered.core.Utils.EnergyBlockUpdate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpigotEnergyAPI extends JavaPlugin {

    private static SpigotEnergyAPI instance;

    public static FileConfiguration configtemp;

    public static String prefix;

    private ModuleRegistry moduleRegistry;
    private SteamNetworkEngine steamNetworkEngine;

    /*@Override
    public void onLoad() {
        boolean ok = ExternalLibraryBootstrap.ensureCustomBlockData(this);
        if (!ok) {
            getLogger().severe("CustomBlockData could not be loaded. Plugin will be disabled on enable.");
        }
    }*/

    @Override
    public void onEnable() {
        try {
            Class.forName("com.jeff_media.customblockdata.CustomBlockData");
            getLogger().info("CustomBlockData is available.");
        } catch (ClassNotFoundException e) {
            getLogger().severe("CustomBlockData is missing. If you're on Paper, use plugin.yml libraries: to load it.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;

        getCommand("getcustomblockdata").setExecutor(new GetCustomBlockData());
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        saveDefaultConfig();
        configtemp = getConfig();

        prefix = configtemp.getString("prefix");

        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);

        BlockWrapperInstance.setWrapper(new BlockWrapperManager());

        // Core registries/services for modules + steam networks
        this.moduleRegistry = new ModuleRegistry(this);
        this.steamNetworkEngine = new SteamNetworkEngine(this);

        new EnergyBlockUpdate(this).update(configtemp.getInt("update-frequency", 10));

        // Steam transfer tick (optional, can be reworked if not good)
        if (configtemp.getBoolean("steam_transfer_enabled", true)) {
            int period = Math.max(1, configtemp.getInt("network_tick_period", 5));
            int maxNodes = Math.max(250, configtemp.getInt("steam_pathfinder_max_nodes", 5000));
            steamNetworkEngine.start(period, maxNodes);
        }
    }

    @Override
    public void onDisable() {
        if (steamNetworkEngine != null) {
            steamNetworkEngine.stop();
        }
        if (moduleRegistry != null) {
            moduleRegistry.disableAll();
        }
    }

    public static SpigotEnergyAPI getInstance() {
        return instance;
    }

    public static SpigotEnergyAPI corenstance() {
        return instance;
    }

    public ModuleRegistry getModuleRegistry() {
        return moduleRegistry;
    }

    public SteamNetworkEngine getSteamNetworkEngine() {
        return steamNetworkEngine;
    }
}