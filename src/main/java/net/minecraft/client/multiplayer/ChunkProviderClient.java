package net.minecraft.client.multiplayer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;

import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

public class ChunkProviderClient implements IChunkProvider {
    private static Logger logger;

    private World worldObj;
    private Chunk blankChunk;
    private List chunkListing;

    private LongHashMap chunkMapping = new LongHashMap();

    public BlockPos getStrongholdGen(World world, String text, BlockPos blockPos) {
        return null;
    }

    public ChunkProviderClient(World awsValue) {
        this.chunkListing = Lists.newArrayList();
        this.blankChunk = new EmptyChunk(awsValue, 0, 0);
        this.worldObj = awsValue;
    }

    static {
        net.minecraft.client.multiplayer.ChunkProviderClient.logger = LogManager.getLogger();
    }

    public Chunk provideChunk(int i, int j) {
        Chunk chunk = (Chunk) this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(i, j));
        return chunk == null ? this.blankChunk : chunk;
    }

    public void recreateStructures(Chunk chunk, int i, int j) {}

    public void saveExtraData() {}

    public void populate(IChunkProvider iChunkProvider, int i, int j) {}

    public List getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
        return null;
    }

    public Chunk provideChunk(BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    public boolean unloadQueuedChunks() {
        long i = System.currentTimeMillis();
        Entity[] entityArray = ClientWorld.getLegacyClientWorldFlowState();
        Iterator iterator = this.chunkListing.iterator();
        Entity[] entityArrayValue = entityArray;

        while (iterator.hasNext()) {
            Chunk chunk = (Chunk) iterator.next();
            long k = System.currentTimeMillis() - i;

            chunk.tickChunk(k > 5L);
            if (entityArrayValue != null) {
                break;
            }
        }

        long j = System.currentTimeMillis() - i;

        if (j > 100L) {
            net.minecraft.client.multiplayer.ChunkProviderClient.logger.info(
                    "Warning: Clientside chunk ticking took {} ms",
                    new Object[] {System.currentTimeMillis() - i});
        }

        return false;
    }

    public String makeString() {
        return "MultiplayerChunkCache: "
                + this.chunkMapping.getNumHashElements()
                + ", "
                + this.chunkListing.size();
    }

    public boolean func_177460_a(IChunkProvider iChunkProvider, Chunk chunk, int i, int j) {
        return false;
    }

    public Chunk loadChunk(int i, int j) {
        Chunk chunk = new Chunk(this.worldObj, i, j);
        this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(i, j), chunk);
        this.chunkListing.add(chunk);
        chunk.setChunkLoaded(true);
        return chunk;
    }

    public int getLoadedChunkCount() {
        return this.chunkListing.size();
    }

    public boolean canSave() {
        return false;
    }

    public void unloadChunk(int i, int j) {
        Chunk chunk = this.provideChunk(i, j);
        if (!chunk.isEmpty()) {
            chunk.onChunkUnload();
        }

        this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(i, j));
        this.chunkListing.remove(chunk);
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iProgressUpdate) {
        return true;
    }

    public boolean chunkExists(int i, int j) {
        return true;
    }
}
