package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;

public final class CompiledChunkDummyCompiledChunk extends CompiledChunk {
    public boolean isVisible(Facing facing, Facing facingValue) {
        return false;
    }

    protected void setLayerUsed(EnumWorldBlockLayer enumWorldBlockLayer) {
        throw new UnsupportedOperationException();
    }

    public void setLayerStarted(EnumWorldBlockLayer enumWorldBlockLayer) {
        throw new UnsupportedOperationException();
    }
}
