package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.PlayerEntity;

public class EntityAIWatchClosest extends EntityAIBase {
    private float chance;
    protected Class watchedClass;
    protected EntityLiving theWatcher;
    private int lookTime;
    protected Entity closestEntity;
    protected float maxDistanceForPlayer;

    public EntityAIWatchClosest(
            EntityLiving bhtValue, Class oclass, float maxDistanceForPlayerValue) {
        this.theWatcher = bhtValue;
        this.watchedClass = oclass;
        this.maxDistanceForPlayer = maxDistanceForPlayerValue;
        this.chance = 0.02F;
        this.setMutexBits(2);
    }

    public void resetTask() {
        this.closestEntity = null;
    }

    public boolean shouldExecute() {
        String text = EntityAIBase.cJ();
        if (this.theWatcher.getRNG().nextFloat() >= this.chance) {
            return false;
        }

        if (this.theWatcher.getAttackTarget() != null) {
            this.closestEntity = this.theWatcher.getAttackTarget();
        }

        if (this.watchedClass == PlayerEntity.class) {
            this.closestEntity =
                    this.theWatcher.world.getClosestPlayerToEntity(
                            this.theWatcher, this.maxDistanceForPlayer);
            if (text != null) {
                return this.closestEntity != null;
            }
        }

        this.closestEntity =
                this.theWatcher.world.findNearestEntityWithinAABB(
                        this.watchedClass,
                        this.theWatcher
                                .getEntityBoundingBox()
                                .expand(this.maxDistanceForPlayer, 3.0, this.maxDistanceForPlayer),
                        this.theWatcher);
        return this.closestEntity != null;
    }

    public EntityAIWatchClosest(
            EntityLiving bhtValue,
            Class oclass,
            float maxDistanceForPlayerValue,
            float chanceValue) {
        this.theWatcher = bhtValue;
        this.watchedClass = oclass;
        this.maxDistanceForPlayer = maxDistanceForPlayerValue;
        this.chance = chanceValue;
        this.setMutexBits(2);
    }

    public void updateTask() {
        this.theWatcher
                .getLookHelper()
                .setLookPosition(
                        this.closestEntity.posX,
                        this.closestEntity.posY + this.closestEntity.getEyeHeight(),
                        this.closestEntity.posZ,
                        10.0F,
                        this.theWatcher.getVerticalFaceSpeed());
        this.lookTime--;
    }

    public void startExecuting() {
        this.lookTime = 40 + this.theWatcher.getRNG().nextInt(40);
    }

    public boolean continueExecuting() {
        return !this.closestEntity.isEntityAlive()
                ? false
                : (this.theWatcher.getDistanceSqToEntity(this.closestEntity)
                                > this.maxDistanceForPlayer * this.maxDistanceForPlayer
                        ? false
                        : this.lookTime > 0);
    }
}
