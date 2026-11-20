package remix.compat.legacy.codec;

import net.minecraft.entity.Entity;

public abstract class ByteTransformStage implements ByteTransformNode {
    private ByteTransformNode previousStage;
    private static int[] legacyTransformMarker;
    private boolean enabled;
    private int mE;
    private ByteTransformNode nextStage;
    private int operationCode;

    public boolean isEnabled() {
        return this.enabled;
    }

    public int aW() {
        return this.mE;
    }

    public ByteTransformStage(
            ByteTransformNode bWuValue,
            ByteTransformNode bWvValue,
            int i,
            int j) {
        getLegacyTransformMarker();
        this.previousStage = bWuValue;
        if (bWuValue != null) {
            bWuValue.setNextStage(this);
        }

        this.nextStage = bWvValue;
        if (bWvValue != null) {
            bWvValue.setPreviousStage(this);
        }

        this.operationCode = i;
        this.mE = j;
        this.enabled = true;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyTransformMarker(new int[3]);
        }
    }

    public int getOperationCode() {
        return this.operationCode;
    }

    public ByteTransformNode getPreviousStage() {
        return this.previousStage;
    }

    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }

    public ByteTransformNode setPreviousStage(
            ByteTransformNode bWuValue) {
        this.previousStage = bWuValue;
        return bWuValue;
    }

    public ByteTransformNode getNextStage() {
        return this.nextStage;
    }

    public ByteTransformNode setNextStage(
            ByteTransformNode bWvValue) {
        this.nextStage = bWvValue;
        return bWvValue;
    }

    public static int[] getLegacyTransformMarker() {
        return remix.compat.legacy.codec.ByteTransformStage.legacyTransformMarker;
    }

    public static void setLegacyTransformMarker(int[] aint) {
        remix.compat.legacy.codec.ByteTransformStage.legacyTransformMarker = aint;
    }

    public ByteTransformStage(int i, int j) {
        this.previousStage = null;
        this.nextStage = null;
        this.operationCode = i;
        this.mE = j;
        this.enabled = true;
    }

    static {
        if (getLegacyTransformMarker() == null) {
            setLegacyTransformMarker(new int[3]);
        }
    }
}
