package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.ESPModule;

public class ModelPlayer extends ModelBiped {
    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedBodyWear;
    public ModelRenderer bipedLeftLegwear;
    private ModelRenderer bipedDeadmau5Head;
    public ModelRenderer bipedRightArmwear;
    private boolean smallArms;
    public ModelRenderer bipedRightLegwear;
    private ModelRenderer bipedCape;

    public void render(
            Entity entity,
            float limbSwing,
            float limbAmount,
            float ageInTicks,
            float headYaw,
            float headPitch,
            float scale) {
        boolean legacyFlow = ModelBase.getLegacyModelFlowMarker() != null;
        super.render(entity, limbSwing, limbAmount, ageInTicks, headYaw, headPitch, scale);
        GlStateManager.pushMatrix();
        boolean child = this.isChild;
        if (child) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.renderClothing(scale);
        }
        if (!child || legacyFlow) {
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            this.renderClothing(scale);
        }
        GlStateManager.popMatrix();
    }

    private void renderClothing(float scale) {
        this.bipedLeftLegwear.render(scale);
        this.bipedRightLegwear.render(scale);
        this.bipedLeftArmwear.render(scale);
        this.bipedRightArmwear.render(scale);
        this.bipedBodyWear.render(scale);
    }

    public void renderDeadmau5Head(float p171_0) {
        net.minecraft.client.model.ModelBase.copyModelAngles(
                this.bipedHead, this.bipedDeadmau5Head);
        this.bipedDeadmau5Head.rotationPointX = 0.0F;
        this.bipedDeadmau5Head.rotationPointY = 0.0F;
        this.bipedDeadmau5Head.render(p171_0);
    }

    public ModelPlayer(float f, boolean flag) {
        this(f, flag, ModelBase.getLegacyModelFlowMarker());
    }

    private ModelPlayer(float modelScale, boolean slimArms, String[] initialFlowMarker) {
        super(modelScale, 0.0F, 64, 64);

        this.smallArms = slimArms;
        this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
        this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, modelScale);
        this.bipedCape = new ModelRenderer(this, 0, 0);
        this.bipedCape.setTextureSize(64, 32);
        this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, modelScale);
        if (slimArms) {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelScale);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.bipedRightArm = new ModelRenderer(this, 40, 16);
            this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelScale);
            this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelScale + 0.25F);
            this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelScale + 0.25F);
            this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
        }

        if (!slimArms || initialFlowMarker != null) {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelScale);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelScale + 0.25F);
            this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelScale + 0.25F);
            this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
        }

        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelScale);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
        this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelScale + 0.25F);
        this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
        this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelScale + 0.25F);
        this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.bipedBodyWear = new ModelRenderer(this, 16, 32);
        this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelScale + 0.25F);
        this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    public void postRenderHiddenArm(float f) {
        String[] astring = ModelBase.getLegacyModelFlowMarker();
        if (this.smallArms) {
            this.bipedRightArm.rotationPointX++;
            this.bipedRightArm.postRender(f);
            this.bipedRightArm.rotationPointX--;
            if (astring == null) {
                return;
            }
        }

        this.bipedRightArm.postRender(f);
    }

    public void renderRightArm() {
        this.bipedRightArm.render(0.0625F);
        this.bipedRightArmwear.render(0.0625F);
    }

    public void setAllPartsVisible(boolean showModel) {
        super.setAllPartsVisible(showModel);
        this.bipedLeftArmwear.showModel = showModel;
        this.bipedRightArmwear.showModel = showModel;
        this.bipedLeftLegwear.showModel = showModel;
        this.bipedRightLegwear.showModel = showModel;
        this.bipedBodyWear.showModel = showModel;
        this.bipedCape.showModel = showModel;
        this.bipedDeadmau5Head.showModel = showModel;
    }

    public void renderLeftArm() {
        this.bipedLeftArm.render(0.0625F);
        this.bipedLeftArmwear.render(0.0625F);
    }

    public void renderCape(float p171_0) {
        this.bipedCape.render(p171_0);
    }

    public void setRotationAngles(
            float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
        copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
        copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
        copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
        copyModelAngles(this.bipedBody, this.bipedBodyWear);
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        ESPModule esp = ClientServices.moduleManager.esp;
        if (esp.isEnabled()) {
            esp.captureModelRotations((PlayerEntity) entity, this);
        }
    }
}
