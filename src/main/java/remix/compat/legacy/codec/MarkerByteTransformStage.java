package remix.compat.legacy.codec;

import java.util.Random;

public final class MarkerByteTransformStage extends ByteTransformStage {
    public byte applyTransform(byte b0, byte b1) {
        throw new UnsupportedOperationException();
    }

    public MarkerByteTransformStage(int i, int j) {
        super(i, j);
    }

    public MarkerByteTransformStage(
            ByteTransformNode byteTransformNode,
            ByteTransformNode byteTransformNodeValue,
            int i,
            int j) {
        super(byteTransformNode, byteTransformNodeValue, i, j);
    }

    public Object[] generateEncodingStep(Random random, byte b0, byte b1) {
        throw new UnsupportedOperationException();
    }
}
