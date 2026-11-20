package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.RegionRenderCacheBuilder;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ChunkCompileTaskGenerator {
    private ChunkCompileTaskGeneratorStatus status;
    private boolean finished;
    private List listFinishRunnables;
    private final ReentrantLock lock = new ReentrantLock();
    private ChunkCompileTaskGeneratorType type;
    private RegionRenderCacheBuilder regionRenderCacheBuilder;
    private CompiledChunk compiledChunk;
    private RenderChunk renderChunk;

    public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
        return this.regionRenderCacheBuilder;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setStatus(ChunkCompileTaskGeneratorStatus aCiValue) {
        this.lock.lock();

        try {
            this.status = aCiValue;
        } finally {
            this.lock.unlock();
        }
    }

    public CompiledChunk getCompiledChunk() {
        return this.compiledChunk;
    }

    public ReentrantLock getLock() {
        return this.lock;
    }

    public ChunkCompileTaskGeneratorType getType() {
        return this.type;
    }

    public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder aClValue) {
        this.regionRenderCacheBuilder = aClValue;
    }

    public void addFinishRunnable(Runnable runnable) {
        this.lock.lock();

        try {
            this.listFinishRunnables.add(runnable);
            if (this.finished) {
                runnable.run();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void setCompiledChunk(CompiledChunk aCmValue) {
        this.compiledChunk = aCmValue;
    }

    public RenderChunk getRenderChunk() {
        return this.renderChunk;
    }

    public ChunkCompileTaskGenerator(
            RenderChunk renderChunk, ChunkCompileTaskGeneratorType aCkValue) {
        this.listFinishRunnables = Lists.newArrayList();
        this.status = ChunkCompileTaskGeneratorStatus.PENDING;
        this.renderChunk = renderChunk;
        this.type = aCkValue;
    }

    public ChunkCompileTaskGeneratorStatus getStatus() {
        return this.status;
    }

    public void finish() {
        boolean flag1 = RenderChunk.isLegacyChunkUpdateFlow();
        this.lock.lock();
        boolean flag = flag1;

        try {
            if (this.type == ChunkCompileTaskGeneratorType.REBUILD_CHUNK
                    && this.status != ChunkCompileTaskGeneratorStatus.DONE) {
                this.renderChunk.setNeedsUpdate(true);
            }

            this.finished = true;
            this.status = ChunkCompileTaskGeneratorStatus.DONE;

            for (java.util.Iterator<?> __recoveryIterator171_3449 =
                            (this.listFinishRunnables).iterator();
                    (__recoveryIterator171_3449).hasNext(); ) {
                Runnable runnable = (Runnable) (__recoveryIterator171_3449).next();
                runnable.run();
                if (flag) {
                    break;
                }
            }
        } finally {
            this.lock.unlock();
        }
    }
}
