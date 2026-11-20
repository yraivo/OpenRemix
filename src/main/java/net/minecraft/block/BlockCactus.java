package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.movement.TerrainModule;

import java.util.Iterator;
import java.util.Random;

public class BlockCactus extends Block {
    public static PropertyInteger AGE_PROP;

    @Override
    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockCactus.AGE_PROP, i);
    }

    public boolean canBlockStay(World world, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        Iterator iterator = FacingPlane.HORIZONTAL.iterator();
        int i = j;

        while (iterator.hasNext()) {
            Object object = iterator.next();
            Facing facing = (Facing) object;
            if (world.getBlockState(blockPos.offset(facing)).getBlock().getMaterial().isSolid()) {
                return false;
            }

            if (i != 0) {
                break;
            }
        }

        Block block = world.getBlockState(blockPos.offsetDown()).getBlock();
        return block == Blocks.cactus || block == Blocks.sand;
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        float f = 0.0625F;
        return new BoundingBox(
                blockPos.getX() + f,
                blockPos.getY(),
                blockPos.getZ() + f,
                blockPos.getX() + 1 - f,
                blockPos.getY() + 1 - f,
                blockPos.getZ() + 1 - f);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockCactus.AGE_PROP = PropertyInteger.create("age", 0, 15);
    }

    @Override
    public void onEntityCollidedWithBlock(
            World world, BlockPos blockPos, BlockState blockState, Entity entity) {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        TerrainModule terrain = ClientServices.moduleManager.terrain;
        if (!terrain.isEnabled() || !terrain.antiCactusSetting.asBoolean()) {
            entity.attackEntityFrom(DamageSource.cactus, 1.0F);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && this.canBlockStay(world, blockPos);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockCactus.AGE_PROP});
    }

    @Override
    public BoundingBox getSelectedBoundingBox(World world, BlockPos blockPos) {
        float f = 0.0625F;
        return new BoundingBox(
                blockPos.getX() + f,
                blockPos.getY(),
                blockPos.getZ() + f,
                blockPos.getX() + 1 - f,
                blockPos.getY() + 1,
                blockPos.getZ() + 1 - f);
    }

    @Override
    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockCactus.AGE_PROP);
    }

    @Override
    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockPos blockPosValue = blockPos.offsetUp();
        int i = l;
        if (world.isAirBlock(blockPosValue)) {
            int j = 1;

            while (world.getBlockState(blockPos.offsetDown(j)).getBlock() == this) {
                j++;
                if (i != 0) {
                    break;
                }
            }

            if (j < 3) {
                int k = (Integer) blockState.getValue(net.minecraft.block.BlockCactus.AGE_PROP);
                if (k == 15) {
                    world.setBlockState(blockPosValue, this.getDefaultState());
                    BlockState blockStateValue =
                            blockState.withProperty(net.minecraft.block.BlockCactus.AGE_PROP, 0);
                    world.setBlockState(blockPos, blockStateValue, 4);
                    this.onNeighborBlockChange(world, blockPosValue, blockStateValue, this);
                    if (i == 0) {
                        return;
                    }
                }

                world.setBlockState(
                        blockPos, blockState.withProperty(net.minecraft.block.BlockCactus.AGE_PROP, k + 1), 4);
            }
        }
    }

    protected BlockCactus() {
        super(Material.cactus);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockCactus.AGE_PROP, 0));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!this.canBlockStay(world, blockPos)) {
            world.destroyBlock(blockPos, true);
        }
    }

    // region String decoding support

    // endregion
}
