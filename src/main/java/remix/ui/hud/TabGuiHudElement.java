package remix.ui.hud;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import remix.ui.hud.HudElement;
import remix.ui.hud.HudMode;
import remix.module.ModuleCategory;
import remix.setting.Setting;
import remix.ui.clickgui.ClickGuiEntryKind;
import remix.ui.font.ClientFontRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TabGuiHudElement implements HudElement {
    private static int[] legacyTabGuiFlowMarker;
    public List<TabGuiPanel> openPanels;

    public void handleKey(int i) {
        getLegacyTabGuiFlowMarker();
        this.openPanels.get(this.openPanels.size() - 1).handleKey(i);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyTabGuiFlowMarker(new int[2]);
        }
    }

    public static int[] getLegacyTabGuiFlowMarker() {
        return remix.ui.hud.TabGuiHudElement.legacyTabGuiFlowMarker;
    }

    public void render(ScaledResolution scaledResolution) {
        this.openPanels.forEach(TabGuiPanel::draw);
    }

    static {
        if (getLegacyTabGuiFlowMarker() != null) {
            setLegacyTabGuiFlowMarker(new int[5]);
        }
    }

    public static void setLegacyTabGuiFlowMarker(int[] aint) {
        remix.ui.hud.TabGuiHudElement.legacyTabGuiFlowMarker = aint;
    }

    public TabGuiHudElement(
            Setting<HudMode> setting, ClientFontRenderer clientFontRenderer) {
        int[] aint1 = getLegacyTabGuiFlowMarker();
        this.openPanels = new ArrayList<>();
        int[] aint = aint1;
        List list =
                Arrays.asList(
                        Arrays.copyOfRange(
                                ModuleCategory.values(), 1, ModuleCategory.values().length));
        this.openPanels.add(
                new TabGuiPanel(
                        setting,
                        clientFontRenderer,
                        5.0F,
                        20.0F,
                        80.0F,
                        12.0F,
                        list,
                        ClickGuiEntryKind.CATEGORY));
        if (aint != null) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }
    }
}
