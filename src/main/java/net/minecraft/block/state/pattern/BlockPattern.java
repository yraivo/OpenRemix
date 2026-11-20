package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class BlockPattern {
    private int cz;
    private static String f;

    private Predicate[][][] blockMatches;
    private int c;
    private static int[] aAz;
    private int dD;

    public BlockPatternPatternHelper match(World world, BlockPos blockPos) {
        LoadingCache loadingcache =
                CacheBuilder.newBuilder().build(new BlockPatternCacheLoader(world));
        int[] aint1 = c();
        int i = Math.max(Math.max(this.dD, this.c), this.cz);
        int[] aint = aint1;

        for (java.util.Iterator<?> __recoveryIterator171_1274 =
                        (BlockPos.getAllInBox(blockPos, blockPos.add(i - 1, i - 1, i - 1)))
                                .iterator();
                (__recoveryIterator171_1274).hasNext(); ) {
            BlockPos blockPosValue = (BlockPos) (__recoveryIterator171_1274).next();
            Facing[] facingArray = Facing.values();
            int j = facingArray.length;
            int k = 0;

            while (true) {
                if (k < j) {
                    Facing facing = facingArray[k];

                    for (Facing facingValue : Facing.values()) {
                        if (facingValue != facing && facingValue != facing.getOpposite()) {
                            BlockPatternPatternHelper blockPatternPatternHelper =
                                    this.checkPatternAt(
                                            blockPosValue, facing, facingValue, loadingcache);
                            if (blockPatternPatternHelper != null) {
                                return blockPatternPatternHelper;
                            }
                        }

                        if (aint != null) {
                            break;
                        }
                    }

                    k++;
                    if (aint == null) {
                        continue;
                    }
                }

                if (aint != null) {
                    return null;
                }
                break;
            }
        }

        return null;
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public int method_7eec03da329a017ec143() {
        return this.dD;
    }

    protected static BlockPos translateOffset(
            BlockPos blockPos, Facing facing, Facing facingValue, int i, int j, int k) {
        int[] aint = c();
        if (facing != facingValue && facing != facingValue.getOpposite()) {
            Vec3i vec3i =
                    new Vec3i(
                            facing.getFrontOffsetX(),
                            facing.getFrontOffsetY(),
                            facing.getFrontOffsetZ());
            Vec3i vec3iValue =
                    new Vec3i(
                            facingValue.getFrontOffsetX(),
                            facingValue.getFrontOffsetY(),
                            facingValue.getFrontOffsetZ());
            Vec3i vec3iValue2 = vec3i.crossProduct(vec3iValue);
            BlockPos blockPosValue =
                    blockPos.add(
                            vec3iValue.getX() * -j + vec3iValue2.getX() * i + vec3i.getX() * k,
                            vec3iValue.getY() * -j + vec3iValue2.getY() * i + vec3i.getY() * k,
                            vec3iValue.getZ() * -j + vec3iValue2.getZ() * i + vec3i.getZ() * k);
            if (aint != null) {
                Entity.setLegacyGlobalFlowMarker(new String[1]);
            }

            return blockPosValue;
        } else {
            throw new IllegalArgumentException(f);
        }
    }

    // region String decoding support

    // endregion

    static {
        if (c() != null) {
            b(new int[2]);
        }
        f = "Invalid forwards & up combination";
    }

    public BlockPattern(Predicate[][][] apredicate) {
        super();
        int[] initialFlowMarker = c();

        int[] aint1 = initialFlowMarker;

        this.blockMatches = apredicate;
        int[] aint = aint1;
        this.cz = apredicate.length;
        if (this.cz > 0) {
            this.c = apredicate[0].length;
            if (this.c > 0) {
                this.dD = apredicate[0][0].length;
                if (aint == null) {
                    return;
                }
            }

            this.dD = 0;
            if (aint == null) {
                return;
            }
        }

        this.c = 0;
        this.dD = 0;
    }

    // region String decoding support

    // endregion

    public static int[] c() {
        return aAz;
    }

    public int method_9b417d27949cba33a0d0() {
        return this.c;
    }

    private BlockPatternPatternHelper checkPatternAt(
            BlockPos blockPos, Facing facing, Facing facingValue, LoadingCache loadingcache) {
        int[] aint1 = c();
        int i = 0;
        int[] aint = aint1;

        while (i < this.dD) {
            int j = 0;

            while (true) {
                if (j < this.c) {
                    int k = 0;

                    while (k < this.cz) {
                        if (!this.blockMatches[k][j][i].apply(
                                loadingcache.getUnchecked(
                                        translateOffset(blockPos, facing, facingValue, i, j, k)))) {
                            return null;
                        }

                        k++;
                        if (aint != null) {
                            break;
                        }
                    }

                    j++;
                    if (aint == null) {
                        continue;
                    }
                }

                i++;
                if (aint != null) {
                    return new BlockPatternPatternHelper(
                            blockPos, facing, facingValue, loadingcache);
                }
                break;
            }
        }

        return new BlockPatternPatternHelper(blockPos, facing, facingValue, loadingcache);
    }

    public static void b(int[] aint) {
        aAz = aint;
    }
}
