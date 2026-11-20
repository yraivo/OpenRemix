package net.minecraft.command;

public class SyntaxErrorException extends CommandException {
    private static String c;

    public SyntaxErrorException() {
        this(c);
    }

    static {
        c = "commands.generic.snytax";
    }

    // region String decoding support

    // endregion

    public SyntaxErrorException(String text, Object... aobject) {
        super(text, aobject);
    }

    // region String decoding support

    // endregion
}
