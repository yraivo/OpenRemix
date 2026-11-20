package shadersmod.client;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.util.MathHelper;

public class ClippingHelperShadow extends ClippingHelper {
    int shadowClipPlaneCount;
    float[] matInvMP;
    private static ClippingHelperShadow instance = new ClippingHelperShadow();
    float[][] shadowClipPlanes;
    float[] vecIntersection;
    float[] frustumTest = new float[6];

    public static ClippingHelper getInstance() {
        shadersmod.client.ClippingHelperShadow.instance.init();
        return shadersmod.client.ClippingHelperShadow.instance;
    }

    public boolean isBoxInFrustum(
            double d0, double d1, double d2, double d3, double d4, double d5) {
        int[] aint1 = ShaderOption.lz();
        int i = 0;
        int[] aint = aint1;

        while (i < this.shadowClipPlaneCount) {
            float[] afloat = this.shadowClipPlanes[i];
            if (this.dot4(afloat, d0, d1, d2) <= 0.0
                    && this.dot4(afloat, d3, d1, d2) <= 0.0
                    && this.dot4(afloat, d0, d4, d2) <= 0.0
                    && this.dot4(afloat, d3, d4, d2) <= 0.0
                    && this.dot4(afloat, d0, d1, d5) <= 0.0
                    && this.dot4(afloat, d3, d1, d5) <= 0.0
                    && this.dot4(afloat, d0, d4, d5) <= 0.0
                    && this.dot4(afloat, d3, d4, d5) <= 0.0) {
                return false;
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        return true;
    }

    private void copyPlane(float[] p171_0, float[] p171_1) {
        p171_0[0] = p171_1[0];
        p171_0[1] = p171_1[1];
        p171_0[2] = p171_1[2];
        p171_0[3] = p171_1[3];
    }

    private void normalize3(float[] afloat) {
        float f =
                MathHelper.sqrt(
                        afloat[0] * afloat[0] + afloat[1] * afloat[1] + afloat[2] * afloat[2]);
        afloat[0] /= f;
        afloat[1] /= f;
        afloat[2] /= f;
        afloat[3] /= f;
    }

    public void init() {
        float[] afloat = this.projectionMatrix;
        float[] afloat1 = this.modelviewMatrix;
        float[] afloat2 = this.clippingMatrix;
        System.arraycopy(Shaders.faProjection, 0, afloat, 0, 16);
        System.arraycopy(Shaders.faModelView, 0, afloat1, 0, 16);
        ShaderMath.multiplyMat4xMat4(afloat2, afloat1, afloat);
        this.assignPlane(
                this.frustum[0],
                afloat2[3] - afloat2[0],
                afloat2[7] - afloat2[4],
                afloat2[11] - afloat2[8],
                afloat2[15] - afloat2[12]);
        this.assignPlane(
                this.frustum[1],
                afloat2[3] + afloat2[0],
                afloat2[7] + afloat2[4],
                afloat2[11] + afloat2[8],
                afloat2[15] + afloat2[12]);
        this.assignPlane(
                this.frustum[2],
                afloat2[3] + afloat2[1],
                afloat2[7] + afloat2[5],
                afloat2[11] + afloat2[9],
                afloat2[15] + afloat2[13]);
        this.assignPlane(
                this.frustum[3],
                afloat2[3] - afloat2[1],
                afloat2[7] - afloat2[5],
                afloat2[11] - afloat2[9],
                afloat2[15] - afloat2[13]);
        this.assignPlane(
                this.frustum[4],
                afloat2[3] - afloat2[2],
                afloat2[7] - afloat2[6],
                afloat2[11] - afloat2[10],
                afloat2[15] - afloat2[14]);
        this.assignPlane(
                this.frustum[5],
                afloat2[3] + afloat2[2],
                afloat2[7] + afloat2[6],
                afloat2[11] + afloat2[10],
                afloat2[15] + afloat2[14]);
        float[] afloat3 = Shaders.shadowLightPositionVector;
        float f = (float) this.dot3(this.frustum[0], afloat3);
        float f1 = (float) this.dot3(this.frustum[1], afloat3);
        float f2 = (float) this.dot3(this.frustum[2], afloat3);
        float f3 = (float) this.dot3(this.frustum[3], afloat3);
        float f4 = (float) this.dot3(this.frustum[4], afloat3);
        float f5 = (float) this.dot3(this.frustum[5], afloat3);
        this.shadowClipPlaneCount = 0;
        if (f >= 0.0F) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0]);
            if (f > 0.0F) {
                if (f2 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[2], afloat3);
                }

                if (f3 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[3], afloat3);
                }

