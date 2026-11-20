package remix.compat.legacy.codec;

public final class BracedColonTokenSplitter implements remix.compat.legacy.codec.TokenArraySplitter {
    private static int cz;

    public static int method_7eec03da329a017ec143() {
        return cz;
    }

    // region String decoding support

    // endregion

    public static void b(int i) {
        cz = i;
    }

    public String[] method_5884051ea73df62f3dfc(String text) {
        method_beea3e16fe3ec0995eb9();

        String textValue = text.substring(text.indexOf(new String("{")) + 1);
        textValue = textValue.substring(0, textValue.indexOf(new String("}")) + 1);

        String[] astring = textValue.split(new String(":"));

        return astring;
    }

    // region String decoding support

    // endregion

    public Object[] method_443057c58a5a709862b7(Object object) {
        return this.method_5884051ea73df62f3dfc((String) object);
    }

    static {
        b(0);
    }

    public static int method_beea3e16fe3ec0995eb9() {
        method_7eec03da329a017ec143();

        return (50);
    }
}
