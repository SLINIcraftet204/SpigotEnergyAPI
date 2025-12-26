package at.slini.spigotenergyapi.remastered.core.Network;

import at.slini.spigotenergyapi.remastered.api.Registry.BlockTypeDescriptor;
import at.slini.spigotenergyapi.remastered.api.Registry.NetworkMedium;
import at.slini.spigotenergyapi.remastered.api.Registry.NodeRole;
import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;
import at.slini.spigotenergyapi.remastered.core.Managers.CustomBlockTypeService;
import at.slini.spigotenergyapi.remastered.core.Managers.SteamStorageService;
import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * Very small first implementation of a Steam network tick.
 *
 * What it does:
 * - Scans loaded chunks for blocks that have a registered custom block type.
 * - For STEAM producers with stored steam, finds the nearest reachable STEAM consumer/storage via pipes.
 * - Transfers steam respecting maxTransferPerTick and consumer capacity.
 *
 * This is intentionally simple and meant as an API foundation.
 */
public final class SteamNetworkEngine {

    private static final BlockFace[] CARDINAL_FACES = new BlockFace[]{
            BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN
    };

    private final SpigotEnergyAPI api;
    private final CustomBlockTypeService typeService;
    private final SteamStorageService steamStorage;

    private BukkitTask task;
    private int maxNodes = 5000;

    public SteamNetworkEngine(SpigotEnergyAPI api) {
        this.api = Objects.requireNonNull(api, "api");
        this.typeService = new CustomBlockTypeService(api);
        this.steamStorage = new SteamStorageService(api);
    }

