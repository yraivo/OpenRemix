package net.minecraft.entity.ai;

public abstract class EntityAIBase {
    private int mutexBits;
    private static String[] packedTextFragments;
    private static String b;

    public static String cJ() {
        return b;
    }

    public static void b(String text) {
        b = text;
    }

    public void startExecuting() {}

    public boolean isInterruptible() {
        return true;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (cJ() == null) {
            b(packedTextFragments[0]);
        }
    }

    public void setMutexBits(int mutexBitsValue) {
        this.mutexBits = mutexBitsValue;
    }

    // region String decoding support

    // endregion

    public boolean continueExecuting() {
        return this.shouldExecute();
    }

    public void updateTask() {}

    public void resetTask() {}

    public abstract boolean shouldExecute();

    public int getMutexBits() {
        return this.mutexBits;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.entity.ai.EntityAIBase.packedTextFragments[0] = new String("EZLjtb");
    }
    // endregion
}
