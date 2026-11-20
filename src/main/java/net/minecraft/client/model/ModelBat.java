package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.MathHelper;

public class ModelBat extends ModelBase {
    private ModelRenderer batRightWing;
    private ModelRenderer batOuterRightWing;
    private ModelRenderer batOuterLeftWing;
    private ModelRenderer batHead;
    private ModelRenderer batLeftWing;
    private ModelRenderer batBody;

    public ModelBat() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.batHead = new ModelRenderer(this, 0, 0);
        this.batHead.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
        ModelRenderer modelRenderer = new ModelRenderer(this, 24, 0);
        modelRenderer.addBox(-4.0F, -6.0F, -2.0F, 3, 4, 1);
        this.batHead.addChild(modelRenderer);
        ModelRenderer modelRendererValue = new ModelRenderer(this, 24, 0);
        modelRendererValue.mirror = true;
        modelRendererValue.addBox(1.0F, -6.0F, -2.0F, 3, 4, 1);
        this.batHead.addChild(modelRendererValue);
        this.batBody = new ModelRenderer(this, 0, 16);
        this.batBody.addBox(-3.0F, 4.0F, -3.0F, 6, 12, 6);
        this.batBody.setTextureOffset(0, 34).addBox(-5.0F, 16.0F, 0.0F, 10, 6, 1);
        this.batRightWing = new ModelRenderer(this, 42, 0);
        this.batRightWing.addBox(-12.0F, 1.0F, 1.5F, 10, 16, 1);
        this.batOuterRightWing = new ModelRenderer(this, 24, 16);
        this.batOuterRightWing.setRotationPoint(-12.0F, 1.0F, 1.5F);
        this.batOuterRightWing.addBox(-8.0F, 1.0F, 0.0F, 8, 12, 1);
        this.batLeftWing = new ModelRenderer(this, 42, 0);
        this.batLeftWing.mirror = true;
        this.batLeftWing.addBox(2.0F, 1.0F, 1.5F, 10, 16, 1);
        this.batOuterLeftWing = new ModelRenderer(this, 24, 16);
        this.batOuterLeftWing.mirror = true;
        this.batOuterLeftWing.setRotationPoint(12.0F, 1.0F, 1.5F);
        this.batOuterLeftWing.addBox(0.0F, 1.0F, 0.0F, 8, 12, 1);
        this.batBody.addChild(this.batRightWing);
        this.batBody.addChild(this.batLeftWing);
        this.batRightWing.addChild(this.batOuterRightWing);
        this.batLeftWing.addChild(this.batOuterLeftWing);
    }

    public void setRotationAngles(
            float f5, float f6, float f, float f1, float f2, float f7, Entity entity) {
        String[] astring = ModelBase.getLegacyModelFlowMarker();
        if (((BatEntity) entity).getIsBatHanging()) {

            this.batHead.rotateAngleX = f2 / (180.0F / (float) Math.PI);
            this.batHead.rotateAngleY = (float) Math.PI - f1 / (180.0F / (float) Math.PI);
            this.batHead.rotateAngleZ = (float) Math.PI;
            this.batHead.setRotationPoint(0.0F, -2.0F, 0.0F);
            this.batRightWing.setRotationPoint(-3.0F, 0.0F, 3.0F);
            this.batLeftWing.setRotationPoint(3.0F, 0.0F, 3.0F);
            this.batBody.rotateAngleX = (float) Math.PI;
            this.batRightWing.rotateAngleX = (float) (-Math.PI / 20);
            this.batRightWing.rotateAngleY = (float) (-Math.PI * 2.0 / 5.0);
            this.batOuterRightWing.rotateAngleY = -1.7278761F;
            this.batLeftWing.rotateAngleX = this.batRightWing.rotateAngleX;
            this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
            this.batOuterLeftWing.rotateAngleY = -this.batOuterRightWing.rotateAngleY;
            if (astring == null) {
                return;
            }
        }

        this.batHead.rotateAngleX = f2 / (180.0F / (float) Math.PI);
        this.batHead.rotateAngleY = f1 / (180.0F / (float) Math.PI);
        this.batHead.rotateAngleZ = 0.0F;
        this.batHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.batRightWing.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.batLeftWing.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.batBody.rotateAngleX = (float) (Math.PI / 4) + MathHelper.cos(f * 0.1F) * 0.15F;
        this.batBody.rotateAngleY = 0.0F;
        this.batRightWing.rotateAngleY = MathHelper.cos(f * 1.3F) * (float) Math.PI * 0.25F;
        this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
        this.batOuterRightWing.rotateAngleY = this.batRightWing.rotateAngleY * 0.5F;
        this.batOuterLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY * 0.5F;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.batHead.render(f5);
        this.batBody.render(f5);
    }
}
