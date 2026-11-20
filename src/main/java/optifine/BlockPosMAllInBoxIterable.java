package optifine;

import net.minecraft.util.BlockPos;

import java.util.Iterator;

final class BlockPosMAllInBoxIterable implements Iterable {
    BlockPos from;
    BlockPos z;

    @Override
    public Iterator iterator() {
        return new BlockPosMAllInBoxIterabler4d(this);
    }

    BlockPosMAllInBoxIterable(BlockPos blockPos, BlockPos blockPosValue) {
        this.from = blockPos;
        this.z = blockPosValue;
    }
}
