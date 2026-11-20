package net.minecraft.util;

import net.minecraft.util.BlockBoxIterable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;

import com.google.common.collect.AbstractIterator;

public class BlockPosIteratorAbstractIterator extends AbstractIterator {
    BlockBoxIterable agV;
    private BlockPos lastReturned;

    public BlockPosIteratorAbstractIterator(BlockBoxIterable agVValue) {
        this.agV = agVValue;
        this.lastReturned = null;
    }

    protected BlockPos computeNext0() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (this.lastReturned == null) {
            this.lastReturned = this.agV.vh;
            return this.lastReturned;
        }

        if (this.lastReturned.equals(this.agV.vm)) {
            return (BlockPos) this.endOfData();
        }

        int i;
        int j;
        int k;
        label30:
        {
            i = this.lastReturned.getX();
            j = this.lastReturned.getY();
            k = this.lastReturned.getZ();
            if (i < this.agV.vm.getX()) {
                i++;
                if (text != null) {
                    break label30;
                }
            }

            if (j < this.agV.vm.getY()) {
                i = this.agV.vh.getX();
                j++;
                if (text != null) {
                    break label30;
                }
            }

            if (k < this.agV.vm.getZ()) {
                i = this.agV.vh.getX();
                j = this.agV.vh.getY();
                k++;
            }
        }

        this.lastReturned = new BlockPos(i, j, k);
        return this.lastReturned;
    }

    protected Object computeNext() {
        return this.computeNext0();
    }
}
