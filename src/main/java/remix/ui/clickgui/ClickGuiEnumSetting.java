package remix.ui.clickgui;

import remix.setting.SettingOption;

import net.minecraft.client.gui.Gui;
import net.minecraft.item.Item;
import optifine.MathUtils;
import remix.setting.Setting;
import remix.ui.clickgui.ClickGuiSettingComponent;

public final class ClickGuiEnumSetting extends ClickGuiSettingComponent {

    private static String h;

    public boolean mouseReleased(int i, int j, int k) {
        return super.mouseReleased(i, j, k);
    }

    static {
        h = ": ";
    }

    // region String decoding support

    // endregion

    public ClickGuiEnumSetting(Setting setting, double d0, double d1, double d2, double d3) {
        super(setting, d0, d1, d2, d3);
    }

    public void draw(int i, int j) {
        float f =
                (float)
                        (this.y
                                + this.height / 2.0
                                - remix.ui.clickgui.ClickGuiSettingComponent.fontRenderer.getHeight() / 2.0F);
        SettingOption value = (SettingOption) this.setting.getValue();
        Gui.drawRect(
                this.x + 2.0, this.y, this.x + this.width - 2.0, this.y + this.height, -1358954496);
        remix.ui.clickgui.ClickGuiSettingComponent.fontRenderer.drawString(
                this.setting.getName() + h + value.getDisplayName(), (float) this.x + 7.0F, f, -1);
    }

    public boolean mousePressed(int i, int j, int k) {
        int l = Item.getLegacyItemFlowMarker();
        if (MathUtils.isHovered(this.x, this.y, this.width, this.height, i, j)
                && (k == 0 || k == 1)) {
            int i1;
            label20:
            {
                i1 = this.setting.getOptionIndex();
                if (k == 0) {
                    if (++i1 <= this.setting.getEnumValues().length - 1) {
                        break label20;
                    }

                    i1 = 0;
                    if (l != 0) {
                        break label20;
                    }
                }

                if (--i1 < 0) {
                    i1 = this.setting.getEnumValues().length - 1;
                }
            }

            this.setting.setValue(this.setting.getEnumValues()[i1]);
            return true;
        } else {
            return false;
        }
    }

    // region String decoding support

    // endregion
}
