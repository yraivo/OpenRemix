package net.minecraft.client.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFX extends Entity {
    protected float particleTextureJitterX;
    protected float particleBlue;
    public static double interpPosY;
    public static double interpPosX;

    protected int particleAge;
    public static double interpPosZ;
    protected int particleTextureIndexY;
    protected float particleScale;
    private static String[] packedTextFragments;
    protected float particleGravity;
    protected float particleTextureJitterY;
    protected int particleMaxAge;
    protected float particleGreen;
    protected float particleRed;

    protected TextureAtlasSprite particleIcon;
    protected float particleAlpha = 1.0F;
    protected int particleTextureIndexX;

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.motionY = this.motionY - 0.04 * this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.98F;
        this.motionY *= 0.98F;
        this.motionZ *= 0.98F;
        if (this.onGround) {
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
        }
    }

    private static RuntimeException method_1a1f08aee5fe72868e61(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    static {
        net.minecraft.client.particle.EntityFX.packedTextFragments = new String[7];
        net.minecraft.client.particle.EntityFX.packedTextFragments[0] = new String(",");
        net.minecraft.client.particle.EntityFX.packedTextFragments[1] = new String(",");
        net.minecraft.client.particle.EntityFX.packedTextFragments[2] = new String(",");
        net.minecraft.client.particle.EntityFX.packedTextFragments[3] = new String(",");
        net.minecraft.client.particle.EntityFX.packedTextFragments[4] = new String(",");
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {}

    public float getRedColorF() {
        return this.particleRed;
    }

    public int getFXLayer() {
        return 0;
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
        float f6 = this.particleTextureIndexX / 16.0F;
        float f7 = f6 + 0.0624375F;
        float f8 = this.particleTextureIndexY / 16.0F;
        float f9 = f8 + 0.0624375F;
        float f10 = 0.1F * this.particleScale;
        if (this.particleIcon != null) {
            f6 = this.particleIcon.getMinU();
            f7 = this.particleIcon.getMaxU();
            f8 = this.particleIcon.getMinV();
            f9 = this.particleIcon.getMaxV();
        }

        float f11 =
                (float)
                        (this.prevPosX
                                + (this.posX - this.prevPosX) * f
                                - net.minecraft.client.particle.EntityFX.interpPosX);
        float f12 =
                (float)
                        (this.prevPosY
                                + (this.posY - this.prevPosY) * f
                                - net.minecraft.client.particle.EntityFX.interpPosY);
        float f13 =
                (float)
                        (this.prevPosZ
                                + (this.posZ - this.prevPosZ) * f
                                - net.minecraft.client.particle.EntityFX.interpPosZ);
        worldRenderer.setVertexColorFromFloats(
                this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        worldRenderer.addVertexWithUV(
                f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, f7, f9);
        worldRenderer.addVertexWithUV(
                f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10, f7, f8);
        worldRenderer.addVertexWithUV(
                f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, f6, f8);
        worldRenderer.addVertexWithUV(
                f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10, f6, f9);
    }

    public void setParticleIcon(TextureAtlasSprite aLyValue) {
        String text = EffectRenderer.getParticleFlowMarker();
        int fxLayer = this.getFXLayer();
        String textValue = text;
        if (fxLayer == 1) {
            this.particleIcon = aLyValue;
            if (textValue != null) {
                return;
            }
        }

        throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
    }

    public void setRBGColorF(float aLxValue, float aLwValue, float aLoValue) {
        this.particleRed = aLxValue;
        this.particleGreen = aLwValue;
        this.particleBlue = aLoValue;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public float getAlpha() {
        return this.particleAlpha;
    }

    public EntityFX multipleParticleScaleBy(float f) {
        this.setSize(0.2F * f, 0.2F * f);
        this.particleScale *= f;
        return this;
    }

    public EntityFX(
            World worldValue, double d0, double d1, double d2, double d3, double d4, double d5) {
        this(worldValue, d0, d1, d2);
        this.motionX = d3 + (Math.random() * 2.0 - 1.0) * 0.4F;
        this.motionY = d4 + (Math.random() * 2.0 - 1.0) * 0.4F;
        this.motionZ = d5 + (Math.random() * 2.0 - 1.0) * 0.4F;
        float f = (float) (Math.random() + Math.random() + 1.0) * 0.15F;
        float f1 =
                MathHelper.sqrt(
                        this.motionX * this.motionX
                                + this.motionY * this.motionY
                                + this.motionZ * this.motionZ);
        this.motionX = this.motionX / f1 * f * 0.4F;
        this.motionY = this.motionY / f1 * f * 0.4F + 0.1F;
        this.motionZ = this.motionZ / f1 * f * 0.4F;
    }

    public EntityFX multiplyVelocity(float f) {
        this.motionX *= f;
        this.motionY = (this.motionY - 0.1F) * f + 0.1F;
        this.motionZ *= f;
        return this;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {}

    public void setParticleTextureIndex(int i) {
        if (this.getFXLayer() != 0) {
            throw new RuntimeException("Invalid call to Particle.setMiscTex");
        }

        this.particleTextureIndexX = i % 16;
        this.particleTextureIndexY = i / 16;
    }

    protected void entityInit() {}

    public float getBlueColorF() {
        return this.particleBlue;
    }

    public float getGreenColorF() {
        return this.particleGreen;
    }

    public void nextTextureIndexX() {
        this.particleTextureIndexX++;
    }

    public boolean canAttackWithItem() {
        return false;
    }

    public String toString() {
        return this.getClass().getSimpleName()
                + ", Pos ("
                + this.posX
                + packedTextFragments[0]
                + this.posY
                + packedTextFragments[1]
                + this.posZ
                + "), RGBA ("
                + this.particleRed
                + packedTextFragments[2]
                + this.particleGreen
                + packedTextFragments[3]
                + this.particleBlue
                + packedTextFragments[4]
                + this.particleAlpha
                + "), Age "
                + this.particleAge;
    }

    protected EntityFX(
            World worldValue,
            double lastTickPosXValue,
            double lastTickPosYValue,
            double lastTickPosZValue) {
        super(worldValue);
        this.setSize(0.2F, 0.2F);
        this.setPosition(lastTickPosXValue, lastTickPosYValue, lastTickPosZValue);
        this.lastTickPosX = lastTickPosXValue;
        this.lastTickPosY = lastTickPosYValue;
        this.lastTickPosZ = lastTickPosZValue;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleTextureJitterX = this.rand.nextFloat() * 3.0F;
        this.particleTextureJitterY = this.rand.nextFloat() * 3.0F;
        this.particleScale = (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
        this.particleMaxAge = (int) (4.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
        this.particleAge = 0;
    }

    public void setAlphaF(float aLzValue) {
        {
            String text = EffectRenderer.getParticleFlowMarker();
            if (this.particleAlpha == 1.0F && aLzValue < 1.0F) {
                MinecraftClient.getMinecraft().effectRenderer.moveToAlphaLayer(this);
                if (!(text != null)) {

                    if (this.particleAlpha < 1.0F && aLzValue == 1.0F) {
                        MinecraftClient.getMinecraft().effectRenderer.moveToNoAlphaLayer(this);
                    }
                }
            } else {

                if (this.particleAlpha < 1.0F && aLzValue == 1.0F) {
                    MinecraftClient.getMinecraft().effectRenderer.moveToNoAlphaLayer(this);
                }
            }
        }

        this.particleAlpha = aLzValue;
    }
}
