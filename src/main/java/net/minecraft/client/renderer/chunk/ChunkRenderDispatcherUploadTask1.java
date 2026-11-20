package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;

public class ChunkRenderDispatcherUploadTask1 implements Runnable {
    ChunkRenderDispatcher chunkRenderDispatcher;
    ChunkCompileTaskGenerator chunkCompileTaskGenerator;

    public ChunkRenderDispatcherUploadTask1(ChunkRenderDispatcher aCvValue, ChunkCompileTaskGenerator aCwValue) {
        this.chunkRenderDispatcher = aCvValue;
        this.chunkCompileTaskGenerator = aCwValue;
    }

    @Override
    public void run() {
        ChunkRenderDispatcher.access$000(this.chunkRenderDispatcher).remove(this.chunkCompileTaskGenerator);
    }
}
