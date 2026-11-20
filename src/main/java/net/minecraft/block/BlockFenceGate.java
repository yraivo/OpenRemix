package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFenceGate extends BlockDirectional {
    public static PropertyBool IN_WALL;
    public static PropertyBool POWERED;

    public static PropertyBool OPEN;

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE))
                                .getHorizontalIndex();
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockFenceGate.POWERED)) {
            i |= 8;
        }

        if ((Boolean) blockState.getValue(net.minecraft.block.BlockFenceGate.OPEN)) {
            i |= 4;
        }

        return i;
    }

    static {
        net.minecraft.block.BlockFenceGate.OPEN = PropertyBool.create("open");
        net.minecraft.block.BlockFenceGate.POWERED = PropertyBool.create("powered");
        net.minecraft.block.BlockFenceGate.IN_WALL = PropertyBool.create("in_wall");
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote) {
            boolean blockPowered = world.isBlockPowered(blockPos);
            if (blockPowered || block.canProvidePower()) {
                if (blockPowered
                        && !(Boolean) blockState.getValue(net.minecraft.block.BlockFenceGate.OPEN)
                        && !(Boolean) blockState.getValue(net.minecraft.block.BlockFenceGate.POWERED)) {
                    world.setBlockState(
                            blockPos,
                            blockState
                                    .withProperty(net.minecraft.block.BlockFenceGate.OPEN, true)
                                    .withProperty(net.minecraft.block.BlockFenceGate.POWERED, true),
                            2);
                    world.playAuxSFXAtEntity((PlayerEntity) null, 1003, blockPos, 0);
                    if (i == 0) {
                        return;
                    }
                }

                if (!blockPowered
                        && (Boolean) blockState.getValue(net.minecraft.block.BlockFenceGate.OPEN)
                        && (Boolean) blockState.getValue(net.minecraft.block.BlockFenceGate.POWERED)) {
                    world.setBlockState(
                            blockPos,
                            blockState
                                    .withProperty(net.minecraft.block.BlockFenceGate.OPEN, false)
                                    .withProperty(net.minecraft.block.BlockFenceGate.POWERED, false),
                            2);
                    world.playAuxSFXAtEntity((PlayerEntity) null, 1006, blockPos, 0);
                    if (i == 0) {
                        return;
                    }
                }

                if (blockPowered != (Boolean) blockState.getValue(net.minecraft.block.BlockFenceGate.POWERED)) {
                    world.setBlockState(
                            blockPos,
                            blockState.withProperty(net.minecraft.block.BlockFenceGate.POWERED, blockPowered),
                            2);
                }
            }
        }
    }

    // region String decoding support

    // endregion

    public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return (Boolean) iBlockAccess.getBlockState(blockPos).getValue(net.minecraft.block.BlockFenceGate.OPEN);
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
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockDirectional.AGE, livingEntity.getHorizontalFacing())
                .withProperty(net.minecraft.block.BlockFenceGate.OPEN, false)
                .withProperty(net.minecraft.block.BlockFenceGate.POWERED, false)
                .withProperty(net.minecraft.block.BlockFenceGate.IN_WALL, false);
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockFenceGate.OPEN)) {
            return null;
        }

        FacingAxis facingAxis =
                ((Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE)).getAxis();
        return facingAxis == FacingAxis.Z
                ? new BoundingBox(
                        blockPos.getX(),
                        blockPos.getY(),
                        blockPos.getZ() + 0.375F,
                        blockPos.getX() + 1,
                        blockPos.getY() + 1.5F,
                        blockPos.getZ() + 0.625F)
                : new BoundingBox(
                        blockPos.getX() + 0.375F,
                        blockPos.getY(),
                        blockPos.getZ(),
                        blockPos.getX() + 0.625F,
                        blockPos.getY() + 1.5F,
                        blockPos.getZ() + 1);
    }

    public BlockFenceGate() {
        super(Material.wood);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockFenceGate.OPEN, false)
                        .withProperty(net.minecraft.block.BlockFenceGate.POWERED, false)
                        .withProperty(net.minecraft.block.BlockFenceGate.IN_WALL, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial().isSolid()
                ? super.canPlaceBlockAt(world, blockPos)
                : false;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockDirectional.AGE,
                    net.minecraft.block.BlockFenceGate.OPEN,
                    net.minecraft.block.BlockFenceGate.POWERED,
                    net.minecraft.block.BlockFenceGate.IN_WALL
                });
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        FacingAxis facingAxis =
                ((Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE)).getAxis();
        if (facingAxis == FacingAxis.Z
                        && (iBlockAccess.getBlockState(blockPos.offsetWest()).getBlock()
                                        == Blocks.cobblestone_wall
                                || iBlockAccess.getBlockState(blockPos.offsetEast()).getBlock()
                                        == Blocks.cobblestone_wall)
                || facingAxis == FacingAxis.X
                        && (iBlockAccess.getBlockState(blockPos.offsetNorth()).getBlock()
                                        == Blocks.cobblestone_wall
                                || iBlockAccess.getBlockState(blockPos.offsetSouth()).getBlock()
                                        == Blocks.cobblestone_wall)) {
            blockState = blockState.withProperty(net.minecraft.block.BlockFenceGate.IN_WALL, true);
        }

        return blockState;
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return true;
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
        label23:
        {
            int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
            if ((Boolean) blockState.getValue(net.minecraft.block.BlockFenceGate.OPEN)) {
                blockState = blockState.withProperty(net.minecraft.block.BlockFenceGate.OPEN, false);
                world.setBlockState(blockPos, blockState, 2);
                if (i == 0) {
                    break label23;
                }
            }

            Facing facingValue = Facing.fromAngle(playerEntity.rotationYaw);
            if (blockState.getValue(net.minecraft.block.BlockDirectional.AGE) == facingValue.getOpposite()) {
                blockState = blockState.withProperty(net.minecraft.block.BlockDirectional.AGE, facingValue);
            }

            blockState = blockState.withProperty(net.minecraft.block.BlockFenceGate.OPEN, true);
            world.setBlockState(blockPos, blockState, 2);
        }

        world.playAuxSFXAtEntity(
                playerEntity,
                ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockFenceGate.OPEN)) ? 1003 : 1006,
                blockPos,
                0);
        return true;
    }

    // region String decoding support

    // endregion

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockDirectional.AGE, Facing.getHorizontal(i))
                .withProperty(net.minecraft.block.BlockFenceGate.OPEN, (i & 4) != 0)
                .withProperty(net.minecraft.block.BlockFenceGate.POWERED, (i & 8) != 0);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        FacingAxis facingAxis =
                ((Facing) iBlockAccess.getBlockState(blockPos).getValue(net.minecraft.block.BlockDirectional.AGE))
                        .getAxis();
        int i = j;
        if (facingAxis == FacingAxis.Z) {
            this.setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
            if (i == 0) {
                return;
            }
        }

        this.setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
    }
}
