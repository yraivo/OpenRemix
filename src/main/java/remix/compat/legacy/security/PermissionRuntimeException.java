package remix.compat.legacy.security;

public class PermissionRuntimeException extends RuntimeException {
    public PermissionRuntimeException(String text) {
        super(text);
    }

    public PermissionRuntimeException(String text, Throwable throwable) {
        super(text, throwable);
    }
}
