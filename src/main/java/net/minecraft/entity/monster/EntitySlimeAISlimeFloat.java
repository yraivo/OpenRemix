package net.minecraft.entity.monster;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigateGround;

class EntitySlimeAISlimeFloat extends EntityAIBase {
    private SlimeEntity slime;
    SlimeEntity bjZ;

    public boolean shouldExecute() {
        return this.slime.isInWater() || this.slime.isInLava();
    }

    public EntitySlimeAISlimeFloat(SlimeEntity bjZValue) {
        this.bjZ = bjZValue;
        this.slime = this.bjZ;
        this.setMutexBits(5);
        ((PathNavigateGround) bjZValue.getNavigator()).setCanSwim(true);
    }

    public void updateTask() {
        if (this.slime.getRNG().nextFloat() < 0.8F) {
            this.slime.getJumpHelper().setJumping();
        }

        ((EntitySlimeSlimeMoveHelper) this.slime.getMoveHelper()).setSpeed(1.2);
    }
}
