package net.minecraft.util;

import net.minecraft.util.BlockPosIteratorAbstractIterator;

import java.util.Iterator;

public final class BlockBoxIterable implements Iterable {
    public BlockPos vh;
    public BlockPos vm;

    @Override
    public Iterator iterator() {
        return new BlockPosIteratorAbstractIterator(this);
    }

    BlockBoxIterable(BlockPos blockPos, BlockPos blockPosValue) {
        this.vh = blockPos;
        this.vm = blockPosValue;
    }
}
