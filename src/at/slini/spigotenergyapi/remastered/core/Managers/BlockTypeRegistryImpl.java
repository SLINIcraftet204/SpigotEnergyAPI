package at.slini.spigotenergyapi.remastered.core.Managers;

import at.slini.spigotenergyapi.remastered.api.Registry.BlockTypeDescriptor;
import at.slini.spigotenergyapi.remastered.api.Registry.BlockTypeRegistry;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class BlockTypeRegistryImpl implements BlockTypeRegistry {

    private final Map<String, BlockTypeDescriptor> types = new ConcurrentHashMap<>();

    @Override
    public void register(BlockTypeDescriptor descriptor) {
        if (descriptor == null) {
            throw new IllegalArgumentException("descriptor is null");
        }
        types.put(descriptor.getTypeId().toLowerCase(), descriptor);
    }

    @Override
    public Optional<BlockTypeDescriptor> get(String typeId) {
        if (typeId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(types.get(typeId.toLowerCase()));
    }

    @Override
    public Collection<BlockTypeDescriptor> all() {
        return Collections.unmodifiableCollection(types.values());
    }
}
