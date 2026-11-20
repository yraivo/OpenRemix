package optifine;

import java.lang.reflect.Constructor;

public class ReflectorConstructor {
    private boolean checked;
    private Class[] parameterTypes;
    private ReflectorClass reflectorClass = null;
    private Constructor targetConstructor;

    public void deactivate() {
        this.checked = true;
        this.targetConstructor = null;
    }

    public boolean exists() {
        return this.checked ? this.targetConstructor != null : this.getTargetConstructor() != null;
    }

    public ReflectorConstructor(ReflectorClass reflectorClassValue, Class[] aclass) {
        this.parameterTypes = null;
        this.checked = false;
        this.targetConstructor = null;
        this.reflectorClass = reflectorClassValue;
        this.parameterTypes = aclass;
        Constructor constructor = this.getTargetConstructor();
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    private static Constructor findConstructor(Class oclass, Class[] aclass) {
        Constructor[] aconstructor = oclass.getDeclaredConstructors();

        for (int i = 0; i < aconstructor.length; i++) {
            Constructor constructor = aconstructor[i];
            Class[] aclass1 = constructor.getParameterTypes();
            if (Reflector.matchesTypes(aclass, aclass1)) {
                return constructor;
            }
        }

        return null;
    }


    public Constructor getTargetConstructor() {
        try {
            if (this.checked) {
                return this.targetConstructor;
            }
        } catch (Throwable throwable4) {
            throw remix.compat.compiler.JvmThrowTypes.flow(method_b7e52da193241b30710d(throwable4));
        }

        this.checked = true;
        Class oclass = this.reflectorClass.getTargetClass();

        try {
            if (oclass == null) {
                return null;
            }
        } catch (Throwable throwable1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(method_b7e52da193241b30710d(throwable1));
        }

        try {
            try {
                this.targetConstructor = findConstructor(oclass, this.parameterTypes);
                if (this.targetConstructor == null) {
                    OptiFineConfig.dbg(
                            "(Reflector) Constructor not present: "
                                    + oclass.getName()
                                    + ", params: "
                                    + OptiFineConfig.arrayToString(this.parameterTypes));
                }
            } catch (Throwable throwable2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_b7e52da193241b30710d(throwable2));
            }

            try {
                if (this.targetConstructor != null) {
                    this.targetConstructor.setAccessible(true);
                }
            } catch (Throwable throwable) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_b7e52da193241b30710d(throwable));
            }
        } catch (Throwable throwable3) {
            throwable3.printStackTrace();
        }

        return this.targetConstructor;
    }
}
