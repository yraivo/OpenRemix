package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityExplodeFX extends EntityFX {
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.motionY += 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9F;
        this.motionY *= 0.9F;
        this.motionZ *= 0.9F;
        if (this.onGround) {
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
        }
    }

    protected EntityExplodeFX(
            World worldValue,
            double p171_1,
            double p171_2,
            double p171_3,
            double p171_4,
            double p171_5,
            double p171_6) {
        super(worldValue, p171_1, p171_2, p171_3, p171_4, p171_5, p171_6);
        this.motionX = p171_4 + (java.lang.Math.random() * 2.0 - 1.0) * 0.05F;
        this.motionY = p171_5 + (java.lang.Math.random() * 2.0 - 1.0) * 0.05F;
        this.motionZ = p171_6 + (java.lang.Math.random() * 2.0 - 1.0) * 0.05F;
        this.particleRed =
                this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3F + 0.7F;
        this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0F + 1.0F;
        this.particleMaxAge = (int) (16.0 / (this.rand.nextFloat() * 0.8 + 0.2)) + 2;
    }
}
