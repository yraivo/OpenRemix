package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.MathHelper;

public class ModelWolf extends ModelBase {
    public ModelRenderer wolfLeg3;
    public ModelRenderer wolfLeg4;
    public ModelRenderer wolfBody;
    public ModelRenderer wolfLeg1;
    ModelRenderer wolfTail;
    public ModelRenderer wolfHeadMain;
    ModelRenderer wolfMane;
    public ModelRenderer wolfLeg2;

    public void setLivingAnimations(LivingEntity livingEntity, float f, float f1, float f2) {
        String[] astring;
        EntityWolf entityWolf;
        {
            String[] astring1 = ModelBase.getLegacyModelFlowMarker();
            entityWolf = (EntityWolf) livingEntity;
            astring = astring1;
            if (entityWolf.isAngry()) {
                this.wolfTail.rotateAngleY = 0.0F;
                if (!(astring == null)) {

                    this.wolfTail.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
                }
            } else {

                this.wolfTail.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
            }
        }

        label14:
        {
            if (entityWolf.isSitting()) {
                this.wolfMane.setRotationPoint(-1.0F, 16.0F, -3.0F);
                this.wolfMane.rotateAngleX = (float) (Math.PI * 2.0 / 5.0);
                this.wolfMane.rotateAngleY = 0.0F;
                this.wolfBody.setRotationPoint(0.0F, 18.0F, 0.0F);
                this.wolfBody.rotateAngleX = (float) (Math.PI / 4);
                this.wolfTail.setRotationPoint(-1.0F, 21.0F, 6.0F);
                this.wolfLeg1.setRotationPoint(-2.5F, 22.0F, 2.0F);
                this.wolfLeg1.rotateAngleX = (float) (Math.PI * 3.0 / 2.0);
                this.wolfLeg2.setRotationPoint(0.5F, 22.0F, 2.0F);
                this.wolfLeg2.rotateAngleX = (float) (Math.PI * 3.0 / 2.0);
                this.wolfLeg3.rotateAngleX = 5.811947F;
                this.wolfLeg3.setRotationPoint(-2.49F, 17.0F, -4.0F);
                this.wolfLeg4.rotateAngleX = 5.811947F;
                this.wolfLeg4.setRotationPoint(0.51F, 17.0F, -4.0F);
                if (astring == null) {
                    break label14;
                }
            }

            this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
            this.wolfBody.rotateAngleX = (float) (Math.PI / 2);
            this.wolfMane.setRotationPoint(-1.0F, 14.0F, -3.0F);
            this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
            this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
            this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
            this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
            this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
            this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
            this.wolfLeg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
            this.wolfLeg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
            this.wolfLeg3.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
            this.wolfLeg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        }

        this.wolfHeadMain.rotateAngleZ =
                entityWolf.getInterestedAngle(f2)
                        + entityWolf.getShakeAngle(f2, 0.0F);
        this.wolfMane.rotateAngleZ = entityWolf.getShakeAngle(f2, -0.08F);
        this.wolfBody.rotateAngleZ = entityWolf.getShakeAngle(f2, -0.16F);
        this.wolfTail.rotateAngleZ = entityWolf.getShakeAngle(f2, -0.2F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        super.render(entity, f, f1, f2, f3, f4, f5);
        String[] astring = astring1;
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        if (this.isChild) {
            float f6 = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 5.0F * f5, 2.0F * f5);
            this.wolfHeadMain.renderWithRotation(f5);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
            GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
            this.wolfBody.render(f5);
            this.wolfLeg1.render(f5);
            this.wolfLeg2.render(f5);
            this.wolfLeg3.render(f5);
            this.wolfLeg4.render(f5);
            this.wolfTail.renderWithRotation(f5);
            this.wolfMane.render(f5);
            GlStateManager.popMatrix();
            if (astring == null) {
                return;
            }
        }

        this.wolfHeadMain.renderWithRotation(f5);
        this.wolfBody.render(f5);
        this.wolfLeg1.render(f5);
        this.wolfLeg2.render(f5);
        this.wolfLeg3.render(f5);
        this.wolfLeg4.render(f5);
        this.wolfTail.renderWithRotation(f5);
        this.wolfMane.render(f5);
    }

    public void setRotationAngles(
            float p171_0,
            float p171_1,
            float rotateAngleX,
            float p171_3,
            float p171_4,
            float p171_5,
            Entity entity) {
        super.setRotationAngles(p171_0, p171_1, rotateAngleX, p171_3, p171_4, p171_5, entity);
        this.wolfHeadMain.rotateAngleX = p171_4 / (180.0F / (float) java.lang.Math.PI);
        this.wolfHeadMain.rotateAngleY = p171_3 / (180.0F / (float) java.lang.Math.PI);
        this.wolfTail.rotateAngleX = rotateAngleX;
    }

    public ModelWolf() {
        float f = 0.0F;
        float f1 = 13.5F;
        this.wolfHeadMain = new net.minecraft.client.model.ModelRenderer(this, 0, 0);
        this.wolfHeadMain.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 4, f);
        this.wolfHeadMain.setRotationPoint(-1.0F, f1, -7.0F);
        this.wolfBody = new net.minecraft.client.model.ModelRenderer(this, 18, 14);
        this.wolfBody.addBox(-4.0F, -2.0F, -3.0F, 6, 9, 6, f);
        this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
        this.wolfMane = new net.minecraft.client.model.ModelRenderer(this, 21, 0);
        this.wolfMane.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 7, f);
        this.wolfMane.setRotationPoint(-1.0F, 14.0F, 2.0F);
        this.wolfLeg1 = new net.minecraft.client.model.ModelRenderer(this, 0, 18);
        this.wolfLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
        this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
        this.wolfLeg2 = new net.minecraft.client.model.ModelRenderer(this, 0, 18);
        this.wolfLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
        this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
        this.wolfLeg3 = new net.minecraft.client.model.ModelRenderer(this, 0, 18);
        this.wolfLeg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
        this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
        this.wolfLeg4 = new net.minecraft.client.model.ModelRenderer(this, 0, 18);
        this.wolfLeg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
        this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
        this.wolfTail = new net.minecraft.client.model.ModelRenderer(this, 9, 18);
        this.wolfTail.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
        this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
        this.wolfHeadMain.setTextureOffset(16, 14).addBox(-3.0F, -5.0F, 0.0F, 2, 2, 1, f);
        this.wolfHeadMain.setTextureOffset(16, 14).addBox(1.0F, -5.0F, 0.0F, 2, 2, 1, f);
        this.wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5F, 0.0F, -5.0F, 3, 3, 4, f);
    }
}
