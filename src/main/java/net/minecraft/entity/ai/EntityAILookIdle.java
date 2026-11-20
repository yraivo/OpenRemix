package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAILookIdle extends EntityAIBase {
    private double lookZ;
    private EntityLiving idleEntity;
    private int idleTime;
    private double lookX;

    public void updateTask() {
        this.idleTime--;
        this.idleEntity
                .getLookHelper()
                .setLookPosition(
                        this.idleEntity.posX + this.lookX,
                        this.idleEntity.posY + this.idleEntity.getEyeHeight(),
                        this.idleEntity.posZ + this.lookZ,
                        10.0F,
                        this.idleEntity.getVerticalFaceSpeed());
    }

    public boolean continueExecuting() {
        return this.idleTime >= 0;
    }

    public void startExecuting() {
        double d0 = (Math.PI * 2) * this.idleEntity.getRNG().nextDouble();
        this.lookX = Math.cos(d0);
        this.lookZ = Math.sin(d0);
        this.idleTime = 20 + this.idleEntity.getRNG().nextInt(20);
    }

    public boolean shouldExecute() {
        return this.idleEntity.getRNG().nextFloat() < 0.02F;
    }

    public EntityAILookIdle(EntityLiving bhtValue) {
        this.idleEntity = bhtValue;
        this.setMutexBits(3);
    }
}
