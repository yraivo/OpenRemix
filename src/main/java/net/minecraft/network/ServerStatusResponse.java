package net.minecraft.network;

import net.minecraft.util.ChatComponent;

public class ServerStatusResponse {
    private String favicon;
    private ServerStatusResponsePlayerCountData playerCount;
    private ChatComponent serverMotd;
    private ServerStatusResponseMinecraftProtocolVersionIdentifier protocolVersion;

    public ServerStatusResponsePlayerCountData getPlayerCountData() {
        return this.playerCount;
    }

    public ChatComponent getServerDescription() {
        return this.serverMotd;
    }

    public ServerStatusResponseMinecraftProtocolVersionIdentifier getProtocolVersionInfo() {
        return this.protocolVersion;
    }

    public void setFavicon(String text) {
        this.favicon = text;
    }

    public void setServerDescription(ChatComponent aHEValue) {
        this.serverMotd = aHEValue;
    }

    public String getFavicon() {
        return this.favicon;
    }

    public void setPlayerCountData(ServerStatusResponsePlayerCountData bvlValue) {
        this.playerCount = bvlValue;
    }

    public void setProtocolVersionInfo(
            ServerStatusResponseMinecraftProtocolVersionIdentifier bvmValue) {
        this.protocolVersion = bvmValue;
    }
}
