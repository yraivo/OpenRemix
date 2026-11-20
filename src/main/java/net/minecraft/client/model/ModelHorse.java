package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.MathHelper;

public class ModelHorse extends ModelBase {
    private ModelRenderer horseLeftEar;
    private ModelRenderer horseSaddleFront;
    private ModelRenderer horseLeftFaceMetal;
    private ModelRenderer horseSaddleBottom;
    private ModelRenderer muleLeftChest;
    private ModelRenderer head;
    private ModelRenderer mane;
    private ModelRenderer horseRightRein;
    private ModelRenderer frontLeftLeg;
    private ModelRenderer backRightLeg;
    private ModelRenderer horseRightSaddleMetal;
    private ModelRenderer frontRightLeg;
    private ModelRenderer horseSaddleBack;
    private ModelRenderer frontLeftHoof;
    private ModelRenderer horseRightEar;
    private ModelRenderer tailMiddle;
    private ModelRenderer horseFaceRopes;
    private ModelRenderer tailBase;
    private ModelRenderer muleRightChest;
    private ModelRenderer horseLeftSaddleMetal;
    private ModelRenderer aur;
    private ModelRenderer horseLeftRein;
    private ModelRenderer frontLeftShin;
    private ModelRenderer muleLeftEar;
    private ModelRenderer horseRightSaddleRope;
    private ModelRenderer backRightShin;
    private ModelRenderer frontRightHoof;
    private ModelRenderer aum;
    private ModelRenderer body;
    private ModelRenderer tailTip;
    private ModelRenderer backRightHoof;
    private ModelRenderer backLeftShin;
    private ModelRenderer horseLeftSaddleRope;
    private ModelRenderer backLeftLeg;
    private ModelRenderer backLeftHoof;
    private ModelRenderer neck;
    private ModelRenderer muleRightEar;
    private ModelRenderer frontRightShin;
    private ModelRenderer horseRightFaceMetal;

    private float updateHorseRotation(float f, float f1, float f2) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        float f3 = f1 - f;
        String[] astring = astring1;

        while (f3 < -180.0F) {
            f3 += 360.0F;
            if (astring != null) {
                break;
            }
        }

        while (f3 >= 180.0F) {
            f3 -= 360.0F;
            if (astring != null) {
                break;
            }
        }

