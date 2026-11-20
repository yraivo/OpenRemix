package net.minecraft.server.management;

import com.google.gson.JsonObject;

import java.io.File;
import java.net.SocketAddress;

public class BanList extends UserList {
    private static String[] packedTextFragments;

    public IPBanEntry getBanEntry(SocketAddress socketaddress) {
        String text = this.addressToString(socketaddress);
        return (IPBanEntry) this.getEntry(text);
    }

    public boolean isBanned(SocketAddress socketaddress) {
        String text = this.addressToString(socketaddress);
        return this.hasEntry(text);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.server.management.BanList.packedTextFragments[0] = new String("/");
        net.minecraft.server.management.BanList.packedTextFragments[1] = new String(":");
    }

    // endregion

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    public BanList(File file1) {
        super(file1);
    }

    protected UserListEntry createEntry(JsonObject jsonobject) {
        return new IPBanEntry(jsonobject);
    }

    // region String decoding support

    // endregion

    private String addressToString(SocketAddress socketaddress) {
        String text = socketaddress.toString();
        if (text.contains(packedTextFragments[0])) {
            text = text.substring(text.indexOf(47) + 1);
        }

        if (text.contains(packedTextFragments[1])) {
            text = text.substring(0, text.indexOf(58));
        }

        return text;
    }

    // region String decoding support

    // endregion
}
