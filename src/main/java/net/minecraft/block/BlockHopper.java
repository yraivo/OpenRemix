package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockHopper extends BlockContainer {
    public static PropertyBool ENABLED;
    public static PropertyDirection FACING;

    static {
        net.minecraft.block.BlockHopper.FACING =
                PropertyDirection.create("facing", new FacingPredicate());
        net.minecraft.block.BlockHopper.ENABLED = PropertyBool.create("enabled");
    }

    public BlockHopper() {
        super(Material.iron);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockHopper.FACING, Facing.DOWN)
                        .withProperty(net.minecraft.block.BlockHopper.ENABLED, true));
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isFullCube() {
        return false;
    }

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, livingEntity, itemStack);
        if (itemStack.hasDisplayName()) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityHopper) {
                ((TileEntityHopper) tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    // region String decoding support

    // endregion

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockHopper.FACING, getFacing(i))
                .withProperty(
                        net.minecraft.block.BlockHopper.ENABLED, getActiveStateFromMetadata(i));
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public int getRenderType() {
        return 3;
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
        if (tileEntity instanceof TileEntityHopper) {
            playerEntity.displayGUIChest((TileEntityHopper) tileEntity);
        }

        return true;
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return true;
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityHopper();
    }

    public static boolean getActiveStateFromMetadata(int i) {
        return (i & 8) != 8;
    }

    private void updateState(World world, BlockPos blockPos, BlockState blockState) {
        int i = !world.isBlockPowered(blockPos) ? 1 : 0;
        if (i
                != remix.compat.compiler.JvmBooleanSlots.toInt(
                        (Boolean) blockState.getValue(net.minecraft.block.BlockHopper.ENABLED))) {
            world.setBlockState(
                    blockPos,
                    blockState.withProperty(
                            net.minecraft.block.BlockHopper.ENABLED,
                            Boolean.valueOf(remix.compat.compiler.JvmBooleanSlots.fromInt(i))),
                    4);
        }
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockHopper.FACING, net.minecraft.block.BlockHopper.ENABLED
                });
    }

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        return Container.calcRedstoneFromInventory(world.getTileEntity(blockPos));
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        this.updateState(world, blockPos, blockState);
    }

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
        Facing facingValue = facing.getOpposite();
        if (facingValue == Facing.UP) {
            facingValue = Facing.DOWN;
        }

        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockHopper.FACING, facingValue)
                .withProperty(net.minecraft.block.BlockHopper.ENABLED, true);
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((Facing) blockState.getValue(net.minecraft.block.BlockHopper.FACING))
                                .getIndex();
        if (!(Boolean) blockState.getValue(net.minecraft.block.BlockHopper.ENABLED)) {
            i |= 8;
        }

        return i;
    }

    public static Facing getFacing(int i) {
        return Facing.getFront(i & 7);
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

    // region String decoding support

    // endregion

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityHopper) {
            InventoryHelper.dropInventoryItems(world, blockPos, (TileEntityHopper) tileEntity);
            world.updateComparatorOutputLevel(blockPos, this);
        }

        super.breakBlock(world, blockPos, blockState);
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        this.updateState(world, blockPos, blockState);
    }

    // Helper used only by this class.
    public static final class FacingPredicate implements com.google.common.base.Predicate {
        public boolean method_cdb21af4cadb5036c34c(net.minecraft.util.Facing facing) {
            return facing != net.minecraft.util.Facing.UP;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_cdb21af4cadb5036c34c((net.minecraft.util.Facing) object);
        }
    }
}
