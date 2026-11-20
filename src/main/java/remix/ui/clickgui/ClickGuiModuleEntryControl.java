package remix.ui.clickgui;

import remix.ui.hud.HudMode;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.ModuleBase;
import remix.module.ModuleMetadata;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;
import remix.ui.hud.TabGuiHudElement;
import remix.ui.hud.TabGuiPanel;

public class ClickGuiModuleEntryControl extends ClickGuiSettingControl<ModuleMetadata> {
    public String getLabel() {
        ModuleMetadata moduleMetadata = (ModuleMetadata) this.entryValue;
        return moduleMetadata.getDisplayName();
    }

    public void handleKey(int i) {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        ClickGuiSubGroupControl.getLegacySubGroupFlowState();

        TabGuiHudElement tabGuiRenderer = ClientServices.moduleManager.hud.tabGuiRenderer;
        ModuleBase moduleBase = (ModuleBase) this.entryValue;

        switch (i) {
            case 28:
                moduleBase.toggle();

                break;

            case 203:
                tabGuiRenderer.openPanels.remove(tabGuiRenderer.openPanels.size() - 1);

                break;

            case 205:
                if (!moduleBase.getSettings().isEmpty()) {
                    tabGuiRenderer.openPanels.add(
                            new TabGuiPanel(
                                    this.setting,
                                    this.clientFontRenderer,
                                    this.left + this.width + 2.0F,
                                    this.top,
                                    this.width + 2.0F,
                                    this.rowHeight,
                                    moduleBase.getSettings(),
                                    ClickGuiEntryKind.SETTING));
                }
        }
    }

    public ClickGuiModuleEntryControl(
            Setting<HudMode> setting,
            ClientFontRenderer clientFontRenderer,
            float f,
            float f1,
            float f2,
            float f3,
            ModuleMetadata moduleMetadata,
            int i) {
        super(setting, clientFontRenderer, f, f1, f2, f3, moduleMetadata, i);
    }

    public int getLabelColor() {
        ModuleBase moduleBase = (ModuleBase) this.entryValue;
        return moduleBase.isEnabled() ? -1 : -1761607681;
    }
}
