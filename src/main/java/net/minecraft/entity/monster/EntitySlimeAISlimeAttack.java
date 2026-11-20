package net.minecraft.entity.monster;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAIBase;

class EntitySlimeAISlimeAttack extends EntityAIBase {
    SlimeEntity bjW;
    private SlimeEntity slime;
    private int field_179465_b;

    public EntitySlimeAISlimeAttack(SlimeEntity bjWValue) {
        this.bjW = bjWValue;
        this.slime = this.bjW;
        this.setMutexBits(2);
    }

    public void startExecuting() {
        this.field_179465_b = 300;
        super.startExecuting();
    }

    public boolean continueExecuting() {
        LivingEntity livingEntity = this.slime.getAttackTarget();
        return livingEntity == null
                ? false
                : (!livingEntity.isEntityAlive() ? false : --this.field_179465_b > 0);
    }

    public void updateTask() {
        this.slime.faceEntity(this.slime.getAttackTarget(), 10.0F, 10.0F);
        ((EntitySlimeSlimeMoveHelper) this.slime.getMoveHelper())
                .func_179920_a(this.slime.rotationYaw, this.slime.canDamagePlayer());
    }

    public boolean shouldExecute() {
        LivingEntity livingEntity = this.slime.getAttackTarget();
        return livingEntity == null ? false : livingEntity.isEntityAlive();
    }
}
