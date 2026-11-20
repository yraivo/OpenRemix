package remix.compat.legacy.codec;

public abstract class AbstractUnaryByteTransformStage extends ByteTransformStage {
    public AbstractUnaryByteTransformStage(
            ByteTransformNode byteTransformNode,
            ByteTransformNode byteTransformNodeValue,
            int i,
            int j) {
        super(byteTransformNode, byteTransformNodeValue, i, j);
    }

    public AbstractUnaryByteTransformStage(int i, int j) {
        super(i, j);
    }
}
