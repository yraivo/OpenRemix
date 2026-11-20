package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPosMutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelperColorResolver1;
import net.minecraft.world.biome.BiomeColorHelperColorResolver2;
import net.minecraft.world.biome.BiomeColorHelperColorResolver3;

public class BiomeColorHelper {
    private static final BiomeColorHelperColorResolver field_180289_b =
            new BiomeColorHelperColorResolver2();
    private static final BiomeColorHelperColorResolver field_180290_c =
            new BiomeColorHelperColorResolver3();
    private static final BiomeColorHelperColorResolver field_180291_a =
            new BiomeColorHelperColorResolver1();

    public static int getFoliageColorAtPos(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return func_180285_a(iBlockAccess, blockPos, net.minecraft.world.biome.BiomeColorHelper.field_180289_b);
    }

    private static int func_180285_a(
            IBlockAccess iBlockAccess,
            BlockPos blockPos,
            BiomeColorHelperColorResolver biomeColorHelperColorResolver) {
        int i = 0;
        boolean flag1 = WorldChunkManager.method_d87039b07bd6961907f2();
        int j = 0;
        boolean flag = flag1;
        int k = 0;

        for (java.util.Iterator<?> __recoveryIterator171_1169 =
                        (BlockPos.getAllInBoxMutable(
                                        blockPos.add(-1, 0, -1), blockPos.add(1, 0, 1)))
                                .iterator();
                (__recoveryIterator171_1169).hasNext(); ) {
            BlockPosMutableBlockPos blockPosMutableBlockPos =
                    (BlockPosMutableBlockPos) (__recoveryIterator171_1169).next();
            int l =
                    biomeColorHelperColorResolver.getColorAtPos(
                            iBlockAccess.getBiomeGenForCoords(blockPosMutableBlockPos),
                            blockPosMutableBlockPos);
            i += (l & 0xFF0000) >> 16;
            j += (l & 0xFF00) >> 8;
            k += l & 0xFF;
            if (!flag) {
                break;
            }
        }

        return (i / 9 & 0xFF) << 16 | (j / 9 & 0xFF) << 8 | k / 9 & 0xFF;
    }

    public static int getWaterColorAtPos(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return func_180285_a(iBlockAccess, blockPos, net.minecraft.world.biome.BiomeColorHelper.field_180290_c);
    }

    public static int getGrassColorAtPos(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return func_180285_a(iBlockAccess, blockPos, net.minecraft.world.biome.BiomeColorHelper.field_180291_a);
    }
}
