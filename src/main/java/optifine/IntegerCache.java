package optifine;

public class IntegerCache {
    private static final int CACHE_SIZE = 4096;
    private static final Integer[] cache = makeCache(4096);

    private static Integer[] makeCache(int p171_0) {
        java.lang.Integer[] ainteger = new java.lang.Integer[p171_0];

        for (int j = 0; j < p171_0; j++) {
            ainteger[j] = new java.lang.Integer(j);
        }

        return ainteger;
    }

    public static Integer valueOf(int p171_0) {
        return p171_0 >= 0 && p171_0 < 4096
                ? optifine.IntegerCache.cache[p171_0]
                : new java.lang.Integer(p171_0);
    }
}
