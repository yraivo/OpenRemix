package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrapDoor extends Block {
    public static PropertyDirection FACING;

    public static PropertyEnum HALF;
    public static PropertyBool OPEN;

    protected BlockTrapDoor(Material material) {
        super(material);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockTrapDoor.FACING, Facing.NORTH)
                        .withProperty(net.minecraft.block.BlockTrapDoor.OPEN, false)
                        .withProperty(
                                net.minecraft.block.BlockTrapDoor.HALF,
                                BlockTrapDoorDoorHalf.BOTTOM));

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f1,
            float f,
            float f2,
            int i,
            LivingEntity livingEntity) {
        BlockState blockState = this.getDefaultState();
        if (facing.getAxis().isHorizontal()) {
            blockState =
                    blockState
                            .withProperty(net.minecraft.block.BlockTrapDoor.FACING, facing)
                            .withProperty(net.minecraft.block.BlockTrapDoor.OPEN, false);
            blockState =
                    blockState.withProperty(
                            net.minecraft.block.BlockTrapDoor.HALF,
                            f > 0.5F ? BlockTrapDoorDoorHalf.TOP : BlockTrapDoorDoorHalf.BOTTOM);
        }

        return blockState;
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote) {
            BlockPos blockPosValue =
                    blockPos.offset(
                            ((Facing) blockState.getValue(net.minecraft.block.BlockTrapDoor.FACING))
                                    .getOpposite());
            if (!isValidSupportBlock(world.getBlockState(blockPosValue).getBlock())) {
                world.setBlockToAir(blockPos);
                this.dropBlockAsItem(world, blockPos, blockState, 0);
                if (i == 0) {
                    return;
                }
            }

            boolean blockPowered = world.isBlockPowered(blockPos);
            if (blockPowered || block.canProvidePower()) {
                boolean value =
                        (Boolean) blockState.getValue(net.minecraft.block.BlockTrapDoor.OPEN);
                if (value != blockPowered) {
                    world.setBlockState(
                            blockPos,
                            blockState.withProperty(
                                    net.minecraft.block.BlockTrapDoor.OPEN, blockPowered),
                            2);
                    world.playAuxSFXAtEntity(
                            (PlayerEntity) null, blockPowered ? 1003 : 1006, blockPos, 0);
                }
            }
        }
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    protected static Facing getFacing(int i) {
        switch (i & 3) {
            case 0:
                return Facing.NORTH;
            case 1:
                return Facing.SOUTH;
            case 2:
                return Facing.WEST;
            case 3:
            default:
                return Facing.EAST;
        }
    }

    public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, Facing facing) {
        return !facing.getAxis().isVertical()
                && isValidSupportBlock(
                        world.getBlockState(blockPos.offset(facing.getOpposite())).getBlock());
    }

    protected static int getMetaForFacing(Facing facing) {
        switch (facing) {
            case NORTH:
                return 0;
            case SOUTH:
                return 1;
            case WEST:
                return 2;
            case EAST:
            default:
                return 3;
        }
    }

    public void setBlockBoundsForItemRender() {

        this.setBlockBounds(0.0F, 0.40625F, 0.0F, 1.0F, 0.59375F, 1.0F);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockTrapDoor.FACING, getFacing(i))
                .withProperty(net.minecraft.block.BlockTrapDoor.OPEN, (i & 4) != 0)
                .withProperty(
                        net.minecraft.block.BlockTrapDoor.HALF,
                        (i & 8) == 0 ? BlockTrapDoorDoorHalf.BOTTOM : BlockTrapDoorDoorHalf.TOP);
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | getMetaForFacing(
                                (Facing)
                                        blockState.getValue(
                                                net.minecraft.block.BlockTrapDoor.FACING));
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockTrapDoor.OPEN)) {
            i |= 4;
        }

        if (blockState.getValue(net.minecraft.block.BlockTrapDoor.HALF)
                == BlockTrapDoorDoorHalf.TOP) {
            i |= 8;
        }

        return i;
    }

    // region String decoding support

    // endregion

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }

    private static boolean isValidSupportBlock(Block block) {
        return block.blockMaterial.isOpaque() && block.isFullCube()
                || block == Blocks.glowstone
                || block instanceof BlockSlab
                || block instanceof BlockStairs;
    }

    public boolean isFullCube() {
        return false;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockTrapDoor.FACING,
                    net.minecraft.block.BlockTrapDoor.OPEN,
                    net.minecraft.block.BlockTrapDoor.HALF
                });
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        this.setBounds(iBlockAccess.getBlockState(blockPos));
    }

    public BoundingBox getSelectedBoundingBox(World world, BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }

    public RayTraceResult collisionRayTrace(
            World world, BlockPos blockPos, Vec3 vec3, Vec3 vec3Value) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.collisionRayTrace(world, blockPos, vec3, vec3Value);
    }

    public void setBounds(BlockState blockState) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (blockState.getBlock() == this) {
            Boolean obool;
            Facing facing;
            {
                int j =
                        blockState.getValue(net.minecraft.block.BlockTrapDoor.HALF)
                                        == BlockTrapDoorDoorHalf.TOP
                                ? 1
                                : 0;
                obool = (Boolean) blockState.getValue(net.minecraft.block.BlockTrapDoor.OPEN);
                facing = (Facing) blockState.getValue(net.minecraft.block.BlockTrapDoor.FACING);

                if (j != 0) {
                    this.setBlockBounds(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 1.0F);
                    if (!(i == 0)) {

                        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
                    }
                } else {

                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
                }
            }

            if (obool) {
                if (facing == Facing.NORTH) {
                    this.setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
                }

                if (facing == Facing.SOUTH) {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
                }

                if (facing == Facing.WEST) {
                    this.setBlockBounds(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }

                if (facing == Facing.EAST) {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
                }
            }
        }
    }

    static {
        net.minecraft.block.BlockTrapDoor.FACING =
                PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
        net.minecraft.block.BlockTrapDoor.OPEN = PropertyBool.create("open");
        net.minecraft.block.BlockTrapDoor.HALF =
                PropertyEnum.create("half", BlockTrapDoorDoorHalf.class);
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
        if (this.blockMaterial == Material.iron) {
            return true;
        }

        blockState = blockState.cycleProperty(net.minecraft.block.BlockTrapDoor.OPEN);
        world.setBlockState(blockPos, blockState, 2);
        world.playAuxSFXAtEntity(
                playerEntity,
                ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockTrapDoor.OPEN))
                        ? 1003
                        : 1006,
                blockPos,
                0);
        return true;
    }

    public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return !(Boolean)
                iBlockAccess
                        .getBlockState(blockPos)
                        .getValue(net.minecraft.block.BlockTrapDoor.OPEN);
    }
}
