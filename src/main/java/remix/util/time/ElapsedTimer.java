package remix.util.time;

public final class ElapsedTimer {
    private static int[] gy;
    private long lastResetMillis = this.currentTimeMillis();

    public long currentTimeMillis() {
        long i = System.nanoTime();

        return i / 1000000L;
    }

    public boolean hasElapsed(long delayMillis) {
        return this.currentTimeMillis() - this.lastResetMillis >= delayMillis;
    }

    public static void b(int[] aint) {
        gy = aint;
    }

    public void reset() {
        this.lastResetMillis = this.currentTimeMillis();
    }

    public long elapsedMillis() {
        return this.currentTimeMillis() - this.lastResetMillis;
    }

    // region Static initialization and string tables
    static {
        if (fM() == null) {
            b(new int[1]);
        }
    }

    // endregion

    public static int[] fM() {
        return gy;
    }

    public boolean elapsedAndReset(long delayMillis) {
        if (this.currentTimeMillis() - this.lastResetMillis >= delayMillis) {
            this.reset();
            return true;
        } else {
            return false;
        }
    }
}
