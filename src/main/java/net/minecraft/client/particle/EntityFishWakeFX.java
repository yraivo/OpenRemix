package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityFishWakeFX extends EntityFX {
    protected EntityFishWakeFX(
            World worldValue,
            double p171_1,
            double p171_2,
            double p171_3,
            double motionXValue,
            double motionYValue,
            double motionZValue) {
        super(worldValue, p171_1, p171_2, p171_3, 0.0, 0.0, 0.0);
        this.motionX *= 0.3F;
        this.motionY = java.lang.Math.random() * 0.2F + 0.1F;
        this.motionZ *= 0.3F;
        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;
        this.setParticleTextureIndex(19);
        this.setSize(0.01F, 0.01F);
        this.particleMaxAge = (int) (8.0 / (java.lang.Math.random() * 0.8 + 0.2));
        this.particleGravity = 0.0F;
        this.motionX = motionXValue;
        this.motionY = motionYValue;
        this.motionZ = motionZValue;
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY = this.motionY - this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.98F;
        this.motionY *= 0.98F;
        this.motionZ *= 0.98F;
        int i = 60 - this.particleMaxAge;
        float f = i * 0.001F;
        this.setSize(f, f);
        this.setParticleTextureIndex(19 + i % 4);
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }
}
