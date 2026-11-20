package optifine;

public class ReflectorClass {
    private Class targetClass;

    private String targetClassName = null;
    private static String d;
    private boolean checked = false;

    public Class getTargetClass() {
        try {
            if (false)
                throw (java.lang.ClassNotFoundException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (this.checked) {
                return this.targetClass;
            }
        } catch (ClassNotFoundException classnotfoundexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(classnotfoundexception1));
        }

        this.checked = true;

        try {
            this.targetClass = Class.forName(this.targetClassName);
        } catch (ClassNotFoundException classnotfoundexception) {
            OptiFineConfig.log(d + this.targetClassName);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return this.targetClass;
    }

    public String getTargetClassName() {
        return this.targetClassName;
    }

    public ReflectorMethod makeMethod(String text, Class[] aclass, boolean flag) {
        return new ReflectorMethod(this, text, aclass, flag);
    }

    // region String decoding support

    // endregion

    public ReflectorClass(Class oclass) {
        this.targetClass = null;
        this.targetClass = oclass;
        this.targetClassName = oclass.getName();
        this.checked = true;
    }

    public ReflectorMethod makeMethod(String text) {
        return new ReflectorMethod(this, text);
    }

    public ReflectorClass(String text, boolean flag) {
        this.targetClass = null;
        this.targetClassName = text;
        if (!flag) {
            Class oclass = this.getTargetClass();
        }
    }

    public ReflectorField makeField(String text) {
        return new ReflectorField(this, text);
    }

    public boolean exists() {
        return this.getTargetClass() != null;
    }

    public ReflectorClass(String text) {
        this(text, false);
    }

    private static ClassNotFoundException a(ClassNotFoundException classnotfoundexception) {
        return classnotfoundexception;
    }

    public ReflectorMethod makeMethod(String text, Class[] aclass) {
        return new ReflectorMethod(this, text, aclass);
    }

    public boolean isInstance(Object object) {
        return this.getTargetClass() == null ? false : this.getTargetClass().isInstance(object);
    }

    static {
        d = "(Reflector) Class not present: ";
    }

    // region String decoding support

    // endregion
}
