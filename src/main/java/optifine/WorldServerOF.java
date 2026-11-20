package optifine;

import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class WorldServerOF extends WorldServer {
    private MinecraftServer mcServer;

    public WorldServerOF(
            MinecraftServer minecraftServer,
            ISaveHandler iSaveHandler,
            WorldInfo worldInfoValue,
            int i,
            Profiler profiler) {
        super(minecraftServer, iSaveHandler, worldInfoValue, i, profiler);
        this.mcServer = minecraftServer;
    }

    protected void updateWeather() {
        if (!optifine.OptiFineConfig.isWeatherEnabled()) {
            this.fixWorldWeather();
        }

        super.updateWeather();
    }

    private void fixWorldWeather() {
        if (this.worldInfo.isRaining() || this.worldInfo.isThundering()) {
            this.worldInfo.setRainTime(0);
            this.worldInfo.setRaining(false);
            this.setRainStrength(0.0F);
            this.worldInfo.setThunderTime(0);
            this.worldInfo.setThundering(false);
            this.setThunderStrength(0.0F);
            this.mcServer.getConfigurationManager()
                    .sendPacketToAllPlayers(new S2BPacketChangeGameState(2, 0.0F));
            this.mcServer.getConfigurationManager()
                    .sendPacketToAllPlayers(new S2BPacketChangeGameState(7, 0.0F));
            this.mcServer.getConfigurationManager()
                    .sendPacketToAllPlayers(new S2BPacketChangeGameState(8, 0.0F));
        }
    }

    private void fixWorldTime() {
        if (this.worldInfo.getGameType().getID() == 1) {
            long worldTime = this.getWorldTime();

            long j = worldTime % 24000L;
            if (OptiFineConfig.isTimeDayOnly()) {

                if (j <= 1000L) {
                    long i2 = worldTime - j;

                    this.setWorldTime(i2 + 1001L);
                }

                if (j >= 11000L) {
                    long k = worldTime - j;

                    this.setWorldTime(k + 24001L);
                }
            }

            if (OptiFineConfig.isTimeNightOnly()) {

                if (j <= 14000L) {
                    long l = worldTime - j;

                    this.setWorldTime(l + 14001L);
                }

                if (j >= 22000L) {
                    long i1 = worldTime - j;

                    i1 += 24000L;

                    this.setWorldTime(i1 + 14001L);
                }
            }
        }
    }

    public void tick() {
        super.tick();
        if (!optifine.OptiFineConfig.isTimeDefault()) {
            this.fixWorldTime();
        }

        if (optifine.OptiFineConfig.waterOpacityChanged) {
            optifine.OptiFineConfig.waterOpacityChanged = false;
            optifine.ClearWater.updateWaterOpacity(optifine.OptiFineConfig.getGameSettings(), this);
        }
    }
}
