package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAISwimming extends EntityAIBase {
    private EntityLiving theEntity;

    public void updateTask() {
        if (this.theEntity.getRNG().nextFloat() < 0.8F) {
            this.theEntity.getJumpHelper().setJumping();
        }
    }

    public boolean shouldExecute() {
        return this.theEntity.isInWater() || this.theEntity.isInLava();
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public EntityAISwimming(EntityLiving bhtValue) {
        super();
        this.theEntity = bhtValue;
        this.setMutexBits(4);
        ((PathNavigateGround) bhtValue.getNavigator()).setCanSwim(true);
    }
}
