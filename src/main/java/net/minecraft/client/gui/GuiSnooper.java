package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.client.gui.GuiScreen;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class GuiSnooper extends GuiScreen {
    private static String[] packedTextFragments;
    private GuiScreen yQ;
    private String[] lg;
    private String jT;

    private GuiButton anU;
    private List rp;
    private GuiSnooperList asT;
    private final List lh = Lists.newArrayList();

    private GameSettings game_settings_2;

    static List access$100(GuiSnooper guiSnooper) {
        return guiSnooper.rp;
    }

    public void drawScreen(int i, int j, float f) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.drawDefaultBackground();
        this.asT.drawScreen(i, j, f);
        String[] astring = astring1;
        this.drawCenteredString(this.fontRendererObj, this.jT, this.width / 2, 8, 16777215);
        int k = 22;

        for (String text : this.lg) {
            this.drawCenteredString(this.fontRendererObj, text, this.width / 2, k, 8421504);
            k += this.fontRendererObj.FONT_HEIGHT;
            if (astring != null) {
                break;
            }
        }

        super.drawScreen(i, j, f);
    }

    public GuiSnooper(GuiScreen guiScreen, GameSettings aqmValue) {
        this.rp = Lists.newArrayList();
        this.yQ = guiScreen;
        this.game_settings_2 = aqmValue;
    }

    public void initGui() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.jT = I18n.format("options.snooper.title", new Object[0]);
        String[] astring = astring1;
        String text = I18n.format("options.snooper.desc", new Object[0]);
        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_24753 =
                        (this.fontRendererObj.listFormattedStringToWidth(text, this.width - 30))
                                .iterator();
                (__recoveryIterator171_24753).hasNext(); ) {
            String textValue = (String) (__recoveryIterator171_24753).next();
            arrayList.add(textValue);
            if (astring != null) {
                break;
            }
        }

        this.lg = ((java.lang.String[]) arrayList.toArray(new String[0]));
        this.lh.clear();
        this.rp.clear();
        this.buttonList.add(
                this.anU =
                        new GuiButton(
                                1,
                                this.width / 2 - 152,
                                this.height - 30,
                                150,
                                20,
                                this.game_settings_2.getKeyBinding(GameSettingsOptions.SNOOPER_ENABLED)));
        this.buttonList.add(
                new GuiButton(
                        2,
                        this.width / 2 + 2,
                        this.height - 30,
                        150,
                        20,
                        I18n.format("gui.done", new Object[0])));
        int i =
                this.mc.getIntegratedServer() != null
                                && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null
                        ? 1
                        : 0;

        for (java.util.Iterator<?> __recoveryIterator171_25611 =
                        (new TreeMap(this.mc.getPlayerUsageSnooper().getCurrentStats()).entrySet()).iterator();
                (__recoveryIterator171_25611).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_25611).next();
            this.lh.add((i != 0 ? "C " : packedTextFragments[0]) + (String) entry.getKey());
            this.rp.add(
                    this.fontRendererObj.trimStringToWidth(
                            (String) entry.getValue(), this.width - 220));
            if (astring != null) {
                break;
            }
        }

        if (i != 0) {
            for (java.util.Iterator<?> __recoveryIterator171_25970 =
                            (new TreeMap(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats())
                                            .entrySet())
                                    .iterator();
                    (__recoveryIterator171_25970).hasNext(); ) {
                Entry entry1 = (Entry) (__recoveryIterator171_25970).next();
                this.lh.add("S " + (String) entry1.getKey());
                this.rp.add(
                        this.fontRendererObj.trimStringToWidth(
                                (String) entry1.getValue(), this.width - 220));
                if (astring != null) {
                    break;
                }
            }
        }

        this.asT = new GuiSnooperList(this);
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.asT.handleMouseInput();
    }

    static List access$000(GuiSnooper guiSnooper) {
        return guiSnooper.lh;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    static {
        net.minecraft.client.gui.GuiSnooper.packedTextFragments = new String[3];
        net.minecraft.client.gui.GuiSnooper.packedTextFragments[0] = new String("");
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 2) {
                this.game_settings_2.saveOptions();
                this.game_settings_2.saveOptions();
                this.mc.displayGuiScreen(this.yQ);
            }

            if (guiButton.id == 1) {
                this.game_settings_2.setOptionValue(GameSettingsOptions.SNOOPER_ENABLED, 1);
                this.anU.displayString =
                        this.game_settings_2.getKeyBinding(GameSettingsOptions.SNOOPER_ENABLED);
            }
        }
    }
}
