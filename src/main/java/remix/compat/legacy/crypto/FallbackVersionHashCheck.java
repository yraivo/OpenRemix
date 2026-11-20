package remix.compat.legacy.crypto;

import remix.util.task.NamedValueHolder;

public final class FallbackVersionHashCheck extends AbstractVersionHashCheck {
    private static String[] packedTextFragments;

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.crypto.FallbackVersionHashCheck.packedTextFragments[0] = new String("");
    }

    // endregion

    public Double method_77ca1ae9ef52990e8495(
            NamedValueHolder namedValueHolder) {
        return (Double) namedValueHolder.setValue(-1022.0);
    }

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public FallbackVersionHashCheck() {
        super(packedTextFragments[0]);
    }
}
