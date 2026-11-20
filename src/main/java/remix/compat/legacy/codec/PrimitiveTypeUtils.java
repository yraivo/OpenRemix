package remix.compat.legacy.codec;

import net.minecraft.entity.Entity;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class PrimitiveTypeUtils {
    private static Entity[] b;

    public static boolean isByte(Object object) {
        return object.getClass().equals(byte.class) || object.getClass().equals(Byte.class);
    }

    public static boolean isShort(Object object) {
        return object.getClass().equals(short.class) || object.getClass().equals(Short.class);
    }

    public static boolean isNumber(Object object) {
        return isInt(object)
                || isChar(object)
                || isByte(object)
                || isLong(object)
                || isShort(object)
                || isDouble(object)
                || isFloat(object)
                || object.getClass().equals(BigInteger.class)
                || object.getClass().equals(BigDecimal.class)
                || object.getClass().equals(Number.class);
    }

    public static boolean isVoid(Object object) {
        return object.getClass().equals(void.class) || object.getClass().equals(Void.class);
    }

    public static boolean isFloat(Object object) {
        return object.getClass().equals(float.class) || object.getClass().equals(Float.class);
    }

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }

    public static boolean isEnum(Object object) {
        return object.getClass().equals(Enum.class)
                || object instanceof Enum
                || object.getClass().isAssignableFrom(Enum.class);
    }

    public static boolean isDouble(Object object) {
        return object.getClass().equals(double.class) || object.getClass().equals(Double.class);
    }

    public static boolean isBoolean(Object object) {
        return object.getClass().equals(boolean.class) || object.getClass().equals(Boolean.class);
    }

    public static boolean isLong(Object object) {
        return object.getClass().equals(long.class) || object.getClass().equals(Long.class);
    }

    public static boolean isChar(Object object) {
        return object.getClass().equals(char.class) || object.getClass().equals(Character.class);
    }

    static {
        if (getEntities() == null) {
            b(new Entity[3]);
        }
    }

    public static boolean isString(Object object) {
        return object.getClass().equals(String.class);
    }

    public static Entity[] getEntities() {
        return b;
    }

    public static boolean isInt(Object object) {
        return object.getClass().equals(int.class) || object.getClass().equals(Integer.class);
    }
}
