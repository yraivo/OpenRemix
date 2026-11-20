package net.minecraft.client.network;

import net.minecraft.client.MinecraftClient;

public class LanServerDetectorLanServer {
    private String lanServerIpPort;
    private String lanServerMotd;
    private long timeLastSeen;

    public String getServerMotd() {
        return this.lanServerMotd;
    }

    public String getServerIpPort() {
        return this.lanServerIpPort;
    }

    public LanServerDetectorLanServer(String text, String textValue) {
        this.lanServerMotd = text;
        this.lanServerIpPort = textValue;
        this.timeLastSeen = net.minecraft.client.MinecraftClient.getSystemTime();
    }

    public void updateLastSeen() {
        this.timeLastSeen = MinecraftClient.getSystemTime();
    }
}
