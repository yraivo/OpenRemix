package net.minecraft.client.resources.data;

import net.minecraft.util.ChatComponent;

public class PackMetadataSection implements IMetadataSection {
    private ChatComponent packDescription;
    private int packFormat;

    public ChatComponent getPackDescription() {
        return this.packDescription;
    }

    public int getPackFormat() {
        return this.packFormat;
    }

    public PackMetadataSection(ChatComponent aHEValue, int packFormatValue) {
        this.packDescription = aHEValue;
        this.packFormat = packFormatValue;
    }
}
