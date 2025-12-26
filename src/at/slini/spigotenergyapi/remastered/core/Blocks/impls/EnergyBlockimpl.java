package at.slini.spigotenergyapi.remastered.core.Blocks.impls;

import at.slini.spigotenergyapi.remastered.api.BlockWrapperInstance;
import at.slini.spigotenergyapi.remastered.api.Emuns.EnergyBlockTypes;
import at.slini.spigotenergyapi.remastered.api.Interfaces.EnergyBlock;
import at.slini.spigotenergyapi.remastered.core.Managers.EnergyProvider;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.bukkit.util.VoxelShape;

import java.util.*;

public class EnergyBlockimpl implements EnergyBlock{
    private final Block block;

    public EnergyBlockimpl(Block block) {
        this.block = block;
    }

    @Override
    public BlockData getBlockData() {
        return block.getBlockData();
    }

    @Deprecated
    @Override
    public byte getData() {
        return block.getData();
    }

    @Override
    public Block getRelative(int i, int i1, int i2) {
        return block.getRelative(i, i1, i2);
    }

    @Override
    public Block getRelative(BlockFace blockFace) {
        return block.getRelative(blockFace);
    }

    @Override
    public Block getRelative(BlockFace blockFace, int i) {
        return block.getRelative(blockFace, i);
    }

    @Override
    public Material getType() {
        return block.getType();
    }

    @Override
    public byte getLightLevel() {
        return block.getLightLevel();
    }

    @Override
    public byte getLightFromSky() {
        return block.getLightFromSky();
    }

    @Override
    public byte getLightFromBlocks() {
        return block.getLightFromBlocks();
    }

    @Override
    public World getWorld() {
        return block.getWorld();
    }

    @Override
    public int getX() {
        return block.getX();
    }

    @Override
    public int getY() {
        return block.getY();
    }

    @Override
    public int getZ() {
        return block.getZ();
    }

    @Override
    public Location getLocation() {
        return block.getLocation();
    }

    @Override
    public Location getLocation(Location location) {
        return block.getLocation(location);
    }

    @Override
    public Chunk getChunk() {
        return block.getChunk();
    }

    @Override
    public void setBlockData(BlockData blockData) {
        block.setBlockData(blockData);
    }

    @Override
    public void setBlockData(BlockData blockData, boolean b) {
        block.setBlockData(blockData, b);
    }

    @Override
    public void setType(Material material) {
        block.setType(material);
    }

    @Override
    public void setType(Material material, boolean b) {
        block.setType(material, b);
    }

    @Override
    public BlockFace getFace(Block block) {
        return this.block.getFace(block);
    }

    @Override
    public BlockState getState() {
        return block.getState();
    }

    @Override
    public Biome getBiome() {
        return block.getBiome();
    }

    @Override
    public void setBiome(Biome biome) {
        block.setBiome(biome);
    }

    @Override
    public boolean isBlockPowered() {
        return block.isBlockPowered();
    }

    @Override
    public boolean isBlockIndirectlyPowered() {
        return block.isBlockIndirectlyPowered();
    }

    @Override
    public boolean isBlockFacePowered(BlockFace blockFace) {
        return block.isBlockFacePowered(blockFace);
    }

    @Override
    public boolean isBlockFaceIndirectlyPowered(BlockFace blockFace) {
        return block.isBlockFaceIndirectlyPowered(blockFace);
    }

    @Override
    public int getBlockPower(BlockFace blockFace) {
        return block.getBlockPower(blockFace);
    }

    @Override
    public int getBlockPower() {
        return block.getBlockPower();
    }

    @Override
    public boolean isEmpty() {
        return block.isEmpty();
    }

    @Override
    public boolean isLiquid() {
        return block.isLiquid();
    }

    @Override
    public double getTemperature() {
        return block.getTemperature();
    }

    @Override
    public double getHumidity() {
        return block.getHumidity();
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return block.getPistonMoveReaction();
    }

    @Override
    public boolean breakNaturally() {
        return block.breakNaturally();
    }

