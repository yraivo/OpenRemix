package remix.compat.legacy.security;

public final class ExceptionSecurityPoolProvider extends remix.compat.legacy.security.DefaultSecurityPoolProvider {
    public ExceptionSecurityPoolProvider(IndexedValuePool... indexedValuePoolArray) {
        super(indexedValuePoolArray);
    }

    public Object method_c1c6799471e452616c28(
            AbstractExceptionSecurityCheckEntry abstractExceptionSecurityCheckEntry) {
        Object object;
        try {
            object = abstractExceptionSecurityCheckEntry.cy();
        } catch (Exception exception) {
            object = exception;
        }

        return (java.lang.Object) object;
    }

    public Object method_a6372c5460944673d5f1(
            SecurityCheckEntry securityCheckEntry) {
        return this.method_c1c6799471e452616c28(
                (AbstractExceptionSecurityCheckEntry) securityCheckEntry);
    }
}
