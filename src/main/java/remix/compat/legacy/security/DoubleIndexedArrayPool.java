package remix.compat.legacy.security;

public final class DoubleIndexedArrayPool extends IndexedArrayPool<Double> {
    public DoubleIndexedArrayPool() {
        super(4, new int[0], constructorArray171$58());
    }

    private static Double[] constructorArray171$58() {
        Double[] adouble = new Double[] {956747.0, null, null, null, null, null, null, null, null};

        adouble[1] = 3.637499538E-314;

        adouble[2] = 2.2250738585072014E-308;
        adouble[3] = 267467.0;

        adouble[4] = 4.8479E-319;

        adouble[5] = Double.POSITIVE_INFINITY;
        adouble[6] = 1.24923067E8;
        adouble[7] = 634067.0;

        adouble[8] = 4.9E-324;

        return adouble;
    }

    public Class<? extends SecurityCheckEntry> mc() {
        return DoubleSecurityCheckEntry.class;
    }
}
