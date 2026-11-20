package remix.compat.legacy.codec;

import java.util.Collections;
import java.util.List;

final class EmptyEncodedStageSequence implements EncodedStageSequence {
    private static String[] packedTextFragments;

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.codec.EmptyEncodedStageSequence.packedTextFragments[0] = new String("");
    }

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public List<ByteTransformNode> getList() {
        return Collections.emptyList();
    }

    // region String decoding support

    // endregion

    public String formatSequence(boolean flag) {
        return packedTextFragments[0];
    }

    public EncodedStageBlock getEncodedStageBlock() {
        return new EncodedStageBlock(Collections.emptyList(), false);
    }
}
