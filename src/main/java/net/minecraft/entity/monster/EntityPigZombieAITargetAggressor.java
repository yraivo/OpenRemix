package net.minecraft.entity.monster;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.PlayerEntity;

class EntityPigZombieAITargetAggressor extends EntityAINearestAttackableTarget {
    EntityPigZombie bkA;

    public boolean shouldExecute() {
        return ((EntityPigZombie) this.taskOwner).isAngry() && super.shouldExecute();
    }

    public EntityPigZombieAITargetAggressor(EntityPigZombie bkAValue) {
        super(bkAValue, PlayerEntity.class, true);
        this.bkA = bkAValue;
    }
}
