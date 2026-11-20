package remix.config;

import remix.setting.SettingOption;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.ModuleCategory;
import remix.module.render.ClickGuiModule;
import remix.ui.clickgui.ClickGuiCategoryPanel;
import remix.ui.clickgui.ClickGuiScreen;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class PanelsJsonConfig extends AbstractJsonConfigFile {
    private List<ClickGuiCategoryPanel> pendingPanels;

    public PanelsJsonConfig(File configFile) {
        super(configFile);
    }

    public void loadFrom(Object data) throws IOException {
        this.loadJson((JsonObject) data);
    }

    public void loadJson(JsonObject config) throws IOException {
        AccountsTextConfig.getLegacyAccountConfigMarker();
        if (config == null) {
            return;
        }

        RemixClient.INSTANCE.getClientServices();
        this.loadStyle(config, ClientServices.moduleManager.clickGui);

        ClickGuiScreen screen = ClientServices.clickGui;
        if (screen == null) {
            return;
        }

        List<ClickGuiCategoryPanel> currentPanels = screen.getCategoryPanels();
        if (this.pendingPanels == null || this.pendingPanels.isEmpty()) {
            this.pendingPanels = new ArrayList<>(currentPanels);
            currentPanels.clear();
        }

        for (ModuleCategory category : ModuleCategory.values()) {
            String key = category.getDisplayName();
            if (config.has(key)) {
                this.restorePanel(screen, category, config.getAsJsonObject(key));
            }
        }
    }

    private void loadStyle(JsonObject config, ClickGuiModule style) {
        if (config.has("checkBoxMode")) {
            style.checkModeSetting.setEnumValueByName(config.get("checkBoxMode").getAsString());
        }
        if (config.has("backgroundMode")) {
            style.backgroundSetting.setEnumValueByName(config.get("backgroundMode").getAsString());
        }
        if (config.has("optionIcon")) {
            style.optionsSetting.setEnumValueByName(config.get("optionIcon").getAsString());
        }
        if (config.has("categoryIcon")) {
            style.iconSetting.setBooleanValue(config.get("categoryIcon").getAsBoolean());
        }
        if (config.has("statusBar")) {
            style.statusBarSetting.setBooleanValue(config.get("statusBar").getAsBoolean());
        }
    }

    private void restorePanel(ClickGuiScreen screen, ModuleCategory category, JsonObject layout) {
        Iterator<ClickGuiCategoryPanel> pending = this.pendingPanels.iterator();
        while (pending.hasNext()) {
            ClickGuiCategoryPanel previous = pending.next();
            if (previous.getCategory() != category) {
                continue;
            }

            boolean hasSavedPosition = layout.has("x") && layout.has("y");
            double x = hasSavedPosition ? layout.get("x").getAsDouble() : previous.getX();
            double y = hasSavedPosition ? layout.get("y").getAsDouble() : previous.getY();
            ClickGuiCategoryPanel restored = screen.addCategoryPanel(x, y, category);
            if (layout.has("open")) {
                restored.expanded = layout.get("open").getAsBoolean();
            }
            pending.remove();
            return;
        }
    }

    public void saveTo(List entries) throws IOException {
        JsonObject config = new JsonObject();
        RemixClient.INSTANCE.getClientServices();
        AccountsTextConfig.getLegacyAccountConfigMarker();

        ClickGuiModule style = ClientServices.moduleManager.clickGui;
        config.addProperty("checkBoxMode", ((SettingOption) style.checkModeSetting.getValue()).getDisplayName());
        config.addProperty("backgroundMode", ((SettingOption) style.backgroundSetting.getValue()).getDisplayName());
        config.addProperty("optionIcon", ((SettingOption) style.optionsSetting.getValue()).getDisplayName());
        config.addProperty("categoryIcon", style.iconSetting.asBoolean());
        config.addProperty("statusBar", style.statusBarSetting.asBoolean());

        ClickGuiScreen screen = ClientServices.clickGui;
        if (screen != null) {
            for (ClickGuiCategoryPanel panel : screen.getCategoryPanels()) {
                JsonObject layout = new JsonObject();
                layout.addProperty("open", panel.expanded);
                layout.addProperty("x", panel.getX());
                layout.addProperty("y", panel.getY());
                config.add(panel.getCategory().getDisplayName(), layout);
            }
        }

        entries.add(config);
    }
}
