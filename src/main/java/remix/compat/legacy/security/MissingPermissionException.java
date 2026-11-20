package remix.compat.legacy.security;

public final class MissingPermissionException extends PermissionRuntimeException {
    private static String a;

    // region String decoding support

    // endregion

    public MissingPermissionException(String text) {
        super(String.format(a, text));
    }

    static {
        a = "Couldn't create the permission by id: %s, because it's missing.";
    }

    public MissingPermissionException(String text, Throwable throwable) {
        super(text, throwable);
    }

    // region String decoding support

    // endregion
}
