package net.minecraft.init;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public final class BootstrapBlockDispenserDispenserBehavior8 extends BehaviorDefaultDispenseItem {
    private final BehaviorDefaultDispenseItem baM = new BehaviorDefaultDispenseItem();

    public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        int i;
        World world;
        BlockPos blockPos;
        Item item;
        {
            int j = Bootstrap.f();
            world = iBlockSource.getWorld();
            blockPos =
                    iBlockSource
                            .getBlockPos()
                            .offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
            BlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            Material material = block.getMaterial();
            i = j;
            if (Material.water.equals(material)
                    && block instanceof BlockLiquid
                    && (Integer) blockState.getValue(BlockLiquid.LEVEL) == 0) {
                item = Items.water_bucket;
                if (!(i == 0)) {

                    if (!Material.lava.equals(material)
                            || !(block instanceof BlockLiquid)
                            || (Integer) blockState.getValue(BlockLiquid.LEVEL) != 0) {
                        return super.dispenseStack(iBlockSource, itemStack);
                    }

                    item = Items.lava_bucket;
                }
            } else {

                if (!Material.lava.equals(material)
                        || !(block instanceof BlockLiquid)
                        || (Integer) blockState.getValue(BlockLiquid.LEVEL) != 0) {
                    return super.dispenseStack(iBlockSource, itemStack);
                }

                item = Items.lava_bucket;
            }
        }

        world.setBlockToAir(blockPos);
        if (--itemStack.stackSize == 0) {
            itemStack.setItem(item);
            itemStack.stackSize = 1;
            if (i == 0) {
                return itemStack;
            }
        }

        if (((TileEntityDispenser) iBlockSource.getBlockTileEntity())
                        .addItemStack(new ItemStack(item))
                < 0) {
            this.baM.dispense(iBlockSource, new ItemStack(item));
        }

        return itemStack;
    }
}
