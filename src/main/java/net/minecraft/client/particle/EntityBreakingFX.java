package net.minecraft.client.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityBreakingFX extends EntityFX {
    protected EntityBreakingFX(World worldValue, double d0, double d1, double d2, Item item) {
        this(worldValue, d0, d1, d2, item, 0);
    }

    public int getFXLayer() {
        return 1;
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
        float f6 = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
        float f7 = f6 + 0.015609375F;
        float f8 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
        float f9 = f8 + 0.015609375F;
        float f10 = 0.1F * this.particleScale;
        if (this.particleIcon != null) {
            f6 = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0F * 16.0F);
            f7 =
                    this.particleIcon.getInterpolatedU(
                            (this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
            f8 = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0F * 16.0F);
            f9 =
                    this.particleIcon.getInterpolatedV(
                            (this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
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
        worldRenderer.setOpaqueVertexColorFromFloats(this.particleRed, this.particleGreen, this.particleBlue);
        worldRenderer.addVertexWithUV(
                f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, f6, f9);
        worldRenderer.addVertexWithUV(
                f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10, f6, f8);
        worldRenderer.addVertexWithUV(
                f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, f7, f8);
        worldRenderer.addVertexWithUV(
                f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10, f7, f9);
    }

    protected EntityBreakingFX(
            World worldValue, double d0, double d1, double d2, Item item, int i) {
        super(worldValue, d0, d1, d2, 0.0, 0.0, 0.0);
        this.setParticleIcon(
                MinecraftClient.getMinecraft()
                        .getRenderItem()
                        .getItemModelMesher()
                        .getParticleIcon(item, i));
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleGravity = Blocks.snow.blockParticleGravity;
        this.particleScale /= 2.0F;
    }

    protected EntityBreakingFX(
            World worldValue,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            Item item,
            int i) {
        this(worldValue, d0, d1, d2, item, i);
        this.motionX *= 0.1F;
        this.motionY *= 0.1F;
        this.motionZ *= 0.1F;
        this.motionX += d3;
        this.motionY += d4;
        this.motionZ += d5;
    }
}
