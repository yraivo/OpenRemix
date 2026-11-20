package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.MathHelper;

public class ModelRabbit extends ModelBase {
    ModelRenderer rabbitLeftEar;
    ModelRenderer rabbitLeftArm;
    ModelRenderer rabbitRightFoot;
    ModelRenderer rabbitRightEar;
    private float ajn = 0.0F;
    ModelRenderer rabbitTail;

    ModelRenderer rabbitNose;
    ModelRenderer rabbitBody;
    ModelRenderer rabbitHead;
    ModelRenderer rabbitRightArm;
    ModelRenderer rabbitLeftThigh;

    ModelRenderer rabbitRightThigh;

    ModelRenderer rabbitLeftFoot;
    private float awp = 0.0F;

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        String[] astring = astring1;
        if (this.isChild) {
            float f6 = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 5.0F * f5, 2.0F * f5);
            this.rabbitHead.render(f5);
            this.rabbitLeftEar.render(f5);
            this.rabbitRightEar.render(f5);
            this.rabbitNose.render(f5);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
            GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
            this.rabbitLeftFoot.render(f5);
            this.rabbitRightFoot.render(f5);
            this.rabbitLeftThigh.render(f5);
            this.rabbitRightThigh.render(f5);
            this.rabbitBody.render(f5);
            this.rabbitLeftArm.render(f5);
            this.rabbitRightArm.render(f5);
            this.rabbitTail.render(f5);
            GlStateManager.popMatrix();
            if (astring == null) {
                return;
            }
        }

        this.rabbitLeftFoot.render(f5);
        this.rabbitRightFoot.render(f5);
        this.rabbitLeftThigh.render(f5);
        this.rabbitRightThigh.render(f5);
        this.rabbitBody.render(f5);
        this.rabbitLeftArm.render(f5);
        this.rabbitRightArm.render(f5);
        this.rabbitHead.render(f5);
        this.rabbitRightEar.render(f5);
        this.rabbitLeftEar.render(f5);
        this.rabbitTail.render(f5);
        this.rabbitNose.render(f5);
    }

    public void setRotationAngles(
            float f4, float f5, float f, float f1, float f2, float f6, Entity entity) {
        float f3 = f - entity.ticksExisted;
        EntityRabbit entityRabbit = (EntityRabbit) entity;
        this.rabbitNose.rotateAngleX =
                this.rabbitHead.rotateAngleX =
                        this.rabbitRightEar.rotateAngleX =
                                this.rabbitLeftEar.rotateAngleX = f2 * (float) (Math.PI / 180.0);
        this.rabbitNose.rotateAngleY =
                this.rabbitHead.rotateAngleY = f1 * (float) (Math.PI / 180.0);
        this.rabbitRightEar.rotateAngleY = this.rabbitNose.rotateAngleY - (float) (Math.PI / 12);
        this.rabbitLeftEar.rotateAngleY = this.rabbitNose.rotateAngleY + (float) (Math.PI / 12);
        this.ajn = MathHelper.sin(entityRabbit.method_ad4859278b6f26197223(f3) * (float) Math.PI);
        this.rabbitLeftThigh.rotateAngleX =
                this.rabbitRightThigh.rotateAngleX =
                        (this.ajn * 50.0F - 21.0F) * (float) (Math.PI / 180.0);
        this.rabbitLeftFoot.rotateAngleX =
                this.rabbitRightFoot.rotateAngleX = this.ajn * 50.0F * (float) (Math.PI / 180.0);
        this.rabbitLeftArm.rotateAngleX =
                this.rabbitRightArm.rotateAngleX =
                        (this.ajn * -40.0F - 11.0F) * (float) (Math.PI / 180.0);
    }

    private void setRotationOffset(
            ModelRenderer modelRenderer,
            float rotateAngleX,
            float rotateAngleY,
            float rotateAngleZ) {
        modelRenderer.rotateAngleX = rotateAngleX;
        modelRenderer.rotateAngleY = rotateAngleY;
        modelRenderer.rotateAngleZ = rotateAngleZ;
    }

    public ModelRabbit() {
        this.setTextureOffset("head.main", 0, 0);
        this.setTextureOffset("head.nose", 0, 24);
        this.setTextureOffset("head.ear1", 0, 10);
        this.setTextureOffset("head.ear2", 6, 10);
        this.rabbitLeftFoot = new ModelRenderer(this, 26, 24);
        this.rabbitLeftFoot.addBox(-1.0F, 5.5F, -3.7F, 2, 1, 7);
        this.rabbitLeftFoot.setRotationPoint(3.0F, 17.5F, 3.7F);
        this.rabbitLeftFoot.mirror = true;
        this.setRotationOffset(this.rabbitLeftFoot, 0.0F, 0.0F, 0.0F);
        this.rabbitRightFoot = new ModelRenderer(this, 8, 24);
        this.rabbitRightFoot.addBox(-1.0F, 5.5F, -3.7F, 2, 1, 7);
        this.rabbitRightFoot.setRotationPoint(-3.0F, 17.5F, 3.7F);
        this.rabbitRightFoot.mirror = true;
        this.setRotationOffset(this.rabbitRightFoot, 0.0F, 0.0F, 0.0F);
        this.rabbitLeftThigh = new ModelRenderer(this, 30, 15);
        this.rabbitLeftThigh.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 5);
        this.rabbitLeftThigh.setRotationPoint(3.0F, 17.5F, 3.7F);
        this.rabbitLeftThigh.mirror = true;
        this.setRotationOffset(this.rabbitLeftThigh, (float) (-Math.PI / 9), 0.0F, 0.0F);
        this.rabbitRightThigh = new ModelRenderer(this, 16, 15);
        this.rabbitRightThigh.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 5);
        this.rabbitRightThigh.setRotationPoint(-3.0F, 17.5F, 3.7F);
        this.rabbitRightThigh.mirror = true;
        this.setRotationOffset(this.rabbitRightThigh, (float) (-Math.PI / 9), 0.0F, 0.0F);
        this.rabbitBody = new ModelRenderer(this, 0, 0);
        this.rabbitBody.addBox(-3.0F, -2.0F, -10.0F, 6, 5, 10);
        this.rabbitBody.setRotationPoint(0.0F, 19.0F, 8.0F);
        this.rabbitBody.mirror = true;
        this.setRotationOffset(this.rabbitBody, (float) (-Math.PI / 9), 0.0F, 0.0F);
        this.rabbitLeftArm = new ModelRenderer(this, 8, 15);
        this.rabbitLeftArm.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2);
        this.rabbitLeftArm.setRotationPoint(3.0F, 17.0F, -1.0F);
        this.rabbitLeftArm.mirror = true;
        this.setRotationOffset(this.rabbitLeftArm, (float) (-Math.PI / 18), 0.0F, 0.0F);
        this.rabbitRightArm = new ModelRenderer(this, 0, 15);
        this.rabbitRightArm.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2);
        this.rabbitRightArm.setRotationPoint(-3.0F, 17.0F, -1.0F);
        this.rabbitRightArm.mirror = true;
        this.setRotationOffset(this.rabbitRightArm, (float) (-Math.PI / 18), 0.0F, 0.0F);
        this.rabbitHead = new ModelRenderer(this, 32, 0);
        this.rabbitHead.addBox(-2.5F, -4.0F, -5.0F, 5, 4, 5);
        this.rabbitHead.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.rabbitHead.mirror = true;
        this.setRotationOffset(this.rabbitHead, 0.0F, 0.0F, 0.0F);
        this.rabbitRightEar = new ModelRenderer(this, 52, 0);
        this.rabbitRightEar.addBox(-2.5F, -9.0F, -1.0F, 2, 5, 1);
        this.rabbitRightEar.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.rabbitRightEar.mirror = true;
        this.setRotationOffset(this.rabbitRightEar, 0.0F, (float) (-Math.PI / 12), 0.0F);
        this.rabbitLeftEar = new ModelRenderer(this, 58, 0);
        this.rabbitLeftEar.addBox(0.5F, -9.0F, -1.0F, 2, 5, 1);
        this.rabbitLeftEar.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.rabbitLeftEar.mirror = true;
        this.setRotationOffset(this.rabbitLeftEar, 0.0F, (float) (Math.PI / 12), 0.0F);
        this.rabbitTail = new ModelRenderer(this, 52, 6);
        this.rabbitTail.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2);
        this.rabbitTail.setRotationPoint(0.0F, 20.0F, 7.0F);
        this.rabbitTail.mirror = true;
        this.setRotationOffset(this.rabbitTail, -0.3490659F, 0.0F, 0.0F);
        this.rabbitNose = new ModelRenderer(this, 32, 9);
        this.rabbitNose.addBox(-0.5F, -2.5F, -5.5F, 1, 1, 1);
        this.rabbitNose.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.rabbitNose.mirror = true;
        this.setRotationOffset(this.rabbitNose, 0.0F, 0.0F, 0.0F);
    }

    public void setLivingAnimations(LivingEntity livingEntity, float f, float f1, float f2) {}


}
