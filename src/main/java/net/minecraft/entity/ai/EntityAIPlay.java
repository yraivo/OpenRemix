package net.minecraft.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.Vec3;

import java.util.List;

public class EntityAIPlay extends EntityAIBase {
    private LivingEntity targetVillager;
    private int playTime;
    private EntityVillager villagerObj;
    private double speed;

    public void startExecuting() {
        if (this.targetVillager != null) {
            this.villagerObj.setPlaying(true);
        }

        this.playTime = 1000;
    }

    public boolean shouldExecute() {
        String text = EntityAIBase.cJ();
        if (this.villagerObj.getGrowingAge() >= 0) {
            return false;
        }

        if (this.villagerObj.getRNG().nextInt(400) != 0) {
            return false;
        }

        List entitiesWithinAABB =
                this.villagerObj.world.getEntitiesWithinAABB(
                        EntityVillager.class,
                        this.villagerObj.getEntityBoundingBox().expand(6.0, 3.0, 6.0));
        double MAX_VALUE = Double.MAX_VALUE;

        for (java.util.Iterator<?> __recoveryIterator171_1008 = (entitiesWithinAABB).iterator();
                (__recoveryIterator171_1008).hasNext(); ) {
            EntityVillager bhmValue = (EntityVillager) (__recoveryIterator171_1008).next();
            if (bhmValue != this.villagerObj && !bhmValue.isPlaying() && bhmValue.getGrowingAge() < 0) {
                double distanceSqToEntity = bhmValue.getDistanceSqToEntity(this.villagerObj);
                if (distanceSqToEntity <= MAX_VALUE) {
                    MAX_VALUE = distanceSqToEntity;
                    this.targetVillager = bhmValue;
                }
            }

            if (text == null) {
                break;
            }
        }

        if (this.targetVillager == null) {
            Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3);
            if (vec3 == null) {
                return false;
            }
        }

        return true;
    }

    public boolean continueExecuting() {
        return this.playTime > 0;
    }

    public EntityAIPlay(EntityVillager bhkValue, double d0) {
        this.villagerObj = bhkValue;
        this.speed = d0;
        this.setMutexBits(1);
    }

    public void updateTask() {
        String text = EntityAIBase.cJ();
        this.playTime--;
        String textValue = text;
        if (this.targetVillager != null) {
            if (!(this.villagerObj.getDistanceSqToEntity(this.targetVillager) > 4.0)) {
                return;
            }

            this.villagerObj.getNavigator().tryMoveToEntityLiving(this.targetVillager, this.speed);
            if (textValue != null) {
                return;
            }
        }

        if (this.villagerObj.getNavigator().noPath()) {
            Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3);
            if (vec3 == null) {
                return;
            }

            this.villagerObj.getNavigator().tryMoveToXYZ(vec3.x, vec3.y, vec3.z, this.speed);
        }
    }

    public void resetTask() {
        this.villagerObj.setPlaying(false);
        this.targetVillager = null;
    }
}
