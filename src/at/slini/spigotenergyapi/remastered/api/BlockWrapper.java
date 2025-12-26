package at.slini.spigotenergyapi.remastered.api;

import at.slini.spigotenergyapi.remastered.api.Interfaces.EnergyBlock;
import at.slini.spigotenergyapi.remastered.api.Interfaces.EnergyConsumerBlock;
import org.bukkit.block.Block;

public interface BlockWrapper {
     EnergyBlock wrapEnergyBlock(Block block);
     EnergyConsumerBlock wrapEnergyConsumerBlock(Block block);
}