        return f + f2 * f3;
    }

    public ModelHorse() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.body = new ModelRenderer(this, 0, 34);
        this.body.addBox(-5.0F, -8.0F, -19.0F, 10, 10, 24);
        this.body.setRotationPoint(0.0F, 11.0F, 9.0F);
        this.tailBase = new ModelRenderer(this, 44, 0);
        this.tailBase.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3);
        this.tailBase.setRotationPoint(0.0F, 3.0F, 14.0F);
        this.setBoxRotation(this.tailBase, -1.134464F, 0.0F, 0.0F);
        this.tailMiddle = new ModelRenderer(this, 38, 7);
        this.tailMiddle.addBox(-1.5F, -2.0F, 3.0F, 3, 4, 7);
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.tailMiddle.setRotationPoint(0.0F, 3.0F, 14.0F);
        this.setBoxRotation(this.tailMiddle, -1.134464F, 0.0F, 0.0F);
        this.tailTip = new ModelRenderer(this, 24, 3);
        this.tailTip.addBox(-1.5F, -4.5F, 9.0F, 3, 4, 7);
        this.tailTip.setRotationPoint(0.0F, 3.0F, 14.0F);
        this.setBoxRotation(this.tailTip, -1.40215F, 0.0F, 0.0F);
        this.backLeftLeg = new ModelRenderer(this, 78, 29);
        this.backLeftLeg.addBox(-2.5F, -2.0F, -2.5F, 4, 9, 5);
        this.backLeftLeg.setRotationPoint(4.0F, 9.0F, 11.0F);
        this.backLeftShin = new ModelRenderer(this, 78, 43);
        this.backLeftShin.addBox(-2.0F, 0.0F, -1.5F, 3, 5, 3);
        this.backLeftShin.setRotationPoint(4.0F, 16.0F, 11.0F);
        this.backLeftHoof = new ModelRenderer(this, 78, 51);
        this.backLeftHoof.addBox(-2.5F, 5.1F, -2.0F, 4, 3, 4);
        this.backLeftHoof.setRotationPoint(4.0F, 16.0F, 11.0F);
        this.backRightLeg = new ModelRenderer(this, 96, 29);
        this.backRightLeg.addBox(-1.5F, -2.0F, -2.5F, 4, 9, 5);
        this.backRightLeg.setRotationPoint(-4.0F, 9.0F, 11.0F);
        this.backRightShin = new ModelRenderer(this, 96, 43);
        this.backRightShin.addBox(-1.0F, 0.0F, -1.5F, 3, 5, 3);
        this.backRightShin.setRotationPoint(-4.0F, 16.0F, 11.0F);
        this.backRightHoof = new ModelRenderer(this, 96, 51);
        this.backRightHoof.addBox(-1.5F, 5.1F, -2.0F, 4, 3, 4);
        String[] astring = astring1;
        this.backRightHoof.setRotationPoint(-4.0F, 16.0F, 11.0F);
        this.frontLeftLeg = new ModelRenderer(this, 44, 29);
        this.frontLeftLeg.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4);
        this.frontLeftLeg.setRotationPoint(4.0F, 9.0F, -8.0F);
        this.frontLeftShin = new ModelRenderer(this, 44, 41);
        this.frontLeftShin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3);
        this.frontLeftShin.setRotationPoint(4.0F, 16.0F, -8.0F);
        this.frontLeftHoof = new ModelRenderer(this, 44, 51);
        this.frontLeftHoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4);
        this.frontLeftHoof.setRotationPoint(4.0F, 16.0F, -8.0F);
        this.frontRightLeg = new ModelRenderer(this, 60, 29);
        this.frontRightLeg.addBox(-1.1F, -1.0F, -2.1F, 3, 8, 4);
        this.frontRightLeg.setRotationPoint(-4.0F, 9.0F, -8.0F);
        this.frontRightShin = new ModelRenderer(this, 60, 41);
        this.frontRightShin.addBox(-1.1F, 0.0F, -1.6F, 3, 5, 3);
        this.frontRightShin.setRotationPoint(-4.0F, 16.0F, -8.0F);
        this.frontRightHoof = new ModelRenderer(this, 60, 51);
        this.frontRightHoof.addBox(-1.6F, 5.1F, -2.1F, 4, 3, 4);
        this.frontRightHoof.setRotationPoint(-4.0F, 16.0F, -8.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.5F, -10.0F, -1.5F, 5, 5, 7);
        this.head.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.setBoxRotation(this.head, (float) (Math.PI / 6), 0.0F, 0.0F);
        this.aum = new ModelRenderer(this, 24, 18);
        this.aum.addBox(-2.0F, -10.0F, -7.0F, 4, 3, 6);
        this.aum.setRotationPoint(0.0F, 3.95F, -10.0F);
        this.setBoxRotation(this.aum, (float) (Math.PI / 6), 0.0F, 0.0F);
        this.aur = new ModelRenderer(this, 24, 27);
        this.aur.addBox(-2.0F, -7.0F, -6.5F, 4, 2, 5);
        this.aur.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.setBoxRotation(this.aur, (float) (Math.PI / 6), 0.0F, 0.0F);
        this.head.addChild(this.aum);
        this.head.addChild(this.aur);
        this.horseLeftEar = new ModelRenderer(this, 0, 0);
        this.horseLeftEar.addBox(0.45F, -12.0F, 4.0F, 2, 3, 1);
        this.horseLeftEar.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.setBoxRotation(this.horseLeftEar, (float) (Math.PI / 6), 0.0F, 0.0F);
        this.horseRightEar = new ModelRenderer(this, 0, 0);
        this.horseRightEar.addBox(-2.45F, -12.0F, 4.0F, 2, 3, 1);
        this.horseRightEar.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.setBoxRotation(this.horseRightEar, (float) (Math.PI / 6), 0.0F, 0.0F);
        this.muleLeftEar = new ModelRenderer(this, 0, 12);
        this.muleLeftEar.addBox(-2.0F, -16.0F, 4.0F, 2, 7, 1);
        this.muleLeftEar.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.setBoxRotation(this.muleLeftEar, (float) (Math.PI / 6), 0.0F, (float) (Math.PI / 12));
        this.muleRightEar = new ModelRenderer(this, 0, 12);
        this.muleRightEar.addBox(0.0F, -16.0F, 4.0F, 2, 7, 1);
        this.muleRightEar.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.setBoxRotation(this.muleRightEar, (float) (Math.PI / 6), 0.0F, (float) (-Math.PI / 12));
        this.neck = new ModelRenderer(this, 0, 12);
        this.neck.addBox(-2.05F, -9.8F, -2.0F, 4, 14, 8);
        this.neck.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.setBoxRotation(this.neck, (float) (Math.PI / 6), 0.0F, 0.0F);
        this.muleLeftChest = new ModelRenderer(this, 0, 34);
        this.muleLeftChest.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
        this.muleLeftChest.setRotationPoint(-7.5F, 3.0F, 10.0F);
        this.setBoxRotation(this.muleLeftChest, 0.0F, (float) (Math.PI / 2), 0.0F);
        this.muleRightChest = new ModelRenderer(this, 0, 47);
        this.muleRightChest.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
        this.muleRightChest.setRotationPoint(4.5F, 3.0F, 10.0F);
        this.setBoxRotation(this.muleRightChest, 0.0F, (float) (Math.PI / 2), 0.0F);
        this.horseSaddleBottom = new ModelRenderer(this, 80, 0);
        this.horseSaddleBottom.addBox(-5.0F, 0.0F, -3.0F, 10, 1, 8);
        this.horseSaddleBottom.setRotationPoint(0.0F, 2.0F, 2.0F);
        this.horseSaddleFront = new ModelRenderer(this, 106, 9);
        this.horseSaddleFront.addBox(-1.5F, -1.0F, -3.0F, 3, 1, 2);
        this.horseSaddleFront.setRotationPoint(0.0F, 2.0F, 2.0F);
        this.horseSaddleBack = new ModelRenderer(this, 80, 9);
        this.horseSaddleBack.addBox(-4.0F, -1.0F, 3.0F, 8, 1, 2);
        this.horseSaddleBack.setRotationPoint(0.0F, 2.0F, 2.0F);
        this.horseLeftSaddleMetal = new ModelRenderer(this, 74, 0);
        this.horseLeftSaddleMetal.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2);
        this.horseLeftSaddleMetal.setRotationPoint(5.0F, 3.0F, 2.0F);
        this.horseLeftSaddleRope = new ModelRenderer(this, 70, 0);
        this.horseLeftSaddleRope.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1);
        this.horseLeftSaddleRope.setRotationPoint(5.0F, 3.0F, 2.0F);
        this.horseRightSaddleMetal = new ModelRenderer(this, 74, 4);
        this.horseRightSaddleMetal.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2);
        this.horseRightSaddleMetal.setRotationPoint(-5.0F, 3.0F, 2.0F);
        this.horseRightSaddleRope = new ModelRenderer(this, 80, 0);
        this.horseRightSaddleRope.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1);
        this.horseRightSaddleRope.setRotationPoint(-5.0F, 3.0F, 2.0F);
        this.horseLeftFaceMetal = new ModelRenderer(this, 74, 13);
        this.horseLeftFaceMetal.addBox(1.5F, -8.0F, -4.0F, 1, 2, 2);
        this.horseLeftFaceMetal.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.setBoxRotation(this.horseLeftFaceMetal, (float) (Math.PI / 6), 0.0F, 0.0F);
        this.horseRightFaceMetal = new ModelRenderer(this, 74, 13);
        this.horseRightFaceMetal.addBox(-2.5F, -8.0F, -4.0F, 1, 2, 2);
        this.horseRightFaceMetal.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.setBoxRotation(this.horseRightFaceMetal, (float) (Math.PI / 6), 0.0F, 0.0F);
        this.horseLeftRein = new ModelRenderer(this, 44, 10);
        this.horseLeftRein.addBox(2.6F, -6.0F, -6.0F, 0, 3, 16);
        this.horseLeftRein.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.horseRightRein = new ModelRenderer(this, 44, 5);
        this.horseRightRein.addBox(-2.6F, -6.0F, -6.0F, 0, 3, 16);
        this.horseRightRein.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.mane = new ModelRenderer(this, 58, 0);
        this.mane.addBox(-1.0F, -11.5F, 5.0F, 2, 16, 4);
        this.mane.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.setBoxRotation(this.mane, (float) (Math.PI / 6), 0.0F, 0.0F);
        this.horseFaceRopes = new ModelRenderer(this, 80, 12);
        this.horseFaceRopes.addBox(-2.5F, -10.1F, -7.0F, 5, 5, 12, 0.2F);
        this.horseFaceRopes.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.setBoxRotation(this.horseFaceRopes, (float) (Math.PI / 6), 0.0F, 0.0F);
        if (astring != null) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }
    }

    public void render(Entity entity, float f4, float f5, float f6, float f7, float f8, float f) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        EntityHorse entityHorse = (EntityHorse) entity;
        int horseType = entityHorse.getHorseType();
        float f1 = entityHorse.method_ad4859278b6f26197223(0.0F);
        String[] astring = astring1;
        boolean adultHorse = entityHorse.isAdultHorse();
        int j = adultHorse && entityHorse.isHorseSaddled() ? 1 : 0;
        int k = adultHorse && entityHorse.isChested() ? 1 : 0;
        int l = horseType != 1 && horseType != 2 ? 0 : 1;
        float f2 = entityHorse.gh();
        int i1 = entityHorse.riddenByEntity != null ? 1 : 0;
        if (j != 0) {
            this.horseFaceRopes.render(f);
            this.horseSaddleBottom.render(f);
            this.horseSaddleFront.render(f);
            this.horseSaddleBack.render(f);
            this.horseLeftSaddleRope.render(f);
            this.horseLeftSaddleMetal.render(f);
            this.horseRightSaddleRope.render(f);
            this.horseRightSaddleMetal.render(f);
            this.horseLeftFaceMetal.render(f);
            this.horseRightFaceMetal.render(f);
            if (i1 != 0) {
                this.horseLeftRein.render(f);
                this.horseRightRein.render(f);
            }
        }

        if (!adultHorse) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(f2, 0.5F + f2 * 0.5F, f2);
            GlStateManager.translate(0.0F, 0.95F * (1.0F - f2), 0.0F);
        }

        this.backLeftLeg.render(f);
        this.backLeftShin.render(f);
        this.backLeftHoof.render(f);
        this.backRightLeg.render(f);
        this.backRightShin.render(f);
        this.backRightHoof.render(f);
        this.frontLeftLeg.render(f);
        this.frontLeftShin.render(f);
        this.frontLeftHoof.render(f);
        this.frontRightLeg.render(f);
        this.frontRightShin.render(f);
        this.frontRightHoof.render(f);
        if (!adultHorse) {
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(f2, f2, f2);
            GlStateManager.translate(0.0F, 1.35F * (1.0F - f2), 0.0F);
        }

        this.body.render(f);
        this.tailBase.render(f);
        this.tailMiddle.render(f);
        this.tailTip.render(f);
        this.neck.render(f);
        this.mane.render(f);
        if (!adultHorse) {
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            float f3 = 0.5F + f2 * f2 * 0.5F;
            GlStateManager.scale(f3, f3, f3);
            if (f1 <= 0.0F) {
                GlStateManager.translate(0.0F, 1.35F * (1.0F - f2), 0.0F);
                if (!(astring == null)) {

                    GlStateManager.translate(
                            0.0F,
                            0.9F * (1.0F - f2) * f1 + 1.35F * (1.0F - f2) * (1.0F - f1),
                            0.15F * (1.0F - f2) * f1);
                }
            } else {

                GlStateManager.translate(
                        0.0F,
                        0.9F * (1.0F - f2) * f1 + 1.35F * (1.0F - f2) * (1.0F - f1),
                        0.15F * (1.0F - f2) * f1);
            }
        }

        {
            if (l != 0) {
                this.muleLeftEar.render(f);
                this.muleRightEar.render(f);
                if (!(astring == null)) {

                    this.horseLeftEar.render(f);
                    this.horseRightEar.render(f);
                }
            } else {

                this.horseLeftEar.render(f);
                this.horseRightEar.render(f);
            }
        }

        this.head.render(f);
        if (!adultHorse) {
            GlStateManager.popMatrix();
        }

        if (k != 0) {
            this.muleLeftChest.render(f);
            this.muleRightChest.render(f);
        }
    }

    public void setLivingAnimations(LivingEntity livingEntity, float f, float f1, float f2) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        super.setLivingAnimations(livingEntity, f, f1, f2);
        float f3 =
                this.updateHorseRotation(livingEntity.prevRenderYawOffset, livingEntity.renderYawOffset, f2);
        float f4 =
                this.updateHorseRotation(livingEntity.prevRotationYawHead, livingEntity.rotationYawHead, f2);
        float f5 =
                livingEntity.prevRotationPitch
                        + (livingEntity.rotationPitch - livingEntity.prevRotationPitch) * f2;
        float f6 = f4 - f3;
        float rotateAngleX = f5 / (180.0F / (float) Math.PI);
        String[] astring = astring1;
        if (f6 > 20.0F) {
            f6 = 20.0F;
        }

        if (f6 < -20.0F) {
            f6 = -20.0F;
        }

        if (f1 > 0.2F) {
            rotateAngleX += MathHelper.cos(f * 0.4F) * 0.15F * f1;
        }

        int i;
        float f12;
        EntityHorse entityHorse = (EntityHorse) livingEntity;
        float f8 = entityHorse.method_ad4859278b6f26197223(f2);
        float rearingAmount = entityHorse.getRearingAmount(f2);
        float f10 = 1.0F - rearingAmount;
        float f11 = entityHorse.method_747d3e7081a808d0b7a1(f2);
        i = entityHorse.field_110278_bp != 0 ? 1 : 0;
        boolean horseSaddled = entityHorse.isHorseSaddled();
        int j = entityHorse.riddenByEntity != null ? 1 : 0;
        f12 = livingEntity.ticksExisted + f2;
        float f13 = MathHelper.cos(f * 0.6662F + (float) Math.PI);
        float f14 = f13 * 0.8F * f1;
        this.head.rotationPointY = 4.0F;
        this.head.rotationPointZ = -10.0F;
        this.tailBase.rotationPointY = 3.0F;
        this.tailMiddle.rotationPointZ = 14.0F;
        this.muleRightChest.rotationPointY = 3.0F;
        this.muleRightChest.rotationPointZ = 10.0F;
        this.body.rotateAngleX = 0.0F;
        this.head.rotateAngleX = (float) (Math.PI / 6) + rotateAngleX;
        this.head.rotateAngleY = f6 / (180.0F / (float) Math.PI);
        this.head.rotateAngleX =
                rearingAmount * ((float) (Math.PI / 12) + rotateAngleX)
                        + f8 * 2.18166F
                        + (1.0F - Math.max(rearingAmount, f8)) * this.head.rotateAngleX;
        this.head.rotateAngleY =
                rearingAmount * f6 / (180.0F / (float) Math.PI)
                        + (1.0F - Math.max(rearingAmount, f8)) * this.head.rotateAngleY;
        this.head.rotationPointY =
                rearingAmount * -6.0F
                        + f8 * 11.0F
                        + (1.0F - Math.max(rearingAmount, f8)) * this.head.rotationPointY;
        this.head.rotationPointZ =
                rearingAmount * -1.0F
                        + f8 * -10.0F
                        + (1.0F - Math.max(rearingAmount, f8)) * this.head.rotationPointZ;
        this.tailBase.rotationPointY = rearingAmount * 9.0F + f10 * this.tailBase.rotationPointY;
        this.tailMiddle.rotationPointZ = rearingAmount * 18.0F + f10 * this.tailMiddle.rotationPointZ;
        this.muleRightChest.rotationPointY = rearingAmount * 5.5F + f10 * this.muleRightChest.rotationPointY;
        this.muleRightChest.rotationPointZ = rearingAmount * 15.0F + f10 * this.muleRightChest.rotationPointZ;
        this.body.rotateAngleX =
                rearingAmount * -45.0F / (180.0F / (float) Math.PI) + f10 * this.body.rotateAngleX;
        this.horseLeftEar.rotationPointY = this.head.rotationPointY;
        this.horseRightEar.rotationPointY = this.head.rotationPointY;
        this.muleLeftEar.rotationPointY = this.head.rotationPointY;
        this.muleRightEar.rotationPointY = this.head.rotationPointY;
        this.neck.rotationPointY = this.head.rotationPointY;
        this.aum.rotationPointY = 0.02F;
        this.aur.rotationPointY = 0.0F;
        this.mane.rotationPointY = this.head.rotationPointY;
        this.horseLeftEar.rotationPointZ = this.head.rotationPointZ;
        this.horseRightEar.rotationPointZ = this.head.rotationPointZ;
        this.muleLeftEar.rotationPointZ = this.head.rotationPointZ;
        this.muleRightEar.rotationPointZ = this.head.rotationPointZ;
        this.neck.rotationPointZ = this.head.rotationPointZ;
        this.aum.rotationPointZ = 0.02F - f11 * 1.0F;
        this.aur.rotationPointZ = 0.0F + f11 * 1.0F;
        this.mane.rotationPointZ = this.head.rotationPointZ;
        this.horseLeftEar.rotateAngleX = this.head.rotateAngleX;
        this.horseRightEar.rotateAngleX = this.head.rotateAngleX;
        this.muleLeftEar.rotateAngleX = this.head.rotateAngleX;
        this.muleRightEar.rotateAngleX = this.head.rotateAngleX;
        this.neck.rotateAngleX = this.head.rotateAngleX;
        this.aum.rotateAngleX = 0.0F - 0.09424778F * f11;
        this.aur.rotateAngleX = 0.0F + (float) (Math.PI / 20) * f11;
        this.mane.rotateAngleX = this.head.rotateAngleX;
        this.horseLeftEar.rotateAngleY = this.head.rotateAngleY;
        this.horseRightEar.rotateAngleY = this.head.rotateAngleY;
        this.muleLeftEar.rotateAngleY = this.head.rotateAngleY;
        this.muleRightEar.rotateAngleY = this.head.rotateAngleY;
        this.neck.rotateAngleY = this.head.rotateAngleY;
        this.aum.rotateAngleY = 0.0F;
        this.aur.rotateAngleY = 0.0F;
        this.mane.rotateAngleY = this.head.rotateAngleY;
        this.muleLeftChest.rotateAngleX = f14 / 5.0F;
        this.muleRightChest.rotateAngleX = -f14 / 5.0F;
        float rotateAngleXValue = (float) (Math.PI / 2);

        float f18 = (float) (Math.PI / 12) * rearingAmount;
        float f19 = MathHelper.cos(f12 * 0.6F + (float) Math.PI);
        this.frontLeftLeg.rotationPointY = -2.0F * rearingAmount + 9.0F * f10;
        this.frontLeftLeg.rotationPointZ = -2.0F * rearingAmount + -8.0F * f10;
        this.frontRightLeg.rotationPointY = this.frontLeftLeg.rotationPointY;
        this.frontRightLeg.rotationPointZ = this.frontLeftLeg.rotationPointZ;
        this.backLeftShin.rotationPointY =
                this.backLeftLeg.rotationPointY
                        + MathHelper.sin((float) (Math.PI / 2) + f18 + f10 * -f13 * 0.5F * f1)
                                * 7.0F;
        this.backLeftShin.rotationPointZ =
                this.backLeftLeg.rotationPointZ
                        + MathHelper.cos(
                                        (float) (Math.PI * 3.0 / 2.0)
                                                + f18
                                                + f10 * -f13 * 0.5F * f1)
                                * 7.0F;
        this.backRightShin.rotationPointY =
                this.backRightLeg.rotationPointY
                        + MathHelper.sin((float) (Math.PI / 2) + f18 + f10 * f13 * 0.5F * f1)
                                * 7.0F;
        this.backRightShin.rotationPointZ =
                this.backRightLeg.rotationPointZ
                        + MathHelper.cos(
                                        (float) (Math.PI * 3.0 / 2.0) + f18 + f10 * f13 * 0.5F * f1)
                                * 7.0F;
        float rotateAngleXValue2 = ((float) (-Math.PI / 3) + f19) * rearingAmount + f14 * f10;
        float rotateAngleXValue3 = ((float) (-Math.PI / 3) + -f19) * rearingAmount + -f14 * f10;
        this.frontLeftShin.rotationPointY =
                this.frontLeftLeg.rotationPointY
                        + MathHelper.sin((float) (Math.PI / 2) + rotateAngleXValue2) * 7.0F;
        this.frontLeftShin.rotationPointZ =
                this.frontLeftLeg.rotationPointZ
                        + MathHelper.cos((float) (Math.PI * 3.0 / 2.0) + rotateAngleXValue2) * 7.0F;
        this.frontRightShin.rotationPointY =
                this.frontRightLeg.rotationPointY
                        + MathHelper.sin((float) (Math.PI / 2) + rotateAngleXValue3) * 7.0F;
        this.frontRightShin.rotationPointZ =
                this.frontRightLeg.rotationPointZ
                        + MathHelper.cos((float) (Math.PI * 3.0 / 2.0) + rotateAngleXValue3) * 7.0F;
        this.backLeftLeg.rotateAngleX = f18 + -f13 * 0.5F * f1 * f10;
        this.backLeftShin.rotateAngleX =
                -0.08726646F * rearingAmount
                        + (-f13 * 0.5F * f1 - Math.max(0.0F, f13 * 0.5F * f1)) * f10;
        this.backLeftHoof.rotateAngleX = this.backLeftShin.rotateAngleX;
        this.backRightLeg.rotateAngleX = f18 + f13 * 0.5F * f1 * f10;
        this.backRightShin.rotateAngleX =
                -0.08726646F * rearingAmount
                        + (f13 * 0.5F * f1 - Math.max(0.0F, -f13 * 0.5F * f1)) * f10;
        this.backRightHoof.rotateAngleX = this.backRightShin.rotateAngleX;
        this.frontLeftLeg.rotateAngleX = rotateAngleXValue2;
        this.frontLeftShin.rotateAngleX =
                (this.frontLeftLeg.rotateAngleX + (float) Math.PI * Math.max(0.0F, 0.2F + f19 * 0.2F))
                                * rearingAmount
                        + (f14 + Math.max(0.0F, f13 * 0.5F * f1)) * f10;
        this.frontLeftHoof.rotateAngleX = this.frontLeftShin.rotateAngleX;
        this.frontRightLeg.rotateAngleX = rotateAngleXValue3;
        this.frontRightShin.rotateAngleX =
                (this.frontRightLeg.rotateAngleX + (float) Math.PI * Math.max(0.0F, 0.2F - f19 * 0.2F))
                                * rearingAmount
                        + (-f14 + Math.max(0.0F, -f13 * 0.5F * f1)) * f10;
        this.frontRightHoof.rotateAngleX = this.frontRightShin.rotateAngleX;
        this.backLeftHoof.rotationPointY = this.backLeftShin.rotationPointY;
        this.backLeftHoof.rotationPointZ = this.backLeftShin.rotationPointZ;
        this.backRightHoof.rotationPointY = this.backRightShin.rotationPointY;
        this.backRightHoof.rotationPointZ = this.backRightShin.rotationPointZ;
        this.frontLeftHoof.rotationPointY = this.frontLeftShin.rotationPointY;
        this.frontLeftHoof.rotationPointZ = this.frontLeftShin.rotationPointZ;
        this.frontRightHoof.rotationPointY = this.frontRightShin.rotationPointY;
        this.frontRightHoof.rotationPointZ = this.frontRightShin.rotationPointZ;
        label45:
        if (horseSaddled) {
            this.horseSaddleBottom.rotationPointY = rearingAmount * 0.5F + f10 * 2.0F;
            this.horseSaddleBottom.rotationPointZ = rearingAmount * 11.0F + f10 * 2.0F;
            this.horseSaddleFront.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseSaddleBack.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseLeftSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseRightSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseLeftSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseRightSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.muleLeftChest.rotationPointY = this.muleRightChest.rotationPointY;
            this.horseSaddleFront.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseSaddleBack.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseLeftSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseRightSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseLeftSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseRightSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.muleLeftChest.rotationPointZ = this.muleRightChest.rotationPointZ;
            this.horseSaddleBottom.rotateAngleX = this.body.rotateAngleX;
            this.horseSaddleFront.rotateAngleX = this.body.rotateAngleX;
            this.horseSaddleBack.rotateAngleX = this.body.rotateAngleX;
            this.horseLeftRein.rotationPointY = this.head.rotationPointY;
            this.horseRightRein.rotationPointY = this.head.rotationPointY;
            this.horseFaceRopes.rotationPointY = this.head.rotationPointY;
            this.horseLeftFaceMetal.rotationPointY = this.head.rotationPointY;
            this.horseRightFaceMetal.rotationPointY = this.head.rotationPointY;
            this.horseLeftRein.rotationPointZ = this.head.rotationPointZ;
            this.horseRightRein.rotationPointZ = this.head.rotationPointZ;
            this.horseFaceRopes.rotationPointZ = this.head.rotationPointZ;
            this.horseLeftFaceMetal.rotationPointZ = this.head.rotationPointZ;
            this.horseRightFaceMetal.rotationPointZ = this.head.rotationPointZ;
            this.horseLeftRein.rotateAngleX = rotateAngleX;
            this.horseRightRein.rotateAngleX = rotateAngleX;
            this.horseFaceRopes.rotateAngleX = this.head.rotateAngleX;
            this.horseLeftFaceMetal.rotateAngleX = this.head.rotateAngleX;
            this.horseRightFaceMetal.rotateAngleX = this.head.rotateAngleX;
            this.horseFaceRopes.rotateAngleY = this.head.rotateAngleY;
            this.horseLeftFaceMetal.rotateAngleY = this.head.rotateAngleY;
            this.horseLeftRein.rotateAngleY = this.head.rotateAngleY;
            this.horseRightFaceMetal.rotateAngleY = this.head.rotateAngleY;
            this.horseRightRein.rotateAngleY = this.head.rotateAngleY;
            if (j != 0) {
                this.horseLeftSaddleRope.rotateAngleX = (float) (-Math.PI / 3);
                this.horseLeftSaddleMetal.rotateAngleX = (float) (-Math.PI / 3);
                this.horseRightSaddleRope.rotateAngleX = (float) (-Math.PI / 3);
                this.horseRightSaddleMetal.rotateAngleX = (float) (-Math.PI / 3);
                this.horseLeftSaddleRope.rotateAngleZ = 0.0F;
                this.horseLeftSaddleMetal.rotateAngleZ = 0.0F;
                this.horseRightSaddleRope.rotateAngleZ = 0.0F;
                this.horseRightSaddleMetal.rotateAngleZ = 0.0F;
                if (astring == null) {
                    break label45;
                }
            }

            this.horseLeftSaddleRope.rotateAngleX = f14 / 3.0F;
            this.horseLeftSaddleMetal.rotateAngleX = f14 / 3.0F;
            this.horseRightSaddleRope.rotateAngleX = f14 / 3.0F;
            this.horseRightSaddleMetal.rotateAngleX = f14 / 3.0F;
            this.horseLeftSaddleRope.rotateAngleZ = f14 / 5.0F;
            this.horseLeftSaddleMetal.rotateAngleZ = f14 / 5.0F;
            this.horseRightSaddleRope.rotateAngleZ = -f14 / 5.0F;
            this.horseRightSaddleMetal.rotateAngleZ = -f14 / 5.0F;
        }

        rotateAngleXValue = -1.3089F + f1 * 1.5F;
        if (rotateAngleXValue > 0.0F) {
            rotateAngleXValue = 0.0F;
        }

        {
            if (i != 0) {
                this.tailBase.rotateAngleY = MathHelper.cos(f12 * 0.7F);
                rotateAngleXValue = 0.0F;
                if (!(astring == null)) {

                    this.tailBase.rotateAngleY = 0.0F;
                }
            } else {

                this.tailBase.rotateAngleY = 0.0F;
            }
        }

        this.tailMiddle.rotateAngleY = this.tailBase.rotateAngleY;
        this.tailTip.rotateAngleY = this.tailBase.rotateAngleY;
        this.tailMiddle.rotationPointY = this.tailBase.rotationPointY;
        this.tailTip.rotationPointY = this.tailBase.rotationPointY;
        this.tailMiddle.rotationPointZ = this.tailBase.rotationPointZ;
        this.tailTip.rotationPointZ = this.tailBase.rotationPointZ;
        this.tailBase.rotateAngleX = rotateAngleXValue;
        this.tailMiddle.rotateAngleX = rotateAngleXValue;
        this.tailTip.rotateAngleX = -0.2618F + rotateAngleXValue;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ModelBase.setLegacyModelFlowMarker(new String[3]);
        }
    }

    private void setBoxRotation(
            ModelRenderer modelRenderer,
            float rotateAngleX,
            float rotateAngleY,
            float rotateAngleZ) {
        modelRenderer.rotateAngleX = rotateAngleX;
        modelRenderer.rotateAngleY = rotateAngleY;
        modelRenderer.rotateAngleZ = rotateAngleZ;
    }
}
