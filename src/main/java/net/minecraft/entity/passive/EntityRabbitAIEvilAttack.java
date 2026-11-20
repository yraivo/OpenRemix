package net.minecraft.entity.passive;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;

class EntityRabbitAIEvilAttack extends EntityAIAttackOnCollide {
    EntityRabbit bhX;

    public EntityRabbitAIEvilAttack(EntityRabbit bhXValue) {
        super(bhXValue, LivingEntity.class, 1.4, true);
        this.bhX = bhXValue;
    }

    protected double method_d2bb536412a0d5078fca(LivingEntity livingEntity) {
        return 4.0F + livingEntity.width;
    }
}