                if (f4 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[4], afloat3);
                }

                if (f5 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[5], afloat3);
                }
            }
        }

        if (f1 >= 0.0F) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1]);
            if (f1 > 0.0F) {
                if (f2 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[2], afloat3);
                }

                if (f3 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[3], afloat3);
                }

                if (f4 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[4], afloat3);
                }

                if (f5 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[5], afloat3);
                }
            }
        }

        if (f2 >= 0.0F) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2]);
            if (f2 > 0.0F) {
                if (f < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[0], afloat3);
                }

                if (f1 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[1], afloat3);
                }

                if (f4 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[4], afloat3);
                }

                if (f5 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[5], afloat3);
                }
            }
        }

        if (f3 >= 0.0F) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3]);
            if (f3 > 0.0F) {
                if (f < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[0], afloat3);
                }

                if (f1 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[1], afloat3);
                }

                if (f4 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[4], afloat3);
                }

                if (f5 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[5], afloat3);
                }
            }
        }

        if (f4 >= 0.0F) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4]);
            if (f4 > 0.0F) {
                if (f < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[0], afloat3);
                }

                if (f1 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[1], afloat3);
                }

                if (f2 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[2], afloat3);
                }

                if (f3 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[3], afloat3);
                }
            }
        }

        if (f5 >= 0.0F) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5]);
            if (f5 > 0.0F) {
                if (f < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[0], afloat3);
                }

                if (f1 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[1], afloat3);
                }

                if (f2 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[2], afloat3);
                }

                if (f3 < 0.0F) {
                    this.makeShadowPlane(
                            this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[3], afloat3);
                }
            }
        }
    }

    private float distance(
            float f, float f1, float f2, float f3, float f4, float f5) {
        return this.length(f - f3, f1 - f4, f2 - f5);
    }

    private void method_3542487022f2470971d9(float[] afloat) {
        float f =
                MathHelper.sqrt(
                        afloat[0] * afloat[0] + afloat[1] * afloat[1] + afloat[2] * afloat[2]);
        if (f == 0.0F) {
            f = 1.0F;
        }

        afloat[0] /= f;
        afloat[1] /= f;
        afloat[2] /= f;
    }

    private void addShadowClipPlane(float[] p171_0) {
        this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], p171_0);
    }

    private void makeShadowPlane(
            float[] p171_0, float[] p171_1, float[] p171_2, float[] p171_3) {
        this.cross3(this.vecIntersection, p171_1, p171_2);
        this.cross3(p171_0, this.vecIntersection, p171_3);
        this.method_3542487022f2470971d9(p171_0);
        float f = (float) this.dot3(p171_1, p171_2);
        float f1 = (float) this.dot3(p171_0, p171_2);
        float f2 =
                this.distance(
                        p171_0[0],
                        p171_0[1],
                        p171_0[2],
                        p171_2[0] * f1,
                        p171_2[1] * f1,
                        p171_2[2] * f1);
        float f3 =
                this.distance(
                        p171_1[0],
                        p171_1[1],
                        p171_1[2],
                        p171_2[0] * f,
                        p171_2[1] * f,
                        p171_2[2] * f);
        float f4 = f2 / f3;
        float f5 = (float) this.dot3(p171_0, p171_1);
        float f6 =
                this.distance(
                        p171_0[0],
                        p171_0[1],
                        p171_0[2],
                        p171_1[0] * f5,
                        p171_1[1] * f5,
                        p171_1[2] * f5);
        float f7 =
                this.distance(
                        p171_2[0],
                        p171_2[1],
                        p171_2[2],
                        p171_1[0] * f,
                        p171_1[1] * f,
                        p171_1[2] * f);
        float f8 = f6 / f7;
        p171_0[3] = p171_1[3] * f4 + p171_2[3] * f8;
    }

    private void assignPlane(
            float[] p171_0, float p171_1, float p171_2, float p171_3, float p171_4) {
        float f4 = (float) java.lang.Math.sqrt(p171_1 * p171_1 + p171_2 * p171_2 + p171_3 * p171_3);
        p171_0[0] = p171_1 / f4;
        p171_0[1] = p171_2 / f4;
        p171_0[2] = p171_3 / f4;
        p171_0[3] = p171_4 / f4;
    }

    private float length(float p171_0, float p171_1, float p171_2) {
        return (float) java.lang.Math.sqrt(p171_0 * p171_0 + p171_1 * p171_1 + p171_2 * p171_2);
    }

    private double dot4(
            float[] p171_0, double p171_1, double p171_2, double p171_3) {
        return p171_0[0] * p171_1 + p171_0[1] * p171_2 + p171_0[2] * p171_3 + p171_0[3];
    }

    private void cross3(float[] p171_0, float[] p171_1, float[] p171_2) {
        p171_0[0] = p171_1[1] * p171_2[2] - p171_1[2] * p171_2[1];
        p171_0[1] = p171_1[2] * p171_2[0] - p171_1[0] * p171_2[2];
        p171_0[2] = p171_1[0] * p171_2[1] - p171_1[1] * p171_2[0];
    }

    private double dot3(float[] p171_0, float[] p171_1) {
        return (double) p171_0[0] * p171_1[0]
                + (double) p171_0[1] * p171_1[1]
                + (double) p171_0[2] * p171_1[2];
    }

    public ClippingHelperShadow() {
        this.shadowClipPlanes = new float[10][4];
        this.matInvMP = new float[16];
        this.vecIntersection = new float[4];
    }
}
