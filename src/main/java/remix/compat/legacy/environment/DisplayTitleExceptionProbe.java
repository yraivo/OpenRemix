package remix.compat.legacy.environment;

import remix.compat.legacy.security.SecurityCheckHelper;
import remix.util.reflection.ReflectiveClassWrapper;
import remix.util.reflection.ReflectiveMethodAccessor;

import remix.client.RemixClient;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public final class DisplayTitleExceptionProbe extends remix.util.task.NamedValueHolder {

    private static boolean f;
    private final String d = new String("Minecraft 1.8");
    private static String[] packedTextFragments;
    private String e;

    // region String decoding support

    // endregion

    public Object getValue() {
        return this.mc();
    }

    public Class mc() {
        if (this.e == null) {
            this.e =
                    (String)
                            RemixClient.encodedStringCodec
                                    .getDecoder()
                                    .decode(
                                            "=(':':1':'2:2':2'3:3':'1:1':1'2:'3:1':')#[39, 0, -35, -79, -100, -100](\"3.\".1\"1.\".2\".1\".1\".\"1.\"1.\".2\".\"2.2\"1.3\".\"1.\"2.2\"1.\".2\"2.1\"6.\"2)#[56, 15, -40, -109, -159, -191](:2'1:'2:1'3:':':':':1'1:'2:'3:2':'1:'1:':'2:2'1:'1:1')#[43, 5, -51, -81, -82, -137](':2'1:1':1'2:3'2:'2:'1:':':':'2:1':':4':2':'1:1':':)#[51, -9, -38, -59, -66, -121](:1':':2'6:':'4:'2:':1'5:1'1:1'3:':1':1':1':'2:'1:2'2:)#[25, 14, 4, -33, -66, -66](:'2:1':'1:':3':1'1:1'1:3':':1':1':1'3:1':'1:':1':':'2)#[34, 15, -21, -71, -102, -122](:'1:':'2:1'2:1':3':':'1)#[9, -8, -28, -31, -38, -40](':2':'1:'1:1':1':':'1:'1:'3:1':'2:':2')#[22, -6, -15, -20, -40, -40](':1'1:':1':'3:':':'1:':2':4':'1)#[28, -3, -4, -9, -23, -44]('2:'1:1':6'2:':1':3'1:1'3:1'1:'2:'1:1'2:2'1:4':':2')#[37, 19, -10, -44, -101, -169]('3:':'1:':':1'2:2'2:'1:1':':2':2')#[19, -9, -47, -86, -101, -125](:2':3':'2:1'1:2'3:1':'1:':':':'1:3':'2:');");
        }

        try {
            Optional optional =
                    SecurityCheckHelper.bYy.findClassByLegacyId(35289129);
            if (optional.isPresent()) {
                ReflectiveClassWrapper reflectiveClassWrapper =
                        (ReflectiveClassWrapper) optional.get();
                ReflectiveMethodAccessor reflectiveMethodAccessor =
                        reflectiveClassWrapper.method_00da736646037f40b0b2(
                                new String("getTitle"), String.class);

                try {
                    Object object =
                            reflectiveMethodAccessor.method_79889e16ff872c1e35a0(new Object[0]);
                    if (OperatingSystemType.aeZ() == OperatingSystemType.WINDOWS) {
                        if (object != null && object.equals(this.d)) {
                            throw new VmDetectionSubException3();
                        } else {
                            throw new VmDetectionSubException2();
                        }
                    } else {
                        throw new VmDetectionSubException3();
                    }
                } catch (InvocationTargetException
                        | IllegalAccessException invocationtargetexception) {
                    throw new VmDetectionSubException2();
                }
            } else {
                throw new VmDetectionSubException1();
            }
        } catch (VmDetectionException vmDetectionException) {
            return (Class<? extends VmDetectionException>)
                    vmDetectionException.getClass();
        }
    }

    public static boolean method_d87039b07bd6961907f2() {
        return f;
    }

    public static boolean d() {
        boolean flag = method_d87039b07bd6961907f2();
        return !flag;
    }

    public DisplayTitleExceptionProbe() {
        super(null, packedTextFragments[0]);
    }

    // region String decoding support

    // endregion

    static {
        remix.compat.legacy.environment.DisplayTitleExceptionProbe.packedTextFragments = new String[1];
        remix.compat.legacy.environment.DisplayTitleExceptionProbe.packedTextFragments[0] = new String("");
        method_a8745b073fa8adfa1857(true);
    }

    private static InvocationTargetException method_fa42edf147129904142e(
            InvocationTargetException invocationtargetexception) {
        return invocationtargetexception;
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        f = flag;
    }
}
