package remix.compat.legacy.codec;

public interface EncodedValueCodec<S extends EncodedStageSequence, T> {
    EncodedValueDecoder<S, T> getDecoder();

    EncodedValueCodecConfig getConfiguration();

    EncodedValueEncoder getEncoder();
}
