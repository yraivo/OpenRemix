package remix.compat.legacy.codec;

import java.util.ArrayList;
import java.util.List;

public final class DefaultEncodedStageEvaluator implements remix.compat.legacy.codec.EncodedStageEvaluator {

    private static String unexpectedOperationPrefix;
    private static int legacyEvaluatorState;

    // region String decoding support

    // endregion

    public static int getLegacyEvaluatorToken() {
        getLegacyEvaluatorState();

        return (8);
    }

    static {
        getLegacyEvaluatorState();

        remix.compat.legacy.codec.DefaultEncodedStageEvaluator.unexpectedOperationPrefix = "Unexpected value: ";
    }

    public static int getLegacyEvaluatorState() {
        return remix.compat.legacy.codec.DefaultEncodedStageEvaluator.legacyEvaluatorState;
    }

    public byte[] evaluateBlocks(
            DefaultEncodedStageSequence sequence, boolean alternateBlockOrder) {
        List<Byte> output = new ArrayList<>();
        List<ByteTransformNode> markers = sequence.getList();
        EncodedStageBlock currentBlock = sequence.getEncodedStageBlock();
        EncodedStageBlock previousBlock = null;
        getLegacyEvaluatorToken();

        byte pendingDifference = 0;
        byte value = 0;
        byte previousValue = 0;
        int markerCount = markers.size();
        for (int index = 0; index < markerCount; index++) {
            ByteTransformNode marker = markers.get(index);
            int operation = marker.getOperationCode();
            if (operation == -1) {
                break;
            }

            switch (operation) {
                case 0:
                    if (pendingDifference != 0) {
                        if (alternateBlockOrder && currentBlock.isReversed()) {
                            value += pendingDifference;
                        } else {
                            value -= pendingDifference;
                        }
                        pendingDifference = 0;
                    }
                    output.add(value);
                    previousValue = value;
                    value = 0;
                    previousBlock = currentBlock;
                    currentBlock = currentBlock.getEncodedStageBlock();
                    break;
                case 1:
                    List<ByteTransformNode> steps = currentBlock.getList();
                    boolean reversed = currentBlock.isReversed();
                    int stepCount = steps.size();
                    if (reversed) {
                        for (int step = stepCount - 1; step >= 0; step--) {
                            value = steps.get(step).applyTransform(value, previousValue);
                        }
                    } else {
                        for (int step = 0; step < stepCount; step++) {
                            value = steps.get(step).applyTransform(value, previousValue);
                        }
                    }
                    break;
                case 2:
                    int[] differenceIndices = ((ArrayDiffByteTransformStage) marker).getDifferenceIndices();
                    List<ByteTransformNode> previousSteps = previousBlock.getList();
                    for (int step : differenceIndices) {
                        pendingDifference += previousSteps.get(step).applyTransform((byte) 1, (byte) 0);
                    }
                    break;
                default:
                    throw new IllegalStateException(remix.compat.legacy.codec.DefaultEncodedStageEvaluator.unexpectedOperationPrefix + operation);
            }
        }

        byte[] bytes = new byte[output.size()];
        for (int index = 0; index < bytes.length; index++) {
            bytes[index] = output.get(index);
        }
        return bytes;
    }

    public static void setLegacyEvaluatorState(int i) {
        remix.compat.legacy.codec.DefaultEncodedStageEvaluator.legacyEvaluatorState = i;
    }

    public byte[] evaluateSequence(
            EncodedStageSequence encodedStageSequence, boolean flag) {
        return this.evaluateBlocks(
                (DefaultEncodedStageSequence) encodedStageSequence, flag);
    }

    // region String decoding support

    // endregion
}
