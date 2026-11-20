package net.minecraft.client.renderer.chunk;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumWorldBlockLayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRenderWorker implements Runnable {

    private ChunkRenderDispatcher chunkRenderDispatcher;
    private RegionRenderCacheBuilder regionRenderCacheBuilder;

    private static Logger LOGGER;

    public static void access$000(
            ChunkRenderWorker chunkRenderWorker,
            ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        chunkRenderWorker.freeRenderBuilder(chunkCompileTaskGenerator);
    }

    static {
        net.minecraft.client.renderer.chunk.ChunkRenderWorker.LOGGER = LogManager.getLogger();
    }

    private RegionRenderCacheBuilder getRegionRenderCacheBuilder() throws InterruptedException {
        try {
            if (false)
                throw (java.lang.InterruptedException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (this.regionRenderCacheBuilder != null) {
                return this.regionRenderCacheBuilder;
            }
        } catch (InterruptedException interruptedexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_372052f7d0d87f976f76(interruptedexception));
        }

        return this.chunkRenderDispatcher.allocateRenderBuilder();
    }

    public ChunkRenderWorker(ChunkRenderDispatcher chunkRenderDispatcherValue) {
        this(chunkRenderDispatcherValue, (RegionRenderCacheBuilder) null);
    }

    public static Logger access$100() {
        return net.minecraft.client.renderer.chunk.ChunkRenderWorker.LOGGER;
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.processTask(this.chunkRenderDispatcher.getNextChunkUpdate());
            }
        } catch (InterruptedException interruptedexception) {
            net.minecraft.client.renderer.chunk.ChunkRenderWorker.LOGGER.debug(
                    "Stopping due to interrupt");
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Batching chunks");
            MinecraftClient.getMinecraft()
                    .crashed(
                            MinecraftClient.getMinecraft()
                                    .addGraphicsAndWorldToCrashReport(crashReport));
        }
    }

    private static InterruptedException method_372052f7d0d87f976f76(
            InterruptedException interruptedexception) {
        return interruptedexception;
    }

    protected void processTask(ChunkCompileTaskGenerator task) throws InterruptedException {
        exit:
        {
            java.util.ArrayList futures;
            CompiledChunk compiled;
            afterType:
            {
                ChunkCompileTaskGeneratorType taskType;
                notRebuild:
                {
                    boolean flag;
                    afterRebuild:
                    {
                        float x;
                        float y;
                        float z;
                        beforeResort:
                        {
                            Entity player;
                            havePlayer:
                            {
                                acceptedTask:
                                {
                                    boolean initialFlag = RenderChunk.usesNormalChunkUpdateFlow();
                                    task.getLock().lock();
                                    flag = initialFlag;
                                    warnAndStop:
                                    {
                                        try {
                                            if (task.getStatus()
                                                    != ChunkCompileTaskGeneratorStatus.PENDING)
                                                break warnAndStop;
                                            task.setStatus(
                                                    ChunkCompileTaskGeneratorStatus.COMPILING);
                                        } catch (Throwable failed) {
                                            task.getLock().unlock();
                                            throw failed;
                                        }
                                        task.getLock().unlock();
                                        if (flag) break acceptedTask;
                                    }
                                    try {
                                        if (!task.isFinished()) {
                                            net.minecraft.client.renderer.chunk.ChunkRenderWorker
                                                    .LOGGER
                                                    .warn(
                                                            new StringBuilder()
                                                                    .append(
                                                                            "Chunk render task was"
                                                                                + " ")
                                                                    .append(task.getStatus())
                                                                    .append(
                                                                            " when I expected it to"
                                                                                + " be pending;"
                                                                                + " ignoring task")
                                                                    .toString());
                                        }
                                    } finally {
                                        task.getLock().unlock();
                                    }
                                    return;
                                }
                                player = MinecraftClient.getMinecraft().getRenderViewEntity();
                                if (player != null) break havePlayer;
                                task.finish();
                                if (flag) break exit;
                            }
                            task.setRegionRenderCacheBuilder(this.getRegionRenderCacheBuilder());
                            x = (float) player.posX;
                            y = (float) player.posY + player.getEyeHeight();
                            z = (float) player.posZ;
                            taskType = task.getType();
                            if (taskType != ChunkCompileTaskGeneratorType.REBUILD_CHUNK)
                                break beforeResort;
                            task.getRenderChunk().rebuildChunk(x, y, z, task);
                            if (flag) break afterRebuild;
                        }
                        if (taskType == ChunkCompileTaskGeneratorType.RESORT_TRANSPARENCY) {
                            task.getRenderChunk().resortTransparency(x, y, z, task);
                        }
                    }
                    task.getLock().lock();
                    try {
                        if (task.getStatus() != ChunkCompileTaskGeneratorStatus.COMPILING) {
                            if (!task.isFinished()) {
                                net.minecraft.client.renderer.chunk.ChunkRenderWorker.LOGGER.warn(
                                        new StringBuilder()
                                                .append("Chunk render task was ")
                                                .append(task.getStatus())
                                                .append(
                                                        " when I expected it to be compiling;"
                                                            + " aborting task")
                                                .toString());
                            }
                            this.freeRenderBuilder(task);
                            return;
                        }
                        task.setStatus(ChunkCompileTaskGeneratorStatus.UPLOADING);
                    } finally {
                        task.getLock().unlock();
                    }
                    compiled = task.getCompiledChunk();
                    futures = com.google.common.collect.Lists.newArrayList();
                    if (taskType != ChunkCompileTaskGeneratorType.REBUILD_CHUNK) break notRebuild;
                    EnumWorldBlockLayer[] layers = EnumWorldBlockLayer.values();
                    int count = layers.length;
                    int index = 0;
                    do {
                        if (index >= count) break;
                        EnumWorldBlockLayer layer = layers[index];
                        if (compiled.isLayerStarted(layer)) {
                            futures.add(
                                    this.chunkRenderDispatcher.uploadChunk(
                                            layer,
                                            task.getRegionRenderCacheBuilder()
                                                    .getWorldRendererByLayer(layer),
                                            task.getRenderChunk(),
                                            compiled));
                        }
                        index++;
                    } while (flag);
                    if (flag) break afterType;
                }
                if (taskType == ChunkCompileTaskGeneratorType.RESORT_TRANSPARENCY) {
                    futures.add(
                            this.chunkRenderDispatcher.uploadChunk(
                                    EnumWorldBlockLayer.TRANSLUCENT,
                                    task.getRegionRenderCacheBuilder()
                                            .getWorldRendererByLayer(
                                                    EnumWorldBlockLayer.TRANSLUCENT),
                                    task.getRenderChunk(),
                                    compiled));
                }
            }
            com.google.common.util.concurrent.ListenableFuture future =
                    com.google.common.util.concurrent.Futures.allAsList(futures);
            task.addFinishRunnable(new ProcessTaskRunnable(this, future));
            remix.compat.library.LegacyLibraryAdapters.addCallback(
                    future, new ChunkUploadCompletionCallback(this, task, compiled));
        }
    }

    public ChunkRenderWorker(ChunkRenderDispatcher aCvValue, RegionRenderCacheBuilder aCDValue) {
        this.chunkRenderDispatcher = aCvValue;
        this.regionRenderCacheBuilder = aCDValue;
    }

    private void freeRenderBuilder(ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        if (this.regionRenderCacheBuilder == null) {
            this.chunkRenderDispatcher.freeRenderBuilder(
                    chunkCompileTaskGenerator.getRegionRenderCacheBuilder());
        }
    }

    // Helper used only by this class.
    public static class ProcessTaskRunnable implements java.lang.Runnable {
        com.google.common.util.concurrent.ListenableFuture aCE;
        net.minecraft.client.renderer.chunk.ChunkRenderWorker aCF;

        @java.lang.Override
        public void run() {
            this.aCE.cancel(false);
        }

        public ProcessTaskRunnable(
                net.minecraft.client.renderer.chunk.ChunkRenderWorker aCFValue,
                com.google.common.util.concurrent.ListenableFuture listenablefuture) {
            this.aCF = aCFValue;
            this.aCE = listenablefuture;
        }
    }

    // Helper used only by this class.
    public static class ChunkUploadCompletionCallback
            implements com.google.common.util.concurrent.FutureCallback {

        net.minecraft.client.renderer.chunk.CompiledChunk aCG;

        net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator aCw;

        net.minecraft.client.renderer.chunk.ChunkRenderWorker aCI;

        public void onSuccess(java.lang.Object object) {
            this.method_aa34364551e813c4b2f4((java.util.List) object);
        }

        private static java.util.concurrent.CancellationException method_23af6b17af6397ce47c9(
                java.util.concurrent.CancellationException cancellationexception) {
            return cancellationexception;
        }

        public void onFailure(java.lang.Throwable throwable) {
            net.minecraft.client.renderer.chunk.ChunkRenderWorker.access$000(this.aCI, this.aCw);
            if (!(throwable instanceof java.util.concurrent.CancellationException)
                    && !(throwable instanceof java.lang.InterruptedException)) {
                net.minecraft.client.MinecraftClient.getMinecraft()
                        .crashed(
                                net.minecraft.crash.CrashReport.makeCrashReport(
                                        throwable, "Rendering chunk"));
            }
        }


        public ChunkUploadCompletionCallback(
                net.minecraft.client.renderer.chunk.ChunkRenderWorker aCIValue,
                net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator aCwValue,
                net.minecraft.client.renderer.chunk.CompiledChunk aCGValue) {
            this.aCI = aCIValue;
            this.aCw = aCwValue;
            this.aCG = aCGValue;
        }

        public void method_aa34364551e813c4b2f4(java.util.List list) {
            net.minecraft.client.renderer.chunk.ChunkRenderWorker.access$000(this.aCI, this.aCw);
            this.aCw.getLock().lock();

            label43:
            {
                try {
                    if (this.aCw.getStatus()
                            == net.minecraft.client.renderer.chunk.ChunkCompileTaskGeneratorStatus
                                    .UPLOADING) {
                        this.aCw.setStatus(
                                net.minecraft.client.renderer.chunk.ChunkCompileTaskGeneratorStatus
                                        .DONE);
                        break label43;
                    }

                    if (!this.aCw.isFinished()) {
                        net.minecraft.client.renderer.chunk.ChunkRenderWorker.access$100()
                                .warn(
                                        "Chunk render task was "
                                                + this.aCw.getStatus()
                                                + " when I expected it to be uploading; aborting"
                                                + " task");
                    }
                } finally {
                    this.aCw.getLock().unlock();
                }

                return;
            }

            this.aCw.getRenderChunk().setCompiledChunk(this.aCG);
        }
    }
}
