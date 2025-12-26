package at.slini.spigotenergyapi.remastered.core.Utils;

import at.slini.spigotenergyapi.remastered.api.BlockWrapperInstance;
import at.slini.spigotenergyapi.remastered.api.Events.EnergyBlockUpdateEvent;
import at.slini.spigotenergyapi.remastered.api.Interfaces.EnergyBlock;
import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class EnergyBlockUpdate {
    private final Plugin plugin;
    BukkitTask testScheduler;
    public EnergyBlockUpdate(Plugin plugin) {
        this.plugin = plugin;
    }

    public void update(int delay) {
        testScheduler = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (World world : Bukkit.getWorlds()) {
                for (EnergyBlock energyBlock : BlockWrapperInstance.getWrapper().wrapEnergyBlock(world.getBlockAt(0, 0, 0)).getAllLoadedBlocksWithStoredEnergyInWorld()) {
                    Bukkit.getPluginManager().callEvent(new EnergyBlockUpdateEvent(energyBlock));
                }
            }
        },  0, delay);
    }
}