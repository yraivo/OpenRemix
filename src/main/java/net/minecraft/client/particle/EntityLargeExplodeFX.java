package net.minecraft.client.particle;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityLargeExplodeFX extends EntityFX {
    private TextureManager theRenderEngine;
    private int field_70581_a;

    private int field_70584_aq;
    private float field_70582_as;
    private static ResourceLocation EXPLOSION_TEXTURE;

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.field_70581_a++;
        if (this.field_70581_a == this.field_70584_aq) {
            this.setDead();
        }
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
        int i = (int) ((this.field_70581_a + f) * 15.0F / this.field_70584_aq);
        if (i <= 15) {
            this.theRenderEngine.bindTexture(net.minecraft.client.particle.EntityLargeExplodeFX.EXPLOSION_TEXTURE);
            float f6 = i % 4 / 4.0F;
            float f7 = f6 + 0.24975F;
            float f8 = i / 4 / 4.0F;
            float f9 = f8 + 0.24975F;
            float f10 = 2.0F * this.field_70582_as;
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
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            RenderHelper.disableStandardItemLighting();
            worldRenderer.startDrawingQuads();
            worldRenderer.setVertexColorFromFloats(
                    this.particleRed, this.particleGreen, this.particleBlue, 1.0F);
            worldRenderer.setVertexNormal(0.0F, 1.0F, 0.0F);
            worldRenderer.setPackedLightmap(240);
            worldRenderer.addVertexWithUV(
                    f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, f7, f9);
            worldRenderer.addVertexWithUV(
                    f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10, f7, f8);
            worldRenderer.addVertexWithUV(
                    f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, f6, f8);
            worldRenderer.addVertexWithUV(
                    f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10, f6, f9);
            Tessellator.getInstance().draw();
            GlStateManager.doPolygonOffset(0.0F, 0.0F);
            GlStateManager.enableLighting();
        }
    }

    public int getBrightnessForRender(float f) {
        return 61680;
    }

    // region String decoding support

    // endregion

    protected EntityLargeExplodeFX(
            TextureManager aLMValue,
            World worldValue,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5) {
        super(worldValue, d0, d1, d2, 0.0, 0.0, 0.0);
        this.theRenderEngine = aLMValue;
        this.field_70584_aq = 6 + this.rand.nextInt(4);
        this.particleRed =
                this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.6F + 0.4F;
        this.field_70582_as = 1.0F - (float) d3 * 0.5F;
    }

    static {
        net.minecraft.client.particle.EntityLargeExplodeFX.EXPLOSION_TEXTURE =
                new ResourceLocation("textures/entity/explosion.png");
    }

    public int getFXLayer() {
        return 3;
    }

    // region String decoding support

    // endregion
}
