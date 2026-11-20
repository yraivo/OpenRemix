package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.EntityDragon;

public class ModelDragon extends ModelBase {

    private ModelRenderer rearFoot;
    private ModelRenderer spine;

    private ModelRenderer wingTip;

    private ModelRenderer frontFoot;
    private ModelRenderer frontLegTip;
    private ModelRenderer wing;
    private ModelRenderer jaw;
    private ModelRenderer body;
    private ModelRenderer head;
    private ModelRenderer frontLeg;
    private float partialTicks;
    private ModelRenderer rearLeg;
    private ModelRenderer rearLegTip;

    public ModelDragon(float f1) {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.setTextureOffset("body.body", 0, 0);
        this.setTextureOffset("wing.skin", -56, 88);
        this.setTextureOffset("wingtip.skin", -56, 144);
        this.setTextureOffset("rearleg.main", 0, 0);
        this.setTextureOffset("rearfoot.main", 112, 0);
        this.setTextureOffset("rearlegtip.main", 196, 0);
        this.setTextureOffset("head.upperhead", 112, 30);
        this.setTextureOffset("wing.bone", 112, 88);
        this.setTextureOffset("head.upperlip", 176, 44);
        this.setTextureOffset("jaw.jaw", 176, 65);
        this.setTextureOffset("frontleg.main", 112, 104);
        this.setTextureOffset("wingtip.bone", 112, 136);
        this.setTextureOffset("frontfoot.main", 144, 104);
        this.setTextureOffset("neck.box", 192, 104);
        this.setTextureOffset("frontlegtip.main", 226, 138);
        this.setTextureOffset("body.scale", 220, 53);
        this.setTextureOffset("head.scale", 0, 0);
        this.setTextureOffset("neck.scale", 48, 0);
        this.setTextureOffset("head.nostril", 112, 0);
        float f = -16.0F;
        this.head = new ModelRenderer(this, "head");
        this.head.addBox("upperlip", -6.0F, -1.0F, -8.0F + f, 12, 5, 16);
        this.head.addBox("upperhead", -8.0F, -8.0F, 6.0F + f, 16, 16, 16);
        this.head.mirror = true;
        this.head.addBox("scale", -5.0F, -12.0F, 12.0F + f, 2, 4, 6);
        this.head.addBox("nostril", -5.0F, -3.0F, -6.0F + f, 2, 2, 4);
        this.head.mirror = false;
        this.head.addBox("scale", 3.0F, -12.0F, 12.0F + f, 2, 4, 6);
        this.head.addBox("nostril", 3.0F, -3.0F, -6.0F + f, 2, 2, 4);
        this.jaw = new ModelRenderer(this, "jaw");
        this.jaw.setRotationPoint(0.0F, 4.0F, 8.0F + f);
        this.jaw.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
        this.head.addChild(this.jaw);
        this.spine = new ModelRenderer(this, "neck");
        this.spine.addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
        this.spine.addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
        this.body = new ModelRenderer(this, "body");
        this.body.setRotationPoint(0.0F, 4.0F, 8.0F);
        this.body.addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
        this.body.addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
        this.body.addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
        this.body.addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
        this.wing = new ModelRenderer(this, "wing");
        this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
        this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
        this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
        this.wingTip = new ModelRenderer(this, "wingtip");
        this.wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
        this.wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
        this.wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
        this.wing.addChild(this.wingTip);
        this.frontLeg = new ModelRenderer(this, "frontleg");
        this.frontLeg.setRotationPoint(-12.0F, 20.0F, 2.0F);
        this.frontLeg.addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
        this.frontLegTip = new ModelRenderer(this, "frontlegtip");
        this.frontLegTip.setRotationPoint(0.0F, 20.0F, -1.0F);
        this.frontLegTip.addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
        this.frontLeg.addChild(this.frontLegTip);
        this.frontFoot = new ModelRenderer(this, "frontfoot");
        this.frontFoot.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.frontFoot.addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
        this.frontLegTip.addChild(this.frontFoot);
        this.rearLeg = new ModelRenderer(this, "rearleg");
        this.rearLeg.setRotationPoint(-16.0F, 16.0F, 42.0F);
        this.rearLeg.addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
        this.rearLegTip = new ModelRenderer(this, "rearlegtip");
        this.rearLegTip.setRotationPoint(0.0F, 32.0F, -4.0F);
        this.rearLegTip.addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
        this.rearLeg.addChild(this.rearLegTip);
        this.rearFoot = new ModelRenderer(this, "rearfoot");
        this.rearFoot.setRotationPoint(0.0F, 31.0F, 4.0F);
        this.rearFoot.addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
        this.rearLegTip.addChild(this.rearFoot);
    }

