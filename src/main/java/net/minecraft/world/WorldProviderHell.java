package net.minecraft.world;

import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;

public class WorldProviderHell extends WorldProvider {

    public float calculateCelestialAngle(long i, float f) {
        return 0.5F;
    }

    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F);
        this.isHellWorld = true;
        this.hasNoSky = true;
        this.dimensionId = -1;
    }

    public String getDimensionName() {
        return "Nether";
    }

    public String getInternalNameSuffix() {
        return "_nether";
    }

    public boolean isSurfaceWorld() {
        return false;
    }

    public boolean canRespawnHere() {
        return false;
    }

    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderHell(
                this.worldObj,
                this.worldObj.getWorldInfo().isMapFeaturesEnabled(),
                this.worldObj.getSeed());
    }

    protected void generateLightBrightnessTable() {
        World.getLegacyWorldFlowMarker();

        float f = 0.1F;

        int i = 0;

        while (i <= 15) {
            float f1 = 1.0F - i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
            i++;
        }
    }

    public WorldBorder getWorldBorder() {
        return new NetherWorldBorder(this);
    }

    public boolean canCoordinateBeSpawn(int i, int j) {
        return false;
    }

    public Vec3 getFogColor(float f, float f1) {
        return new Vec3(0.2F, 0.03F, 0.03F);
    }


    public boolean doesXZShowFog(int i, int j) {
        return true;
    }
}
