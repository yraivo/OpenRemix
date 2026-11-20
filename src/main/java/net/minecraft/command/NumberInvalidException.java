package net.minecraft.command;

public class NumberInvalidException extends CommandException {

    private static String c;

    // region String decoding support

    // endregion

    public NumberInvalidException(String text, Object... aobject) {
        super(text, aobject);
    }

    // region String decoding support

    // endregion

    public NumberInvalidException() {
        this(c);
    }

    static {
        c = "commands.generic.num.invalid";
    }
}
