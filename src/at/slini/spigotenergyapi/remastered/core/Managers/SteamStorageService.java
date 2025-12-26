package at.slini.spigotenergyapi.remastered.core.Managers;

import com.jeff_media.customblockdata.CustomBlockData;
import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

/**
 * Basic Steam storage on blocks.
 *
 * Steam is represented as a stored amount (double) plus optional pressure/temperature values.
 */
public final class SteamStorageService {

    private final SpigotEnergyAPI plugin;

    public final NamespacedKey STORED_STEAM;
    public final NamespacedKey MAX_STEAM;
    public final NamespacedKey STEAM_PRESSURE;
    public final NamespacedKey STEAM_TEMPERATURE;

    public SteamStorageService(SpigotEnergyAPI plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin");
        this.STORED_STEAM = new NamespacedKey(plugin, "stored_steam");
        this.MAX_STEAM = new NamespacedKey(plugin, "max_steam");
        this.STEAM_PRESSURE = new NamespacedKey(plugin, "steam_pressure");
        this.STEAM_TEMPERATURE = new NamespacedKey(plugin, "steam_temperature");
    }

    public double getStoredSteam(Block block) {
        PersistentDataContainer data = new CustomBlockData(Objects.requireNonNull(block, "block"), plugin);
        Double v = data.get(STORED_STEAM, PersistentDataType.DOUBLE);
        return v == null ? 0.0 : v;
    }

    public void setStoredSteam(Block block, double amount) {
        PersistentDataContainer data = new CustomBlockData(Objects.requireNonNull(block, "block"), plugin);
        data.set(STORED_STEAM, PersistentDataType.DOUBLE, Math.max(0.0, amount));
    }

    public void addSteam(Block block, double amount) {
        if (amount <= 0) return;
        setStoredSteam(block, getStoredSteam(block) + amount);
    }

    public void removeSteam(Block block, double amount) {
        if (amount <= 0) return;
        setStoredSteam(block, Math.max(0.0, getStoredSteam(block) - amount));
    }

    public boolean hasSteam(Block block) {
        PersistentDataContainer data = new CustomBlockData(Objects.requireNonNull(block, "block"), plugin);
        return data.has(STORED_STEAM, PersistentDataType.DOUBLE);
    }

    public void clear(Block block) {
        PersistentDataContainer data = new CustomBlockData(Objects.requireNonNull(block, "block"), plugin);
        data.remove(STORED_STEAM);
        data.remove(MAX_STEAM);
        data.remove(STEAM_PRESSURE);
        data.remove(STEAM_TEMPERATURE);
    }
}
