package remix.module.hidden;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import remix.server.hypixel.HypixelServerDetector;
import remix.server.hypixel.MegaWallsScoreTracker;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.client.RemixClient;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.ModuleMetadata;

public final class MegaWallsKillTrackerModule extends ModuleMetadata {

    public MegaWallsScoreTracker megaWallsScoreTracker;
    private static String[] packedTextFragments;

    public MegaWallsKillTrackerModule() {
        super(
                packedTextFragments[0],
                new ModuleDefaults(ModuleCategory.HIDDEN).visible(false));
        RemixClient.eventBus.register(this);
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (HypixelServerDetector.isOnHypixel() && event.getDirection() == PacketDirection.RECEIVED) {
            Packet packet = event.getPacket();
            if (packet instanceof S02PacketChat) {
                S02PacketChat s02PacketChat = (S02PacketChat) packet;
                String text = s02PacketChat.getChatComponent().getUnformattedText();
                if (text.equals("                                 Mega Walls")) {
                    this.megaWallsScoreTracker = null;
                }

                if (text.equals("       You have 6 minutes until the walls fall down!")) {
                    this.megaWallsScoreTracker = new MegaWallsScoreTracker();
                }

                if (this.megaWallsScoreTracker != null) {
                    this.megaWallsScoreTracker.onChatPacket(s02PacketChat);
                }
            }
        }
    }

    static {
        remix.module.hidden.MegaWallsKillTrackerModule.packedTextFragments = new String[2];
        remix.module.hidden.MegaWallsKillTrackerModule.packedTextFragments[0] = new String("");
    }
}
