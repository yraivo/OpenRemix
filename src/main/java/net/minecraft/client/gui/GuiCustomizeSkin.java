package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiCustomizeSkin extends GuiScreen {

    private GuiScreen parentScreen;

    private String title;

    private String method_fc55b93155c0c1e1b668(EnumPlayerModelParts enumPlayerModelParts) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.mc.gameSettings.getModelParts().contains(enumPlayerModelParts)) {
            String text = I18n.format("options.on", new Object[0]);
            if (astring == null) {
                return enumPlayerModelParts.func_179326_d().getFormattedText() + ": " + text;
            }
        }

        String textValue = I18n.format("options.off", new Object[0]);
        return enumPlayerModelParts.func_179326_d().getFormattedText() + ": " + textValue;
    }

    static String method_f1d7a08e803801cf35ab(
            GuiCustomizeSkin guiCustomizeSkin,
            EnumPlayerModelParts enumPlayerModelParts) {
        return guiCustomizeSkin.method_fc55b93155c0c1e1b668(enumPlayerModelParts);
    }


    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
        super.drawScreen(i, j, f);
    }

    public GuiCustomizeSkin(GuiScreen guiScreen) {
        this.parentScreen = guiScreen;
    }

    public void initGui() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int i = 0;
        this.title = I18n.format("options.skinCustomisation.title", new Object[0]);
        EnumPlayerModelParts[] enumPlayerModelPartsArray = EnumPlayerModelParts.values();
        int j = enumPlayerModelPartsArray.length;
        int k = 0;
        String[] astring = astring1;

        while (k < j) {
            EnumPlayerModelParts enumPlayerModelParts = enumPlayerModelPartsArray[k];
            this.buttonList.add(
                    new GuiCustomizeSkinButtonPart(
                            this,
                            enumPlayerModelParts.getPartId(),
                            this.width / 2 - 155 + i % 2 * 160,
                            this.height / 6 + 24 * (i >> 1),
                            150,
                            20,
                            enumPlayerModelParts,
                            null));
            i++;
            k++;
            if (astring != null) {
                break;
            }
        }

        if (i % 2 == 1) {
            i++;
        }

        this.buttonList.add(
                new GuiButton(
                        200,
                        this.width / 2 - 100,
                        this.height / 6 + 24 * (i >> 1),
                        I18n.format("gui.done", new Object[0])));
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.enabled) {
            if (guiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
                if (astring == null) {
                    return;
                }
            }

            if (guiButton instanceof GuiCustomizeSkinButtonPart) {
                EnumPlayerModelParts enumPlayerModelParts =
                        GuiCustomizeSkinButtonPart.method_bf86ebcfac415b97d359(
                                (GuiCustomizeSkinButtonPart) guiButton);
                this.mc.gameSettings.switchModelPartEnabled(enumPlayerModelParts);
                guiButton.displayString = this.method_fc55b93155c0c1e1b668(enumPlayerModelParts);
            }
        }
    }
}
