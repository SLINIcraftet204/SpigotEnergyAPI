package at.slini.spigotenergyapi.remastered.core.Blocks.Managers;

import at.slini.spigotenergyapi.remastered.api.BlockWrapper;
import at.slini.spigotenergyapi.remastered.api.Interfaces.EnergyBlock;
import at.slini.spigotenergyapi.remastered.api.Interfaces.EnergyConsumerBlock;
import at.slini.spigotenergyapi.remastered.core.Blocks.impls.EnergyBlockimpl;
import at.slini.spigotenergyapi.remastered.core.Blocks.impls.EnergyConsumerimpl;
import org.bukkit.block.Block;

public class BlockWrapperManager implements BlockWrapper {
    @Override
    public EnergyBlock wrapEnergyBlock(Block block) {
        return new EnergyBlockimpl(block);
    }

    @Override
    public EnergyConsumerBlock wrapEnergyConsumerBlock(Block block) {
        return new EnergyConsumerimpl(block);
    }
}
