package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resources.I18n;

import java.util.List;

public class GuiResourcePackAvailable extends GuiResourcePackList {
    private static String jU;

    static {
        jU = "resourcePack.available.title";
    }

    protected String getListHeader() {
        return I18n.format(jU, new Object[0]);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public GuiResourcePackAvailable(MinecraftClient minecraftClient, int i, int j, List list) {
        super(minecraftClient, i, j, list);
    }
}
