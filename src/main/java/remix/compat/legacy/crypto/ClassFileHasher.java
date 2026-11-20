package remix.compat.legacy.crypto;

import remix.compat.legacy.http.CodeSourceWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum ClassFileHasher {
    bXm;
    private static ClassFileHasher[] bXl;

    private static boolean e;
    private static String[] packedTextFragments;

    private static String c;

    private static String b;
    private static String a;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    private void method_642a4deedfe021418d5c(
            MessageDigest messagedigest, StringBuilder stringbuilder) {
        byte[] abyte = messagedigest.digest();
        method_d87039b07bd6961907f2();

        int i = 0;
        int j = abyte.length;

        while (i < j) {
            stringbuilder.append(Integer.toString((abyte[i] & 255) + 256, 16).substring(1));
            i++;
        }
    }

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = method_b417d7500ec59d69e373();
        return !flag;
    }

    public static boolean isRunningFromPackagedClasses() {
        int i = remix.compat.compiler.JvmBooleanSlots.toInt(method_b417d7500ec59d69e373());
        Class<ClassHashTypeAnchor> oclass = ClassHashTypeAnchor.class;

        URL url = oclass.getResource(oclass.getSimpleName() + c);
        String text = url != null ? url.getProtocol() : packedTextFragments[0];
        String textValue = oclass.getName().replace('.', '/');
        String textValue2 = oclass.getResource(new String("/") + textValue + c).toString();
        i =
                !new File(new CodeSourceWrapper(oclass).d()).isFile()
                                && !text.startsWith(b)
                                && !text.startsWith(a)
                                && !textValue2.startsWith(b)
                        ? 0
                        : 1;

        return remix.compat.compiler.JvmBooleanSlots.fromInt(i);
    }

    public String method_e773bbfafdde98105a2f(File file1)
            throws IOException, NoSuchAlgorithmException {
        method_b417d7500ec59d69e373();

        if (file1 == null) {
            return null;
        }

        StringBuilder stringbuilder = new StringBuilder();
        MessageDigest messagedigest = MessageDigest.getInstance(new String("MD5"));
        FileInputStream fileinputstream = new FileInputStream(file1);
        Throwable throwable = null;
        boolean flag1 = false /* VF: Semaphore variable */;

        try {
            flag1 = true;
            byte[] abyte = new byte[1024];

            int i;
            while ((i = fileinputstream.read(abyte)) != -1) {
                messagedigest.update(abyte, 0, i);
            }

            this.method_642a4deedfe021418d5c(messagedigest, stringbuilder);
            flag1 = false;
        } catch (Throwable throwable3) {
            throwable = throwable3;
            throw throwable3;
        } finally {
            if (flag1) {
                label78:
                if (fileinputstream != null) {
                    if (throwable != null) {
                        try {
                            fileinputstream.close();
                            break label78;
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);

                            break label78;
                        }
                    }

                    fileinputstream.close();
                }
            }
        }

        if (fileinputstream != null) {
            if (throwable != null) {
                try {
                    fileinputstream.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
            } else {
                fileinputstream.close();
            }
        }

        return stringbuilder.toString();
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        e = flag;
    }

    public static boolean method_b417d7500ec59d69e373() {
        return e;
    }

    static {
        bXl = new ClassFileHasher[] {bXm};
        a = new String("rsrc:");
        b = new String("jar:");
        c = new String(".class");
    }

    // region String decoding support

    // endregion

    private static ClassFileHasher[] __recovery_values171() {
        return bXl.clone();
    }

    private ClassFileHasher() {
        if (ordinal() == 0) {
            initializeConstantState();
        }
    }

    // Initialize shared state before the first constant is published.
    private static void initializeConstantState() {
        remix.compat.legacy.crypto.ClassFileHasher.packedTextFragments = new String[3];
        remix.compat.legacy.crypto.ClassFileHasher.packedTextFragments[0] = new String("");
        method_a8745b073fa8adfa1857(false);
    }
}
