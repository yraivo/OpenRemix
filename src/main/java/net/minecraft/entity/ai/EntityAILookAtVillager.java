package net.minecraft.entity.ai;

import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;

public class EntityAILookAtVillager extends EntityAIBase {
    private EntityIronGolem theGolem;
    private int lookTime;
    private EntityVillager theVillager;

    public boolean shouldExecute() {
        if (!this.theGolem.world.isDaytime()) {
            return false;
        }

        if (this.theGolem.getRNG().nextInt(8000) != 0) {
            return false;
        }

        this.theVillager =
                (EntityVillager)
                        this.theGolem.world.findNearestEntityWithinAABB(
                                EntityVillager.class,
                                this.theGolem.getEntityBoundingBox().expand(6.0, 2.0, 6.0),
                                this.theGolem);
        return this.theVillager != null;
    }

    public void resetTask() {
        this.theGolem.setHoldingRose(false);
        this.theVillager = null;
    }

    public boolean continueExecuting() {
        return this.lookTime > 0;
    }

    public EntityAILookAtVillager(EntityIronGolem bhuValue) {
        this.theGolem = bhuValue;
        this.setMutexBits(3);
    }

    public void startExecuting() {
        this.lookTime = 400;
        this.theGolem.setHoldingRose(true);
    }

    public void updateTask() {
        this.theGolem.getLookHelper().setLookPositionWithEntity(this.theVillager, 30.0F, 30.0F);
        this.lookTime--;
    }
}
