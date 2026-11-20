package remix.compat.legacy.codec;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EncodedValueCodecConfigBuilder<S extends EncodedStageSequence, T> {
    private ValueByteSerializer valueByteSerializer;
    private EncodedStageEvaluator encodedStageEvaluator;
    private EncodedSequenceParser encodedSequenceParser = EncodedSequenceParser.NO_OP;
    private boolean alternateBlockOrder;
    private int differenceIndexLimit;
    private List<ByteTransformNode> list = new ArrayList<>();
    private Random random;
    private ByteCipherRegistry<T> byteCipherRegistry;
    private boolean readRepeatCounts;

    public EncodedValueCodecConfigBuilder<S, T> withRepeatCounts() {
        this.readRepeatCounts = true;
        return this;
    }

    public EncodedValueCodecConfigBuilder<S, T> withDifferenceIndexLimit(int i) {
        this.differenceIndexLimit = i;
        return this;
    }

    public EncodedValueCodecConfigBuilder<S, T> withStageEvaluator(
            EncodedStageEvaluator bWMValue) {
        this.encodedStageEvaluator = bWMValue;
        return this;
    }

    public EncodedValueCodecConfigBuilder<S, T> withRandomDifferenceCount() {
        this.differenceIndexLimit = -1;
        return this;
    }

    public EncodedValueCodecConfigBuilder<S, T> withSequenceParser(
            EncodedSequenceParser bWNValue) {
        this.encodedSequenceParser = bWNValue;
        return this;
    }

    public EncodedValueCodecConfigBuilder<S, T> withRandom(Random random) {
        this.random = random;
        return this;
    }

    public EncodedValueCodecConfigBuilder<S, T> withByteSerializer(
            ValueByteSerializer bWJValue) {
        this.valueByteSerializer = bWJValue;
        return this;
    }

    public EncodedValueCodecConfigBuilder<S, T> withAlternateBlockOrder() {
        this.alternateBlockOrder = true;
        return this;
    }

    public EncodedValueCodecConfig<S, T> build() {
        if (this.valueByteSerializer != null
                && !this.list.isEmpty()
                && this.byteCipherRegistry != null
                && this.encodedSequenceParser != null
                && this.encodedStageEvaluator != null
                && this.random != null) {
            return new EncodedValueCodecConfig(
                    this.valueByteSerializer, this.list, this.byteCipherRegistry, this.encodedSequenceParser, this.encodedStageEvaluator, this.alternateBlockOrder, this.readRepeatCounts, this.differenceIndexLimit,
                    this.random, null);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public EncodedValueCodecConfigBuilder<S, T> withCipherRegistry(
            ByteCipherRegistry<T> bWOValue) {
        this.byteCipherRegistry = bWOValue;
        return this;
    }

    protected EncodedValueCodecConfigBuilder() {
        this.differenceIndexLimit = -1;
    }

    public EncodedValueCodecConfigBuilder<S, T> withSecureRandom() {
        this.random = new SecureRandom();
        return this;
    }

    public EncodedValueCodecConfigBuilder<S, T> addTransformStage(
            ByteTransformNode byteTransformNode) {
        this.list.add(byteTransformNode);
        return this;
    }

    public EncodedValueCodecConfigBuilder<S, T> addTransformStages(
            ByteTransformNode... byteTransformNodeArray) {
        EncodedValueCodecConfig.isLegacyCodecConfigFlagSet();

        this.list.addAll(Arrays.asList(byteTransformNodeArray));

        return this;
    }
}
