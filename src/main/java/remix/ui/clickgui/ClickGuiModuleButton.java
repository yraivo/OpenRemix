package remix.ui.clickgui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.ModuleBase;
import remix.module.render.ClickGuiModule;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;
import remix.util.render.Render2D3DUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ClickGuiModuleButton extends ClickGuiComponent {
    private List<ClickGuiComponent> settingComponents;
    private ModuleBase module;

    private boolean expanded;
    private static ResourceLocation linesOptionsIcon;
    private boolean waitingForKey;

    private double statusBarWidth;
    private ClickGuiCategoryPanel categoryPanel;
    private static ClientFontRenderer fontRenderer;
    private static ResourceLocation dotsOptionsIcon;

    public boolean mouseReleased(int i, int j, int k) {
        int l = ModuleBase.getInvertedModuleFlowToken();
        if (this.expanded) {
            for (java.util.Iterator<?> __recoveryIterator171_1376 =
                            (this.settingComponents).iterator();
                    (__recoveryIterator171_1376).hasNext(); ) {
                ClickGuiComponent clickGuiComponent =
                        (ClickGuiComponent) (__recoveryIterator171_1376).next();
                if (clickGuiComponent.mouseReleased(i, j, k)) {
                    return true;
                }

                if (l == 0) {
                    break;
                }
            }
        }

        return false;
    }

    public ClickGuiModuleButton(
            ClickGuiCategoryPanel clickGuiCategoryPanel,
            ModuleBase moduleBase,
            double d0,
            double d1,
            double d2,
            double d3) {
        this(
                clickGuiCategoryPanel,
                moduleBase,
                d0,
                d1,
                d2,
                d3,
                ModuleBase.getInvertedModuleFlowToken());
    }

    private ClickGuiModuleButton(
            ClickGuiCategoryPanel categoryPanelValue,
            ModuleBase moduleBase,
            double d0,
            double d1,
            double d2,
            double d3,
            int initialFlowToken) {
        super(d0, d1, d2, d3);

        int j = initialFlowToken;

        this.settingComponents = new ArrayList<>();
        int i = j;
        this.categoryPanel = categoryPanelValue;
        this.module = moduleBase;
        this.initializeChildren();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ModuleBase.setLegacyModuleFlowToken(++i);
        }
    }

    public double getHeight() {
        int j = ModuleBase.getLegacyModuleFlowToken();
        double heightValue = this.height;
        int i = j;
        if (this.expanded) {
            for (java.util.Iterator<?> __recoveryIterator171_24024 =
                            (this.settingComponents).iterator();
                    (__recoveryIterator171_24024).hasNext(); ) {
                ClickGuiComponent clickGuiComponent =
                        (ClickGuiComponent) (__recoveryIterator171_24024).next();
                heightValue += clickGuiComponent.getHeight();
                if (i != 0) {
                    break;
                }
            }
        }

        return heightValue;
    }

    protected void initializeChildren() {
        boolean normalSettingSelection = ModuleBase.getInvertedModuleFlowToken() != 0;
        List<?> settings = this.module.getSettings();
        double nextY = this.y + this.height;

        for (Object entry : settings) {
            Setting setting = (Setting) entry;
            ClickGuiComponent component = null;
            if (setting.isEnum()) {
                component =
                        new ClickGuiEnumSetting(setting, this.x, nextY, this.width, this.height);
            }
            if ((component == null || !normalSettingSelection) && setting.isNumeric()) {
                component =
                        new ClickGuiNumericSetting(setting, this.x, nextY, this.width, this.height);
            }
            if (component == null || !normalSettingSelection) {
                component =
                        new ClickGuiBooleanSetting(setting, this.x, nextY, this.width, this.height);
            }

            this.settingComponents.add(component);
            nextY += component.getHeight();
            if (!normalSettingSelection) {
                break;
            }
        }
    }

    public boolean keyPressed(int i) {
        if (this.waitingForKey) {
            if (i != 1) {
                this.module.setKeyBind(i);
            }

            this.waitingForKey = false;
            return true;
        } else {
            return false;
        }
    }

    public void moveBy(double d0, double d1) {
        int j = ModuleBase.getLegacyModuleFlowToken();
        Iterator iterator = this.settingComponents.iterator();
        int i = j;

        while (iterator.hasNext()) {
            ClickGuiComponent clickGuiComponent = (ClickGuiComponent) iterator.next();
            clickGuiComponent.moveBy(d0, d1);
            if (i != 0) {
                break;
            }
        }

        super.moveBy(d0, d1);
    }

    public boolean mousePressed(int i, int j, int k) {
        int i1 = ModuleBase.getLegacyModuleFlowToken();
        boolean flag = this.containsPoint(i, j);
        int l = i1;
        if (flag) {
            switch (k) {
                case 0:
                    if (this.module instanceof ClickGuiModule) {
                        break;
                    }

                    this.module.toggle();
                    if (l == 0) {
                        break;
                    }
                case 1:
                    if (this.settingComponents.isEmpty()) {
                        break;
                    }

                    this.expanded =
                            remix.compat.compiler.JvmBooleanSlots.fromInt((!this.expanded ? 1 : 0));
                    this.categoryPanel.layoutModuleButtons();
                    if (l == 0) {
                        break;
                    }
                case 2:
                    this.waitingForKey =
                            remix.compat.compiler.JvmBooleanSlots.fromInt(
                                    (!this.waitingForKey ? 1 : 0));
            }

            if (l == 0) {
                return flag;
            }
        }

        if (this.expanded) {
            for (java.util.Iterator<?> __recoveryIterator171_27876 =
                            (this.settingComponents).iterator();
                    (__recoveryIterator171_27876).hasNext(); ) {
                ClickGuiComponent clickGuiComponent =
                        (ClickGuiComponent) (__recoveryIterator171_27876).next();
                if (clickGuiComponent.mousePressed(i, j, k)) {
                    return true;
                }

                if (l != 0) {
                    break;
                }
            }
        }

        return flag;
    }

    static {
        remix.ui.clickgui.ClickGuiModuleButton.fontRenderer = RemixClient.fonts.roboto18;
        remix.ui.clickgui.ClickGuiModuleButton.linesOptionsIcon =
                new ResourceLocation("remix/gui/clickgui/Lines.png");
        remix.ui.clickgui.ClickGuiModuleButton.dotsOptionsIcon =
                new ResourceLocation("remix/gui/clickgui/Dots.png");
    }

    public void draw(int i, int j) {
        int l1 = ModuleBase.getLegacyModuleFlowToken();
        float f = (float) (this.x + this.width / 2.0);
        int k = l1;
        float f1 =
                (float)
                        (this.y
                                + this.height / 2.0
                                - remix.ui.clickgui.ClickGuiModuleButton.fontRenderer.getHeight()
                                        / 2.0F);
        String text = this.waitingForKey ? "Press any key..." : this.module.getDisplayName();
        boolean enabled = this.module.isEnabled();
        int l = !enabled && !this.waitingForKey ? 0 : 1;
        int i1 = l != 0 ? -1052689 : -4210753;
        int j1 = l != 0 ? WorldRenderer.withAlpha(-1744830464, 0.75F) : -1744830464;
        int k1 = this.waitingForKey ? -662245 : (enabled ? -13710223 : -1035212);
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, j1);
        ClickGuiModule clickGui = ClientServices.moduleManager.clickGui;
        if (clickGui.statusBarSetting.asBoolean() && !(this.module instanceof ClickGuiModule)) {
            boolean expandedOrBinding = this.expanded || this.waitingForKey;
            boolean legacyAnimationState = k != 0;
            if (expandedOrBinding) {
                this.statusBarWidth = 3.0;
                if (legacyAnimationState) {
                    Entity.setLegacyGlobalFlowMarker(new String[1]);
                }
            }

            if (!expandedOrBinding || legacyAnimationState) {
                if (this.containsPoint(i, j)) {
                    if (this.statusBarWidth < 3.0) {
                        this.statusBarWidth += 0.25;
                        if (legacyAnimationState && this.statusBarWidth > 0.0) {
                            this.statusBarWidth -= 0.5;
                        }
                    }
                } else if (this.statusBarWidth > 0.0) {
                    this.statusBarWidth -= 0.5;
                }
            }

            Gui.drawRect(
                    this.x,
                    this.y,
                    this.x + this.statusBarWidth,
                    this.y + this.height,
                    new Color(k1).brighter().getRGB());
            Gui.drawRect(
                    this.x,
                    this.y,
                    this.x + (this.statusBarWidth < 2.0 ? 0.0 : this.statusBarWidth / 1.5),
                    this.y + this.height,
                    k1);
        }

        if (!this.settingComponents.isEmpty()) {
            switch (((ClickGuiOptionsMode) clickGui.optionsSetting.getValue())) {
                case LINES:
                    Render2D3DUtils.drawIcon(
                            remix.ui.clickgui.ClickGuiModuleButton.linesOptionsIcon,
                            (float) (this.x + this.width - 17.0),
                            (float) this.y + 5.0F,
                            20.0F,
                            20.0F);
                    if (k == 0) {
                        break;
                    }
                case DOTS:
                    Render2D3DUtils.drawIcon(
                            remix.ui.clickgui.ClickGuiModuleButton.dotsOptionsIcon,
                            (float) (this.x + this.width - 17.0),
                            (float) this.y + 5.0F,
                            25.0F,
                            25.0F);
            }
        }

        remix.ui.clickgui.ClickGuiModuleButton.fontRenderer.drawCenteredString(
                text, f - 5.0F, f1, i1);
        if (this.expanded) {
            for (java.util.Iterator<?> __recoveryIterator171_33886 =
                            (this.settingComponents).iterator();
                    (__recoveryIterator171_33886).hasNext(); ) {
                ClickGuiComponent clickGuiComponent =
                        (ClickGuiComponent) (__recoveryIterator171_33886).next();
                clickGuiComponent.draw(i, j);
                if (k != 0) {
                    break;
                }
            }
        }
    }
}
