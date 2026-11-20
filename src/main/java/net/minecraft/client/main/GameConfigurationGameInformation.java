package net.minecraft.client.main;

public class GameConfigurationGameInformation {
    public boolean isDemo;
    public String version;

    public GameConfigurationGameInformation(boolean flag, String text) {
        this.isDemo = flag;
        this.version = text;
    }
}
