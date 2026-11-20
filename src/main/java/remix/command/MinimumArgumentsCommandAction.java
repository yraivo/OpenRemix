package remix.command;

import net.minecraft.entity.Entity;

import java.util.function.Function;

public final class MinimumArgumentsCommandAction extends CallbackCommandAction {
    private int minimumArguments;

    public MinimumArgumentsCommandAction(
            String text, int i, String[] astring, Function<String[], Boolean> function) {
        this(text, i, astring, function, CallbackCommandAction.method_da2ac5c94686ee11e9e6());
    }

    private MinimumArgumentsCommandAction(
            String text,
            int minimumArgumentsValue,
            String[] astring,
            Function<String[], Boolean> function,
            net.minecraft.entity.Entity[] initialFlowMarker) {
        super(text, astring, function);

        this.minimumArguments = minimumArgumentsValue;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            CallbackCommandAction.b(new Entity[4]);
        }
    }

    public int getMinimumArguments() {
        return this.minimumArguments;
    }

    public boolean execute(String[] astring) {
        return astring.length < this.minimumArguments ? false : super.execute(astring);
    }
}
