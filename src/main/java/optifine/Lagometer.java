package optifine;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.profiler.Profiler;

import org.lwjgl.opengl.GL11;

public class Lagometer {
    public static LagometerTimerNano timerChunkUpdate;

    public static boolean active;
    private static long[] timesFrame;
    private static long memLast;
    private static long[] timesChunkUpload;
    private static Profiler profiler;
    public static LagometerTimerNano timerTerrain;
    private static long memTimeDiffMs;
    private static long memStart;
    public static LagometerTimerNano timerTick;
    public static LagometerTimerNano timerChunkUpload;
    private static long memDiff;

    private static MinecraftClient mc;
    private static String[] packedTextFragments;
    public static LagometerTimerNano timerVisibility;
    private static long[] timesTick;
    public static LagometerTimerNano timerScheduledExecutables;
    private static long renderTimeNano;
    private static int numRecordedFrameTimes;
    private static int memMbSec;
    public static LagometerTimerNano timerServer;
    private static long memTimeLast;
    private static long memTimeStartMs;
    private static long[] timesScheduledExecutables;
    private static long[] timesTerrain;
    private static long[] timesVisibility;
    private static boolean[] gcs;
    private static GameSettings gameSettings;
    private static long[] timesServer;
    private static long prevFrameTimeNano;
    private static long[] timesChunkUpdate;

    private static long renderTime(
            int i, long j, int k, int l, int i1, float f, WorldRenderer worldRenderer) {

        long j1 = j / 200000L;

        if (j1 < 3L) {

            return 0L;
        } else {
            worldRenderer.setVertexColor(k, l, i1, 255);
            worldRenderer.addVertex(i + 0.5F, f - (float) j1 + 0.5F, 0.0);
            worldRenderer.addVertex(i + 0.5F, f + 0.5F, 0.0);
            return j1;
        }
    }

    private static long renderTimeDivider(
            int i, int j, long k, int l, int i1, int j1, float f, WorldRenderer worldRenderer) {

        long k1 = k / 200000L;

        if (k1 < 3L) {

            return 0L;
        } else {
            worldRenderer.setVertexColor(l, i1, j1, 255);
            worldRenderer.addVertex(i + 0.5F, f - (float) k1 + 0.5F, 0.0);
            worldRenderer.addVertex(j + 0.5F, f - (float) k1 + 0.5F, 0.0);
            return k1;
        }
    }

    private static long getMemoryUsed() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static boolean isActive() {
        return optifine.Lagometer.active;
    }

    public static void updateLagometer() {
        int i = ReflectorMethod.f();
        if (optifine.Lagometer.mc == null) {
            optifine.Lagometer.mc = MinecraftClient.getMinecraft();
            optifine.Lagometer.gameSettings = optifine.Lagometer.mc.gameSettings;
            optifine.Lagometer.profiler = optifine.Lagometer.mc.mcProfiler;
        }

        if (optifine.Lagometer.gameSettings.showDebugInfo
                && optifine.Lagometer.gameSettings.ofLagometer) {
            label20:
            {
                optifine.Lagometer.active = true;
                long j = System.nanoTime();

                if (optifine.Lagometer.prevFrameTimeNano == -1L) {
                    optifine.Lagometer.prevFrameTimeNano = j;
                    if (i == 0) {
                        break label20;
                    }
                }

                int k =
                        optifine.Lagometer.numRecordedFrameTimes
                                & optifine.Lagometer.timesFrame.length - 1;
                optifine.Lagometer.numRecordedFrameTimes++;
                boolean flag = aY();
                optifine.Lagometer.timesFrame[k] =
                        j
                                - optifine.Lagometer.prevFrameTimeNano
                                - optifine.Lagometer.renderTimeNano;
                optifine.Lagometer.timesTick[k] = optifine.Lagometer.timerTick.timeNano;
                optifine.Lagometer.timesScheduledExecutables[k] =
                        optifine.Lagometer.timerScheduledExecutables.timeNano;
                optifine.Lagometer.timesChunkUpload[k] =
                        optifine.Lagometer.timerChunkUpload.timeNano;
                optifine.Lagometer.timesChunkUpdate[k] =
                        optifine.Lagometer.timerChunkUpdate.timeNano;
                optifine.Lagometer.timesVisibility[k] = optifine.Lagometer.timerVisibility.timeNano;
                optifine.Lagometer.timesTerrain[k] = optifine.Lagometer.timerTerrain.timeNano;
                optifine.Lagometer.timesServer[k] = optifine.Lagometer.timerServer.timeNano;
                optifine.Lagometer.gcs[k] = flag;
                LagometerTimerNano.resetTimer(optifine.Lagometer.timerTick);
                LagometerTimerNano.resetTimer(optifine.Lagometer.timerScheduledExecutables);
                LagometerTimerNano.resetTimer(optifine.Lagometer.timerVisibility);
                LagometerTimerNano.resetTimer(optifine.Lagometer.timerChunkUpdate);
                LagometerTimerNano.resetTimer(optifine.Lagometer.timerChunkUpload);
                LagometerTimerNano.resetTimer(optifine.Lagometer.timerTerrain);
                LagometerTimerNano.resetTimer(optifine.Lagometer.timerServer);
                optifine.Lagometer.prevFrameTimeNano = System.nanoTime();
            }

            if (i == 0) {
                return;
            }
        }

        optifine.Lagometer.active = false;

        optifine.Lagometer.prevFrameTimeNano = -1L;
    }

