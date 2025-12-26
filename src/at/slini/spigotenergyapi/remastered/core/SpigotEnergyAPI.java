package at.slini.spigotenergyapi.remastered.core;

import at.slini.spigotenergyapi.remastered.api.BlockWrapperInstance;
//import at.slini.spigotenergyapi.remastered.api.spigotenergyapi.Blocks.Managers.BlockWrapperManager;
import at.slini.spigotenergyapi.remastered.core.Blocks.Managers.BlockWrapperManager;
import at.slini.spigotenergyapi.remastered.core.Commands.GetCustomBlockData;
import at.slini.spigotenergyapi.remastered.core.Listeners.BlockListener;
import at.slini.spigotenergyapi.remastered.core.Utils.ConfigUtil;
import at.slini.spigotenergyapi.remastered.core.Utils.EnergyBlockUpdate;
import at.slini.spigotenergyapi.remastered.core.Utils.PlayerActionBar;
import at.slini.spigotenergyapi.remastered.core.Utils.SConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpigotEnergyAPI extends JavaPlugin {

    private static SpigotEnergyAPI instance;

    public static SConfig config;
    public static SConfig RegistertsBlockIds;

    public static String prefix;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("getcustomblockdata").setExecutor(new GetCustomBlockData());
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        config = ConfigUtil.getConfig("config");
        RegistertsBlockIds = ConfigUtil.getConfig("RegistertsBlockIds");

        if(!config.getFile().isFile()) {
            config.setDefault("prefix", "§eSpigotEnergy§7: §r");
            config.setDefault("energyprefix", "SE");
            config.setDefault("energyblockupdatefrequenz", 10);
            config.setDefault("energyshowinactionbar", false);
        }

        prefix = config.getString("prefix");

        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        
        if (config.getBoolean("energyshowinactionbar")) {
            PlayerActionBar.start();
        }

        BlockWrapperInstance.setWrapper(new BlockWrapperManager());

            new EnergyBlockUpdate(this).start(config.getInt("energyblockupdatefrequenz", 10));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SpigotEnergyAPI getInstance() {
        return instance;
    }
}