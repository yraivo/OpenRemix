package net.minecraft.client.particle;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFootStepFX extends EntityFX {

    private static ResourceLocation FOOTPRINT_TEXTURE;
    private int footstepAge;
    private int footstepMaxAge;
    private TextureManager currentFootSteps;

    public void renderParticle(
            WorldRenderer worldRenderer,
            Entity entity,
            float f,
            float f8,
            float f9,
            float f10,
            float f11,
            float f12) {
        float f1 = (this.footstepAge + f) / this.footstepMaxAge;
        f1 *= f1;
        float f2 = 2.0F - f1 * 2.0F;
        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        f2 *= 0.2F;
        GlStateManager.disableLighting();
        float f3 = 0.125F;
        float f4 = (float) (this.posX - net.minecraft.client.particle.EntityFX.interpPosX);
        float f5 = (float) (this.posY - net.minecraft.client.particle.EntityFX.interpPosY);
        float f6 = (float) (this.posZ - net.minecraft.client.particle.EntityFX.interpPosZ);
        float lightBrightness = this.world.getLightBrightness(new BlockPos(this));
        this.currentFootSteps.bindTexture(net.minecraft.client.particle.EntityFootStepFX.FOOTPRINT_TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexColorFromFloats(lightBrightness, lightBrightness, lightBrightness, f2);
        worldRenderer.addVertexWithUV(f4 - f3, f5, f6 + f3, 0.0, 1.0);
        worldRenderer.addVertexWithUV(f4 + f3, f5, f6 + f3, 1.0, 1.0);
        worldRenderer.addVertexWithUV(f4 + f3, f5, f6 - f3, 1.0, 0.0);
        worldRenderer.addVertexWithUV(f4 - f3, f5, f6 - f3, 0.0, 0.0);
        Tessellator.getInstance().draw();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
    }

    public int getFXLayer() {
        return 3;
    }

    // region String decoding support

    // endregion

    protected EntityFootStepFX(
            TextureManager aLMValue, World worldValue, double d0, double d1, double d2) {
        super(worldValue, d0, d1, d2, 0.0, 0.0, 0.0);
        this.currentFootSteps = aLMValue;
        this.motionX = this.motionY = this.motionZ = 0.0;
        this.footstepMaxAge = 200;
    }

    public void onUpdate() {
        this.footstepAge++;
        if (this.footstepAge == this.footstepMaxAge) {
            this.setDead();
        }
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.particle.EntityFootStepFX.FOOTPRINT_TEXTURE =
                new ResourceLocation("textures/particle/footprint.png");
    }
}
