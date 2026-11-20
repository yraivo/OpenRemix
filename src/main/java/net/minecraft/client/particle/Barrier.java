package net.minecraft.client.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class Barrier extends EntityFX {
    public int getFXLayer() {
        return 1;
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
        float minU = this.particleIcon.getMinU();
        float maxU = this.particleIcon.getMaxU();
        float minV = this.particleIcon.getMinV();
        float maxV = this.particleIcon.getMaxV();
        float f10 =
                (float)
                        (this.prevPosX
                                + (this.posX - this.prevPosX) * p171_2
                                - net.minecraft.client.particle.EntityFX.interpPosX);
        float f11 =
                (float)
                        (this.prevPosY
                                + (this.posY - this.prevPosY) * p171_2
                                - net.minecraft.client.particle.EntityFX.interpPosY);
        float f12 =
                (float)
                        (this.prevPosZ
                                + (this.posZ - this.prevPosZ) * p171_2
                                - net.minecraft.client.particle.EntityFX.interpPosZ);
        worldRenderer.setOpaqueVertexColorFromFloats(this.particleRed, this.particleGreen, this.particleBlue);
        float f13 = 0.5F;
        worldRenderer.addVertexWithUV(
                f10 - p171_3 * f13 - p171_6 * f13,
                f11 - p171_4 * f13,
                f12 - p171_5 * f13 - p171_7 * f13,
                maxU,
                maxV);
        worldRenderer.addVertexWithUV(
                f10 - p171_3 * f13 + p171_6 * f13,
                f11 + p171_4 * f13,
                f12 - p171_5 * f13 + p171_7 * f13,
                maxU,
                minV);
        worldRenderer.addVertexWithUV(
                f10 + p171_3 * f13 + p171_6 * f13,
                f11 + p171_4 * f13,
                f12 + p171_5 * f13 + p171_7 * f13,
                minU,
                minV);
        worldRenderer.addVertexWithUV(
                f10 + p171_3 * f13 - p171_6 * f13,
                f11 - p171_4 * f13,
                f12 + p171_5 * f13 - p171_7 * f13,
                minU,
                maxV);
    }

    protected Barrier(World worldValue, double d0, double d1, double d2, Item item) {
        super(worldValue, d0, d1, d2, 0.0, 0.0, 0.0);
        this.setParticleIcon(
                MinecraftClient.getMinecraft()
                        .getRenderItem()
                        .getItemModelMesher()
                        .getParticleIcon(item));
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.motionX = this.motionY = this.motionZ = 0.0;
        this.particleGravity = 0.0F;
        this.particleMaxAge = 80;
    }
}
