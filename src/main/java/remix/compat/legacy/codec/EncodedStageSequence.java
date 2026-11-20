package remix.compat.legacy.codec;

import java.util.List;

public interface EncodedStageSequence {
    char DECREMENT_TOKEN = '.';
    char END_SEQUENCE_TOKEN = ';';
    char START_SEQUENCE_TOKEN = '=';
    char DOUBLE_DECREMENT_TOKEN = '"';
    char INCREMENT_TOKEN = '\'';
    char ARRAY_DIFFERENCE_TOKEN = '#';
    char START_BLOCK_TOKEN = '(';
    char LEGACY_UNDERSCORE_TOKEN = '_';
    char END_BLOCK_TOKEN = ')';
    EncodedStageSequence EMPTY = new EmptyEncodedStageSequence();
    char DOUBLE_INCREMENT_TOKEN = ':';

    EncodedStageBlock getEncodedStageBlock();

    default char selectStepToken(boolean flag, boolean flag1) {
        boolean flag3 = EncodedStageBlock.isStandardBlockFlow();

        boolean flag2 = flag3;
        if (flag1 && flag) {
            char c0 = '"';
            if (flag2) {
                return c0;
            }
        }

        if (!flag1 && flag) {
            char c1 = ':';
            if (flag2) {
                return c1;
            }
        }

        if (flag1) {
            char c2 = '.';
            if (flag2) {
                return c2;
            }
        }

        return '\'';
    }

    String formatSequence(boolean flag);

    List<ByteTransformNode> getList();
}
