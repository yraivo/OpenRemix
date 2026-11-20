package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityMagmaCube;

public class ModelMagmaCube extends ModelBase {
    ModelRenderer[] segments;
    ModelRenderer core;

    public void setLivingAnimations(LivingEntity livingEntity, float f2, float f3, float f) {
        EntityMagmaCube entityMagmaCube = (EntityMagmaCube) livingEntity;
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        float f1 =
                entityMagmaCube.prevSquishFactor
                        + (entityMagmaCube.squishFactor - entityMagmaCube.prevSquishFactor) * f;
        String[] astring = astring1;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        int i = 0;

        while (i < this.segments.length) {
            this.segments[i].rotationPointY = -(4 - i) * f1 * 1.7F;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public ModelMagmaCube() {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.segments = new ModelRenderer[8];
        String[] astring = astring1;
        int i = 0;

        while (i < this.segments.length) {
            byte b0;
            int j;
            {
                b0 = 0;
                j = i;
                if (i == 2) {
                    b0 = 24;
                    j = 10;
                    if (!(astring == null)) {

                        if (i == 3) {
                            b0 = 24;
                            j = 19;
                        }
                    }
                } else {

                    if (i == 3) {
                        b0 = 24;
                        j = 19;
                    }
                }
            }

            this.segments[i] = new ModelRenderer(this, b0, j);
            this.segments[i].addBox(-4.0F, 16 + i, -4.0F, 8, 1, 8);
            i++;
            if (astring != null) {
                break;
            }
        }

        this.core = new ModelRenderer(this, 0, 16);
        this.core.addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        String[] astring = astring1;
        this.core.render(f5);
        int i = 0;

        while (i < this.segments.length) {
            this.segments[i].render(f5);
            i++;
            if (astring != null) {
                break;
            }
        }
    }
}
