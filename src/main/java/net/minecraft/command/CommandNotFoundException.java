package net.minecraft.command;

public class CommandNotFoundException extends CommandException {

    private static String c;

    public CommandNotFoundException(String text, Object... aobject) {
        super(text, aobject);
    }

    static {
        c = "commands.generic.notFound";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public CommandNotFoundException() {
        this(c);
    }
}