    public void setLivingAnimations(LivingEntity livingEntity, float f1, float f2, float ajnValue) {
        this.partialTicks = ajnValue;
    }

    private float updateRotations(double d0) {
        String[] astring = ModelBase.getLegacyModelFlowMarker();

        while (d0 >= 180.0) {
            d0 -= 360.0;
            if (astring != null) {
                break;
            }
        }

        while (d0 < -180.0) {
            d0 += 360.0;
            if (astring != null) {
                break;
            }
        }

        return (float) d0;
    }


    public void render(
            Entity entity, float f13, float f14, float f15, float f16, float f17, float f) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        GlStateManager.pushMatrix();
        EntityDragon entityDragon = (EntityDragon) entity;
        float f1 = entityDragon.bbG + (entityDragon.bct - entityDragon.bbG) * this.partialTicks;
        String[] astring = astring1;
        this.jaw.rotateAngleX = (float) (Math.sin(f1 * (float) Math.PI * 2.0F) + 1.0) * 0.2F;
        float f2 = (float) (Math.sin(f1 * (float) Math.PI * 2.0F - 1.0F) + 1.0);
        f2 = (f2 * f2 * 1.0F + f2 * 2.0F) * 0.05F;
        GlStateManager.translate(0.0F, f2 - 2.0F, -3.0F);
        GlStateManager.rotate(f2 * 2.0F, 1.0F, 0.0F, 0.0F);
        float rotationPointY = -30.0F;
        float rotationPointX = 0.0F;
        float f5 = 1.5F;
        double[] adouble = entityDragon.getMovementOffsets(6, this.partialTicks);
        float f6 =
                this.updateRotations(
                        entityDragon.getMovementOffsets(5, this.partialTicks)[0]
                                - entityDragon.getMovementOffsets(10, this.partialTicks)[0]);
        float f7 =
                this.updateRotations(
                        entityDragon.getMovementOffsets(5, this.partialTicks)[0] + f6 / 2.0F);
        rotationPointY += 2.0F;
        float f8 = f1 * (float) Math.PI * 2.0F;
        rotationPointY = 20.0F;
        float rotationPointZ = -12.0F;
        int i = 0;

        while (i < 5) {
            double[] adouble1 = entityDragon.getMovementOffsets(5 - i, this.partialTicks);
            float f10 = (float) Math.cos(i * 0.45F + f8) * 0.15F;
            this.spine.rotateAngleY =
                    this.updateRotations(adouble1[0] - adouble[0])
                            * (float) Math.PI
                            / 180.0F
                            * f5;
            this.spine.rotateAngleX =
                    f10 + (float) (adouble1[1] - adouble[1]) * (float) Math.PI / 180.0F * f5 * 5.0F;
            this.spine.rotateAngleZ =
                    -this.updateRotations(adouble1[0] - f7)
                            * (float) Math.PI
                            / 180.0F
                            * f5;
            this.spine.rotationPointY = rotationPointY;
            this.spine.rotationPointZ = rotationPointZ;
            this.spine.rotationPointX = rotationPointX;
            rotationPointY = (float) (rotationPointY + Math.sin(this.spine.rotateAngleX) * 10.0);
            rotationPointZ =
                    (float)
                            (rotationPointZ
                                    - Math.cos(this.spine.rotateAngleY)
                                            * Math.cos(this.spine.rotateAngleX)
                                            * 10.0);
            rotationPointX =
                    (float)
                            (rotationPointX
                                    - Math.sin(this.spine.rotateAngleY)
                                            * Math.cos(this.spine.rotateAngleX)
                                            * 10.0);
            this.spine.render(f);
            i++;
            if (astring != null) {
                break;
            }
        }

