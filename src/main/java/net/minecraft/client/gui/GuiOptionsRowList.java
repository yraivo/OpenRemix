package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.settings.GameSettingsOptions;

import com.google.common.collect.Lists;

import java.util.List;

public class GuiOptionsRowList extends GuiListExtended {
    private final List xU = Lists.newArrayList();

    public GuiOptionsRowList(
            MinecraftClient minecraftClient,
            int i,
            int j,
            int k,
            int l,
            int i1,
            GameSettingsOptions... gameSettingsOptionsArray) {
        super(minecraftClient, i, j, k, l, i1);
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.kS = false;
        String[] astring = astring1;
        int b0 = 0;

        while (b0 < gameSettingsOptionsArray.length) {
            GameSettingsOptions gameSettingsOptions = gameSettingsOptionsArray[b0];
            GameSettingsOptions gameSettingsOptionsValue =
                    b0 < gameSettingsOptionsArray.length - 1
                            ? gameSettingsOptionsArray[b0 + 1]
                            : null;
            GuiButton guiButton =
                    this.method_a08b274978302d32b13a(
                            minecraftClient, i / 2 - 155, 0, gameSettingsOptions);
            GuiButton guiButtonValue =
                    this.method_a08b274978302d32b13a(
                            minecraftClient, i / 2 - 155 + 160, 0, gameSettingsOptionsValue);
            this.xU.add(new GuiOptionsRowListRow(guiButton, guiButtonValue));
            b0 += 2;
            if (astring != null) {
                break;
            }
        }
    }

    public GuiOptionsRowListRow P(int i) {
        return (GuiOptionsRowListRow) this.xU.get(i);
    }

    public int cM() {
        return 400;
    }

    protected int getScrollBarX() {
        return super.getScrollBarX() + 32;
    }

    protected int getSize() {
        return this.xU.size();
    }

    private GuiButton method_a08b274978302d32b13a(
            MinecraftClient minecraftClient,
            int i,
            int j,
            GameSettingsOptions gameSettingsOptions) {
        if (gameSettingsOptions == null) {
            return null;
        }

        int k = gameSettingsOptions.returnEnumOrdinal();
        return (GuiButton)
                (gameSettingsOptions.getEnumFloat()
                        ? new GuiOptionSlider(k, i, j, gameSettingsOptions)
                        : new GuiOptionButton(
                                k,
                                i,
                                j,
                                gameSettingsOptions,
                                minecraftClient.gameSettings.getKeyBinding(gameSettingsOptions)));
    }

    public GuiListExtendedIGuiListEntry getListEntry(int i) {
        return this.P(i);
    }
}
