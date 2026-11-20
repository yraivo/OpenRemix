package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelMinecart extends ModelBase {
    public ModelRenderer[] sideModels = new ModelRenderer[7];

    public void render(Entity entity, float f2, float f3, float f, float f4, float f5, float f1) {
        this.sideModels[5].rotationPointY = 4.0F - f;
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        int i = 0;
        String[] astring = astring1;

        while (i < 6) {
            this.sideModels[i].render(f1);
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public ModelMinecart() {
        this.sideModels[0] = new ModelRenderer(this, 0, 10);
        this.sideModels[1] = new ModelRenderer(this, 0, 0);
        this.sideModels[2] = new ModelRenderer(this, 0, 0);
        this.sideModels[3] = new ModelRenderer(this, 0, 0);
        this.sideModels[4] = new ModelRenderer(this, 0, 0);
        this.sideModels[5] = new ModelRenderer(this, 44, 10);
        byte b0 = 20;
        byte b1 = 8;
        byte b2 = 16;
        byte b3 = 4;
        this.sideModels[0].addBox(-b0 / 2, -b2 / 2, -1.0F, b0, b2, 2, 0.0F);
        this.sideModels[0].setRotationPoint(0.0F, b3, 0.0F);
        this.sideModels[5].addBox(-b0 / 2 + 1, -b2 / 2 + 1, -1.0F, b0 - 2, b2 - 2, 1, 0.0F);
        this.sideModels[5].setRotationPoint(0.0F, b3, 0.0F);
        this.sideModels[1].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
        this.sideModels[1].setRotationPoint(-b0 / 2 + 1, b3, 0.0F);
        this.sideModels[2].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
        this.sideModels[2].setRotationPoint(b0 / 2 - 1, b3, 0.0F);
        this.sideModels[3].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
        this.sideModels[3].setRotationPoint(0.0F, b3, -b2 / 2 + 1);
        this.sideModels[4].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
        this.sideModels[4].setRotationPoint(0.0F, b3, b2 / 2 - 1);
        this.sideModels[0].rotateAngleX = (float) (Math.PI / 2);
        this.sideModels[1].rotateAngleY = (float) (Math.PI * 3.0 / 2.0);
        this.sideModels[2].rotateAngleY = (float) (Math.PI / 2);
        this.sideModels[3].rotateAngleY = (float) Math.PI;
        this.sideModels[5].rotateAngleX = (float) (-Math.PI / 2);
    }
}
