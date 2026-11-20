package net.minecraft.command;

public class CommandException extends Exception {
    private Object[] errorObjects;
    private static boolean b;

    public Object[] getErrorObjects() {
        return this.errorObjects;
    }

    public static boolean method_b417d7500ec59d69e373() {
        return b;
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        b = flag;
    }

    static {
        if (!method_b417d7500ec59d69e373()) {
            method_a8745b073fa8adfa1857(true);
        }
    }

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = method_b417d7500ec59d69e373();
        return !flag;
    }

    public CommandException(String text, Object... aobject) {
        super(text);
        this.errorObjects = aobject;
    }
}
