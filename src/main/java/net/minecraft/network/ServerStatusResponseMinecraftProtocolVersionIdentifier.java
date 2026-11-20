package net.minecraft.network;

public class ServerStatusResponseMinecraftProtocolVersionIdentifier {
    private String name;
    private int protocol;

    public int getProtocol() {
        return this.protocol;
    }

    public ServerStatusResponseMinecraftProtocolVersionIdentifier(String text, int protocolValue) {
        this.name = text;
        this.protocol = protocolValue;
    }

    public String getName() {
        return this.name;
    }
}
