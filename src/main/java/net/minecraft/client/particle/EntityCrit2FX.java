package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCrit2FX extends EntityFX {
    float field_174839_a;

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.particleGreen = (float) (this.particleGreen * 0.96);
        this.particleBlue = (float) (this.particleBlue * 0.9);
        this.motionX *= 0.7F;
        this.motionY *= 0.7F;
        this.motionZ *= 0.7F;
        this.motionY -= 0.02F;
        if (this.onGround) {
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
        }
    }

    protected EntityCrit2FX(
            World worldValue,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            float f) {
        super(worldValue, d0, d1, d2, 0.0, 0.0, 0.0);
        this.motionX *= 0.1F;
        this.motionY *= 0.1F;
        this.motionZ *= 0.1F;
        this.motionX += d3 * 0.4;
        this.motionY += d4 * 0.4;
        this.motionZ += d5 * 0.4;
        this.particleRed =
                this.particleGreen = this.particleBlue = (float) (Math.random() * 0.3F + 0.6F);
        this.particleScale *= 0.75F;
        this.particleScale *= f;
        this.field_174839_a = this.particleScale;
        this.particleMaxAge = (int) (6.0 / (Math.random() * 0.8 + 0.6));
        this.particleMaxAge = (int) (this.particleMaxAge * f);
        this.noClip = false;
        this.setParticleTextureIndex(65);
        this.onUpdate();
    }

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
        this.particleScale = this.field_174839_a * f6;
        super.renderParticle(worldRenderer, entity, f, f1, f2, f3, f4, f5);
    }

    protected EntityCrit2FX(
            World worldValue, double d0, double d1, double d2, double d3, double d4, double d5) {
        this(worldValue, d0, d1, d2, d3, d4, d5, 1.0F);
    }
}
