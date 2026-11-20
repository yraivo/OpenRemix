package net.minecraft.server.management;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

import com.google.common.collect.Lists;

import java.util.List;

class PlayerManagerPlayerInstance {
    private long previousWorldTime;
    private int flagsYAreasToUpdate;
    private int numBlocksToUpdate;

    PlayerManager field_73265_a;
    private short[] locationOfBlockChange;
    private static String h;
    private ChunkCoordIntPair currentChunk;
    private List playersWatchingChunk;

    static {
        h = "Failed to add player. {} already is in chunk {}, {}";
    }

    private void increaseInhabitedTime(Chunk chunk) {
        chunk.setInhabitedTime(
                chunk.getInhabitedTime()
                        + PlayerManager.access$100(this.field_73265_a).getTotalWorldTime()
                        - this.previousWorldTime);
        this.previousWorldTime = PlayerManager.access$100(this.field_73265_a).getTotalWorldTime();
    }

    public void onUpdate() {
        boolean flag = ServerConfigurationManager.kr();
        if (this.numBlocksToUpdate != 0) {
            label57:
            {
                if (this.numBlocksToUpdate == 1) {
                    int i =
                            (this.locationOfBlockChange[0] >> 12 & 15)
                                    + this.currentChunk.chunkXPos * 16;
                    int j = this.locationOfBlockChange[0] & 255;
                    int k =
                            (this.locationOfBlockChange[0] >> 8 & 15)
                                    + this.currentChunk.chunkZPos * 16;
                    BlockPos blockPos = new BlockPos(i, j, k);
                    this.sendToAllPlayersWatchingChunk(
                            new S23PacketBlockChange(
                                    PlayerManager.access$100(this.field_73265_a), blockPos));
                    if (PlayerManager.access$100(this.field_73265_a)
                            .getBlockState(blockPos)
                            .getBlock()
                            .hasTileEntity()) {
                        this.sendTileToAllPlayersWatchingChunk(
                                PlayerManager.access$100(this.field_73265_a)
                                        .getTileEntity(blockPos));
                    }

                    break label57;
                }

                if (this.numBlocksToUpdate == 64) {
                    int i1 = this.currentChunk.chunkXPos * 16;
                    int k1 = this.currentChunk.chunkZPos * 16;
                    this.sendToAllPlayersWatchingChunk(
                            new S21PacketChunkData(
                                    PlayerManager.access$100(this.field_73265_a)
                                            .getChunkFromChunkCoords(
                                                    this.currentChunk.chunkXPos,
                                                    this.currentChunk.chunkZPos),
                                    false,
                                    this.flagsYAreasToUpdate));
                    int i2 = 0;

                    do {
                        if (i2 >= 16) {
                            break label57;
                        }

                        if ((this.flagsYAreasToUpdate & 1 << i2) != 0) {
                            int k2 = i2 << 4;
                            List list =
                                    PlayerManager.access$100(this.field_73265_a)
                                            .getTileEntitiesIn(
                                                    i1, k2, k1, i1 + 16, k2 + 16, k1 + 16);
                            int l = 0;

                            while (l < list.size()) {
                                this.sendTileToAllPlayersWatchingChunk((TileEntity) list.get(l));
                                l++;
                            }
                        }

                        i2++;
                    } while (!flag);
                }

                this.sendToAllPlayersWatchingChunk(
                        new S22PacketMultiBlockChange(
                                this.numBlocksToUpdate,
                                this.locationOfBlockChange,
                                PlayerManager.access$100(this.field_73265_a)
                                        .getChunkFromChunkCoords(
                                                this.currentChunk.chunkXPos,
                                                this.currentChunk.chunkZPos)));
                int j1 = 0;

                while (j1 < this.numBlocksToUpdate) {
                    int l1 =
                            (this.locationOfBlockChange[j1] >> 12 & 15)
                                    + this.currentChunk.chunkXPos * 16;
                    int j2 = this.locationOfBlockChange[j1] & 255;
                    int l2 =
                            (this.locationOfBlockChange[j1] >> 8 & 15)
                                    + this.currentChunk.chunkZPos * 16;
                    BlockPos blockPosValue = new BlockPos(l1, j2, l2);
                    if (PlayerManager.access$100(this.field_73265_a)
                            .getBlockState(blockPosValue)
                            .getBlock()
                            .hasTileEntity()) {
                        this.sendTileToAllPlayersWatchingChunk(
                                PlayerManager.access$100(this.field_73265_a)
                                        .getTileEntity(blockPosValue));
                    }

                    j1++;
                }
            }

            this.numBlocksToUpdate = 0;
            this.flagsYAreasToUpdate = 0;
        }
    }

