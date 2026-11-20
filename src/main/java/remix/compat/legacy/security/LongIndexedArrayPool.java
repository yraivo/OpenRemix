package remix.compat.legacy.security;

public final class LongIndexedArrayPool extends IndexedArrayPool<Long> {
    private static String[] f;

    public LongIndexedArrayPool() {
        super(6, new int[] {2, 3, -6, -2, 7}, constructorArray171$79());
    }

    private static Long[] constructorArray171$79() {
        Long[] along = new Long[14];

        along[0] = 7260872569L;

        along[1] = 74499426L;

        along[2] = 9909285487L;

        along[3] = 96798945916L;

        along[4] = 6715606727L;

        along[5] = 3331488701L;

        along[6] = 6624732569L;

        along[7] = 8917260L;

        along[8] = 1305813L;

        along[9] = 637445813L;

        along[10] = 96783385588L;

        along[11] = 75436708L;

        along[12] = 60363724L;

        along[13] = 92179971L;

        return along;
    }

    public static String[] y() {
        return f;
    }

    public Class<? extends SecurityCheckEntry> mc() {
        return LongSecurityCheckEntry.class;
    }

    public static void b(String[] astring) {
        f = astring;
    }

    static {
        if (y() == null) {
            b(new String[2]);
        }
    }
}
