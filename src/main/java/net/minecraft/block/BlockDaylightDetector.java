package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockDaylightDetector extends BlockContainer {

    public static PropertyInteger POWER;

    private boolean inverted;

    public Item getItem(World world, BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.daylight_detector);
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Item.getItemFromBlock(Blocks.daylight_detector);
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        if (!this.inverted) {
            super.getSubBlocks(item, creativeTabs, list);
        }
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float f,
            float f1,
            float f2) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (playerEntity.isAllowEdit()) {
            if (world.isRemote) {
                return true;
            }

            if (this.inverted) {
                world.setBlockState(
                        blockPos,
                        Blocks.daylight_detector
                                .getDefaultState()
                                .withProperty(
                                        net.minecraft.block.BlockDaylightDetector.POWER,
                                        blockState.getValue(net.minecraft.block.BlockDaylightDetector.POWER)),
                        4);
                Blocks.daylight_detector.updatePower(world, blockPos);
                if (i == 0) {
                    return true;
                }
            }

            world.setBlockState(
                    blockPos,
                    Blocks.daylight_detector_inverted
                            .getDefaultState()
                            .withProperty(
                                    net.minecraft.block.BlockDaylightDetector.POWER,
                                    blockState.getValue(net.minecraft.block.BlockDaylightDetector.POWER)),
                    4);
            Blocks.daylight_detector_inverted.updatePower(world, blockPos);
            return true;
        } else {
            return super.onBlockActivated(
                    world, blockPos, blockState, playerEntity, facing, f, f1, f2);
        }
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityDaylightDetector();
    }

    public void updatePower(World world, BlockPos blockPos) {
        if (!world.provider.getHasNoSky()) {
            BlockState blockState = world.getBlockState(blockPos);
            int i = world.getLightFor(EnumSkyBlock.SKY, blockPos) - world.getSkylightSubtracted();
            float celestialAngleRadians = world.getCelestialAngleRadians(1.0F);
            float f1 = celestialAngleRadians < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
            celestialAngleRadians += (f1 - celestialAngleRadians) * 0.2F;
            i = Math.round(i * MathHelper.cos(celestialAngleRadians));
            i = MathHelper.clamp(i, 0, 15);
            if (this.inverted) {
                i = 15 - i;
            }

            if ((Integer) blockState.getValue(net.minecraft.block.BlockDaylightDetector.POWER) != i) {
                world.setBlockState(
                        blockPos, blockState.withProperty(net.minecraft.block.BlockDaylightDetector.POWER, i), 3);
            }
        }
    }

    public int isProvidingWeakPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockDaylightDetector.POWER);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
    }

    static {
        net.minecraft.block.BlockDaylightDetector.POWER =
                PropertyInteger.create("power", 0, 15);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockDaylightDetector.POWER});
    }

    public boolean canProvidePower() {
        return true;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockDaylightDetector.POWER, i);
    }

    public BlockDaylightDetector(boolean ahvValue) {
        super(Material.wood);
        this.inverted = ahvValue;
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockDaylightDetector.POWER, 0));
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setHardness(0.2F);
        this.setStepSound(net.minecraft.block.Block.soundTypeWood);
        this.setUnlocalizedName("daylightDetector");
    }

    public int getRenderType() {
        return 3;
    }

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockDaylightDetector.POWER);
    }
}
