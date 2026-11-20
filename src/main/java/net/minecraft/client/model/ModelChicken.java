package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelChicken extends ModelBase {
    public ModelRenderer leftWing;
    public ModelRenderer leftLeg;
    public ModelRenderer body;
    public ModelRenderer bill;
    public ModelRenderer chin;
    public ModelRenderer head;
    public ModelRenderer rightWing;
    public ModelRenderer rightLeg;

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        String[] astring = astring1;
        if (this.isChild) {
            float f6 = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 5.0F * f5, 2.0F * f5);
            this.head.render(f5);
            this.bill.render(f5);
            this.chin.render(f5);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
            GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
            this.body.render(f5);
            this.rightLeg.render(f5);
            this.leftLeg.render(f5);
            this.rightWing.render(f5);
            this.leftWing.render(f5);
            GlStateManager.popMatrix();
            if (astring == null) {
                return;
            }
        }

        this.head.render(f5);
        this.bill.render(f5);
        this.chin.render(f5);
        this.body.render(f5);
        this.rightLeg.render(f5);
        this.leftLeg.render(f5);
        this.rightWing.render(f5);
        this.leftWing.render(f5);
    }

    public void setRotationAngles(
            float f, float f1, float rotateAngleZ, float f3, float f4, float f5, Entity entity) {
        this.head.rotateAngleX = f4 / (180.0F / (float) Math.PI);
        this.head.rotateAngleY = f3 / (180.0F / (float) Math.PI);
        this.bill.rotateAngleX = this.head.rotateAngleX;
        this.bill.rotateAngleY = this.head.rotateAngleY;
        this.chin.rotateAngleX = this.head.rotateAngleX;
        this.chin.rotateAngleY = this.head.rotateAngleY;
        this.body.rotateAngleX = (float) (Math.PI / 2);
        this.rightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.leftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
        this.rightWing.rotateAngleZ = rotateAngleZ;
        this.leftWing.rotateAngleZ = -rotateAngleZ;
    }

    public ModelChicken() {
        byte b0 = 16;
        this.head = new net.minecraft.client.model.ModelRenderer(this, 0, 0);
        this.head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
        this.head.setRotationPoint(0.0F, -1 + b0, -4.0F);
        this.bill = new net.minecraft.client.model.ModelRenderer(this, 14, 0);
        this.bill.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
        this.bill.setRotationPoint(0.0F, -1 + b0, -4.0F);
        this.chin = new net.minecraft.client.model.ModelRenderer(this, 14, 4);
        this.chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
        this.chin.setRotationPoint(0.0F, -1 + b0, -4.0F);
        this.body = new net.minecraft.client.model.ModelRenderer(this, 0, 9);
        this.body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
        this.body.setRotationPoint(0.0F, b0, 0.0F);
        this.rightLeg = new net.minecraft.client.model.ModelRenderer(this, 26, 0);
        this.rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
        this.rightLeg.setRotationPoint(-2.0F, 3 + b0, 1.0F);
        this.leftLeg = new net.minecraft.client.model.ModelRenderer(this, 26, 0);
        this.leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
        this.leftLeg.setRotationPoint(1.0F, 3 + b0, 1.0F);
        this.rightWing = new net.minecraft.client.model.ModelRenderer(this, 24, 13);
        this.rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
        this.rightWing.setRotationPoint(-4.0F, -3 + b0, 0.0F);
        this.leftWing = new net.minecraft.client.model.ModelRenderer(this, 24, 13);
        this.leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
        this.leftWing.setRotationPoint(4.0F, -3 + b0, 0.0F);
    }
}
