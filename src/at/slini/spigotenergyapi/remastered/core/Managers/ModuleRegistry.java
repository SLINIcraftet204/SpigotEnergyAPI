package at.slini.spigotenergyapi.remastered.core.Managers;

import at.slini.spigotenergyapi.remastered.api.Modules.ApiModule;
import at.slini.spigotenergyapi.remastered.api.Registry.BlockTypeRegistry;
import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Runtime registry for modules that hook into the API.
 */
public final class ModuleRegistry {

    private final SpigotEnergyAPI api;
    private final BlockTypeRegistryImpl blockTypeRegistry = new BlockTypeRegistryImpl();

    private final Map<String, RegisteredModule> modules = new LinkedHashMap<>();

    public ModuleRegistry(SpigotEnergyAPI api) {
        this.api = Objects.requireNonNull(api, "api");

        // Register a few built-in placeholder types (optional)
        // Modules can replace them with their own.
    }

    public BlockTypeRegistry getBlockTypeRegistry() {
        return blockTypeRegistry;
    }

    public Map<String, RegisteredModule> getModules() {
        return Collections.unmodifiableMap(modules);
    }

    /**
     * Register a module from another plugin.
     */
    public void register(Plugin plugin, ApiModule module) {
        Objects.requireNonNull(plugin, "plugin");
        Objects.requireNonNull(module, "module");

        String id = module.getModuleId();
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("moduleId is empty");
        }
        id = id.toLowerCase();

        RegisteredModule reg = new RegisteredModule(id, plugin, module);
        modules.put(id, reg);
        module.onEnable(api, blockTypeRegistry);
    }

    public void unregister(String moduleId) {
        if (moduleId == null) {
            return;
        }
        RegisteredModule reg = modules.remove(moduleId.toLowerCase());
        if (reg != null) {
            try {
                reg.module.onDisable(api);
            } catch (Throwable ignored) {
                // don't fail shutdown
            }
        }
    }

    public void disableAll() {
        for (String id : modules.keySet().toArray(new String[0])) {
            unregister(id);
        }
    }

    public static final class RegisteredModule {
        public final String moduleId;
        public final Plugin plugin;
        public final ApiModule module;

        private RegisteredModule(String moduleId, Plugin plugin, ApiModule module) {
            this.moduleId = moduleId;
            this.plugin = plugin;
            this.module = module;
        }
    }
}
