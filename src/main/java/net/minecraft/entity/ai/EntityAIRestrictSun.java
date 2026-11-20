package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAIRestrictSun extends EntityAIBase {
    private EntityCreature theEntity;

    public void resetTask() {
        ((PathNavigateGround) this.theEntity.getNavigator()).setAvoidSun(false);
    }

    public EntityAIRestrictSun(EntityCreature bhjValue) {
        this.theEntity = bhjValue;
    }

    public void startExecuting() {
        ((PathNavigateGround) this.theEntity.getNavigator()).setAvoidSun(true);
    }

    public boolean shouldExecute() {
        return this.theEntity.world.isDaytime();
    }
}
