package remix.compat.legacy.auth;

public enum LicenseValidationState {
    VALIDATING(LicenseCode1Exception.class),
    INVALID(LicenseCode3Exception.class),
    VALIDATING_PROCESS(LicenseCode4Exception.class),
    WAITING_RESPONSE(LicenseCode5Exception.class),
    INVALID_EXTRA(LicenseCode3Exception.class),
    VALIDATING_XLP_EXTRA(LicenseCode8Exception.class),
    WAITING_EXTRA(LicenseCode0Exception.class),
    VALIDATING_XLP(LicenseCode8Exception.class),
    WAITING(LicenseCode0Exception.class),
    CONFIRMED_ALT(LicenseCode10Exception.class),
    VALIDATING_EXTRA(LicenseCode1Exception.class),
    INVALID_HWID(LicenseCode8NullPointerException.class),
    VALID(LicenseCode7ArraySizeException.class),
    VALIDATING_HWID_EXTRA(LicenseCode6Exception.class),
    INVALID_HWID_EXTRA(LicenseCode8NullPointerException.class),
    CONFIRMED_ALT_EXTRA(LicenseCode10Exception.class),
    VALIDATING_HWID(LicenseCode6Exception.class),
    VALID_EXTRA(LicenseCode7ArraySizeException.class),
    VALIDATING_PROCESS_EXTRA(LicenseCode4Exception.class),
    WAITING_RESPONSE_EXTRA(LicenseCode5Exception.class);

    private int a;
    private static String e;

    private Class<? extends Exception> aHC;
    private Exception bZU;

    public static void b(String text) {
        e = text;
    }

    LicenseValidationState(Class<? extends Exception> oclass) {
        this.aHC = oclass;

        Exception exception;
        int j;
        try {
            exception = (Exception) oclass.newInstance();
            j = exception.hashCode();
        } catch (InstantiationException | IllegalAccessException instantiationexception) {
            exception = instantiationexception;
            j = -1;
        }

        this.bZU = exception;
        this.a = j;
    }

    public static String d() {
        return e;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public Class<? extends Exception> afx() {
        return this.aHC;
    }

    public Exception afy() {
        return this.bZU;
    }


    public int method_9b417d27949cba33a0d0() {
        return this.a;
    }
}
