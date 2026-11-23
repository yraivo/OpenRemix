package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.settings.GameSettingsOptions;

import optifine.Lang;

import java.util.List;
import java.util.Map;

class GuiLanguageList extends GuiSlot {
    private static String jT;
    private List langCodeList;
    private Map languageMap;
    GuiLanguage ars;

    protected int getContentHeight() {
        return this.getSize() * 18;
    }

    // region String decoding support

    // endregion

    protected void drawBackground() {
        this.ars.drawDefaultBackground();
    }

    static {
        jT = "gui.done";
    }

    protected void drawSlot(int i, int k, int j, int l, int i1, int j1) {
        this.ars.fontRendererObj.setBidiFlag(true);
        this.ars.drawCenteredString(
                this.ars.fontRendererObj,
                ((Language) this.languageMap.get(this.langCodeList.get(i))).toString(),
                this.width / 2,
                j + 1,
                16777215);
        this.ars.fontRendererObj.setBidiFlag(
                GuiLanguage.method_fa1c1ad0737ddbeaeb72(this.ars)
                        .getCurrentLanguage()
                        .isBidirectional());
    }

    protected void elementClicked(int i, boolean flag, int j, int k) {
        if (this.isSelected(i)) return;
        Language language = (Language) this.languageMap.get(this.langCodeList.get(i));
        GuiLanguage.method_fa1c1ad0737ddbeaeb72(this.ars).setCurrentLanguage(language);
        GuiLanguage.method_0e23fa9a30eb0fa24070(this.ars).language = language.getLanguageCode();
        GuiLanguage.method_fa1c1ad0737ddbeaeb72(this.ars)
                .onResourceManagerReload(this.mc.getResourceManager());
        Lang.resourcesReloaded();
        this.ars.fontRendererObj.setUnicodeFlag(
                GuiLanguage.method_fa1c1ad0737ddbeaeb72(this.ars).isCurrentLocaleUnicode()
                        || GuiLanguage.method_0e23fa9a30eb0fa24070(this.ars).forceUnicodeFont);
        this.ars.fontRendererObj.setBidiFlag(
                GuiLanguage.method_fa1c1ad0737ddbeaeb72(this.ars).isCurrentLanguageBidirectional());
        GuiLanguage.method_a40fa5101c49b3efea19(this.ars).displayString =
                I18n.format(jT, new Object[0]);
        GuiLanguage.method_4ee61c0f11121b526f9e(this.ars).displayString =
                GuiLanguage.method_0e23fa9a30eb0fa24070(this.ars)
                        .getKeyBinding(GameSettingsOptions.FORCE_UNICODE_FONT);
        GuiLanguage.method_0e23fa9a30eb0fa24070(this.ars).saveOptions();
    }

    // region String decoding support

    // endregion

    public GuiLanguageList(GuiLanguage arsValue, MinecraftClient minecraftClient) {
        super(minecraftClient, arsValue.width, arsValue.height, 32, arsValue.height - 65 + 4, 18);
        this.ars = arsValue;
        String[] astring1 = Gui.getLegacyGuiRenderMarker();

        this.langCodeList = Lists.newArrayList();
        this.languageMap = Maps.newHashMap();
        String[] astring = astring1;

        for (java.util.Iterator<?> __recoveryIterator171_6498 =
                        (GuiLanguage.method_fa1c1ad0737ddbeaeb72(arsValue).getLanguages())
                                .iterator();
                (__recoveryIterator171_6498).hasNext(); ) {
            Language language = (Language) (__recoveryIterator171_6498).next();
            this.languageMap.put(language.getLanguageCode(), language);
            this.langCodeList.add(language.getLanguageCode());
            if (astring != null) {
                break;
            }
        }
    }

    protected boolean isSelected(int i) {
        return ((String) this.langCodeList.get(i))
                .equals(
                        GuiLanguage.method_fa1c1ad0737ddbeaeb72(this.ars)
                                .getCurrentLanguage()
                                .getLanguageCode());
    }

    protected int getSize() {
        return this.langCodeList.size();
    }
}