    @Override
    public boolean breakNaturally(ItemStack itemStack) {
        return block.breakNaturally(itemStack);
    }

    @Override
    public boolean applyBoneMeal(BlockFace blockFace) {
        return block.applyBoneMeal(blockFace);
    }

    @Override
    public Collection<ItemStack> getDrops() {
        return block.getDrops();
    }

    @Override
    public Collection<ItemStack> getDrops(ItemStack itemStack) {
        return block.getDrops(itemStack);
    }

    @Override
    public Collection<ItemStack> getDrops(ItemStack itemStack, Entity entity) {
        return block.getDrops(itemStack, entity);
    }

    @Override
    public boolean isPreferredTool(ItemStack itemStack) {
        return block.isPreferredTool(itemStack);
    }

    @Override
    public float getBreakSpeed(Player player) {
        return block.getBreakSpeed(player);
    }

    @Override
    public boolean isPassable() {
        return block.isPassable();
    }

    @Override
    public RayTraceResult rayTrace(Location location, Vector vector, double v, FluidCollisionMode fluidCollisionMode) {
        return block.rayTrace(location, vector, v, fluidCollisionMode);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return block.getBoundingBox();
    }

    @Override
    public VoxelShape getCollisionShape() {
        return block.getCollisionShape();
    }

    @Override
    public boolean canPlace(BlockData blockData) {
        return block.canPlace(blockData);
    }

    @Override
    public void setMetadata(String s, MetadataValue metadataValue) {
        block.setMetadata(s, metadataValue);
    }

    @Override
    public List<MetadataValue> getMetadata(String s) {
        return block.getMetadata(s);
    }

    @Override
    public boolean hasMetadata(String s) {
        return block.hasMetadata(s);
    }

    @Override
    public void removeMetadata(String s, Plugin plugin) {
        block.removeMetadata(s, plugin);
    }

    @Override
    public String getTranslationKey() {
        return block.getTranslationKey();
    }
//----------------My API----------------------

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public double getStoredEnergyForBlock() {
        return EnergyProvider.getStoredEnergyForBlock(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block));
    }

    @Override
    public void setStoredEnergyForBlock(double setstoredEnergy) {
        EnergyProvider.setStoredEnergyForBlock(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block), setstoredEnergy);
    }

    @Override
    public void addStoredEnergyForBlock(double addstoredEnergy) {
        EnergyProvider.addStoredEnergyForBlock(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block), addstoredEnergy);
    }

    @Override
    public void removeStoredEnergyForBlock(double removestoredEnergy) {
        EnergyProvider.removeStoredEnergyForBlock(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block), removestoredEnergy);
    }

    @Override
    public void resetStoredEnergyForBlock() {
        EnergyProvider.resetStoredEnergyForBlock(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block));
    }

    @Override
    public void removeStoredEnergyBlock() {
        EnergyProvider.removeStoredEnergyBlock(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block));
    }

    @Override
    public boolean hasStoredEnergyinBlock() {
        return EnergyProvider.hasStoredEnergyInBlock(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block));
    }

    @Override
    public List<EnergyBlock> getBlocksWithStoredEnergyinChunk() {
        return EnergyProvider.getBlocksWithStoredEnergyInChunk(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block).getChunk());
    }

    @Override
    public List<EnergyBlock> getAllLoadedBlocksWithStoredEnergyInWorld() {
        return EnergyProvider.getAllLoadedBlocksWithStoredEnergyInWorld(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block).getWorld());
    }

    @Override
    public void setEnergyBlockType(EnergyBlockTypes energyBlockType) {
        EnergyProvider.setEnergyBlockTypeToBlock(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block), energyBlockType);
    }

    @Override
    public boolean hasEnergyBlockType(EnergyBlockTypes energyBlockTypes) {
        return EnergyProvider.EnergyBlockequalsEnergyBlockType(BlockWrapperInstance.getWrapper().wrapEnergyBlock(block), energyBlockTypes);
    }


    @Override
    public void setCustomDataToBlock(Block block) {
        //TODO: Wir müssen da noch functsionen einbringen
    }
}
