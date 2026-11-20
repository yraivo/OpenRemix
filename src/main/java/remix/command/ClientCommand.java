package remix.command;

import net.minecraft.client.MinecraftClient;

import java.util.Set;

public interface ClientCommand {
    MinecraftClient ec = MinecraftClient.getMinecraft();

    boolean execute(String[] astring);

    String getUsage();

    String[] getAliases();

    Set<CommandAction> getActions();

    String getName();
}
