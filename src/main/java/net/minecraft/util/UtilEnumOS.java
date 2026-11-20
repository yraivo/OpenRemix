package net.minecraft.util;

public enum UtilEnumOS {
    LINUX("LINUX", 0),
    SOLARIS("SOLARIS", 1),
    WINDOWS("WINDOWS", 2),
    OSX("OSX", 3),
    UNKNOWN("UNKNOWN", 4);
    private static UtilEnumOS[] ahC;

    UtilEnumOS(String text, int j) {}

    // region String decoding support

    // endregion

    static {
        ahC = new UtilEnumOS[] {net.minecraft.util.UtilEnumOS.LINUX, net.minecraft.util.UtilEnumOS.SOLARIS, net.minecraft.util.UtilEnumOS.WINDOWS, net.minecraft.util.UtilEnumOS.OSX, net.minecraft.util.UtilEnumOS.UNKNOWN};
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
