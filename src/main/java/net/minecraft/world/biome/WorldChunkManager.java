package net.minecraft.world.biome;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class WorldChunkManager {
    private List biomesToSpawnIn;
    private String field_180301_f;
    private GenLayer genBiomes;
    private static String[] packedTextFragments;
    private static boolean f;

    private GenLayer biomeIndexLayer;

    private BiomeCache biomeCache = new BiomeCache(this);

    static {
        net.minecraft.world.biome.WorldChunkManager.packedTextFragments = new String[13];
        net.minecraft.world.biome.WorldChunkManager.packedTextFragments[0] = new String("");
        net.minecraft.world.biome.WorldChunkManager.packedTextFragments[1] = new String("x");
        net.minecraft.world.biome.WorldChunkManager.packedTextFragments[2] = new String("z");
        net.minecraft.world.biome.WorldChunkManager.packedTextFragments[3] = new String("w");
        net.minecraft.world.biome.WorldChunkManager.packedTextFragments[4] = new String("h");
        net.minecraft.world.biome.WorldChunkManager.packedTextFragments[5] = new String("x");
        net.minecraft.world.biome.WorldChunkManager.packedTextFragments[6] = new String("z");
        net.minecraft.world.biome.WorldChunkManager.packedTextFragments[7] = new String("w");
        net.minecraft.world.biome.WorldChunkManager.packedTextFragments[8] = new String("h");
        net.minecraft.world.biome.WorldChunkManager.packedTextFragments[9] = new String("x");
        net.minecraft.world.biome.WorldChunkManager.packedTextFragments[10] = new String("z");
        method_a8745b073fa8adfa1857(true);
    }

    // region String decoding support

    // endregion

    public BiomeGenBase getBiomeGenerator(BlockPos blockPos, BiomeGenBase biomeGenBase) {
        return this.biomeCache.func_180284_a(blockPos.getX(), blockPos.getZ(), biomeGenBase);
    }

    public BiomeGenBase[] getBiomeGenAt(
            BiomeGenBase[] biomeGenBaseArray, int i, int j, int k, int l, boolean flag) {
        boolean flag2 = d();
        IntCache.resetIntCache();
        boolean flag1 = flag2;
        if (biomeGenBaseArray == null || biomeGenBaseArray.length < k * l) {
            biomeGenBaseArray = new BiomeGenBase[k * l];
        }

        if (flag && k == 16 && l == 16 && (i & 15) == 0 && (j & 15) == 0) {
            BiomeGenBase[] biomeGenBaseArrayValue = this.biomeCache.getCachedBiomes(i, j);
            System.arraycopy(biomeGenBaseArrayValue, 0, biomeGenBaseArray, 0, k * l);
            return biomeGenBaseArray;
        }

        int[] aint = this.biomeIndexLayer.getInts(i, j, k, l);
        int i1 = 0;

        while (i1 < k * l) {
            biomeGenBaseArray[i1] =
                    BiomeGenBase.getBiomeFromBiomeList(
                            aint[i1], BiomeGenBase.field_180279_ad);
            i1++;
            if (flag1) {
                break;
            }
        }

        return biomeGenBaseArray;
    }

    public WorldChunkManager(World world) {
        this(
                world.getSeed(),
                world.getWorldInfo().getTerrainType(),
                world.getWorldInfo().getGeneratorOptions());
    }

    protected WorldChunkManager() {
        this.field_180301_f = packedTextFragments[0];
        this.biomesToSpawnIn = Lists.newArrayList();
        this.biomesToSpawnIn.add(BiomeGenBase.forest);
        this.biomesToSpawnIn.add(BiomeGenBase.plains);
        this.biomesToSpawnIn.add(BiomeGenBase.taiga);
        this.biomesToSpawnIn.add(BiomeGenBase.taigaHills);
        this.biomesToSpawnIn.add(BiomeGenBase.forestHills);
        this.biomesToSpawnIn.add(BiomeGenBase.jungle);
        this.biomesToSpawnIn.add(BiomeGenBase.jungleHills);
    }

    public BiomeGenBase getBiomeGenerator(BlockPos blockPos) {
        return this.getBiomeGenerator(blockPos, (BiomeGenBase) null);
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public WorldChunkManager(long i, WorldType worldType, String text) {
        this();
        this.field_180301_f = text;
        GenLayer[] genLayerArray = GenLayer.initializeAllBiomeGenerators(i, worldType, text);
        this.genBiomes = genLayerArray[0];
        this.biomeIndexLayer = genLayerArray[1];
    }

    public List getBiomesToSpawnIn() {
        return this.biomesToSpawnIn;
    }

    public static boolean d() {
        boolean flag = method_d87039b07bd6961907f2();
        return !flag;
    }

    public BlockPos findBiomePosition(int i, int j, int k, List list, Random random) {
        IntCache.resetIntCache();
        int l = i - k >> 2;
        int i1 = j - k >> 2;
        int j1 = i + k >> 2;
        int k1 = j + k >> 2;
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        boolean flag1 = method_d87039b07bd6961907f2();
        int[] aint = this.genBiomes.getInts(l, i1, l1, i2);
        boolean flag = flag1;
        BlockPos blockPos = null;
        int j2 = 0;
        int k2 = 0;

        while (k2 < l1 * i2) {
            int l2 = l + k2 % l1 << 2;
            int i3 = i1 + k2 / l1 << 2;
            BiomeGenBase biomeGenBase = BiomeGenBase.getBiome(aint[k2]);
            if (list.contains(biomeGenBase) && (blockPos == null || random.nextInt(j2 + 1) == 0)) {
                blockPos = new BlockPos(l2, 0, i3);
                j2++;
            }

            k2++;
            if (!flag) {
                break;
            }
        }

        return blockPos;
    }

    public BiomeGenBase[] getBiomesForGeneration(
            BiomeGenBase[] biomeGenBaseArray, int i, int j, int k, int l) {
        boolean flag1 = method_d87039b07bd6961907f2();
        IntCache.resetIntCache();
        boolean flag = flag1;
        if (biomeGenBaseArray == null || biomeGenBaseArray.length < k * l) {
            biomeGenBaseArray = new BiomeGenBase[k * l];
        }

        int[] aint = this.genBiomes.getInts(i, j, k, l);

        try {
            int i1 = 0;

            while (i1 < k * l) {
                biomeGenBaseArray[i1] =
                        BiomeGenBase.getBiomeFromBiomeList(
                                aint[i1], BiomeGenBase.field_180279_ad);
                i1++;
                if (!flag) {
                    break;
                }
            }

            return biomeGenBaseArray;
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("RawBiomeBlock");
            crashReportCategory.addCrashSection("biomes[] size", biomeGenBaseArray.length);
            crashReportCategory.addCrashSection(packedTextFragments[5], i);
            crashReportCategory.addCrashSection(packedTextFragments[6], j);
            crashReportCategory.addCrashSection(packedTextFragments[7], k);
            crashReportCategory.addCrashSection(packedTextFragments[8], l);
            throw new ReportedException(crashReport);
        }
    }

    public float getTemperatureAtHeight(float f, int i) {
        return f;
    }

    // region String decoding support

    // endregion

    public BiomeGenBase[] loadBlockGeneratorData(
            BiomeGenBase[] biomeGenBaseArray, int i, int j, int k, int l) {
        return this.getBiomeGenAt(biomeGenBaseArray, i, j, k, l, true);
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        f = flag;
    }

    public static boolean method_d87039b07bd6961907f2() {
        return f;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public boolean areBiomesViable(int i, int j, int k, List list) {
        IntCache.resetIntCache();
        int l = i - k >> 2;
        int i1 = j - k >> 2;
        int j1 = i + k >> 2;
        int k1 = j + k >> 2;
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        boolean flag1 = d();
        int[] aint = this.genBiomes.getInts(l, i1, l1, i2);
        boolean flag = flag1;

        try {
            int j2 = 0;

            while (j2 < l1 * i2) {
                BiomeGenBase biomeGenBase = BiomeGenBase.getBiome(aint[j2]);
                if (!list.contains(biomeGenBase)) {
                    return false;
                }

                j2++;
                if (flag) {
                    break;
                }
            }

            return true;
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Layer");
            crashReportCategory.addCrashSection("Layer", this.genBiomes.toString());
            crashReportCategory.addCrashSection(packedTextFragments[9], i);
            crashReportCategory.addCrashSection(packedTextFragments[10], j);
            crashReportCategory.addCrashSection("radius", k);
            crashReportCategory.addCrashSection("allowed", list);
            throw new ReportedException(crashReport);
        }
    }

    public void cleanupCache() {
        this.biomeCache.cleanupCache();
    }

    public float[] getRainfall(float[] afloat, int i, int j, int k, int l) {
        boolean flag1 = d();
        IntCache.resetIntCache();
        boolean flag = flag1;
        if (afloat == null || afloat.length < k * l) {
            afloat = new float[k * l];
        }

        int[] aint = this.biomeIndexLayer.getInts(i, j, k, l);
        int i1 = 0;

        while (i1 < k * l) {
            try {
                float fx =
                        BiomeGenBase.getBiomeFromBiomeList(
                                                aint[i1], BiomeGenBase.field_180279_ad)
                                        .getIntRainfall()
                                / 65536.0F;
                if (fx > 1.0F) {
                    fx = 1.0F;
                }

                afloat[i1] = fx;
            } catch (Throwable throwable) {
                CrashReport crashReport =
                        CrashReport.makeCrashReport(throwable, "Invalid Biome id");
                CrashReportCategory crashReportCategory = crashReport.makeCategory("DownfallBlock");
                crashReportCategory.addCrashSection("biome id", i1);
                crashReportCategory.addCrashSection("downfalls[] size", afloat.length);
                crashReportCategory.addCrashSection(packedTextFragments[1], i);
                crashReportCategory.addCrashSection(packedTextFragments[2], j);
                crashReportCategory.addCrashSection(packedTextFragments[3], k);
                crashReportCategory.addCrashSection(packedTextFragments[4], l);
                throw new ReportedException(crashReport);
            }

            i1++;
            if (flag) {
                break;
            }
        }

        return afloat;
    }
}
