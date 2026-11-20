package remix.ui.widget;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import remix.ui.hud.OverlayWidget;

import org.lwjgl.input.Mouse;

import java.awt.Color;

public final class OverlayScrollbar extends OverlayWidget {
    private float dragStartMouseY;
    private float wheelVelocity;
    private float contentHeight;
    private ModalOverlayPanel modalOverlayPanel;
    private float scrollOffset;
    private float dragStartThumbOffset;

    public OverlayScrollbar(
            float f,
            float f1,
            float f2,
            float f3,
            int i,
            ModalOverlayPanel modalOverlayPanelValue,
            float f4) {
        super(-1, f, f1, f2, f3, i);
        this.modalOverlayPanel = modalOverlayPanelValue;
        this.contentHeight = f4;
    }

    public void setScrollOffset(float f) {
        this.scrollOffset = f;
    }

    public void updateHoverState(int mouseX, int mouseY) {
        float viewportHeight = this.bottom - this.top;
        double thumbHeight = viewportHeight / this.contentHeight * viewportHeight;
        int legacyScrollSeed = OverlayIconButton.legacyScrollSeed();
        double thumbOffset = this.scrollOffset / this.contentHeight * viewportHeight;
        if (this.modalOverlayPanel.isHovered()) {
            if (Mouse.hasWheel()) {
                int wheel = Mouse.getDWheel();
                if (wheel > 0) {
                    this.wheelVelocity -= 5.0F;
                } else if (wheel < 0) {
                    this.wheelVelocity += 5.0F;
                }
            }
            if (this.wheelVelocity > 0.0F) {
                this.wheelVelocity--;
                if (legacyScrollSeed == 0 && this.wheelVelocity < 0.0F) {
                    this.wheelVelocity++;
                }
            } else if (this.wheelVelocity < 0.0F) {
                this.wheelVelocity++;
            }
        }

        if (this.isActive()) {
            this.scrollOffset = (mouseY - this.dragStartMouseY + this.dragStartThumbOffset)
                    / (viewportHeight / this.contentHeight);
        }

        this.scrollOffset = Math.min(Math.max(this.scrollOffset + this.wheelVelocity, 0.0F),
                this.contentHeight - (this.bottom - this.top));
        Gui.drawRect(this.left, this.top, this.right, this.bottom, new Color(0, 0, 0, 30).getRGB());
        Gui.drawRect(this.left, this.top + thumbOffset, this.right, this.top + thumbOffset + thumbHeight, this.color);
        this.setHovered(mouseX >= this.left && mouseY >= this.top + thumbOffset
                && mouseX <= this.right && mouseY <= this.top + thumbOffset + thumbHeight);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            OverlayIconButton.setLegacyIconFlowMarker(legacyScrollSeed + 1);
        }
    }

    public float getScrollOffset() {
        return this.scrollOffset;
    }

    public void mouseClicked(int i, int j, int k) {
        if (this.isHovered() && k == 0) {
            float f = this.bottom - this.top;
            float f1 = this.scrollOffset / this.contentHeight * f;
            this.dragStartThumbOffset = f1;
            this.dragStartMouseY = j;
            this.setActive(true);
        }

        super.mouseClicked(i, j, k);
    }
}
