package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelBoat extends ModelBase {
    public ModelRenderer[] boatSides = new ModelRenderer[5];

    public ModelBoat() {
        this.boatSides[0] = new ModelRenderer(this, 0, 8);
        this.boatSides[1] = new ModelRenderer(this, 0, 0);
        this.boatSides[2] = new ModelRenderer(this, 0, 0);
        this.boatSides[3] = new ModelRenderer(this, 0, 0);
        this.boatSides[4] = new ModelRenderer(this, 0, 0);
        byte b0 = 24;
        byte b1 = 6;
        byte b2 = 20;
        byte b3 = 4;
        this.boatSides[0].addBox(-b0 / 2, -b2 / 2 + 2, -3.0F, b0, b2 - 4, 4, 0.0F);
        this.boatSides[0].setRotationPoint(0.0F, b3, 0.0F);
        this.boatSides[1].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
        this.boatSides[1].setRotationPoint(-b0 / 2 + 1, b3, 0.0F);
        this.boatSides[2].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
        this.boatSides[2].setRotationPoint(b0 / 2 - 1, b3, 0.0F);
        this.boatSides[3].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
        this.boatSides[3].setRotationPoint(0.0F, b3, -b2 / 2 + 1);
        this.boatSides[4].addBox(-b0 / 2 + 2, -b1 - 1, -1.0F, b0 - 4, b1, 2, 0.0F);
        this.boatSides[4].setRotationPoint(0.0F, b3, b2 / 2 - 1);
        this.boatSides[0].rotateAngleX = (float) (Math.PI / 2);
        this.boatSides[1].rotateAngleY = (float) (Math.PI * 3.0 / 2.0);
        this.boatSides[2].rotateAngleY = (float) (Math.PI / 2);
        this.boatSides[3].rotateAngleY = (float) Math.PI;
    }

    public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        int i = 0;
        String[] astring = astring1;

        while (i < 5) {
            this.boatSides[i].render(f);
            i++;
            if (astring != null) {
                break;
            }
        }
    }
}
