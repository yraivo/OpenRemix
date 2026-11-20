package net.minecraft.client.renderer;

import optifine.OptiFineConfig;

import org.lwjgl.opengl.GL11;

import remix.util.reflection.ZeroArgReflectiveInstantiator;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GlStateManager {
    private static GlStateManagerColorMaterialState colorMaterialState;
    private static GlStateManagerCullState cullState;
    private static GlStateManagerFogState fogState;
    private static GlStateManagerBooleanState[] lightState;

    private static GlStateManagerColor colorState;
    private static GlStateManagerBooleanState normalizeState;
    private static GlStateManagerColorLogicState colorLogicState;
    private static GlStateManagerBooleanState rescaleNormalState;
    private static int activeShadeModel;
    public static boolean clearEnabled;
    private static GlStateManagerTexGenState texGenState;
    private static GlStateManagerDepthState depthState;
    private static GlStateManagerBlendState blendState;
    private static String[] jC;
    private static GlStateManagerClearState clearState;

    private static GlStateManagerPolygonOffsetState polygonOffsetState;
    private static GlStateManagerStencilState stencilState;
    private static int activeTextureUnit;
    private static GlStateManagerViewport field_179169_u;
    private static GlStateManagerAlphaState alphaState;
    private static GlStateManagerBooleanState lightingState;
    private static GlStateManagerColorMask colorMaskState;
    private static GlStateManagerTextureState[] textureState;

    public static void colorPointer(int p171_0, int p171_1, int p171_2, ByteBuffer byteBuffer) {
        org.lwjgl.opengl.GL11.glColorPointer(p171_0, p171_1, p171_2, byteBuffer);
    }

    public static void scale(float p171_0, float p171_1, float p171_2) {
        org.lwjgl.opengl.GL11.glScalef(p171_0, p171_1, p171_2);
    }

    public static void color(float p171_0, float p171_1, float p171_2) {
        net.minecraft.client.renderer.GlStateManager.color(p171_0, p171_1, p171_2, 1.0F);
    }

    public static void disableBooleanStateAt(int i) {
        net.minecraft.client.renderer.GlStateManager.lightState[i].setDisabled();
    }

    public static void disablePolygonOffset() {
        net.minecraft.client.renderer.GlStateManager.polygonOffsetState.field_179044_a
                .setDisabled();
    }

    public static void disableFog() {
        net.minecraft.client.renderer.GlStateManager.fogState.field_179049_a.setDisabled();
    }

    public static void disableBlend() {
        net.minecraft.client.renderer.GlStateManager.blendState.field_179213_a.setDisabled();
    }

    public static void mO() {
        GL11.glEnd();
    }

    public static void enableColorLogic() {
        net.minecraft.client.renderer.GlStateManager.colorLogicState.field_179197_a.setEnabled();
    }

    public static void enableColorMaterial() {
        net.minecraft.client.renderer.GlStateManager.colorMaterialState.field_179191_a.setEnabled();
    }

    public static void b(String[] astring) {
        jC = astring;
    }

    public static void translate(double p171_0, double p171_1, double p171_2) {
        org.lwjgl.opengl.GL11.glTranslated(p171_0, p171_1, p171_2);
    }

    public static void texCoordPointer(int p171_0, int p171_1, int p171_2, int p171_3) {
        org.lwjgl.opengl.GL11.glTexCoordPointer(p171_0, p171_1, p171_2, p171_3);
    }

    public static void enableFog() {
        net.minecraft.client.renderer.GlStateManager.fogState.field_179049_a.setEnabled();
    }

    public static void disableDepth() {
        net.minecraft.client.renderer.GlStateManager.depthState.depthTest.setDisabled();
    }

    public static void enableTexture2D() {
        net.minecraft.client.renderer.GlStateManager.textureState[
                net.minecraft.client.renderer.GlStateManager.activeTextureUnit]
                .texture2DState.setEnabled();
    }

    public static void disableColorLogic() {
        net.minecraft.client.renderer.GlStateManager.colorLogicState.field_179197_a.setDisabled();
    }

    public static void enablePolygonOffset() {
        net.minecraft.client.renderer.GlStateManager.polygonOffsetState.field_179044_a.setEnabled();
    }

    public static void setFogDensity(float p171_0) {
        if (p171_0 != net.minecraft.client.renderer.GlStateManager.fogState.field_179048_c) {
            net.minecraft.client.renderer.GlStateManager.fogState.field_179048_c = p171_0;
            org.lwjgl.opengl.GL11.glFogf(2914, p171_0);
        }
    }

    public static void multMatrix(FloatBuffer floatbuffer) {
        GL11.glMultMatrix(floatbuffer);
    }

    // region String decoding support

    // endregion

    public static void popAttrib() {
        GL11.glPopAttrib();
    }

    public static void setFog(int p171_0) {
        if (p171_0 != net.minecraft.client.renderer.GlStateManager.fogState.field_179047_b) {
            net.minecraft.client.renderer.GlStateManager.fogState.field_179047_b = p171_0;
            org.lwjgl.opengl.GL11.glFogi(2917, p171_0);
        }
    }

    public static void loadIdentity() {
        GL11.glLoadIdentity();
    }

    public static void rotate(float p171_0, float p171_1, float p171_2, float p171_3) {
        org.lwjgl.opengl.GL11.glRotatef(p171_0, p171_1, p171_2, p171_3);
    }

    public static void colorMaterial(int p171_0, int p171_1) {
        if (p171_0 != net.minecraft.client.renderer.GlStateManager.colorMaterialState.field_179189_b
                || p171_1
                        != net.minecraft.client.renderer.GlStateManager.colorMaterialState
                                .field_179190_c) {
            net.minecraft.client.renderer.GlStateManager.colorMaterialState.field_179189_b = p171_0;
            net.minecraft.client.renderer.GlStateManager.colorMaterialState.field_179190_c = p171_1;
            org.lwjgl.opengl.GL11.glColorMaterial(p171_0, p171_1);
        }
    }

    public static void alphaFunc(int func, float ref) {
        if (func != net.minecraft.client.renderer.GlStateManager.alphaState.func
                || ref != net.minecraft.client.renderer.GlStateManager.alphaState.ref) {
            net.minecraft.client.renderer.GlStateManager.alphaState.func = func;
            net.minecraft.client.renderer.GlStateManager.alphaState.ref = ref;
            org.lwjgl.opengl.GL11.glAlphaFunc(func, ref);
        }
    }

    public static void disableTexGenCoord(GlStateManagerTexGen glStateManagerTexGen) {
        texGenCoord(glStateManagerTexGen).field_179067_a.setDisabled();
    }

    public static void ab(int i) {
        GL11.glBegin(i);
    }

    public static void setFogStart(float p171_0) {
        if (p171_0 != net.minecraft.client.renderer.GlStateManager.fogState.field_179045_d) {
            net.minecraft.client.renderer.GlStateManager.fogState.field_179045_d = p171_0;
            org.lwjgl.opengl.GL11.glFogf(2915, p171_0);
        }
    }

    public static int getBoundTexture() {
        return net.minecraft.client.renderer.GlStateManager.textureState[
                net.minecraft.client.renderer.GlStateManager.activeTextureUnit]
                .textureName;
    }

    public static void checkBoundTexture() {
        if (OptiFineConfig.isMinecraftThread()) {
            int i = GL11.glGetInteger(34016);
            int j = GL11.glGetInteger(32873);
            int k = getActiveTextureUnit();
            int l = getBoundTexture();
            if (l > 0 && (i != k || j != l)) {
                OptiFineConfig.dbg(
                        "checkTexture: act: "
                                + k
                                + ", glAct: "
                                + i
                                + ", tex: "
                                + l
                                + ", glTex: "
                                + j);
            }
        }
    }

    public static void clearColor(float red, float green, float blue, float alpha) {
        if (red != net.minecraft.client.renderer.GlStateManager.clearState.field_179203_b.red
                || green
                        != net.minecraft.client.renderer.GlStateManager.clearState
                                .field_179203_b
                                .green
                || blue
                        != net.minecraft.client.renderer.GlStateManager.clearState
                                .field_179203_b
                                .blue
                || alpha
                        != net.minecraft.client.renderer.GlStateManager.clearState
                                .field_179203_b
                                .alpha) {
            net.minecraft.client.renderer.GlStateManager.clearState.field_179203_b.red = red;
            net.minecraft.client.renderer.GlStateManager.clearState.field_179203_b.green = green;
            net.minecraft.client.renderer.GlStateManager.clearState.field_179203_b.blue = blue;
            net.minecraft.client.renderer.GlStateManager.clearState.field_179203_b.alpha = alpha;
            org.lwjgl.opengl.GL11.glClearColor(red, green, blue, alpha);
        }
    }

    public static void enableBlend() {
        net.minecraft.client.renderer.GlStateManager.blendState.field_179213_a.setEnabled();
    }

    public static void enableNormalize() {
        net.minecraft.client.renderer.GlStateManager.normalizeState.setEnabled();
    }

    public static void vertexPointer(int p171_0, int p171_1, int p171_2, int p171_3) {
        org.lwjgl.opengl.GL11.glVertexPointer(p171_0, p171_1, p171_2, p171_3);
    }

    public static void setActiveTexture(int p171_0) {
        if (net.minecraft.client.renderer.GlStateManager.activeTextureUnit
                != p171_0 - net.minecraft.client.renderer.OpenGlHelper.defaultTexUnit) {
            net.minecraft.client.renderer.GlStateManager.activeTextureUnit =
                    p171_0 - net.minecraft.client.renderer.OpenGlHelper.defaultTexUnit;
            net.minecraft.client.renderer.OpenGlHelper.setActiveTexture(p171_0);
        }
    }

    public static void colorPointer(int p171_0, int p171_1, int p171_2, int p171_3) {
        org.lwjgl.opengl.GL11.glColorPointer(p171_0, p171_1, p171_2, p171_3);
    }

    public static void enableBooleanStateAt(int i) {
        net.minecraft.client.renderer.GlStateManager.lightState[i].setEnabled();
    }

    public static void tryBlendFuncSeparate(
            int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        if (srcFactor != net.minecraft.client.renderer.GlStateManager.blendState.srcFactor
                || dstFactor != net.minecraft.client.renderer.GlStateManager.blendState.dstFactor
                || srcFactorAlpha
                        != net.minecraft.client.renderer.GlStateManager.blendState.srcFactorAlpha
                || dstFactorAlpha
                        != net.minecraft.client.renderer.GlStateManager.blendState.dstFactorAlpha) {
            net.minecraft.client.renderer.GlStateManager.blendState.srcFactor = srcFactor;
            net.minecraft.client.renderer.GlStateManager.blendState.dstFactor = dstFactor;
            net.minecraft.client.renderer.GlStateManager.blendState.srcFactorAlpha = srcFactorAlpha;
            net.minecraft.client.renderer.GlStateManager.blendState.dstFactorAlpha = dstFactorAlpha;
            net.minecraft.client.renderer.OpenGlHelper.glBlendFunc(
                    srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        }
    }

    public static void vertex3d(double p171_0, double p171_1, double p171_2) {
        org.lwjgl.opengl.GL11.glVertex3d(p171_0, p171_1, p171_2);
    }

    public static void normalPointer(int p171_0, int p171_1, ByteBuffer byteBuffer) {
        org.lwjgl.opengl.GL11.glNormalPointer(p171_0, p171_1, byteBuffer);
    }

    public static void shadeModel(int activeShadeModelValue) {
        if (activeShadeModelValue
                != net.minecraft.client.renderer.GlStateManager.activeShadeModel) {
            net.minecraft.client.renderer.GlStateManager.activeShadeModel = activeShadeModelValue;
            org.lwjgl.opengl.GL11.glShadeModel(activeShadeModelValue);
        }
    }

    public static void getFloat(int i, FloatBuffer floatbuffer) {
        GL11.glGetFloat(i, floatbuffer);
    }

    private static GlStateManagerTexGenCoord texGenCoord(
            GlStateManagerTexGen glStateManagerTexGen) {
        switch (glStateManagerTexGen) {
            case aQa:
                return net.minecraft.client.renderer.GlStateManager.texGenState.field_179064_a;
            case aQg:
                return net.minecraft.client.renderer.GlStateManager.texGenState.field_179062_b;
            case aQf:
                return net.minecraft.client.renderer.GlStateManager.texGenState.field_179063_c;
            case aQb:
                return net.minecraft.client.renderer.GlStateManager.texGenState.field_179061_d;
            default:
                return net.minecraft.client.renderer.GlStateManager.texGenState.field_179064_a;
        }
    }

    public static void matrixMode(int i) {
        GL11.glMatrixMode(i);
    }

    public static void bindCurrentTexture() {
        GL11.glBindTexture(
                3553,
                net.minecraft.client.renderer.GlStateManager.textureState[
                        net.minecraft.client.renderer.GlStateManager.activeTextureUnit]
                        .textureName);
    }

    public static void doPolygonOffset(float p171_0, float p171_1) {
        if (p171_0 != net.minecraft.client.renderer.GlStateManager.polygonOffsetState.field_179043_c
                || p171_1
                        != net.minecraft.client.renderer.GlStateManager.polygonOffsetState
                                .field_179041_d) {
            net.minecraft.client.renderer.GlStateManager.polygonOffsetState.field_179043_c = p171_0;
            net.minecraft.client.renderer.GlStateManager.polygonOffsetState.field_179041_d = p171_1;
            org.lwjgl.opengl.GL11.glPolygonOffset(p171_0, p171_1);
        }
    }

    // region String decoding support

    // endregion

    public static void disableCull() {
        net.minecraft.client.renderer.GlStateManager.cullState.field_179054_a.setDisabled();
    }

    public static void disableLighting() {
        net.minecraft.client.renderer.GlStateManager.lightingState.setDisabled();
    }

    public static void depthFunc(int depthFunc) {
        if (depthFunc != net.minecraft.client.renderer.GlStateManager.depthState.depthFunc) {
            net.minecraft.client.renderer.GlStateManager.depthState.depthFunc = depthFunc;
            org.lwjgl.opengl.GL11.glDepthFunc(depthFunc);
        }
    }

    public static void popMatrix() {
        GL11.glPopMatrix();
    }

    public static void depthMask(boolean maskEnabled) {
        if (maskEnabled != net.minecraft.client.renderer.GlStateManager.depthState.maskEnabled) {
            net.minecraft.client.renderer.GlStateManager.depthState.maskEnabled = maskEnabled;
            org.lwjgl.opengl.GL11.glDepthMask(maskEnabled);
        }
    }

    public static void texGen(GlStateManagerTexGen glStateManagerTexGen, int i) {
        GlStateManagerTexGenCoord glStateManagerTexGenCoord = texGenCoord(glStateManagerTexGen);
        if (i != glStateManagerTexGenCoord.field_179066_c) {
            glStateManagerTexGenCoord.field_179066_c = i;
            GL11.glTexGeni(glStateManagerTexGenCoord.field_179065_b, 9472, i);
        }
    }

    public static void cullFace(int i) {
        if (i != net.minecraft.client.renderer.GlStateManager.cullState.field_179053_b) {
            net.minecraft.client.renderer.GlStateManager.cullState.field_179053_b = i;
            GL11.glCullFace(i);
        }
    }

    public static void enableTexGenCoord(GlStateManagerTexGen glStateManagerTexGen) {
        texGenCoord(glStateManagerTexGen).field_179067_a.setEnabled();
    }

    public static void scale(double p171_0, double p171_1, double p171_2) {
        org.lwjgl.opengl.GL11.glScaled(p171_0, p171_1, p171_2);
    }

    public static void pushMatrix() {
        GL11.glPushMatrix();
    }

    public static void enableRescaleNormal() {
        net.minecraft.client.renderer.GlStateManager.rescaleNormalState.setEnabled();
    }

    public static void deleteTextures(IntBuffer intbuffer) {
        int k = WorldRenderer.getLegacyRenderFlowToken();
        ((Buffer) intbuffer).rewind();
        int i = k;

        while (intbuffer.position() < intbuffer.limit()) {
            int j = intbuffer.get();
            deleteTexture(j);
            if (i != 0) {
                break;
            }
        }

        ((Buffer) intbuffer).rewind();
    }

    public static void blendFunc(int srcFactor, int dstFactor) {
        if (srcFactor != net.minecraft.client.renderer.GlStateManager.blendState.srcFactor
                || dstFactor != net.minecraft.client.renderer.GlStateManager.blendState.dstFactor) {
            net.minecraft.client.renderer.GlStateManager.blendState.srcFactor = srcFactor;
            net.minecraft.client.renderer.GlStateManager.blendState.dstFactor = dstFactor;
            org.lwjgl.opengl.GL11.glBlendFunc(srcFactor, dstFactor);
        }
    }

    public static void func_179105_a(
            GlStateManagerTexGen glStateManagerTexGen, int i, FloatBuffer floatbuffer) {
        GL11.glTexGen(texGenCoord(glStateManagerTexGen).field_179065_b, i, floatbuffer);
    }

    // region String decoding support

    // endregion

    public static void enableCull() {
        net.minecraft.client.renderer.GlStateManager.cullState.field_179054_a.setEnabled();
    }

    public static void setFogEnd(float p171_0) {
        if (p171_0 != net.minecraft.client.renderer.GlStateManager.fogState.field_179046_e) {
            net.minecraft.client.renderer.GlStateManager.fogState.field_179046_e = p171_0;
            org.lwjgl.opengl.GL11.glFogf(2916, p171_0);
        }
    }

    public static void enableAlpha() {
        net.minecraft.client.renderer.GlStateManager.alphaState.field_179208_a.setEnabled();
    }

    public static void vertexPointer(int p171_0, int p171_1, int p171_2, ByteBuffer byteBuffer) {
        org.lwjgl.opengl.GL11.glVertexPointer(p171_0, p171_1, p171_2, byteBuffer);
    }

    public static void disableAlpha() {
        net.minecraft.client.renderer.GlStateManager.alphaState.field_179208_a.setDisabled();
    }

    public static void resetColor() {
        net.minecraft.client.renderer.GlStateManager.colorState.red =
                net.minecraft.client.renderer.GlStateManager.colorState.green =
                        net.minecraft.client.renderer.GlStateManager.colorState.blue =
                                net.minecraft.client.renderer.GlStateManager.colorState.alpha =
                                        -1.0F;
    }

    public static void deleteTexture(int i) {
        if (i != 0) {
            GL11.glDeleteTextures(i);

            for (GlStateManagerTextureState glStateManagerTextureState :
                    net.minecraft.client.renderer.GlStateManager.textureState) {
                if (glStateManagerTextureState.textureName == i) {
                    glStateManagerTextureState.textureName = 0;
                }
            }
        }
    }

    public static void disableNormalize() {
        net.minecraft.client.renderer.GlStateManager.normalizeState.setDisabled();
    }

    public static void disableRescaleNormal() {
        net.minecraft.client.renderer.GlStateManager.rescaleNormalState.setDisabled();
    }

    public static String[] xr() {
        return jC;
    }

    public static void ortho(
            double p171_0,
            double p171_1,
            double p171_2,
            double p171_3,
            double p171_4,
            double p171_5) {
        org.lwjgl.opengl.GL11.glOrtho(p171_0, p171_1, p171_2, p171_3, p171_4, p171_5);
    }

    public static void disableTexture2D() {
        net.minecraft.client.renderer.GlStateManager.textureState[
                net.minecraft.client.renderer.GlStateManager.activeTextureUnit]
                .texture2DState.setDisabled();
    }

    public static void pushAttrib() {
        GL11.glPushAttrib(8268 - ZeroArgReflectiveInstantiator.cz);
    }

    public static void clearDepth(double p171_0) {
        if (p171_0 != net.minecraft.client.renderer.GlStateManager.clearState.field_179205_a) {
            net.minecraft.client.renderer.GlStateManager.clearState.field_179205_a = p171_0;
            org.lwjgl.opengl.GL11.glClearDepth(p171_0);
        }
    }

    public static int getActiveTextureUnit() {
        return OpenGlHelper.defaultTexUnit
                + net.minecraft.client.renderer.GlStateManager.activeTextureUnit;
    }

    public static void viewport(int p171_0, int p171_1, int p171_2, int p171_3) {
        if (p171_0 != net.minecraft.client.renderer.GlStateManager.field_179169_u.field_179058_a
                || p171_1
                        != net.minecraft.client.renderer.GlStateManager.field_179169_u
                                .field_179056_b
                || p171_2
                        != net.minecraft.client.renderer.GlStateManager.field_179169_u
                                .field_179057_c
                || p171_3
                        != net.minecraft.client.renderer.GlStateManager.field_179169_u
                                .field_179055_d) {
            net.minecraft.client.renderer.GlStateManager.field_179169_u.field_179058_a = p171_0;
            net.minecraft.client.renderer.GlStateManager.field_179169_u.field_179056_b = p171_1;
            net.minecraft.client.renderer.GlStateManager.field_179169_u.field_179057_c = p171_2;
            net.minecraft.client.renderer.GlStateManager.field_179169_u.field_179055_d = p171_3;
            org.lwjgl.opengl.GL11.glViewport(p171_0, p171_1, p171_2, p171_3);
        }
    }

    static {
        b(null);
        net.minecraft.client.renderer.GlStateManager.alphaState =
                new GlStateManagerAlphaState(null);
        net.minecraft.client.renderer.GlStateManager.lightingState =
                new GlStateManagerBooleanState(2896);
        net.minecraft.client.renderer.GlStateManager.lightState = new GlStateManagerBooleanState[8];
        net.minecraft.client.renderer.GlStateManager.colorMaterialState =
                new GlStateManagerColorMaterialState(null);
        net.minecraft.client.renderer.GlStateManager.blendState =
                new GlStateManagerBlendState(null);
        net.minecraft.client.renderer.GlStateManager.depthState =
                new GlStateManagerDepthState(null);
        net.minecraft.client.renderer.GlStateManager.fogState = new GlStateManagerFogState(null);
        net.minecraft.client.renderer.GlStateManager.cullState = new GlStateManagerCullState(null);
        net.minecraft.client.renderer.GlStateManager.polygonOffsetState =
                new GlStateManagerPolygonOffsetState(null);
        net.minecraft.client.renderer.GlStateManager.colorLogicState =
                new GlStateManagerColorLogicState(null);
        net.minecraft.client.renderer.GlStateManager.texGenState =
                new GlStateManagerTexGenState(null);
        net.minecraft.client.renderer.GlStateManager.clearState =
                new GlStateManagerClearState(null);
        net.minecraft.client.renderer.GlStateManager.stencilState =
                new GlStateManagerStencilState(null);
        net.minecraft.client.renderer.GlStateManager.normalizeState =
                new GlStateManagerBooleanState(2977);
        net.minecraft.client.renderer.GlStateManager.activeTextureUnit = 0;
        net.minecraft.client.renderer.GlStateManager.textureState =
                new GlStateManagerTextureState[32];
        net.minecraft.client.renderer.GlStateManager.activeShadeModel = 7425;
        net.minecraft.client.renderer.GlStateManager.rescaleNormalState =
                new GlStateManagerBooleanState(32826);
        net.minecraft.client.renderer.GlStateManager.colorMaskState =
                new GlStateManagerColorMask(null);
        net.minecraft.client.renderer.GlStateManager.colorState = new GlStateManagerColor();
        net.minecraft.client.renderer.GlStateManager.field_179169_u =
                new GlStateManagerViewport(null);
        net.minecraft.client.renderer.GlStateManager.clearEnabled = true;
        for (int l = 0; l < 8; l++) {
            net.minecraft.client.renderer.GlStateManager.lightState[l] =
                    new GlStateManagerBooleanState(16384 + l);
        }
        for (int j1 = 0;
                j1 < net.minecraft.client.renderer.GlStateManager.textureState.length;
                j1++) {
            net.minecraft.client.renderer.GlStateManager.textureState[j1] =
                    new GlStateManagerTextureState(null);
        }
    }

    public static void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        if (red != net.minecraft.client.renderer.GlStateManager.colorMaskState.red
                || green != net.minecraft.client.renderer.GlStateManager.colorMaskState.green
                || blue != net.minecraft.client.renderer.GlStateManager.colorMaskState.blue
                || alpha != net.minecraft.client.renderer.GlStateManager.colorMaskState.alpha) {
            net.minecraft.client.renderer.GlStateManager.colorMaskState.red = red;
            net.minecraft.client.renderer.GlStateManager.colorMaskState.green = green;
            net.minecraft.client.renderer.GlStateManager.colorMaskState.blue = blue;
            net.minecraft.client.renderer.GlStateManager.colorMaskState.alpha = alpha;
            org.lwjgl.opengl.GL11.glColorMask(red, green, blue, alpha);
        }
    }

    public static void clear(int p171_0) {
        if (net.minecraft.client.renderer.GlStateManager.clearEnabled) {
            org.lwjgl.opengl.GL11.glClear(p171_0);
        }
    }

    public static void glLineWidth(float f) {
        GL11.glLineWidth(f);
    }

    public static int generateTexture() {
        return GL11.glGenTextures();
    }

    public static void colorLogicOp(int i) {
        if (i != net.minecraft.client.renderer.GlStateManager.colorLogicState.field_179196_b) {
            net.minecraft.client.renderer.GlStateManager.colorLogicState.field_179196_b = i;
            GL11.glLogicOp(i);
        }
    }

    public static void callList(int i) {
        GL11.glCallList(i);
    }

    public static void vertex3f(float f, float f1, float f2) {
        GL11.glVertex3f(f, f1, f2);
    }

    public static void translate(float p171_0, float p171_1, float p171_2) {
        org.lwjgl.opengl.GL11.glTranslatef(p171_0, p171_1, p171_2);
    }

    public static void texCoordPointer(int p171_0, int p171_1, int p171_2, ByteBuffer byteBuffer) {
        org.lwjgl.opengl.GL11.glTexCoordPointer(p171_0, p171_1, p171_2, byteBuffer);
    }

    public static void bindTexture(int textureName) {
        if (textureName
                != net.minecraft.client.renderer.GlStateManager.textureState[
                        net.minecraft.client.renderer.GlStateManager.activeTextureUnit]
                        .textureName) {
            net.minecraft.client.renderer.GlStateManager.textureState[
                            net.minecraft.client.renderer.GlStateManager.activeTextureUnit]
                            .textureName =
                    textureName;
            GL11.glBindTexture(3553, textureName);
        }
    }

    public static void disableColorMaterial() {
        net.minecraft.client.renderer.GlStateManager.colorMaterialState.field_179191_a
                .setDisabled();
    }

    public static void enableLighting() {
        net.minecraft.client.renderer.GlStateManager.lightingState.setEnabled();
    }

    public static void enableDepth() {
        net.minecraft.client.renderer.GlStateManager.depthState.depthTest.setEnabled();
    }

    public static void color(float red, float green, float blue, float alpha) {
        if (red != net.minecraft.client.renderer.GlStateManager.colorState.red
                || green != net.minecraft.client.renderer.GlStateManager.colorState.green
                || blue != net.minecraft.client.renderer.GlStateManager.colorState.blue
                || alpha != net.minecraft.client.renderer.GlStateManager.colorState.alpha) {
            net.minecraft.client.renderer.GlStateManager.colorState.red = red;
            net.minecraft.client.renderer.GlStateManager.colorState.green = green;
            net.minecraft.client.renderer.GlStateManager.colorState.blue = blue;
            net.minecraft.client.renderer.GlStateManager.colorState.alpha = alpha;
            org.lwjgl.opengl.GL11.glColor4f(red, green, blue, alpha);
        }
    }
}
