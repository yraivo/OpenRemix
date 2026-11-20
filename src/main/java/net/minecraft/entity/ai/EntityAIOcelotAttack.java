package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class EntityAIOcelotAttack extends EntityAIBase {
    World theWorld;
    EntityLiving theEntity;
    int attackCountdown;
    LivingEntity theVictim;

    public EntityAIOcelotAttack(EntityLiving bheValue) {
        this.theEntity = bheValue;
        this.theWorld = bheValue.world;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
        LivingEntity bhQValue = this.theEntity.getAttackTarget();
        if (bhQValue == null) {
            return false;
        }

        this.theVictim = bhQValue;
        return true;
    }

    public boolean continueExecuting() {
        return !this.theVictim.isEntityAlive()
                ? false
                : (this.theEntity.getDistanceSqToEntity(this.theVictim) > 225.0
                        ? false
                        : !this.theEntity.getNavigator().noPath() || this.shouldExecute());
    }

    public void updateTask() {
        double d0;
        double d1;
        double d2;
        {
            String text = EntityAIBase.cJ();
            this.theEntity.getLookHelper().setLookPositionWithEntity(this.theVictim, 30.0F, 30.0F);
            String textValue = text;
            d0 = this.theEntity.width * 2.0F * this.theEntity.width * 2.0F;
            d1 =
                    this.theEntity.getDistanceSq(
                            this.theVictim.posX, this.theVictim.getEntityBoundingBox().minY, this.theVictim.posZ);
            d2 = 0.8;
            if (d1 > d0 && d1 < 16.0) {
                d2 = 1.33;
                if (!(textValue != null)) {

                    if (d1 < 225.0) {
                        d2 = 0.6;
                    }
                }
            } else {

                if (d1 < 225.0) {
                    d2 = 0.6;
                }
            }
        }

        this.theEntity.getNavigator().tryMoveToEntityLiving(this.theVictim, d2);
        this.attackCountdown = Math.max(this.attackCountdown - 1, 0);
        if (d1 <= d0 && this.attackCountdown <= 0) {
            this.attackCountdown = 20;
            this.theEntity.attackEntityAsMob(this.theVictim);
        }
    }

    public void resetTask() {
        this.theVictim = null;
        this.theEntity.getNavigator().clearPathEntity();
    }
}
