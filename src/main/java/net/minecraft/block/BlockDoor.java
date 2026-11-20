package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDoor extends Block {
    public static PropertyEnum HALF_PROP;
    public static PropertyBool POWERED_PROP;
    public static PropertyEnum HINGEPOSITION_PROP;
    public static PropertyBool OPEN_PROP;
    public static PropertyDirection FACING_PROP;

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }

    public Item getItem(World world, BlockPos blockPos) {
        return this.getItem();
    }

    public RayTraceResult collisionRayTrace(
            World world, BlockPos blockPos, Vec3 vec3, Vec3 vec3Value) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.collisionRayTrace(world, blockPos, vec3, vec3Value);
    }

    public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return net.minecraft.block.BlockDoor.isOpen(
                net.minecraft.block.BlockDoor.combineMetadata(iBlockAccess, blockPos));
    }

    protected static boolean isHingeLeft(int p171_0) {
        return (p171_0 & 16) != 0;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    protected BlockDoor(Material material) {
        super(material);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockDoor.FACING_PROP, Facing.NORTH)
                        .withProperty(net.minecraft.block.BlockDoor.OPEN_PROP, false)
                        .withProperty(
                                net.minecraft.block.BlockDoor.HINGEPOSITION_PROP,
                                BlockDoorEnumHingePosition.LEFT)
                        .withProperty(net.minecraft.block.BlockDoor.POWERED_PROP, false)
                        .withProperty(
                                net.minecraft.block.BlockDoor.HALF_PROP,
                                BlockDoorEnumDoorHalf.LOWER));
    }

    protected static boolean isTop(int p171_0) {
        return (p171_0 & 8) != 0;
    }

    public int getMetaFromState(BlockState blockState) {
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        byte b0 = 0;
        int i = l;
        if (blockState.getValue(net.minecraft.block.BlockDoor.HALF_PROP)
                == BlockDoorEnumDoorHalf.UPPER) {
            int j = b0 | 8;
            if (blockState.getValue(net.minecraft.block.BlockDoor.HINGEPOSITION_PROP)
                    == BlockDoorEnumHingePosition.RIGHT) {
                j |= 1;
            }

            if (!(Boolean) blockState.getValue(net.minecraft.block.BlockDoor.POWERED_PROP)) {
                return j;
            }

            j |= 2;
            if (i != 0) {
                return j;
            }
        }

        int k =
                b0
                        | ((Facing) blockState.getValue(net.minecraft.block.BlockDoor.FACING_PROP))
                                .rotateY()
                                .getHorizontalIndex();
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockDoor.OPEN_PROP)) {
            k |= 4;
        }

        return k;
    }

    protected static boolean isOpen(int p171_0) {
        return (p171_0 & 4) != 0;
    }

    public void onBlockHarvested(
            World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        BlockPos blockPosValue = blockPos.offsetDown();
        if (playerEntity.capabilities.isCreativeMode
                && blockState.getValue(net.minecraft.block.BlockDoor.HALF_PROP)
                        == BlockDoorEnumDoorHalf.UPPER
                && world.getBlockState(blockPosValue).getBlock() == this) {
            world.setBlockToAir(blockPosValue);
        }
    }

    public boolean isFullCube() {
        return false;
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (blockState.getValue(net.minecraft.block.BlockDoor.HALF_PROP)
                == BlockDoorEnumDoorHalf.UPPER) {
            {
                BlockPos blockPosValue = blockPos.offsetDown();
                BlockState blockStateValue = world.getBlockState(blockPosValue);
                if (blockStateValue.getBlock() != this) {
                    world.setBlockToAir(blockPos);
                    if (!(i != 0)) {

                        if (block != this) {
                            this.onNeighborBlockChange(
                                    world, blockPosValue, blockStateValue, block);
                        }
                    }
                } else {

                    if (block != this) {
                        this.onNeighborBlockChange(world, blockPosValue, blockStateValue, block);
                    }
                }
            }

            if (i != 0) {
                return;
            }
        }

        byte b0 = 0;
        BlockPos blockPosValue2 = blockPos.offsetUp();
        BlockState blockStateValue2 = world.getBlockState(blockPosValue2);
        if (blockStateValue2.getBlock() != this) {
            world.setBlockToAir(blockPos);
            b0 = 1;
        }

        if (!World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())) {
            world.setBlockToAir(blockPos);
            b0 = 1;
            if (blockStateValue2.getBlock() == this) {
                world.setBlockToAir(blockPosValue2);
            }
        }

        if (b0 != 0) {
            if (world.isRemote) {
                return;
            }

            this.dropBlockAsItem(world, blockPos, blockState, 0);
            if (i != 0) {
                return;
            }
        }

        int j = !world.isBlockPowered(blockPos) && !world.isBlockPowered(blockPosValue2) ? 0 : 1;
        if ((j != 0 || block.canProvidePower())
                && block != this
                && j
                        != remix.compat.compiler.JvmBooleanSlots.toInt(
                                (Boolean)
                                        blockStateValue2.getValue(
                                                net.minecraft.block.BlockDoor.POWERED_PROP))) {
            world.setBlockState(
                    blockPosValue2,
                    blockStateValue2.withProperty(
                            net.minecraft.block.BlockDoor.POWERED_PROP,
                            Boolean.valueOf(remix.compat.compiler.JvmBooleanSlots.fromInt(j))),
                    2);
            if (j
                    != remix.compat.compiler.JvmBooleanSlots.toInt(
                            (Boolean)
                                    blockState.getValue(net.minecraft.block.BlockDoor.OPEN_PROP))) {
                world.setBlockState(
                        blockPos,
                        blockState.withProperty(
                                net.minecraft.block.BlockDoor.OPEN_PROP,
                                Boolean.valueOf(remix.compat.compiler.JvmBooleanSlots.fromInt(j))),
                        2);
                world.markBlockRangeForRenderUpdate(blockPos, blockPos);
                world.playAuxSFXAtEntity((PlayerEntity) null, j != 0 ? 1003 : 1006, blockPos, 0);
            }
        }
    }

    public static int combineMetadata(IBlockAccess iBlockAccess, BlockPos blockPos) {
        net.minecraft.block.state.BlockState blockState = iBlockAccess.getBlockState(blockPos);
        int metaFromState = blockState.getBlock().getMetaFromState(blockState);
        boolean top = net.minecraft.block.BlockDoor.isTop(metaFromState);
        net.minecraft.block.state.BlockState blockStateValue =
                iBlockAccess.getBlockState(blockPos.offsetDown());
        int j = blockStateValue.getBlock().getMetaFromState(blockStateValue);
        int k = top ? j : metaFromState;
        net.minecraft.block.state.BlockState blockStateValue2 =
                iBlockAccess.getBlockState(blockPos.offsetUp());
        int l = blockStateValue2.getBlock().getMetaFromState(blockStateValue2);
        int i1 = top ? metaFromState : l;
        boolean flag1 = ((i1 & 1) != 0);
        boolean flag2 = ((i1 & 2) != 0);
        return net.minecraft.block.BlockDoor.removeHalfBit(k)
                | (top ? 8 : 0)
                | ((flag1) ? 16 : 0)
                | ((flag2) ? 32 : 0);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockDoor.HALF_PROP,
                    net.minecraft.block.BlockDoor.FACING_PROP,
                    net.minecraft.block.BlockDoor.OPEN_PROP,
                    net.minecraft.block.BlockDoor.HINGEPOSITION_PROP,
                    net.minecraft.block.BlockDoor.POWERED_PROP
                });
    }

    // region String decoding support

    // endregion

    public static Facing getFacing(int p171_0) {
        return net.minecraft.util.Facing.getHorizontal(p171_0 & 3).rotateYCCW();
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return blockPos.getY() >= 255
                ? false
                : World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                        && super.canPlaceBlockAt(world, blockPos)
                        && super.canPlaceBlockAt(world, blockPos.offsetUp());
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public BlockState getStateFromMeta(int i) {
        return (i & 8) > 0
                ? this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockDoor.HALF_PROP,
                                BlockDoorEnumDoorHalf.UPPER)
                        .withProperty(
                                net.minecraft.block.BlockDoor.HINGEPOSITION_PROP,
                                (i & 1) > 0
                                        ? BlockDoorEnumHingePosition.RIGHT
                                        : BlockDoorEnumHingePosition.LEFT)
                        .withProperty(net.minecraft.block.BlockDoor.POWERED_PROP, (i & 2) > 0)
                : this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockDoor.HALF_PROP,
                                BlockDoorEnumDoorHalf.LOWER)
                        .withProperty(
                                net.minecraft.block.BlockDoor.FACING_PROP,
                                Facing.getHorizontal(i & 3).rotateYCCW())
                        .withProperty(net.minecraft.block.BlockDoor.OPEN_PROP, (i & 4) > 0);
    }

    protected static int removeHalfBit(int i) {
        return i & 7;
    }

    private void setBoundBasedOnMeta(int i) {
        float f = 0.1875F;
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        Facing facing = getFacing(i);
        int j = k;
        boolean open = isOpen(i);
        boolean flag1 = isHingeLeft(i);
        if (open) {
            if (facing == Facing.EAST) {
                if (!flag1) {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                    if (j != 0) {
                        return;
                    }
                }

                this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                if (j != 0) {
                    return;
                }
            }

            if (facing == Facing.SOUTH) {
                if (!flag1) {
                    this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    if (j != 0) {
                        return;
                    }
                }

                this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
                if (j != 0) {
                    return;
                }
            }

            if (facing == Facing.WEST) {
                if (!flag1) {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                    if (j != 0) {
                        return;
                    }
                }

                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                if (j != 0) {
                    return;
                }
            }

            if (facing != Facing.NORTH) {
                return;
            }

            if (!flag1) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
                if (j != 0) {
                    return;
                }
            }

            this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            if (j != 0) {
                return;
            }
        }

        if (facing == Facing.EAST) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            if (j != 0) {
                return;
            }
        }

        if (facing == Facing.SOUTH) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            if (j != 0) {
                return;
            }
        }

        if (facing == Facing.WEST) {
            this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            if (j != 0) {
                return;
            }
        }

        if (facing == Facing.NORTH) {
            this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }
    }

    private Item getItem() {
        return this == Blocks.iron_door
                ? Items.iron_door
                : (this == Blocks.spruce_door
                        ? Items.spruce_door
                        : (this == Blocks.birch_door
                                ? Items.birch_door
                                : (this == Blocks.jungle_door
                                        ? Items.jungle_door
                                        : (this == Blocks.acacia_door
                                                ? Items.acacia_door
                                                : (this == Blocks.dark_oak_door
                                                        ? Items.dark_oak_door
                                                        : Items.oak_door)))));
    }

    public BoundingBox getSelectedBoundingBox(World world, BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        this.setBoundBasedOnMeta(
                net.minecraft.block.BlockDoor.combineMetadata(iBlockAccess, blockPos));
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (blockState.getValue(net.minecraft.block.BlockDoor.HALF_PROP)
                == BlockDoorEnumDoorHalf.LOWER) {
            BlockState blockStateValue = iBlockAccess.getBlockState(blockPos.offsetUp());
            if (blockStateValue.getBlock() != this) {
                return blockState;
            }

            blockState =
                    blockState
                            .withProperty(
                                    net.minecraft.block.BlockDoor.HINGEPOSITION_PROP,
                                    blockStateValue.getValue(
                                            net.minecraft.block.BlockDoor.HINGEPOSITION_PROP))
                            .withProperty(
                                    net.minecraft.block.BlockDoor.POWERED_PROP,
                                    blockStateValue.getValue(
                                            net.minecraft.block.BlockDoor.POWERED_PROP));
            if (i != 0) {
                return blockState;
            }
        }

        BlockState blockStateValue2 = iBlockAccess.getBlockState(blockPos.offsetDown());
        if (blockStateValue2.getBlock() == this) {
            blockState =
                    blockState
                            .withProperty(
                                    net.minecraft.block.BlockDoor.FACING_PROP,
                                    blockStateValue2.getValue(
                                            net.minecraft.block.BlockDoor.FACING_PROP))
                            .withProperty(
                                    net.minecraft.block.BlockDoor.OPEN_PROP,
                                    blockStateValue2.getValue(
                                            net.minecraft.block.BlockDoor.OPEN_PROP));
        }

        return blockState;
    }

    public int getMobilityFlag() {
        return 1;
    }

    public static boolean isOpen(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return net.minecraft.block.BlockDoor.isOpen(
                net.minecraft.block.BlockDoor.combineMetadata(iBlockAccess, blockPos));
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
        if (this.blockMaterial == Material.iron) {
            return true;
        }

        BlockPos blockPosValue =
                blockState.getValue(net.minecraft.block.BlockDoor.HALF_PROP)
                                == BlockDoorEnumDoorHalf.LOWER
                        ? blockPos
                        : blockPos.offsetDown();
        BlockState blockStateValue =
                blockPos.equals(blockPosValue) ? blockState : world.getBlockState(blockPosValue);
        if (blockStateValue.getBlock() != this) {
            return false;
        }

        blockState = blockStateValue.cycleProperty(net.minecraft.block.BlockDoor.OPEN_PROP);
        world.setBlockState(blockPosValue, blockState, 2);
        world.markBlockRangeForRenderUpdate(blockPosValue, blockPos);
        world.playAuxSFXAtEntity(
                playerEntity,
                ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockDoor.OPEN_PROP))
                        ? 1003
                        : 1006,
                blockPos,
                0);
        return true;
    }

    public static Facing getFacing(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return net.minecraft.block.BlockDoor.getFacing(
                net.minecraft.block.BlockDoor.combineMetadata(iBlockAccess, blockPos));
    }

    static {
        net.minecraft.block.BlockDoor.FACING_PROP =
                PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
        net.minecraft.block.BlockDoor.OPEN_PROP = PropertyBool.create("open");
        net.minecraft.block.BlockDoor.HINGEPOSITION_PROP =
                PropertyEnum.create("hinge", BlockDoorEnumHingePosition.class);
        net.minecraft.block.BlockDoor.POWERED_PROP = PropertyBool.create("powered");
        net.minecraft.block.BlockDoor.HALF_PROP =
                PropertyEnum.create("half", BlockDoorEnumDoorHalf.class);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return blockState.getValue(net.minecraft.block.BlockDoor.HALF_PROP)
                        == BlockDoorEnumDoorHalf.UPPER
                ? null
                : this.getItem();
    }

    public void toggleDoor(World world, BlockPos blockPos, boolean flag) {
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            BlockPos blockPosValue =
                    blockState.getValue(net.minecraft.block.BlockDoor.HALF_PROP)
                                    == BlockDoorEnumDoorHalf.LOWER
                            ? blockPos
                            : blockPos.offsetDown();
            BlockState blockStateValue =
                    blockPos == blockPosValue ? blockState : world.getBlockState(blockPosValue);
            if (blockStateValue.getBlock() == this
                    && (Boolean) blockStateValue.getValue(net.minecraft.block.BlockDoor.OPEN_PROP)
                            != flag) {
                world.setBlockState(
                        blockPosValue,
                        blockStateValue.withProperty(net.minecraft.block.BlockDoor.OPEN_PROP, flag),
                        2);
                world.markBlockRangeForRenderUpdate(blockPosValue, blockPos);
                world.playAuxSFXAtEntity((PlayerEntity) null, flag ? 1003 : 1006, blockPos, 0);
            }
        }
    }
}
