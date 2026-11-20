package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFurnace extends BlockContainer {
    public static PropertyDirection FACING;

    private static boolean keepInventory;
    private boolean isBurning;

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityFurnace();
    }

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        world.setBlockState(
                blockPos,
                blockState.withProperty(
                        net.minecraft.block.BlockFurnace.FACING,
                        livingEntity.getHorizontalFacing().getOpposite()),
                2);
        if (itemStack.hasDisplayName()) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityFurnace) {
                ((TileEntityFurnace) tileEntity).setCustomInventoryName(itemStack.getDisplayName());
            }
        }
    }

    public int getRenderType() {
        return 3;
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        this.setDefaultFacing(world, blockPos, blockState);
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (this.isBurning) {
            Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockFurnace.FACING);
            double d0 = blockPos.getX() + 0.5;
            double d1 = blockPos.getY() + random.nextDouble() * 6.0 / 16.0;
            double d2 = blockPos.getZ() + 0.5;
            double d3 = 0.52;
            double d4 = random.nextDouble() * 0.6 - 0.3;
            switch (facing) {
                case WEST:
                    world.spawnParticle(
                            EnumParticleTypes.SMOKE_NORMAL,
                            d0 - d3,
                            d1,
                            d2 + d4,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                    world.spawnParticle(
                            EnumParticleTypes.FLAME,
                            d0 - d3,
                            d1,
                            d2 + d4,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                    if (i != 0) {
                        break;
                    }
                case EAST:
                    world.spawnParticle(
                            EnumParticleTypes.SMOKE_NORMAL,
                            d0 + d3,
                            d1,
                            d2 + d4,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                    world.spawnParticle(
                            EnumParticleTypes.FLAME,
                            d0 + d3,
                            d1,
                            d2 + d4,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                    if (i != 0) {
                        break;
                    }
                case NORTH:
                    world.spawnParticle(
                            EnumParticleTypes.SMOKE_NORMAL,
                            d0 + d4,
                            d1,
                            d2 - d3,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                    world.spawnParticle(
                            EnumParticleTypes.FLAME,
                            d0 + d4,
                            d1,
                            d2 - d3,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                    if (i != 0) {
                        break;
                    }
                case SOUTH:
                    world.spawnParticle(
                            EnumParticleTypes.SMOKE_NORMAL,
                            d0 + d4,
                            d1,
                            d2 + d3,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                    world.spawnParticle(
                            EnumParticleTypes.FLAME,
                            d0 + d4,
                            d1,
                            d2 + d3,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
            }
        }
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Item.getItemFromBlock(Blocks.furnace);
    }

    public BlockState getStateFromMeta(int i) {
        Facing facing = Facing.getFront(i);
        if (facing.getAxis() == FacingAxis.Y) {
            facing = Facing.NORTH;
        }

        return this.getDefaultState().withProperty(net.minecraft.block.BlockFurnace.FACING, facing);
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    protected BlockFurnace(boolean ahvValue) {
        super(Material.rock);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockFurnace.FACING, Facing.NORTH));
        this.isBurning = ahvValue;
    }

    // region String decoding support

    // endregion

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        return Container.calcRedstoneFromInventory(world.getTileEntity(blockPos));
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        if (!net.minecraft.block.BlockFurnace.keepInventory) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityFurnace) {
                InventoryHelper.dropInventoryItems(world, blockPos, (TileEntityFurnace) tileEntity);
                world.updateComparatorOutputLevel(blockPos, this);
            }
        }

        super.breakBlock(world, blockPos, blockState);
    }

    private void setDefaultFacing(World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (!world.isRemote) {
            Facing facing;
            label45:
            {
                Block block = world.getBlockState(blockPos.offsetNorth()).getBlock();
                Block blockValue = world.getBlockState(blockPos.offsetSouth()).getBlock();
                Block blockValue2 = world.getBlockState(blockPos.offsetWest()).getBlock();
                Block blockValue3 = world.getBlockState(blockPos.offsetEast()).getBlock();
                facing = (Facing) blockState.getValue(net.minecraft.block.BlockFurnace.FACING);
                if (facing == Facing.NORTH && block.isFullBlock() && !blockValue.isFullBlock()) {
                    facing = Facing.SOUTH;
                    if (i != 0) {
                        break label45;
                    }
                }

                if (facing == Facing.SOUTH && blockValue.isFullBlock() && !block.isFullBlock()) {
                    facing = Facing.NORTH;
                    if (i != 0) {
                        break label45;
                    }
                }

                if (facing == Facing.WEST
                        && blockValue2.isFullBlock()
                        && !blockValue3.isFullBlock()) {
                    facing = Facing.EAST;
                    if (i != 0) {
                        break label45;
                    }
                }

                if (facing == Facing.EAST
                        && blockValue3.isFullBlock()
                        && !blockValue2.isFullBlock()) {
                    facing = Facing.WEST;
                }
            }

            world.setBlockState(
                    blockPos,
                    blockState.withProperty(net.minecraft.block.BlockFurnace.FACING, facing),
                    2);
        }
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
                .withProperty(
                        net.minecraft.block.BlockFurnace.FACING,
                        livingEntity.getHorizontalFacing().getOpposite());
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
        if (world.isRemote) {
            return true;
        }

        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityFurnace) {
            playerEntity.displayGUIChest((TileEntityFurnace) tileEntity);
        }

        return true;
    }

    static {
        net.minecraft.block.BlockFurnace.FACING =
                PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
    }

    public int getMetaFromState(BlockState blockState) {
        return ((Facing) blockState.getValue(net.minecraft.block.BlockFurnace.FACING)).getIndex();
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.furnace);
    }

    public static void setState(boolean flag, World world, BlockPos blockPos) {
        TileEntity tileEntity;
        label15:
        {
            int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
            BlockState blockState = world.getBlockState(blockPos);
            int i = j;
            tileEntity = world.getTileEntity(blockPos);
            net.minecraft.block.BlockFurnace.keepInventory = true;
            if (flag) {
                world.setBlockState(
                        blockPos,
                        Blocks.lit_furnace
                                .getDefaultState()
                                .withProperty(
                                        net.minecraft.block.BlockFurnace.FACING,
                                        blockState.getValue(
                                                net.minecraft.block.BlockFurnace.FACING)),
                        3);
                world.setBlockState(
                        blockPos,
                        Blocks.lit_furnace
                                .getDefaultState()
                                .withProperty(
                                        net.minecraft.block.BlockFurnace.FACING,
                                        blockState.getValue(
                                                net.minecraft.block.BlockFurnace.FACING)),
                        3);
                if (i != 0) {
                    break label15;
                }
            }

            world.setBlockState(
                    blockPos,
                    Blocks.furnace
                            .getDefaultState()
                            .withProperty(
                                    net.minecraft.block.BlockFurnace.FACING,
                                    blockState.getValue(net.minecraft.block.BlockFurnace.FACING)),
                    3);
            world.setBlockState(
                    blockPos,
                    Blocks.furnace
                            .getDefaultState()
                            .withProperty(
                                    net.minecraft.block.BlockFurnace.FACING,
                                    blockState.getValue(net.minecraft.block.BlockFurnace.FACING)),
                    3);
        }

        net.minecraft.block.BlockFurnace.keepInventory = false;
        if (tileEntity != null) {
            tileEntity.validate();
            world.setTileEntity(blockPos, tileEntity);
        }
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this, new IProperty[] {net.minecraft.block.BlockFurnace.FACING});
    }

    public BlockState getStateForEntityRender(BlockState blockState) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockFurnace.FACING, Facing.SOUTH);
    }
}
