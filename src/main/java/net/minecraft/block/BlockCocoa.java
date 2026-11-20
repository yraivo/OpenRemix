package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCocoa extends BlockDirectional implements IGrowable {
    public static PropertyInteger AGE;

    public BoundingBox getSelectedBoundingBox(World world, BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i1) {
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int j = (Integer) blockState.getValue(net.minecraft.block.BlockCocoa.AGE);
        int i = l;
        byte b0 = 1;
        if (j >= 2) {
            b0 = 3;
        }

        int k = 0;

        while (k < b0) {
            spawnAsEntity(
                    world,
                    blockPos,
                    new ItemStack(Items.dye, 1, EnumDyeColor.BROWN.getDyeColorDamage()));
            k++;
            if (i == 0) {
                break;
            }
        }
    }

    // region String decoding support

    // endregion

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int i1 = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        int i = i1;
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE);
        int j = (Integer) blockState.getValue(net.minecraft.block.BlockCocoa.AGE);
        int k = 4 + j * 2;
        int l = 5 + j * 2;
        float f = k / 2.0F;
        switch (facing) {
            case SOUTH:
                this.setBlockBounds(
                        (8.0F - f) / 16.0F,
                        (12.0F - l) / 16.0F,
                        (15.0F - k) / 16.0F,
                        (8.0F + f) / 16.0F,
                        0.75F,
                        0.9375F);
                if (i == 0) {
                    break;
                }
            case NORTH:
                this.setBlockBounds(
                        (8.0F - f) / 16.0F,
                        (12.0F - l) / 16.0F,
                        0.0625F,
                        (8.0F + f) / 16.0F,
                        0.75F,
                        (1.0F + k) / 16.0F);
                if (i == 0) {
                    break;
                }
            case WEST:
                this.setBlockBounds(
                        0.0625F,
                        (12.0F - l) / 16.0F,
                        (8.0F - f) / 16.0F,
                        (1.0F + k) / 16.0F,
                        0.75F,
                        (8.0F + f) / 16.0F);
                if (i == 0) {
                    break;
                }
            case EAST:
                this.setBlockBounds(
                        (15.0F - k) / 16.0F,
                        (12.0F - l) / 16.0F,
                        (8.0F - f) / 16.0F,
                        0.9375F,
                        0.75F,
                        (8.0F + f) / 16.0F);
        }
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE))
                                .getHorizontalIndex();
        return i | (Integer) blockState.getValue(net.minecraft.block.BlockCocoa.AGE) << 2;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Items.dye;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!this.canBlockStay(world, blockPos, blockState)) {
            this.dropBlock(world, blockPos, blockState);
        }
    }

    private void dropBlock(World world, BlockPos blockPos, BlockState blockState) {
        world.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
        this.dropBlockAsItem(world, blockPos, blockState, 0);
    }

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        Facing facing = Facing.fromAngle(livingEntity.rotationYaw);
        world.setBlockState(
                blockPos,
                blockState.withProperty(net.minecraft.block.BlockDirectional.AGE, facing),
                2);
    }

    public boolean isStillGrowing(
            World world, BlockPos blockPos, BlockState blockState, boolean flag) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockCocoa.AGE) < 2;
    }

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2,
            int i,
            LivingEntity livingEntity) {
        if (!facing.getAxis().isHorizontal()) {
            facing = Facing.NORTH;
        }

        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockDirectional.AGE, facing.getOpposite())
                .withProperty(net.minecraft.block.BlockCocoa.AGE, 0);
    }

    public int getDamageValue(World world, BlockPos blockPos) {
        return EnumDyeColor.BROWN.getDyeColorDamage();
    }

    public void grow(World world, Random random, BlockPos blockPos, BlockState blockState) {
        world.setBlockState(
                blockPos,
                blockState.withProperty(
                        net.minecraft.block.BlockCocoa.AGE,
                        (Integer) blockState.getValue(net.minecraft.block.BlockCocoa.AGE) + 1),
                2);
    }

    // region String decoding support

    // endregion

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockDirectional.AGE, net.minecraft.block.BlockCocoa.AGE
                });
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (!this.canBlockStay(world, blockPos, blockState)) {
            this.dropBlock(world, blockPos, blockState);
            if (i != 0) {
                return;
            }
        }

        if (world.rand.nextInt(5) == 0) {
            int j = (Integer) blockState.getValue(net.minecraft.block.BlockCocoa.AGE);
            if (j < 2) {
                world.setBlockState(
                        blockPos,
                        blockState.withProperty(net.minecraft.block.BlockCocoa.AGE, j + 1),
                        2);
            }
        }
    }

    public boolean canBlockStay(World world, BlockPos blockPos, BlockState blockState) {
        blockPos =
                blockPos.offset(
                        (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE));
        BlockState blockStateValue = world.getBlockState(blockPos);
        return blockStateValue.getBlock() == Blocks.log
                && blockStateValue.getValue(BlockPlanks.VARIANT_PROP) == BlockPlanksEnumType.JUNGLE;
    }

    public boolean canUseBonemeal(
            World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockDirectional.AGE, Facing.getHorizontal(i))
                .withProperty(net.minecraft.block.BlockCocoa.AGE, (i & 15) >> 2);
    }

    public boolean isFullCube() {
        return false;
    }

    static {
        net.minecraft.block.BlockCocoa.AGE = PropertyInteger.create("age", 0, 2);
    }

    public BlockCocoa() {
        super(Material.plants);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockDirectional.AGE, Facing.NORTH)
                        .withProperty(net.minecraft.block.BlockCocoa.AGE, 0));
        this.setTickRandomly(true);
    }
}
