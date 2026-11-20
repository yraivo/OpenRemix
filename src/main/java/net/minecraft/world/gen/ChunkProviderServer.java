package net.minecraft.world.gen;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;

import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChunkProviderServer implements IChunkProvider {
    private IChunkLoader chunkLoader;
    private IChunkProvider serverChunkGenerator;

    private static Logger logger;
    private Set droppedChunksSet = Collections.newSetFromMap(new ConcurrentHashMap());
    public boolean chunkLoadOverride = true;
    private Chunk dummyChunk;

    private List loadedChunks;
    private WorldServer worldObj;
    private LongHashMap id2ChunkMap = new LongHashMap();

    public boolean canSave() {
        return !this.worldObj.disableLevelSaving;
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iProgressUpdate) {
        MapGenBase.b();

        int i = 0;

        int j = 0;

        while (j < this.loadedChunks.size()) {
            Chunk chunk = (Chunk) this.loadedChunks.get(j);
            if (flag) {
                this.saveChunkExtraData(chunk);
            }

            if (chunk.needsSaving(flag)) {
                this.saveChunkData(chunk);
                chunk.setModified(false);
                if (++i == 24 && !flag) {
                    return false;
                }
            }

            j++;
        }

        return true;
    }

    public boolean func_177460_a(IChunkProvider iChunkProvider, Chunk chunk, int i, int j) {
        if (this.serverChunkGenerator != null
                && this.serverChunkGenerator.func_177460_a(iChunkProvider, chunk, i, j)) {
            Chunk chunkValue = this.provideChunk(i, j);
            chunkValue.setChunkModified();
            return true;
        } else {
            return false;
        }
    }

    private void saveChunkExtraData(Chunk chunk) {
        if (this.chunkLoader != null) {
            try {
                this.chunkLoader.saveExtraChunkData(this.worldObj, chunk);
            } catch (Exception exception) {
                net.minecraft.world.gen.ChunkProviderServer.logger.error("Couldn't save entities", exception);
            }
        }
    }

    private void saveChunkData(Chunk chunk) {
        if (this.chunkLoader != null) {
            try {
                chunk.setLastSaveTime(this.worldObj.getTotalWorldTime());
                this.chunkLoader.saveChunk(this.worldObj, chunk);
            } catch (IOException ioexception) {
                net.minecraft.world.gen.ChunkProviderServer.logger.error("Couldn't save chunk", ioexception);
            } catch (MinecraftException minecraftException) {
                net.minecraft.world.gen.ChunkProviderServer.logger.error(
                        "Couldn't save chunk; already in use by another instance of Minecraft?",
                        minecraftException);
            }
        }
    }

    public void populate(IChunkProvider iChunkProvider, int i, int j) {
        Chunk chunk = this.provideChunk(i, j);
        if (!chunk.isTerrainPopulated()) {
            chunk.func_150809_p();
            if (this.serverChunkGenerator != null) {
                this.serverChunkGenerator.populate(iChunkProvider, i, j);
                chunk.setChunkModified();
            }
        }
    }

    public String makeString() {
        return "ServerChunkCache: "
                + this.id2ChunkMap.getNumHashElements()
                + " Drop: "
                + this.droppedChunksSet.size();
    }

    public int getLoadedChunkCount() {
        return this.id2ChunkMap.getNumHashElements();
    }

    public void recreateStructures(Chunk chunk, int i, int j) {}

    public List getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
        return this.serverChunkGenerator.getPossibleCreatures(enumCreatureType, blockPos);
    }

    private Chunk loadChunkFromFile(int i, int j) {
        if (this.chunkLoader == null) {
            return null;
        }

        try {
            Chunk chunk = this.chunkLoader.loadChunk(this.worldObj, i, j);
            if (chunk != null) {
                chunk.setLastSaveTime(this.worldObj.getTotalWorldTime());
                if (this.serverChunkGenerator != null) {
                    this.serverChunkGenerator.recreateStructures(chunk, i, j);
                }
            }

            return chunk;
        } catch (Exception exception) {
            net.minecraft.world.gen.ChunkProviderServer.logger.error("Couldn't load chunk", exception);
            return null;
        }
    }

    public Chunk provideChunk(int i, int j) {
        Chunk chunk = (Chunk) this.id2ChunkMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(i, j));
        return chunk == null
                ? (!this.worldObj.isFindingSpawnPoint() && !this.chunkLoadOverride
                        ? this.dummyChunk
                        : this.loadChunk(i, j))
                : chunk;
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public BlockPos getStrongholdGen(World world, String text, BlockPos blockPos) {
        return this.serverChunkGenerator.getStrongholdGen(world, text, blockPos);
    }

    public void saveExtraData() {
        if (this.chunkLoader != null) {
            this.chunkLoader.saveExtraData();
        }
    }

    public void dropChunk(int i, int j) {
        MapGenBase.b();

        if (this.worldObj.provider.canRespawnHere()) {
            if (this.worldObj.chunkExists(i, j)) {
                return;
            }

            this.droppedChunksSet.add(ChunkCoordIntPair.chunkXZ2Int(i, j));

            return;
        }

        this.droppedChunksSet.add(ChunkCoordIntPair.chunkXZ2Int(i, j));
    }

    public boolean unloadQueuedChunks() {
        MapGenBase.b();

        if (!this.worldObj.disableLevelSaving) {
            int i = 0;

            while (i < 100) {
                if (!this.droppedChunksSet.isEmpty()) {
                    Long olong = (Long) this.droppedChunksSet.iterator().next();
                    Chunk chunk = (Chunk) this.id2ChunkMap.getValueByKey(olong);
                    if (chunk != null) {
                        chunk.onChunkUnload();
                        this.saveChunkData(chunk);
                        this.saveChunkExtraData(chunk);
                        this.id2ChunkMap.remove(olong);
                        this.loadedChunks.remove(chunk);
                    }

                    this.droppedChunksSet.remove(olong);
                }

                i++;
            }

            if (this.chunkLoader != null) {
                this.chunkLoader.chunkTick();
            }
        }

        return this.serverChunkGenerator.unloadQueuedChunks();
    }

    public ChunkProviderServer(
            WorldServer bIpValue, IChunkLoader bImValue, IChunkProvider bInValue) {
        this.loadedChunks = Lists.newArrayList();
        this.dummyChunk = new EmptyChunk(bIpValue, 0, 0);
        this.worldObj = bIpValue;
        this.chunkLoader = bImValue;
        this.serverChunkGenerator = bInValue;
    }

    public Chunk provideChunk(BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    public boolean chunkExists(int i, int j) {
        return this.id2ChunkMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(i, j));
    }

    static {
        net.minecraft.world.gen.ChunkProviderServer.logger = LogManager.getLogger();
    }

    public void unloadAllChunks() {
        MapGenBase.b();

        Iterator iterator = this.loadedChunks.iterator();

        while (iterator.hasNext()) {
            Chunk chunk = (Chunk) iterator.next();
            this.dropChunk(chunk.xPosition, chunk.zPosition);
        }
    }

    public List getLoadedChunks() {
        return this.loadedChunks;
    }

    public Chunk loadChunk(int i, int j) {
        long k = ChunkCoordIntPair.chunkXZ2Int(i, j);
        MapGenBase.b();

        this.droppedChunksSet.remove(k);

        Chunk chunk = (Chunk) this.id2ChunkMap.getValueByKey(k);
        if (chunk == null) {
            chunk = this.loadChunkFromFile(i, j);
            if (chunk == null) {
                if (this.serverChunkGenerator == null) {
                    chunk = this.dummyChunk;

                    
                } else {

                    try {
                        chunk = this.serverChunkGenerator.provideChunk(i, j);
                    } catch (Throwable throwable) {
                        CrashReport crashReport =
                                CrashReport.makeCrashReport(
                                        throwable, "Exception generating new chunk");
                        CrashReportCategory crashReportCategory =
                                crashReport.makeCategory("Chunk to be generated");
                        crashReportCategory.addCrashSection("Location", String.format("%d,%d", i, j));
                        crashReportCategory.addCrashSection("Position hash", k);
                        crashReportCategory.addCrashSection(
                                "Generator", this.serverChunkGenerator.makeString());
                        throw new ReportedException(crashReport);
                    }
                }
            }

            this.id2ChunkMap.add(k, chunk);
            this.loadedChunks.add(chunk);
            chunk.onChunkLoad();
            chunk.populateChunk(this, this, i, j);
        }

        return chunk;
    }
}
