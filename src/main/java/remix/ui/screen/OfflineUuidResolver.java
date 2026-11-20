package remix.ui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ConnectionState;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.util.Session;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

public final class OfflineUuidResolver {

    private static String spoofedUsername;
    private static String[] packedTextFragments;
    private static boolean spoofEnabled;

    private static boolean il;
    private static String spoofedIp;

    public static boolean isSpoofEnabled() {
        return remix.ui.screen.OfflineUuidResolver.spoofEnabled;
    }

    public static void setSpoofedIp(String text) {
        remix.ui.screen.OfflineUuidResolver.spoofedIp = text;
    }

    public static void setSpoofEnabled(boolean flag) {
        remix.ui.screen.OfflineUuidResolver.spoofEnabled = flag;
    }

    static {
        remix.ui.screen.OfflineUuidResolver.packedTextFragments = new String[7];
        remix.ui.screen.OfflineUuidResolver.packedTextFragments[0] = new String("\u0000");
        remix.ui.screen.OfflineUuidResolver.packedTextFragments[1] = new String("\u0000");
        remix.ui.screen.OfflineUuidResolver.packedTextFragments[2] = new String("-");
        remix.ui.screen.OfflineUuidResolver.packedTextFragments[3] = new String("");
        remix.ui.screen.OfflineUuidResolver.packedTextFragments[4] = new String("\"");
        remix.ui.screen.OfflineUuidResolver.packedTextFragments[6] = new String("");
        il = true;
        remix.ui.screen.OfflineUuidResolver.spoofEnabled = false;
        remix.ui.screen.OfflineUuidResolver.spoofedIp = "1.3.3.7";
        remix.ui.screen.OfflineUuidResolver.spoofedUsername = packedTextFragments[6];
    }

    public static void setSpoofedUsername(String text) {
        remix.ui.screen.OfflineUuidResolver.spoofedUsername = text;
    }

    public static String getOnlineUuid(String text) throws IOException {
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + text);
        Scanner scanner = new Scanner(url.openStream());
        String textValue = scanner.nextLine();
        scanner.close();
        return textValue.split(packedTextFragments[4])[3];
    }

    public static String getSpoofedIp() {
        return remix.ui.screen.OfflineUuidResolver.spoofedIp;
    }

    public static String getSpoofedUsername() {
        return remix.ui.screen.OfflineUuidResolver.spoofedUsername;
    }

    public static boolean method_b417d7500ec59d69e373() {
        return il;
    }

    public static String getOfflineUuid(String text) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + text).getBytes())
                .toString()
                .replace(packedTextFragments[2], packedTextFragments[3]);
    }

    public static void applyBungeeSpoof(C00Handshake c00Handshake) {
        if (c00Handshake.getRequestedState() == ConnectionState.LOGIN && isSpoofEnabled()) {
            c00Handshake.setIp(
                    c00Handshake.getIp()
                            + packedTextFragments[0]
                            + remix.ui.screen.OfflineUuidResolver.spoofedIp
                            + packedTextFragments[1]
                            + resolveUsernameUuid(remix.ui.screen.OfflineUuidResolver.spoofedUsername));
        }
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        il = flag;
    }

    public static void setClientSession(Session ana) {
        MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
        minecraftClient.session = ana;
    }

    public static String resolveUsernameUuid(String text) {
        try {
            return getOnlineUuid(text);
        } catch (IOException ioexception) {
            return getOfflineUuid(text);
        }
    }
}
