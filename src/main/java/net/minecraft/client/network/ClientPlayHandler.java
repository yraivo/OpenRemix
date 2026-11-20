package net.minecraft.client.network;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;

import net.minecraft.block.Block;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.GuardianSound;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.GuiScreenDemo;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerDataServerResourceMode;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.particle.EntityPickupFX;
import net.minecraft.client.player.inventory.ContainerLocalMenu;
import net.minecraft.client.player.inventory.LocalBlockIntercommunication;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.MetadataAchievement;
import net.minecraft.client.stream.MetadataCombat;
import net.minecraft.client.stream.MetadataPlayerDeath;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartEnumMinecartType;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.C19PacketResourcePackStatusAction;
import net.minecraft.network.play.client.PlayerPositionLookPacket;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S20PacketEntityPropertiesSnapshot;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S22PacketMultiBlockChangeBlockUpdateData;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S38PacketPlayerListItemAction;
import net.minecraft.network.play.server.S38PacketPlayerListItemAddPlayerData;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S43PacketCamera;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S45PacketTitleType;
import net.minecraft.network.play.server.S46PacketSetCompressionLevel;
import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.TeamEnumVisible;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.Explosion;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import remix.client.RemixClient;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.ui.screen.RemixMainMenuScreen;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class ClientPlayHandler implements INetHandlerPlayClient {
    private GuiScreen guiScreenServer;
    public int currentServerMaxPlayers;
    private static String[] packedTextFragments;
    private boolean doneLoadingTerrain;
    private ClientWorld clientWorldController;

    private boolean hasReceivedStatistics;
    private static String[] legacyPlayHandlerFlowMarker;
    private static Logger logger;
    private Random avRandomizer;
    private MinecraftClient gameController;
    private GameProfile profile;

    private NetworkManager netManager;
    private final Map<UUID, PlayerInfo> playerInfoMap = Maps.newHashMap();

    public void handleCloseWindow(S2EPacketCloseWindow s2EPacketCloseWindow) {
        PacketThreadUtil.checkThreadAndEnqueue(s2EPacketCloseWindow, this, this.gameController);
        this.gameController.player.closeScreenAndDropStack();
    }

    public void handleCollectItem(S0DPacketCollectItem s0DPacketCollectItem) {
        PacketThreadUtil.checkThreadAndEnqueue(s0DPacketCollectItem, this, this.gameController);
        getLegacyPlayHandlerFlowMarker();

        Entity entity =
                this.clientWorldController.getEntityByID(
                        s0DPacketCollectItem.getCollectedItemEntityID());

        Object entityByID =
                this.clientWorldController.getEntityByID(s0DPacketCollectItem.getEntityID());
        if (entityByID == null) {
            entityByID = this.gameController.player;
        }

        if (entity != null) {
            {
                if (entity instanceof EntityXPOrb) {
                    this.clientWorldController.playSoundAtEntity(
                            entity,
                            "random.orb",
                            0.2F,
                            ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F
                                            + 1.0F)
                                    * 2.0F);

                } else {

                    this.clientWorldController.playSoundAtEntity(
                            entity,
                            "random.pop",
                            0.2F,
                            ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F
                                            + 1.0F)
                                    * 2.0F);
                }
            }

            this.gameController.effectRenderer.addEffect(
                    new EntityPickupFX(
                            this.clientWorldController, entity, (Entity) entityByID, 0.5F));
            this.clientWorldController.removeEntityFromWorld(
                    s0DPacketCollectItem.getCollectedItemEntityID());
        }
    }

    // region Static initialization and string tables
    static {
        net.minecraft.client.network.ClientPlayHandler.packedTextFragments = new String[5];
        net.minecraft.client.network.ClientPlayHandler.packedTextFragments[0] = new String("");
        net.minecraft.client.network.ClientPlayHandler.packedTextFragments[1] = new String("");
        net.minecraft.client.network.ClientPlayHandler.packedTextFragments[2] = new String("");
        net.minecraft.client.network.ClientPlayHandler.packedTextFragments[3] = new String(")");
        String[] astring1 = new String[2];
        setLegacyPlayHandlerFlowMarker(astring1);
        net.minecraft.client.network.ClientPlayHandler.logger = LogManager.getLogger();
    }

    // endregion

    public Collection<PlayerInfo> getPlayerInfoMap() {
        return this.playerInfoMap.values();
    }

    public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect s1EPacketRemoveEntityEffect) {
        PacketThreadUtil.checkThreadAndEnqueue(
                s1EPacketRemoveEntityEffect, this, this.gameController);
        Entity entity =
                this.clientWorldController.getEntityByID(s1EPacketRemoveEntityEffect.getEntityId());
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity)
                    .removePotionEffectClient(s1EPacketRemoveEntityEffect.getEffectId());
        }
    }

    public ClientPlayHandler(
            MinecraftClient asgValue,
            GuiScreen awOValue,
            NetworkManager awKValue,
            GameProfile gameprofile) {
        this.avRandomizer = new Random();
        this.currentServerMaxPlayers = 20;
        this.hasReceivedStatistics = false;
        this.gameController = asgValue;
        this.guiScreenServer = awOValue;
        this.netManager = awKValue;
        this.profile = gameprofile;
    }

    public void handleEntityStatus(S19PacketEntityStatus s19PacketEntityStatus) {
        getLegacyPlayHandlerFlowMarker();

        PacketThreadUtil.checkThreadAndEnqueue(s19PacketEntityStatus, this, this.gameController);
        Entity entity = s19PacketEntityStatus.getEntity(this.clientWorldController);

        if (entity != null) {
            byte b0 = s19PacketEntityStatus.getOpCode();
            if (b0 == 21) {
                this.gameController
                        .getSoundHandler()
                        .playSound(new GuardianSound((EntityGuardian) entity));

                return;
            }

            entity.handleHealthUpdate(b0);
        }
    }

    public void handleMapChunkBulk(S26PacketMapChunkBulk s26PacketMapChunkBulk) {
        PacketThreadUtil.checkThreadAndEnqueue(s26PacketMapChunkBulk, this, this.gameController);
        getLegacyPlayHandlerFlowMarker();

        ClientWorld awQValue = this.clientWorldController;
        int i = 0;

        while (i < s26PacketMapChunkBulk.getChunkCount()) {
            int j = s26PacketMapChunkBulk.getChunkX(i);
            int k = s26PacketMapChunkBulk.getChunkZ(i);
            awQValue.doPreChunk(j, k, true);
            awQValue.invalidateBlockReceiveRegion(
                    j << 4, 0, k << 4, (j << 4) + 15, 256, (k << 4) + 15);
            Chunk chunkFromChunkCoords = awQValue.getChunkFromChunkCoords(j, k);
            chunkFromChunkCoords.fillChunk(
                    s26PacketMapChunkBulk.getChunkBytes(i),
                    s26PacketMapChunkBulk.getChunkSize(i),
                    true);
            awQValue.markBlockRangeForRenderUpdate(
                    j << 4, 0, k << 4, (j << 4) + 15, 256, (k << 4) + 15);
            if (!(awQValue.provider instanceof WorldProviderSurface)) {
                chunkFromChunkCoords.resetRelightChecks();
            }

            i++;
        }
    }

    public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb s11PacketSpawnExperienceOrb) {
        PacketThreadUtil.checkThreadAndEnqueue(
                s11PacketSpawnExperienceOrb, this, this.gameController);
        int x = s11PacketSpawnExperienceOrb.getX();
        int j = s11PacketSpawnExperienceOrb.getY();
        int k = s11PacketSpawnExperienceOrb.getZ();
        EntityXPOrb entityXPOrb =
                new EntityXPOrb(
                        this.clientWorldController,
                        x,
                        j,
                        k,
                        s11PacketSpawnExperienceOrb.getXPValue());
        entityXPOrb.serverPosX = x;
        entityXPOrb.serverPosY = j;
        entityXPOrb.serverPosZ = k;
        entityXPOrb.rotationYaw = 0.0F;
        entityXPOrb.rotationPitch = 0.0F;
        int l = s11PacketSpawnExperienceOrb.getEntityID();
        entityXPOrb.setEntityId(l);
        this.clientWorldController.addEntityToWorld(l, entityXPOrb);
    }

    public PlayerInfo getPlayerInfo(String text) {
        for (java.util.Iterator<?> __recoveryIterator171_33321 =
                        (this.playerInfoMap.values()).iterator();
                (__recoveryIterator171_33321).hasNext(); ) {
            PlayerInfo playerInfo = (PlayerInfo) (__recoveryIterator171_33321).next();
            if (playerInfo.getGameProfile().getName().equals(text)) {
                return playerInfo;
            }
        }

        return null;
    }

    public void handleSpawnPlayer(S0CPacketSpawnPlayer packet) {
        PacketThreadUtil.checkThreadAndEnqueue(packet, this, this.gameController);
        int serverY = packet.getY();
        int serverX = packet.getX();
        int serverZ = packet.getZ();
        double x = serverX / 32.0;
        double y = serverY / 32.0;
        double z = serverZ / 32.0;
        float yaw = packet.getYaw() * 360 / 256.0F;
        float pitch = packet.getPitch() * 360 / 256.0F;
        EntityOtherPlayerMP player =
                new EntityOtherPlayerMP(
                        this.gameController.world,
                        this.getPlayerInfo(packet.getPlayer()).getGameProfile());
        player.prevPosX = player.lastTickPosX = player.serverPosX = serverX;
        getLegacyPlayHandlerFlowMarker();
        player.prevPosY = player.lastTickPosY = player.serverPosY = serverY;
        player.prevPosZ = player.lastTickPosZ = player.serverPosZ = serverZ;

        int heldItemId = packet.getCurrentItemID();
        player.inventory.mainInventory[player.inventory.currentItem] =
                heldItemId == 0 ? null : new ItemStack(Item.getItemById(heldItemId), 1, 0);

        player.setPositionAndRotation(x, y, z, yaw, pitch);
        this.clientWorldController.addEntityToWorld(packet.getEntityID(), player);
        List metadata = packet.getWatchableObjects();
        if (metadata != null) {
            player.getDataWatcher().updateWatchedObjectsFromList(metadata);
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyPlayHandlerFlowMarker(new String[2]);
        }
    }

    public void handleExplosion(S27PacketExplosion s27PacketExplosion) {
        PacketThreadUtil.checkThreadAndEnqueue(s27PacketExplosion, this, this.gameController);
        Explosion explosion =
                new Explosion(
                        this.gameController.world,
                        null,
                        s27PacketExplosion.getX(),
                        s27PacketExplosion.getY(),
                        s27PacketExplosion.getZ(),
                        s27PacketExplosion.getStrength(),
                        s27PacketExplosion.getAffectedBlockPositions());
        explosion.doExplosionB(true);
        this.gameController.player.motionX =
                this.gameController.player.motionX + s27PacketExplosion.getMotionX();
        this.gameController.player.motionY =
                this.gameController.player.motionY + s27PacketExplosion.getMotionY();
        this.gameController.player.motionZ =
                this.gameController.player.motionZ + s27PacketExplosion.getMotionZ();
    }

    // region String decoding support

    // endregion

    public void handleEntityAttach(S1BPacketEntityAttach s1BPacketEntityAttach) {
        PacketThreadUtil.checkThreadAndEnqueue(s1BPacketEntityAttach, this, this.gameController);
        Object entityByID =
                this.clientWorldController.getEntityByID(s1BPacketEntityAttach.getEntityId());
        getLegacyPlayHandlerFlowMarker();

        Entity entity =
                this.clientWorldController.getEntityByID(
                        s1BPacketEntityAttach.getVehicleEntityId());

        if (s1BPacketEntityAttach.getLeash() == 0) {
            byte b0;
            {
                b0 = 0;
                if (s1BPacketEntityAttach.getEntityId()
                        == this.gameController.player.getEntityId()) {
                    entityByID = this.gameController.player;
                    if (entity instanceof EntityBoat) {
                        ((EntityBoat) entity).setIsBoatEmpty(false);
                    }

                    b0 =
                            (byte)
                                    (((Entity) entityByID).ridingEntity == null && entity != null
                                            ? 1
                                            : 0);

                } else {

                    if (entity instanceof EntityBoat) {
                        ((EntityBoat) entity).setIsBoatEmpty(true);
                    }
                }
            }

            if (entityByID == null) {
                return;
            }

            ((net.minecraft.entity.Entity) (entityByID)).mountEntity(entity);
            if (b0 != 0) {
                GameSettings gameSettings = this.gameController.gameSettings;
                this.gameController.ingameGUI.setRecordPlaying(
                        I18n.format(
                                "mount.onboard",
                                new Object[] {
                                    GameSettings.getKeyDisplayString(
                                            gameSettings.keyBindSneak.getKeyCode())
                                }),
                        false);
            }

            return;
        }

        if (s1BPacketEntityAttach.getLeash() == 1 && entityByID instanceof EntityLiving) {
            if (entity != null) {
                ((EntityLiving) entityByID).setLeashedToEntity(entity, false);

                return;
            }

            ((EntityLiving) entityByID).clearLeashed(false, false);
        }
    }

    public void handleCamera(S43PacketCamera s43PacketCamera) {
        PacketThreadUtil.checkThreadAndEnqueue(s43PacketCamera, this, this.gameController);
        Entity entity = s43PacketCamera.getEntity(this.clientWorldController);
        if (entity != null) {
            this.gameController.setRenderViewEntity(entity);
        }
    }

    public void handleEntityNBT(S49PacketUpdateEntityNBT s49PacketUpdateEntityNBT) {
        PacketThreadUtil.checkThreadAndEnqueue(s49PacketUpdateEntityNBT, this, this.gameController);
        Entity entity = s49PacketUpdateEntityNBT.getEntity(this.clientWorldController);
        if (entity != null) {
            entity.clientUpdateEntityNBT(s49PacketUpdateEntityNBT.getTagCompound());
        }
    }

    public static void setLegacyPlayHandlerFlowMarker(String[] astring) {
        net.minecraft.client.network.ClientPlayHandler.legacyPlayHandlerFlowMarker = astring;
    }

    public void handleUpdateTileEntity(S35PacketUpdateTileEntity s35PacketUpdateTileEntity) {
        PacketThreadUtil.checkThreadAndEnqueue(
                s35PacketUpdateTileEntity, this, this.gameController);
        if (this.gameController.world.isBlockLoaded(s35PacketUpdateTileEntity.getPos())) {
            TileEntity tileEntity =
                    this.gameController.world.getTileEntity(s35PacketUpdateTileEntity.getPos());
            int tileEntityType = s35PacketUpdateTileEntity.getTileEntityType();
            if (tileEntityType == 1 && tileEntity instanceof TileEntityMobSpawner
                    || tileEntityType == 2 && tileEntity instanceof TileEntityCommandBlock
                    || tileEntityType == 3 && tileEntity instanceof TileEntityBeacon
                    || tileEntityType == 4 && tileEntity instanceof TileEntitySkull
                    || tileEntityType == 5 && tileEntity instanceof TileEntityFlowerPot
                    || tileEntityType == 6 && tileEntity instanceof TileEntityBanner) {
                tileEntity.readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
            }
        }
    }

    public void onDisconnect(ChatComponent chatComponent) {
        getLegacyPlayHandlerFlowMarker();

        this.gameController.loadWorld(null);

        if (this.guiScreenServer != null) {
            this.gameController.displayGuiScreen(
                    new GuiDisconnected(this.guiScreenServer, "disconnect.lost", chatComponent));

            return;
        }

        this.gameController.displayGuiScreen(
                new GuiDisconnected(
                        new GuiMultiplayer(new RemixMainMenuScreen(RemixClient.INSTANCE)),
                        "disconnect.lost",
                        chatComponent));
    }

    public void handleChat(S02PacketChat s02PacketChat) {
        getLegacyPlayHandlerFlowMarker();

        PacketThreadUtil.checkThreadAndEnqueue(s02PacketChat, this, this.gameController);

        if (s02PacketChat.getType() == 2) {
            this.gameController.ingameGUI.setRecordPlaying(s02PacketChat.getChatComponent(), false);

            return;
        }

        this.gameController
                .ingameGUI
                .getChatGUI()
                .printChatMessage(s02PacketChat.getChatComponent());
    }

    public void handleConfirmTransaction(S32PacketConfirmTransaction s32PacketConfirmTransaction) {
        PacketThreadUtil.checkThreadAndEnqueue(
                s32PacketConfirmTransaction, this, this.gameController);
        LocalPlayer player = this.gameController.player;
        int windowId = s32PacketConfirmTransaction.getWindowId();
        Container container = null;
        if (windowId == 0) {
            container = player.inventoryContainer;
        } else if (windowId == player.openContainer.windowId) {
            container = player.openContainer;
        }

        if (container != null && !s32PacketConfirmTransaction.isAccepted()) {
            this.addToSendQueue(
                    new C0FPacketConfirmTransaction(
                            windowId, s32PacketConfirmTransaction.getActionNumber(), true));
        }
    }

    public NetworkManager getNetworkManager() {
        return this.netManager;
    }

    public void handleRespawn(S07PacketRespawn packet) {
        PacketThreadUtil.checkThreadAndEnqueue(packet, this, this.gameController);
        int dimension = packet.getDimensionID();
        if (dimension != this.gameController.player.dimension) {
            this.doneLoadingTerrain = false;
            Scoreboard scoreboard = this.clientWorldController.getScoreboard();

            this.clientWorldController =
                    new ClientWorld(
                            this,
                            new WorldSettings(
                                    0L,
                                    packet.getGameType(),
                                    false,
                                    this.gameController
                                            .world
                                            .getWorldInfo()
                                            .isHardcoreModeEnabled(),
                                    packet.getWorldType()),
                            dimension,
                            packet.getDifficulty(),
                            this.gameController.mcProfiler);
            this.clientWorldController.setWorldScoreboard(scoreboard);
            this.gameController.loadWorld(this.clientWorldController);
            this.gameController.player.dimension = dimension;
            this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
        }

        this.gameController.setDimensionAndSpawnPlayer(dimension);
        this.gameController.playerController.setGameType(packet.getGameType());
    }

    public void addToSendQueue(Packet packet) {
        PacketEvent outbound = new PacketEvent(PacketDirection.SEND, packet);
        RemixClient.eventBus.post(outbound);
        if (!outbound.isCancelled()) {
            this.netManager.sendPacket(outbound.getPacket());
        }
        // This legacy notification completes the attempt, even when a listener cancelled it.
        RemixClient.eventBus.post(new PacketEvent(PacketDirection.SENT, packet));
    }

    public void handleSpawnObject(S0EPacketSpawnObject packet) {
        PacketThreadUtil.checkThreadAndEnqueue(packet, this, this.gameController);
        int objectData = packet.getObjectData();
        ClientWorld world = this.clientWorldController;
        Entity entity = createSpawnedObject(world, packet, objectData);
        if (entity == null) {
            return;
        }

        entity.serverPosX = packet.getX();
        entity.serverPosY = packet.getY();
        entity.serverPosZ = packet.getZ();
        entity.rotationPitch = packet.getPitch() * 360 / 256.0F;
        entity.rotationYaw = packet.getYaw() * 360 / 256.0F;

        Entity[] parts = entity.getParts();
        if (parts != null) {
            int idOffset = packet.getEntityID() - entity.getEntityId();
            for (Entity part : parts) {
                part.setEntityId(part.getEntityId() + idOffset);
            }
        }
        entity.setEntityId(packet.getEntityID());
        world.addEntityToWorld(packet.getEntityID(), entity);

        // This client uses the original object data here, even when the factory clears the packet
        // field.
        if (objectData > 0) {
            if (entity instanceof EntityArrow) {
                Entity shooter = world.getEntityByID(objectData);
                if (shooter instanceof LivingEntity) {
                    ((EntityArrow) entity).shootingEntity = shooter;
                }
            }
            entity.setVelocity(
                    packet.getSpeedX() / 8000.0,
                    packet.getSpeedY() / 8000.0,
                    packet.getSpeedZ() / 8000.0);
        }
    }

    private static Entity createSpawnedObject(
            ClientWorld world, S0EPacketSpawnObject packet, int objectData) {
        double x = packet.getX() / 32.0;
        double y = packet.getY() / 32.0;
        double z = packet.getZ() / 32.0;
        Entity entity;
        switch (packet.getType()) {
            case 1:
                return new EntityBoat(world, x, y, z);
            case 2:
                return new EntityItem(world, x, y, z);
            case 10:
                return EntityMinecart.createMinecart(
                        world, x, y, z, EntityMinecartEnumMinecartType.byNetworkID(objectData));
            case 50:
                return new EntityTNTPrimed(world, x, y, z, null);
            case 51:
                return new EntityEnderCrystal(world, x, y, z);
            case 60:
                return new EntityArrow(world, x, y, z);
            case 61:
                return new EntitySnowball(world, x, y, z);
            case 62:
                return new EntityEgg(world, x, y, z);
            case 63:
                entity =
                        new EntityLargeFireball(
                                world,
                                x,
                                y,
                                z,
                                packet.getSpeedX() / 8000.0,
                                packet.getSpeedY() / 8000.0,
                                packet.getSpeedZ() / 8000.0);
                break;
            case 64:
                entity =
                        new EntitySmallFireball(
                                world,
                                x,
                                y,
                                z,
                                packet.getSpeedX() / 8000.0,
                                packet.getSpeedY() / 8000.0,
                                packet.getSpeedZ() / 8000.0);
                break;
            case 65:
                return new EntityEnderPearl(world, x, y, z);
            case 66:
                entity =
                        new EntityWitherSkull(
                                world,
                                x,
                                y,
                                z,
                                packet.getSpeedX() / 8000.0,
                                packet.getSpeedY() / 8000.0,
                                packet.getSpeedZ() / 8000.0);
                break;
            case 70:
                entity =
                        new EntityFallingBlock(
                                world, x, y, z, Block.getStateById(objectData & 65535));
                break;
            case 71:
                entity =
                        new EntityItemFrame(
                                world,
                                new BlockPos(
                                        MathHelper.floor(x),
                                        MathHelper.floor(y),
                                        MathHelper.floor(z)),
                                Facing.getHorizontal(objectData));
                break;
            case 72:
                return new EntityEnderEye(world, x, y, z);
            case 73:
                entity = new EntityPotion(world, x, y, z, objectData);
                break;
            case 75:
                entity = new EntityExpBottle(world, x, y, z);
                break;
            case 76:
                return new EntityFireworkRocket(world, x, y, z, null);
            case 77:
                entity =
                        new EntityLeashKnot(
                                world,
                                new BlockPos(
                                        MathHelper.floor(x),
                                        MathHelper.floor(y),
                                        MathHelper.floor(z)));
                break;
            case 78:
                return new EntityArmorStand(world, x, y, z);
            case 90:
                Entity owner = world.getEntityByID(objectData);
                entity =
                        owner instanceof PlayerEntity
                                ? new EntityFishHook(world, x, y, z, (PlayerEntity) owner)
                                : null;
                break;
            default:
                return null;
        }
        packet.setObjectData(0);
        return entity;
    }

    public void handleServerDifficulty(S41PacketServerDifficulty s41PacketServerDifficulty) {
        PacketThreadUtil.checkThreadAndEnqueue(
                s41PacketServerDifficulty, this, this.gameController);
        this.gameController
                .world
                .getWorldInfo()
                .setDifficulty(s41PacketServerDifficulty.getDifficulty());
        this.gameController
                .world
                .getWorldInfo()
                .setDifficultyLocked(s41PacketServerDifficulty.isDifficultyLocked());
    }

    public void handleMaps(S34PacketMaps s34PacketMaps) {
        PacketThreadUtil.checkThreadAndEnqueue(s34PacketMaps, this, this.gameController);
        MapData mapData = ItemMap.loadMapData(s34PacketMaps.getMapId(), this.gameController.world);
        s34PacketMaps.setMapdataTo(mapData);
        this.gameController.entityRenderer.getMapItemRenderer().updateMapTexture(mapData);
    }

    public void handleWindowItems(S30PacketWindowItems s30PacketWindowItems) {
        getLegacyPlayHandlerFlowMarker();

        PacketThreadUtil.checkThreadAndEnqueue(s30PacketWindowItems, this, this.gameController);
        LocalPlayer player = this.gameController.player;
        int i = s30PacketWindowItems.getWindowId();

        ItemStack[] itemStackArray = s30PacketWindowItems.getItemStacks();
        if (i == 0) {
            player.inventoryContainer.putStacksInSlots(itemStackArray);

            return;
        }

        if (i == player.openContainer.windowId) {
            player.openContainer.putStacksInSlots(itemStackArray);
        }
    }

    public void handleBlockBreakAnim(S25PacketBlockBreakAnim s25PacketBlockBreakAnim) {
        PacketThreadUtil.checkThreadAndEnqueue(s25PacketBlockBreakAnim, this, this.gameController);
        this.gameController.world.sendBlockBreakProgress(
                s25PacketBlockBreakAnim.getBreakerId(),
                s25PacketBlockBreakAnim.getPosition(),
                s25PacketBlockBreakAnim.getProgress());
    }

    private boolean validateResourcePackUrl(String text, String textValue) {
        try {
            URI uri = new URI(text);
            String scheme = uri.getScheme();
            boolean flag = "level".equals(scheme);
            if (!"http".equals(scheme) && !"https".equals(scheme) && !flag) {
                this.netManager.sendPacket(
                        new C19PacketResourcePackStatus(
                                textValue, C19PacketResourcePackStatusAction.FAILED_DOWNLOAD));
                throw new URISyntaxException(text, "Wrong protocol");
            }

            text =
                    URLDecoder.decode(
                            text.substring("level://".length()), StandardCharsets.UTF_8.toString());
            if (flag && (text.contains("..") || !text.endsWith("/resources.zip"))) {
                System.out.println("Malicious server tried to access " + text);
                LocalPlayer player = MinecraftClient.getMinecraft().player;
                if (player != null) {
                    player.addChatMessage(
                            new ChatComponentText(
                                    EnumChatFormatting.RED
                                            + EnumChatFormatting.BOLD.toString()
                                            + "[WARNING] The current server has attempted to be"
                                            + " malicious but we have stopped them."));
                }

                throw new URISyntaxException(text, "Invalid levelstorage resourcepack path");
            } else {
                return true;
            }
        } catch (URISyntaxException urisyntaxexception) {
            return false;
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
            unsupportedencodingexception.printStackTrace();
            return false;
        }
    }

    public void handleBlockChange(S23PacketBlockChange s23PacketBlockChange) {
        PacketThreadUtil.checkThreadAndEnqueue(s23PacketBlockChange, this, this.gameController);
        this.clientWorldController.invalidateRegionAndSetBlock(
                s23PacketBlockChange.getBlockPosition(), s23PacketBlockChange.getBlockState());
    }

    public void handleSpawnMob(S0FPacketSpawnMob s0FPacketSpawnMob) {
        PacketThreadUtil.checkThreadAndEnqueue(s0FPacketSpawnMob, this, this.gameController);
        getLegacyPlayHandlerFlowMarker();

        double d0 = s0FPacketSpawnMob.getX() / 32.0;
        double d1 = s0FPacketSpawnMob.getY() / 32.0;
        double d2 = s0FPacketSpawnMob.getZ() / 32.0;
        float f = s0FPacketSpawnMob.getYaw() * 360 / 256.0F;
        float f1 = s0FPacketSpawnMob.getPitch() * 360 / 256.0F;
        LivingEntity livingEntity =
                (LivingEntity)
                        EntityList.createEntityByID(
                                s0FPacketSpawnMob.getEntityType(), this.gameController.world);
        livingEntity.serverPosX = s0FPacketSpawnMob.getX();
        livingEntity.serverPosY = s0FPacketSpawnMob.getY();
        livingEntity.serverPosZ = s0FPacketSpawnMob.getZ();
        livingEntity.rotationYawHead = s0FPacketSpawnMob.getHeadPitch() * 360 / 256.0F;
        Entity[] entityArray = livingEntity.getParts();

        if (entityArray != null) {
            int i = s0FPacketSpawnMob.getEntityID() - livingEntity.getEntityId();
            int j = 0;
            int k = entityArray.length;

            while (j < k) {
                Entity entity = entityArray[j];
                entity.setEntityId(entity.getEntityId() + i);
                j++;
            }
        }

        livingEntity.setEntityId(s0FPacketSpawnMob.getEntityID());
        livingEntity.setPositionAndRotation(d0, d1, d2, f, f1);
        livingEntity.motionX = s0FPacketSpawnMob.getVelocityX() / 8000.0F;
        livingEntity.motionY = s0FPacketSpawnMob.getVelocityY() / 8000.0F;
        livingEntity.motionZ = s0FPacketSpawnMob.getVelocityZ() / 8000.0F;
        this.clientWorldController.addEntityToWorld(s0FPacketSpawnMob.getEntityID(), livingEntity);
        List list = s0FPacketSpawnMob.getWatchableObjects();
        if (list != null) {
            livingEntity.getDataWatcher().updateWatchedObjectsFromList(list);
        }
    }

    public void handleTabComplete(S3APacketTabComplete s3APacketTabComplete) {
        PacketThreadUtil.checkThreadAndEnqueue(s3APacketTabComplete, this, this.gameController);
        String[] astring = s3APacketTabComplete.getMatches();
        GuiScreen currentScreen = this.gameController.currentScreen;
        if (currentScreen instanceof GuiChat) {
            GuiChat guiChat = (GuiChat) currentScreen;
            guiChat.onAutocompleteResponse(astring);
        }
    }

    public void handleSetExperience(S1FPacketSetExperience s1FPacketSetExperience) {
        PacketThreadUtil.checkThreadAndEnqueue(s1FPacketSetExperience, this, this.gameController);
        this.gameController.player.setXPStats(
                s1FPacketSetExperience.getExperienceProgress(),
                s1FPacketSetExperience.getTotalExperience(),
                s1FPacketSetExperience.getLevel());
    }

    public void handlePlayerAbilities(S39PacketPlayerAbilities s39PacketPlayerAbilities) {
        PacketThreadUtil.checkThreadAndEnqueue(s39PacketPlayerAbilities, this, this.gameController);
        PlayerCapabilities capabilities = this.gameController.player.capabilities;
        capabilities.isFlying = s39PacketPlayerAbilities.isFlying();
        capabilities.isCreativeMode = s39PacketPlayerAbilities.isCreativeMode();
        capabilities.disableDamage = s39PacketPlayerAbilities.isInvulnerable();
        capabilities.allowFlying = s39PacketPlayerAbilities.isAllowFlying();
        capabilities.setFlySpeed(s39PacketPlayerAbilities.getFlySpeed());
        capabilities.setPlayerWalkSpeed(s39PacketPlayerAbilities.getWalkSpeed());
    }

    public void handleSetCompressionLevel(
            S46PacketSetCompressionLevel s46PacketSetCompressionLevel) {
        if (!this.netManager.isLocalChannel()) {
            this.netManager.setCompressionThreshold(
                    s46PacketSetCompressionLevel.getCompressionThreshold());
        }
    }

    public void handleResourcePack(S48PacketResourcePackSend s48PacketResourcePackSend) {
        getLegacyPlayHandlerFlowMarker();

        if (this.validateResourcePackUrl(
                s48PacketResourcePackSend.getURL(), s48PacketResourcePackSend.getHash())) {
            String text = s48PacketResourcePackSend.getURL();
            String textValue = s48PacketResourcePackSend.getHash();
            NetworkManager awKValue = this.netManager;
            ServerData currentServerData = this.gameController.getCurrentServerData();
            if (text.startsWith("level://")) {
                {
                    String textValue2 = text.substring("level://".length());
                    File file1 = new File(this.gameController.gameDirectory, "saves");
                    File file2 = remix.security.SafeResourcePaths.insideOrNull(file1, textValue2);
                    if (file2 != null && file2.isFile()) {
                        this.netManager.sendPacket(
                                new C19PacketResourcePackStatus(
                                        textValue, C19PacketResourcePackStatusAction.ACCEPTED));
                        remix.compat.library.LegacyLibraryAdapters.addCallback(
                                this.gameController
                                        .getResourcePackRepository()
                                        .setResourcePackInstance(file2),
                                new NetHandlerPlayClientHandleResourcePackFutureCallback(
                                        this, awKValue, textValue));

                    } else {

                        this.netManager.sendPacket(
                                new C19PacketResourcePackStatus(
                                        textValue,
                                        C19PacketResourcePackStatusAction.FAILED_DOWNLOAD));
                    }
                }

                return;
            }

            if (currentServerData != null
                    && currentServerData.getResourceMode()
                            == ServerDataServerResourceMode.ENABLED) {
                this.netManager.sendPacket(
                        new C19PacketResourcePackStatus(
                                textValue, C19PacketResourcePackStatusAction.ACCEPTED));
                remix.compat.library.LegacyLibraryAdapters.addCallback(
                        this.gameController
                                .getResourcePackRepository()
                                .downloadResourcePack(text, textValue),
                        new NetHandlerPlayClientHandleResourcePackFutureCallback2(
                                this, awKValue, textValue));

                return;
            }

            if (currentServerData != null
                    && currentServerData.getResourceMode() != ServerDataServerResourceMode.PROMPT) {
                this.netManager.sendPacket(
                        new C19PacketResourcePackStatus(
                                textValue, C19PacketResourcePackStatusAction.DECLINED));

                return;
            }

            this.gameController.addScheduledTask(() ->
                    this.gameController.displayGuiScreen(
                            new GuiYesNo(
                                    (flag, i) -> {
                                        MinecraftClient asgValue;
                                        ServerData serverData;
                                        {
                                            getLegacyPlayHandlerFlowMarker();

                                            asgValue = MinecraftClient.getMinecraft();
                                            this.gameController = asgValue;

                                            serverData = asgValue.getCurrentServerData();
                                            if (flag) {
                                                if (serverData != null) {
                                                    serverData.setResourceMode(
                                                            ServerDataServerResourceMode.ENABLED);
                                                }

                                                awKValue.sendPacket(
                                                        new C19PacketResourcePackStatus(
                                                                textValue,
                                                                C19PacketResourcePackStatusAction
                                                                        .ACCEPTED));
                                                remix.compat.library.LegacyLibraryAdapters
                                                        .addCallback(
                                                                asgValue.getResourcePackRepository()
                                                                        .downloadResourcePack(
                                                                                text, textValue),
                                                                new ResourcePackCallback(
                                                                        this, awKValue, textValue));

                                            } else {

                                                if (serverData != null) {
                                                    serverData.setResourceMode(
                                                            ServerDataServerResourceMode.DISABLED);
                                                }

                                                awKValue.sendPacket(
                                                        new C19PacketResourcePackStatus(
                                                                textValue,
                                                                C19PacketResourcePackStatusAction
                                                                        .DECLINED));
                                            }
                                        }

                                        ServerList.saveSingleServer(serverData);
                                        asgValue.displayGuiScreen(null);
                                    },
                                    I18n.format("multiplayer.texturePrompt.line1", new Object[0]),
                                    I18n.format("multiplayer.texturePrompt.line2", new Object[0]),
                                    0)));
        }
    }

    public void handleDestroyEntities(S13PacketDestroyEntities s13PacketDestroyEntities) {
        PacketThreadUtil.checkThreadAndEnqueue(s13PacketDestroyEntities, this, this.gameController);
        ClientWorld awQValue = this.clientWorldController;
        int[] aint = s13PacketDestroyEntities.getEntityIDs();
        getLegacyPlayHandlerFlowMarker();

        int i = 0;

        int j = aint.length;

        while (i < j) {
            awQValue.removeEntityFromWorld(aint[i]);
            i++;
        }
    }

    public void handleParticles(S2APacketParticles s2APacketParticles) {
        PacketThreadUtil.checkThreadAndEnqueue(s2APacketParticles, this, this.gameController);
        getLegacyPlayHandlerFlowMarker();

        float particleSpeed = s2APacketParticles.getParticleSpeed();

        if (s2APacketParticles.getParticleCount() == 0) {
            double d0 = particleSpeed * s2APacketParticles.getXOffset();
            double d2 = particleSpeed * s2APacketParticles.getYOffset();
            double d4 = particleSpeed * s2APacketParticles.getZOffset();

            try {
                this.clientWorldController.spawnParticle(
                        s2APacketParticles.getParticleType(),
                        s2APacketParticles.isLongDistance(),
                        s2APacketParticles.getXCoordinate(),
                        s2APacketParticles.getYCoordinate(),
                        s2APacketParticles.getZCoordinate(),
                        d0,
                        d2,
                        d4,
                        s2APacketParticles.getParticleArgs());
            } catch (Throwable throwable1) {
                net.minecraft.client.network.ClientPlayHandler.logger.warn(
                        "Could not spawn particle effect " + s2APacketParticles.getParticleType());
            }

            return;
        }

        int i = 0;

        while (i < s2APacketParticles.getParticleCount()) {
            double d1 = this.avRandomizer.nextGaussian() * s2APacketParticles.getXOffset();
            double d3 = this.avRandomizer.nextGaussian() * s2APacketParticles.getYOffset();
            double d5 = this.avRandomizer.nextGaussian() * s2APacketParticles.getZOffset();
            double d6 = this.avRandomizer.nextGaussian() * particleSpeed;
            double d7 = this.avRandomizer.nextGaussian() * particleSpeed;
            double d8 = this.avRandomizer.nextGaussian() * particleSpeed;

            try {
                this.clientWorldController.spawnParticle(
                        s2APacketParticles.getParticleType(),
                        s2APacketParticles.isLongDistance(),
                        s2APacketParticles.getXCoordinate() + d1,
                        s2APacketParticles.getYCoordinate() + d3,
                        s2APacketParticles.getZCoordinate() + d5,
                        d6,
                        d7,
                        d8,
                        s2APacketParticles.getParticleArgs());
            } catch (Throwable throwable) {
                net.minecraft.client.network.ClientPlayHandler.logger.warn(
                        "Could not spawn particle effect " + s2APacketParticles.getParticleType());
                return;
            }

            i++;
        }
    }

    public void handleSetSlot(S2FPacketSetSlot packet) {
        PacketThreadUtil.checkThreadAndEnqueue(packet, this, this.gameController);
        LocalPlayer player = this.gameController.player;
        int windowId = packet.getWindowId();
        ItemStack incoming = packet.getItemStack();
        if (windowId == -1) {
            player.inventory.setItemStack(incoming);
            return;
        }

        GuiScreen currentScreen = this.gameController.currentScreen;
        boolean creativeCatalogOpen =
                currentScreen instanceof GuiContainerCreative
                        && ((GuiContainerCreative) currentScreen).getSelectedTabIndex()
                                != CreativeTabs.tabInventory.getTabIndex();
        int slot = packet.getSlotIndex();
        if (windowId == 0 && slot >= 36 && slot < 45) {
            Container inventory = player.inventoryContainer;
            ItemStack current = inventory.getSlot(slot).getStack();
            if (incoming != null && (current == null || current.stackSize < incoming.stackSize)) {
                incoming.animationsToGo = 5;
            }
            inventory.putStackInSlot(slot, incoming);
            return;
        }

        if (windowId == player.openContainer.windowId && (windowId != 0 || !creativeCatalogOpen)) {
            player.openContainer.putStackInSlot(slot, incoming);
        }
    }

    public void handleAnimation(S0BPacketAnimation s0BPacketAnimation) {
        getLegacyPlayHandlerFlowMarker();

        PacketThreadUtil.checkThreadAndEnqueue(s0BPacketAnimation, this, this.gameController);

        Entity entity = this.clientWorldController.getEntityByID(s0BPacketAnimation.getEntityID());
        if (entity != null) {
            switch (s0BPacketAnimation.getAnimationType()) {
                case 0:
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.swingItem();

                    break;

                case 1:
                    entity.performHurtAnimation();

                    break;

                case 2:
                    PlayerEntity playerEntity = (PlayerEntity) entity;
                    playerEntity.wakeUpPlayer(false, false, false);

                    break;

                case 4:
                    this.gameController.effectRenderer.emitParticleAtEntity(
                            entity, EnumParticleTypes.CRIT);

                    break;

                case 5:
                    this.gameController.effectRenderer.emitParticleAtEntity(
                            entity, EnumParticleTypes.CRIT_MAGIC);
                case 3:
            }
        }
    }

    public void handleCustomPayload(S3FPacketCustomPayload s3FPacketCustomPayload) {
        PacketThreadUtil.checkThreadAndEnqueue(s3FPacketCustomPayload, this, this.gameController);
        PacketBuffer payload = s3FPacketCustomPayload.getBufferData();
        try {
            getLegacyPlayHandlerFlowMarker();

            LocalPlayer player = this.gameController.player;

            String text = s3FPacketCustomPayload.getChannelName();
            byte b0 = -1;
            switch (text.hashCode()) {
                case -37059198:
                    if (!text.equals("MC|TrList")) {
                        break;
                    }

                    b0 = 0;

                    break;

                case -294893183:
                    if (!text.equals("MC|Brand")) {
                        break;
                    }

                    b0 = 1;

                    break;

                case -295921722:
                    if (text.equals("MC|BOpen")) {
                        b0 = 2;
                    }
            }

            switch (b0) {
                case 0:
                    PacketBuffer packetBuffer = s3FPacketCustomPayload.getBufferData();

                    try {
                        int i = packetBuffer.readInt();
                        GuiScreen currentScreen = this.gameController.currentScreen;
                        if (currentScreen instanceof GuiMerchant
                                && i == player.openContainer.windowId) {
                            IMerchant merchant = ((GuiMerchant) currentScreen).getMerchant();
                            MerchantRecipeList merchantRecipeList =
                                    MerchantRecipeList.readFromBuf(packetBuffer);
                            merchant.setRecipes(merchantRecipeList);
                        }
                    } catch (IOException ioexception) {
                        net.minecraft.client.network.ClientPlayHandler.logger.error(
                                "Couldn't load trade info", ioexception);
                    }

                    break;

                case 1:
                    player.setClientBrand(
                            s3FPacketCustomPayload.getBufferData().readStringFromBuffer(32767));

                    break;

                case 2:
                    ItemStack currentEquippedItem = player.getCurrentEquippedItem();
                    if (currentEquippedItem != null
                            && currentEquippedItem.getItem() == Items.written_book) {
                        this.gameController.displayGuiScreen(
                                new GuiScreenBook(player, currentEquippedItem, false));
                    }
            }
        } finally {
            // readPacketData allocated this copy; client-thread handling owns it.
            payload.release();
        }
    }

    public void handleScoreboardObjective(S3BPacketScoreboardObjective packet) {
        PacketThreadUtil.checkThreadAndEnqueue(packet, this, this.gameController);
        Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        String objectiveId = packet.getObjectiveId();
        int action = packet.getAction();
        ScoreObjective objective =
                action == 0
                        ? scoreboard.addScoreObjective(objectiveId, IScoreObjectiveCriteria.DUMMY)
                        : scoreboard.getObjective(objectiveId);
        if (action == 1) {
            scoreboard.removeObjective(objective);
        } else if (action == 0 || action == 2) {
            objective.setDisplayName(packet.getDisplayName());
            objective.setRenderType(packet.getRenderType());
        }
    }

    public void handleJoinGame(S01PacketJoinGame packet) {
        MinecraftClient client = this.gameController;
        PacketThreadUtil.checkThreadAndEnqueue(packet, this, client);
        client.playerController = new PlayerController(client, this);
        WorldSettingsGameType gameType = packet.getGameType();

        this.clientWorldController =
                new ClientWorld(
                        this,
                        new WorldSettings(
                                0L,
                                gameType,
                                false,
                                packet.isHardcoreMode(),
                                packet.getWorldType()),
                        packet.getDimension(),
                        packet.getDifficulty(),
                        client.mcProfiler);
        client.gameSettings.difficulty = packet.getDifficulty();
        client.loadWorld(this.clientWorldController);
        LocalPlayer player = client.player;
        player.dimension = packet.getDimension();
        client.displayGuiScreen(new GuiDownloadTerrain(this));
        player.setEntityId(packet.getEntityId());
        this.currentServerMaxPlayers = packet.getMaxPlayers();
        player.setReducedDebug(packet.isReducedDebugInfo());
        client.playerController.setGameType(gameType);
        client.gameSettings.sendSettingsToServer();
        this.netManager.sendPacket(
                new C17PacketCustomPayload(
                        "MC|Brand",
                        new PacketBuffer(Unpooled.buffer())
                                .writeString(ClientBrandRetriever.getClientModName())));
    }

    public void handleUseBed(S0APacketUseBed s0APacketUseBed) {
        PacketThreadUtil.checkThreadAndEnqueue(s0APacketUseBed, this, this.gameController);
        s0APacketUseBed
                .getPlayer(this.clientWorldController)
                .trySleep(s0APacketUseBed.getBedPosition());
    }

    public void handleSoundEffect(S29PacketSoundEffect s29PacketSoundEffect) {
        PacketThreadUtil.checkThreadAndEnqueue(s29PacketSoundEffect, this, this.gameController);
        this.gameController.world.playSound(
                s29PacketSoundEffect.getX(),
                s29PacketSoundEffect.getY(),
                s29PacketSoundEffect.getZ(),
                s29PacketSoundEffect.getSoundName(),
                s29PacketSoundEffect.getVolume(),
                s29PacketSoundEffect.getPitch(),
                false);
    }

    // region String decoding support

    // endregion

    public void handleDisplayScoreboard(S3DPacketDisplayScoreboard s3DPacketDisplayScoreboard) {
        PacketThreadUtil.checkThreadAndEnqueue(
                s3DPacketDisplayScoreboard, this, this.gameController);
        getLegacyPlayHandlerFlowMarker();

        Scoreboard scoreboard = this.clientWorldController.getScoreboard();

        String text = s3DPacketDisplayScoreboard.getObjectiveId();
        int i = s3DPacketDisplayScoreboard.getDisplaySlot();
        if (text.isEmpty()) {
            scoreboard.setObjectiveInDisplaySlot(i, null);

            return;
        }

        ScoreObjective objective = scoreboard.getObjective(text);
        scoreboard.setObjectiveInDisplaySlot(i, objective);
    }

    public void handlePlayerListHeaderFooter(
            S47PacketPlayerListHeaderFooter s47PacketPlayerListHeaderFooter) {
        GuiPlayerTabOverlay tabList = this.gameController.ingameGUI.getTabList();
        tabList.setHeader(
                s47PacketPlayerListHeaderFooter.getHeader().getFormattedText().isEmpty()
                        ? null
                        : s47PacketPlayerListHeaderFooter.getHeader());
        tabList.setFooter(
                s47PacketPlayerListHeaderFooter.getFooter().getFormattedText().isEmpty()
                        ? null
                        : s47PacketPlayerListHeaderFooter.getFooter());
    }

    public PlayerInfo getPlayerInfo(UUID uuid) {
        return ((net.minecraft.client.network.PlayerInfo) this.playerInfoMap.get(uuid));
    }

    public void handleEffect(S28PacketEffect s28PacketEffect) {
        getLegacyPlayHandlerFlowMarker();

        PacketThreadUtil.checkThreadAndEnqueue(s28PacketEffect, this, this.gameController);

        if (s28PacketEffect.isSoundServerwide()) {
            this.gameController.world.playBroadcastSound(
                    s28PacketEffect.getSoundType(),
                    s28PacketEffect.getSoundPos(),
                    s28PacketEffect.getSoundData());

            return;
        }

        this.gameController.world.playAuxSFX(
                s28PacketEffect.getSoundType(),
                s28PacketEffect.getSoundPos(),
                s28PacketEffect.getSoundData());
    }

    public void handleCombatEvent(S42PacketCombatEvent s42PacketCombatEvent) {
        getLegacyPlayHandlerFlowMarker();

        PacketThreadUtil.checkThreadAndEnqueue(s42PacketCombatEvent, this, this.gameController);
        Entity entity = this.clientWorldController.getEntityByID(s42PacketCombatEvent.c);

        LivingEntity livingEntity = entity instanceof LivingEntity ? (LivingEntity) entity : null;
        switch (s42PacketCombatEvent.eventType) {
            case END_COMBAT:
                long i = 1000 * s42PacketCombatEvent.dD / 20;
                MetadataCombat metadataCombat =
                        new MetadataCombat(this.gameController.player, livingEntity);
                IStream twitchStream = this.gameController.getTwitchStream();
                long l = 3431654356598323456L;
                long k = 0L - i;
                long i1 = -862711446642685221L;
                twitchStream.sendMetadataSpan(metadataCombat, k, 0L);

                break;

            case ENTITY_DIED:
                Entity entityValue =
                        this.clientWorldController.getEntityByID(s42PacketCombatEvent.cz);
                if (entityValue instanceof PlayerEntity) {
                    MetadataPlayerDeath metadataPlayerDeath =
                            new MetadataPlayerDeath((PlayerEntity) entityValue, livingEntity);
                    metadataPlayerDeath.setDescription(s42PacketCombatEvent.deathMessage);
                    IStream twitchStreamValue = this.gameController.getTwitchStream();

                    twitchStreamValue.sendMetadataEvent(metadataPlayerDeath, 0L);
                }
        }
    }

    public void handleHeldItemChange(S09PacketHeldItemChange s09PacketHeldItemChange) {
        PacketThreadUtil.checkThreadAndEnqueue(s09PacketHeldItemChange, this, this.gameController);
        int currentItem = s09PacketHeldItemChange.getHeldItemHotbarIndex();
        if (currentItem >= 0 && currentItem < InventoryPlayer.getHotbarSize()) {
            this.gameController.player.inventory.currentItem = currentItem;
        }
    }

    public void handleSignEditorOpen(S36PacketSignEditorOpen s36PacketSignEditorOpen) {
        PacketThreadUtil.checkThreadAndEnqueue(s36PacketSignEditorOpen, this, this.gameController);
        Object tileEntity =
                this.clientWorldController.getTileEntity(s36PacketSignEditorOpen.getSignPosition());
        if (!(tileEntity instanceof TileEntitySign)) {
            tileEntity = new TileEntitySign();
            ((net.minecraft.tileentity.TileEntity) (tileEntity))
                    .setWorldObj(this.clientWorldController);
            ((net.minecraft.tileentity.TileEntity) (tileEntity))
                    .setPos(s36PacketSignEditorOpen.getSignPosition());
        }

        this.gameController.player.openEditSign((TileEntitySign) tileEntity);
    }

    public void handleMultiBlockChange(S22PacketMultiBlockChange s22PacketMultiBlockChange) {
        getLegacyPlayHandlerFlowMarker();

        PacketThreadUtil.checkThreadAndEnqueue(
                s22PacketMultiBlockChange, this, this.gameController);
        S22PacketMultiBlockChangeBlockUpdateData[] s22PacketMultiBlockChangeBlockUpdateDataArray =
                s22PacketMultiBlockChange.getChangedBlocks();
        int length = s22PacketMultiBlockChangeBlockUpdateDataArray.length;
        ClientWorld awQValue = this.clientWorldController;

        int j = 0;

        while (j < length) {
            S22PacketMultiBlockChangeBlockUpdateData s22PacketMultiBlockChangeBlockUpdateData =
                    s22PacketMultiBlockChangeBlockUpdateDataArray[j];
            awQValue.invalidateRegionAndSetBlock(
                    s22PacketMultiBlockChangeBlockUpdateData.getPos(),
                    s22PacketMultiBlockChangeBlockUpdateData.getBlockState());
            j++;
        }
    }

    public void handleSpawnPosition(S05PacketSpawnPosition s05PacketSpawnPosition) {
        PacketThreadUtil.checkThreadAndEnqueue(s05PacketSpawnPosition, this, this.gameController);
        this.gameController.player.setSpawnPoint(s05PacketSpawnPosition.getSpawnPos(), true);
        this.gameController.world.getWorldInfo().setSpawn(s05PacketSpawnPosition.getSpawnPos());
    }

    // region String decoding support

    // endregion

    public void handleTimeUpdate(S03PacketTimeUpdate s03PacketTimeUpdate) {
        PacketThreadUtil.checkThreadAndEnqueue(s03PacketTimeUpdate, this, this.gameController);
        ClientWorld world = this.gameController.world;
        world.setTotalWorldTime(s03PacketTimeUpdate.getTotalWorldTime());
        world.setWorldTime(s03PacketTimeUpdate.getWorldTime());
    }

    public void handleTeams(S3EPacketTeams packet) {
        PacketThreadUtil.checkThreadAndEnqueue(packet, this, this.gameController);
        Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        int action = packet.getAction();
        String teamId = packet.getTeamId();
        ScorePlayerTeam team =
                action == 0 ? scoreboard.createTeam(teamId) : scoreboard.getTeam(teamId);

        if (action == 0 || action == 2) {
            team.setTeamName(packet.getDisplayName());
            team.setNamePrefix(packet.getPrefix());
            team.setNameSuffix(packet.getSuffix());
            team.setChatFormat(EnumChatFormatting.fromColorIndex(packet.getColorIndex()));
            team.applyFriendlyFlags(packet.getFriendlyFlags());
            TeamEnumVisible visibility =
                    TeamEnumVisible.fromProtocolKey(packet.getNameTagVisibilityKey());
            if (visibility != null) {
                team.setNameTagVisibility(visibility);
            }
        }
        if (action == 0 || action == 3) {
            for (Object name : packet.getPlayerNames()) {
                scoreboard.addPlayerToTeam((String) name, teamId);
            }
        }
        if (action == 4) {
            for (Object name : packet.getPlayerNames()) {
                scoreboard.removePlayerFromTeam((String) name, team);
            }
        }
        if (action == 1) {
            scoreboard.removeTeam(team);
        }
    }

    public void handleBlockAction(S24PacketBlockAction s24PacketBlockAction) {
        PacketThreadUtil.checkThreadAndEnqueue(s24PacketBlockAction, this, this.gameController);
        this.gameController.world.addBlockEvent(
                s24PacketBlockAction.getBlockPosition(),
                s24PacketBlockAction.getBlockType(),
                s24PacketBlockAction.getData1(),
                s24PacketBlockAction.getData2());
    }

    public void handlePlayerPosLook(S08PacketPlayerPosLook packet) {
        PacketThreadUtil.checkThreadAndEnqueue(packet, this, this.gameController);
        LocalPlayer player = this.gameController.player;
        Set flags = packet.getRelativeFlags();

        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        float yaw = packet.getYaw();
        float pitch = packet.getPitch();
        if (flags.contains(S08PacketPlayerPosLookEnumFlags.RELATIVE_X)) {
            x += player.posX;
        } else {
            player.motionX = 0.0;
        }
        if (flags.contains(S08PacketPlayerPosLookEnumFlags.RELATIVE_Y)) {
            y += player.posY;
        } else {
            player.motionY = 0.0;
        }
        if (flags.contains(S08PacketPlayerPosLookEnumFlags.RELATIVE_Z)) {
            z += player.posZ;
        } else {
            player.motionZ = 0.0;
        }
        if (flags.contains(S08PacketPlayerPosLookEnumFlags.RELATIVE_PITCH)) {
            pitch += player.rotationPitch;
        }
        if (flags.contains(S08PacketPlayerPosLookEnumFlags.RELATIVE_YAW)) {
            yaw += player.rotationYaw;
        }

        player.setPositionAndRotation(x, y, z, yaw, pitch);
        this.netManager.sendPacket(
                new PlayerPositionLookPacket(
                        x, player.getEntityBoundingBox().minY, z, yaw, pitch, false));
        if (!this.doneLoadingTerrain) {
            player.prevPosX = player.posX;
            player.prevPosY = player.posY;
            player.prevPosZ = player.posZ;
            this.doneLoadingTerrain = true;
            this.gameController.displayGuiScreen(null);
        }
    }

    public void handleDisconnect(S40PacketDisconnect s40PacketDisconnect) {
        this.netManager.closeChannel(s40PacketDisconnect.getReason());
    }

    public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity s2CPacketSpawnGlobalEntity) {
        PacketThreadUtil.checkThreadAndEnqueue(
                s2CPacketSpawnGlobalEntity, this, this.gameController);
        double d0 = s2CPacketSpawnGlobalEntity.getX() / 32.0;
        double d1 = s2CPacketSpawnGlobalEntity.getY() / 32.0;
        double d2 = s2CPacketSpawnGlobalEntity.getZ() / 32.0;
        EntityLightningBolt entityLightningBolt = null;
        if (s2CPacketSpawnGlobalEntity.getType() == 1) {
            entityLightningBolt = new EntityLightningBolt(this.clientWorldController, d0, d1, d2);
        }

        if (entityLightningBolt != null) {
            entityLightningBolt.serverPosX = s2CPacketSpawnGlobalEntity.getX();
            entityLightningBolt.serverPosY = s2CPacketSpawnGlobalEntity.getY();
            entityLightningBolt.serverPosZ = s2CPacketSpawnGlobalEntity.getZ();
            entityLightningBolt.rotationYaw = 0.0F;
            entityLightningBolt.rotationPitch = 0.0F;
            entityLightningBolt.setEntityId(s2CPacketSpawnGlobalEntity.getEntityId());
            this.clientWorldController.addWeatherEffect(entityLightningBolt);
        }
    }

    public void handleUpdateScore(S3CPacketUpdateScore s3CPacketUpdateScore) {
        getLegacyPlayHandlerFlowMarker();

        PacketThreadUtil.checkThreadAndEnqueue(s3CPacketUpdateScore, this, this.gameController);
        Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        ScoreObjective objective = scoreboard.getObjective(s3CPacketUpdateScore.getObjectiveName());
        String text = s3CPacketUpdateScore.getPlayerName();

        switch (s3CPacketUpdateScore.getScoreAction()) {
            case CHANGE:
                Score valueFromObjective = scoreboard.getValueFromObjective(text, objective);
                valueFromObjective.setScorePoints(s3CPacketUpdateScore.getScoreValue());

                break;

            case REMOVE:
                if (StringUtils.isNullOrEmpty(s3CPacketUpdateScore.getObjectiveName())) {
                    scoreboard.removeObjectiveFromEntity(text, null);

                    return;
                }

                if (objective != null) {
                    scoreboard.removeObjectiveFromEntity(text, objective);
                }
        }
    }

    public void handleEntityMetadata(S1CPacketEntityMetadata s1CPacketEntityMetadata) {
        PacketThreadUtil.checkThreadAndEnqueue(s1CPacketEntityMetadata, this, this.gameController);
        Entity entity =
                this.clientWorldController.getEntityByID(s1CPacketEntityMetadata.getEntityId());
        List list = s1CPacketEntityMetadata.getWatchableObjects();
        if (entity != null && list != null) {
            entity.getDataWatcher().updateWatchedObjectsFromList(list);
        }
    }

    public void handleUpdateHealth(S06PacketUpdateHealth s06PacketUpdateHealth) {
        PacketThreadUtil.checkThreadAndEnqueue(s06PacketUpdateHealth, this, this.gameController);
        LocalPlayer player = this.gameController.player;
        player.setPlayerSPHealth(s06PacketUpdateHealth.getHealth());
        FoodStats foodStats = player.getFoodStats();
        foodStats.setFoodLevel(s06PacketUpdateHealth.getFoodLevel());
        foodStats.setFoodSaturationLevel(s06PacketUpdateHealth.getSaturationLevel());
    }

    public void sendPacketNoEvent(Packet packet) {
        this.netManager.sendPacket(packet);
    }

    public void handleEntityTeleport(S18PacketEntityTeleport s18PacketEntityTeleport) {
        getLegacyPlayHandlerFlowMarker();

        PacketThreadUtil.checkThreadAndEnqueue(s18PacketEntityTeleport, this, this.gameController);

        Entity entity =
                this.clientWorldController.getEntityByID(s18PacketEntityTeleport.getEntityId());
        if (entity != null) {
            {
                entity.serverPosX = s18PacketEntityTeleport.getX();
                entity.serverPosY = s18PacketEntityTeleport.getY();
                entity.serverPosZ = s18PacketEntityTeleport.getZ();
                double d0 = entity.serverPosX / 32.0;
                double d1 = entity.serverPosY / 32.0 + 0.015625;
                double d2 = entity.serverPosZ / 32.0;
                float f = s18PacketEntityTeleport.getYaw() * 360 / 256.0F;
                float f1 = s18PacketEntityTeleport.getPitch() * 360 / 256.0F;
                if (Math.abs(entity.posX - d0) < 0.03125
                        && Math.abs(entity.posY - d1) < 0.015625
                        && Math.abs(entity.posZ - d2) < 0.03125) {
                    entity.setPositionAndRotation2(
                            entity.posX, entity.posY, entity.posZ, f, f1, 3, true);

                } else {

                    entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3, true);
                }
            }

            entity.onGround = s18PacketEntityTeleport.getOnGround();
        }
    }

    public void handleWorldBorder(S44PacketWorldBorder s44PacketWorldBorder) {
        PacketThreadUtil.checkThreadAndEnqueue(s44PacketWorldBorder, this, this.gameController);
        s44PacketWorldBorder.applyToBorder(this.clientWorldController.getWorldBorder());
    }

    public void handleEntityEffect(S1DPacketEntityEffect s1DPacketEntityEffect) {
        PacketThreadUtil.checkThreadAndEnqueue(s1DPacketEntityEffect, this, this.gameController);
        Entity entity =
                this.clientWorldController.getEntityByID(s1DPacketEntityEffect.getEntityId());
        if (entity instanceof LivingEntity) {
            PotionEffect potionEffect =
                    new PotionEffect(
                            s1DPacketEntityEffect.getEffectId(),
                            s1DPacketEntityEffect.getDuration(),
                            s1DPacketEntityEffect.getAmplifier(),
                            false,
                            s1DPacketEntityEffect.shouldShowParticles());
            potionEffect.setPotionDurationMax(s1DPacketEntityEffect.isDurationCapped());
            ((LivingEntity) entity).addPotionEffect(potionEffect);
        }
    }

    public void handleChangeGameState(S2BPacketChangeGameState s2BPacketChangeGameState) {
        getLegacyPlayHandlerFlowMarker();

        PacketThreadUtil.checkThreadAndEnqueue(s2BPacketChangeGameState, this, this.gameController);
        LocalPlayer player = this.gameController.player;
        int gameState = s2BPacketChangeGameState.getGameState();

        float f = s2BPacketChangeGameState.getValue();
        int j = MathHelper.floor(f + 0.5F);
        if (gameState >= 0
                && gameState < S2BPacketChangeGameState.MESSAGE_NAMES.length
                && S2BPacketChangeGameState.MESSAGE_NAMES[gameState] != null) {
            player.addChatComponentMessage(
                    new ChatComponentTranslation(
                            S2BPacketChangeGameState.MESSAGE_NAMES[gameState]));
        }

        ClientWorld awQValue = this.clientWorldController;
        switch (gameState) {
            case 1:
                awQValue.getWorldInfo().setRaining(true);
                awQValue.setRainStrength(0.0F);

                break;

            case 2:
                awQValue.getWorldInfo().setRaining(false);
                awQValue.setRainStrength(1.0F);

                break;

            case 3:
                this.gameController.playerController.setGameType(WorldSettingsGameType.getByID(j));

                break;

            case 4:
                this.gameController.displayGuiScreen(new GuiWinGame());

                break;

            case 5:
                GameSettings gameSettings = this.gameController.gameSettings;
                if (f == 0.0F) {
                    this.gameController.displayGuiScreen(new GuiScreenDemo());

                    break;
                }

                if (f == 101.0F) {
                    this.gameController
                            .ingameGUI
                            .getChatGUI()
                            .printChatMessage(
                                    new ChatComponentTranslation(
                                            "demo.help.movement",
                                            GameSettings.getKeyDisplayString(
                                                    gameSettings.keyBindForward.getKeyCode()),
                                            GameSettings.getKeyDisplayString(
                                                    gameSettings.keyBindLeft.getKeyCode()),
                                            GameSettings.getKeyDisplayString(
                                                    gameSettings.keyBindBack.getKeyCode()),
                                            GameSettings.getKeyDisplayString(
                                                    gameSettings.keyBindRight.getKeyCode())));

                    break;
                }

                if (f == 102.0F) {
                    this.gameController
                            .ingameGUI
                            .getChatGUI()
                            .printChatMessage(
                                    new ChatComponentTranslation(
                                            "demo.help.jump",
                                            GameSettings.getKeyDisplayString(
                                                    gameSettings.keyBindJump.getKeyCode())));

                    break;
                }

                if (f != 103.0F) {
                    break;
                }

                this.gameController
                        .ingameGUI
                        .getChatGUI()
                        .printChatMessage(
                                new ChatComponentTranslation(
                                        "demo.help.inventory",
                                        GameSettings.getKeyDisplayString(
                                                gameSettings.keyBindInventory.getKeyCode())));

                break;

            case 6:
                awQValue.playSound(
                        player.posX,
                        player.posY + player.getEyeHeight(),
                        player.posZ,
                        "random.successful_hit",
                        0.18F,
                        0.45F,
                        false);

                break;

            case 7:
                awQValue.setRainStrength(f);

                break;

            case 8:
                awQValue.setThunderStrength(f);

                break;

            case 10:
                awQValue.spawnParticle(
                        EnumParticleTypes.MOB_APPEARANCE,
                        player.posX,
                        player.posY,
                        player.posZ,
                        0.0,
                        0.0,
                        0.0,
                        new int[0]);
                awQValue.playSound(
                        player.posX,
                        player.posY,
                        player.posZ,
                        "mob.guardian.curse",
                        1.0F,
                        1.0F,
                        false);
            case 9:
        }
    }

    public void handlePlayerListItem(S38PacketPlayerListItem s38PacketPlayerListItem) {
        PacketThreadUtil.checkThreadAndEnqueue(s38PacketPlayerListItem, this, this.gameController);
        Iterator iterator = s38PacketPlayerListItem.getEntries().iterator();
        getLegacyPlayHandlerFlowMarker();

        Map map = this.playerInfoMap;

        while (iterator.hasNext()) {
            S38PacketPlayerListItemAddPlayerData s38PacketPlayerListItemAddPlayerData =
                    (S38PacketPlayerListItemAddPlayerData) iterator.next();
            S38PacketPlayerListItemAction s38PacketPlayerListItemAction =
                    s38PacketPlayerListItem.getAction();
            switch (s38PacketPlayerListItemAction) {
                case REMOVE_PLAYER:
                    map.remove(s38PacketPlayerListItemAddPlayerData.getProfile().getId());

                    break;

                default:
                    PlayerInfo playerInfo =
                            (PlayerInfo)
                                    map.get(
                                            s38PacketPlayerListItemAddPlayerData
                                                    .getProfile()
                                                    .getId());
                    if (s38PacketPlayerListItemAction == S38PacketPlayerListItemAction.ADD_PLAYER) {
                        playerInfo = new PlayerInfo(s38PacketPlayerListItemAddPlayerData);
                        map.put(playerInfo.getGameProfile().getId(), playerInfo);
                    }

                    if (playerInfo != null) {
                        switch (s38PacketPlayerListItemAction) {
                            case ADD_PLAYER:
                                playerInfo.setGameType(
                                        s38PacketPlayerListItemAddPlayerData.getGameMode());
                                playerInfo.setResponseTime(
                                        s38PacketPlayerListItemAddPlayerData.getPing());

                                break;

                            case UPDATE_GAME_MODE:
                                playerInfo.setGameType(
                                        s38PacketPlayerListItemAddPlayerData.getGameMode());

                                break;

                            case UPDATE_LATENCY:
                                playerInfo.setResponseTime(
                                        s38PacketPlayerListItemAddPlayerData.getPing());

                                break;

                            case UPDATE_DISPLAY_NAME:
                                playerInfo.setDisplayName(
                                        s38PacketPlayerListItemAddPlayerData.getDisplayName());
                        }
                    }
            }
        }
    }

    public void handleEntityVelocity(S12PacketEntityVelocity s12PacketEntityVelocity) {
        PacketThreadUtil.checkThreadAndEnqueue(s12PacketEntityVelocity, this, this.gameController);
        Entity entity =
                this.clientWorldController.getEntityByID(s12PacketEntityVelocity.getEntityID());
        if (entity != null) {
            entity.setVelocity(
                    s12PacketEntityVelocity.getMotionX() / 8000.0,
                    s12PacketEntityVelocity.getMotionY() / 8000.0,
                    s12PacketEntityVelocity.getMotionZ() / 8000.0);
        }
    }

    public void handleEntityMovement(S14PacketEntity s14PacketEntity) {
        PacketThreadUtil.checkThreadAndEnqueue(s14PacketEntity, this, this.gameController);
        Entity entity = s14PacketEntity.getEntity(this.clientWorldController);
        if (entity != null) {
            entity.serverPosX = entity.serverPosX + s14PacketEntity.getDeltaX();
            entity.serverPosY = entity.serverPosY + s14PacketEntity.getDeltaY();
            entity.serverPosZ = entity.serverPosZ + s14PacketEntity.getDeltaZ();
            double d0 = entity.serverPosX / 32.0;
            double d1 = entity.serverPosY / 32.0;
            double d2 = entity.serverPosZ / 32.0;
            float f =
                    s14PacketEntity.includesRotation()
                            ? s14PacketEntity.getYawByte() * 360 / 256.0F
                            : entity.rotationYaw;
            float f1 =
                    s14PacketEntity.includesRotation()
                            ? s14PacketEntity.getPitchByte() * 360 / 256.0F
                            : entity.rotationPitch;
            entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3, false);
            entity.onGround = s14PacketEntity.getOnGround();
        }
    }

    public void handleTitle(S45PacketTitle s45PacketTitle) {
        PacketThreadUtil.checkThreadAndEnqueue(s45PacketTitle, this, this.gameController);
        S45PacketTitleType s45PacketTitleType = s45PacketTitle.getType();
        String text = null;
        String textValue = null;
        ChatComponent chatComponent = s45PacketTitle.getMessage();
        String textValue2 =
                chatComponent != null ? chatComponent.getFormattedText() : packedTextFragments[0];
        GuiIngame anf = this.gameController.ingameGUI;
        switch (s45PacketTitleType) {
            case TITLE:
                text = textValue2;
                break;
            case SUBTITLE:
                textValue = textValue2;
                break;
            case RESET:
                anf.displayTitle(packedTextFragments[1], packedTextFragments[2], -1, -1, -1);
                anf.resetTitleTimings();
                return;
        }

        anf.displayTitle(
                text,
                textValue,
                s45PacketTitle.getFadeInTime(),
                s45PacketTitle.getDisplayTime(),
                s45PacketTitle.getFadeOutTime());
    }

    public void handleEntityProperties(S20PacketEntityProperties packet) {
        PacketThreadUtil.checkThreadAndEnqueue(packet, this, this.gameController);
        Entity entity = this.clientWorldController.getEntityByID(packet.getEntityId());
        if (entity == null) {
            return;
        }
        if (!(entity instanceof LivingEntity)) {
            throw new IllegalStateException(
                    "Server tried to update attributes of a non-living entity (actually: "
                            + entity
                            + packedTextFragments[3]);
        }

        BaseAttributeMap attributes = ((LivingEntity) entity).getAttributeMap();
        for (Object entry : packet.getSnapshots()) {
            S20PacketEntityPropertiesSnapshot snapshot = (S20PacketEntityPropertiesSnapshot) entry;
            String attributeName = snapshot.getAttributeName();
            IAttributeInstance attribute = attributes.getAttributeInstanceByName(attributeName);
            if (attribute == null) {
                attribute =
                        attributes.registerAttribute(
                                new RangedAttribute(
                                        null,
                                        attributeName,
                                        0.0,
                                        Double.MIN_NORMAL,
                                        Double.MAX_VALUE));
            }

            attribute.setBaseValue(snapshot.getBaseValue());
            attribute.removeAllModifiers();
            for (Object modifier : snapshot.getModifiers()) {
                attribute.applyModifier((AttributeModifier) modifier);
            }
        }
    }

    public void handleWindowProperty(S31PacketWindowProperty s31PacketWindowProperty) {
        PacketThreadUtil.checkThreadAndEnqueue(s31PacketWindowProperty, this, this.gameController);
        LocalPlayer player = this.gameController.player;
        Container bds = player.openContainer;
        if (bds != null && bds.windowId == s31PacketWindowProperty.getWindowId()) {
            bds.updateProgressBar(
                    s31PacketWindowProperty.getVarIndex(), s31PacketWindowProperty.getVarValue());
        }
    }

    public void handleKeepAlive(S00PacketKeepAlive s00PacketKeepAlive) {
        this.addToSendQueue(new C00PacketKeepAlive(s00PacketKeepAlive.getKeepAliveId()));
    }

    public void cleanup() {
        this.clientWorldController = null;
    }

    public static String[] getLegacyPlayHandlerFlowMarker() {
        return net.minecraft.client.network.ClientPlayHandler.legacyPlayHandlerFlowMarker;
    }

    public void handleSpawnPainting(S10PacketSpawnPainting s10PacketSpawnPainting) {
        PacketThreadUtil.checkThreadAndEnqueue(s10PacketSpawnPainting, this, this.gameController);
        EntityPainting entityPainting =
                new EntityPainting(
                        this.clientWorldController,
                        s10PacketSpawnPainting.getPosition(),
                        s10PacketSpawnPainting.getFacing(),
                        s10PacketSpawnPainting.getTitle());
        this.clientWorldController.addEntityToWorld(
                s10PacketSpawnPainting.getEntityID(), entityPainting);
    }

    public void handleStatistics(S37PacketStatistics s37PacketStatistics) {
        MinecraftClient asgValue = this.gameController;
        PacketThreadUtil.checkThreadAndEnqueue(s37PacketStatistics, this, asgValue);
        byte b0 = 0;
        GameSettings gameSettings = asgValue.gameSettings;
        LocalPlayer player = asgValue.player;
        StatFileWriter statFileWriter = player.getStatFileWriter();
        getLegacyPlayHandlerFlowMarker();

        Iterator iterator = s37PacketStatistics.getStatistics().entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            StatBase key = (StatBase) entry.getKey();
            int value = (Integer) entry.getValue();
            if (key.isAchievement() && value > 0) {
                if (this.hasReceivedStatistics && statFileWriter.writeStat(key) == 0) {
                    Achievement achievement = (Achievement) key;
                    asgValue.guiAchievement.displayAchievement(achievement);
                    IStream twitchStream = asgValue.getTwitchStream();
                    MetadataAchievement metadataAchievement = new MetadataAchievement(achievement);

                    twitchStream.sendMetadataEvent(metadataAchievement, 0L);
                    if (key == AchievementList.openInventory) {
                        gameSettings.showInventoryAchievementHint = false;
                        gameSettings.saveOptions();
                    }
                }

                b0 = 1;
            }

            statFileWriter.unlockAchievement(player, key, value);
        }

        if (!this.hasReceivedStatistics && b0 == 0 && gameSettings.showInventoryAchievementHint) {
            asgValue.guiAchievement.displayUnformattedAchievement(AchievementList.openInventory);
        }

        this.hasReceivedStatistics = true;
        if (asgValue.currentScreen instanceof IProgressMeter) {
            ((IProgressMeter) asgValue.currentScreen).doneLoading();
        }
    }

    public void handleUpdateSign(S33PacketUpdateSign s33PacketUpdateSign) {
        PacketThreadUtil.checkThreadAndEnqueue(s33PacketUpdateSign, this, this.gameController);
        byte b0 = 0;
        BlockPos blockPos = s33PacketUpdateSign.getPos();
        ClientWorld world = this.gameController.world;
        if (world.isBlockLoaded(blockPos)) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntitySign) {
                TileEntitySign tileEntitySign = (TileEntitySign) tileEntity;
                if (tileEntitySign.getIsEditable()) {
                    System.arraycopy(
                            s33PacketUpdateSign.getLines(), 0, tileEntitySign.signText, 0, 4);
                    tileEntitySign.markDirty();
                }

                b0 = 1;
            }
        }

        if (b0 == 0 && this.gameController.player != null) {
            this.gameController.player.addChatMessage(
                    new ChatComponentText(
                            "Unable to locate sign at "
                                    + blockPos.getX()
                                    + ", "
                                    + blockPos.getY()
                                    + ", "
                                    + blockPos.getZ()));
        }
    }

    public void handleEntityHeadLook(S19PacketEntityHeadLook s19PacketEntityHeadLook) {
        PacketThreadUtil.checkThreadAndEnqueue(s19PacketEntityHeadLook, this, this.gameController);
        Entity entity = s19PacketEntityHeadLook.getEntity(this.clientWorldController);
        if (entity != null) {
            entity.setRotationYawHead(s19PacketEntityHeadLook.getYaw() * 360 / 256.0F);
        }
    }

    public void handleOpenWindow(S2DPacketOpenWindow s2DPacketOpenWindow) {
        PacketThreadUtil.checkThreadAndEnqueue(s2DPacketOpenWindow, this, this.gameController);
        LocalPlayer player = this.gameController.player;
        int windowId = s2DPacketOpenWindow.getWindowId();
        getLegacyPlayHandlerFlowMarker();

        ChatComponent chatComponent = s2DPacketOpenWindow.getWindowTitle();
        String text = s2DPacketOpenWindow.getGuiId();

        byte b0 = -1;
        switch (text.hashCode()) {
            case 1438413556:
                if (!text.equals("minecraft:container")) {
                    break;
                }

                b0 = 0;

                break;

            case -1879003021:
                if (!text.equals("minecraft:villager")) {
                    break;
                }

                b0 = 1;

                break;

            case -1366784614:
                if (text.equals("EntityHorse")) {
                    b0 = 2;
                }
        }

        switch (b0) {
            case 0:
                player.displayGUIChest(
                        new InventoryBasic(chatComponent, s2DPacketOpenWindow.getSlotCount()));
                player.openContainer.windowId = windowId;

                break;

            case 1:
                player.displayVillagerTradeGui(new NpcMerchant(player, chatComponent));
                player.openContainer.windowId = windowId;

                break;

            case 2:
                Entity entity =
                        this.clientWorldController.getEntityByID(s2DPacketOpenWindow.getEntityId());
                if (!(entity instanceof EntityHorse)) {
                    break;
                }

                player.displayGUIHorse(
                        (EntityHorse) entity,
                        new AnimalChest(chatComponent, s2DPacketOpenWindow.getSlotCount()));
                player.openContainer.windowId = windowId;

                break;

            default:
                if (!s2DPacketOpenWindow.hasSlots()) {
                    player.displayGui(
                            new LocalBlockIntercommunication(
                                    s2DPacketOpenWindow.getGuiId(), chatComponent));
                    player.openContainer.windowId = windowId;

                    return;
                }

                ContainerLocalMenu containerLocalMenu =
                        new ContainerLocalMenu(
                                s2DPacketOpenWindow.getGuiId(),
                                chatComponent,
                                s2DPacketOpenWindow.getSlotCount());
                player.displayGUIChest(containerLocalMenu);
                player.openContainer.windowId = windowId;
        }
    }

    public void handleChunkData(S21PacketChunkData packet) {
        PacketThreadUtil.checkThreadAndEnqueue(packet, this, this.gameController);
        ClientWorld world = this.clientWorldController;
        boolean fullChunk = packet.isFullChunk();
        int sectionMask = packet.getSectionMask();
        int chunkX = packet.getChunkX();
        int chunkZ = packet.getChunkZ();
        if (fullChunk) {
            if (sectionMask == 0) {
                world.doPreChunk(chunkX, chunkZ, false);
                return;
            }
            world.doPreChunk(chunkX, chunkZ, true);
        }

        int blockX = chunkX << 4;
        int blockZ = chunkZ << 4;
        world.invalidateBlockReceiveRegion(blockX, 0, blockZ, blockX + 15, 256, blockZ + 15);
        Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
        chunk.fillChunk(packet.getChunkBytes(), sectionMask, fullChunk);
        world.markBlockRangeForRenderUpdate(blockX, 0, blockZ, blockX + 15, 256, blockZ + 15);
        if (!fullChunk || !(world.provider instanceof WorldProviderSurface)) {
            chunk.resetRelightChecks();
        }
    }

    public GameProfile getGameProfile() {
        return this.profile;
    }

    public void handleEntityEquipment(S04PacketEntityEquipment s04PacketEntityEquipment) {
        PacketThreadUtil.checkThreadAndEnqueue(s04PacketEntityEquipment, this, this.gameController);
        Entity entity =
                this.clientWorldController.getEntityByID(s04PacketEntityEquipment.getEntityID());
        if (entity != null) {
            entity.setCurrentItemOrArmor(
                    s04PacketEntityEquipment.getEquipmentSlot(),
                    s04PacketEntityEquipment.getItemStack());
        }
    }

    // Helper used only by this class.
    public static class ResourcePackCallback
            implements com.google.common.util.concurrent.FutureCallback {
        net.minecraft.client.network.ClientPlayHandler clientPlayHandler;
        java.lang.String b;
        net.minecraft.network.NetworkManager networkManager;

        public void onSuccess(java.lang.Object object) {
            this.networkManager.sendPacket(
                    new net.minecraft.network.play.client.C19PacketResourcePackStatus(
                            this.b,
                            net.minecraft.network.play.client.C19PacketResourcePackStatusAction
                                    .SUCCESSFULLY_LOADED));
        }

        public void onFailure(java.lang.Throwable throwable) {
            this.networkManager.sendPacket(
                    new net.minecraft.network.play.client.C19PacketResourcePackStatus(
                            this.b,
                            net.minecraft.network.play.client.C19PacketResourcePackStatusAction
                                    .FAILED_DOWNLOAD));
        }

        public ResourcePackCallback(
                net.minecraft.client.network.ClientPlayHandler awTValue,
                net.minecraft.network.NetworkManager awSValue,
                java.lang.String text) {
            this.clientPlayHandler = awTValue;
            this.networkManager = awSValue;
            this.b = text;
        }
    }

    // Helper used only by this class.
    public static class NetHandlerPlayClientHandleResourcePackFutureCallback
            implements com.google.common.util.concurrent.FutureCallback {
        java.lang.String b;
        net.minecraft.network.NetworkManager awS;
        net.minecraft.client.network.ClientPlayHandler awT;

        public NetHandlerPlayClientHandleResourcePackFutureCallback(
                net.minecraft.client.network.ClientPlayHandler awTValue,
                net.minecraft.network.NetworkManager awSValue,
                java.lang.String text) {
            this.awT = awTValue;
            this.awS = awSValue;
            this.b = text;
        }

        public void onSuccess(java.lang.Object object) {
            this.awS.sendPacket(
                    new net.minecraft.network.play.client.C19PacketResourcePackStatus(
                            this.b,
                            net.minecraft.network.play.client.C19PacketResourcePackStatusAction
                                    .SUCCESSFULLY_LOADED));
        }

        public void onFailure(java.lang.Throwable throwable) {
            this.awS.sendPacket(
                    new net.minecraft.network.play.client.C19PacketResourcePackStatus(
                            this.b,
                            net.minecraft.network.play.client.C19PacketResourcePackStatusAction
                                    .FAILED_DOWNLOAD));
        }
    }

    // Helper used only by this class.
    public static class NetHandlerPlayClientHandleResourcePackFutureCallback2
            implements com.google.common.util.concurrent.FutureCallback {
        net.minecraft.client.network.ClientPlayHandler awT;
        java.lang.String b;
        net.minecraft.network.NetworkManager awS;

        public void onFailure(java.lang.Throwable throwable) {
            this.awS.sendPacket(
                    new net.minecraft.network.play.client.C19PacketResourcePackStatus(
                            this.b,
                            net.minecraft.network.play.client.C19PacketResourcePackStatusAction
                                    .FAILED_DOWNLOAD));
        }

        public void onSuccess(java.lang.Object object) {
            this.awS.sendPacket(
                    new net.minecraft.network.play.client.C19PacketResourcePackStatus(
                            this.b,
                            net.minecraft.network.play.client.C19PacketResourcePackStatusAction
                                    .SUCCESSFULLY_LOADED));
        }

        public NetHandlerPlayClientHandleResourcePackFutureCallback2(
                net.minecraft.client.network.ClientPlayHandler awTValue,
                net.minecraft.network.NetworkManager awSValue,
                java.lang.String text) {
            this.awT = awTValue;
            this.awS = awSValue;
            this.b = text;
        }
    }
}
