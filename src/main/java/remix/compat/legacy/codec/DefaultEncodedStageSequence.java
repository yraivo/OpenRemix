package remix.compat.legacy.codec;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultEncodedStageSequence implements EncodedStageSequence {
    private List<ByteTransformNode> list;
    private EncodedStageBlock encodedStageBlock;

    public String formatSequence(boolean compressSteps) {
        boolean standardFlow = EncodedStageBlock.isStandardBlockFlow();
        StringBuilder text = new StringBuilder();
        List<ByteTransformNode> stages = this.getList();
        if (stages.isEmpty()) {
            return text.toString();
        }

        text.append('=');
        int repeated = 0;
        boolean firstInBlock = true;
        boolean previousFlag = false;
        boolean previousWide = false;
        ByteTransformNode node = stages.get(0);
        while (node != null) {
            int operation = node.getOperationCode();
            ByteTransformNode next = node.getNextStage();
            switch (operation) {
                case -1:
                    text.append(';');
                    if (standardFlow) {
                        break;
                    }
                case 1:
                    text.append('(');
                    if (standardFlow) {
                        break;
                    }
                case 0:
                    text.append(')');
                    firstInBlock = true;
                    if (standardFlow) {
                        break;
                    }
                case 2:
                    int[] differences = ((ArrayDiffByteTransformStage) node).getDifferenceIndices();
                    text.append('#').append(Arrays.toString(expandDifferences(differences, standardFlow)));
                    if (standardFlow) {
                        break;
                    }
                case 3:
                case 4:
                    boolean flagged = ((StepByteTransformStage) node).isDoubleStep();
                    boolean wide = operation == 4;
                    repeated = appendStep(text, next, compressSteps, standardFlow, firstInBlock,
                            previousFlag, previousWide, flagged, wide, repeated);
                    firstInBlock = false;
                    previousFlag = flagged;
                    previousWide = wide;
                    break;
                default:
                    break;
            }
            node = next;
            if (!standardFlow) {
                break;
            }
        }

        return text.toString();
    }

    private static int[] expandDifferences(int[] differences, boolean standardFlow) {
        int previous = -1;
        int[] values = new int[differences.length];
        for (int index = 0; index < differences.length; index++) {
            int difference = differences[index];
            if (previous == -1) {
                previous = difference;
                if (standardFlow) {
                    values[index] = previous;
                    continue;
                }
            }
            previous -= difference;
            values[index] = previous;
            if (!standardFlow) {
                break;
            }
        }
        return values;
    }

    private int appendStep(
            StringBuilder text, ByteTransformNode next, boolean compressSteps, boolean standardFlow,
            boolean firstInBlock, boolean previousFlag, boolean previousWide, boolean flagged,
            boolean wide, int repeated) {
        if (compressSteps) {
            boolean appendCurrent = true;
            if (!firstInBlock && previousFlag == flagged && previousWide == wide) {
                appendCurrent = next instanceof StepByteTransformStage;
                if (appendCurrent || ++repeated == 0) {
                    return repeated;
                }
                text.append(this.selectStepToken(previousFlag, previousWide)).append(repeated);
                repeated = 0;
                if (standardFlow) {
                    return repeated;
                }
            }

            if (repeated != 0) {
                text.append(this.selectStepToken(previousFlag, previousWide)).append(repeated);
                repeated = 0;
            }
            if (next instanceof StepByteTransformStage) {
                boolean nextWide = next.getOperationCode() == 4;
                boolean nextFlag = ((StepByteTransformStage) next).isDoubleStep();
                if (nextFlag == flagged && nextWide == wide) {
                    appendCurrent = false;
                }
            }
            if (!appendCurrent) {
                return repeated;
            }

            text.append(this.selectStepToken(flagged, wide));
            if (standardFlow) {
                return repeated;
            }
        }

        text.append(this.selectStepToken(flagged, wide));
        return repeated;
    }

    public DefaultEncodedStageSequence(
            List<ByteTransformNode> list, EncodedStageBlock bWkValue) {
        this.list = list == null ? Collections.emptyList() : list;
        this.encodedStageBlock = bWkValue;
    }

    public EncodedStageBlock getEncodedStageBlock() {
        return this.encodedStageBlock;
    }

    public List<ByteTransformNode> getList() {
        return this.list;
    }
}
