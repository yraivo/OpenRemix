package net.minecraft.entity.ai;

import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;

import java.util.List;

public class EntityAIFollowGolem extends EntityAIBase {
    private EntityVillager theVillager;
    private boolean tookGolemRose;
    private int takeGolemRoseTick;
    private EntityIronGolem theGolem;

    public boolean shouldExecute() {
        String text = EntityAIBase.cJ();
        if (this.theVillager.getGrowingAge() >= 0) {
            return false;
        }

        if (!this.theVillager.world.isDaytime()) {
            return false;
        }

        List entitiesWithinAABB =
                this.theVillager.world.getEntitiesWithinAABB(
                        EntityIronGolem.class,
                        this.theVillager.getEntityBoundingBox().expand(6.0, 2.0, 6.0));
        if (entitiesWithinAABB.isEmpty()) {
            return false;
        }

        for (java.util.Iterator<?> __recoveryIterator171_850 = (entitiesWithinAABB).iterator();
                (__recoveryIterator171_850).hasNext(); ) {
            EntityIronGolem bhlValue = (EntityIronGolem) (__recoveryIterator171_850).next();
            if (bhlValue.getHoldRoseTick() > 0) {
                this.theGolem = bhlValue;
                if (text != null) {
                    break;
                }
            }

            if (text == null) {
                break;
            }
        }

        return this.theGolem != null;
    }

    public void startExecuting() {
        this.takeGolemRoseTick = this.theVillager.getRNG().nextInt(320);
        this.tookGolemRose = false;
        this.theGolem.getNavigator().clearPathEntity();
    }

    public void updateTask() {
        this.theVillager.getLookHelper().setLookPositionWithEntity(this.theGolem, 30.0F, 30.0F);
        if (this.theGolem.getHoldRoseTick() == this.takeGolemRoseTick) {
            this.theVillager.getNavigator().tryMoveToEntityLiving(this.theGolem, 0.5);
            this.tookGolemRose = true;
        }

        if (this.tookGolemRose && this.theVillager.getDistanceSqToEntity(this.theGolem) < 4.0) {
            this.theGolem.setHoldingRose(false);
            this.theVillager.getNavigator().clearPathEntity();
        }
    }

    public EntityAIFollowGolem(EntityVillager bhkValue) {
        this.theVillager = bhkValue;
        this.setMutexBits(3);
    }

    public void resetTask() {
        this.theGolem = null;
        this.theVillager.getNavigator().clearPathEntity();
    }

    public boolean continueExecuting() {
        return this.theGolem.getHoldRoseTick() > 0;
    }
}
