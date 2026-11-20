package net.minecraft.entity;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.network.Packet;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class EntityTracker {
    private WorldServer theWorld;
    private int maxTrackingDistanceThreshold;
    private IntHashMap trackedEntityHashTable;

    private Set trackedEntities = Sets.newHashSet();

    private static Logger logger;

    public void removePlayerFromTrackers(EntityPlayerMP entityPlayerMP) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        Iterator iterator = this.trackedEntities.iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            EntityTrackerEntry entityTrackerEntry = (EntityTrackerEntry) iterator.next();
            entityTrackerEntry.method_d47af718c77504a6451d(entityPlayerMP);
            if (astring != null) {
                break;
            }
        }
    }

    public void method_5a6071bb02cc6d17b169(EntityPlayerMP entityPlayerMP) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        Iterator iterator = this.trackedEntities.iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            {
                EntityTrackerEntry entityTrackerEntry = (EntityTrackerEntry) iterator.next();
                if (entityTrackerEntry.trackedEntity == entityPlayerMP) {
                    entityTrackerEntry.updatePlayerEntities(this.theWorld.playerEntities);
                    if (!(astring == null)) {

                        entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
                    }
                } else {

                    entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
                }
            }

            if (astring != null) {
                break;
            }
        }
    }

    public void sendToTrackingPlayersAndEntity(Entity entity, Packet packet) {
        EntityTrackerEntry entityTrackerEntry =
                (EntityTrackerEntry) this.trackedEntityHashTable.lookup(entity.getEntityId());
        if (entityTrackerEntry != null) {
            entityTrackerEntry.sendToTrackedPlayersAndSelf(packet);
        }
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public void addEntityToTracker(Entity entity, int i, int j, boolean flag) {
        if (i > this.maxTrackingDistanceThreshold) {
            i = this.maxTrackingDistanceThreshold;
        }

        try {
            if (this.trackedEntityHashTable.containsItem(entity.getEntityId())) {
                throw new IllegalStateException("Entity is already tracked!");
            }

            EntityTrackerEntry entityTrackerEntry = new EntityTrackerEntry(entity, i, j, flag);
            this.trackedEntities.add(entityTrackerEntry);
            this.trackedEntityHashTable.addKey(entity.getEntityId(), entityTrackerEntry);
            entityTrackerEntry.updatePlayerEntities(this.theWorld.playerEntities);
        } catch (Throwable throwable) {
            CrashReport crashReport =
                    CrashReport.makeCrashReport(throwable, "Adding entity to track");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Entity To Track");
            crashReportCategory.addCrashSection("Tracking range", i + " blocks");
            crashReportCategory.addCrashSectionCallable(
                    "Update interval", new EntityTrackingIntervalCrashDetail(this, j));
            entity.addEntityCrashInfo(crashReportCategory);
            CrashReportCategory crashReportCategoryValue =
                    crashReport.makeCategory("Entity That Is Already Tracked");
            ((EntityTrackerEntry) this.trackedEntityHashTable.lookup(entity.getEntityId()))
                    .trackedEntity.addEntityCrashInfo(crashReportCategoryValue);

            try {
                throw new ReportedException(crashReport);
            } catch (ReportedException reportedException) {
                net.minecraft.entity.EntityTracker.logger.error(
                        "\"Silently\" catching entity tracking error.", reportedException);
            }
        }
    }

    public void trackEntity(Entity entity) {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (entity instanceof EntityPlayerMP) {
            this.trackEntity(entity, 512, 2);
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entity;

            for (java.util.Iterator<?> __recoveryIterator171_25960 =
                            (this.trackedEntities).iterator();
                    (__recoveryIterator171_25960).hasNext(); ) {
                EntityTrackerEntry entityTrackerEntry =
                        (EntityTrackerEntry) (__recoveryIterator171_25960).next();
                if (entityTrackerEntry.trackedEntity != entityPlayerMP) {
                    entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
                }

                if (astring != null) {
                    break;
                }
            }

            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityFishHook) {
            this.addEntityToTracker(entity, 64, 5, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityArrow) {
            this.addEntityToTracker(entity, 64, 20, false);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntitySmallFireball) {
            this.addEntityToTracker(entity, 64, 10, false);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityFireball) {
            this.addEntityToTracker(entity, 64, 10, false);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntitySnowball) {
            this.addEntityToTracker(entity, 64, 10, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityEnderPearl) {
            this.addEntityToTracker(entity, 64, 10, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityEnderEye) {
            this.addEntityToTracker(entity, 64, 4, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityEgg) {
            this.addEntityToTracker(entity, 64, 10, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityPotion) {
            this.addEntityToTracker(entity, 64, 10, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityExpBottle) {
            this.addEntityToTracker(entity, 64, 10, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityFireworkRocket) {
            this.addEntityToTracker(entity, 64, 10, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityItem) {
            this.addEntityToTracker(entity, 64, 20, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityMinecart) {
            this.addEntityToTracker(entity, 80, 3, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityBoat) {
            this.addEntityToTracker(entity, 80, 3, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof SquidEntity) {
            this.addEntityToTracker(entity, 64, 3, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityWither) {
            this.addEntityToTracker(entity, 80, 3, false);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof BatEntity) {
            this.addEntityToTracker(entity, 80, 3, false);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityDragon) {
            this.addEntityToTracker(entity, 160, 3, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof IAnimals) {
            this.addEntityToTracker(entity, 80, 3, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityTNTPrimed) {
            this.addEntityToTracker(entity, 160, 10, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityFallingBlock) {
            this.addEntityToTracker(entity, 160, 20, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityHanging) {
            this.addEntityToTracker(entity, 160, Integer.MAX_VALUE, false);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityArmorStand) {
            this.addEntityToTracker(entity, 160, 3, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityXPOrb) {
            this.addEntityToTracker(entity, 160, 20, true);
            if (astring == null) {
                return;
            }
        }

        if (entity instanceof EntityEnderCrystal) {
            this.addEntityToTracker(entity, 256, Integer.MAX_VALUE, false);
        }
    }

    static {
        net.minecraft.entity.EntityTracker.logger = LogManager.getLogger();
    }

    public void trackEntity(Entity entity, int i, int j) {
        this.addEntityToTracker(entity, i, j, false);
    }

    public void untrackEntity(Entity entity) {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (entity instanceof EntityPlayerMP) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entity;

            for (java.util.Iterator<?> __recoveryIterator171_38347 =
                            (this.trackedEntities).iterator();
                    (__recoveryIterator171_38347).hasNext(); ) {
                EntityTrackerEntry entityTrackerEntry =
                        (EntityTrackerEntry) (__recoveryIterator171_38347).next();
                entityTrackerEntry.method_5a6071bb02cc6d17b169(entityPlayerMP);
                if (astring != null) {
                    break;
                }
            }
        }

        EntityTrackerEntry entityTrackerEntryValue =
                (EntityTrackerEntry) this.trackedEntityHashTable.removeObject(entity.getEntityId());
        if (entityTrackerEntryValue != null) {
            this.trackedEntities.remove(entityTrackerEntryValue);
            entityTrackerEntryValue.sendDestroyEntityPacketToTrackedPlayers();
        }
    }

    public void updateTrackedEntities() {
        ArrayList arrayList = Lists.newArrayList();
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        Iterator iterator = this.trackedEntities.iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            EntityTrackerEntry entityTrackerEntry = (EntityTrackerEntry) iterator.next();
            entityTrackerEntry.updatePlayerList(this.theWorld.playerEntities);
            if (entityTrackerEntry.nC
                    && entityTrackerEntry.trackedEntity instanceof EntityPlayerMP) {
                arrayList.add((EntityPlayerMP) entityTrackerEntry.trackedEntity);
            }

            if (astring != null) {
                break;
            }
        }

        int i = 0;

        while (i < arrayList.size()) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) arrayList.get(i);
            Iterator iteratorValue = this.trackedEntities.iterator();

            while (true) {
                if (iteratorValue.hasNext()) {
                    EntityTrackerEntry entityTrackerEntryValue =
                            (EntityTrackerEntry) iteratorValue.next();
                    if (entityTrackerEntryValue.trackedEntity != entityPlayerMP) {
                        entityTrackerEntryValue.updatePlayerEntity(entityPlayerMP);
                    }

                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public EntityTracker(WorldServer bjdValue) {
        this.trackedEntityHashTable = new IntHashMap();
        this.theWorld = bjdValue;
        this.maxTrackingDistanceThreshold =
                bjdValue.getMinecraftServer().getConfigurationManager().getEntityViewDistance();
    }

    public void method_b463234afe841e8d4890(EntityPlayerMP entityPlayerMP, Chunk chunk) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        Iterator iterator = this.trackedEntities.iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            EntityTrackerEntry entityTrackerEntry = (EntityTrackerEntry) iterator.next();
            if (entityTrackerEntry.trackedEntity != entityPlayerMP
                    && entityTrackerEntry.trackedEntity.chunkCoordX == chunk.xPosition
                    && entityTrackerEntry.trackedEntity.chunkCoordZ == chunk.zPosition) {
                entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
            }

            if (astring != null) {
                break;
            }
        }
    }

    public void sendToAllTrackingEntity(Entity entity, Packet packet) {
        EntityTrackerEntry entityTrackerEntry =
                (EntityTrackerEntry) this.trackedEntityHashTable.lookup(entity.getEntityId());
        if (entityTrackerEntry != null) {
            entityTrackerEntry.sendPacketToTrackedPlayers(packet);
        }
    }
}
