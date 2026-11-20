package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import java.util.Random;

public class BlockEnderChest extends BlockContainer {
    public static PropertyDirection FACING;

    // region String decoding support

    // endregion

    public BlockState getStateFromMeta(int i) {
        Facing facing = Facing.getFront(i);
        if (facing.getAxis() == FacingAxis.Y) {
            facing = Facing.NORTH;
        }

        return this.getDefaultState().withProperty(net.minecraft.block.BlockEnderChest.FACING, facing);
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
                        net.minecraft.block.BlockEnderChest.FACING,
                        livingEntity.getHorizontalFacing().getOpposite());
    }

    // region String decoding support

    // endregion

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i1 = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int j = 0;
        int i = i1;

        while (j < 3) {
            int k = random.nextInt(2) * 2 - 1;
            int l = random.nextInt(2) * 2 - 1;
            double d0 = blockPos.getX() + 0.5 + 0.25 * k;
            double d1 = blockPos.getY() + random.nextFloat();
            double d2 = blockPos.getZ() + 0.5 + 0.25 * l;
            double d3 = random.nextFloat() * k;
            double d4 = (random.nextFloat() - 0.5) * 0.125;
            double d5 = random.nextFloat() * l;
            world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public int getMetaFromState(BlockState blockState) {
        return ((Facing) blockState.getValue(net.minecraft.block.BlockEnderChest.FACING)).getIndex();
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
        InventoryEnderChest inventoryEnderChest = playerEntity.getInventoryEnderChest();
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (inventoryEnderChest == null || !(tileEntity instanceof TileEntityEnderChest)) {
            return true;
        }

        if (world.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube()) {
            return true;
        }

        if (world.isRemote) {
            return true;
        }

        inventoryEnderChest.setChestTileEntity((TileEntityEnderChest) tileEntity);
        playerEntity.displayGUIChest(inventoryEnderChest);
        return true;
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityEnderChest();
    }

    public int quantityDropped(Random random) {
        return 8;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockEnderChest.FACING});
    }

    protected BlockEnderChest() {
        super(Material.rock);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockEnderChest.FACING, Facing.NORTH));
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Item.getItemFromBlock(Blocks.obsidian);
    }

    public boolean isFullCube() {
        return false;
    }

    static {
        net.minecraft.block.BlockEnderChest.FACING = PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
    }

    protected boolean canSilkHarvest() {
        return true;
    }

    public boolean isOpaqueCube() {
        return false;
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
        world.setBlockState(
                blockPos,
                blockState.withProperty(
                        net.minecraft.block.BlockEnderChest.FACING,
                        livingEntity.getHorizontalFacing().getOpposite()),
                2);
    }
}
