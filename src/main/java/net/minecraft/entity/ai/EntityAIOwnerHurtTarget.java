package net.minecraft.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtTarget extends EntityAITarget {
    LivingEntity theTarget;
    private int mF;
    EntityTameable theEntityTameable;

    public boolean shouldExecute() {
        if (!this.theEntityTameable.isTamed()) {
            return false;
        }

        LivingEntity livingEntity = this.theEntityTameable.func_180492_cm();
        if (livingEntity == null) {
            return false;
        }

        this.theTarget = livingEntity.getLastAttacker();
        int i = livingEntity.getLastAttackerTime();
        return i != this.mF
                && this.isSuitableTarget(this.theTarget, false)
                && this.theEntityTameable.shouldAttackEntity(this.theTarget, livingEntity);
    }

    public EntityAIOwnerHurtTarget(EntityTameable bjyValue) {
        super(bjyValue, false);
        this.theEntityTameable = bjyValue;
        this.setMutexBits(1);
    }

    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theTarget);
        LivingEntity livingEntity = this.theEntityTameable.func_180492_cm();
        if (livingEntity != null) {
            this.mF = livingEntity.getLastAttackerTime();
        }

        super.startExecuting();
    }
}
