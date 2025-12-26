package at.slini.spigotenergyapi.remastered.core.Managers;

import at.slini.spigotenergyapi.remastered.api.BlockWrapperInstance;
import at.slini.spigotenergyapi.remastered.api.Emuns.EnergyBlockTypes;
import at.slini.spigotenergyapi.remastered.api.Interfaces.EnergyBlock;
import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;
import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Central place for reading/writing energy-related custom block data.
 *
 * Uses CustomBlockData (Jeff Media) so data survives chunk unloads and server restarts.
 */
public final class EnergyStorageService {

    private EnergyStorageService() {
    }

    public static final NamespacedKey STORED_ENERGY = new NamespacedKey(SpigotEnergyAPI.getInstance(), "stored_energy");
    public static final NamespacedKey MAX_ENERGY = new NamespacedKey(SpigotEnergyAPI.getInstance(), "max_energy");
    public static final NamespacedKey BLOCK_ID = new NamespacedKey(SpigotEnergyAPI.getInstance(), "energy_block_id");
    public static final NamespacedKey ENERGY_BLOCK_TYPE = new NamespacedKey(SpigotEnergyAPI.getInstance(), "energy_block_type");

    private static PersistentDataContainer data(EnergyBlock block) {
        return new CustomBlockData(block, SpigotEnergyAPI.getInstance());
    }

    public static double getStoredEnergy(EnergyBlock block) {
        Double value = data(block).get(STORED_ENERGY, PersistentDataType.DOUBLE);
        return value != null ? value : 0.0D;
    }

    public static void setStoredEnergy(EnergyBlock block, double value) {
        data(block).set(STORED_ENERGY, PersistentDataType.DOUBLE, value);
    }

    public static void addStoredEnergy(EnergyBlock block, double delta) {
        setStoredEnergy(block, getStoredEnergy(block) + delta);
    }

    public static void removeStoredEnergy(EnergyBlock block, double delta) {
        setStoredEnergy(block, getStoredEnergy(block) - delta);
    }

    public static void resetStoredEnergy(EnergyBlock block) {
        setStoredEnergy(block, 0.0D);
    }

    public static void removeStoredEnergyKey(EnergyBlock block) {
        data(block).remove(STORED_ENERGY);
    }

    public static boolean hasStoredEnergy(EnergyBlock block) {
        return data(block).has(STORED_ENERGY, PersistentDataType.DOUBLE);
    }

    public static List<EnergyBlock> getBlocksWithStoredEnergyInChunk(Chunk chunk) {
        List<EnergyBlock> blocks = new ArrayList<>();

        for (Block block : CustomBlockData.getBlocksWithCustomData(SpigotEnergyAPI.getInstance(), chunk)) {
            PersistentDataContainer pdc = new CustomBlockData(block, SpigotEnergyAPI.getInstance());
            if (!pdc.has(STORED_ENERGY, PersistentDataType.DOUBLE)) {
                continue;
            }
            // Wrap as EnergyBlock via the current wrapper
            blocks.add(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block));
        }

        return blocks;
    }

    public static List<EnergyBlock> getAllLoadedBlocksWithStoredEnergyInWorld(World world) {
        List<EnergyBlock> blocks = new ArrayList<>();
        for (Chunk chunk : Objects.requireNonNull(world.getLoadedChunks())) {
            blocks.addAll(getBlocksWithStoredEnergyInChunk(chunk));
        }
        return blocks;
    }

    public static void setEnergyBlockType(EnergyBlock block, EnergyBlockTypes type) {
        data(block).set(ENERGY_BLOCK_TYPE, PersistentDataType.STRING, type.name());
    }

    public static boolean hasEnergyBlockType(EnergyBlock block, EnergyBlockTypes type) {
        String stored = data(block).get(ENERGY_BLOCK_TYPE, PersistentDataType.STRING);
        return stored != null && stored.equalsIgnoreCase(type.name());
    }
}
