package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S38PacketPlayerListItemAction;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.network.play.server.S44PacketWorldBorderAction;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.demo.DemoWorldManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.WorldInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class ServerConfigurationManager {
    private UserListWhitelist whiteListedPlayers;
    private static SimpleDateFormat dateFormat;
    private static boolean ko;
    private UserListBans bannedPlayers;

    public final List playerEntityList = Lists.newArrayList();

    public static File FILE_PLAYERBANS;
    protected int maxPlayers;
    private int viewDistance;
    public static File FILE_OPS;
    public static File FILE_IPBANS;
    private static String[] packedTextFragments;
    private MinecraftServer mcServer;
    private IPlayerFileData playerNBTManagerObj;
    private BanList bannedIPs;
    private boolean whiteListEnforced;
    public final Map uuidToPlayerMap = Maps.newHashMap();
    private boolean commandsAllowedForAll;
    private static Logger logger;
    private WorldSettingsGameType gameType;
    private int playerPingIndex;
    private UserListOps ops;
    public static File FILE_WHITELIST;
    private Map playerStatFiles;

    public static boolean sN() {
        boolean flag = kr();
        return !flag;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public boolean canJoin(GameProfile gameprofile) {
        return !this.whiteListEnforced
                || this.ops.hasEntry(gameprofile)
                || this.whiteListedPlayers.hasEntry(gameprofile);
    }

    public CompoundTag readPlayerDataFromFile(EntityPlayerMP entityPlayerMP) {
        sN();

        CompoundTag compoundTag =
                this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();

        if (entityPlayerMP.getName().equals(this.mcServer.getServerOwner())
                && compoundTag != null) {
            entityPlayerMP.readFromNBT(compoundTag);
            CompoundTag compoundTagValue = compoundTag;
            net.minecraft.server.management.ServerConfigurationManager.logger.debug(
                    "loading single player");

            return compoundTagValue;
        }

        return this.playerNBTManagerObj.readPlayerData(entityPlayerMP);
    }

    public int getEntityViewDistance() {
        return PlayerManager.getFurthestViewableBlock(this.getViewDistance());
    }

    public UserListBans getBannedPlayers() {
        return this.bannedPlayers;
    }

    public EntityPlayerMP recreatePlayerEntity(
            EntityPlayerMP entityPlayerMP, int dimension, boolean flag) {
        boolean flag1;
        BlockPos blockPos;
        boolean flag2;
        Object object;
        {
            entityPlayerMP
                    .getServerForPlayer()
                    .getEntityTracker()
                    .removePlayerFromTrackers(entityPlayerMP);
            entityPlayerMP.getServerForPlayer().getEntityTracker().untrackEntity(entityPlayerMP);
            boolean flag3 = sN();
            entityPlayerMP.getServerForPlayer().getPlayerManager().removePlayer(entityPlayerMP);
            flag1 = flag3;
            this.playerEntityList.remove(entityPlayerMP);
            this.mcServer
                    .worldServerForDimension(entityPlayerMP.dimension)
                    .removePlayerEntityDangerously(entityPlayerMP);
            blockPos = entityPlayerMP.getBedLocation();
            flag2 = entityPlayerMP.isSpawnForced();
            entityPlayerMP.dimension = dimension;
            if (this.mcServer.isDemo()) {
                object =
                        new DemoWorldManager(
                                this.mcServer.worldServerForDimension(entityPlayerMP.dimension));

            } else {

                object =
                        new ItemInWorldManager(
                                this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
            }
        }

        EntityPlayerMP entityPlayerMPValue;
        WorldServer worldServer;
        entityPlayerMPValue =
                new EntityPlayerMP(
                        this.mcServer,
                        this.mcServer.worldServerForDimension(entityPlayerMP.dimension),
                        entityPlayerMP.getGameProfile(),
                        (ItemInWorldManager) object);
        entityPlayerMPValue.playerNetServerHandler = entityPlayerMP.playerNetServerHandler;
        entityPlayerMPValue.clonePlayer(entityPlayerMP, flag);
        entityPlayerMPValue.setEntityId(entityPlayerMP.getEntityId());
        entityPlayerMPValue.copyCommandStatsFrom(entityPlayerMP);
        worldServer = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        this.setPlayerGameTypeBasedOnOther(entityPlayerMPValue, entityPlayerMP, worldServer);
        if (blockPos != null) {
            BlockPos blockPosValue =
                    PlayerEntity.getBedSpawnLocation(
                            this.mcServer.worldServerForDimension(entityPlayerMP.dimension),
                            blockPos,
                            flag2);
            if (blockPosValue != null) {
                entityPlayerMPValue.setLocationAndAngles(
                        blockPosValue.getX() + 0.5F,
                        blockPosValue.getY() + 0.1F,
                        blockPosValue.getZ() + 0.5F,
                        0.0F,
                        0.0F);
                entityPlayerMPValue.setSpawnPoint(blockPos, flag2);

            } else {

                entityPlayerMPValue.playerNetServerHandler.sendPacket(
                        new S2BPacketChangeGameState(0, 0.0F));
            }
        }

        worldServer.theChunkProviderServer.loadChunk(
                (int) entityPlayerMPValue.posX >> 4, (int) entityPlayerMPValue.posZ >> 4);

        while (!worldServer
                        .getCollidingBoundingBoxes(
                                entityPlayerMPValue, entityPlayerMPValue.getEntityBoundingBox())
                        .isEmpty()
                && entityPlayerMPValue.posY < 256.0) {
            entityPlayerMPValue.setPosition(
                    entityPlayerMPValue.posX,
                    entityPlayerMPValue.posY + 1.0,
                    entityPlayerMPValue.posZ);
        }

        entityPlayerMPValue.playerNetServerHandler.sendPacket(
                new S07PacketRespawn(
                        entityPlayerMPValue.dimension,
                        entityPlayerMPValue.world.getDifficulty(),
                        entityPlayerMPValue.world.getWorldInfo().getTerrainType(),
                        entityPlayerMPValue.theItemInWorldManager.getGameType()));
        BlockPos blockPosValue2 = worldServer.getSpawnPoint();
        entityPlayerMPValue.playerNetServerHandler.setPlayerLocation(
                entityPlayerMPValue.posX,
                entityPlayerMPValue.posY,
                entityPlayerMPValue.posZ,
                entityPlayerMPValue.rotationYaw,
                entityPlayerMPValue.rotationPitch);
        entityPlayerMPValue.playerNetServerHandler.sendPacket(
                new S05PacketSpawnPosition(blockPosValue2));
        entityPlayerMPValue.playerNetServerHandler.sendPacket(
                new S1FPacketSetExperience(
                        entityPlayerMPValue.experience,
                        entityPlayerMPValue.experienceTotal,
                        entityPlayerMPValue.experienceLevel));
        this.updateTimeAndWeatherForPlayer(entityPlayerMPValue, worldServer);
        worldServer.getPlayerManager().addPlayer(entityPlayerMPValue);
        worldServer.spawnEntityInWorld(entityPlayerMPValue);
        this.playerEntityList.add(entityPlayerMPValue);
        this.uuidToPlayerMap.put(entityPlayerMPValue.getUniqueId(), entityPlayerMPValue);
        entityPlayerMPValue.addSelfToInternalCraftingInventory();
        entityPlayerMPValue.setHealth(entityPlayerMPValue.getHealth());
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            method_4801f4857dc1d2ca3fd8(!flag1);
        }

        return entityPlayerMPValue;
    }

    public String[] getAllUsernames() {
        sN();

        String[] astring = new String[this.playerEntityList.size()];

        int i = 0;

        while (i < this.playerEntityList.size()) {
            astring[i] = ((EntityPlayerMP) this.playerEntityList.get(i)).getName();
            i++;
        }

        return astring;
    }

    public void sendToAllNear(double d0, double d1, double d2, double d3, int i, Packet packet) {
        this.sendToAllNearExcept((PlayerEntity) null, d0, d1, d2, d3, i, packet);
    }

    public void sendToAllNearExcept(
            PlayerEntity playerEntity,
            double d0,
            double d1,
            double d2,
            double d3,
            int i,
            Packet packet) {
        kr();

        int j = 0;

        while (j < this.playerEntityList.size()) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) this.playerEntityList.get(j);
            if (entityPlayerMP != playerEntity && entityPlayerMP.dimension == i) {
                double d4 = d0 - entityPlayerMP.posX;
                double d5 = d1 - entityPlayerMP.posY;
                double d6 = d2 - entityPlayerMP.posZ;
                if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(packet);
                }
            }

            j++;
        }
    }

    public GameProfile[] getAllProfiles() {
        kr();

        GameProfile[] agameprofile = new GameProfile[this.playerEntityList.size()];

        int i = 0;

        while (i < this.playerEntityList.size()) {
            agameprofile[i] = ((EntityPlayerMP) this.playerEntityList.get(i)).getGameProfile();
            i++;
        }

        return agameprofile;
    }

    public void playerLoggedOut(EntityPlayerMP entityPlayerMP) {
        entityPlayerMP.triggerAchievement(StatList.leaveGameStat);
        this.writePlayerData(entityPlayerMP);
        WorldServer serverForPlayer = entityPlayerMP.getServerForPlayer();
        if (entityPlayerMP.ridingEntity != null) {
            serverForPlayer.removePlayerEntityDangerously(entityPlayerMP.ridingEntity);
            net.minecraft.server.management.ServerConfigurationManager.logger.debug(
                    "removing player mount");
        }

        serverForPlayer.removeEntity(entityPlayerMP);
        serverForPlayer.getPlayerManager().removePlayer(entityPlayerMP);
        this.playerEntityList.remove(entityPlayerMP);
        this.uuidToPlayerMap.remove(entityPlayerMP.getUniqueId());
        this.playerStatFiles.remove(entityPlayerMP.getUniqueId());
        this.sendPacketToAllPlayers(
                new S38PacketPlayerListItem(
                        S38PacketPlayerListItemAction.REMOVE_PLAYER,
                        new EntityPlayerMP[] {entityPlayerMP}));
    }

    public void sendPacketToAllPlayers(Packet packet) {
        kr();

        int i = 0;

        while (i < this.playerEntityList.size()) {
            ((EntityPlayerMP) this.playerEntityList.get(i))
                    .playerNetServerHandler.sendPacket(packet);
            i++;
        }
    }

    public String[] getWhitelistedPlayerNames() {
        return this.whiteListedPlayers.getKeys();
    }

    public String[] getAvailablePlayerDat() {
        return this.mcServer
                .worldServers[0]
                .getSaveHandler()
                .getPlayerNBTManager()
                .getAvailablePlayerDat();
    }

    public MinecraftServer getServerInstance() {
        return this.mcServer;
    }

    public void sendMessageToAllTeamMembers(
            PlayerEntity playerEntity, ChatComponent chatComponent) {
        kr();

        Team team = playerEntity.getTeam();

        if (team != null) {
            for (java.util.Iterator<?> __recoveryIterator171_14382 =
                            (team.getMembershipCollection()).iterator();
                    (__recoveryIterator171_14382).hasNext(); ) {
                String text = (String) (__recoveryIterator171_14382).next();
                EntityPlayerMP playerByUsername = this.getPlayerByUsername(text);
                if (playerByUsername != null && playerByUsername != playerEntity) {
                    playerByUsername.addChatMessage(chatComponent);
                }
            }
        }
    }

    public UserListWhitelist getWhitelistedPlayers() {
        return this.whiteListedPlayers;
    }

    public ServerConfigurationManager(MinecraftServer bzxValue) {
        this.bannedPlayers =
                new UserListBans(
                        net.minecraft.server.management.ServerConfigurationManager.FILE_PLAYERBANS);
        this.bannedIPs =
                new BanList(net.minecraft.server.management.ServerConfigurationManager.FILE_IPBANS);
        this.ops =
                new UserListOps(
                        net.minecraft.server.management.ServerConfigurationManager.FILE_OPS);
        this.whiteListedPlayers =
                new UserListWhitelist(
                        net.minecraft.server.management.ServerConfigurationManager.FILE_WHITELIST);
        this.playerStatFiles = Maps.newHashMap();
        this.mcServer = bzxValue;
        this.bannedPlayers.setLanServer(false);
        this.bannedIPs.setLanServer(false);
        this.maxPlayers = 8;
    }

    // region String decoding support

    // endregion

    public List getPlayersMatchingAddress(String text) {
        kr();

        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_16200 = (this.playerEntityList).iterator();
                (__recoveryIterator171_16200).hasNext(); ) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) (__recoveryIterator171_16200).next();
            if (entityPlayerMP.getPlayerIP().equals(text)) {
                arrayList.add(entityPlayerMP);
            }
        }

        return arrayList;
    }

    public String method_cee993844cce4b97ed62() {
        kr();

        String text = packedTextFragments[2];
        int i = 0;

        while (i < this.playerEntityList.size()) {
            if (i > 0) {
                text = text + ", ";
            }

            text = text + ((EntityPlayerMP) this.playerEntityList.get(i)).getName();
            i++;
        }

        return text;
    }

    public void playerLoggedIn(EntityPlayerMP entityPlayerMP) {
        kr();

        this.playerEntityList.add(entityPlayerMP);
        this.uuidToPlayerMap.put(entityPlayerMP.getUniqueId(), entityPlayerMP);

        this.sendPacketToAllPlayers(
                new S38PacketPlayerListItem(
                        S38PacketPlayerListItemAction.ADD_PLAYER,
                        new EntityPlayerMP[] {entityPlayerMP}));
        WorldServer worldServer = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        worldServer.spawnEntityInWorld(entityPlayerMP);
        this.preparePlayer(entityPlayerMP, (WorldServer) null);
        int i = 0;

        while (i < this.playerEntityList.size()) {
            EntityPlayerMP entityPlayerMPValue = (EntityPlayerMP) this.playerEntityList.get(i);
            entityPlayerMP.playerNetServerHandler.sendPacket(
                    new S38PacketPlayerListItem(
                            S38PacketPlayerListItemAction.ADD_PLAYER,
                            new EntityPlayerMP[] {entityPlayerMPValue}));
            i++;
        }
    }

    public void onTick() {
        if (++this.playerPingIndex > 600) {
            this.sendPacketToAllPlayers(
                    new S38PacketPlayerListItem(
                            S38PacketPlayerListItemAction.UPDATE_LATENCY, this.playerEntityList));
            this.playerPingIndex = 0;
        }
    }

    public void removeOp(GameProfile gameprofile) {
        this.ops.removeEntry(gameprofile);
    }

    public static void method_4801f4857dc1d2ca3fd8(boolean flag) {
        ko = flag;
    }

    public void addOp(GameProfile gameprofile) {
        this.ops.addEntry(new UserListOpsEntry(gameprofile, this.mcServer.getOpPermissionLevel()));
    }

    public EntityPlayerMP createPlayerForUser(GameProfile gameprofile) {
        sN();

        UUID uuid = PlayerEntity.getUUID(gameprofile);
        ArrayList arrayList = Lists.newArrayList();

        int i = 0;

        while (i < this.playerEntityList.size()) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) this.playerEntityList.get(i);
            if (entityPlayerMP.getUniqueId().equals(uuid)) {
                arrayList.add(entityPlayerMP);
            }

            i++;
        }

        for (java.util.Iterator<?> __recoveryIterator171_39998 = (arrayList).iterator();
                (__recoveryIterator171_39998).hasNext(); ) {
            EntityPlayerMP entityPlayerMPValue =
                    (EntityPlayerMP) (__recoveryIterator171_39998).next();
            entityPlayerMPValue.playerNetServerHandler.kickPlayerFromServer(
                    "You logged in from another location");
        }

        if (this.mcServer.isDemo()) {
            DemoWorldManager demoWorldManager =
                    new DemoWorldManager(this.mcServer.worldServerForDimension(0));

            return new EntityPlayerMP(
                    this.mcServer,
                    this.mcServer.worldServerForDimension(0),
                    gameprofile,
                    (ItemInWorldManager) demoWorldManager);
        }

        ItemInWorldManager itemInWorldManager =
                new ItemInWorldManager(this.mcServer.worldServerForDimension(0));
        return new EntityPlayerMP(
                this.mcServer,
                this.mcServer.worldServerForDimension(0),
                gameprofile,
                itemInWorldManager);
    }

    // region String decoding support

    // endregion

    public void preparePlayer(EntityPlayerMP entityPlayerMP, WorldServer worldServer) {
        WorldServer serverForPlayer = entityPlayerMP.getServerForPlayer();
        if (worldServer != null) {
            worldServer.getPlayerManager().removePlayer(entityPlayerMP);
        }

        serverForPlayer.getPlayerManager().addPlayer(entityPlayerMP);
        serverForPlayer.theChunkProviderServer.loadChunk(
                (int) entityPlayerMP.posX >> 4, (int) entityPlayerMP.posZ >> 4);
    }

    public boolean canSendCommands(GameProfile gameprofile) {
        return this.ops.hasEntry(gameprofile)
                || this.mcServer.isSinglePlayer()
                        && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed()
                        && this.mcServer.getServerOwner().equalsIgnoreCase(gameprofile.getName())
                || this.commandsAllowedForAll;
    }

    private void setPlayerGameTypeBasedOnOther(
            EntityPlayerMP entityPlayerMP, EntityPlayerMP entityPlayerMPValue, World world) {
        {
            sN();

            if (entityPlayerMPValue != null) {
                entityPlayerMP.theItemInWorldManager.setGameType(
                        entityPlayerMPValue.theItemInWorldManager.getGameType());

            } else {

                if (this.gameType != null) {
                    entityPlayerMP.theItemInWorldManager.setGameType(this.gameType);
                }
            }
        }

        entityPlayerMP.theItemInWorldManager.initializeGameType(world.getWorldInfo().getGameType());
    }

    // region String decoding support

    // endregion

    public void initializeConnectionToPlayer(
            NetworkManager networkManager, EntityPlayerMP entityPlayerMP) {
        GameProfile gameprofile = entityPlayerMP.getGameProfile();
        PlayerProfileCache playerProfileCache = this.mcServer.getPlayerProfileCache();
        sN();

        GameProfile gameprofile1 = playerProfileCache.getProfileByUUID(gameprofile.getId());

        String text = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();
        playerProfileCache.addEntry(gameprofile);
        CompoundTag compoundTag = this.readPlayerDataFromFile(entityPlayerMP);
        entityPlayerMP.setWorld(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
        entityPlayerMP.theItemInWorldManager.setWorld((WorldServer) entityPlayerMP.world);
        String textValue = "local";
        if (networkManager.getRemoteAddress() != null) {
            textValue = networkManager.getRemoteAddress().toString();
        }

        WorldServer worldServer;
        NetHandlerPlayServer netHandlerPlayServer;
        ChatComponentTranslation chatComponentTranslation;
        {
            net.minecraft.server.management.ServerConfigurationManager.logger.info(
                    entityPlayerMP.getName()
                            + packedTextFragments[0]
                            + textValue
                            + "] logged in with entity id "
                            + entityPlayerMP.getEntityId()
                            + " at ("
                            + entityPlayerMP.posX
                            + ", "
                            + entityPlayerMP.posY
                            + ", "
                            + entityPlayerMP.posZ
                            + packedTextFragments[1]);
            worldServer = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
            WorldInfo worldInfo = worldServer.getWorldInfo();
            BlockPos blockPos = worldServer.getSpawnPoint();
            this.setPlayerGameTypeBasedOnOther(entityPlayerMP, (EntityPlayerMP) null, worldServer);
            netHandlerPlayServer =
                    new NetHandlerPlayServer(this.mcServer, networkManager, entityPlayerMP);
            netHandlerPlayServer.sendPacket(
                    new S01PacketJoinGame(
                            entityPlayerMP.getEntityId(),
                            entityPlayerMP.theItemInWorldManager.getGameType(),
                            worldInfo.isHardcoreModeEnabled(),
                            worldServer.provider.getDimensionId(),
                            worldServer.getDifficulty(),
                            this.getMaxPlayers(),
                            worldInfo.getTerrainType(),
                            worldServer
                                    .getGameRules()
                                    .getGameRuleBooleanValue("reducedDebugInfo")));
            netHandlerPlayServer.sendPacket(
                    new S3FPacketCustomPayload(
                            "MC|Brand",
                            new PacketBuffer(Unpooled.buffer())
                                    .writeString(this.getServerInstance().getServerModName())));
            netHandlerPlayServer.sendPacket(
                    new S41PacketServerDifficulty(
                            worldInfo.getDifficulty(), worldInfo.isDifficultyLocked()));
            netHandlerPlayServer.sendPacket(new S05PacketSpawnPosition(blockPos));
            netHandlerPlayServer.sendPacket(
                    new S39PacketPlayerAbilities(entityPlayerMP.capabilities));
            netHandlerPlayServer.sendPacket(
                    new S09PacketHeldItemChange(entityPlayerMP.inventory.currentItem));
            entityPlayerMP.getStatFile().l();
            entityPlayerMP.getStatFile().sendAchievements(entityPlayerMP);
            this.sendScoreboard((ServerScoreboard) worldServer.getScoreboard(), entityPlayerMP);
            this.mcServer.refreshStatusNextTick();
            if (!entityPlayerMP.getName().equalsIgnoreCase(text)) {
                chatComponentTranslation =
                        new ChatComponentTranslation(
                                "multiplayer.player.joined.renamed",
                                new Object[] {entityPlayerMP.getDisplayName(), text});

            } else {

                chatComponentTranslation =
                        new ChatComponentTranslation(
                                "multiplayer.player.joined",
                                new Object[] {entityPlayerMP.getDisplayName()});
            }
        }

        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        this.sendChatMsg(chatComponentTranslation);
        this.playerLoggedIn(entityPlayerMP);
        netHandlerPlayServer.setPlayerLocation(
                entityPlayerMP.posX,
                entityPlayerMP.posY,
                entityPlayerMP.posZ,
                entityPlayerMP.rotationYaw,
                entityPlayerMP.rotationPitch);
        this.updateTimeAndWeatherForPlayer(entityPlayerMP, worldServer);
        if (this.mcServer.getResourcePackUrl().length() > 0) {
            entityPlayerMP.loadResourcePack(
                    this.mcServer.getResourcePackUrl(), this.mcServer.getResourcePackHash());
        }

        for (java.util.Iterator<?> __recoveryIterator171_49808 =
                        (entityPlayerMP.getActivePotionEffects()).iterator();
                (__recoveryIterator171_49808).hasNext(); ) {
            PotionEffect potionEffect = (PotionEffect) (__recoveryIterator171_49808).next();
            netHandlerPlayServer.sendPacket(
                    new S1DPacketEntityEffect(entityPlayerMP.getEntityId(), potionEffect));
        }

        entityPlayerMP.addSelfToInternalCraftingInventory();
        if (compoundTag != null && compoundTag.hasKey("Riding", 10)) {
            Entity entity =
                    EntityList.createEntityFromNBT(
                            compoundTag.getCompoundTag("Riding"), worldServer);
            if (entity != null) {
                entity.forceSpawn = true;
                worldServer.spawnEntityInWorld(entity);
                entityPlayerMP.mountEntity(entity);
                entity.forceSpawn = false;
            }
        }
    }

    public int getCurrentPlayerCount() {
        return this.playerEntityList.size();
    }

    public String allowUserToConnect(SocketAddress socketaddress, GameProfile gameprofile) {
        if (this.bannedPlayers.isBanned(gameprofile)) {
            UserListBansEntry entry = (UserListBansEntry) this.bannedPlayers.getEntry(gameprofile);
            String text = "You are banned from this server!\nReason: " + entry.getBanReason();
            if (entry.getBanEndDate() != null) {
                text =
                        text
                                + "\nYour ban will be removed on "
                                + net.minecraft.server.management.ServerConfigurationManager
                                        .dateFormat
                                        .format(entry.getBanEndDate());
            }

            return text;
        } else {
            if (!this.canJoin(gameprofile)) {
                return "You are not white-listed on this server!";
            }

            if (this.bannedIPs.isBanned(socketaddress)) {
                IPBanEntry banEntry = this.bannedIPs.getBanEntry(socketaddress);
                String textValue =
                        "Your IP address is banned from this server!\nReason: "
                                + banEntry.getBanReason();
                if (banEntry.getBanEndDate() != null) {
                    textValue =
                            textValue
                                    + "\nYour ban will be removed on "
                                    + net.minecraft.server.management.ServerConfigurationManager
                                            .dateFormat
                                            .format(banEntry.getBanEndDate());
                }

                return textValue;
            } else {
                return this.playerEntityList.size() >= this.maxPlayers
                        ? "The server is full!"
                        : null;
            }
        }
    }

    public void setCommandsAllowedForAll(boolean commandsAllowedForAllValue) {
        this.commandsAllowedForAll = commandsAllowedForAllValue;
    }

    public void setPlayerManager(WorldServer[] worldServerArray) {
        this.playerNBTManagerObj = worldServerArray[0].getSaveHandler().getPlayerNBTManager();
        worldServerArray[0].getWorldBorder().addListener(new PlayerWorldBorderListener(this));
    }

    public void a(int viewDistanceValue) {
        sN();

        this.viewDistance = viewDistanceValue;

        if (this.mcServer.worldServers != null) {
            for (WorldServer worldServer : this.mcServer.worldServers) {
                if (worldServer != null) {
                    worldServer.getPlayerManager().setPlayerViewRadius(viewDistanceValue);
                }
            }
        }
    }

    protected void writePlayerData(EntityPlayerMP entityPlayerMP) {
        this.playerNBTManagerObj.writePlayerData(entityPlayerMP);
        StatisticsFile statisticsFile =
                (StatisticsFile) this.playerStatFiles.get(entityPlayerMP.getUniqueId());
        if (statisticsFile != null) {
            statisticsFile.saveStatFile();
        }
    }

    public void iJ() {}

    public void transferPlayerToDimension(EntityPlayerMP entityPlayerMP, int dimension) {
        sN();

        int j = entityPlayerMP.dimension;
        WorldServer worldServer = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);

        entityPlayerMP.dimension = dimension;
        WorldServer worldServerValue =
                this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        entityPlayerMP.playerNetServerHandler.sendPacket(
                new S07PacketRespawn(
                        entityPlayerMP.dimension,
                        entityPlayerMP.world.getDifficulty(),
                        entityPlayerMP.world.getWorldInfo().getTerrainType(),
                        entityPlayerMP.theItemInWorldManager.getGameType()));
        worldServer.removePlayerEntityDangerously(entityPlayerMP);
        entityPlayerMP.isDead = false;
        this.transferEntityToWorld(entityPlayerMP, j, worldServer, worldServerValue);
        this.preparePlayer(entityPlayerMP, worldServer);
        entityPlayerMP.playerNetServerHandler.setPlayerLocation(
                entityPlayerMP.posX,
                entityPlayerMP.posY,
                entityPlayerMP.posZ,
                entityPlayerMP.rotationYaw,
                entityPlayerMP.rotationPitch);
        entityPlayerMP.theItemInWorldManager.setWorld(worldServerValue);
        this.updateTimeAndWeatherForPlayer(entityPlayerMP, worldServerValue);
        this.syncPlayerInventory(entityPlayerMP);

        for (java.util.Iterator<?> __recoveryIterator171_55710 =
                        (entityPlayerMP.getActivePotionEffects()).iterator();
                (__recoveryIterator171_55710).hasNext(); ) {
            PotionEffect potionEffect = (PotionEffect) (__recoveryIterator171_55710).next();
            entityPlayerMP.playerNetServerHandler.sendPacket(
                    new S1DPacketEntityEffect(entityPlayerMP.getEntityId(), potionEffect));
        }
    }

    public String[] getOppedPlayerNames() {
        return this.ops.getKeys();
    }

    public void syncPlayerInventory(EntityPlayerMP entityPlayerMP) {
        entityPlayerMP.sendContainerToPlayer(entityPlayerMP.inventoryContainer);
        entityPlayerMP.setPlayerHealthUpdated();
        entityPlayerMP.playerNetServerHandler.sendPacket(
                new S09PacketHeldItemChange(entityPlayerMP.inventory.currentItem));
    }

    static {
        net.minecraft.server.management.ServerConfigurationManager.packedTextFragments =
                new String[4];
        net.minecraft.server.management.ServerConfigurationManager.packedTextFragments[0] =
                new String("[");
        net.minecraft.server.management.ServerConfigurationManager.packedTextFragments[1] =
                new String(")");
        net.minecraft.server.management.ServerConfigurationManager.packedTextFragments[2] =
                new String("");
        method_4801f4857dc1d2ca3fd8(false);
        net.minecraft.server.management.ServerConfigurationManager.FILE_PLAYERBANS =
                new File("banned-players.json");
        net.minecraft.server.management.ServerConfigurationManager.FILE_IPBANS =
                new File("banned-ips.json");
        net.minecraft.server.management.ServerConfigurationManager.FILE_OPS = new File("ops.json");
        net.minecraft.server.management.ServerConfigurationManager.FILE_WHITELIST =
                new File("whitelist.json");
        net.minecraft.server.management.ServerConfigurationManager.logger = LogManager.getLogger();
        net.minecraft.server.management.ServerConfigurationManager.dateFormat =
                new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    }

    public BanList getBannedIPs() {
        return this.bannedIPs;
    }

    public void sendMessageToTeamOrEvryPlayer(
            PlayerEntity playerEntity, ChatComponent chatComponent) {
        kr();

        Team team = playerEntity.getTeam();

        if (team == null) {
            this.sendChatMsg(chatComponent);

            return;
        }

        int i = 0;

        while (i < this.playerEntityList.size()) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) this.playerEntityList.get(i);
            if (entityPlayerMP.getTeam() != team) {
                entityPlayerMP.addChatMessage(chatComponent);
            }

            i++;
        }
    }

    public void saveAllPlayerData() {
        sN();

        int i = 0;

        while (i < this.playerEntityList.size()) {
            this.writePlayerData((EntityPlayerMP) this.playerEntityList.get(i));
            i++;
        }
    }

    public void transferEntityToWorld(
            Entity entity, int i, WorldServer worldServer, WorldServer worldServerValue) {
        double d0;
        double d1;
        float f;
        {
            sN();

            d0 = entity.posX;
            d1 = entity.posZ;

            double d2 = 8.0;
            f = entity.rotationYaw;
            worldServer.theProfiler.startSection("moving");
            if (entity.dimension == -1) {
                d0 =
                        MathHelper.clamp(
                                d0 / d2,
                                worldServerValue.getWorldBorder().minX() + 16.0,
                                worldServerValue.getWorldBorder().maxX() - 16.0);
                d1 =
                        MathHelper.clamp(
                                d1 / d2,
                                worldServerValue.getWorldBorder().minZ() + 16.0,
                                worldServerValue.getWorldBorder().maxZ() - 16.0);
                entity.setLocationAndAngles(
                        d0, entity.posY, d1, entity.rotationYaw, entity.rotationPitch);
                if (!(!entity.isEntityAlive())) {

                    worldServer.updateEntityWithOptionalForce(entity, false);
                }
            } else {

                if (entity.dimension == 0) {
                    d0 =
                            MathHelper.clamp(
                                    d0 * d2,
                                    worldServerValue.getWorldBorder().minX() + 16.0,
                                    worldServerValue.getWorldBorder().maxX() - 16.0);
                    d1 =
                            MathHelper.clamp(
                                    d1 * d2,
                                    worldServerValue.getWorldBorder().minZ() + 16.0,
                                    worldServerValue.getWorldBorder().maxZ() - 16.0);
                    entity.setLocationAndAngles(
                            d0, entity.posY, d1, entity.rotationYaw, entity.rotationPitch);
                    if (!(!entity.isEntityAlive())) {

                        worldServer.updateEntityWithOptionalForce(entity, false);
                    }
                } else {

                    BlockPos blockPos;
                    {
                        if (i == 1) {
                            blockPos = worldServerValue.getSpawnPoint();

                        } else {

                            blockPos = worldServerValue.getSpawnCoordinate();
                        }
                    }

                    d0 = blockPos.getX();
                    entity.posY = blockPos.getY();
                    d1 = blockPos.getZ();
                    entity.setLocationAndAngles(d0, entity.posY, d1, 90.0F, 0.0F);
                    if (entity.isEntityAlive()) {
                        worldServer.updateEntityWithOptionalForce(entity, false);
                    }
                }
            }
        }

        worldServer.theProfiler.endSection();
        if (i != 1) {
            worldServer.theProfiler.startSection("placing");
            d0 = MathHelper.clamp((int) d0, -29999872, 29999872);
            d1 = MathHelper.clamp((int) d1, -29999872, 29999872);
            if (entity.isEntityAlive()) {
                entity.setLocationAndAngles(
                        d0, entity.posY, d1, entity.rotationYaw, entity.rotationPitch);
                worldServerValue.getDefaultTeleporter().placeInPortal(entity, f);
                worldServerValue.spawnEntityInWorld(entity);
                worldServerValue.updateEntityWithOptionalForce(entity, false);
            }

            worldServer.theProfiler.endSection();
        }

        entity.setWorld(worldServerValue);
    }

    public void removePlayerFromWhitelist(GameProfile gameprofile) {
        this.whiteListedPlayers.removeEntry(gameprofile);
    }

    public void sendChatMsgImpl(ChatComponent chatComponent, boolean flag) {
        this.mcServer.addChatMessage(chatComponent);
        int i = flag ? 1 : 0;
        this.sendPacketToAllPlayers(new S02PacketChat(chatComponent, (byte) i));
    }

    // region String decoding support

    // endregion

    public void removeAllPlayers() {
        sN();

        int i = 0;

        while (i < this.playerEntityList.size()) {
            ((EntityPlayerMP) this.playerEntityList.get(i))
                    .playerNetServerHandler.kickPlayerFromServer("Server closed");
            i++;
        }
    }

    public EntityPlayerMP getPlayerByUsername(String text) {
        for (java.util.Iterator<?> __recoveryIterator171_69048 = (this.playerEntityList).iterator();
                (__recoveryIterator171_69048).hasNext(); ) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) (__recoveryIterator171_69048).next();
            if (entityPlayerMP.getName().equalsIgnoreCase(text)) {
                return entityPlayerMP;
            }
        }

        return null;
    }

    public void setGameType(WorldSettingsGameType bzBValue) {
        this.gameType = bzBValue;
    }

    public void updateTimeAndWeatherForPlayer(
            EntityPlayerMP entityPlayerMP, WorldServer worldServer) {
        WorldBorder worldBorder = this.mcServer.worldServers[0].getWorldBorder();
        entityPlayerMP.playerNetServerHandler.sendPacket(
                new S44PacketWorldBorder(worldBorder, S44PacketWorldBorderAction.INITIALIZE));
        entityPlayerMP.playerNetServerHandler.sendPacket(
                new S03PacketTimeUpdate(
                        worldServer.getTotalWorldTime(),
                        worldServer.getWorldTime(),
                        worldServer.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));
        if (worldServer.isRaining()) {
            entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(1, 0.0F));
            entityPlayerMP.playerNetServerHandler.sendPacket(
                    new S2BPacketChangeGameState(7, worldServer.getRainStrength(1.0F)));
            entityPlayerMP.playerNetServerHandler.sendPacket(
                    new S2BPacketChangeGameState(8, worldServer.getWeightedThunderStrength(1.0F)));
        }
    }

    public CompoundTag getHostPlayerData() {
        return null;
    }

    public UserListOps getOppedPlayers() {
        return this.ops;
    }

    public void addWhitelistedPlayer(GameProfile gameprofile) {
        this.whiteListedPlayers.addEntry(new UserListWhitelistEntry(gameprofile));
    }

    public void serverUpdateMountedMovingPlayer(EntityPlayerMP entityPlayerMP) {
        entityPlayerMP
                .getServerForPlayer()
                .getPlayerManager()
                .updateMountedMovingPlayer(entityPlayerMP);
    }

    public void setWhiteListEnabled(boolean whiteListEnforcedValue) {
        this.whiteListEnforced = whiteListEnforcedValue;
    }

    protected void sendScoreboard(
            ServerScoreboard serverScoreboard, EntityPlayerMP entityPlayerMP) {
        HashSet hashset = Sets.newHashSet();
        kr();

        Iterator iterator = serverScoreboard.getTeams().iterator();

        while (iterator.hasNext()) {
            ScorePlayerTeam scorePlayerTeam = (ScorePlayerTeam) iterator.next();
            entityPlayerMP.playerNetServerHandler.sendPacket(
                    new S3EPacketTeams(scorePlayerTeam, 0));
        }

        int i = 0;

        while (i < 19) {
            ScoreObjective objectiveInDisplaySlot = serverScoreboard.getObjectiveInDisplaySlot(i);
            if (objectiveInDisplaySlot != null && !hashset.contains(objectiveInDisplaySlot)) {
                for (java.util.Iterator<?> __recoveryIterator171_72549 =
                                (serverScoreboard.method_28eca892baac1edc65ff(
                                                objectiveInDisplaySlot))
                                        .iterator();
                        (__recoveryIterator171_72549).hasNext(); ) {
                    Packet packet = (Packet) (__recoveryIterator171_72549).next();
                    entityPlayerMP.playerNetServerHandler.sendPacket(packet);
                }

                hashset.add(objectiveInDisplaySlot);
            }

            i++;
        }
    }

    public static boolean kr() {
        return ko;
    }

    public void sendChatMsg(ChatComponent chatComponent) {
        this.sendChatMsgImpl(chatComponent, true);
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public EntityPlayerMP getPlayerByUUID(UUID uuid) {
        return (EntityPlayerMP) this.uuidToPlayerMap.get(uuid);
    }

    public StatisticsFile getPlayerStatsFile(PlayerEntity playerEntity) {
        UUID uuid = playerEntity.getUniqueId();
        StatisticsFile statisticsFile =
                uuid == null ? null : (StatisticsFile) this.playerStatFiles.get(uuid);
        if (statisticsFile == null) {
            File file1 =
                    new File(
                            this.mcServer
                                    .worldServerForDimension(0)
                                    .getSaveHandler()
                                    .getWorldDirectory(),
                            "stats");
            File file2 = new File(file1, uuid.toString() + ".json");
            if (!file2.exists()) {
                File file3 = new File(file1, playerEntity.getName() + ".json");
                if (file3.exists() && file3.isFile()) {
                    file3.renameTo(file2);
                }
            }

            statisticsFile = new StatisticsFile(this.mcServer, file2);
            statisticsFile.readStatFile();
            this.playerStatFiles.put(uuid, statisticsFile);
        }

        return statisticsFile;
    }

    public void sendPacketToAllPlayersInDimension(Packet packet, int i) {
        kr();

        int j = 0;

        while (j < this.playerEntityList.size()) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) this.playerEntityList.get(j);
            if (entityPlayerMP.dimension == i) {
                entityPlayerMP.playerNetServerHandler.sendPacket(packet);
            }

            j++;
        }
    }

    // Helper used only by this class.
    static class PlayerWorldBorderListener implements net.minecraft.world.border.IBorderListener {
        net.minecraft.server.management.ServerConfigurationManager field_177697_a;

        public void onWarningDistanceChanged(
                net.minecraft.world.border.WorldBorder worldBorder, int i) {
            this.field_177697_a.sendPacketToAllPlayers(
                    new net.minecraft.network.play.server.S44PacketWorldBorder(
                            worldBorder,
                            net.minecraft.network.play.server.S44PacketWorldBorderAction
                                    .SET_WARNING_BLOCKS));
        }

        PlayerWorldBorderListener(
                net.minecraft.server.management.ServerConfigurationManager bzFValue) {
            this.field_177697_a = bzFValue;
        }

        public void onCenterChanged(
                net.minecraft.world.border.WorldBorder worldBorder, double d0, double d1) {
            this.field_177697_a.sendPacketToAllPlayers(
                    new net.minecraft.network.play.server.S44PacketWorldBorder(
                            worldBorder,
                            net.minecraft.network.play.server.S44PacketWorldBorderAction
                                    .SET_CENTER));
        }

        public void onDamageBufferChanged(
                net.minecraft.world.border.WorldBorder worldBorder, double d0) {}

        public void onSizeChanged(net.minecraft.world.border.WorldBorder worldBorder, double d0) {
            this.field_177697_a.sendPacketToAllPlayers(
                    new net.minecraft.network.play.server.S44PacketWorldBorder(
                            worldBorder,
                            net.minecraft.network.play.server.S44PacketWorldBorderAction.SET_SIZE));
        }

        public void onWarningTimeChanged(
                net.minecraft.world.border.WorldBorder worldBorder, int i) {
            this.field_177697_a.sendPacketToAllPlayers(
                    new net.minecraft.network.play.server.S44PacketWorldBorder(
                            worldBorder,
                            net.minecraft.network.play.server.S44PacketWorldBorderAction
                                    .SET_WARNING_TIME));
        }

        public void onTransitionStarted(
                net.minecraft.world.border.WorldBorder worldBorder, double d0, double d1, long i) {
            this.field_177697_a.sendPacketToAllPlayers(
                    new net.minecraft.network.play.server.S44PacketWorldBorder(
                            worldBorder,
                            net.minecraft.network.play.server.S44PacketWorldBorderAction
                                    .LERP_SIZE));
        }

        public void onDamageAmountChanged(
                net.minecraft.world.border.WorldBorder worldBorder, double d0) {}
    }
}
