package net.minecraft.world.chunk;

public class NibbleArray {
    private static String b;
    private byte[] data;

    // region String decoding support

    // endregion

    private int getNibbleIndex(int i) {
        return i >> 1;
    }

    public NibbleArray(byte[] abyte) {
        this.data = abyte;
        if (abyte.length != 2048) {
            throw new IllegalArgumentException(b + abyte.length);
        }
    }

    public void set(int i, int j, int k, int l) {
        this.setIndex(this.getCoordinateIndex(i, j, k), l);
    }

    // region String decoding support

    // endregion

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    static {
        b = "ChunkNibbleArrays should be 2048 bytes not: ";
    }

    public void setIndex(int i, int j) {
        int[] aint1 = Chunk.Wj();
        int k = this.getNibbleIndex(i);
        int[] aint = aint1;
        if (this.isLowerNibble(i)) {
            this.data[k] = (byte) (this.data[k] & 240 | j & 15);
            if (aint == null) {
                return;
            }
        }

        this.data[k] = (byte) (this.data[k] & 15 | (j & 15) << 4);
    }

    private int getCoordinateIndex(int i, int j, int k) {
        return j << 8 | k << 4 | i;
    }

    private boolean isLowerNibble(int i) {
        return (i & 1) == 0;
    }

    public NibbleArray() {
        this.data = new byte[2048];
    }

    public int getFromIndex(int i) {
        int j = this.getNibbleIndex(i);
        return this.isLowerNibble(i) ? this.data[j] & 15 : this.data[j] >> 4 & 15;
    }

    public byte[] getData() {
        return this.data;
    }

    public int get(int i, int j, int k) {
        return this.getFromIndex(this.getCoordinateIndex(i, j, k));
    }
}
