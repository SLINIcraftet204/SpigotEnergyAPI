package at.slini.spigotenergyapi.remastered.core.Listeners;

import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;
import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.persistence.PersistentDataContainer;

public class BlockListener implements Listener {

    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        removeAllData(block);
    }

    //TODO: Piston move event.

    @EventHandler
    public void BlockExplodeEvent(BlockExplodeEvent event) {
        Block block = event.getBlock();
        removeAllData(block);
    }

    @EventHandler
    public void BlockupdateEvent(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        if (block.getType().isAir()) {
            removeAllData(block);
        }
    }

    private void removeAllData(Block block) {
        PersistentDataContainer customBlockData = new CustomBlockData(block, SpigotEnergyAPI.getInstance());
        for (NamespacedKey key : customBlockData.getKeys()) {
            customBlockData.remove(key);
        }
    }
}
