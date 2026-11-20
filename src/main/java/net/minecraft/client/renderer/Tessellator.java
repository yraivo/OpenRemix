package net.minecraft.client.renderer;

public class Tessellator {
    private WorldRenderer worldRenderer;
    private WorldVertexBufferUploader vboUploader = new WorldVertexBufferUploader();
    private static final Tessellator instance = new Tessellator(2097152);

    public int draw() {
        return this.vboUploader.draw(this.worldRenderer, this.worldRenderer.draw());
    }

    public static Tessellator getInstance() {
        return net.minecraft.client.renderer.Tessellator.instance;
    }

    public Tessellator(int i) {
        this.worldRenderer = new WorldRenderer(i);
    }

    public WorldRenderer getWorldRenderer() {
        return this.worldRenderer;
    }
}
