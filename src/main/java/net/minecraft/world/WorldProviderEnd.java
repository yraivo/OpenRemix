package net.minecraft.world;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;

public class WorldProviderEnd extends WorldProvider {

    public boolean doesXZShowFog(int i, int j) {
        return true;
    }

    public boolean canRespawnHere() {
        return false;
    }

    public String getInternalNameSuffix() {
        return "_end";
    }

    public boolean canCoordinateBeSpawn(int i, int j) {
        return this.worldObj
                .getGroundAboveSeaLevel(new BlockPos(i, 0, j))
                .getMaterial()
                .blocksMovement();
    }

    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F);
        this.dimensionId = 1;
        this.hasNoSky = true;
    }

    public float[] calcSunriseSunsetColors(float f, float f1) {
        return null;
    }

    public float calculateCelestialAngle(long i, float f) {
        return 0.0F;
    }

    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
    }

    public float getCloudHeight() {
        return 8.0F;
    }

    public boolean isSkyColored() {
        return false;
    }

    public String getDimensionName() {
        return "The End";
    }

    public BlockPos getSpawnCoordinate() {
        return new BlockPos(100, 50, 0);
    }

    public Vec3 getFogColor(float f, float f5) {
        int i = 10518688;
        float f1 = MathHelper.cos(f * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        float f2 = (i >> 16 & 0xFF) / 255.0F;
        float f3 = (i >> 8 & 0xFF) / 255.0F;
        float f4 = (i & 0xFF) / 255.0F;
        f2 *= f1 * 0.0F + 0.15F;
        f3 *= f1 * 0.0F + 0.15F;
        f4 *= f1 * 0.0F + 0.15F;
        return new Vec3(f2, f3, f4);
    }


    public boolean isSurfaceWorld() {
        return false;
    }

    public int getAverageGroundLevel() {
        return 50;
    }
}
