package net.minecraft.entity.monster;

import net.minecraft.entity.ai.EntityAIBase;

class EntitySlimeAISlimeHop extends EntityAIBase {
    private SlimeEntity slime;
    SlimeEntity bjZ;

    public boolean shouldExecute() {
        return true;
    }

    public void updateTask() {
        ((EntitySlimeSlimeMoveHelper) this.slime.getMoveHelper()).setSpeed(1.0);
    }

    public EntitySlimeAISlimeHop(SlimeEntity bjZValue) {
        this.bjZ = bjZValue;
        this.slime = this.bjZ;
        this.setMutexBits(5);
    }
}
