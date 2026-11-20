package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.storage.IThreadedFileIO;
import net.minecraft.world.storage.ThreadedFileIOBase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO {
    private Set pendingAnvilChunksCoordinates;
    private static Logger logger;
    private static String[] packedTextFragments;

    private List chunksToRemove = Lists.newArrayList();
    private File chunkSaveLocation;

    private Object syncLockObject;

    private void writeChunkNBTTags(AnvilChunkLoaderPendingChunk anvilChunkLoaderPendingChunk)
            throws IOException {
        java.io.DataOutputStream dataoutputstream =
                net.minecraft.world.chunk.storage.RegionFileCache.getChunkOutputStream(
                        this.chunkSaveLocation,
                        anvilChunkLoaderPendingChunk.chunkCoordinate.chunkXPos,
                        anvilChunkLoaderPendingChunk.chunkCoordinate.chunkZPos);
        net.minecraft.nbt.CompressedStreamTools.write(
                anvilChunkLoaderPendingChunk.nbtTags, dataoutputstream);
        dataoutputstream.close();
    }

    static {
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments = new String[24];
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[0] = new String(",");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[1] = new String(",");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[2] = new String(",");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[3] = new String(")");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[4] = new String("V");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[5] = new String("Y");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[6] = new String("i");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[7] = new String("");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[8] = new String("x");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[9] = new String("y");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[10] =
                new String("z");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[11] =
                new String("t");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[12] =
                new String("p");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[13] =
                new String("Y");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[14] =
                new String("i");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[15] =
                new String("i");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[16] =
                new String("i");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[17] =
                new String("x");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[18] =
                new String("y");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[19] =
                new String("z");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[20] =
                new String("t");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.packedTextFragments[21] =
                new String("p");
        net.minecraft.world.chunk.storage.AnvilChunkLoader.logger = LogManager.getLogger();
    }

    public void saveExtraChunkData(World world, Chunk chunk) {}

    public void saveExtraData() {
        while (this.writeNextIO()) {}
    }

    public boolean writeNextIO() {
        net.minecraft.world.chunk.storage.AnvilChunkLoaderPendingChunk
                anvilChunkLoaderPendingChunk = null;
        java.lang.Object bITValue = this.syncLockObject;
        synchronized (this.syncLockObject) {
            if (this.chunksToRemove.isEmpty()) {
                return false;
            }

            anvilChunkLoaderPendingChunk =
                    (net.minecraft.world.chunk.storage.AnvilChunkLoaderPendingChunk)
                            this.chunksToRemove.remove(0);
            this.pendingAnvilChunksCoordinates.remove(anvilChunkLoaderPendingChunk.chunkCoordinate);
        }

        if (anvilChunkLoaderPendingChunk != null) {
            try {
                this.writeChunkNBTTags(anvilChunkLoaderPendingChunk);
            } catch (java.lang.Exception exception) {
                exception.printStackTrace();
            }
        }

        return true;
    }

    protected void addChunkToPending(ChunkCoordIntPair chunkCoordIntPair, CompoundTag compoundTag) {
        String text = ExtendedBlockStorage.cJ();
        String textValue = text;
        Object bITValue = this.syncLockObject;
        synchronized (this.syncLockObject) {
            if (this.pendingAnvilChunksCoordinates.contains(chunkCoordIntPair)) {
                int i = 0;

                while (i < this.chunksToRemove.size()) {
                    if (((AnvilChunkLoaderPendingChunk) this.chunksToRemove.get(i))
                            .chunkCoordinate.equals(chunkCoordIntPair)) {
                        this.chunksToRemove.set(
                                i,
                                new AnvilChunkLoaderPendingChunk(chunkCoordIntPair, compoundTag));
                        return;
                    }

                    i++;
                    if (textValue == null) {
                        break;
                    }
                }
            }

            this.chunksToRemove.add(
                    new AnvilChunkLoaderPendingChunk(chunkCoordIntPair, compoundTag));
            this.pendingAnvilChunksCoordinates.add(chunkCoordIntPair);
            ThreadedFileIOBase.getThreadedIOInstance().queueIO(this);
        }
    }

    protected Chunk checkedReadChunkFromNBT(World world, int i, int j, CompoundTag compoundTag) {
        if (!compoundTag.hasKey("Level", 10)) {
            net.minecraft.world.chunk.storage.AnvilChunkLoader.logger.error(
                    "Chunk file at "
                            + i
                            + packedTextFragments[0]
                            + j
                            + " is missing level data, skipping");
            return null;
        }

        if (!compoundTag.getCompoundTag("Level").hasKey("Sections", 9)) {
            net.minecraft.world.chunk.storage.AnvilChunkLoader.logger.error(
                    "Chunk file at "
                            + i
                            + packedTextFragments[1]
                            + j
                            + " is missing block data, skipping");
            return null;
        }

        Chunk chunk = this.readChunkFromNBT(world, compoundTag.getCompoundTag("Level"));
        if (!chunk.isAtLocation(i, j)) {
            net.minecraft.world.chunk.storage.AnvilChunkLoader.logger.error(
                    "Chunk file at "
                            + i
                            + packedTextFragments[2]
                            + j
                            + " is in the wrong location; relocating. (Expected "
                            + i
                            + ", "
                            + j
                            + ", got "
                            + chunk.xPosition
                            + ", "
                            + chunk.zPosition
                            + packedTextFragments[3]);
            compoundTag.setInteger("xPos", i);
            compoundTag.setInteger("zPos", j);
            chunk = this.readChunkFromNBT(world, compoundTag.getCompoundTag("Level"));
        }

        return chunk;
    }

    // region String decoding support

    // endregion

    private Chunk readChunkFromNBT(World world, CompoundTag compoundTag) {
        int integer = compoundTag.getInteger("xPos");
        String text = ExtendedBlockStorage.cJ();
        int j = compoundTag.getInteger("zPos");
        Chunk chunk = new Chunk(world, integer, j);
        chunk.setHeightMap(compoundTag.getIntArray("HeightMap"));
        chunk.setTerrainPopulated(compoundTag.getBoolean("TerrainPopulated"));
        String textValue = text;
        chunk.setLightPopulated(compoundTag.getBoolean("LightPopulated"));
        chunk.setInhabitedTime(compoundTag.getLong("InhabitedTime"));
        NBTTagList nbtTagList = compoundTag.getTagList("Sections", 10);
        byte b0 = 16;
        ExtendedBlockStorage[] extendedBlockStorageArray = new ExtendedBlockStorage[b0];
        int k = !world.provider.getHasNoSky() ? 1 : 0;
        int l = 0;

        while (l < nbtTagList.tagCount()) {
            CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(l);
            byte b1 = compoundTagValue.getByte(packedTextFragments[13]);
            ExtendedBlockStorage extendedBlockStorage =
                    new ExtendedBlockStorage(
                            b1 << 4, remix.compat.compiler.JvmBooleanSlots.fromInt(k));
            byte[] abyte = compoundTagValue.getByteArray("Blocks");
            NibbleArray nibbleArray = new NibbleArray(compoundTagValue.getByteArray("Data"));
            NibbleArray nibbleArrayValue =
                    compoundTagValue.hasKey("Add", 7)
                            ? new NibbleArray(compoundTagValue.getByteArray("Add"))
                            : null;
            char[] achar = new char[abyte.length];
            int i1 = 0;

            while (i1 < achar.length) {
                int j1 = i1 & 15;
                int k1 = i1 >> 8 & 15;
                int l1 = i1 >> 4 & 15;
                int i2 = nibbleArrayValue != null ? nibbleArrayValue.get(j1, k1, l1) : 0;
                achar[i1] =
                        (char) (i2 << 12 | (abyte[i1] & 255) << 4 | nibbleArray.get(j1, k1, l1));
                i1++;
                if (textValue == null) {
                    break;
                }
            }

            extendedBlockStorage.setData(achar);
            extendedBlockStorage.setBlocklightArray(
                    new NibbleArray(compoundTagValue.getByteArray("BlockLight")));
            if (k != 0) {
                extendedBlockStorage.setSkylightArray(
                        new NibbleArray(compoundTagValue.getByteArray("SkyLight")));
            }

            extendedBlockStorage.l();
            extendedBlockStorageArray[b1] = extendedBlockStorage;
            l++;
            if (textValue == null) {
                break;
            }
        }

        chunk.setStorageArrays(extendedBlockStorageArray);
        if (compoundTag.hasKey("Biomes", 7)) {
            chunk.setBiomeArray(compoundTag.getByteArray("Biomes"));
        }

        NBTTagList nbtTagListValue = compoundTag.getTagList("Entities", 10);
        if (nbtTagListValue != null) {
            int j2 = 0;

            while (j2 < nbtTagListValue.tagCount()) {
                CompoundTag compoundTagValue2 = nbtTagListValue.getCompoundTagAt(j2);
                Entity entity = EntityList.createEntityFromNBT(compoundTagValue2, world);
                chunk.setHasEntities(true);
                if (entity != null) {
                    chunk.addEntity(entity);
                    Entity entityValue = entity;
                    CompoundTag compoundTagValue3 = compoundTagValue2;

                    while (compoundTagValue3.hasKey("Riding", 10)) {
                        Entity entityValue2 =
                                EntityList.createEntityFromNBT(
                                        compoundTagValue3.getCompoundTag("Riding"), world);
                        if (entityValue2 != null) {
                            chunk.addEntity(entityValue2);
                            entityValue.mountEntity(entityValue2);
                        }

                        entityValue = entityValue2;
                        compoundTagValue3 = compoundTagValue3.getCompoundTag("Riding");
                        if (textValue == null) {
                            break;
                        }
                    }
                }

                j2++;
                if (textValue == null) {
                    break;
                }
            }
        }

        NBTTagList nbtTagListValue2 = compoundTag.getTagList("TileEntities", 10);
        if (nbtTagListValue2 != null) {
            int k2 = 0;

            while (k2 < nbtTagListValue2.tagCount()) {
                CompoundTag compoundTagValue4 = nbtTagListValue2.getCompoundTagAt(k2);
                TileEntity tileEntity = TileEntity.createAndLoadEntity(compoundTagValue4);
                if (tileEntity != null) {
                    chunk.addTileEntity(tileEntity);
                }

                k2++;
                if (textValue == null) {
                    break;
                }
            }
        }

        if (compoundTag.hasKey("TileTicks", 9)) {
            NBTTagList nbtTagListValue3 = compoundTag.getTagList("TileTicks", 10);
            if (nbtTagListValue3 != null) {
                int l2 = 0;

                while (l2 < nbtTagListValue3.tagCount()) {
                    CompoundTag compoundTagValue5;
                    Block block;
                    {
                        compoundTagValue5 = nbtTagListValue3.getCompoundTagAt(l2);
                        if (compoundTagValue5.hasKey(packedTextFragments[14], 8)) {
                            block =
                                    Block.getBlockFromName(
                                            compoundTagValue5.getString(packedTextFragments[15]));
                            if (!(textValue != null)) {

                                block =
                                        Block.getBlockById(
                                                compoundTagValue5.getInteger(
                                                        packedTextFragments[16]));
                            }
                        } else {

                            block =
                                    Block.getBlockById(
                                            compoundTagValue5.getInteger(packedTextFragments[16]));
                        }
                    }

                    world.scheduleBlockUpdate(
                            new BlockPos(
                                    compoundTagValue5.getInteger(packedTextFragments[17]),
                                    compoundTagValue5.getInteger(packedTextFragments[18]),
                                    compoundTagValue5.getInteger(packedTextFragments[19])),
                            block,
                            compoundTagValue5.getInteger(packedTextFragments[20]),
                            compoundTagValue5.getInteger(packedTextFragments[21]));
                    l2++;
                    if (textValue == null) {
                        break;
                    }
                }
            }
        }

        return chunk;
    }

    public void saveChunk(World world, Chunk chunk) throws MinecraftException, IOException {
        world.checkSessionLock();

        try {
            CompoundTag compoundTag = new CompoundTag();
            CompoundTag compoundTagValue = new CompoundTag();
            compoundTag.setTag("Level", compoundTagValue);
            this.writeChunkToNBT(chunk, world, compoundTagValue);
            this.addChunkToPending(chunk.getChunkCoordIntPair(), compoundTag);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void chunkTick() {}

    // region String decoding support

    // endregion

    private void writeChunkToNBT(Chunk chunk, World world, CompoundTag compoundTag) {
        compoundTag.setByte(packedTextFragments[4], (byte) 1);
        compoundTag.setInteger("xPos", chunk.xPosition);
        String text = ExtendedBlockStorage.cJ();
        compoundTag.setInteger("zPos", chunk.zPosition);
        String textValue = text;
        compoundTag.setLong("LastUpdate", world.getTotalWorldTime());
        compoundTag.setIntArray("HeightMap", chunk.getHeightMap());
        compoundTag.setBoolean("TerrainPopulated", chunk.isTerrainPopulated());
        compoundTag.setBoolean("LightPopulated", chunk.isLightPopulated());
        compoundTag.setLong("InhabitedTime", chunk.getInhabitedTime());
        ExtendedBlockStorage[] blockStorageArray = chunk.getBlockStorageArray();
        NBTTagList nbtTagList = new NBTTagList();
        int i = !world.provider.getHasNoSky() ? 1 : 0;
        ExtendedBlockStorage[] extendedBlockStorageArray = blockStorageArray;
        int j = blockStorageArray.length;
        int k = 0;

        while (k < j) {
            ExtendedBlockStorage extendedBlockStorage = extendedBlockStorageArray[k];
            if (extendedBlockStorage != null) {
                CompoundTag compoundTagValue = new CompoundTag();
                compoundTagValue.setByte(
                        packedTextFragments[5],
                        (byte) (extendedBlockStorage.getYLocation() >> 4 & 0xFF));
                byte[] abyte = new byte[extendedBlockStorage.getData().length];
                NibbleArray nibbleArray = new NibbleArray();
                NibbleArray nibbleArrayValue = null;
                int l = 0;

                while (l < extendedBlockStorage.getData().length) {
                    char c0 = extendedBlockStorage.getData()[l];
                    int i1 = l & 15;
                    int j1 = l >> 8 & 15;
                    int k1 = l >> 4 & 15;
                    if (c0 >> '\f' != 0) {
                        if (nibbleArrayValue == null) {
                            nibbleArrayValue = new NibbleArray();
                        }

                        nibbleArrayValue.set(i1, j1, k1, c0 >> '\f');
                    }

                    abyte[l] = (byte) (c0 >> 4 & 0xFF);
                    nibbleArray.set(i1, j1, k1, c0 & 15);
                    l++;
                    if (textValue == null) {
                        break;
                    }
                }

                compoundTagValue.setByteArray("Blocks", abyte);
                compoundTagValue.setByteArray("Data", nibbleArray.getData());
                if (nibbleArrayValue != null) {
                    compoundTagValue.setByteArray("Add", nibbleArrayValue.getData());
                }

                {
                    compoundTagValue.setByteArray(
                            "BlockLight", extendedBlockStorage.getBlocklightArray().getData());
                    if (i != 0) {
                        compoundTagValue.setByteArray(
                                "SkyLight", extendedBlockStorage.getSkylightArray().getData());
                        if (!(textValue != null)) {

                            compoundTagValue.setByteArray(
                                    "SkyLight",
                                    new byte
                                            [extendedBlockStorage
                                                    .getBlocklightArray()
                                                    .getData()
                                                    .length]);
                        }
                    } else {

                        compoundTagValue.setByteArray(
                                "SkyLight",
                                new byte
                                        [extendedBlockStorage
                                                .getBlocklightArray()
                                                .getData()
                                                .length]);
                    }
                }

                nbtTagList.appendTag(compoundTagValue);
            }

            k++;
            if (textValue == null) {
                break;
            }
        }

        compoundTag.setTag("Sections", nbtTagList);
        compoundTag.setByteArray("Biomes", chunk.getBiomeArray());
        chunk.setHasEntities(false);
        NBTTagList nbtTagListValue = new NBTTagList();
        j = 0;

        label80:
        while (j < chunk.getEntityLists().length) {
            Iterator iterator = chunk.getEntityLists()[j].iterator();

            while (true) {
                if (iterator.hasNext()) {
                    Entity entity = (Entity) iterator.next();
                    CompoundTag compoundTagValue2 = new CompoundTag();
                    if (entity.writeToNBTOptional(compoundTagValue2)) {
                        chunk.setHasEntities(true);
                        nbtTagListValue.appendTag(compoundTagValue2);
                    }

                    if (textValue != null) {
                        continue;
                    }
                }

                j++;
                if (textValue == null) {
                    break label80;
                }
                break;
            }
        }

        compoundTag.setTag("Entities", nbtTagListValue);
        NBTTagList nbtTagListValue2 = new NBTTagList();

        for (java.util.Iterator<?> __recoveryIterator171_53540 =
                        (chunk.getTileEntityMap().values()).iterator();
                (__recoveryIterator171_53540).hasNext(); ) {
            TileEntity tileEntity = (TileEntity) (__recoveryIterator171_53540).next();
            CompoundTag compoundTagValue3 = new CompoundTag();
            tileEntity.writeToNBT(compoundTagValue3);
            nbtTagListValue2.appendTag(compoundTagValue3);
            if (textValue == null) {
                break;
            }
        }

        compoundTag.setTag("TileEntities", nbtTagListValue2);
        List pendingBlockUpdates = world.getPendingBlockUpdates(chunk, false);
        if (pendingBlockUpdates != null) {
            long l1 = world.getTotalWorldTime();
            NBTTagList nbtTagListValue3 = new NBTTagList();

            for (java.util.Iterator<?> __recoveryIterator171_54420 =
                            (pendingBlockUpdates).iterator();
                    (__recoveryIterator171_54420).hasNext(); ) {
                NextTickListEntry nextTickListEntry =
                        (NextTickListEntry) (__recoveryIterator171_54420).next();
                CompoundTag compoundTagValue4 = new CompoundTag();
                ResourceLocation nameForObject =
                        (ResourceLocation)
                                Block.blockRegistry.getNameForObject(nextTickListEntry.getBlock());
                compoundTagValue4.setString(
                        packedTextFragments[6],
                        nameForObject == null ? packedTextFragments[7] : nameForObject.toString());
                compoundTagValue4.setInteger(
                        packedTextFragments[8], nextTickListEntry.position.getX());
                compoundTagValue4.setInteger(
                        packedTextFragments[9], nextTickListEntry.position.getY());
                compoundTagValue4.setInteger(
                        packedTextFragments[10], nextTickListEntry.position.getZ());
                compoundTagValue4.setInteger(
                        packedTextFragments[11], (int) (nextTickListEntry.scheduledTime - l1));
                compoundTagValue4.setInteger(packedTextFragments[12], nextTickListEntry.priority);
                nbtTagListValue3.appendTag(compoundTagValue4);
                if (textValue == null) {
                    break;
                }
            }

            compoundTag.setTag("TileTicks", nbtTagListValue3);
        }
    }

    public AnvilChunkLoader(File aKrValue) {
        this.pendingAnvilChunksCoordinates = Sets.newHashSet();
        this.syncLockObject = new Object();
        this.chunkSaveLocation = aKrValue;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public Chunk loadChunk(World world, int p171_1, int p171_2) throws IOException {
        net.minecraft.nbt.CompoundTag compoundTag = null;
        net.minecraft.world.ChunkCoordIntPair chunkCoordIntPair =
                new net.minecraft.world.ChunkCoordIntPair(p171_1, p171_2);
        Object monitor171 = this.syncLockObject;
        synchronized (this.syncLockObject) {
            if (this.pendingAnvilChunksCoordinates.contains(chunkCoordIntPair)) {
                for (int k = 0; k < this.chunksToRemove.size(); k++) {
                    if (((net.minecraft.world.chunk.storage.AnvilChunkLoaderPendingChunk)
                                    this.chunksToRemove.get(k))
                            .chunkCoordinate.equals(chunkCoordIntPair)) {
                        compoundTag =
                                ((net.minecraft.world.chunk.storage.AnvilChunkLoaderPendingChunk)
                                                this.chunksToRemove.get(k))
                                        .nbtTags;
                        break;
                    }
                }
            }
        }

        if (compoundTag == null) {
            java.io.DataInputStream datainputstream =
                    net.minecraft.world.chunk.storage.RegionFileCache.getChunkInputStream(
                            this.chunkSaveLocation, p171_1, p171_2);

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                if (datainputstream == null) {
                    return null;
                }
            } catch (java.io.IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.world.chunk.storage.AnvilChunkLoader.a(ioexception));
            }

            compoundTag = net.minecraft.nbt.CompressedStreamTools.read(datainputstream);
        }

        return this.checkedReadChunkFromNBT(world, p171_1, p171_2, compoundTag);
    }
}
