package remix.compat.legacy.environment;

import remix.compat.legacy.invocation.SystemMethodHandleRegistry;
import remix.util.task.NamedValueHolder;

public final class WindowsTasklistCommandSupplier extends NamedValueHolder {
    private static int[] rg;

    // region String decoding support

    // endregion

    public static int[] s() {
        return rg;
    }

    // region String decoding support

    // endregion

    public WindowsTasklistCommandSupplier() {
        super(
                (String) SystemMethodHandleRegistry.bTq.apply("windir")
                        + "\\system32\\"
                        + new String("tasklist.exe"),
                new String("Windows"));
    }

    // region String decoding support

    // endregion

    public static void b(int[] aint) {
        rg = aint;
    }

    static {
        b(((int[]) (null)));
    }
}
