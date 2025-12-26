package at.slini.spigotenergyapi.remastered.core.Managers;

import com.jeff_media.customblockdata.CustomBlockData;
import at.slini.spigotenergyapi.remastered.api.BlockWrapperInstance;
import at.slini.spigotenergyapi.remastered.api.Emuns.EnergyBlockTypes;
import at.slini.spigotenergyapi.remastered.api.Interfaces.EnergyBlock;
import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class EnergyProvider implements Listener {
    public static final NamespacedKey storedEnergy = new NamespacedKey(SpigotEnergyAPI.getInstance(), "stored_energy");
    public static final NamespacedKey maxEnergy = new NamespacedKey(SpigotEnergyAPI.getInstance(), "max_energy");
    public static final NamespacedKey ID = new NamespacedKey(SpigotEnergyAPI.getInstance(), "energy_block_id");
    public static final NamespacedKey EnergyBlockType = new NamespacedKey(SpigotEnergyAPI.getInstance(), "energyblocktype");

    public static double getStoredEnergyForBlock(EnergyBlock block) {
        PersistentDataContainer customBlockData = new CustomBlockData(block, SpigotEnergyAPI.getInstance());

        return Double.valueOf(customBlockData.get(storedEnergy, PersistentDataType.DOUBLE));
    }

    public static void setStoredEnergyForBlock(EnergyBlock block, Double setstoredEnergy) {
        PersistentDataContainer customBlockData = new CustomBlockData(block, SpigotEnergyAPI.getInstance());

        customBlockData.set(storedEnergy, PersistentDataType.DOUBLE, setstoredEnergy);
    }

    public static void addStoredEnergyForBlock(EnergyBlock block, Double addstoredEnergy) {
        PersistentDataContainer customBlockData = new CustomBlockData(block, SpigotEnergyAPI.getInstance());

        if (customBlockData.getKeys().equals(storedEnergy)) {
            customBlockData.set(storedEnergy, PersistentDataType.DOUBLE, getStoredEnergyForBlock(block) + addstoredEnergy);
        } else {
            customBlockData.set(storedEnergy, PersistentDataType.DOUBLE, addstoredEnergy);
        }
    }

    public static void removeStoredEnergyForBlock(EnergyBlock block, Double removestoredEnergy) {
        PersistentDataContainer customBlockData = new CustomBlockData(block, SpigotEnergyAPI.getInstance());

        customBlockData.set(storedEnergy, PersistentDataType.DOUBLE, getStoredEnergyForBlock(block) - removestoredEnergy);
    }

    public static void resetStoredEnergyForBlock(EnergyBlock block) {
        PersistentDataContainer customBlockData = new CustomBlockData(block, SpigotEnergyAPI.getInstance());

        customBlockData.set(storedEnergy, PersistentDataType.DOUBLE, 0.0);
    }

    public static void removeStoredEnergyBlock(EnergyBlock block) {
        PersistentDataContainer customBlockData = new CustomBlockData(block, SpigotEnergyAPI.getInstance());

        customBlockData.remove(storedEnergy);
    }

    public static boolean hasStoredEnergyInBlock(EnergyBlock block) {
        PersistentDataContainer customBlockData = new CustomBlockData(block, SpigotEnergyAPI.getInstance());
        return customBlockData.has(storedEnergy, PersistentDataType.DOUBLE);
    }

    public static List<EnergyBlock> getBlocksWithStoredEnergyInChunk(Chunk chunk) {
        List<EnergyBlock> Blocks = new ArrayList<>();
        for (Block block : CustomBlockData.getBlocksWithCustomData(SpigotEnergyAPI.getInstance(), chunk)) {
            if (!(hasStoredEnergyInBlock(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block)))) {
                continue;
            }
            Blocks.add(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block));
        }
        return Blocks;
    }

    public static List<EnergyBlock> getAllLoadedBlocksWithStoredEnergyInWorld(World world) {
        List<EnergyBlock> Blocks = new ArrayList<>();
        for (Chunk chunk : Objects.requireNonNull(world.getLoadedChunks())) {
            Blocks.addAll(EnergyProvider.getBlocksWithStoredEnergyInChunk(chunk));
        }
        return Blocks;
    }

    public static void setEnergyBlockTypeToBlock(EnergyBlock energyBlock, EnergyBlockTypes energyBlockType) {
        PersistentDataContainer customBlockData = new CustomBlockData(energyBlock, SpigotEnergyAPI.getInstance());

        customBlockData.set(EnergyBlockType, PersistentDataType.STRING, energyBlockType.toString());
    }

    public static boolean EnergyBlockequalsEnergyBlockType(EnergyBlock energyBlock, EnergyBlockTypes energyBlockType) {
        PersistentDataContainer customBlockData = new CustomBlockData(energyBlock, SpigotEnergyAPI.getInstance());

        if(customBlockData.get(EnergyBlockType, PersistentDataType.STRING) == energyBlock.toString()){
            return true;
        } else {
            return false;
        }
    }
}