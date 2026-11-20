package optifine;

import java.lang.reflect.Field;

public class ReflectorField {
    private static String e;
    private static String[] packedTextFragments;
    private ReflectorClass Ii = null;
    private boolean checked;
    private Field targetField;
    private String b = null;

    private static Exception a(Exception exception) {
        return exception;
    }

    public Field getTargetField() {
        if (this.checked) {
            return this.targetField;
        }

        this.checked = true;
        Class oclass = this.Ii.getTargetClass();
        if (oclass == null) {
            return null;
        }

        try {
            this.targetField = oclass.getDeclaredField(this.b);
            this.targetField.setAccessible(true);
        } catch (NoSuchFieldException nosuchfieldexception) {
            OptiFineConfig.log(e + oclass.getName() + packedTextFragments[0] + this.b);
        } catch (SecurityException securityexception) {
            securityexception.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return this.targetField;
    }

    // region String decoding support

    // endregion

    public ReflectorField(ReflectorClass reflectorClass, String text) {
        this.checked = false;
        this.targetField = null;
        this.Ii = reflectorClass;
        this.b = text;
        Field field = this.getTargetField();
    }

    static {
        optifine.ReflectorField.packedTextFragments = new String[2];
        optifine.ReflectorField.packedTextFragments[0] = new String(".");
        optifine.ReflectorField.packedTextFragments[1] =
                new String(
                        "Y<\"o-\u0011E\u0005\u00015 a2O\u0014\u0002#)/\u001bRQ\u001e5l2\u0011H\u0005Tg");
        e = "(Reflector) Field not present: ";
    }

    // region String decoding support

    // endregion

    public void setValue(Object object) {
        Reflector.setFieldValue(null, this, object);
    }

    // region String decoding support

    // endregion

    public Object getValue() {
        return Reflector.getFieldValue(null, this);
    }

    public boolean exists() {
        return this.checked ? this.targetField != null : this.getTargetField() != null;
    }
}
