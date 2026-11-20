package remix.compat.legacy.codec;

public abstract class AbstractBinaryByteTransformStage extends ByteTransformStage {
    public AbstractBinaryByteTransformStage(
            ByteTransformNode byteTransformNode,
            ByteTransformNode byteTransformNodeValue,
            int i,
            int j) {
        super(byteTransformNode, byteTransformNodeValue, i, j);
    }

    public AbstractBinaryByteTransformStage(int i, int j) {
        super(i, j);
    }
}
