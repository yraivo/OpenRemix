package remix.ui.clickgui;

import net.minecraft.util.ResourceLocation;
import remix.ui.hud.HudMode;
import remix.util.render.Render2D3DUtils;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.ModuleCategory;
import remix.module.ModuleManager;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;
import remix.ui.hud.TabGuiHudElement;
import remix.ui.hud.TabGuiPanel;

public class ClickGuiCategoryHeader extends ClickGuiSettingControl<ModuleCategory> {

    public ClickGuiCategoryHeader(
            Setting<HudMode> setting,
            ClientFontRenderer clientFontRenderer,
            float f,
            float f1,
            float f2,
            float f3,
            ModuleCategory moduleCategory,
            int i) {
        super(setting, clientFontRenderer, f, f1, f2, f3, moduleCategory, i);
    }

    public String getLabel() {
        ModuleCategory moduleCategory = (ModuleCategory) this.entryValue;
        return moduleCategory.getDisplayName();
    }

    public void drawRow() {
        super.drawRow();
        if (this.setting.getValue() == HudMode.AMBER) {
            ModuleCategory moduleCategory = (ModuleCategory) this.entryValue;
            Render2D3DUtils.drawIconColored(
                    new ResourceLocation(
                            "remix/gui/hud/"
                                    + moduleCategory.getDisplayName()
                                    + ".png"),
                    this.left + this.width - 8.5F,
                    this.top + this.rowHeight / 2.0F + 0.5F,
                    20.0F,
                    20.0F,
                    new int[] {0, 0, 0, 255});
            Render2D3DUtils.drawIconColored(
                    new ResourceLocation(
                            "remix/gui/hud/"
                                    + moduleCategory.getDisplayName()
                                    + ".png"),
                    this.left + this.width - 9.0F,
                    this.top + this.rowHeight / 2.0F,
                    20.0F,
                    20.0F,
                    new int[] {255, 255, 255, 255});
        }
    }

    public void handleKey(int i) {
        if (i == 205) {
            ModuleCategory moduleCategory = (ModuleCategory) this.entryValue;
            ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
            TabGuiHudElement tabGuiRenderer =
                    ClientServices.moduleManager.hud.tabGuiRenderer;
            tabGuiRenderer.openPanels.add(
                    new TabGuiPanel(
                            this.setting,
                            this.clientFontRenderer,
                            this.left + this.width + 2.0F,
                            this.top,
                            this.width + 2.0F,
                            this.rowHeight,
                            ModuleManager.getModulesByCategory(moduleCategory),
                            ClickGuiEntryKind.MODULE));
        }
    }

    public int getLabelColor() {
        return -1;
    }


}
