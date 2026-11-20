package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class BlockSlab extends Block {
    public static PropertyEnum HALF_PROP;

    public int quantityDropped(Random random) {
        return this.isDouble() ? 2 : 1;
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        if (this.isDouble()) {
            return super.shouldSideBeRendered(iBlockAccess, blockPos, facing);
        }

        if (facing != Facing.UP
                && facing != Facing.DOWN
                && !super.shouldSideBeRendered(iBlockAccess, blockPos, facing)) {
            return false;
        }

        BlockPos blockPosValue = blockPos.offset(facing.getOpposite());
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        BlockState blockStateValue = iBlockAccess.getBlockState(blockPosValue);
        int i =
                isSlab(blockState.getBlock())
                                && blockState.getValue(net.minecraft.block.BlockSlab.HALF_PROP)
                                        == BlockSlabEnumBlockHalf.TOP
                        ? 1
                        : 0;
        int j =
                isSlab(blockStateValue.getBlock())
                                && blockStateValue.getValue(net.minecraft.block.BlockSlab.HALF_PROP)
                                        == BlockSlabEnumBlockHalf.TOP
                        ? 1
                        : 0;
        return j != 0
                ? (facing == Facing.DOWN
                        ? true
                        : (facing == Facing.UP
                                        && super.shouldSideBeRendered(
                                                iBlockAccess, blockPos, facing)
                                ? true
                                : !isSlab(blockState.getBlock()) || i == 0))
                : (facing == Facing.UP
                        ? true
                        : (facing == Facing.DOWN
                                        && super.shouldSideBeRendered(
                                                iBlockAccess, blockPos, facing)
                                ? true
                                : !isSlab(blockState.getBlock()) || i != 0));
    }

    public abstract String getFullSlabName(int i);

    public boolean isOpaqueCube() {
        return this.isDouble();
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockSlab.HALF_PROP =
                PropertyEnum.create("half", BlockSlabEnumBlockHalf.class);
    }

    public boolean isFullCube() {
        return this.isDouble();
    }

    public abstract Object getVariant(ItemStack itemStack);

    public int getDamageValue(World world, BlockPos blockPos) {
        return super.getDamageValue(world, blockPos) & 7;
    }

    protected static boolean isSlab(Block block) {
        return block == Blocks.stone_slab
                || block == Blocks.wooden_slab
                || block == Blocks.stone_slab2;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (this.isDouble()) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            if (i != 0) {
                return;
            }
        }

        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            if (blockState.getValue(net.minecraft.block.BlockSlab.HALF_PROP)
                    == BlockSlabEnumBlockHalf.TOP) {
                this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                if (i != 0) {
                    return;
                }
            }

            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    public BlockSlab(Material material) {
        this(material, BlockPlanksEnumType.getInvertedBlockFlowToken());
    }

    private BlockSlab(Material material, int initialFlowToken) {
        super(material);

        {
            int j = initialFlowToken;

            int i = j;
            if (this.isDouble()) {
                this.fullBlock = true;
                if (!(i == 0)) {

                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
                }
            } else {

                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
        }

        this.setLightOpacity(255);
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    public void setBlockBoundsForItemRender() {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (this.isDouble()) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            if (i != 0) {
                return;
            }
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
    }

    public abstract IProperty getVariantProperty();

    public abstract boolean isDouble();

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2,
            int i,
            LivingEntity livingEntity) {
        BlockState blockState =
                super.onBlockPlaced(world, blockPos, facing, f, f1, f2, i, livingEntity)
                        .withProperty(
                                net.minecraft.block.BlockSlab.HALF_PROP,
                                BlockSlabEnumBlockHalf.BOTTOM);
        return this.isDouble()
                ? blockState
                : (facing == Facing.DOWN || facing != Facing.UP && !(f1 <= 0.5)
                        ? blockState.withProperty(
                                net.minecraft.block.BlockSlab.HALF_PROP, BlockSlabEnumBlockHalf.TOP)
                        : blockState);
    }

    // region String decoding support

    // endregion
}
