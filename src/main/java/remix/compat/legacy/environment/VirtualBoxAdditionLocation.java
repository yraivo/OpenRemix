package remix.compat.legacy.environment;

import remix.compat.legacy.invocation.SystemMethodHandleRegistry;
import remix.util.io.FileSupplier;

import net.minecraft.entity.Entity;

import java.io.File;

public enum VirtualBoxAdditionLocation {
    WINDOWS_FILE(
            OperatingSystemType.WINDOWS,
            () -> {
                String sx =
                        !((String) SystemMethodHandleRegistry.bTr.apply("os.arch"))
                                        .equalsIgnoreCase("x86")
                                ? (String) SystemMethodHandleRegistry.bTq.apply("ProgramFiles(X86)")
                                : (String) SystemMethodHandleRegistry.bTq.apply("ProgramFiles");
                return new File(sx + "\\Oracle\\VirtualBox Guest Additions");
            }),
    LINUX_FILE(
            OperatingSystemType.LINUX,
            () -> {
                String sx =
                        !((String) SystemMethodHandleRegistry.bTr.apply("os.arch"))
                                        .equalsIgnoreCase("x86")
                                ? (String) SystemMethodHandleRegistry.bTq.apply("ProgramFiles(X86)")
                                : (String) SystemMethodHandleRegistry.bTq.apply("ProgramFiles");
                return new File(sx + "\\Oracle\\VirtualBox Guest Additions");
            }),
    UNDEFINED_FILE(null, () -> null);

    private OperatingSystemType bXQ;

    private FileSupplier bXP;

    public static VirtualBoxAdditionLocation method_00234a51d56b7da6d046(
            OperatingSystemType operatingSystemType) {
        String text = VmDetectionStatus.method_c60693f457aa70be7160();
        VirtualBoxAdditionLocation[] virtualBoxAdditionLocationArray = values();
        String textValue = text;
        int length = virtualBoxAdditionLocationArray.length;
        int j = 0;

        while (j < length) {
            VirtualBoxAdditionLocation virtualBoxAdditionLocation = virtualBoxAdditionLocationArray[j];
            if (virtualBoxAdditionLocation.aeZ() == operatingSystemType) {
                return virtualBoxAdditionLocation;
            }

            j++;
            if (textValue != null) {
                Entity.setLegacyGlobalFlowMarker(new String[2]);
                break;
            }
        }

        return remix.compat.legacy.environment.VirtualBoxAdditionLocation.UNDEFINED_FILE;
    }

    // region String decoding support

    // endregion

    VirtualBoxAdditionLocation(
            OperatingSystemType bXQValue, FileSupplier bXPValue) {
        this.bXQ = bXQValue;
        this.bXP = bXPValue;
    }

    public FileSupplier afb() {
        return this.bXP;
    }

    // region String decoding support

    // endregion

    public OperatingSystemType aeZ() {
        return this.bXQ;
    }


}
