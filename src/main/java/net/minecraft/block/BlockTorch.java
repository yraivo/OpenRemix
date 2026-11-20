package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.util.FacingPlane;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTorch extends Block {

    public static PropertyDirection FACING_PROP;

    static {
        net.minecraft.block.BlockTorch.FACING_PROP =
                PropertyDirection.create("facing", new FacingPredicate());
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        for (java.util.Iterator<?> __recoveryIterator171_2671 =
                        (net.minecraft.block.BlockTorch.FACING_PROP.getAllowedValues()).iterator();
                (__recoveryIterator171_2671).hasNext(); ) {
            Facing facing = (Facing) (__recoveryIterator171_2671).next();
            if (this.canPlaceAt(world, blockPos, facing)) {
                return true;
            }
        }

        return false;
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        this.checkForDrop(world, blockPos, blockState);
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        this.onNeighborChangeInternal(world, blockPos, blockState);
    }

    // region String decoding support

    // endregion

    protected BlockTorch() {
        super(Material.circuits);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockTorch.FACING_PROP, Facing.UP));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    private boolean canPlaceOn(World world, BlockPos blockPos) {
        if (World.doesBlockHaveSolidTopSurface(world, blockPos)) {
            return true;
        }

        Block block = world.getBlockState(blockPos).getBlock();
        return block instanceof BlockFence
                || block == Blocks.glass
                || block == Blocks.cobblestone_wall
                || block == Blocks.stained_glass;
    }

    public RayTraceResult collisionRayTrace(
            World world, BlockPos blockPos, Vec3 vec3, Vec3 vec3Value) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        Facing facing =
                (Facing)
                        world.getBlockState(blockPos)
                                .getValue(net.minecraft.block.BlockTorch.FACING_PROP);
        int i = j;
        float f = 0.15F;
        if (facing == Facing.EAST) {
            this.setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
            if (i != 0) {
                return super.collisionRayTrace(world, blockPos, vec3, vec3Value);
            }
        }

        if (facing == Facing.WEST) {
            this.setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
            if (i != 0) {
                return super.collisionRayTrace(world, blockPos, vec3, vec3Value);
            }
        }

        if (facing == Facing.SOUTH) {
            this.setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
            if (i != 0) {
                return super.collisionRayTrace(world, blockPos, vec3, vec3Value);
            }
        }

        if (facing == Facing.NORTH) {
            this.setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
            if (i != 0) {
                return super.collisionRayTrace(world, blockPos, vec3, vec3Value);
            }
        }

        f = 0.1F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
        return super.collisionRayTrace(world, blockPos, vec3, vec3Value);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean isFullCube() {
        return false;
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
        if (this.canPlaceAt(world, blockPos, facing)) {
            return this.getDefaultState()
                    .withProperty(net.minecraft.block.BlockTorch.FACING_PROP, facing);
        }

        for (java.util.Iterator<?> __recoveryIterator171_9360 = (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_9360).hasNext(); ) {
            Facing facingValue = (Facing) (__recoveryIterator171_9360).next();
            if (world.isBlockNormalCube(blockPos.offset(facingValue.getOpposite()), true)) {
                return this.getDefaultState()
                        .withProperty(net.minecraft.block.BlockTorch.FACING_PROP, facingValue);
            }
        }

        return this.getDefaultState();
    }

    // region String decoding support

    // endregion

    protected boolean checkForDrop(World world, BlockPos blockPos, BlockState blockState) {
        if (blockState.getBlock() == this
                && this.canPlaceAt(
                        world,
                        blockPos,
                        (Facing) blockState.getValue(net.minecraft.block.BlockTorch.FACING_PROP))) {
            return true;
        }

        if (world.getBlockState(blockPos).getBlock() == this) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
        }

        return false;
    }

    protected boolean onNeighborChangeInternal(
            World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!this.checkForDrop(world, blockPos, blockState)) {
            return true;
        }

        byte b0;
        {
            Facing facing =
                    (Facing) blockState.getValue(net.minecraft.block.BlockTorch.FACING_PROP);
            FacingAxis facingAxis = facing.getAxis();
            Facing facingValue = facing.getOpposite();
            b0 = 0;
            if (facingAxis.isHorizontal()
                    && !world.isBlockNormalCube(blockPos.offset(facingValue), true)) {
                b0 = 1;
                if (!(i == 0)) {

                    if (facingAxis.isVertical()
                            && !this.canPlaceOn(world, blockPos.offset(facingValue))) {
                        b0 = 1;
                    }
                }
            } else {

                if (facingAxis.isVertical()
                        && !this.canPlaceOn(world, blockPos.offset(facingValue))) {
                    b0 = 1;
                }
            }
        }

        if (b0 != 0) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
            return true;
        } else {
            return false;
        }
    }

    private boolean canPlaceAt(World world, BlockPos blockPos, Facing facing) {
        BlockPos blockPosValue = blockPos.offset(facing.getOpposite());
        boolean horizontal = facing.getAxis().isHorizontal();
        return horizontal && world.isBlockNormalCube(blockPosValue, true)
                || facing.equals(Facing.UP) && this.canPlaceOn(world, blockPosValue);
    }

    public int getMetaFromState(BlockState blockState) {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        byte b0 = 0;
        int i = k;
        int j;
        switch (((Facing) blockState.getValue(net.minecraft.block.BlockTorch.FACING_PROP))) {
            case EAST:
                j = b0 | 1;
                if (i == 0) {
                    break;
                }
            case WEST:
                j = b0 | 2;
                if (i == 0) {
                    break;
                }
            case SOUTH:
                j = b0 | 3;
                if (i == 0) {
                    break;
                }
            case NORTH:
                j = b0 | 4;
                if (i == 0) {
                    break;
                }
            case DOWN:
            case UP:
            default:
                j = b0 | 5;
        }

        return j;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this, new IProperty[] {net.minecraft.block.BlockTorch.FACING_PROP});
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public BlockState getStateFromMeta(int i) {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockState blockState = this.getDefaultState();
        int j = k;
        switch (i) {
            case 1:
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockTorch.FACING_PROP, Facing.EAST);
                if (j == 0) {
                    break;
                }
            case 2:
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockTorch.FACING_PROP, Facing.WEST);
                if (j == 0) {
                    break;
                }
            case 3:
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockTorch.FACING_PROP, Facing.SOUTH);
                if (j == 0) {
                    break;
                }
            case 4:
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockTorch.FACING_PROP, Facing.NORTH);
                if (j == 0) {
                    break;
                }
            case 5:
            default:
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockTorch.FACING_PROP, Facing.UP);
        }

        return blockState;
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockTorch.FACING_PROP);
        int i = j;
        double d0 = blockPos.getX() + 0.5;
        double d1 = blockPos.getY() + 0.7;
        double d2 = blockPos.getZ() + 0.5;
        double d3 = 0.22;
        double d4 = 0.27;
        if (facing.getAxis().isHorizontal()) {
            Facing facingValue = facing.getOpposite();
            world.spawnParticle(
                    EnumParticleTypes.SMOKE_NORMAL,
                    d0 + d4 * facingValue.getFrontOffsetX(),
                    d1 + d3,
                    d2 + d4 * facingValue.getFrontOffsetZ(),
                    0.0,
                    0.0,
                    0.0);
            world.spawnParticle(
                    EnumParticleTypes.FLAME,
                    d0 + d4 * facingValue.getFrontOffsetX(),
                    d1 + d3,
                    d2 + d4 * facingValue.getFrontOffsetZ(),
                    0.0,
                    0.0,
                    0.0);
            if (i == 0) {
                return;
            }
        }

        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0, 0.0, 0.0);
        world.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0, 0.0, 0.0);
    }

    // Helper used only by this class.
    public static final class FacingPredicate implements com.google.common.base.Predicate {
        public boolean method_cdb21af4cadb5036c34c(net.minecraft.util.Facing facing) {
            return facing != net.minecraft.util.Facing.DOWN;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_cdb21af4cadb5036c34c((net.minecraft.util.Facing) object);
        }
    }
}
