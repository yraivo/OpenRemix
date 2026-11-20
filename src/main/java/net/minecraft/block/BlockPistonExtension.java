package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockPistonExtension extends Block {
    public static PropertyDirection FACING;

    public static PropertyEnum TYPE;
    public static PropertyBool SHORT;

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockPistonExtension.FACING,
                    net.minecraft.block.BlockPistonExtension.TYPE,
                    net.minecraft.block.BlockPistonExtension.SHORT
                });
    }

    // region String decoding support

    // endregion

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {
        this.applyHeadBounds(blockState);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        this.applyCoreBounds(blockState);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    // region String decoding support

    // endregion

    private void applyCoreBounds(BlockState blockState) {

        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();

        int i = j;

        switch (((Facing) blockState.getValue(net.minecraft.block.BlockPistonExtension.FACING))) {
            case DOWN:
                this.setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
                if (i == 0) {
                    break;
                }
            case UP:
                this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
                if (i == 0) {
                    break;
                }
            case NORTH:
                this.setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
                if (i == 0) {
                    break;
                }
            case SOUTH:
                this.setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
                if (i == 0) {
                    break;
                }
            case WEST:
                this.setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
                if (i == 0) {
                    break;
                }
            case EAST:
                this.setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
        }
    }

    static {
        net.minecraft.block.BlockPistonExtension.FACING = PropertyDirection.create("facing");
        net.minecraft.block.BlockPistonExtension.TYPE =
                PropertyEnum.create("type", BlockPistonExtensionEnumPistonType.class);
        net.minecraft.block.BlockPistonExtension.SHORT = PropertyBool.create("short");
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((Facing)
                                        blockState.getValue(
                                                net.minecraft.block.BlockPistonExtension.FACING))
                                .getIndex();
        if (blockState.getValue(net.minecraft.block.BlockPistonExtension.TYPE)
                == BlockPistonExtensionEnumPistonType.STICKY) {
            i |= 8;
        }

        return i;
    }

    public boolean isFullCube() {
        return false;
    }

    public void onBlockHarvested(
            World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        if (playerEntity.capabilities.isCreativeMode) {
            Facing facing =
                    (Facing) blockState.getValue(net.minecraft.block.BlockPistonExtension.FACING);
            if (facing != null) {
                BlockPos blockPosValue = blockPos.offset(facing.getOpposite());
                Block block = world.getBlockState(blockPosValue).getBlock();
                if (block == Blocks.piston || block == Blocks.sticky_piston) {
                    world.setBlockToAir(blockPosValue);
                }
            }
        }

        super.onBlockHarvested(world, blockPos, blockState, playerEntity);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        Facing facing =
                (Facing) blockState.getValue(net.minecraft.block.BlockPistonExtension.FACING);
        BlockPos blockPosValue = blockPos.offset(facing.getOpposite());
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        BlockState blockStateValue = world.getBlockState(blockPosValue);
        int i = j;
        if (blockStateValue.getBlock() != Blocks.piston
                && blockStateValue.getBlock() != Blocks.sticky_piston) {
            world.setBlockToAir(blockPos);
            if (i != 0) {
                return;
            }
        }

        blockStateValue
                .getBlock()
                .onNeighborBlockChange(world, blockPosValue, blockStateValue, block);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        this.applyHeadBounds(iBlockAccess.getBlockState(blockPos));
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        Facing facing =
                ((Facing) blockState.getValue(net.minecraft.block.BlockPistonExtension.FACING))
                        .getOpposite();
        blockPos = blockPos.offset(facing);
        BlockState blockStateValue = world.getBlockState(blockPos);
        if ((blockStateValue.getBlock() == Blocks.piston
                        || blockStateValue.getBlock() == Blocks.sticky_piston)
                && (Boolean) blockStateValue.getValue(BlockPistonBase.EXTENDED)) {
            blockStateValue.getBlock().dropBlockAsItem(world, blockPos, blockStateValue, 0);
            world.setBlockToAir(blockPos);
        }
    }

    public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, Facing facing) {
        return false;
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return false;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockPistonExtension.FACING, getFacing(i))
                .withProperty(
                        net.minecraft.block.BlockPistonExtension.TYPE,
                        (i & 8) > 0
                                ? BlockPistonExtensionEnumPistonType.STICKY
                                : BlockPistonExtensionEnumPistonType.DEFAULT);
    }

    public void applyHeadBounds(BlockState blockState) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();

        int i = j;
        Facing facing =
                (Facing) blockState.getValue(net.minecraft.block.BlockPistonExtension.FACING);
        if (facing != null) {
            switch (facing) {
                case DOWN:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
                    if (i != 0) {
                        break;
                    }
                case UP:
                    this.setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
                    if (i != 0) {
                        break;
                    }
                case NORTH:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
                    if (i != 0) {
                        break;
                    }
                case SOUTH:
                    this.setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
                    if (i != 0) {
                        break;
                    }
                case WEST:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
                    if (i != 0) {
                        break;
                    }
                case EAST:
                    this.setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    public Item getItem(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos).getValue(net.minecraft.block.BlockPistonExtension.TYPE)
                        == BlockPistonExtensionEnumPistonType.STICKY
                ? Item.getItemFromBlock(Blocks.sticky_piston)
                : Item.getItemFromBlock(Blocks.piston);
    }

    public BlockPistonExtension() {
        super(Material.piston);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockPistonExtension.FACING, Facing.NORTH)
                        .withProperty(
                                net.minecraft.block.BlockPistonExtension.TYPE,
                                BlockPistonExtensionEnumPistonType.DEFAULT)
                        .withProperty(net.minecraft.block.BlockPistonExtension.SHORT, false));
        this.setStepSound(net.minecraft.block.Block.soundTypePiston);
        this.setHardness(0.5F);
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return true;
    }

    public static Facing getFacing(int i) {
        int j = i & 7;
        return j > 5 ? null : Facing.getFront(j);
    }
}
