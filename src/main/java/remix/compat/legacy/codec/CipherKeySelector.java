package remix.compat.legacy.codec;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentIterableList;
import remix.util.collection.FluentListWrapper;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public final class CipherKeySelector {
    private String identityText;
    private static FluentIterableList bTa;

    private String c;
    private static boolean d;

    public String method_c60693f457aa70be7160() {
        return this.c;
    }

    public static boolean method_d87039b07bd6961907f2() {
        return d;
    }

    static {
        method_a8745b073fa8adfa1857(false);
        bTa = new FluentIterableList();
        FluentListWrapper class_3df882c73d22946b27fc =
                FluentCollections.method_e2cb25ad6dbcdd481b22(
                        Arrays.asList(new String("AES"), new String("RC4"), new String("RC2")));
        int l = 0;
        for (int i1 = class_3df882c73d22946b27fc.size(); l < i1; l++) {
            bTa.a(class_3df882c73d22946b27fc.getElementAt(l));
        }
    }

    public static boolean d() {
        boolean flag = method_d87039b07bd6961907f2();
        return !flag;
    }

    public CipherKeySelector(String text, boolean flag) {
        this.identityText = text;
        this.c =
                flag
                        ? (String)
                                bTa.method_f2d74290405e3deed647(
                                        ThreadLocalRandom.current()
                                                .nextInt(0, bTa.method_7eec03da329a017ec143()))
                        : "AES";
    }

    // region String decoding support

    // endregion

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        d = flag;
    }

    public String getIdentityText() {
        return this.identityText;
    }

    // region String decoding support

    // endregion

}