    public void start(int periodTicks, int maxNodes) {
        stop();
        this.maxNodes = Math.max(250, maxNodes);
        this.task = Bukkit.getScheduler().runTaskTimer(api, this::tickAllWorlds, periodTicks, periodTicks);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public CustomBlockTypeService getTypeService() {
        return typeService;
    }

    public SteamStorageService getSteamStorage() {
        return steamStorage;
    }

    private void tickAllWorlds() {
        for (World world : Bukkit.getWorlds()) {
            tickWorld(world);
        }
    }

    public void tickWorld(World world) {
        if (world == null) return;

        // Find producers
        for (Chunk chunk : world.getLoadedChunks()) {
            for (Block block : CustomBlockData.getBlocksWithCustomData(api, chunk)) {
                Optional<String> typeIdOpt = typeService.getType(block);
                if (typeIdOpt.isEmpty()) continue;

                BlockTypeDescriptor desc = api.getModuleRegistry().getBlockTypeRegistry().get(typeIdOpt.get()).orElse(null);
                if (desc == null) continue;
                if (desc.getMedium() != NetworkMedium.STEAM) continue;
                if (desc.getRole() != NodeRole.PRODUCER) continue;

                double available = steamStorage.getStoredSteam(block);
                if (available <= 0.0) continue;

                transferFromProducer(block, desc, available);
            }
        }
    }

    /**
     * Public pathfinder helper for modules.
     * Returns a path (including start and goal) if the goal is reachable through STEAM nodes.
     */
    public Optional<List<Block>> findPath(Block start, Block goal, int maxNodes) {
        if (start == null || goal == null) return Optional.empty();
        maxNodes = Math.max(50, maxNodes);

        ArrayDeque<Block> queue = new ArrayDeque<>();
        Map<Block, Block> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        parent.put(start, null);
        visited.add(key(start));

        int processed = 0;
        while (!queue.isEmpty() && processed++ < maxNodes) {
            Block current = queue.poll();
            if (current.equals(goal)) {
                ArrayList<Block> path = new ArrayList<>();
                Block cur = goal;
                while (cur != null) {
                    path.add(cur);
                    cur = parent.get(cur);
                }
                Collections.reverse(path);
                return Optional.of(path);
            }

            BlockTypeDescriptor currentDesc = descriptorOf(current);
            if (currentDesc == null || currentDesc.getMedium() != NetworkMedium.STEAM) continue;

            for (BlockFace face : CARDINAL_FACES) {
                if (!currentDesc.getConnectFaces().contains(face)) continue;
                Block neighbor = current.getRelative(face);
                BlockTypeDescriptor neighborDesc = descriptorOf(neighbor);
                if (neighborDesc == null || neighborDesc.getMedium() != NetworkMedium.STEAM) continue;
                if (!neighborDesc.getConnectFaces().contains(face.getOppositeFace())) continue;

                String k = key(neighbor);
                if (!visited.add(k)) continue;
                parent.put(neighbor, current);
                queue.add(neighbor);
            }
        }
        return Optional.empty();
    }

    private void transferFromProducer(Block producer, BlockTypeDescriptor producerDesc, double available) {
        PathResult target = findNearestConsumerOrStorage(producer);
        if (target == null) return;

        Block consumer = target.goal;
        BlockTypeDescriptor consumerDesc = target.goalDesc;

        double consumerStored = steamStorage.getStoredSteam(consumer);
        double consumerCapacity = consumerDesc.getCapacity();
        if (consumerCapacity <= 0.0) return;
        double free = Math.max(0.0, consumerCapacity - consumerStored);
        if (free <= 0.0) return;

        double producerLimit = producerDesc.getMaxTransferPerTick() <= 0 ? Double.MAX_VALUE : producerDesc.getMaxTransferPerTick();
        double consumerLimit = consumerDesc.getMaxTransferPerTick() <= 0 ? Double.MAX_VALUE : consumerDesc.getMaxTransferPerTick();
        double pathLimit = pathMaxTransferPerTick(target.path);

        double amount = Math.min(available, free);
        amount = Math.min(amount, producerLimit);
        amount = Math.min(amount, consumerLimit);
        amount = Math.min(amount, pathLimit);

        if (amount <= 0.0) return;

        steamStorage.removeSteam(producer, amount);
        steamStorage.addSteam(consumer, amount);
    }

    private double pathMaxTransferPerTick(List<Block> path) {
        // path includes producer and consumer. We only limit by nodes with a configured maxTransferPerTick.
        double limit = Double.MAX_VALUE;
        for (Block b : path) {
            String typeId = typeService.getType(b).orElse(null);
            if (typeId == null) continue;
            BlockTypeDescriptor desc = api.getModuleRegistry().getBlockTypeRegistry().get(typeId).orElse(null);
            if (desc == null) continue;
            double l = desc.getMaxTransferPerTick();
            if (l > 0.0) {
                limit = Math.min(limit, l);
            }
        }
        return limit;
    }

    private PathResult findNearestConsumerOrStorage(Block start) {
        // BFS over connected STEAM nodes
        ArrayDeque<Block> queue = new ArrayDeque<>();
        Map<Block, Block> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        parent.put(start, null);
        visited.add(key(start));

        int processed = 0;
        while (!queue.isEmpty() && processed++ < maxNodes) {
            Block current = queue.poll();

            // Goal check (but do not select start itself)
            if (!current.equals(start)) {
                String typeId = typeService.getType(current).orElse(null);
                if (typeId != null) {
                    BlockTypeDescriptor desc = api.getModuleRegistry().getBlockTypeRegistry().get(typeId).orElse(null);
                    if (desc != null && desc.getMedium() == NetworkMedium.STEAM) {
                        if (desc.getRole() == NodeRole.CONSUMER || desc.getRole() == NodeRole.STORAGE) {
                            return buildPathResult(current, desc, parent);
                        }
                    }
                }
            }

            BlockTypeDescriptor currentDesc = descriptorOf(current);
            if (currentDesc == null) continue;
            if (currentDesc.getMedium() != NetworkMedium.STEAM) continue;

            // Traversal allowed roles
            if (currentDesc.getRole() != NodeRole.PIPE && currentDesc.getRole() != NodeRole.PRODUCER && currentDesc.getRole() != NodeRole.STORAGE) {
                // do not traverse through consumers (endpoints), but still can be goal above
                continue;
            }

            for (BlockFace face : CARDINAL_FACES) {
                if (!currentDesc.getConnectFaces().contains(face)) continue;
                Block neighbor = current.getRelative(face);

                BlockTypeDescriptor neighborDesc = descriptorOf(neighbor);
                if (neighborDesc == null) continue;
                if (neighborDesc.getMedium() != NetworkMedium.STEAM) continue;

                // must also allow reverse connection
                BlockFace opposite = face.getOppositeFace();
                if (!neighborDesc.getConnectFaces().contains(opposite)) continue;

                String k = key(neighbor);
                if (!visited.add(k)) continue;
                parent.put(neighbor, current);
                queue.add(neighbor);
            }
        }
        return null;
    }

    private BlockTypeDescriptor descriptorOf(Block block) {
        String typeId = typeService.getType(block).orElse(null);
        if (typeId == null) return null;
        return api.getModuleRegistry().getBlockTypeRegistry().get(typeId).orElse(null);
    }

    private static String key(Block b) {
        return b.getWorld().getName() + ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();
    }

    private PathResult buildPathResult(Block goal, BlockTypeDescriptor goalDesc, Map<Block, Block> parent) {
        ArrayList<Block> path = new ArrayList<>();
        Block cur = goal;
        while (cur != null) {
            path.add(cur);
            cur = parent.get(cur);
        }
        Collections.reverse(path);
        return new PathResult(goal, goalDesc, path);
    }

    private static final class PathResult {
        final Block goal;
        final BlockTypeDescriptor goalDesc;
        final List<Block> path;

        private PathResult(Block goal, BlockTypeDescriptor goalDesc, List<Block> path) {
            this.goal = goal;
            this.goalDesc = goalDesc;
            this.path = path;
        }
    }
}
