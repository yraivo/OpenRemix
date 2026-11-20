package remix.module.hidden;

import com.google.gson.Gson;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponent;

import remix.client.RemixClient;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.event.impl.ClientTickEvent;
import remix.event.impl.PacketEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.server.hypixel.HypixelGameType;
import remix.server.hypixel.HypixelLocationData;
import remix.server.hypixel.HypixelServerDetector;

import java.util.regex.Pattern;

public final class HypixelLocrawListenerModule extends ModuleBase {

    private Pattern pattern;

    private HypixelLocationData currentLocation;

    private boolean automaticLocrawPending;

    private boolean inLobby;

    private HypixelLocationData lastKnownLocation;

    public int ticksSinceWorldChange;

    private static String[] packedTextFragments;

    private Gson gson;

    private boolean manualLocrawRequested;

    public HypixelLocationData getCurrentLocation() {

        return this.currentLocation;
    }

    public HypixelLocationData getLastKnownLocation() {

        return this.lastKnownLocation;
    }

    @Subscribe(mc = ClientTickEvent.class)
    public void onClientTick(ClientTickEvent event) {

        if (minecraft.player != null
                && HypixelServerDetector.isOnHypixel()
                && this.ticksSinceWorldChange < 20) {

            this.ticksSinceWorldChange++;

            if (this.ticksSinceWorldChange == 20) {

                this.automaticLocrawPending = true;

                minecraft.player.sendChatMessage("/locraw");
            }
        }
    }

    public HypixelLocrawListenerModule() {
        super(
                "LocrawListener",
                new ModuleDefaults(ModuleCategory.HIDDEN).visible(false).keyBind(0));

        this.gson = new Gson();

        this.manualLocrawRequested = false;

        this.pattern = Pattern.compile("(.+)?lobby(.+)");

        RemixClient.eventBus.register(this);
    }

    @Subscribe(mc = PacketEvent.class, priority = SubscriberPriority.HIGH)
    public void onPacket(PacketEvent event) {

        Packet packet = event.getPacket();

        switch (event.getDirection()) {
            case RECEIVED:
                if (packet instanceof S02PacketChat) {

                    S02PacketChat s02PacketChat = (S02PacketChat) packet;

                    ChatComponent chatComponent = s02PacketChat.getChatComponent();

                    if (chatComponent != null) {

                        try {

                            String chatText = chatComponent.getUnformattedTextForChat();

                            if (chatText.startsWith(packedTextFragments[0])) {

                                this.lastKnownLocation = this.currentLocation;

                                this.currentLocation =
                                        (HypixelLocationData)
                                                this.gson.fromJson(
                                                        chatText, HypixelLocationData.class);

                                if (this.currentLocation != null) {

                                    this.lastKnownLocation = this.currentLocation;

                                    this.currentLocation.setHypixelGameType(
                                            HypixelGameType.fromGameName(
                                                    this.currentLocation.getGametype()));

                                    if (!this.manualLocrawRequested) {

                                        event.cancel();
                                    }

                                    this.manualLocrawRequested = false;

                                    this.automaticLocrawPending = false;
                                }
                            }

                            if (this.currentLocation == null) {

                                this.inLobby = false;

                            } else {

                                this.inLobby =
                                        this.pattern
                                                .matcher(this.currentLocation.getServer())
                                                .matches();
                            }

                        } catch (Exception exception) {

                        }
                    }
                }

                break;

            case SEND:
                if (packet instanceof C01PacketChatMessage) {

                    C01PacketChatMessage c01PacketChatMessage = (C01PacketChatMessage) packet;

                    if (c01PacketChatMessage.getMessage().startsWith("/locraw")
                            && !this.automaticLocrawPending) {

                        this.manualLocrawRequested = true;
                    }
                }

                break;
        }
    }

    public boolean isInLobby() {

        return this.inLobby;
    }


    static {
        remix.module.hidden.HypixelLocrawListenerModule.packedTextFragments = new String[3];

        remix.module.hidden.HypixelLocrawListenerModule.packedTextFragments[0] = new String("{");
    }
}
