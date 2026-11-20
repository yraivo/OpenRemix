package remix.command;

import remix.command.ClientCommand;
import remix.client.ClientServices;
import remix.client.RemixClient;

import java.util.function.Function;

public abstract class AbstractClientCommandDispatcher implements ClientCommandDispatcher {

    private static String[] packedTextFragments;

    private static Function<String, String> prefixExtractor;

    // region String decoding support

    // endregion

    public CommandDispatchResult dispatch(String message) {
        CommandDispatchResult.getLegacyCommandDispatchMarker();
        String prefix = this.getPrefixExtractor().apply(message);
        if (prefix.isEmpty()) {
            return CommandDispatchResult.NOT_A_COMMAND;
        }

        String[] arguments = message.substring(prefix.length()).split(packedTextFragments[0]);
        boolean executed = false;
        String alias = arguments.length == 0 ? "" : arguments[0];
        if (!alias.isEmpty()) {
            for (Object entry : this.getRegistry().getCommands().asList()) {
                ClientCommand command = (ClientCommand) entry;
                for (String candidate : command.getAliases()) {
                    if (candidate.equalsIgnoreCase(alias)) {
                        if (!command.execute(arguments)) {
                            return new CommandDispatchResult("Usage: " + command.getUsage());
                        }
                        executed = true;
                        break;
                    }
                }
            }
        }
        return executed ? CommandDispatchResult.EXECUTED
                : new CommandDispatchResult("Command not found, use " + prefix + "help for the full command list");
    }

    public Function<String, String> getPrefixExtractor() {
        return remix.command.AbstractClientCommandDispatcher.prefixExtractor;
    }

    // region String decoding support

    // endregion

    static {
        remix.command.AbstractClientCommandDispatcher.packedTextFragments = new String[3];
        remix.command.AbstractClientCommandDispatcher.packedTextFragments[0] = new String(" ");
        remix.command.AbstractClientCommandDispatcher.packedTextFragments[1] = new String("");
        remix.command.AbstractClientCommandDispatcher.prefixExtractor =
                sx -> {
                    String s1x = ((ClientServices) RemixClient.INSTANCE.getClientServices()).commandPrefix;
                    return sx.startsWith(s1x) ? s1x : packedTextFragments[1];
                };
    }

    // region String decoding support

    // endregion

}
