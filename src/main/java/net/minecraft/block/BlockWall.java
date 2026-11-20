package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockWall extends Block {
    public static PropertyBool WEST;
    public static PropertyBool NORTH;
    public static PropertyBool SOUTH;
    public static PropertyBool EAST;
    public static PropertyEnum VARIANT;

    public static PropertyBool UP;

    public boolean isFullCube() {
        return false;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockWall.UP,
                    net.minecraft.block.BlockWall.NORTH,
                    net.minecraft.block.BlockWall.EAST,
                    net.minecraft.block.BlockWall.WEST,
                    net.minecraft.block.BlockWall.SOUTH,
                    net.minecraft.block.BlockWall.VARIANT
                });
    }

    public BlockWall(Block block) {
        super(block.blockMaterial);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockWall.UP, false)
                        .withProperty(net.minecraft.block.BlockWall.NORTH, false)
                        .withProperty(net.minecraft.block.BlockWall.EAST, false)
                        .withProperty(net.minecraft.block.BlockWall.SOUTH, false)
                        .withProperty(net.minecraft.block.BlockWall.WEST, false)
                        .withProperty(net.minecraft.block.BlockWall.VARIANT, BlockWallEnumType.NORMAL));
        this.setHardness(block.blockHardness);
        this.setResistance(block.blockResistance / 3.0F);
        this.setStepSound(block.stepSound);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        this.maxY = 1.5;
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        boolean flag = this.canConnectTo(iBlockAccess, blockPos.offsetNorth());
        boolean flag1 = this.canConnectTo(iBlockAccess, blockPos.offsetSouth());
        boolean flag2 = this.canConnectTo(iBlockAccess, blockPos.offsetWest());
        boolean flag3 = this.canConnectTo(iBlockAccess, blockPos.offsetEast());
        float f = 0.25F;
        float f1 = 0.75F;
        float f2 = 0.25F;
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        float f3 = 0.75F;
        float f4 = 1.0F;
        int i = j;
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

        {
            if (flag && flag1 && !flag2 && !flag3) {
                f4 = 0.8125F;
                f = 0.3125F;
                f1 = 0.6875F;
                if (!(i != 0)) {

                    if (!flag && !flag1 && flag2 && flag3) {
                        f4 = 0.8125F;
                        f2 = 0.3125F;
                        f3 = 0.6875F;
                    }
                }
            } else {

                if (!flag && !flag1 && flag2 && flag3) {
                    f4 = 0.8125F;
                    f2 = 0.3125F;
                    f3 = 0.6875F;
                }
            }
        }

        this.setBlockBounds(f, 0.0F, f2, f1, f4, f3);
    }

    public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    static {
        net.minecraft.block.BlockWall.UP = PropertyBool.create("up");
        net.minecraft.block.BlockWall.NORTH = PropertyBool.create("north");
        net.minecraft.block.BlockWall.EAST = PropertyBool.create("east");
        net.minecraft.block.BlockWall.SOUTH = PropertyBool.create("south");
        net.minecraft.block.BlockWall.WEST = PropertyBool.create("west");
        net.minecraft.block.BlockWall.VARIANT = PropertyEnum.create("variant", BlockWallEnumType.class);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockWall.VARIANT, BlockWallEnumType.byMetadata(i));
    }

    public int getMetaFromState(BlockState blockState) {
        return ((BlockWallEnumType) blockState.getValue(net.minecraft.block.BlockWall.VARIANT)).getMetadata();
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return facing == Facing.DOWN
                ? super.shouldSideBeRendered(iBlockAccess, blockPos, facing)
                : true;
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        return blockState
                .withProperty(net.minecraft.block.BlockWall.UP, !iBlockAccess.isAirBlock(blockPos.offsetUp()))
                .withProperty(
                        net.minecraft.block.BlockWall.NORTH,
                        this.canConnectTo(iBlockAccess, blockPos.offsetNorth()))
                .withProperty(
                        net.minecraft.block.BlockWall.EAST,
                        this.canConnectTo(iBlockAccess, blockPos.offsetEast()))
                .withProperty(
                        net.minecraft.block.BlockWall.SOUTH,
                        this.canConnectTo(iBlockAccess, blockPos.offsetSouth()))
                .withProperty(
                        net.minecraft.block.BlockWall.WEST,
                        this.canConnectTo(iBlockAccess, blockPos.offsetWest()));
    }

    public boolean canConnectTo(IBlockAccess iBlockAccess, BlockPos blockPos) {
        Block block = iBlockAccess.getBlockState(blockPos).getBlock();
        return block == Blocks.barrier
                ? false
                : (block != this && !(block instanceof BlockFenceGate)
                        ? (block.blockMaterial.isOpaque() && block.isFullCube()
                                ? block.blockMaterial != Material.gourd
                                : false)
                        : true);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public int damageDropped(BlockState blockState) {
        return ((BlockWallEnumType) blockState.getValue(net.minecraft.block.BlockWall.VARIANT)).getMetadata();
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        BlockWallEnumType[] blockWallEnumTypeArray = BlockWallEnumType.values();
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int j = blockWallEnumTypeArray.length;
        int k = 0;
        int i = l;

        while (k < j) {
            BlockWallEnumType blockWallEnumType = blockWallEnumTypeArray[k];
            list.add(new ItemStack(item, 1, blockWallEnumType.getMetadata()));
            k++;
            if (i == 0) {
                break;
            }
        }
    }
}
