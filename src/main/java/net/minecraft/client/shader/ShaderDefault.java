package net.minecraft.client.shader;

import remix.compat.vecmath.Matrix4f;

public class ShaderDefault extends ShaderUniform {

    private static String mC;

    public void set(
            Matrix4f matrix4f) {}

    public ShaderDefault() {
        super(mC, 4, 1, (ShaderManager) null);
    }

    public void set(float f, float f1, float f2) {}

    public void setFloatsForUniformType(float f, float f1, float f2, float f3) {}

    public void set(float f) {}

    public void set(float f, float f1) {}

    public void set(float[] afloat) {}

    public void set(int i, int j, int k, int l) {}

    public void setFloat4(float f, float f1, float f2, float f3) {}

    public void set(
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6,
            float f7,
            float f8,
            float f9,
            float f10,
            float f11,
            float f12,
            float f13,
            float f14,
            float f15) {}

    // region String decoding support

    // endregion

    static {
        mC = "dummy";
    }

    // region String decoding support

    // endregion
}
