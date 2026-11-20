package optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;

public class BlockPosM extends BlockPos {
    private int level;
    private BlockPosM[] facings;
    private int nO;
    private int kZ;
    private boolean needsUpdate;
    private int rz;

    public BlockPosM(int p171_0, int p171_1, int p171_2) {
        this(p171_0, p171_1, p171_2, 0);
    }

    public BlockPosM(double d0, double d1, double d2) {
        this(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2));
    }

    public BlockPosM(int p171_0, int p171_1, int p171_2, int levelValue) {
        super(0, 0, 0);
        this.rz = p171_0;
        this.kZ = p171_1;
        this.nO = p171_2;
        this.level = levelValue;
    }

    private void update() {
        int j1 = optifine.ReflectorMethod.cR();
        int j = 0;
        int i = j1;

        while (j < 6) {
            optifine.BlockPosM blockPosM = this.facings[j];
            if (blockPosM != null) {
                net.minecraft.util.Facing facing = net.minecraft.util.Facing.VALUES[j];
                int k = this.rz + facing.getFrontOffsetX();
                int l = this.kZ + facing.getFrontOffsetY();
                int i1 = this.nO + facing.getFrontOffsetZ();
                blockPosM.setXyz(k, l, i1);
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        this.needsUpdate = false;
    }

    public int getY() {
        return this.kZ;
    }

    public void setXyz(double d0, double d1, double d2) {
        this.setXyz(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2));
    }

    public int getX() {
        return this.rz;
    }

    public BlockPos offset(Facing facing, int i) {
        return i == 1 ? this.offset(facing) : super.offset(facing, i);
    }

    public BlockPos offset(Facing facing) {
        if (this.level <= 0) {
            return super.offset(facing, 1);
        }

        if (this.facings == null) {
            this.facings = new optifine.BlockPosM[net.minecraft.util.Facing.VALUES.length];
        }

        if (this.needsUpdate) {
            this.update();
        }

        int index = facing.getIndex();
        optifine.BlockPosM blockPosM = this.facings[index];
        if (blockPosM == null) {
            int j = this.rz + facing.getFrontOffsetX();
            int k = this.kZ + facing.getFrontOffsetY();
            int l = this.nO + facing.getFrontOffsetZ();
            blockPosM = new optifine.BlockPosM(j, k, l, this.level - 1);
            this.facings[index] = blockPosM;
        }

        return blockPosM;
    }

    public void setXyz(int p171_0, int p171_1, int p171_2) {
        this.rz = p171_0;
        this.kZ = p171_1;
        this.nO = p171_2;
        this.needsUpdate = true;
    }

    public static Iterable getAllInBoxPosM(BlockPos blockPos, BlockPos blockPosValue) {
        BlockPos blockPosValue2 =
                new BlockPos(
                        Math.min(blockPos.getX(), blockPosValue.getX()),
                        Math.min(blockPos.getY(), blockPosValue.getY()),
                        Math.min(blockPos.getZ(), blockPosValue.getZ()));
        BlockPos blockPosValue3 =
                new BlockPos(
                        Math.max(blockPos.getX(), blockPosValue.getX()),
                        Math.max(blockPos.getY(), blockPosValue.getY()),
                        Math.max(blockPos.getZ(), blockPosValue.getZ()));
        return new BlockPosMAllInBoxIterable(blockPosValue2, blockPosValue3);
    }

    public int getZ() {
        return this.nO;
    }

    public BlockPos getImmutable() {
        return new net.minecraft.util.BlockPos(this.getX(), this.getY(), this.getZ());
    }
}
