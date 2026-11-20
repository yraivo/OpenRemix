package remix.compat.legacy.codec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class DefaultEncodedValueEncoder extends remix.compat.legacy.codec.AbstractEncodedValueEncoder {
    private final List<ByteTransformNode> emittedStages = new ArrayList<>();

    public void clearEmittedStages() {
        this.emittedStages.clear();
    }

    public DefaultEncodedValueEncoder(EncodedValueCodecConfig encodedValueCodecConfig) {
        super(encodedValueCodecConfig);
    }

    public DefaultEncodedStageSequence encodeDefaultSequence(Object input) {
        byte[] inputBytes = this.getValueByteSerializer().serializeValue(input);
        Random random = this.getRandom();
        boolean alternateBlockOrder = this.usesAlternateBlockOrder();
        List transformTemplates = this.getList();
        List<ByteTransformNode> emittedStages = this.emittedStages;
        AbstractEncodedValueEncoder.getLegacyEncoderMarker();

        List<ByteTransformNode> steps = new ArrayList<>();
        boolean reversed = false;
        byte differenceForNextByte = 0;
        byte previousRemainder = 0;
        EncodedStageBlock firstBlock = null;
        EncodedStageBlock lastBlock = null;
        ByteTransformNode lastTransform = null;
        ByteTransformNode tail = null;

        for (int byteIndex = 0; byteIndex < inputBytes.length; byteIndex++) {
            tail = new MarkerByteTransformStage(tail, lastTransform, 1, 1);
            emittedStages.add(tail);

            byte remaining = inputBytes[byteIndex];
            if (differenceForNextByte != 0) {
                if (alternateBlockOrder) {
                    remaining = (byte) (reversed
                            ? remaining + differenceForNextByte : remaining - differenceForNextByte);
                } else {
                    remaining += differenceForNextByte;
                }
                differenceForNextByte = 0;
            }
            if (remaining == 0) {
                // The legacy stream emits only the opening marker for a zero byte.
                continue;
            }

            while (remaining != 0) {
                ByteTransformNode template =
                        (ByteTransformNode) transformTemplates.get(random.nextInt(transformTemplates.size()));
                Object[] stepResult =
                        template.generateEncodingStep(random, remaining, previousRemainder);
                lastTransform = (ByteTransformNode) stepResult[0];
                lastTransform.setPreviousStage(tail);
                tail.setNextStage(lastTransform);
                remaining = (Byte) stepResult[1];
                previousRemainder = remaining;
                steps.add(lastTransform);
                tail = lastTransform;
            }

            int stepCount = steps.size();
            if (alternateBlockOrder) {
                reversed = !reversed;
                if (reversed) {
                    Collections.reverse(steps);
                }
            }
            if (lastBlock == null) {
                firstBlock = new EncodedStageBlock(new ArrayList<>(steps), reversed);
                lastBlock = firstBlock;
            } else {
                EncodedStageBlock nextBlock =
                        new EncodedStageBlock(null, new ArrayList<>(steps), reversed);
                lastBlock.setEncodedStageBlock(nextBlock);
                lastBlock = nextBlock;
            }

            tail = new MarkerByteTransformStage(lastTransform, null, 0, 0);
            emittedStages.add(tail);
            if (byteIndex == inputBytes.length - 1) {
                tail = new MarkerByteTransformStage(tail, null, -1, 0);
            } else {
                int configuredLimit = this.getDifferenceIndexLimit();
                int requestedCount = configuredLimit == -1 ? random.nextInt(stepCount) : configuredLimit;
                int[] differenceIndices = new int[Math.min(stepCount, requestedCount)];
                for (int index = 0; index < differenceIndices.length; index++) {
                    int stepIndex = random.nextInt(stepCount);
                    differenceForNextByte += steps.get(stepIndex).applyTransform((byte) 1, (byte) 0);
                    differenceIndices[index] = stepIndex;
                }
                tail = new ArrayDiffByteTransformStage(tail, null, 2, 1, differenceIndices, lastBlock);
                previousRemainder = remaining;
            }
            emittedStages.add(tail);
            steps.clear();
        }
        return new DefaultEncodedStageSequence(new ArrayList<>(emittedStages), firstBlock);
    }

    public EncodedStageSequence encodeSequence(Object object) {
        return this.encodeDefaultSequence((java.lang.Object) object);
    }
}
