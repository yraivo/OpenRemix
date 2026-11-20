package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityHeartFX extends EntityFX {
    float particleScaleOverTime;

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
        this.particleScale = this.particleScaleOverTime * f6;
        super.renderParticle(worldRenderer, entity, f, f1, f2, f3, f4, f5);
    }

    protected EntityHeartFX(
            World worldValue,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            float f) {
        super(worldValue, d0, d1, d2, 0.0, 0.0, 0.0);
        this.motionX *= 0.01F;
        this.motionY *= 0.01F;
        this.motionZ *= 0.01F;
        this.motionY += 0.1;
        this.particleScale *= 0.75F;
        this.particleScale *= f;
        this.particleScaleOverTime = this.particleScale;
        this.particleMaxAge = 16;
        this.noClip = false;
        this.setParticleTextureIndex(80);
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }

        this.motionX *= 0.86F;
        this.motionY *= 0.86F;
        this.motionZ *= 0.86F;
        if (this.onGround) {
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
        }
    }

    protected EntityHeartFX(
            World worldValue, double d0, double d1, double d2, double d3, double d4, double d5) {
        this(worldValue, d0, d1, d2, d3, d4, d5, 2.0F);
    }
}
