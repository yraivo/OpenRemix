package net.minecraft.entity.passive;

import net.minecraft.entity.ai.EntityAIPanic;

class EntityRabbitAIPanic extends EntityAIPanic {
    EntityRabbit bkm;
    private EntityRabbit theEntity;

    public void updateTask() {
        super.updateTask();
        this.theEntity.setMovementSpeed(this.speed);
    }

    public EntityRabbitAIPanic(EntityRabbit bkmValue, double d0) {
        super(bkmValue, d0);
        this.bkm = bkmValue;
        this.theEntity = this.bkm;
    }
}
