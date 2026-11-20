package remix.ui.clickgui;

import remix.util.render.Render2D3DUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import optifine.MathUtils;
import remix.setting.Setting;
import remix.ui.clickgui.ClickGuiSettingComponent;

import java.awt.Color;

public final class ClickGuiNumericSetting extends ClickGuiSettingComponent {
    private boolean ie;
    private static String sT;
    private final int mu = 5;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        sT = ": ";
    }

    public ClickGuiNumericSetting(Setting setting, double d0, double d1, double d2, double d3) {
        super(setting, d0, d1, d2, d3);
    }

    public boolean mousePressed(int i, int j, int k) {
        if (MathUtils.isHovered(this.x, this.y, this.width, this.height, i, j)
                && k == 0) {
            this.ie = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean mouseReleased(int j, int k, int i) {
        if (i == 0 && this.ie) {
            this.ie = false;
            return true;
        } else {
            return false;
        }
    }

    public void draw(int i, int i1) {
        int k = this.setting.getOwner().getCategory().getColor();
        float f =
                (float)
                        (this.y
                                + this.height / 2.0
                                - remix.ui.clickgui.ClickGuiSettingComponent.fontRenderer.getHeight() / 2.0F);
        int l = Item.getLegacyItemFlowMarker();
        double d0 =
                (this.setting.asDouble() - this.setting.getMinimum())
                        / (this.setting.getMaximum() - this.setting.getMinimum());
        int j = l;
        String text =
                this.setting.getName()
                        + sT
                        + MathUtils.formatNumeric(this.setting.asDouble());
        Gui.drawRect(
                this.x + 2.0, this.y, this.x + this.width - 2.0, this.y + this.height, -1358954496);
        remix.ui.clickgui.ClickGuiSettingComponent.fontRenderer.drawString(text, (float) this.x + 7.0F, f, -1);
        double d1 = this.x + 5.0;
        double d2 = this.width - 10.0;
        if (this.ie) {
            double d3 = (i - d1) / d2;
            double d4 =
                    (this.setting.getMaximum() - this.setting.getMinimum()) * d3
                            + this.setting.getMinimum();
            d4 -= d4 % this.setting.getIncrement();
            d4 =
                    MathUtils.roundToDisplay(
                            MathUtils.round(
                                    MathUtils.roundToStep(
                                            d4, this.setting.getIncrement()),
                                    3));
            this.setting.setNumericValue(d4);
        }

        Gui.drawRect(
                d1, this.y + this.height - 3.5, d1 + d2 * d0, this.y + this.height - 2.5, k);
        Render2D3DUtils.drawCircle(
                (float) (d1 + d2 * d0 - 1.0),
                (float) (this.y + this.height - 3.0),
                1.0F,
                new Color(k).darker().getRGB());
        if (j == 0) {
            Entity.setLegacyGlobalFlowMarker(new String[1]);
        }
    }
}
