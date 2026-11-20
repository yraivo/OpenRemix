package net.minecraft.entity.monster;

import net.minecraft.entity.ai.EntityAIBase;

class EntitySlimeAISlimeFaceRandom extends EntityAIBase {
    SlimeEntity bjY;
    private int field_179460_c;
    private float field_179459_b;
    private SlimeEntity slime;

    public void updateTask() {
        if (--this.field_179460_c <= 0) {
            this.field_179460_c = 40 + this.slime.getRNG().nextInt(60);
            this.field_179459_b = this.slime.getRNG().nextInt(360);
        }

        ((EntitySlimeSlimeMoveHelper) this.slime.getMoveHelper())
                .func_179920_a(this.field_179459_b, false);
    }

    public boolean shouldExecute() {
        return this.slime.getAttackTarget() == null
                && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava());
    }

    public EntitySlimeAISlimeFaceRandom(SlimeEntity bjYValue) {
        this.bjY = bjYValue;
        this.slime = this.bjY;
        this.setMutexBits(2);
    }
}
