package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCake extends Block {
    public static PropertyInteger BITES;

    public void onBlockClicked(World world, BlockPos blockPos, PlayerEntity playerEntity) {
        this.eatCake(world, blockPos, world.getBlockState(blockPos), playerEntity);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
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
        this.eatCake(world, blockPos, blockState, playerEntity);
        return true;
    }

    private boolean canBlockStay(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial().isSolid();
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return null;
    }

    // region String decoding support

    // endregion

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) ? this.canBlockStay(world, blockPos) : false;
    }

    // region String decoding support

    // endregion

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockCake.BITES});
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        float f = 0.0625F;
        float f1 = (1 + (Integer) blockState.getValue(net.minecraft.block.BlockCake.BITES) * 2) / 16.0F;
        float f2 = 0.5F;
        return new BoundingBox(
                blockPos.getX() + f1,
                blockPos.getY(),
                blockPos.getZ() + f,
                blockPos.getX() + 1 - f,
                blockPos.getY() + f2,
                blockPos.getZ() + 1 - f);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockCake.BITES, i);
    }

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockCake.BITES);
    }

    public void setBlockBoundsForItemRender() {
        float f = 0.0625F;
        float f1 = 0.5F;
        this.setBlockBounds(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
    }

    static {
        net.minecraft.block.BlockCake.BITES = PropertyInteger.create("bites", 0, 6);
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Items.cake;
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    protected BlockCake() {
        super(Material.cake);
        this.setDefaultState(this.blockState.getBaseState().withProperty(net.minecraft.block.BlockCake.BITES, 0));
        this.setTickRandomly(true);
    }

    public BoundingBox getSelectedBoundingBox(World world, BlockPos blockPos) {
        return this.getCollisionBoundingBox(world, blockPos, world.getBlockState(blockPos));
    }

    public boolean isFullCube() {
        return false;
    }

    private void eatCake(
            World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (playerEntity.canEat(false)) {
            playerEntity.getFoodStats().addStats(2, 0.1F);
            int j = (Integer) blockState.getValue(net.minecraft.block.BlockCake.BITES);
            if (j < 6) {
                world.setBlockState(
                        blockPos, blockState.withProperty(net.minecraft.block.BlockCake.BITES, j + 1), 3);
                if (i == 0) {
                    return;
                }
            }

            world.setBlockToAir(blockPos);
        }
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        float f = 0.0625F;
        float f1 =
                (1
                                + (Integer)
                                                iBlockAccess
                                                        .getBlockState(blockPos)
                                                        .getValue(net.minecraft.block.BlockCake.BITES)
                                        * 2)
                        / 16.0F;
        float f2 = 0.5F;
        this.setBlockBounds(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
    }

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        return (7 - (Integer) world.getBlockState(blockPos).getValue(net.minecraft.block.BlockCake.BITES)) * 2;
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!this.canBlockStay(world, blockPos)) {
            world.setBlockToAir(blockPos);
        }
    }
}
