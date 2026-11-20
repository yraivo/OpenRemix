package net.minecraft.network;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;

import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.GenericFutureListener;

import net.minecraft.block.material.Material;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayerEnumChatVisibility;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.ServerPlayPacketHandler;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatusEnumState;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.DiggingAction;
import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.network.play.client.HeldItemChangePacket;
import net.minecraft.network.play.client.PlayerMovementPacket;
import net.minecraft.network.play.client.UseEntityAction;
import net.minecraft.network.play.client.UseEntityPacket;
import net.minecraft.network.play.client.UseItemPacket;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class NetHandlerPlayServer implements ServerPlayPacketHandler, IUpdatePlayerListBox {
    private double lastPosZ;
    private IntHashMap field_147372_n = new IntHashMap();
    private long lastSentPingPacket;
    private static String[] packedTextFragments;
    private int field_147378_h;
    private static Logger logger;
    private int networkTickCount;
    private double lastPosX;

    private int chatSpamThresholdCount;
    private boolean field_147366_g;
    private int floatingTickCount;
    private long lastPingTime;
    public EntityPlayerMP playerEntity;
    private int itemDropThreshold;
    private int f;
    public NetworkManager netManager;

    private MinecraftServer serverController;
    private boolean hasMoved = true;
    private double lastPosY;

    private void handleSlashCommand(String text) {
        this.serverController.getCommandManager().executeCommand(this.playerEntity, text);
    }

    public void processUseEntity(UseEntityPacket useEntityPacket) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        PacketThreadUtil.checkThreadAndEnqueue(
                useEntityPacket, this, this.playerEntity.getServerForPlayer());
        String textValue = text;
        WorldServer worldServer =
                this.serverController.worldServerForDimension(this.playerEntity.dimension);
        Entity entity = useEntityPacket.getEntityFromWorld(worldServer);
        this.playerEntity.markPlayerActive();
        if (entity != null) {
            boolean flag = this.playerEntity.canEntityBeSeen(entity);
            double d0 = 36.0;
            if (!flag) {
                d0 = 9.0;
            }

            if (this.playerEntity.getDistanceSqToEntity(entity) < d0) {
                if (useEntityPacket.getAction() == UseEntityAction.INTERACT) {
                    this.playerEntity.interactWith(entity);
                    if (textValue != null) {
                        return;
                    }
                }

                if (useEntityPacket.getAction() == UseEntityAction.INTERACT_AT) {
                    entity.interactAt(this.playerEntity, useEntityPacket.getHitVec());
                    if (textValue != null) {
                        return;
                    }
                }

                if (useEntityPacket.getAction() == UseEntityAction.ATTACK) {
                    if (entity instanceof EntityItem
                            || entity instanceof EntityXPOrb
                            || entity instanceof EntityArrow
                            || entity == this.playerEntity) {
                        this.kickPlayerFromServer("Attempting to attack an invalid entity");
                        this.serverController.logWarning(
                                "Player "
                                        + this.playerEntity.getName()
                                        + " tried to attack an invalid entity");
                        return;
                    }

                    this.playerEntity.attackTargetEntityWithCurrentItem(entity);
                }
            }
        }
    }

    public void handleResourcePackStatus(C19PacketResourcePackStatus c19PacketResourcePackStatus) {}

    public void update() {
        this.field_147366_g = false;
        this.networkTickCount++;
        this.serverController.theProfiler.startSection("keepAlive");
        long i = this.networkTickCount - this.lastSentPingPacket;

        if (i > 40L) {
            this.lastSentPingPacket = this.networkTickCount;
            this.lastPingTime = this.currentTimeMillis();
            this.field_147378_h = (int) this.lastPingTime;
            this.sendPacket(new S00PacketKeepAlive(this.field_147378_h));
        }

        this.serverController.theProfiler.endSection();
        if (this.chatSpamThresholdCount > 0) {
            this.chatSpamThresholdCount--;
        }

        if (this.itemDropThreshold > 0) {
            this.itemDropThreshold--;
        }

        i = this.playerEntity.getLastActiveTime();

        if (i > 0L
                && this.serverController.getMaxPlayerIdleMinutes() > 0
                && MinecraftServer.getCurrentTimeMillis() - this.playerEntity.getLastActiveTime()
                        > this.serverController.getMaxPlayerIdleMinutes() * 1000 * 60) {
            this.kickPlayerFromServer("You have been idle for too long!");
        }
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public void processPlayer(PlayerMovementPacket playerMovementPacket) {
        PacketThreadUtil.checkThreadAndEnqueue(
                playerMovementPacket, this, this.playerEntity.getServerForPlayer());
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        WorldServer worldServer =
                this.serverController.worldServerForDimension(this.playerEntity.dimension);
        String textValue = text;
        this.field_147366_g = true;
        if (!this.playerEntity.playerConqueredTheEnd) {
            double posX = this.playerEntity.posX;
            double posY = this.playerEntity.posY;
            double posZ = this.playerEntity.posZ;
            double d3 = 0.0;
            double d4 = playerMovementPacket.getX() - this.lastPosX;
            double d5 = playerMovementPacket.getY() - this.lastPosY;
            double d6 = playerMovementPacket.getZ() - this.lastPosZ;
            if (playerMovementPacket.hasPosition()) {
                d3 = d4 * d4 + d5 * d5 + d6 * d6;
                if (!this.hasMoved && d3 < 0.25) {
                    this.hasMoved = true;
                }
            }

            if (this.hasMoved) {
                this.f = this.networkTickCount;
                if (this.playerEntity.ridingEntity != null) {
                    float rotationYaw = this.playerEntity.rotationYaw;
                    float fx = this.playerEntity.rotationPitch;
                    this.playerEntity.ridingEntity.updateRiderPosition();
                    double posXValue = this.playerEntity.posX;
                    double posYValue = this.playerEntity.posY;
                    double posZValue = this.playerEntity.posZ;
                    if (playerMovementPacket.hasRotation()) {
                        rotationYaw = playerMovementPacket.getYaw();
                        fx = playerMovementPacket.getPitch();
                    }

                    this.playerEntity.onGround = playerMovementPacket.isOnGround();
                    this.playerEntity.cj();
                    this.playerEntity.setPositionAndRotation(
                            posXValue, posYValue, posZValue, rotationYaw, fx);
                    if (this.playerEntity.ridingEntity != null) {
                        this.playerEntity.ridingEntity.updateRiderPosition();
                    }

                    this.serverController
                            .getConfigurationManager()
                            .serverUpdateMountedMovingPlayer(this.playerEntity);
                    if (this.playerEntity.ridingEntity != null) {
                        if (d3 > 4.0) {
                            Entity ridingEntity = this.playerEntity.ridingEntity;
                            this.playerEntity.playerNetServerHandler.sendPacket(
                                    new S18PacketEntityTeleport(ridingEntity));
                            this.setPlayerLocation(
                                    this.playerEntity.posX,
                                    this.playerEntity.posY,
                                    this.playerEntity.posZ,
                                    this.playerEntity.rotationYaw,
                                    this.playerEntity.rotationPitch);
                        }

                        this.playerEntity.ridingEntity.isAirBorne = true;
                    }

                    if (this.hasMoved) {
                        this.lastPosX = this.playerEntity.posX;
                        this.lastPosY = this.playerEntity.posY;
                        this.lastPosZ = this.playerEntity.posZ;
                    }

                    worldServer.updateEntity(this.playerEntity);
                    return;
                }

                if (this.playerEntity.isPlayerSleeping()) {
                    this.playerEntity.cj();
                    this.playerEntity.setPositionAndRotation(
                            this.lastPosX,
                            this.lastPosY,
                            this.lastPosZ,
                            this.playerEntity.rotationYaw,
                            this.playerEntity.rotationPitch);
                    worldServer.updateEntity(this.playerEntity);
                    return;
                }

                double posYValue2 = this.playerEntity.posY;
                this.lastPosX = this.playerEntity.posX;
                this.lastPosY = this.playerEntity.posY;
                this.lastPosZ = this.playerEntity.posZ;
                double posXValue2 = this.playerEntity.posX;
                double posYValue3 = this.playerEntity.posY;
                double posZValue2 = this.playerEntity.posZ;
                float rotationYawValue = this.playerEntity.rotationYaw;
                float rotationPitch = this.playerEntity.rotationPitch;
                if (playerMovementPacket.hasPosition() && playerMovementPacket.getY() == -999.0) {
                    playerMovementPacket.setHasPosition(false);
                }

                if (playerMovementPacket.hasPosition()) {
                    posXValue2 = playerMovementPacket.getX();
                    posYValue3 = playerMovementPacket.getY();
                    posZValue2 = playerMovementPacket.getZ();
                    if (Math.abs(playerMovementPacket.getX()) > 3.0E7
                            || Math.abs(playerMovementPacket.getZ()) > 3.0E7) {
                        this.kickPlayerFromServer("Illegal position");
                        return;
                    }
                }

                if (playerMovementPacket.hasRotation()) {
                    rotationYawValue = playerMovementPacket.getYaw();
                    rotationPitch = playerMovementPacket.getPitch();
                }

                this.playerEntity.cj();
                this.playerEntity.setPositionAndRotation(
                        this.lastPosX,
                        this.lastPosY,
                        this.lastPosZ,
                        rotationYawValue,
                        rotationPitch);
                if (!this.hasMoved) {
                    return;
                }

                double d11 = posXValue2 - this.playerEntity.posX;
                double d12 = posYValue3 - this.playerEntity.posY;
                double d13 = posZValue2 - this.playerEntity.posZ;
                double d14 = Math.min(Math.abs(d11), Math.abs(this.playerEntity.motionX));
                double d15 = Math.min(Math.abs(d12), Math.abs(this.playerEntity.motionY));
                double d16 = Math.min(Math.abs(d13), Math.abs(this.playerEntity.motionZ));
                double d17 = d14 * d14 + d15 * d15 + d16 * d16;
                if (d17 > 100.0
                        && (!this.serverController.isSinglePlayer()
                                || !this.serverController
                                        .getServerOwner()
                                        .equals(this.playerEntity.getName()))) {
                    net.minecraft.network.NetHandlerPlayServer.logger.warn(
                            this.playerEntity.getName()
                                    + " moved too quickly! "
                                    + d11
                                    + packedTextFragments[0]
                                    + d12
                                    + packedTextFragments[1]
                                    + d13
                                    + " ("
                                    + d14
                                    + ", "
                                    + d15
                                    + ", "
                                    + d16
                                    + packedTextFragments[2]);
                    this.setPlayerLocation(
                            this.lastPosX,
                            this.lastPosY,
                            this.lastPosZ,
                            this.playerEntity.rotationYaw,
                            this.playerEntity.rotationPitch);
                    return;
                }

                float f3 = 0.0625F;
                boolean empty =
                        worldServer
                                .getCollidingBoundingBoxes(
                                        this.playerEntity,
                                        this.playerEntity
                                                .getEntityBoundingBox()
                                                .contract(f3, f3, f3))
                                .isEmpty();
                if (this.playerEntity.onGround && !playerMovementPacket.isOnGround() && d12 > 0.0) {
                    this.playerEntity.jump();
                }

                this.playerEntity.moveEntity(d11, d12, d13);
                this.playerEntity.onGround = playerMovementPacket.isOnGround();
                double d18 = d12;
                d11 = posXValue2 - this.playerEntity.posX;
                d12 = posYValue3 - this.playerEntity.posY;
                if (d12 > -0.5 || d12 < 0.5) {
                    d12 = 0.0;
                }

                d13 = posZValue2 - this.playerEntity.posZ;
                d17 = d11 * d11 + d12 * d12 + d13 * d13;
                byte b0 = 0;
                if (d17 > 0.0625
                        && !this.playerEntity.isPlayerSleeping()
                        && !this.playerEntity.theItemInWorldManager.isCreative()) {
                    b0 = 1;
                    net.minecraft.network.NetHandlerPlayServer.logger.warn(
                            this.playerEntity.getName() + " moved wrongly!");
                }

                this.playerEntity.setPositionAndRotation(
                        posXValue2, posYValue3, posZValue2, rotationYawValue, rotationPitch);
                this.playerEntity.addMovementStat(
                        this.playerEntity.posX - posX,
                        this.playerEntity.posY - posY,
                        this.playerEntity.posZ - posZ);
                if (!this.playerEntity.noClip) {
                    boolean emptyValue =
                            worldServer
                                    .getCollidingBoundingBoxes(
                                            this.playerEntity,
                                            this.playerEntity
                                                    .getEntityBoundingBox()
                                                    .contract(f3, f3, f3))
                                    .isEmpty();
                    if (empty
                            && (b0 != 0 || !emptyValue)
                            && !this.playerEntity.isPlayerSleeping()) {
                        this.setPlayerLocation(
                                this.lastPosX,
                                this.lastPosY,
                                this.lastPosZ,
                                rotationYawValue,
                                rotationPitch);
                        return;
                    }
                }

                BoundingBox boundingBox =
                        this.playerEntity
                                .getEntityBoundingBox()
                                .expand(f3, f3, f3)
                                .addCoord(0.0, -0.55, 0.0);
                if (this.serverController.isFlightAllowed()
                        || this.playerEntity.capabilities.allowFlying
                        || worldServer.checkBlockCollision(boundingBox)) {
                    this.floatingTickCount = 0;
                } else if (d18 >= -0.03125) {
                    this.floatingTickCount++;
                    if (this.floatingTickCount > 80) {
                        net.minecraft.network.NetHandlerPlayServer.logger.warn(
                                this.playerEntity.getName() + " was kicked for floating too long!");
                        this.kickPlayerFromServer("Flying is not enabled on this server");
                        return;
                    }
                }

                this.playerEntity.onGround = playerMovementPacket.isOnGround();
                this.serverController
                        .getConfigurationManager()
                        .serverUpdateMountedMovingPlayer(this.playerEntity);
                this.playerEntity.handleFalling(
                        this.playerEntity.posY - posYValue2, playerMovementPacket.isOnGround());
                if (textValue != null) {
                    return;
                }
            }

            if (this.networkTickCount - this.f > 20) {
                this.setPlayerLocation(
                        this.lastPosX,
                        this.lastPosY,
                        this.lastPosZ,
                        this.playerEntity.rotationYaw,
                        this.playerEntity.rotationPitch);
            }
        }
    }

    public void kickPlayerFromServer(String text) {
        ChatComponentText chatComponentText = new ChatComponentText(text);
        this.netManager.sendPacket(
                new S40PacketDisconnect(chatComponentText),
                new ServerDisconnectChannelListener(this, chatComponentText),
                new GenericFutureListener[0]);
        this.netManager.disableAutoRead();
        Futures.getUnchecked(
                this.serverController.addScheduledTask(new ServerDisconnectCheckTask(this)));
    }

    public void processTabComplete(C14PacketTabComplete c14PacketTabComplete) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        PacketThreadUtil.checkThreadAndEnqueue(
                c14PacketTabComplete, this, this.playerEntity.getServerForPlayer());
        ArrayList arrayList = Lists.newArrayList();
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_15496 =
                        (this.serverController.getTabCompletions(
                                        this.playerEntity,
                                        c14PacketTabComplete.getMessage(),
                                        c14PacketTabComplete.getTargetBlock()))
                                .iterator();
                (__recoveryIterator171_15496).hasNext(); ) {
            String textValue2 = (String) (__recoveryIterator171_15496).next();
            arrayList.add(textValue2);
            if (textValue == null) {
                break;
            }
        }

        this.playerEntity.playerNetServerHandler.sendPacket(
                new S3APacketTabComplete(
                        ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]))));
    }

    public void processCloseWindow(C0DPacketCloseWindow c0DPacketCloseWindow) {
        PacketThreadUtil.checkThreadAndEnqueue(
                c0DPacketCloseWindow, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.closeContainer();
    }

    public void sendPacket(Packet packet) {
        if (packet instanceof S02PacketChat) {
            S02PacketChat s02PacketChat = (S02PacketChat) packet;
            EntityPlayerEnumChatVisibility chatVisibility = this.playerEntity.getChatVisibility();
            if (chatVisibility == EntityPlayerEnumChatVisibility.HIDDEN) {
                return;
            }

            if (chatVisibility == EntityPlayerEnumChatVisibility.SYSTEM
                    && !s02PacketChat.isChat()) {
                return;
            }
        }

        try {
            this.netManager.sendPacket(packet);
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Sending packet");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Packet being sent");
            crashReportCategory.addCrashSectionCallable(
                    "Packet class", new ServerPacketTypeCrashDetail(this, packet));
            throw new ReportedException(crashReport);
        }
    }

    public void processConfirmTransaction(C0FPacketConfirmTransaction c0FPacketConfirmTransaction) {
        PacketThreadUtil.checkThreadAndEnqueue(
                c0FPacketConfirmTransaction, this, this.playerEntity.getServerForPlayer());
        Short oshort = (Short) this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
        if (oshort != null
                && c0FPacketConfirmTransaction.getUid() == oshort
                && this.playerEntity.openContainer.windowId == c0FPacketConfirmTransaction.getId()
                && !this.playerEntity.openContainer.getCanCraft(this.playerEntity)
                && !this.playerEntity.isSpectator()) {
            this.playerEntity.openContainer.setCanCraft(this.playerEntity, true);
        }
    }

    private long currentTimeMillis() {
        long i = System.nanoTime();

        return i / 1000000L;
    }

    public void processClickWindow(C0EPacketClickWindow c0EPacketClickWindow) {
        PacketThreadUtil.checkThreadAndEnqueue(
                c0EPacketClickWindow, this, this.playerEntity.getServerForPlayer());
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        this.playerEntity.markPlayerActive();
        String textValue = text;
        if (this.playerEntity.openContainer.windowId == c0EPacketClickWindow.getWindowId()
                && this.playerEntity.openContainer.getCanCraft(this.playerEntity)) {
            if (this.playerEntity.isSpectator()) {
                ArrayList arrayList = Lists.newArrayList();
                int i = 0;

                while (i < this.playerEntity.openContainer.inventorySlots.size()) {
                    arrayList.add(
                            ((net.minecraft.inventory.Slot)
                                            this.playerEntity.openContainer.inventorySlots.get(i))
                                    .getStack());
                    i++;
                    if (textValue == null) {
                        break;
                    }
                }

                this.playerEntity.updateCraftingInventory(
                        this.playerEntity.openContainer, arrayList);
                if (textValue != null) {
                    return;
                }
            }

            ItemStack itemStack =
                    this.playerEntity.openContainer.slotClick(
                            c0EPacketClickWindow.getSlotId(),
                            c0EPacketClickWindow.getUsedButton(),
                            c0EPacketClickWindow.getMode(),
                            this.playerEntity);
            if (ItemStack.areItemStacksEqual(c0EPacketClickWindow.getClickedItem(), itemStack)) {
                this.playerEntity.playerNetServerHandler.sendPacket(
                        new S32PacketConfirmTransaction(
                                c0EPacketClickWindow.getWindowId(),
                                c0EPacketClickWindow.getActionNumber(),
                                true));
                this.playerEntity.isChangingQuantityOnly = true;
                this.playerEntity.openContainer.detectAndSendChanges();
                this.playerEntity.updateHeldItem();
                this.playerEntity.isChangingQuantityOnly = false;
                if (textValue != null) {
                    return;
                }
            }

            this.field_147372_n.addKey(
                    this.playerEntity.openContainer.windowId,
                    c0EPacketClickWindow.getActionNumber());
            this.playerEntity.playerNetServerHandler.sendPacket(
                    new S32PacketConfirmTransaction(
                            c0EPacketClickWindow.getWindowId(),
                            c0EPacketClickWindow.getActionNumber(),
                            false));
            this.playerEntity.openContainer.setCanCraft(this.playerEntity, false);
            ArrayList arrayListValue = Lists.newArrayList();
            int j = 0;

            while (j < this.playerEntity.openContainer.inventorySlots.size()) {
                arrayListValue.add(
                        ((net.minecraft.inventory.Slot)
                                        this.playerEntity.openContainer.inventorySlots.get(j))
                                .getStack());
                j++;
                if (textValue == null) {
                    break;
                }
            }

            this.playerEntity.updateCraftingInventory(
                    this.playerEntity.openContainer, arrayListValue);
        }
    }

    public void processClientStatus(C16PacketClientStatus c16PacketClientStatus) {
        PacketThreadUtil.checkThreadAndEnqueue(
                c16PacketClientStatus, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        C16PacketClientStatusEnumState status = c16PacketClientStatus.getStatus();
        String textValue = text;
        switch (status) {
            case PERFORM_RESPAWN:
                if (this.playerEntity.playerConqueredTheEnd) {
                    this.playerEntity =
                            this.serverController
                                    .getConfigurationManager()
                                    .recreatePlayerEntity(this.playerEntity, 0, true);
                    if (textValue != null) {
                        break;
                    }
                }

                if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
                    if (this.serverController.isSinglePlayer()
                            && this.playerEntity
                                    .getName()
                                    .equals(this.serverController.getServerOwner())) {
                        this.playerEntity.playerNetServerHandler.kickPlayerFromServer(
                                "You have died. Game over, man, it's game over!");
                        this.serverController.deleteWorldAndStopServer();
                        if (textValue != null) {
                            break;
                        }
                    }

                    UserListBansEntry userListBansEntry =
                            new UserListBansEntry(
                                    this.playerEntity.getGameProfile(),
                                    (Date) null,
                                    "(You just lost the game)",
                                    (Date) null,
                                    "Death in Hardcore");
                    this.serverController
                            .getConfigurationManager()
                            .getBannedPlayers()
                            .addEntry(userListBansEntry);
                    this.playerEntity.playerNetServerHandler.kickPlayerFromServer(
                            "You have died. Game over, man, it's game over!");
                    if (textValue != null) {
                        break;
                    }
                }

                if (this.playerEntity.getHealth() > 0.0F) {
                    return;
                }

                this.playerEntity =
                        this.serverController
                                .getConfigurationManager()
                                .recreatePlayerEntity(this.playerEntity, 0, false);
                if (textValue != null) {
                    break;
                }
            case REQUEST_STATS:
                this.playerEntity.getStatFile().method_5a6071bb02cc6d17b169(this.playerEntity);
                if (textValue != null) {
                    break;
                }
            case OPEN_INVENTORY_ACHIEVEMENT:
                this.playerEntity.triggerAchievement(AchievementList.openInventory);
        }
    }

    public void processPlayerDigging(DiggingPacket diggingPacket) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        PacketThreadUtil.checkThreadAndEnqueue(
                diggingPacket, this, this.playerEntity.getServerForPlayer());
        WorldServer worldServer =
                this.serverController.worldServerForDimension(this.playerEntity.dimension);
        BlockPos blockPos = diggingPacket.getPosition();
        this.playerEntity.markPlayerActive();
        String textValue = text;
        switch (diggingPacket.getStatus()) {
            case DROP_ITEM:
                if (!this.playerEntity.isSpectator()) {
                    this.playerEntity.dropOneItem(false);
                }

                return;
            case DROP_ALL_ITEMS:
                if (!this.playerEntity.isSpectator()) {
                    this.playerEntity.dropOneItem(true);
                }

                return;
            case RELEASE_USE_ITEM:
                this.playerEntity.stopUsingItem();
                return;
            case START_DESTROY_BLOCK:
            case ABORT_DESTROY_BLOCK:
            case STOP_DESTROY_BLOCK:
                double d0 = this.playerEntity.posX - (blockPos.getX() + 0.5);
                double d1 = this.playerEntity.posY - (blockPos.getY() + 0.5) + 1.5;
                double d2 = this.playerEntity.posZ - (blockPos.getZ() + 0.5);
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 > 36.0) {
                    return;
                } else if (blockPos.getY() >= this.serverController.getBuildLimit()) {
                    return;
                } else {
                    if (diggingPacket.getStatus() == DiggingAction.START_DESTROY_BLOCK) {
                        if (!this.serverController.isBlockProtected(
                                        worldServer, blockPos, this.playerEntity)
                                && worldServer.getWorldBorder().contains(blockPos)) {
                            this.playerEntity.theItemInWorldManager.onBlockClicked(
                                    blockPos, diggingPacket.getFacing());
                            if (textValue != null) {
                                return;
                            }
                        }

                        this.playerEntity.playerNetServerHandler.sendPacket(
                                new S23PacketBlockChange(worldServer, blockPos));
                        if (textValue != null) {
                            return;
                        }
                    }

                    {
                        if (diggingPacket.getStatus() == DiggingAction.STOP_DESTROY_BLOCK) {
                            this.playerEntity.theItemInWorldManager.blockRemoving(blockPos);
                            if (!(textValue != null)) {

                                if (diggingPacket.getStatus()
                                        == DiggingAction.ABORT_DESTROY_BLOCK) {
                                    this.playerEntity.theItemInWorldManager.cancelDestroyingBlock();
                                }
                            }
                        } else {

                            if (diggingPacket.getStatus() == DiggingAction.ABORT_DESTROY_BLOCK) {
                                this.playerEntity.theItemInWorldManager.cancelDestroyingBlock();
                            }
                        }
                    }

                    if (worldServer.getBlockState(blockPos).getBlock().getMaterial()
                            != Material.AIR) {
                        this.playerEntity.playerNetServerHandler.sendPacket(
                                new S23PacketBlockChange(worldServer, blockPos));
                    }

                    return;
                }
            default:
                throw new IllegalArgumentException("Invalid player action");
        }
    }

    public void processUpdateSign(C12PacketUpdateSign c12PacketUpdateSign) {
        PacketThreadUtil.checkThreadAndEnqueue(
                c12PacketUpdateSign, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        WorldServer worldServer =
                this.serverController.worldServerForDimension(this.playerEntity.dimension);
        BlockPos blockPos = c12PacketUpdateSign.getPosition();
        if (worldServer.isBlockLoaded(blockPos)) {
            TileEntity tileEntity = worldServer.getTileEntity(blockPos);
            if (!(tileEntity instanceof TileEntitySign)) {
                return;
            }

            TileEntitySign tileEntitySign = (TileEntitySign) tileEntity;
            if (!tileEntitySign.getIsEditable()
                    || tileEntitySign.getPlayer() != this.playerEntity) {
                this.serverController.logWarning(
                        "Player "
                                + this.playerEntity.getName()
                                + " just tried to change non-editable sign");
                return;
            }

            System.arraycopy(c12PacketUpdateSign.getLines(), 0, tileEntitySign.signText, 0, 4);
            tileEntitySign.markDirty();
            worldServer.markBlockForUpdate(blockPos);
        }
    }

    public void processEntityAction(C0BPacketEntityAction c0BPacketEntityAction) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        PacketThreadUtil.checkThreadAndEnqueue(
                c0BPacketEntityAction, this, this.playerEntity.getServerForPlayer());
        String textValue = text;
        this.playerEntity.markPlayerActive();
        switch (c0BPacketEntityAction.getAction()) {
            case START_SNEAKING:
                this.playerEntity.setSneaking(true);
                if (textValue != null) {
                    break;
                }
            case STOP_SNEAKING:
                this.playerEntity.setSneaking(false);
                if (textValue != null) {
                    break;
                }
            case START_SPRINTING:
                this.playerEntity.setSprinting(true);
                if (textValue != null) {
                    break;
                }
            case STOP_SPRINTING:
                this.playerEntity.setSprinting(false);
                if (textValue != null) {
                    break;
                }
            case STOP_SLEEPING:
                this.playerEntity.wakeUpPlayer(false, true, true);
                this.hasMoved = false;
                if (textValue != null) {
                    break;
                }
            case RIDING_JUMP:
                if (!(this.playerEntity.ridingEntity instanceof EntityHorse)) {
                    break;
                }

                ((EntityHorse) this.playerEntity.ridingEntity)
                        .setJumpPower(c0BPacketEntityAction.getAuxData());
                if (textValue != null) {
                    break;
                }
            case OPEN_INVENTORY:
                if (this.playerEntity.ridingEntity instanceof EntityHorse) {
                    ((EntityHorse) this.playerEntity.ridingEntity).openGUI(this.playerEntity);
                    if (textValue == null) {
                        throw new IllegalArgumentException("Invalid client command!");
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid client command!");
        }
    }

    public void processHeldItemChange(HeldItemChangePacket heldItemChangePacket) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        PacketThreadUtil.checkThreadAndEnqueue(
                heldItemChangePacket, this, this.playerEntity.getServerForPlayer());
        String textValue = text;
        if (heldItemChangePacket.getSlotId() >= 0
                && heldItemChangePacket.getSlotId() < InventoryPlayer.getHotbarSize()) {
            this.playerEntity.inventory.currentItem = heldItemChangePacket.getSlotId();
            this.playerEntity.markPlayerActive();
            if (textValue != null) {
                return;
            }
        }

        net.minecraft.network.NetHandlerPlayServer.logger.warn(
                this.playerEntity.getName() + " tried to set an invalid carried item");
    }

    static {
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments = new String[16];
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[0] = new String(",");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[1] = new String(",");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[2] = new String(")");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[3] = new String("/");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[4] = new String("x");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[5] = new String("y");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[6] = new String("z");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[7] = new String("x");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[8] = new String("y");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[9] = new String("z");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[10] = new String("x");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[11] = new String("y");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[12] = new String("z");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[13] = new String("");
        net.minecraft.network.NetHandlerPlayServer.packedTextFragments[14] = new String("");
        net.minecraft.network.NetHandlerPlayServer.logger = LogManager.getLogger();
    }

    public void processPlayerAbilities(C13PacketPlayerAbilities c13PacketPlayerAbilities) {
        PacketThreadUtil.checkThreadAndEnqueue(
                c13PacketPlayerAbilities, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.capabilities.isFlying =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (c13PacketPlayerAbilities.isFlying()
                                        && this.playerEntity.capabilities.allowFlying
                                ? 1
                                : 0));
    }

    public void processPlayerBlockPlacement(UseItemPacket useItemPacket) {
        WorldServer worldServer;
        byte b0;
        BlockPos blockPos;
        Facing facing;
        label70:
        {
            PacketThreadUtil.checkThreadAndEnqueue(
                    useItemPacket, this, this.playerEntity.getServerForPlayer());
            worldServer =
                    this.serverController.worldServerForDimension(this.playerEntity.dimension);
            ItemStack itemStack = this.playerEntity.inventory.getCurrentItem();
            b0 = 0;
            String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
            blockPos = useItemPacket.getPosition();
            facing = Facing.getFront(useItemPacket.getPlacedBlockDirection());
            String textValue = text;
            this.playerEntity.markPlayerActive();
            if (useItemPacket.getPlacedBlockDirection() == 255) {
                if (itemStack == null) {
                    return;
                }

                this.playerEntity.theItemInWorldManager.tryUseItem(
                        this.playerEntity, worldServer, itemStack);
                if (textValue != null) {
                    break label70;
                }
            }

            if (blockPos.getY() >= this.serverController.getBuildLimit() - 1
                    && (facing == Facing.UP
                            || blockPos.getY() >= this.serverController.getBuildLimit())) {
                ChatComponentTranslation chatComponentTranslation =
                        new ChatComponentTranslation(
                                "build.tooHigh", this.serverController.getBuildLimit());
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                this.playerEntity.playerNetServerHandler.sendPacket(
                        new S02PacketChat(chatComponentTranslation));
                b0 = 1;
                if (textValue != null) {
                    break label70;
                }
            }

            if (this.hasMoved
                    && this.playerEntity.getDistanceSq(
                                    blockPos.getX() + 0.5,
                                    blockPos.getY() + 0.5,
                                    blockPos.getZ() + 0.5)
                            < 64.0
                    && !this.serverController.isBlockProtected(
                            worldServer, blockPos, this.playerEntity)
                    && worldServer.getWorldBorder().contains(blockPos)) {
                this.playerEntity.theItemInWorldManager.activateBlockOrUseItem(
                        this.playerEntity,
                        worldServer,
                        itemStack,
                        blockPos,
                        facing,
                        useItemPacket.getPlacedBlockOffsetX(),
                        useItemPacket.getPlacedBlockOffsetY(),
                        useItemPacket.getPlacedBlockOffsetZ());
            }

            b0 = 1;
        }

        if (b0 != 0) {
            this.playerEntity.playerNetServerHandler.sendPacket(
                    new S23PacketBlockChange(worldServer, blockPos));
            this.playerEntity.playerNetServerHandler.sendPacket(
                    new S23PacketBlockChange(worldServer, blockPos.offset(facing)));
        }

        ItemStack itemStackValue = this.playerEntity.inventory.getCurrentItem();
        if (itemStackValue != null && itemStackValue.stackSize == 0) {
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] =
                    null;
            itemStackValue = null;
        }

        if (itemStackValue == null || itemStackValue.getMaxItemUseDuration() == 0) {
            this.playerEntity.isChangingQuantityOnly = true;
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] =
                    ItemStack.copyItemStack(
                            this.playerEntity
                                    .inventory
                                    .mainInventory[this.playerEntity.inventory.currentItem]);
            Slot slot =
                    this.playerEntity.openContainer.getSlotFromInventory(
                            this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
            this.playerEntity.openContainer.detectAndSendChanges();
            this.playerEntity.isChangingQuantityOnly = false;
            if (!ItemStack.areItemStacksEqual(
                    this.playerEntity.inventory.getCurrentItem(), useItemPacket.getStack())) {
                this.sendPacket(
                        new S2FPacketSetSlot(
                                this.playerEntity.openContainer.windowId,
                                slot.slotNumber,
                                this.playerEntity.inventory.getCurrentItem()));
            }
        }
    }

    public void setPlayerLocation(double d0, double d1, double d2, float f, float f1, Set set) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        this.hasMoved = false;
        this.lastPosX = d0;
        this.lastPosY = d1;
        this.lastPosZ = d2;
        String textValue = text;
        if (set.contains(S08PacketPlayerPosLookEnumFlags.RELATIVE_X)) {
            this.lastPosX = this.lastPosX + this.playerEntity.posX;
        }

        if (set.contains(S08PacketPlayerPosLookEnumFlags.RELATIVE_Y)) {
            this.lastPosY = this.lastPosY + this.playerEntity.posY;
        }

        if (set.contains(S08PacketPlayerPosLookEnumFlags.RELATIVE_Z)) {
            this.lastPosZ = this.lastPosZ + this.playerEntity.posZ;
        }

        float f2 = f;
        float f3 = f1;
        if (set.contains(S08PacketPlayerPosLookEnumFlags.RELATIVE_YAW)) {
            f2 = f + this.playerEntity.rotationYaw;
        }

        if (set.contains(S08PacketPlayerPosLookEnumFlags.RELATIVE_PITCH)) {
            f3 = f1 + this.playerEntity.rotationPitch;
        }

        this.playerEntity.setPositionAndRotation(
                this.lastPosX, this.lastPosY, this.lastPosZ, f2, f3);
        this.playerEntity.playerNetServerHandler.sendPacket(
                new S08PacketPlayerPosLook(d0, d1, d2, f, f1, set));
        if (textValue == null) {
            Entity.setLegacyGlobalFlowMarker(new String[1]);
        }
    }

    public NetworkManager getNetworkManager() {
        return this.netManager;
    }

    public void processClientSettings(C15PacketClientSettings c15PacketClientSettings) {
        PacketThreadUtil.checkThreadAndEnqueue(
                c15PacketClientSettings, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.handleClientSettings(c15PacketClientSettings);
    }

    public void handleAnimation(C0APacketAnimation c0APacketAnimation) {
        PacketThreadUtil.checkThreadAndEnqueue(
                c0APacketAnimation, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        this.playerEntity.swingItem();
    }

    public void processCreativeInventoryAction(
            C10PacketCreativeInventoryAction c10PacketCreativeInventoryAction) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        PacketThreadUtil.checkThreadAndEnqueue(
                c10PacketCreativeInventoryAction, this, this.playerEntity.getServerForPlayer());
        String textValue = text;
        if (this.playerEntity.theItemInWorldManager.isCreative()) {
            int i = c10PacketCreativeInventoryAction.getSlotId() < 0 ? 1 : 0;
            ItemStack itemStack = c10PacketCreativeInventoryAction.getStack();
            if (itemStack != null
                    && itemStack.hasTagCompound()
                    && itemStack.getTag().hasKey("BlockEntityTag", 10)) {
                CompoundTag compoundTag = itemStack.getTag().getCompoundTag("BlockEntityTag");
                if (compoundTag.hasKey(packedTextFragments[4])
                        && compoundTag.hasKey(packedTextFragments[5])
                        && compoundTag.hasKey(packedTextFragments[6])) {
                    BlockPos blockPos =
                            new BlockPos(
                                    compoundTag.getInteger(packedTextFragments[7]),
                                    compoundTag.getInteger(packedTextFragments[8]),
                                    compoundTag.getInteger(packedTextFragments[9]));
                    TileEntity tileEntity = this.playerEntity.world.getTileEntity(blockPos);
                    if (tileEntity != null) {
                        CompoundTag compoundTagValue = new CompoundTag();
                        tileEntity.writeToNBT(compoundTagValue);
                        compoundTagValue.removeTag(packedTextFragments[10]);
                        compoundTagValue.removeTag(packedTextFragments[11]);
                        compoundTagValue.removeTag(packedTextFragments[12]);
                        itemStack.setTagInfo("BlockEntityTag", compoundTagValue);
                    }
                }
            }

            int j =
                    c10PacketCreativeInventoryAction.getSlotId() >= 1
                                    && c10PacketCreativeInventoryAction.getSlotId()
                                            < 36 + InventoryPlayer.getHotbarSize()
                            ? 1
                            : 0;
            int k = itemStack != null && itemStack.getItem() == null ? 0 : 1;
            int l =
                    itemStack != null
                                    && (itemStack.getItemDamage() < 0
                                            || itemStack.stackSize > 64
                                            || itemStack.stackSize <= 0)
                            ? 0
                            : 1;
            if (j != 0 && k != 0 && l != 0) {
                {
                    if (itemStack == null) {
                        this.playerEntity.inventoryContainer.putStackInSlot(
                                c10PacketCreativeInventoryAction.getSlotId(), (ItemStack) null);
                        if (!(textValue != null)) {

                            this.playerEntity.inventoryContainer.putStackInSlot(
                                    c10PacketCreativeInventoryAction.getSlotId(), itemStack);
                        }
                    } else {

                        this.playerEntity.inventoryContainer.putStackInSlot(
                                c10PacketCreativeInventoryAction.getSlotId(), itemStack);
                    }
                }

                this.playerEntity.inventoryContainer.setCanCraft(this.playerEntity, true);
                if (textValue != null) {
                    return;
                }
            }

            if (i != 0 && k != 0 && l != 0 && this.itemDropThreshold < 200) {
                this.itemDropThreshold += 20;
                EntityItem entityItem =
                        this.playerEntity.dropPlayerItemWithRandomChoice(itemStack, true);
                if (entityItem != null) {
                    entityItem.setAgeToCreativeDespawnTime();
                }
            }
        }
    }

    public void processKeepAlive(C00PacketKeepAlive c00PacketKeepAlive) {
        if (c00PacketKeepAlive.getKey() == this.field_147378_h) {
            int i = (int) (this.currentTimeMillis() - this.lastPingTime);
            this.playerEntity.ping = (this.playerEntity.ping * 3 + i) / 4;
        }
    }

    public void handleSpectate(C18PacketSpectate c18PacketSpectate) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        PacketThreadUtil.checkThreadAndEnqueue(
                c18PacketSpectate, this, this.playerEntity.getServerForPlayer());
        String textValue = text;
        if (this.playerEntity.isSpectator()) {
            Entity entity = null;

            for (WorldServer worldServer : this.serverController.worldServers) {
                if (worldServer != null) {
                    entity = c18PacketSpectate.getEntity(worldServer);
                    if (entity != null && textValue != null) {
                        break;
                    }
                }

                if (textValue == null) {
                    break;
                }
            }

            if (entity != null) {
                this.playerEntity.setSpectatingEntity(this.playerEntity);
                this.playerEntity.mountEntity(null);
                if (entity.world != this.playerEntity.world) {
                    WorldServer serverForPlayer = this.playerEntity.getServerForPlayer();
                    WorldServer world = (WorldServer) entity.world;
                    this.playerEntity.dimension = entity.dimension;
                    this.sendPacket(
                            new S07PacketRespawn(
                                    this.playerEntity.dimension,
                                    serverForPlayer.getDifficulty(),
                                    serverForPlayer.getWorldInfo().getTerrainType(),
                                    this.playerEntity.theItemInWorldManager.getGameType()));
                    serverForPlayer.removePlayerEntityDangerously(this.playerEntity);
                    this.playerEntity.isDead = false;
                    this.playerEntity.setLocationAndAngles(
                            entity.posX,
                            entity.posY,
                            entity.posZ,
                            entity.rotationYaw,
                            entity.rotationPitch);
                    if (this.playerEntity.isEntityAlive()) {
                        serverForPlayer.updateEntityWithOptionalForce(this.playerEntity, false);
                        world.spawnEntityInWorld(this.playerEntity);
                        world.updateEntityWithOptionalForce(this.playerEntity, false);
                    }

                    this.playerEntity.setWorld(world);
                    this.serverController
                            .getConfigurationManager()
                            .preparePlayer(this.playerEntity, serverForPlayer);
                    this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
                    this.playerEntity.theItemInWorldManager.setWorld(world);
                    this.serverController
                            .getConfigurationManager()
                            .updateTimeAndWeatherForPlayer(this.playerEntity, world);
                    this.serverController
                            .getConfigurationManager()
                            .syncPlayerInventory(this.playerEntity);
                } else {
                    this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
                }
            }
        }
    }

    public void onDisconnect(ChatComponent chatComponent) {
        net.minecraft.network.NetHandlerPlayServer.logger.info(
                this.playerEntity.getName() + " lost connection: " + chatComponent);
        this.serverController.refreshStatusNextTick();
        ChatComponentTranslation chatComponentTranslation =
                new ChatComponentTranslation(
                        "multiplayer.player.left", this.playerEntity.getDisplayName());
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        this.serverController.getConfigurationManager().sendChatMsg(chatComponentTranslation);
        this.playerEntity.mountEntityAndWakeUp();
        this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
        if (this.serverController.isSinglePlayer()
                && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
            net.minecraft.network.NetHandlerPlayServer.logger.info(
                    "Stopping singleplayer server as player logged out");
            this.serverController.initiateShutdown();
        }
    }

    public void setPlayerLocation(double d0, double d1, double d2, float f, float f1) {
        this.setPlayerLocation(d0, d1, d2, f, f1, Collections.emptySet());
    }

    public void processEnchantItem(C11PacketEnchantItem c11PacketEnchantItem) {
        PacketThreadUtil.checkThreadAndEnqueue(
                c11PacketEnchantItem, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        if (this.playerEntity.openContainer.windowId == c11PacketEnchantItem.getId()
                && this.playerEntity.openContainer.getCanCraft(this.playerEntity)
                && !this.playerEntity.isSpectator()) {
            this.playerEntity.openContainer.enchantItem(
                    this.playerEntity, c11PacketEnchantItem.getButton());
            this.playerEntity.openContainer.detectAndSendChanges();
        }
    }

    public void processChatMessage(C01PacketChatMessage c01PacketChatMessage) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        PacketThreadUtil.checkThreadAndEnqueue(
                c01PacketChatMessage, this, this.playerEntity.getServerForPlayer());
        String textValue = text;
        if (this.playerEntity.getChatVisibility() == EntityPlayerEnumChatVisibility.HIDDEN) {
            ChatComponentTranslation chatComponentTranslation =
                    new ChatComponentTranslation("chat.cannotSend");
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            this.sendPacket(new S02PacketChat(chatComponentTranslation));
            if (textValue != null) {
                return;
            }
        }

        this.playerEntity.markPlayerActive();
        String textValue2 = c01PacketChatMessage.getMessage();
        textValue2 = StringUtils.normalizeSpace(textValue2);
        int i = 0;

        while (i < textValue2.length()) {
            if (!ChatAllowedCharacters.isAllowedCharacter(textValue2.charAt(i))) {
                this.kickPlayerFromServer("Illegal characters in chat");
                return;
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        {
            if (textValue2.startsWith(packedTextFragments[3])) {
                this.handleSlashCommand(textValue2);
                if (!(textValue != null)) {

                    ChatComponentTranslation chatComponentTranslationValue =
                            new ChatComponentTranslation(
                                    "chat.type.text",
                                    this.playerEntity.getDisplayName(),
                                    textValue2);
                    this.serverController
                            .getConfigurationManager()
                            .sendChatMsgImpl(chatComponentTranslationValue, false);
                }
            } else {

                ChatComponentTranslation chatComponentTranslationValue =
                        new ChatComponentTranslation(
                                "chat.type.text", this.playerEntity.getDisplayName(), textValue2);
                this.serverController
                        .getConfigurationManager()
                        .sendChatMsgImpl(chatComponentTranslationValue, false);
            }
        }

        this.chatSpamThresholdCount += 20;
        if (this.chatSpamThresholdCount > 200
                && !this.serverController
                        .getConfigurationManager()
                        .canSendCommands(this.playerEntity.getGameProfile())) {
            this.kickPlayerFromServer("disconnect.spam");
        }
    }

    public void processVanilla250Packet(C17PacketCustomPayload c17PacketCustomPayload) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        PacketThreadUtil.checkThreadAndEnqueue(
                c17PacketCustomPayload, this, this.playerEntity.getServerForPlayer());
        String textValue = text;
        if ("MC|BEdit".equals(c17PacketCustomPayload.getChannelName())) {
            PacketBuffer packetBuffer =
                    new PacketBuffer(
                            Unpooled.wrappedBuffer(c17PacketCustomPayload.getBufferData()));

            try {
                ItemStack itemStack = packetBuffer.readItemStackFromBuffer();
                if (itemStack == null) {
                    return;
                }

                if (!ItemWritableBook.isNBTValid(itemStack.getTag())) {
                    throw new IOException("Invalid book tag!");
                }

                ItemStack itemStackValue = this.playerEntity.inventory.getCurrentItem();
                if (itemStackValue == null) {
                    return;
                }

                if (itemStack.getItem() == Items.writable_book
                        && itemStack.getItem() == itemStackValue.getItem()) {
                    itemStackValue.setTagInfo("pages", itemStack.getTag().getTagList("pages", 8));
                }
            } catch (Exception exception1) {
                net.minecraft.network.NetHandlerPlayServer.logger.error(
                        "Couldn't handle book info", exception1);
                return;
            } finally {
                packetBuffer.release();
            }
        } else if ("MC|BSign".equals(c17PacketCustomPayload.getChannelName())) {
            PacketBuffer packetBufferValue =
                    new PacketBuffer(
                            Unpooled.wrappedBuffer(c17PacketCustomPayload.getBufferData()));

            try {
                ItemStack itemStackValue2 = packetBufferValue.readItemStackFromBuffer();
                if (itemStackValue2 == null) {
                    return;
                }

                if (!ItemEditableBook.validBookTagContents(itemStackValue2.getTag())) {
                    throw new IOException("Invalid book tag!");
                }

                ItemStack itemStackValue3 = this.playerEntity.inventory.getCurrentItem();
                if (itemStackValue3 == null) {
                    return;
                }

                if (itemStackValue2.getItem() == Items.written_book
                        && itemStackValue3.getItem() == Items.writable_book) {
                    itemStackValue3.setTagInfo(
                            "author", new NBTTagString(this.playerEntity.getName()));
                    itemStackValue3.setTagInfo(
                            "title", new NBTTagString(itemStackValue2.getTag().getString("title")));
                    itemStackValue3.setTagInfo(
                            "pages", itemStackValue2.getTag().getTagList("pages", 8));
                    itemStackValue3.setItem(Items.written_book);
                }
            } catch (Exception exception2) {
                net.minecraft.network.NetHandlerPlayServer.logger.error(
                        "Couldn't sign book", exception2);
                return;
            } finally {
                packetBufferValue.release();
            }
        } else {
            if ("MC|TrSel".equals(c17PacketCustomPayload.getChannelName())) {
                try {
                    int j = c17PacketCustomPayload.getBufferData().readInt();
                    Container bds = this.playerEntity.openContainer;
                    if (bds instanceof ContainerMerchant) {
                        ((ContainerMerchant) bds).setCurrentRecipeIndex(j);
                    }

                    return;
                } catch (Exception exception4) {
                    net.minecraft.network.NetHandlerPlayServer.logger.error(
                            "Couldn't select trade", exception4);
                    if (textValue != null) {
                        return;
                    }
                }
            }

            if ("MC|AdvCdm".equals(c17PacketCustomPayload.getChannelName())) {
                if (!this.serverController.isCommandBlockEnabled()) {
                    this.playerEntity.addChatMessage(
                            new ChatComponentTranslation("advMode.notEnabled"));
                    if (textValue != null) {
                        return;
                    }
                }

                if (this.playerEntity.canCommandSenderUseCommand(2, packedTextFragments[13])
                        && this.playerEntity.capabilities.isCreativeMode) {
                    PacketBuffer packetBufferValue2 = c17PacketCustomPayload.getBufferData();

                    try {
                        byte b0 = packetBufferValue2.readByte();
                        CommandBlockLogic commandBlockLogic = null;
                        if (b0 == 0) {
                            TileEntity tileEntity =
                                    this.playerEntity.world.getTileEntity(
                                            new BlockPos(
                                                    packetBufferValue2.readInt(),
                                                    packetBufferValue2.readInt(),
                                                    packetBufferValue2.readInt()));
                            if (tileEntity instanceof TileEntityCommandBlock) {
                                commandBlockLogic =
                                        ((TileEntityCommandBlock) tileEntity)
                                                .getCommandBlockLogic();
                            }
                        } else if (b0 == 1) {
                            Entity entity =
                                    this.playerEntity.world.getEntityByID(
                                            packetBufferValue2.readInt());
                            if (entity instanceof EntityMinecartCommandBlock) {
                                commandBlockLogic =
                                        ((EntityMinecartCommandBlock) entity)
                                                .getCommandBlockLogic();
                            }
                        }

                        String textValue2 =
                                packetBufferValue2.readStringFromBuffer(
                                        packetBufferValue2.readableBytes());
                        boolean flag = packetBufferValue2.readBoolean();
                        if (commandBlockLogic != null) {
                            commandBlockLogic.setCommand(textValue2);
                            commandBlockLogic.setTrackOutput(flag);
                            if (!flag) {
                                commandBlockLogic.setLastOutput((ChatComponent) null);
                            }

                            commandBlockLogic.updateCommand();
                            this.playerEntity.addChatMessage(
                                    new ChatComponentTranslation(
                                            "advMode.setCommand.success", textValue2));
                        }
                    } catch (Exception exception) {
                        net.minecraft.network.NetHandlerPlayServer.logger.error(
                                "Couldn't set command block", exception);
                    } finally {
                        packetBufferValue2.release();
                    }

                    if (textValue != null) {
                        return;
                    }
                }

                this.playerEntity.addChatMessage(
                        new ChatComponentTranslation("advMode.notAllowed"));
                if (textValue != null) {
                    return;
                }
            }

            if ("MC|Beacon".equals(c17PacketCustomPayload.getChannelName())) {
                if (!(this.playerEntity.openContainer instanceof ContainerBeacon)) {
                    return;
                }

                try {
                    PacketBuffer packetBufferValue3 = c17PacketCustomPayload.getBufferData();
                    int i = packetBufferValue3.readInt();
                    int k = packetBufferValue3.readInt();
                    ContainerBeacon bdsValue = (ContainerBeacon) this.playerEntity.openContainer;
                    Slot slot = bdsValue.getSlot(0);
                    if (slot.getHasStack()) {
                        slot.decrStackSize(1);
                        IInventory iInventory = bdsValue.getTileBeacon();
                        iInventory.setField(1, i);
                        iInventory.setField(2, k);
                        iInventory.markDirty();
                    }

                    return;
                } catch (Exception exception3) {
                    net.minecraft.network.NetHandlerPlayServer.logger.error(
                            "Couldn't set beacon", exception3);
                    if (textValue != null) {
                        return;
                    }
                }
            }

            if ("MC|ItemName".equals(c17PacketCustomPayload.getChannelName())
                    && this.playerEntity.openContainer instanceof ContainerRepair) {
                ContainerRepair bdsValue2 = (ContainerRepair) this.playerEntity.openContainer;
                if (c17PacketCustomPayload.getBufferData() != null
                        && c17PacketCustomPayload.getBufferData().readableBytes() >= 1) {
                    String textValue3 =
                            ChatAllowedCharacters.filterAllowedCharacters(
                                    c17PacketCustomPayload
                                            .getBufferData()
                                            .readStringFromBuffer(32767));
                    if (textValue3.length() <= 30) {
                        bdsValue2.updateItemName(textValue3);
                    }

                    if (textValue != null) {
                        return;
                    }
                }

                bdsValue2.updateItemName(packedTextFragments[14]);
            }
        }
    }

    public void processInput(C0CPacketInput c0CPacketInput) {
        PacketThreadUtil.checkThreadAndEnqueue(
                c0CPacketInput, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.setEntityActionState(
                c0CPacketInput.getStrafeSpeed(),
                c0CPacketInput.getForwardSpeed(),
                c0CPacketInput.isJumping(),
                c0CPacketInput.isSneaking());
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public NetHandlerPlayServer(
            MinecraftServer bvIValue, NetworkManager awKValue, EntityPlayerMP bvHValue) {
        super();
        this.serverController = bvIValue;
        this.netManager = awKValue;
        awKValue.setPacketHandler(this);
        this.playerEntity = bvHValue;
        bvHValue.playerNetServerHandler = this;
    }

    // Helper used only by this class.
    static class ServerDisconnectChannelListener
            implements io.netty.util.concurrent.GenericFutureListener {
        net.minecraft.network.NetHandlerPlayServer field_151288_b;
        net.minecraft.util.ChatComponentText field_151289_a;

        ServerDisconnectChannelListener(
                net.minecraft.network.NetHandlerPlayServer bvJValue,
                net.minecraft.util.ChatComponentText bvKValue) {
            this.field_151288_b = bvJValue;
            this.field_151289_a = bvKValue;
        }

        public void operationComplete(io.netty.util.concurrent.Future future) {
            this.field_151288_b.netManager.closeChannel(this.field_151289_a);
        }
    }
}
