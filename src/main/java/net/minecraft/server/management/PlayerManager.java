package net.minecraft.server.management;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private int playerViewRadius;
    private long previousTotalWorldTime;
    private final List players = Lists.newArrayList();
    private WorldServer theWorldServer;
    private int[][] xzDirectionsConst;
    private List playerInstanceList;
    private List playerInstancesToUpdate;
    private static final Logger pmLogger = LogManager.getLogger();
    private final LongHashMap playerInstances = new LongHashMap();

    static LongHashMap access$200(PlayerManager playerManager) {
        return playerManager.playerInstances;
    }

    static Logger access$000() {
        return net.minecraft.server.management.PlayerManager.pmLogger;
    }

    public boolean isPlayerWatchingChunk(EntityPlayerMP entityPlayerMP, int i, int j) {
        PlayerManagerPlayerInstance playerInstance = this.getPlayerInstance(i, j, false);
        return playerInstance != null
                && PlayerManagerPlayerInstance.access$600(playerInstance).contains(entityPlayerMP)
                && !entityPlayerMP.loadedChunks.contains(
                        PlayerManagerPlayerInstance.access$500(playerInstance));
    }

    static List access$300(PlayerManager playerManager) {
        return playerManager.playerInstanceList;
    }

    public void updateMountedMovingPlayer(EntityPlayerMP entityPlayerMP) {
        int i = (int) entityPlayerMP.posX >> 4;
        ServerConfigurationManager.sN();

        int j = (int) entityPlayerMP.posZ >> 4;

        double d0 = entityPlayerMP.managedPosX - entityPlayerMP.posX;
        double d1 = entityPlayerMP.managedPosZ - entityPlayerMP.posZ;
        double d2 = d0 * d0 + d1 * d1;
        if (d2 >= 64.0) {
            int k = (int) entityPlayerMP.managedPosX >> 4;
            int l = (int) entityPlayerMP.managedPosZ >> 4;
            int playerViewRadiusValue = this.playerViewRadius;
            int j1 = i - k;
            int k1 = j - l;
            if (j1 != 0 || k1 != 0) {
                int l1 = i - playerViewRadiusValue;

                while (l1 <= i + playerViewRadiusValue) {
                    int i2 = j - playerViewRadiusValue;

                    while (i2 <= j + playerViewRadiusValue) {
                        if (!this.overlaps(
                                l1, i2, k, l, playerViewRadiusValue)) {
                            this.getPlayerInstance(l1, i2, true)
                                    .addPlayer(entityPlayerMP);
                        }

                        if (!this.overlaps(
                                l1 - j1, i2 - k1, i, j, playerViewRadiusValue)) {
                            PlayerManagerPlayerInstance playerInstance =
                                    this.getPlayerInstance(l1 - j1, i2 - k1, false);
                            if (playerInstance != null) {
                                playerInstance.removePlayer(entityPlayerMP);
                            }
                        }

                        i2++;
                    }

                    l1++;
                }

                this.filterChunkLoadQueue(entityPlayerMP);
                entityPlayerMP.managedPosX = entityPlayerMP.posX;
                entityPlayerMP.managedPosZ = entityPlayerMP.posZ;
            }
        }
    }

    private PlayerManagerPlayerInstance getPlayerInstance(int i, int j, boolean flag) {
        long l = i;

        l += 2147483647L;
        long i1 = j;

        long k = l | i1 + 2147483647L << 32;
        PlayerManagerPlayerInstance playerManagerPlayerInstance =
                (PlayerManagerPlayerInstance) this.playerInstances.getValueByKey(k);
        if (playerManagerPlayerInstance == null && flag) {
            playerManagerPlayerInstance = new PlayerManagerPlayerInstance(this, i, j);
            this.playerInstances.add(k, playerManagerPlayerInstance);
            this.playerInstanceList.add(playerManagerPlayerInstance);
        }

        return playerManagerPlayerInstance;
    }

    public void addPlayer(EntityPlayerMP entityPlayerMP) {
        int i = (int) entityPlayerMP.posX >> 4;
        ServerConfigurationManager.kr();

        int j = (int) entityPlayerMP.posZ >> 4;

        entityPlayerMP.managedPosX = entityPlayerMP.posX;
        entityPlayerMP.managedPosZ = entityPlayerMP.posZ;
        int k = i - this.playerViewRadius;

        while (k <= i + this.playerViewRadius) {
            int l = j - this.playerViewRadius;

            while (true) {
                if (l <= j + this.playerViewRadius) {
                    this.getPlayerInstance(k, l, true).addPlayer(entityPlayerMP);
                    l++;

                    continue;
                }

                k++;

                break;
            }
        }

        this.players.add(entityPlayerMP);
        this.filterChunkLoadQueue(entityPlayerMP);
    }

    public void setPlayerViewRadius(int playerViewRadiusValue) {
        boolean flag1 = ServerConfigurationManager.kr();
        playerViewRadiusValue = MathHelper.clamp(playerViewRadiusValue, 3, 32);
        boolean flag = flag1;
        if (playerViewRadiusValue != this.playerViewRadius) {
            int j = playerViewRadiusValue - this.playerViewRadius;

            for (java.util.Iterator<?> __recoveryIterator171_6369 =
                            (Lists.newArrayList(this.players)).iterator();
                    (__recoveryIterator171_6369).hasNext(); ) {
                EntityPlayerMP entityPlayerMP =
                        (EntityPlayerMP) (__recoveryIterator171_6369).next();
                label71:
                {
                    int k = (int) entityPlayerMP.posX >> 4;
                    int l = (int) entityPlayerMP.posZ >> 4;
                    if (j > 0) {
                        int i1 = k - playerViewRadiusValue;

                        do {
                            if (i1 > k + playerViewRadiusValue) {
                                break label71;
                            }

                            int j1 = l - playerViewRadiusValue;

                            while (j1 <= l + playerViewRadiusValue) {
                                PlayerManagerPlayerInstance playerInstance =
                                        this.getPlayerInstance(i1, j1, true);
                                if (!PlayerManagerPlayerInstance.access$600(playerInstance)
                                        .contains(entityPlayerMP)) {
                                    playerInstance.addPlayer(entityPlayerMP);
                                }

                                j1++;
                            }

                            i1++;
                        } while (!flag);
                    }

                    int k1 = k - this.playerViewRadius;

                    while (k1 <= k + this.playerViewRadius) {
                        int l1 = l - this.playerViewRadius;

                        while (l1 <= l + this.playerViewRadius) {
                            if (!this.overlaps(
                                    k1, l1, k, l, playerViewRadiusValue)) {
                                this.getPlayerInstance(k1, l1, true)
                                        .removePlayer(entityPlayerMP);
                            }

                            l1++;
                        }

                        k1++;
                    }
                }
            }

            this.playerViewRadius = playerViewRadiusValue;
        }
    }

    static List access$400(PlayerManager playerManager) {
        return playerManager.playerInstancesToUpdate;
    }

    public void removePlayer(EntityPlayerMP entityPlayerMP) {
        int i = (int) entityPlayerMP.managedPosX >> 4;
        ServerConfigurationManager.sN();

        int j = (int) entityPlayerMP.managedPosZ >> 4;
        int k = i - this.playerViewRadius;

        while (k <= i + this.playerViewRadius) {
            int l = j - this.playerViewRadius;

            while (true) {
                if (l <= j + this.playerViewRadius) {
                    PlayerManagerPlayerInstance playerInstance =
                            this.getPlayerInstance(k, l, false);
                    if (playerInstance != null) {
                        playerInstance.removePlayer(entityPlayerMP);
                    }

                    l++;

                    continue;
                }

                k++;

                break;
            }
        }

        this.players.remove(entityPlayerMP);
    }

    public PlayerManager(WorldServer bjdValue) {
        this.playerInstancesToUpdate = Lists.newArrayList();
        this.playerInstanceList = Lists.newArrayList();
        this.xzDirectionsConst = new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        this.theWorldServer = bjdValue;
        this.setPlayerViewRadius(
                bjdValue.getMinecraftServer().getConfigurationManager().getViewDistance());
    }

    static WorldServer access$100(PlayerManager playerManager) {
        return playerManager.theWorldServer;
    }

    public WorldServer getMinecraftServer() {
        return this.theWorldServer;
    }

    public static int getFurthestViewableBlock(int i) {
        return i * 16 - 16;
    }

    public void markBlockForUpdate(BlockPos blockPos) {
        int i = blockPos.getX() >> 4;
        int j = blockPos.getZ() >> 4;
        PlayerManagerPlayerInstance playerInstance = this.getPlayerInstance(i, j, false);
        if (playerInstance != null) {
            playerInstance.flagChunkForUpdate(
                    blockPos.getX() & 15, blockPos.getY(), blockPos.getZ() & 15);
        }
    }

    public boolean hasPlayerInstance(int i, int j) {
        long l = i;

        l += 2147483647L;
        long i1 = j;

        long k = l | i1 + 2147483647L << 32;
        return this.playerInstances.getValueByKey(k) != null;
    }

    public void updatePlayerInstances() {
        label35:
        {
            boolean flag1 = ServerConfigurationManager.sN();
            long previousTotalWorldTimeValue = this.theWorldServer.getTotalWorldTime();
            boolean flag = flag1;
            long l = previousTotalWorldTimeValue - this.previousTotalWorldTime;

            if (l > 8000L) {
                this.previousTotalWorldTime = previousTotalWorldTimeValue;
                int j = 0;

                do {
                    if (j >= this.playerInstanceList.size()) {
                        break label35;
                    }

                    PlayerManagerPlayerInstance playerManagerPlayerInstance =
                            (PlayerManagerPlayerInstance) this.playerInstanceList.get(j);
                    playerManagerPlayerInstance.onUpdate();
                    playerManagerPlayerInstance.processChunk();
                    j++;
                } while (flag);
            }

            int k = 0;

            while (k < this.playerInstancesToUpdate.size()) {
                PlayerManagerPlayerInstance playerManagerPlayerInstanceValue =
                        (PlayerManagerPlayerInstance) this.playerInstancesToUpdate.get(k);
                playerManagerPlayerInstanceValue.onUpdate();
                k++;
            }
        }

        this.playerInstancesToUpdate.clear();
        if (this.players.isEmpty()) {
            WorldProvider bFY = this.theWorldServer.provider;
            if (!bFY.canRespawnHere()) {
                this.theWorldServer.theChunkProviderServer.unloadAllChunks();
            }
        }
    }

    private boolean overlaps(int i, int j, int k, int l, int i1) {
        int j1 = i - k;
        int k1 = j - l;
        return j1 >= -i1 && j1 <= i1 ? k1 >= -i1 && k1 <= i1 : false;
    }

    public void filterChunkLoadQueue(EntityPlayerMP entityPlayerMP) {
        ArrayList arrayList = Lists.newArrayList(entityPlayerMP.loadedChunks);
        int i = 0;
        int j = this.playerViewRadius;
        ServerConfigurationManager.kr();

        int k = (int) entityPlayerMP.posX >> 4;
        int l = (int) entityPlayerMP.posZ >> 4;
        int i1 = 0;
        int j1 = 0;
        ChunkCoordIntPair chunkCoordIntPair =
                PlayerManagerPlayerInstance.access$500(this.getPlayerInstance(k, l, true));

        entityPlayerMP.loadedChunks.clear();
        if (arrayList.contains(chunkCoordIntPair)) {
            entityPlayerMP.loadedChunks.add(chunkCoordIntPair);
        }

        int k1 = 1;

        while (k1 <= j * 2) {
            int l1 = 0;

            while (true) {
                if (l1 < 2) {
                    int[] aint = this.xzDirectionsConst[i++ % 4];
                    int i2 = 0;

                    while (i2 < k1) {
                        i1 += aint[0];
                        j1 += aint[1];
                        chunkCoordIntPair =
                                PlayerManagerPlayerInstance.access$500(
                                        this.getPlayerInstance(k + i1, l + j1, true));
                        if (arrayList.contains(chunkCoordIntPair)) {
                            entityPlayerMP.loadedChunks.add(chunkCoordIntPair);
                        }

                        i2++;
                    }

                    l1++;

                    continue;
                }

                k1++;

                break;
            }
        }

        i %= 4;
        k1 = 0;

        while (k1 < j * 2) {
            i1 += this.xzDirectionsConst[i][0];
            j1 += this.xzDirectionsConst[i][1];
            chunkCoordIntPair =
                    PlayerManagerPlayerInstance.access$500(
                            this.getPlayerInstance(k + i1, l + j1, true));
            if (arrayList.contains(chunkCoordIntPair)) {
                entityPlayerMP.loadedChunks.add(chunkCoordIntPair);
            }

            k1++;
        }
    }
}
