package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemLead;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockFence extends Block {
    public static PropertyBool EAST;

    public static PropertyBool WEST;
    public static PropertyBool NORTH;
    public static PropertyBool SOUTH;

    public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return false;
    }

    public boolean isFullCube() {
        return false;
    }

    public int getMetaFromState(BlockState blockState) {
        return 0;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public BlockFence(Material material) {
        super(material);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockFence.NORTH, false)
                        .withProperty(net.minecraft.block.BlockFence.EAST, false)
                        .withProperty(net.minecraft.block.BlockFence.SOUTH, false)
                        .withProperty(net.minecraft.block.BlockFence.WEST, false));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    // region String decoding support

    // endregion

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockFence.NORTH,
                    net.minecraft.block.BlockFence.EAST,
                    net.minecraft.block.BlockFence.WEST,
                    net.minecraft.block.BlockFence.SOUTH
                });
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return true;
    }

    // region String decoding support

    // endregion

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float f,
            float f1,
            float f2) {
        return world.isRemote ? true : ItemLead.attachToFence(playerEntity, world, blockPos);
    }

    public boolean canConnectTo(IBlockAccess iBlockAccess, BlockPos blockPos) {
        Block block = iBlockAccess.getBlockState(blockPos).getBlock();
        return block == Blocks.barrier
                ? false
                : ((!(block instanceof BlockFence) || block.blockMaterial != this.blockMaterial)
                                && !(block instanceof BlockFenceGate)
                        ? (block.blockMaterial.isOpaque() && block.isFullCube()
                                ? block.blockMaterial != Material.gourd
                                : false)
                        : true);
    }

    static {
        net.minecraft.block.BlockFence.NORTH = PropertyBool.create("north");
        net.minecraft.block.BlockFence.EAST = PropertyBool.create("east");
        net.minecraft.block.BlockFence.SOUTH = PropertyBool.create("south");
        net.minecraft.block.BlockFence.WEST = PropertyBool.create("west");
    }

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {
        boolean flag = this.canConnectTo(world, blockPos.offsetNorth());
        boolean flag1 = this.canConnectTo(world, blockPos.offsetSouth());
        boolean flag2 = this.canConnectTo(world, blockPos.offsetWest());
        boolean flag3 = this.canConnectTo(world, blockPos.offsetEast());
        float f = 0.375F;
        float f1 = 0.625F;
        float f2 = 0.375F;
        float f3 = 0.625F;
        if (flag) {
            f2 = 0.0F;
        }

        if (flag1) {
            f3 = 1.0F;
        }

        if (flag || flag1) {
            this.setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
            super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        }

        f2 = 0.375F;
        f3 = 0.625F;
        if (flag2) {
            f = 0.0F;
        }

        if (flag3) {
            f1 = 1.0F;
        }

        if (flag2 || flag3 || !flag && !flag1) {
            this.setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
            super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        }

        if (flag) {
            f2 = 0.0F;
        }

        if (flag1) {
            f3 = 1.0F;
        }

        this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
    }

    // region String decoding support

    // endregion

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        boolean flag = this.canConnectTo(iBlockAccess, blockPos.offsetNorth());
        boolean flag1 = this.canConnectTo(iBlockAccess, blockPos.offsetSouth());
        boolean flag2 = this.canConnectTo(iBlockAccess, blockPos.offsetWest());
        boolean flag3 = this.canConnectTo(iBlockAccess, blockPos.offsetEast());
        float f = 0.375F;
        float f1 = 0.625F;
        float f2 = 0.375F;
        float f3 = 0.625F;
        if (flag) {
            f2 = 0.0F;
        }

        if (flag1) {
            f3 = 1.0F;
        }

        if (flag2) {
            f = 0.0F;
        }

        if (flag3) {
            f1 = 1.0F;
        }

        this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        return blockState
                .withProperty(
                        net.minecraft.block.BlockFence.NORTH,
                        this.canConnectTo(iBlockAccess, blockPos.offsetNorth()))
                .withProperty(
                        net.minecraft.block.BlockFence.EAST,
                        this.canConnectTo(iBlockAccess, blockPos.offsetEast()))
                .withProperty(
                        net.minecraft.block.BlockFence.SOUTH,
                        this.canConnectTo(iBlockAccess, blockPos.offsetSouth()))
                .withProperty(
                        net.minecraft.block.BlockFence.WEST,
                        this.canConnectTo(iBlockAccess, blockPos.offsetWest()));
    }
}
