package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRailBase extends Block {
    protected boolean isPowered;

    public boolean isOpaqueCube() {
        return false;
    }

    public int getMobilityFlag() {
        return 0;
    }

    public abstract IProperty getShapeProperty();

    protected BlockState func_176564_a(
            World world, BlockPos blockPos, BlockState blockState, boolean flag) {
        return world.isRemote
                ? blockState
                : new BlockRailBaseRail(this, world, blockPos, blockState)
                        .method_c806dab0a04fb5ea83bb(world.isBlockPowered(blockPos), flag)
                        .getBlockState();
    }

    public static boolean isRailBlock(World world, BlockPos blockPos) {
        return isRailBlock(world.getBlockState(blockPos));
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        int i = j;
        BlockRailBaseEnumRailDirection blockRailBaseEnumRailDirection =
                blockState.getBlock() == this
                        ? (BlockRailBaseEnumRailDirection)
                                blockState.getValue(this.getShapeProperty())
                        : null;
        if (blockRailBaseEnumRailDirection != null
                && blockRailBaseEnumRailDirection.isAscending()) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
            if (i == 0) {
                return;
            }
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public boolean isFullCube() {
        return false;
    }

    protected void onNeighborChangedInternal(
            World world, BlockPos blockPos, BlockState blockState, Block block) {}

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        if (!world.isRemote) {
            blockState = this.func_176564_a(world, blockPos, blockState, true);
            if (this.isPowered) {
                this.onNeighborBlockChange(world, blockPos, blockState, this);
            }
        }
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown());
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote) {
            BlockRailBaseEnumRailDirection value =
                    (BlockRailBaseEnumRailDirection) blockState.getValue(this.getShapeProperty());
            byte b0 = 0;
            if (!World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())) {
                b0 = 1;
            }

            label48:
            {
                if (value == BlockRailBaseEnumRailDirection.ASCENDING_EAST
                        && !World.doesBlockHaveSolidTopSurface(world, blockPos.offsetEast())) {
                    b0 = 1;
                    if (i == 0) {
                        break label48;
                    }
                }

                if (value == BlockRailBaseEnumRailDirection.ASCENDING_WEST
                        && !World.doesBlockHaveSolidTopSurface(world, blockPos.offsetWest())) {
                    b0 = 1;
                    if (i == 0) {
                        break label48;
                    }
                }

                if (value == BlockRailBaseEnumRailDirection.ASCENDING_NORTH
                        && !World.doesBlockHaveSolidTopSurface(world, blockPos.offsetNorth())) {
                    b0 = 1;
                    if (i == 0) {
                        break label48;
                    }
                }

                if (value == BlockRailBaseEnumRailDirection.ASCENDING_SOUTH
                        && !World.doesBlockHaveSolidTopSurface(world, blockPos.offsetSouth())) {
                    b0 = 1;
                }
            }

            if (b0 != 0) {
                this.dropBlockAsItem(world, blockPos, blockState, 0);
                world.setBlockToAir(blockPos);
                if (i == 0) {
                    return;
                }
            }

            this.onNeighborChangedInternal(world, blockPos, blockState, block);
        }
    }

    public static boolean isRailBlock(BlockState blockState) {
        Block block = blockState.getBlock();
        return block == Blocks.rail
                || block == Blocks.golden_rail
                || block == Blocks.detector_rail
                || block == Blocks.activator_rail;
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        if (((BlockRailBaseEnumRailDirection) blockState.getValue(this.getShapeProperty()))
                .isAscending()) {
            world.notifyNeighborsOfStateChange(blockPos.offsetUp(), this);
        }

        if (this.isPowered) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.offsetDown(), this);
        }
    }

    public RayTraceResult collisionRayTrace(
            World world, BlockPos blockPos, Vec3 vec3, Vec3 vec3Value) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.collisionRayTrace(world, blockPos, vec3, vec3Value);
    }

    protected BlockRailBase(boolean isPoweredValue) {
        super(Material.circuits);
        this.isPowered = isPoweredValue;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
}
