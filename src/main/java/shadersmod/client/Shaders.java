package shadersmod.client;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import optifine.Lang;
import optifine.OptiFineConfig;
import optifine.PropertiesOrdered;
import optifine.Reflector;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

import remix.util.reflection.ZeroArgReflectiveInstantiator;

import shadersmod.common.ShaderLog;
import shadersmod.uniform.ShaderUniformFloat4;
import shadersmod.uniform.ShaderUniformInt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Shaders {
    static FloatBuffer previousModelView;
    static boolean shadowMapIsOrtho;
    static int shadowMapHeight;
    static boolean usewetness;
    static float[] upPosition;
    private static int newCompositeMipmapSetting;
    static EntityRenderer entityRenderer;
    static double previousCameraPositionY;
    static int systemTimeInt32;
    static float eyeBrightnessFadeY;
    public static boolean isInitializedOnce;
    static boolean lightmapEnabled;
    public static boolean configShadowClipFrustrum;
    static FloatBuffer shadowModelView;
    static float shadowMapHalfPlane;
    static double cameraPositionZ;
    public static int[] terrainTextureSize;
    public static int configTexMagFilS;
    static IntBuffer sfbColorTextures;
    private static HFNoiseTexture customUniforms;
    static float clearColorG;
    private static String[] Lb;
    static float shadowAngle;
    static IShaderPack shaderPack;
    public static final int Le = 24;
    public static boolean isHandRenderedMain;
    public static final int Lf = 17;
    static long systemTime;
    public static boolean useMultiTexCoord3Attrib;
    static boolean updateChunksErrorRecorded;
    public static int renderHeight;
    static File shadersdir;
    private static Map<String, String> shaderPackResources;
    public static final int bs = 12;
    public static float blockLightLevel08;
    public static String packNameNone;
    private static int renderDisplayHeight;
    private static String[] programsDrawBufSettings;
    public static final int Lo = 28;
    static String shaderpacksdirname;
    public static Properties shadersConfig;
    static boolean centerDepthSmoothEnabled;
    public static float eyeBrightnessHalflife;
    public static float configHandDepthMul;
    public static float wetnessHalfLife;
    public static int numberResetDisplayList;
    public static int configTexMinFilS;
    static boolean fogEnabled;
    static ShaderProfile[] shaderPackProfiles;
    private static String newColorAtmSetting;
    public static final int Lz = 31;
    static IntBuffer drawBuffersAll;
    static float[] faShadowModelView;
    public static final int LC = 16;
    public static final boolean LD = true;
    public static float fogColorG;
    public static final int LF = 13;
    static double cameraPositionY;
    static IntBuffer drawBuffersClear0;
    static int spShadowMapHeight;
    static long worldTime;
    public static PropertyDefaultTrueFalse shaderPackOldLighting;
    static Map<String, ShaderOption[]> shaderPackGuiScreens;
    static IntBuffer dfbDrawBuffers;
    static float[] faShadowModelViewInverse;
    static ShaderOption[] shaderPackOptions;
    static FloatBuffer modelView;
    static FloatBuffer projection;
    public static ShaderUniformInt uniformEntityId;
    public static String glVersionString;
    static int spShadowMapWidth;
    public static final int LS = 29;
    static File shaderpacksdir;
    static float skyColorR;
    public static String currentshadername;
    public static boolean configTweakBlockDamage;
    public static float configRenderResMul;
    public static boolean progUseTangentAttrib;
    static long lastSystemTime;
    static float skyColorG;
    public static int configAntialiasingLevel;
    public static int fogMode;
    static float eyePosY;
    public static boolean renderItemKeepDepthMask;
    static final int Mc = 8;
    public static final int Md = 8;
    static int shadowMapWidth;
    static int[] colorTexturesToggle;
    private static Pattern Mg;
    public static int configTexMagFilN;
    public static int midTexCoordAttrib;
    static float[] faModelViewInverse;
    public static final int Mj = 22;
    static float celestialAngle;
    public static Properties loadedShaders;
    public static final int Mm = 7;
    public static boolean needResizeShadow;
    public static PropertyDefaultTrueFalse configOldHandLight;
    public static final int Mp = 3;
    static IntBuffer[] programsDrawBuffers;
    static float[] shadowLightPositionVector;
    static long diffWorldTime;
    static float[] faShadowProjection;
    static int[] programsID;
    public static final int Mv = 23;
    public static boolean isShadowPass;
    public static int entityAttrib;
    static float rainStrength;
    public static final int Mx = 14;
    public static int tangentAttrib;
    private static int noiseTextureResolution;
    public static boolean isRenderingSky;
    public static boolean hasGlGenMipmap;
    static FloatBuffer projectionInverse;
    private static int programIDCopyDepth;
    public static PropertyDefaultTrueFalse shaderPackDynamicHandLight;
    static FloatBuffer modelViewInverse;
    public static ShaderUniformInt uniformBlockEntityId;
    static FloatBuffer shadowProjection;
    public static final int MF = 9;
    static float[] faProjection;
    public static float drynessHalfLife;
    static boolean waterShadowEnabled;
    public static final int MJ = 6;
    static FloatBuffer shadowModelViewInverse;
    static float[] sunPosition;
    public static final int MM = 15;
    static File configFile;
    static FloatBuffer tempMatrixDirectBuffer;
    public static float MP;
    private static List<Integer> shaderPackDimensions;
    public static float blockLightLevel06;
    static int[] dfbColorTexturesA;
    public static ITextureObject defaultTexture;
    static double cameraPositionX;
    static float clearColorB;
    private static String newDrawBufSetting;
    static final int MX = 2;
    public static final int MY = 8;
    public static boolean configNormalMap;
    static FloatBuffer shadowProjectionInverse;
    public static ShaderUniformFloat4 uniformEntityColor;
    public static int atlasSizeX;
    public static int renderWidth;
    public static int[] texMinFilValue;
    public static float fogColorB;
    static float[] faProjectionInverse;
    public static float aoLevel;
    private static Pattern Nh;
    static boolean[][] programsToggleColorTextures;
    static IntBuffer dfbDepthTextures;
    public static boolean configSpecularMap;
    public static boolean[] shadowColorFilterNearest;
    private static int[] programsCompositeMipmapSetting;
    public static int configTexMagFilB;
    static int[] colorTextureTextureImageUnit;
    public static boolean useMidTexCoordAttrib;
    static long lastWorldTime;
    public static final int Nr = 2;
    static int dfb;
    public static boolean[] shadowHardwareFilteringEnabled;
    static float[] sunPosModelView;
    private static int[] gbuffersFormat;
    static int shadowPassCounter;
    public static boolean isRenderingWorld;
    static FloatBuffer previousProjection;
    public static boolean normalMapEnabled;
    static int usedColorBuffers;
    static MinecraftClient mc;
    private static int[] programsRef;
    static int superSamplingLevel;
    static Map<Block, Integer> mapBlockToEntityData;
    public static float fogColorR;
    private static String[] packedTextFragments;
    static float[] moonPosModelView;
    public static boolean configCloudShadow;
    private static ByteBuffer bigBuffer;
    public static final int NF = 26;
    static float[] shadowLightPosition;
    public static final int NH = 2;

    static final int NJ = 8;
    public static final int NK = 1;
    static float skyColorB;
    private static final boolean NM = true;
    static int moonPhase;
    public static boolean isSleeping;
    public static final int NN = 18;
    public static final int NO = 20;
    static IntBuffer drawBuffersClearColor;
    static double previousCameraPositionX;
    public static boolean shaderPackLoaded;
    public static float sunPathRotation;
    public static final int NT = 5;
    public static final int NU = 19;
    private static String activeColorAtmSettings;
    public static String glRendererString;
    private static int activeCompositeMipmapSetting;
    public static String[] texMinFilDesc;
    public static float shadowIntervalSize;
    static int usedShadowDepthBuffers;
    public static boolean hasForge;
    public static boolean[] shadowFilterNearest;
    static int sfb;
    static float eyeBrightnessFadeX;
    public static boolean isRenderingDfb;
    static IntBuffer sfbDrawBuffers;
    static float centerDepth;
    static int usedDrawBuffers;
    static String packNameDefault;
    public static PropertyDefaultFastFancyOff shaderPackClouds;
    public static final int Oi = 4;
    public static int[] texMagFilValue;
    public static int configTexMinFilB;
    static IntBuffer sfbDepthTextures;
    public static final int Om = 0;
    public static String glVendorString;
    static float[] upPosModelView;
    public static final int Oo = 27;
    static int isEyeInWater;
    private static int[] formatIds;
    public static boolean useEntityAttrib;
    public static final int Or = 33;
    static int usedDepthBuffers;
    static int usedShadowColorBuffers;
    public static final int Ou = 21;
    private static boolean saveFinalShaders;
    static int preShadowPassThirdPersonView;
    static long diffSystemTime;
    public static final int Oy = 32;
    private static int[] programBackups;
    public static final int OA = 25;
    public static boolean useTangentAttrib;
    static float wetness;
    public static ContextCapabilities capabilities;
    public static boolean progUseEntityAttrib;
    static int shadowPassInterval;
    private static boolean noiseTextureEnabled;
    static File currentshader;
    static final int OI = 3;
    public static final int OJ = 30;
    static float[] moonPosition;
    public static float blockLightLevel05;
    static float centerDepthSmooth;
    public static ItemStack shaderHeldItem;
    public static int[] entityData;
    private static String[] programsColorAtmSettings;
    static IntBuffer drawBuffersClear1;
    static FloatBuffer tempDirectFloatBuffer;
    static int frameCounter;
    private static int renderDisplayWidth;
    static float shadowMapFOV;
    static int eyeBrightness;
    public static float shadowAngleInterval;
    public static final int OS = 11;
    public static boolean isCompositeRendered;
    public static boolean isShaderPackInitialized;
    public static int terrainIconSize;
    public static int entityDataIndex;
    static int usedColorAttachs;
    public static boolean shouldSkipDefaultShadow;
    static float sunAngle;
    static IntBuffer dfbColorTextures;
    static float clearColorR;
    static float centerDepthSmoothHalflife;
    private static final int Pa = 2196;
    public static final int Pb = 10;
    public static boolean[] shadowColorMipmapEnabled;
    public static final int Pd = 3;
    private static float[] tempMat;
    static float[] faModelView;
    static final int Pg = 8;
    static float[] faShadowProjectionInverse;
    static IntBuffer[] drawBuffersBuffer;
    public static int activeProgram;
    private static World currentWorld;
    public static String[] texMagFilDesc;
    static IntBuffer drawBuffersNone;

    public static boolean progUseMidTexCoordAttrib;
    public static int configTexMinFilN;
    static String optionsfilename;
    static double previousCameraPositionZ;
    public static float configShadowResMul;
    static IntBuffer activeDrawBuffers;
    static float frameTime;
    public static boolean[] shadowMipmapEnabled;
    private static String[] programNames;
    private static Pattern Px;
    static boolean needResetModels;
    static IntBuffer drawBuffersColorAtt0;
    public static int atlasSizeY;

    public static void setCameraShadow(float f) {
        int[] aint;
        double cameraPositionXValue;
        double cameraPositionYValue;
        double cameraPositionZValue;
        {
            Entity entity = shadersmod.client.Shaders.mc.getRenderViewEntity();
            int[] aint1 = ShaderOption.lz();
            cameraPositionXValue = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * f;
            aint = aint1;
            cameraPositionYValue = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * f;
            cameraPositionZValue = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * f;
            shadersmod.client.Shaders.cameraPositionX = cameraPositionXValue;
            shadersmod.client.Shaders.cameraPositionY = cameraPositionYValue;
            shadersmod.client.Shaders.cameraPositionZ = cameraPositionZValue;
            GL11.glGetFloat(
                    2983,
                    (FloatBuffer) ((Buffer) shadersmod.client.Shaders.projection).position(0));
            ShaderMath.invertMat4FBFA(
                    (FloatBuffer)
                            ((Buffer) shadersmod.client.Shaders.projectionInverse).position(0),
                    (FloatBuffer) ((Buffer) shadersmod.client.Shaders.projection).position(0),
                    shadersmod.client.Shaders.faProjectionInverse,
                    shadersmod.client.Shaders.faProjection);
            ((Buffer) shadersmod.client.Shaders.projection).position(0);
            ((Buffer) shadersmod.client.Shaders.projectionInverse).position(0);
            GL11.glGetFloat(
                    2982, (FloatBuffer) ((Buffer) shadersmod.client.Shaders.modelView).position(0));
            ShaderMath.invertMat4FBFA(
                    (FloatBuffer) ((Buffer) shadersmod.client.Shaders.modelViewInverse).position(0),
                    (FloatBuffer) ((Buffer) shadersmod.client.Shaders.modelView).position(0),
                    shadersmod.client.Shaders.faModelViewInverse,
                    shadersmod.client.Shaders.faModelView);
            ((Buffer) shadersmod.client.Shaders.modelView).position(0);
            ((Buffer) shadersmod.client.Shaders.modelViewInverse).position(0);
            GL11.glViewport(
                    0,
                    0,
                    shadersmod.client.Shaders.shadowMapWidth,
                    shadersmod.client.Shaders.shadowMapHeight);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            if (shadersmod.client.Shaders.shadowMapIsOrtho) {
                GL11.glOrtho(
                        -shadersmod.client.Shaders.shadowMapHalfPlane,
                        shadersmod.client.Shaders.shadowMapHalfPlane,
                        -shadersmod.client.Shaders.shadowMapHalfPlane,
                        shadersmod.client.Shaders.shadowMapHalfPlane,
                        0.05F,
                        256.0);
                if (!(aint == null)) {

                    GLU.gluPerspective(
                            shadersmod.client.Shaders.shadowMapFOV,
                            (float) shadersmod.client.Shaders.shadowMapWidth
                                    / shadersmod.client.Shaders.shadowMapHeight,
                            0.05F,
                            256.0F);
                }
            } else {

                GLU.gluPerspective(
                        shadersmod.client.Shaders.shadowMapFOV,
                        (float) shadersmod.client.Shaders.shadowMapWidth
                                / shadersmod.client.Shaders.shadowMapHeight,
                        0.05F,
                        256.0F);
            }
        }

        {
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0F, 0.0F, -100.0F);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            shadersmod.client.Shaders.celestialAngle =
                    shadersmod.client.Shaders.mc.world.getCelestialAngle(f);
            shadersmod.client.Shaders.sunAngle =
                    shadersmod.client.Shaders.celestialAngle < 0.75F
                            ? shadersmod.client.Shaders.celestialAngle + 0.25F
                            : shadersmod.client.Shaders.celestialAngle - 0.75F;
            float f1 = shadersmod.client.Shaders.celestialAngle * -360.0F;
            float f2 =
                    shadersmod.client.Shaders.shadowAngleInterval > 0.0F
                            ? f1 % shadersmod.client.Shaders.shadowAngleInterval
                                    - shadersmod.client.Shaders.shadowAngleInterval * 0.5F
                            : 0.0F;
            if (shadersmod.client.Shaders.sunAngle <= 0.5) {
                GL11.glRotatef(f1 - f2, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(shadersmod.client.Shaders.sunPathRotation, 1.0F, 0.0F, 0.0F);
                shadersmod.client.Shaders.shadowAngle = shadersmod.client.Shaders.sunAngle;
                if (!(aint == null)) {

                    GL11.glRotatef(f1 + 180.0F - f2, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(shadersmod.client.Shaders.sunPathRotation, 1.0F, 0.0F, 0.0F);
                    shadersmod.client.Shaders.shadowAngle =
                            shadersmod.client.Shaders.sunAngle - 0.5F;
                }
            } else {

                GL11.glRotatef(f1 + 180.0F - f2, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(shadersmod.client.Shaders.sunPathRotation, 1.0F, 0.0F, 0.0F);
                shadersmod.client.Shaders.shadowAngle = shadersmod.client.Shaders.sunAngle - 0.5F;
            }
        }

        if (shadersmod.client.Shaders.shadowMapIsOrtho) {
            float f3 = shadersmod.client.Shaders.shadowIntervalSize;
            float f4 = f3 / 2.0F;
            GL11.glTranslatef(
                    (float) cameraPositionXValue % f3 - f4,
                    (float) cameraPositionYValue % f3 - f4,
                    (float) cameraPositionZValue % f3 - f4);
        }

        float f10 = shadersmod.client.Shaders.sunAngle * (float) (Math.PI * 2);
        float f11 = (float) Math.cos(f10);
        float f5 = (float) Math.sin(f10);
        float f6 = shadersmod.client.Shaders.sunPathRotation * (float) (Math.PI * 2);
        float f7 = f11;
        float f8 = f5 * (float) Math.cos(f6);
        float f9 = f5 * (float) Math.sin(f6);
        if (shadersmod.client.Shaders.sunAngle > 0.5) {
            f7 = -f11;
            f8 = -f8;
            f9 = -f9;
        }

        shadersmod.client.Shaders.shadowLightPositionVector[0] = f7;
        shadersmod.client.Shaders.shadowLightPositionVector[1] = f8;
        shadersmod.client.Shaders.shadowLightPositionVector[2] = f9;
        shadersmod.client.Shaders.shadowLightPositionVector[3] = 0.0F;
        GL11.glGetFloat(
                2983,
                (FloatBuffer) ((Buffer) shadersmod.client.Shaders.shadowProjection).position(0));
        ShaderMath.invertMat4FBFA(
                (FloatBuffer)
                        ((Buffer) shadersmod.client.Shaders.shadowProjectionInverse).position(0),
                (FloatBuffer) ((Buffer) shadersmod.client.Shaders.shadowProjection).position(0),
                shadersmod.client.Shaders.faShadowProjectionInverse,
                shadersmod.client.Shaders.faShadowProjection);
        ((Buffer) shadersmod.client.Shaders.shadowProjection).position(0);
        ((Buffer) shadersmod.client.Shaders.shadowProjectionInverse).position(0);
        GL11.glGetFloat(
                2982,
                (FloatBuffer) ((Buffer) shadersmod.client.Shaders.shadowModelView).position(0));
        ShaderMath.invertMat4FBFA(
                (FloatBuffer)
                        ((Buffer) shadersmod.client.Shaders.shadowModelViewInverse).position(0),
                (FloatBuffer) ((Buffer) shadersmod.client.Shaders.shadowModelView).position(0),
                shadersmod.client.Shaders.faShadowModelViewInverse,
                shadersmod.client.Shaders.faShadowModelView);
        ((Buffer) shadersmod.client.Shaders.shadowModelView).position(0);
        ((Buffer) shadersmod.client.Shaders.shadowModelViewInverse).position(0);
        setProgramUniformMatrix4ARB(
                "gbufferProjection", false, shadersmod.client.Shaders.projection);
        setProgramUniformMatrix4ARB(
                "gbufferProjectionInverse", false, shadersmod.client.Shaders.projectionInverse);
        setProgramUniformMatrix4ARB(
                "gbufferPreviousProjection", false, shadersmod.client.Shaders.previousProjection);
        setProgramUniformMatrix4ARB("gbufferModelView", false, shadersmod.client.Shaders.modelView);
        setProgramUniformMatrix4ARB(
                "gbufferModelViewInverse", false, shadersmod.client.Shaders.modelViewInverse);
        setProgramUniformMatrix4ARB(
                "gbufferPreviousModelView", false, shadersmod.client.Shaders.previousModelView);
        setProgramUniformMatrix4ARB(
                "shadowProjection", false, shadersmod.client.Shaders.shadowProjection);
        setProgramUniformMatrix4ARB(
                "shadowProjectionInverse",
                false,
                shadersmod.client.Shaders.shadowProjectionInverse);
        setProgramUniformMatrix4ARB(
                "shadowModelView", false, shadersmod.client.Shaders.shadowModelView);
        setProgramUniformMatrix4ARB(
                "shadowModelViewInverse", false, shadersmod.client.Shaders.shadowModelViewInverse);
        shadersmod.client.Shaders.mc.gameSettings.thirdPersonView = 1;
        checkGLError("setCamera");
    }

    public static int setEntityData2(int p171_0) {
        shadersmod.client.Shaders.entityData[shadersmod.client.Shaders.entityDataIndex * 2 + 1] =
                shadersmod.client.Shaders.entityData[
                                        shadersmod.client.Shaders.entityDataIndex * 2 + 1]
                                & -65536
                        | p171_0 & 65535;
        return p171_0;
    }

    public static void beginClouds() {
        shadersmod.client.Shaders.fogEnabled = true;
        shadersmod.client.Shaders.pushEntity(-3, 0);
        shadersmod.client.Shaders.useProgram(6);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public static String getEnumShaderOption(EnumShaderOption enumShaderOption) {
        switch (enumShaderOption) {
            case dl:
                return Integer.toString(shadersmod.client.Shaders.configAntialiasingLevel);
            case ds:
                return Boolean.toString(shadersmod.client.Shaders.configNormalMap);
            case dd:
                return Boolean.toString(shadersmod.client.Shaders.configSpecularMap);
            case dq:
                return Float.toString(shadersmod.client.Shaders.configRenderResMul);
            case di:
                return Float.toString(shadersmod.client.Shaders.configShadowResMul);
            case dn:
                return Float.toString(shadersmod.client.Shaders.configHandDepthMul);
            case db:
                return Boolean.toString(shadersmod.client.Shaders.configCloudShadow);
            case dp:
                return shadersmod.client.Shaders.configOldHandLight.getPropertyValue();
            case SHADER_PACK:
                return shadersmod.client.Shaders.currentshadername;
            case dm:
                return Boolean.toString(shadersmod.client.Shaders.configTweakBlockDamage);
            case du:
                return Boolean.toString(shadersmod.client.Shaders.configShadowClipFrustrum);
            case df:
                return Integer.toString(shadersmod.client.Shaders.configTexMinFilB);
            case dv:
                return Integer.toString(shadersmod.client.Shaders.configTexMinFilN);
            case dk:
                return Integer.toString(shadersmod.client.Shaders.configTexMinFilS);
            case de:
                return Integer.toString(shadersmod.client.Shaders.configTexMagFilB);
            case dh:
                return Integer.toString(shadersmod.client.Shaders.configTexMagFilB);
            case dc:
                return Integer.toString(shadersmod.client.Shaders.configTexMagFilB);
            default:
                throw new IllegalArgumentException("Unknown option: " + enumShaderOption);
        }
    }

    public static boolean isProgramPath(String text) {
        try {
            if (text == null) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }

        try {
            if (text.length() <= 0) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception1));
        }

        int i = text.lastIndexOf(shadersmod.client.Shaders.packedTextFragments[23]);
        if (i >= 0) {
            text = text.substring(i + 1);
        }

        return java.util.Arrays.asList(shadersmod.client.Shaders.programNames).contains(text);
    }

    private static void resizeShadow() {
        shadersmod.client.Shaders.needResizeShadow = false;
        shadersmod.client.Shaders.shadowMapWidth =
                java.lang.Math.round(
                        shadersmod.client.Shaders.spShadowMapWidth
                                * shadersmod.client.Shaders.configShadowResMul);
        shadersmod.client.Shaders.shadowMapHeight =
                java.lang.Math.round(
                        shadersmod.client.Shaders.spShadowMapHeight
                                * shadersmod.client.Shaders.configShadowResMul);
        shadersmod.client.Shaders.setupShadowFrameBuffer();
    }

    public static void onTexture2DDisabled() {
        int[] aint = ShaderOption.lz();
        if (shadersmod.client.Shaders.isRenderingSky) {
            useProgram(4);
            if (aint == null) {
                return;
            }
        }

        if (shadersmod.client.Shaders.activeProgram == 2
                || shadersmod.client.Shaders.activeProgram == 3) {
            useProgram(1);
        }
    }

    public static void endEntities() {
        if (shadersmod.client.Shaders.isRenderingWorld) {
            shadersmod.client.Shaders.useProgram(shadersmod.client.Shaders.lightmapEnabled ? 3 : 2);
        }
    }

    public static void nextAntialiasingLevel() {
        try {
            shadersmod.client.Shaders.configAntialiasingLevel += 2;
            shadersmod.client.Shaders.configAntialiasingLevel =
                    shadersmod.client.Shaders.configAntialiasingLevel / 2 * 2;
            if (shadersmod.client.Shaders.configAntialiasingLevel > 4) {
                shadersmod.client.Shaders.configAntialiasingLevel = 0;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }

        shadersmod.client.Shaders.configAntialiasingLevel =
                optifine.OptiFineConfig.limit(
                        shadersmod.client.Shaders.configAntialiasingLevel, 0, 4);
    }

    public static void renderSkyFog() {
        org.lwjgl.opengl.GL11.glColor3f(
                shadersmod.client.Shaders.fogColorR,
                shadersmod.client.Shaders.fogColorG,
                shadersmod.client.Shaders.fogColorB);
        shadersmod.client.Shaders.drawSkyFogRing();
        org.lwjgl.opengl.GL11.glColor3f(
                shadersmod.client.Shaders.skyColorR,
                shadersmod.client.Shaders.skyColorG,
                shadersmod.client.Shaders.skyColorB);
    }

    static void ha() {}

    public static int checkGLError(String text, String textValue, String textValue2) {
        int i = GL11.glGetError();
        if (i != 0) {
            System.err.format(
                    "GL error 0x%04x: %s at %s %s %s\n",
                    i, GLU.gluErrorString(i), text, textValue, textValue2);
        }

        return i;
    }

    public static void resetDisplayListModel(ModelBase modelBase) {
        int[] aint = ShaderOption.lz();
        if (modelBase != null) {
            for (Object object : modelBase.boxList) {
                if (object instanceof ModelRenderer) {
                    resetDisplayListModelRenderer((ModelRenderer) object);
                }

                if (aint != null) {
                    break;
                }
            }
        }
    }

    private static int getBufferIndexFromString(String text) {
        return text.equals("colortex0") || text.equals("gcolor")
                ? 0
                : (text.equals("colortex1") || text.equals("gdepth")
                        ? 1
                        : (text.equals("colortex2") || text.equals("gnormal")
                                ? 2
                                : (text.equals("colortex3") || text.equals("composite")
                                        ? 3
                                        : (text.equals("colortex4") || text.equals("gaux1")
                                                ? 4
                                                : (text.equals("colortex5") || text.equals("gaux2")
                                                        ? 5
                                                        : (!text.equals("colortex6")
                                                                        && !text.equals("gaux3")
                                                                ? (!text.equals("colortex7")
                                                                                && !text.equals(
                                                                                        "gaux4")
                                                                        ? -1
                                                                        : 7)
                                                                : 6))))));
    }

    public static void beginLivingDamage() {
        if (shadersmod.client.Shaders.isRenderingWorld) {
            shadersmod.client.ShadersTex.bindTexture(shadersmod.client.Shaders.defaultTexture);
            if (!shadersmod.client.Shaders.isShadowPass) {
                shadersmod.client.Shaders.setDrawBuffers(
                        shadersmod.client.Shaders.drawBuffersColorAtt0);
            }
        }
    }

    public static void scheduleResize() {
        shadersmod.client.Shaders.renderDisplayHeight = 0;
    }

    public static int checkFramebufferStatus(String text) {
        int i = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
        if (i != 36053) {
            System.err.format("FramebufferStatus 0x%04X at %s\n", i, text);
        }

        return i;
    }

    public static void checkShadersModInstalled() {
        try {
            Class oclass = Class.forName("shadersmod.transform.SMCClassTransformer");
        } catch (Throwable throwable) {
            return;
        }

        throw new RuntimeException(
                "Shaders Mod detected. Please remove it, OptiFine has built-in support for"
                        + " shaders.");
    }

    private static void method_6fe7e1f7c0060d0054cf(String text) {
        shadersmod.client.Shaders.mc
                .ingameGUI
                .getChatGUI()
                .printChatMessage(new net.minecraft.util.ChatComponentText(text));
    }

    public static ShaderOption[] getChangedOptions(ShaderOption[] shaderOptionArray) {
        int[] aint1 = ShaderOption.lz();
        ArrayList arrayList = new ArrayList();
        int[] aint = aint1;
        int i = 0;

        while (i < shaderOptionArray.length) {
            ShaderOption shaderOption = shaderOptionArray[i];
            if (shaderOption.isEnabled() && shaderOption.isChanged()) {
                arrayList.add(shaderOption);
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        return ((shadersmod.client.ShaderOption[])
                arrayList.toArray(new ShaderOption[arrayList.size()]));
    }

    public static void setFog(int i) {
        GlStateManager.setFog(i);
        i = i;
        if (shadersmod.client.Shaders.fogEnabled) {
            setProgramUniform1i("fogMode", i);
        }
    }

    public static void mS() {
        int[] aint = ShaderOption.lz();
        if (!shadersmod.client.Shaders.isShadowPass) {
            checkGLError("pre-renderCompositeFinal");
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, 1.0, 0.0, 1.0, 0.0, 1.0);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableTexture2D();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.enableDepth();
            GlStateManager.depthFunc(519);
            GlStateManager.depthMask(false);
            GlStateManager.disableLighting();
            if (shadersmod.client.Shaders.usedShadowDepthBuffers >= 1) {
                GlStateManager.setActiveTexture(33988);
                GlStateManager.bindTexture(shadersmod.client.Shaders.sfbDepthTextures.get(0));
                if (shadersmod.client.Shaders.usedShadowDepthBuffers >= 2) {
                    GlStateManager.setActiveTexture(33989);
                    GlStateManager.bindTexture(shadersmod.client.Shaders.sfbDepthTextures.get(1));
                }
            }

            int i = 0;

            while (i < shadersmod.client.Shaders.usedColorBuffers) {
                GlStateManager.setActiveTexture(
                        33984 + shadersmod.client.Shaders.colorTextureTextureImageUnit[i]);
                GlStateManager.bindTexture(shadersmod.client.Shaders.dfbColorTexturesA[i]);
                i++;
                if (aint != null) {
                    break;
                }
            }

            GlStateManager.setActiveTexture(33990);
            GlStateManager.bindTexture(shadersmod.client.Shaders.dfbDepthTextures.get(0));
            if (shadersmod.client.Shaders.usedDepthBuffers >= 2) {
                GlStateManager.setActiveTexture(33995);
                GlStateManager.bindTexture(shadersmod.client.Shaders.dfbDepthTextures.get(1));
                if (shadersmod.client.Shaders.usedDepthBuffers >= 3) {
                    GlStateManager.setActiveTexture(33996);
                    GlStateManager.bindTexture(shadersmod.client.Shaders.dfbDepthTextures.get(2));
                }
            }

            i = 0;

            while (i < shadersmod.client.Shaders.usedShadowColorBuffers) {
                GlStateManager.setActiveTexture(33997 + i);
                GlStateManager.bindTexture(shadersmod.client.Shaders.sfbColorTextures.get(i));
                i++;
                if (aint != null) {
                    break;
                }
            }

            if (shadersmod.client.Shaders.noiseTextureEnabled) {
                GlStateManager.setActiveTexture(
                        33984 + shadersmod.client.Shaders.customUniforms.textureUnit);
                GlStateManager.bindTexture(shadersmod.client.Shaders.customUniforms.getTexID());
                GL11.glTexParameteri(3553, 10242, 10497);
                GL11.glTexParameteri(3553, 10243, 10497);
                GL11.glTexParameteri(3553, 10240, 9729);
                GL11.glTexParameteri(3553, 10241, 9729);
            }

            GlStateManager.setActiveTexture(33984);

            int j = 0;

            while (j < shadersmod.client.Shaders.usedColorBuffers) {
                EXTFramebufferObject.glFramebufferTexture2DEXT(
                        36160,
                        36064 + j,
                        3553,
                        shadersmod.client.Shaders.dfbColorTexturesA[8 + j],
                        0);
                j++;
                if (aint != null) {
                    break;
                }
            }

            EXTFramebufferObject.glFramebufferTexture2DEXT(
                    36160, 36096, 3553, shadersmod.client.Shaders.dfbDepthTextures.get(0), 0);
            GL20.glDrawBuffers(shadersmod.client.Shaders.dfbDrawBuffers);
            checkGLError("pre-composite");
            j = 0;

            while (j < 8) {
                if (shadersmod.client.Shaders.programsID[21 + j] != 0) {
                    useProgram(21 + j);
                    checkGLError(shadersmod.client.Shaders.programNames[21 + j]);
                    if (shadersmod.client.Shaders.activeCompositeMipmapSetting != 0) {
                        nS();
                    }

                    drawComposite();
                    int k = 0;

                    while (k < shadersmod.client.Shaders.usedColorBuffers) {
                        if (shadersmod.client.Shaders.programsToggleColorTextures[21 + j][k]) {
                            int l = shadersmod.client.Shaders.colorTexturesToggle[k];
                            int i1 = shadersmod.client.Shaders.colorTexturesToggle[k] = 8 - l;
                            GlStateManager.setActiveTexture(
                                    33984
                                            + shadersmod.client.Shaders
                                                    .colorTextureTextureImageUnit[k]);
                            GlStateManager.bindTexture(
                                    shadersmod.client.Shaders.dfbColorTexturesA[i1 + k]);
                            EXTFramebufferObject.glFramebufferTexture2DEXT(
                                    36160,
                                    36064 + k,
                                    3553,
                                    shadersmod.client.Shaders.dfbColorTexturesA[l + k],
                                    0);
                        }

                        k++;
                        if (aint != null) {
                            break;
                        }
                    }

                    GlStateManager.setActiveTexture(33984);
                }

                j++;
                if (aint != null) {
                    break;
                }
            }

            checkGLError("composite");
            shadersmod.client.Shaders.isRenderingDfb = false;
            shadersmod.client.Shaders.mc.getFramebuffer().bindFramebuffer(true);
            OpenGlHelper.glFramebufferTexture2D(
                    OpenGlHelper.GL_FRAMEBUFFER,
                    OpenGlHelper.dD,
                    3553,
                    shadersmod.client.Shaders.mc.getFramebuffer().framebufferTexture,
                    0);
            GL11.glViewport(
                    0,
                    0,
                    shadersmod.client.Shaders.mc.displayWidth,
                    shadersmod.client.Shaders.mc.displayHeight);
            if (EntityRenderer.anaglyphEnable) {
                int j1 = EntityRenderer.anaglyphPassIndex != 0 ? 1 : 0;
                GlStateManager.colorMask(
                        remix.compat.compiler.JvmBooleanSlots.fromInt(j1), j1 == 0, j1 == 0, true);
            }

            GlStateManager.depthMask(true);
            GL11.glClearColor(
                    shadersmod.client.Shaders.clearColorR,
                    shadersmod.client.Shaders.clearColorG,
                    shadersmod.client.Shaders.clearColorB,
                    1.0F);
            GL11.glClear(16640);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableTexture2D();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.enableDepth();
            GlStateManager.depthFunc(519);
            GlStateManager.depthMask(false);
            checkGLError("pre-final");
            useProgram(29);
            checkGLError("final");
            if (shadersmod.client.Shaders.activeCompositeMipmapSetting != 0) {
                nS();
            }

            drawComposite();
            checkGLError("renderCompositeFinal");
            shadersmod.client.Shaders.isCompositeRendered = true;
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            useProgram(0);
        }
    }

    public static void mT() {
        int[] aint = ShaderOption.lz();
        if (shadersmod.client.Shaders.needResetModels) {
            shadersmod.client.Shaders.needResetModels = false;
            ShaderLog.info("Reset model renderers");

            for (java.util.Iterator<?> __recoveryIterator171_39835 =
                            (shadersmod.client.Shaders.mc
                                            .getRenderManager()
                                            .getEntityRenderMap()
                                            .values())
                                    .iterator();
                    (__recoveryIterator171_39835).hasNext(); ) {
                Render render = (Render) (__recoveryIterator171_39835).next();
                if (render instanceof RendererLivingEntity) {
                    RendererLivingEntity rendererLivingEntity = (RendererLivingEntity) render;
                    resetDisplayListModel(rendererLivingEntity.getMainModel());
                }

                if (aint != null) {
                    break;
                }
            }
        }
    }

    public static void setProgramUniform2i(String text, int p171_1, int p171_2) {
        int k = shadersmod.client.Shaders.programsID[shadersmod.client.Shaders.activeProgram];
        if (k != 0) {
            int l = org.lwjgl.opengl.ARBShaderObjects.glGetUniformLocationARB(k, text);
            org.lwjgl.opengl.ARBShaderObjects.glUniform2iARB(l, p171_1, p171_2);
            shadersmod.client.Shaders.checkGLError(
                    shadersmod.client.Shaders.programNames[shadersmod.client.Shaders.activeProgram],
                    text);
        }
    }

    public static void glDisableWrapper(int i) {
        int[] aint1 = ShaderOption.lz();
        GL11.glDisable(i);
        int[] aint = aint1;
        if (i == 3553) {
            onTexture2DDisabled();
            if (aint == null) {
                return;
            }
        }

        if (i == 2912) {
            disableFog();
        }
    }

    public static void endSky() {
        byte b0;
        label16:
        {
            try {
                shadersmod.client.Shaders.isRenderingSky = false;
                shadersmod.client.Shaders.setDrawBuffers(shadersmod.client.Shaders.dfbDrawBuffers);
                if (shadersmod.client.Shaders.lightmapEnabled) {
                    b0 = 3;
                    break label16;
                }
            } catch (java.lang.IllegalArgumentException illegalargumentexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        shadersmod.client.Shaders.a(illegalargumentexception));
            }

            b0 = 2;
        }

        shadersmod.client.Shaders.useProgram(b0);
        shadersmod.client.Shaders.popEntity();
    }

    private static FloatBuffer nextFloatBuffer(int p171_0) {
        java.nio.ByteBuffer bytebuffer = shadersmod.client.Shaders.bigBuffer;
        int j = bytebuffer.limit();
        ((java.nio.Buffer) bytebuffer).position(j).limit(j + p171_0 * 4);
        return bytebuffer.asFloatBuffer();
    }

    public static void clearRenderBuffers() {
        int[] aint = ShaderOption.lz();
        if (shadersmod.client.Shaders.isShadowPass) {
            checkGLError("shadow clear pre");
            EXTFramebufferObject.glFramebufferTexture2DEXT(
                    36160, 36096, 3553, shadersmod.client.Shaders.sfbDepthTextures.get(0), 0);
            GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
            GL20.glDrawBuffers(shadersmod.client.Shaders.programsDrawBuffers[30]);
            checkFramebufferStatus("shadow clear");
            GL11.glClear(16640);
            checkGLError("shadow clear");
            if (aint == null) {
                return;
            }
        }

        checkGLError("clear pre");
        GL20.glDrawBuffers(36064);
        GL11.glClear(16384);
        GL20.glDrawBuffers(36065);
        GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glClear(16384);
        int i = 2;

        while (i < shadersmod.client.Shaders.usedColorBuffers) {
            GL20.glDrawBuffers(36064 + i);
            GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            GL11.glClear(16384);
            i++;
            if (aint != null) {
                break;
            }
        }

        setDrawBuffers(shadersmod.client.Shaders.dfbDrawBuffers);
        checkFramebufferStatus("clear");
        checkGLError("clear");
    }

    private static IntBuffer[] nextIntBufferArray(int p171_0, int p171_1) {
        java.nio.IntBuffer[] aintbuffer = new java.nio.IntBuffer[p171_0];
        int k = 0;

        try {
            while (k < p171_0) {
                aintbuffer[k] = shadersmod.client.Shaders.nextIntBuffer(p171_1);
                k++;
            }

            return aintbuffer;
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }
    }

    public static void beginRenderPass(int i, float f, long j) {
        if (!shadersmod.client.Shaders.isShadowPass) {
            EXTFramebufferObject.glBindFramebufferEXT(36160, shadersmod.client.Shaders.dfb);
            GL11.glViewport(
                    0,
                    0,
                    shadersmod.client.Shaders.renderWidth,
                    shadersmod.client.Shaders.renderHeight);
            shadersmod.client.Shaders.activeDrawBuffers = null;
            ShadersTex.bindNSTextures(shadersmod.client.Shaders.defaultTexture.getMultiTexID());
            useProgram(2);
            checkGLError("end beginRenderPass");
        }
    }

    public static int getEntityData2() {
        return shadersmod.client.Shaders.entityData[
                shadersmod.client.Shaders.entityDataIndex * 2 + 1];
    }

    private static IntBuffer nextIntBuffer(int p171_0) {
        java.nio.ByteBuffer bytebuffer = shadersmod.client.Shaders.bigBuffer;
        int j = bytebuffer.limit();
        ((java.nio.Buffer) bytebuffer).position(j).limit(j + p171_0 * 4);
        return bytebuffer.asIntBuffer();
    }

    public static void endClouds() {
        byte b0;
        label16:
        {
            try {
                shadersmod.client.Shaders.disableFog();
                shadersmod.client.Shaders.popEntity();
                if (shadersmod.client.Shaders.lightmapEnabled) {
                    b0 = 3;
                    break label16;
                }
            } catch (java.lang.IllegalArgumentException illegalargumentexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        shadersmod.client.Shaders.a(illegalargumentexception));
            }

            b0 = 2;
        }

        shadersmod.client.Shaders.useProgram(b0);
    }

    public static void onTexture2DEnabled() {
        int[] aint = ShaderOption.lz();
        if (shadersmod.client.Shaders.isRenderingSky) {
            useProgram(5);
            if (aint == null) {
                return;
            }
        }

        if (shadersmod.client.Shaders.activeProgram == 1) {
            useProgram(shadersmod.client.Shaders.lightmapEnabled ? 3 : 2);
        }
    }

    private static void checkWorldChanged(World world) {
        if (shadersmod.client.Shaders.currentWorld != world) {
            net.minecraft.world.World worldValue = shadersmod.client.Shaders.currentWorld;
            shadersmod.client.Shaders.currentWorld = world;
            if (worldValue != null && world != null) {
                int dimensionId = worldValue.provider.getDimensionId();
                int j = world.provider.getDimensionId();
                boolean flag =
                        ((boolean)
                                (((java.util.List) (shadersmod.client.Shaders.shaderPackDimensions))
                                        .contains(
                                                ((java.lang.Object)
                                                        (((java.lang.Integer)
                                                                (java.lang.Integer.valueOf(
                                                                        ((int)
                                                                                (dimensionId))))))))));
                boolean flag1 =
                        ((boolean)
                                (((java.util.List) (shadersmod.client.Shaders.shaderPackDimensions))
                                        .contains(
                                                ((java.lang.Object)
                                                        (((java.lang.Integer)
                                                                (java.lang.Integer.valueOf(
                                                                        ((int) (j))))))))));
                if (flag || flag1) {
                    shadersmod.client.Shaders.uninit();
                }
            }
        }
    }

    public static ShaderOption[] getShaderPackOptions(String text) {
        int[] aint1 = ShaderOption.lz();
        ShaderOption[] shaderOptionArray =
                (ShaderOption[]) shadersmod.client.Shaders.shaderPackOptions.clone();
        int[] aint = aint1;
        if (shadersmod.client.Shaders.shaderPackGuiScreens == null) {
            if (shadersmod.client.Shaders.shaderPackProfiles != null) {
                ShaderOptionProfile shaderOptionProfile =
                        new ShaderOptionProfile(
                                shadersmod.client.Shaders.shaderPackProfiles, shaderOptionArray);
                shaderOptionArray =
                        (ShaderOption[])
                                OptiFineConfig.addObjectToArray(
                                        shaderOptionArray, shaderOptionProfile, 0);
            }

            return getVisibleOptions(shaderOptionArray);
        } else {
            String textValue = text != null ? "screen." + text : "screen";
            ShaderOption[] shaderOptionArrayValue =
                    ((shadersmod.client.ShaderOption[])
                            shadersmod.client.Shaders.shaderPackGuiScreens.get(textValue));
            if (shaderOptionArrayValue == null) {
                return new ShaderOption[0];
            }

            ArrayList arrayList = new ArrayList();
            int i = 0;

            while (i < shaderOptionArrayValue.length) {
                label51:
                {
                    ShaderOption shaderOption = shaderOptionArrayValue[i];
                    if (shaderOption == null) {
                        arrayList.add(null);
                        if (aint == null) {
                            break label51;
                        }
                    }

                    if (shaderOption instanceof ShaderOptionRest) {
                        ShaderOption[] shaderOptionArrayValue2 =
                                getShaderOptionsRest(
                                        shadersmod.client.Shaders.shaderPackGuiScreens,
                                        shaderOptionArray);
                        arrayList.addAll(Arrays.asList(shaderOptionArrayValue2));
                        if (aint == null) {
                            break label51;
                        }
                    }

                    arrayList.add(shaderOption);
                }

                i++;
                if (aint != null) {
                    break;
                }
            }

            return ((shadersmod.client.ShaderOption[])
                    arrayList.toArray(new ShaderOption[arrayList.size()]));
        }
    }

    public static void setDrawBuffers(IntBuffer intBuffer) {
        if (intBuffer == null) {
            intBuffer = shadersmod.client.Shaders.drawBuffersNone;
        }

        try {
            if (shadersmod.client.Shaders.activeDrawBuffers != intBuffer) {
                shadersmod.client.Shaders.activeDrawBuffers = intBuffer;
                org.lwjgl.opengl.GL20.glDrawBuffers(intBuffer);
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }
    }

    public static void beginEntities() {
        try {
            if (shadersmod.client.Shaders.isRenderingWorld) {
                shadersmod.client.Shaders.useProgram(16);
                shadersmod.client.Shaders.mT();
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }
    }

    public static void setShaderPack(String text) {
        shadersmod.client.Shaders.currentshadername = text;
        shadersmod.client.Shaders.shadersConfig.setProperty(
                EnumShaderOption.SHADER_PACK.getPropertyKey(), text);
        loadShaderPack();
    }

    private static void saveShaderPackOptions(
            ShaderOption[] shaderOptionArray, IShaderPack iShaderPack) {
        int[] aint1 = ShaderOption.lz();
        Properties properties = new Properties();
        int[] aint = aint1;
        if (shadersmod.client.Shaders.shaderPackOptions != null) {
            int i = 0;

            while (i < shaderOptionArray.length) {
                ShaderOption shaderOption = shaderOptionArray[i];
                if (shaderOption.isChanged() && shaderOption.isEnabled()) {
                    properties.setProperty(shaderOption.getName(), shaderOption.getValue());
                }

                i++;
                if (aint != null) {
                    break;
                }
            }
        }

        try {
            saveOptionProperties(iShaderPack, properties);
        } catch (IOException ioexception) {
            OptiFineConfig.warn(
                    "[Shaders] Error saving configuration for "
                            + shadersmod.client.Shaders.shaderPack.getName());
            ioexception.printStackTrace();
        }
    }

    public static void sglDisableT2D(int i) {
        GL11.glDisable(i);
        onTexture2DDisabled();
    }

    public static int setFogMode(int fogModeValue) {
        shadersmod.client.Shaders.fogMode = fogModeValue;
        return fogModeValue;
    }

    public static void uninit() {
        int[] aint = ShaderOption.lz();
        if (shadersmod.client.Shaders.isShaderPackInitialized) {
            checkGLError("Shaders.uninit pre");
            int i = 0;

            while (i < 33) {
                if (shadersmod.client.Shaders.programsRef[i] != 0) {
                    ARBShaderObjects.glDeleteObjectARB(shadersmod.client.Shaders.programsRef[i]);
                    checkGLError("del programRef");
                }

                shadersmod.client.Shaders.programsRef[i] = 0;
                shadersmod.client.Shaders.programsID[i] = 0;
                shadersmod.client.Shaders.programsDrawBufSettings[i] = null;
                shadersmod.client.Shaders.programsDrawBuffers[i] = null;
                shadersmod.client.Shaders.programsCompositeMipmapSetting[i] = 0;
                i++;
                if (aint != null) {
                    break;
                }
            }

            if (shadersmod.client.Shaders.dfb != 0) {
                EXTFramebufferObject.glDeleteFramebuffersEXT(shadersmod.client.Shaders.dfb);
                shadersmod.client.Shaders.dfb = 0;
                checkGLError("del dfb");
            }

            if (shadersmod.client.Shaders.sfb != 0) {
                EXTFramebufferObject.glDeleteFramebuffersEXT(shadersmod.client.Shaders.sfb);
                shadersmod.client.Shaders.sfb = 0;
                checkGLError("del sfb");
            }

            if (shadersmod.client.Shaders.dfbDepthTextures != null) {
                GlStateManager.deleteTextures(shadersmod.client.Shaders.dfbDepthTextures);
                fillIntBufferZero(shadersmod.client.Shaders.dfbDepthTextures);
                checkGLError("del dfbDepthTextures");
            }

            if (shadersmod.client.Shaders.dfbColorTextures != null) {
                GlStateManager.deleteTextures(shadersmod.client.Shaders.dfbColorTextures);
                fillIntBufferZero(shadersmod.client.Shaders.dfbColorTextures);
                checkGLError("del dfbTextures");
            }

            if (shadersmod.client.Shaders.sfbDepthTextures != null) {
                GlStateManager.deleteTextures(shadersmod.client.Shaders.sfbDepthTextures);
                fillIntBufferZero(shadersmod.client.Shaders.sfbDepthTextures);
                checkGLError("del shadow depth");
            }

            if (shadersmod.client.Shaders.sfbColorTextures != null) {
                GlStateManager.deleteTextures(shadersmod.client.Shaders.sfbColorTextures);
                fillIntBufferZero(shadersmod.client.Shaders.sfbColorTextures);
                checkGLError("del shadow color");
            }

            if (shadersmod.client.Shaders.dfbDrawBuffers != null) {
                fillIntBufferZero(shadersmod.client.Shaders.dfbDrawBuffers);
            }

            if (shadersmod.client.Shaders.customUniforms != null) {
                shadersmod.client.Shaders.customUniforms.deleteTexture();
                shadersmod.client.Shaders.customUniforms = null;
            }

            ShaderLog.info("Uninit");
            shadersmod.client.Shaders.shadowPassInterval = 0;
            shadersmod.client.Shaders.shouldSkipDefaultShadow = false;
            shadersmod.client.Shaders.isShaderPackInitialized = false;
            checkGLError("Shaders.uninit");
        }
    }

    // region String decoding support

    // endregion

    private static ShaderOption[] getVisibleOptions(ShaderOption[] shaderOptionArray) {
        ArrayList arrayList = new ArrayList();
        int[] aint1 = ShaderOption.lz();
        int i = 0;
        int[] aint = aint1;

        while (i < shaderOptionArray.length) {
            ShaderOption shaderOption = shaderOptionArray[i];
            if (shaderOption.isVisible()) {
                arrayList.add(shaderOption);
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        return ((shadersmod.client.ShaderOption[])
                arrayList.toArray(new ShaderOption[arrayList.size()]));
    }

    public static void endLivingDamage() {
        if (shadersmod.client.Shaders.isRenderingWorld && !shadersmod.client.Shaders.isShadowPass) {
            shadersmod.client.Shaders.setDrawBuffers(
                    shadersmod.client.Shaders.programsDrawBuffers[16]);
        }
    }

    static String versiontostring(int p171_0) {
        java.lang.String text = java.lang.Integer.toString(p171_0);
        return java.lang.Integer.toString(java.lang.Integer.parseInt(text.substring(1, 3)))
                + shadersmod.client.Shaders.packedTextFragments[6]
                + java.lang.Integer.toString(java.lang.Integer.parseInt(text.substring(3, 5)))
                + shadersmod.client.Shaders.packedTextFragments[7]
                + java.lang.Integer.toString(java.lang.Integer.parseInt(text.substring(5)));
    }

    public static void init() {
        int[] aint;
        byte b0;
        {
            aint = ShaderOption.lz();
            if (!shadersmod.client.Shaders.isInitializedOnce) {
                shadersmod.client.Shaders.isInitializedOnce = true;
                b0 = 1;
                if (!(aint == null)) {

                    b0 = 0;
                }
            } else {

                b0 = 0;
            }
        }

        if (!shadersmod.client.Shaders.isShaderPackInitialized) {
            checkGLError("Shaders.init pre");
            if (getShaderPackName() != null) {}

            if (!shadersmod.client.Shaders.capabilities.OpenGL20) {
                printChatAndLogError("No OpenGL 2.0");
            }

            if (!shadersmod.client.Shaders.capabilities.GL_EXT_framebuffer_object) {
                printChatAndLogError("No EXT_framebuffer_object");
            }

            ((Buffer) shadersmod.client.Shaders.dfbDrawBuffers).position(0).limit(8);
            ((Buffer) shadersmod.client.Shaders.dfbColorTextures).position(0).limit(16);
            ((Buffer) shadersmod.client.Shaders.dfbDepthTextures).position(0).limit(3);
            ((Buffer) shadersmod.client.Shaders.sfbDrawBuffers).position(0).limit(8);
            ((Buffer) shadersmod.client.Shaders.sfbDepthTextures).position(0).limit(2);
            ((Buffer) shadersmod.client.Shaders.sfbColorTextures).position(0).limit(8);
            shadersmod.client.Shaders.usedColorBuffers = 4;
            shadersmod.client.Shaders.usedDepthBuffers = 1;
            shadersmod.client.Shaders.usedShadowColorBuffers = 0;
            shadersmod.client.Shaders.usedShadowDepthBuffers = 0;
            shadersmod.client.Shaders.usedColorAttachs = 1;
            shadersmod.client.Shaders.usedDrawBuffers = 1;
            Arrays.fill(shadersmod.client.Shaders.gbuffersFormat, 6408);
            Arrays.fill(shadersmod.client.Shaders.shadowHardwareFilteringEnabled, false);
            Arrays.fill(shadersmod.client.Shaders.shadowMipmapEnabled, false);
            Arrays.fill(shadersmod.client.Shaders.shadowFilterNearest, false);
            Arrays.fill(shadersmod.client.Shaders.shadowColorMipmapEnabled, false);
            Arrays.fill(shadersmod.client.Shaders.shadowColorFilterNearest, false);
            shadersmod.client.Shaders.centerDepthSmoothEnabled = false;
            shadersmod.client.Shaders.noiseTextureEnabled = false;
            shadersmod.client.Shaders.sunPathRotation = 0.0F;
            shadersmod.client.Shaders.shadowIntervalSize = 2.0F;
            MP = 0.8F;
            shadersmod.client.Shaders.aoLevel = 1.0F - MP;
            shadersmod.client.Shaders.useEntityAttrib = false;
            shadersmod.client.Shaders.useMidTexCoordAttrib = false;
            shadersmod.client.Shaders.useMultiTexCoord3Attrib = false;
            shadersmod.client.Shaders.useTangentAttrib = false;
            shadersmod.client.Shaders.waterShadowEnabled = false;
            shadersmod.client.Shaders.updateChunksErrorRecorded = false;
            updateBlockLightLevel();
            ShaderProfile shaderProfile =
                    ShaderUtils.detectProfile(
                            shadersmod.client.Shaders.shaderPackProfiles,
                            shadersmod.client.Shaders.shaderPackOptions,
                            false);
            String text = packedTextFragments[12];
            if (shadersmod.client.Shaders.currentWorld != null) {
                int dimensionId = shadersmod.client.Shaders.currentWorld.provider.getDimensionId();
                if (shadersmod.client.Shaders.shaderPackDimensions.contains(dimensionId)) {
                    text = "world" + dimensionId + packedTextFragments[13];
                }
            }

            if (shadersmod.client.Shaders.saveFinalShaders) {
                clearDirectory(new File(shadersmod.client.Shaders.shaderpacksdir, "debug"));
            }

            int j1 = 0;

            while (j1 < 33) {
                label267:
                {
                    String textValue = shadersmod.client.Shaders.programNames[j1];
                    if (textValue.equals(packedTextFragments[14])) {
                        shadersmod.client.Shaders.programsID[j1] =
                                shadersmod.client.Shaders.programsRef[j1] = 0;
                        shadersmod.client.Shaders.programsDrawBufSettings[j1] = null;
                        shadersmod.client.Shaders.programsColorAtmSettings[j1] = null;
                        shadersmod.client.Shaders.programsCompositeMipmapSetting[j1] = 0;
                        if (aint == null) {
                            break label267;
                        }
                    }

                    shadersmod.client.Shaders.newDrawBufSetting = null;
                    shadersmod.client.Shaders.newColorAtmSetting = null;
                    shadersmod.client.Shaders.newCompositeMipmapSetting = 0;
                    String textValue2 = text + textValue;
                    if (shaderProfile != null && shaderProfile.isProgramDisabled(textValue2)) {
                        ShaderLog.info("Program disabled: " + textValue2);
                        textValue = "<disabled>";
                        textValue2 = text + textValue;
                    }

                    String textValue3 = "/shaders/" + textValue2;
                    int j = setupProgram(j1, textValue3 + ".vsh", textValue3 + ".fsh");
                    if (j > 0) {
                        ShaderLog.info("Program loaded: " + textValue2);
                    }

                    shadersmod.client.Shaders.programsID[j1] =
                            shadersmod.client.Shaders.programsRef[j1] = j;
                    shadersmod.client.Shaders.programsDrawBufSettings[j1] =
                            j != 0 ? shadersmod.client.Shaders.newDrawBufSetting : null;
                    shadersmod.client.Shaders.programsColorAtmSettings[j1] =
                            j != 0 ? shadersmod.client.Shaders.newColorAtmSetting : null;
                    shadersmod.client.Shaders.programsCompositeMipmapSetting[j1] =
                            j != 0 ? shadersmod.client.Shaders.newCompositeMipmapSetting : 0;
                }

                j1++;
                if (aint != null) {
                    break;
                }
            }

            j1 = GL11.glGetInteger(34852);
            new HashMap();
            int k1 = 0;

            while (k1 < 33) {
                label269:
                {
                    Arrays.fill(shadersmod.client.Shaders.programsToggleColorTextures[k1], false);
                    if (k1 == 29) {
                        shadersmod.client.Shaders.programsDrawBuffers[k1] = null;
                        if (aint == null) {
                            break label269;
                        }
                    }

                    if (shadersmod.client.Shaders.programsID[k1] == 0) {
                        if (k1 == 30) {
                            shadersmod.client.Shaders.programsDrawBuffers[k1] =
                                    shadersmod.client.Shaders.drawBuffersNone;
                            if (aint == null) {
                                break label269;
                            }
                        }

                        shadersmod.client.Shaders.programsDrawBuffers[k1] =
                                shadersmod.client.Shaders.drawBuffersColorAtt0;
                        if (aint == null) {
                            break label269;
                        }
                    }

                    String textValue4 = shadersmod.client.Shaders.programsDrawBufSettings[k1];
                    if (textValue4 != null) {
                        IntBuffer intbuffer = shadersmod.client.Shaders.drawBuffersBuffer[k1];
                        int i2 = textValue4.length();
                        if (i2 > shadersmod.client.Shaders.usedDrawBuffers) {
                            shadersmod.client.Shaders.usedDrawBuffers = i2;
                        }

                        if (i2 > j1) {
                            i2 = j1;
                        }

                        shadersmod.client.Shaders.programsDrawBuffers[k1] = intbuffer;
                        ((Buffer) intbuffer).limit(i2);
                        int k = 0;

                        while (k < i2) {
                            int l;
                            l = 0;
                            label228:
                            if (textValue4.length() > k) {
                                int i1 = textValue4.charAt(k) - '0';
                                if (k1 != 30) {
                                    if (i1 < 0 || i1 > 7) {
                                        break label228;
                                    }

                                    shadersmod.client.Shaders.programsToggleColorTextures[k1][i1] =
                                            true;
                                    l = i1 + 36064;
                                    if (i1 > shadersmod.client.Shaders.usedColorAttachs) {
                                        shadersmod.client.Shaders.usedColorAttachs = i1;
                                    }

                                    if (i1 <= shadersmod.client.Shaders.usedColorBuffers) {
                                        break label228;
                                    }

                                    shadersmod.client.Shaders.usedColorBuffers = i1;
                                    if (aint == null) {
                                        break label228;
                                    }
                                }

                                if (i1 >= 0 && i1 <= 1) {
                                    l = i1 + 36064;
                                    if (i1 > shadersmod.client.Shaders.usedShadowColorBuffers) {
                                        shadersmod.client.Shaders.usedShadowColorBuffers = i1;
                                    }
                                }
                            }

                            intbuffer.put(k, l);
                            k++;
                            if (aint != null) {
                                break;
                            }
                        }

                        if (aint == null) {
                            break label269;
                        }
                    }

                    if (k1 != 30 && k1 != 31 && k1 != 32) {
                        shadersmod.client.Shaders.programsDrawBuffers[k1] =
                                shadersmod.client.Shaders.dfbDrawBuffers;
                        shadersmod.client.Shaders.usedDrawBuffers =
                                shadersmod.client.Shaders.usedColorBuffers;
                        Arrays.fill(
                                shadersmod.client.Shaders.programsToggleColorTextures[k1],
                                0,
                                shadersmod.client.Shaders.usedColorBuffers,
                                true);
                        if (aint == null) {
                            break label269;
                        }
                    }

                    shadersmod.client.Shaders.programsDrawBuffers[k1] =
                            shadersmod.client.Shaders.sfbDrawBuffers;
                }

                k1++;
                if (aint != null) {
                    break;
                }
            }

            shadersmod.client.Shaders.usedColorAttachs = shadersmod.client.Shaders.usedColorBuffers;
            shadersmod.client.Shaders.shadowPassInterval =
                    shadersmod.client.Shaders.usedShadowDepthBuffers > 0 ? 1 : 0;
            shadersmod.client.Shaders.shouldSkipDefaultShadow =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (shadersmod.client.Shaders.usedShadowDepthBuffers > 0 ? 1 : 0));
            ShaderLog.info("usedColorBuffers: " + shadersmod.client.Shaders.usedColorBuffers);
            ShaderLog.info("usedDepthBuffers: " + shadersmod.client.Shaders.usedDepthBuffers);
            ShaderLog.info(
                    "usedShadowColorBuffers: " + shadersmod.client.Shaders.usedShadowColorBuffers);
            ShaderLog.info(
                    "usedShadowDepthBuffers: " + shadersmod.client.Shaders.usedShadowDepthBuffers);
            ShaderLog.info("usedColorAttachs: " + shadersmod.client.Shaders.usedColorAttachs);
            ShaderLog.info("usedDrawBuffers: " + shadersmod.client.Shaders.usedDrawBuffers);
            ((Buffer) shadersmod.client.Shaders.dfbDrawBuffers)
                    .position(0)
                    .limit(shadersmod.client.Shaders.usedDrawBuffers);
            ((Buffer) shadersmod.client.Shaders.dfbColorTextures)
                    .position(0)
                    .limit(shadersmod.client.Shaders.usedColorBuffers * 2);
            k1 = 0;

            while (k1 < shadersmod.client.Shaders.usedDrawBuffers) {
                shadersmod.client.Shaders.dfbDrawBuffers.put(k1, 36064 + k1);
                k1++;
                if (aint != null) {
                    break;
                }
            }

            if (shadersmod.client.Shaders.usedDrawBuffers > j1) {
                printChatAndLogError(
                        "[Shaders] Error: Not enough draw buffers, needed: "
                                + shadersmod.client.Shaders.usedDrawBuffers
                                + ", available: "
                                + j1);
            }

            ((Buffer) shadersmod.client.Shaders.sfbDrawBuffers)
                    .position(0)
                    .limit(shadersmod.client.Shaders.usedShadowColorBuffers);
            k1 = 0;

            while (k1 < shadersmod.client.Shaders.usedShadowColorBuffers) {
                shadersmod.client.Shaders.sfbDrawBuffers.put(k1, 36064 + k1);
                k1++;
                if (aint != null) {
                    break;
                }
            }

            k1 = 0;

            while (k1 < 33) {
                int l1 = k1;

                while (shadersmod.client.Shaders.programsID[l1] == 0
                        && shadersmod.client.Shaders.programBackups[l1] != l1) {
                    l1 = shadersmod.client.Shaders.programBackups[l1];
                    if (aint != null) {
                        break;
                    }
                }

                if (l1 != k1 && k1 != 30) {
                    shadersmod.client.Shaders.programsID[k1] =
                            shadersmod.client.Shaders.programsID[l1];
                    shadersmod.client.Shaders.programsDrawBufSettings[k1] =
                            shadersmod.client.Shaders.programsDrawBufSettings[l1];
                    shadersmod.client.Shaders.programsDrawBuffers[k1] =
                            shadersmod.client.Shaders.programsDrawBuffers[l1];
                }

                k1++;
                if (aint != null) {
                    break;
                }
            }

            resize();
            resizeShadow();
            if (shadersmod.client.Shaders.noiseTextureEnabled) {
                setupNoiseTexture();
            }

            if (shadersmod.client.Shaders.defaultTexture == null) {
                shadersmod.client.Shaders.defaultTexture = ShadersTex.createDefaultTexture();
            }

            GlStateManager.pushMatrix();
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            preCelestialRotate();
            postCelestialRotate();
            GlStateManager.popMatrix();
            shadersmod.client.Shaders.isShaderPackInitialized = true;
            loadEntityDataMap();
            resetDisplayList();
            if (b0 == 0) {}

            checkGLError("Shaders.init");
        }
    }

    public static void setProgramUniform1f(String text, float p171_1) {
        int i = shadersmod.client.Shaders.programsID[shadersmod.client.Shaders.activeProgram];
        if (i != 0) {
            int j = org.lwjgl.opengl.ARBShaderObjects.glGetUniformLocationARB(i, text);
            org.lwjgl.opengl.ARBShaderObjects.glUniform1fARB(j, p171_1);
            shadersmod.client.Shaders.checkGLError(
                    shadersmod.client.Shaders.programNames[shadersmod.client.Shaders.activeProgram],
                    text);
        }
    }

    public static void drawSkyFogRing() {
        net.minecraft.client.renderer.WorldRenderer worldRenderer =
                net.minecraft.client.renderer.Tessellator.getInstance().getWorldRenderer();
        float fx = shadersmod.client.Shaders.mc.gameSettings.renderDistanceChunks * 16;
        double d0 = fx * 0.9238;
        double d1 = fx * 0.3826;
        double d2 = -d1;
        double d3 = -d0;
        double d4 = 16.0;
        double d5 = -shadersmod.client.Shaders.cameraPositionY;
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(d2, d5, d3);
        worldRenderer.addVertex(d2, d4, d3);
        worldRenderer.addVertex(d3, d4, d2);
        worldRenderer.addVertex(d3, d5, d2);
        worldRenderer.addVertex(d3, d5, d2);
        worldRenderer.addVertex(d3, d4, d2);
        worldRenderer.addVertex(d3, d4, d1);
        worldRenderer.addVertex(d3, d5, d1);
        worldRenderer.addVertex(d3, d5, d1);
        worldRenderer.addVertex(d3, d4, d1);
        worldRenderer.addVertex(d2, d4, d1);
        worldRenderer.addVertex(d2, d5, d1);
        worldRenderer.addVertex(d2, d5, d1);
        worldRenderer.addVertex(d2, d4, d1);
        worldRenderer.addVertex(d1, d4, d0);
        worldRenderer.addVertex(d1, d5, d0);
        worldRenderer.addVertex(d1, d5, d0);
        worldRenderer.addVertex(d1, d4, d0);
        worldRenderer.addVertex(d0, d4, d1);
        worldRenderer.addVertex(d0, d5, d1);
        worldRenderer.addVertex(d0, d5, d1);
        worldRenderer.addVertex(d0, d4, d1);
        worldRenderer.addVertex(d0, d4, d2);
        worldRenderer.addVertex(d0, d5, d2);
        worldRenderer.addVertex(d0, d5, d2);
        worldRenderer.addVertex(d0, d4, d2);
        worldRenderer.addVertex(d1, d4, d3);
        worldRenderer.addVertex(d1, d5, d3);
        worldRenderer.addVertex(d1, d5, d3);
        worldRenderer.addVertex(d1, d4, d3);
        worldRenderer.addVertex(d2, d4, d3);
        worldRenderer.addVertex(d2, d5, d3);
        net.minecraft.client.renderer.Tessellator.getInstance().draw();
    }

    public static int setEntityData1(int p171_0) {
        shadersmod.client.Shaders.entityData[shadersmod.client.Shaders.entityDataIndex * 2] =
                shadersmod.client.Shaders.entityData[shadersmod.client.Shaders.entityDataIndex * 2]
                                & 65535
                        | p171_0 << 16;
        return p171_0;
    }

    public static void popEntity() {
        shadersmod.client.Shaders.entityData[shadersmod.client.Shaders.entityDataIndex * 2] = 0;
        shadersmod.client.Shaders.entityData[shadersmod.client.Shaders.entityDataIndex * 2 + 1] = 0;
        shadersmod.client.Shaders.entityDataIndex--;
    }

    public static String translate(String text, String textValue) {
        java.lang.String textValue2 =
                (java.lang.String) shadersmod.client.Shaders.shaderPackResources.get(text);

        try {
            if (textValue2 == null) {
                return textValue;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }

        return textValue2;
    }

    static {
        shadersmod.client.Shaders.packedTextFragments = new String[26];
        shadersmod.client.Shaders.packedTextFragments[0] = new String("");
        shadersmod.client.Shaders.packedTextFragments[1] = new String("x");
        shadersmod.client.Shaders.packedTextFragments[2] = new String("x");
        shadersmod.client.Shaders.packedTextFragments[3] = new String(" ");
        shadersmod.client.Shaders.packedTextFragments[4] = new String("/");
        shadersmod.client.Shaders.packedTextFragments[5] = new String("/");
        shadersmod.client.Shaders.packedTextFragments[6] = new String(".");
        shadersmod.client.Shaders.packedTextFragments[7] = new String(".");
        shadersmod.client.Shaders.packedTextFragments[8] = new String(" ");
        shadersmod.client.Shaders.packedTextFragments[9] = new String("]");
        shadersmod.client.Shaders.packedTextFragments[10] = new String("Y");
        shadersmod.client.Shaders.packedTextFragments[11] = new String("N");
        shadersmod.client.Shaders.packedTextFragments[12] = new String("");
        shadersmod.client.Shaders.packedTextFragments[13] = new String("/");
        shadersmod.client.Shaders.packedTextFragments[14] = new String("");
        shadersmod.client.Shaders.packedTextFragments[15] = new String("\"");
        shadersmod.client.Shaders.packedTextFragments[16] = new String("!");
        shadersmod.client.Shaders.packedTextFragments[17] = new String("x");
        shadersmod.client.Shaders.packedTextFragments[18] = new String("!");
        shadersmod.client.Shaders.packedTextFragments[19] = new String("\n");
        shadersmod.client.Shaders.packedTextFragments[20] = new String("\n");
        shadersmod.client.Shaders.packedTextFragments[21] = new String(")");
        shadersmod.client.Shaders.packedTextFragments[22] = new String(")");
        shadersmod.client.Shaders.packedTextFragments[23] = new String("/");
        shadersmod.client.Shaders.packedTextFragments[25] = new String("");
        shadersmod.client.Shaders.mc = MinecraftClient.getMinecraft();
        shadersmod.client.Shaders.isInitializedOnce = false;
        shadersmod.client.Shaders.isShaderPackInitialized = false;
        shadersmod.client.Shaders.hasGlGenMipmap = false;
        shadersmod.client.Shaders.hasForge = false;
        shadersmod.client.Shaders.numberResetDisplayList = 0;
        shadersmod.client.Shaders.needResetModels = false;
        shadersmod.client.Shaders.renderDisplayWidth = 0;
        shadersmod.client.Shaders.renderDisplayHeight = 0;
        shadersmod.client.Shaders.renderWidth = 0;
        shadersmod.client.Shaders.renderHeight = 0;
        shadersmod.client.Shaders.isRenderingWorld = false;
        shadersmod.client.Shaders.isRenderingSky = false;
        shadersmod.client.Shaders.isCompositeRendered = false;
        shadersmod.client.Shaders.isRenderingDfb = false;
        shadersmod.client.Shaders.isShadowPass = false;
        shadersmod.client.Shaders.renderItemKeepDepthMask = false;
        shadersmod.client.Shaders.sunPosition = new float[4];
        shadersmod.client.Shaders.moonPosition = new float[4];
        shadersmod.client.Shaders.shadowLightPosition = new float[4];
        shadersmod.client.Shaders.upPosition = new float[4];
        shadersmod.client.Shaders.shadowLightPositionVector = new float[4];
        shadersmod.client.Shaders.upPosModelView = new float[] {0.0F, 100.0F, 0.0F, 0.0F};
        shadersmod.client.Shaders.sunPosModelView = new float[] {0.0F, 100.0F, 0.0F, 0.0F};
        shadersmod.client.Shaders.moonPosModelView = new float[] {0.0F, -100.0F, 0.0F, 0.0F};
        shadersmod.client.Shaders.tempMat = new float[16];
        shadersmod.client.Shaders.worldTime = 0L;
        shadersmod.client.Shaders.lastWorldTime = 0L;
        shadersmod.client.Shaders.diffWorldTime = 0L;
        shadersmod.client.Shaders.celestialAngle = 0.0F;
        shadersmod.client.Shaders.sunAngle = 0.0F;
        shadersmod.client.Shaders.shadowAngle = 0.0F;
        shadersmod.client.Shaders.moonPhase = 0;
        shadersmod.client.Shaders.systemTime = 0L;
        shadersmod.client.Shaders.lastSystemTime = 0L;
        shadersmod.client.Shaders.diffSystemTime = 0L;
        shadersmod.client.Shaders.frameCounter = 0;
        shadersmod.client.Shaders.frameTime = 0.0F;
        shadersmod.client.Shaders.systemTimeInt32 = 0;
        shadersmod.client.Shaders.rainStrength = 0.0F;
        shadersmod.client.Shaders.wetness = 0.0F;
        shadersmod.client.Shaders.wetnessHalfLife = 600.0F;
        shadersmod.client.Shaders.drynessHalfLife = 200.0F;
        shadersmod.client.Shaders.eyeBrightnessHalflife = 10.0F;
        shadersmod.client.Shaders.usewetness = false;
        shadersmod.client.Shaders.isEyeInWater = 0;
        shadersmod.client.Shaders.eyeBrightness = 0;
        shadersmod.client.Shaders.eyeBrightnessFadeX = 0.0F;
        shadersmod.client.Shaders.eyeBrightnessFadeY = 0.0F;
        shadersmod.client.Shaders.eyePosY = 0.0F;
        shadersmod.client.Shaders.centerDepth = 0.0F;
        shadersmod.client.Shaders.centerDepthSmooth = 0.0F;
        shadersmod.client.Shaders.centerDepthSmoothHalflife = 1.0F;
        shadersmod.client.Shaders.centerDepthSmoothEnabled = false;
        shadersmod.client.Shaders.superSamplingLevel = 1;
        shadersmod.client.Shaders.updateChunksErrorRecorded = false;
        shadersmod.client.Shaders.lightmapEnabled = false;
        shadersmod.client.Shaders.fogEnabled = true;
        shadersmod.client.Shaders.entityAttrib = 10;
        shadersmod.client.Shaders.midTexCoordAttrib = 11;
        shadersmod.client.Shaders.tangentAttrib = 12;
        shadersmod.client.Shaders.useEntityAttrib = false;
        shadersmod.client.Shaders.useMidTexCoordAttrib = false;
        shadersmod.client.Shaders.useMultiTexCoord3Attrib = false;
        shadersmod.client.Shaders.useTangentAttrib = false;
        shadersmod.client.Shaders.progUseEntityAttrib = false;
        shadersmod.client.Shaders.progUseMidTexCoordAttrib = false;
        shadersmod.client.Shaders.progUseTangentAttrib = false;
        shadersmod.client.Shaders.atlasSizeX = 0;
        shadersmod.client.Shaders.atlasSizeY = 0;
        shadersmod.client.Shaders.uniformEntityColor = new ShaderUniformFloat4("entityColor");
        shadersmod.client.Shaders.uniformEntityId = new ShaderUniformInt("entityId");
        shadersmod.client.Shaders.uniformBlockEntityId = new ShaderUniformInt("blockEntityId");
        shadersmod.client.Shaders.shadowPassInterval = 0;
        shadersmod.client.Shaders.needResizeShadow = false;
        shadersmod.client.Shaders.shadowMapWidth = 1024;
        shadersmod.client.Shaders.shadowMapHeight = 1024;
        shadersmod.client.Shaders.spShadowMapWidth = 1024;
        shadersmod.client.Shaders.spShadowMapHeight = 1024;
        shadersmod.client.Shaders.shadowMapFOV = 90.0F;
        shadersmod.client.Shaders.shadowMapHalfPlane = 160.0F;
        shadersmod.client.Shaders.shadowMapIsOrtho = true;
        shadersmod.client.Shaders.shadowPassCounter = 0;
        shadersmod.client.Shaders.shouldSkipDefaultShadow = false;
        shadersmod.client.Shaders.waterShadowEnabled = false;
        shadersmod.client.Shaders.usedColorBuffers = 0;
        shadersmod.client.Shaders.usedDepthBuffers = 0;
        shadersmod.client.Shaders.usedShadowColorBuffers = 0;
        shadersmod.client.Shaders.usedShadowDepthBuffers = 0;
        shadersmod.client.Shaders.usedColorAttachs = 0;
        shadersmod.client.Shaders.usedDrawBuffers = 0;
        shadersmod.client.Shaders.dfb = 0;
        shadersmod.client.Shaders.sfb = 0;
        shadersmod.client.Shaders.gbuffersFormat = new int[8];
        shadersmod.client.Shaders.activeProgram = 0;
        shadersmod.client.Shaders.programNames =
                new String[] {
                    packedTextFragments[25],
                    "gbuffers_basic",
                    "gbuffers_textured",
                    "gbuffers_textured_lit",
                    "gbuffers_skybasic",
                    "gbuffers_skytextured",
                    "gbuffers_clouds",
                    "gbuffers_terrain",
                    "gbuffers_terrain_solid",
                    "gbuffers_terrain_cutout_mip",
                    "gbuffers_terrain_cutout",
                    "gbuffers_damagedblock",
                    "gbuffers_water",
                    "gbuffers_block",
                    "gbuffers_beaconbeam",
                    "gbuffers_item",
                    "gbuffers_entities",
                    "gbuffers_armor_glint",
                    "gbuffers_spidereyes",
                    "gbuffers_hand",
                    "gbuffers_weather",
                    "composite",
                    "composite1",
                    "composite2",
                    "composite3",
                    "composite4",
                    "composite5",
                    "composite6",
                    "composite7",
                    "final",
                    "shadow",
                    "shadow_solid",
                    "shadow_cutout"
                };
        shadersmod.client.Shaders.programBackups =
                new int[] {
                    0, 0, 1, 2, 1, 2, 2, 3, 7, 7, 7, 7, 7, 7, 2, 3, 3, 2, 2, 3, 3, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 30, 30
                };
        shadersmod.client.Shaders.programsID = new int[33];
        shadersmod.client.Shaders.programsRef = new int[33];
        shadersmod.client.Shaders.programIDCopyDepth = 0;
        shadersmod.client.Shaders.programsDrawBufSettings = new String[33];
        shadersmod.client.Shaders.newDrawBufSetting = null;
        shadersmod.client.Shaders.programsDrawBuffers = new IntBuffer[33];
        shadersmod.client.Shaders.activeDrawBuffers = null;
        shadersmod.client.Shaders.programsColorAtmSettings = new String[33];
        shadersmod.client.Shaders.newColorAtmSetting = null;
        shadersmod.client.Shaders.activeColorAtmSettings = null;
        shadersmod.client.Shaders.programsCompositeMipmapSetting = new int[33];
        shadersmod.client.Shaders.newCompositeMipmapSetting = 0;
        shadersmod.client.Shaders.activeCompositeMipmapSetting = 0;
        shadersmod.client.Shaders.loadedShaders = null;
        shadersmod.client.Shaders.shadersConfig = null;
        shadersmod.client.Shaders.defaultTexture = null;
        shadersmod.client.Shaders.normalMapEnabled = false;
        shadersmod.client.Shaders.shadowHardwareFilteringEnabled = new boolean[2];
        shadersmod.client.Shaders.shadowMipmapEnabled = new boolean[2];
        shadersmod.client.Shaders.shadowFilterNearest = new boolean[2];
        shadersmod.client.Shaders.shadowColorMipmapEnabled = new boolean[8];
        shadersmod.client.Shaders.shadowColorFilterNearest = new boolean[8];
        shadersmod.client.Shaders.configTweakBlockDamage = true;
        shadersmod.client.Shaders.configCloudShadow = true;
        shadersmod.client.Shaders.configHandDepthMul = 0.125F;
        shadersmod.client.Shaders.configRenderResMul = 1.0F;
        shadersmod.client.Shaders.configShadowResMul = 1.0F;
        shadersmod.client.Shaders.configTexMinFilB = 0;
        shadersmod.client.Shaders.configTexMinFilN = 0;
        shadersmod.client.Shaders.configTexMinFilS = 0;
        shadersmod.client.Shaders.configTexMagFilB = 0;
        shadersmod.client.Shaders.configTexMagFilN = 0;
        shadersmod.client.Shaders.configTexMagFilS = 0;
        shadersmod.client.Shaders.configShadowClipFrustrum = true;
        shadersmod.client.Shaders.configNormalMap = true;
        shadersmod.client.Shaders.configSpecularMap = true;
        shadersmod.client.Shaders.configOldHandLight =
                new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 2);
        shadersmod.client.Shaders.configAntialiasingLevel = 0;
        shadersmod.client.Shaders.texMinFilDesc =
                new String[] {"Nearest", "Nearest-Nearest", "Nearest-Linear"};
        shadersmod.client.Shaders.texMagFilDesc = new String[] {"Nearest", "Linear"};
        shadersmod.client.Shaders.texMinFilValue = new int[] {9728, 9984, 9986};
        shadersmod.client.Shaders.texMagFilValue = new int[] {9728, 9729};
        shadersmod.client.Shaders.shaderPack = null;
        shadersmod.client.Shaders.shaderPackLoaded = false;
        shadersmod.client.Shaders.packNameNone = "OFF";
        shadersmod.client.Shaders.packNameDefault = "(internal)";
        shadersmod.client.Shaders.shaderpacksdirname = "shaderpacks";
        shadersmod.client.Shaders.optionsfilename = "optionsshaders.txt";
        shadersmod.client.Shaders.shadersdir =
                new File(MinecraftClient.getMinecraft().gameDirectory, "shaders");
        shadersmod.client.Shaders.shaderpacksdir =
                new File(
                        MinecraftClient.getMinecraft().gameDirectory,
                        shadersmod.client.Shaders.shaderpacksdirname);
        shadersmod.client.Shaders.configFile =
                new File(
                        MinecraftClient.getMinecraft().gameDirectory,
                        shadersmod.client.Shaders.optionsfilename);
        shadersmod.client.Shaders.shaderPackOptions = null;
        shadersmod.client.Shaders.shaderPackProfiles = null;
        shadersmod.client.Shaders.shaderPackGuiScreens = null;
        shadersmod.client.Shaders.shaderPackClouds =
                new PropertyDefaultFastFancyOff("clouds", "Clouds", 0);
        shadersmod.client.Shaders.shaderPackOldLighting =
                new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
        shadersmod.client.Shaders.shaderPackDynamicHandLight =
                new PropertyDefaultTrueFalse("dynamicHandLight", "Dynamic Hand Light", 0);
        shadersmod.client.Shaders.shaderPackResources = new HashMap<>();
        shadersmod.client.Shaders.currentWorld = null;
        shadersmod.client.Shaders.shaderPackDimensions = new ArrayList<>();
        shadersmod.client.Shaders.saveFinalShaders =
                System.getProperty("shaders.debug.save", "false").equals("true");
        shadersmod.client.Shaders.blockLightLevel05 = 0.5F;
        shadersmod.client.Shaders.blockLightLevel06 = 0.6F;
        shadersmod.client.Shaders.blockLightLevel08 = 0.8F;
        MP = 0.8F;
        shadersmod.client.Shaders.aoLevel = 1.0F - MP;
        shadersmod.client.Shaders.sunPathRotation = 0.0F;
        shadersmod.client.Shaders.shadowAngleInterval = 0.0F;
        shadersmod.client.Shaders.fogMode = 0;
        shadersmod.client.Shaders.shadowIntervalSize = 2.0F;
        shadersmod.client.Shaders.terrainIconSize = 16;
        shadersmod.client.Shaders.terrainTextureSize = new int[2];
        shadersmod.client.Shaders.noiseTextureEnabled = false;
        shadersmod.client.Shaders.noiseTextureResolution = 256;
        shadersmod.client.Shaders.dfbColorTexturesA = new int[16];
        shadersmod.client.Shaders.colorTexturesToggle = new int[8];
        shadersmod.client.Shaders.colorTextureTextureImageUnit =
                new int[] {0, 1, 2, 3, 7, 8, 9, 10};
        shadersmod.client.Shaders.programsToggleColorTextures = new boolean[33][8];
        shadersmod.client.Shaders.bigBuffer =
                (ByteBuffer) ((Buffer) BufferUtils.createByteBuffer(2196)).limit(0);
        shadersmod.client.Shaders.faProjection = new float[16];
        shadersmod.client.Shaders.faProjectionInverse = new float[16];
        shadersmod.client.Shaders.faModelView = new float[16];
        shadersmod.client.Shaders.faModelViewInverse = new float[16];
        shadersmod.client.Shaders.faShadowProjection = new float[16];
        shadersmod.client.Shaders.faShadowProjectionInverse = new float[16];
        shadersmod.client.Shaders.faShadowModelView = new float[16];
        shadersmod.client.Shaders.faShadowModelViewInverse = new float[16];
        shadersmod.client.Shaders.projection = nextFloatBuffer(16);
        shadersmod.client.Shaders.projectionInverse = nextFloatBuffer(16);
        shadersmod.client.Shaders.modelView = nextFloatBuffer(16);
        shadersmod.client.Shaders.modelViewInverse = nextFloatBuffer(16);
        shadersmod.client.Shaders.shadowProjection = nextFloatBuffer(16);
        shadersmod.client.Shaders.shadowProjectionInverse = nextFloatBuffer(16);
        shadersmod.client.Shaders.shadowModelView = nextFloatBuffer(16);
        shadersmod.client.Shaders.shadowModelViewInverse = nextFloatBuffer(16);
        shadersmod.client.Shaders.previousProjection = nextFloatBuffer(16);
        shadersmod.client.Shaders.previousModelView = nextFloatBuffer(16);
        shadersmod.client.Shaders.tempMatrixDirectBuffer = nextFloatBuffer(16);
        shadersmod.client.Shaders.tempDirectFloatBuffer = nextFloatBuffer(16);
        shadersmod.client.Shaders.dfbColorTextures = nextIntBuffer(16);
        shadersmod.client.Shaders.dfbDepthTextures = nextIntBuffer(3);
        shadersmod.client.Shaders.sfbColorTextures = nextIntBuffer(8);
        shadersmod.client.Shaders.sfbDepthTextures = nextIntBuffer(2);
        shadersmod.client.Shaders.dfbDrawBuffers = nextIntBuffer(8);
        shadersmod.client.Shaders.sfbDrawBuffers = nextIntBuffer(8);
        shadersmod.client.Shaders.drawBuffersNone = nextIntBuffer(8);
        shadersmod.client.Shaders.drawBuffersAll = nextIntBuffer(8);
        shadersmod.client.Shaders.drawBuffersClear0 = nextIntBuffer(8);
        shadersmod.client.Shaders.drawBuffersClear1 = nextIntBuffer(8);
        shadersmod.client.Shaders.drawBuffersClearColor = nextIntBuffer(8);
        shadersmod.client.Shaders.drawBuffersColorAtt0 = nextIntBuffer(8);
        shadersmod.client.Shaders.drawBuffersBuffer = nextIntBufferArray(33, 8);
        ((Buffer) shadersmod.client.Shaders.drawBuffersNone).limit(0);
        ((Buffer) shadersmod.client.Shaders.drawBuffersColorAtt0.put(36064)).position(0).limit(1);
        Nh =
                Pattern.compile(
                        "[ \t]*const[ \t]*int[ \t]*(\\w+)Format[ \t]*=[ "
                                + "\t]*([RGBA81632FUI_SNORM]*)[ \t]*;.*");
        Px =
                Pattern.compile(
                        "[ \t]*const[ \t]*bool[ \t]*(\\w+)MipmapEnabled[ \t]*=[ \t]*true[ \t]*;.*");
        Lb =
                new String[] {
                    "R8",
                    "RG8",
                    "RGB8",
                    "RGBA8",
                    "R8_SNORM",
                    "RG8_SNORM",
                    "RGB8_SNORM",
                    "RGBA8_SNORM",
                    "R16",
                    "RG16",
                    "RGB16",
                    "RGBA16",
                    "R16_SNORM",
                    "RG16_SNORM",
                    "RGB16_SNORM",
                    "RGBA16_SNORM",
                    "R32F",
                    "RG32F",
                    "RGB32F",
                    "RGBA32F",
                    "R32I",
                    "RG32I",
                    "RGB32I",
                    "RGBA32I",
                    "R32UI",
                    "RG32UI",
                    "RGB32UI",
                    "RGBA32UI"
                };
        shadersmod.client.Shaders.formatIds =
                new int[] {
                    33321, 33323, 32849, 32856, 36756, 36757, 36758, 36759, 33322, 33324, 32852,
                    32859, 36760, 36761, 36762, 36763, 33326, 33328, 34837, 34836, 33333, 33339,
                    36227, 36226, 33334, 33340, 36209, 36208
                };
        Mg = Pattern.compile("\\s*([\\w:]+)\\s*=\\s*([-]?\\d+)\\s*");
        shadersmod.client.Shaders.entityData = new int[32];
        shadersmod.client.Shaders.entityDataIndex = 0;
    }

    private static void printChatAndLogError(String text) {
        shadersmod.common.ShaderLog.severe(text);
        shadersmod.client.Shaders.mc
                .ingameGUI
                .getChatGUI()
                .printChatMessage(new net.minecraft.util.ChatComponentText(text));
    }

    private static void loadShaderPackProperties() {
        shadersmod.client.Shaders.shaderPackClouds.resetValue();
        shadersmod.client.Shaders.shaderPackDynamicHandLight.resetValue();
        shadersmod.client.Shaders.shaderPackOldLighting.resetValue();
        if (shadersmod.client.Shaders.shaderPack != null) {
            String text = "/shaders/shaders.properties";

            try {
                InputStream inputstream =
                        shadersmod.client.Shaders.shaderPack.getResourceAsStream(text);
                if (inputstream == null) {
                    return;
                }

                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(inputstream);
                inputstream.close();
                shadersmod.client.Shaders.shaderPackClouds.loadFrom(propertiesOrdered);
                shadersmod.client.Shaders.shaderPackDynamicHandLight.loadFrom(propertiesOrdered);
                shadersmod.client.Shaders.shaderPackOldLighting.loadFrom(propertiesOrdered);
                shadersmod.client.Shaders.shaderPackProfiles =
                        ShaderPackParser.parseProfiles(
                                propertiesOrdered, shadersmod.client.Shaders.shaderPackOptions);
                shadersmod.client.Shaders.shaderPackGuiScreens =
                        ShaderPackParser.parseGuiScreens(
                                propertiesOrdered,
                                shadersmod.client.Shaders.shaderPackProfiles,
                                shadersmod.client.Shaders.shaderPackOptions);
            } catch (IOException ioexception) {
                OptiFineConfig.warn("[Shaders] Error reading: " + text);
            }
        }
    }

    public static void setSkyColor(Vec3 vec3) {
        shadersmod.client.Shaders.skyColorR = (float) vec3.x;
        shadersmod.client.Shaders.skyColorG = (float) vec3.y;
        shadersmod.client.Shaders.skyColorB = (float) vec3.z;
        setProgramUniform3f(
                "skyColor",
                shadersmod.client.Shaders.skyColorR,
                shadersmod.client.Shaders.skyColorG,
                shadersmod.client.Shaders.skyColorB);
    }

    public static void setProgramUniform3f(String text, float p171_1, float p171_2, float p171_3) {
        int i = shadersmod.client.Shaders.programsID[shadersmod.client.Shaders.activeProgram];
        if (i != 0) {
            int j = org.lwjgl.opengl.ARBShaderObjects.glGetUniformLocationARB(i, text);
            org.lwjgl.opengl.ARBShaderObjects.glUniform3fARB(j, p171_1, p171_2, p171_3);
            shadersmod.client.Shaders.checkGLError(
                    shadersmod.client.Shaders.programNames[shadersmod.client.Shaders.activeProgram],
                    text);
        }
    }

    public static void ne() {}

    public static void setViewport(int i, int j, int k, int l) {
        int[] aint1 = ShaderOption.lz();
        GlStateManager.colorMask(true, true, true, true);
        int[] aint = aint1;
        if (shadersmod.client.Shaders.isShadowPass) {
            GL11.glViewport(
                    0,
                    0,
                    shadersmod.client.Shaders.shadowMapWidth,
                    shadersmod.client.Shaders.shadowMapHeight);
            if (aint == null) {
                return;
            }
        }

        GL11.glViewport(
                0,
                0,
                shadersmod.client.Shaders.renderWidth,
                shadersmod.client.Shaders.renderHeight);
        EXTFramebufferObject.glBindFramebufferEXT(36160, shadersmod.client.Shaders.dfb);
        shadersmod.client.Shaders.isRenderingDfb = true;
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
        setDrawBuffers(shadersmod.client.Shaders.drawBuffersNone);
        useProgram(2);
        checkGLError("beginRenderPass");
    }

    public static void setFogColor(float p171_0, float p171_1, float p171_2) {
        shadersmod.client.Shaders.fogColorR = p171_0;
        shadersmod.client.Shaders.fogColorG = p171_1;
        shadersmod.client.Shaders.fogColorB = p171_2;
    }

    public static int getEntityData() {
        return shadersmod.client.Shaders.entityData[shadersmod.client.Shaders.entityDataIndex * 2];
    }

    private static boolean printLogInfo(int i, String text) {
        IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
        ARBShaderObjects.glGetObjectParameterARB(i, 35716, intbuffer);
        int j = intbuffer.get();
        if (j > 1) {
            ByteBuffer bytebuffer = BufferUtils.createByteBuffer(j);
            ((Buffer) intbuffer).flip();
            ARBShaderObjects.glGetInfoLogARB(i, intbuffer, bytebuffer);
            byte[] abyte = new byte[j];
            bytebuffer.get(abyte);
            if (abyte[j - 1] == 0) {
                abyte[j - 1] = 10;
            }

            String textValue = new String(abyte);
            ShaderLog.info("Info log: " + text + packedTextFragments[19] + textValue);
            return false;
        } else {
            return true;
        }
    }

    public static void enableFog() {
        shadersmod.client.Shaders.fogEnabled = true;
        setProgramUniform1i("fogMode", shadersmod.client.Shaders.fogMode);
    }

    // region String decoding support

    // endregion

    public static void beginBlockEntities() {
        if (shadersmod.client.Shaders.isRenderingWorld) {
            checkGLError("beginBlockEntities");
            useProgram(13);
        }
    }

    public static void mcProfilerEndSection() {
        shadersmod.client.Shaders.mc.mcProfiler.endSection();
    }

    public static void beginParticles() {
        useProgram(2);
    }

    private static String method_6f00e9922b6f5ce83c88(boolean p171_0) {
        try {
            if (p171_0) {
                return shadersmod.client.Shaders.packedTextFragments[10];
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }

        return shadersmod.client.Shaders.packedTextFragments[11];
    }

    public static void sglEnableFog(int i) {
        GL11.glEnable(i);
        enableFog();
    }

    public static void nextBlockEntity(TileEntity tileEntity) {
        if (shadersmod.client.Shaders.isRenderingWorld) {
            checkGLError("nextBlockEntity");
            useProgram(13);
            setBlockEntityId(tileEntity);
        }
    }

    public static void loadConfig() {
        int[] aint1 = ShaderOption.lz();
        ShaderLog.info("Load ShadersMod configuration.");
        int[] aint = aint1;

        try {
            if (!shadersmod.client.Shaders.shaderpacksdir.exists()) {
                shadersmod.client.Shaders.shaderpacksdir.mkdir();
            }
        } catch (Exception exception2) {
            ShaderLog.severe(
                    "Failed to open the shaderpacks directory: "
                            + shadersmod.client.Shaders.shaderpacksdir);
        }

        shadersmod.client.Shaders.shadersConfig = new PropertiesOrdered();
        shadersmod.client.Shaders.shadersConfig.setProperty(
                EnumShaderOption.SHADER_PACK.getPropertyKey(), packedTextFragments[0]);
        if (shadersmod.client.Shaders.configFile.exists()) {
            try {
                FileReader filereader = new FileReader(shadersmod.client.Shaders.configFile);
                shadersmod.client.Shaders.shadersConfig.load(filereader);
                filereader.close();
            } catch (Exception exception1) {
            }
        }

        if (!shadersmod.client.Shaders.configFile.exists()) {
            try {
                storeConfig();
            } catch (Exception exception) {
            }
        }

        EnumShaderOption[] enumShaderOptionArray = EnumShaderOption.values();
        int i = 0;

        while (i < enumShaderOptionArray.length) {
            EnumShaderOption enumShaderOption = enumShaderOptionArray[i];
            String text = enumShaderOption.getPropertyKey();
            String textValue = enumShaderOption.getValueDefault();
            String property = shadersmod.client.Shaders.shadersConfig.getProperty(text, textValue);
            setEnumShaderOption(enumShaderOption, property);
            i++;
            if (aint != null) {
                break;
            }
        }

        loadShaderPack();
    }

    public static void setClearColor(
            float clearColorRValue, float clearColorGValue, float clearColorBValue, float p171_3) {
        net.minecraft.client.renderer.GlStateManager.clearColor(
                clearColorRValue, clearColorGValue, clearColorBValue, p171_3);
        shadersmod.client.Shaders.clearColorR = clearColorRValue;
        shadersmod.client.Shaders.clearColorG = clearColorGValue;
        shadersmod.client.Shaders.clearColorB = clearColorBValue;
    }

    public static void startup(MinecraftClient minecraftClient) {
        checkShadersModInstalled();
        shadersmod.client.Shaders.mc = minecraftClient;
        shadersmod.client.Shaders.capabilities = GLContext.getCapabilities();
        shadersmod.client.Shaders.glVersionString = GL11.glGetString(7938);
        shadersmod.client.Shaders.glVendorString = GL11.glGetString(7936);
        shadersmod.client.Shaders.glRendererString = GL11.glGetString(7937);
        ShaderLog.info("ShadersMod version: 2.4.12");
        ShaderLog.info("OpenGL Version: " + shadersmod.client.Shaders.glVersionString);
        ShaderLog.info("Vendor:  " + shadersmod.client.Shaders.glVendorString);
        ShaderLog.info("Renderer: " + shadersmod.client.Shaders.glRendererString);
        ShaderLog.info(
                "Capabilities: "
                        + (shadersmod.client.Shaders.capabilities.OpenGL20 ? " 2.0 " : " - ")
                        + (shadersmod.client.Shaders.capabilities.OpenGL21 ? " 2.1 " : " - ")
                        + (shadersmod.client.Shaders.capabilities.OpenGL30 ? " 3.0 " : " - ")
                        + (shadersmod.client.Shaders.capabilities.OpenGL32 ? " 3.2 " : " - ")
                        + (shadersmod.client.Shaders.capabilities.OpenGL40 ? " 4.0 " : " - "));
        ShaderLog.info("GL_MAX_DRAW_BUFFERS: " + GL11.glGetInteger(34852));
        ShaderLog.info("GL_MAX_COLOR_ATTACHMENTS_EXT: " + GL11.glGetInteger(36063));
        ShaderLog.info("GL_MAX_TEXTURE_IMAGE_UNITS: " + GL11.glGetInteger(34930));
        shadersmod.client.Shaders.hasGlGenMipmap = shadersmod.client.Shaders.capabilities.OpenGL30;
        loadConfig();
    }

    public static void endUpdateChunks() {
        checkGLError("endUpdateChunks1");
        checkFramebufferStatus("endUpdateChunks1");
        if (!shadersmod.client.Shaders.isShadowPass) {
            useProgram(7);
        }

        checkGLError("endUpdateChunks2");
        checkFramebufferStatus("endUpdateChunks2");
    }

    public static void beginSpiderEyes() {
        if (shadersmod.client.Shaders.isRenderingWorld
                && shadersmod.client.Shaders.programsID[18]
                        != shadersmod.client.Shaders.programsID[0]) {
            shadersmod.client.Shaders.useProgram(18);
            net.minecraft.client.renderer.GlStateManager.enableAlpha();
            net.minecraft.client.renderer.GlStateManager.alphaFunc(516, 0.0F);
            net.minecraft.client.renderer.GlStateManager.blendFunc(770, 771);
        }
    }

    public static void beginProjectRedHalo() {
        if (shadersmod.client.Shaders.isRenderingWorld) {
            useProgram(1);
        }
    }

    public static void enableLightmap() {
        shadersmod.client.Shaders.lightmapEnabled = true;
        if (shadersmod.client.Shaders.activeProgram == 2) {
            useProgram(3);
        }
    }

    public static void sglDisableFog(int i) {
        GL11.glDisable(i);
        disableFog();
    }

    public static void pushEntity(int p171_0, int p171_1) {
        shadersmod.client.Shaders.entityDataIndex++;
        shadersmod.client.Shaders.entityData[shadersmod.client.Shaders.entityDataIndex * 2] =
                p171_0 & 65535 | p171_1 << 16;
        shadersmod.client.Shaders.entityData[shadersmod.client.Shaders.entityDataIndex * 2 + 1] = 0;
    }

    public static void preCelestialRotate() {
        kl();
        GL11.glRotatef(shadersmod.client.Shaders.sunPathRotation * 1.0F, 0.0F, 0.0F, 1.0F);
        checkGLError("preCelestialRotate");
    }

    public static void resetDisplayList() {
        shadersmod.client.Shaders.numberResetDisplayList++;
        shadersmod.client.Shaders.needResetModels = true;
        ShaderLog.info("Reset world renderers");
        shadersmod.client.Shaders.mc.renderGlobal.loadRenderers();
    }

    private static void setupFrameBuffer() {
        int[] aint = ShaderOption.lz();
        if (shadersmod.client.Shaders.dfb != 0) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(shadersmod.client.Shaders.dfb);
            GlStateManager.deleteTextures(shadersmod.client.Shaders.dfbDepthTextures);
            GlStateManager.deleteTextures(shadersmod.client.Shaders.dfbColorTextures);
        }

        shadersmod.client.Shaders.dfb = EXTFramebufferObject.glGenFramebuffersEXT();
        GL11.glGenTextures(
                (IntBuffer)
                        ((Buffer) shadersmod.client.Shaders.dfbDepthTextures)
                                .clear()
                                .limit(shadersmod.client.Shaders.usedDepthBuffers));
        GL11.glGenTextures(
                (IntBuffer)
                        ((Buffer) shadersmod.client.Shaders.dfbColorTextures).clear().limit(16));
        ((Buffer) shadersmod.client.Shaders.dfbDepthTextures).position(0);
        ((Buffer) shadersmod.client.Shaders.dfbColorTextures).position(0);
        ((Buffer)
                        shadersmod.client.Shaders.dfbColorTextures.get(
                                shadersmod.client.Shaders.dfbColorTexturesA))
                .position(0);
        EXTFramebufferObject.glBindFramebufferEXT(36160, shadersmod.client.Shaders.dfb);
        GL20.glDrawBuffers(0);
        GL11.glReadBuffer(0);
        int i = 0;

        while (i < shadersmod.client.Shaders.usedDepthBuffers) {
            GlStateManager.bindTexture(shadersmod.client.Shaders.dfbDepthTextures.get(i));
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
            GL11.glTexParameteri(3553, 10241, 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
            GL11.glTexParameteri(3553, 34891, 6409);
            GL11.glTexImage2D(
                    3553,
                    0,
                    6402,
                    shadersmod.client.Shaders.renderWidth,
                    shadersmod.client.Shaders.renderHeight,
                    0,
                    6402,
                    5126,
                    (FloatBuffer) null);
            i++;
            if (aint != null) {
                break;
            }
        }

        EXTFramebufferObject.glFramebufferTexture2DEXT(
                36160, 36096, 3553, shadersmod.client.Shaders.dfbDepthTextures.get(0), 0);
        GL20.glDrawBuffers(shadersmod.client.Shaders.dfbDrawBuffers);
        GL11.glReadBuffer(0);
        checkGLError("FT d");
        i = 0;

        while (i < shadersmod.client.Shaders.usedColorBuffers) {
            GlStateManager.bindTexture(shadersmod.client.Shaders.dfbColorTexturesA[i]);
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexImage2D(
                    3553,
                    0,
                    shadersmod.client.Shaders.gbuffersFormat[i],
                    shadersmod.client.Shaders.renderWidth,
                    shadersmod.client.Shaders.renderHeight,
                    0,
                    32993,
                    33639,
                    (ByteBuffer) null);
            EXTFramebufferObject.glFramebufferTexture2DEXT(
                    36160, 36064 + i, 3553, shadersmod.client.Shaders.dfbColorTexturesA[i], 0);
            checkGLError("FT c");
            i++;
            if (aint != null) {
                break;
            }
        }

        i = 0;

        while (i < shadersmod.client.Shaders.usedColorBuffers) {
            GlStateManager.bindTexture(shadersmod.client.Shaders.dfbColorTexturesA[8 + i]);
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexImage2D(
                    3553,
                    0,
                    shadersmod.client.Shaders.gbuffersFormat[i],
                    shadersmod.client.Shaders.renderWidth,
                    shadersmod.client.Shaders.renderHeight,
                    0,
                    32993,
                    33639,
                    (ByteBuffer) null);
            checkGLError("FT ca");
            i++;
            if (aint != null) {
                break;
            }
        }

        i = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
        if (i == 36058) {
            printChatAndLogError("[Shaders] Error: Failed framebuffer incomplete formats");
            int j = 0;

            while (j < shadersmod.client.Shaders.usedColorBuffers) {
                GlStateManager.bindTexture(shadersmod.client.Shaders.dfbColorTextures.get(j));
                GL11.glTexImage2D(
                        3553,
                        0,
                        6408,
                        shadersmod.client.Shaders.renderWidth,
                        shadersmod.client.Shaders.renderHeight,
                        0,
                        32993,
                        33639,
                        (ByteBuffer) null);
                EXTFramebufferObject.glFramebufferTexture2DEXT(
                        36160,
                        36064 + j,
                        3553,
                        shadersmod.client.Shaders.dfbColorTextures.get(j),
                        0);
                checkGLError("FT c");
                j++;
                if (aint != null) {
                    break;
                }
            }

            i = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
            if (i == 36053) {
                ShaderLog.info("complete");
            }
        }

        GlStateManager.bindTexture(0);
        if (i != 36053) {
            printChatAndLogError(
                    "[Shaders] Error: Failed creating framebuffer! (Status "
                            + i
                            + packedTextFragments[21]);
            if (aint == null) {
                return;
            }
        }

        ShaderLog.info("Framebuffer created.");
    }

    public static void endProjectRedHalo() {
        if (shadersmod.client.Shaders.isRenderingWorld) {
            useProgram(3);
        }
    }

    public static void resetDisplayListModelRenderer(ModelRenderer modelRenderer) {
        int[] aint1 = ShaderOption.lz();
        modelRenderer.resetDisplayList();
        int[] aint = aint1;
        if (modelRenderer.childModels != null) {
            int i = 0;
            int j = modelRenderer.childModels.size();

            while (i < j) {
                resetDisplayListModelRenderer((ModelRenderer) modelRenderer.childModels.get(i));
                i++;
                if (aint != null) {
                    break;
                }
            }
        }
    }

    private static void saveShader(String text, String textValue) {
        try {
            File file1 = new File(shadersmod.client.Shaders.shaderpacksdir, "debug/" + text);
            file1.getParentFile().mkdirs();
            OptiFineConfig.writeFile(file1, textValue);
        } catch (IOException ioexception) {
            OptiFineConfig.warn("Error saving: " + text);
            ioexception.printStackTrace();
        }
    }

    private static Properties loadOptionProperties(IShaderPack iShaderPack) throws IOException {
        Properties properties = new Properties();
        String text =
                shadersmod.client.Shaders.shaderpacksdirname
                        + packedTextFragments[5]
                        + iShaderPack.getName()
                        + ".txt";
        File file1 = new File(MinecraftClient.getMinecraft().gameDirectory, text);
        if (file1.exists() && file1.isFile() && file1.canRead()) {
            FileInputStream fileinputstream = new FileInputStream(file1);
            properties.load(fileinputstream);
            fileinputstream.close();
            return properties;
        } else {
            return properties;
        }
    }

    private static ShaderOption[] getShaderOptionsRest(
            Map<String, ShaderOption[]> map, ShaderOption[] shaderOptionArray) {
        int[] aint1 = ShaderOption.lz();
        HashSet hashset = new HashSet();
        int[] aint = aint1;

        label41:
        for (java.util.Iterator<?> __recoveryIterator171_120209 = (map.keySet()).iterator();
                (__recoveryIterator171_120209).hasNext(); ) {
            String text = (String) (__recoveryIterator171_120209).next();
            ShaderOption[] shaderOptionArrayValue = (ShaderOption[]) map.get(text);
            int i = 0;

            while (true) {
                if (i < shaderOptionArrayValue.length) {
                    ShaderOption shaderOption = shaderOptionArrayValue[i];
                    if (shaderOption != null) {
                        hashset.add(shaderOption.getName());
                    }

                    i++;
                    if (aint == null) {
                        continue;
                    }
                }

                if (aint != null) {
                    break label41;
                }
                break;
            }
        }

        ArrayList arrayList = new ArrayList();
        int j = 0;

        while (j < shaderOptionArray.length) {
            ShaderOption shaderOptionValue = shaderOptionArray[j];
            if (shaderOptionValue.isVisible()) {
                String textValue = shaderOptionValue.getName();
                if (!hashset.contains(textValue)) {
                    arrayList.add(shaderOptionValue);
                }
            }

            j++;
            if (aint != null) {
                break;
            }
        }

        return ((shadersmod.client.ShaderOption[])
                arrayList.toArray(new ShaderOption[arrayList.size()]));
    }

    private static void loadEntityDataMap() {
        int[] aint1 = ShaderOption.lz();
        shadersmod.client.Shaders.mapBlockToEntityData = new IdentityHashMap<>(300);
        int[] aint = aint1;
        if (shadersmod.client.Shaders.mapBlockToEntityData.isEmpty()) {
            for (java.util.Iterator<?> __recoveryIterator171_142096 =
                            (Block.blockRegistry.getKeys()).iterator();
                    (__recoveryIterator171_142096).hasNext(); ) {
                ResourceLocation resourceLocation =
                        (ResourceLocation) (__recoveryIterator171_142096).next();
                Block block = (Block) Block.blockRegistry.getObject(resourceLocation);
                int idForObject = Block.blockRegistry.getIDForObject(block);
                shadersmod.client.Shaders.mapBlockToEntityData.put(block, idForObject);
                if (aint != null) {
                    break;
                }
            }
        }

        BufferedReader bufferedreader = null;

        try {
            bufferedreader =
                    new BufferedReader(
                            new InputStreamReader(
                                    shadersmod.client.Shaders.shaderPack.getResourceAsStream(
                                            "/mc_Entity_x.txt")));
        } catch (Exception exception1) {
        }

        if (bufferedreader != null) {
            String text;
            try {
                while ((text = bufferedreader.readLine()) != null) {
                    label51:
                    {
                        Matcher matcher = Mg.matcher(text);
                        if (matcher.matches()) {
                            {
                                String textValue = matcher.group(1);
                                String textValue2 = matcher.group(2);
                                int j = Integer.parseInt(textValue2);
                                Block blockValue = Block.getBlockFromName(textValue);
                                if (blockValue != null) {
                                    shadersmod.client.Shaders.mapBlockToEntityData.put(
                                            blockValue, j);
                                    if (!(aint == null)) {

                                        ShaderLog.method_f293f253906d266e1f11(
                                                "Unknown block name %s", new Object[] {textValue});
                                    }
                                } else {

                                    ShaderLog.method_f293f253906d266e1f11(
                                            "Unknown block name %s", new Object[] {textValue});
                                }
                            }

                            if (aint == null) {
                                break label51;
                            }
                        }

                        ShaderLog.method_f293f253906d266e1f11(
                                "unmatched %s\n", new Object[] {text});
                    }

                    if (aint != null) {
                        break;
                    }
                }
            } catch (Exception exception2) {
                ShaderLog.warning("Error parsing mc_Entity_x.txt");
            }
        }

        if (bufferedreader != null) {
            try {
                bufferedreader.close();
            } catch (Exception exception) {
            }
        }
    }

    private static IntBuffer fillIntBufferZero(IntBuffer intbuffer) {
        int[] aint1 = ShaderOption.lz();
        int i = intbuffer.limit();
        int[] aint = aint1;
        int j = intbuffer.position();

        while (j < i) {
            intbuffer.put(j, 0);
            j++;
            if (aint != null) {
                break;
            }
        }

        return intbuffer;
    }

    public static void glEnableWrapper(int i) {
        GL11.glEnable(i);
        onTexture2DEnabled();
    }

    public static void disableFog() {
        shadersmod.client.Shaders.fogEnabled = false;
        setProgramUniform1i("fogMode", 0);
    }

    public static void resourcesReloaded() {
        loadShaderPackResources();
    }

    public static void kl() {
        FloatBuffer floatbuffer = shadersmod.client.Shaders.tempMatrixDirectBuffer;
        ((Buffer) floatbuffer).clear();
        GL11.glGetFloat(2982, floatbuffer);
        floatbuffer.get(shadersmod.client.Shaders.tempMat, 0, 16);
        ShaderMath.multiplyMat4xVec4(
                shadersmod.client.Shaders.upPosition,
                shadersmod.client.Shaders.tempMat,
                shadersmod.client.Shaders.upPosModelView);
    }

    public static void nv() {
        useProgram(3);
    }

    public static void readCenterDepth() {
        try {
            if (shadersmod.client.Shaders.isShadowPass
                    || !shadersmod.client.Shaders.centerDepthSmoothEnabled) {
                return;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }

        ((java.nio.Buffer) shadersmod.client.Shaders.tempDirectFloatBuffer).clear();
        org.lwjgl.opengl.GL11.glReadPixels(
                shadersmod.client.Shaders.renderWidth / 2,
                shadersmod.client.Shaders.renderHeight / 2,
                1,
                1,
                6402,
                5126,
                shadersmod.client.Shaders.tempDirectFloatBuffer);
        shadersmod.client.Shaders.centerDepth =
                shadersmod.client.Shaders.tempDirectFloatBuffer.get(0);
        float fx = (float) shadersmod.client.Shaders.diffSystemTime * 0.01F;
        float f1 =
                (float)
                        java.lang.Math.exp(
                                java.lang.Math.log(0.5)
                                        * fx
                                        / shadersmod.client.Shaders.centerDepthSmoothHalflife);
        shadersmod.client.Shaders.centerDepthSmooth =
                shadersmod.client.Shaders.centerDepthSmooth * f1
                        + shadersmod.client.Shaders.centerDepth * (1.0F - f1);
    }

    public static void storeConfig() {
        int[] aint1 = ShaderOption.lz();
        ShaderLog.info("Save ShadersMod configuration.");
        int[] aint = aint1;
        if (shadersmod.client.Shaders.shadersConfig == null) {
            shadersmod.client.Shaders.shadersConfig = new PropertiesOrdered();
        }

        EnumShaderOption[] enumShaderOptionArray = EnumShaderOption.values();
        int i = 0;

        while (i < enumShaderOptionArray.length) {
            EnumShaderOption enumShaderOption = enumShaderOptionArray[i];
            String text = enumShaderOption.getPropertyKey();
            String textValue = getEnumShaderOption(enumShaderOption);
            shadersmod.client.Shaders.shadersConfig.setProperty(text, textValue);
            i++;
            if (aint != null) {
                break;
            }
        }

        try {
            FileWriter filewriter = new FileWriter(shadersmod.client.Shaders.configFile);
            shadersmod.client.Shaders.shadersConfig.store(filewriter, (String) null);
            filewriter.close();
        } catch (Exception exception) {
            ShaderLog.severe(
                    "Error saving configuration: "
                            + exception.getClass().getName()
                            + ": "
                            + exception.getMessage());
        }
    }

    public static void beginUpdateChunks() {
        checkGLError("beginUpdateChunks1");
        checkFramebufferStatus("beginUpdateChunks1");
        if (!shadersmod.client.Shaders.isShadowPass) {
            useProgram(7);
        }

        checkGLError("beginUpdateChunks2");
        checkFramebufferStatus("beginUpdateChunks2");
    }

    public static void loadShaderPack() {
        int[] aint1 = ShaderOption.lz();
        boolean shaderPackLoadedValue = shadersmod.client.Shaders.shaderPackLoaded;
        int[] aint = aint1;
        boolean flag1 = isOldLighting();
        shadersmod.client.Shaders.shaderPackLoaded = false;
        if (shadersmod.client.Shaders.shaderPack != null) {
            shadersmod.client.Shaders.shaderPack.close();
            shadersmod.client.Shaders.shaderPack = null;
            shadersmod.client.Shaders.shaderPackResources.clear();
            shadersmod.client.Shaders.shaderPackDimensions.clear();
            shadersmod.client.Shaders.shaderPackOptions = null;
            shadersmod.client.Shaders.shaderPackProfiles = null;
            shadersmod.client.Shaders.shaderPackGuiScreens = null;
            shadersmod.client.Shaders.shaderPackClouds.resetValue();
            shadersmod.client.Shaders.shaderPackDynamicHandLight.resetValue();
            shadersmod.client.Shaders.shaderPackOldLighting.resetValue();
        }

        byte b0 = 0;
        if (OptiFineConfig.isAntialiasing()) {
            ShaderLog.info(
                    "Shaders can not be loaded, Antialiasing is enabled: "
                            + OptiFineConfig.getAntialiasingLevel()
                            + packedTextFragments[1]);
            b0 = 1;
        }

        if (OptiFineConfig.isAnisotropicFiltering()) {
            ShaderLog.info(
                    "Shaders can not be loaded, Anisotropic Filtering is enabled: "
                            + OptiFineConfig.getAnisotropicFilterLevel()
                            + packedTextFragments[2]);
            b0 = 1;
        }

        if (OptiFineConfig.isFastRender()) {
            ShaderLog.info("Shaders can not be loaded, Fast Render is enabled.");
            b0 = 1;
        }

        String property =
                shadersmod.client.Shaders.shadersConfig.getProperty(
                        EnumShaderOption.SHADER_PACK.getPropertyKey(),
                        shadersmod.client.Shaders.packNameDefault);
        label82:
        if (!property.isEmpty()
                && !property.equals(shadersmod.client.Shaders.packNameNone)
                && b0 == 0) {
            if (property.equals(shadersmod.client.Shaders.packNameDefault)) {
                shadersmod.client.Shaders.shaderPack = new ShaderPackDefault();
                shadersmod.client.Shaders.shaderPackLoaded = true;
                if (aint == null) {
                    break label82;
                }
            }

            try {
                {
                    File file1 = new File(shadersmod.client.Shaders.shaderpacksdir, property);
                    if (file1.isDirectory()) {
                        shadersmod.client.Shaders.shaderPack =
                                new ShaderPackFolder(property, file1);
                        shadersmod.client.Shaders.shaderPackLoaded = true;
                        if (!(aint == null)) {

                            if (file1.isFile() && property.toLowerCase().endsWith(".zip")) {
                                shadersmod.client.Shaders.shaderPack =
                                        new ShaderPackZip(property, file1);
                                shadersmod.client.Shaders.shaderPackLoaded = true;
                            }
                        }
                    } else {

                        if (file1.isFile() && property.toLowerCase().endsWith(".zip")) {
                            shadersmod.client.Shaders.shaderPack =
                                    new ShaderPackZip(property, file1);
                            shadersmod.client.Shaders.shaderPackLoaded = true;
                        }
                    }
                }
            } catch (Exception exception) {
            }
        }

        {
            if (shadersmod.client.Shaders.shaderPack != null) {
                ShaderLog.info("Loaded shaderpack: " + getShaderPackName());
                if (!(aint == null)) {

                    ShaderLog.info("No shaderpack loaded.");
                    shadersmod.client.Shaders.shaderPack = new ShaderPackNone();
                }
            } else {

                ShaderLog.info("No shaderpack loaded.");
                shadersmod.client.Shaders.shaderPack = new ShaderPackNone();
            }
        }

        loadShaderPackResources();
        loadShaderPackDimensions();
        shadersmod.client.Shaders.shaderPackOptions = loadShaderPackOptions();
        loadShaderPackProperties();
        int j = shadersmod.client.Shaders.shaderPackLoaded != shaderPackLoadedValue ? 1 : 0;
        int i = isOldLighting() != flag1 ? 1 : 0;
        if (j != 0 || i != 0) {
            DefaultVertexFormats.updateVertexFormats();
            if (Reflector.LightUtil.exists()) {
                Reflector.LightUtil_itemConsumer.setValue(null);
                Reflector.LightUtil_tessellator.setValue(null);
            }

            updateBlockLightLevel();
            shadersmod.client.Shaders.mc.scheduleResourcesRefresh();
        }
    }

    public static void ny() {
        int[] aint = ShaderOption.lz();
        if (shadersmod.client.Shaders.isShadowPass) {
            checkGLError("shadow endRender");
            if (aint == null) {
                return;
            }
        }

        if (!shadersmod.client.Shaders.isCompositeRendered) {
            mS();
        }

        shadersmod.client.Shaders.isRenderingWorld = false;
        GlStateManager.colorMask(true, true, true, true);
        useProgram(0);
        RenderHelper.disableStandardItemLighting();
        checkGLError("endRender end");
    }

    public static void beginLeash() {
        useProgram(1);
    }

    private static ShaderOption[] loadShaderPackOptions() {
        int[] aint = ShaderOption.lz();

        try {
            ShaderOption[] shaderOptionArray =
                    ShaderPackParser.parseShaderPackOptions(
                            shadersmod.client.Shaders.shaderPack,
                            shadersmod.client.Shaders.programNames,
                            shadersmod.client.Shaders.shaderPackDimensions);
            Properties properties = loadOptionProperties(shadersmod.client.Shaders.shaderPack);
            int i = 0;

            while (i < shaderOptionArray.length) {
                ShaderOption shaderOption = shaderOptionArray[i];
                String property = properties.getProperty(shaderOption.getName());
                if (property != null) {
                    shaderOption.resetValue();
                    if (!shaderOption.setValue(property)) {
                        OptiFineConfig.warn(
                                "[Shaders] Invalid value, option: "
                                        + shaderOption.getName()
                                        + ", value: "
                                        + property);
                    }
                }

                i++;
                if (aint != null) {
                    break;
                }
            }

            return shaderOptionArray;
        } catch (IOException ioexception) {
            OptiFineConfig.warn(
                    "[Shaders] Error reading configuration for "
                            + shadersmod.client.Shaders.shaderPack.getName());
            ioexception.printStackTrace();
            return null;
        }
    }

    public static void nextEntity(Entity entity) {
        try {
            if (shadersmod.client.Shaders.isRenderingWorld) {
                shadersmod.client.Shaders.useProgram(16);
                shadersmod.client.Shaders.setEntityId(entity);
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }
    }

    private static void clearDirectory(File file1) {
        int[] aint = ShaderOption.lz();
        if (file1.exists() && file1.isDirectory()) {
            File[] afile = file1.listFiles();
            if (afile != null) {
                int i = 0;

                while (i < afile.length) {
                    File file2 = afile[i];
                    if (file2.isDirectory()) {
                        clearDirectory(file2);
                    }

                    file2.delete();
                    i++;
                    if (aint != null) {
                        break;
                    }
                }
            }
        }
    }

    public static void updateBlockLightLevel() {
        int[] aint = ShaderOption.lz();
        if (isOldLighting()) {
            shadersmod.client.Shaders.blockLightLevel05 = 0.5F;
            shadersmod.client.Shaders.blockLightLevel06 = 0.6F;
            shadersmod.client.Shaders.blockLightLevel08 = 0.8F;
            if (aint == null) {
                return;
            }
        }

        shadersmod.client.Shaders.blockLightLevel05 = 1.0F;
        shadersmod.client.Shaders.blockLightLevel06 = 1.0F;
        shadersmod.client.Shaders.blockLightLevel08 = 1.0F;
    }

    public static void endLeash() {
        useProgram(16);
    }

    public static void scheduleResizeShadow() {
        shadersmod.client.Shaders.needResizeShadow = true;
    }

    public static void setEntityId(Entity entity) {
        if (shadersmod.client.Shaders.isRenderingWorld
                && !shadersmod.client.Shaders.isShadowPass
                && shadersmod.client.Shaders.uniformEntityId.isDefined()) {
            int entityID = net.minecraft.entity.EntityList.getEntityID(entity);
            shadersmod.client.Shaders.uniformEntityId.setValue(entityID);
        }
    }

    public static void nE() {
        int[] aint = ShaderOption.lz();
        if (shadersmod.client.Shaders.isRenderingWorld) {
            if (!shadersmod.client.Shaders.isShadowPass) {
                useProgram(12);
                GlStateManager.enableBlend();
                GlStateManager.depthMask(true);
                if (aint == null) {
                    return;
                }
            }

            GlStateManager.depthMask(true);
        }
    }

    public static void method_ff28ed8d0a795e8ac8e9(int i) {
        int[] aint1 = ShaderOption.lz();
        GL11.glEnable(i);
        int[] aint = aint1;
        if (i == 3553) {
            onTexture2DEnabled();
            if (aint == null) {
                return;
            }
        }

        if (i == 2912) {
            enableFog();
        }
    }

    public static void beginRender(MinecraftClient minecraftClient, float f, long i) {
        int[] aint1 = ShaderOption.lz();
        checkGLError("pre beginRender");
        checkWorldChanged(shadersmod.client.Shaders.mc.world);
        shadersmod.client.Shaders.mc = minecraftClient;
        int[] aint = aint1;
        shadersmod.client.Shaders.mc.mcProfiler.startSection("init");
        shadersmod.client.Shaders.entityRenderer = shadersmod.client.Shaders.mc.entityRenderer;
        if (!shadersmod.client.Shaders.isShaderPackInitialized) {
            try {
                init();
            } catch (IllegalStateException illegalstateexception) {
                if (OptiFineConfig.normalize(illegalstateexception.getMessage())
                        .equals("Function is not supported")) {
                    printChatAndLogError("[Shaders] Error: " + illegalstateexception.getMessage());
                    illegalstateexception.printStackTrace();
                    setShaderPack(shadersmod.client.Shaders.packNameNone);
                    return;
                }
            }
        }

        if (shadersmod.client.Shaders.mc.displayWidth
                        != shadersmod.client.Shaders.renderDisplayWidth
                || shadersmod.client.Shaders.mc.displayHeight
                        != shadersmod.client.Shaders.renderDisplayHeight) {
            resize();
        }

        if (shadersmod.client.Shaders.needResizeShadow) {
            resizeShadow();
        }

        shadersmod.client.Shaders.worldTime = shadersmod.client.Shaders.mc.world.getWorldTime();
        long k = shadersmod.client.Shaders.worldTime - shadersmod.client.Shaders.lastWorldTime;

        shadersmod.client.Shaders.diffWorldTime = k % 24000L;

        if (shadersmod.client.Shaders.diffWorldTime < 0L) {

            shadersmod.client.Shaders.diffWorldTime += 24000L;
        }

        shadersmod.client.Shaders.lastWorldTime = shadersmod.client.Shaders.worldTime;
        shadersmod.client.Shaders.moonPhase = shadersmod.client.Shaders.mc.world.getMoonPhase();
        shadersmod.client.Shaders.systemTime = System.currentTimeMillis();

        if (shadersmod.client.Shaders.lastSystemTime == 0L) {
            shadersmod.client.Shaders.lastSystemTime = shadersmod.client.Shaders.systemTime;
        }

        shadersmod.client.Shaders.diffSystemTime =
                shadersmod.client.Shaders.systemTime - shadersmod.client.Shaders.lastSystemTime;
        shadersmod.client.Shaders.lastSystemTime = shadersmod.client.Shaders.systemTime;
        shadersmod.client.Shaders.frameTime =
                shadersmod.client.Shaders.frameTime
                        + (float) shadersmod.client.Shaders.diffSystemTime * 0.001F;
        shadersmod.client.Shaders.frameTime %= 3600.0F;
        shadersmod.client.Shaders.rainStrength = minecraftClient.world.getRainStrength(f);
        float f1 = (float) shadersmod.client.Shaders.diffSystemTime * 0.01F;
        float f2 =
                (float)
                        Math.exp(
                                Math.log(0.5)
                                        * f1
                                        / (shadersmod.client.Shaders.wetness
                                                        < shadersmod.client.Shaders.rainStrength
                                                ? shadersmod.client.Shaders.drynessHalfLife
                                                : shadersmod.client.Shaders.wetnessHalfLife));
        shadersmod.client.Shaders.wetness =
                shadersmod.client.Shaders.wetness * f2
                        + shadersmod.client.Shaders.rainStrength * (1.0F - f2);
        Entity entity = shadersmod.client.Shaders.mc.getRenderViewEntity();
        shadersmod.client.Shaders.isSleeping =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (entity instanceof LivingEntity
                                        && ((LivingEntity) entity).isPlayerSleeping()
                                ? 1
                                : 0));
        shadersmod.client.Shaders.eyePosY =
                (float) entity.posY * f + (float) entity.lastTickPosY * (1.0F - f);
        shadersmod.client.Shaders.eyeBrightness = entity.getBrightnessForRender(f);
        f2 = (float) shadersmod.client.Shaders.diffSystemTime * 0.01F;
        float f3 =
                (float)
                        Math.exp(
                                Math.log(0.5)
                                        * f2
                                        / shadersmod.client.Shaders.eyeBrightnessHalflife);
        shadersmod.client.Shaders.eyeBrightnessFadeX =
                shadersmod.client.Shaders.eyeBrightnessFadeX * f3
                        + (shadersmod.client.Shaders.eyeBrightness & 65535) * (1.0F - f3);
        shadersmod.client.Shaders.eyeBrightnessFadeY =
                shadersmod.client.Shaders.eyeBrightnessFadeY * f3
                        + (shadersmod.client.Shaders.eyeBrightness >> 16) * (1.0F - f3);
        shadersmod.client.Shaders.isEyeInWater =
                shadersmod.client.Shaders.mc.gameSettings.thirdPersonView == 0
                                && !shadersmod.client.Shaders.isSleeping
                                && shadersmod.client.Shaders.mc.player.isInsideOfMaterial(
                                        Material.water)
                        ? 1
                        : 0;
        Vec3 vec3 =
                shadersmod.client.Shaders.mc.world.getSkyColor(
                        shadersmod.client.Shaders.mc.getRenderViewEntity(), f);
        shadersmod.client.Shaders.skyColorR = (float) vec3.x;
        shadersmod.client.Shaders.skyColorG = (float) vec3.y;
        shadersmod.client.Shaders.skyColorB = (float) vec3.z;
        shadersmod.client.Shaders.isRenderingWorld = true;
        shadersmod.client.Shaders.isCompositeRendered = false;
        shadersmod.client.Shaders.isHandRenderedMain = false;
        if (shadersmod.client.Shaders.usedShadowDepthBuffers >= 1) {
            GlStateManager.setActiveTexture(33988);
            GlStateManager.bindTexture(shadersmod.client.Shaders.sfbDepthTextures.get(0));
            if (shadersmod.client.Shaders.usedShadowDepthBuffers >= 2) {
                GlStateManager.setActiveTexture(33989);
                GlStateManager.bindTexture(shadersmod.client.Shaders.sfbDepthTextures.get(1));
            }
        }

        GlStateManager.setActiveTexture(33984);
        int j = 0;

        while (j < shadersmod.client.Shaders.usedColorBuffers) {
            GlStateManager.bindTexture(shadersmod.client.Shaders.dfbColorTexturesA[j]);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9729);
            GlStateManager.bindTexture(shadersmod.client.Shaders.dfbColorTexturesA[8 + j]);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9729);
            j++;
            if (aint != null) {
                break;
            }
        }

        GlStateManager.bindTexture(0);
        j = 0;

        while (j < 4 && 4 + j < shadersmod.client.Shaders.usedColorBuffers) {
            GlStateManager.setActiveTexture(33991 + j);
            GlStateManager.bindTexture(shadersmod.client.Shaders.dfbColorTextures.get(4 + j));
            j++;
            if (aint != null) {
                break;
            }
        }

        GlStateManager.setActiveTexture(33990);
        GlStateManager.bindTexture(shadersmod.client.Shaders.dfbDepthTextures.get(0));
        if (shadersmod.client.Shaders.usedDepthBuffers >= 2) {
            GlStateManager.setActiveTexture(33995);
            GlStateManager.bindTexture(shadersmod.client.Shaders.dfbDepthTextures.get(1));
            if (shadersmod.client.Shaders.usedDepthBuffers >= 3) {
                GlStateManager.setActiveTexture(33996);
                GlStateManager.bindTexture(shadersmod.client.Shaders.dfbDepthTextures.get(2));
            }
        }

        j = 0;

        while (j < shadersmod.client.Shaders.usedShadowColorBuffers) {
            GlStateManager.setActiveTexture(33997 + j);
            GlStateManager.bindTexture(shadersmod.client.Shaders.sfbColorTextures.get(j));
            j++;
            if (aint != null) {
                break;
            }
        }

        if (shadersmod.client.Shaders.noiseTextureEnabled) {
            GlStateManager.setActiveTexture(
                    33984 + shadersmod.client.Shaders.customUniforms.textureUnit);
            GlStateManager.bindTexture(shadersmod.client.Shaders.customUniforms.getTexID());
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9729);
        }

        GlStateManager.setActiveTexture(33984);
        shadersmod.client.Shaders.previousCameraPositionX =
                shadersmod.client.Shaders.cameraPositionX;
        shadersmod.client.Shaders.previousCameraPositionY =
                shadersmod.client.Shaders.cameraPositionY;
        shadersmod.client.Shaders.previousCameraPositionZ =
                shadersmod.client.Shaders.cameraPositionZ;
        ((Buffer) shadersmod.client.Shaders.previousProjection).position(0);
        ((Buffer) shadersmod.client.Shaders.projection).position(0);
        shadersmod.client.Shaders.previousProjection.put(shadersmod.client.Shaders.projection);
        ((Buffer) shadersmod.client.Shaders.previousProjection).position(0);
        ((Buffer) shadersmod.client.Shaders.projection).position(0);
        ((Buffer) shadersmod.client.Shaders.previousModelView).position(0);
        ((Buffer) shadersmod.client.Shaders.modelView).position(0);
        shadersmod.client.Shaders.previousModelView.put(shadersmod.client.Shaders.modelView);
        ((Buffer) shadersmod.client.Shaders.previousModelView).position(0);
        ((Buffer) shadersmod.client.Shaders.modelView).position(0);
        checkGLError("beginRender");
        ShadersRender.renderShadowMap(shadersmod.client.Shaders.entityRenderer, 0, f, i);
        shadersmod.client.Shaders.mc.mcProfiler.endSection();
        EXTFramebufferObject.glBindFramebufferEXT(36160, shadersmod.client.Shaders.dfb);
        j = 0;

        while (j < shadersmod.client.Shaders.usedColorBuffers) {
            shadersmod.client.Shaders.colorTexturesToggle[j] = 0;
            EXTFramebufferObject.glFramebufferTexture2DEXT(
                    36160, 36064 + j, 3553, shadersmod.client.Shaders.dfbColorTexturesA[j], 0);
            j++;
            if (aint != null) {
                break;
            }
        }

        checkGLError("end beginRender");
    }

    public static void endBlockEntities() {
        if (shadersmod.client.Shaders.isRenderingWorld) {
            checkGLError("endBlockEntities");
            useProgram(shadersmod.client.Shaders.lightmapEnabled ? 3 : 2);
            ShadersTex.bindNSTextures(shadersmod.client.Shaders.defaultTexture.getMultiTexID());
        }
    }

    public static void setBlockEntityId(TileEntity tileEntity) {
        if (shadersmod.client.Shaders.isRenderingWorld
                && !shadersmod.client.Shaders.isShadowPass
                && shadersmod.client.Shaders.uniformBlockEntityId.isDefined()) {
            net.minecraft.block.Block block = tileEntity.getBlockType();
            int idFromBlock = net.minecraft.block.Block.getIdFromBlock(block);
            shadersmod.client.Shaders.uniformBlockEntityId.setValue(idFromBlock);
        }
    }

    public static void useProgram(int activeProgramValue) {
        int[] aint1 = ShaderOption.lz();
        checkGLError("pre-useProgram");
        int[] aint = aint1;
        if (shadersmod.client.Shaders.isShadowPass) {
            activeProgramValue = 30;
            if (shadersmod.client.Shaders.programsID[30] == 0) {
                shadersmod.client.Shaders.normalMapEnabled = false;
                return;
            }
        }

        if (shadersmod.client.Shaders.activeProgram != activeProgramValue) {
            shadersmod.client.Shaders.activeProgram = activeProgramValue;
            ARBShaderObjects.glUseProgramObjectARB(
                    shadersmod.client.Shaders.programsID[activeProgramValue]);
            if (shadersmod.client.Shaders.programsID[activeProgramValue] == 0) {
                shadersmod.client.Shaders.normalMapEnabled = false;
                if (aint == null) {
                    return;
                }
            }

            if (checkGLError(
                            "useProgram ",
                            shadersmod.client.Shaders.programNames[activeProgramValue])
                    != 0) {
                shadersmod.client.Shaders.programsID[activeProgramValue] = 0;
            }

            IntBuffer intbuffer = shadersmod.client.Shaders.programsDrawBuffers[activeProgramValue];
            if (shadersmod.client.Shaders.isRenderingDfb) {
                setDrawBuffers(intbuffer);
                checkGLError(
                        shadersmod.client.Shaders.programNames[activeProgramValue],
                        " draw buffers = ",
                        shadersmod.client.Shaders.programsDrawBufSettings[activeProgramValue]);
            }

            shadersmod.client.Shaders.activeCompositeMipmapSetting =
                    shadersmod.client.Shaders.programsCompositeMipmapSetting[activeProgramValue];
            shadersmod.client.Shaders.uniformEntityColor.setProgram(
                    shadersmod.client.Shaders.programsID[shadersmod.client.Shaders.activeProgram]);
            shadersmod.client.Shaders.uniformEntityId.setProgram(
                    shadersmod.client.Shaders.programsID[shadersmod.client.Shaders.activeProgram]);
            shadersmod.client.Shaders.uniformBlockEntityId.setProgram(
                    shadersmod.client.Shaders.programsID[shadersmod.client.Shaders.activeProgram]);
            switch (activeProgramValue) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                case 18:
                case 19:
                case 20:
                    shadersmod.client.Shaders.normalMapEnabled = true;
                    setProgramUniform1i("texture", 0);
                    setProgramUniform1i("lightmap", 1);
                    setProgramUniform1i("normals", 2);
                    setProgramUniform1i("specular", 3);
                    setProgramUniform1i(
                            "shadow", shadersmod.client.Shaders.waterShadowEnabled ? 5 : 4);
                    setProgramUniform1i("watershadow", 4);
                    setProgramUniform1i("shadowtex0", 4);
                    setProgramUniform1i("shadowtex1", 5);
                    setProgramUniform1i("depthtex0", 6);
                    setProgramUniform1i("depthtex1", 12);
                    setProgramUniform1i("shadowcolor", 13);
                    setProgramUniform1i("shadowcolor0", 13);
                    setProgramUniform1i("shadowcolor1", 14);
                    setProgramUniform1i("noisetex", 15);
                    if (aint == null) {
                        break;
                    }
                case 14:
                case 15:
                case 17:
                default:
                    shadersmod.client.Shaders.normalMapEnabled = false;
                    if (aint == null) {
                        break;
                    }
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                    shadersmod.client.Shaders.normalMapEnabled = false;
                    setProgramUniform1i("gcolor", 0);
                    setProgramUniform1i("gdepth", 1);
                    setProgramUniform1i("gnormal", 2);
                    setProgramUniform1i("composite", 3);
                    setProgramUniform1i("gaux1", 7);
                    setProgramUniform1i("gaux2", 8);
                    setProgramUniform1i("gaux3", 9);
                    setProgramUniform1i("gaux4", 10);
                    setProgramUniform1i("colortex0", 0);
                    setProgramUniform1i("colortex1", 1);
                    setProgramUniform1i("colortex2", 2);
                    setProgramUniform1i("colortex3", 3);
                    setProgramUniform1i("colortex4", 7);
                    setProgramUniform1i("colortex5", 8);
                    setProgramUniform1i("colortex6", 9);
                    setProgramUniform1i("colortex7", 10);
                    setProgramUniform1i(
                            "shadow", shadersmod.client.Shaders.waterShadowEnabled ? 5 : 4);
                    setProgramUniform1i("watershadow", 4);
                    setProgramUniform1i("shadowtex0", 4);
                    setProgramUniform1i("shadowtex1", 5);
                    setProgramUniform1i("gdepthtex", 6);
                    setProgramUniform1i("depthtex0", 6);
                    setProgramUniform1i("depthtex1", 11);
                    setProgramUniform1i("depthtex2", 12);
                    setProgramUniform1i("shadowcolor", 13);
                    setProgramUniform1i("shadowcolor0", 13);
                    setProgramUniform1i("shadowcolor1", 14);
                    setProgramUniform1i("noisetex", 15);
                    if (aint == null) {
                        break;
                    }
                case 30:
                case 31:
                case 32:
                    setProgramUniform1i("tex", 0);
                    setProgramUniform1i("texture", 0);
                    setProgramUniform1i("lightmap", 1);
                    setProgramUniform1i("normals", 2);
                    setProgramUniform1i("specular", 3);
                    setProgramUniform1i(
                            "shadow", shadersmod.client.Shaders.waterShadowEnabled ? 5 : 4);
                    setProgramUniform1i("watershadow", 4);
                    setProgramUniform1i("shadowtex0", 4);
                    setProgramUniform1i("shadowtex1", 5);
                    setProgramUniform1i("shadowcolor", 13);
                    setProgramUniform1i("shadowcolor0", 13);
                    setProgramUniform1i("shadowcolor1", 14);
                    setProgramUniform1i("noisetex", 15);
            }

            int j;
            Block block;
            {
                ItemStack currentEquippedItem =
                        shadersmod.client.Shaders.mc.player.getCurrentEquippedItem();
                Item item = currentEquippedItem != null ? currentEquippedItem.getItem() : null;
                if (item != null) {
                    j = Item.itemRegistry.getIDForObject(item);
                    block = (Block) Block.blockRegistry.getObjectById(j);
                    if (!(aint == null)) {

                        j = -1;
                        block = null;
                    }
                } else {

                    j = -1;
                    block = null;
                }
            }

            int k = block != null ? block.getLightValue() : 0;
            setProgramUniform1i("heldItemId", j);
            setProgramUniform1i("heldBlockLightValue", k);
            setProgramUniform1i(
                    "fogMode",
                    shadersmod.client.Shaders.fogEnabled ? shadersmod.client.Shaders.fogMode : 0);
            setProgramUniform3f(
                    "fogColor",
                    shadersmod.client.Shaders.fogColorR,
                    shadersmod.client.Shaders.fogColorG,
                    shadersmod.client.Shaders.fogColorB);
            setProgramUniform3f(
                    "skyColor",
                    shadersmod.client.Shaders.skyColorR,
                    shadersmod.client.Shaders.skyColorG,
                    shadersmod.client.Shaders.skyColorB);
            setProgramUniform1i("worldTime", (int) shadersmod.client.Shaders.worldTime % 24000);
            setProgramUniform1i("moonPhase", shadersmod.client.Shaders.moonPhase);
            setProgramUniform1f("frameTimeCounter", shadersmod.client.Shaders.frameTime);
            setProgramUniform1f("sunAngle", shadersmod.client.Shaders.sunAngle);
            setProgramUniform1f("shadowAngle", shadersmod.client.Shaders.shadowAngle);
            setProgramUniform1f("rainStrength", shadersmod.client.Shaders.rainStrength);
            setProgramUniform1f(
                    "aspectRatio",
                    (float) shadersmod.client.Shaders.renderWidth
                            / shadersmod.client.Shaders.renderHeight);
            setProgramUniform1f("viewWidth", shadersmod.client.Shaders.renderWidth);
            setProgramUniform1f("viewHeight", shadersmod.client.Shaders.renderHeight);
            setProgramUniform1f("near", 0.05F);
            setProgramUniform1f(
                    "far", shadersmod.client.Shaders.mc.gameSettings.renderDistanceChunks * 16);
            setProgramUniform3f(
                    "sunPosition",
                    shadersmod.client.Shaders.sunPosition[0],
                    shadersmod.client.Shaders.sunPosition[1],
                    shadersmod.client.Shaders.sunPosition[2]);
            setProgramUniform3f(
                    "moonPosition",
                    shadersmod.client.Shaders.moonPosition[0],
                    shadersmod.client.Shaders.moonPosition[1],
                    shadersmod.client.Shaders.moonPosition[2]);
            setProgramUniform3f(
                    "shadowLightPosition",
                    shadersmod.client.Shaders.shadowLightPosition[0],
                    shadersmod.client.Shaders.shadowLightPosition[1],
                    shadersmod.client.Shaders.shadowLightPosition[2]);
            setProgramUniform3f(
                    "upPosition",
                    shadersmod.client.Shaders.upPosition[0],
                    shadersmod.client.Shaders.upPosition[1],
                    shadersmod.client.Shaders.upPosition[2]);
            setProgramUniform3f(
                    "previousCameraPosition",
                    (float) shadersmod.client.Shaders.previousCameraPositionX,
                    (float) shadersmod.client.Shaders.previousCameraPositionY,
                    (float) shadersmod.client.Shaders.previousCameraPositionZ);
            setProgramUniform3f(
                    "cameraPosition",
                    (float) shadersmod.client.Shaders.cameraPositionX,
                    (float) shadersmod.client.Shaders.cameraPositionY,
                    (float) shadersmod.client.Shaders.cameraPositionZ);
            setProgramUniformMatrix4ARB(
                    "gbufferModelView", false, shadersmod.client.Shaders.modelView);
            setProgramUniformMatrix4ARB(
                    "gbufferModelViewInverse", false, shadersmod.client.Shaders.modelViewInverse);
            setProgramUniformMatrix4ARB(
                    "gbufferPreviousProjection",
                    false,
                    shadersmod.client.Shaders.previousProjection);
            setProgramUniformMatrix4ARB(
                    "gbufferProjection", false, shadersmod.client.Shaders.projection);
            setProgramUniformMatrix4ARB(
                    "gbufferProjectionInverse", false, shadersmod.client.Shaders.projectionInverse);
            setProgramUniformMatrix4ARB(
                    "gbufferPreviousModelView", false, shadersmod.client.Shaders.previousModelView);
            if (shadersmod.client.Shaders.usedShadowDepthBuffers > 0) {
                setProgramUniformMatrix4ARB(
                        "shadowProjection", false, shadersmod.client.Shaders.shadowProjection);
                setProgramUniformMatrix4ARB(
                        "shadowProjectionInverse",
                        false,
                        shadersmod.client.Shaders.shadowProjectionInverse);
                setProgramUniformMatrix4ARB(
                        "shadowModelView", false, shadersmod.client.Shaders.shadowModelView);
                setProgramUniformMatrix4ARB(
                        "shadowModelViewInverse",
                        false,
                        shadersmod.client.Shaders.shadowModelViewInverse);
            }

            setProgramUniform1f("wetness", shadersmod.client.Shaders.wetness);
            setProgramUniform1f("eyeAltitude", shadersmod.client.Shaders.eyePosY);
            setProgramUniform2i(
                    "eyeBrightness",
                    shadersmod.client.Shaders.eyeBrightness & 65535,
                    shadersmod.client.Shaders.eyeBrightness >> 16);
            setProgramUniform2i(
                    "eyeBrightnessSmooth",
                    Math.round(shadersmod.client.Shaders.eyeBrightnessFadeX),
                    Math.round(shadersmod.client.Shaders.eyeBrightnessFadeY));
            setProgramUniform2i(
                    "terrainTextureSize",
                    shadersmod.client.Shaders.terrainTextureSize[0],
                    shadersmod.client.Shaders.terrainTextureSize[1]);
            setProgramUniform1i("terrainIconSize", shadersmod.client.Shaders.terrainIconSize);
            setProgramUniform1i("isEyeInWater", shadersmod.client.Shaders.isEyeInWater);
            setProgramUniform1i(
                    "hideGUI", shadersmod.client.Shaders.mc.gameSettings.hideGUI ? 1 : 0);
            setProgramUniform1f("centerDepthSmooth", shadersmod.client.Shaders.centerDepthSmooth);
            setProgramUniform2i(
                    "atlasSize",
                    shadersmod.client.Shaders.atlasSizeX,
                    shadersmod.client.Shaders.atlasSizeY);
            checkGLError("useProgram ", shadersmod.client.Shaders.programNames[activeProgramValue]);
        }
    }

    private static boolean method_c11931953a5ac19e9b4d(int i, String text) {
        IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
        int j = GL20.glGetShaderi(i, 35716);
        if (j > 1) {
            String textValue = GL20.glGetShaderInfoLog(i, j);
            ShaderLog.info("Shader info log: " + text + packedTextFragments[20] + textValue);
            return false;
        } else {
            return true;
        }
    }

    private static int setupProgram(int i, String text, String textValue) {
        int[] aint1 = ShaderOption.lz();
        checkGLError("pre setupProgram");
        int[] aint = aint1;
        int j = ARBShaderObjects.glCreateProgramObjectARB();
        checkGLError("create");
        if (j != 0) {
            shadersmod.client.Shaders.progUseEntityAttrib = false;
            shadersmod.client.Shaders.progUseMidTexCoordAttrib = false;
            shadersmod.client.Shaders.progUseTangentAttrib = false;
            int k = createVertShader(text);
            int l = u(textValue);
            checkGLError("create");
            if (k == 0 && l == 0) {
                ARBShaderObjects.glDeleteObjectARB(j);
                j = 0;
                if (aint == null) {
                    return j;
                }
            }

            if (k != 0) {
                ARBShaderObjects.glAttachObjectARB(j, k);
                checkGLError("attach");
            }

            if (l != 0) {
                ARBShaderObjects.glAttachObjectARB(j, l);
                checkGLError("attach");
            }

            if (shadersmod.client.Shaders.progUseEntityAttrib) {
                ARBVertexShader.glBindAttribLocationARB(
                        j, shadersmod.client.Shaders.entityAttrib, "mc_Entity");
                checkGLError("mc_Entity");
            }

            if (shadersmod.client.Shaders.progUseMidTexCoordAttrib) {
                ARBVertexShader.glBindAttribLocationARB(
                        j, shadersmod.client.Shaders.midTexCoordAttrib, "mc_midTexCoord");
                checkGLError("mc_midTexCoord");
            }

            if (shadersmod.client.Shaders.progUseTangentAttrib) {
                ARBVertexShader.glBindAttribLocationARB(
                        j, shadersmod.client.Shaders.tangentAttrib, "at_tangent");
                checkGLError("at_tangent");
            }

            ARBShaderObjects.glLinkProgramARB(j);
            if (GL20.glGetProgrami(j, 35714) != 1) {
                ShaderLog.severe("Error linking program: " + j);
            }

            printLogInfo(j, text + ", " + textValue);
            if (k != 0) {
                ARBShaderObjects.glDetachObjectARB(j, k);
                ARBShaderObjects.glDeleteObjectARB(k);
            }

            if (l != 0) {
                ARBShaderObjects.glDetachObjectARB(j, l);
                ARBShaderObjects.glDeleteObjectARB(l);
            }

            shadersmod.client.Shaders.programsID[i] = j;
            useProgram(i);
            ARBShaderObjects.glValidateProgramARB(j);
            useProgram(0);
            printLogInfo(j, text + ", " + textValue);
            int i1 = GL20.glGetProgrami(j, 35715);
            if (i1 != 1) {
                String textValue2 = packedTextFragments[15];
                printChatAndLogError(
                        "[Shaders] Error: Invalid program "
                                + textValue2
                                + shadersmod.client.Shaders.programNames[i]
                                + textValue2);
                ARBShaderObjects.glDeleteObjectARB(j);
                j = 0;
            }
        }

        return j;
    }

    private static void setEnumShaderOption(EnumShaderOption enumShaderOption, String text) {
        int[] aint = ShaderOption.lz();
        if (text == null) {
            text = enumShaderOption.getValueDefault();
        }

        switch (enumShaderOption) {
            case dl:
                shadersmod.client.Shaders.configAntialiasingLevel =
                        OptiFineConfig.parseInt(text, 0);
                if (aint == null) {
                    break;
                }
            case ds:
                shadersmod.client.Shaders.configNormalMap = OptiFineConfig.parseBoolean(text, true);
                if (aint == null) {
                    break;
                }
            case dd:
                shadersmod.client.Shaders.configSpecularMap =
                        OptiFineConfig.parseBoolean(text, true);
                if (aint == null) {
                    break;
                }
            case dq:
                shadersmod.client.Shaders.configRenderResMul =
                        OptiFineConfig.parseFloat(text, 1.0F);
                if (aint == null) {
                    break;
                }
            case di:
                shadersmod.client.Shaders.configShadowResMul =
                        OptiFineConfig.parseFloat(text, 1.0F);
                if (aint == null) {
                    break;
                }
            case dn:
                shadersmod.client.Shaders.configHandDepthMul =
                        OptiFineConfig.parseFloat(text, 0.125F);
                if (aint == null) {
                    break;
                }
            case db:
                shadersmod.client.Shaders.configCloudShadow =
                        OptiFineConfig.parseBoolean(text, true);
                if (aint == null) {
                    break;
                }
            case dp:
                shadersmod.client.Shaders.configOldHandLight.setPropertyValue(text);
                if (aint == null) {
                    break;
                }
            case SHADER_PACK:
                shadersmod.client.Shaders.currentshadername = text;
                if (aint == null) {
                    break;
                }
            case dm:
                shadersmod.client.Shaders.configTweakBlockDamage =
                        OptiFineConfig.parseBoolean(text, true);
                if (aint == null) {
                    break;
                }
            case du:
                shadersmod.client.Shaders.configShadowClipFrustrum =
                        OptiFineConfig.parseBoolean(text, true);
                if (aint == null) {
                    break;
                }
            case df:
                shadersmod.client.Shaders.configTexMinFilB = OptiFineConfig.parseInt(text, 0);
                if (aint == null) {
                    break;
                }
            case dv:
                shadersmod.client.Shaders.configTexMinFilN = OptiFineConfig.parseInt(text, 0);
                if (aint == null) {
                    break;
                }
            case dk:
                shadersmod.client.Shaders.configTexMinFilS = OptiFineConfig.parseInt(text, 0);
                if (aint == null) {
                    break;
                }
            case de:
                shadersmod.client.Shaders.configTexMagFilB = OptiFineConfig.parseInt(text, 0);
                if (aint == null) {
                    break;
                }
            case dh:
                shadersmod.client.Shaders.configTexMagFilB = OptiFineConfig.parseInt(text, 0);
                if (aint == null) {
                    break;
                }
            case dc:
                shadersmod.client.Shaders.configTexMagFilB = OptiFineConfig.parseInt(text, 0);
                if (aint != null) {
                    throw new IllegalArgumentException("Unknown option: " + enumShaderOption);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown option: " + enumShaderOption);
        }
    }

    public static void printIntBuffer(String text, IntBuffer intbuffer) {
        StringBuilder stringbuilder = new StringBuilder(128);
        int[] aint1 = ShaderOption.lz();
        stringbuilder
                .append(text)
                .append(" [pos ")
                .append(intbuffer.position())
                .append(" lim ")
                .append(intbuffer.limit())
                .append(" cap ")
                .append(intbuffer.capacity())
                .append(" :");
        int i = intbuffer.limit();
        int[] aint = aint1;
        int j = 0;

        while (j < i) {
            stringbuilder.append(packedTextFragments[8]).append(intbuffer.get(j));
            j++;
            if (aint != null) {
                break;
            }
        }

        stringbuilder.append(packedTextFragments[9]);
        ShaderLog.info(stringbuilder.toString());
    }

    private static int createVertShader(String text) {
        int[] aint1 = ShaderOption.lz();
        int i = ARBShaderObjects.glCreateShaderObjectARB(35633);
        int[] aint = aint1;
        if (i == 0) {
            return 0;
        }

        StringBuilder stringbuilder = new StringBuilder(131072);
        BufferedReader bufferedreader = null;

        try {
            bufferedreader =
                    new BufferedReader(
                            new InputStreamReader(
                                    shadersmod.client.Shaders.shaderPack.getResourceAsStream(
                                            text)));
        } catch (Exception exception1) {
            try {
                bufferedreader = new BufferedReader(new FileReader(new File(text)));
            } catch (Exception exception) {
                ARBShaderObjects.glDeleteObjectARB(i);
                return 0;
            }
        }

        ShaderOption[] shaderOptionArray =
                getChangedOptions(shadersmod.client.Shaders.shaderPackOptions);
        if (bufferedreader != null) {
            try {
                bufferedreader =
                        ShaderPackParser.method_6fa68e5662b8e255afce(
                                bufferedreader, text, shadersmod.client.Shaders.shaderPack, 0);

                String textValue;
                while ((textValue = bufferedreader.readLine()) != null) {
                    textValue = applyOptions(textValue, shaderOptionArray);
                    stringbuilder.append(textValue).append('\n');
                    if (textValue.matches("attribute [_a-zA-Z0-9]+ mc_Entity.*")) {
                        shadersmod.client.Shaders.useEntityAttrib = true;
                        shadersmod.client.Shaders.progUseEntityAttrib = true;
                        if (aint == null) {
                            continue;
                        }
                    }

                    if (textValue.matches("attribute [_a-zA-Z0-9]+ mc_midTexCoord.*")) {
                        shadersmod.client.Shaders.useMidTexCoordAttrib = true;
                        shadersmod.client.Shaders.progUseMidTexCoordAttrib = true;
                        if (aint == null) {
                            continue;
                        }
                    }

                    if (textValue.matches(".*gl_MultiTexCoord3.*")) {
                        shadersmod.client.Shaders.useMultiTexCoord3Attrib = true;
                        if (aint == null) {
                            continue;
                        }
                    }

                    if (textValue.matches("attribute [_a-zA-Z0-9]+ at_tangent.*")) {
                        shadersmod.client.Shaders.useTangentAttrib = true;
                        shadersmod.client.Shaders.progUseTangentAttrib = true;
                        if (aint != null) {
                            break;
                        }
                    }
                }

                bufferedreader.close();
            } catch (Exception exception2) {
                ShaderLog.severe("Couldn't read " + text + packedTextFragments[16]);
                exception2.printStackTrace();
                ARBShaderObjects.glDeleteObjectARB(i);
                return 0;
            }
        }

        if (shadersmod.client.Shaders.saveFinalShaders) {
            saveShader(text, stringbuilder.toString());
        }

        ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
        ARBShaderObjects.glCompileShaderARB(i);
        if (GL20.glGetShaderi(i, 35713) != 1) {
            ShaderLog.severe("Error compiling vertex shader: " + text);
        }

        method_c11931953a5ac19e9b4d(i, text);
        return i;
    }

    public static void sglFogi(int i, int j) {
        GL11.glFogi(i, j);
        if (i == 2917) {
            shadersmod.client.Shaders.fogMode = j;
            if (shadersmod.client.Shaders.fogEnabled) {
                setProgramUniform1i("fogMode", shadersmod.client.Shaders.fogMode);
            }
        }
    }

    private static void setupNoiseTexture() {
        try {
            if (shadersmod.client.Shaders.customUniforms == null) {
                shadersmod.client.Shaders.customUniforms =
                        new shadersmod.client.HFNoiseTexture(
                                shadersmod.client.Shaders.noiseTextureResolution,
                                shadersmod.client.Shaders.noiseTextureResolution);
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }
    }

    private static void setupShadowFrameBuffer() {
        int[] aint = ShaderOption.lz();
        if (shadersmod.client.Shaders.usedShadowDepthBuffers != 0) {
            if (shadersmod.client.Shaders.sfb != 0) {
                EXTFramebufferObject.glDeleteFramebuffersEXT(shadersmod.client.Shaders.sfb);
                GlStateManager.deleteTextures(shadersmod.client.Shaders.sfbDepthTextures);
                GlStateManager.deleteTextures(shadersmod.client.Shaders.sfbColorTextures);
            }

            shadersmod.client.Shaders.sfb = EXTFramebufferObject.glGenFramebuffersEXT();
            EXTFramebufferObject.glBindFramebufferEXT(36160, shadersmod.client.Shaders.sfb);
            GL11.glDrawBuffer(0);
            GL11.glReadBuffer(0);
            GL11.glGenTextures(
                    (IntBuffer)
                            ((Buffer) shadersmod.client.Shaders.sfbDepthTextures)
                                    .clear()
                                    .limit(shadersmod.client.Shaders.usedShadowDepthBuffers));
            GL11.glGenTextures(
                    (IntBuffer)
                            ((Buffer) shadersmod.client.Shaders.sfbColorTextures)
                                    .clear()
                                    .limit(shadersmod.client.Shaders.usedShadowColorBuffers));
            ((Buffer) shadersmod.client.Shaders.sfbDepthTextures).position(0);
            ((Buffer) shadersmod.client.Shaders.sfbColorTextures).position(0);
            int i = 0;

            while (i < shadersmod.client.Shaders.usedShadowDepthBuffers) {
                GlStateManager.bindTexture(shadersmod.client.Shaders.sfbDepthTextures.get(i));
                GL11.glTexParameterf(3553, 10242, 10496.0F);
                GL11.glTexParameterf(3553, 10243, 10496.0F);
                int j = shadersmod.client.Shaders.shadowFilterNearest[i] ? 9728 : 9729;
                GL11.glTexParameteri(3553, 10241, j);
                GL11.glTexParameteri(3553, 10240, j);
                if (shadersmod.client.Shaders.shadowHardwareFilteringEnabled[i]) {
                    GL11.glTexParameteri(3553, 34892, 34894);
                }

                GL11.glTexImage2D(
                        3553,
                        0,
                        6402,
                        shadersmod.client.Shaders.shadowMapWidth,
                        shadersmod.client.Shaders.shadowMapHeight,
                        0,
                        6402,
                        5126,
                        (FloatBuffer) null);
                i++;
                if (aint != null) {
                    break;
                }
            }

            EXTFramebufferObject.glFramebufferTexture2DEXT(
                    36160, 36096, 3553, shadersmod.client.Shaders.sfbDepthTextures.get(0), 0);
            checkGLError("FT sd");
            i = 0;

            while (i < shadersmod.client.Shaders.usedShadowColorBuffers) {
                GlStateManager.bindTexture(shadersmod.client.Shaders.sfbColorTextures.get(i));
                GL11.glTexParameterf(3553, 10242, 10496.0F);
                GL11.glTexParameterf(3553, 10243, 10496.0F);
                int k = shadersmod.client.Shaders.shadowColorFilterNearest[i] ? 9728 : 9729;
                GL11.glTexParameteri(3553, 10241, k);
                GL11.glTexParameteri(3553, 10240, k);
                GL11.glTexImage2D(
                        3553,
                        0,
                        6408,
                        shadersmod.client.Shaders.shadowMapWidth,
                        shadersmod.client.Shaders.shadowMapHeight,
                        0,
                        32993,
                        33639,
                        (ByteBuffer) null);
                EXTFramebufferObject.glFramebufferTexture2DEXT(
                        36160,
                        36064 + i,
                        3553,
                        shadersmod.client.Shaders.sfbColorTextures.get(i),
                        0);
                checkGLError("FT sc");
                i++;
                if (aint != null) {
                    break;
                }
            }

            GlStateManager.bindTexture(0);
            if (shadersmod.client.Shaders.usedShadowColorBuffers > 0) {
                GL20.glDrawBuffers(shadersmod.client.Shaders.sfbDrawBuffers);
            }

            i = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
            if (i != 36053) {
                printChatAndLogError(
                        "[Shaders] Error: Failed creating shadow framebuffer! (Status "
                                + i
                                + packedTextFragments[22]);
                if (aint == null) {
                    return;
                }
            }

            ShaderLog.info("Shadow framebuffer created.");
        }
    }

    public static void beginWeather() {
        if (!shadersmod.client.Shaders.isShadowPass) {
            if (shadersmod.client.Shaders.usedDepthBuffers >= 3) {
                net.minecraft.client.renderer.GlStateManager.setActiveTexture(33996);
                org.lwjgl.opengl.GL11.glCopyTexSubImage2D(
                        3553,
                        0,
                        0,
                        0,
                        0,
                        0,
                        shadersmod.client.Shaders.renderWidth,
                        shadersmod.client.Shaders.renderHeight);
                net.minecraft.client.renderer.GlStateManager.setActiveTexture(33984);
            }

            net.minecraft.client.renderer.GlStateManager.enableDepth();
            net.minecraft.client.renderer.GlStateManager.enableBlend();
            net.minecraft.client.renderer.GlStateManager.blendFunc(770, 771);
            net.minecraft.client.renderer.GlStateManager.enableAlpha();
            shadersmod.client.Shaders.useProgram(20);
        }
    }

    static ArrayList listOfShaders() {
        int[] aint1 = ShaderOption.lz();
        ArrayList arrayList = new ArrayList();
        int[] aint = aint1;
        arrayList.add(shadersmod.client.Shaders.packNameNone);
        arrayList.add(shadersmod.client.Shaders.packNameDefault);

        try {
            if (!shadersmod.client.Shaders.shaderpacksdir.exists()) {
                shadersmod.client.Shaders.shaderpacksdir.mkdir();
            }

            File[] afile = shadersmod.client.Shaders.shaderpacksdir.listFiles();
            int i = 0;

            while (i < afile.length) {
                {
                    File file1 = afile[i];
                    String name = file1.getName();
                    if (file1.isDirectory()) {
                        File file2 = new File(file1, "shaders");
                        if (file2.exists() && file2.isDirectory()) {
                            arrayList.add(name);
                        }

                        if (!(aint == null)) {

                            if (file1.isFile() && name.toLowerCase().endsWith(".zip")) {
                                arrayList.add(name);
                            }
                        }
                    } else {

                        if (file1.isFile() && name.toLowerCase().endsWith(".zip")) {
                            arrayList.add(name);
                        }
                    }
                }

                i++;
                if (aint != null) {
                    break;
                }
            }
        } catch (Exception exception) {
        }

        return arrayList;
    }

    public static void nK() {
        try {
            if (!shadersmod.client.Shaders.isRenderingWorld) {
                return;
            }

            if (shadersmod.client.Shaders.isShadowPass) {}
        } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception1));
        }

        byte b0;
        label23:
        {
            try {
                if (shadersmod.client.Shaders.lightmapEnabled) {
                    b0 = 3;
                    break label23;
                }
            } catch (java.lang.IllegalArgumentException illegalargumentexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        shadersmod.client.Shaders.a(illegalargumentexception));
            }

            b0 = 2;
        }

        shadersmod.client.Shaders.useProgram(b0);
    }

    public static void applyHandDepthScale() {
        try {
            if (shadersmod.client.Shaders.configHandDepthMul != 1.0) {
                org.lwjgl.opengl.GL11.glScaled(
                        1.0, 1.0, shadersmod.client.Shaders.configHandDepthMul);
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }
    }

    private static ByteBuffer method_1d3153e9bd2ab49c7f8f(int p171_0) {
        java.nio.ByteBuffer bytebuffer = shadersmod.client.Shaders.bigBuffer;
        int j = bytebuffer.limit();
        ((java.nio.Buffer) bytebuffer).position(j).limit(j + p171_0);
        return bytebuffer.slice();
    }

    private static int K(String text) {
        int[] aint1 = ShaderOption.lz();
        text = text.trim();
        int i = 0;
        int[] aint = aint1;

        while (i < Lb.length) {
            String textValue = Lb[i];
            if (text.equals(textValue)) {
                return shadersmod.client.Shaders.formatIds[i];
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        return 0;
    }

    private static void loadShaderPackDimensions() {
        int[] aint1 = ShaderOption.lz();
        shadersmod.client.Shaders.shaderPackDimensions.clear();
        int[] aint = aint1;
        StringBuffer stringbuffer = new StringBuffer();
        int i = -128;

        while (i <= 128) {
            String text = "/shaders/world" + i;
            if (shadersmod.client.Shaders.shaderPack.hasDirectory(text)) {
                shadersmod.client.Shaders.shaderPackDimensions.add(i);
                stringbuffer.append(packedTextFragments[3] + i);
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        if (stringbuffer.length() > 0) {
            OptiFineConfig.dbg("[Shaders] Dimensions:" + stringbuffer);
        }
    }

    public static int checkGLError(String text) {
        int[] aint1 = ShaderOption.lz();
        int i = GL11.glGetError();
        int[] aint = aint1;
        if (i != 0) {

            if (i == 1286) {
                int j = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
                System.err.format(
                        "GL error 0x%04X: %s (Fb status 0x%04X) at %s\n",
                        i, GLU.gluErrorString(i), j, text);
                if (aint == null) {
                    return i;
                }
            }

            System.err.format("GL error 0x%04X: %s at %s\n", i, GLU.gluErrorString(i), text);
        }

        return i;
    }

    public static void setProgramUniform1i(String text, int p171_1) {
        int j = shadersmod.client.Shaders.programsID[shadersmod.client.Shaders.activeProgram];
        if (j != 0) {
            int k = org.lwjgl.opengl.ARBShaderObjects.glGetUniformLocationARB(j, text);
            org.lwjgl.opengl.ARBShaderObjects.glUniform1iARB(k, p171_1);
            shadersmod.client.Shaders.checkGLError(
                    shadersmod.client.Shaders.programNames[shadersmod.client.Shaders.activeProgram],
                    text);
        }
    }

    private static int u(String text) {
        int[] aint1 = ShaderOption.lz();
        int i = ARBShaderObjects.glCreateShaderObjectARB(35632);
        int[] aint = aint1;
        if (i == 0) {
            return 0;
        }

        StringBuilder stringbuilder = new StringBuilder(131072);
        BufferedReader bufferedreader = null;

        try {
            bufferedreader =
                    new BufferedReader(
                            new InputStreamReader(
                                    shadersmod.client.Shaders.shaderPack.getResourceAsStream(
                                            text)));
        } catch (Exception exception1) {
            try {
                bufferedreader = new BufferedReader(new FileReader(new File(text)));
            } catch (Exception exception) {
                ARBShaderObjects.glDeleteObjectARB(i);
                return 0;
            }
        }

        ShaderOption[] shaderOptionArray =
                getChangedOptions(shadersmod.client.Shaders.shaderPackOptions);
        if (bufferedreader != null) {
            try {
                bufferedreader =
                        ShaderPackParser.method_6fa68e5662b8e255afce(
                                bufferedreader, text, shadersmod.client.Shaders.shaderPack, 0);

                String textValue;
                while ((textValue = bufferedreader.readLine()) != null) {
                    textValue = applyOptions(textValue, shaderOptionArray);
                    stringbuilder.append(textValue).append('\n');
                    if (!textValue.matches("#version .*")) {
                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ shadow;.*")) {
                            if (shadersmod.client.Shaders.usedShadowDepthBuffers >= 1) {
                                continue;
                            }

                            shadersmod.client.Shaders.usedShadowDepthBuffers = 1;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ watershadow;.*")) {
                            shadersmod.client.Shaders.waterShadowEnabled = true;
                            if (shadersmod.client.Shaders.usedShadowDepthBuffers >= 2) {
                                continue;
                            }

                            shadersmod.client.Shaders.usedShadowDepthBuffers = 2;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ shadowtex0;.*")) {
                            if (shadersmod.client.Shaders.usedShadowDepthBuffers >= 1) {
                                continue;
                            }

                            shadersmod.client.Shaders.usedShadowDepthBuffers = 1;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ shadowtex1;.*")) {
                            if (shadersmod.client.Shaders.usedShadowDepthBuffers >= 2) {
                                continue;
                            }

                            shadersmod.client.Shaders.usedShadowDepthBuffers = 2;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ shadowcolor;.*")) {
                            if (shadersmod.client.Shaders.usedShadowColorBuffers >= 1) {
                                continue;
                            }

                            shadersmod.client.Shaders.usedShadowColorBuffers = 1;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ shadowcolor0;.*")) {
                            if (shadersmod.client.Shaders.usedShadowColorBuffers >= 1) {
                                continue;
                            }

                            shadersmod.client.Shaders.usedShadowColorBuffers = 1;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ shadowcolor1;.*")) {
                            if (shadersmod.client.Shaders.usedShadowColorBuffers >= 2) {
                                continue;
                            }

                            shadersmod.client.Shaders.usedShadowColorBuffers = 2;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ shadowcolor2;.*")) {
                            if (shadersmod.client.Shaders.usedShadowColorBuffers >= 3) {
                                continue;
                            }

                            shadersmod.client.Shaders.usedShadowColorBuffers = 3;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ shadowcolor3;.*")) {
                            if (shadersmod.client.Shaders.usedShadowColorBuffers >= 4) {
                                continue;
                            }

                            shadersmod.client.Shaders.usedShadowColorBuffers = 4;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ depthtex0;.*")) {
                            if (shadersmod.client.Shaders.usedDepthBuffers >= 1) {
                                continue;
                            }

                            shadersmod.client.Shaders.usedDepthBuffers = 1;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ depthtex1;.*")) {
                            if (shadersmod.client.Shaders.usedDepthBuffers >= 2) {
                                continue;
                            }

                            shadersmod.client.Shaders.usedDepthBuffers = 2;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ depthtex2;.*")) {
                            if (shadersmod.client.Shaders.usedDepthBuffers >= 3) {
                                continue;
                            }

                            shadersmod.client.Shaders.usedDepthBuffers = 3;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (textValue.matches("uniform [ _a-zA-Z0-9]+ gdepth;.*")) {
                            if (shadersmod.client.Shaders.gbuffersFormat[1] != 6408) {
                                continue;
                            }

                            shadersmod.client.Shaders.gbuffersFormat[1] = 34836;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (shadersmod.client.Shaders.usedColorBuffers < 5
                                && textValue.matches("uniform [ _a-zA-Z0-9]+ gaux1;.*")) {
                            shadersmod.client.Shaders.usedColorBuffers = 5;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (shadersmod.client.Shaders.usedColorBuffers < 6
                                && textValue.matches("uniform [ _a-zA-Z0-9]+ gaux2;.*")) {
                            shadersmod.client.Shaders.usedColorBuffers = 6;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (shadersmod.client.Shaders.usedColorBuffers < 7
                                && textValue.matches("uniform [ _a-zA-Z0-9]+ gaux3;.*")) {
                            shadersmod.client.Shaders.usedColorBuffers = 7;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (shadersmod.client.Shaders.usedColorBuffers < 8
                                && textValue.matches("uniform [ _a-zA-Z0-9]+ gaux4;.*")) {
                            shadersmod.client.Shaders.usedColorBuffers = 8;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (shadersmod.client.Shaders.usedColorBuffers < 5
                                && textValue.matches("uniform [ _a-zA-Z0-9]+ colortex4;.*")) {
                            shadersmod.client.Shaders.usedColorBuffers = 5;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (shadersmod.client.Shaders.usedColorBuffers < 6
                                && textValue.matches("uniform [ _a-zA-Z0-9]+ colortex5;.*")) {
                            shadersmod.client.Shaders.usedColorBuffers = 6;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (shadersmod.client.Shaders.usedColorBuffers < 7
                                && textValue.matches("uniform [ _a-zA-Z0-9]+ colortex6;.*")) {
                            shadersmod.client.Shaders.usedColorBuffers = 7;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (shadersmod.client.Shaders.usedColorBuffers < 8
                                && textValue.matches("uniform [ _a-zA-Z0-9]+ colortex7;.*")) {
                            shadersmod.client.Shaders.usedColorBuffers = 8;
                            if (aint == null) {
                                continue;
                            }
                        }

                        if (shadersmod.client.Shaders.usedColorBuffers < 8
                                && textValue.matches(
                                        "uniform [ _a-zA-Z0-9]+ centerDepthSmooth;.*")) {
                            shadersmod.client.Shaders.centerDepthSmoothEnabled = true;
                            if (aint == null) {
                                continue;
                            }
                        }

                        label539:
                        {
                            if (textValue.matches("/\\* SHADOWRES:[0-9]+ \\*/.*")) {
                                String[] astring = textValue.split("(:| )", 4);
                                ShaderLog.info("Shadow map resolution: " + astring[2]);
                                shadersmod.client.Shaders.spShadowMapWidth =
                                        shadersmod.client.Shaders.spShadowMapHeight =
                                                Integer.parseInt(astring[2]);
                                shadersmod.client.Shaders.shadowMapWidth =
                                        shadersmod.client.Shaders.shadowMapHeight =
                                                Math.round(
                                                        shadersmod.client.Shaders.spShadowMapWidth
                                                                * shadersmod.client.Shaders
                                                                        .configShadowResMul);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*int[ \t]*shadowMapResolution[ \t]*=[ "
                                            + "\t]*-?[0-9.]+f?;.*")) {
                                String[] astring1 = textValue.split("(=[ \t]*|;)");
                                ShaderLog.info("Shadow map resolution: " + astring1[1]);
                                shadersmod.client.Shaders.spShadowMapWidth =
                                        shadersmod.client.Shaders.spShadowMapHeight =
                                                Integer.parseInt(astring1[1]);
                                shadersmod.client.Shaders.shadowMapWidth =
                                        shadersmod.client.Shaders.shadowMapHeight =
                                                Math.round(
                                                        shadersmod.client.Shaders.spShadowMapWidth
                                                                * shadersmod.client.Shaders
                                                                        .configShadowResMul);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches("/\\* SHADOWFOV:[0-9\\.]+ \\*/.*")) {
                                String[] astring2 = textValue.split("(:| )", 4);
                                ShaderLog.info("Shadow map field of view: " + astring2[2]);
                                shadersmod.client.Shaders.shadowMapFOV =
                                        Float.parseFloat(astring2[2]);
                                shadersmod.client.Shaders.shadowMapIsOrtho = false;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches("/\\* SHADOWHPL:[0-9\\.]+ \\*/.*")) {
                                String[] astring3 = textValue.split("(:| )", 4);
                                ShaderLog.info("Shadow map half-plane: " + astring3[2]);
                                shadersmod.client.Shaders.shadowMapHalfPlane =
                                        Float.parseFloat(astring3[2]);
                                shadersmod.client.Shaders.shadowMapIsOrtho = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*float[ \t]*shadowDistance[ \t]*=[ "
                                            + "\t]*-?[0-9.]+f?;.*")) {
                                String[] astring4 = textValue.split("(=[ \t]*|;)");
                                ShaderLog.info("Shadow map distance: " + astring4[1]);
                                shadersmod.client.Shaders.shadowMapHalfPlane =
                                        Float.parseFloat(astring4[1]);
                                shadersmod.client.Shaders.shadowMapIsOrtho = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*float[ \t]*shadowIntervalSize[ \t]*=[ "
                                            + "\t]*-?[0-9.]+f?;.*")) {
                                String[] astring5 = textValue.split("(=[ \t]*|;)");
                                ShaderLog.info("Shadow map interval size: " + astring5[1]);
                                shadersmod.client.Shaders.shadowIntervalSize =
                                        Float.parseFloat(astring5[1]);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ \t]*generateShadowMipmap[ \t]*=[ "
                                            + "\t]*true[ \t]*;.*")) {
                                ShaderLog.info("Generate shadow mipmap");
                                Arrays.fill(shadersmod.client.Shaders.shadowMipmapEnabled, true);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ \t]*generateShadowColorMipmap[ \t]*=[ "
                                            + "\t]*true[ \t]*;.*")) {
                                ShaderLog.info("Generate shadow color mipmap");
                                Arrays.fill(
                                        shadersmod.client.Shaders.shadowColorMipmapEnabled, true);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering[ \t]*=[ "
                                            + "\t]*true[ \t]*;.*")) {
                                ShaderLog.info("Hardware shadow filtering enabled.");
                                Arrays.fill(
                                        shadersmod.client.Shaders.shadowHardwareFilteringEnabled,
                                        true);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering0[ \t]*=[ "
                                            + "\t]*true[ \t]*;.*")) {
                                ShaderLog.info("shadowHardwareFiltering0");
                                shadersmod.client.Shaders.shadowHardwareFilteringEnabled[0] = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering1[ \t]*=[ "
                                            + "\t]*true[ \t]*;.*")) {
                                ShaderLog.info("shadowHardwareFiltering1");
                                shadersmod.client.Shaders.shadowHardwareFilteringEnabled[1] = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ \t]*(shadowtex0Mipmap|shadowtexMipmap)["
                                            + " \t]*=[ \t]*true[ \t]*;.*")) {
                                ShaderLog.info("shadowtex0Mipmap");
                                shadersmod.client.Shaders.shadowMipmapEnabled[0] = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ \t]*(shadowtex1Mipmap)[ \t]*=[ "
                                            + "\t]*true[ \t]*;.*")) {
                                ShaderLog.info("shadowtex1Mipmap");
                                shadersmod.client.Shaders.shadowMipmapEnabled[1] = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ "
                                            + "\t]*(shadowcolor0Mipmap|shadowColor0Mipmap)[ \t]*=[ "
                                            + "\t]*true[ \t]*;.*")) {
                                ShaderLog.info("shadowcolor0Mipmap");
                                shadersmod.client.Shaders.shadowColorMipmapEnabled[0] = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ "
                                            + "\t]*(shadowcolor1Mipmap|shadowColor1Mipmap)[ \t]*=[ "
                                            + "\t]*true[ \t]*;.*")) {
                                ShaderLog.info("shadowcolor1Mipmap");
                                shadersmod.client.Shaders.shadowColorMipmapEnabled[1] = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ "
                                        + "\t]*(shadowtex0Nearest|shadowtexNearest|shadow0MinMagNearest)["
                                        + " \t]*=[ \t]*true[ \t]*;.*")) {
                                ShaderLog.info("shadowtex0Nearest");
                                shadersmod.client.Shaders.shadowFilterNearest[0] = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ "
                                        + "\t]*(shadowtex1Nearest|shadow1MinMagNearest)[ \t]*=[ "
                                        + "\t]*true[ \t]*;.*")) {
                                ShaderLog.info("shadowtex1Nearest");
                                shadersmod.client.Shaders.shadowFilterNearest[1] = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ "
                                        + "\t]*(shadowcolor0Nearest|shadowColor0Nearest|shadowColor0MinMagNearest)["
                                        + " \t]*=[ \t]*true[ \t]*;.*")) {
                                ShaderLog.info("shadowcolor0Nearest");
                                shadersmod.client.Shaders.shadowColorFilterNearest[0] = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ "
                                        + "\t]*(shadowcolor1Nearest|shadowColor1Nearest|shadowColor1MinMagNearest)["
                                        + " \t]*=[ \t]*true[ \t]*;.*")) {
                                ShaderLog.info("shadowcolor1Nearest");
                                shadersmod.client.Shaders.shadowColorFilterNearest[1] = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches("/\\* WETNESSHL:[0-9\\.]+ \\*/.*")) {
                                String[] astring6 = textValue.split("(:| )", 4);
                                ShaderLog.info("Wetness halflife: " + astring6[2]);
                                shadersmod.client.Shaders.wetnessHalfLife =
                                        Float.parseFloat(astring6[2]);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*float[ \t]*wetnessHalflife[ \t]*=[ "
                                            + "\t]*-?[0-9.]+f?;.*")) {
                                String[] astring7 = textValue.split("(=[ \t]*|;)");
                                ShaderLog.info("Wetness halflife: " + astring7[1]);
                                shadersmod.client.Shaders.wetnessHalfLife =
                                        Float.parseFloat(astring7[1]);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches("/\\* DRYNESSHL:[0-9\\.]+ \\*/.*")) {
                                String[] astring8 = textValue.split("(:| )", 4);
                                ShaderLog.info("Dryness halflife: " + astring8[2]);
                                shadersmod.client.Shaders.drynessHalfLife =
                                        Float.parseFloat(astring8[2]);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*float[ \t]*drynessHalflife[ \t]*=[ "
                                            + "\t]*-?[0-9.]+f?;.*")) {
                                String[] astring9 = textValue.split("(=[ \t]*|;)");
                                ShaderLog.info("Dryness halflife: " + astring9[1]);
                                shadersmod.client.Shaders.drynessHalfLife =
                                        Float.parseFloat(astring9[1]);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*float[ \t]*eyeBrightnessHalflife[ \t]*=[ "
                                            + "\t]*-?[0-9.]+f?;.*")) {
                                String[] astring10 = textValue.split("(=[ \t]*|;)");
                                ShaderLog.info("Eye brightness halflife: " + astring10[1]);
                                shadersmod.client.Shaders.eyeBrightnessHalflife =
                                        Float.parseFloat(astring10[1]);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*float[ \t]*centerDepthHalflife[ \t]*=[ "
                                            + "\t]*-?[0-9.]+f?;.*")) {
                                String[] astring11 = textValue.split("(=[ \t]*|;)");
                                ShaderLog.info("Center depth halflife: " + astring11[1]);
                                shadersmod.client.Shaders.centerDepthSmoothHalflife =
                                        Float.parseFloat(astring11[1]);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*float[ \t]*sunPathRotation[ \t]*=[ "
                                            + "\t]*-?[0-9.]+f?;.*")) {
                                String[] astring12 = textValue.split("(=[ \t]*|;)");
                                ShaderLog.info("Sun path rotation: " + astring12[1]);
                                shadersmod.client.Shaders.sunPathRotation =
                                        Float.parseFloat(astring12[1]);
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*float[ \t]*ambientOcclusionLevel[ \t]*=[ "
                                            + "\t]*-?[0-9.]+f?;.*")) {
                                String[] astring13 = textValue.split("(=[ \t]*|;)");
                                ShaderLog.info("AO Level: " + astring13[1]);
                                MP = Float.parseFloat(astring13[1]);
                                shadersmod.client.Shaders.aoLevel = 1.0F - MP;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*int[ \t]*superSamplingLevel[ \t]*=[ "
                                            + "\t]*-?[0-9.]+f?;.*")) {
                                {
                                    String[] astring14 = textValue.split("(=[ \t]*|;)");
                                    int j = Integer.parseInt(astring14[1]);
                                    if (j > 1) {
                                        ShaderLog.info(
                                                "Super sampling level: "
                                                        + j
                                                        + packedTextFragments[17]);
                                        shadersmod.client.Shaders.superSamplingLevel = j;
                                        if (!(aint == null)) {

                                            shadersmod.client.Shaders.superSamplingLevel = 1;
                                        }
                                    } else {

                                        shadersmod.client.Shaders.superSamplingLevel = 1;
                                    }
                                }

                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*int[ \t]*noiseTextureResolution[ \t]*=[ "
                                            + "\t]*-?[0-9.]+f?;.*")) {
                                String[] astring15 = textValue.split("(=[ \t]*|;)");
                                ShaderLog.info("Noise texture enabled");
                                ShaderLog.info("Noise texture resolution: " + astring15[1]);
                                shadersmod.client.Shaders.noiseTextureResolution =
                                        Integer.parseInt(astring15[1]);
                                shadersmod.client.Shaders.noiseTextureEnabled = true;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*int[ \t]*\\w+Format[ \t]*=[ "
                                            + "\t]*[RGBA81632FUI_SNORM]*[ \t]*;.*")) {
                                Matcher matcher = Nh.matcher(textValue);
                                matcher.matches();
                                String textValue2 = matcher.group(1);
                                String textValue3 = matcher.group(2);
                                int k = getBufferIndexFromString(textValue2);
                                int l = K(textValue3);
                                if (k >= 0 && l != 0) {
                                    shadersmod.client.Shaders.gbuffersFormat[k] = l;
                                    ShaderLog.method_940da8a8e278fb8fbea3(
                                            "%s format: %s", new Object[] {textValue2, textValue3});
                                }

                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches("/\\* GAUX4FORMAT:RGBA32F \\*/.*")) {
                                ShaderLog.info("gaux4 format : RGB32AF");
                                shadersmod.client.Shaders.gbuffersFormat[7] = 34836;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches("/\\* GAUX4FORMAT:RGB32F \\*/.*")) {
                                ShaderLog.info("gaux4 format : RGB32F");
                                shadersmod.client.Shaders.gbuffersFormat[7] = 34837;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches("/\\* GAUX4FORMAT:RGB16 \\*/.*")) {
                                ShaderLog.info("gaux4 format : RGB16");
                                shadersmod.client.Shaders.gbuffersFormat[7] = 32852;
                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches(
                                    "[ \t]*const[ \t]*bool[ \t]*\\w+MipmapEnabled[ \t]*=[ \t]*true["
                                            + " \t]*;.*")) {
                                if (!text.matches(".*composite[0-9]?.fsh")
                                        && !text.matches(".*final.fsh")) {
                                    break label539;
                                }

                                Matcher matcher1 = Px.matcher(textValue);
                                matcher1.matches();
                                String textValue4 = matcher1.group(1);
                                int bufferIndexFromString = getBufferIndexFromString(textValue4);
                                if (bufferIndexFromString >= 0) {
                                    shadersmod.client.Shaders.newCompositeMipmapSetting |=
                                            1 << bufferIndexFromString;
                                    ShaderLog.method_940da8a8e278fb8fbea3(
                                            "%s mipmap enabled for %s",
                                            new Object[] {textValue4, text});
                                }

                                if (aint == null) {
                                    break label539;
                                }
                            }

                            if (textValue.matches("/\\* DRAWBUFFERS:[0-7N]* \\*/.*")) {
                                String[] astring16 = textValue.split("(:| )", 4);
                                shadersmod.client.Shaders.newDrawBufSetting = astring16[2];
                            }
                        }

                        if (aint != null) {
                            break;
                        }
                    }
                }

                bufferedreader.close();
            } catch (Exception exception2) {
                ShaderLog.severe("Couldn't read " + text + packedTextFragments[18]);
                exception2.printStackTrace();
                ARBShaderObjects.glDeleteObjectARB(i);
                return 0;
            }
        }

        if (shadersmod.client.Shaders.saveFinalShaders) {
            saveShader(text, stringbuilder.toString());
        }

        ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
        ARBShaderObjects.glCompileShaderARB(i);
        if (GL20.glGetShaderi(i, 35713) != 1) {
            ShaderLog.severe("Error compiling fragment shader: " + text);
        }

        method_c11931953a5ac19e9b4d(i, text);
        return i;
    }

    public static boolean shouldRenderClouds(GameSettings gameSettings) {
        if (!shadersmod.client.Shaders.shaderPackLoaded) {
            return true;
        }

        checkGLError("shouldRenderClouds");
        return shadersmod.client.Shaders.isShadowPass
                ? shadersmod.client.Shaders.configCloudShadow
                : gameSettings.clouds;
    }

    private static void resize() {
        shadersmod.client.Shaders.renderDisplayWidth = shadersmod.client.Shaders.mc.displayWidth;
        shadersmod.client.Shaders.renderDisplayHeight = shadersmod.client.Shaders.mc.displayHeight;
        shadersmod.client.Shaders.renderWidth =
                java.lang.Math.round(
                        shadersmod.client.Shaders.renderDisplayWidth
                                * shadersmod.client.Shaders.configRenderResMul);
        shadersmod.client.Shaders.renderHeight =
                java.lang.Math.round(
                        shadersmod.client.Shaders.renderDisplayHeight
                                * shadersmod.client.Shaders.configRenderResMul);
        shadersmod.client.Shaders.setupFrameBuffer();
    }

    public static void drawComposite() {
        org.lwjgl.opengl.GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        org.lwjgl.opengl.GL11.glBegin(7);
        org.lwjgl.opengl.GL11.glTexCoord2f(0.0F, 0.0F);
        org.lwjgl.opengl.GL11.glVertex3f(0.0F, 0.0F, 0.0F);
        org.lwjgl.opengl.GL11.glTexCoord2f(1.0F, 0.0F);
        org.lwjgl.opengl.GL11.glVertex3f(1.0F, 0.0F, 0.0F);
        org.lwjgl.opengl.GL11.glTexCoord2f(1.0F, 1.0F);
        org.lwjgl.opengl.GL11.glVertex3f(1.0F, 1.0F, 0.0F);
        org.lwjgl.opengl.GL11.glTexCoord2f(0.0F, 1.0F);
        org.lwjgl.opengl.GL11.glVertex3f(0.0F, 1.0F, 0.0F);
        org.lwjgl.opengl.GL11.glEnd();
    }

    public static void endWeather() {
        GlStateManager.disableBlend();
        useProgram(3);
    }

    public static void beginHand() {
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5888);
        useProgram(19);
        checkGLError("beginHand");
        checkFramebufferStatus("beginHand");
    }

    private static String applyOptions(String text, ShaderOption[] shaderOptionArray) {
        int[] aint = ShaderOption.lz();
        if (shaderOptionArray != null && shaderOptionArray.length > 0) {
            int i = 0;

            while (i < shaderOptionArray.length) {
                ShaderOption shaderOption = shaderOptionArray[i];
                String textValue = shaderOption.getName();
                if (shaderOption.matchesLine(text)) {
                    text = shaderOption.getSourceLine();
                    if (aint == null) {
                        break;
                    }
                }

                i++;
                if (aint != null) {
                    break;
                }
            }

            return text;
        } else {
            return text;
        }
    }

    public static void setEntityColor(float p171_0, float p171_1, float p171_2, float p171_3) {
        if (shadersmod.client.Shaders.isRenderingWorld && !shadersmod.client.Shaders.isShadowPass) {
            shadersmod.client.Shaders.uniformEntityColor.setValue(p171_0, p171_1, p171_2, p171_3);
        }
    }

    public static void endHand() {
        checkGLError("pre endHand");
        checkFramebufferStatus("pre endHand");
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GlStateManager.blendFunc(770, 771);
        checkGLError("endHand");
    }

    private static void saveOptionProperties(IShaderPack iShaderPack, Properties properties)
            throws IOException {
        int[] aint1 = ShaderOption.lz();
        String text =
                shadersmod.client.Shaders.shaderpacksdirname
                        + packedTextFragments[4]
                        + iShaderPack.getName()
                        + ".txt";
        File file1 = new File(MinecraftClient.getMinecraft().gameDirectory, text);
        int[] aint = aint1;
        if (properties.isEmpty()) {
            file1.delete();
            if (aint == null) {
                return;
            }
        }

        FileOutputStream fileoutputstream = new FileOutputStream(file1);
        properties.store(fileoutputstream, (String) null);
        fileoutputstream.flush();
        fileoutputstream.close();
    }

    public static void nR() {
        useProgram(3);
    }

    public static int checkGLError(String text, String textValue) {
        int i = GL11.glGetError();
        if (i != 0) {
            System.err.format(
                    "GL error 0x%04x: %s at %s %s\n", i, GLU.gluErrorString(i), text, textValue);
        }

        return i;
    }

    public static void nS() {
        int[] aint = ShaderOption.lz();
        if (shadersmod.client.Shaders.hasGlGenMipmap) {
            int i = 0;

            while (i < shadersmod.client.Shaders.usedColorBuffers) {
                if ((shadersmod.client.Shaders.activeCompositeMipmapSetting & 1 << i) != 0) {
                    GlStateManager.setActiveTexture(
                            33984 + shadersmod.client.Shaders.colorTextureTextureImageUnit[i]);
                    GL11.glTexParameteri(3553, 10241, 9987);
                    GL30.glGenerateMipmap(3553);
                }

                i++;
                if (aint != null) {
                    break;
                }
            }

            GlStateManager.setActiveTexture(33984);
        }
    }

    public static void pushEntity(Block block) {
        shadersmod.client.Shaders.entityDataIndex++;
        shadersmod.client.Shaders.entityData[shadersmod.client.Shaders.entityDataIndex * 2] =
                net.minecraft.block.Block.blockRegistry.getIDForObject(block) & 65535
                        | block.getRenderType() << 16;
        shadersmod.client.Shaders.entityData[shadersmod.client.Shaders.entityDataIndex * 2 + 1] = 0;
    }

    private static void loadShaderPackResources() {
        int[] aint1 = ShaderOption.lz();
        shadersmod.client.Shaders.shaderPackResources = new HashMap<>();
        int[] aint = aint1;
        if (shadersmod.client.Shaders.shaderPackLoaded) {
            ArrayList arrayList = new ArrayList();
            String text = "/shaders/lang/";
            String textValue = "en_US";
            String textValue2 = ".lang";
            arrayList.add(text + textValue + textValue2);
            if (!OptiFineConfig.getGameSettings().language.equals(textValue)) {
                arrayList.add(text + OptiFineConfig.getGameSettings().language + textValue2);
            }

            try {
                for (java.util.Iterator<?> __recoveryIterator171_236458 = (arrayList).iterator();
                        (__recoveryIterator171_236458).hasNext(); ) {
                    String textValue3 = (String) (__recoveryIterator171_236458).next();
                    InputStream inputstream =
                            shadersmod.client.Shaders.shaderPack.getResourceAsStream(textValue3);
                    if (inputstream != null) {
                        Properties properties = new Properties();
                        Lang.loadLocaleData(inputstream, properties);
                        inputstream.close();

                        for (java.util.Iterator<?> __recoveryIterator171_236805 =
                                        (properties.keySet()).iterator();
                                (__recoveryIterator171_236805).hasNext(); ) {
                            String textValue4 = (String) (__recoveryIterator171_236805).next();
                            String property = properties.getProperty(textValue4);
                            shadersmod.client.Shaders.shaderPackResources.put(textValue4, property);
                            if (aint != null) {
                                break;
                            }
                        }
                    }

                    if (aint != null) {
                        break;
                    }
                }
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
        }
    }

    public static void saveShaderPackOptions() {
        saveShaderPackOptions(
                shadersmod.client.Shaders.shaderPackOptions, shadersmod.client.Shaders.shaderPack);
    }

    public static void beginSky() {
        shadersmod.client.Shaders.isRenderingSky = true;
        shadersmod.client.Shaders.fogEnabled = true;
        shadersmod.client.Shaders.setDrawBuffers(shadersmod.client.Shaders.dfbDrawBuffers);
        shadersmod.client.Shaders.useProgram(5);
        shadersmod.client.Shaders.pushEntity(-2, 0);
    }

    public static ShaderOption[] getShaderPackOptions() {
        return shadersmod.client.Shaders.shaderPackOptions;
    }

    public static void postCelestialRotate() {
        FloatBuffer floatbuffer = shadersmod.client.Shaders.tempMatrixDirectBuffer;
        ((Buffer) floatbuffer).clear();
        GL11.glGetFloat(2982, floatbuffer);
        floatbuffer.get(shadersmod.client.Shaders.tempMat, 0, 16);
        ShaderMath.multiplyMat4xVec4(
                shadersmod.client.Shaders.sunPosition,
                shadersmod.client.Shaders.tempMat,
                shadersmod.client.Shaders.sunPosModelView);
        ShaderMath.multiplyMat4xVec4(
                shadersmod.client.Shaders.moonPosition,
                shadersmod.client.Shaders.tempMat,
                shadersmod.client.Shaders.moonPosModelView);
        System.arraycopy(
                shadersmod.client.Shaders.shadowAngle == shadersmod.client.Shaders.sunAngle
                        ? shadersmod.client.Shaders.sunPosition
                        : shadersmod.client.Shaders.moonPosition,
                0,
                shadersmod.client.Shaders.shadowLightPosition,
                0,
                3);
        checkGLError("postCelestialRotate");
    }

    public static void beginFPOverlay() {
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    public static void pushEntity(int p171_0) {
        shadersmod.client.Shaders.entityDataIndex++;
        shadersmod.client.Shaders.entityData[shadersmod.client.Shaders.entityDataIndex * 2] =
                p171_0 & 65535;
        shadersmod.client.Shaders.entityData[shadersmod.client.Shaders.entityDataIndex * 2 + 1] = 0;
    }

    public static void nY() {
        shadersmod.client.Shaders.lightmapEnabled = false;
        if (shadersmod.client.Shaders.activeProgram == 3) {
            useProgram(ZeroArgReflectiveInstantiator.cz - 10);
        }
    }

    public static boolean isOldLighting() {
        return !shadersmod.client.Shaders.configOldHandLight.isDefault()
                ? shadersmod.client.Shaders.configOldHandLight.isTrue()
                : (!shadersmod.client.Shaders.shaderPackOldLighting.isDefault()
                        ? shadersmod.client.Shaders.shaderPackOldLighting.isTrue()
                        : true);
    }

    public static void setCamera(float f) {
        Entity entity = shadersmod.client.Shaders.mc.getRenderViewEntity();
        double cameraPositionXValue = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * f;
        double cameraPositionYValue = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * f;
        double cameraPositionZValue = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * f;
        shadersmod.client.Shaders.cameraPositionX = cameraPositionXValue;
        shadersmod.client.Shaders.cameraPositionY = cameraPositionYValue;
        shadersmod.client.Shaders.cameraPositionZ = cameraPositionZValue;
        GL11.glGetFloat(
                2983, (FloatBuffer) ((Buffer) shadersmod.client.Shaders.projection).position(0));
        ShaderMath.invertMat4FBFA(
                (FloatBuffer) ((Buffer) shadersmod.client.Shaders.projectionInverse).position(0),
                (FloatBuffer) ((Buffer) shadersmod.client.Shaders.projection).position(0),
                shadersmod.client.Shaders.faProjectionInverse,
                shadersmod.client.Shaders.faProjection);
        ((Buffer) shadersmod.client.Shaders.projection).position(0);
        ((Buffer) shadersmod.client.Shaders.projectionInverse).position(0);
        GL11.glGetFloat(
                2982, (FloatBuffer) ((Buffer) shadersmod.client.Shaders.modelView).position(0));
        ShaderMath.invertMat4FBFA(
                (FloatBuffer) ((Buffer) shadersmod.client.Shaders.modelViewInverse).position(0),
                (FloatBuffer) ((Buffer) shadersmod.client.Shaders.modelView).position(0),
                shadersmod.client.Shaders.faModelViewInverse,
                shadersmod.client.Shaders.faModelView);
        ((Buffer) shadersmod.client.Shaders.modelView).position(0);
        ((Buffer) shadersmod.client.Shaders.modelViewInverse).position(0);
        checkGLError("setCamera");
    }

    public static String getShaderPackName() {
        try {
            if (shadersmod.client.Shaders.shaderPack == null) {
                return null;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception1));
        }

        try {
            if (shadersmod.client.Shaders.shaderPack instanceof shadersmod.client.ShaderPackNone) {
                return null;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }

        return shadersmod.client.Shaders.shaderPack.getName();
    }

    public static boolean isDynamicHandLight() {
        return !shadersmod.client.Shaders.shaderPackDynamicHandLight.isDefault()
                ? shadersmod.client.Shaders.shaderPackDynamicHandLight.isTrue()
                : true;
    }

    public static ShaderOption getShaderOption(String text) {
        return ShaderUtils.getShaderOption(text, shadersmod.client.Shaders.shaderPackOptions);
    }

    public static void preWater() {
        if (shadersmod.client.Shaders.usedDepthBuffers >= 2) {
            GlStateManager.setActiveTexture(33995);
            checkGLError("pre copy depth");
            GL11.glCopyTexSubImage2D(
                    3553,
                    0,
                    0,
                    0,
                    0,
                    0,
                    shadersmod.client.Shaders.renderWidth,
                    shadersmod.client.Shaders.renderHeight);
            checkGLError("copy depth");
            GlStateManager.setActiveTexture(33984);
        }

        ShadersTex.bindNSTextures(shadersmod.client.Shaders.defaultTexture.getMultiTexID());
    }

    public static void setProgramUniformMatrix4ARB(
            String text, boolean p171_1, FloatBuffer floatBuffer) {
        int i = shadersmod.client.Shaders.programsID[shadersmod.client.Shaders.activeProgram];

        try {
            if (i == 0 || floatBuffer == null) {
                return;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    shadersmod.client.Shaders.a(illegalargumentexception));
        }

        int j = org.lwjgl.opengl.ARBShaderObjects.glGetUniformLocationARB(i, text);
        org.lwjgl.opengl.ARBShaderObjects.glUniformMatrix4ARB(j, p171_1, floatBuffer);
        shadersmod.client.Shaders.checkGLError(
                shadersmod.client.Shaders.programNames[shadersmod.client.Shaders.activeProgram],
                text);
    }
}
