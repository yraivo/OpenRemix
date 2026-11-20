package remix.compat.legacy.crypto;

public final class Md5LengthValidator implements remix.compat.legacy.codec.ValueValidator {
    private static String[] b;

    public boolean method_c7b139ccf3ff22603b21(Object object) {
        return this.a((String) object);
    }

    public static String[] b() {
        return b;
    }

    static {
        if (b() == null) {
            b(new String[3]);
        }
    }

    public boolean a(String text) {
        b();

        int i = text.length() != 32 ? 1 : 0;

        return remix.compat.compiler.JvmBooleanSlots.fromInt(i);
    }

    public static void b(String[] astring) {
        b = astring;
    }
}
