package remix.command;

import java.util.Arrays;

public final class ClientCommandManager {
    private ClientCommandDispatcher dispatcher;
    private final ClientCommandRegistry registry = new DefaultClientCommandRegistry();
    public static final ClientCommandOutput output = new ChatCommandOutput();

    public ClientCommandDispatcher getDispatcher() {
        return this.dispatcher;
    }

    public ClientCommandRegistry getRegistry() {
        return this.registry;
    }

    public static ClientCommandManager create() {
        return new ClientCommandManager();
    }

    private ClientCommandManager() {
        this.registry
                .getCommands()
                .asList()
                .addAll(
                        Arrays.asList(
                                new ToggleClientCommand(),
                                new BindClientCommand(),
                                new ClearChatClientCommand(),
                                new PrefixClientCommand(),
                                new SpamClientCommand(),
                                new HelpClientCommand(),
                                new ConfigClientCommand(),
                                new FakeHackerClientCommand(),
                                new BookClientCommand(),
                                new AuthmeClientCommand(),
                                new BindsClientCommand(),
                                new TargetsClientCommand(),
                                new PathfindClientCommand(),
                                new PortscanClientCommand(),
                                new VClipClientCommand(),
                                new HClipClientCommand(),
                                new FinalKillCounterClientCommand(),
                                new ClientNameClientCommand(),
                                new NickClientCommand(),
                                new HologramClientCommand(),
                                new MotionStandClientCommand(),
                                new KickallClientCommand(),
                                new VisibleClientCommand(),
                                new FriendClientCommand(),
                                new SettingClientCommand(),
                                new SayingsClientCommand(),
                                new AutoConfigClientCommand(),
                                new TeleportClientCommand(),
                                new KillsultsClientCommand()));
        this.dispatcher = new ChatPacketCommandDispatcher(this);
    }

    public static void printMessage(String text) {
        remix.command.ClientCommandManager.output.printMessage(text, new Object[0]);
    }
}
