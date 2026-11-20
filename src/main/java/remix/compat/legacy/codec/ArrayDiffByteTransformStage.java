package remix.compat.legacy.codec;

import java.util.Random;

public final class ArrayDiffByteTransformStage extends ByteTransformStage {
    private EncodedStageBlock encodedStageBlock;
    private int[] differenceIndices;

    public Object[] generateEncodingStep(Random random, byte b0, byte b1) {
        return new Object[0];
    }

    public ArrayDiffByteTransformStage(
            int i, int j, int[] aint, EncodedStageBlock bWwValue) {
        super(i, j);
        this.differenceIndices = aint;
        this.encodedStageBlock = bWwValue;
    }

    public ArrayDiffByteTransformStage(
            ByteTransformNode byteTransformNode,
            ByteTransformNode byteTransformNodeValue,
            int i,
            int j,
            int[] aint,
            EncodedStageBlock bWwValue) {
        super(byteTransformNode, byteTransformNodeValue, i, j);
        this.differenceIndices = aint;
        this.encodedStageBlock = bWwValue;
    }

    public int[] getDifferenceIndices() {
        return this.differenceIndices;
    }

    public EncodedStageBlock getEncodedStageBlock() {
        return this.encodedStageBlock;
    }

    public byte applyTransform(byte b0, byte b1) {
        return 0;
    }
}
