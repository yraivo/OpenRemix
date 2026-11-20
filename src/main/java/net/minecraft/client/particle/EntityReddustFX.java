package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityReddustFX extends EntityFX {
    float reddustParticleScale;

    public void renderParticle(
            WorldRenderer worldRenderer,
            Entity entity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5) {
        float f6 = (this.particleAge + f) / this.particleMaxAge * 32.0F;
        f6 = MathHelper.clamp(f6, 0.0F, 1.0F);
        this.particleScale = this.reddustParticleScale * f6;
        super.renderParticle(worldRenderer, entity, f, f1, f2, f3, f4, f5);
    }

    protected EntityReddustFX(
            World worldValue,
            double d0,
            double d1,
            double d2,
            float f,
            float f1,
            float f2,
            float f3) {
        super(worldValue, d0, d1, d2, 0.0, 0.0, 0.0);
        this.motionX *= 0.1F;
        this.motionY *= 0.1F;
        this.motionZ *= 0.1F;
        if (f1 == 0.0F) {
            f1 = 1.0F;
        }

        float f4 = (float) Math.random() * 0.4F + 0.6F;
        this.particleRed = ((float) (Math.random() * 0.2F) + 0.8F) * f1 * f4;
        this.particleGreen = ((float) (Math.random() * 0.2F) + 0.8F) * f2 * f4;
        this.particleBlue = ((float) (Math.random() * 0.2F) + 0.8F) * f3 * f4;
        this.particleScale *= 0.75F;
        this.particleScale *= f;
        this.reddustParticleScale = this.particleScale;
        this.particleMaxAge = (int) (8.0 / (Math.random() * 0.8 + 0.2));
        this.particleMaxAge = (int) (this.particleMaxAge * f);
        this.noClip = false;
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }

        this.motionX *= 0.96F;
        this.motionY *= 0.96F;
        this.motionZ *= 0.96F;
        if (this.onGround) {
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
        }
    }

    protected EntityReddustFX(
            World worldValue, double d0, double d1, double d2, float f, float f1, float f2) {
        this(worldValue, d0, d1, d2, 1.0F, f, f1, f2);
    }
}
