package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resources.I18n;

import java.util.List;

public class GuiResourcePackSelected extends GuiResourcePackList {

    private static String jU;

    // region String decoding support

    // endregion

    static {
        jU = "resourcePack.selected.title";
    }

    public GuiResourcePackSelected(MinecraftClient minecraftClient, int i, int j, List list) {
        super(minecraftClient, i, j, list);
    }

    // region String decoding support

    // endregion

    protected String getListHeader() {
        return I18n.format(jU, new Object[0]);
    }
}
