package at.slini.spigotenergyapi.remastered.api;

public class BlockWrapperInstance {
    private static BlockWrapper wrapper;

    public static BlockWrapper getWrapper() {
        return wrapper;
    }

    public static void setWrapper(BlockWrapper wrapper) {
        BlockWrapperInstance.wrapper = wrapper;
    }
}
