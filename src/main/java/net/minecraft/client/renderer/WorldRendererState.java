package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class WorldRendererState {
    public TextureAtlasSprite[] stateQuadSprites;
    private VertexFormat stateVertexFormat;
    private int[] stateRawBuffer;
    WorldRenderer field_179021_a;
    private int cz;
    private int c;

    public int getVertexCount() {
        return this.c;
    }

    public VertexFormat getVertexFormat() {
        return this.stateVertexFormat;
    }

    public int getRawBufferIndex() {
        return this.cz;
    }

    public WorldRendererState(
            WorldRenderer aAwValue, int[] aint, int i, int j, VertexFormat aAvValue) {
        this.field_179021_a = aAwValue;
        this.stateRawBuffer = aint;
        this.cz = i;
        this.c = j;
        this.stateVertexFormat = aAvValue;
    }

    public WorldRendererState(
            WorldRenderer aAwValue,
            int[] aint,
            int i,
            int j,
            VertexFormat aAvValue,
            TextureAtlasSprite[] aAuValue) {
        this.field_179021_a = aAwValue;
        this.stateRawBuffer = aint;
        this.cz = i;
        this.c = j;
        this.stateVertexFormat = aAvValue;
        this.stateQuadSprites = aAuValue;
    }

    public int[] getRawBuffer() {
        return this.stateRawBuffer;
    }
}