    public static void showLagometer(ScaledResolution scaledResolution) {
        int i = ReflectorMethod.cR();
        if (optifine.Lagometer.gameSettings != null
                && optifine.Lagometer.gameSettings.ofLagometer) {
            long j = System.nanoTime();
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            GlStateManager.pushMatrix();
            GlStateManager.enableColorMaterial();
            GlStateManager.loadIdentity();
            GlStateManager.ortho(
                    0.0,
                    optifine.Lagometer.mc.displayWidth,
                    optifine.Lagometer.mc.displayHeight,
                    0.0,
                    1000.0,
                    3000.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, 0.0F, -2000.0F);
            GL11.glLineWidth(1.0F);
            GlStateManager.disableTexture2D();
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.startDrawing(1);
            int k = 0;

            while (k < optifine.Lagometer.timesFrame.length) {
                label21:
                {
                    int l =
                            (k - optifine.Lagometer.numRecordedFrameTimes
                                            & optifine.Lagometer.timesFrame.length - 1)
                                    * 100
                                    / optifine.Lagometer.timesFrame.length;
                    l += 155;
                    float displayHeight = optifine.Lagometer.mc.displayHeight;

                    if (optifine.Lagometer.gcs[k]) {
                        renderTime(
                                k,
                                optifine.Lagometer.timesFrame[k],
                                l,
                                l / 2,
                                0,
                                displayHeight,
                                worldRenderer);
                        if (i != 0) {
                            break label21;
                        }
                    }

                    renderTime(
                            k,
                            optifine.Lagometer.timesFrame[k],
                            l,
                            l,
                            l,
                            displayHeight,
                            worldRenderer);
                    displayHeight -=
                            (float)
                                    renderTime(
                                            k,
                                            optifine.Lagometer.timesServer[k],
                                            l / 2,
                                            l / 2,
                                            l / 2,
                                            displayHeight,
                                            worldRenderer);
                    displayHeight -=
                            (float)
                                    renderTime(
                                            k,
                                            optifine.Lagometer.timesTerrain[k],
                                            0,
                                            l,
                                            0,
                                            displayHeight,
                                            worldRenderer);
                    displayHeight -=
                            (float)
                                    renderTime(
                                            k,
                                            optifine.Lagometer.timesVisibility[k],
                                            l,
                                            l,
                                            0,
                                            displayHeight,
                                            worldRenderer);
                    displayHeight -=
                            (float)
                                    renderTime(
                                            k,
                                            optifine.Lagometer.timesChunkUpdate[k],
                                            l,
                                            0,
                                            0,
                                            displayHeight,
                                            worldRenderer);
                    displayHeight -=
                            (float)
                                    renderTime(
                                            k,
                                            optifine.Lagometer.timesChunkUpload[k],
                                            l,
                                            0,
                                            l,
                                            displayHeight,
                                            worldRenderer);
                    displayHeight -=
                            (float)
                                    renderTime(
                                            k,
                                            optifine.Lagometer.timesScheduledExecutables[k],
                                            0,
                                            0,
                                            l,
                                            displayHeight,
                                            worldRenderer);
                    float f1 =
                            displayHeight
                                    - (float)
                                            renderTime(
                                                    k,
                                                    optifine.Lagometer.timesTick[k],
                                                    0,
                                                    l,
                                                    l,
                                                    displayHeight,
                                                    worldRenderer);
                }

                k++;
                if (i == 0) {
                    break;
                }
            }

            int length = optifine.Lagometer.timesFrame.length;

            renderTimeDivider(
                    0,
                    length,
                    33333333L,
                    196,
                    196,
                    196,
                    optifine.Lagometer.mc.displayHeight,
                    worldRenderer);
            length = optifine.Lagometer.timesFrame.length;

            renderTimeDivider(
                    0,
                    length,
                    16666666L,
                    196,
                    196,
                    196,
                    optifine.Lagometer.mc.displayHeight,
                    worldRenderer);
            instance.draw();
            GlStateManager.enableTexture2D();
            k = optifine.Lagometer.mc.displayHeight - 80;
            int j2 = optifine.Lagometer.mc.displayHeight - 160;
            optifine.Lagometer.mc.fontRendererObj.drawString("30", 2, j2 + 1, -8947849);
            optifine.Lagometer.mc.fontRendererObj.drawString("30", 1, j2, -3881788);
            optifine.Lagometer.mc.fontRendererObj.drawString("60", 2, k + 1, -8947849);
            optifine.Lagometer.mc.fontRendererObj.drawString("60", 1, k, -3881788);
            GlStateManager.matrixMode(5889);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.enableTexture2D();
            float f2 =
                    1.0F
                            - (float)
                                    ((System.currentTimeMillis()
                                                    - optifine.Lagometer.memTimeStartMs)
                                            / 1000.0);
            f2 = OptiFineConfig.limit(f2, 0.0F, 1.0F);
            int k2 = (int) (170.0F + f2 * 85.0F);
            int j1 = (int) (100.0F + f2 * 55.0F);
            int l2 = (int) (10.0F + f2 * 10.0F);
            int k1 = k2 << 16 | j1 << 8 | l2;
            int l1 = 512 / scaledResolution.getScaleFactor() + 2;
            int i2 = optifine.Lagometer.mc.displayHeight / scaledResolution.getScaleFactor() - 8;
            GuiIngame anf = optifine.Lagometer.mc.ingameGUI;
            GuiIngame.drawRect(l1 - 1, i2 - 1, l1 + 50, i2 + 10, -1605349296);
            optifine.Lagometer.mc.fontRendererObj.drawString(
                    packedTextFragments[0] + optifine.Lagometer.memMbSec + " MB/s", l1, i2, k1);
            optifine.Lagometer.renderTimeNano = System.nanoTime() - j;
        }
    }

