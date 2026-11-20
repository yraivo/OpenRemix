package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.VertexBufferUploader;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumWorldBlockLayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;

public class ChunkRenderDispatcher {
    private BlockingQueue queueChunkUpdates;
    private VertexBufferUploader vertexUploader;
    private ChunkRenderWorker renderWorker;
    private static Logger logger;

    private WorldVertexBufferUploader worldVertexUploader;
    private BlockingQueue queueFreeRenderBuilders;
    private Queue queueChunkUploads;
    private static ThreadFactory threadFactory;
    private List listThreadedWorkers;

    public RegionRenderCacheBuilder allocateRenderBuilder()
            throws InterruptedException, InterruptedException {
        return (RegionRenderCacheBuilder) this.queueFreeRenderBuilders.take();
    }

    public boolean runChunkUploads(long i) {
        boolean flag1 = RenderChunk.usesNormalChunkUpdateFlow();
        byte b0 = 0;
        boolean flag = flag1;

        long j;
        do {
            byte b1 = 0;
            Queue queue = this.queueChunkUploads;
            Queue queue1 = this.queueChunkUploads;
            synchronized (this.queueChunkUploads) {
                if (!this.queueChunkUploads.isEmpty()) {
                    ((ListenableFutureTask) this.queueChunkUploads.poll()).run();
                    b1 = 1;
                    b0 = 1;
                }
            }

            if (i == 0L || b1 == 0 && flag) {
                break;
            }

            j = i - System.nanoTime();

            if (j < 0L) {
                break;
            }

        } while (j <= 1000000000L);

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public boolean updateTransparencyLater(RenderChunk renderChunk) {
        renderChunk.getLockCompileTask().lock();

        boolean flag;
        try {
            ChunkCompileTaskGenerator chunkCompileTaskGenerator =
                    renderChunk.createTransparencyResortTask();
            if (chunkCompileTaskGenerator == null) {
                return true;
            }

            chunkCompileTaskGenerator.addFinishRunnable(
                    new ChunkRenderDispatcherUploadTask1(this, chunkCompileTaskGenerator));
            boolean flag1 = this.queueChunkUpdates.offer(chunkCompileTaskGenerator);
            flag = flag1;
        } finally {
            renderChunk.getLockCompileTask().unlock();
        }

        return flag;
    }

    public static BlockingQueue access$000(ChunkRenderDispatcher chunkRenderDispatcher) {
        return chunkRenderDispatcher.queueChunkUpdates;
    }

    public boolean updateChunkLater(RenderChunk renderChunk) {
        renderChunk.getLockCompileTask().lock();

        boolean flag;
        try {
            ChunkCompileTaskGenerator chunkCompileTaskGenerator =
                    renderChunk.createChunkRebuildTask();
            chunkCompileTaskGenerator.addFinishRunnable(
                    new ChunkRenderDispatcherUploadTask2(this, chunkCompileTaskGenerator));
            boolean flag1 = this.queueChunkUpdates.offer(chunkCompileTaskGenerator);
            if (!flag1) {
                chunkCompileTaskGenerator.finish();
            }

            flag = flag1;
        } finally {
            renderChunk.getLockCompileTask().unlock();
        }

        return flag;
    }

    static {
        net.minecraft.client.renderer.chunk.ChunkRenderDispatcher.logger = LogManager.getLogger();
        net.minecraft.client.renderer.chunk.ChunkRenderDispatcher.threadFactory =
                new ThreadFactoryBuilder()
                        .setNameFormat("Chunk Batcher %d")
                        .setDaemon(true)
                        .build();
    }

    public void stopChunkUpdates() {
        boolean flag1 = RenderChunk.isLegacyChunkUpdateFlow();
        this.clearPendingChunkUpdates();
        boolean flag = flag1;

        try {
            if (false)
                throw (java.lang.InterruptedException)
                        null; // Original JVM checked handler; no emitted instructions.
            while (true) {

                if (this.runChunkUploads(0L)) {
                    continue;
                }
                break;
            }
        } catch (InterruptedException interruptedexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_372052f7d0d87f976f76(interruptedexception1));
        }

        ArrayList arrayList = Lists.newArrayList();

        while (arrayList.size() != 5) {
            try {
                arrayList.add(this.allocateRenderBuilder());
            } catch (InterruptedException interruptedexception) {
                if (flag) {
                    break;
                }
            }
        }

        this.queueFreeRenderBuilders.addAll(arrayList);
    }

    private void uploadDisplayList(WorldRenderer worldRenderer, int i, RenderChunk renderChunk) {
        GL11.glNewList(i, 4864);
        GlStateManager.pushMatrix();
        renderChunk.multModelviewMatrix();
        this.worldVertexUploader.draw(worldRenderer, worldRenderer.getDrawByteCount());
        GlStateManager.popMatrix();
        GL11.glEndList();
    }

