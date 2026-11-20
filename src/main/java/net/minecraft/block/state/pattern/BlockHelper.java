package net.minecraft.block.state.pattern;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;

import com.google.common.base.Predicate;

public class BlockHelper implements Predicate {
    private Block block;

    public boolean apply(Object object) {
        return this.apply((BlockState) object);
    }

    public boolean apply(BlockState blockState) {
        return blockState != null && blockState.getBlock() == this.block;
    }

    private BlockHelper(Block aXzValue) {
        this.block = aXzValue;
    }

    public static BlockHelper forBlock(Block block) {
        return new BlockHelper(block);
    }
}
