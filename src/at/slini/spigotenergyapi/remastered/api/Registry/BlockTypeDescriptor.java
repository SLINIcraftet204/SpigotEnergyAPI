package at.slini.spigotenergyapi.remastered.api.Registry;

import org.bukkit.block.BlockFace;

import java.util.EnumSet;
import java.util.Objects;

/**
 * Describes a custom block type that can participate in networks.
 */
public final class BlockTypeDescriptor {

    private final String typeId; // e.g. "fluxpipes:steam_pipe"
    private final NetworkMedium medium;
    private final NodeRole role;
    private final double capacity;
    private final double maxTransferPerTick;
    private final EnumSet<BlockFace> connectFaces;

    public BlockTypeDescriptor(
            String typeId,
            NetworkMedium medium,
            NodeRole role,
            double capacity,
            double maxTransferPerTick,
            EnumSet<BlockFace> connectFaces
    ) {
        this.typeId = Objects.requireNonNull(typeId, "typeId");
        this.medium = Objects.requireNonNull(medium, "medium");
        this.role = Objects.requireNonNull(role, "role");
        this.capacity = Math.max(0.0, capacity);
        this.maxTransferPerTick = Math.max(0.0, maxTransferPerTick);
        this.connectFaces = connectFaces == null
                ? EnumSet.allOf(BlockFace.class)
                : EnumSet.copyOf(connectFaces);
    }

    public String getTypeId() {
        return typeId;
    }

    public NetworkMedium getMedium() {
        return medium;
    }

    public NodeRole getRole() {
        return role;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getMaxTransferPerTick() {
        return maxTransferPerTick;
    }

    public EnumSet<BlockFace> getConnectFaces() {
        return EnumSet.copyOf(connectFaces);
    }
}
