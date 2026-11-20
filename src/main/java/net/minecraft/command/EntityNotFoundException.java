package net.minecraft.command;

public class EntityNotFoundException extends CommandException {
    private static String c;

    public EntityNotFoundException() {
        this(c);
    }

    static {
        c = "commands.generic.entity.notFound";
    }

    // region String decoding support

    // endregion

    public EntityNotFoundException(String text, Object... aobject) {
        super(text, aobject);
    }

    // region String decoding support

    // endregion
}
