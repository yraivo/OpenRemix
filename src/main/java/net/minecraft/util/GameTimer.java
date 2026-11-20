package net.minecraft.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class GameTimer {
    public float timerSpeed = 1.0F;
    private double timeSyncAdjustment = 1.0;
    private double lastHRTime;
    public float elapsedPartialTicks;
    private static String[] packedTextFragments;
    float ticksPerSecond;
    private long lastSyncSysClock;
    public float renderPartialTicks;
    private long lastSyncHRClock;
    private long vZ;
    public int elapsedTicks;

    public GameTimer(float ticksPerSecondValue) {
        this.ticksPerSecond = ticksPerSecondValue;
        this.lastSyncSysClock = MinecraftClient.getSystemTime();
        long i = System.nanoTime();

        this.lastSyncHRClock = i / 1000000L;
    }

    // region Static initialization and string tables
    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // endregion

    // region String decoding support

    // endregion

    public void updateTimer() {
        long aMNValue;
        double d0;
        label28:
        {
            aMNValue = MinecraftClient.getSystemTime();
            String text = Vec3i.getLegacyCoordinateFlowMarker();
            long j = aMNValue - this.lastSyncSysClock;
            String textValue = text;
            long i1 = System.nanoTime();

            long k = i1 / 1000000L;
            d0 = k / 1000.0;

            if (j <= 1000L) {

                if (j >= 0L) {
                    this.vZ += j;

                    if (this.vZ > 1000L) {
                        long l = k - this.lastSyncHRClock;
                        double d1 = (double) this.vZ / l;
                        this.timeSyncAdjustment = this.timeSyncAdjustment + (d1 - this.timeSyncAdjustment) * 0.2F;
                        this.lastSyncHRClock = k;

                        this.vZ = 0L;
                    }

                    if (this.vZ >= 0L) {
                        break label28;
                    }

                    this.lastSyncHRClock = k;
                    if (textValue != null) {
                        break label28;
                    }
                }
            }

            this.lastHRTime = d0;
        }

        this.lastSyncSysClock = aMNValue;
        double d2 = (d0 - this.lastHRTime) * this.timeSyncAdjustment;
        this.lastHRTime = d0;
        d2 = MathHelper.clamp(d2, 0.0, 1.0);
        this.elapsedPartialTicks =
                (float) (this.elapsedPartialTicks + d2 * this.timerSpeed * this.ticksPerSecond);
        this.elapsedTicks = (int) this.elapsedPartialTicks;
        this.elapsedPartialTicks = this.elapsedPartialTicks - this.elapsedTicks;
        if (this.elapsedTicks > 10) {
            this.elapsedTicks = 10;
        }

        this.renderPartialTicks = this.elapsedPartialTicks;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            Vec3i.setLegacyCoordinateFlowMarker(packedTextFragments[0]);
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.util.GameTimer.packedTextFragments[0] = new String("zK0em");
    }
    // endregion
}
