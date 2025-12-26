package at.slini.spigotenergyapi.remastered.api.Registry;

import java.util.Collection;
import java.util.Optional;

/**
 * Registry for network-capable custom block types.
 */
public interface BlockTypeRegistry {

    /**
     * Registers a descriptor. If the typeId exists, it will be replaced.
     */
    void register(BlockTypeDescriptor descriptor);

    Optional<BlockTypeDescriptor> get(String typeId);

    Collection<BlockTypeDescriptor> all();
}
