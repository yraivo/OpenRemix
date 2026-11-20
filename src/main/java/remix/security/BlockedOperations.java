package remix.security;

/** Explicitly blocked APIs; no shell, native memory, network or global TLS action. */
public final class BlockedOperations {
    private BlockedOperations() {}

    private static SecurityException denied(String operation) {
        return new SecurityException("Disabled in local source build: " + operation);
    }

    public static Process deny1(java.lang.Runtime receiver, String p0) {
        throw denied("java/lang/Runtime.exec(Ljava/lang/String;)Ljava/lang/Process;");
    }

    public static Process deny2(java.lang.Runtime receiver, String[] p0) {
        throw denied("java/lang/Runtime.exec([Ljava/lang/String;)Ljava/lang/Process;");
    }

    public static Process deny3(java.lang.Runtime receiver, String p0, String[] p1) {
        throw denied(
                "java/lang/Runtime.exec(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/Process;");
    }

    public static Process deny4(java.lang.Runtime receiver, String[] p0, String[] p1) {
        throw denied(
                "java/lang/Runtime.exec([Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/Process;");
    }

    public static Process deny5(
            java.lang.Runtime receiver, String p0, String[] p1, java.io.File p2) {
        throw denied(
                "java/lang/Runtime.exec(Ljava/lang/String;[Ljava/lang/String;Ljava/io/File;)Ljava/lang/Process;");
    }

    public static Process deny6(
            java.lang.Runtime receiver, String[] p0, String[] p1, java.io.File p2) {
        throw denied(
                "java/lang/Runtime.exec([Ljava/lang/String;[Ljava/lang/String;Ljava/io/File;)Ljava/lang/Process;");
    }

    public static Process deny7(java.lang.ProcessBuilder receiver) {
        throw denied("java/lang/ProcessBuilder.start()Ljava/lang/Process;");
    }

    public static sun.misc.Unsafe deny8() {
        throw denied("sun/misc/Unsafe.getUnsafe()Lsun/misc/Unsafe;");
    }

    public static byte deny9(sun.misc.Unsafe receiver, long p0) {
        throw denied("sun/misc/Unsafe.getByte(J)B");
    }

    public static void deny10(sun.misc.Unsafe receiver, long p0, byte p1) {
        throw denied("sun/misc/Unsafe.putByte(JB)V");
    }

    public static short deny11(sun.misc.Unsafe receiver, long p0) {
        throw denied("sun/misc/Unsafe.getShort(J)S");
    }

    public static void deny12(sun.misc.Unsafe receiver, long p0, short p1) {
        throw denied("sun/misc/Unsafe.putShort(JS)V");
    }

    public static char deny13(sun.misc.Unsafe receiver, long p0) {
        throw denied("sun/misc/Unsafe.getChar(J)C");
    }

    public static void deny14(sun.misc.Unsafe receiver, long p0, char p1) {
        throw denied("sun/misc/Unsafe.putChar(JC)V");
    }

    public static int deny15(sun.misc.Unsafe receiver, long p0) {
        throw denied("sun/misc/Unsafe.getInt(J)I");
    }

    public static void deny16(sun.misc.Unsafe receiver, long p0, int p1) {
        throw denied("sun/misc/Unsafe.putInt(JI)V");
    }

    public static long deny17(sun.misc.Unsafe receiver, long p0) {
        throw denied("sun/misc/Unsafe.getLong(J)J");
    }

    public static void deny18(sun.misc.Unsafe receiver, long p0, long p1) {
        throw denied("sun/misc/Unsafe.putLong(JJ)V");
    }

    public static float deny19(sun.misc.Unsafe receiver, long p0) {
        throw denied("sun/misc/Unsafe.getFloat(J)F");
    }

    public static void deny20(sun.misc.Unsafe receiver, long p0, float p1) {
        throw denied("sun/misc/Unsafe.putFloat(JF)V");
    }

    public static double deny21(sun.misc.Unsafe receiver, long p0) {
        throw denied("sun/misc/Unsafe.getDouble(J)D");
    }

    public static void deny22(sun.misc.Unsafe receiver, long p0, double p1) {
        throw denied("sun/misc/Unsafe.putDouble(JD)V");
    }

    public static long deny23(sun.misc.Unsafe receiver, long p0) {
        throw denied("sun/misc/Unsafe.getAddress(J)J");
    }

    public static void deny24(sun.misc.Unsafe receiver, long p0, long p1) {
        throw denied("sun/misc/Unsafe.putAddress(JJ)V");
    }

    public static long deny25(sun.misc.Unsafe receiver, long p0) {
        throw denied("sun/misc/Unsafe.allocateMemory(J)J");
    }

    public static long deny26(sun.misc.Unsafe receiver, long p0, long p1) {
        throw denied("sun/misc/Unsafe.reallocateMemory(JJ)J");
    }

    public static void deny27(sun.misc.Unsafe receiver, long p0) {
        throw denied("sun/misc/Unsafe.freeMemory(J)V");
    }

    public static void deny28(sun.misc.Unsafe receiver, long p0, long p1, byte p2) {
        throw denied("sun/misc/Unsafe.setMemory(JJB)V");
    }

    public static void deny29(sun.misc.Unsafe receiver, long p0, long p1, long p2) {
        throw denied("sun/misc/Unsafe.copyMemory(JJJ)V");
    }

    public static void deny30(javax.net.ssl.SSLSocketFactory p0) {
        throw denied(
                "javax/net/ssl/HttpsURLConnection.setDefaultSSLSocketFactory(Ljavax/net/ssl/SSLSocketFactory;)V");
    }

    public static void deny31(javax.net.ssl.HostnameVerifier p0) {
        throw denied(
                "javax/net/ssl/HttpsURLConnection.setDefaultHostnameVerifier(Ljavax/net/ssl/HostnameVerifier;)V");
    }

    public static void deny32(
            javax.net.ssl.HttpsURLConnection receiver, javax.net.ssl.SSLSocketFactory p0) {
        throw denied(
                "javax/net/ssl/HttpsURLConnection.setSSLSocketFactory(Ljavax/net/ssl/SSLSocketFactory;)V");
    }

    public static void deny33(
            javax.net.ssl.HttpsURLConnection receiver, javax.net.ssl.HostnameVerifier p0) {
        throw denied(
                "javax/net/ssl/HttpsURLConnection.setHostnameVerifier(Ljavax/net/ssl/HostnameVerifier;)V");
    }

    public static javax.script.ScriptEngine deny34(
            javax.script.ScriptEngineManager receiver, String p0) {
        throw denied(
                "javax/script/ScriptEngineManager.getEngineByName(Ljava/lang/String;)Ljavax/script/ScriptEngine;");
    }

    public static Object deny35(javax.script.ScriptEngine receiver, String p0) {
        throw denied("javax/script/ScriptEngine.eval(Ljava/lang/String;)Ljava/lang/Object;");
    }

    public static Object deny36(javax.script.Invocable receiver, String p0, Object[] p1) {
        throw denied(
                "javax/script/Invocable.invokeFunction(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;");
    }

    public static void deny37(String p0) {
        throw denied("java/lang/System.loadLibrary(Ljava/lang/String;)V");
    }
}
