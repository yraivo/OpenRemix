package remix.compat.legacy.codec;

import net.minecraft.entity.Entity;

public class DefaultEncodedValueDecoder<S extends EncodedStageSequence, T>
        implements EncodedValueDecoder<S, T> {
    private int decodeCount;
    private ValueByteSerializer valueSerializer;
    private boolean readRepeatCounts;
    private ByteCipherRegistry<T> cipherRegistry;
    private EncodedSequenceParser sequenceParser;
    private boolean alternateBlockOrder;
    private static String[] legacyDecoderMarker;
    private EncodedStageEvaluator stageEvaluator;

    public static void setLegacyDecoderMarker(String[] astring) {
        remix.compat.legacy.codec.DefaultEncodedValueDecoder.legacyDecoderMarker = astring;
    }

    static {
        if (getLegacyDecoderMarker() == null) {
            setLegacyDecoderMarker(new String[2]);
        }
    }

    public T decodeWithCipher(
            T object, ByteArrayCipher byteArrayCipher) {
        return this.decodeSequenceWithCipher(
                (S) this.sequenceParser.parseSequence(object, this.alternateBlockOrder, this.readRepeatCounts),
                byteArrayCipher);
    }

    public T decodeWithCipherSelector(
            T object, CipherIndexSelector cipherIndexSelector) {
        return this.decodeSequenceWithCipherSelector(
                (S) this.sequenceParser.parseSequence(object, this.alternateBlockOrder, this.readRepeatCounts),
                cipherIndexSelector);
    }

    public T decode(T object) {
        EncodedStageSequence encodedStageSequence =
                this.sequenceParser.parseSequence(object, this.alternateBlockOrder, this.readRepeatCounts);
        this.decodeCount++;
        return this.decodeSequence((S) encodedStageSequence);
    }

    public EncodedSequenceParser getSequenceParser() {
        return this.sequenceParser;
    }

    public static String[] getLegacyDecoderMarker() {
        return remix.compat.legacy.codec.DefaultEncodedValueDecoder.legacyDecoderMarker;
    }

    public T decodeSequence(S sequence) {
        return this.decodeSequenceWithCipher((S) sequence, this.cipherRegistry.getByteArrayCipher());
    }

    public boolean usesAlternateBlockOrder() {
        return this.alternateBlockOrder;
    }

    public T decodeWithCipherIndex(T object, int i) {
        return this.decodeSequenceWithCipherIndex(
                (S) this.sequenceParser.parseSequence(object, this.alternateBlockOrder, this.readRepeatCounts), i);
    }

    public DefaultEncodedValueDecoder(EncodedValueCodecConfig<S, T> encodedValueCodecConfig) {
        this.cipherRegistry = encodedValueCodecConfig.getByteCipherRegistry();
        this.valueSerializer = encodedValueCodecConfig.getValueByteSerializer();
        this.sequenceParser = encodedValueCodecConfig.getEncodedSequenceParser();
        this.stageEvaluator = encodedValueCodecConfig.getEncodedStageEvaluator();
        this.alternateBlockOrder = encodedValueCodecConfig.usesAlternateBlockOrder();
        this.readRepeatCounts = encodedValueCodecConfig.readsRepeatCounts();
    }

    public ValueByteSerializer getValueSerializer() {
        return this.valueSerializer;
    }

    public T decodeSequenceWithCipherIndex(S sequence, int i) {
        ByteArrayCipher byteArrayCipher = this.cipherRegistry.getCipherAt(i);
        return this.decodeSequenceWithCipher(
                (S) sequence, byteArrayCipher);
    }

    public T decodeSequenceWithCipher(
            S sequence, ByteArrayCipher byteArrayCipher) {
        EncodedStageEvaluator bWMValue = this.stageEvaluator;
        boolean flag = this.usesAlternateBlockOrder();
        getLegacyDecoderMarker();
        byte[] abyte = bWMValue.evaluateSequence(sequence, flag);
        byte[] abyte1 = byteArrayCipher.decodeBytes(abyte);
        DecodedByteDeserializer decodedByteDeserializer =
                this.cipherRegistry.getDeserializerForCipher(byteArrayCipher);
        Object object = decodedByteDeserializer.deserializeBytes(abyte1, this.valueSerializer);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyDecoderMarker(new String[1]);
        }

        return (T) object;
    }

    public int getDecodeCount() {
        return this.decodeCount;
    }

    public boolean readsRepeatCounts() {
        return this.readRepeatCounts;
    }

    public EncodedStageEvaluator getStageEvaluator() {
        return this.stageEvaluator;
    }

    public T decodeSequenceWithCipherSelector(
            S sequence, CipherIndexSelector cipherIndexSelector) {
        getLegacyDecoderMarker();

        EncodedStageEvaluator bWMValue = this.stageEvaluator;
        boolean flag = this.usesAlternateBlockOrder();
        byte[] abyte = bWMValue.evaluateSequence(sequence, flag);
        ByteArrayCipher byteArrayCipher =
                this.cipherRegistry.selectCipher(abyte, cipherIndexSelector);

        byte[] abyte1 = byteArrayCipher.decodeBytes(abyte);
        DecodedByteDeserializer decodedByteDeserializer =
                this.cipherRegistry.getDeserializerForCipher(byteArrayCipher);
        Object object = decodedByteDeserializer.deserializeBytes(abyte1, this.valueSerializer);

        return (T) object;
    }

    public ByteCipherRegistry<T> getCipherRegistry() {
        return this.cipherRegistry;
    }
}
