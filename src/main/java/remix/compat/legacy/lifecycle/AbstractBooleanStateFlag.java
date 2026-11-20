package remix.compat.legacy.lifecycle;

import java.io.Serializable;

public abstract class AbstractBooleanStateFlag implements Serializable {
    private boolean il;
    private static int[] gy;

    public AbstractBooleanStateFlag(boolean flag) {
        this.il = flag;
    }

    static {
        method_650532ba484532b3efb0();
    }

    public static int[] method_650532ba484532b3efb0() {
        return gy;
    }

    public boolean aY() {
        return this.il;
    }

    public static void b(int[] aint) {
        gy = aint;
    }

    public void method_5a0449839d28b4a71843(boolean flag) {
        this.il = flag;
    }
}
