package shadersmod.client;

import net.minecraft.entity.Entity;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class ShaderMath {
    static void invertMat4FBFA(
            FloatBuffer floatbuffer, FloatBuffer floatbuffer1, float[] afloat, float[] afloat1) {
        floatbuffer1.get(afloat1);
        invertMat4(afloat, afloat1);
        floatbuffer.put(afloat);
    }

    static void multiplyMat4xMat4(float[] afloat, float[] afloat1, float[] afloat2) {
        int[] aint1 = ShaderOption.lz();
        int i = 0;
        int[] aint = aint1;

        while (i < 4) {
            int j = 0;

            while (true) {
                if (j < 4) {
                    afloat[4 * i + j] =
                            afloat1[4 * i + 0] * afloat2[0 + j]
                                    + afloat1[4 * i + 1] * afloat2[4 + j]
                                    + afloat1[4 * i + 2] * afloat2[8 + j]
                                    + afloat1[4 * i + 3] * afloat2[12 + j];
                    j++;
                    if (aint == null) {
                        continue;
                    }
                }

                i++;
                if (aint != null) {
                    return;
                }
                break;
            }
        }
    }

    static void invertMat4(float[] afloat, float[] afloat1) {
        afloat[0] =
                afloat1[5] * afloat1[10] * afloat1[15]
                        - afloat1[5] * afloat1[11] * afloat1[14]
                        - afloat1[9] * afloat1[6] * afloat1[15]
                        + afloat1[9] * afloat1[7] * afloat1[14]
                        + afloat1[13] * afloat1[6] * afloat1[11]
                        - afloat1[13] * afloat1[7] * afloat1[10];
        afloat[1] =
                -afloat1[1] * afloat1[10] * afloat1[15]
                        + afloat1[1] * afloat1[11] * afloat1[14]
                        + afloat1[9] * afloat1[2] * afloat1[15]
                        - afloat1[9] * afloat1[3] * afloat1[14]
                        - afloat1[13] * afloat1[2] * afloat1[11]
                        + afloat1[13] * afloat1[3] * afloat1[10];
        afloat[2] =
                afloat1[1] * afloat1[6] * afloat1[15]
                        - afloat1[1] * afloat1[7] * afloat1[14]
                        - afloat1[5] * afloat1[2] * afloat1[15]
                        + afloat1[5] * afloat1[3] * afloat1[14]
                        + afloat1[13] * afloat1[2] * afloat1[7]
                        - afloat1[13] * afloat1[3] * afloat1[6];
        afloat[3] =
                -afloat1[1] * afloat1[6] * afloat1[11]
                        + afloat1[1] * afloat1[7] * afloat1[10]
                        + afloat1[5] * afloat1[2] * afloat1[11]
                        - afloat1[5] * afloat1[3] * afloat1[10]
                        - afloat1[9] * afloat1[2] * afloat1[7]
                        + afloat1[9] * afloat1[3] * afloat1[6];
        afloat[4] =
                -afloat1[4] * afloat1[10] * afloat1[15]
                        + afloat1[4] * afloat1[11] * afloat1[14]
                        + afloat1[8] * afloat1[6] * afloat1[15]
                        - afloat1[8] * afloat1[7] * afloat1[14]
                        - afloat1[12] * afloat1[6] * afloat1[11]
                        + afloat1[12] * afloat1[7] * afloat1[10];
        int[] aint1 = ShaderOption.lz();
        afloat[5] =
                afloat1[0] * afloat1[10] * afloat1[15]
                        - afloat1[0] * afloat1[11] * afloat1[14]
                        - afloat1[8] * afloat1[2] * afloat1[15]
                        + afloat1[8] * afloat1[3] * afloat1[14]
                        + afloat1[12] * afloat1[2] * afloat1[11]
                        - afloat1[12] * afloat1[3] * afloat1[10];
        afloat[6] =
                -afloat1[0] * afloat1[6] * afloat1[15]
                        + afloat1[0] * afloat1[7] * afloat1[14]
                        + afloat1[4] * afloat1[2] * afloat1[15]
                        - afloat1[4] * afloat1[3] * afloat1[14]
                        - afloat1[12] * afloat1[2] * afloat1[7]
                        + afloat1[12] * afloat1[3] * afloat1[6];
        int[] aint = aint1;
        afloat[7] =
                afloat1[0] * afloat1[6] * afloat1[11]
                        - afloat1[0] * afloat1[7] * afloat1[10]
                        - afloat1[4] * afloat1[2] * afloat1[11]
                        + afloat1[4] * afloat1[3] * afloat1[10]
                        + afloat1[8] * afloat1[2] * afloat1[7]
                        - afloat1[8] * afloat1[3] * afloat1[6];
        afloat[8] =
                afloat1[4] * afloat1[9] * afloat1[15]
                        - afloat1[4] * afloat1[11] * afloat1[13]
                        - afloat1[8] * afloat1[5] * afloat1[15]
                        + afloat1[8] * afloat1[7] * afloat1[13]
                        + afloat1[12] * afloat1[5] * afloat1[11]
                        - afloat1[12] * afloat1[7] * afloat1[9];
        afloat[9] =
                -afloat1[0] * afloat1[9] * afloat1[15]
                        + afloat1[0] * afloat1[11] * afloat1[13]
                        + afloat1[8] * afloat1[1] * afloat1[15]
                        - afloat1[8] * afloat1[3] * afloat1[13]
                        - afloat1[12] * afloat1[1] * afloat1[11]
                        + afloat1[12] * afloat1[3] * afloat1[9];
        afloat[10] =
                afloat1[0] * afloat1[5] * afloat1[15]
                        - afloat1[0] * afloat1[7] * afloat1[13]
                        - afloat1[4] * afloat1[1] * afloat1[15]
                        + afloat1[4] * afloat1[3] * afloat1[13]
                        + afloat1[12] * afloat1[1] * afloat1[7]
                        - afloat1[12] * afloat1[3] * afloat1[5];
        afloat[11] =
                -afloat1[0] * afloat1[5] * afloat1[11]
                        + afloat1[0] * afloat1[7] * afloat1[9]
                        + afloat1[4] * afloat1[1] * afloat1[11]
                        - afloat1[4] * afloat1[3] * afloat1[9]
                        - afloat1[8] * afloat1[1] * afloat1[7]
                        + afloat1[8] * afloat1[3] * afloat1[5];
        afloat[12] =
                -afloat1[4] * afloat1[9] * afloat1[14]
                        + afloat1[4] * afloat1[10] * afloat1[13]
                        + afloat1[8] * afloat1[5] * afloat1[14]
                        - afloat1[8] * afloat1[6] * afloat1[13]
                        - afloat1[12] * afloat1[5] * afloat1[10]
                        + afloat1[12] * afloat1[6] * afloat1[9];
        afloat[13] =
                afloat1[0] * afloat1[9] * afloat1[14]
                        - afloat1[0] * afloat1[10] * afloat1[13]
                        - afloat1[8] * afloat1[1] * afloat1[14]
                        + afloat1[8] * afloat1[2] * afloat1[13]
                        + afloat1[12] * afloat1[1] * afloat1[10]
                        - afloat1[12] * afloat1[2] * afloat1[9];
        afloat[14] =
                -afloat1[0] * afloat1[5] * afloat1[14]
                        + afloat1[0] * afloat1[6] * afloat1[13]
                        + afloat1[4] * afloat1[1] * afloat1[14]
                        - afloat1[4] * afloat1[2] * afloat1[13]
                        - afloat1[12] * afloat1[1] * afloat1[6]
                        + afloat1[12] * afloat1[2] * afloat1[5];
        afloat[15] =
                afloat1[0] * afloat1[5] * afloat1[10]
                        - afloat1[0] * afloat1[6] * afloat1[9]
                        - afloat1[4] * afloat1[1] * afloat1[10]
                        + afloat1[4] * afloat1[2] * afloat1[9]
                        + afloat1[8] * afloat1[1] * afloat1[6]
                        - afloat1[8] * afloat1[2] * afloat1[5];
        float f =
                afloat1[0] * afloat[0]
                        + afloat1[1] * afloat[4]
                        + afloat1[2] * afloat[8]
                        + afloat1[3] * afloat[12];
        if (f != 0.0) {
            int i = 0;

            while (i < 16) {
                afloat[i] /= f;
                i++;
                if (aint != null) {
                    Entity.setLegacyGlobalFlowMarker(new String[5]);
                    break;
                }
            }

            if (aint == null) {
                return;
            }
        }

        Arrays.fill(afloat, 0.0F);
    }

    static void multiplyMat4xVec4(float[] afloat, float[] afloat1, float[] afloat2) {
        afloat[0] =
                afloat1[0] * afloat2[0]
                        + afloat1[4] * afloat2[1]
                        + afloat1[8] * afloat2[2]
                        + afloat1[12] * afloat2[3];
        afloat[1] =
                afloat1[1] * afloat2[0]
                        + afloat1[5] * afloat2[1]
                        + afloat1[9] * afloat2[2]
                        + afloat1[13] * afloat2[3];
        afloat[2] =
                afloat1[2] * afloat2[0]
                        + afloat1[6] * afloat2[1]
                        + afloat1[10] * afloat2[2]
                        + afloat1[14] * afloat2[3];
        afloat[3] =
                afloat1[3] * afloat2[0]
                        + afloat1[7] * afloat2[1]
                        + afloat1[11] * afloat2[2]
                        + afloat1[15] * afloat2[3];
    }
}
