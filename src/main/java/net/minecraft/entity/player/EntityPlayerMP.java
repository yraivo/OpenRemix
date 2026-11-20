package net.minecraft.entity.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityListEntityEggInfo;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S42PacketCombatEventEvent;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.TeamEnumVisible;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.JsonSerializableSet;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class EntityPlayerMP extends PlayerEntity implements ICrafting {
    private Entity spectatingEntity;
    public double managedPosZ;
    private int currentWindowId;
    private float lastHealth;
    private static Logger logger;
    private int lastFoodLevel;
    public boolean playerConqueredTheEnd;
    public NetHandlerPlayServer playerNetServerHandler;

    private static String[] packedTextFragments;
    private boolean chatColours;
    public List loadedChunks;
    public ItemInWorldManager theItemInWorldManager;
    private int lastExperience;
    private boolean wasHungry;
    public int ping;
    private long playerLastActiveTime;
    public double managedPosX;
    private int respawnInvulnerabilityTicks;
    private List destroyedItemsNetCache;
    private EntityPlayerEnumChatVisibility chatVisibility;
    public boolean isChangingQuantityOnly;
    private float combinedHealth;
    private StatisticsFile statsFile;
    private String translator = "en_US";
    public MinecraftServer mcServer;

    private static Throwable method_c246cb5bfc0059f69bf5(Throwable throwable) {
        return throwable;
    }

    public void onItemPickup(Entity entity, int p171_1) {
        super.onItemPickup(entity, p171_1);
        this.openContainer.detectAndSendChanges();
    }

    public void addSelfToInternalCraftingInventory() {
        this.openContainer.onCraftGuiOpened(this);
    }

    protected void onChangedPotionEffect(PotionEffect potionEffect, boolean flag) {
        super.onChangedPotionEffect(potionEffect, flag);
        this.playerNetServerHandler.sendPacket(
                new S1DPacketEntityEffect(this.getEntityId(), potionEffect));
    }

    public void sendEndCombat() {
        super.sendEndCombat();
        this.playerNetServerHandler.sendPacket(
                new S42PacketCombatEvent(
                        this.getCombatTracker(), S42PacketCombatEventEvent.END_COMBAT));
    }

    public boolean isSpectator() {
        return this.theItemInWorldManager.getGameType() == WorldSettingsGameType.SPECTATOR;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        int i =
                this.mcServer.isDedicatedServer()
                                && this.canPlayersAttack()
                                && "fall".equals(damageSource.damageType)
                        ? 1
                        : 0;
        if (i == 0
                && this.respawnInvulnerabilityTicks > 0
                && damageSource != DamageSource.outOfWorld) {
            return false;
        }

        if (damageSource instanceof EntityDamageSource) {
            Entity entity = damageSource.getEntity();
            if (entity instanceof PlayerEntity && !this.canAttackPlayer((PlayerEntity) entity)) {
                return false;
            }

            if (entity instanceof EntityArrow) {
                EntityArrow entityArrow = (EntityArrow) entity;
                if (entityArrow.shootingEntity instanceof PlayerEntity
                        && !this.canAttackPlayer((PlayerEntity) entityArrow.shootingEntity)) {
                    return false;
                }
            }
        }

        return super.attackEntityFrom(damageSource, f);
    }

    public void mountEntity(Entity entity) {
        net.minecraft.entity.Entity ridingEntityValue = this.ridingEntity;

        try {
            super.mountEntity(entity);
            if (entity != ridingEntityValue) {
                this.playerNetServerHandler.sendPacket(
                        new net.minecraft.network.play.server.S1BPacketEntityAttach(
                                0, this, this.ridingEntity));
                this.playerNetServerHandler.setPlayerLocation(
                        this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.entity.player.EntityPlayerMP.method_c246cb5bfc0059f69bf5(
                            reportedException));
        }
    }

    public void onCriticalHit(Entity entity) {
        this.getServerForPlayer()
                .getEntityTracker()
                .sendToTrackingPlayersAndEntity(this, new S0BPacketAnimation(entity, 4));
    }

    protected void onNewPotionEffect(PotionEffect potionEffect) {
        super.onNewPotionEffect(potionEffect);
        this.playerNetServerHandler.sendPacket(
                new S1DPacketEntityEffect(this.getEntityId(), potionEffect));
    }

    protected void nq() {
        int j = InventoryPlayer.getLegacyInventoryFlowToken();
        BiomeGenBase biomeGenForCoords =
                this.world.getBiomeGenForCoords(
                        new BlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ)));
        int i = j;
        String text = biomeGenForCoords.biomeName;
        JsonSerializableSet jsonSerializableSet =
                (JsonSerializableSet)
                        this.getStatFile()
                                .method_c2fe49aaad864d954ea4(AchievementList.exploreAllBiomes);
        if (jsonSerializableSet == null) {
            jsonSerializableSet =
                    (JsonSerializableSet)
                            this.getStatFile()
                                    .func_150872_a(
                                            AchievementList.exploreAllBiomes,
                                            new JsonSerializableSet());
        }

        jsonSerializableSet.add(text);
        if (this.getStatFile().canUnlockAchievement(AchievementList.exploreAllBiomes)
                && jsonSerializableSet.size() >= BiomeGenBase.explorationBiomesList.size()) {
            HashSet hashset = Sets.newHashSet(BiomeGenBase.explorationBiomesList);

            for (java.util.Iterator<?> __recoveryIterator171_8395 =
                            (jsonSerializableSet).iterator();
                    (__recoveryIterator171_8395).hasNext(); ) {
                String textValue = (String) (__recoveryIterator171_8395).next();
                Iterator iterator = hashset.iterator();

                while (iterator.hasNext()) {
                    BiomeGenBase biomeGenBase = (BiomeGenBase) iterator.next();
                    if (biomeGenBase.biomeName.equals(textValue)) {
                        iterator.remove();
                    }

                    if (i == 0) {
                        break;
                    }
                }

                if (hashset.isEmpty() && i != 0 || i == 0) {
                    break;
                }
            }

            if (hashset.isEmpty()) {
                this.triggerAchievement(AchievementList.exploreAllBiomes);
            }
        }
    }

    public void handleFalling(double d0, boolean flag) {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY - 0.2F);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockPos = new BlockPos(i, j, k);
        Block block = this.world.getBlockState(blockPos).getBlock();
        if (block.getMaterial() == Material.AIR) {
            Block blockValue = this.world.getBlockState(blockPos.offsetDown()).getBlock();
            if (blockValue instanceof BlockFence
                    || blockValue instanceof BlockWall
                    || blockValue instanceof BlockFenceGate) {
                blockPos = blockPos.offsetDown();
                block = this.world.getBlockState(blockPos).getBlock();
            }
        }

        super.updateFallState(d0, flag, block, blockPos);
    }

    static {
        net.minecraft.entity.player.EntityPlayerMP.packedTextFragments = new String[4];
        net.minecraft.entity.player.EntityPlayerMP.packedTextFragments[0] = new String("/");
        net.minecraft.entity.player.EntityPlayerMP.packedTextFragments[1] = new String(":");
        net.minecraft.entity.player.EntityPlayerMP.logger = LogManager.getLogger();
    }

    public ChatComponent getTabListDisplayName() {
        return null;
    }

    public void displayGui(IInteractionObject iInteractionObject) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(
                new S2DPacketOpenWindow(
                        this.currentWindowId,
                        iInteractionObject.getGuiID(),
                        iInteractionObject.getDisplayName()));
        this.openContainer = iInteractionObject.createContainer(this.inventory, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
    }

    public void handleClientSettings(C15PacketClientSettings c15PacketClientSettings) {
        this.translator = c15PacketClientSettings.getLang();
        this.chatVisibility = c15PacketClientSettings.getChatVisibility();
        this.chatColours = c15PacketClientSettings.isColorsEnabled();
        this.getDataWatcher().updateObject(10, (byte) c15PacketClientSettings.getView());
    }

    public void sendProgressBarUpdate(Container container, int p171_1, int p171_2) {
        this.playerNetServerHandler.sendPacket(
                new net.minecraft.network.play.server.S31PacketWindowProperty(
                        container.windowId, p171_1, p171_2));
    }

    public void wakeUpPlayer(boolean p171_0, boolean p171_1, boolean p171_2) {
        try {
            if (this.isPlayerSleeping()) {
                this.getServerForPlayer()
                        .getEntityTracker()
                        .sendToTrackingPlayersAndEntity(
                                this,
                                new net.minecraft.network.play.server.S0BPacketAnimation(this, 2));
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.entity.player.EntityPlayerMP.method_c246cb5bfc0059f69bf5(
                            reportedException));
        }

        try {
            super.wakeUpPlayer(p171_0, p171_1, p171_2);
            if (this.playerNetServerHandler != null) {
                this.playerNetServerHandler.setPlayerLocation(
                        this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.entity.player.EntityPlayerMP.method_c246cb5bfc0059f69bf5(
                            reportedExceptionValue));
        }
    }

    public void displayGUIBook(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item == Items.written_book) {
            this.playerNetServerHandler.sendPacket(
                    new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer())));
        }
    }

    public String getPlayerIP() {
        java.lang.String text =
                this.playerNetServerHandler.netManager.getRemoteAddress().toString();
        text =
                text.substring(
                        text.indexOf(
                                        net.minecraft.entity.player.EntityPlayerMP
                                                .packedTextFragments[0])
                                + 1);
        return text.substring(
                0, text.indexOf(net.minecraft.entity.player.EntityPlayerMP.packedTextFragments[1]));
    }

    protected void onFinishedPotionEffect(PotionEffect potionEffect) {
        super.onFinishedPotionEffect(potionEffect);
        this.playerNetServerHandler.sendPacket(
                new S1EPacketRemoveEntityEffect(this.getEntityId(), potionEffect));
    }

    public void setSpectatingEntity(Entity entity) {
        net.minecraft.entity.Entity entityValue = this.getSpectatingEntity();

        net.minecraft.entity.player.EntityPlayerMP entityPlayerMP;
        java.lang.Object object;
        label25:
        {
            try {
                entityPlayerMP = this;
                if (entity == null) {
                    object = this;
                    break label25;
                }
            } catch (net.minecraft.util.ReportedException reportedException) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.entity.player.EntityPlayerMP.method_c246cb5bfc0059f69bf5(
                                reportedException));
            }

            object = entity;
        }

        try {
            entityPlayerMP.spectatingEntity = (net.minecraft.entity.Entity) object;
            if (entityValue != this.spectatingEntity) {
                this.playerNetServerHandler.sendPacket(
                        new net.minecraft.network.play.server.S43PacketCamera(
                                this.spectatingEntity));
                this.setPositionAndUpdate(
                        this.spectatingEntity.posX,
                        this.spectatingEntity.posY,
                        this.spectatingEntity.posZ);
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.entity.player.EntityPlayerMP.method_c246cb5bfc0059f69bf5(
                            reportedExceptionValue));
        }
    }

    protected void onItemUseFinish() {
        this.playerNetServerHandler.sendPacket(
                new net.minecraft.network.play.server.S19PacketEntityStatus(this, (byte) 9));
        super.onItemUseFinish();
    }

    public StatisticsFile getStatFile() {
        return this.statsFile;
    }

    public void travelToDimension(int i) {
        int j = InventoryPlayer.getLegacyInventoryFlowToken();
        if (this.dimension == 1 && i == 1) {
            this.triggerAchievement(AchievementList.theEnd2);
            this.world.removeEntity(this);
            this.playerConqueredTheEnd = true;
            this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(4, 0.0F));
            if (j != 0) {
                return;
            }
        }

        {
            if (this.dimension == 0 && i == 1) {
                this.triggerAchievement(AchievementList.theEnd);
                BlockPos blockPos = this.mcServer.worldServerForDimension(i).getSpawnCoordinate();
                if (blockPos != null) {
                    this.playerNetServerHandler.setPlayerLocation(
                            blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0.0F, 0.0F);
                }

                i = 1;
                if (!(j != 0)) {

                    this.triggerAchievement(AchievementList.portal);
                }
            } else {

                this.triggerAchievement(AchievementList.portal);
            }
        }

        this.mcServer.getConfigurationManager().transferPlayerToDimension(this, i);
        this.lastExperience = -1;
        this.lastHealth = -1.0F;
        this.lastFoodLevel = -1;
    }

    public void updateCraftingInventory(Container container, List list) {
        this.playerNetServerHandler.sendPacket(new S30PacketWindowItems(container.windowId, list));
        this.playerNetServerHandler.sendPacket(
                new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
    }

    public void resetStat(StatBase statBase) {
        int i = InventoryPlayer.getLegacyInventoryFlowToken();
        if (statBase != null) {
            this.statsFile.unlockAchievement(this, statBase, 0);

            for (java.util.Iterator<?> __recoveryIterator171_22119 =
                            (this.getWorldScoreboard()
                                            .getObjectivesFromCriteria(statBase.func_150952_k()))
                                    .iterator();
                    (__recoveryIterator171_22119).hasNext(); ) {
                ScoreObjective scoreObjective =
                        (ScoreObjective) (__recoveryIterator171_22119).next();
                this.getWorldScoreboard()
                        .getValueFromObjective(this.getName(), scoreObjective)
                        .setScorePoints(0);
                if (i == 0) {
                    break;
                }
            }

            if (this.statsFile.func_150879_e()) {
                this.statsFile.method_5a6071bb02cc6d17b169(this);
            }
        }
    }

    public void removeExperienceLevel(int i) {
        super.removeExperienceLevel(i);
        this.lastExperience = -1;
    }

    public void closeContainer() {
        this.openContainer.onContainerClosed(this);
        this.openContainer = this.inventoryContainer;
    }

    public void onEnchantmentCritical(Entity entity) {
        this.getServerForPlayer()
                .getEntityTracker()
                .sendToTrackingPlayersAndEntity(this, new S0BPacketAnimation(entity, 5));
    }

    public boolean canAttackPlayer(PlayerEntity playerEntity) {
        try {
            if (!this.canPlayersAttack()) {
                return false;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.entity.player.EntityPlayerMP.method_c246cb5bfc0059f69bf5(
                            reportedException));
        }

        return super.canAttackPlayer(playerEntity);
    }

    public BlockPos getPosition() {
        return new net.minecraft.util.BlockPos(this.posX, this.posY + 0.5, this.posZ);
    }

    public void sendEnterCombat() {
        super.sendEnterCombat();
        this.playerNetServerHandler.sendPacket(
                new S42PacketCombatEvent(
                        this.getCombatTracker(), S42PacketCombatEventEvent.ENTER_COMBAT));
    }

    public void func_175173_a(Container container, IInventory iInventory) {
        int k = InventoryPlayer.getLegacyInventoryFlowMarker();
        int j = 0;
        int i = k;

        while (j < iInventory.getFieldCount()) {
            this.playerNetServerHandler.sendPacket(
                    new S31PacketWindowProperty(container.windowId, j, iInventory.getField(j)));
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public void displayGUIChest(IInventory iInventory) {
        int i = InventoryPlayer.getLegacyInventoryFlowMarker();
        if (this.openContainer != this.inventoryContainer) {
            this.closeScreen();
        }

        if (iInventory instanceof ILockableContainer) {
            ILockableContainer iLockableContainer = (ILockableContainer) iInventory;
            if (iLockableContainer.isLocked()
                    && !this.canOpen(iLockableContainer.getLockCode())
                    && !this.isSpectator()) {
                this.playerNetServerHandler.sendPacket(
                        new S02PacketChat(
                                new ChatComponentTranslation(
                                        "container.isLocked", iInventory.getDisplayName()),
                                (byte) 2));
                this.playerNetServerHandler.sendPacket(
                        new S29PacketSoundEffect(
                                "random.door_close", this.posX, this.posY, this.posZ, 1.0F, 1.0F));
                return;
            }
        }

        label25:
        {
            this.getNextWindowId();
            if (iInventory instanceof IInteractionObject) {
                this.playerNetServerHandler.sendPacket(
                        new S2DPacketOpenWindow(
                                this.currentWindowId,
                                ((IInteractionObject) iInventory).getGuiID(),
                                iInventory.getDisplayName(),
                                iInventory.getSizeInventory()));
                this.openContainer =
                        ((IInteractionObject) iInventory).createContainer(this.inventory, this);
                if (i == 0) {
                    break label25;
                }
            }

            this.playerNetServerHandler.sendPacket(
                    new S2DPacketOpenWindow(
                            this.currentWindowId,
                            "minecraft:container",
                            iInventory.getDisplayName(),
                            iInventory.getSizeInventory()));
            this.openContainer = new ContainerChest(this.inventory, iInventory, this);
        }

        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
    }

    private void sendTileEntityUpdate(TileEntity tileEntity) {
        if (tileEntity != null) {
            net.minecraft.network.Packet packet = tileEntity.getDescriptionPacket();

            try {
                if (packet != null) {
                    this.playerNetServerHandler.sendPacket(packet);
                }
            } catch (net.minecraft.util.ReportedException reportedException) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.entity.player.EntityPlayerMP.method_c246cb5bfc0059f69bf5(
                                reportedException));
            }
        }
    }

    public long getLastActiveTime() {
        return this.playerLastActiveTime;
    }

    public void setPlayerHealthUpdated() {
        this.lastHealth = -1.0E8F;
    }

    public void sendSlotContents(Container container, int p171_1, ItemStack itemStack) {
        if (!(container.getSlot(p171_1) instanceof net.minecraft.inventory.SlotCrafting)
                && !this.isChangingQuantityOnly) {
            this.playerNetServerHandler.sendPacket(
                    new net.minecraft.network.play.server.S2FPacketSetSlot(
                            container.windowId, p171_1, itemStack));
        }
    }

    public void displayGUIHorse(EntityHorse entityHorse, IInventory iInventory) {
        if (this.openContainer != this.inventoryContainer) {
            this.closeScreen();
        }

        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(
                new S2DPacketOpenWindow(
                        this.currentWindowId,
                        "EntityHorse",
                        iInventory.getDisplayName(),
                        iInventory.getSizeInventory(),
                        entityHorse.getEntityId()));
        this.openContainer =
                new ContainerHorseInventory(this.inventory, iInventory, entityHorse, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        int j = InventoryPlayer.getLegacyInventoryFlowMarker();
        super.readEntityFromNBT(compoundTag);
        int i = j;
        if (compoundTag.hasKey("playerGameType", 99)) {
            if (MinecraftServer.getServer().getForceGamemode()) {
                this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
                if (i == 0) {
                    return;
                }
            }

            this.theItemInWorldManager.setGameType(
                    WorldSettingsGameType.getByID(compoundTag.getInteger("playerGameType")));
        }
    }

    public void attackTargetEntityWithCurrentItem(Entity entity) {
        int i = InventoryPlayer.getLegacyInventoryFlowToken();
        if (this.theItemInWorldManager.getGameType() == WorldSettingsGameType.SPECTATOR) {
            this.setSpectatingEntity(entity);
            if (i != 0) {
                return;
            }
        }

        super.attackTargetEntityWithCurrentItem(entity);
    }

    public void setPositionAndUpdate(double p171_0, double p171_1, double p171_2) {
        this.playerNetServerHandler.setPlayerLocation(
                p171_0, p171_1, p171_2, this.rotationYaw, this.rotationPitch);
    }

    public void sendContainerToPlayer(Container container) {
        this.updateCraftingInventory(container, container.getInventory());
    }

    public void mountEntityAndWakeUp() {
        try {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity(this);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.entity.player.EntityPlayerMP.method_c246cb5bfc0059f69bf5(
                            reportedException));
        }

        try {
            if (this.sleeping) {
                this.wakeUpPlayer(true, false, false);
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.entity.player.EntityPlayerMP.method_c246cb5bfc0059f69bf5(
                            reportedExceptionValue));
        }
    }

    public void setEntityActionState(
            float berValue, float aIBValue, boolean beDValue, boolean p171_3) {
        if (this.ridingEntity != null) {
            if (berValue >= -1.0F && berValue <= 1.0F) {
                this.moveStrafing = berValue;
            }

            if (aIBValue >= -1.0F && aIBValue <= 1.0F) {
                this.moveForward = aIBValue;
            }

            this.isJumping = beDValue;
            this.setSneaking(p171_3);
        }
    }

    public EntityPlayerEnumChatVisibility getChatVisibility() {
        return this.chatVisibility;
    }

    public void closeScreen() {
        this.playerNetServerHandler.sendPacket(
                new net.minecraft.network.play.server.S2EPacketCloseWindow(
                        this.openContainer.windowId));
        this.closeContainer();
    }

    public EntityPlayerEnumStatus trySleep(BlockPos blockPos) {
        EntityPlayerEnumStatus entityPlayerEnumStatus = super.trySleep(blockPos);
        if (entityPlayerEnumStatus == EntityPlayerEnumStatus.OK) {
            S0APacketUseBed s0APacketUseBed = new S0APacketUseBed(this, blockPos);
            this.getServerForPlayer()
                    .getEntityTracker()
                    .sendToAllTrackingEntity(this, s0APacketUseBed);
            this.playerNetServerHandler.setPlayerLocation(
                    this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.playerNetServerHandler.sendPacket(s0APacketUseBed);
        }

        return entityPlayerEnumStatus;
    }

    private boolean canPlayersAttack() {
        return this.mcServer.isPVPEnabled();
    }

    public void cj() {
        int i = InventoryPlayer.getLegacyInventoryFlowMarker();

        try {
            super.onUpdate();
            int j = 0;

            while (j < this.inventory.getSizeInventory()) {
                ItemStack itemStack = this.inventory.getStackInSlot(j);
                if (itemStack != null && itemStack.getItem().isMap()) {
                    Packet packet =
                            ((ItemMapBase) itemStack.getItem())
                                    .createMapDataPacket(itemStack, this.world, this);
                    if (packet != null) {
                        this.playerNetServerHandler.sendPacket(packet);
                    }
                }

                j++;
                if (i != 0) {
                    break;
                }
            }

            if (this.getHealth() != this.lastHealth
                    || this.lastFoodLevel != this.foodStats.getFoodLevel()
                    || (this.foodStats.getSaturationLevel() == 0.0F ? 1 : 0)
                            != remix.compat.compiler.JvmBooleanSlots.toInt(this.wasHungry)) {
                this.playerNetServerHandler.sendPacket(
                        new S06PacketUpdateHealth(
                                this.getHealth(),
                                this.foodStats.getFoodLevel(),
                                this.foodStats.getSaturationLevel()));
                this.lastHealth = this.getHealth();
                this.lastFoodLevel = this.foodStats.getFoodLevel();
                this.wasHungry =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (this.foodStats.getSaturationLevel() == 0.0F ? 1 : 0));
            }

            if (this.getHealth() + this.getAbsorptionAmount() != this.combinedHealth) {
                this.combinedHealth = this.getHealth() + this.getAbsorptionAmount();

                for (java.util.Iterator<?> __recoveryIterator171_34954 =
                                (this.getWorldScoreboard()
                                                .getObjectivesFromCriteria(
                                                        IScoreObjectiveCriteria.health))
                                        .iterator();
                        (__recoveryIterator171_34954).hasNext(); ) {
                    ScoreObjective scoreObjective =
                            (ScoreObjective) (__recoveryIterator171_34954).next();
                    this.getWorldScoreboard()
                            .getValueFromObjective(this.getName(), scoreObjective)
                            .func_96651_a(Arrays.asList(this));
                    if (i != 0) {
                        break;
                    }
                }
            }

            if (this.experienceTotal != this.lastExperience) {
                this.lastExperience = this.experienceTotal;
                this.playerNetServerHandler.sendPacket(
                        new S1FPacketSetExperience(
                                this.experience, this.experienceTotal, this.experienceLevel));
            }

            if (this.ticksExisted % 20 * 5 == 0
                    && !this.getStatFile()
                            .hasAchievementUnlocked(AchievementList.exploreAllBiomes)) {
                this.nq();
            }
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Ticking player");
            CrashReportCategory crashReportCategory =
                    crashReport.makeCategory("Player being ticked");
            this.addEntityCrashInfo(crashReportCategory);
            throw new ReportedException(crashReport);
        }
    }

    public void addChatMessage(ChatComponent chatComponent) {
        this.playerNetServerHandler.sendPacket(
                new net.minecraft.network.play.server.S02PacketChat(chatComponent));
    }

    public boolean isSpectatedByPlayer(EntityPlayerMP entityPlayerMP) {
        return ((entityPlayerMP.isSpectator()
                ? this.getSpectatingEntity() == this
                : (this.isSpectator() ? false : (super.isSpectatedByPlayer(entityPlayerMP)))));
    }

    private void getNextWindowId() {
        this.currentWindowId = this.currentWindowId % 100 + 1;
    }

    public Entity getSpectatingEntity() {
        try {
            if (this.spectatingEntity == null) {
                return this;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.entity.player.EntityPlayerMP.method_c246cb5bfc0059f69bf5(
                            reportedException));
        }

        return this.spectatingEntity;
    }

    public void setGameType(WorldSettingsGameType worldSettingsGameType) {
        {
            this.theItemInWorldManager.setGameType(worldSettingsGameType);
            int j = InventoryPlayer.getLegacyInventoryFlowToken();
            this.playerNetServerHandler.sendPacket(
                    new S2BPacketChangeGameState(3, worldSettingsGameType.getID()));
            int i = j;
            if (worldSettingsGameType == WorldSettingsGameType.SPECTATOR) {
                this.mountEntity((Entity) null);
                if (!(i != 0)) {

                    this.setSpectatingEntity(this);
                }
            } else {

                this.setSpectatingEntity(this);
            }
        }

        this.sendPlayerAbilities();
        this.markPotionsDirty();
    }

    public boolean canCommandSenderUseCommand(int i, String text) {
        if ("seed".equals(text) && !this.mcServer.isDedicatedServer()) {
            return true;
        } else if ("tell".equals(text)
                || "help".equals(text)
                || "me".equals(text)
                || "trigger".equals(text)) {
            return true;
        } else if (this.mcServer.getConfigurationManager().canSendCommands(this.getGameProfile())) {
            UserListOpsEntry entry =
                    (UserListOpsEntry)
                            this.mcServer
                                    .getConfigurationManager()
                                    .getOppedPlayers()
                                    .getEntry(this.getGameProfile());
            return entry != null
                    ? entry.getPermissionLevel() >= i
                    : this.mcServer.getOpPermissionLevel() >= i;
        } else {
            return false;
        }
    }

    public void clonePlayer(PlayerEntity playerEntity, boolean p171_1) {
        super.clonePlayer(playerEntity, p171_1);
        this.lastExperience = -1;
        this.lastHealth = -1.0F;
        this.lastFoodLevel = -1;
        this.destroyedItemsNetCache.addAll(
                ((net.minecraft.entity.player.EntityPlayerMP) playerEntity).destroyedItemsNetCache);
    }

    public void removeEntity(Entity entity) {
        int i = InventoryPlayer.getLegacyInventoryFlowToken();
        if (entity instanceof PlayerEntity) {
            this.playerNetServerHandler.sendPacket(
                    new S13PacketDestroyEntities(new int[] {entity.getEntityId()}));
            if (i != 0) {
                return;
            }
        }

        this.destroyedItemsNetCache.add(entity.getEntityId());
    }

    public void setItemInUse(ItemStack itemStack, int i) {
        super.setItemInUse(itemStack, i);
        if (itemStack != null
                && itemStack.getItem() != null
                && itemStack.getItem().getItemUseAction(itemStack) == EnumAction.EAT) {
            this.getServerForPlayer()
                    .getEntityTracker()
                    .sendToTrackingPlayersAndEntity(this, new S0BPacketAnimation(this, 3));
        }
    }

    protected void updatePotionMetadata() {
        {
            int i = InventoryPlayer.getLegacyInventoryFlowToken();
            if (this.isSpectator()) {
                this.resetPotionEffectMetadata();
                this.setInvisible(true);
                if (!(i != 0)) {

                    super.updatePotionMetadata();
                }
            } else {

                super.updatePotionMetadata();
            }
        }

        this.getServerForPlayer().getEntityTracker().method_5a6071bb02cc6d17b169(this);
    }

    public void onDeath(DamageSource damageSource) {
        int i;
        i = InventoryPlayer.getLegacyInventoryFlowMarker();
        label50:
        if (this.world.getGameRules().getGameRuleBooleanValue("showDeathMessages")) {
            Team team = this.getTeam();
            if (team != null && team.getDeathMessageVisibility() != TeamEnumVisible.ALWAYS) {
                if (team.getDeathMessageVisibility() == TeamEnumVisible.HIDE_FOR_OTHER_TEAMS) {
                    this.mcServer
                            .getConfigurationManager()
                            .sendMessageToAllTeamMembers(
                                    this, this.getCombatTracker().getDeathMessage());
                    if (i == 0) {
                        break label50;
                    }
                }

                if (team.getDeathMessageVisibility() != TeamEnumVisible.HIDE_FOR_OWN_TEAM) {
                    break label50;
                }

                this.mcServer
                        .getConfigurationManager()
                        .sendMessageToTeamOrEvryPlayer(
                                this, this.getCombatTracker().getDeathMessage());
                if (i == 0) {
                    break label50;
                }
            }

            this.mcServer
                    .getConfigurationManager()
                    .sendChatMsg(this.getCombatTracker().getDeathMessage());
        }

        if (!this.world.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.dropAllItems();
        }

        for (java.util.Iterator<?> __recoveryIterator171_42066 =
                        (this.world
                                        .getScoreboard()
                                        .getObjectivesFromCriteria(
                                                IScoreObjectiveCriteria.deathCount))
                                .iterator();
                (__recoveryIterator171_42066).hasNext(); ) {
            ScoreObjective scoreObjective = (ScoreObjective) (__recoveryIterator171_42066).next();
            Score valueFromObjective =
                    this.getWorldScoreboard().getValueFromObjective(this.getName(), scoreObjective);
            valueFromObjective.incrementScore();
            if (i != 0) {
                break;
            }
        }

        LivingEntity livingEntity = this.func_94060_bK();
        if (livingEntity != null) {
            EntityListEntityEggInfo entityListEntityEggInfo =
                    (EntityListEntityEggInfo)
                            EntityList.entityEggs.get(EntityList.getEntityID(livingEntity));
            if (entityListEntityEggInfo != null) {
                this.triggerAchievement(entityListEntityEggInfo.field_151513_e);
            }

            livingEntity.addToPlayerScore(this, this.scoreValue);
        }

        this.triggerAchievement(StatList.deathsStat);
        this.resetStat(StatList.timeSinceDeathStat);
        this.getCombatTracker().reset();
    }

    public WorldServer getServerForPlayer() {
        return (WorldServer) this.world;
    }

    public void displayVillagerTradeGui(IMerchant iMerchant) {
        this.getNextWindowId();
        this.openContainer = new ContainerMerchant(this.inventory, iMerchant, this.world);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
        InventoryMerchant merchantInventory =
                ((ContainerMerchant) this.openContainer).getMerchantInventory();
        ChatComponent chatComponent = iMerchant.getDisplayName();
        this.playerNetServerHandler.sendPacket(
                new S2DPacketOpenWindow(
                        this.currentWindowId,
                        "minecraft:villager",
                        chatComponent,
                        merchantInventory.getSizeInventory()));
        MerchantRecipeList recipes = iMerchant.getRecipes(this);
        if (recipes != null) {
            PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeInt(this.currentWindowId);
            recipes.writeToBuf(packetBuffer);
            this.playerNetServerHandler.sendPacket(
                    new S3FPacketCustomPayload("MC|TrList", packetBuffer));
        }
    }

    public void addChatComponentMessage(ChatComponent chatComponent) {
        this.playerNetServerHandler.sendPacket(new S02PacketChat(chatComponent));
    }

    public EntityPlayerMP(
            MinecraftServer bedValue,
            WorldServer worldServer,
            GameProfile gameprofile,
            ItemInWorldManager bdTValue) {
        super(worldServer, gameprofile);
        this.loadedChunks = Lists.newLinkedList();
        int l = InventoryPlayer.getLegacyInventoryFlowToken();
        this.destroyedItemsNetCache = Lists.newLinkedList();
        this.combinedHealth = Float.MIN_VALUE;
        this.lastHealth = -1.0E8F;
        this.lastFoodLevel = -99999999;
        this.wasHungry = true;
        this.lastExperience = -99999999;
        this.respawnInvulnerabilityTicks = 60;
        int i = l;
        this.chatColours = true;
        this.playerLastActiveTime = System.currentTimeMillis();
        this.spectatingEntity = null;
        bdTValue.thisPlayerMP = this;
        this.theItemInWorldManager = bdTValue;
        BlockPos blockPos = worldServer.getSpawnPoint();
        if (!worldServer.provider.getHasNoSky()
                && worldServer.getWorldInfo().getGameType() != WorldSettingsGameType.ADVENTURE) {
            int j = Math.max(5, bedValue.getSpawnProtectionSize() - 6);
            int k =
                    MathHelper.floor(
                            worldServer
                                    .getWorldBorder()
                                    .getClosestDistance(blockPos.getX(), blockPos.getZ()));
            if (k < j) {
                j = k;
            }

            if (k <= 1) {
                j = 1;
            }

            blockPos =
                    worldServer.getTopSolidOrLiquidBlock(
                            blockPos.add(
                                    this.rand.nextInt(j * 2) - j, 0, this.rand.nextInt(j * 2) - j));
        }

        this.mcServer = bedValue;
        this.statsFile = bedValue.getConfigurationManager().getPlayerStatsFile(this);
        this.stepHeight = 0.0F;
        this.moveToBlockPosAndAngles(blockPos, 0.0F, 0.0F);

        while (!worldServer.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()
                && this.posY < 255.0) {
            this.setPosition(this.posX, this.posY + 1.0, this.posZ);
            if (i == 0) {
                break;
            }
        }
    }

    public void openEditSign(TileEntitySign tileEntitySign) {
        tileEntitySign.setPlayer(this);
        this.playerNetServerHandler.sendPacket(
                new S36PacketSignEditorOpen(tileEntitySign.getPos()));
    }

    public void loadResourcePack(String text, String textValue) {
        this.playerNetServerHandler.sendPacket(
                new net.minecraft.network.play.server.S48PacketResourcePackSend(text, textValue));
    }

    public void addStat(StatBase statBase, int i) {
        int j = InventoryPlayer.getLegacyInventoryFlowToken();
        if (statBase != null) {
            this.statsFile.increaseStat(this, statBase, i);

            for (java.util.Iterator<?> __recoveryIterator171_68457 =
                            (this.getWorldScoreboard()
                                            .getObjectivesFromCriteria(statBase.func_150952_k()))
                                    .iterator();
                    (__recoveryIterator171_68457).hasNext(); ) {
                ScoreObjective scoreObjective =
                        (ScoreObjective) (__recoveryIterator171_68457).next();
                this.getWorldScoreboard()
                        .getValueFromObjective(this.getName(), scoreObjective)
                        .increseScore(i);
                if (j == 0) {
                    break;
                }
            }

            if (this.statsFile.func_150879_e()) {
                this.statsFile.method_5a6071bb02cc6d17b169(this);
            }
        }
    }

    public void addExperienceLevel(int i) {
        super.addExperienceLevel(i);
        this.lastExperience = -1;
    }

    public void onUpdate() {
        int l = InventoryPlayer.getLegacyInventoryFlowToken();
        this.theItemInWorldManager.updateBlockRemoving();
        int i = l;
        this.respawnInvulnerabilityTicks--;
        if (this.hurtResistantTime > 0) {
            this.hurtResistantTime--;
        }

        this.openContainer.detectAndSendChanges();
        if (!this.world.isRemote && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
        }

        label112:
        while (!this.destroyedItemsNetCache.isEmpty()) {
            int j = Math.min(this.destroyedItemsNetCache.size(), Integer.MAX_VALUE);
            int[] aint = new int[j];
            Iterator iterator = this.destroyedItemsNetCache.iterator();
            int k = 0;

            while (true) {
                if (iterator.hasNext() && k < j) {
                    aint[k++] = (Integer) iterator.next();
                    iterator.remove();
                    if (i != 0) {
                        continue;
                    }
                }

                this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(aint));
                if (i == 0) {
                    break label112;
                }
                break;
            }
        }

        if (!this.loadedChunks.isEmpty()) {
            ArrayList arrayList = Lists.newArrayList();
            Iterator iteratorValue = this.loadedChunks.iterator();
            ArrayList arrayListValue = Lists.newArrayList();

            while (iteratorValue.hasNext() && arrayList.size() < 10) {
                label93:
                {
                    ChunkCoordIntPair chunkCoordIntPair = (ChunkCoordIntPair) iteratorValue.next();
                    if (chunkCoordIntPair != null) {
                        if (!this.world.isBlockLoaded(
                                new BlockPos(
                                        chunkCoordIntPair.chunkXPos << 4,
                                        0,
                                        chunkCoordIntPair.chunkZPos << 4))) {
                            break label93;
                        }

                        Chunk chunkFromChunkCoords =
                                this.world.getChunkFromChunkCoords(
                                        chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos);
                        if (!chunkFromChunkCoords.isPopulated()) {
                            break label93;
                        }

                        arrayList.add(chunkFromChunkCoords);
                        arrayListValue.addAll(
                                ((WorldServer) this.world)
                                        .getTileEntitiesIn(
                                                chunkCoordIntPair.chunkXPos * 16,
                                                0,
                                                chunkCoordIntPair.chunkZPos * 16,
                                                chunkCoordIntPair.chunkXPos * 16 + 16,
                                                256,
                                                chunkCoordIntPair.chunkZPos * 16 + 16));
                        iteratorValue.remove();
                        if (i != 0) {
                            break label93;
                        }
                    }

                    iteratorValue.remove();
                }

                if (i == 0) {
                    break;
                }
            }

            if (!arrayList.isEmpty()) {
                {
                    if (arrayList.size() == 1) {
                        this.playerNetServerHandler.sendPacket(
                                new S21PacketChunkData((Chunk) arrayList.get(0), true, 65535));
                        if (!(i != 0)) {

                            this.playerNetServerHandler.sendPacket(
                                    new S26PacketMapChunkBulk(arrayList));
                        }
                    } else {

                        this.playerNetServerHandler.sendPacket(
                                new S26PacketMapChunkBulk(arrayList));
                    }
                }

                for (java.util.Iterator<?> __recoveryIterator171_73983 =
                                (arrayListValue).iterator();
                        (__recoveryIterator171_73983).hasNext(); ) {
                    TileEntity tileEntity = (TileEntity) (__recoveryIterator171_73983).next();
                    this.sendTileEntityUpdate(tileEntity);
                    if (i == 0) {
                        break;
                    }
                }

                for (java.util.Iterator<?> __recoveryIterator171_74240 = (arrayList).iterator();
                        (__recoveryIterator171_74240).hasNext(); ) {
                    Chunk chunk = (Chunk) (__recoveryIterator171_74240).next();
                    this.getServerForPlayer()
                            .getEntityTracker()
                            .method_b463234afe841e8d4890(this, chunk);
                    if (i == 0) {
                        break;
                    }
                }
            }
        }

        Entity entity = this.getSpectatingEntity();
        if (entity != this) {
            if (!entity.isEntityAlive()) {
                this.setSpectatingEntity(this);
                if (i != 0) {
                    return;
                }
            }

            this.setPositionAndRotation(
                    entity.posX,
                    entity.posY,
                    entity.posZ,
                    entity.rotationYaw,
                    entity.rotationPitch);
            this.mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this);
            if (this.isSneaking()) {
                this.setSpectatingEntity(this);
            }
        }
    }

    public void markPlayerActive() {
        this.playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
    }

    public void sendPlayerAbilities() {
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.sendPacket(new S39PacketPlayerAbilities(this.capabilities));
            this.updatePotionMetadata();
        }
    }

    protected void updateFallState(double d0, boolean flag, Block block, BlockPos blockPos) {}

    public void updateHeldItem() {
        if (!this.isChangingQuantityOnly) {
            this.playerNetServerHandler.sendPacket(
                    new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
        }
    }
}
