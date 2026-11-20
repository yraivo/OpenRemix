package remix.compat.legacy.codec;

public interface EncodedSequenceParser {
    EncodedSequenceParser NO_OP = new NoOpEncodedSequenceParser();

    EncodedStageSequence parseSequence(Object t, boolean flag, boolean flag1);
}
