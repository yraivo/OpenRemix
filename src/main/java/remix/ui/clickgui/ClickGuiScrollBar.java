package remix.ui.clickgui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;

import optifine.MathUtils;

import org.lwjgl.input.Mouse;

import java.awt.Color;

public final class ClickGuiScrollBar extends ClickGuiComponent {
    private boolean dragging;
    public double scrollDelta;
    public double contentHeight;
    private int trackColor;
    private static String[] packedTextFragments;
    private double dragOffsetY;
    public double thumbOffsetY;

    public boolean containsPoint(int i, int j) {
        return MathUtils.isHovered(this.x, this.y, this.width, this.height, i, j);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.ui.clickgui.ClickGuiScrollBar.packedTextFragments[0] = new String("RwzEP");
    }

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public double getContentScrollOffset() {
        double d0 = 1.0 / this.height * this.contentHeight;
        return this.scrollDelta * d0;
    }

    public void setContentHeight(double contentHeightValue) {
        this.thumbOffsetY = this.thumbOffsetY * (1.0 / contentHeightValue * this.contentHeight);
        this.contentHeight = contentHeightValue;
    }

    public void setViewportHeight(double heightValue) {
        this.height = heightValue;
    }

    // region String decoding support

    // endregion

    public void draw(int mouseX, int mouseY) {
        double viewportFraction = 1.0 / this.contentHeight * this.height;
        boolean legacyMarkerSet = ClickGuiComponent.getLegacyComponentFlowMarker() != null;
        double thumbHeight = this.height * viewportFraction;
        int wheelStep = 0;
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel > 0) {
                wheelStep = -5;
                if (!legacyMarkerSet) {
                    Entity.setLegacyGlobalFlowMarker(new String[5]);
                }
            } else if (wheel < 0) {
                wheelStep = 5;
            }
        }

        double previousThumbOffset = this.thumbOffsetY;
        if (this.dragging) {
            this.thumbOffsetY = mouseY - this.y - this.dragOffsetY;
        }

        this.thumbOffsetY =
                Math.max(Math.min(this.thumbOffsetY + wheelStep, this.height - thumbHeight), 0.0);
        this.scrollDelta = this.thumbOffsetY - previousThumbOffset;
        if (this.contentHeight > this.height) {
            Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, 536870912);
            double thumbY = this.y + this.thumbOffsetY;
            Gui.drawRect(
                    this.x, thumbY, this.x + this.width, thumbY + thumbHeight, this.trackColor);
        }
    }

    public boolean mousePressed(int i, int j, int k) {
        if (k == 0) {
            boolean flag = MathUtils.isHovered(this.x, this.y, this.width, this.height, i, j);
            if (flag) {
                this.dragging = true;
                this.dragOffsetY = j - this.y - this.thumbOffsetY;
            }

            return flag;
        } else {
            return false;
        }
    }

    public boolean mouseReleased(int k, int l, int i) {
        int j = i == 0 ? 1 : 0;
        if (j != 0) {
            this.dragging = false;
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(j);
    }

    public ClickGuiScrollBar(int i, double d0, double d1, double d2, double d3, double d4) {
        this(i, d0, d1, d2, d3, d4, ClickGuiComponent.getLegacyComponentFlowMarker());
    }

    private ClickGuiScrollBar(
            int i,
            double d0,
            double d1,
            double d2,
            double d3,
            double contentHeightValue,
            java.lang.String initialFlowMarker) {
        super(d0, d1, d2, d3);

        this.trackColor = new Color(i).darker().getRGB();
        this.contentHeight = contentHeightValue;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ClickGuiComponent.setLegacyComponentFlowMarker(packedTextFragments[0]);
        }
    }

    public boolean isDragging() {
        return this.dragging;
    }
}
