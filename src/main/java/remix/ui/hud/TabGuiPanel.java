package remix.ui.hud;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;

import org.lwjgl.input.Keyboard;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.compat.vecmath.Vector2f;
import remix.module.ModuleCategory;
import remix.module.ModuleMetadata;
import remix.module.render.ClickGuiModule;
import remix.setting.Setting;
import remix.ui.animation.AnimationInterpolator;
import remix.ui.clickgui.ClickGuiCategoryHeader;
import remix.ui.clickgui.ClickGuiEntryKind;
import remix.ui.clickgui.ClickGuiModuleEntryControl;
import remix.ui.clickgui.ClickGuiSettingControl;
import remix.ui.clickgui.ClickGuiSubGroupControl;
import remix.ui.font.ClientFontRenderer;
import remix.util.render.Render2D3DUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TabGuiPanel {
    private float rowHeight;
    private float top;
    private int selectionIndex;
    private AnimationInterpolator animationInterpolator;
    private List<ClickGuiSettingControl> rows;
    private Setting<HudMode> setting;
    private List<?> entries;
    private float minimumWidth;
    private float left;
    private ClickGuiEntryKind clickGuiEntryKind;
    private ClientFontRenderer clientFontRenderer;

    private void buildRows() {
        boolean firstEntryOnly = ClickGuiEntryKind.getLegacyTabGuiTraversalMarker() != null;
        float rowY = this.top;
        this.rows = new ArrayList<>();
        for (Object entry : this.entries) {
            switch (this.clickGuiEntryKind) {
                case CATEGORY:
                    this.rows.add(
                            new ClickGuiCategoryHeader(
                                    this.setting,
                                    this.clientFontRenderer,
                                    this.left,
                                    rowY,
                                    this.minimumWidth,
                                    this.rowHeight,
                                    (ModuleCategory) entry,
                                    -14606047));
                    break;
                case MODULE:
                    this.rows.add(
                            new ClickGuiModuleEntryControl(
                                    this.setting,
                                    this.clientFontRenderer,
                                    this.left,
                                    rowY,
                                    this.minimumWidth,
                                    this.rowHeight,
                                    (ModuleMetadata) entry,
                                    -14606047));
                    break;
                case SETTING:
                    this.rows.add(
                            new ClickGuiSubGroupControl(
                                    this.setting,
                                    this.clientFontRenderer,
                                    this.left,
                                    rowY,
                                    this.minimumWidth,
                                    this.rowHeight,
                                    (Setting) entry,
                                    -14606047));
                    break;
            }
            rowY += this.rowHeight;
            if (firstEntryOnly) {
                break;
            }
        }
    }

    public void init() {
        this.animationInterpolator =
                new AnimationInterpolator(
                        5.0F,
                        new Vector2f(0.0F, this.top + this.rowHeight * this.selectionIndex),
                        new Vector2f(0.0F, this.top + this.rowHeight * this.selectionIndex));
        this.buildRows();
    }

    private float measureLabelWidth() {
        boolean firstEntryOnly = ClickGuiEntryKind.getLegacyTabGuiTraversalMarker() != null;
        ClientFontRenderer font = RemixClient.fonts.menuRoboto16;
        float widest = 0.0F;
        for (ClickGuiSettingControl<?> row : this.rows) {
            widest = Math.max(widest, font.getStringWidth(row.getLabel()));
            if (firstEntryOnly) {
                break;
            }
        }
        return widest;
    }

    public void handleKey(int key) {
        if (this.rows.isEmpty()) {
            return;
        }
        boolean legacyNavigation = ClickGuiEntryKind.getLegacyTabGuiTraversalMarker() != null;
        ClickGuiSettingControl<?> selected = this.rows.get(this.selectionIndex);
        if (selected.capturingKeys) {
            selected.handleKey(key);
            if (!legacyNavigation) {
                return;
            }
        }

        Keyboard.enableRepeatEvents(true);
        if (key == Keyboard.KEY_UP || key == Keyboard.KEY_DOWN) {
            int direction = key == Keyboard.KEY_UP ? -1 : 1;
            this.selectionIndex = Math.floorMod(this.selectionIndex + direction, this.rows.size());
            this.animationInterpolator.setTargetPosition(
                    new Vector2f(0.0F, this.top + this.rowHeight * this.selectionIndex));
            if (!legacyNavigation) {
                return;
            }
            if (key == Keyboard.KEY_UP) {
                this.selectionIndex = (this.selectionIndex + 1) % this.rows.size();
                this.animationInterpolator.setTargetPosition(
                        new Vector2f(0.0F, this.top + this.rowHeight * this.selectionIndex));
            }
        }
        this.rows.get(this.selectionIndex).handleKey(key);
    }

    public TabGuiPanel(
            Setting<HudMode> setting,
            ClientFontRenderer clientFontRenderer,
            float f,
            float f1,
            float f2,
            float f3,
            List<?> list,
            ClickGuiEntryKind clickGuiEntryKind) {
        super();
        int[] initialFlowMarker = ClickGuiEntryKind.getLegacyTabGuiTraversalMarker();

        int[] aint1 = initialFlowMarker;

        this.setting = setting;
        this.clientFontRenderer = clientFontRenderer;
        this.left = f;
        this.top = f1;
        this.minimumWidth = f2;
        this.rowHeight = f3;
        this.entries = new ArrayList<>(list);
        this.entries.removeIf(object -> object instanceof ClickGuiModule);
        this.clickGuiEntryKind = clickGuiEntryKind;
        int[] aint = aint1;
        this.init();
        if (aint != null) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }
    }

    public void draw() {
        boolean legacyDoubleBackground = ClickGuiEntryKind.getLegacyTabGuiTraversalMarker() != null;
        int i = ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor();
        Color color = new Color(i);
        float f = this.measureLabelWidth();
        float f1 = this.minimumWidth;
        if (f > f1 - 10.0F) {
            f1 = f + 10.0F;
        }

        switch (((HudMode) this.setting.getValue())) {
            case REMIX:
                Gui.drawRect(
                        this.left,
                        this.top,
                        this.left + f1,
                        this.top + this.entries.size() * this.rowHeight,
                        -15001573);
                Gui.drawRect(
                        this.left,
                        this.animationInterpolator.stepY(),
                        this.left + f1,
                        this.animationInterpolator.stepY() + this.rowHeight,
                        i);
                if (!legacyDoubleBackground) {
                    break;
                }
            case AMBER:
                Render2D3DUtils.drawBorderedRect(
                        this.left,
                        this.top,
                        this.left + f1,
                        this.top + this.entries.size() * this.rowHeight,
                        0.5,
                        -1769963392,
                        1677721600);
                Gui.drawRect(
                        this.left + 0.5,
                        this.animationInterpolator.stepY() + 0.5,
                        this.left + f1 - 0.5,
                        this.animationInterpolator.stepY() + this.rowHeight - 0.5,
                        new Color(color.getRed(), color.getGreen(), color.getBlue(), 150).getRGB());
        }

        this.rows.forEach(ClickGuiSettingControl::drawRow);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ClickGuiEntryKind.setLegacyTabGuiTraversalMarker(new int[5]);
        }
    }
}