        this.head.rotationPointY = rotationPointY;
        this.head.rotationPointZ = rotationPointZ;
        this.head.rotationPointX = rotationPointX;
        double[] adouble2 = entityDragon.getMovementOffsets(0, this.partialTicks);
        this.head.rotateAngleY =
                this.updateRotations(adouble2[0] - adouble[0])
                        * (float) Math.PI
                        / 180.0F
                        * 1.0F;
        this.head.rotateAngleZ =
                -this.updateRotations(adouble2[0] - f7)
                        * (float) Math.PI
                        / 180.0F
                        * 1.0F;
        this.head.render(f);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-f6 * f5 * 1.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(0.0F, -1.0F, 0.0F);
        this.body.rotateAngleZ = 0.0F;
        this.body.render(f);
        int k = 0;

        while (k < 2) {
            GlStateManager.enableCull();
            float f11 = f1 * (float) Math.PI * 2.0F;
            this.wing.rotateAngleX = 0.125F - (float) Math.cos(f11) * 0.2F;
            this.wing.rotateAngleY = 0.25F;
            this.wing.rotateAngleZ = (float) (Math.sin(f11) + 0.125) * 0.8F;
            this.wingTip.rotateAngleZ = -((float) (Math.sin(f11 + 2.0F) + 0.5)) * 0.75F;
            this.rearLeg.rotateAngleX = 1.0F + f2 * 0.1F;
            this.rearLegTip.rotateAngleX = 0.5F + f2 * 0.1F;
            this.rearFoot.rotateAngleX = 0.75F + f2 * 0.1F;
            this.frontLeg.rotateAngleX = 1.3F + f2 * 0.1F;
            this.frontLegTip.rotateAngleX = -0.5F - f2 * 0.1F;
            this.frontFoot.rotateAngleX = 0.75F + f2 * 0.1F;
            this.wing.render(f);
            this.frontLeg.render(f);
            this.rearLeg.render(f);
            GlStateManager.scale(-1.0F, 1.0F, 1.0F);
            if (k == 0) {
                GlStateManager.cullFace(1028);
            }

            k++;
            if (astring != null) {
                break;
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.cullFace(1029);
        GlStateManager.disableCull();
        float f12 = -((float) Math.sin(f1 * (float) Math.PI * 2.0F)) * 0.0F;
        f8 = f1 * (float) Math.PI * 2.0F;
        rotationPointY = 10.0F;
        rotationPointZ = 60.0F;
        rotationPointX = 0.0F;
        adouble = entityDragon.getMovementOffsets(11, this.partialTicks);
        int j = 0;

        while (j < 12) {
            double[] adouble3 = entityDragon.getMovementOffsets(12 + j, this.partialTicks);
            f12 = (float) (f12 + Math.sin(j * 0.45F + f8) * 0.05F);
            this.spine.rotateAngleY =
                    (this.updateRotations(adouble3[0] - adouble[0]) * f5 + 180.0F)
                            * (float) Math.PI
                            / 180.0F;
            this.spine.rotateAngleX =
                    f12 + (float) (adouble3[1] - adouble[1]) * (float) Math.PI / 180.0F * f5 * 5.0F;
            this.spine.rotateAngleZ =
                    this.updateRotations(adouble3[0] - f7)
                            * (float) Math.PI
                            / 180.0F
                            * f5;
            this.spine.rotationPointY = rotationPointY;
            this.spine.rotationPointZ = rotationPointZ;
            this.spine.rotationPointX = rotationPointX;
            rotationPointY = (float) (rotationPointY + Math.sin(this.spine.rotateAngleX) * 10.0);
            rotationPointZ =
                    (float)
                            (rotationPointZ
                                    - Math.cos(this.spine.rotateAngleY)
                                            * Math.cos(this.spine.rotateAngleX)
                                            * 10.0);
            rotationPointX =
                    (float)
                            (rotationPointX
                                    - Math.sin(this.spine.rotateAngleY)
                                            * Math.cos(this.spine.rotateAngleX)
                                            * 10.0);
            this.spine.render(f);
            j++;
            if (astring != null) {
                break;
            }
        }

        GlStateManager.popMatrix();
    }
}
