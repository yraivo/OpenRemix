package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class ChunkRenderContainer {
    protected boolean initialized;
    private double viewEntityY;
    protected List renderChunks = Lists.newArrayListWithCapacity(17424);
    private double viewEntityZ;
    private double viewEntityX;

    public abstract void renderChunkLayer(EnumWorldBlockLayer enumWorldBlockLayer);

    public void addRenderChunk(
            RenderChunk renderChunk, EnumWorldBlockLayer enumWorldBlockLayer) {
        this.renderChunks.add(renderChunk);
    }

    public void preRenderChunk(RenderChunk renderChunk) {
        BlockPos blockPos = renderChunk.getPosition();
        GlStateManager.translate(
                (float) (blockPos.getX() - this.viewEntityX),
                (float) (blockPos.getY() - this.viewEntityY),
                (float) (blockPos.getZ() - this.viewEntityZ));
    }

    public void initialize(double p171_0, double p171_1, double p171_2) {
        this.initialized = true;
        this.renderChunks.clear();
        this.viewEntityX = p171_0;
        this.viewEntityY = p171_1;
        this.viewEntityZ = p171_2;
    }
}
