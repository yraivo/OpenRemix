package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSnow extends Block {

    public static PropertyInteger LAYERS_PROP;

    private boolean checkAndDropBlock(World world, BlockPos blockPos, BlockState blockState) {
        if (!this.canPlaceBlockAt(world, blockPos)) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
            return false;
        } else {
            return true;
        }
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        int i = (Integer) blockState.getValue(net.minecraft.block.BlockSnow.LAYERS_PROP) - 1;
        float f = 0.125F;
        return new BoundingBox(
                blockPos.getX() + this.minX,
                blockPos.getY() + this.minY,
                blockPos.getZ() + this.minZ,
                blockPos.getX() + this.maxX,
                blockPos.getY() + i * f,
                blockPos.getZ() + this.maxZ);
    }

    public boolean isReplaceable(World world, BlockPos blockPos) {
        return (Integer) world.getBlockState(blockPos).getValue(net.minecraft.block.BlockSnow.LAYERS_PROP) == 1;
    }

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockSnow.LAYERS_PROP) - 1;
    }

    protected void getBoundsForLayers(int i) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, i / 8.0F, 1.0F);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        this.checkAndDropBlock(world, blockPos, blockState);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        this.getBoundsForLayers((Integer) blockState.getValue(net.minecraft.block.BlockSnow.LAYERS_PROP));
    }

    protected BlockSnow() {
        super(Material.snow);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockSnow.LAYERS_PROP, 1));
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBoundsForItemRender();
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (world.getLightFor(EnumSkyBlock.BLOCK, blockPos) > 11) {
            this.dropBlockAsItem(world, blockPos, world.getBlockState(blockPos), 0);
            world.setBlockToAir(blockPos);
        }
    }

    public void harvestBlock(
            World world,
            PlayerEntity playerEntity,
            BlockPos blockPos,
            BlockState blockState,
            TileEntity tileEntity) {
        spawnAsEntity(
                world,
                blockPos,
                new ItemStack(
                        Items.snowball,
                        (Integer) blockState.getValue(net.minecraft.block.BlockSnow.LAYERS_PROP) + 1,
                        0));
        world.setBlockToAir(blockPos);
        playerEntity.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockSnow.LAYERS_PROP, (i & 7) + 1);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockSnow.LAYERS_PROP});
    }

    // region String decoding support

    // endregion

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos.offsetDown());
        Block block = blockState.getBlock();
        return block != Blocks.ice && block != Blocks.packed_ice
                ? (block.getMaterial() == Material.leaves
                        ? true
                        : (block == this
                                        && ((java.lang.Integer)
                                                        blockState.getValue(
                                                                net.minecraft.block.BlockSnow.LAYERS_PROP))
                                                == 7
                                ? true
                                : block.isOpaqueCube() && block.blockMaterial.blocksMovement()))
                : false;
    }

    public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return (Integer) iBlockAccess.getBlockState(blockPos).getValue(net.minecraft.block.BlockSnow.LAYERS_PROP)
                < 5;
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return facing == Facing.UP
                ? true
                : super.shouldSideBeRendered(iBlockAccess, blockPos, facing);
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    static {
        net.minecraft.block.BlockSnow.LAYERS_PROP = PropertyInteger.create("layers", 1, 8);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.snowball;
    }

    public void setBlockBoundsForItemRender() {
        this.getBoundsForLayers(0);
    }

    public boolean isFullCube() {
        return false;
    }

    // region String decoding support

    // endregion
}
