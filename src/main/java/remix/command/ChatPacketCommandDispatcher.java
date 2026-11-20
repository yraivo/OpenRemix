package remix.command;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C01PacketChatMessage;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.client.RemixClient;

public final class ChatPacketCommandDispatcher extends AbstractClientCommandDispatcher {
    private ClientCommandManager commandManager;

    public ChatPacketCommandDispatcher(ClientCommandManager commandManagerValue) {
        CommandDispatchResult.getLegacyCommandDispatchMarker();
        this.commandManager = commandManagerValue;
        RemixClient.eventBus.register(this);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            CommandDispatchResult.setLegacyCommandDispatchMarker(new Entity[4]);
        }
    }

    public ClientCommandOutput getOutput() {
        return ClientCommandManager.output;
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() != PacketDirection.SEND || !(event.getPacket() instanceof C01PacketChatMessage)) {
            return;
        }
        C01PacketChatMessage chat = (C01PacketChatMessage) event.getPacket();
        CommandDispatchResult result = this.dispatch(chat.getMessage());
        if (result.isHandled()) {
            if (result != CommandDispatchResult.EXECUTED) {
                this.getOutput().printMessage(result.getMessage(), new Object[0]);
            }
            event.cancel();
        }
    }

    public ClientCommandRegistry getRegistry() {
        return this.commandManager.getRegistry();
    }
}
