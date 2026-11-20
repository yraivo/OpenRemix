package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockEndPortalFrame extends Block {

    public static PropertyDirection FACING;
    public static PropertyBool EYE;

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {net.minecraft.block.BlockEndPortalFrame.FACING, net.minecraft.block.BlockEndPortalFrame.EYE});
    }

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        if ((Boolean) world.getBlockState(blockPos).getValue(net.minecraft.block.BlockEndPortalFrame.EYE)) {
            this.setBlockBounds(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
            super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        }

        this.setBlockBoundsForItemRender();
    }

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        return ((java.lang.Boolean)
                        world.getBlockState(blockPos).getValue(net.minecraft.block.BlockEndPortalFrame.EYE))
                ? 15
                : 0;
    }

    // region String decoding support

    // endregion

    public BlockEndPortalFrame() {
        super(Material.rock);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockEndPortalFrame.FACING, Facing.NORTH)
                        .withProperty(net.minecraft.block.BlockEndPortalFrame.EYE, false));
    }

    static {
        net.minecraft.block.BlockEndPortalFrame.FACING =
                PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
        net.minecraft.block.BlockEndPortalFrame.EYE = PropertyBool.create("eye");
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
    }

    // region String decoding support

    // endregion

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2,
            int i,
            LivingEntity livingEntity) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockEndPortalFrame.FACING,
                        livingEntity.getHorizontalFacing().getOpposite())
                .withProperty(net.minecraft.block.BlockEndPortalFrame.EYE, false);
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((Facing) blockState.getValue(net.minecraft.block.BlockEndPortalFrame.FACING))
                                .getHorizontalIndex();
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockEndPortalFrame.EYE)) {
            i |= 4;
        }

        return i;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return null;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockEndPortalFrame.EYE, (i & 4) != 0)
                .withProperty(net.minecraft.block.BlockEndPortalFrame.FACING, Facing.getHorizontal(i & 3));
    }
}
