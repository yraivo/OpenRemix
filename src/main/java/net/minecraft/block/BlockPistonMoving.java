package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPistonMoving extends BlockContainer {
    public static final PropertyDirection FACING = BlockPistonExtension.FACING;
    public static final PropertyEnum TYPE = BlockPistonExtension.TYPE;

    private TileEntityPiston getTileEntity(IBlockAccess iBlockAccess, BlockPos blockPos) {
        net.minecraft.tileentity.TileEntity tileEntity = iBlockAccess.getTileEntity(blockPos);
        return tileEntity instanceof net.minecraft.tileentity.TileEntityPiston
                ? (net.minecraft.tileentity.TileEntityPiston) tileEntity
                : null;
    }

    public BlockPistonMoving() {
        super(Material.piston);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockPistonMoving.FACING, Facing.NORTH)
                        .withProperty(
                                net.minecraft.block.BlockPistonMoving.TYPE,
                                BlockPistonExtensionEnumPistonType.DEFAULT));
        this.setHardness(-1.0F);
    }

    public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, Facing facing) {
        return false;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {net.minecraft.block.BlockPistonMoving.FACING, net.minecraft.block.BlockPistonMoving.TYPE});
    }

    public void onBlockDestroyedByPlayer(World world, BlockPos blockPos, BlockState blockState) {
        net.minecraft.util.BlockPos blockPosValue =
                blockPos.offset(
                        ((net.minecraft.util.Facing) blockState.getValue(net.minecraft.block.BlockPistonMoving.FACING))
                                .getOpposite());
        net.minecraft.block.state.BlockState blockStateValue = world.getBlockState(blockPosValue);
        if (blockStateValue.getBlock() instanceof net.minecraft.block.BlockPistonBase
                && (java.lang.Boolean) blockStateValue.getValue(net.minecraft.block.BlockPistonBase.EXTENDED)) {
            world.setBlockToAir(blockPosValue);
        }
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!world.isRemote) {
            world.getTileEntity(blockPos);
        }
    }

    public Item getItem(World world, BlockPos blockPos) {
        return null;
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return null;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return null;
    }

    public boolean isFullCube() {
        return false;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockPistonMoving.FACING, BlockPistonExtension.getFacing(i))
                .withProperty(
                        net.minecraft.block.BlockPistonMoving.TYPE,
                        (i & 8) > 0
                                ? BlockPistonExtensionEnumPistonType.STICKY
                                : BlockPistonExtensionEnumPistonType.DEFAULT);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        TileEntity tileEntity = world.getTileEntity(blockPos);
        int i = j;
        if (tileEntity instanceof TileEntityPiston) {
            ((TileEntityPiston) tileEntity).clearPistonTileEntity();
            if (i != 0) {
                return;
            }
        }

        super.breakBlock(world, blockPos, blockState);
    }

    public BoundingBox getBoundingBox(
            World world, BlockPos blockPos, BlockState blockState, float f, Facing facing) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (blockState.getBlock() != this && blockState.getBlock().getMaterial() != Material.AIR) {
            BoundingBox boundingBox =
                    blockState.getBlock().getCollisionBoundingBox(world, blockPos, blockState);
            if (boundingBox == null) {
                return null;
            }

            double d0;
            double d1;
            double d2;
            double d3;
            double d4;
            double d5;
            {
                d0 = boundingBox.minX;
                d1 = boundingBox.minY;
                d2 = boundingBox.minZ;
                d3 = boundingBox.maxX;
                d4 = boundingBox.maxY;
                d5 = boundingBox.maxZ;
                if (facing.getFrontOffsetX() < 0) {
                    d0 -= facing.getFrontOffsetX() * f;
                    if (!(i == 0)) {

                        d3 -= facing.getFrontOffsetX() * f;
                    }
                } else {

                    d3 -= facing.getFrontOffsetX() * f;
                }
            }

            {
                if (facing.getFrontOffsetY() < 0) {
                    d1 -= facing.getFrontOffsetY() * f;
                    if (!(i == 0)) {

                        d4 -= facing.getFrontOffsetY() * f;
                    }
                } else {

                    d4 -= facing.getFrontOffsetY() * f;
                }
            }

            if (facing.getFrontOffsetZ() < 0) {
                d2 -= facing.getFrontOffsetZ() * f;
                if (i == 0) {
                    return new BoundingBox(d0, d1, d2, d3, d4, d5);
                }
            }

            d5 -= facing.getFrontOffsetZ() * f;
            return new BoundingBox(d0, d1, d2, d3, d4, d5);
        } else {
            return null;
        }
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float p171_3, int p171_4) {
        if (!world.isRemote) {
            net.minecraft.tileentity.TileEntityPiston tileEntityPiston = this.getTileEntity(world, blockPos);
            if (tileEntityPiston != null) {
                net.minecraft.block.state.BlockState blockStateValue = tileEntityPiston.getPistonState();
                blockStateValue.getBlock().dropBlockAsItem(world, blockPos, blockStateValue, 0);
            }
        }
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return false;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        TileEntityPiston tileEntityPiston = this.getTileEntity(iBlockAccess, blockPos);
        if (tileEntityPiston != null) {
            BlockState blockState = tileEntityPiston.getPistonState();
            Block block = blockState.getBlock();
            if (block == this || block.getMaterial() == Material.AIR) {
                return;
            }

            float progress = tileEntityPiston.getProgress(0.0F);
            if (tileEntityPiston.isExtending()) {
                progress = 1.0F - progress;
            }

            block.setBlockBoundsBasedOnState(iBlockAccess, blockPos);
            if (block == Blocks.piston || block == Blocks.sticky_piston) {
                progress = 0.0F;
            }

            Facing facing = tileEntityPiston.getFacing();
            this.minX = block.getBlockBoundsMinX() - facing.getFrontOffsetX() * progress;
            this.minY = block.getBlockBoundsMinY() - facing.getFrontOffsetY() * progress;
            this.minZ = block.getBlockBoundsMinZ() - facing.getFrontOffsetZ() * progress;
            this.maxX = block.getBlockBoundsMaxX() - facing.getFrontOffsetX() * progress;
            this.maxY = block.getBlockBoundsMaxY() - facing.getFrontOffsetY() * progress;
            this.maxZ = block.getBlockBoundsMaxZ() - facing.getFrontOffsetZ() * progress;
        }
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        net.minecraft.tileentity.TileEntityPiston tileEntityPiston = this.getTileEntity(world, blockPos);
        if (tileEntityPiston == null) {
            return null;
        }

        float progress = tileEntityPiston.getProgress(0.0F);
        if (tileEntityPiston.isExtending()) {
            progress = 1.0F - progress;
        }

        return this.getBoundingBox(
                world,
                blockPos,
                tileEntityPiston.getPistonState(),
                progress,
                tileEntityPiston.getFacing());
    }

    public RayTraceResult collisionRayTrace(
            World world, BlockPos blockPos, Vec3 vec3, Vec3 vec3Value) {
        return null;
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float p171_5,
            float p171_6,
            float p171_7) {
        if (!world.isRemote && world.getTileEntity(blockPos) == null) {
            world.setBlockToAir(blockPos);
            return true;
        } else {
            return false;
        }
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i = b0 | ((Facing) blockState.getValue(net.minecraft.block.BlockPistonMoving.FACING)).getIndex();
        if (blockState.getValue(net.minecraft.block.BlockPistonMoving.TYPE)
                == BlockPistonExtensionEnumPistonType.STICKY) {
            i |= 8;
        }

        return i;
    }

    public static TileEntity newTileEntity(
            BlockState blockState, Facing facing, boolean p171_2, boolean p171_3) {
        return new net.minecraft.tileentity.TileEntityPiston(blockState, facing, p171_2, p171_3);
    }
}
