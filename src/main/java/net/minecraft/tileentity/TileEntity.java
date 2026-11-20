package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockJukeboxTileEntityJukebox;
import net.minecraft.block.state.BlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntityActualBlockDataValueCallable;
import net.minecraft.tileentity.TileEntityActualBlockTypeCallable;
import net.minecraft.tileentity.TileEntityNameCallable;

import com.google.common.collect.Maps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public abstract class TileEntity {
    public World worldObj;
    private static Map classToNameMap;
    private int blockMetadata;

    private static Map nameToClassMap;
    private static Logger logger;
    private static String[] packedTextFragments;
    protected Block blockType;
    protected boolean tileEntityInvalid;
    public BlockPos pos = BlockPos.ORIGIN;
    private static int[] dO;

    public boolean hasWorldObj() {
        return this.worldObj != null;
    }

    public void addInfoToCrashReport(CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSectionCallable("Name", new TileEntityNameCallable(this));
        if (this.worldObj != null) {
            CrashReportCategory.addBlockInfo(
                    crashReportCategory, this.pos, this.getBlockType(), this.getBlockMetadata());
            crashReportCategory.addCrashSectionCallable(
                    "Actual block type", new TileEntityActualBlockTypeCallable(this));
            crashReportCategory.addCrashSectionCallable(
                    "Actual block data value", new TileEntityActualBlockDataValueCallable(this));
        }
    }

    public void updateContainingBlockInfo() {
        this.blockType = null;
        this.blockMetadata = -1;
    }

    public int getBlockMetadata() {
        if (this.blockMetadata == -1) {
            BlockState blockState = this.worldObj.getBlockState(this.pos);
            this.blockMetadata = blockState.getBlock().getMetaFromState(blockState);
        }

        return this.blockMetadata;
    }

    public void writeToNBT(CompoundTag compoundTag) {
        String text = (String) net.minecraft.tileentity.TileEntity.classToNameMap.get(this.getClass());
        if (text == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }

        compoundTag.setString("id", text);
        compoundTag.setInteger(packedTextFragments[3], this.pos.getX());
        compoundTag.setInteger(packedTextFragments[4], this.pos.getY());
        compoundTag.setInteger(packedTextFragments[5], this.pos.getZ());
    }

    public boolean isInvalid() {
        return this.tileEntityInvalid;
    }

    public double getDistanceSq(double d0, double d1, double d2) {
        double d3 = this.pos.getX() + 0.5 - d0;
        double d4 = this.pos.getY() + 0.5 - d1;
        double d5 = this.pos.getZ() + 0.5 - d2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    // region String decoding support

    // endregion

    public void markDirty() {
        if (this.worldObj != null) {
            BlockState blockState = this.worldObj.getBlockState(this.pos);
            this.blockMetadata = blockState.getBlock().getMetaFromState(blockState);
            this.worldObj.markChunkDirty(this.pos, this);
            if (this.getBlockType() != Blocks.air) {
                this.worldObj.updateComparatorOutputLevel(this.pos, this.getBlockType());
            }
        }
    }

    static {
        net.minecraft.tileentity.TileEntity.packedTextFragments = new String[8];
        net.minecraft.tileentity.TileEntity.packedTextFragments[0] = new String("x");
        net.minecraft.tileentity.TileEntity.packedTextFragments[1] = new String("y");
        net.minecraft.tileentity.TileEntity.packedTextFragments[2] = new String("z");
        net.minecraft.tileentity.TileEntity.packedTextFragments[3] = new String("x");
        net.minecraft.tileentity.TileEntity.packedTextFragments[4] = new String("y");
        net.minecraft.tileentity.TileEntity.packedTextFragments[5] = new String("z");
        int[] aint = new int[3];
        b(aint);
        net.minecraft.tileentity.TileEntity.logger = LogManager.getLogger();
        net.minecraft.tileentity.TileEntity.nameToClassMap = Maps.newHashMap();
        net.minecraft.tileentity.TileEntity.classToNameMap = Maps.newHashMap();
        addMapping(TileEntityFurnace.class, "Furnace");
        addMapping(TileEntityChest.class, "Chest");
        addMapping(TileEntityEnderChest.class, "EnderChest");
        addMapping(BlockJukeboxTileEntityJukebox.class, "RecordPlayer");
        addMapping(TileEntityDispenser.class, "Trap");
        addMapping(TileEntityDropper.class, "Dropper");
        addMapping(TileEntitySign.class, "Sign");
        addMapping(TileEntityMobSpawner.class, "MobSpawner");
        addMapping(TileEntityNote.class, "Music");
        addMapping(TileEntityPiston.class, "Piston");
        addMapping(TileEntityBrewingStand.class, "Cauldron");
        addMapping(TileEntityEnchantmentTable.class, "EnchantTable");
        addMapping(TileEntityEndPortal.class, "Airportal");
        addMapping(TileEntityCommandBlock.class, "Control");
        addMapping(TileEntityBeacon.class, "Beacon");
        addMapping(TileEntitySkull.class, "Skull");
        addMapping(TileEntityDaylightDetector.class, "DLDetector");
        addMapping(TileEntityHopper.class, "Hopper");
        addMapping(TileEntityComparator.class, "Comparator");
        addMapping(TileEntityFlowerPot.class, "FlowerPot");
        addMapping(TileEntityBanner.class, "Banner");
    }

    // region String decoding support

    // endregion

    public void setPos(BlockPos bDNValue) {
        this.pos = bDNValue;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    // region String decoding support

    // endregion

    public static int[] lz() {
        return dO;
    }

    public void readFromNBT(CompoundTag compoundTag) {
        this.pos =
                new BlockPos(
                        compoundTag.getInteger(packedTextFragments[0]),
                        compoundTag.getInteger(packedTextFragments[1]),
                        compoundTag.getInteger(packedTextFragments[2]));
    }

    public double getMaxRenderDistanceSquared() {
        return 4096.0;
    }

    public Block getBlockType() {
        if (this.blockType == null) {
            this.blockType = this.worldObj.getBlockState(this.pos).getBlock();
        }

        return this.blockType;
    }

    public boolean receiveClientEvent(int i, int j) {
        return false;
    }

    // region String decoding support

    // endregion

    public static TileEntity createAndLoadEntity(CompoundTag compoundTag) {
        MobSpawnerBaseLogic.GU();

        TileEntity tileEntity = null;

        try {
            Class oclass = (Class) net.minecraft.tileentity.TileEntity.nameToClassMap.get(compoundTag.getString("id"));
            if (oclass != null) {
                tileEntity = (TileEntity) oclass.newInstance();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (tileEntity != null) {
            tileEntity.readFromNBT(compoundTag);

            return tileEntity;
        }

        net.minecraft.tileentity.TileEntity.logger.warn("Skipping BlockEntity with id " + compoundTag.getString("id"));
        return tileEntity;
    }

    public static Map access$000() {
        return net.minecraft.tileentity.TileEntity.classToNameMap;
    }

    public void validate() {
        this.tileEntityInvalid = false;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public static void b(int[] aint) {
        dO = aint;
    }

    public TileEntity() {
        this.blockMetadata = -1;
    }

    public World getWorld() {
        return this.worldObj;
    }

    public Packet getDescriptionPacket() {
        return null;
    }

    public void setWorldObj(World bhwValue) {
        this.worldObj = bhwValue;
    }

    private static void addMapping(Class oclass, String text) {
        if (net.minecraft.tileentity.TileEntity.nameToClassMap.containsKey(text)) {
            throw new IllegalArgumentException("Duplicate id: " + text);
        }

        net.minecraft.tileentity.TileEntity.nameToClassMap.put(text, oclass);
        net.minecraft.tileentity.TileEntity.classToNameMap.put(oclass, text);
    }

    public void invalidate() {
        this.tileEntityInvalid = true;
    }
}
