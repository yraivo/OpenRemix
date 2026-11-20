package remix.util.reflection;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;

import remix.client.RemixClient;

import java.lang.reflect.Field;

public final class ReflectiveFieldAccessor {
    public static int dD;
    private Class<?> bhV;
    public static Object b;

    public static boolean il;
    private Field field;
    public static Object aZp;
    private Class<?> bim;
    private String g;
    private static Field bSp;

    public ReflectiveFieldAccessor(Class<?> oclass, Field field) {
        this.bim = oclass;
        this.field = field;
        this.field.setAccessible(true);
        this.g = field.getName();
        this.bhV = this.field.getType();
    }

    public Field getField() {
        return this.field;
    }

    public String q() {
        return this.g;
    }

    public void a(Object aZpValue) throws Exception {
        ReflectiveClassWrapper.Ds();

        Object aZpValue2 = aZp;
        aZp = aZpValue;
        b = aZpValue2;

        this.field.setAccessible(true);
        this.field.set(null, aZpValue);
        dD++;
    }

    // region String decoding support

    // endregion

    public static FluentListWrapper method_ce11fb5bdc53be5f188c(Class<?> oclass) {
        FluentListWrapper fluentListWrapper = FluentCollections.ax();
        Field[] afield = oclass.getDeclaredFields();

        for (Field field : afield) {
            fluentListWrapper.add(
                    new ReflectiveFieldAccessor(oclass, field));
        }

        return fluentListWrapper;
    }

    public Class<?> ml() {
        return this.bim;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public Class<?> abh() {
        return this.bhV;
    }

    public Object Qu() throws IllegalAccessException {
        Object object = this.field.get(null);
        if (object instanceof RemixClient && !il) {
            il = true;
        }

        return object;
    }

    // region String decoding support

    // endregion

    static {
        try {
            bSp = Field.class.getDeclaredField(new String("modifiers"));
        } catch (NoSuchFieldException nosuchfieldexception1) {
            bSp = null;
        }
        bSp.setAccessible(true);
    }
}
