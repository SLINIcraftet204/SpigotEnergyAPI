package at.slini.spigotenergyapi.remastered.api.Steam;

import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;
import org.bukkit.block.Block;

/**
 * Small static facade for steam storage.
 */
public final class SteamApi {

    private SteamApi() {
    }

    public static double getStoredSteam(Block block) {
        SpigotEnergyAPI api = SpigotEnergyAPI.getInstance();
        if (api == null) {
            return 0.0;
        }
        return api.getSteamNetworkEngine().getSteamStorage().getStoredSteam(block);
    }

    public static void setStoredSteam(Block block, double amount) {
        SpigotEnergyAPI api = SpigotEnergyAPI.getInstance();
        if (api == null) {
            throw new IllegalStateException("SpigotEnergyAPI is not enabled");
        }
        api.getSteamNetworkEngine().getSteamStorage().setStoredSteam(block, amount);
    }

    public static void addSteam(Block block, double amount) {
        SpigotEnergyAPI api = SpigotEnergyAPI.getInstance();
        if (api == null) {
            throw new IllegalStateException("SpigotEnergyAPI is not enabled");
        }
        api.getSteamNetworkEngine().getSteamStorage().addSteam(block, amount);
    }

    public static void removeSteam(Block block, double amount) {
        SpigotEnergyAPI api = SpigotEnergyAPI.getInstance();
        if (api == null) {
            throw new IllegalStateException("SpigotEnergyAPI is not enabled");
        }
        api.getSteamNetworkEngine().getSteamStorage().removeSteam(block, amount);
    }
}
