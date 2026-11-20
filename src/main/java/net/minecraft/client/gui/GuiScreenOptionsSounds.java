package net.minecraft.client.gui;

import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiScreenOptionsSounds extends GuiScreen {
    private static String[] packedTextFragments;

    protected String kg = "Options";
    private GuiScreen yQ;
    private String jU;
    private GameSettings game_settings_4;

    static {
        net.minecraft.client.gui.GuiScreenOptionsSounds.packedTextFragments = new String[3];
        net.minecraft.client.gui.GuiScreenOptionsSounds.packedTextFragments[0] = new String("%");
    }

    static GameSettings method_74e12e7806f43704480e(
            GuiScreenOptionsSounds guiScreenOptionsSounds) {
        return guiScreenOptionsSounds.game_settings_4;
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.enabled && guiButton.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.yQ);
        }
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.kg, this.width / 2, 15, 16777215);
        super.drawScreen(i, j, f);
    }

    protected String getSoundVolume(SoundCategory soundCategory) {
        float f = this.game_settings_4.getSoundLevel(soundCategory);
        return f == 0.0F ? this.jU : (int) (f * 100.0F) + packedTextFragments[0];
    }

    public void initGui() {
        byte b0 = 0;
        this.kg = I18n.format("options.sounds.title", new Object[0]);
        this.jU = I18n.format("options.off", new Object[0]);
        this.buttonList.add(
                new GuiScreenOptionsSoundsButton(
                        this,
                        SoundCategory.MASTER.getCategoryId(),
                        this.width / 2 - 155 + b0 % 2 * 160,
                        this.height / 6 - 12 + 24 * (b0 >> 1),
                        SoundCategory.MASTER,
                        true));
        int i = b0 + 2;
        SoundCategory[] soundCategoryArray = SoundCategory.values();
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int j = soundCategoryArray.length;
        String[] astring = astring1;
        int k = 0;

        while (k < j) {
            SoundCategory soundCategory = soundCategoryArray[k];
            if (soundCategory != SoundCategory.MASTER) {
                this.buttonList.add(
                        new GuiScreenOptionsSoundsButton(
                                this,
                                soundCategory.getCategoryId(),
                                this.width / 2 - 155 + i % 2 * 160,
                                this.height / 6 - 12 + 24 * (i >> 1),
                                soundCategory,
                                false));
                i++;
            }

            k++;
            if (astring != null) {
                break;
            }
        }

        this.buttonList.add(
                new GuiButton(
                        200,
                        this.width / 2 - 100,
                        this.height / 6 + 168,
                        I18n.format("gui.done", new Object[0])));
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public GuiScreenOptionsSounds(GuiScreen guiScreen, GameSettings aqmValue) {
        super();
        this.yQ = guiScreen;
        this.game_settings_4 = aqmValue;
    }
}
