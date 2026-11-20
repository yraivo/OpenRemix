package remix.compat.legacy.codec;

import java.util.List;

public class DefaultEncodedStageFormatter implements remix.compat.legacy.codec.EncodedStageFormatter {
    private static int[] gy;
    private static String[] packedTextFragments;

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.codec.DefaultEncodedStageFormatter.packedTextFragments[0] = new String("");
    }

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (method_650532ba484532b3efb0() == null) {
            b(new int[4]);
        }
    }

    public static int[] method_650532ba484532b3efb0() {
        return gy;
    }

    public String method_70a5b81b39d503a27868(
            DefaultEncodedStageSequence defaultEncodedStageSequence) {
        new StringBuilder();
        method_650532ba484532b3efb0();

        List list = defaultEncodedStageSequence.getList();

        if (list.isEmpty()) {
            return packedTextFragments[0];
        }

        EncodedStageBlock encodedStageBlock = defaultEncodedStageSequence.getEncodedStageBlock();

        return null;
    }

    public Object method_38047999a7b0535501de(
            EncodedStageSequence encodedStageSequence) {
        return this.method_70a5b81b39d503a27868(
                (DefaultEncodedStageSequence) encodedStageSequence);
    }

    // region String decoding support

    // endregion

    public static void b(int[] aint) {
        gy = aint;
    }
}
