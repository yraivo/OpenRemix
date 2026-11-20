package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBiped extends ModelBase {
    public ModelRenderer bipedBody;
    public ModelRenderer bipedHead;
    public ModelRenderer bipedRightLeg;
    public boolean isSneak;
    public ModelRenderer bipedHeadwear;
    public boolean aimedBow;
    public int heldItemLeft;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightArm;
    public int heldItemRight;
    public ModelRenderer bipedLeftLeg;

    public ModelBiped(float p171_0) {
        this(p171_0, 0.0F, 64, 32);
    }

    public void setAllPartsVisible(boolean showModel) {
        this.bipedHead.showModel = showModel;
        this.bipedHeadwear.showModel = showModel;
        this.bipedBody.showModel = showModel;
        this.bipedRightArm.showModel = showModel;
        this.bipedLeftArm.showModel = showModel;
        this.bipedRightLeg.showModel = showModel;
        this.bipedLeftLeg.showModel = showModel;
    }

    public void setModelAttributes(ModelBase modelBase) {
        super.setModelAttributes(modelBase);
        if (modelBase instanceof net.minecraft.client.model.ModelBiped) {
            net.minecraft.client.model.ModelBiped modelBiped = (net.minecraft.client.model.ModelBiped) modelBase;
            this.heldItemLeft = modelBiped.heldItemLeft;
            this.heldItemRight = modelBiped.heldItemRight;
            this.isSneak = modelBiped.isSneak;
            this.aimedBow = modelBiped.aimedBow;
        }
    }

    public ModelBiped(float p171_0, float p171_1, int textureWidthValue, int textureHeightValue) {
        this.textureWidth = textureWidthValue;
        this.textureHeight = textureHeightValue;
        this.bipedHead = new net.minecraft.client.model.ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p171_0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + p171_1, 0.0F);
        this.bipedHeadwear = new net.minecraft.client.model.ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p171_0 + 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + p171_1, 0.0F);
        this.bipedBody = new net.minecraft.client.model.ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p171_0);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + p171_1, 0.0F);
        this.bipedRightArm = new net.minecraft.client.model.ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p171_0);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p171_1, 0.0F);
        this.bipedLeftArm = new net.minecraft.client.model.ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p171_0);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p171_1, 0.0F);
        this.bipedRightLeg = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p171_0);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + p171_1, 0.0F);
        this.bipedLeftLeg = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p171_0);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + p171_1, 0.0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        label17:
        {
            String[] astring1 = ModelBase.getLegacyModelFlowMarker();
            this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
            GlStateManager.pushMatrix();
            String[] astring = astring1;
            if (this.isChild) {
                float f6 = 2.0F;
                GlStateManager.scale(1.5F / f6, 1.5F / f6, 1.5F / f6);
                GlStateManager.translate(0.0F, 16.0F * f5, 0.0F);
                this.bipedHead.render(f5);
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
                GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
                this.bipedBody.render(f5);
                this.bipedRightArm.render(f5);
                this.bipedLeftArm.render(f5);
                this.bipedRightLeg.render(f5);
                this.bipedLeftLeg.render(f5);
                this.bipedHeadwear.render(f5);
                if (astring == null) {
                    break label17;
                }
            }

            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            this.bipedHead.render(f5);
            this.bipedBody.render(f5);
            this.bipedRightArm.render(f5);
            this.bipedLeftArm.render(f5);
            this.bipedRightLeg.render(f5);
            this.bipedLeftLeg.render(f5);
            this.bipedHeadwear.render(f5);
        }

        GlStateManager.popMatrix();
    }

    public void postRenderHiddenArm(float p171_0) {
        this.bipedRightArm.postRender(p171_0);
    }

    public void setRotationAngles(
            float f, float f1, float f2, float f3, float f4, float f10, Entity entity) {
        this.bipedHead.rotateAngleY = f3 / (180.0F / (float) Math.PI);
        this.bipedHead.rotateAngleX = f4 / (180.0F / (float) Math.PI);
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.bipedRightArm.rotateAngleX =
                MathHelper.cos(f * 0.6662F + (float) Math.PI) * 2.0F * f1 * 0.5F;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        String[] astring = astring1;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
        this.bipedRightLeg.rotateAngleY = 0.0F;
        this.bipedLeftLeg.rotateAngleY = 0.0F;
        if (this.isRiding) {
            this.bipedRightArm.rotateAngleX += (float) (-Math.PI / 5);
            this.bipedLeftArm.rotateAngleX += (float) (-Math.PI / 5);
            this.bipedRightLeg.rotateAngleX = (float) (-Math.PI * 2.0 / 5.0);
            this.bipedLeftLeg.rotateAngleX = (float) (-Math.PI * 2.0 / 5.0);
            this.bipedRightLeg.rotateAngleY = (float) (Math.PI / 10);
            this.bipedLeftLeg.rotateAngleY = (float) (-Math.PI / 10);
        }

        if (this.heldItemLeft != 0) {
            this.bipedLeftArm.rotateAngleX =
                    this.bipedLeftArm.rotateAngleX * 0.5F
                            - (float) (Math.PI / 10) * this.heldItemLeft;
        }

        this.bipedRightArm.rotateAngleY = 0.0F;
        this.bipedRightArm.rotateAngleZ = 0.0F;
        switch (this.heldItemRight) {
            case 0:
            case 2:
            default:
                if (astring == null) {
                    break;
                }
            case 1:
                this.bipedRightArm.rotateAngleX =
                        this.bipedRightArm.rotateAngleX * 0.5F
                                - (float) (Math.PI / 10) * this.heldItemRight;
                if (astring == null) {
                    break;
                }
            case 3:
                this.bipedRightArm.rotateAngleX =
                        this.bipedRightArm.rotateAngleX * 0.5F
                                - (float) (Math.PI / 10) * this.heldItemRight;
                this.bipedRightArm.rotateAngleY = (float) (-Math.PI / 6);
        }

        this.bipedLeftArm.rotateAngleY = 0.0F;
        if (this.swingProgress > -9990.0F) {
            float swingProgressValue = this.swingProgress;
            this.bipedBody.rotateAngleY =
                    MathHelper.sin(MathHelper.sqrt(swingProgressValue) * (float) Math.PI * 2.0F)
                            * 0.2F;
            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedRightArm.rotateAngleY =
                    this.bipedRightArm.rotateAngleY + this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleY =
                    this.bipedLeftArm.rotateAngleY + this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleX =
                    this.bipedLeftArm.rotateAngleX + this.bipedBody.rotateAngleY;
            swingProgressValue = 1.0F - this.swingProgress;
            swingProgressValue *= swingProgressValue;
            swingProgressValue *= swingProgressValue;
            swingProgressValue = 1.0F - swingProgressValue;
            float f6 = MathHelper.sin(swingProgressValue * (float) Math.PI);
            float f7 =
                    MathHelper.sin(this.swingProgress * (float) Math.PI)
                            * -(this.bipedHead.rotateAngleX - 0.7F)
                            * 0.75F;
            this.bipedRightArm.rotateAngleX =
                    (float) (this.bipedRightArm.rotateAngleX - (f6 * 1.2 + f7));
            this.bipedRightArm.rotateAngleY =
                    this.bipedRightArm.rotateAngleY + this.bipedBody.rotateAngleY * 2.0F;
            this.bipedRightArm.rotateAngleZ =
                    this.bipedRightArm.rotateAngleZ
                            + MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
        }

        label27:
        {
            if (this.isSneak) {
                this.bipedBody.rotateAngleX = 0.5F;
                this.bipedRightArm.rotateAngleX += 0.4F;
                this.bipedLeftArm.rotateAngleX += 0.4F;
                this.bipedRightLeg.rotationPointZ = 4.0F;
                this.bipedLeftLeg.rotationPointZ = 4.0F;
                this.bipedRightLeg.rotationPointY = 9.0F;
                this.bipedLeftLeg.rotationPointY = 9.0F;
                this.bipedHead.rotationPointY = 1.0F;
                if (astring == null) {
                    break label27;
                }
            }

            this.bipedBody.rotateAngleX = 0.0F;
            this.bipedRightLeg.rotationPointZ = 0.1F;
            this.bipedLeftLeg.rotationPointZ = 0.1F;
            this.bipedRightLeg.rotationPointY = 12.0F;
            this.bipedLeftLeg.rotationPointY = 12.0F;
            this.bipedHead.rotationPointY = 0.0F;
        }

        this.bipedRightArm.rotateAngleZ =
                this.bipedRightArm.rotateAngleZ + (MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F);
        this.bipedLeftArm.rotateAngleZ =
                this.bipedLeftArm.rotateAngleZ - (MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F);
        this.bipedRightArm.rotateAngleX =
                this.bipedRightArm.rotateAngleX + MathHelper.sin(f2 * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX =
                this.bipedLeftArm.rotateAngleX - MathHelper.sin(f2 * 0.067F) * 0.05F;
        if (this.aimedBow) {
            float f8 = 0.0F;
            float f9 = 0.0F;
            this.bipedRightArm.rotateAngleZ = 0.0F;
            this.bipedLeftArm.rotateAngleZ = 0.0F;
            this.bipedRightArm.rotateAngleY = -(0.1F - f8 * 0.6F) + this.bipedHead.rotateAngleY;
            this.bipedLeftArm.rotateAngleY = 0.1F - f8 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
            this.bipedRightArm.rotateAngleX = (float) (-Math.PI / 2) + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = (float) (-Math.PI / 2) + this.bipedHead.rotateAngleX;
            this.bipedRightArm.rotateAngleX -= f8 * 1.2F - f9 * 0.4F;
            this.bipedLeftArm.rotateAngleX -= f8 * 1.2F - f9 * 0.4F;
            this.bipedRightArm.rotateAngleZ =
                    this.bipedRightArm.rotateAngleZ + (MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F);
            this.bipedLeftArm.rotateAngleZ =
                    this.bipedLeftArm.rotateAngleZ - (MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F);
            this.bipedRightArm.rotateAngleX =
                    this.bipedRightArm.rotateAngleX + MathHelper.sin(f2 * 0.067F) * 0.05F;
            this.bipedLeftArm.rotateAngleX =
                    this.bipedLeftArm.rotateAngleX - MathHelper.sin(f2 * 0.067F) * 0.05F;
        }

        copyModelAngles(this.bipedHead, this.bipedHeadwear);
    }

    public ModelBiped() {
        this(0.0F);
    }
}
