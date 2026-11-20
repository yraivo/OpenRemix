package remix.ui.hud;

import net.minecraft.entity.Entity;

public class OverlayWidget {
    private static int legacyWidgetState;
    public int color;
    public float right;
    private boolean hovered;
    public float bottom;
    public float top;
    public float left;
    public int actionId;
    private boolean active;

    public float getTop() {
        return this.top;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public boolean isActive() {
        return this.active;
    }

    static {
        if (legacyHoverSeed() == 0) {
            setLegacyWidgetState(22);
        }
    }

    public void method_8d9b536bcdc9991c48fb() {}

    public OverlayWidget(int i, float f, float f1, float f2, float f3, int j) {
        super();
        int initialFlowToken = getLegacyWidgetState();

        int l = initialFlowToken;

        this.actionId = i;
        this.left = f;
        int k = l;
        this.top = f1;
        this.right = f2;
        this.bottom = f3;
        this.color = j;
        if (k != 0) {
            Entity.setLegacyGlobalFlowMarker(new String[1]);
        }
    }

    public static int legacyHoverSeed() {
        int i = getLegacyWidgetState();
        return i == 0 ? 56 : 0;
    }

    public void setActive(boolean flag) {
        this.active = flag;
    }

    public void updateHoverState(int i, int j) {
        int k = legacyHoverSeed();
        this.hovered =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (i >= this.left && j >= this.top && i <= this.right && j <= this.bottom
                                ? 1
                                : 0));
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyWidgetState(++k);
        }
    }

    public int getActionId() {
        return this.actionId;
    }

    public float getRight() {
        return this.right;
    }

    public float getLeft() {
        return this.left;
    }

    public void mouseClicked(int i, int j, int k) {}

    public static int getLegacyWidgetState() {
        return remix.ui.hud.OverlayWidget.legacyWidgetState;
    }

    public void resetActive() {
        this.setActive(false);
    }

    public boolean isHovered() {
        return this.hovered;
    }

    public void setY1(float f) {
        this.top = f;
    }

    public void setHovered(boolean flag) {
        this.hovered = flag;
    }

    public void setY2(float f) {
        this.bottom = f;
    }

    public void setX1(float f) {
        this.left = f;
    }

    public float getBottom() {
        return this.bottom;
    }

    public static void setLegacyWidgetState(int i) {
        remix.ui.hud.OverlayWidget.legacyWidgetState = i;
    }

    public void setX2(float f) {
        this.right = f;
    }
}
