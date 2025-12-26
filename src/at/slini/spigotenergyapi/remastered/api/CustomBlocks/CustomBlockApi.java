package at.slini.spigotenergyapi.remastered.api.CustomBlocks;

import at.slini.spigotenergyapi.remastered.api.Registry.BlockTypeDescriptor;
import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;
import org.bukkit.block.Block;

import java.util.Optional;

/**
 * Small static facade for modules.
 *
 * Lets modules tag a placed block with a registered custom block type id.
 */
public final class CustomBlockApi {

    private CustomBlockApi() {
    }

    public static void setType(Block block, String typeId) {
        SpigotEnergyAPI api = SpigotEnergyAPI.getInstance();
        if (api == null) {
            throw new IllegalStateException("SpigotEnergyAPI is not enabled");
        }
        api.getSteamNetworkEngine().getTypeService().setType(block, typeId);
    }

    public static Optional<String> getType(Block block) {
        SpigotEnergyAPI api = SpigotEnergyAPI.getInstance();
        if (api == null) {
            return Optional.empty();
        }
        return api.getSteamNetworkEngine().getTypeService().getType(block);
    }

    public static Optional<BlockTypeDescriptor> getDescriptor(Block block) {
        SpigotEnergyAPI api = SpigotEnergyAPI.getInstance();
        if (api == null) {
            return Optional.empty();
        }
        String typeId = api.getSteamNetworkEngine().getTypeService().getType(block).orElse(null);
        if (typeId == null) {
            return Optional.empty();
        }
        return api.getModuleRegistry().getBlockTypeRegistry().get(typeId);
    }

    public static void clear(Block block) {
        SpigotEnergyAPI api = SpigotEnergyAPI.getInstance();
        if (api == null) {
            return;
        }
        api.getSteamNetworkEngine().getTypeService().clear(block);
    }
}
