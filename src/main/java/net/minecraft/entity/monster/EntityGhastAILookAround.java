package net.minecraft.entity.monster;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAIBase;

class EntityGhastAILookAround extends EntityAIBase {
    private EntityGhast parentEntity;
    EntityGhast bjQ;

    public EntityGhastAILookAround(EntityGhast bjQValue) {
        this.bjQ = bjQValue;
        this.parentEntity = this.bjQ;
        this.setMutexBits(2);
    }

    public void updateTask() {
        int i = EntitySpiderGroupData.method_7eec03da329a017ec143();
        if (this.parentEntity.getAttackTarget() == null) {
            this.parentEntity.renderYawOffset =
                    this.parentEntity.rotationYaw =
                            -((float) Math.atan2(this.parentEntity.motionX, this.parentEntity.motionZ))
                                    * 180.0F
                                    / (float) Math.PI;
            if (i != 0) {
                return;
            }
        }

        LivingEntity livingEntity = this.parentEntity.getAttackTarget();
        double d0 = 64.0;
        if (livingEntity.getDistanceSqToEntity(this.parentEntity) < d0 * d0) {
            double deltaPosX = livingEntity.posX - this.parentEntity.posX;
            double deltaPosZ = livingEntity.posZ - this.parentEntity.posZ;
            this.parentEntity.renderYawOffset =
                    this.parentEntity.rotationYaw =
                            -((float) Math.atan2(deltaPosX, deltaPosZ)) * 180.0F / (float) Math.PI;
        }
    }

    public boolean shouldExecute() {
        return true;
    }
}
