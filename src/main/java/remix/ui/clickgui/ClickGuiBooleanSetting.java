package remix.ui.clickgui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

import optifine.MathUtils;

import remix.client.ClientServices;
import remix.setting.Setting;
import remix.util.render.Render2D3DUtils;

import java.awt.Color;

public final class ClickGuiBooleanSetting extends ClickGuiSettingComponent {
    private double sQ;
    private final int sR = 6;

    public boolean mouseReleased(int i, int j, int k) {
        return super.mouseReleased(i, j, k);
    }

    public boolean mousePressed(int i, int j, int k) {
        if (MathUtils.isHovered(this.x, this.y, this.width, this.height, i, j)) {
            this.setting.setBooleanValue(!this.setting.asBoolean());
            return true;
        } else {
            return false;
        }
    }

    public void draw(int j1, int k1) {
        double d0 = this.y + this.height / 2.0;
        float f =
                (float)
                        (this.y
                                + this.height / 2.0
                                - remix.ui.clickgui.ClickGuiSettingComponent.fontRenderer
                                                .getHeight()
                                        / 2.0F);
        int j = this.setting.getOwner().getCategory().getColor();
        int i1 = Item.getLegacyItemFlowMarker();
        boolean settingValue = this.setting.asBoolean();
        int i = i1;
        Gui.drawRect(
                this.x + 2.0, this.y, this.x + this.width - 2.0, this.y + this.height, -1358954496);
        remix.ui.clickgui.ClickGuiSettingComponent.fontRenderer.drawString(
                this.setting.getName(),
                (float) this.x + 7.0F,
                f,
                settingValue ? -1052689 : -4210753);
        ClickGuiCheckMode checkMode =
                (ClickGuiCheckMode)
                        ClientServices.moduleManager.clickGui.checkModeSetting.getValue();
        switch (checkMode) {
            case CHECK:
                int k = settingValue ? -13710223 : -1035212;
                if (settingValue) {
                    Render2D3DUtils.drawCheckmark(
                            this.x + this.width - 12.0 - 1.0, d0 - 3.0 - 1.0, k);
                    if (i != 0) {
                        break;
                    }
                }

                Render2D3DUtils.drawCross(this.x + this.width - 12.0 - 0.5, d0 - 3.0 - 0.5, k);
                if (i != 0) {
                    break;
                }
            case BOX:
                int l = settingValue ? j : 0;
                Render2D3DUtils.drawBorderedRect(
                        this.x + this.width - 12.0 - 1.0,
                        d0 - 3.0 - 1.0,
                        this.x + this.width - 6.0 + 1.0,
                        d0 + 3.0 + 1.0,
                        0.5,
                        l,
                        new Color(j).darker().getRGB());
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            Item.setLegacyItemFlowMarker(++i);
        }
    }

    public ClickGuiBooleanSetting(Setting setting, double d0, double d1, double d2, double d3) {
        super(setting, d0, d1, d2, d3);
        this.sQ = 0.5;
    }
}
