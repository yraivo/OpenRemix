package net.minecraft.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

import com.google.common.collect.Lists;

import java.util.List;

public class ChunkProviderDebug implements IChunkProvider {
    private static String d;
    private static List field_177464_a;
    private World world;

    private static int field_177462_b;

    public static BlockState func_177461_b(int i, int j) {
        BlockState blockState = null;
        if (i > 0 && j > 0 && i % 2 != 0 && j % 2 != 0) {
            i /= 2;
            j /= 2;
            if (i <= net.minecraft.world.gen.ChunkProviderDebug.field_177462_b
                    && j <= net.minecraft.world.gen.ChunkProviderDebug.field_177462_b) {
                int k = MathHelper.abs(i * net.minecraft.world.gen.ChunkProviderDebug.field_177462_b + j);
                if (k < net.minecraft.world.gen.ChunkProviderDebug.field_177464_a.size()) {
                    blockState = (BlockState) net.minecraft.world.gen.ChunkProviderDebug.field_177464_a.get(k);
                }
            }
        }

        return blockState;
    }

    public Chunk provideChunk(int i, int j) {
        MapGenBase.b();

        ChunkPrimer chunkPrimer = new ChunkPrimer();

        int l = 0;

        while (l < 16) {
            int i1 = 0;

            while (true) {
                if (i1 < 16) {
                    int j1 = i * 16 + l;
                    int k = j * 16 + i1;
                    chunkPrimer.setBlockState(l, 60, i1, Blocks.barrier.getDefaultState());
                    BlockState blockState = func_177461_b(j1, k);
                    if (blockState != null) {
                        chunkPrimer.setBlockState(l, 70, i1, blockState);
                    }

                    i1++;

                    continue;
                }

                l++;

                break;
            }
        }

        Chunk chunk = new Chunk(this.world, chunkPrimer, i, j);
        chunk.generateSkylightMap();
        BiomeGenBase[] biomeGenBaseArray =
                this.world
                        .getWorldChunkManager()
                        .loadBlockGeneratorData((BiomeGenBase[]) null, i * 16, j * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();
        int k1 = 0;

        while (k1 < abyte.length) {
            abyte[k1] = (byte) biomeGenBaseArray[k1].biomeID;
            k1++;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public ChunkProviderDebug(World bhTValue) {
        this.world = bhTValue;
    }

    public boolean func_177460_a(IChunkProvider iChunkProvider, Chunk chunk, int i, int j) {
        return false;
    }

    public void recreateStructures(Chunk chunk, int i, int j) {}

    public boolean canSave() {
        return true;
    }

    static {
        d = "DebugLevelSource";
        net.minecraft.world.gen.ChunkProviderDebug.field_177464_a = Lists.newArrayList();
        for (java.util.Iterator<?> __recoveryIterator171_5350 = (Block.blockRegistry).iterator();
                (__recoveryIterator171_5350).hasNext(); ) {
            Block class_457565cd5d9897e760e01 = (Block) (__recoveryIterator171_5350).next();
            net.minecraft.world.gen.ChunkProviderDebug.field_177464_a.addAll(
                    class_457565cd5d9897e760e01.getBlockState().getValidStates());
        }
        net.minecraft.world.gen.ChunkProviderDebug.field_177462_b =
                MathHelper.ceil(MathHelper.sqrt(net.minecraft.world.gen.ChunkProviderDebug.field_177464_a.size()));
    }

    public void populate(IChunkProvider iChunkProvider, int i, int j) {}

    public String makeString() {
        return d;
    }

    public void saveExtraData() {}

    // region String decoding support

    // endregion

    public boolean unloadQueuedChunks() {
        return false;
    }

    public BlockPos getStrongholdGen(World world, String text, BlockPos blockPos) {
        return null;
    }

    public boolean chunkExists(int i, int j) {
        return true;
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iProgressUpdate) {
        return true;
    }

    // region String decoding support

    // endregion

    public List getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
        BiomeGenBase biomeGenForCoords = this.world.getBiomeGenForCoords(blockPos);
        return biomeGenForCoords.getSpawnableList(enumCreatureType);
    }

    public Chunk provideChunk(BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }
}
