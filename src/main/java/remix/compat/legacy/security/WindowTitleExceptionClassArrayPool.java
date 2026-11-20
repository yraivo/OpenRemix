package remix.compat.legacy.security;

import remix.compat.legacy.environment.VmDetectionException;
import remix.compat.legacy.environment.VmDetectionSubException1;
import remix.compat.legacy.environment.VmDetectionSubException2;
import remix.compat.legacy.environment.VmDetectionSubException3;

public final class WindowTitleExceptionClassArrayPool
        extends AbstractExceptionClassArrayPool<VmDetectionException> {
    public WindowTitleExceptionClassArrayPool() {
        super(
                2,
                new int[0],
                new Class[] {
                    VmDetectionSubException1.class,
                    VmDetectionSubException2.class,
                    VmDetectionSubException3.class
                });
    }

    public Class<? extends SecurityCheckEntry> mc() {
        return WindowTitleExceptionSecurityCheckEntry.class;
    }
}
