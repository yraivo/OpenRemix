package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockPane extends Block {

    public static PropertyBool SOUTH;
    public static PropertyBool NORTH;
    private boolean canDrop;
    public static PropertyBool EAST;
    public static PropertyBool WEST;

    protected boolean canSilkHarvest() {
        return true;
    }

    public int getMetaFromState(BlockState blockState) {
        return 0;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    // region String decoding support

    // endregion

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return iBlockAccess.getBlockState(blockPos).getBlock() == this
                ? false
                : super.shouldSideBeRendered(iBlockAccess, blockPos, facing);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockPane.NORTH = PropertyBool.create("north");
        net.minecraft.block.BlockPane.EAST = PropertyBool.create("east");
        net.minecraft.block.BlockPane.SOUTH = PropertyBool.create("south");
        net.minecraft.block.BlockPane.WEST = PropertyBool.create("west");
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        return blockState
                .withProperty(
                        net.minecraft.block.BlockPane.NORTH,
                        this.canPaneConnectToBlock(
                                iBlockAccess.getBlockState(blockPos.offsetNorth()).getBlock()))
                .withProperty(
                        net.minecraft.block.BlockPane.SOUTH,
                        this.canPaneConnectToBlock(
                                iBlockAccess.getBlockState(blockPos.offsetSouth()).getBlock()))
                .withProperty(
                        net.minecraft.block.BlockPane.WEST,
                        this.canPaneConnectToBlock(
                                iBlockAccess.getBlockState(blockPos.offsetWest()).getBlock()))
                .withProperty(
                        net.minecraft.block.BlockPane.EAST,
                        this.canPaneConnectToBlock(
                                iBlockAccess.getBlockState(blockPos.offsetEast()).getBlock()));
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return !this.canDrop ? null : super.getItemDropped(blockState, random, i);
    }

    protected BlockPane(Material material, boolean canDropValue) {
        super(material);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockPane.NORTH, false)
                        .withProperty(net.minecraft.block.BlockPane.EAST, false)
                        .withProperty(net.minecraft.block.BlockPane.SOUTH, false)
                        .withProperty(net.minecraft.block.BlockPane.WEST, false));
        this.canDrop = canDropValue;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public final boolean canPaneConnectToBlock(Block block) {
        return block.isFullBlock()
                || block == this
                || block == Blocks.glass
                || block == Blocks.stained_glass
                || block == Blocks.stained_glass_pane
                || block instanceof BlockPane;
    }

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {
        int i;
        boolean flag;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        label72:
        {
            flag =
                    this.canPaneConnectToBlock(
                            world.getBlockState(blockPos.offsetNorth()).getBlock());
            flag1 =
                    this.canPaneConnectToBlock(
                            world.getBlockState(blockPos.offsetSouth()).getBlock());
            flag2 =
                    this.canPaneConnectToBlock(
                            world.getBlockState(blockPos.offsetWest()).getBlock());
            int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
            flag3 =
                    this.canPaneConnectToBlock(
                            world.getBlockState(blockPos.offsetEast()).getBlock());
            i = j;
            if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1)) {
                if (flag2) {
                    this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
                    super.addCollisionBoxesToList(
                            world, blockPos, blockState, boundingBox, list, entity);
                    if (i == 0) {
                        break label72;
                    }
                }

                if (!flag3) {
                    break label72;
                }

                this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
                super.addCollisionBoxesToList(
                        world, blockPos, blockState, boundingBox, list, entity);
                if (i == 0) {
                    break label72;
                }
            }

            this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
            super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        }

        if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1)) {
            if (flag) {
                this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
                super.addCollisionBoxesToList(
                        world, blockPos, blockState, boundingBox, list, entity);
                if (i == 0) {
                    return;
                }
            }

            if (!flag1) {
                return;
            }

            this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
            super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
            if (i == 0) {
                return;
            }
        }

        this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockPane.NORTH,
                    net.minecraft.block.BlockPane.EAST,
                    net.minecraft.block.BlockPane.WEST,
                    net.minecraft.block.BlockPane.SOUTH
                });
    }

    public boolean isFullCube() {
        return false;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int i;
        float f;
        float f1;
        float f2;
        float f3;
        boolean flag;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        label72:
        {
            int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
            f = 0.4375F;
            f1 = 0.5625F;
            f2 = 0.4375F;
            i = j;
            f3 = 0.5625F;
            flag =
                    this.canPaneConnectToBlock(
                            iBlockAccess.getBlockState(blockPos.offsetNorth()).getBlock());
            flag1 =
                    this.canPaneConnectToBlock(
                            iBlockAccess.getBlockState(blockPos.offsetSouth()).getBlock());
            flag2 =
                    this.canPaneConnectToBlock(
                            iBlockAccess.getBlockState(blockPos.offsetWest()).getBlock());
            flag3 =
                    this.canPaneConnectToBlock(
                            iBlockAccess.getBlockState(blockPos.offsetEast()).getBlock());
            if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1)) {
                if (flag2) {
                    f = 0.0F;
                    if (i != 0) {
                        break label72;
                    }
                }

                if (!flag3) {
                    break label72;
                }

                f1 = 1.0F;
                if (i != 0) {
                    break label72;
                }
            }

            f = 0.0F;
            f1 = 1.0F;
        }

        label73:
        {
            if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1)) {
                if (flag) {
                    f2 = 0.0F;
                    if (i != 0) {
                        break label73;
                    }
                }

                if (!flag1) {
                    break label73;
                }

                f3 = 1.0F;
                if (i != 0) {
                    break label73;
                }
            }

            f2 = 0.0F;
            f3 = 1.0F;
        }

        this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
    }
}
