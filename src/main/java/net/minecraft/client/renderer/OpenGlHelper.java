package net.minecraft.client.renderer;

import net.minecraft.client.MinecraftClient;

import optifine.OptiFineConfig;

import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTBlendFuncSeparate;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class OpenGlHelper {

    public static int c;
    public static int GL_FRAGMENT_SHADER;
    public static int GL_INTERPOLATE;
    private static String[] packedTextFragments;
    public static int GL_ARRAY_BUFFER;
    public static int GL_OPERAND1_ALPHA;
    private static boolean arbShaders;
    public static int GL_LINK_STATUS;
    public static int GL_FB_INCOMPLETE_DRAW_BUFFER;
    public static boolean ahv;
    public static int GL_FB_INCOMPLETE_READ_BUFFER;
    public static float Pu;
    public static int lightmapTexUnit;
    public static int GL_SOURCE2_RGB;
    private static boolean arbVbo;
    public static float arI;
    public static boolean il;
    public static int GL_FB_INCOMPLETE_MISS_ATTACH;
    public static int GL_COMBINE;
    public static int GL_CONSTANT;
    public static int GL_COMBINE_RGB;
    private static boolean lR;
    public static int GL_OPERAND2_RGB;
    public static boolean mL;

    public static boolean vboSupported;
    public static int GL_FB_INCOMPLETE_ATTACHMENT;
    public static int GL_PREVIOUS;
    public static int GL_FRAMEBUFFER_COMPLETE;
    public static int GL_SOURCE0_RGB;
    private static String logText;
    public static boolean shadersSupported;
    private static boolean kS;
    public static int Kq;
    public static int GL_VERTEX_SHADER;
    public static int GL_TEXTURE2;
    public static int GL_OPERAND1_RGB;
    public static int GL_COMPILE_STATUS;
    public static int dD;
    public static boolean framebufferSupported;
    private static boolean nF;
    public static int ni;
    public static int GL_SOURCE1_ALPHA;
    public static int GL_COMBINE_ALPHA;
    private static boolean lx;
    public static int GL_FRAMEBUFFER;
    public static int GL_SOURCE0_ALPHA;
    private static int framebufferType;
    public static int defaultTexUnit;
    public static int BU;
    public static int GL_SOURCE1_RGB;
    public static int ef;
    public static int GL_OPERAND0_ALPHA;
    public static int GL_OPERAND0_RGB;
    public static int GL_PRIMARY_COLOR;

    public static void glBindFramebuffer(int i, int j) {
        int k = WorldRenderer.getLegacyRenderFlowMarker();
        if (net.minecraft.client.renderer.OpenGlHelper.framebufferSupported) {
            switch (net.minecraft.client.renderer.OpenGlHelper.framebufferType) {
                case 0:
                    GL30.glBindFramebuffer(i, j);
                    if (k != 0) {
                        break;
                    }
                case 1:
                    ARBFramebufferObject.glBindFramebuffer(i, j);
                    if (k != 0) {
                        break;
                    }
                case 2:
                    EXTFramebufferObject.glBindFramebufferEXT(i, j);
            }
        }
    }

    public static void setActiveTexture(int i) {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        if (lR) {
            ARBMultitexture.glActiveTextureARB(i);
            if (j != 0) {
                return;
            }
        }

        GL13.glActiveTexture(i);
    }

    public static int glGenRenderbuffers() {
        if (!net.minecraft.client.renderer.OpenGlHelper.framebufferSupported) {
            return -1;
        }

        switch (net.minecraft.client.renderer.OpenGlHelper.framebufferType) {
            case 0:
                return GL30.glGenRenderbuffers();
            case 1:
                return ARBFramebufferObject.glGenRenderbuffers();
            case 2:
                return EXTFramebufferObject.glGenRenderbuffersEXT();
            default:
                return -1;
        }
    }

    public static void glBindRenderbuffer(int i, int j) {
        int k = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.framebufferSupported) {
            switch (net.minecraft.client.renderer.OpenGlHelper.framebufferType) {
                case 0:
                    GL30.glBindRenderbuffer(i, j);
                    if (k == 0) {
                        break;
                    }
                case 1:
                    ARBFramebufferObject.glBindRenderbuffer(i, j);
                    if (k == 0) {
                        break;
                    }
                case 2:
                    EXTFramebufferObject.glBindRenderbufferEXT(i, j);
            }
        }
    }

    public static void glUniform1(int i, IntBuffer intbuffer) {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform1ARB(i, intbuffer);
            if (j != 0) {
                return;
            }
        }

        GL20.glUniform1(i, intbuffer);
    }

    public static void glShaderSource(int i, ByteBuffer bytebuffer) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glShaderSourceARB(i, bytebuffer);
            if (j == 0) {
                return;
            }
        }

        GL20.glShaderSource(i, bytebuffer);
    }

    public static boolean useVbo() {
        return OptiFineConfig.isMultiTexture()
                ? false
                : net.minecraft.client.renderer.OpenGlHelper.vboSupported
                        && MinecraftClient.getMinecraft().gameSettings.useVbo;
    }

    public static String getLogText() {
        return net.minecraft.client.renderer.OpenGlHelper.logText;
    }

    public static void setClientActiveTexture(int i) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (lR) {
            ARBMultitexture.glClientActiveTextureARB(i);
            if (j == 0) {
                return;
            }
        }

        GL13.glClientActiveTexture(i);
    }

    public static String glGetProgramInfoLog(int i, int j) {
        return net.minecraft.client.renderer.OpenGlHelper.arbShaders
                ? ARBShaderObjects.glGetInfoLogARB(i, j)
                : GL20.glGetProgramInfoLog(i, j);
    }

    public static void glCompileShader(int i) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glCompileShaderARB(i);
            if (j == 0) {
                return;
            }
        }

        GL20.glCompileShader(i);
    }

    public static void glUniform1i(int i, int j) {
        int k = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform1iARB(i, j);
            if (k == 0) {
                return;
            }
        }

        GL20.glUniform1i(i, j);
    }

    public static int glGetUniformLocation(int i, CharSequence charsequence) {
        return net.minecraft.client.renderer.OpenGlHelper.arbShaders
                ? ARBShaderObjects.glGetUniformLocationARB(i, charsequence)
                : GL20.glGetUniformLocation(i, charsequence);
    }

    public static void glUniform2(int i, FloatBuffer floatbuffer) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform2ARB(i, floatbuffer);
            if (j == 0) {
                return;
            }
        }

        GL20.glUniform2(i, floatbuffer);
    }

    public static void glBufferData(int i, ByteBuffer bytebuffer, int j) {
        int k = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbVbo) {
            ARBVertexBufferObject.glBufferDataARB(i, bytebuffer, j);
            if (k == 0) {
                return;
            }
        }

        GL15.glBufferData(i, bytebuffer, j);
    }

    public static String glGetShaderInfoLog(int i, int j) {
        return net.minecraft.client.renderer.OpenGlHelper.arbShaders
                ? ARBShaderObjects.glGetInfoLogARB(i, j)
                : GL20.glGetShaderInfoLog(i, j);
    }

    public static void glUniform4(int i, IntBuffer intbuffer) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform4ARB(i, intbuffer);
            if (j == 0) {
                return;
            }
        }

        GL20.glUniform4(i, intbuffer);
    }

    public static int glCreateShader(int i) {
        return net.minecraft.client.renderer.OpenGlHelper.arbShaders
                ? ARBShaderObjects.glCreateShaderObjectARB(i)
                : GL20.glCreateShader(i);
    }

    public static boolean areShadersSupported() {
        return net.minecraft.client.renderer.OpenGlHelper.shadersSupported;
    }

    public static int glGenBuffers() {
        return net.minecraft.client.renderer.OpenGlHelper.arbVbo
                ? ARBVertexBufferObject.glGenBuffersARB()
                : GL15.glGenBuffers();
    }

    public static int glGetProgrami(int i, int j) {
        return net.minecraft.client.renderer.OpenGlHelper.arbShaders
                ? ARBShaderObjects.glGetObjectParameteriARB(i, j)
                : GL20.glGetProgrami(i, j);
    }

    public static void glUniformMatrix2(int i, boolean flag, FloatBuffer floatbuffer) {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniformMatrix2ARB(i, flag, floatbuffer);
            if (j != 0) {
                return;
            }
        }

        GL20.glUniformMatrix2(i, flag, floatbuffer);
    }

    public static void glDeleteShader(int i) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glDeleteObjectARB(i);
            if (j == 0) {
                return;
            }
        }

        GL20.glDeleteShader(i);
    }

    public static boolean isFramebufferEnabled() {
        return OptiFineConfig.isFastRender()
                ? false
                : (OptiFineConfig.isAntialiasing()
                        ? false
                        : net.minecraft.client.renderer.OpenGlHelper.framebufferSupported
                                && MinecraftClient.getMinecraft().gameSettings.fboEnable);
    }

    static {
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments = new String[13];
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments[0] = new String("");
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments[1] = new String("");
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments[2] = new String("");
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments[3] = new String("");
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments[4] = new String("");
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments[5] = new String("");
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments[6] = new String("");
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments[7] = new String("");
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments[8] = new String("");
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments[9] = new String("");
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments[10] = new String("");
        net.minecraft.client.renderer.OpenGlHelper.packedTextFragments[12] = new String("");
        net.minecraft.client.renderer.OpenGlHelper.logText = packedTextFragments[12];
        Pu = 0.0F;
        arI = 0.0F;
    }

    public static void glBlendFunc(int i, int j, int k, int l) {
        int i1 = WorldRenderer.getLegacyRenderFlowToken();
        if (lx) {
            if (ahv) {
                EXTBlendFuncSeparate.glBlendFuncSeparateEXT(i, j, k, l);
                if (i1 == 0) {
                    return;
                }
            }

            GL14.glBlendFuncSeparate(i, j, k, l);
            if (i1 == 0) {
                return;
            }
        }

        GL11.glBlendFunc(i, j);
    }

    public static void glFramebufferTexture2D(int i, int j, int k, int l, int i1) {
        int j1 = WorldRenderer.getLegacyRenderFlowMarker();
        if (net.minecraft.client.renderer.OpenGlHelper.framebufferSupported) {
            switch (net.minecraft.client.renderer.OpenGlHelper.framebufferType) {
                case 0:
                    GL30.glFramebufferTexture2D(i, j, k, l, i1);
                    if (j1 != 0) {
                        break;
                    }
                case 1:
                    ARBFramebufferObject.glFramebufferTexture2D(i, j, k, l, i1);
                    if (j1 != 0) {
                        break;
                    }
                case 2:
                    EXTFramebufferObject.glFramebufferTexture2DEXT(i, j, k, l, i1);
            }
        }
    }

    public static int glGetShaderi(int i, int j) {
        return net.minecraft.client.renderer.OpenGlHelper.arbShaders
                ? ARBShaderObjects.glGetObjectParameteriARB(i, j)
                : GL20.glGetShaderi(i, j);
    }

    public static void glUniformMatrix4(int i, boolean flag, FloatBuffer floatbuffer) {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniformMatrix4ARB(i, flag, floatbuffer);
            if (j != 0) {
                return;
            }
        }

        GL20.glUniformMatrix4(i, flag, floatbuffer);
    }

    public static int glGetAttribLocation(int i, CharSequence charsequence) {
        return net.minecraft.client.renderer.OpenGlHelper.arbShaders
                ? ARBVertexShader.glGetAttribLocationARB(i, charsequence)
                : GL20.glGetAttribLocation(i, charsequence);
    }

    public static void glUniformMatrix3(int i, boolean flag, FloatBuffer floatbuffer) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniformMatrix3ARB(i, flag, floatbuffer);
            if (j == 0) {
                return;
            }
        }

        GL20.glUniformMatrix3(i, flag, floatbuffer);
    }

    public static void glAttachShader(int i, int j) {
        int k = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glAttachObjectARB(i, j);
            if (k == 0) {
                return;
            }
        }

        GL20.glAttachShader(i, j);
    }

    public static int glGenFramebuffers() {
        if (!net.minecraft.client.renderer.OpenGlHelper.framebufferSupported) {
            return -1;
        }

        switch (net.minecraft.client.renderer.OpenGlHelper.framebufferType) {
            case 0:
                return GL30.glGenFramebuffers();
            case 1:
                return ARBFramebufferObject.glGenFramebuffers();
            case 2:
                return EXTFramebufferObject.glGenFramebuffersEXT();
            default:
                return -1;
        }
    }

    public static void glDeleteRenderbuffers(int i) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.framebufferSupported) {
            switch (net.minecraft.client.renderer.OpenGlHelper.framebufferType) {
                case 0:
                    GL30.glDeleteRenderbuffers(i);
                    if (j == 0) {
                        break;
                    }
                case 1:
                    ARBFramebufferObject.glDeleteRenderbuffers(i);
                    if (j == 0) {
                        break;
                    }
                case 2:
                    EXTFramebufferObject.glDeleteRenderbuffersEXT(i);
            }
        }
    }

    public static void glBindBuffer(int i, int j) {
        int k = WorldRenderer.getLegacyRenderFlowMarker();
        if (net.minecraft.client.renderer.OpenGlHelper.arbVbo) {
            ARBVertexBufferObject.glBindBufferARB(i, j);
            if (k != 0) {
                return;
            }
        }

        GL15.glBindBuffer(i, j);
    }

    public static void glDeleteFramebuffers(int i) {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        if (net.minecraft.client.renderer.OpenGlHelper.framebufferSupported) {
            switch (net.minecraft.client.renderer.OpenGlHelper.framebufferType) {
                case 0:
                    GL30.glDeleteFramebuffers(i);
                    if (j != 0) {
                        break;
                    }
                case 1:
                    ARBFramebufferObject.glDeleteFramebuffers(i);
                    if (j != 0) {
                        break;
                    }
                case 2:
                    EXTFramebufferObject.glDeleteFramebuffersEXT(i);
            }
        }
    }

    public static void glUniform2(int i, IntBuffer intbuffer) {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform2ARB(i, intbuffer);
            if (j != 0) {
                return;
            }
        }

        GL20.glUniform2(i, intbuffer);
    }

    public static void initializeTextures() {
        int i;
        ContextCapabilities contextcapabilities;
        label204:
        {
            int j = WorldRenderer.getLegacyRenderFlowToken();
            OptiFineConfig.initDisplay();
            i = j;
            contextcapabilities = GLContext.getCapabilities();
            lR =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (contextcapabilities.GL_ARB_multitexture
                                            && !contextcapabilities.OpenGL13
                                    ? 1
                                    : 0));
            nF =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (contextcapabilities.GL_ARB_texture_env_combine
                                            && !contextcapabilities.OpenGL13
                                    ? 1
                                    : 0));
            if (lR) {
                net.minecraft.client.renderer.OpenGlHelper.logText =
                        net.minecraft.client.renderer.OpenGlHelper.logText
                                + "Using ARB_multitexture.\n";
                net.minecraft.client.renderer.OpenGlHelper.defaultTexUnit = 33984;
                net.minecraft.client.renderer.OpenGlHelper.lightmapTexUnit = 33985;
                net.minecraft.client.renderer.OpenGlHelper.GL_TEXTURE2 = 33986;
                if (i == 0) {
                    break label204;
                }
            }

            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "Using GL 1.3 multitexturing.\n";
            net.minecraft.client.renderer.OpenGlHelper.defaultTexUnit = 33984;
            net.minecraft.client.renderer.OpenGlHelper.lightmapTexUnit = 33985;
            net.minecraft.client.renderer.OpenGlHelper.GL_TEXTURE2 = 33986;
        }

        label199:
        {
            if (nF) {
                net.minecraft.client.renderer.OpenGlHelper.logText =
                        net.minecraft.client.renderer.OpenGlHelper.logText
                                + "Using ARB_texture_env_combine.\n";
                net.minecraft.client.renderer.OpenGlHelper.GL_COMBINE = 34160;
                net.minecraft.client.renderer.OpenGlHelper.GL_INTERPOLATE = 34165;
                net.minecraft.client.renderer.OpenGlHelper.GL_PRIMARY_COLOR = 34167;
                net.minecraft.client.renderer.OpenGlHelper.GL_CONSTANT = 34166;
                net.minecraft.client.renderer.OpenGlHelper.GL_PREVIOUS = 34168;
                net.minecraft.client.renderer.OpenGlHelper.GL_COMBINE_RGB = 34161;
                net.minecraft.client.renderer.OpenGlHelper.GL_SOURCE0_RGB = 34176;
                net.minecraft.client.renderer.OpenGlHelper.GL_SOURCE1_RGB = 34177;
                net.minecraft.client.renderer.OpenGlHelper.GL_SOURCE2_RGB = 34178;
                net.minecraft.client.renderer.OpenGlHelper.GL_OPERAND0_RGB = 34192;
                net.minecraft.client.renderer.OpenGlHelper.GL_OPERAND1_RGB = 34193;
                net.minecraft.client.renderer.OpenGlHelper.GL_OPERAND2_RGB = 34194;
                net.minecraft.client.renderer.OpenGlHelper.GL_COMBINE_ALPHA = 34162;
                net.minecraft.client.renderer.OpenGlHelper.GL_SOURCE0_ALPHA = 34184;
                net.minecraft.client.renderer.OpenGlHelper.GL_SOURCE1_ALPHA = 34185;
                ni = 34186;
                net.minecraft.client.renderer.OpenGlHelper.GL_OPERAND0_ALPHA = 34200;
                net.minecraft.client.renderer.OpenGlHelper.GL_OPERAND1_ALPHA = 34201;
                BU = 34202;
                if (i == 0) {
                    break label199;
                }
            }

            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "Using GL 1.3 texture combiners.\n";
            net.minecraft.client.renderer.OpenGlHelper.GL_COMBINE = 34160;
            net.minecraft.client.renderer.OpenGlHelper.GL_INTERPOLATE = 34165;
            net.minecraft.client.renderer.OpenGlHelper.GL_PRIMARY_COLOR = 34167;
            net.minecraft.client.renderer.OpenGlHelper.GL_CONSTANT = 34166;
            net.minecraft.client.renderer.OpenGlHelper.GL_PREVIOUS = 34168;
            net.minecraft.client.renderer.OpenGlHelper.GL_COMBINE_RGB = 34161;
            net.minecraft.client.renderer.OpenGlHelper.GL_SOURCE0_RGB = 34176;
            net.minecraft.client.renderer.OpenGlHelper.GL_SOURCE1_RGB = 34177;
            net.minecraft.client.renderer.OpenGlHelper.GL_SOURCE2_RGB = 34178;
            net.minecraft.client.renderer.OpenGlHelper.GL_OPERAND0_RGB = 34192;
            net.minecraft.client.renderer.OpenGlHelper.GL_OPERAND1_RGB = 34193;
            net.minecraft.client.renderer.OpenGlHelper.GL_OPERAND2_RGB = 34194;
            net.minecraft.client.renderer.OpenGlHelper.GL_COMBINE_ALPHA = 34162;
            net.minecraft.client.renderer.OpenGlHelper.GL_SOURCE0_ALPHA = 34184;
            net.minecraft.client.renderer.OpenGlHelper.GL_SOURCE1_ALPHA = 34185;
            ni = 34186;
            net.minecraft.client.renderer.OpenGlHelper.GL_OPERAND0_ALPHA = 34200;
            net.minecraft.client.renderer.OpenGlHelper.GL_OPERAND1_ALPHA = 34201;
            BU = 34202;
        }

        label218:
        {
            ahv =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (contextcapabilities.GL_EXT_blend_func_separate
                                            && !contextcapabilities.OpenGL14
                                    ? 1
                                    : 0));
            lx =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!contextcapabilities.OpenGL14
                                            && !contextcapabilities.GL_EXT_blend_func_separate
                                    ? 0
                                    : 1));
            net.minecraft.client.renderer.OpenGlHelper.framebufferSupported =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!lx
                                            || !contextcapabilities.GL_ARB_framebuffer_object
                                                    && !contextcapabilities
                                                            .GL_EXT_framebuffer_object
                                                    && !contextcapabilities.OpenGL30
                                    ? 0
                                    : 1));
            if (net.minecraft.client.renderer.OpenGlHelper.framebufferSupported) {
                net.minecraft.client.renderer.OpenGlHelper.logText =
                        net.minecraft.client.renderer.OpenGlHelper.logText
                                + "Using framebuffer objects because ";
                if (contextcapabilities.OpenGL30) {
                    net.minecraft.client.renderer.OpenGlHelper.logText =
                            net.minecraft.client.renderer.OpenGlHelper.logText
                                    + "OpenGL 3.0 is supported and separate blending is"
                                    + " supported.\n";
                    net.minecraft.client.renderer.OpenGlHelper.framebufferType = 0;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FRAMEBUFFER = 36160;
                    c = 36161;
                    dD = 36064;
                    ef = 36096;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FRAMEBUFFER_COMPLETE = 36053;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER = 36060;
                    if (i == 0) {
                        break label218;
                    }
                }

                if (contextcapabilities.GL_ARB_framebuffer_object) {
                    net.minecraft.client.renderer.OpenGlHelper.logText =
                            net.minecraft.client.renderer.OpenGlHelper.logText
                                    + "ARB_framebuffer_object is supported and separate blending is"
                                    + " supported.\n";
                    net.minecraft.client.renderer.OpenGlHelper.framebufferType = 1;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FRAMEBUFFER = 36160;
                    c = 36161;
                    dD = 36064;
                    ef = 36096;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FRAMEBUFFER_COMPLETE = 36053;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER = 36060;
                    if (i == 0) {
                        break label218;
                    }
                }

                if (!contextcapabilities.GL_EXT_framebuffer_object) {
                    break label218;
                }

                net.minecraft.client.renderer.OpenGlHelper.logText =
                        net.minecraft.client.renderer.OpenGlHelper.logText
                                + "EXT_framebuffer_object is supported.\n";
                net.minecraft.client.renderer.OpenGlHelper.framebufferType = 2;
                net.minecraft.client.renderer.OpenGlHelper.GL_FRAMEBUFFER = 36160;
                c = 36161;
                dD = 36064;
                ef = 36096;
                net.minecraft.client.renderer.OpenGlHelper.GL_FRAMEBUFFER_COMPLETE = 36053;
                net.minecraft.client.renderer.OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                net.minecraft.client.renderer.OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                net.minecraft.client.renderer.OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                net.minecraft.client.renderer.OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER = 36060;
                if (i == 0) {
                    break label218;
                }
            }

            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "Not using framebuffer objects because ";
            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "OpenGL 1.4 is "
                            + (contextcapabilities.OpenGL14 ? packedTextFragments[0] : "not ")
                            + "supported, ";
            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "EXT_blend_func_separate is "
                            + (contextcapabilities.GL_EXT_blend_func_separate
                                    ? packedTextFragments[1]
                                    : "not ")
                            + "supported, ";
            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "OpenGL 3.0 is "
                            + (contextcapabilities.OpenGL30 ? packedTextFragments[2] : "not ")
                            + "supported, ";
            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "ARB_framebuffer_object is "
                            + (contextcapabilities.GL_ARB_framebuffer_object
                                    ? packedTextFragments[3]
                                    : "not ")
                            + "supported, and ";
            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "EXT_framebuffer_object is "
                            + (contextcapabilities.GL_EXT_framebuffer_object
                                    ? packedTextFragments[4]
                                    : "not ")
                            + "supported.\n";
        }

        label221:
        {
            mL = contextcapabilities.OpenGL21;
            kS =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!mL
                                            && (!contextcapabilities.GL_ARB_vertex_shader
                                                    || !contextcapabilities.GL_ARB_fragment_shader
                                                    || !contextcapabilities.GL_ARB_shader_objects)
                                    ? 0
                                    : 1));
            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "Shaders are "
                            + (kS ? packedTextFragments[5] : "not ")
                            + "available because ";
            if (kS) {
                if (contextcapabilities.OpenGL21) {
                    net.minecraft.client.renderer.OpenGlHelper.logText =
                            net.minecraft.client.renderer.OpenGlHelper.logText
                                    + "OpenGL 2.1 is supported.\n";
                    net.minecraft.client.renderer.OpenGlHelper.arbShaders = false;
                    net.minecraft.client.renderer.OpenGlHelper.GL_LINK_STATUS = 35714;
                    net.minecraft.client.renderer.OpenGlHelper.GL_COMPILE_STATUS = 35713;
                    net.minecraft.client.renderer.OpenGlHelper.GL_VERTEX_SHADER = 35633;
                    net.minecraft.client.renderer.OpenGlHelper.GL_FRAGMENT_SHADER = 35632;
                    if (i == 0) {
                        break label221;
                    }
                }

                net.minecraft.client.renderer.OpenGlHelper.logText =
                        net.minecraft.client.renderer.OpenGlHelper.logText
                                + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader"
                                + " are supported.\n";
                net.minecraft.client.renderer.OpenGlHelper.arbShaders = true;
                net.minecraft.client.renderer.OpenGlHelper.GL_LINK_STATUS = 35714;
                net.minecraft.client.renderer.OpenGlHelper.GL_COMPILE_STATUS = 35713;
                net.minecraft.client.renderer.OpenGlHelper.GL_VERTEX_SHADER = 35633;
                net.minecraft.client.renderer.OpenGlHelper.GL_FRAGMENT_SHADER = 35632;
                if (i == 0) {
                    break label221;
                }
            }

            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "OpenGL 2.1 is "
                            + (contextcapabilities.OpenGL21 ? packedTextFragments[6] : "not ")
                            + "supported, ";
            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "ARB_shader_objects is "
                            + (contextcapabilities.GL_ARB_shader_objects
                                    ? packedTextFragments[7]
                                    : "not ")
                            + "supported, ";
            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "ARB_vertex_shader is "
                            + (contextcapabilities.GL_ARB_vertex_shader
                                    ? packedTextFragments[8]
                                    : "not ")
                            + "supported, and ";
            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "ARB_fragment_shader is "
                            + (contextcapabilities.GL_ARB_fragment_shader
                                    ? packedTextFragments[9]
                                    : "not ")
                            + "supported.\n";
        }

        net.minecraft.client.renderer.OpenGlHelper.shadersSupported =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (net.minecraft.client.renderer.OpenGlHelper.framebufferSupported && kS
                                ? 1
                                : 0));
        il = GL11.glGetString(7936).toLowerCase().contains("nvidia");
        net.minecraft.client.renderer.OpenGlHelper.arbVbo =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (!contextcapabilities.OpenGL15
                                        && contextcapabilities.GL_ARB_vertex_buffer_object
                                ? 1
                                : 0));
        net.minecraft.client.renderer.OpenGlHelper.vboSupported =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (!contextcapabilities.OpenGL15
                                        && !net.minecraft.client.renderer.OpenGlHelper.arbVbo
                                ? 0
                                : 1));
        net.minecraft.client.renderer.OpenGlHelper.logText =
                net.minecraft.client.renderer.OpenGlHelper.logText
                        + "VBOs are "
                        + (net.minecraft.client.renderer.OpenGlHelper.vboSupported
                                ? packedTextFragments[10]
                                : "not ")
                        + "available because ";
        if (net.minecraft.client.renderer.OpenGlHelper.vboSupported) {
            if (net.minecraft.client.renderer.OpenGlHelper.arbVbo) {
                net.minecraft.client.renderer.OpenGlHelper.logText =
                        net.minecraft.client.renderer.OpenGlHelper.logText
                                + "ARB_vertex_buffer_object is supported.\n";
                Kq = 35044;
                net.minecraft.client.renderer.OpenGlHelper.GL_ARRAY_BUFFER = 34962;
                if (i == 0) {
                    return;
                }
            }

            net.minecraft.client.renderer.OpenGlHelper.logText =
                    net.minecraft.client.renderer.OpenGlHelper.logText
                            + "OpenGL 1.5 is supported.\n";
            Kq = 35044;
            net.minecraft.client.renderer.OpenGlHelper.GL_ARRAY_BUFFER = 34962;
        }
    }

    public static void glLinkProgram(int i) {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glLinkProgramARB(i);
            if (j != 0) {
                return;
            }
        }

        GL20.glLinkProgram(i);
    }

    public static void glUniform3(int i, FloatBuffer floatbuffer) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform3ARB(i, floatbuffer);
            if (j == 0) {
                return;
            }
        }

        GL20.glUniform3(i, floatbuffer);
    }

    public static int glCreateProgram() {
        return net.minecraft.client.renderer.OpenGlHelper.arbShaders
                ? ARBShaderObjects.glCreateProgramObjectARB()
                : GL20.glCreateProgram();
    }

    public static void glRenderbufferStorage(int i, int j, int k, int l) {
        int i1 = WorldRenderer.getLegacyRenderFlowMarker();
        if (net.minecraft.client.renderer.OpenGlHelper.framebufferSupported) {
            switch (net.minecraft.client.renderer.OpenGlHelper.framebufferType) {
                case 0:
                    GL30.glRenderbufferStorage(i, j, k, l);
                    if (i1 != 0) {
                        break;
                    }
                case 1:
                    ARBFramebufferObject.glRenderbufferStorage(i, j, k, l);
                    if (i1 != 0) {
                        break;
                    }
                case 2:
                    EXTFramebufferObject.glRenderbufferStorageEXT(i, j, k, l);
            }
        }
    }

    public static void glUseProgram(int i) {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUseProgramObjectARB(i);
            if (j != 0) {
                return;
            }
        }

        GL20.glUseProgram(i);
    }

    public static void glDeleteBuffers(int i) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbVbo) {
            ARBVertexBufferObject.glDeleteBuffersARB(i);
            if (j == 0) {
                return;
            }
        }

        GL15.glDeleteBuffers(i);
    }

    public static void glDeleteProgram(int i) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glDeleteObjectARB(i);
            if (j == 0) {
                return;
            }
        }

        GL20.glDeleteProgram(i);
    }

    public static int glCheckFramebufferStatus(int i) {
        if (!net.minecraft.client.renderer.OpenGlHelper.framebufferSupported) {
            return -1;
        }

        switch (net.minecraft.client.renderer.OpenGlHelper.framebufferType) {
            case 0:
                return GL30.glCheckFramebufferStatus(i);
            case 1:
                return ARBFramebufferObject.glCheckFramebufferStatus(i);
            case 2:
                return EXTFramebufferObject.glCheckFramebufferStatusEXT(i);
            default:
                return -1;
        }
    }

    public static void glUniform3(int i, IntBuffer intbuffer) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform3ARB(i, intbuffer);
            if (j == 0) {
                return;
            }
        }

        GL20.glUniform3(i, intbuffer);
    }

    public static void glFramebufferRenderbuffer(int i, int j, int k, int l) {
        int i1 = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.framebufferSupported) {
            switch (net.minecraft.client.renderer.OpenGlHelper.framebufferType) {
                case 0:
                    GL30.glFramebufferRenderbuffer(i, j, k, l);
                    if (i1 == 0) {
                        break;
                    }
                case 1:
                    ARBFramebufferObject.glFramebufferRenderbuffer(i, j, k, l);
                    if (i1 == 0) {
                        break;
                    }
                case 2:
                    EXTFramebufferObject.glFramebufferRenderbufferEXT(i, j, k, l);
            }
        }
    }

    public static void glUniform1(int i, FloatBuffer floatbuffer) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform1ARB(i, floatbuffer);
            if (j == 0) {
                return;
            }
        }

        GL20.glUniform1(i, floatbuffer);
    }

    public static void glUniform4(int i, FloatBuffer floatbuffer) {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        if (net.minecraft.client.renderer.OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform4ARB(i, floatbuffer);
            if (j != 0) {
                return;
            }
        }

        GL20.glUniform4(i, floatbuffer);
    }

    public static void setLightmapTextureCoords(int i, float f, float arIValue) {
        {
            int j = WorldRenderer.getLegacyRenderFlowMarker();
            if (lR) {
                ARBMultitexture.glMultiTexCoord2fARB(i, f, arIValue);
                if (!(j != 0)) {

                    GL13.glMultiTexCoord2f(i, f, arIValue);
                }
            } else {

                GL13.glMultiTexCoord2f(i, f, arIValue);
            }
        }

        if (i == net.minecraft.client.renderer.OpenGlHelper.lightmapTexUnit) {
            Pu = f;
            arI = arIValue;
        }
    }
}
