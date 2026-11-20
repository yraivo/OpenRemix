package remix.module.hidden;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.EnumChatFormatting;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.client.RemixClient;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;

public final class MiniWitherStatusModule extends ModuleBase {
    public boolean objectiveDestroyed;


    public MiniWitherStatusModule() {
        super(
                "HardcoreStatus",
                new ModuleDefaults(ModuleCategory.HIDDEN).keyBind(0));
        RemixClient.eventBus.register(this);
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() == PacketDirection.RECEIVED) {
            Packet packet = event.getPacket();
            if (packet instanceof S45PacketTitle) {
                S45PacketTitle s45PacketTitle = (S45PacketTitle) packet;
                ChatComponent chatComponent = s45PacketTitle.getMessage();
                if (chatComponent != null) {
                    String text =
                            EnumChatFormatting.getTextWithoutFormattingCodes(
                                    chatComponent.getUnformattedText());
                    if (text != null
                            && (text.equals("Your Mini Wither died!")
                                    || text.equals("Your Wither died!")
                                    || text.equals("BED DESTROYED!"))) {
                        this.objectiveDestroyed = true;
                    }
                }
            }
        }
    }
}
