package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import java.util.Random;

public class ModelGhast extends ModelBase {
    ModelRenderer[] tentacles;
    ModelRenderer body;

    public void setRotationAngles(
            float f1, float f2, float f, float f3, float f4, float f5, Entity entity) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        int i = 0;
        String[] astring = astring1;

        while (i < this.tentacles.length) {
            this.tentacles[i].rotateAngleX = 0.2F * MathHelper.sin(f * 0.3F + i) + 0.4F;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.6F, 0.0F);
        this.body.render(f5);
        ModelRenderer[] awUValue = this.tentacles;
        int length = awUValue.length;
        String[] astring = astring1;
        int j = 0;

        while (j < length) {
            ModelRenderer modelRenderer = awUValue[j];
            modelRenderer.render(f5);
            j++;
            if (astring != null) {
                break;
            }
        }

        GlStateManager.popMatrix();
    }

    public ModelGhast() {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.tentacles = new ModelRenderer[9];
        byte b0 = -16;
        this.body = new ModelRenderer(this, 0, 0);
        String[] astring = astring1;
        this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
        this.body.rotationPointY += 24 + b0;

        Random random = new Random(1660L);
        int i = 0;

        while (i < this.tentacles.length) {
            this.tentacles[i] = new ModelRenderer(this, 0, 0);
            float rotationPointX = ((i % 3 - i / 3 % 2 * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
            float rotationPointZ = (i / 3 / 2.0F * 2.0F - 1.0F) * 5.0F;
            int j = random.nextInt(7) + 8;
            this.tentacles[i].addBox(-1.0F, 0.0F, -1.0F, 2, j, 2);
            this.tentacles[i].rotationPointX = rotationPointX;
            this.tentacles[i].rotationPointZ = rotationPointZ;
            this.tentacles[i].rotationPointY = 31 + b0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }
}
