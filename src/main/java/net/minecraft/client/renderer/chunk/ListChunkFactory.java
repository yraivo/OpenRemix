package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ListChunkFactory implements IRenderChunkFactory {
    public RenderChunk makeRenderChunk(
            World world, RenderGlobal renderGlobal, BlockPos blockPos, int i) {
        return new ListedRenderChunk(world, renderGlobal, blockPos, i);
    }
}