    public static boolean aY() {
        long j;
        long k;
        byte b0;
        {
            int i1 = ReflectorMethod.f();
            j = System.currentTimeMillis();
            k = getMemoryUsed();
            int i = i1;
            b0 = 0;
            if (k < optifine.Lagometer.memLast) {
                double d0 = optifine.Lagometer.memDiff / 1000000.0;
                double d1 = optifine.Lagometer.memTimeDiffMs / 1000.0;
                int l = (int) (d0 / d1);
                if (l > 0) {
                    optifine.Lagometer.memMbSec = l;
                }

                optifine.Lagometer.memTimeStartMs = j;
                optifine.Lagometer.memStart = k;

                optifine.Lagometer.memTimeDiffMs = 0L;

                optifine.Lagometer.memDiff = 0L;
                b0 = 1;
                if (!(i == 0)) {

                    optifine.Lagometer.memTimeDiffMs = j - optifine.Lagometer.memTimeStartMs;
                    optifine.Lagometer.memDiff = k - optifine.Lagometer.memStart;
                }
            } else {

                optifine.Lagometer.memTimeDiffMs = j - optifine.Lagometer.memTimeStartMs;
                optifine.Lagometer.memDiff = k - optifine.Lagometer.memStart;
            }
        }

        optifine.Lagometer.memTimeLast = j;
        optifine.Lagometer.memLast = k;
        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    static {
        optifine.Lagometer.packedTextFragments = new String[3];
        optifine.Lagometer.packedTextFragments[0] = new String(" ");
        optifine.Lagometer.active = false;
        optifine.Lagometer.timerTick = new LagometerTimerNano();
        optifine.Lagometer.timerScheduledExecutables = new LagometerTimerNano();
        optifine.Lagometer.timerChunkUpload = new LagometerTimerNano();
        optifine.Lagometer.timerChunkUpdate = new LagometerTimerNano();
        optifine.Lagometer.timerVisibility = new LagometerTimerNano();
        optifine.Lagometer.timerTerrain = new LagometerTimerNano();
        optifine.Lagometer.timerServer = new LagometerTimerNano();
        optifine.Lagometer.timesFrame = new long[512];
        optifine.Lagometer.timesTick = new long[512];
        optifine.Lagometer.timesScheduledExecutables = new long[512];
        optifine.Lagometer.timesChunkUpload = new long[512];
        optifine.Lagometer.timesChunkUpdate = new long[512];
        optifine.Lagometer.timesVisibility = new long[512];
        optifine.Lagometer.timesTerrain = new long[512];
        optifine.Lagometer.timesServer = new long[512];
        optifine.Lagometer.gcs = new boolean[512];
        optifine.Lagometer.numRecordedFrameTimes = 0;
        optifine.Lagometer.prevFrameTimeNano = -1L;
        optifine.Lagometer.renderTimeNano = 0L;
        optifine.Lagometer.memTimeStartMs = System.currentTimeMillis();
        optifine.Lagometer.memStart = getMemoryUsed();
        optifine.Lagometer.memTimeLast = optifine.Lagometer.memTimeStartMs;
        optifine.Lagometer.memLast = optifine.Lagometer.memStart;
        optifine.Lagometer.memTimeDiffMs = 1L;
        optifine.Lagometer.memDiff = 0L;
        optifine.Lagometer.memMbSec = 0;
    }
}
