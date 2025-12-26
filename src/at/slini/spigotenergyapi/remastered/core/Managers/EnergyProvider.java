package at.slini.spigotenergyapi.remastered.core.Managers;

import at.slini.spigotenergyapi.remastered.api.Emuns.EnergyBlockTypes;
import at.slini.spigotenergyapi.remastered.api.Interfaces.EnergyBlock;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.List;

/**
 * @deprecated Use {@link EnergyStorageService} instead.
 *
 * Kept for backwards compatibility with older modules.
 */
@Deprecated
public final class EnergyProvider {

    private EnergyProvider() {
    }

    public static double getStoredEnergyForBlock(EnergyBlock block) {
        return EnergyStorageService.getStoredEnergy(block);
    }

    public static void setStoredEnergyForBlock(EnergyBlock block, Double setstoredEnergy) {
        EnergyStorageService.setStoredEnergy(block, setstoredEnergy != null ? setstoredEnergy : 0.0D);
    }

    public static void addStoredEnergyForBlock(EnergyBlock block, Double addstoredEnergy) {
        EnergyStorageService.addStoredEnergy(block, addstoredEnergy != null ? addstoredEnergy : 0.0D);
    }

    public static void removeStoredEnergyForBlock(EnergyBlock block, Double removestoredEnergy) {
        EnergyStorageService.removeStoredEnergy(block, removestoredEnergy != null ? removestoredEnergy : 0.0D);
    }

    public static void resetStoredEnergyForBlock(EnergyBlock block) {
        EnergyStorageService.resetStoredEnergy(block);
    }

    public static void removeStoredEnergyBlock(EnergyBlock block) {
        EnergyStorageService.removeStoredEnergyKey(block);
    }

    public static boolean hasStoredEnergyInBlock(EnergyBlock block) {
        return EnergyStorageService.hasStoredEnergy(block);
    }

    public static List<EnergyBlock> getBlocksWithStoredEnergyInChunk(Chunk chunk) {
        return EnergyStorageService.getBlocksWithStoredEnergyInChunk(chunk);
    }

    public static List<EnergyBlock> getAllLoadedBlocksWithStoredEnergyInWorld(World world) {
        return EnergyStorageService.getAllLoadedBlocksWithStoredEnergyInWorld(world);
    }

    public static void setEnergyBlockTypeToBlock(EnergyBlock energyBlock, EnergyBlockTypes energyBlockType) {
        EnergyStorageService.setEnergyBlockType(energyBlock, energyBlockType);
    }

    public static boolean EnergyBlockequalsEnergyBlockType(EnergyBlock energyBlock, EnergyBlockTypes energyBlockType) {
        return EnergyStorageService.hasEnergyBlockType(energyBlock, energyBlockType);
    }
}
