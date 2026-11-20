package net.minecraft.client.network;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.network.ConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.client.gui.GuiScreen;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.exploit.PingSpoofModule;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;

import io.netty.util.concurrent.GenericFutureListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.PublicKey;

import javax.crypto.SecretKey;

public class NetHandlerLoginClient implements INetHandlerLoginClient {
    private static Logger logger;
    private NetworkManager networkManager;

    private GameProfile gameProfile;
    private MinecraftClient mc;
    private GuiScreen previousGuiScreen;

    public NetHandlerLoginClient(
            NetworkManager awaValue, MinecraftClient minecraftClient, GuiScreen awNValue) {
        this.networkManager = awaValue;
        this.mc = minecraftClient;
        this.previousGuiScreen = awNValue;
    }

    public void handleDisconnect(S00PacketDisconnect s00PacketDisconnect) {
        this.networkManager.closeChannel(s00PacketDisconnect.getReason());
    }

    public void handleLoginSuccess(S02PacketLoginSuccess s02PacketLoginSuccess) {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        PingSpoofModule pingSpoof = ClientServices.moduleManager.pingSpoof;
        if (pingSpoof.isEnabled()
                && pingSpoof.negativeSetting.asBoolean()
                && !this.mc.isSingleplayer()) {
            this.networkManager.sendPacket(new C00PacketKeepAlive());
        }

        this.gameProfile = s02PacketLoginSuccess.getProfile();
        this.networkManager.setConnectionState(ConnectionState.PLAY);
        this.networkManager.setPacketHandler(
                new ClientPlayHandler(
                        this.mc, this.previousGuiScreen, this.networkManager, this.gameProfile));
    }

    public void handleEnableCompression(S03PacketEnableCompression s03PacketEnableCompression) {
        if (!this.networkManager.isLocalChannel()) {
            this.networkManager.setCompressionThreshold(
                    s03PacketEnableCompression.getCompressionTreshold());
        }
    }

    static {
        net.minecraft.client.network.NetHandlerLoginClient.logger = LogManager.getLogger();
    }

    private MinecraftSessionService getSessionService() {
        return this.mc.getSessionService();
    }

    public void onDisconnect(ChatComponent chatComponent) {
        this.mc.displayGuiScreen(
                new GuiDisconnected(
                        this.previousGuiScreen, "connect.failed", chatComponent));
    }

    public void handleEncryptionRequest(S01PacketEncryptionRequest s01PacketEncryptionRequest) {
        SecretKey secretkey = CryptManager.createNewSharedKey();
        String text = s01PacketEncryptionRequest.getServerId();
        PublicKey publickey = s01PacketEncryptionRequest.getPublicKey();
        String textValue =
                new BigInteger(CryptManager.getServerIdHash(text, publickey, secretkey))
                        .toString(16);

        try {
            this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), textValue);
        } catch (AuthenticationUnavailableException authenticationunavailableexception) {
            this.networkManager.closeChannel(
                    new ChatComponentTranslation(
                            "disconnect.loginFailedInfo",
                            new Object[] {
                                new ChatComponentTranslation(
                                        "disconnect.loginFailedInfo.serversUnavailable",
                                        new Object[0])
                            }));
            return;
        } catch (InvalidCredentialsException invalidcredentialsexception) {
            this.networkManager.closeChannel(
                    new ChatComponentTranslation(
                            "disconnect.loginFailedInfo",
                            new Object[] {
                                new ChatComponentTranslation(
                                        "disconnect.loginFailedInfo.invalidSession", new Object[0])
                            }));
            return;
        } catch (AuthenticationException authenticationexception) {
            this.networkManager.closeChannel(
                    new ChatComponentTranslation(
                            "disconnect.loginFailedInfo",
                            new Object[] {authenticationexception.getMessage()}));
            return;
        }

        this.networkManager.sendPacket(
                new C01PacketEncryptionResponse(
                        secretkey, publickey, s01PacketEncryptionRequest.getVerifyToken()),
                future -> this.networkManager.enableEncryption(secretkey),
                new GenericFutureListener[0]);
    }
}
