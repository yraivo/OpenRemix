package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityTameable;

public class EntityAISit extends EntityAIBase {
    private EntityTameable theEntity;
    private boolean isSitting;

    public EntityAISit(EntityTameable bhoValue) {
        this.theEntity = bhoValue;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isTamed()) {
            return false;
        }

        if (this.theEntity.isInWater()) {
            return false;
        }

        if (!this.theEntity.onGround) {
            return false;
        }

        net.minecraft.entity.LivingEntity livingEntity = this.theEntity.func_180492_cm();
        return livingEntity == null
                ? true
                : (this.theEntity.getDistanceSqToEntity(livingEntity) < 144.0
                                && livingEntity.getAITarget() != null
                        ? false
                        : this.isSitting);
    }

    @Override
    public void resetTask() {
        this.theEntity.setSitting(false);
    }

    public void setSitting(boolean isSittingValue) {
        this.isSitting = isSittingValue;
    }

    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().clearPathEntity();
        this.theEntity.setSitting(true);
    }
}
