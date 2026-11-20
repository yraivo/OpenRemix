package net.minecraft.world.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ChunkProviderSettingsFactory {
    public boolean useWaterLakes;
    public int diamondCount;
    public int lapisSize;
    public int andesiteMaxHeight;
    public int dioriteCount;
    public int gravelCount;
    public int dioriteMinHeight;
    public int ironMinHeight;
    public int andesiteCount;
    public float depthNoiseScaleExponent;
    public int diamondSize;
    public int gravelMaxHeight;
    public float coordinateScale = 684.412F;
    public int redstoneMinHeight;
    public float lowerLimitScale;
    public boolean useRavines;
    public int dirtCount;
    public float mainNoiseScaleX;
    public int goldSize;
    public float baseSize;
    public int ironSize;
    public int redstoneSize;
    public int ironMaxHeight;
    public int biomeSize;
    public boolean useDungeons;
    public int gravelMinHeight;
    public float heightScale = 684.412F;
    public float depthNoiseScaleZ;
    public int goldMinHeight;
    public int graniteMaxHeight;
    public int redstoneMaxHeight;
    public int lavaLakeChance;
    public boolean useVillages;
    public int lapisSpread;
    public int graniteSize;
    public float biomeDepthOffset;
    public int diamondMaxHeight;
    public int gravelSize;
    public int diamondMinHeight;
    public boolean useMineShafts;
    public float upperLimitScale = 512.0F;
    public float biomeDepthWeight;
    public int dioriteSize;
    public int andesiteMinHeight;
    public int coalCount;
    public int dirtSize;
    public boolean useCaves;
    public boolean useStrongholds;
    public int coalMaxHeight;
    public boolean useLavaOceans;
    public int waterLakeChance;
    public int dungeonChance;
    public float biomeScaleWeight;
    public int redstoneCount;
    public int goldMaxHeight;
    public float biomeScaleOffset;
    public int andesiteSize;
    public boolean useMonuments;
    public int lapisCenterHeight;
    public float depthNoiseScaleX;
    public boolean useLavaLakes;
    public int coalSize;
    public float mainNoiseScaleZ;
    public int lapisCount;
    public float stretchY;
    public int graniteCount;
    public int graniteMinHeight;
    public int goldCount;
    public int ironCount;
    public int dirtMinHeight;
    public int riverSize;
    public int dirtMaxHeight;
    public boolean useTemples;
    public int coalMinHeight;
    public int fixedBiome;
    static final Gson JSON_ADAPTER =
            new GsonBuilder()
                    .registerTypeAdapter(
                            ChunkProviderSettingsFactory.class,
                            new ChunkProviderSettingsSerializer())
                    .create();
    public float mainNoiseScaleY;
    public int dioriteMaxHeight;
    public int seaLevel;

    public void func_177863_a() {
        this.coordinateScale = 684.412F;
        this.heightScale = 684.412F;
        this.upperLimitScale = 512.0F;
        this.lowerLimitScale = 512.0F;
        this.depthNoiseScaleX = 200.0F;
        this.depthNoiseScaleZ = 200.0F;
        this.depthNoiseScaleExponent = 0.5F;
        this.mainNoiseScaleX = 80.0F;
        this.mainNoiseScaleY = 160.0F;
        this.mainNoiseScaleZ = 80.0F;
        this.baseSize = 8.5F;
        this.stretchY = 12.0F;
        this.biomeDepthWeight = 1.0F;
        this.biomeDepthOffset = 0.0F;
        this.biomeScaleWeight = 1.0F;
        this.biomeScaleOffset = 0.0F;
        this.seaLevel = 63;
        this.useCaves = true;
        this.useDungeons = true;
        this.dungeonChance = 8;
        this.useStrongholds = true;
        this.useVillages = true;
        this.useMineShafts = true;
        this.useTemples = true;
        this.useMonuments = true;
        this.useRavines = true;
        this.useWaterLakes = true;
        this.waterLakeChance = 4;
        this.useLavaLakes = true;
        this.lavaLakeChance = 80;
        this.useLavaOceans = false;
        this.fixedBiome = -1;
        this.biomeSize = 4;
        this.riverSize = 4;
        this.dirtSize = 33;
        this.dirtCount = 10;
        this.dirtMinHeight = 0;
        this.dirtMaxHeight = 256;
        this.gravelSize = 33;
        this.gravelCount = 8;
        this.gravelMinHeight = 0;
        this.gravelMaxHeight = 256;
        this.graniteSize = 33;
        this.graniteCount = 10;
        this.graniteMinHeight = 0;
        this.graniteMaxHeight = 80;
        this.dioriteSize = 33;
        this.dioriteCount = 10;
        this.dioriteMinHeight = 0;
        this.dioriteMaxHeight = 80;
        this.andesiteSize = 33;
        this.andesiteCount = 10;
        this.andesiteMinHeight = 0;
        this.andesiteMaxHeight = 80;
        this.coalSize = 17;
        this.coalCount = 20;
        this.coalMinHeight = 0;
        this.coalMaxHeight = 128;
        this.ironSize = 9;
        this.ironCount = 20;
        this.ironMinHeight = 0;
        this.ironMaxHeight = 64;
        this.goldSize = 9;
        this.goldCount = 2;
        this.goldMinHeight = 0;
        this.goldMaxHeight = 32;
        this.redstoneSize = 8;
        this.redstoneCount = 8;
        this.redstoneMinHeight = 0;
        this.redstoneMaxHeight = 16;
        this.diamondSize = 8;
        this.diamondCount = 1;
        this.diamondMinHeight = 0;
        this.diamondMaxHeight = 16;
        this.lapisSize = 7;
        this.lapisCount = 1;
        this.lapisCenterHeight = 16;
        this.lapisSpread = 16;
    }

    @Override
    public boolean equals(Object p171_0) {
        if (this == p171_0) {
            return true;
        } else if (p171_0 != null && this.getClass() == p171_0.getClass()) {
            net.minecraft.world.gen.ChunkProviderSettingsFactory chunkProviderSettingsFactory =
                    (net.minecraft.world.gen.ChunkProviderSettingsFactory) p171_0;
            return this.andesiteCount != chunkProviderSettingsFactory.andesiteCount
                    ? false
                    : (this.andesiteMaxHeight != chunkProviderSettingsFactory.andesiteMaxHeight
                            ? false
                            : (this.andesiteMinHeight
                                            != chunkProviderSettingsFactory.andesiteMinHeight
                                    ? false
                                    : (this.andesiteSize
                                                    != chunkProviderSettingsFactory.andesiteSize
                                            ? false
                                            : (java.lang.Float.compare(
                                                                    chunkProviderSettingsFactory
                                                                            .baseSize,
                                                                    this.baseSize)
                                                            != 0
                                                    ? false
                                                    : (java.lang.Float.compare(
                                                                            chunkProviderSettingsFactory
                                                                                    .biomeDepthOffset,
                                                                            this.biomeDepthOffset)
                                                                    != 0
                                                            ? false
                                                            : (java.lang.Float.compare(
                                                                                    chunkProviderSettingsFactory
                                                                                            .biomeDepthWeight,
                                                                                    this
                                                                                            .biomeDepthWeight)
                                                                            != 0
                                                                    ? false
                                                                    : (java.lang.Float.compare(
                                                                                            chunkProviderSettingsFactory
                                                                                                    .biomeScaleOffset,
                                                                                            this
                                                                                                    .biomeScaleOffset)
                                                                                    != 0
                                                                            ? false
                                                                            : (java.lang.Float
                                                                                                    .compare(
                                                                                                            chunkProviderSettingsFactory
                                                                                                                    .biomeScaleWeight,
                                                                                                            this
                                                                                                                    .biomeScaleWeight)
                                                                                            != 0
                                                                                    ? false
                                                                                    : (this
                                                                                                            .biomeSize
                                                                                                    != chunkProviderSettingsFactory
                                                                                                            .biomeSize
                                                                                            ? false
                                                                                            : (this
                                                                                                                    .coalCount
                                                                                                            != chunkProviderSettingsFactory
                                                                                                                    .coalCount
                                                                                                    ? false
                                                                                                    : (this
                                                                                                                            .coalMaxHeight
                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                            .coalMaxHeight
                                                                                                            ? false
                                                                                                            : (this
                                                                                                                                    .coalMinHeight
                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                    .coalMinHeight
                                                                                                                    ? false
                                                                                                                    : (this
                                                                                                                                            .coalSize
                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                            .coalSize
                                                                                                                            ? false
                                                                                                                            : (java
                                                                                                                                                    .lang
                                                                                                                                                    .Float
                                                                                                                                                    .compare(
                                                                                                                                                            chunkProviderSettingsFactory
                                                                                                                                                                    .coordinateScale,
                                                                                                                                                            this
                                                                                                                                                                    .coordinateScale)
                                                                                                                                            != 0
                                                                                                                                    ? false
                                                                                                                                    : (java
                                                                                                                                                            .lang
                                                                                                                                                            .Float
                                                                                                                                                            .compare(
                                                                                                                                                                    chunkProviderSettingsFactory
                                                                                                                                                                            .depthNoiseScaleExponent,
                                                                                                                                                                    this
                                                                                                                                                                            .depthNoiseScaleExponent)
                                                                                                                                                    != 0
                                                                                                                                            ? false
                                                                                                                                            : (java
                                                                                                                                                                    .lang
                                                                                                                                                                    .Float
                                                                                                                                                                    .compare(
                                                                                                                                                                            chunkProviderSettingsFactory
                                                                                                                                                                                    .depthNoiseScaleX,
                                                                                                                                                                            this
                                                                                                                                                                                    .depthNoiseScaleX)
                                                                                                                                                            != 0
                                                                                                                                                    ? false
                                                                                                                                                    : (java
                                                                                                                                                                            .lang
                                                                                                                                                                            .Float
                                                                                                                                                                            .compare(
                                                                                                                                                                                    chunkProviderSettingsFactory
                                                                                                                                                                                            .depthNoiseScaleZ,
                                                                                                                                                                                    this
                                                                                                                                                                                            .depthNoiseScaleZ)
                                                                                                                                                                    != 0
                                                                                                                                                            ? false
                                                                                                                                                            : (this
                                                                                                                                                                                    .diamondCount
                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                    .diamondCount
                                                                                                                                                                    ? false
                                                                                                                                                                    : (this
                                                                                                                                                                                            .diamondMaxHeight
                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                            .diamondMaxHeight
                                                                                                                                                                            ? false
                                                                                                                                                                            : (this
                                                                                                                                                                                                    .diamondMinHeight
                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                    .diamondMinHeight
                                                                                                                                                                                    ? false
                                                                                                                                                                                    : (this
                                                                                                                                                                                                            .diamondSize
                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                            .diamondSize
                                                                                                                                                                                            ? false
                                                                                                                                                                                            : (this
                                                                                                                                                                                                                    .dioriteCount
                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                    .dioriteCount
                                                                                                                                                                                                    ? false
                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                            .dioriteMaxHeight
                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                            .dioriteMaxHeight
                                                                                                                                                                                                            ? false
                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                    .dioriteMinHeight
                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                    .dioriteMinHeight
                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                            .dioriteSize
                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                            .dioriteSize
                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                    .dirtCount
                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                    .dirtCount
                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                            .dirtMaxHeight
                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                            .dirtMaxHeight
                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                    .dirtMinHeight
                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                    .dirtMinHeight
                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                            .dirtSize
                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                            .dirtSize
                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                    .dungeonChance
                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                    .dungeonChance
                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                            .fixedBiome
                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                            .fixedBiome
                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                    .goldCount
                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                    .goldCount
                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                            .goldMaxHeight
                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                            .goldMaxHeight
                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                    .goldMinHeight
                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                    .goldMinHeight
                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                            .goldSize
                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                            .goldSize
                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                    .graniteCount
                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                    .graniteCount
                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                            .graniteMaxHeight
                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                            .graniteMaxHeight
                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                    .graniteMinHeight
                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                    .graniteMinHeight
                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                            .graniteSize
                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                            .graniteSize
                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                    .gravelCount
                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                    .gravelCount
                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                            .gravelMaxHeight
                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                            .gravelMaxHeight
                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                    .gravelMinHeight
                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                    .gravelMinHeight
                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                            .gravelSize
                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                            .gravelSize
                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                            : (java
                                                                                                                                                                                                                                                                                                                                                                                                    .lang
                                                                                                                                                                                                                                                                                                                                                                                                    .Float
                                                                                                                                                                                                                                                                                                                                                                                                    .compare(
                                                                                                                                                                                                                                                                                                                                                                                                            chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                    .heightScale,
                                                                                                                                                                                                                                                                                                                                                                                                            this
                                                                                                                                                                                                                                                                                                                                                                                                                    .heightScale)
                                                                                                                                                                                                                                                                                                                                                                                            != 0
                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                            .ironCount
                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                            .ironCount
                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                    .ironMaxHeight
                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                    .ironMaxHeight
                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                                            .ironMinHeight
                                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                            .ironMinHeight
                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                    .ironSize
                                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                    .ironSize
                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                            .lapisCenterHeight
                                                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                            .lapisCenterHeight
                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                    .lapisCount
                                                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                    .lapisCount
                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                            .lapisSize
                                                                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                            .lapisSize
                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .lapisSpread
                                                                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .lapisSpread
                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .lavaLakeChance
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .lavaLakeChance
                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                            : (java
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .lang
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .Float
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .compare(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .lowerLimitScale,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .lowerLimitScale)
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            != 0
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : (java
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .lang
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .Float
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .compare(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .mainNoiseScaleX,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .mainNoiseScaleX)
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    != 0
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            : (java
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .lang
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .Float
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .compare(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .mainNoiseScaleY,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .mainNoiseScaleY)
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            != 0
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : (java
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .lang
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .Float
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .compare(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .mainNoiseScaleZ,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .mainNoiseScaleZ)
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    != 0
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .redstoneCount
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .redstoneCount
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .redstoneMaxHeight
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .redstoneMaxHeight
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .redstoneMinHeight
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .redstoneMinHeight
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .redstoneSize
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .redstoneSize
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .riverSize
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .riverSize
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .seaLevel
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .seaLevel
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            : (java
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .lang
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .Float
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .compare(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .stretchY,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .stretchY)
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            != 0
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : (java
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .lang
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .Float
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .compare(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .upperLimitScale,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .upperLimitScale)
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    != 0
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .useCaves
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .useCaves
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .useDungeons
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .useDungeons
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .useLavaLakes
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .useLavaLakes
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .useLavaOceans
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .useLavaOceans
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .useMineShafts
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .useMineShafts
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .useRavines
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .useRavines
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .useStrongholds
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .useStrongholds
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .useTemples
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .useTemples
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .useMonuments
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .useMonuments
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .useVillages
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .useVillages
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            : (this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .useWaterLakes
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            != chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .useWaterLakes
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? false
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : this
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .waterLakeChance
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            == chunkProviderSettingsFactory
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    .waterLakeChance))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))));
        } else {
            return false;
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    @Override
    public int hashCode() {
        MapGenBase.b();

        int i = this.coordinateScale != 0.0F ? Float.floatToIntBits(this.coordinateScale) : 0;
        i = 31 * i + (this.heightScale != 0.0F ? Float.floatToIntBits(this.heightScale) : 0);
        i =
                31 * i
                        + (this.upperLimitScale != 0.0F
                                ? Float.floatToIntBits(this.upperLimitScale)
                                : 0);
        i =
                31 * i
                        + (this.lowerLimitScale != 0.0F
                                ? Float.floatToIntBits(this.lowerLimitScale)
                                : 0);
        i =
                31 * i
                        + (this.depthNoiseScaleX != 0.0F
                                ? Float.floatToIntBits(this.depthNoiseScaleX)
                                : 0);
        i =
                31 * i
                        + (this.depthNoiseScaleZ != 0.0F
                                ? Float.floatToIntBits(this.depthNoiseScaleZ)
                                : 0);
        i =
                31 * i
                        + (this.depthNoiseScaleExponent != 0.0F
                                ? Float.floatToIntBits(this.depthNoiseScaleExponent)
                                : 0);
        i =
                31 * i
                        + (this.mainNoiseScaleX != 0.0F
                                ? Float.floatToIntBits(this.mainNoiseScaleX)
                                : 0);
        i =
                31 * i
                        + (this.mainNoiseScaleY != 0.0F
                                ? Float.floatToIntBits(this.mainNoiseScaleY)
                                : 0);
        i =
                31 * i
                        + (this.mainNoiseScaleZ != 0.0F
                                ? Float.floatToIntBits(this.mainNoiseScaleZ)
                                : 0);
        i = 31 * i + (this.baseSize != 0.0F ? Float.floatToIntBits(this.baseSize) : 0);
        i = 31 * i + (this.stretchY != 0.0F ? Float.floatToIntBits(this.stretchY) : 0);
        i =
                31 * i
                        + (this.biomeDepthWeight != 0.0F
                                ? Float.floatToIntBits(this.biomeDepthWeight)
                                : 0);
        i =
                31 * i
                        + (this.biomeDepthOffset != 0.0F
                                ? Float.floatToIntBits(this.biomeDepthOffset)
                                : 0);
        i =
                31 * i
                        + (this.biomeScaleWeight != 0.0F
                                ? Float.floatToIntBits(this.biomeScaleWeight)
                                : 0);
        i =
                31 * i
                        + (this.biomeScaleOffset != 0.0F
                                ? Float.floatToIntBits(this.biomeScaleOffset)
                                : 0);
        i = 31 * i + this.seaLevel;
        i = 31 * i + (this.useCaves ? 1 : 0);
        i = 31 * i + (this.useDungeons ? 1 : 0);
        i = 31 * i + this.dungeonChance;
        i = 31 * i + (this.useStrongholds ? 1 : 0);
        i = 31 * i + (this.useVillages ? 1 : 0);
        i = 31 * i + (this.useMineShafts ? 1 : 0);
        i = 31 * i + (this.useTemples ? 1 : 0);
        i = 31 * i + (this.useMonuments ? 1 : 0);
        i = 31 * i + (this.useRavines ? 1 : 0);
        i = 31 * i + (this.useWaterLakes ? 1 : 0);
        i = 31 * i + this.waterLakeChance;
        i = 31 * i + (this.useLavaLakes ? 1 : 0);
        i = 31 * i + this.lavaLakeChance;
        i = 31 * i + (this.useLavaOceans ? 1 : 0);
        i = 31 * i + this.fixedBiome;
        i = 31 * i + this.biomeSize;
        i = 31 * i + this.riverSize;
        i = 31 * i + this.dirtSize;
        i = 31 * i + this.dirtCount;
        i = 31 * i + this.dirtMinHeight;
        i = 31 * i + this.dirtMaxHeight;
        i = 31 * i + this.gravelSize;
        i = 31 * i + this.gravelCount;
        i = 31 * i + this.gravelMinHeight;
        i = 31 * i + this.gravelMaxHeight;
        i = 31 * i + this.graniteSize;
        i = 31 * i + this.graniteCount;
        i = 31 * i + this.graniteMinHeight;
        i = 31 * i + this.graniteMaxHeight;
        i = 31 * i + this.dioriteSize;
        i = 31 * i + this.dioriteCount;
        i = 31 * i + this.dioriteMinHeight;
        i = 31 * i + this.dioriteMaxHeight;
        i = 31 * i + this.andesiteSize;
        i = 31 * i + this.andesiteCount;
        i = 31 * i + this.andesiteMinHeight;
        i = 31 * i + this.andesiteMaxHeight;
        i = 31 * i + this.coalSize;
        i = 31 * i + this.coalCount;
        i = 31 * i + this.coalMinHeight;
        i = 31 * i + this.coalMaxHeight;
        i = 31 * i + this.ironSize;
        i = 31 * i + this.ironCount;
        i = 31 * i + this.ironMinHeight;
        i = 31 * i + this.ironMaxHeight;
        i = 31 * i + this.goldSize;
        i = 31 * i + this.goldCount;
        i = 31 * i + this.goldMinHeight;
        i = 31 * i + this.goldMaxHeight;
        i = 31 * i + this.redstoneSize;
        i = 31 * i + this.redstoneCount;
        i = 31 * i + this.redstoneMinHeight;
        i = 31 * i + this.redstoneMaxHeight;
        i = 31 * i + this.diamondSize;
        i = 31 * i + this.diamondCount;
        i = 31 * i + this.diamondMinHeight;
        i = 31 * i + this.diamondMaxHeight;
        i = 31 * i + this.lapisSize;
        i = 31 * i + this.lapisCount;
        i = 31 * i + this.lapisCenterHeight;
        i = 31 * i + this.lapisSpread;

        return i;
    }

    public ChunkProviderSettings WS() {
        return new net.minecraft.world.gen.ChunkProviderSettings(this, null);
    }

    public static ChunkProviderSettingsFactory jsonToFactory(String text) {
        try {
            if (text.length() == 0) {
                return new ChunkProviderSettingsFactory();
            }
        } catch (Exception exception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception1));
        }

        try {
            return (ChunkProviderSettingsFactory)
                    net.minecraft.world.gen.ChunkProviderSettingsFactory.JSON_ADAPTER.fromJson(
                            text, ChunkProviderSettingsFactory.class);
        } catch (Exception exception) {
            return new ChunkProviderSettingsFactory();
        }
    }

    @Override
    public String toString() {
        return net.minecraft.world.gen.ChunkProviderSettingsFactory.JSON_ADAPTER.toJson(this);
    }

    public ChunkProviderSettingsFactory() {
        this.lowerLimitScale = 512.0f;
        this.depthNoiseScaleX = 200.0f;
        this.depthNoiseScaleZ = 200.0f;
        this.depthNoiseScaleExponent = 0.5f;
        this.mainNoiseScaleX = 80.0f;
        this.mainNoiseScaleY = 160.0f;
        this.mainNoiseScaleZ = 80.0f;
        this.baseSize = 8.5f;
        this.stretchY = 12.0f;
        this.biomeDepthWeight = 1.0f;
        this.biomeDepthOffset = 0.0f;
        this.biomeScaleWeight = 1.0f;
        this.biomeScaleOffset = 0.0f;
        this.seaLevel = 63;
        this.useCaves = true;
        this.useDungeons = true;
        this.dungeonChance = 8;
        this.useStrongholds = true;
        this.useVillages = true;
        this.useMineShafts = true;
        this.useTemples = true;
        this.useMonuments = true;
        this.useRavines = true;
        this.useWaterLakes = true;
        this.waterLakeChance = 4;
        this.useLavaLakes = true;
        this.lavaLakeChance = 80;
        this.useLavaOceans = false;
        this.fixedBiome = -1;
        this.biomeSize = 4;
        this.riverSize = 4;
        this.dirtSize = 33;
        this.dirtCount = 10;
        this.dirtMinHeight = 0;
        this.dirtMaxHeight = 256;
        this.gravelSize = 33;
        this.gravelCount = 8;
        this.gravelMinHeight = 0;
        this.gravelMaxHeight = 256;
        this.graniteSize = 33;
        this.graniteCount = 10;
        this.graniteMinHeight = 0;
        this.graniteMaxHeight = 80;
        this.dioriteSize = 33;
        this.dioriteCount = 10;
        this.dioriteMinHeight = 0;
        this.dioriteMaxHeight = 80;
        this.andesiteSize = 33;
        this.andesiteCount = 10;
        this.andesiteMinHeight = 0;
        this.andesiteMaxHeight = 80;
        this.coalSize = 17;
        this.coalCount = 20;
        this.coalMinHeight = 0;
        this.coalMaxHeight = 128;
        this.ironSize = 9;
        this.ironCount = 20;
        this.ironMinHeight = 0;
        this.ironMaxHeight = 64;
        this.goldSize = 9;
        this.goldCount = 2;
        this.goldMinHeight = 0;
        this.goldMaxHeight = 32;
        this.redstoneSize = 8;
        this.redstoneCount = 8;
        this.redstoneMinHeight = 0;
        this.redstoneMaxHeight = 16;
        this.diamondSize = 8;
        this.diamondCount = 1;
        this.diamondMinHeight = 0;
        this.diamondMaxHeight = 16;
        this.lapisSize = 7;
        this.lapisCount = 1;
        this.lapisCenterHeight = 16;
        this.lapisSpread = 16;
        this.func_177863_a();
    }
}
