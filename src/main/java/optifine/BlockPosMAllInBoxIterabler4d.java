package optifine;

import com.google.common.collect.AbstractIterator;

class BlockPosMAllInBoxIterabler4d extends AbstractIterator {
    BlockPosMAllInBoxIterable this$0;
    private BlockPosM theBlockPos;

    protected BlockPosM computeNextPos() {
        int i = ReflectorMethod.f();
        if (this.theBlockPos == null) {
            this.theBlockPos =
                    new BlockPosM(this.this$0.from.getX(), this.this$0.from.getY(), this.this$0.from.getZ(), 3);
            return this.theBlockPos;
        }

        if (this.theBlockPos.equals(this.this$0.z)) {
            return (BlockPosM) this.endOfData();
        }

        int j;
        int k;
        int l;
        label30:
        {
            j = this.theBlockPos.getX();
            k = this.theBlockPos.getY();
            l = this.theBlockPos.getZ();
            if (j < this.this$0.z.getX()) {
                j++;
                if (i == 0) {
                    break label30;
                }
            }

            if (k < this.this$0.z.getY()) {
                j = this.this$0.from.getX();
                k++;
                if (i == 0) {
                    break label30;
                }
            }

            if (l < this.this$0.z.getZ()) {
                j = this.this$0.from.getX();
                k = this.this$0.from.getY();
                l++;
            }
        }

        this.theBlockPos.setXyz(j, k, l);
        return this.theBlockPos;
    }

    BlockPosMAllInBoxIterabler4d(BlockPosMAllInBoxIterable blockPosMAllInBoxIterable) {
        this.this$0 = blockPosMAllInBoxIterable;
        this.theBlockPos = null;
    }

    protected Object computeNext() {
        return this.computeNextPos();
    }
}
