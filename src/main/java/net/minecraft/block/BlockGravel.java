package net.minecraft.block;

import net.minecraft.block.state.BlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockGravel extends BlockFalling {
    public Item getItemDropped(BlockState blockState, Random random, int i) {
        if (i > 3) {
            i = 3;
        }

        return random.nextInt(10 - i * 3) == 0 ? Items.flint : Item.getItemFromBlock(this);
    }
}
