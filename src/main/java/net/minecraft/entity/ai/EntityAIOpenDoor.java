package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIOpenDoor extends EntityAIDoorInteract {
    boolean closeDoor;
    int closeDoorTemporisation;

    public void startExecuting() {
        this.closeDoorTemporisation = 20;
        this.doorBlock.toggleDoor(this.theEntity.world, this.doorPosition, true);
    }

    public void updateTask() {
        this.closeDoorTemporisation--;
        super.updateTask();
    }

    public EntityAIOpenDoor(EntityLiving bhtValue, boolean flag) {
        super(bhtValue);
        this.theEntity = bhtValue;
        this.closeDoor = flag;
    }

    public void resetTask() {
        if (this.closeDoor) {
            this.doorBlock.toggleDoor(
                    this.theEntity.world, this.doorPosition, false);
        }
    }

    public boolean continueExecuting() {
        return this.closeDoor && this.closeDoorTemporisation > 0 && super.continueExecuting();
    }
}
