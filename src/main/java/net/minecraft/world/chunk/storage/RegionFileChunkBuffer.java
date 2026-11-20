package net.minecraft.world.chunk.storage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

class RegionFileChunkBuffer extends ByteArrayOutputStream {
    private int chunkX;
    private int chunkZ;
    RegionFile bLS;

    @Override
    public void close() throws IOException {
        this.bLS.write(this.chunkX, this.chunkZ, this.buf, this.count);
    }

    public RegionFileChunkBuffer(RegionFile bLSValue, int i, int j) {
        super(8096);
        this.bLS = bLSValue;
        this.chunkX = i;
        this.chunkZ = j;
    }
}
