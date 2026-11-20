package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.Facing;

import java.util.EnumSet;
import java.util.Set;

public class ChunkVisibilityEntry {
    public int traversalDepth;
    public Facing entryDirection;
    public RenderChunk renderChunk;
    public final Set pathDirections = EnumSet.noneOf(Facing.class);

    public ChunkVisibilityEntry(RenderChunk renderChunk, Facing aAcValue, int i) {
        this.renderChunk = renderChunk;
        this.entryDirection = aAcValue;
        this.traversalDepth = i;
    }

    public ChunkVisibilityEntry(RenderChunk renderChunk, Facing facing, int i, Object object) {
        this(renderChunk, facing, i);
    }
}
