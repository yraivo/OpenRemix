package remix.util.reflection;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public final class ReflectiveMethodAccessor {
    private Class<?> aHC;
    private Method method;
    private Parameter[] bSt;
    private Class<?> bSu;

    public Method getMethod() {
        return this.method;
    }

    public Parameter[] abm() {
        return this.bSt;
    }

    public ReflectiveMethodAccessor(Class<?> oclass, Method method) {
        this.aHC = oclass;
        this.method = method;
        this.bSt = method.getParameters();
        this.bSu = method.getReturnType();
    }

    public Class<?> ml() {
        return this.aHC;
    }

    public Object method_79889e16ff872c1e35a0(Object... aobject)
            throws InvocationTargetException, IllegalAccessException {
        return this.method.invoke(null, aobject);
    }

    public Class<?> abh() {
        return this.bSu;
    }

    public static FluentListWrapper method_ce11fb5bdc53be5f188c(Class<?> oclass) {
        FluentListWrapper fluentListWrapper = FluentCollections.ax();
        Method[] amethod = oclass.getDeclaredMethods();

        for (Method method : amethod) {
            fluentListWrapper.add(
                    new ReflectiveMethodAccessor(oclass, method));
        }

        return fluentListWrapper;
    }
}
