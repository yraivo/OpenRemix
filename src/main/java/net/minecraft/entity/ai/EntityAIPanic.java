package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;

public class EntityAIPanic extends EntityAIBase {
    private double randPosY;
    private double randPosX;
    protected double speed;
    private EntityCreature theEntityCreature;
    private double randPosZ;

    public EntityAIPanic(EntityCreature bhjValue, double speedValue) {
        this.theEntityCreature = bhjValue;
        this.speed = speedValue;
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        if (this.theEntityCreature.getAITarget() == null && !this.theEntityCreature.isBurning()) {
            return false;
        }

        net.minecraft.util.Vec3 vec3 =
                net.minecraft.entity.ai.RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);
        if (vec3 == null) {
            return false;
        }

        this.randPosX = vec3.x;
        this.randPosY = vec3.y;
        this.randPosZ = vec3.z;
        return true;
    }

    public void startExecuting() {
        this.theEntityCreature
                .getNavigator()
                .tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }

    public boolean continueExecuting() {
        return !this.theEntityCreature.getNavigator().noPath();
    }
}
