package remix.command;

import java.util.function.Function;

public interface ClientCommandDispatcher {
    ClientCommandRegistry getRegistry();

    ClientCommandOutput getOutput();

    Function<String, String> getPrefixExtractor();

    CommandDispatchResult dispatch(String text);
}
