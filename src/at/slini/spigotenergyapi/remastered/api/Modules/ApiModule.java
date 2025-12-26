package at.slini.spigotenergyapi.remastered.api.Modules;

import at.slini.spigotenergyapi.remastered.api.Registry.BlockTypeRegistry;
import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;

/**
 * A module is a separate plugin that "hooks" into this API.
 *
 * Modules typically register custom block types (pipes, machines, tanks) and
 * implement their own listeners/logic.
 */
public interface ApiModule {

    /**
     * A stable id (lowercase) used for namespacing, e.g. "fluxpipes".
     */
    String getModuleId();

    /**
     * Human readable module name. e.g. "FluxPipes Expansion"
     */
    String getDisplayName();

    /**
     * Semantic version string of the module. e.g. "1.0.0"
     */
    String getVersion();

    /**
     * Called when the module is registered. (API being called and hook into all other Expansions)
     */
    void onEnable(SpigotEnergyAPI api, BlockTypeRegistry blockTypeRegistry);

    /**
     * Called when the API is disabling (or module is unregistered).
     */
    void onDisable(SpigotEnergyAPI api);
}
