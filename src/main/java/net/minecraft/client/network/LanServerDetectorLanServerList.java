package net.minecraft.client.network;

import net.minecraft.client.multiplayer.ThreadLanServerPing;

import com.google.common.collect.Lists;

import java.net.InetAddress;
import java.util.Collections;
import java.util.List;

public class LanServerDetectorLanServerList {
    private List listOfLanServers = Lists.newArrayList();
    private static String[] packedTextFragments;
    boolean wasUpdated;

    public synchronized void setWasNotUpdated() {
        this.wasUpdated = false;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public synchronized void method_e6e1733e9b2e1aff79b2(String text, InetAddress inetaddress) {
        ClientPlayHandler.getLegacyPlayHandlerFlowMarker();

        String textValue = ThreadLanServerPing.a(text);

        String textValue2 = ThreadLanServerPing.method_0442347320b0b56aa8a0(text);
        if (textValue2 != null) {
            textValue2 = inetaddress.getHostAddress() + packedTextFragments[0] + textValue2;
            byte b0 = 0;

            for (Object object : this.listOfLanServers) {
                LanServerDetectorLanServer lanServerDetectorLanServer =
                        (LanServerDetectorLanServer) object;
                if (lanServerDetectorLanServer.getServerIpPort().equals(textValue2)) {
                    lanServerDetectorLanServer.updateLastSeen();
                    b0 = 1;

                    break;
                }
            }

            if (b0 == 0) {
                this.listOfLanServers.add(new LanServerDetectorLanServer(textValue, textValue2));
                this.wasUpdated = true;
            }
        }
    }

    public synchronized boolean getWasUpdated() {
        return this.wasUpdated;
    }

    // region String decoding support

    // endregion

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.network.LanServerDetectorLanServerList.packedTextFragments[0] = new String(":");
    }

    // endregion

    public synchronized List getLanServers() {
        return Collections.unmodifiableList(this.listOfLanServers);
    }
}
