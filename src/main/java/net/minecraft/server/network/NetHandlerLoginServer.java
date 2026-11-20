package net.minecraft.server.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import io.netty.util.concurrent.GenericFutureListener;

import net.minecraft.entity.Entity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.crypto.SecretKey;

public class NetHandlerLoginServer implements INetHandlerLoginServer, IUpdatePlayerListBox {
    private static Logger logger;
    private static String[] packedTextFragments;
    private static Random RANDOM;
    private int connectionTimer;
    private byte[] verifyToken;
    private MinecraftServer server;
    private static AtomicInteger AUTHENTICATOR_THREAD_ID;
    private String serverId;

    public NetworkManager networkManager;
    private NetHandlerLoginServerLoginState currentLoginState;
    private static int mE;
    private SecretKey secretKey;
    private GameProfile loginGameProfile;

    public NetHandlerLoginServer(MinecraftServer bzfValue, NetworkManager bCaValue) {
        int j = f();
        this.verifyToken = new byte[4];
        int i = j;
        this.currentLoginState = NetHandlerLoginServerLoginState.HELLO;
        this.serverId = packedTextFragments[0];
        this.server = bzfValue;
        this.networkManager = bCaValue;
        net.minecraft.server.network.NetHandlerLoginServer.RANDOM.nextBytes(this.verifyToken);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(++i);
        }
    }

    public static GameProfile access$102(
            NetHandlerLoginServer netHandlerLoginServer, GameProfile gameprofile) {
        return netHandlerLoginServer.loginGameProfile = gameprofile;
    }

    public GameProfile getOfflineProfile(GameProfile gameprofile) {
        UUID uuid =
                UUID.nameUUIDFromBytes(
                        ("OfflinePlayer:" + gameprofile.getName()).getBytes(Charsets.UTF_8));
        return new GameProfile(uuid, gameprofile.getName());
    }

    // region String decoding support

    // endregion

    public String getConnectionInfo() {
        return this.loginGameProfile != null
                ? this.loginGameProfile.toString()
                        + " ("
                        + this.networkManager.getRemoteAddress().toString()
                        + packedTextFragments[1]
                : String.valueOf(this.networkManager.getRemoteAddress());
    }

    public static int f() {
        int i = cR();
        return i == 0 ? 25 : 0;
    }

    public void onDisconnect(ChatComponent chatComponent) {
        net.minecraft.server.network.NetHandlerLoginServer.logger.info(
                this.getConnectionInfo()
                        + " lost connection: "
                        + chatComponent.getUnformattedText());
    }

    public static int cR() {
        return mE;
    }

    public void processLoginStart(C00PacketLoginStart c00PacketLoginStart) {
        int i = f();
        Validate.validState(
                this.currentLoginState == NetHandlerLoginServerLoginState.HELLO,
                "Unexpected hello packet",
                new Object[0]);
        this.loginGameProfile = c00PacketLoginStart.getProfile();
        if (this.server.isServerInOnlineMode() && !this.networkManager.isLocalChannel()) {
            this.currentLoginState = NetHandlerLoginServerLoginState.KEY;
            this.networkManager.sendPacket(
                    new S01PacketEncryptionRequest(
                            this.serverId, this.server.getKeyPair().getPublic(), this.verifyToken));
            if (i != 0) {
                return;
            }
        }

        this.currentLoginState = NetHandlerLoginServerLoginState.READY_TO_ACCEPT;
    }

    public static String access$200(NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.serverId;
    }

    public void update() {
        if (this.currentLoginState == NetHandlerLoginServerLoginState.READY_TO_ACCEPT) {
            this.tryAcceptPlayer();
        }

        if (this.connectionTimer++ == 600) {
            this.closeConnection("Took too long to log in");
        }
    }

    public static SecretKey access$300(NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.secretKey;
    }

    public void closeConnection(String text) {
        try {
            net.minecraft.server.network.NetHandlerLoginServer.logger.info(
                    "Disconnecting " + this.getConnectionInfo() + ": " + text);
            ChatComponentText chatComponentText = new ChatComponentText(text);
            this.networkManager.sendPacket(new S00PacketDisconnect(chatComponentText));
            this.networkManager.closeChannel(chatComponentText);
        } catch (Exception exception) {
            net.minecraft.server.network.NetHandlerLoginServer.logger.error(
                    "Error whilst disconnecting player", exception);
        }
    }

    private static IllegalStateException method_b3985d2d68c259fdadde(
            IllegalStateException illegalstateexception) {
        return illegalstateexception;
    }

    // region String decoding support

    // endregion

    public static Logger access$400() {
        return net.minecraft.server.network.NetHandlerLoginServer.logger;
    }

    public static NetHandlerLoginServerLoginState access$502(
            NetHandlerLoginServer netHandlerLoginServer, NetHandlerLoginServerLoginState bCbValue) {
        return netHandlerLoginServer.currentLoginState = bCbValue;
    }

    static {
        net.minecraft.server.network.NetHandlerLoginServer.packedTextFragments = new String[4];
        net.minecraft.server.network.NetHandlerLoginServer.packedTextFragments[0] = new String("");
        net.minecraft.server.network.NetHandlerLoginServer.packedTextFragments[1] = new String(")");
        b(0);
        net.minecraft.server.network.NetHandlerLoginServer.AUTHENTICATOR_THREAD_ID =
                new AtomicInteger(0);
        net.minecraft.server.network.NetHandlerLoginServer.logger = LogManager.getLogger();
        net.minecraft.server.network.NetHandlerLoginServer.RANDOM = new Random();
    }

    // region String decoding support

    // endregion

    public void tryAcceptPlayer() {
        int i = f();
        if (!this.loginGameProfile.isComplete()) {
            this.loginGameProfile = this.getOfflineProfile(this.loginGameProfile);
        }

        String text =
                this.server
                        .getConfigurationManager()
                        .allowUserToConnect(
                                this.networkManager.getRemoteAddress(), this.loginGameProfile);
        if (text != null) {
            this.closeConnection(text);
            if (i != 0) {
                return;
            }
        }

        this.currentLoginState = NetHandlerLoginServerLoginState.ACCEPTED;
        if (this.server.getNetworkCompressionTreshold() >= 0
                && !this.networkManager.isLocalChannel()) {
            this.networkManager.sendPacket(
                    new S03PacketEnableCompression(this.server.getNetworkCompressionTreshold()),
                    new TryAcceptPlayerChannelFutureListener(this),
                    new GenericFutureListener[0]);
        }

        this.networkManager.sendPacket(new S02PacketLoginSuccess(this.loginGameProfile));
        this.server
                .getConfigurationManager()
                .initializeConnectionToPlayer(
                        this.networkManager,
                        this.server
                                .getConfigurationManager()
                                .createPlayerForUser(this.loginGameProfile));
    }

    public static void b(int i) {
        mE = i;
    }

    public static MinecraftServer access$000(NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.server;
    }

    public static GameProfile access$100(NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.loginGameProfile;
    }

    public void processEncryptionResponse(C01PacketEncryptionResponse c01PacketEncryptionResponse) {
        int i = f();
        Validate.validState(
                this.currentLoginState == NetHandlerLoginServerLoginState.KEY,
                "Unexpected key packet",
                new Object[0]);
        PrivateKey privatekey = this.server.getKeyPair().getPrivate();
        if (!Arrays.equals(
                this.verifyToken, c01PacketEncryptionResponse.getVerifyToken(privatekey))) {
            throw new IllegalStateException("Invalid nonce!");
        }

        this.secretKey = c01PacketEncryptionResponse.getSecretKey(privatekey);
        this.currentLoginState = NetHandlerLoginServerLoginState.AUTHENTICATING;
        this.networkManager.enableEncryption(this.secretKey);
        new ServerLoginAuthenticationThread(
                        this,
                        "User Authenticator #"
                                + net.minecraft.server.network.NetHandlerLoginServer
                                        .AUTHENTICATOR_THREAD_ID
                                        .incrementAndGet())
                .start();
        if (i == 0) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }
    }

    // Helper used only by this class.
    public static class TryAcceptPlayerChannelFutureListener
            implements io.netty.channel.ChannelFutureListener {
        net.minecraft.server.network.NetHandlerLoginServer bCe;

        public TryAcceptPlayerChannelFutureListener(
                net.minecraft.server.network.NetHandlerLoginServer bCeValue) {
            this.bCe = bCeValue;
        }

        public void operationComplete(io.netty.channel.ChannelFuture channelfuture) {
            this.bCe.networkManager.setCompressionThreshold(
                    net.minecraft.server.network.NetHandlerLoginServer.access$000(this.bCe)
                            .getNetworkCompressionTreshold());
        }

        public void jvm171_operationComplete_328f727c3350(io.netty.util.concurrent.Future future)
                throws java.lang.Exception {
            this.operationComplete((io.netty.channel.ChannelFuture) future);
        }
    }
}
