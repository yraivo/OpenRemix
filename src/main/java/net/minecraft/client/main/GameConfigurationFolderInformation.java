package net.minecraft.client.main;

import java.io.File;

public class GameConfigurationFolderInformation {
    public File resourcePacksDir;
    public File mcDataDir;
    public File assetsDir;
    public String assetIndex;

    public GameConfigurationFolderInformation(File file1, File file2, File file3, String text) {
        this.mcDataDir = file1;
        this.resourcePacksDir = file2;
        this.assetsDir = file3;
        this.assetIndex = text;
    }
}
