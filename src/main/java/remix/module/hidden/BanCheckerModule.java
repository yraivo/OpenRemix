package remix.module.hidden;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.util.ChatComponentText;
import remix.ui.account.AltServiceSwitcher;
import remix.event.impl.ClientTickEvent;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.ModuleMetadata;
import remix.ui.screen.RemixMainMenuScreen;



import com.thealtening.api.data.AccountData;



public final class BanCheckerModule extends ModuleMetadata {



    private boolean banCheckEnabled;



    private boolean replacementAccountPending;

    private static String[] packedTextFragments;



    public void setBanCheckEnabled(boolean flag) {

        this.banCheckEnabled = flag;

    }



    public String getBanCheckButtonLabel() {

        StringBuilder stringbuilder = new StringBuilder().append("Ban Checker: ");

        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();

        return stringbuilder

                .append(ClientServices.moduleManager.banChecker.banCheckEnabled ? "\u00a7aEnabled" : "\u00a74Disabled")

                .toString();

    }




    static {

        remix.module.hidden.BanCheckerModule.packedTextFragments = new String[4];

        remix.module.hidden.BanCheckerModule.packedTextFragments[0] = new String("");

        remix.module.hidden.BanCheckerModule.packedTextFragments[1] = new String("a");

    }



    public BanCheckerModule() {

        super(

                packedTextFragments[0],

                new ModuleDefaults(ModuleCategory.HIDDEN).visible(false));

        RemixClient.eventBus.register(this);

    }



    @Subscribe(mc = PacketEvent.class)

    public void onPacket(PacketEvent event) {

        if (this.banCheckEnabled && event.getDirection() == PacketDirection.RECEIVED) {

            if (event.getPacket() instanceof S03PacketEnableCompression) {

                ChatComponentText chatComponentText =

                        new ChatComponentText(

                                String.format(

                                        "\u00a77Found Working Alt! (%s)",

                                        minecraft.session.getUsername()));

                minecraft.displayGuiScreen(

                        new GuiDisconnected(

                                new GuiMultiplayer(

                                        new RemixMainMenuScreen(RemixClient.INSTANCE)),

                                "\u00a7cBan Checker",

                                chatComponentText));

                event.setCancelled(true);

                return;

            }



            if (event.getPacket() instanceof S00PacketDisconnect) {

                this.replacementAccountPending = true;

            }

        }

    }



    public boolean isBanCheckEnabled() {

        return this.banCheckEnabled;

    }



    @Subscribe(mc = ClientTickEvent.class)

    public void onClientTick(ClientTickEvent event) {

        if (this.replacementAccountPending) {

            try {

                ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();

                AltServiceSwitcher altService = ClientServices.altServiceSwitcher;

                AccountData accountdata = altService.getTheAltening().generateAccount();

                if (accountdata != null) {

                    String text = accountdata.getToken();

                    clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();

                    ClientServices.altLoginManager.loginWithCredentials(text, packedTextFragments[1]);

                    MinecraftClient.getMinecraft()

                            .displayGuiScreen(

                                    new GuiConnecting(

                                            new GuiMultiplayer(

                                                    new RemixMainMenuScreen(

                                                            RemixClient.INSTANCE)),

                                            minecraft,

                                            GuiConnecting.lastServerData));

                }

            } catch (Exception exception) {

                exception.printStackTrace();

            }



            this.replacementAccountPending = false;

        }

    }

}

