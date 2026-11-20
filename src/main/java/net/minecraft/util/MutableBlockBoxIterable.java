package net.minecraft.util;

import net.minecraft.util.BlockPosIteratorAbstractIterator2;

import java.util.Iterator;

public final class MutableBlockBoxIterable implements Iterable {
    public BlockPos vh;
    public BlockPos vm;

    @Override
    public Iterator iterator() {
        return new BlockPosIteratorAbstractIterator2(this);
    }

    MutableBlockBoxIterable(BlockPos blockPos, BlockPos blockPosValue) {
        this.vh = blockPos;
        this.vm = blockPosValue;
    }
}
