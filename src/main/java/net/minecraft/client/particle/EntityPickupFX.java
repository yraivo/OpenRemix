package net.minecraft.client.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityPickupFX extends EntityFX {
    private Entity aLO;
    private int maxAge;
    private int age;
    private RenderManager aLQ = MinecraftClient.getMinecraft().getRenderManager();
    private Entity aLR;
    private float aIV;

    public void renderParticle(
            WorldRenderer worldRenderer,
            Entity entity,
            float p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            float p171_6,
            float p171_7) {
        float f6 = (this.age + p171_2) / this.maxAge;
        f6 *= f6;
        double posXValue = this.aLO.posX;
        double posYValue = this.aLO.posY;
        double posZValue = this.aLO.posZ;
        double d3 = this.aLR.lastTickPosX + (this.aLR.posX - this.aLR.lastTickPosX) * p171_2;
        double d4 =
                this.aLR.lastTickPosY + (this.aLR.posY - this.aLR.lastTickPosY) * p171_2 + this.aIV;
        double d5 = this.aLR.lastTickPosZ + (this.aLR.posZ - this.aLR.lastTickPosZ) * p171_2;
        double d6 = posXValue + (d3 - posXValue) * f6;
        double d7 = posYValue + (d4 - posYValue) * f6;
        double d8 = posZValue + (d5 - posZValue) * f6;
        int brightnessForRender = this.getBrightnessForRender(p171_2);
        int j = brightnessForRender % 65536;
        int k = brightnessForRender / 65536;
        net.minecraft.client.renderer.OpenGlHelper.setLightmapTextureCoords(
                net.minecraft.client.renderer.OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
        net.minecraft.client.renderer.GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        d6 -= net.minecraft.client.particle.EntityFX.interpPosX;
        d7 -= net.minecraft.client.particle.EntityFX.interpPosY;
        d8 -= net.minecraft.client.particle.EntityFX.interpPosZ;
        this.aLQ.renderEntityWithPosYaw(
                this.aLO, (float) d6, (float) d7, (float) d8, this.aLO.rotationYaw, p171_2);
    }

    public void onUpdate() {
        this.age++;
        if (this.age == this.maxAge) {
            this.setDead();
        }
    }

    public EntityPickupFX(
            World worldValue, Entity aLOValue, Entity aLRValue, float aIVValue) {
        super(
                worldValue,
                aLOValue.posX,
                aLOValue.posY,
                aLOValue.posZ,
                aLOValue.motionX,
                aLOValue.motionY,
                aLOValue.motionZ);
        this.aLO = aLOValue;
        this.aLR = aLRValue;
        this.maxAge = 3;
        this.aIV = aIVValue;
    }

    public int getFXLayer() {
        return 3;
    }
}
