package net.minecraft.client.multiplayer;

import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;

public enum ServerDataServerResourceMode {
    ENABLED("ENABLED", 0, "enabled"),
    DISABLED("DISABLED", 1, "disabled"),
    PROMPT("PROMPT", 2, "prompt");
    private static ServerDataServerResourceMode[] aOQ;

    private ChatComponent motd;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    ServerDataServerResourceMode(String text, int j, String textValue) {
        this.motd =
                new ChatComponentTranslation("addServer.resourcePack." + textValue, new Object[0]);
    }

    static {
        aOQ =
                new ServerDataServerResourceMode[] {
                    net.minecraft.client.multiplayer.ServerDataServerResourceMode.ENABLED,
                    net.minecraft.client.multiplayer.ServerDataServerResourceMode.DISABLED,
                    net.minecraft.client.multiplayer.ServerDataServerResourceMode.PROMPT
                };
    }

    public ChatComponent getMotd() {
        return this.motd;
    }
}
