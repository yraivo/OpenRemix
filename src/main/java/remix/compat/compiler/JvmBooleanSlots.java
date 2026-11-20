package remix.compat.compiler;

/** Ordinary Java implementation of the retained legacy integer/boolean conversions. */
public final class JvmBooleanSlots {
    private JvmBooleanSlots() {}

    public static boolean fromInt(int bits) {
        return (bits & 1) != 0;
    }

    public static int toInt(boolean value) {
        return value ? 1 : 0;
    }

    public static short toShort(boolean value) {
        return (short) (value ? 1 : 0);
    }

    public static char toChar(boolean value) {
        return (char) (value ? 1 : 0);
    }

    public static byte toByte(boolean value) {
        return (byte) (value ? 1 : 0);
    }
}
