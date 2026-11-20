package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBlaze extends ModelBase {
    private ModelRenderer blazeHead;
    private ModelRenderer[] blazeSticks;

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.blazeHead.render(f5);
        String[] astring = astring1;
        int i = 0;

        while (i < this.blazeSticks.length) {
            this.blazeSticks[i].render(f5);
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public void setRotationAngles(
            float f4, float f5, float f, float f1, float f2, float f6, Entity entity) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        float f3 = f * (float) Math.PI * -0.1F;
        int i = 0;
        String[] astring = astring1;

        while (i < 4) {
            this.blazeSticks[i].rotationPointY = -2.0F + MathHelper.cos((i * 2 + f) * 0.25F);
            this.blazeSticks[i].rotationPointX = MathHelper.cos(f3) * 9.0F;
            this.blazeSticks[i].rotationPointZ = MathHelper.sin(f3) * 9.0F;
            f3++;
            i++;
            if (astring != null) {
                break;
            }
        }

        f3 = (float) (Math.PI / 4) + f * (float) Math.PI * 0.03F;
        i = 4;

        while (i < 8) {
            this.blazeSticks[i].rotationPointY = 2.0F + MathHelper.cos((i * 2 + f) * 0.25F);
            this.blazeSticks[i].rotationPointX = MathHelper.cos(f3) * 7.0F;
            this.blazeSticks[i].rotationPointZ = MathHelper.sin(f3) * 7.0F;
            f3++;
            i++;
            if (astring != null) {
                break;
            }
        }

        f3 = 0.47123894F + f * (float) Math.PI * -0.05F;
        i = 8;

        while (i < 12) {
            this.blazeSticks[i].rotationPointY = 11.0F + MathHelper.cos((i * 1.5F + f) * 0.5F);
            this.blazeSticks[i].rotationPointX = MathHelper.cos(f3) * 5.0F;
            this.blazeSticks[i].rotationPointZ = MathHelper.sin(f3) * 5.0F;
            f3++;
            i++;
            if (astring != null) {
                break;
            }
        }

        this.blazeHead.rotateAngleY = f1 / (180.0F / (float) Math.PI);
        this.blazeHead.rotateAngleX = f2 / (180.0F / (float) Math.PI);
    }

    public ModelBlaze() {
        this(ModelBase.getLegacyModelFlowMarker());
    }

    private ModelBlaze(String[] initialFlowMarker) {
        super();

        String[] astring1 = initialFlowMarker;

        this.blazeSticks = new ModelRenderer[12];
        String[] astring = astring1;
        int i = 0;

        while (i < this.blazeSticks.length) {
            this.blazeSticks[i] = new ModelRenderer(this, 0, 16);
            this.blazeSticks[i].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
            i++;
            if (astring != null) {
                break;
            }
        }

        this.blazeHead = new ModelRenderer(this, 0, 0);
        this.blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
    }
}