    public ChunkRenderDispatcher() {
        super();
        boolean initialFlowFlag = RenderChunk.isLegacyChunkUpdateFlow();

        boolean flag1 = initialFlowFlag;

        boolean flag = flag1;
        this.listThreadedWorkers = Lists.newArrayList();
        this.queueChunkUpdates = Queues.newArrayBlockingQueue(100);
        this.queueFreeRenderBuilders = Queues.newArrayBlockingQueue(5);
        this.worldVertexUploader = new WorldVertexBufferUploader();
        this.vertexUploader = new VertexBufferUploader();
        this.queueChunkUploads = Queues.newArrayDeque();
        int i = 0;

        while (i < 2) {
            ChunkRenderWorker chunkRenderWorker = new ChunkRenderWorker(this);
            Thread thread =
                    net.minecraft.client.renderer.chunk.ChunkRenderDispatcher.threadFactory
                            .newThread(chunkRenderWorker);
            thread.start();
            this.listThreadedWorkers.add(chunkRenderWorker);
            i++;
            if (flag) {
                break;
            }
        }

        i = 0;

        while (i < 5) {
            this.queueFreeRenderBuilders.add(new RegionRenderCacheBuilder());
            i++;
            if (flag) {
                break;
            }
        }

        this.renderWorker = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            RenderChunk.setLegacyChunkUpdateFlow(!flag);
        }
    }

    public ListenableFuture uploadChunk(
            EnumWorldBlockLayer enumWorldBlockLayer,
            WorldRenderer worldRenderer,
            RenderChunk renderChunk,
            CompiledChunk compiledChunk) {
        boolean flag = RenderChunk.usesNormalChunkUpdateFlow();
        if (!MinecraftClient.getMinecraft().isCallingFromMinecraftThread()) {
            ListenableFutureTask listenablefuturetask =
                    ListenableFutureTask.create(
                            new UploadChunkRunnable(
                                    this,
                                    enumWorldBlockLayer,
                                    worldRenderer,
                                    renderChunk,
                                    compiledChunk),
                            null);
            Queue queue = this.queueChunkUploads;
            Queue queue1 = this.queueChunkUploads;
            synchronized (this.queueChunkUploads) {
                this.queueChunkUploads.add(listenablefuturetask);
                return listenablefuturetask;
            }
        } else {
            {
                if (OpenGlHelper.useVbo()) {
                    this.uploadVertexBuffer(
                            worldRenderer,
                            renderChunk.getVertexBufferByLayer(enumWorldBlockLayer.ordinal()));
                    if (!(flag)) {

                        this.uploadDisplayList(
                                worldRenderer,
                                ((ListedRenderChunk) renderChunk)
                                        .getDisplayList(enumWorldBlockLayer, compiledChunk),
                                renderChunk);
                    }
                } else {

                    this.uploadDisplayList(
                            worldRenderer,
                            ((ListedRenderChunk) renderChunk)
                                    .getDisplayList(enumWorldBlockLayer, compiledChunk),
                            renderChunk);
                }
            }

            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            return Futures.immediateFuture(null);
        }
    }

    private void uploadVertexBuffer(WorldRenderer worldRenderer, VertexBuffer vertexBuffer) {
        this.vertexUploader.setVertexBuffer(vertexBuffer);
        this.vertexUploader.draw(worldRenderer, worldRenderer.getDrawByteCount());
    }

    public boolean updateChunkNow(RenderChunk renderChunk) {
        renderChunk.getLockCompileTask().lock();

        byte b0;
        try {
            ChunkCompileTaskGenerator chunkCompileTaskGenerator =
                    renderChunk.createChunkRebuildTask();

            try {
                this.renderWorker.processTask(chunkCompileTaskGenerator);
            } catch (InterruptedException interruptedexception) {
            }

            b0 = 1;
        } finally {
            renderChunk.getLockCompileTask().unlock();
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public void clearPendingChunkUpdates() {
        boolean flag = RenderChunk.usesNormalChunkUpdateFlow();

        while (!this.queueChunkUpdates.isEmpty()) {
            ChunkCompileTaskGenerator chunkCompileTaskGenerator =
                    (ChunkCompileTaskGenerator) this.queueChunkUpdates.poll();
            if (chunkCompileTaskGenerator != null) {
                chunkCompileTaskGenerator.finish();
            }

            if (!flag) {
                break;
            }
        }
    }

    public ChunkCompileTaskGenerator getNextChunkUpdate()
            throws InterruptedException, InterruptedException {
        return (ChunkCompileTaskGenerator) this.queueChunkUpdates.take();
    }

    private static InterruptedException method_372052f7d0d87f976f76(
            InterruptedException interruptedexception) {
        return interruptedexception;
    }

    public String getDebugInfo() {
        return String.format(
                "pC: %03d, pU: %1d, aB: %1d",
                this.queueChunkUpdates.size(),
                this.queueChunkUploads.size(),
                this.queueFreeRenderBuilders.size());
    }

    public void freeRenderBuilder(RegionRenderCacheBuilder regionRenderCacheBuilder) {
        this.queueFreeRenderBuilders.add(regionRenderCacheBuilder);
    }

    // Helper used only by this class.
    public static class UploadChunkRunnable implements java.lang.Runnable {
        net.minecraft.util.EnumWorldBlockLayer aCx;
        net.minecraft.client.renderer.chunk.CompiledChunk aCy;
        net.minecraft.client.renderer.chunk.ChunkRenderDispatcher aCz;
        net.minecraft.client.renderer.WorldRenderer aCA;
        net.minecraft.client.renderer.chunk.RenderChunk aCB;

        public UploadChunkRunnable(
                net.minecraft.client.renderer.chunk.ChunkRenderDispatcher aCzValue,
                net.minecraft.util.EnumWorldBlockLayer aCxValue,
                net.minecraft.client.renderer.WorldRenderer aCAValue,
                net.minecraft.client.renderer.chunk.RenderChunk aCBValue,
                net.minecraft.client.renderer.chunk.CompiledChunk aCyValue) {
            this.aCz = aCzValue;
            this.aCx = aCxValue;
            this.aCA = aCAValue;
            this.aCB = aCBValue;
            this.aCy = aCyValue;
        }

        @java.lang.Override
        public void run() {
            this.aCz.uploadChunk(this.aCx, this.aCA, this.aCB, this.aCy);
        }
    }
}
