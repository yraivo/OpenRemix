package remix.ui.widget;

import remix.util.render.Render2D3DUtils;

import net.minecraft.util.ResourceLocation;
import remix.ui.hud.OverlayWidget;

public final class OverlayIconButton extends OverlayWidget {
    private int iconHeight;
    private static int legacyIconFlowMarker;
    private int iconWidth;
    private ResourceLocation resourceLocation;

    public static int legacyScrollSeed() {
        int i = getLegacyIconFlowMarker();
        return i == 0 ? 67 : 0;
    }

    public static void setLegacyIconFlowMarker(int i) {
        remix.ui.widget.OverlayIconButton.legacyIconFlowMarker = i;
    }

    public static int getLegacyIconFlowMarker() {
        return remix.ui.widget.OverlayIconButton.legacyIconFlowMarker;
    }

    static {
        if (getLegacyIconFlowMarker() != 0) {
            setLegacyIconFlowMarker(3);
        }
    }

    public void updateHoverState(int i, int j) {
        double d0 = this.iconHeight / 4;
        double d1 = this.iconWidth / 4;
        Render2D3DUtils.drawCenteredIcon(
                this.resourceLocation, (int) this.left, (int) this.top, this.iconWidth, this.iconHeight);
        this.setHovered(
                i >= this.left - d1 && j >= this.top - d0 && i <= this.left + d1 && j <= this.top + d0);
    }

    public OverlayIconButton(
            int i, float f, float f1, int j, ResourceLocation resourceLocation, int k, int l) {
        super(i, f, f1, f, f1, j);
        this.iconWidth = k;
        this.iconHeight = l;
        this.resourceLocation = resourceLocation;
    }
}
