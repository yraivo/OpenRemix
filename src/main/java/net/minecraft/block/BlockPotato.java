package net.minecraft.block;

import net.minecraft.block.state.BlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockPotato extends BlockCrops {
    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, f, i);
        if (!world.isRemote
                && (Integer) blockState.getValue(net.minecraft.block.BlockCrops.AGE) >= 7
                && world.rand.nextInt(50) == 0) {
            spawnAsEntity(world, blockPos, new ItemStack(Items.poisonous_potato));
        }
    }

    protected Item getCrop() {
        return Items.potato;
    }

    protected Item getSeed() {
        return Items.potato;
    }
}
