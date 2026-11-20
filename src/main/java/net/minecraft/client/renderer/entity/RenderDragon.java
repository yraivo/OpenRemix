package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.layers.LayerEnderDragonDeath;
import net.minecraft.client.renderer.entity.layers.LayerEnderDragonEyes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderDragon extends RenderLiving {
    private static ResourceLocation enderDragonTextures;
    private static ResourceLocation enderDragonCrystalBeamTextures;
    private static ResourceLocation enderDragonExplodingTextures;

    protected ModelDragon modelDragon = (ModelDragon) this.mainModel;

    public void doRender(
            EntityLiving entityLiving, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityDragon) entityLiving, d0, d1, d2, f, f1);
    }

    public RenderDragon(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelDragon(0.0F), 0.5F);
        this.addLayer(new LayerEnderDragonEyes(this));
        this.addLayer(new LayerEnderDragonDeath());
    }

    protected void drawRechargeRay(
            EntityDragon entityDragon, double d0, double d1, double d2, float f) {
        float f1 = entityDragon.healingEnderCrystal.innerRotation + f;
        float f2 = MathHelper.sin(f1 * 0.2F) / 2.0F + 0.5F;
        f2 = (f2 * f2 + f2) * 0.2F;
        float f3 =
                (float)
                        (entityDragon.healingEnderCrystal.posX
                                - entityDragon.posX
                                - (entityDragon.prevPosX - entityDragon.posX) * (1.0F - f));
        float f4 =
                (float)
                        (f2
                                + entityDragon.healingEnderCrystal.posY
                                - 1.0
                                - entityDragon.posY
                                - (entityDragon.prevPosY - entityDragon.posY) * (1.0F - f));
        float f5 =
                (float)
                        (entityDragon.healingEnderCrystal.posZ
                                - entityDragon.posZ
                                - (entityDragon.prevPosZ - entityDragon.posZ) * (1.0F - f));
        float f6 = MathHelper.sqrt(f3 * f3 + f5 * f5);
        Render.getLegacyEntityRenderState();

        float f7 = MathHelper.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) d0, (float) d1 + 2.0F, (float) d2);
        GlStateManager.rotate(
                (float) (-Math.atan2(f5, f3)) * 180.0F / (float) Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(
                (float) (-Math.atan2(f6, f4)) * 180.0F / (float) Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        this.bindTexture(net.minecraft.client.renderer.entity.RenderDragon.enderDragonCrystalBeamTextures);
        GlStateManager.shadeModel(7425);

        float f8 = 0.0F - (entityDragon.ticksExisted + f) * 0.01F;
        float f9 =
                MathHelper.sqrt(f3 * f3 + f4 * f4 + f5 * f5) / 32.0F
                        - (entityDragon.ticksExisted + f) * 0.01F;
        worldRenderer.startDrawing(5);
        byte b0 = 8;
        int i = 0;

        while (i <= b0) {
            float f10 = MathHelper.sin(i % b0 * (float) Math.PI * 2.0F / b0) * 0.75F;
            float f11 = MathHelper.cos(i % b0 * (float) Math.PI * 2.0F / b0) * 0.75F;
            float f12 = i % b0 * 1.0F / b0;
            worldRenderer.setOpaqueVertexColorFromRgb(0);
            worldRenderer.addVertexWithUV(f10 * 0.2F, f11 * 0.2F, 0.0, f12, f9);
            worldRenderer.setOpaqueVertexColorFromRgb(16777215);
            worldRenderer.addVertexWithUV(f10, f11, f7, f12, f8);
            i++;
        }

        instance.draw();
        GlStateManager.enableCull();
        GlStateManager.shadeModel(7424);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityDragon) entity, d0, d1, d2, f, f1);
    }

    public void doRender(
            EntityDragon entityDragon, double d0, double d1, double d2, float f, float f1) {
        BossStatus.setBossStatus(entityDragon, false);
        super.doRender(entityDragon, d0, d1, d2, f, f1);
        if (entityDragon.healingEnderCrystal != null) {
            this.drawRechargeRay(entityDragon, d0, d1, d2, f1);
        }
    }

    public void doRender(
            LivingEntity livingEntity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityDragon) livingEntity, d0, d1, d2, f, f1);
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityDragon) entity);
    }

    protected void renderModel(
            EntityDragon entityDragon, float f, float f1, float f2, float f3, float f4, float f5) {
        if (entityDragon.deathTicks > 0) {
            float f6 = entityDragon.deathTicks / 200.0F;
            GlStateManager.depthFunc(515);
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, f6);
            this.bindTexture(net.minecraft.client.renderer.entity.RenderDragon.enderDragonExplodingTextures);
            this.mainModel.render(entityDragon, f, f1, f2, f3, f4, f5);
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.depthFunc(514);
        }

        this.bindEntityTexture(entityDragon);
        this.mainModel.render(entityDragon, f, f1, f2, f3, f4, f5);
        if (entityDragon.hurtTime > 0) {
            GlStateManager.depthFunc(514);
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 0.0F, 0.0F, 0.5F);
            this.mainModel.render(entityDragon, f, f1, f2, f3, f4, f5);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.depthFunc(515);
        }
    }

    static {
        net.minecraft.client.renderer.entity.RenderDragon.enderDragonCrystalBeamTextures =
                new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
        net.minecraft.client.renderer.entity.RenderDragon.enderDragonExplodingTextures =
                new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
        net.minecraft.client.renderer.entity.RenderDragon.enderDragonTextures =
                new ResourceLocation("textures/entity/enderdragon/dragon.png");
    }

    protected void renderModel(
            LivingEntity livingEntity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.renderModel((EntityDragon) livingEntity, f, f1, f2, f3, f4, f5);
    }

    protected void rotateCorpse(LivingEntity livingEntity, float f, float f1, float f2) {
        this.method_f27da31f4c570cd76213((EntityDragon) livingEntity, f, f1, f2);
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(EntityDragon entityDragon) {
        return net.minecraft.client.renderer.entity.RenderDragon.enderDragonTextures;
    }

    protected void method_f27da31f4c570cd76213(
            EntityDragon entityDragon, float f4, float f5, float f) {
        float f1 = (float) entityDragon.getMovementOffsets(7, f)[0];
        float f2 =
                (float)
                        (entityDragon.getMovementOffsets(5, f)[1]
                                - entityDragon.getMovementOffsets(10, f)[1]);
        GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f2 * 10.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, 1.0F);
        if (entityDragon.deathTime > 0) {
            float f3 = (entityDragon.deathTime + f - 1.0F) / 20.0F * 1.6F;
            f3 = MathHelper.sqrt(f3);
            if (f3 > 1.0F) {
                f3 = 1.0F;
            }

            GlStateManager.rotate(
                    f3 * this.getDeathMaxRotation(entityDragon), 0.0F, 0.0F, 1.0F);
        }
    }
}
