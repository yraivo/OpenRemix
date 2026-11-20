package remix.compat.legacy.codec;

final class NoOpEncodedSequenceParser implements EncodedSequenceParser {
    public EncodedStageSequence parseSequence(
            Object object, boolean flag, boolean flag1) {
        return EncodedStageSequence.EMPTY;
    }
}
