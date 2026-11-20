package remix.compat.legacy.codec;

import java.util.List;
import java.util.Random;

public final class EncodedValueCodecConfig<S extends EncodedStageSequence, T> {
    private Random random;
    private static boolean legacyCodecConfigFlag;
    private EncodedSequenceParser encodedSequenceParser;
    private ValueByteSerializer valueByteSerializer;
    private boolean readRepeatCounts;
    private EncodedStageEvaluator encodedStageEvaluator;
    private boolean alternateBlockOrder;
    private List<ByteTransformNode> list;
    private int differenceIndexLimit;
    private ByteCipherRegistry<T> byteCipherRegistry;

    public EncodedSequenceParser getEncodedSequenceParser() {
        return this.encodedSequenceParser;
    }

    public List<ByteTransformNode> getList() {
        return this.list;
    }

    EncodedValueCodecConfig(
            ValueByteSerializer valueByteSerializerValue,
            List list,
            ByteCipherRegistry byteCipherRegistryValue,
            EncodedSequenceParser encodedSequenceParserValue,
            EncodedStageEvaluator encodedStageEvaluatorValue,
            boolean flag,
            boolean flag1,
            int i,
            Random random,
            CodecConfigAccessMarker accessMarker3) {
        this(
                valueByteSerializerValue,
                list,
                byteCipherRegistryValue,
                encodedSequenceParserValue,
                encodedStageEvaluatorValue,
                flag,
                flag1,
                i,
                random);
    }

    public ValueByteSerializer getValueByteSerializer() {
        return this.valueByteSerializer;
    }

    public boolean readsRepeatCounts() {
        return this.readRepeatCounts;
    }

    public Random getRandom() {
        return this.random;
    }

    public ByteCipherRegistry<T> getByteCipherRegistry() {
        return this.byteCipherRegistry;
    }

    public static void setLegacyCodecConfigFlag(boolean flag) {
        remix.compat.legacy.codec.EncodedValueCodecConfig.legacyCodecConfigFlag = flag;
    }

    public static boolean isLegacyCodecConfigFlagClear() {
        boolean flag = isLegacyCodecConfigFlagSet();
        return !flag;
    }

    private EncodedValueCodecConfig(
            ValueByteSerializer bWJValue,
            List<ByteTransformNode> list,
            ByteCipherRegistry<T> bWLValue,
            EncodedSequenceParser bWIValue,
            EncodedStageEvaluator bWKValue,
            boolean flag,
            boolean flag1,
            int i,
            Random random) {
        this.valueByteSerializer = bWJValue;
        this.list = list;
        this.byteCipherRegistry = bWLValue;
        this.encodedSequenceParser = bWIValue;
        this.encodedStageEvaluator = bWKValue;
        this.alternateBlockOrder = flag;
        this.readRepeatCounts = flag1;
        this.differenceIndexLimit = i;
        this.random = random;
    }

    public static final <S extends EncodedStageSequence, T>
            EncodedValueCodecConfigBuilder<S, T> newBuilder() {
        return new EncodedValueCodecConfigBuilder();
    }

    public boolean usesAlternateBlockOrder() {
        return this.alternateBlockOrder;
    }

    static {
        isLegacyCodecConfigFlagClear();
    }

    public EncodedStageEvaluator getEncodedStageEvaluator() {
        return this.encodedStageEvaluator;
    }

    public int getDifferenceIndexLimit() {
        return this.differenceIndexLimit;
    }

    public static boolean isLegacyCodecConfigFlagSet() {
        return remix.compat.legacy.codec.EncodedValueCodecConfig.legacyCodecConfigFlag;
    }
}
