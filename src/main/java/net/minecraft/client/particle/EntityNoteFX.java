package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityNoteFX extends EntityFX {
    float noteParticleScale;

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

        this.motionX *= 0.66F;
        this.motionY *= 0.66F;
        this.motionZ *= 0.66F;
        if (this.onGround) {
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
        }
    }

    protected EntityNoteFX(
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
        this.motionY += 0.2;
        this.particleRed =
                MathHelper.sin(((float) d3 + 0.0F) * (float) Math.PI * 2.0F) * 0.65F + 0.35F;
        this.particleGreen =
                MathHelper.sin(((float) d3 + 0.33333334F) * (float) Math.PI * 2.0F) * 0.65F + 0.35F;
        this.particleBlue =
                MathHelper.sin(((float) d3 + 0.6666667F) * (float) Math.PI * 2.0F) * 0.65F + 0.35F;
        this.particleScale *= 0.75F;
        this.particleScale *= f;
        this.noteParticleScale = this.particleScale;
        this.particleMaxAge = 6;
        this.noClip = false;
        this.setParticleTextureIndex(64);
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
        this.particleScale = this.noteParticleScale * f6;
        super.renderParticle(worldRenderer, entity, f, f1, f2, f3, f4, f5);
    }

    protected EntityNoteFX(
            World worldValue, double d0, double d1, double d2, double d3, double d4, double d5) {
        this(worldValue, d0, d1, d2, d3, d4, d5, 2.0F);
    }
}
