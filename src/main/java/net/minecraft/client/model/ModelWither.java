package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.MathHelper;

public class ModelWither extends ModelBase {
    private ModelRenderer[] aun;
    private ModelRenderer[] awU;

    public void setLivingAnimations(LivingEntity livingEntity, float f, float f1, float f2) {
        EntityWither entityWither = (EntityWither) livingEntity;
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        int i = 1;
        String[] astring = astring1;

        while (i < 3) {
            this.awU[i].rotateAngleY =
                    (entityWither.au(i - 1) - livingEntity.renderYawOffset)
                            / (180.0F / (float) Math.PI);
            this.awU[i].rotateAngleX = entityWither.aL(i - 1) / (180.0F / (float) Math.PI);
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public ModelWither(float p171_0) {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.aun = new net.minecraft.client.model.ModelRenderer[3];
        this.aun[0] = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
        this.aun[0].addBox(-10.0F, 3.9F, -0.5F, 20, 3, 3, p171_0);
        this.aun[1] =
                new net.minecraft.client.model.ModelRenderer(this).setTextureSize(this.textureWidth, this.textureHeight);
        this.aun[1].setRotationPoint(-2.0F, 6.9F, -0.5F);
        this.aun[1].setTextureOffset(0, 22).addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, p171_0);
        this.aun[1].setTextureOffset(24, 22).addBox(-4.0F, 1.5F, 0.5F, 11, 2, 2, p171_0);
        this.aun[1].setTextureOffset(24, 22).addBox(-4.0F, 4.0F, 0.5F, 11, 2, 2, p171_0);
        this.aun[1].setTextureOffset(24, 22).addBox(-4.0F, 6.5F, 0.5F, 11, 2, 2, p171_0);
        this.aun[2] = new net.minecraft.client.model.ModelRenderer(this, 12, 22);
        this.aun[2].addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, p171_0);
        this.awU = new net.minecraft.client.model.ModelRenderer[3];
        this.awU[0] = new net.minecraft.client.model.ModelRenderer(this, 0, 0);
        this.awU[0].addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, p171_0);
        this.awU[1] = new net.minecraft.client.model.ModelRenderer(this, 32, 0);
        this.awU[1].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, p171_0);
        this.awU[1].rotationPointX = -8.0F;
        this.awU[1].rotationPointY = 4.0F;
        this.awU[2] = new net.minecraft.client.model.ModelRenderer(this, 32, 0);
        this.awU[2].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, p171_0);
        this.awU[2].rotationPointX = 10.0F;
        this.awU[2].rotationPointY = 4.0F;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        ModelRenderer[] awUValue = this.awU;
        int length = awUValue.length;
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        int j = 0;
        String[] astring = astring1;

        while (j < length) {
            ModelRenderer modelRenderer = awUValue[j];
            modelRenderer.render(f5);
            j++;
            if (astring != null) {
                break;
            }
        }

        for (ModelRenderer modelRendererValue : this.aun) {
            modelRendererValue.render(f5);
            if (astring != null) {
                break;
            }
        }
    }

    public void setRotationAngles(
            float f4, float f5, float f, float f1, float f2, float f6, Entity entity) {
        float f3 = MathHelper.cos(f * 0.1F);
        this.aun[1].rotateAngleX = (0.065F + 0.05F * f3) * (float) Math.PI;
        this.aun[2].setRotationPoint(
                -2.0F,
                6.9F + MathHelper.cos(this.aun[1].rotateAngleX) * 10.0F,
                -0.5F + MathHelper.sin(this.aun[1].rotateAngleX) * 10.0F);
        this.aun[2].rotateAngleX = (0.265F + 0.1F * f3) * (float) Math.PI;
        this.awU[0].rotateAngleY = f1 / (180.0F / (float) Math.PI);
        this.awU[0].rotateAngleX = f2 / (180.0F / (float) Math.PI);
    }
}
