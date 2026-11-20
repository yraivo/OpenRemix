package optifine;

import net.minecraft.client.renderer.ChunkVisibilityEntry;

import net.minecraft.client.renderer.chunk.RenderChunk;

public class ShadowChunkEntryCache {
    private RenderChunk renderChunk;
    private ChunkVisibilityEntry cachedEntry;

    public void resetForChunk(RenderChunk renderChunk) {
        this.renderChunk = renderChunk;
        this.cachedEntry = null;
    }

    public ChunkVisibilityEntry getRenderInfo() {
        if (this.cachedEntry == null) {
            this.cachedEntry = new net.minecraft.client.renderer.ChunkVisibilityEntry(this.renderChunk, (net.minecraft.util.Facing) null, 0);
        }

        return this.cachedEntry;
    }

    public RenderChunk getRenderChunk() {
        return this.renderChunk;
    }
}
