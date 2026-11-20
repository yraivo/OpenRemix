package remix.compat.legacy.codec;

public interface EncodedValueDecoder<S extends EncodedStageSequence, T> {
    T decodeSequenceWithCipherSelector(S s, CipherIndexSelector cipherIndexSelector);

    boolean readsRepeatCounts();

    T decodeWithCipherSelector(T t, CipherIndexSelector cipherIndexSelector);

    T decodeWithCipher(T t, ByteArrayCipher byteArrayCipher);

    T decodeSequence(S s);

    T decodeSequenceWithCipher(S s, ByteArrayCipher byteArrayCipher);

    T decodeWithCipherIndex(T t, int i);

    ValueByteSerializer getValueSerializer();

    EncodedSequenceParser getSequenceParser();

    boolean usesAlternateBlockOrder();

    T decodeSequenceWithCipherIndex(S s, int i);

    T decode(T t);

    ByteCipherRegistry<T> getCipherRegistry();

    EncodedStageEvaluator getStageEvaluator();
}
