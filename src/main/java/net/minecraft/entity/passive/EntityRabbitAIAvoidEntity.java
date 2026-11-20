package net.minecraft.entity.passive;

import net.minecraft.entity.ai.EntityAIAvoidEntity;

import com.google.common.base.Predicate;

class EntityRabbitAIAvoidEntity extends EntityAIAvoidEntity {
    EntityRabbit bib;
    private EntityRabbit bic;

    public void updateTask() {
        super.updateTask();
    }

    public EntityRabbitAIAvoidEntity(
            EntityRabbit bibValue, Predicate predicate, float f, double d0, double d1) {
        super(bibValue, predicate, f, d0, d1);
        this.bib = bibValue;
        this.bic = this.bib;
    }
}
