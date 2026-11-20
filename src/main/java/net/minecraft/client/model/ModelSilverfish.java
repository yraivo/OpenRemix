package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSilverfish extends ModelBase {
    private float[] wX;
    private ModelRenderer[] silverfishWings;
    private ModelRenderer[] silverfishBodyParts = new ModelRenderer[7];
    private static final int[][] silverfishBoxLength =
            new int[][] {
                {3, 2, 2}, {4, 3, 2}, {6, 4, 3}, {3, 3, 3}, {2, 2, 3}, {2, 1, 2}, {1, 1, 2}
            };
    private static final int[][] silverfishTexturePositions =
            new int[][] {{0, 0}, {0, 4}, {0, 9}, {0, 16}, {0, 22}, {11, 0}, {13, 4}};

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        int i = 0;
        String[] astring = astring1;

        while (i < this.silverfishBodyParts.length) {
            this.silverfishBodyParts[i].render(f5);
            i++;
            if (astring != null) {
                break;
            }
        }

        i = 0;

        while (i < this.silverfishWings.length) {
            this.silverfishWings[i].render(f5);
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public void setRotationAngles(
            float f1, float f2, float f, float f3, float f4, float f5, Entity entity) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        int i = 0;
        String[] astring = astring1;

        while (i < this.silverfishBodyParts.length) {
            this.silverfishBodyParts[i].rotateAngleY =
                    MathHelper.cos(f * 0.9F + i * 0.15F * (float) Math.PI)
                            * (float) Math.PI
                            * 0.05F
                            * (1 + Math.abs(i - 2));
            this.silverfishBodyParts[i].rotationPointX =
                    MathHelper.sin(f * 0.9F + i * 0.15F * (float) Math.PI)
                            * (float) Math.PI
                            * 0.2F
                            * Math.abs(i - 2);
            i++;
            if (astring != null) {
                break;
            }
        }

        this.silverfishWings[0].rotateAngleY = this.silverfishBodyParts[2].rotateAngleY;
        this.silverfishWings[1].rotateAngleY = this.silverfishBodyParts[4].rotateAngleY;
        this.silverfishWings[1].rotationPointX = this.silverfishBodyParts[4].rotationPointX;
        this.silverfishWings[2].rotateAngleY = this.silverfishBodyParts[1].rotateAngleY;
        this.silverfishWings[2].rotationPointX = this.silverfishBodyParts[1].rotationPointX;
    }

    public ModelSilverfish() {
        this.wX = new float[7];
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        float f = -3.5F;
        String[] astring = astring1;
        int i = 0;

        while (i < this.silverfishBodyParts.length) {
            this.silverfishBodyParts[i] = new ModelRenderer(this, net.minecraft.client.model.ModelSilverfish.silverfishTexturePositions[i][0], net.minecraft.client.model.ModelSilverfish.silverfishTexturePositions[i][1]);
            this.silverfishBodyParts[i].addBox(
                    net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[i][0] * -0.5F, 0.0F, net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[i][2] * -0.5F, net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[i][0], net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[i][1], net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[i][2]);
            this.silverfishBodyParts[i].setRotationPoint(0.0F, 24 - net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[i][1], f);
            this.wX[i] = f;
            if (i < this.silverfishBodyParts.length - 1) {
                f += (net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[i][2] + net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[i + 1][2]) * 0.5F;
            }

            i++;
            if (astring != null) {
                break;
            }
        }

        this.silverfishWings = new ModelRenderer[3];
        this.silverfishWings[0] = new ModelRenderer(this, 20, 0);
        this.silverfishWings[0].addBox(-5.0F, 0.0F, net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[2][2] * -0.5F, 10, 8, net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[2][2]);
        this.silverfishWings[0].setRotationPoint(0.0F, 16.0F, this.wX[2]);
        this.silverfishWings[1] = new ModelRenderer(this, 20, 11);
        this.silverfishWings[1].addBox(-3.0F, 0.0F, net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[4][2] * -0.5F, 6, 4, net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[4][2]);
        this.silverfishWings[1].setRotationPoint(0.0F, 20.0F, this.wX[4]);
        this.silverfishWings[2] = new ModelRenderer(this, 20, 18);
        this.silverfishWings[2].addBox(-3.0F, 0.0F, net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[4][2] * -0.5F, 6, 5, net.minecraft.client.model.ModelSilverfish.silverfishBoxLength[1][2]);
        this.silverfishWings[2].setRotationPoint(0.0F, 19.0F, this.wX[1]);
    }
}
