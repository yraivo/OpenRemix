package net.minecraft.server;

import net.minecraft.util.IProgressUpdate;

class WorldConversionProgressLogger implements IProgressUpdate {
    MinecraftServer field_74267_a;
    private static String[] packedTextFragments;
    private long startTime;
    private static String c;

    public void setLoadingProgress(int i) {
        long j = System.currentTimeMillis() - this.startTime;

        if (j >= 1000L) {
            this.startTime = System.currentTimeMillis();
            MinecraftServer.access$000().info(c + i + packedTextFragments[0]);
        }
    }

    public void displaySavingString(String text) {}

    // region String decoding support

    // endregion

    public void displayLoadingString(String text) {}

    static {
        net.minecraft.server.WorldConversionProgressLogger.packedTextFragments = new String[2];
        net.minecraft.server.WorldConversionProgressLogger.packedTextFragments[0] = new String("%");
        net.minecraft.server.WorldConversionProgressLogger.packedTextFragments[1] =
                new String("YEg\u0007(^\u0005sDn_c\u0002Q");
        c = "Converting... ";
    }

    public void setDoneWorking() {}

    // region String decoding support

    // endregion

    public void resetProgressAndMessage(String text) {}

    // region String decoding support

    // endregion

    WorldConversionProgressLogger(MinecraftServer byPValue) {
        this.field_74267_a = byPValue;
        this.startTime = System.currentTimeMillis();
    }
}
