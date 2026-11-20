package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelEnderMite extends ModelBase {
    private static final int[][] ava;
    private static final int mF;
    private ModelRenderer[] avb;
    private static final int[][] avc;

    static {
        avc = new int[][] {{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
        ava = new int[][] {{0, 0}, {0, 5}, {0, 14}, {0, 18}};
        mF = ModelEnderMite.avc.length;
    }

    public ModelEnderMite() {
        this(ModelBase.getLegacyModelFlowMarker());
    }

    private ModelEnderMite(String[] initialFlowMarker) {
        super();

        String[] astring1 = initialFlowMarker;

        String[] astring = astring1;
        this.avb = new ModelRenderer[mF];
        float f = -3.5F;
        int i = 0;

        while (i < this.avb.length) {
            this.avb[i] = new ModelRenderer(this, ava[i][0], ava[i][1]);
            this.avb[i].addBox(
                    avc[i][0] * -0.5F, 0.0F, avc[i][2] * -0.5F, avc[i][0], avc[i][1], avc[i][2]);
            this.avb[i].setRotationPoint(0.0F, 24 - avc[i][1], f);
            if (i < this.avb.length - 1) {
                f += (avc[i][2] + avc[i + 1][2]) * 0.5F;
            }

            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        int i = 0;
        String[] astring = astring1;

        while (i < this.avb.length) {
            this.avb[i].render(f5);
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

        while (i < this.avb.length) {
            this.avb[i].rotateAngleY =
                    MathHelper.cos(f * 0.9F + i * 0.15F * (float) Math.PI)
                            * (float) Math.PI
                            * 0.01F
                            * (1 + Math.abs(i - 2));
            this.avb[i].rotationPointX =
                    MathHelper.sin(f * 0.9F + i * 0.15F * (float) Math.PI)
                            * (float) Math.PI
                            * 0.1F
                            * Math.abs(i - 2);
            i++;
            if (astring != null) {
                break;
            }
        }
    }
}
