package remix.compat.legacy.codec;

public interface EncodedStageEvaluator {
    byte[] evaluateSequence(EncodedStageSequence s, boolean flag);
}
