package net.minecraft.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtByTarget extends EntityAITarget {
    private int mF;
    EntityTameable theDefendingTameable;
    LivingEntity theOwnerAttacker;

    public EntityAIOwnerHurtByTarget(EntityTameable bjyValue) {
        super(bjyValue, false);
        this.theDefendingTameable = bjyValue;
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        if (!this.theDefendingTameable.isTamed()) {
            return false;
        }

        LivingEntity livingEntity = this.theDefendingTameable.func_180492_cm();
        if (livingEntity == null) {
            return false;
        }

        this.theOwnerAttacker = livingEntity.getAITarget();
        int revengeTimer = livingEntity.getRevengeTimer();
        return revengeTimer != this.mF
                && this.isSuitableTarget(this.theOwnerAttacker, false)
                && this.theDefendingTameable.shouldAttackEntity(this.theOwnerAttacker, livingEntity);
    }

    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theOwnerAttacker);
        LivingEntity livingEntity = this.theDefendingTameable.func_180492_cm();
        if (livingEntity != null) {
            this.mF = livingEntity.getRevengeTimer();
        }

        super.startExecuting();
    }
}
