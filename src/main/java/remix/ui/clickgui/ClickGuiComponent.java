package remix.ui.clickgui;

import optifine.MathUtils;

public class ClickGuiComponent {
    private static String legacyComponentFlowMarker;
    public double x;
    private static String[] packedTextFragments;
    public double height;
    public double y;
    public double width;

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.ui.clickgui.ClickGuiComponent.packedTextFragments[0] = new String("fZ65xb");
    }

    // endregion

    public static String getLegacyComponentFlowMarker() {
        return remix.ui.clickgui.ClickGuiComponent.legacyComponentFlowMarker;
    }

    public double getY() {
        return this.y;
    }

    public ClickGuiComponent(double d0, double d1, double widthValue, double heightValue) {
        this.x = d0;
        this.y = d1;
        this.width = widthValue;
        this.height = heightValue;
    }

    public boolean mouseReleased(int i, int j, int k) {
        return false;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (getLegacyComponentFlowMarker() == null) {
            setLegacyComponentFlowMarker(packedTextFragments[0]);
        }
    }

    public boolean mousePressed(int i, int j, int k) {
        return false;
    }

    public boolean containsPoint(int i, int j) {
        return MathUtils.isHovered(this.x, this.y, this.width, this.height, i, j);
    }

    public double getX() {
        return this.x;
    }

    public void draw(int i, int j) {}

    public double getWidth() {
        return this.width;
    }

    public static void setLegacyComponentFlowMarker(String text) {
        remix.ui.clickgui.ClickGuiComponent.legacyComponentFlowMarker = text;
    }

    protected void initializeChildren() {}

    // region String decoding support

    // endregion

    public boolean keyPressed(int i) {
        return false;
    }

    public void moveBy(double d0, double d1) {
        this.x += d0;
        this.y += d1;
    }

    public double getHeight() {
        return this.height;
    }
}
