package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityAuraFX extends EntityFX {
    protected EntityAuraFX(
            World worldValue,
            double p171_1,
            double p171_2,
            double p171_3,
            double p171_4,
            double p171_5,
            double p171_6) {
        super(worldValue, p171_1, p171_2, p171_3, p171_4, p171_5, p171_6);
        float aLoValue = this.rand.nextFloat() * 0.1F + 0.2F;
        this.particleRed = aLoValue;
        this.particleGreen = aLoValue;
        this.particleBlue = aLoValue;
        this.setParticleTextureIndex(0);
        this.setSize(0.02F, 0.02F);
        this.particleScale = this.particleScale * (this.rand.nextFloat() * 0.6F + 0.5F);
        this.motionX *= 0.02F;
        this.motionY *= 0.02F;
        this.motionZ *= 0.02F;
        this.particleMaxAge = (int) (20.0 / (java.lang.Math.random() * 0.8 + 0.2));
        this.noClip = true;
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.99;
        this.motionY *= 0.99;
        this.motionZ *= 0.99;
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }
}
