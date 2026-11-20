package net.minecraft.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderDebug;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.FlatGeneratorInfo;

public abstract class WorldProvider {
    private String generatorSettings;
    private float[] colorsSunriseSunset;
    protected int dimensionId;
    protected boolean hasNoSky;
    private WorldType terrainType;
    protected boolean isHellWorld;
    protected World worldObj;
    protected WorldChunkManager worldChunkMgr;
    public static final float[] moonPhaseFactors =
            new float[] {1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
    protected final float[] lightBrightnessTable = new float[16];

    public boolean doesXZShowFog(int i, int j) {
        return false;
    }

    public double getVoidFogYFactor() {
        return this.terrainType == WorldType.FLAT ? 1.0 : 0.03125;
    }

    public WorldProvider() {
        this.colorsSunriseSunset = new float[4];
    }

    public int getMoonPhase(long i) {

        long j = i / 24000L;

        j %= 8L;

        return (int) (j + 8L) % 8;
    }

    public float getCloudHeight() {
        return 128.0F;
    }

    public IChunkProvider createChunkGenerator() {
        return (IChunkProvider)
                (this.terrainType == WorldType.FLAT
                        ? new ChunkProviderFlat(
                                this.worldObj,
                                this.worldObj.getSeed(),
                                this.worldObj.getWorldInfo().isMapFeaturesEnabled(),
                                this.generatorSettings)
                        : (this.terrainType == WorldType.DEBUG_WORLD
                                ? new ChunkProviderDebug(this.worldObj)
                                : (this.terrainType == WorldType.CUSTOMIZED
                                        ? new ChunkProviderGenerate(
                                                this.worldObj,
                                                this.worldObj.getSeed(),
                                                this.worldObj.getWorldInfo().isMapFeaturesEnabled(),
                                                this.generatorSettings)
                                        : new ChunkProviderGenerate(
                                                this.worldObj,
                                                this.worldObj.getSeed(),
                                                this.worldObj.getWorldInfo().isMapFeaturesEnabled(),
                                                this.generatorSettings))));
    }

    public boolean canCoordinateBeSpawn(int i, int j) {
        return this.worldObj.getGroundAboveSeaLevel(new BlockPos(i, 0, j)) == Blocks.grass;
    }

    public float[] getLightBrightnessTable() {
        return this.lightBrightnessTable;
    }

    public boolean isSkyColored() {
        return true;
    }

    public abstract String getInternalNameSuffix();

    public float calculateCelestialAngle(long i, float f) {

        int j = (int) (i % 24000L);
        float f1 = (j + f) / 24000.0F - 0.25F;
        if (f1 < 0.0F) {
            f1++;
        }

        if (f1 > 1.0F) {
            f1--;
        }

        float f2 = f1;
        f1 = 1.0F - (float) ((Math.cos(f1 * Math.PI) + 1.0) / 2.0);
        return f2 + (f1 - f2) / 3.0F;
    }

    public int getDimensionId() {
        return this.dimensionId;
    }

    public BlockPos getSpawnCoordinate() {
        return null;
    }

    public abstract String getDimensionName();

    protected void generateLightBrightnessTable() {
        float fx = 0.0F;
        World.getLegacyWorldFlowMarker();

        int i = 0;

        while (i <= 15) {
            float f1 = 1.0F - i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - fx) + fx;
            i++;
        }
    }

    public float[] calcSunriseSunsetColors(float f, float f6) {
        float f1 = 0.4F;
        float f2 = MathHelper.cos(f * (float) Math.PI * 2.0F) - 0.0F;
        float f3 = -0.0F;
        if (f2 >= f3 - f1 && f2 <= f3 + f1) {
            float f4 = (f2 - f3) / f1 * 0.5F + 0.5F;
            float f5 = 1.0F - (1.0F - MathHelper.sin(f4 * (float) Math.PI)) * 0.99F;
            f5 *= f5;
            this.colorsSunriseSunset[0] = f4 * 0.3F + 0.7F;
            this.colorsSunriseSunset[1] = f4 * f4 * 0.7F + 0.2F;
            this.colorsSunriseSunset[2] = f4 * f4 * 0.0F + 0.2F;
            this.colorsSunriseSunset[3] = f5;
            return this.colorsSunriseSunset;
        } else {
            return null;
        }
    }

    public int getAverageGroundLevel() {
        return this.terrainType == WorldType.FLAT ? 4 : 64;
    }

    public boolean getHasNoSky() {
        return this.hasNoSky;
    }

    public static WorldProvider getProviderForDimension(int i) {
        return (WorldProvider)
                (i == -1
                        ? new WorldProviderHell()
                        : (i == 0
                                ? new WorldProviderSurface()
                                : (i == 1 ? new WorldProviderEnd() : null)));
    }

    public boolean canRespawnHere() {
        return true;
    }

    public boolean doesWaterVaporize() {
        return this.isHellWorld;
    }

    public boolean isSurfaceWorld() {
        return true;
    }

    public final void registerWorld(World awfValue) {
        this.worldObj = awfValue;
        this.terrainType = awfValue.getWorldInfo().getTerrainType();
        this.generatorSettings = awfValue.getWorldInfo().getGeneratorOptions();
        this.registerWorldChunkManager();
        this.generateLightBrightnessTable();
    }

    public WorldBorder getWorldBorder() {
        return new WorldBorder();
    }

    protected void registerWorldChunkManager() {
        World.getLegacyWorldFlowMarker();

        WorldType terrainTypeValue = this.worldObj.getWorldInfo().getTerrainType();

        if (terrainTypeValue == WorldType.FLAT) {
            FlatGeneratorInfo flatGeneratorFromString =
                    FlatGeneratorInfo.createFlatGeneratorFromString(
                            this.worldObj.getWorldInfo().getGeneratorOptions());
            this.worldChunkMgr =
                    new WorldChunkManagerHell(
                            BiomeGenBase.getBiomeFromBiomeList(
                                    flatGeneratorFromString.getBiome(),
                                    BiomeGenBase.field_180279_ad),
                            0.5F);

            return;
        }

        if (terrainTypeValue == WorldType.DEBUG_WORLD) {
            this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.plains, 0.0F);

            return;
        }

        this.worldChunkMgr = new WorldChunkManager(this.worldObj);
    }

    public WorldChunkManager getWorldChunkManager() {
        return this.worldChunkMgr;
    }

    public Vec3 getFogColor(float f, float f5) {
        float f1 = MathHelper.cos(f * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        float f2 = 0.7529412F;
        float f3 = 0.84705883F;
        float f4 = 1.0F;
        f2 *= f1 * 0.94F + 0.06F;
        f3 *= f1 * 0.94F + 0.06F;
        f4 *= f1 * 0.91F + 0.09F;
        return new Vec3(f2, f3, f4);
    }
}
