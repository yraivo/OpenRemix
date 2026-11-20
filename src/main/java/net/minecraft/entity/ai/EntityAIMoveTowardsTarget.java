package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Vec3;

public class EntityAIMoveTowardsTarget extends EntityAIBase {
    private double movePosZ;
    private double movePosX;
    private double movePosY;
    private float maxTargetDistance;
    private LivingEntity targetEntity;
    private EntityCreature theEntity;
    private double speed;

    public void resetTask() {
        this.targetEntity = null;
    }

    public boolean continueExecuting() {
        return !this.theEntity.getNavigator().noPath()
                && this.targetEntity.isEntityAlive()
                && this.targetEntity.getDistanceSqToEntity(this.theEntity)
                        < this.maxTargetDistance * this.maxTargetDistance;
    }

    public EntityAIMoveTowardsTarget(
            EntityCreature bhjValue, double speedValue, float maxTargetDistanceValue) {
        this.theEntity = bhjValue;
        this.speed = speedValue;
        this.maxTargetDistance = maxTargetDistanceValue;
        this.setMutexBits(1);
    }

    public void startExecuting() {
        this.theEntity
                .getNavigator()
                .tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.speed);
    }

    public boolean shouldExecute() {
        this.targetEntity = this.theEntity.getAttackTarget();
        if (this.targetEntity == null) {
            return false;
        }

        if (this.targetEntity.getDistanceSqToEntity(this.theEntity)
                > this.maxTargetDistance * this.maxTargetDistance) {
            return false;
        }

        Vec3 vec3 =
                RandomPositionGenerator.findRandomTargetBlockTowards(
                        this.theEntity,
                        16,
                        7,
                        new Vec3(
                                this.targetEntity.posX,
                                this.targetEntity.posY,
                                this.targetEntity.posZ));
        if (vec3 == null) {
            return false;
        }

        this.movePosX = vec3.x;
        this.movePosY = vec3.y;
        this.movePosZ = vec3.z;
        return true;
    }
}
