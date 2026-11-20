package remix.compat.legacy.environment;

import remix.compat.legacy.security.SecurityCheckException;
import remix.compat.legacy.security.SecurityCheckSubException1;
import remix.compat.legacy.security.SecurityCheckSubException3;
import remix.compat.legacy.security.SecurityCheckSubException4;

public final class VmFileExceptionProbe extends remix.util.task.NamedValueHolder {
    private static boolean d;
    private static String[] packedTextFragments;

    private static SecurityCheckException method_f6fb6c997df2f8c377e5(
            SecurityCheckException securityCheckException) {
        return securityCheckException;
    }

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (!method_d87039b07bd6961907f2()) {
            method_a8745b073fa8adfa1857(true);
        }
    }

    public Object getValue() {
        return this.mc();
    }

    public static boolean d() {
        boolean flag = method_d87039b07bd6961907f2();
        return !flag;
    }

    public VmFileExceptionProbe() {
        super(null, packedTextFragments[0]);
    }

    public static boolean method_d87039b07bd6961907f2() {
        return d;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.environment.VmFileExceptionProbe.packedTextFragments[0] =
                new String("");
    }

    // endregion

    public Class mc() {
        try {
            OperatingSystemType operatingSystemType = OperatingSystemType.aeZ();
            VirtualBoxAdditionLocation virtualBoxAdditionLocation =
                    VirtualBoxAdditionLocation.method_00234a51d56b7da6d046(operatingSystemType);
            switch (virtualBoxAdditionLocation) {
                case WINDOWS_FILE:
                case LINUX_FILE:
                    if (virtualBoxAdditionLocation.afb().a().exists()) {
                        throw new SecurityCheckSubException4();
                    }
                default:
                    VmwareToolsLocation vmwareToolsLocation =
                            VmwareToolsLocation.method_d273c5daf5028f542c4a(operatingSystemType);
                    switch (vmwareToolsLocation) {
                        case WINDOWS_FILE:
                        case LINUX_FILE:
                        case OSX_FILE:
                            if (vmwareToolsLocation.afb().a().exists()) {
                                throw new SecurityCheckSubException3();
                            }
                        default:
                            throw new SecurityCheckSubException1();
                    }
            }
        } catch (SecurityCheckException securityCheckException) {
            return (Class<? extends SecurityCheckException>) securityCheckException.getClass();
        }
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        d = flag;
    }
}
