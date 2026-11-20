package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityLavaFX extends EntityFX {
    private float lavaParticleScale;

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        float f = (float) this.particleAge / this.particleMaxAge;
        if (this.rand.nextFloat() > f) {
            this.world.spawnParticle(
                    EnumParticleTypes.SMOKE_NORMAL,
                    this.posX,
                    this.posY,
                    this.posZ,
                    this.motionX,
                    this.motionY,
                    this.motionZ,
                    new int[0]);
        }

        this.motionY -= 0.03;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.999F;
        this.motionY *= 0.999F;
        this.motionZ *= 0.999F;
        if (this.onGround) {
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
        }
    }

    public float getBrightness(float f) {
        return 1.0F;
    }

    public void renderParticle(
            WorldRenderer worldRenderer,
            Entity entity,
            float p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            float p171_6,
            float p171_7) {
        float f6 = (this.particleAge + p171_2) / this.particleMaxAge;
        this.particleScale = this.lavaParticleScale * (1.0F - f6 * f6);
        super.renderParticle(worldRenderer, entity, p171_2, p171_3, p171_4, p171_5, p171_6, p171_7);
    }

    public int getBrightnessForRender(float f) {
        float f1 = (this.particleAge + f) / this.particleMaxAge;
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        int brightnessForRender = super.getBrightnessForRender(f);
        short short1 = 240;
        int j = brightnessForRender >> 16 & 0xFF;
        return short1 | j << 16;
    }

    protected EntityLavaFX(World worldValue, double p171_1, double p171_2, double p171_3) {
        super(worldValue, p171_1, p171_2, p171_3, 0.0, 0.0, 0.0);
        this.motionX *= 0.8F;
        this.motionY *= 0.8F;
        this.motionZ *= 0.8F;
        this.motionY = this.rand.nextFloat() * 0.4F + 0.05F;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleScale = this.particleScale * (this.rand.nextFloat() * 2.0F + 0.2F);
        this.lavaParticleScale = this.particleScale;
        this.particleMaxAge = (int) (16.0 / (java.lang.Math.random() * 0.8 + 0.2));
        this.noClip = false;
        this.setParticleTextureIndex(49);
    }
}
