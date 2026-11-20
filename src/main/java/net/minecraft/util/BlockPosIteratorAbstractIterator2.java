package net.minecraft.util;

import net.minecraft.util.BlockPosMutableBlockPos;
import net.minecraft.util.MutableBlockBoxIterable;
import net.minecraft.util.Vec3i;

import com.google.common.collect.AbstractIterator;

public class BlockPosIteratorAbstractIterator2 extends AbstractIterator {
    MutableBlockBoxIterable ajB;
    private BlockPosMutableBlockPos theBlockPos;

    protected BlockPosMutableBlockPos computeNext0() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (this.theBlockPos == null) {
            this.theBlockPos =
                    new BlockPosMutableBlockPos(
                            this.ajB.vh.getX(), this.ajB.vh.getY(), this.ajB.vh.getZ(), null);
            return this.theBlockPos;
        }

        if (this.theBlockPos.equals(this.ajB.vm)) {
            return (BlockPosMutableBlockPos) this.endOfData();
        }

        int i;
        int j;
        int k;
        label30:
        {
            i = this.theBlockPos.getX();
            j = this.theBlockPos.getY();
            k = this.theBlockPos.getZ();
            if (i < this.ajB.vm.getX()) {
                i++;
                if (text != null) {
                    break label30;
                }
            }

            if (j < this.ajB.vm.getY()) {
                i = this.ajB.vh.getX();
                j++;
                if (text != null) {
                    break label30;
                }
            }

            if (k < this.ajB.vm.getZ()) {
                i = this.ajB.vh.getX();
                j = this.ajB.vh.getY();
                k++;
            }
        }

        this.theBlockPos.x = i;
        this.theBlockPos.y = j;
        this.theBlockPos.z = k;
        return this.theBlockPos;
    }

    public BlockPosIteratorAbstractIterator2(MutableBlockBoxIterable ajBValue) {
        this.ajB = ajBValue;
        this.theBlockPos = null;
    }

    protected Object computeNext() {
        return this.computeNext0();
    }
}
