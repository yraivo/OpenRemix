package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
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
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S14PacketEntityS15PacketEntityRelMove;
import net.minecraft.network.play.server.S14PacketEntityS16PacketEntityLook;
import net.minecraft.network.play.server.S14PacketEntityS17PacketEntityLookMove;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.MapData;

import com.google.common.collect.Sets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EntityTrackerEntry {
    public boolean nC;
    private double lO;
    private boolean sendVelocityUpdates;
    private static String[] packedTextFragments;
    private static Logger logger;
    public double sX;
    public int encodedPosY;
    public int encodedPosZ;
    private boolean lc;
    public Set trackingPlayers = Sets.newHashSet();

    public int updateFrequency;
    public int trackingDistanceThreshold;
    private double lM;
    public double sV;
    public double sL;
    public Entity trackedEntity;

    public int lastHeadMotion;
    private Entity field_85178_v;
    private double lastTrackedEntityPosZ;
    private int ticksSinceLastForcedTeleport;
    private boolean onGround;
    private boolean ko;
    public int encodedRotationPitch;
    public int encodedRotationYaw;
    public int encodedPosX;
    public int updateCounter;

    private boolean isPlayerWatchingThisChunk(EntityPlayerMP entityPlayerMP) {
        return entityPlayerMP
                .getServerForPlayer()
                .getPlayerManager()
                .isPlayerWatchingChunk(
                        entityPlayerMP,
                        this.trackedEntity.chunkCoordX,
                        this.trackedEntity.chunkCoordZ);
    }

    static {
        net.minecraft.entity.EntityTrackerEntry.packedTextFragments = new String[2];
        net.minecraft.entity.EntityTrackerEntry.packedTextFragments[0] = new String("!");
        net.minecraft.entity.EntityTrackerEntry.logger = LogManager.getLogger();
    }

    public void sendToTrackedPlayersAndSelf(Packet packet) {
        try {
            this.sendPacketToTrackedPlayers(packet);
            if (this.trackedEntity instanceof net.minecraft.entity.player.EntityPlayerMP) {
                ((net.minecraft.entity.player.EntityPlayerMP) this.trackedEntity)
                        .playerNetServerHandler.sendPacket(packet);
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw net.minecraft.entity.EntityTrackerEntry.method_9a70918140b0a7642dba(illegalargumentexception);
        }
    }

    public void method_d47af718c77504a6451d(EntityPlayerMP entityPlayerMP) {
        try {
            if (this.trackingPlayers.contains(entityPlayerMP)) {
                this.trackingPlayers.remove(entityPlayerMP);
                entityPlayerMP.removeEntity(this.trackedEntity);
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw net.minecraft.entity.EntityTrackerEntry.method_9a70918140b0a7642dba(illegalargumentexception);
        }
    }

    public void sendPacketToTrackedPlayers(Packet packet) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        Iterator iterator = this.trackingPlayers.iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) iterator.next();
            entityPlayerMP.playerNetServerHandler.sendPacket(packet);
            if (astring != null) {
                break;
            }
        }
    }

    public void sendDestroyEntityPacketToTrackedPlayers() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        Iterator iterator = this.trackingPlayers.iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) iterator.next();
            entityPlayerMP.removeEntity(this.trackedEntity);
            if (astring != null) {
                break;
            }
        }
    }

    private void sendMetadataToAllAssociatedPlayers() {
        net.minecraft.entity.DataWatcher dataWatcher = this.trackedEntity.getDataWatcher();

        try {
            if (dataWatcher.hasObjectChanged()) {
                this.sendToTrackedPlayersAndSelf(
                        new net.minecraft.network.play.server.S1CPacketEntityMetadata(
                                this.trackedEntity.getEntityId(), dataWatcher, false));
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw net.minecraft.entity.EntityTrackerEntry.method_9a70918140b0a7642dba(illegalargumentexception);
        }

        if (this.trackedEntity instanceof net.minecraft.entity.LivingEntity) {
            net.minecraft.entity.ai.attributes.ServersideAttributeMap attributeMap =
                    (net.minecraft.entity.ai.attributes.ServersideAttributeMap)
                            ((net.minecraft.entity.LivingEntity) this.trackedEntity).getAttributeMap();
            java.util.Set set = attributeMap.getAttributeInstanceSet();

            try {
                if (!set.isEmpty()) {
                    this.sendToTrackedPlayersAndSelf(
                            new net.minecraft.network.play.server.S20PacketEntityProperties(
                                    this.trackedEntity.getEntityId(), set));
                }
            } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
                throw net.minecraft.entity.EntityTrackerEntry.method_9a70918140b0a7642dba(
                        illegalargumentexception1);
            }

            set.clear();
        }
    }

    public void updatePlayerList(List list) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        this.nC = false;
        String[] astring = astring1;
        if (!this.lc || this.trackedEntity.getDistanceSq(this.lO, this.lM, this.lastTrackedEntityPosZ) > 16.0) {
            this.lO = this.trackedEntity.posX;
            this.lM = this.trackedEntity.posY;
            this.lastTrackedEntityPosZ = this.trackedEntity.posZ;
            this.lc = true;
            this.nC = true;
            this.updatePlayerEntities(list);
        }

        if (this.field_85178_v != this.trackedEntity.ridingEntity
                || this.trackedEntity.ridingEntity != null && this.updateCounter % 60 == 0) {
            this.field_85178_v = this.trackedEntity.ridingEntity;
            this.sendPacketToTrackedPlayers(
                    new S1BPacketEntityAttach(
                            0, this.trackedEntity, this.trackedEntity.ridingEntity));
        }

        if (this.trackedEntity instanceof EntityItemFrame && this.updateCounter % 10 == 0) {
            EntityItemFrame aZjValue = (EntityItemFrame) this.trackedEntity;
            ItemStack itemStack = aZjValue.getDisplayedItem();
            if (itemStack != null && itemStack.getItem() instanceof ItemMap) {
                MapData mapData = Items.filled_map.getMapData(itemStack, this.trackedEntity.world);

                for (java.util.Iterator<?> __recoveryIterator171_29428 = (list).iterator();
                        (__recoveryIterator171_29428).hasNext(); ) {
                    PlayerEntity playerEntity = (PlayerEntity) (__recoveryIterator171_29428).next();
                    EntityPlayerMP entityPlayerMP = (EntityPlayerMP) playerEntity;
                    mapData.updateVisiblePlayers(entityPlayerMP, itemStack);
                    Packet packet =
                            Items.filled_map.createMapDataPacket(
                                    itemStack, this.trackedEntity.world, entityPlayerMP);
                    if (packet != null) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(packet);
                    }

                    if (astring != null) {
                        break;
                    }
                }
            }

            this.sendMetadataToAllAssociatedPlayers();
        }

        if (this.updateCounter % this.updateFrequency == 0
                || this.trackedEntity.isAirBorne
                || this.trackedEntity.getDataWatcher().hasObjectChanged()) {
            label233:
            {
                if (this.trackedEntity.ridingEntity == null) {
                    this.ticksSinceLastForcedTeleport++;
                    int l = MathHelper.floor(this.trackedEntity.posX * 32.0);
                    int k1 = MathHelper.floor(this.trackedEntity.posY * 32.0);
                    int encodedPosZValue = MathHelper.floor(this.trackedEntity.posZ * 32.0);
                    int k2 = MathHelper.floor(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
                    int l2 = MathHelper.floor(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
                    int i3 = l - this.encodedPosX;
                    int j3 = k1 - this.encodedPosY;
                    int i = encodedPosZValue - this.encodedPosZ;
                    Object object = null;
                    int j =
                            Math.abs(i3) < 4
                                            && Math.abs(j3) < 4
                                            && Math.abs(i) < 4
                                            && this.updateCounter % 60 != 0
                                    ? 0
                                    : 1;
                    int k =
                            Math.abs(k2 - this.encodedRotationYaw) < 4
                                            && Math.abs(l2 - this.encodedRotationPitch) < 4
                                    ? 0
                                    : 1;
                    if (this.updateCounter > 0 || this.trackedEntity instanceof EntityArrow) {
                        if (i3 < -128
                                || i3 >= 128
                                || j3 < -128
                                || j3 >= 128
                                || i < -128
                                || i >= 128
                                || this.ticksSinceLastForcedTeleport > 400
                                || this.ko
                                || this.onGround != this.trackedEntity.onGround) {
                            this.onGround = this.trackedEntity.onGround;
                            this.ticksSinceLastForcedTeleport = 0;
                            object =
                                    new S18PacketEntityTeleport(
                                            this.trackedEntity.getEntityId(),
                                            l,
                                            k1,
                                            encodedPosZValue,
                                            (byte) k2,
                                            (byte) l2,
                                            this.trackedEntity.onGround);
                        } else if (j != 0 && k != 0) {
                            object =
                                    new S14PacketEntityS17PacketEntityLookMove(
                                            this.trackedEntity.getEntityId(),
                                            (byte) i3,
                                            (byte) j3,
                                            (byte) i,
                                            (byte) k2,
                                            (byte) l2,
                                            this.trackedEntity.onGround);
                        } else if (j != 0) {
                            object =
                                    new S14PacketEntityS15PacketEntityRelMove(
                                            this.trackedEntity.getEntityId(),
                                            (byte) i3,
                                            (byte) j3,
                                            (byte) i,
                                            this.trackedEntity.onGround);
                        } else if (k != 0) {
                            object =
                                    new S14PacketEntityS16PacketEntityLook(
                                            this.trackedEntity.getEntityId(),
                                            (byte) k2,
                                            (byte) l2,
                                            this.trackedEntity.onGround);
                        }
                    }

                    if (this.sendVelocityUpdates) {
                        double d0 = this.trackedEntity.motionX - this.sV;
                        double d1 = this.trackedEntity.motionY - this.sL;
                        double d2 = this.trackedEntity.motionZ - this.sX;
                        double d3 = 0.02;
                        double d4 = d0 * d0 + d1 * d1 + d2 * d2;
                        if (d4 > d3 * d3
                                || d4 > 0.0
                                        && this.trackedEntity.motionX == 0.0
                                        && this.trackedEntity.motionY == 0.0
                                        && this.trackedEntity.motionZ == 0.0) {
                            this.sV = this.trackedEntity.motionX;
                            this.sL = this.trackedEntity.motionY;
                            this.sX = this.trackedEntity.motionZ;
                            this.sendPacketToTrackedPlayers(
                                    new S12PacketEntityVelocity(
                                            this.trackedEntity.getEntityId(),
                                            this.sV,
                                            this.sL,
                                            this.sX));
                        }
                    }

                    if (object != null) {
                        this.sendPacketToTrackedPlayers((Packet) object);
                    }

                    this.sendMetadataToAllAssociatedPlayers();
                    if (j != 0) {
                        this.encodedPosX = l;
                        this.encodedPosY = k1;
                        this.encodedPosZ = encodedPosZValue;
                    }

                    if (k != 0) {
                        this.encodedRotationYaw = k2;
                        this.encodedRotationPitch = l2;
                    }

                    this.ko = false;
                    if (astring == null) {
                        break label233;
                    }
                }

                int encodedRotationYawValue =
                        MathHelper.floor(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
                int l1 = MathHelper.floor(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
                int j2 =
                        Math.abs(encodedRotationYawValue - this.encodedRotationYaw) < 4
                                        && Math.abs(l1 - this.encodedRotationPitch) < 4
                                ? 0
                                : 1;
                if (j2 != 0) {
                    this.sendPacketToTrackedPlayers(
                            new S14PacketEntityS16PacketEntityLook(
                                    this.trackedEntity.getEntityId(),
                                    (byte) encodedRotationYawValue,
                                    (byte) l1,
                                    this.trackedEntity.onGround));
                    this.encodedRotationYaw = encodedRotationYawValue;
                    this.encodedRotationPitch = l1;
                }

                this.encodedPosX = MathHelper.floor(this.trackedEntity.posX * 32.0);
                this.encodedPosY = MathHelper.floor(this.trackedEntity.posY * 32.0);
                this.encodedPosZ = MathHelper.floor(this.trackedEntity.posZ * 32.0);
                this.sendMetadataToAllAssociatedPlayers();
                this.ko = true;
            }

            int j1 = MathHelper.floor(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
            if (Math.abs(j1 - this.lastHeadMotion) >= 4) {
                this.sendPacketToTrackedPlayers(
                        new S19PacketEntityHeadLook(this.trackedEntity, (byte) j1));
                this.lastHeadMotion = j1;
            }

            this.trackedEntity.isAirBorne = false;
        }

        this.updateCounter++;
        if (this.trackedEntity.velocityChanged) {
            this.sendToTrackedPlayersAndSelf(new S12PacketEntityVelocity(this.trackedEntity));
            this.trackedEntity.velocityChanged = false;
        }
    }

    public EntityTrackerEntry(
            Entity aZjValue,
            int trackingDistanceThresholdValue,
            int j,
            boolean sendVelocityUpdatesValue) {
        this.trackedEntity = aZjValue;
        this.trackingDistanceThreshold = trackingDistanceThresholdValue;
        this.updateFrequency = j;
        this.sendVelocityUpdates = sendVelocityUpdatesValue;
        this.encodedPosX = MathHelper.floor(aZjValue.posX * 32.0);
        this.encodedPosY = MathHelper.floor(aZjValue.posY * 32.0);
        this.encodedPosZ = MathHelper.floor(aZjValue.posZ * 32.0);
        this.encodedRotationYaw = MathHelper.floor(aZjValue.rotationYaw * 256.0F / 360.0F);
        this.encodedRotationPitch = MathHelper.floor(aZjValue.rotationPitch * 256.0F / 360.0F);
        this.lastHeadMotion = MathHelper.floor(aZjValue.getRotationYawHead() * 256.0F / 360.0F);
        this.onGround = aZjValue.onGround;
    }

    public void method_5a6071bb02cc6d17b169(EntityPlayerMP entityPlayerMP) {
        try {
            if (this.trackingPlayers.contains(entityPlayerMP)) {
                entityPlayerMP.removeEntity(this.trackedEntity);
                this.trackingPlayers.remove(entityPlayerMP);
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw net.minecraft.entity.EntityTrackerEntry.method_9a70918140b0a7642dba(illegalargumentexception);
        }
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public boolean func_180233_c(EntityPlayerMP entityPlayerMP) {
        double d0 = entityPlayerMP.posX - this.encodedPosX / 32;
        double d1 = entityPlayerMP.posZ - this.encodedPosZ / 32;
        return (d0 >= -this.trackingDistanceThreshold
                && d0 <= this.trackingDistanceThreshold
                && d1 >= -this.trackingDistanceThreshold
                && d1 <= this.trackingDistanceThreshold
                && this.trackedEntity.isSpectatedByPlayer(entityPlayerMP));
    }

    public void updatePlayerEntities(List list) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        int i = 0;
        String[] astring = astring1;

        while (i < list.size()) {
            this.updatePlayerEntity((EntityPlayerMP) list.get(i));
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    private Packet LD() {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.trackedEntity.isDead) {
            net.minecraft.entity.EntityTrackerEntry.logger.warn("Fetching addPacket for removed entity");
        }

        if (this.trackedEntity instanceof EntityItem) {
            return new S0EPacketSpawnObject(this.trackedEntity, 2, 1);
        }

        if (this.trackedEntity instanceof EntityPlayerMP) {
            return new S0CPacketSpawnPlayer((PlayerEntity) this.trackedEntity);
        }

        if (this.trackedEntity instanceof EntityMinecart) {
            EntityMinecart aZjValue = (EntityMinecart) this.trackedEntity;
            return new S0EPacketSpawnObject(
                    this.trackedEntity, 10, aZjValue.getMinecartType().getNetworkID());
        }

        if (this.trackedEntity instanceof EntityBoat) {
            return new S0EPacketSpawnObject(this.trackedEntity, 1);
        }

        if (this.trackedEntity instanceof IAnimals) {
            this.lastHeadMotion =
                    MathHelper.floor(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
            return new S0FPacketSpawnMob((LivingEntity) this.trackedEntity);
        }

        if (this.trackedEntity instanceof EntityFishHook) {
            PlayerEntity biJ = ((EntityFishHook) this.trackedEntity).angler;
            return new S0EPacketSpawnObject(
                    this.trackedEntity,
                    90,
                    biJ != null ? biJ.getEntityId() : this.trackedEntity.getEntityId());
        }

        if (this.trackedEntity instanceof EntityArrow) {
            Entity biz = ((EntityArrow) this.trackedEntity).shootingEntity;
            return new S0EPacketSpawnObject(
                    this.trackedEntity,
                    60,
                    biz != null ? biz.getEntityId() : this.trackedEntity.getEntityId());
        }

        if (this.trackedEntity instanceof EntitySnowball) {
            return new S0EPacketSpawnObject(this.trackedEntity, 61);
        }

        if (this.trackedEntity instanceof EntityPotion) {
            return new S0EPacketSpawnObject(
                    this.trackedEntity, 73, ((EntityPotion) this.trackedEntity).getPotionDamage());
        }

        if (this.trackedEntity instanceof EntityExpBottle) {
            return new S0EPacketSpawnObject(this.trackedEntity, 75);
        }

        if (this.trackedEntity instanceof EntityEnderPearl) {
            return new S0EPacketSpawnObject(this.trackedEntity, 65);
        }

        if (this.trackedEntity instanceof EntityEnderEye) {
            return new S0EPacketSpawnObject(this.trackedEntity, 72);
        }

        if (this.trackedEntity instanceof EntityFireworkRocket) {
            return new S0EPacketSpawnObject(this.trackedEntity, 76);
        }

        if (!(this.trackedEntity instanceof EntityFireball)) {
            if (this.trackedEntity instanceof EntityEgg) {
                return new S0EPacketSpawnObject(this.trackedEntity, 62);
            } else if (this.trackedEntity instanceof EntityTNTPrimed) {
                return new S0EPacketSpawnObject(this.trackedEntity, 50);
            } else if (this.trackedEntity instanceof EntityEnderCrystal) {
                return new S0EPacketSpawnObject(this.trackedEntity, 51);
            } else if (this.trackedEntity instanceof EntityFallingBlock) {
                EntityFallingBlock aZjValue2 = (EntityFallingBlock) this.trackedEntity;
                return new S0EPacketSpawnObject(
                        this.trackedEntity, 70, Block.getStateId(aZjValue2.getBlock()));
            } else if (this.trackedEntity instanceof EntityArmorStand) {
                return new S0EPacketSpawnObject(this.trackedEntity, 78);
            } else if (this.trackedEntity instanceof EntityPainting) {
                return new S10PacketSpawnPainting((EntityPainting) this.trackedEntity);
            } else if (this.trackedEntity instanceof EntityItemFrame) {
                EntityItemFrame aZjValue3 = (EntityItemFrame) this.trackedEntity;
                S0EPacketSpawnObject s0EPacketSpawnObject =
                        new S0EPacketSpawnObject(
                                this.trackedEntity,
                                71,
                                aZjValue3.facingDirection.getHorizontalIndex());
                BlockPos blockPos = aZjValue3.getHangingPosition();
                s0EPacketSpawnObject.setX(MathHelper.floor(blockPos.getX() * 32));
                s0EPacketSpawnObject.setY(MathHelper.floor(blockPos.getY() * 32));
                s0EPacketSpawnObject.setZ(MathHelper.floor(blockPos.getZ() * 32));
                return s0EPacketSpawnObject;
            } else if (this.trackedEntity instanceof EntityLeashKnot) {
                EntityLeashKnot aZjValue4 = (EntityLeashKnot) this.trackedEntity;
                S0EPacketSpawnObject s0EPacketSpawnObjectValue =
                        new S0EPacketSpawnObject(this.trackedEntity, 77);
                BlockPos blockPosValue = aZjValue4.getHangingPosition();
                s0EPacketSpawnObjectValue.setX(MathHelper.floor(blockPosValue.getX() * 32));
                s0EPacketSpawnObjectValue.setY(MathHelper.floor(blockPosValue.getY() * 32));
                s0EPacketSpawnObjectValue.setZ(MathHelper.floor(blockPosValue.getZ() * 32));
                return s0EPacketSpawnObjectValue;
            } else if (this.trackedEntity instanceof EntityXPOrb) {
                return new S11PacketSpawnExperienceOrb((EntityXPOrb) this.trackedEntity);
            } else {
                throw new IllegalArgumentException(
                        "Don't know how to add "
                                + this.trackedEntity.getClass()
                                + packedTextFragments[0]);
            }
        } else {
            EntityFireball entityFireball;
            byte b0;
            {
                entityFireball = (EntityFireball) this.trackedEntity;

                b0 = 63;
                if (this.trackedEntity instanceof EntitySmallFireball) {
                    b0 = 64;
                    if (!(astring == null)) {

                        if (this.trackedEntity instanceof EntityWitherSkull) {
                            b0 = 66;
                        }
                    }
                } else {

                    if (this.trackedEntity instanceof EntityWitherSkull) {
                        b0 = 66;
                    }
                }
            }

            S0EPacketSpawnObject s0EPacketSpawnObjectValue2;
            if (entityFireball.shootingEntity != null) {
                s0EPacketSpawnObjectValue2 =
                        new S0EPacketSpawnObject(
                                this.trackedEntity,
                                b0,
                                ((EntityFireball) this.trackedEntity).shootingEntity.getEntityId());
            } else {
                s0EPacketSpawnObjectValue2 = new S0EPacketSpawnObject(this.trackedEntity, b0, 0);
            }

            s0EPacketSpawnObjectValue2.setSpeedX((int) (entityFireball.accelerationX * 8000.0));
            s0EPacketSpawnObjectValue2.setSpeedY((int) (entityFireball.accelerationY * 8000.0));
            s0EPacketSpawnObjectValue2.setSpeedZ((int) (entityFireball.accelerationZ * 8000.0));
            return s0EPacketSpawnObjectValue2;
        }
    }

    public void updatePlayerEntity(EntityPlayerMP entityPlayerMP) {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (entityPlayerMP != this.trackedEntity) {
            if (this.func_180233_c(entityPlayerMP)) {
                if (this.trackingPlayers.contains(entityPlayerMP)
                        || !this.isPlayerWatchingThisChunk(entityPlayerMP)
                                && !this.trackedEntity.forceSpawn) {
                    return;
                }

                this.trackingPlayers.add(entityPlayerMP);
                Packet packet = this.LD();
                entityPlayerMP.playerNetServerHandler.sendPacket(packet);
                if (!this.trackedEntity.getDataWatcher().getIsBlank()) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(
                            new S1CPacketEntityMetadata(
                                    this.trackedEntity.getEntityId(),
                                    this.trackedEntity.getDataWatcher(),
                                    true));
                }

                CompoundTag compoundTag = this.trackedEntity.getNBTTagCompound();
                if (compoundTag != null) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(
                            new S49PacketUpdateEntityNBT(
                                    this.trackedEntity.getEntityId(), compoundTag));
                }

                if (this.trackedEntity instanceof LivingEntity) {
                    ServersideAttributeMap attributeMap =
                            (ServersideAttributeMap)
                                    ((LivingEntity) this.trackedEntity).getAttributeMap();
                    Collection collection = attributeMap.getWatchedAttributes();
                    if (!collection.isEmpty()) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(
                                new S20PacketEntityProperties(
                                        this.trackedEntity.getEntityId(), collection));
                    }
                }

                this.sV = this.trackedEntity.motionX;
                this.sL = this.trackedEntity.motionY;
                this.sX = this.trackedEntity.motionZ;
                if (this.sendVelocityUpdates && !(packet instanceof S0FPacketSpawnMob)) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(
                            new S12PacketEntityVelocity(
                                    this.trackedEntity.getEntityId(),
                                    this.trackedEntity.motionX,
                                    this.trackedEntity.motionY,
                                    this.trackedEntity.motionZ));
                }

                if (this.trackedEntity.ridingEntity != null) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(
                            new S1BPacketEntityAttach(
                                    0, this.trackedEntity, this.trackedEntity.ridingEntity));
                }

                if (this.trackedEntity instanceof EntityLiving
                        && ((EntityLiving) this.trackedEntity).getLeashedToEntity() != null) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(
                            new S1BPacketEntityAttach(
                                    1,
                                    this.trackedEntity,
                                    ((EntityLiving) this.trackedEntity).getLeashedToEntity()));
                }

                if (this.trackedEntity instanceof LivingEntity) {
                    int i = 0;

                    while (i < 5) {
                        ItemStack itemStack =
                                ((LivingEntity) this.trackedEntity).getEquipmentInSlot(i);
                        if (itemStack != null) {
                            entityPlayerMP.playerNetServerHandler.sendPacket(
                                    new S04PacketEntityEquipment(
                                            this.trackedEntity.getEntityId(), i, itemStack));
                        }

                        i++;
                        if (astring != null) {
                            break;
                        }
                    }
                }

                if (this.trackedEntity instanceof PlayerEntity) {
                    PlayerEntity aZjValue = (PlayerEntity) this.trackedEntity;
                    if (aZjValue.isPlayerSleeping()) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(
                                new S0APacketUseBed(aZjValue, new BlockPos(this.trackedEntity)));
                    }
                }

                if (this.trackedEntity instanceof LivingEntity) {
                    LivingEntity aZjValue2 = (LivingEntity) this.trackedEntity;

                    for (java.util.Iterator<?> __recoveryIterator171_50360 =
                                    (aZjValue2.getActivePotionEffects()).iterator();
                            (__recoveryIterator171_50360).hasNext(); ) {
                        PotionEffect potionEffect =
                                (PotionEffect) (__recoveryIterator171_50360).next();
                        entityPlayerMP.playerNetServerHandler.sendPacket(
                                new S1DPacketEntityEffect(
                                        this.trackedEntity.getEntityId(), potionEffect));
                        if (astring != null) {
                            break;
                        }
                    }
                }

                if (astring == null) {
                    return;
                }
            }

            if (this.trackingPlayers.contains(entityPlayerMP)) {
                this.trackingPlayers.remove(entityPlayerMP);
                entityPlayerMP.removeEntity(this.trackedEntity);
            }
        }
    }

    @Override
    public boolean equals(Object p171_0) {
        return ((p171_0 instanceof net.minecraft.entity.EntityTrackerEntry
                ? ((net.minecraft.entity.EntityTrackerEntry) p171_0).trackedEntity.getEntityId()
                        == this.trackedEntity.getEntityId()
                : false));
    }

    @Override
    public int hashCode() {
        return this.trackedEntity.getEntityId();
    }
}
