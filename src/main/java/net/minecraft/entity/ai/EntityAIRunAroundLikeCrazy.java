package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityHorse;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase {
    private EntityHorse horseHost;
    private double targetX;
    private double speed;
    private double targetZ;
    private double targetY;

    public void updateTask() {
        if (this.horseHost.getRNG().nextInt(50) == 0) {
            if (this.horseHost.riddenByEntity instanceof net.minecraft.entity.player.PlayerEntity) {
                int temper = this.horseHost.getTemper();
                int j = this.horseHost.getMaxTemper();
                if (j > 0 && this.horseHost.getRNG().nextInt(j) < temper) {
                    this.horseHost.setTamedBy((net.minecraft.entity.player.PlayerEntity) this.horseHost.riddenByEntity);
                    this.horseHost.world.setEntityState(this.horseHost, (byte) 7);
                    return;
                }

                this.horseHost.increaseTemper(5);
            }

            this.horseHost.riddenByEntity.mountEntity((net.minecraft.entity.Entity) null);
            this.horseHost.riddenByEntity = null;
            this.horseHost.makeHorseRearWithSound();
            this.horseHost.world.setEntityState(this.horseHost, (byte) 6);
        }
    }

    public void startExecuting() {
        this.horseHost
                .getNavigator()
                .tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
    }

    public EntityAIRunAroundLikeCrazy(EntityHorse bkoValue, double speedValue) {
        this.horseHost = bkoValue;
        this.speed = speedValue;
        this.setMutexBits(1);
    }

    public boolean continueExecuting() {
        return !this.horseHost.getNavigator().noPath() && this.horseHost.riddenByEntity != null;
    }

    public boolean shouldExecute() {
        if (!this.horseHost.isTame() && this.horseHost.riddenByEntity != null) {
            net.minecraft.util.Vec3 vec3 = net.minecraft.entity.ai.RandomPositionGenerator.findRandomTarget(this.horseHost, 5, 4);
            if (vec3 == null) {
                return false;
            }

            this.targetX = vec3.x;
            this.targetY = vec3.y;
            this.targetZ = vec3.z;
            return true;
        } else {
            return false;
        }
    }
}
