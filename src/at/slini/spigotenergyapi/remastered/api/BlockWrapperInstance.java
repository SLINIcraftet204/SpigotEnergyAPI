package at.slini.spigotenergyapi.remastered.api;

import java.util.Objects;

public final class BlockWrapperInstance {

    private static BlockWrapper wrapper;

    private BlockWrapperInstance() {
    }

    public static BlockWrapper getWrapper() {
        if (wrapper == null) {
            throw new IllegalStateException("BlockWrapper has not been configured yet. " +
                    "Call BlockWrapperInstance.setWrapper(...) in your plugin onEnable().");
        }
        return wrapper;
    }

    public static void setWrapper(BlockWrapper wrapper) {
        BlockWrapperInstance.wrapper = Objects.requireNonNull(wrapper, "wrapper");
    }
}
