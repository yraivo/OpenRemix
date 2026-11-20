package remix.command;

import net.minecraft.entity.Entity;

public final class CommandDispatchResult {
    public static CommandDispatchResult NOT_A_COMMAND;
    public static CommandDispatchResult EXECUTED;
    private static Entity[] legacyCommandDispatchMarker;

    private boolean handled;
    private String message;

    public static void setLegacyCommandDispatchMarker(Entity[] entityArray) {
        remix.command.CommandDispatchResult.legacyCommandDispatchMarker = entityArray;
    }

    static {
        Entity[] aclass_b6df81f333fa31dc98ea = new Entity[1];
        setLegacyCommandDispatchMarker(aclass_b6df81f333fa31dc98ea);
        NOT_A_COMMAND = new CommandDispatchResult("Invalid Message", false);
        EXECUTED = new CommandDispatchResult("Executed Corretly", true);
    }

    public static Entity[] getLegacyCommandDispatchMarker() {
        return remix.command.CommandDispatchResult.legacyCommandDispatchMarker;
    }

    public boolean isHandled() {
        return this.handled;
    }

    private CommandDispatchResult(String text, boolean handledValue) {
        this.message = text;
        this.handled = handledValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getMessage() {
        return this.message;
    }

    public CommandDispatchResult(String text) {
        this(text, true);
    }
}
