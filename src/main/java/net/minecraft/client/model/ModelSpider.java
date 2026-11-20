package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSpider extends ModelBase {
    public ModelRenderer spiderLeg6;
    public ModelRenderer spiderLeg7;
    public ModelRenderer spiderLeg2;
    public ModelRenderer spiderLeg1;
    public ModelRenderer spiderLeg3;
    public ModelRenderer spiderNeck;
    public ModelRenderer spiderLeg4;
    public ModelRenderer spiderLeg5;
    public ModelRenderer spiderHead;
    public ModelRenderer spiderLeg8;
    public ModelRenderer spiderBody;

    public void setRotationAngles(
            float f, float f1, float f15, float f2, float f3, float f16, Entity entity) {
        this.spiderHead.rotateAngleY = f2 / (180.0F / (float) Math.PI);
        this.spiderHead.rotateAngleX = f3 / (180.0F / (float) Math.PI);
        float rotateAngleZ = (float) (Math.PI / 4);
        this.spiderLeg1.rotateAngleZ = -rotateAngleZ;
        this.spiderLeg2.rotateAngleZ = rotateAngleZ;
        this.spiderLeg3.rotateAngleZ = -rotateAngleZ * 0.74F;
        this.spiderLeg4.rotateAngleZ = rotateAngleZ * 0.74F;
        this.spiderLeg5.rotateAngleZ = -rotateAngleZ * 0.74F;
        this.spiderLeg6.rotateAngleZ = rotateAngleZ * 0.74F;
        this.spiderLeg7.rotateAngleZ = -rotateAngleZ;
        this.spiderLeg8.rotateAngleZ = rotateAngleZ;
        float f5 = -0.0F;
        float f6 = (float) (Math.PI / 8);
        this.spiderLeg1.rotateAngleY = f6 * 2.0F + f5;
        this.spiderLeg2.rotateAngleY = -f6 * 2.0F - f5;
        this.spiderLeg3.rotateAngleY = f6 * 1.0F + f5;
        this.spiderLeg4.rotateAngleY = -f6 * 1.0F - f5;
        this.spiderLeg5.rotateAngleY = -f6 * 1.0F + f5;
        this.spiderLeg6.rotateAngleY = f6 * 1.0F - f5;
        this.spiderLeg7.rotateAngleY = -f6 * 2.0F + f5;
        this.spiderLeg8.rotateAngleY = f6 * 2.0F - f5;
        float f7 = -(MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.4F) * f1;
        float f8 = -(MathHelper.cos(f * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * f1;
        float f9 = -(MathHelper.cos(f * 0.6662F * 2.0F + (float) (Math.PI / 2)) * 0.4F) * f1;
        float f10 =
                -(MathHelper.cos(f * 0.6662F * 2.0F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * f1;
        float f11 = Math.abs(MathHelper.sin(f * 0.6662F + 0.0F) * 0.4F) * f1;
        float f12 = Math.abs(MathHelper.sin(f * 0.6662F + (float) Math.PI) * 0.4F) * f1;
        float f13 = Math.abs(MathHelper.sin(f * 0.6662F + (float) (Math.PI / 2)) * 0.4F) * f1;
        float f14 =
                Math.abs(MathHelper.sin(f * 0.6662F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * f1;
        this.spiderLeg1.rotateAngleY += f7;
        this.spiderLeg2.rotateAngleY += -f7;
        this.spiderLeg3.rotateAngleY += f8;
        this.spiderLeg4.rotateAngleY += -f8;
        this.spiderLeg5.rotateAngleY += f9;
        this.spiderLeg6.rotateAngleY += -f9;
        this.spiderLeg7.rotateAngleY += f10;
        this.spiderLeg8.rotateAngleY += -f10;
        this.spiderLeg1.rotateAngleZ += f11;
        this.spiderLeg2.rotateAngleZ += -f11;
        this.spiderLeg3.rotateAngleZ += f12;
        this.spiderLeg4.rotateAngleZ += -f12;
        this.spiderLeg5.rotateAngleZ += f13;
        this.spiderLeg6.rotateAngleZ += -f13;
        this.spiderLeg7.rotateAngleZ += f14;
        this.spiderLeg8.rotateAngleZ += -f14;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.spiderHead.render(f5);
        this.spiderNeck.render(f5);
        this.spiderBody.render(f5);
        this.spiderLeg1.render(f5);
        this.spiderLeg2.render(f5);
        this.spiderLeg3.render(f5);
        this.spiderLeg4.render(f5);
        this.spiderLeg5.render(f5);
        this.spiderLeg6.render(f5);
        this.spiderLeg7.render(f5);
        this.spiderLeg8.render(f5);
    }

    public ModelSpider() {
        float f = 0.0F;
        byte b0 = 15;
        this.spiderHead = new ModelRenderer(this, 32, 4);
        this.spiderHead.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, f);
        this.spiderHead.setRotationPoint(0.0F, b0, -3.0F);
        this.spiderNeck = new ModelRenderer(this, 0, 0);
        this.spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, f);
        this.spiderNeck.setRotationPoint(0.0F, b0, 0.0F);
        this.spiderBody = new ModelRenderer(this, 0, 12);
        this.spiderBody.addBox(-5.0F, -4.0F, -6.0F, 10, 8, 12, f);
        this.spiderBody.setRotationPoint(0.0F, b0, 9.0F);
        this.spiderLeg1 = new ModelRenderer(this, 18, 0);
        this.spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
        this.spiderLeg1.setRotationPoint(-4.0F, b0, 2.0F);
        this.spiderLeg2 = new ModelRenderer(this, 18, 0);
        this.spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
        this.spiderLeg2.setRotationPoint(4.0F, b0, 2.0F);
        this.spiderLeg3 = new ModelRenderer(this, 18, 0);
        this.spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
        this.spiderLeg3.setRotationPoint(-4.0F, b0, 1.0F);
        this.spiderLeg4 = new ModelRenderer(this, 18, 0);
        this.spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
        this.spiderLeg4.setRotationPoint(4.0F, b0, 1.0F);
        this.spiderLeg5 = new ModelRenderer(this, 18, 0);
        this.spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
        this.spiderLeg5.setRotationPoint(-4.0F, b0, 0.0F);
        this.spiderLeg6 = new ModelRenderer(this, 18, 0);
        this.spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
        this.spiderLeg6.setRotationPoint(4.0F, b0, 0.0F);
        this.spiderLeg7 = new ModelRenderer(this, 18, 0);
        this.spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
        this.spiderLeg7.setRotationPoint(-4.0F, b0, -1.0F);
        this.spiderLeg8 = new ModelRenderer(this, 18, 0);
        this.spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
        this.spiderLeg8.setRotationPoint(4.0F, b0, -1.0F);
    }
}
