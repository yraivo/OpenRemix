package net.minecraft.world.gen.layer;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.ChunkProviderSettingsFactory;
import net.minecraft.world.gen.layer.GenLayerBiomeNameCallable1;
import net.minecraft.world.gen.layer.GenLayerBiomeNameCallable2;

public abstract class GenLayer {
    protected long baseSeed;
    protected GenLayer parent;

    private static Entity[] dL;

    private long chunkSeed;
    private long worldGenSeed;

    public void initWorldGenSeed(long i) {
        this.worldGenSeed = i;
        if (this.parent != null) {
            this.parent.initWorldGenSeed(i);
        }

        long j = this.worldGenSeed * 6364136223846793005L;

        this.worldGenSeed *= j + 1442695040888963407L;
        this.worldGenSeed = this.worldGenSeed + this.baseSeed;

        j = this.worldGenSeed * 6364136223846793005L;

        this.worldGenSeed *= j + 1442695040888963407L;
        this.worldGenSeed = this.worldGenSeed + this.baseSeed;

        j = this.worldGenSeed * 6364136223846793005L;

        this.worldGenSeed *= j + 1442695040888963407L;
        this.worldGenSeed = this.worldGenSeed + this.baseSeed;
    }

    public void method_91c6fd2b8b1ce492fa7c(long i, long j) {
        this.chunkSeed = this.worldGenSeed;

        long k = this.chunkSeed * 6364136223846793005L;

        this.chunkSeed *= k + 1442695040888963407L;
        this.chunkSeed += i;

        k = this.chunkSeed * 6364136223846793005L;

        this.chunkSeed *= k + 1442695040888963407L;
        this.chunkSeed += j;

        k = this.chunkSeed * 6364136223846793005L;

        this.chunkSeed *= k + 1442695040888963407L;
        this.chunkSeed += i;

        k = this.chunkSeed * 6364136223846793005L;

        this.chunkSeed *= k + 1442695040888963407L;
        this.chunkSeed += j;
    }

    // region String decoding support

    // endregion

    protected static boolean isBiomeOceanic(int i) {
        return i == BiomeGenBase.ocean.biomeID
                || i == BiomeGenBase.deepOcean.biomeID
                || i == BiomeGenBase.frozenOcean.biomeID;
    }

    public static void b(Entity[] entityArray) {
        dL = entityArray;
    }

    // region String decoding support

