package net.minecraft.util;

public class Util {


    public static UtilEnumOS getOSType() {
        String text = System.getProperty("os.name").toLowerCase();
        return text.contains("win")
                ? UtilEnumOS.WINDOWS
                : (text.contains("mac")
                        ? UtilEnumOS.OSX
                        : (text.contains("solaris")
                                ? UtilEnumOS.SOLARIS
                                : (text.contains("sunos")
                                        ? UtilEnumOS.SOLARIS
                                        : (text.contains("linux")
                                                ? UtilEnumOS.LINUX
                                                : (text.contains("unix")
                                                        ? UtilEnumOS.LINUX
                                                        : UtilEnumOS.UNKNOWN)))));
    }
}
