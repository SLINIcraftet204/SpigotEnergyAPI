package at.slini.spigotenergyapi.remastered.core.Utils;

import at.slini.spigotenergyapi.remastered.api.Events.EnergyBlockUpdateEvent;
import at.slini.spigotenergyapi.remastered.api.Interfaces.EnergyBlock;
import at.slini.spigotenergyapi.remastered.core.Managers.EnergyStorageService;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * Periodically fires an {@link EnergyBlockUpdateEvent} for every loaded block that has stored energy.
 */
public final class EnergyBlockUpdate {

    private final Plugin plugin;
    private BukkitTask task;

    public EnergyBlockUpdate(Plugin plugin) {
        this.plugin = plugin;
    }

    public void start(int periodTicks) {
        stop();
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (World world : Bukkit.getWorlds()) {
                for (EnergyBlock energyBlock : EnergyStorageService.getAllLoadedBlocksWithStoredEnergyInWorld(world)) {
                    Bukkit.getPluginManager().callEvent(new EnergyBlockUpdateEvent(energyBlock));
                }
            }
        }, 0L, Math.max(1, periodTicks));
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
}
