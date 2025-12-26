package at.slini.spigotenergyapi.remastered.api.Interfaces;

import at.slini.spigotenergyapi.remastered.api.Emuns.EnergyBlockTypes;
import org.bukkit.block.Block;

import java.util.List;

public interface EnergyBlock extends CustomDataBlock {

    Block getBlock();
    double getStoredEnergyForBlock();
    void setStoredEnergyForBlock(double setstoredEnergy);
    void addStoredEnergyForBlock(double addstoredEnergy);
    void removeStoredEnergyForBlock(double removestoredEnergy);
    void resetStoredEnergyForBlock();

    /**
     * removeStoredEnergyBlock remove the Energy parameter for thist block.
     */
    void removeStoredEnergyBlock();
    boolean hasStoredEnergyinBlock();
    List<EnergyBlock> getBlocksWithStoredEnergyinChunk();
    List<EnergyBlock> getAllLoadedBlocksWithStoredEnergyInWorld();
    void setEnergyBlockType(EnergyBlockTypes energyBlockType);
    boolean hasEnergyBlockType(EnergyBlockTypes energyBlockTypes);
}
