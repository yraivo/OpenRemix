package net.minecraft.client.renderer.culling;

import net.minecraft.entity.Entity;

public class ClippingHelper {
    public float[] clippingMatrix;
    public float[][] frustum = new float[6][4];
    public float[] modelviewMatrix;
    public float[] projectionMatrix = new float[16];
    private static int ef;

    public static int method_beea3e16fe3ec0995eb9() {
        int i = method_7eec03da329a017ec143();
        return i == 0 ? 18 : 0;
    }

    public ClippingHelper() {
        this.modelviewMatrix = new float[16];
        this.clippingMatrix = new float[16];
    }

    private float dot(float[] afloat, float f, float f1, float f2) {
        return afloat[0] * f + afloat[1] * f1 + afloat[2] * f2 + afloat[3];
    }

    public static void b(int i) {
        ef = i;
    }

    public static int method_7eec03da329a017ec143() {
        return ef;
    }

    public boolean isBoxInFrustum(
            double d0, double d1, double d2, double d3, double d4, double d5) {
        float f = (float) d0;
        float f1 = (float) d1;
        float f2 = (float) d2;
        float f3 = (float) d3;
        int k = method_7eec03da329a017ec143();
        float f4 = (float) d4;
        float f5 = (float) d5;
        int j = 0;
        int i = k;

        while (j < 6) {
            float[] afloat = this.frustum[j];
            if (this.dot(afloat, f, f1, f2) <= 0.0F
                    && this.dot(afloat, f3, f1, f2) <= 0.0F
                    && this.dot(afloat, f, f4, f2) <= 0.0F
                    && this.dot(afloat, f3, f4, f2) <= 0.0F
                    && this.dot(afloat, f, f1, f5) <= 0.0F
                    && this.dot(afloat, f3, f1, f5) <= 0.0F
                    && this.dot(afloat, f, f4, f5) <= 0.0F
                    && this.dot(afloat, f3, f4, f5) <= 0.0F) {
                return false;
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(++i);
        }

        return true;
    }

    static {
        if (method_7eec03da329a017ec143() != 0) {
            b(119);
        }
    }
}
