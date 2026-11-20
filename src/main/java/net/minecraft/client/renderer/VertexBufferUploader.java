package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.VertexBuffer;

public class VertexBufferUploader extends WorldVertexBufferUploader {
    private VertexBuffer vertexBuffer = null;

    public int draw(WorldRenderer worldRenderer, int p171_1) {
        worldRenderer.reset();
        this.vertexBuffer.uploadVertexData(worldRenderer.getByteBuffer(), worldRenderer.getDrawByteCount());
        return p171_1;
    }

    public void setVertexBuffer(VertexBuffer aAxValue) {
        this.vertexBuffer = aAxValue;
    }
}
