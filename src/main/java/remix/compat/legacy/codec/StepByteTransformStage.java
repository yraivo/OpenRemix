package remix.compat.legacy.codec;

import java.util.Random;

public final class StepByteTransformStage extends ByteTransformStage {
    private boolean doubleStep;

    public StepByteTransformStage(int i, int j, boolean flag) {
        super(i, j);
        this.doubleStep = flag;
    }

    public Object[] generateEncodingStep(Random random, byte b0, byte b3) {
        byte b1 = -1;
        ByteTransformStage.getLegacyTransformMarker();

        byte b2 = 0;

        int i = b0 - 1 < 0 ? 1 : 0;
        int j = b0 % 2 == 0 ? 1 : 0;
        int k = b0 != 1 && b0 != 0 ? 1 : 0;
        if (k != 0) {
            b2 = remix.compat.compiler.JvmBooleanSlots.toByte(random.nextBoolean());
            b0 = (byte) (b2 != 0 ? (i != 0 ? b0 + 2 : b0 - 2) : (i != 0 ? b0 + 1 : b0 - 1));
            b1 = (byte) (i != 0 ? 4 : 3);

            return new Object[] {
                new StepByteTransformStage(
                        b1, 0, remix.compat.compiler.JvmBooleanSlots.fromInt(b2)),
                b0
            };
        }

        if (j == 0) {
            b0--;
            b1 = 3;
            b2 = 0;
        }

        return new Object[] {
            new StepByteTransformStage(b1, 0, remix.compat.compiler.JvmBooleanSlots.fromInt(b2)), b0
        };
    }

    public byte applyTransform(byte b0, byte b1) {
        ByteTransformStage.getLegacyTransformMarker();

        if (!this.isEnabled()) {
            return b0;
        }

        switch (this.getOperationCode()) {
            case 3:
                if (this.doubleStep) {
                    b0 = (byte) (b0 + 2);

                    break;
                }

                b0++;

                break;

            case 4:
                if (this.doubleStep) {
                    b0 = (byte) (b0 - 2);

                    break;
                }

                b0--;

                break;

            default:
                throw new UnsupportedOperationException();
        }

        return b0;
    }

    public StepByteTransformStage(
            ByteTransformNode byteTransformNode,
            ByteTransformNode byteTransformNodeValue,
            int i,
            int j,
            boolean flag) {
        super(byteTransformNode, byteTransformNodeValue, i, j);
        this.doubleStep = flag;
    }

    public boolean isDoubleStep() {
        return this.doubleStep;
    }
}
