package remix.server.hypixel;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.multiplayer.ServerData;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HypixelServerDetector {
    private static String[] packedTextFragments;
    private static Map<String, Boolean> serverMatchCache;
    private static InetAddress localHostAddress;
    private static List<String> excludedLocalAddresses;

    private static MinecraftClient minecraft;
    private static String legacyDomainSuffix;

    private static List<String> resolvedServerAddresses;
    private static String legacyServerDomain;
    private static String legacyDomainLabel;

    private static Exception preserveServerDetectionException(Exception exception) {
        return exception;
    }

    public static boolean isOnHypixel() {
        if (remix.server.hypixel.HypixelServerDetector.minecraft.isSingleplayer()) {
            return false;
        }

        if (remix.server.hypixel.HypixelServerDetector.minecraft.isIntegratedServerRunning()) {
            return false;
        }

        ServerData currentServerData =
                remix.server.hypixel.HypixelServerDetector.minecraft.getCurrentServerData();
        if (currentServerData == null) {
            return false;
        }

        String text = currentServerData.serverIP.toLowerCase();
        if (remix.server.hypixel.HypixelServerDetector.serverMatchCache.containsKey(text)
                && !remix.server.hypixel.HypixelServerDetector.serverMatchCache.get(text)) {
            return false;
        }

        if (!text.isEmpty()
                && text.contains("hypixel")
                && (remix.server.hypixel.HypixelServerDetector.localHostAddress == null
                        || !text.contains(
                                        remix.server.hypixel.HypixelServerDetector.localHostAddress
                                                .getHostName())
                                && !text.contains(
                                        remix.server.hypixel.HypixelServerDetector.localHostAddress
                                                .getHostAddress()))) {
            String textValue = packedTextFragments[0];
            int i = text.length();
            if (text.contains(packedTextFragments[1]) && Character.isDigit(text.charAt(i - 1))) {
                int j = text.lastIndexOf(packedTextFragments[2]) + 1;
                String textValue2 = text.substring(j);

                int k;
                try {
                    k = Integer.parseInt(textValue2);
                } catch (Exception exception) {
                    k = -1;
                }

                if (k >= 0 && k <= 65535) {
                    text = text.substring(0, j - 1);
                }
            }

            String textValue3 = packedTextFragments[3];
            if (text.contains(packedTextFragments[4])) {
                int i1 = text.lastIndexOf(packedTextFragments[5]) + 1;
                String textValue4 = text.substring(i1);
                if (!textValue4.equalsIgnoreCase("net")) {
                    remix.server.hypixel.HypixelServerDetector.serverMatchCache.put(text, false);
                    return false;
                } else {
                    String textValue5 = text.substring(0, i1 - 1);
                    int l =
                            (!text.equalsIgnoreCase("hypixel.net")
                                                    || remix.server.hypixel.HypixelServerDetector
                                                            .excludedLocalAddresses
                                                            .contains(text))
                                            && (!textValue5.endsWith(".hypixel")
                                                    || remix.server.hypixel.HypixelServerDetector
                                                            .excludedLocalAddresses
                                                            .contains(text))
                                            && (!remix.server.hypixel.HypixelServerDetector
                                                            .resolvedServerAddresses
                                                            .contains(textValue5)
                                                    || remix.server.hypixel.HypixelServerDetector
                                                            .excludedLocalAddresses
                                                            .contains(text))
                                    ? 0
                                    : 1;
                    remix.server.hypixel.HypixelServerDetector.serverMatchCache.put(
                            text,
                            Boolean.valueOf(remix.compat.compiler.JvmBooleanSlots.fromInt(l)));
                    return remix.compat.compiler.JvmBooleanSlots.fromInt(l);
                }
            } else {
                remix.server.hypixel.HypixelServerDetector.serverMatchCache.put(text, false);
                return false;
            }
        } else {
            remix.server.hypixel.HypixelServerDetector.serverMatchCache.put(text, false);
            return false;
        }
    }

    static {
        remix.server.hypixel.HypixelServerDetector.packedTextFragments = new String[8];
        remix.server.hypixel.HypixelServerDetector.packedTextFragments[0] = new String(":");
        remix.server.hypixel.HypixelServerDetector.packedTextFragments[1] = new String(":");
        remix.server.hypixel.HypixelServerDetector.packedTextFragments[2] = new String(":");
        remix.server.hypixel.HypixelServerDetector.packedTextFragments[3] = new String(".");
        remix.server.hypixel.HypixelServerDetector.packedTextFragments[4] = new String(".");
        remix.server.hypixel.HypixelServerDetector.packedTextFragments[5] = new String(".");
        remix.server.hypixel.HypixelServerDetector.legacyServerDomain = "hypixel.net";
        remix.server.hypixel.HypixelServerDetector.legacyDomainSuffix = "net";
        remix.server.hypixel.HypixelServerDetector.legacyDomainLabel = "hypixel";
        remix.server.hypixel.HypixelServerDetector.serverMatchCache = new HashMap<>();
        remix.server.hypixel.HypixelServerDetector.resolvedServerAddresses = new ArrayList<>();
        remix.server.hypixel.HypixelServerDetector.excludedLocalAddresses = new ArrayList<>();
        remix.server.hypixel.HypixelServerDetector.minecraft = MinecraftClient.getMinecraft();
        try {
            remix.server.hypixel.HypixelServerDetector.localHostAddress =
                    InetAddress.getLocalHost();
        } catch (UnknownHostException unknownhostexception1) {
        }
        try {
            recordResolvedServerAddress(InetAddress.getByName("hypixel.net"));
        } catch (UnknownHostException unknownhostexception) {
        }
        try {
            InetAddress[] ainetaddress = InetAddress.getAllByName("hypixel.net");

            for (InetAddress inetaddress : ainetaddress) {
                recordResolvedServerAddress(inetaddress);
            }
        } catch (UnknownHostException unknownhostexception2) {
        }
    }

    public static void recordResolvedServerAddress(InetAddress inetaddress) {
        String hostAddress = inetaddress.getHostAddress();
        if ((remix.server.hypixel.HypixelServerDetector.localHostAddress == null
                        || !hostAddress.contains(
                                remix.server.hypixel.HypixelServerDetector.localHostAddress
                                        .getHostAddress()))
                && !hostAddress.contains("localhost")
                && !hostAddress.contains("127.0.0.1")) {
            if (!remix.server.hypixel.HypixelServerDetector.resolvedServerAddresses.contains(
                    hostAddress)) {
                remix.server.hypixel.HypixelServerDetector.resolvedServerAddresses.add(hostAddress);
            }
        } else {
            remix.server.hypixel.HypixelServerDetector.excludedLocalAddresses.add(hostAddress);
        }
    }
}
