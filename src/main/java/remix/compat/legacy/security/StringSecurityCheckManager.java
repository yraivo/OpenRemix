package remix.compat.legacy.security;

import remix.compat.legacy.environment.JvmArgsStringSecurityCheckEntry;
import remix.compat.legacy.invocation.UnsafeFieldTargetSupplier;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

public class StringSecurityCheckManager
        extends SecurityCheckManager<AbstractStringSecurityCheckEntry, String> {
    private static String e;
    private static String[] packedTextFragments;

    public static String method_e0e3092a59fee58fae52() {
        return e;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (method_e0e3092a59fee58fae52() == null) {
            b(packedTextFragments[0]);
        }
    }

    public StringSecurityCheckManager(FluentMapWrapper fluentMapWrapper) {
        this(
                fluentMapWrapper,
                new DefaultSecurityPoolProvider(
                        new IndexedValuePool[] {new StringIndexedArrayPool()}));
        this.getFluentMapWrapper()
                .putEntry(
                        982986, new JvmArgsStringSecurityCheckEntry(new UnsafeFieldTargetSupplier()));
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.security.StringSecurityCheckManager.packedTextFragments[0] = new String("qfGhvc");
    }

    // endregion

    public static void b(String text) {
        e = text;
    }

    public StringSecurityCheckManager(
            FluentMapWrapper fluentMapWrapper,
            SecurityPoolProvider securityPoolProvider) {
        super(fluentMapWrapper, securityPoolProvider);
    }

    public StringSecurityCheckManager() {
        this(FluentCollections.newMutableMap());
    }

    // region String decoding support

    // endregion
}
