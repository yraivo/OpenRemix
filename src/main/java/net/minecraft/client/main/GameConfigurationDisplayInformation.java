package net.minecraft.client.main;

public class GameConfigurationDisplayInformation {
    public int width;
    public boolean checkGlErrors;
    public int height;
    public boolean fullscreen;

    public GameConfigurationDisplayInformation(int i, int j, boolean flag, boolean flag1) {
        this.width = i;
        this.height = j;
        this.fullscreen = flag;
        this.checkGlErrors = flag1;
    }
}
