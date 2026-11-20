package remix.command;

import remix.command.ClientCommand;
import remix.util.collection.FluentListWrapper;

public interface ClientCommandRegistry {
    String[] getAliasesFor(String text);

    ClientCommand findByAlias(String text);

    FluentListWrapper getCommands();
}
