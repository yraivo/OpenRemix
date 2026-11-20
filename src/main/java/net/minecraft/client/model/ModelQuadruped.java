package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelQuadruped extends ModelBase {
    public ModelRenderer leg2;
    public ModelRenderer leg1;
    public ModelRenderer leg4;
    public ModelRenderer leg3;
    public ModelRenderer head = new ModelRenderer(this, 0, 0);
    protected float childZOffset;
    public ModelRenderer body;
    protected float childYOffset = 8.0F;

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        String[] astring = astring1;
        if (this.isChild) {
            float f6 = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, this.childYOffset * f5, this.childZOffset * f5);
            this.head.render(f5);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
            GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
            this.body.render(f5);
            this.leg1.render(f5);
            this.leg2.render(f5);
            this.leg3.render(f5);
            this.leg4.render(f5);
            GlStateManager.popMatrix();
            if (astring == null) {
                return;
            }
        }

        this.head.render(f5);
        this.body.render(f5);
        this.leg1.render(f5);
        this.leg2.render(f5);
        this.leg3.render(f5);
        this.leg4.render(f5);
    }

    public ModelQuadruped(int p171_0, float p171_1) {
        this.childZOffset = 4.0F;
        this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, p171_1);
        this.head.setRotationPoint(0.0F, 18 - p171_0, -6.0F);
        this.body = new net.minecraft.client.model.ModelRenderer(this, 28, 8);
        this.body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, p171_1);
        this.body.setRotationPoint(0.0F, 17 - p171_0, 2.0F);
        this.leg1 = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, p171_0, 4, p171_1);
        this.leg1.setRotationPoint(-3.0F, 24 - p171_0, 7.0F);
        this.leg2 = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, p171_0, 4, p171_1);
        this.leg2.setRotationPoint(3.0F, 24 - p171_0, 7.0F);
        this.leg3 = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, p171_0, 4, p171_1);
        this.leg3.setRotationPoint(-3.0F, 24 - p171_0, -5.0F);
        this.leg4 = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
        this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, p171_0, 4, p171_1);
        this.leg4.setRotationPoint(3.0F, 24 - p171_0, -5.0F);
    }

    public void setRotationAngles(
            float f, float f1, float f5, float f2, float f3, float f6, Entity entity) {

        this.head.rotateAngleX = f3 / (180.0F / (float) Math.PI);
        this.head.rotateAngleY = f2 / (180.0F / (float) Math.PI);
        this.body.rotateAngleX = (float) (Math.PI / 2);
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
        this.leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }
}
