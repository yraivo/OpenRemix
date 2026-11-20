package remix.compat.legacy.codec;

import java.util.Random;

public interface EncodedValueEncoder {
    void clearEmittedStages();

    int getDifferenceIndexLimit();

    java.util.List getList();

    ValueByteSerializer getValueByteSerializer();

    EncodedStageSequence encodeSequence(Object t);

    Random getRandom();

    boolean usesAlternateBlockOrder();
}
