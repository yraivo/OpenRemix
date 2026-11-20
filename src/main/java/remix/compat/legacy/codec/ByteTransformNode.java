package remix.compat.legacy.codec;

import java.util.Random;

public interface ByteTransformNode {
    int c = 0;
    int dD = 1;
    int cz = -1;
    int mu = 0;
    int ef = 2;
    int a = -2;
    int Ao = 4;
    int tB = 1;
    int f = 3;
    int sR = 6;

    byte applyTransform(byte b0, byte b1);

    ByteTransformNode setPreviousStage(
            ByteTransformNode byteTransformNode);

    void setEnabled(boolean flag);

    boolean isEnabled();

    ByteTransformNode getPreviousStage();

    ByteTransformNode setNextStage(
            ByteTransformNode byteTransformNode);

    Object[] generateEncodingStep(Random random, byte b0, byte b1);

    ByteTransformNode getNextStage();

    int getOperationCode();

    int aW();
}
