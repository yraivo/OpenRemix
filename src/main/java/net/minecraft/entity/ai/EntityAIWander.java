package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;

public class EntityAIWander extends EntityAIBase {
    private int executionChance;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private EntityCreature entity;
    private boolean mustUpdate;
    private double speed;

    public boolean continueExecuting() {
        return !this.entity.getNavigator().noPath();
    }

    public void startExecuting() {
        this.entity
                .getNavigator()
                .tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }

    public void setExecutionChance(int executionChanceValue) {
        this.executionChance = executionChanceValue;
    }

    public EntityAIWander(EntityCreature bhjValue, double speedValue, int executionChanceValue) {
        this.entity = bhjValue;
        this.speed = speedValue;
        this.executionChance = executionChanceValue;
        this.setMutexBits(1);
    }

    public void makeUpdate() {
        this.mustUpdate = true;
    }

    public EntityAIWander(EntityCreature entityCreature, double p171_1) {
        this(entityCreature, p171_1, 120);
    }

    public boolean shouldExecute() {
        if (!this.mustUpdate) {
            if (this.entity.IJ() >= 100) {
                return false;
            }

            if (this.entity.getRNG().nextInt(this.executionChance) != 0) {
                return false;
            }
        }

        net.minecraft.util.Vec3 vec3 = net.minecraft.entity.ai.RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
        if (vec3 == null) {
            return false;
        }

        this.xPosition = vec3.x;
        this.yPosition = vec3.y;
        this.zPosition = vec3.z;
        this.mustUpdate = false;
        return true;
    }
}
