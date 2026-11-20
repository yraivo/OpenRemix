package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import org.apache.commons.lang3.StringUtils;

public abstract class EntityAITarget extends EntityAIBase {
    protected EntityCreature taskOwner;
    private boolean nearbyOnly;
    private int targetSearchStatus;
    private int targetUnseenTicks;
    protected boolean shouldCheckSight;
    private int targetSearchDelay;

    protected boolean isSuitableTarget(LivingEntity livingEntity, boolean flag) {
        if (!isSuitableTarget(this.taskOwner, livingEntity, flag, this.shouldCheckSight)) {
            return false;
        }

        if (!this.taskOwner.isWithinHomeDistanceFromPosition(new BlockPos(livingEntity))) {
            return false;
        }

        if (this.nearbyOnly) {
            if (--this.targetSearchDelay <= 0) {
                this.targetSearchStatus = 0;
            }

            if (this.targetSearchStatus == 0) {
                this.targetSearchStatus = this.canEasilyReach(livingEntity) ? 1 : 2;
            }

            if (this.targetSearchStatus == 2) {
                return false;
            }
        }

        return true;
    }

    public EntityAITarget(
            EntityCreature bhjValue, boolean shouldCheckSightValue, boolean nearbyOnlyValue) {
        this.taskOwner = bhjValue;
        this.shouldCheckSight = shouldCheckSightValue;
        this.nearbyOnly = nearbyOnlyValue;
    }

    protected double getTargetDistance() {
        IAttributeInstance entityAttribute =
                this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
        return entityAttribute == null ? 16.0 : entityAttribute.getAttributeValue();
    }

    public static boolean isSuitableTarget(
            EntityLiving entityLiving, LivingEntity livingEntity, boolean flag, boolean flag1) {
        if (livingEntity == null) {
            return false;
        }

        if (livingEntity == entityLiving) {
            return false;
        }

        if (!livingEntity.isEntityAlive()) {
            return false;
        }

        if (!entityLiving.canAttackClass(livingEntity.getClass())) {
            return false;
        }

        Team team = entityLiving.getTeam();
        Team teamValue = livingEntity.getTeam();
        if (team != null && teamValue == team) {
            return false;
        }

        if (entityLiving instanceof IEntityOwnable
                && StringUtils.isNotEmpty(((IEntityOwnable) entityLiving).getOwnerId())) {
            if (livingEntity instanceof IEntityOwnable
                    && ((IEntityOwnable) entityLiving)
                            .getOwnerId()
                            .equals(((IEntityOwnable) livingEntity).getOwnerId())) {
                return false;
            }

            if (livingEntity == ((IEntityOwnable) entityLiving).getOwner()) {
                return false;
            }
        } else if (livingEntity instanceof PlayerEntity
                && !flag
                && ((PlayerEntity) livingEntity).capabilities.disableDamage) {
            return false;
        }

        return !flag1 || entityLiving.getEntitySenses().canSee(livingEntity);
    }

    public void startExecuting() {
        this.targetSearchStatus = 0;
        this.targetSearchDelay = 0;
        this.targetUnseenTicks = 0;
    }

    public boolean continueExecuting() {
        String text = EntityAIBase.cJ();
        LivingEntity livingEntity = this.taskOwner.getAttackTarget();
        String textValue = text;
        if (livingEntity == null) {
            return false;
        }

        if (!livingEntity.isEntityAlive()) {
            return false;
        }

        Team team = this.taskOwner.getTeam();
        Team teamValue = livingEntity.getTeam();
        if (team != null && teamValue == team) {
            return false;
        }

        double targetDistance = this.getTargetDistance();
        if (this.taskOwner.getDistanceSqToEntity(livingEntity) > targetDistance * targetDistance) {
            return false;
        }

        if (this.shouldCheckSight) {
            if (this.taskOwner.getEntitySenses().canSee(livingEntity)) {
                this.targetUnseenTicks = 0;
                if (textValue != null) {
                    return !(livingEntity instanceof PlayerEntity)
                            || !((PlayerEntity) livingEntity).capabilities.disableDamage;
                }
            }

            if (++this.targetUnseenTicks > 60) {
                return false;
            }
        }

        return !(livingEntity instanceof PlayerEntity)
                || !((PlayerEntity) livingEntity).capabilities.disableDamage;
    }

    public EntityAITarget(EntityCreature entityCreature, boolean flag) {
        this(entityCreature, flag, false);
    }

    public void resetTask() {
        this.taskOwner.setAttackTarget((LivingEntity) null);
    }

    private boolean canEasilyReach(LivingEntity livingEntity) {
        this.targetSearchDelay = 10 + this.taskOwner.getRNG().nextInt(5);
        PathEntity pathToEntityLiving =
                this.taskOwner.getNavigator().getPathToEntityLiving(livingEntity);
        if (pathToEntityLiving == null) {
            return false;
        }

        PathPoint finalPathPoint = pathToEntityLiving.getFinalPathPoint();
        if (finalPathPoint == null) {
            return false;
        }

        int i = finalPathPoint.xCoord - MathHelper.floor(livingEntity.posX);
        int j = finalPathPoint.zCoord - MathHelper.floor(livingEntity.posZ);
        return i * i + j * j <= 2.25;
    }
}
