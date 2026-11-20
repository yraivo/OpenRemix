package net.minecraft.world;

import net.minecraft.util.BlockPos;

public class ChunkCoordIntPair {
    public int chunkXPos;
    private static String d;
    private int cachedHashCode = 0;
    private static String[] packedTextFragments;
    public int chunkZPos;

    public int getXStart() {
        return this.chunkXPos << 4;
    }

    // region String decoding support

    // endregion

    public int getXEnd() {
        return (this.chunkXPos << 4) + 15;
    }

    public int getZEnd() {
        return (this.chunkZPos << 4) + 15;
    }

    static {
        net.minecraft.world.ChunkCoordIntPair.packedTextFragments = new String[3];
        net.minecraft.world.ChunkCoordIntPair.packedTextFragments[0] = new String("[");
        net.minecraft.world.ChunkCoordIntPair.packedTextFragments[1] = new String("]");
        net.minecraft.world.ChunkCoordIntPair.packedTextFragments[2] = new String("\u0002@");
        d = ", ";
    }

    public static long chunkXZ2Int(int i, int j) {
        long k = i;

        k &= 4294967295L;
        long l = j;

        return k | (l & 4294967295L) << 32;
    }

    public BlockPos getBlock(int i, int j, int k) {
        return new BlockPos((this.chunkXPos << 4) + i, j, (this.chunkZPos << 4) + k);
    }

    public ChunkCoordIntPair(int chunkXPosValue, int j) {
        this.chunkXPos = chunkXPosValue;
        this.chunkZPos = j;
    }

    // region String decoding support

    // endregion

    public BlockPos getCenterBlock(int i) {
        return new BlockPos(this.getCenterXPos(), i, this.getCenterZPosition());
    }

    public int getCenterXPos() {
        return (this.chunkXPos << 4) + 8;
    }

    @Override
    public String toString() {
        return packedTextFragments[0]
                + this.chunkXPos
                + d
                + this.chunkZPos
                + packedTextFragments[1];
    }

    @Override
    public int hashCode() {
        if (this.cachedHashCode == 0) {
            int i = 1664525 * this.chunkXPos + 1013904223;
            int j = 1664525 * (this.chunkZPos ^ -559038737) + 1013904223;
            this.cachedHashCode = i ^ j;
        }

        return this.cachedHashCode;
    }

    // region String decoding support

    // endregion

    public int getCenterZPosition() {
        return (this.chunkZPos << 4) + 8;
    }

    public int getZStart() {
        return this.chunkZPos << 4;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ChunkCoordIntPair)) {
            return false;
        }

        ChunkCoordIntPair chunkCoordIntPair = (ChunkCoordIntPair) object;
        return this.chunkXPos == chunkCoordIntPair.chunkXPos
                && this.chunkZPos == chunkCoordIntPair.chunkZPos;
    }
}
