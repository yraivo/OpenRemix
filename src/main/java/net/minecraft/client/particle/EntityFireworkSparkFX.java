package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.BoundingBox;
import net.minecraft.world.World;

public class EntityFireworkSparkFX extends EntityFX {
    private int baseTextureIndex = 160;
    private float fadeColourGreen;
    private float fadeColourRed;
    private boolean twinkle;
    private boolean trail;
    private EffectRenderer field_92047_az;
    private boolean hasFadeColour;
    private float fadeColourBlue;

    public BoundingBox getCollisionBoundingBox() {
        return null;
    }

    public EntityFireworkSparkFX(
            World worldValue,
            double d0,
            double d1,
            double d2,
            double motionXValue,
            double motionYValue,
            double motionZValue,
            EffectRenderer aLFValue) {
        super(worldValue, d0, d1, d2);
        this.motionX = motionXValue;
        this.motionY = motionYValue;
        this.motionZ = motionZValue;
        this.field_92047_az = aLFValue;
        this.particleScale *= 0.75F;
        this.particleMaxAge = 48 + this.rand.nextInt(12);
        this.noClip = false;
    }

    public int getBrightnessForRender(float f) {
        return 15728880;
    }

    public void setTwinkle(boolean alZValue) {
        this.twinkle = alZValue;
    }

    public void setColour(int i) {
        float f = ((i & 0xFF0000) >> 16) / 255.0F;
        float f1 = ((i & 0xFF00) >> 8) / 255.0F;
        float f2 = ((i & 0xFF) >> 0) / 255.0F;
        float f3 = 1.0F;
        this.setRBGColorF(f * f3, f1 * f3, f2 * f3);
    }

    public void setFadeColour(int i) {
        this.fadeColourRed = ((i & 0xFF0000) >> 16) / 255.0F;
        this.fadeColourGreen = ((i & 0xFF00) >> 8) / 255.0F;
        this.fadeColourBlue = ((i & 0xFF) >> 0) / 255.0F;
        this.hasFadeColour = true;
    }

    public boolean canBePushed() {
        return false;
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
        if (!this.twinkle
                || this.particleAge < this.particleMaxAge / 3
                || (this.particleAge + this.particleMaxAge) / 3 % 2 == 0) {
            super.renderParticle(worldRenderer, entity, f, f1, f2, f3, f4, f5);
        }
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        if (this.particleAge > this.particleMaxAge / 2) {
            this.setAlphaF(
                    1.0F
                            - ((float) this.particleAge - this.particleMaxAge / 2)
                                    / this.particleMaxAge);
            if (this.hasFadeColour) {
                this.particleRed =
                        this.particleRed + (this.fadeColourRed - this.particleRed) * 0.2F;
                this.particleGreen =
                        this.particleGreen + (this.fadeColourGreen - this.particleGreen) * 0.2F;
                this.particleBlue =
                        this.particleBlue + (this.fadeColourBlue - this.particleBlue) * 0.2F;
            }
        }

        this.setParticleTextureIndex(
                this.baseTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
        this.motionY -= 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.91F;
        this.motionY *= 0.91F;
        this.motionZ *= 0.91F;
        if (this.onGround) {
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
        }

        if (this.trail
                && this.particleAge < this.particleMaxAge / 2
                && (this.particleAge + this.particleMaxAge) % 2 == 0) {
            EntityFireworkSparkFX entityFireworkSparkFX =
                    new EntityFireworkSparkFX(
                            this.world,
                            this.posX,
                            this.posY,
                            this.posZ,
                            0.0,
                            0.0,
                            0.0,
                            this.field_92047_az);
            entityFireworkSparkFX.setAlphaF(0.99F);
            entityFireworkSparkFX.setRBGColorF(
                    this.particleRed, this.particleGreen, this.particleBlue);
            entityFireworkSparkFX.particleAge = entityFireworkSparkFX.particleMaxAge / 2;
            if (this.hasFadeColour) {
                entityFireworkSparkFX.hasFadeColour = true;
                entityFireworkSparkFX.fadeColourRed = this.fadeColourRed;
                entityFireworkSparkFX.fadeColourGreen = this.fadeColourGreen;
                entityFireworkSparkFX.fadeColourBlue = this.fadeColourBlue;
            }

            entityFireworkSparkFX.twinkle = this.twinkle;
            this.field_92047_az.addEffect(entityFireworkSparkFX);
        }
    }

    public float getBrightness(float f) {
        return 1.0F;
    }

    public void setTrail(boolean aLEValue) {
        this.trail = aLEValue;
    }
}
