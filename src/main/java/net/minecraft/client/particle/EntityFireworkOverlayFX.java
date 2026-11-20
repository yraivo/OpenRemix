package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFireworkOverlayFX extends EntityFX {
    protected EntityFireworkOverlayFX(World worldValue, double d0, double d1, double d2) {
        super(worldValue, d0, d1, d2);
        this.particleMaxAge = 4;
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
        float f6 = 0.25F;
        float f7 = f6 + 0.25F;
        float f8 = 0.125F;
        float f9 = f8 + 0.25F;
        float f10 = 7.1F * MathHelper.sin((this.particleAge + f - 1.0F) * 0.25F * (float) Math.PI);
        this.particleAlpha = 0.6F - (this.particleAge + f - 1.0F) * 0.25F * 0.5F;
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
        String text = EffectRenderer.getParticleFlowMarker();
        worldRenderer.addVertexWithUV(
                f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, f6, f8);
        String textValue = text;
        worldRenderer.addVertexWithUV(
                f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10, f6, f9);
        if (textValue == null) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }
    }
}
