package net.minecraft.world.gen;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class MapGenBase {
    protected int range = 8;
    protected Random rand = new Random();
    protected World worldObj;
    private static String[] d;

    static {
        if (b() == null) {
            b(new String[4]);
        }
    }

    public static String[] b() {
        return d;
    }

    protected void recursiveGenerate(
            World world, int i, int j, int k, int l, ChunkPrimer chunkPrimer) {}

    public static void b(String[] astring) {
        d = astring;
    }

    public void generate(
            IChunkProvider iChunkProvider, World bhTValue, int i, int j, ChunkPrimer chunkPrimer) {
        int k = this.range;
        this.worldObj = bhTValue;
        b();

        this.rand.setSeed(bhTValue.getSeed());
        long l = this.rand.nextLong();

        long i1 = this.rand.nextLong();
        int j1 = i - k;

        while (j1 <= i + k) {
            int k1 = j - k;

            while (true) {
                if (k1 <= j + k) {
                    long l1 = j1 * l;
                    long i2 = k1 * i1;
                    this.rand.setSeed(l1 ^ i2 ^ bhTValue.getSeed());
                    this.recursiveGenerate(bhTValue, j1, k1, i, j, chunkPrimer);
                    k1++;

                    continue;
                }

                j1++;

                break;
            }
        }
    }
}
