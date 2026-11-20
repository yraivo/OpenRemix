package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.WorldRendererState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.client.renderer.chunk.CompiledChunkDummyCompiledChunk;

import com.google.common.collect.Lists;

import java.util.List;

public class CompiledChunk {
    private WorldRendererState state;
    private SetVisibility setVisibility;
    private final boolean[] layersUsed = new boolean[EnumWorldBlockLayer.values().length];
    private boolean empty;
    private List tileEntities;
    private final boolean[] layersStarted = new boolean[EnumWorldBlockLayer.values().length];
    public static final CompiledChunk DUMMY = new CompiledChunkDummyCompiledChunk();

    public boolean isVisible(Facing facing, Facing facingValue) {
        return this.setVisibility.isVisible(facing, facingValue);
    }

    public List getTileEntities() {
        return this.tileEntities;
    }

    public void setLayerStarted(EnumWorldBlockLayer enumWorldBlockLayer) {
        this.layersStarted[enumWorldBlockLayer.ordinal()] = true;
    }

    public void setVisibility(SetVisibility aCKValue) {
        this.setVisibility = aCKValue;
    }

    public void setState(WorldRendererState aCJValue) {
        this.state = aCJValue;
    }

    protected void setLayerUsed(EnumWorldBlockLayer enumWorldBlockLayer) {
        this.empty = false;
        this.layersUsed[enumWorldBlockLayer.ordinal()] = true;
    }

    public boolean isLayerStarted(EnumWorldBlockLayer enumWorldBlockLayer) {
        return this.layersStarted[enumWorldBlockLayer.ordinal()];
    }

    public boolean isLayerEmpty(EnumWorldBlockLayer enumWorldBlockLayer) {
        return !this.layersUsed[enumWorldBlockLayer.ordinal()];
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public CompiledChunk() {
        this.empty = true;
        this.tileEntities = Lists.newArrayList();
        this.setVisibility = new SetVisibility();
    }

    public void addTileEntity(TileEntity tileEntity) {
        this.tileEntities.add(tileEntity);
    }

    public WorldRendererState getState() {
        return this.state;
    }
}
