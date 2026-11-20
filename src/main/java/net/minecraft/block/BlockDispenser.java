package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.PositionImpl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.RegistryDefaulted;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDispenser extends BlockContainer {
    protected Random rand = new Random();

    public static PropertyBool TRIGGERED;
    public static PropertyDirection FACING;
    public static RegistryDefaulted dispenseBehaviorRegistry;

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        world.setBlockState(
                blockPos,
                blockState.withProperty(
                        net.minecraft.block.BlockDispenser.FACING,
                        BlockPistonBase.getFacingFromEntity(world, blockPos, livingEntity)),
                2);
        if (itemStack.hasDisplayName()) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityDispenser) {
                ((TileEntityDispenser) tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }

    protected BlockDispenser() {
        super(Material.rock);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockDispenser.FACING, Facing.NORTH)
                        .withProperty(net.minecraft.block.BlockDispenser.TRIGGERED, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public int getRenderType() {
        return 3;
    }

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        return Container.calcRedstoneFromInventory(world.getTileEntity(blockPos));
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int tickRate(World world) {
        return 4;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockDispenser.FACING, getFacing(i))
                .withProperty(net.minecraft.block.BlockDispenser.TRIGGERED, (i & 8) > 0);
    }

    public static Facing getFacing(int i) {
        return Facing.getFront(i & 7);
    }

    protected void dispense(World world, BlockPos blockPos) {
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        BlockSourceImpl blockSourceImpl =
                new BlockSourceImpl(world, blockPos);
        int i = k;
        TileEntityDispenser blockTileEntity =
                (TileEntityDispenser) blockSourceImpl.getBlockTileEntity();
        if (blockTileEntity != null) {
            int j = blockTileEntity.getDispenseSlot();
            if (j < 0) {
                world.playAuxSFX(1001, blockPos, 0);
                if (i != 0) {
                    return;
                }
            }

            ItemStack itemStack = blockTileEntity.getStackInSlot(j);
            IBehaviorDispenseItem iBehaviorDispenseItem = this.getBehavior(itemStack);
            if (iBehaviorDispenseItem != IBehaviorDispenseItem.itemDispenseBehaviorProvider) {
                ItemStack itemStackValue =
                        iBehaviorDispenseItem.dispense(blockSourceImpl, itemStack);
                blockTileEntity.setInventorySlotContents(
                        j, itemStackValue.stackSize == 0 ? null : itemStackValue);
            }
        }
    }

    protected IBehaviorDispenseItem getBehavior(ItemStack itemStack) {
        return (IBehaviorDispenseItem)
                net.minecraft.block.BlockDispenser.dispenseBehaviorRegistry.getObject(
                        itemStack == null ? null : itemStack.getItem());
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int j =
                !world.isBlockPowered(blockPos) && !world.isBlockPowered(blockPos.offsetUp())
                        ? 0
                        : 1;
        boolean value = (Boolean) blockState.getValue(net.minecraft.block.BlockDispenser.TRIGGERED);
        if (j != 0 && !value) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
            world.setBlockState(
                    blockPos, blockState.withProperty(net.minecraft.block.BlockDispenser.TRIGGERED, true), 4);
            if (i == 0) {
                return;
            }
        }

        if (j == 0 && value) {
            world.setBlockState(
                    blockPos, blockState.withProperty(net.minecraft.block.BlockDispenser.TRIGGERED, false), 4);
        }
    }

    private void setDefaultDirection(World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (!world.isRemote) {
            Facing facing;
            label45:
            {
                facing = (Facing) blockState.getValue(net.minecraft.block.BlockDispenser.FACING);
                boolean fullBlockValue =
                        world.getBlockState(blockPos.offsetNorth()).getBlock().isFullBlock();
                boolean fullBlockValue2 =
                        world.getBlockState(blockPos.offsetSouth()).getBlock().isFullBlock();
                if (facing == Facing.NORTH && fullBlockValue && !fullBlockValue2) {
                    facing = Facing.SOUTH;
                    if (i != 0) {
                        break label45;
                    }
                }

                if (facing == Facing.SOUTH && fullBlockValue2 && !fullBlockValue) {
                    facing = Facing.NORTH;
                    if (i != 0) {
                        break label45;
                    }
                }

                boolean fullBlockValue3 =
                        world.getBlockState(blockPos.offsetWest()).getBlock().isFullBlock();
                boolean fullBlockValue4 =
                        world.getBlockState(blockPos.offsetEast()).getBlock().isFullBlock();
                if (facing == Facing.WEST && fullBlockValue3 && !fullBlockValue4) {
                    facing = Facing.EAST;
                    if (i != 0) {
                        break label45;
                    }
                }

                if (facing == Facing.EAST && fullBlockValue4 && !fullBlockValue3) {
                    facing = Facing.WEST;
                }
            }

            world.setBlockState(
                    blockPos,
                    blockState
                            .withProperty(net.minecraft.block.BlockDispenser.FACING, facing)
                            .withProperty(net.minecraft.block.BlockDispenser.TRIGGERED, false),
                    2);
        }
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        this.setDefaultDirection(world, blockPos, blockState);
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityDispenser) {
            InventoryHelper.dropInventoryItems(world, blockPos, (TileEntityDispenser) tileEntity);
            world.updateComparatorOutputLevel(blockPos, this);
        }

        super.breakBlock(world, blockPos, blockState);
    }

    static {
        net.minecraft.block.BlockDispenser.FACING = PropertyDirection.create("facing");
        net.minecraft.block.BlockDispenser.TRIGGERED = PropertyBool.create("triggered");
        net.minecraft.block.BlockDispenser.dispenseBehaviorRegistry =
                new RegistryDefaulted(new BehaviorDefaultDispenseItem());
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {net.minecraft.block.BlockDispenser.FACING, net.minecraft.block.BlockDispenser.TRIGGERED});
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityDispenser();
    }

    public BlockState getStateForEntityRender(BlockState blockState) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockDispenser.FACING, Facing.SOUTH);
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (!world.isRemote) {
            this.dispense(world, blockPos);
        }
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i = b0 | ((Facing) blockState.getValue(net.minecraft.block.BlockDispenser.FACING)).getIndex();
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockDispenser.TRIGGERED)) {
            i |= 8;
        }

        return i;
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
                        net.minecraft.block.BlockDispenser.FACING,
                        BlockPistonBase.getFacingFromEntity(world, blockPos, livingEntity))
                .withProperty(net.minecraft.block.BlockDispenser.TRIGGERED, false);
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
        if (world.isRemote) {
            return true;
        }

        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityDispenser) {
            playerEntity.displayGUIChest((TileEntityDispenser) tileEntity);
        }

        return true;
    }

    public static IPosition getDispensePosition(IBlockSource iBlockSource) {
        Facing facing = getFacing(iBlockSource.getBlockMetadata());
        double d0 = iBlockSource.getX() + 0.7 * facing.getFrontOffsetX();
        double d1 = iBlockSource.getY() + 0.7 * facing.getFrontOffsetY();
        double d2 = iBlockSource.getZ() + 0.7 * facing.getFrontOffsetZ();
        return new PositionImpl(d0, d1, d2);
    }
}
