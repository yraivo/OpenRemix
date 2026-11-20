package remix.compat.legacy.codec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class StringEncodedSequenceParser implements remix.compat.legacy.codec.EncodedSequenceParser {
    private static String legacyParserMarker;
    private static String[] packedTextFragments;

    public EncodedStageSequence parseSequence(
            Object object, boolean flag, boolean flag1) {
        return this.parseStringSequence((String) object, flag, flag1);
    }

    public static void setLegacyParserMarker(String text) {
        remix.compat.legacy.codec.StringEncodedSequenceParser.legacyParserMarker = text;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.codec.StringEncodedSequenceParser.packedTextFragments[0] = new String(",");
        remix.compat.legacy.codec.StringEncodedSequenceParser.packedTextFragments[1] = new String("Fatiob");
    }

    // endregion

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();

        getLegacyParserMarker();
    }

    public DefaultEncodedStageSequence parseStringSequence(
            String text, boolean alternateBlockOrder, boolean readRepeatCounts) {
        getLegacyParserMarker();
        if (text == null || text.isEmpty()) {
            return (DefaultEncodedStageSequence) EncodedStageSequence.EMPTY;
        }

        List<ByteTransformNode> sequence = new ArrayList<>();
        List<ByteTransformNode> blockSteps = new ArrayList<>();
        EncodedStageBlock firstBlock = null;
        EncodedStageBlock lastBlock = null;
        boolean startNewChain = false;
        boolean reversedBlock = false;
        ByteTransformNode lastLinkedStage = null;
        ByteTransformNode lastParsedStage = null;
        char[] tokens = text.toCharArray();

        for (int index = 0; index < tokens.length; index++) {
            char token = tokens[index];
            switch (token) {
                case '=':
                    startNewChain = true;
                    break;
                case ';':
                    lastParsedStage = new MarkerByteTransformStage(lastLinkedStage, lastParsedStage, -1, 0);
                    linkStage(lastLinkedStage, lastParsedStage);
                    sequence.add(lastParsedStage);
                    blockSteps.clear();
                    break;
                case '(':
                    lastParsedStage = new MarkerByteTransformStage(lastLinkedStage, lastParsedStage, 1, 1);
                    linkStage(lastLinkedStage, lastParsedStage);
                    sequence.add(lastParsedStage);
                    break;
                case ')':
                    lastParsedStage = new MarkerByteTransformStage(lastParsedStage, null, 0, 0);
                    linkStage(lastLinkedStage, lastParsedStage);
                    lastLinkedStage = lastParsedStage;
                    if (alternateBlockOrder) {
                        reversedBlock = !reversedBlock;
                        if (reversedBlock) {
                            Collections.reverse(blockSteps);
                        }
                    }
                    if (startNewChain) {
                        firstBlock = new EncodedStageBlock(new ArrayList<>(blockSteps), reversedBlock);
                        lastBlock = firstBlock;
                        startNewChain = false;
                    } else {
                        EncodedStageBlock nextBlock =
                                new EncodedStageBlock(null, new ArrayList<>(blockSteps), reversedBlock);
                        lastBlock.setEncodedStageBlock(nextBlock);
                        lastBlock = nextBlock;
                    }
                    sequence.add(lastParsedStage);
                    blockSteps.clear();
                    break;
                case '#':
                    int[] differences = parseArrayDifferences(text, tokens, index);
                    lastParsedStage =
                            new ArrayDiffByteTransformStage(lastParsedStage, null, 2, 1, differences, lastBlock);
                    linkStage(lastLinkedStage, lastParsedStage);
                    lastLinkedStage = lastParsedStage;
                    sequence.add(lastParsedStage);
                    break;
                case '"':
                case '\'':
                case '.':
                case ':':
                    int operation = token == '"' || token == '.' ? 4 : 3;
                    boolean flaggedStep = token == '"' || token == ':';
                    int remaining = 0;
                    do {
                        lastParsedStage = new StepByteTransformStage(operation, 0, flaggedStep);
                        lastParsedStage.setPreviousStage(lastLinkedStage);
                        linkStage(lastLinkedStage, lastParsedStage);
                        lastLinkedStage = lastParsedStage;
                        blockSteps.add(lastParsedStage);
                        if (readRepeatCounts) {
                            if (index + 1 < tokens.length && remaining == 0) {
                                remaining = readStepRepeatCount(tokens, index + 1);
                            }
                            if (remaining != 0) {
                                remaining--;
                            }
                        }
                    } while (remaining > 0);
                    break;
                default:
                    // Repeat digits and the textual array payload are consumed by their own readers.
                    break;
            }
        }
        return new DefaultEncodedStageSequence(new ArrayList<>(sequence), firstBlock);
    }

    private static void linkStage(ByteTransformNode previous, ByteTransformNode next) {
        if (previous != null) {
            previous.setNextStage(next);
        }
    }

    private static int readStepRepeatCount(char[] tokens, int start) {
        StringBuilder digits = new StringBuilder();
        int index = start;
        char token = tokens[index];
        while (Character.isDigit(token)) {
            digits.append(token);
            // The old format requires a terminator after a run length.
            token = tokens[++index];
        }
        return digits.length() == 0 ? 0 : Integer.parseInt(digits.toString()) + 1;
    }

    private static int[] parseArrayDifferences(String text, char[] tokens, int start) {
        int opening = -1;
        int closing = -1;
        for (int index = start; index < tokens.length; index++) {
            if (tokens[index] == '[') {
                opening = index;
            } else if (opening != -1 && tokens[index] == ']') {
                closing = index;
                break;
            }
        }

        String[] numbers = text.substring(opening + 1, closing).split(packedTextFragments[0]);
        int[] differences = new int[numbers.length];
        int difference = Integer.MAX_VALUE;
        int previous = -1;
        try {
            for (int index = 0; index < numbers.length; index++) {
                int value = Integer.parseInt(numbers[index].trim());
                difference = difference == Integer.MAX_VALUE ? value : previous - value;
                previous = value;
                differences[index] = difference < 0 ? -difference - 1 : difference;
            }
        } catch (NumberFormatException invalidArray) {
            return new int[0];
        }
        return differences;
    }

    private static NumberFormatException preserveInvalidNumberException(
            NumberFormatException numberformatexception) {
        return numberformatexception;
    }

    public static String getLegacyParserMarker() {
        return remix.compat.legacy.codec.StringEncodedSequenceParser.legacyParserMarker;
    }

    // region String decoding support

    // endregion
}
