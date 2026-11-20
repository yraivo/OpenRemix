package net.minecraft.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityCreeper;

public class EntityAICreeperSwell extends EntityAIBase {
    EntityCreeper swellingCreeper;
    LivingEntity creeperAttackTarget;

    public EntityAICreeperSwell(EntityCreeper bifValue) {
        this.swellingCreeper = bifValue;
        this.setMutexBits(1);
    }

    public void startExecuting() {
        this.swellingCreeper.getNavigator().clearPathEntity();
        this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
    }

    public void updateTask() {
        String text = EntityAIBase.cJ();
        if (this.creeperAttackTarget == null) {
            this.swellingCreeper.setCreeperState(-1);
            if (text != null) {
                return;
            }
        }

        if (this.swellingCreeper.getDistanceSqToEntity(this.creeperAttackTarget) > 49.0) {
            this.swellingCreeper.setCreeperState(-1);
            if (text != null) {
                return;
            }
        }

        if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget)) {
            this.swellingCreeper.setCreeperState(-1);
            if (text != null) {
                return;
            }
        }

        this.swellingCreeper.setCreeperState(1);
    }

    public void resetTask() {
        this.creeperAttackTarget = null;
    }

    public boolean shouldExecute() {
        LivingEntity livingEntity = this.swellingCreeper.getAttackTarget();
        return this.swellingCreeper.getCreeperState() > 0
                || livingEntity != null
                        && this.swellingCreeper.getDistanceSqToEntity(livingEntity) < 9.0;
    }
}
