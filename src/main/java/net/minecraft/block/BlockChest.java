package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.util.FacingPlane;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class BlockChest extends BlockContainer {
    private final Random rand = new Random();

    public int chestType;

    public static PropertyDirection FACING_PROP;

    public boolean isOpaqueCube() {
        return false;
    }

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float p171_3,
            float p171_4,
            float p171_5,
            int p171_6,
            LivingEntity livingEntity) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockChest.FACING_PROP, livingEntity.getHorizontalFacing());
    }

    public boolean canProvidePower() {
        return this.chestType == 1;
    }

    public boolean isFullCube() {
        return false;
    }

    public BlockState correctFacing(World world, BlockPos blockPos, BlockState blockState) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        Facing facing = null;
        Iterator iterator = FacingPlane.HORIZONTAL.iterator();
        int i = j;

        while (iterator.hasNext()) {
            Facing facingValue = (Facing) iterator.next();
            BlockState blockStateValue = world.getBlockState(blockPos.offset(facingValue));
            if (blockStateValue.getBlock() == this) {
                return blockState;
            }

            if (blockStateValue.getBlock().isFullBlock()) {
                if (facing != null) {
                    facing = null;
                    break;
                }

                facing = facingValue;
            }

            if (i == 0) {
                break;
            }
        }

        if (facing != null) {
            return blockState.withProperty(net.minecraft.block.BlockChest.FACING_PROP, facing.getOpposite());
        }

        Facing facingValue2 = (Facing) blockState.getValue(net.minecraft.block.BlockChest.FACING_PROP);
        if (world.getBlockState(blockPos.offset(facingValue2)).getBlock().isFullBlock()) {
            facingValue2 = facingValue2.getOpposite();
        }

        if (world.getBlockState(blockPos.offset(facingValue2)).getBlock().isFullBlock()) {
            facingValue2 = facingValue2.rotateY();
        }

        if (world.getBlockState(blockPos.offset(facingValue2)).getBlock().isFullBlock()) {
            facingValue2 = facingValue2.getOpposite();
        }

        return blockState.withProperty(net.minecraft.block.BlockChest.FACING_PROP, facingValue2);
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        int i = 0;
        net.minecraft.util.BlockPos blockPosValue = blockPos.offsetWest();
        net.minecraft.util.BlockPos blockPosValue2 = blockPos.offsetEast();
        net.minecraft.util.BlockPos blockPosValue3 = blockPos.offsetNorth();
        net.minecraft.util.BlockPos blockPosValue4 = blockPos.offsetSouth();
        if (world.getBlockState(blockPosValue).getBlock() == this) {
            if (this.isSurroundingBlockChest(world, blockPosValue)) {
                return false;
            }

            i++;
        }

        if (world.getBlockState(blockPosValue2).getBlock() == this) {
            if (this.isSurroundingBlockChest(world, blockPosValue2)) {
                return false;
            }

            i++;
        }

        if (world.getBlockState(blockPosValue3).getBlock() == this) {
            if (this.isSurroundingBlockChest(world, blockPosValue3)) {
                return false;
            }

            i++;
        }

        if (world.getBlockState(blockPosValue4).getBlock() == this) {
            if (this.isSurroundingBlockChest(world, blockPosValue4)) {
                return false;
            }

            i++;
        }

        return i <= 1;
    }

    private boolean isSurroundingBlockChest(World world, BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock() != this) {
            return false;
        }

        for (java.util.Iterator<?> typedIterator422 = (net.minecraft.util.FacingPlane.HORIZONTAL).iterator();
                ((boolean) (((java.util.Iterator) (typedIterator422)).hasNext())); ) {
            net.minecraft.util.Facing facing =
                    (net.minecraft.util.Facing)
                            ((java.lang.Object) (((java.util.Iterator) (typedIterator422)).next()));
            if (world.getBlockState(blockPos.offset(facing)).getBlock() == this) {
                return true;
            }
        }

        return false;
    }

    protected BlockStateContainer createBlockState() {
        return new net.minecraft.block.state.BlockStateContainer(
                this, new net.minecraft.block.properties.IProperty[] {net.minecraft.block.BlockChest.FACING_PROP});
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        super.onNeighborBlockChange(world, blockPos, blockState, block);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityChest) {
            tileEntity.updateContainingBlockInfo();
        }
    }

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        return net.minecraft.inventory.Container.calcRedstoneFromInventory(
                this.getLockableContainer(world, blockPos));
    }

    public BlockState getStateFromMeta(int i) {
        Facing facing = Facing.getFront(i);
        if (facing.getAxis() == FacingAxis.Y) {
            facing = Facing.NORTH;
        }

        return this.getDefaultState().withProperty(net.minecraft.block.BlockChest.FACING_PROP, facing);
    }

    public int isProvidingWeakPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        if (!this.canProvidePower()) {
            return 0;
        }

        int i = 0;
        TileEntity tileEntity = iBlockAccess.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityChest) {
            i = ((TileEntityChest) tileEntity).numPlayersUsing;
        }

        return MathHelper.clamp(i, 0, 15);
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
        if (world.isRemote) {
            return true;
        }

        net.minecraft.world.ILockableContainer lockableContainer = this.getLockableContainer(world, blockPos);
        if (lockableContainer != null) {
            playerEntity.displayGUIChest(lockableContainer);
        }

        return true;
    }

    public int getRenderType() {
        return 2;
    }

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        label82:
        {
            Facing facing =
                    Facing.getHorizontal(
                                    MathHelper.floor(livingEntity.rotationYaw * 4.0F / 360.0F + 0.5)
                                            & 3)
                            .getOpposite();
            int j1 = BlockPlanksEnumType.getLegacyBlockFlowToken();
            blockState = blockState.withProperty(net.minecraft.block.BlockChest.FACING_PROP, facing);
            int i = j1;
            BlockPos blockPosValue = blockPos.offsetNorth();
            BlockPos blockPosValue2 = blockPos.offsetSouth();
            BlockPos blockPosValue3 = blockPos.offsetWest();
            BlockPos blockPosValue4 = blockPos.offsetEast();
            int j = this == world.getBlockState(blockPosValue).getBlock() ? 1 : 0;
            int k = this == world.getBlockState(blockPosValue2).getBlock() ? 1 : 0;
            int l = this == world.getBlockState(blockPosValue3).getBlock() ? 1 : 0;
            int i1 = this == world.getBlockState(blockPosValue4).getBlock() ? 1 : 0;
            if (j == 0 && k == 0 && l == 0 && i1 == 0) {
                world.setBlockState(blockPos, blockState, 3);
                if (i != 0) {
                    break label82;
                }
            }

            if (facing.getAxis() == FacingAxis.X && (j != 0 || k != 0)) {
                {
                    if (j != 0) {
                        world.setBlockState(blockPosValue, blockState, 3);
                        if (!(i != 0)) {

                            world.setBlockState(blockPosValue2, blockState, 3);
                        }
                    } else {

                        world.setBlockState(blockPosValue2, blockState, 3);
                    }
                }

                world.setBlockState(blockPos, blockState, 3);
                if (i != 0) {
                    break label82;
                }
            }

            if (facing.getAxis() == FacingAxis.Z && (l != 0 || i1 != 0)) {
                {
                    if (l != 0) {
                        world.setBlockState(blockPosValue3, blockState, 3);
                        if (!(i != 0)) {

                            world.setBlockState(blockPosValue4, blockState, 3);
                        }
                    } else {

                        world.setBlockState(blockPosValue4, blockState, 3);
                    }
                }

                world.setBlockState(blockPos, blockState, 3);
            }
        }

        if (itemStack.hasDisplayName()) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityChest) {
                ((TileEntityChest) tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }

    public BlockState checkForSurroundingChests(
            World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (world.isRemote) {
            return blockState;
        }

        Facing facing;
        label111:
        {
            BlockState blockStateValue = world.getBlockState(blockPos.offsetNorth());
            BlockState blockStateValue2 = world.getBlockState(blockPos.offsetSouth());
            BlockState blockStateValue3 = world.getBlockState(blockPos.offsetWest());
            BlockState blockStateValue4 = world.getBlockState(blockPos.offsetEast());
            facing = (Facing) blockState.getValue(net.minecraft.block.BlockChest.FACING_PROP);
            Block block = blockStateValue.getBlock();
            Block blockValue = blockStateValue2.getBlock();
            Block blockValue2 = blockStateValue3.getBlock();
            Block blockValue3 = blockStateValue4.getBlock();
            if (block != this && blockValue != this) {
                boolean fullBlockValue = block.isFullBlock();
                boolean fullBlockValue2 = blockValue.isFullBlock();
                if (blockValue2 == this || blockValue3 == this) {
                    BlockState blockStateValue5;
                    BlockState blockStateValue6;
                    Facing facingValue;
                    {
                        BlockPos blockPosValue =
                                blockValue2 == this ? blockPos.offsetWest() : blockPos.offsetEast();
                        blockStateValue5 = world.getBlockState(blockPosValue.offsetNorth());
                        blockStateValue6 = world.getBlockState(blockPosValue.offsetSouth());
                        facing = Facing.SOUTH;
                        if (blockValue2 == this) {
                            facingValue =
                                    (Facing)
                                            blockStateValue3.getValue(net.minecraft.block.BlockChest.FACING_PROP);
                            if (!(i == 0)) {

                                facingValue =
                                        (Facing) blockStateValue4.getValue(net.minecraft.block.BlockChest.FACING_PROP);
                            }
                        } else {

                            facingValue =
                                    (Facing) blockStateValue4.getValue(net.minecraft.block.BlockChest.FACING_PROP);
                        }
                    }

                    if (facingValue == Facing.NORTH) {
                        facing = Facing.NORTH;
                    }

                    Block blockValue4 = blockStateValue5.getBlock();
                    Block blockValue5 = blockStateValue6.getBlock();
                    if ((fullBlockValue || blockValue4.isFullBlock())
                            && !fullBlockValue2
                            && !blockValue5.isFullBlock()) {
                        facing = Facing.SOUTH;
                    }

                    if ((fullBlockValue2 || blockValue5.isFullBlock())
                            && !fullBlockValue
                            && !blockValue4.isFullBlock()) {
                        facing = Facing.NORTH;
                    }
                }

                if (i == 0) {
                    break label111;
                }
            }

            BlockState blockStateValue7;
            BlockState blockStateValue8;
            Facing facingValue2;
            {
                BlockPos blockPosValue2 =
                        block == this ? blockPos.offsetNorth() : blockPos.offsetSouth();
                blockStateValue7 = world.getBlockState(blockPosValue2.offsetWest());
                blockStateValue8 = world.getBlockState(blockPosValue2.offsetEast());
                facing = Facing.EAST;
                if (block == this) {
                    facingValue2 = (Facing) blockStateValue.getValue(net.minecraft.block.BlockChest.FACING_PROP);
                    if (!(i == 0)) {

                        facingValue2 = (Facing) blockStateValue2.getValue(net.minecraft.block.BlockChest.FACING_PROP);
                    }
                } else {

                    facingValue2 = (Facing) blockStateValue2.getValue(net.minecraft.block.BlockChest.FACING_PROP);
                }
            }

            if (facingValue2 == Facing.WEST) {
                facing = Facing.WEST;
            }

            Block blockValue6 = blockStateValue7.getBlock();
            Block blockValue7 = blockStateValue8.getBlock();
            if ((blockValue2.isFullBlock() || blockValue6.isFullBlock())
                    && !blockValue3.isFullBlock()
                    && !blockValue7.isFullBlock()) {
                facing = Facing.EAST;
            }

            if ((blockValue3.isFullBlock() || blockValue7.isFullBlock())
                    && !blockValue2.isFullBlock()
                    && !blockValue6.isFullBlock()) {
                facing = Facing.WEST;
            }
        }

        blockState = blockState.withProperty(net.minecraft.block.BlockChest.FACING_PROP, facing);
        world.setBlockState(blockPos, blockState, 3);
        return blockState;
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int isProvidingStrongPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return facing == net.minecraft.util.Facing.UP
                ? this.isProvidingWeakPower(iBlockAccess, blockPos, blockState, facing)
                : 0;
    }

    private boolean cannotOpenChest(World world, BlockPos blockPos) {
        return this.isBelowSolidBlock(world, blockPos)
                || this.isOcelotSittingOnChest(world, blockPos);
    }

    private boolean isOcelotSittingOnChest(World world, BlockPos blockPos) {
        for (java.util.Iterator<?> __recoveryIterator171_22759 =
                        (world.getEntitiesWithinAABB(
                                        EntityOcelot.class,
                                        new BoundingBox(
                                                blockPos.getX(),
                                                blockPos.getY() + 1,
                                                blockPos.getZ(),
                                                blockPos.getX() + 1,
                                                blockPos.getY() + 2,
                                                blockPos.getZ() + 1)))
                                .iterator();
                (__recoveryIterator171_22759).hasNext(); ) {
            Entity entity = (Entity) (__recoveryIterator171_22759).next();
            EntityOcelot entityOcelot = (EntityOcelot) entity;
            if (entityOcelot.isSitting()) {
                return true;
            }
        }

        return false;
    }

    public ILockableContainer getLockableContainer(World world, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        TileEntity tileEntity = world.getTileEntity(blockPos);
        int i = j;
        if (!(tileEntity instanceof TileEntityChest)) {
            return null;
        }

        Object object = (TileEntityChest) tileEntity;
        if (this.cannotOpenChest(world, blockPos)) {
            return null;
        }

        for (java.util.Iterator<?> __recoveryIterator171_24473 =
                        (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_24473).hasNext(); ) {
            Facing facing = (Facing) (__recoveryIterator171_24473).next();
            BlockPos blockPosValue = blockPos.offset(facing);
            Block block = world.getBlockState(blockPosValue).getBlock();
            if (block == this) {
                if (this.cannotOpenChest(world, blockPosValue)) {
                    return null;
                }

                TileEntity tileEntityValue = world.getTileEntity(blockPosValue);
                if (tileEntityValue instanceof TileEntityChest) {
                    if (facing != Facing.WEST && facing != Facing.NORTH) {
                        object =
                                new InventoryLargeChest(
                                        "container.chestDouble",
                                        (ILockableContainer) object,
                                        (TileEntityChest) tileEntityValue);
                        if (!(i != 0)) {

                            object =
                                    new InventoryLargeChest(
                                            "container.chestDouble",
                                            (TileEntityChest) tileEntityValue,
                                            (ILockableContainer) object);
                        }
                    } else {

                        object =
                                new InventoryLargeChest(
                                        "container.chestDouble",
                                        (TileEntityChest) tileEntityValue,
                                        (ILockableContainer) object);
                    }
                }
            }

            if (i == 0) {
                break;
            }
        }

        return (ILockableContainer) object;
    }

    public int getMetaFromState(BlockState blockState) {
        return ((net.minecraft.util.Facing) blockState.getValue(net.minecraft.block.BlockChest.FACING_PROP)).getIndex();
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (iBlockAccess.getBlockState(blockPos.offsetNorth()).getBlock() == this) {
            this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
            if (i == 0) {
                return;
            }
        }

        if (iBlockAccess.getBlockState(blockPos.offsetSouth()).getBlock() == this) {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
            if (i == 0) {
                return;
            }
        }

        if (iBlockAccess.getBlockState(blockPos.offsetWest()).getBlock() == this) {
            this.setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
            if (i == 0) {
                return;
            }
        }

        if (iBlockAccess.getBlockState(blockPos.offsetEast()).getBlock() == this) {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
            if (i == 0) {
                return;
            }
        }

        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityChest();
    }

    protected BlockChest(int chestTypeValue) {
        super(Material.wood);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockChest.FACING_PROP, Facing.NORTH));
        this.chestType = chestTypeValue;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof IInventory) {
            InventoryHelper.dropInventoryItems(world, blockPos, (IInventory) tileEntity);
            world.updateComparatorOutputLevel(blockPos, this);
        }

        super.breakBlock(world, blockPos, blockState);
    }

    private boolean isBelowSolidBlock(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube();
    }

    static {
        net.minecraft.block.BlockChest.FACING_PROP = PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        this.checkForSurroundingChests(world, blockPos, blockState);
        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_53553 =
                        (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_53553).hasNext(); ) {
            Facing facing = (Facing) (__recoveryIterator171_53553).next();
            BlockPos blockPosValue = blockPos.offset(facing);
            BlockState blockStateValue = world.getBlockState(blockPosValue);
            if (blockStateValue.getBlock() == this) {
                this.checkForSurroundingChests(world, blockPosValue, blockStateValue);
            }

            if (i != 0) {
                break;
            }
        }
    }
}
