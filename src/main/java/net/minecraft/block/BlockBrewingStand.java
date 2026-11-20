package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockBrewingStand extends BlockContainer {
    private final Random rand = new Random();
    public static PropertyBool[] BOTTLE_PROPS;

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {
        this.setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        this.setBlockBoundsForItemRender();
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
    }

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        return Container.calcRedstoneFromInventory(world.getTileEntity(blockPos));
    }

    public BlockBrewingStand() {
        super(Material.iron);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockBrewingStand.BOTTLE_PROPS[0], false)
                        .withProperty(net.minecraft.block.BlockBrewingStand.BOTTLE_PROPS[1], false)
                        .withProperty(net.minecraft.block.BlockBrewingStand.BOTTLE_PROPS[2], false));
    }

    // region String decoding support

    // endregion

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBrewingStand) {
            InventoryHelper.dropInventoryItems(
                    world, blockPos, (TileEntityBrewingStand) tileEntity);
        }

        super.breakBlock(world, blockPos, blockState);
    }

    public int getRenderType() {
        return 3;
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityBrewingStand();
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        double d0 = blockPos.getX() + 0.4F + random.nextFloat() * 0.2F;
        double d1 = blockPos.getY() + 0.7F + random.nextFloat() * 0.3F;
        double d2 = blockPos.getZ() + 0.4F + random.nextFloat() * 0.2F;
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
    }

    static {
        net.minecraft.block.BlockBrewingStand.BOTTLE_PROPS =
                new PropertyBool[] {
                    PropertyBool.create("has_bottle_0"),
                    PropertyBool.create("has_bottle_1"),
                    PropertyBool.create("has_bottle_2")
                };
    }

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        if (itemStack.hasDisplayName()) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityBrewingStand) {
                ((TileEntityBrewingStand) tileEntity).setName(itemStack.getDisplayName());
            }
        }
    }

    public BlockState getStateFromMeta(int i) {
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockState blockState = this.getDefaultState();
        int j = l;
        int k = 0;

        while (k < 3) {
            blockState =
                    blockState.withProperty(
                            net.minecraft.block.BlockBrewingStand.BOTTLE_PROPS[k], (i & 1 << k) > 0);
            k++;
            if (j != 0) {
                break;
            }
        }

        return blockState;
    }

    public int getMetaFromState(BlockState blockState) {
        int j = 0;
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int k = 0;
        int i = l;

        while (k < 3) {
            if ((Boolean) blockState.getValue(net.minecraft.block.BlockBrewingStand.BOTTLE_PROPS[k])) {
                j |= 1 << k;
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        return j;
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Items.brewing_stand;
    }

    // region String decoding support

    // endregion

    public boolean isOpaqueCube() {
        return false;
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
        if (tileEntity instanceof TileEntityBrewingStand) {
            playerEntity.displayGUIChest((TileEntityBrewingStand) tileEntity);
        }

        return true;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockBrewingStand.BOTTLE_PROPS[0],
                    net.minecraft.block.BlockBrewingStand.BOTTLE_PROPS[1],
                    net.minecraft.block.BlockBrewingStand.BOTTLE_PROPS[2]
                });
    }

    public boolean isFullCube() {
        return false;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.brewing_stand;
    }
}
