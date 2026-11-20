package remix.compat.legacy.environment;

import remix.compat.legacy.security.AbstractExceptionClassArrayPool;
import remix.compat.legacy.security.SecurityCheckEntry;
import remix.compat.legacy.security.SecurityCheckException;
import remix.compat.legacy.security.SecurityCheckSubException1;
import remix.compat.legacy.security.SecurityCheckSubException2;
import remix.compat.legacy.security.SecurityCheckSubException3;
import remix.compat.legacy.security.SecurityCheckSubException4;

public final class VmExceptionClassArrayPool
        extends AbstractExceptionClassArrayPool<SecurityCheckException> {
    public Class<? extends SecurityCheckEntry> mc() {
        return VmExceptionSecurityCheckEntry.class;
    }

    public VmExceptionClassArrayPool() {
        super(
                1,
                new int[0],
                new Class[] {
                    SecurityCheckSubException4.class,
                    SecurityCheckSubException1.class,
                    SecurityCheckSubException3.class,
                    SecurityCheckSubException2.class
                });
    }
}
