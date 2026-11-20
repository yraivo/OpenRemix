package net.minecraft.world.chunk;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;

import java.util.List;

public interface IChunkProvider {
    Chunk provideChunk(BlockPos blockPos);

    boolean chunkExists(int i, int j);

    boolean unloadQueuedChunks();

    String makeString();

    int getLoadedChunkCount();

    void saveExtraData();

    List getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos);

    void recreateStructures(Chunk chunk, int i, int j);

    BlockPos getStrongholdGen(World world, String text, BlockPos blockPos);

    void populate(IChunkProvider iChunkProvider, int i, int j);

    boolean saveChunks(boolean flag, IProgressUpdate iProgressUpdate);

    boolean canSave();

    boolean func_177460_a(IChunkProvider iChunkProvider, Chunk chunk, int i, int j);

    Chunk provideChunk(int i, int j);
}
