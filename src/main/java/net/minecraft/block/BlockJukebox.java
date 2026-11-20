package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BlockJukebox extends BlockContainer {
    public static PropertyBool HAS_RECORD;

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        this.dropRecord(world, blockPos, blockState);
        super.breakBlock(world, blockPos, blockState);
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
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockJukebox.HAS_RECORD)) {
            this.dropRecord(world, blockPos, blockState);
            blockState = blockState.withProperty(net.minecraft.block.BlockJukebox.HAS_RECORD, false);
            world.setBlockState(blockPos, blockState, 2);
            return true;
        } else {
            return false;
        }
    }

    static {
        net.minecraft.block.BlockJukebox.HAS_RECORD = PropertyBool.create("has_record");
    }

    public int getRenderType() {
        return 3;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockJukebox.HAS_RECORD, i > 0);
    }

    protected BlockJukebox() {
        super(Material.wood);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockJukebox.HAS_RECORD, false));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof BlockJukeboxTileEntityJukebox) {
            ItemStack itemStack = ((BlockJukeboxTileEntityJukebox) tileEntity).getRecord();
            if (itemStack != null) {
                return Item.getIdFromItem(itemStack.getItem())
                        + 1
                        - Item.getIdFromItem(Items.record_13);
            }
        }

        return 0;
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        if (!world.isRemote) {
            super.dropBlockAsItemWithChance(world, blockPos, blockState, f, 0);
        }
    }

    // region String decoding support

    // endregion

    private void dropRecord(World world, BlockPos blockPos, BlockState blockState) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof BlockJukeboxTileEntityJukebox) {
                BlockJukeboxTileEntityJukebox blockJukeboxTileEntityJukebox =
                        (BlockJukeboxTileEntityJukebox) tileEntity;
                ItemStack itemStack = blockJukeboxTileEntityJukebox.getRecord();
                if (itemStack != null) {
                    world.playAuxSFX(1005, blockPos, 0);
                    world.playRecord(blockPos, (String) null);
                    blockJukeboxTileEntityJukebox.setRecord((ItemStack) null);
                    float f = 0.7F;
                    double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5;
                    double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.2 + 0.6;
                    double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5;
                    ItemStack itemStackValue = itemStack.copy();
                    EntityItem entityItem =
                            new EntityItem(
                                    world,
                                    blockPos.getX() + d0,
                                    blockPos.getY() + d1,
                                    blockPos.getZ() + d2,
                                    itemStackValue);
                    entityItem.setDefaultPickupDelay();
                    world.spawnEntityInWorld(entityItem);
                }
            }
        }
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, net.minecraft.block.BlockJukebox.HAS_RECORD);
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new BlockJukeboxTileEntityJukebox();
    }

    // region String decoding support

    // endregion

    public void insertRecord(
            World world, BlockPos blockPos, BlockState blockState, ItemStack itemStack) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof BlockJukeboxTileEntityJukebox) {
                ((BlockJukeboxTileEntityJukebox) tileEntity)
                        .setRecord(
                                new ItemStack(itemStack.getItem(), 1, itemStack.getItemDamage()));
                world.setBlockState(
                        blockPos, blockState.withProperty(net.minecraft.block.BlockJukebox.HAS_RECORD, true), 2);
            }
        }
    }

    public int getMetaFromState(BlockState blockState) {
        return ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockJukebox.HAS_RECORD)) ? 1 : 0;
    }
}
