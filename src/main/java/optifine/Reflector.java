package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class Reflector {
    public static ReflectorClass FR;
    public static ReflectorMethod FS;
    public static ReflectorClass FT;
    public static ReflectorMethod FU;
    public static ReflectorConstructor FV;
    public static ReflectorClass FW;
    public static ReflectorMethod ForgeBlock_hasTileEntity;
    public static ReflectorField FY;
    public static ReflectorField forgeFogColorRed;
    public static ReflectorClass Ga;
    public static ReflectorMethod ForgeItem_showDurabilityBar;
    public static ReflectorClass Gc;
    public static ReflectorMethod Gd;
    public static ReflectorMethod Ge;
    public static ReflectorMethod Gf;
    public static ReflectorMethod Gg;
    public static ReflectorClass forgeRenderLivingPostClass;
    public static ReflectorMethod Gi;
    public static ReflectorMethod forgeTileGetRenderBoundingBox;
    public static ReflectorClass Gk;
    public static ReflectorMethod Gl;
    public static ReflectorConstructor Gm;
    public static ReflectorMethod Gn;
    public static ReflectorClass Go;
    public static ReflectorMethod forgeGetOffsetFov;
    public static ReflectorField Gq;
    public static ReflectorClass forgeDimensionManager;
    public static ReflectorMethod Gs;
    public static ReflectorMethod Gt;
    public static ReflectorMethod Gu;
    public static ReflectorClass Gv;
    public static ReflectorMethod Gw;
    public static ReflectorMethod Gx;
    public static ReflectorClass Gy;
    public static ReflectorMethod Gz;
    public static ReflectorMethod ForgeHooksClient_getMatrix;

    public static ReflectorMethod forgeDrawScreen;
    public static ReflectorMethod GC;
    public static ReflectorClass GD;
    public static ReflectorClass MinecraftForgeClient;
    public static ReflectorMethod forgeSetRenderLayer;
    public static ReflectorMethod GG;
    public static ReflectorClass GH;
    public static ReflectorMethod forgeDrawBlockHighlight;
    public static ReflectorMethod ForgeHooksClient_getArmorTexture;
    public static ReflectorClass LightUtil;
    public static ReflectorClass GL;
    public static ReflectorMethod EventBus_post;
    public static ReflectorField forgeCameraSetupYaw;
    public static ReflectorField Attributes_DEFAULT_BAKED_FORMAT;
    public static ReflectorMethod forgeTileShouldRenderInPass;
    public static ReflectorConstructor WorldEvent_Load_Constructor;
    public static ReflectorField GR;
    public static ReflectorField GS;
    public static ReflectorMethod ForgeHooksClient_setRenderPass;
    public static ReflectorConstructor forgeRenderLivingPre;
    public static ReflectorClass GV;
    public static ReflectorClass GW;
    public static ReflectorMethod forgeGetCloudRenderer;
    public static ReflectorClass GY;
    public static ReflectorMethod forgeDispatchRenderLast;
    public static ReflectorField forgeFogColorGreen;
    public static ReflectorMethod forgeEntityShouldRenderInPass;
    public static ReflectorMethod Hc;
    public static ReflectorClass Hd;
    public static ReflectorMethod He;
    public static ReflectorField forgeCameraSetupRoll;
    public static ReflectorConstructor forgeRenderLivingSpecialsPre;
    public static ReflectorMethod Hh;
    public static ReflectorConstructor Hi;
    public static ReflectorMethod forgeRenderFirstPersonHand;
    public static ReflectorMethod forgeCanRiderInteract;
    public static ReflectorClass Hl;
    public static ReflectorMethod Hm;
    public static ReflectorClass Hn;
    public static ReflectorField OptiFineClassTransformer_instance;
    public static ReflectorMethod forgeGetSkyRenderer;
    private static String[] packedTextFragments;
    public static ReflectorMethod forgeTileCanRenderBreaking;
    public static ReflectorMethod Hs;
    public static ReflectorMethod Ht;
    public static ReflectorClass forgeEventBus;
    public static ReflectorClass Hv;
    public static ReflectorField forgeCameraSetupPitch;
    public static ReflectorMethod FMLClientHandler_trackMissingTexture;
    public static ReflectorMethod forgeOrientBedCamera;
    public static ReflectorMethod Hz;
    public static ReflectorClass HA;
    public static ReflectorClass HB;
    public static ReflectorClass HC;
    public static ReflectorField Launch_blackboard;
    public static ReflectorMethod HE;
    public static ReflectorConstructor forgeFogColorsConstructor;
    public static ReflectorMethod forgeBlockIsAir;
    public static ReflectorMethod HH;
    public static ReflectorMethod MinecraftForgeClient_getRenderPass;
    public static ReflectorClass HJ;
    public static ReflectorMethod HK;
    public static ReflectorMethod FMLClientHandler_instance;
    public static ReflectorClass HM;
    public static ReflectorMethod HN;
    public static ReflectorClass HO;
    public static ReflectorClass HP;
    public static ReflectorClass HQ;
    public static ReflectorMethod HR;
    public static ReflectorMethod HS;
    public static ReflectorMethod HT;
    public static ReflectorClass HU;
    public static ReflectorClass forgeRenderLivingPreClass;
    public static ReflectorField forgeFogColorBlue;
    public static ReflectorField LightUtil_tessellator;
    public static ReflectorClass HY;
    public static ReflectorMethod forgeCanRenderBlockInLayer;
    public static ReflectorMethod Ia;
    public static ReflectorMethod Ib;
    public static ReflectorMethod Ic;
    public static ReflectorField Id;
    public static ReflectorMethod ForgeBiome_getWaterColorMultiplier;
    public static ReflectorField MinecraftForge_EVENT_BUS;
    public static ReflectorMethod Ig;
    public static ReflectorConstructor Ih;
    public static ReflectorClass MinecraftForge;
    public static ReflectorClass Ij;
    public static ReflectorMethod Ik;
    public static ReflectorMethod Il;
    public static ReflectorMethod forgeGetFogDensity;
    public static ReflectorConstructor forgeCameraSetupConstructor;
    public static ReflectorMethod forgeTileDispatcherPreDrawBatch;
    public static ReflectorClass Ip;
    public static ReflectorClass Iq;
    public static ReflectorMethod forgeShouldCauseReequipAnimation;
    public static ReflectorField Is;
    public static ReflectorMethod It;
    public static ReflectorMethod Iu;
    public static ReflectorMethod MinecraftForgeClient_onRebuildChunk;
    public static ReflectorConstructor Iw;
    public static ReflectorClass OptiFineClassTransformer;
    public static ReflectorClass forgeCameraSetupClass;
    public static ReflectorMethod forgeGetWeatherRenderer;
    public static ReflectorMethod IA;
    public static ReflectorMethod IB;
    public static ReflectorMethod FMLClientHandler_trackBrokenTexture;
    public static ReflectorMethod forgeOnFogRender;
    public static ReflectorMethod forgeBlockAddDestroyEffects;
    public static ReflectorMethod ForgeBlock_addHitEffects;
    public static ReflectorConstructor forgeRenderLivingSpecialsPost;
    public static ReflectorClass IH;
    public static ReflectorMethod forgeTileDispatcherDrawBatch;
    public static ReflectorField LightUtil_itemConsumer;
    public static ReflectorClass IK;
    public static ReflectorMethod forgeGetStaticDimensionIds;
    public static ReflectorMethod IM;
    public static ReflectorMethod IN;
    public static ReflectorMethod forgeShouldRiderSit;
    public static ReflectorConstructor forgeRenderLivingPost;
    public static ReflectorMethod forgeRenderHandlerRender;
    public static ReflectorMethod IR;
    public static ReflectorMethod OptiFineClassTransformer_getOptiFineResource;
    public static ReflectorClass WorldEvent_Load;
    public static ReflectorMethod IU;
    public static ReflectorClass IV;
    public static ReflectorClass IW;
    public static ReflectorClass IX;

    public static ReflectorMethod IZ;
    public static ReflectorMethod Ja;
    public static ReflectorMethod Jb;
    public static ReflectorClass Jc;
    public static ReflectorMethod Jd;
    public static ReflectorClass Je;
    public static ReflectorMethod Jf;
    public static ReflectorField Jg;

    public static boolean callBoolean(ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return false;
            }

            Boolean obool = (Boolean) method.invoke(null, aobject);
            return obool;
        } catch (Throwable throwable) {
            handleException(throwable, null, reflectorMethod, aobject);
            return false;
        }
    }

    public static void setFieldValue(ReflectorField reflectorField, Object p171_1) {
        optifine.Reflector.setFieldValue(null, reflectorField, p171_1);
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public static double callDouble(ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0.0;
            }

            Double doubleValue = (Double) method.invoke(null, aobject);
            return doubleValue;
        } catch (Throwable throwable) {
            handleException(throwable, null, reflectorMethod, aobject);
            return 0.0;
        }
    }

    public static boolean postForgeBusEvent(Object p171_0) {
        try {
            if (p171_0 == null) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.Reflector.method_b7e52da193241b30710d(illegalargumentexception2));
        }

        java.lang.Object fieldValue =
                optifine.Reflector.getFieldValue(optifine.Reflector.MinecraftForge_EVENT_BUS);

        try {
            if (fieldValue == null) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.Reflector.method_b7e52da193241b30710d(illegalargumentexception1));
        }

        java.lang.Object object2 =
                optifine.Reflector.call(
                        fieldValue,
                        optifine.Reflector.EventBus_post,
                        new java.lang.Object[] {p171_0});

        try {
            if (!(object2 instanceof java.lang.Boolean)) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.Reflector.method_b7e52da193241b30710d(illegalargumentexception));
        }

        java.lang.Boolean obool = (java.lang.Boolean) object2;
        return obool;
    }

    // region String decoding support

    // endregion

    public static Field getField(Class oclass, Class oclass1) {
        int i = ReflectorMethod.cR();

        try {
            Field[] afield = oclass.getDeclaredFields();
            int j = 0;

            while (j < afield.length) {
                Field field = afield[j];
                if (field.getType() == oclass1) {
                    field.setAccessible(true);
                    return field;
                }

                j++;
                if (i == 0) {
                    break;
                }
            }

            return null;
        } catch (Exception exception) {
            return null;
        }
    }

    public static Object getFieldValue(ReflectorField reflectorField) {
        return getFieldValue(null, reflectorField);
    }

    public static Method getMethod(Class type, String text, Class[] classArray) {
        java.lang.reflect.Method[] amethod = type.getDeclaredMethods();

        for (int i = 0; i < amethod.length; i++) {
            java.lang.reflect.Method method = amethod[i];
            if (method.getName().equals(text)) {
                java.lang.Class[] aclass1 = method.getParameterTypes();

                try {
                    if (optifine.Reflector.matchesTypes(classArray, aclass1)) {
                        return method;
                    }
                } catch (java.lang.IllegalArgumentException illegalargumentexception) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(
                            optifine.Reflector.method_b7e52da193241b30710d(
                                    illegalargumentexception));
                }
            }
        }

        return null;
    }

    // region String decoding support

    // endregion

    private static void dbgFieldValue(
            boolean flag, String text, ReflectorField reflectorField, Object object) {
        String name = reflectorField.getTargetField().getDeclaringClass().getName();
        String nameValue = reflectorField.getTargetField().getName();
        String textValue = packedTextFragments[9];
        if (flag) {
            textValue = " static";
        }

        OptiFineConfig.dbg(
                text
                        + textValue
                        + packedTextFragments[10]
                        + name
                        + packedTextFragments[11]
                        + nameValue
                        + " => "
                        + object);
    }

    public static void setFieldValue(Object p171_0, ReflectorField reflectorField, Object p171_2) {
        try {
            java.lang.reflect.Field field = reflectorField.getTargetField();
            if (field == null) {
                return;
            }

            field.set(p171_0, p171_2);
        } catch (java.lang.Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static Object call(Object object, ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            return method == null ? null : method.invoke(object, aobject);
        } catch (Throwable throwable) {
            handleException(throwable, object, reflectorMethod, aobject);
            return null;
        }
    }

    public static String callString(
            Object object, ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            return method == null ? null : (String) method.invoke(object, aobject);
        } catch (Throwable throwable) {
            handleException(throwable, object, reflectorMethod, aobject);
            return null;
        }
    }

    private static Object[] getClasses(Object[] aobject) {
        if (aobject == null) {
            return new Class[0];
        }

        Class[] aclass = new Class[aobject.length];

        for (int i = 0; i < aclass.length; i++) {
            Object object = aobject[i];
            if (object != null) {
                aclass[i] = object.getClass();
            }
        }

        return aclass;
    }

    public static double callDouble(
            Object object, ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0.0;
            }

            Double doubleValue = (Double) method.invoke(object, aobject);
            return doubleValue;
        } catch (Throwable throwable) {
            handleException(throwable, object, reflectorMethod, aobject);
            return 0.0;
        }
    }

    public static float getFieldValueFloat(
            Object p171_0, ReflectorField reflectorField, float p171_2) {
        java.lang.Object fieldValue = optifine.Reflector.getFieldValue(p171_0, reflectorField);

        try {
            if (!(fieldValue instanceof java.lang.Float)) {
                return p171_2;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.Reflector.method_b7e52da193241b30710d(illegalargumentexception));
        }

        java.lang.Float floatValue = (java.lang.Float) fieldValue;
        return floatValue;
    }

    public static boolean callBoolean(
            Object object, ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return false;
            }

            Boolean obool = (Boolean) method.invoke(object, aobject);
            return obool;
        } catch (Throwable throwable) {
            handleException(throwable, object, reflectorMethod, aobject);
            return false;
        }
    }

    private static void dbgCallVoid(
            boolean flag, String text, ReflectorMethod reflectorMethod, Object[] aobject) {
        String name = reflectorMethod.getTargetMethod().getDeclaringClass().getName();
        String nameValue = reflectorMethod.getTargetMethod().getName();
        String textValue = packedTextFragments[4];
        if (flag) {
            textValue = " static";
        }

        OptiFineConfig.dbg(
                text
                        + textValue
                        + packedTextFragments[5]
                        + name
                        + packedTextFragments[6]
                        + nameValue
                        + packedTextFragments[7]
                        + OptiFineConfig.arrayToString(aobject)
                        + packedTextFragments[8]);
    }

    private static void handleException(
            Throwable throwable, Object object, ReflectorMethod reflectorMethod, Object[] aobject) {
        if (throwable instanceof InvocationTargetException) {
            throwable.printStackTrace();
        } else {
            if (throwable instanceof IllegalArgumentException) {
                OptiFineConfig.warn("*** IllegalArgumentException ***");
                OptiFineConfig.warn("Method: " + reflectorMethod.getTargetMethod());
                OptiFineConfig.warn("Object: " + object);
                OptiFineConfig.warn(
                        "Parameter classes: " + OptiFineConfig.arrayToString(getClasses(aobject)));
                OptiFineConfig.warn("Parameters: " + OptiFineConfig.arrayToString(aobject));
            }

            OptiFineConfig.warn("*** Exception outside of method ***");
            OptiFineConfig.warn("Method deactivated: " + reflectorMethod.getTargetMethod());
            reflectorMethod.deactivate();
            throwable.printStackTrace();
        }
    }

    public static float callFloat(
            Object object, ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0.0F;
            }

            Float floatValue = (Float) method.invoke(object, aobject);
            return floatValue;
        } catch (Throwable throwable) {
            handleException(throwable, object, reflectorMethod, aobject);
            return 0.0F;
        }
    }

    public static int callInt(ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0;
            }

            Integer integer = (Integer) method.invoke(null, aobject);
            return integer;
        } catch (Throwable throwable) {
            handleException(throwable, null, reflectorMethod, aobject);
            return 0;
        }
    }

    public static void callVoid(ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return;
            }

            method.invoke(null, aobject);
        } catch (Throwable throwable) {
            handleException(throwable, null, reflectorMethod, aobject);
        }
    }

    public static Object newInstance(ReflectorConstructor reflectorConstructor, Object... aobject) {
        Constructor constructor = reflectorConstructor.getTargetConstructor();
        if (constructor == null) {
            return null;
        }

        try {
            return constructor.newInstance(aobject);
        } catch (Throwable throwable) {
            handleException(throwable, reflectorConstructor, aobject);
            return null;
        }
    }

    public static Field[] getFields(Class oclass, Class oclass1) {
        int k = ReflectorMethod.f();
        ArrayList arrayList = new ArrayList();
        int i = k;

        try {
            Field[] afield = oclass.getDeclaredFields();
            int j = 0;

            while (j < afield.length) {
                Field field = afield[j];
                if (field.getType() == oclass1) {
                    field.setAccessible(true);
                    arrayList.add(field);
                }

                j++;
                if (i != 0) {
                    break;
                }
            }

            return ((java.lang.reflect.Field[]) arrayList.toArray(new Field[arrayList.size()]));
        } catch (Exception exception) {
            return null;
        }
    }

    public static String callString(ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            return method == null ? null : (String) method.invoke(null, aobject);
        } catch (Throwable throwable) {
            handleException(throwable, null, reflectorMethod, aobject);
            return null;
        }
    }

    public static Object getFieldValue(Object p171_0, ReflectorField reflectorField) {
        try {
            java.lang.reflect.Field field = reflectorField.getTargetField();
            return field == null ? null : field.get(p171_0);
        } catch (java.lang.Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public static boolean matchesTypes(Class[] classArray, Class[] classArrayValue) {
        try {
            if (classArray.length != classArrayValue.length) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.Reflector.method_b7e52da193241b30710d(illegalargumentexception1));
        }

        for (int i = 0; i < classArrayValue.length; i++) {
            java.lang.Class oclass = classArray[i];
            java.lang.Class oclass1 = classArrayValue[i];

            try {
                if (oclass != oclass1) {
                    return false;
                }
            } catch (java.lang.IllegalArgumentException illegalargumentexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        optifine.Reflector.method_b7e52da193241b30710d(illegalargumentexception));
            }
        }

        return true;
    }

    public static int callInt(Object object, ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0;
            }

            Integer integer = (Integer) method.invoke(object, aobject);
            return integer;
        } catch (Throwable throwable) {
            handleException(throwable, object, reflectorMethod, aobject);
            return 0;
        }
    }

    public static void callVoid(Object object, ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            if (object == null) {
                return;
            }

            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return;
            }

            method.invoke(object, aobject);
        } catch (Throwable throwable) {
            handleException(throwable, object, reflectorMethod, aobject);
        }
    }

    private static void dbgCall(
            boolean flag,
            String text,
            ReflectorMethod reflectorMethod,
            Object[] aobject,
            Object object) {
        String name = reflectorMethod.getTargetMethod().getDeclaringClass().getName();
        String nameValue = reflectorMethod.getTargetMethod().getName();
        String textValue = packedTextFragments[0];
        if (flag) {
            textValue = " static";
        }

        OptiFineConfig.dbg(
                text
                        + textValue
                        + packedTextFragments[1]
                        + name
                        + packedTextFragments[2]
                        + nameValue
                        + packedTextFragments[3]
                        + OptiFineConfig.arrayToString(aobject)
                        + ") => "
                        + object);
    }

    public static Method[] getMethods(Class type, String text) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.lang.reflect.Method[] amethod = type.getDeclaredMethods();

        for (int i = 0; i < amethod.length; i++) {
            java.lang.reflect.Method method = amethod[i];

            try {
                if (method.getName().equals(text)) {
                    arrayList.add(method);
                }
            } catch (java.lang.IllegalArgumentException illegalargumentexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        optifine.Reflector.method_b7e52da193241b30710d(illegalargumentexception));
            }
        }

        return ((java.lang.reflect.Method[])
                ((java.lang.Object[])
                        (((java.util.ArrayList) (arrayList))
                                .toArray(
                                        ((java.lang.Object[])
                                                (new java.lang.reflect.Method
                                                        [arrayList.size()]))))));
    }

    private static void handleException(
            Throwable throwable, ReflectorConstructor reflectorConstructor, Object[] aobject) {
        int i = ReflectorMethod.cR();
        if (throwable instanceof InvocationTargetException) {
            throwable.printStackTrace();
            if (i != 0) {
                return;
            }
        }

        if (throwable instanceof IllegalArgumentException) {
            OptiFineConfig.warn("*** IllegalArgumentException ***");
            OptiFineConfig.warn("Constructor: " + reflectorConstructor.getTargetConstructor());
            OptiFineConfig.warn(
                    "Parameter classes: " + OptiFineConfig.arrayToString(getClasses(aobject)));
            OptiFineConfig.warn("Parameters: " + OptiFineConfig.arrayToString(aobject));
        }

        OptiFineConfig.warn("*** Exception outside of constructor ***");
        OptiFineConfig.warn(
                "Constructor deactivated: " + reflectorConstructor.getTargetConstructor());
        reflectorConstructor.deactivate();
        throwable.printStackTrace();
    }

    public static boolean postForgeBusEvent(
            ReflectorConstructor reflectorConstructor, Object... aobject) {
        Object object = newInstance(reflectorConstructor, aobject);
        return object == null ? false : postForgeBusEvent(object);
    }

    public static float callFloat(ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            if (method == null) {
                return 0.0F;
            }

            Float floatValue = (Float) method.invoke(null, aobject);
            return floatValue;
        } catch (Throwable throwable) {
            handleException(throwable, null, reflectorMethod, aobject);
            return 0.0F;
        }
    }

    public static Object call(ReflectorMethod reflectorMethod, Object... aobject) {
        try {
            Method method = reflectorMethod.getTargetMethod();
            return method == null ? null : method.invoke(null, aobject);
        } catch (Throwable throwable) {
            handleException(throwable, null, reflectorMethod, aobject);
            return null;
        }
    }

    static {
        optifine.Reflector.packedTextFragments = new String[13];
        optifine.Reflector.packedTextFragments[0] = new String("");
        optifine.Reflector.packedTextFragments[1] = new String(" ");
        optifine.Reflector.packedTextFragments[2] = new String(".");
        optifine.Reflector.packedTextFragments[3] = new String("(");
        optifine.Reflector.packedTextFragments[4] = new String("");
        optifine.Reflector.packedTextFragments[5] = new String(" ");
        optifine.Reflector.packedTextFragments[6] = new String(".");
        optifine.Reflector.packedTextFragments[7] = new String("(");
        optifine.Reflector.packedTextFragments[8] = new String(")");
        optifine.Reflector.packedTextFragments[9] = new String("");
        optifine.Reflector.packedTextFragments[10] = new String(" ");
        optifine.Reflector.packedTextFragments[11] = new String(".");
        optifine.Reflector.MinecraftForge =
                new ReflectorClass("net.minecraftforge.common.MinecraftForge");
        optifine.Reflector.MinecraftForge_EVENT_BUS =
                new ReflectorField(optifine.Reflector.MinecraftForge, "EVENT_BUS");
        HA = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
        IB = new ReflectorMethod(HA, "onLivingSetAttackTarget");
        Gn = new ReflectorMethod(HA, "onLivingUpdate");
        Ik = new ReflectorMethod(HA, "onLivingAttack");
        Gt = new ReflectorMethod(HA, "onLivingHurt");
        IR = new ReflectorMethod(HA, "onLivingDeath");
        HK = new ReflectorMethod(HA, "onLivingDrops");
        HN = new ReflectorMethod(HA, "onLivingFall");
        GC = new ReflectorMethod(HA, "onLivingJump");
        optifine.Reflector.MinecraftForgeClient =
                new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
        optifine.Reflector.MinecraftForgeClient_getRenderPass =
                new ReflectorMethod(optifine.Reflector.MinecraftForgeClient, "getRenderPass");
        optifine.Reflector.MinecraftForgeClient_onRebuildChunk =
                new ReflectorMethod(optifine.Reflector.MinecraftForgeClient, "onRebuildChunk");
        FR = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
        optifine.Reflector.forgeDrawBlockHighlight =
                new ReflectorMethod(FR, "onDrawBlockHighlight");
        optifine.Reflector.forgeOrientBedCamera = new ReflectorMethod(FR, "orientBedCamera");
        optifine.Reflector.forgeDispatchRenderLast = new ReflectorMethod(FR, "dispatchRenderLast");
        optifine.Reflector.ForgeHooksClient_setRenderPass =
                new ReflectorMethod(FR, "setRenderPass");
        Hz = new ReflectorMethod(FR, "onTextureStitchedPre");
        Jd = new ReflectorMethod(FR, "onTextureStitchedPost");
        optifine.Reflector.forgeRenderFirstPersonHand =
                new ReflectorMethod(FR, "renderFirstPersonHand");
        optifine.Reflector.forgeGetOffsetFov = new ReflectorMethod(FR, "getOffsetFOV");
        optifine.Reflector.forgeDrawScreen = new ReflectorMethod(FR, "drawScreen");
        optifine.Reflector.forgeOnFogRender = new ReflectorMethod(FR, "onFogRender");
        optifine.Reflector.forgeSetRenderLayer = new ReflectorMethod(FR, "setRenderLayer");
        Hs = new ReflectorMethod(FR, "transform");
        optifine.Reflector.ForgeHooksClient_getMatrix =
                new ReflectorMethod(FR, "getMatrix", new Class[] {ModelRotation.class});
        HR = new ReflectorMethod(FR, "fillNormal");
        He = new ReflectorMethod(FR, "handleCameraTransforms");
        IA = new ReflectorMethod(FR, "getArmorModel");
        optifine.Reflector.ForgeHooksClient_getArmorTexture =
                new ReflectorMethod(FR, "getArmorTexture");
        Jf = new ReflectorMethod(FR, "putQuadColor");
        Gd = new ReflectorMethod(FR, "loadEntityShader");
        optifine.Reflector.forgeGetFogDensity = new ReflectorMethod(FR, "getFogDensity");
        GH = new ReflectorClass("net.minecraftforge.fml.common.FMLCommonHandler");
        FS = new ReflectorMethod(GH, "instance");
        Hh = new ReflectorMethod(GH, "handleServerStarting");
        Ib = new ReflectorMethod(GH, "handleServerAboutToStart");
        HS = new ReflectorMethod(GH, "enhanceCrashReport");
        Gi = new ReflectorMethod(GH, "getBrandings");
        Gw = new ReflectorMethod(GH, "callFuture");
        HY = new ReflectorClass("net.minecraftforge.fml.client.FMLClientHandler");
        optifine.Reflector.FMLClientHandler_instance = new ReflectorMethod(HY, "instance");
        Ig = new ReflectorMethod(HY, "isLoading");
        optifine.Reflector.FMLClientHandler_trackBrokenTexture =
                new ReflectorMethod(HY, "trackBrokenTexture");
        optifine.Reflector.FMLClientHandler_trackMissingTexture =
                new ReflectorMethod(HY, "trackMissingTexture");
        Gv = new ReflectorClass(WorldProvider.class);
        optifine.Reflector.forgeGetSkyRenderer = new ReflectorMethod(Gv, "getSkyRenderer");
        optifine.Reflector.forgeGetCloudRenderer = new ReflectorMethod(Gv, "getCloudRenderer");
        optifine.Reflector.forgeGetWeatherRenderer = new ReflectorMethod(Gv, "getWeatherRenderer");
        Ga = new ReflectorClass(World.class);
        HH =
                new ReflectorMethod(
                        Ga, "countEntities", new Class[] {EnumCreatureType.class, boolean.class});
        Gu = new ReflectorMethod(Ga, "getPerWorldStorage");
        HO = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
        optifine.Reflector.forgeRenderHandlerRender = new ReflectorMethod(HO, "render");
        optifine.Reflector.forgeDimensionManager =
                new ReflectorClass("net.minecraftforge.common.DimensionManager");
        optifine.Reflector.forgeGetStaticDimensionIds =
                new ReflectorMethod(
                        optifine.Reflector.forgeDimensionManager, "getStaticDimensionIDs");
        optifine.Reflector.WorldEvent_Load =
                new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
        optifine.Reflector.WorldEvent_Load_Constructor =
                new ReflectorConstructor(
                        optifine.Reflector.WorldEvent_Load, new Class[] {World.class});
        Je = new ReflectorClass("net.minecraftforge.client.event.RenderItemInFrameEvent");
        Ih =
                new ReflectorConstructor(
                        Je, new Class[] {EntityItemFrame.class, RenderItemFrame.class});
        FT =
                new ReflectorClass(
                        "net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Pre");
        Gm =
                new ReflectorConstructor(
                        FT, new Class[] {GuiScreen.class, int.class, int.class, float.class});
        Hd =
                new ReflectorClass(
                        "net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post");
        FV =
                new ReflectorConstructor(
                        Hd, new Class[] {GuiScreen.class, int.class, int.class, float.class});
        HC = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
        optifine.Reflector.forgeFogColorsConstructor =
                new ReflectorConstructor(
                        HC,
                        new Class[] {
                            EntityRenderer.class,
                            Entity.class,
                            Block.class,
                            double.class,
                            float.class,
                            float.class,
                            float.class
                        });
        optifine.Reflector.forgeFogColorRed = new ReflectorField(HC, "red");
        optifine.Reflector.forgeFogColorGreen = new ReflectorField(HC, "green");
        optifine.Reflector.forgeFogColorBlue = new ReflectorField(HC, "blue");
        optifine.Reflector.forgeCameraSetupClass =
                new ReflectorClass(
                        "net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup");
        optifine.Reflector.forgeCameraSetupConstructor =
                new ReflectorConstructor(
                        optifine.Reflector.forgeCameraSetupClass,
                        new Class[] {
                            EntityRenderer.class,
                            Entity.class,
                            Block.class,
                            double.class,
                            float.class,
                            float.class,
                            float.class
                        });
        optifine.Reflector.forgeCameraSetupYaw =
                new ReflectorField(optifine.Reflector.forgeCameraSetupClass, "yaw");
        optifine.Reflector.forgeCameraSetupPitch =
                new ReflectorField(optifine.Reflector.forgeCameraSetupClass, "pitch");
        optifine.Reflector.forgeCameraSetupRoll =
                new ReflectorField(optifine.Reflector.forgeCameraSetupClass, "roll");
        optifine.Reflector.forgeRenderLivingPreClass =
                new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Pre");
        optifine.Reflector.forgeRenderLivingPre =
                new ReflectorConstructor(
                        optifine.Reflector.forgeRenderLivingPreClass,
                        new Class[] {
                            LivingEntity.class,
                            RendererLivingEntity.class,
                            double.class,
                            double.class,
                            double.class
                        });
        optifine.Reflector.forgeRenderLivingPostClass =
                new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Post");
        optifine.Reflector.forgeRenderLivingPost =
                new ReflectorConstructor(
                        optifine.Reflector.forgeRenderLivingPostClass,
                        new Class[] {
                            LivingEntity.class,
                            RendererLivingEntity.class,
                            double.class,
                            double.class,
                            double.class
                        });
        Ij = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre");
        optifine.Reflector.forgeRenderLivingSpecialsPre =
                new ReflectorConstructor(
                        Ij,
                        new Class[] {
                            LivingEntity.class,
                            RendererLivingEntity.class,
                            double.class,
                            double.class,
                            double.class
                        });
        Hl = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Post");
        optifine.Reflector.forgeRenderLivingSpecialsPost =
                new ReflectorConstructor(
                        Hl,
                        new Class[] {
                            LivingEntity.class,
                            RendererLivingEntity.class,
                            double.class,
                            double.class,
                            double.class
                        });
        optifine.Reflector.forgeEventBus =
                new ReflectorClass("net.minecraftforge.fml.common.eventhandler.EventBus");
        optifine.Reflector.EventBus_post =
                new ReflectorMethod(optifine.Reflector.forgeEventBus, "post");
        HB = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event$Result");
        GS = new ReflectorField(HB, "DENY");
        FY = new ReflectorField(HB, "ALLOW");
        Jg = new ReflectorField(HB, "DEFAULT");
        HQ = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
        Hc = new ReflectorMethod(HQ, "canEntitySpawn");
        Il = new ReflectorMethod(HQ, "canEntityDespawn");
        IN = new ReflectorMethod(HQ, "renderBlockOverlay");
        IM = new ReflectorMethod(HQ, "renderWaterOverlay");
        Gs = new ReflectorMethod(HQ, "renderFireOverlay");
        IW =
                new ReflectorClass(
                        "net.minecraftforge.client.event.RenderBlockOverlayEvent$OverlayType");
        Id = new ReflectorField(IW, "BLOCK");
        HM = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
        Hi =
                new ReflectorConstructor(
                        HM, new Class[] {ChunkCoordIntPair.class, EntityPlayerMP.class});
        FW = new ReflectorClass(Block.class);
        Gx = new ReflectorMethod(FW, "getBedDirection");
        Ia = new ReflectorMethod(FW, "isBed");
        Hm = new ReflectorMethod(FW, "isBedFoot");
        optifine.Reflector.ForgeBlock_hasTileEntity =
                new ReflectorMethod(FW, "hasTileEntity", new Class[] {BlockState.class});
        Ge = new ReflectorMethod(FW, "canCreatureSpawn");
        optifine.Reflector.ForgeBlock_addHitEffects = new ReflectorMethod(FW, "addHitEffects");
        optifine.Reflector.forgeBlockAddDestroyEffects =
                new ReflectorMethod(FW, "addDestroyEffects");
        optifine.Reflector.forgeBlockIsAir = new ReflectorMethod(FW, "isAir");
        optifine.Reflector.forgeCanRenderBlockInLayer = new ReflectorMethod(FW, "canRenderInLayer");
        Ht = new ReflectorMethod(FW, "getExtendedState");
        IH = new ReflectorClass(Entity.class);
        GR = new ReflectorField(IH, "captureDrops");
        Gq = new ReflectorField(IH, "capturedDrops");
        optifine.Reflector.forgeEntityShouldRenderInPass =
                new ReflectorMethod(IH, "shouldRenderInPass");
        optifine.Reflector.forgeCanRiderInteract = new ReflectorMethod(IH, "canRiderInteract");
        optifine.Reflector.forgeShouldRiderSit = new ReflectorMethod(IH, "shouldRiderSit");
        Iq = new ReflectorClass(TileEntity.class);
        optifine.Reflector.forgeTileShouldRenderInPass =
                new ReflectorMethod(Iq, "shouldRenderInPass");
        optifine.Reflector.forgeTileGetRenderBoundingBox =
                new ReflectorMethod(Iq, "getRenderBoundingBox");
        optifine.Reflector.forgeTileCanRenderBreaking =
                new ReflectorMethod(Iq, "canRenderBreaking");
        IV = new ReflectorClass(Item.class);
        Jb = new ReflectorMethod(IV, "onEntitySwing");
        optifine.Reflector.forgeShouldCauseReequipAnimation =
                new ReflectorMethod(IV, "shouldCauseReequipAnimation");
        Ja = new ReflectorMethod(IV, "getModel");
        optifine.Reflector.ForgeItem_showDurabilityBar =
                new ReflectorMethod(IV, "showDurabilityBar");
        IU = new ReflectorMethod(IV, "getDurabilityForDisplay");
        Go = new ReflectorClass(PotionEffect.class);
        It = new ReflectorMethod(Go, "isCurativeItem");
        GD = new ReflectorClass(ItemRecord.class);
        Gg = new ReflectorMethod(GD, "getRecordResource", new Class[] {String.class});
        Gy = new ReflectorClass(VertexFormatElementEnumUseage.class);
        FU = new ReflectorMethod(Gy, "preDraw");
        Iu = new ReflectorMethod(Gy, "postDraw");
        Hn =
                new ReflectorClass(
                        "net.minecraftforge.fml.common.asm.transformers.BlamingTransformer");
        HE = new ReflectorMethod(Hn, "onCrash");
        Jc = new ReflectorClass("net.minecraftforge.fml.relauncher.CoreModManager");
        Gz = new ReflectorMethod(Jc, "onCrash");
        Gk = new ReflectorClass("net.minecraftforge.client.model.ISmartBlockModel");
        IZ = new ReflectorMethod(Gk, "handleBlockState");
        HU = new ReflectorClass("net.minecraft.launchwrapper.Launch");
        optifine.Reflector.Launch_blackboard = new ReflectorField(HU, "blackboard");
        IK = new ReflectorClass("net.minecraftforge.fml.client.SplashProgress");
        optifine.Reflector.LightUtil =
                new ReflectorClass("net.minecraftforge.client.model.pipeline.LightUtil");
        optifine.Reflector.LightUtil_tessellator =
                new ReflectorField(optifine.Reflector.LightUtil, "tessellator");
        optifine.Reflector.LightUtil_itemConsumer =
                new ReflectorField(optifine.Reflector.LightUtil, "itemConsumer");
        Gf = new ReflectorMethod(optifine.Reflector.LightUtil, "putBakedQuad");
        Ic = new ReflectorMethod(optifine.Reflector.LightUtil, "renderQuadColor");
        GL = new ReflectorClass("net.minecraftforge.common.property.IExtendedBlockState");
        HT = new ReflectorMethod(GL, "getClean");
        Gc = new ReflectorClass("net.minecraftforge.client.ItemModelMesherForge");
        Iw = new ReflectorConstructor(Gc, new Class[] {ModelManager.class});
        IX = new ReflectorClass("net.minecraftforge.client.model.ModelLoader");
        Gl = new ReflectorMethod(IX, "onRegisterItems");
        HP = new ReflectorClass("net.minecraftforge.client.model.Attributes");
        optifine.Reflector.Attributes_DEFAULT_BAKED_FORMAT =
                new ReflectorField(HP, "DEFAULT_BAKED_FORMAT");
        Ip = new ReflectorClass("mods.betterfoliage.client.BetterFoliageClient");
        GV = new ReflectorClass("net.minecraftforge.client.model.IColoredBakedQuad");
        GW = new ReflectorClass(BiomeGenBase.class);
        optifine.Reflector.ForgeBiome_getWaterColorMultiplier =
                new ReflectorMethod(GW, "getWaterColorMultiplier");
        GY = new ReflectorClass("net.minecraftforge.fml.client.registry.RenderingRegistry");
        GG =
                new ReflectorMethod(
                        GY, "loadEntityRenderers", new Class[] {RenderManager.class, Map.class});
        HJ = new ReflectorClass(TileEntityRendererDispatcher.class);
        optifine.Reflector.forgeTileDispatcherPreDrawBatch =
                new ReflectorMethod(HJ, "preDrawBatch");
        optifine.Reflector.forgeTileDispatcherDrawBatch = new ReflectorMethod(HJ, "drawBatch");
        optifine.Reflector.OptiFineClassTransformer =
                new ReflectorClass("optifine.OptiFineClassTransformer");
        optifine.Reflector.OptiFineClassTransformer_instance =
                new ReflectorField(optifine.Reflector.OptiFineClassTransformer, "instance");
        optifine.Reflector.OptiFineClassTransformer_getOptiFineResource =
                new ReflectorMethod(
                        optifine.Reflector.OptiFineClassTransformer, "getOptiFineResource");
        Hv = new ReflectorClass("net.minecraftforge.common.ForgeModContainer");
        Is = new ReflectorField(Hv, "forgeLightPipelineEnabled");
    }
}
