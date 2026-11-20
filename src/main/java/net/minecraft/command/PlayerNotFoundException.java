package net.minecraft.command;

public class PlayerNotFoundException extends CommandException {
    private static String c;

    static {
        c = "commands.generic.player.notFound";
    }

    // region String decoding support

    // endregion

    public PlayerNotFoundException(String text, Object... aobject) {
        super(text, aobject);
    }

    public PlayerNotFoundException() {
        this(c);
    }

    // region String decoding support

    // endregion
}
