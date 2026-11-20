package remix.compat.legacy.environment;

import net.minecraft.entity.Entity;

import remix.compat.legacy.invocation.SystemMethodHandleRegistry;
import remix.util.io.FileSupplier;

import java.io.File;

public enum VmwareToolsLocation {
    WINDOWS_FILE(
            initializeConstantState(),
            () -> {
                String sx =
                        !((String) SystemMethodHandleRegistry.bTr.apply("os.arch"))
                                        .equalsIgnoreCase("x86")
                                ? (String) SystemMethodHandleRegistry.bTq.apply("ProgramFiles(X86)")
                                : (String) SystemMethodHandleRegistry.bTq.apply("ProgramFiles");
                return new File(sx + "/VMware/VMware Tools");
            }),
    LINUX_FILE(OperatingSystemType.LINUX, () -> new File("/etc/vmware-tools")),
    OSX_FILE(OperatingSystemType.OSX, () -> new File("/Library/Application Support/VMware Tools")),
    UNDEFINED_FILE(null, () -> null);
    private static VmwareToolsLocation[] bXO;

    private static String[] packedTextFragments;
    private FileSupplier bXP;
    private OperatingSystemType bXQ;

    public OperatingSystemType aeZ() {
        return this.bXQ;
    }

    VmwareToolsLocation(OperatingSystemType bXQValue, FileSupplier bXPValue) {
        this.bXQ = bXQValue;
        this.bXP = bXPValue;
    }

    static {
        bXO =
                new VmwareToolsLocation[] {
                    remix.compat.legacy.environment.VmwareToolsLocation.WINDOWS_FILE,
                    remix.compat.legacy.environment.VmwareToolsLocation.LINUX_FILE,
                    remix.compat.legacy.environment.VmwareToolsLocation.OSX_FILE,
                    remix.compat.legacy.environment.VmwareToolsLocation.UNDEFINED_FILE
                };
    }

    public FileSupplier afb() {
        return this.bXP;
    }

    // region String decoding support

    // endregion

    public static VmwareToolsLocation method_d273c5daf5028f542c4a(
            OperatingSystemType operatingSystemType) {
        String text = VmDetectionStatus.method_c60693f457aa70be7160();
        VmwareToolsLocation[] vmwareToolsLocationArray = values();
        String textValue = text;

        for (VmwareToolsLocation vmwareToolsLocation : vmwareToolsLocationArray) {
            if (vmwareToolsLocation.aeZ() == operatingSystemType) {
                return vmwareToolsLocation;
            }

            if (textValue != null) {
                break;
            }
        }

        VmwareToolsLocation vmwareToolsLocationValue =
                remix.compat.legacy.environment.VmwareToolsLocation.UNDEFINED_FILE;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            VmDetectionStatus.b(packedTextFragments[0]);
        }

        return vmwareToolsLocationValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private static VmwareToolsLocation[] __recovery_values171() {
        return bXO.clone();
    }

    // Initialize shared state before the first constant is published.
    private static OperatingSystemType initializeConstantState() {
        remix.compat.legacy.environment.VmwareToolsLocation.packedTextFragments = new String[3];
        remix.compat.legacy.environment.VmwareToolsLocation.packedTextFragments[0] =
                new String("qQX4B");
        return OperatingSystemType.WINDOWS;
    }
}
