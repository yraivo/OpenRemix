package net.minecraft.server.network;

import net.minecraft.server.network.NetHandlerLoginServer;
import net.minecraft.server.network.NetHandlerLoginServerLoginState;
import net.minecraft.util.CryptManager;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;

import java.math.BigInteger;
import java.util.UUID;

public class ServerLoginAuthenticationThread extends Thread {

    NetHandlerLoginServer bCe;

    public ServerLoginAuthenticationThread(NetHandlerLoginServer bCeValue, String text) {
        super(text);
        this.bCe = bCeValue;
    }

    private static AuthenticationUnavailableException method_4dff2eef37d529afc43e(
            AuthenticationUnavailableException authenticationunavailableexception) {
        return authenticationunavailableexception;
    }


    @Override
    public void run() {
        int j = NetHandlerLoginServer.cR();
        GameProfile gameprofile = NetHandlerLoginServer.access$100(this.bCe);
        int i = j;

        try {
            String text =
                    new BigInteger(
                                    CryptManager.getServerIdHash(
                                            NetHandlerLoginServer.access$200(this.bCe),
                                            NetHandlerLoginServer.access$000(this.bCe)
                                                    .getKeyPair()
                                                    .getPublic(),
                                            NetHandlerLoginServer.access$300(this.bCe)))
                            .toString(16);
            NetHandlerLoginServer.access$102(
                    this.bCe,
                    NetHandlerLoginServer.access$000(this.bCe)
                            .getMinecraftSessionService()
                            .hasJoinedServer(
                                    new GameProfile((UUID) null, gameprofile.getName()), text));
            if (NetHandlerLoginServer.access$100(this.bCe) != null) {
                NetHandlerLoginServer.access$400()
                        .info(
                                "UUID of player "
                                        + NetHandlerLoginServer.access$100(this.bCe).getName()
                                        + " is "
                                        + NetHandlerLoginServer.access$100(this.bCe).getId());
                NetHandlerLoginServer.access$502(
                        this.bCe, NetHandlerLoginServerLoginState.READY_TO_ACCEPT);
                if (i == 0) {
                    return;
                }
            }

            if (NetHandlerLoginServer.access$000(this.bCe).isSinglePlayer()) {
                NetHandlerLoginServer.access$400()
                        .warn("Failed to verify username but will let them in anyway!");
                NetHandlerLoginServer.access$102(this.bCe, this.bCe.getOfflineProfile(gameprofile));
                NetHandlerLoginServer.access$502(
                        this.bCe, NetHandlerLoginServerLoginState.READY_TO_ACCEPT);
                if (i == 0) {
                    return;
                }
            }

            this.bCe.closeConnection("Failed to verify username!");
            NetHandlerLoginServer.access$400()
                    .error(
                            "Username '"
                                    + NetHandlerLoginServer.access$100(this.bCe).getName()
                                    + "' tried to join with an invalid session");
        } catch (AuthenticationUnavailableException authenticationunavailableexception) {
            if (NetHandlerLoginServer.access$000(this.bCe).isSinglePlayer()) {
                NetHandlerLoginServer.access$400()
                        .warn("Authentication servers are down but will let them in anyway!");
                NetHandlerLoginServer.access$102(this.bCe, this.bCe.getOfflineProfile(gameprofile));
                NetHandlerLoginServer.access$502(
                        this.bCe, NetHandlerLoginServerLoginState.READY_TO_ACCEPT);
                if (i == 0) {
                    return;
                }
            }

            this.bCe.closeConnection(
                    "Authentication servers are down. Please try again later, sorry!");
            NetHandlerLoginServer.access$400()
                    .error("Couldn't verify username because servers are unavailable");
        }
    }
}
