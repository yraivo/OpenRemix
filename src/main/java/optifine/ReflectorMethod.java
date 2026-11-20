package optifine;

import java.lang.reflect.Method;

public class ReflectorMethod {
    private boolean checked;
    private static String[] packedTextFragments;
    private ReflectorClass reflectorClass = null;

    private Class[] targetMethodParameterTypes;
    private static int f;
    private Method targetMethod;

    private String targetMethodName = null;

    public Method getTargetMethod() {
        try {
            if (this.checked) {
                return this.targetMethod;
            }
        } catch (Throwable throwable2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(method_b7e52da193241b30710d(throwable2));
        }

        this.checked = true;
        Class oclass = this.reflectorClass.getTargetClass();

        try {
            if (oclass == null) {
                return null;
            }
        } catch (Throwable throwable) {
            throw remix.compat.compiler.JvmThrowTypes.flow(method_b7e52da193241b30710d(throwable));
        }

        try {
            if (this.targetMethodParameterTypes == null) {
                Method[] amethod = Reflector.getMethods(oclass, this.targetMethodName);
                if (amethod.length <= 0) {
                    OptiFineConfig.log(
                            "(Reflector) Method not present: "
                                    + oclass.getName()
                                    + packedTextFragments[0]
                                    + this.targetMethodName);
                    return null;
                }

                if (amethod.length > 1) {
                    OptiFineConfig.warn(
                            "(Reflector) More than one method found: "
                                    + oclass.getName()
                                    + packedTextFragments[1]
                                    + this.targetMethodName);

                    for (int i = 0; i < amethod.length; i++) {
                        Method method = amethod[i];
                        OptiFineConfig.warn("(Reflector)  - " + method);
                    }

                    return null;
                }

                this.targetMethod = amethod[0];
            } else {
                this.targetMethod =
                        Reflector.getMethod(
                                oclass, this.targetMethodName, this.targetMethodParameterTypes);
            }

            if (this.targetMethod == null) {
                OptiFineConfig.log(
                        "(Reflector) Method not present: "
                                + oclass.getName()
                                + packedTextFragments[2]
                                + this.targetMethodName);
                return null;
            } else {
                this.targetMethod.setAccessible(true);
                return this.targetMethod;
            }
        } catch (Throwable throwable1) {
            throwable1.printStackTrace();
            return null;
        }
    }

    public ReflectorMethod(ReflectorClass reflectorClassValue, String text) {
        this(reflectorClassValue, text, (Class[]) null, false);
    }

    public ReflectorMethod(ReflectorClass reflectorClassValue, String text, Class[] aclass) {
        this(reflectorClassValue, text, aclass, false);
    }

    // region String decoding support

    // endregion

    public static void b(int i) {
        f = i;
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    // region String decoding support

    // endregion

    public boolean exists() {
        return this.checked ? this.targetMethod != null : this.getTargetMethod() != null;
    }

    // region String decoding support

    // endregion

    public ReflectorMethod(
            ReflectorClass reflectorClassValue, String text, Class[] aclass, boolean flag) {
        this.targetMethodParameterTypes = null;
        this.checked = false;
        this.targetMethod = null;
        this.reflectorClass = reflectorClassValue;
        this.targetMethodName = text;
        this.targetMethodParameterTypes = aclass;
        if (!flag) {
            Method method = this.getTargetMethod();
        }
    }

    public static int f() {
        int i = cR();
        return i == 0 ? 123 : 0;
    }

    static {
        optifine.ReflectorMethod.packedTextFragments = new String[5];
        optifine.ReflectorMethod.packedTextFragments[0] = new String(".");
        optifine.ReflectorMethod.packedTextFragments[1] = new String(".");
        optifine.ReflectorMethod.packedTextFragments[2] = new String(".");
        b(68);
    }

    public void deactivate() {
        this.checked = true;
        this.targetMethod = null;
    }

    public Class getReturnType() {
        Method method = this.getTargetMethod();
        return method == null ? null : method.getReturnType();
    }

    public static int cR() {
        return f;
    }
}
