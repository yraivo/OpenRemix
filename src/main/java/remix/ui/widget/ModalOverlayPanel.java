package remix.ui.widget;

import net.minecraft.entity.Entity;

public class ModalOverlayPanel {
    public float top;
    public float right;
    private static String[] packedTextFragments;
    public float bottom;
    public boolean hovered;
    public float left;
    public int backgroundColor;
    private static String legacyBoundsState;

    public void setHeight(float f) {
        this.bottom = f;
    }

    public float topEdge() {
        return this.top;
    }

    public void dn() {}

    public void setY(float f) {
        this.top = f;
    }

    // region String decoding support

    // endregion

    public void handleButtonAction(int i) {}

    public void mouseReleased(int i, int j) {}

    public void setHovered(boolean flag) {
        this.hovered = flag;
    }

    public static String getLegacyBoundsState() {
        return remix.ui.widget.ModalOverlayPanel.legacyBoundsState;
    }

    public void setBounds(float f, float f1, float f2, float f3) {
        this.left = f;
        getLegacyBoundsState();
        this.top = f1;
        this.right = f2;
        this.bottom = f3;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyBoundsState(packedTextFragments[0]);
        }
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
        if (getLegacyBoundsState() == null) {
            setLegacyBoundsState(packedTextFragments[1]);
        }
    }

    public ModalOverlayPanel(float f, float f1, float f2, float f3, int i) {
        super();
        String initialFlowMarker = getLegacyBoundsState();

        String text = initialFlowMarker;

        String textValue = text;
        this.left = f;
        this.top = f1;
        this.right = f2;
        this.bottom = f3;
        this.backgroundColor = i;
        if (textValue == null) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }
    }

    public void setX(float f) {
        this.left = f;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.ui.widget.ModalOverlayPanel.packedTextFragments[0] = new String("oM4qLb");
        remix.ui.widget.ModalOverlayPanel.packedTextFragments[1] = new String("kwylbb");
    }

    // endregion

    public void init() {}

    public float leftEdge() {
        return this.left;
    }

    public boolean isHovered() {
        return this.hovered;
    }

    public float rightEdge() {
        return this.right;
    }

    public void draw(int i, int j) {
        this.hovered =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (i >= this.left && j >= this.top && i <= this.right && j <= this.bottom
                                ? 1
                                : 0));
    }

    public float bottomEdge() {
        return this.bottom;
    }

    public void mouseClicked(int i, int j, int k) {}

    public static void setLegacyBoundsState(String text) {
        remix.ui.widget.ModalOverlayPanel.legacyBoundsState = text;
    }

    public void keyTyped(char c0, int i) {}

    // region String decoding support

    // endregion

    public void setWidth(float f) {
        this.right = f;
    }
}