    public void sendToAllPlayersWatchingChunk(Packet packet) {
        ServerConfigurationManager.sN();

        int i = 0;

        while (i < this.playersWatchingChunk.size()) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) this.playersWatchingChunk.get(i);
            if (!entityPlayerMP.loadedChunks.contains(this.currentChunk)) {
                entityPlayerMP.playerNetServerHandler.sendPacket(packet);
            }

            i++;
        }
    }

    public void flagChunkForUpdate(int i, int j, int k) {
        ServerConfigurationManager.kr();

        if (this.numBlocksToUpdate == 0) {
            PlayerManager.access$400(this.field_73265_a).add(this);
        }

        this.flagsYAreasToUpdate |= 1 << (j >> 4);
        if (this.numBlocksToUpdate < 64) {
            short short1 = (short) (i << 12 | k << 8 | j);
            int l = 0;

            while (l < this.numBlocksToUpdate) {
                if (this.locationOfBlockChange[l] == short1) {
                    return;
                }

                l++;
            }

            this.locationOfBlockChange[this.numBlocksToUpdate++] = short1;
        }
    }

    private void sendTileToAllPlayersWatchingChunk(TileEntity tileEntity) {
        if (tileEntity != null) {
            Packet packet = tileEntity.getDescriptionPacket();
            if (packet != null) {
                this.sendToAllPlayersWatchingChunk(packet);
            }
        }
    }

    public PlayerManagerPlayerInstance(PlayerManager bzbValue, int i, int j) {
        this.field_73265_a = bzbValue;
        this.playersWatchingChunk = Lists.newArrayList();
        this.locationOfBlockChange = new short[64];
        this.currentChunk = new ChunkCoordIntPair(i, j);
        bzbValue.getMinecraftServer().theChunkProviderServer.loadChunk(i, j);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static List access$600(PlayerManagerPlayerInstance playerManagerPlayerInstance) {
        return playerManagerPlayerInstance.playersWatchingChunk;
    }

    static ChunkCoordIntPair access$500(PlayerManagerPlayerInstance playerManagerPlayerInstance) {
        return playerManagerPlayerInstance.currentChunk;
    }

    public void removePlayer(EntityPlayerMP entityPlayerMP) {
        if (this.playersWatchingChunk.contains(entityPlayerMP)) {
            Chunk chunkFromChunkCoords =
                    PlayerManager.access$100(this.field_73265_a)
                            .getChunkFromChunkCoords(
                                    this.currentChunk.chunkXPos, this.currentChunk.chunkZPos);
            if (chunkFromChunkCoords.isPopulated()) {
                entityPlayerMP.playerNetServerHandler.sendPacket(
                        new S21PacketChunkData(chunkFromChunkCoords, true, 0));
            }

            this.playersWatchingChunk.remove(entityPlayerMP);
            entityPlayerMP.loadedChunks.remove(this.currentChunk);
            if (this.playersWatchingChunk.isEmpty()) {
                long j = this.currentChunk.chunkXPos;

                j += 2147483647L;
                long k = this.currentChunk.chunkZPos;

                long i = j | k + 2147483647L << 32;
                this.increaseInhabitedTime(chunkFromChunkCoords);
                PlayerManager.access$200(this.field_73265_a).remove(i);
                PlayerManager.access$300(this.field_73265_a).remove(this);
                if (this.numBlocksToUpdate > 0) {
                    PlayerManager.access$400(this.field_73265_a).remove(this);
                }

                this.field_73265_a
                        .getMinecraftServer()
                        .theChunkProviderServer
                        .dropChunk(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos);
            }
        }
    }

    public void processChunk() {
        this.increaseInhabitedTime(
                PlayerManager.access$100(this.field_73265_a)
                        .getChunkFromChunkCoords(
                                this.currentChunk.chunkXPos, this.currentChunk.chunkZPos));
    }

    public void addPlayer(EntityPlayerMP entityPlayerMP) {
        ServerConfigurationManager.sN();

        if (this.playersWatchingChunk.contains(entityPlayerMP)) {
            PlayerManager.access$000()
                    .debug(
                            h,
                            new Object[] {
                                entityPlayerMP,
                                this.currentChunk.chunkXPos,
                                this.currentChunk.chunkZPos
                            });

            return;
        }

        if (this.playersWatchingChunk.isEmpty()) {
            this.previousWorldTime =
                    PlayerManager.access$100(this.field_73265_a).getTotalWorldTime();
        }

        this.playersWatchingChunk.add(entityPlayerMP);
        entityPlayerMP.loadedChunks.add(this.currentChunk);
    }
}
