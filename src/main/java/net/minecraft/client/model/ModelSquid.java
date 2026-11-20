package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSquid extends ModelBase {
    ModelRenderer squidBody;
    ModelRenderer[] squidTentacles;

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        String[] astring = astring1;
        this.squidBody.render(f5);
        int i = 0;

        while (i < this.squidTentacles.length) {
            this.squidTentacles[i].render(f5);
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public void setRotationAngles(
            float f1, float f2, float rotateAngleX, float f3, float f4, float f5, Entity entity) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        ModelRenderer[] awUValue = this.squidTentacles;
        int length = awUValue.length;
        String[] astring = astring1;
        int j = 0;

        while (j < length) {
            ModelRenderer modelRenderer = awUValue[j];
            modelRenderer.rotateAngleX = rotateAngleX;
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    public ModelSquid() {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.squidTentacles = new ModelRenderer[8];
        String[] astring = astring1;
        byte b0 = -16;
        this.squidBody = new ModelRenderer(this, 0, 0);
        this.squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
        this.squidBody.rotationPointY += 24 + b0;
        int i = 0;

        while (i < this.squidTentacles.length) {
            this.squidTentacles[i] = new ModelRenderer(this, 48, 0);
            double d0 = i * Math.PI * 2.0 / this.squidTentacles.length;
            float rotationPointX = (float) Math.cos(d0) * 5.0F;
            float rotationPointZ = (float) Math.sin(d0) * 5.0F;
            this.squidTentacles[i].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
            this.squidTentacles[i].rotationPointX = rotationPointX;
            this.squidTentacles[i].rotationPointZ = rotationPointZ;
            this.squidTentacles[i].rotationPointY = 31 + b0;
            d0 = i * Math.PI * -2.0 / this.squidTentacles.length + (Math.PI / 2);
            this.squidTentacles[i].rotateAngleY = (float) d0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }
}
