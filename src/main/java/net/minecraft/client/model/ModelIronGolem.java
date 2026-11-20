package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityIronGolem;

public class ModelIronGolem extends ModelBase {
    public ModelRenderer ironGolemHead;
    public ModelRenderer ironGolemRightLeg;
    public ModelRenderer ironGolemLeftLeg;
    public ModelRenderer ironGolemLeftArm;
    public ModelRenderer ironGolemBody;
    public ModelRenderer ironGolemRightArm;

    private float method_efd4ed941b8270ddc82f(float f, float f1) {
        return (Math.abs(f % f1 - f1 * 0.5F) - f1 * 0.25F) / (f1 * 0.25F);
    }

    public void setRotationAngles(
            float f, float f1, float f4, float f2, float f3, float f5, Entity entity) {
        this.ironGolemHead.rotateAngleY = f2 / (180.0F / (float) Math.PI);
        this.ironGolemHead.rotateAngleX = f3 / (180.0F / (float) Math.PI);
        this.ironGolemLeftLeg.rotateAngleX =
                -1.5F * this.method_efd4ed941b8270ddc82f(f, 13.0F) * f1;
        this.ironGolemRightLeg.rotateAngleX =
                1.5F * this.method_efd4ed941b8270ddc82f(f, 13.0F) * f1;
        this.ironGolemLeftLeg.rotateAngleY = 0.0F;
        this.ironGolemRightLeg.rotateAngleY = 0.0F;
    }

    public void setLivingAnimations(LivingEntity livingEntity, float f, float f1, float f2) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        EntityIronGolem entityIronGolem = (EntityIronGolem) livingEntity;
        String[] astring = astring1;
        int attackTimer = entityIronGolem.getAttackTimer();
        if (attackTimer > 0) {
            this.ironGolemRightArm.rotateAngleX =
                    -2.0F + 1.5F * this.method_efd4ed941b8270ddc82f(attackTimer - f2, 10.0F);
            this.ironGolemLeftArm.rotateAngleX =
                    -2.0F + 1.5F * this.method_efd4ed941b8270ddc82f(attackTimer - f2, 10.0F);
            if (astring == null) {
                return;
            }
        }

        int j = entityIronGolem.getHoldRoseTick();
        if (j > 0) {
            this.ironGolemRightArm.rotateAngleX =
                    -0.8F + 0.025F * this.method_efd4ed941b8270ddc82f(j, 70.0F);
            this.ironGolemLeftArm.rotateAngleX = 0.0F;
            if (astring == null) {
                return;
            }
        }

        this.ironGolemRightArm.rotateAngleX =
                (-0.2F + 1.5F * this.method_efd4ed941b8270ddc82f(f, 13.0F)) * f1;
        this.ironGolemLeftArm.rotateAngleX =
                (-0.2F - 1.5F * this.method_efd4ed941b8270ddc82f(f, 13.0F)) * f1;
    }

    public ModelIronGolem() {
        this(0.0F);
    }

    public ModelIronGolem(float f) {
        this(f, -7.0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.ironGolemHead.render(f5);
        this.ironGolemBody.render(f5);
        this.ironGolemLeftLeg.render(f5);
        this.ironGolemRightLeg.render(f5);
        this.ironGolemRightArm.render(f5);
        this.ironGolemLeftArm.render(f5);
    }

    public ModelIronGolem(float f, float f1) {
        short short1 = 128;
        short short2 = 128;
        this.ironGolemHead = new ModelRenderer(this).setTextureSize(short1, short2);
        this.ironGolemHead.setRotationPoint(0.0F, 0.0F + f1, -2.0F);
        this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, f);
        this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, f);
        this.ironGolemBody = new ModelRenderer(this).setTextureSize(short1, short2);
        this.ironGolemBody.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, f);
        this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, f + 0.5F);
        this.ironGolemRightArm = new ModelRenderer(this).setTextureSize(short1, short2);
        this.ironGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, f);
        this.ironGolemLeftArm = new ModelRenderer(this).setTextureSize(short1, short2);
        this.ironGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, f);
        this.ironGolemLeftLeg = new ModelRenderer(this, 0, 22).setTextureSize(short1, short2);
        this.ironGolemLeftLeg.setRotationPoint(-4.0F, 18.0F + f1, 0.0F);
        this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, f);
        this.ironGolemRightLeg = new ModelRenderer(this, 0, 22).setTextureSize(short1, short2);
        this.ironGolemRightLeg.mirror = true;
        this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + f1, 0.0F);
        this.ironGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, f);
    }
}
