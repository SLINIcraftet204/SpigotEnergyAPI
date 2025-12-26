package at.slini.spigotenergyapi.remastered.core.Managers;

import com.jeff_media.customblockdata.CustomBlockData;
import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.Optional;

/**
 * Stores a "custom block type id" on any Bukkit block using CustomBlockData.
 *
 * Type ids are expected to be namespaced like: moduleId:typeName
 * Example: fluxpipes:steam_pipe
 */
public final class CustomBlockTypeService {

    private final SpigotEnergyAPI plugin;

    public final NamespacedKey TYPE_ID;
    public final NamespacedKey MODULE_ID;

    public CustomBlockTypeService(SpigotEnergyAPI plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin");
        this.TYPE_ID = new NamespacedKey(plugin, "custom_block_type_id");
        this.MODULE_ID = new NamespacedKey(plugin, "custom_block_module_id");
    }

    public void setType(Block block, String typeId) {
        Objects.requireNonNull(block, "block");
        Objects.requireNonNull(typeId, "typeId");
        PersistentDataContainer data = new CustomBlockData(block, plugin);
        data.set(TYPE_ID, PersistentDataType.STRING, typeId.toLowerCase());

        // best-effort moduleId extraction
        int idx = typeId.indexOf(':');
        if (idx > 0) {
            data.set(MODULE_ID, PersistentDataType.STRING, typeId.substring(0, idx).toLowerCase());
        }
    }

    public Optional<String> getType(Block block) {
        Objects.requireNonNull(block, "block");
        PersistentDataContainer data = new CustomBlockData(block, plugin);
        String v = data.get(TYPE_ID, PersistentDataType.STRING);
        return Optional.ofNullable(v);
    }

    public boolean hasType(Block block) {
        Objects.requireNonNull(block, "block");
        PersistentDataContainer data = new CustomBlockData(block, plugin);
        return data.has(TYPE_ID, PersistentDataType.STRING);
    }

    public void clear(Block block) {
        Objects.requireNonNull(block, "block");
        PersistentDataContainer data = new CustomBlockData(block, plugin);
        data.remove(TYPE_ID);
        data.remove(MODULE_ID);
    }
}
