package net.minecraft.client.main;

public class GameConfigurationServerInformation {
    public int serverPort;
    public String serverName;

    public GameConfigurationServerInformation(String text, int i) {
        this.serverName = text;
        this.serverPort = i;
    }
}
