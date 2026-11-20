package net.minecraft.entity.monster;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;

class EntityPigZombieAIHurtByAggressor extends EntityAIHurtByTarget {
    EntityPigZombie bkr;

    public EntityPigZombieAIHurtByAggressor(EntityPigZombie bkrValue) {
        super(bkrValue, true, new Class[0]);
        this.bkr = bkrValue;
    }

    protected void setEntityAttackTarget(EntityCreature entityCreature, LivingEntity livingEntity) {
        super.setEntityAttackTarget(entityCreature, livingEntity);
        if (entityCreature instanceof EntityPigZombie) {
            EntityPigZombie.access$000((EntityPigZombie) entityCreature, livingEntity);
        }
    }
}
