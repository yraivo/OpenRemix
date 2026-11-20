package remix.command;

import java.util.function.Function;

public interface CommandAction {
    String[] getAliases();

    String getName();

    static CommandAction callback(Function<String[], Boolean> function) {
        return new CallbackCommandAction(null, new String[0], function);
    }

    boolean execute(String[] astring);

    static CommandAction requiringArguments(int i, Function<String[], Boolean> function) {
        return new MinimumArgumentsCommandAction(null, i, new String[0], function);
    }
}
