package net.minecraft.entity.passive;

import net.minecraft.entity.ai.EntityMoveHelper;

class EntityRabbitRabbitMoveHelper extends EntityMoveHelper {
    private EntityRabbit theEntity;
    EntityRabbit bkg;

    public void onUpdateMoveHelper() {
        if (this.theEntity.onGround && !this.theEntity.func_175523_cj()) {
            this.theEntity.setMovementSpeed(0.0);
        }

        super.onUpdateMoveHelper();
    }

    public EntityRabbitRabbitMoveHelper(EntityRabbit bkgValue) {
        super(bkgValue);
        this.bkg = bkgValue;
        this.theEntity = this.bkg;
    }
}
