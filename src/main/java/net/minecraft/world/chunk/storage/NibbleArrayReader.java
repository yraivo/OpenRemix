package net.minecraft.world.chunk.storage;

public class NibbleArrayReader {
    private int depthBitsPlusFour;
    public byte[] data;
    private int depthBits;

    public int get(int i, int j, int k) {
        int l = i << this.depthBitsPlusFour | k << this.depthBits | j;
        int i1 = l >> 1;
        int j1 = l & 1;
        return j1 == 0 ? this.data[i1] & 15 : this.data[i1] >> 4 & 15;
    }

    public NibbleArrayReader(byte[] abyte, int i) {
        this.data = abyte;
        this.depthBits = i;
        this.depthBitsPlusFour = i + 4;
    }
}