    // endregion

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return dL;
    }

    static {
        Entity[] aclass_b6df81f333fa31dc98ea = new Entity[3];
        b(aclass_b6df81f333fa31dc98ea);
    }

    public GenLayer(long i) {
        this.baseSeed = i;

        long j = this.baseSeed * 6364136223846793005L;

        this.baseSeed *= j + 1442695040888963407L;
        this.baseSeed += i;

        j = this.baseSeed * 6364136223846793005L;

        this.baseSeed *= j + 1442695040888963407L;
        this.baseSeed += i;

        j = this.baseSeed * 6364136223846793005L;

        this.baseSeed *= j + 1442695040888963407L;
        this.baseSeed += i;
    }

    public static GenLayer[] initializeAllBiomeGenerators(
            long i, WorldType worldType, String text) {

        GenLayerIsland genLayerIsland = new GenLayerIsland(1L);

        GenLayerFuzzyZoom genLayerFuzzyZoom =
                new GenLayerFuzzyZoom(2000L, genLayerIsland);

        GenLayerAddIsland genLayerAddIsland =
                new GenLayerAddIsland(1L, genLayerFuzzyZoom);

        GenLayerZoom genLayerZoom =
                new GenLayerZoom(2001L, genLayerAddIsland);

        genLayerAddIsland = new GenLayerAddIsland(2L, genLayerZoom);

        genLayerAddIsland =
                new GenLayerAddIsland(50L, genLayerAddIsland);

        genLayerAddIsland =
                new GenLayerAddIsland(70L, genLayerAddIsland);

        GenLayerRemoveTooMuchOcean genLayerRemoveTooMuchOcean =
                new GenLayerRemoveTooMuchOcean(2L, genLayerAddIsland);

        GenLayerAddSnow genLayerAddSnow =
                new GenLayerAddSnow(2L, genLayerRemoveTooMuchOcean);

        genLayerAddIsland = new GenLayerAddIsland(3L, genLayerAddSnow);
        method_da2ac5c94686ee11e9e6();

        GenLayerEdge genLayerEdge =
                new GenLayerEdge(2L, genLayerAddIsland, GenLayerEdgeMode.COOL_WARM);

        genLayerEdge = new GenLayerEdge(2L, genLayerEdge, GenLayerEdgeMode.HEAT_ICE);

        genLayerEdge = new GenLayerEdge(3L, genLayerEdge, GenLayerEdgeMode.SPECIAL);

        genLayerZoom = new GenLayerZoom(2002L, genLayerEdge);

        genLayerZoom =
                new GenLayerZoom(2003L, genLayerZoom);

        genLayerAddIsland = new GenLayerAddIsland(4L, genLayerZoom);

        GenLayerAddMushroomIsland genLayerAddMushroomIsland =
                new GenLayerAddMushroomIsland(5L, genLayerAddIsland);

        GenLayerDeepOcean genLayerDeepOcean =
                new GenLayerDeepOcean(4L, genLayerAddMushroomIsland);

        GenLayer genLayer =
                GenLayerZoom.magnify(
                        1000L, genLayerDeepOcean, 0);
        ChunkProviderSettings chunkProviderSettings = null;
        int j = 4;
        int k = j;
        if (worldType == WorldType.CUSTOMIZED && text.length() > 0) {
            chunkProviderSettings = ChunkProviderSettingsFactory.jsonToFactory(text).WS();
            j = chunkProviderSettings.biomeSize;
            k = chunkProviderSettings.riverSize;
        }

        if (worldType == WorldType.LARGE_BIOMES) {
            j = 6;
        }

        GenLayer genLayerValue =
                GenLayerZoom.magnify(1000L, genLayer, 0);

        GenLayerRiverInit genLayerRiverInit =
                new GenLayerRiverInit(100L, genLayerValue);

        GenLayerBiome genLayerBiome =
                new GenLayerBiome(200L, genLayer, worldType, text);

        GenLayer genLayerValue2 =
                GenLayerZoom.magnify(
                        1000L, genLayerBiome, 2);

        GenLayerBiomeEdge genLayerBiomeEdge =
                new GenLayerBiomeEdge(1000L, genLayerValue2);

        GenLayer genLayerValue3 =
                GenLayerZoom.magnify(
                        1000L, genLayerRiverInit, 2);

        GenLayerHills genLayerHills =
                new GenLayerHills(1000L, genLayerBiomeEdge, genLayerValue3);

        genLayerValue =
                GenLayerZoom.magnify(
                        1000L, genLayerRiverInit, 2);

        genLayerValue =
                GenLayerZoom.magnify(1000L, genLayerValue, k);

        GenLayerRiver genLayerRiver =
                new GenLayerRiver(1L, genLayerValue);

        GenLayerSmooth genLayerSmooth =
                new GenLayerSmooth(1000L, genLayerRiver);

        Object object = new GenLayerRareBiome(1001L, genLayerHills);
        int l = 0;

        while (l < j) {
            object = new GenLayerZoom(1000 + l, (GenLayer) object);
            if (l == 0) {

                object = new GenLayerAddIsland(3L, (GenLayer) object);
            }

            if (l == 1 || j == 1) {

                object = new GenLayerShore(1000L, (GenLayer) object);
            }

            l++;
        }

        GenLayerSmooth genLayerSmoothValue =
                new GenLayerSmooth(1000L, (GenLayer) object);

        GenLayerRiverMix genLayerRiverMix =
                new GenLayerRiverMix(
                        100L, genLayerSmoothValue, genLayerSmooth);

        GenLayerVoronoiZoom genLayerVoronoiZoom =
                new GenLayerVoronoiZoom(10L, genLayerRiverMix);
        genLayerRiverMix.initWorldGenSeed(i);
        genLayerVoronoiZoom.initWorldGenSeed(i);
        return new GenLayer[] {
            genLayerRiverMix, genLayerVoronoiZoom, genLayerRiverMix
        };
    }

    protected int nextInt(int i) {
        int j = (int) ((this.chunkSeed >> 24) % i);
        if (j < 0) {
            j += i;
        }

        long k = this.chunkSeed * 6364136223846793005L;

        this.chunkSeed *= k + 1442695040888963407L;
        this.chunkSeed = this.chunkSeed + this.worldGenSeed;
        return j;
    }

    protected int selectRandom(int... aint) {
        return aint[this.nextInt(aint.length)];
    }

    protected static boolean method_3be63dceb97b64e5a50f(int i, int j) {
        if (i == j) {
            return true;
        }

        if (i != BiomeGenBase.mesaPlateau_F.biomeID && i != BiomeGenBase.mesaPlateau.biomeID) {
            BiomeGenBase biomeGenBase = BiomeGenBase.getBiome(i);
            BiomeGenBase biomeGenBaseValue = BiomeGenBase.getBiome(j);

            try {
                return biomeGenBase != null && biomeGenBaseValue != null
                        ? biomeGenBase.isEqualTo(biomeGenBaseValue)
                        : false;
            } catch (Throwable throwable) {
                CrashReport crashReport =
                        CrashReport.makeCrashReport(throwable, "Comparing biomes");
                CrashReportCategory crashReportCategory =
                        crashReport.makeCategory("Biomes being compared");
                crashReportCategory.addCrashSection("Biome A ID", i);
                crashReportCategory.addCrashSection("Biome B ID", j);
                crashReportCategory.addCrashSectionCallable(
                        "Biome A", new GenLayerBiomeNameCallable2(biomeGenBase));
                crashReportCategory.addCrashSectionCallable(
                        "Biome B", new GenLayerBiomeNameCallable1(biomeGenBaseValue));
                throw new ReportedException(crashReport);
            }
        } else {
            return j == BiomeGenBase.mesaPlateau_F.biomeID || j == BiomeGenBase.mesaPlateau.biomeID;
        }
    }

    public abstract int[] getInts(int i, int j, int k, int l);

    // region String decoding support

    // endregion

    protected int selectModeOrRandom(int i, int j, int k, int l) {
        return j == k && k == l
                ? j
                : (i == j && i == k
                        ? i
                        : (i == j && i == l
                                ? i
                                : (i == k && i == l
                                        ? i
                                        : (i == j && k != l
                                                ? i
                                                : (i == k && j != l
                                                        ? i
                                                        : (i == l && j != k
                                                                ? i
                                                                : (j == k && i != l
                                                                        ? j
                                                                        : (j == l && i != k
                                                                                ? j
                                                                                : (k == l && i != j
                                                                                        ? k
                                                                                        : this
                                                                                                .selectRandom(
                                                                                                        i,
                                                                                                        j,
                                                                                                        k,
                                                                                                        l))))))))));
    }
}
