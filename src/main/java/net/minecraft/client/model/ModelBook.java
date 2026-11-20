package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBook extends ModelBase {
    public ModelRenderer flippingPageRight;
    public ModelRenderer pagesLeft;
    public ModelRenderer flippingPageLeft;
    public ModelRenderer pagesRight;
    public ModelRenderer bookSpine;
    public ModelRenderer coverRight =
            new ModelRenderer(this).setTextureOffset(0, 0).addBox(-6.0F, -5.0F, 0.0F, 6, 10, 0);
    public ModelRenderer coverLeft =
            new ModelRenderer(this).setTextureOffset(16, 0).addBox(0.0F, -5.0F, 0.0F, 6, 10, 0);

    public ModelBook() {
        this.pagesRight =
                new ModelRenderer(this)
                        .setTextureOffset(0, 10)
                        .addBox(0.0F, -4.0F, -0.99F, 5, 8, 1);
        this.pagesLeft =
                new ModelRenderer(this)
                        .setTextureOffset(12, 10)
                        .addBox(0.0F, -4.0F, -0.01F, 5, 8, 1);
        this.flippingPageRight =
                new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0F, -4.0F, 0.0F, 5, 8, 0);
        this.flippingPageLeft =
                new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0F, -4.0F, 0.0F, 5, 8, 0);
        this.bookSpine =
                new ModelRenderer(this)
                        .setTextureOffset(12, 0)
                        .addBox(-1.0F, -5.0F, 0.0F, 2, 10, 0);
        this.coverRight.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.coverLeft.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.bookSpine.rotateAngleY = (float) (Math.PI / 2);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.coverRight.render(f5);
        this.coverLeft.render(f5);
        this.bookSpine.render(f5);
        this.pagesRight.render(f5);
        this.pagesLeft.render(f5);
        this.flippingPageRight.render(f5);
        this.flippingPageLeft.render(f5);
    }

    public void setRotationAngles(
            float f, float f1, float f2, float f3, float f5, float f6, Entity entity) {
        float rotateAngleY = (MathHelper.sin(f * 0.02F) * 0.1F + 1.25F) * f3;
        this.coverRight.rotateAngleY = (float) Math.PI + rotateAngleY;
        this.coverLeft.rotateAngleY = -rotateAngleY;
        this.pagesRight.rotateAngleY = rotateAngleY;
        this.pagesLeft.rotateAngleY = -rotateAngleY;
        this.flippingPageRight.rotateAngleY = rotateAngleY - rotateAngleY * 2.0F * f1;
        this.flippingPageLeft.rotateAngleY = rotateAngleY - rotateAngleY * 2.0F * f2;
        this.pagesRight.rotationPointX = MathHelper.sin(rotateAngleY);
        this.pagesLeft.rotationPointX = MathHelper.sin(rotateAngleY);
        this.flippingPageRight.rotationPointX = MathHelper.sin(rotateAngleY);
        this.flippingPageLeft.rotationPointX = MathHelper.sin(rotateAngleY);
    }
}
