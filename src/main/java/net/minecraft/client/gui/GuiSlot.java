package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.MathHelper;

import org.lwjgl.input.Mouse;

import remix.ui.account.AltAccountSlotList;

import java.awt.Color;

public abstract class GuiSlot {
    protected boolean showSelectionBox;
    protected int top;
    protected boolean lR;
    protected long lastClicked;
    protected int left;
    protected boolean kS = true;
    protected int selectedElement;
    protected int right;
    protected int slotHeight;
    private int scrollUpButtonID;
    protected int mouseY;
    protected float amountScrolled;
    protected int headerPadding;
    private int scrollDownButtonID;
    protected int bottom;
    protected float initialClickY = -2.0F;
    private boolean enabled;
    protected int height;
    protected float scrollMultiplier;
    protected int mouseX;
    protected int width;
    protected boolean hasListHeader;
    protected MinecraftClient mc;

    protected void drawSelectionBox(int i, int j, int k, int l) {
        int size = this.getSize();
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int j1 = 0;
        String[] astring = astring1;

        while (j1 < size) {
            int k1 = j + j1 * this.slotHeight + this.headerPadding;
            int l1 = this.slotHeight - 4;
            if (k1 > this.bottom || k1 + l1 < this.top) {
                this.method_a8908bd0f12e21ee1ad3(j1, i, k1);
            }

            int i2 = this.left + (this.width / 2 - this.cM() / 2);
            int j2 = this.left + this.width / 2 + this.cM() / 2;
            if (this.showSelectionBox
                    && this.isSelected(j1)
                    && !(this instanceof AltAccountSlotList)) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableTexture2D();
                worldRenderer.startDrawingQuads();
                worldRenderer.setOpaqueVertexColorFromRgb(8421504);
                worldRenderer.addVertexWithUV(i2, k1 + l1 + 2, 0.0, 0.0, 1.0);
                worldRenderer.addVertexWithUV(j2, k1 + l1 + 2, 0.0, 1.0, 1.0);
                worldRenderer.addVertexWithUV(j2, k1 - 2, 0.0, 1.0, 0.0);
                worldRenderer.addVertexWithUV(i2, k1 - 2, 0.0, 0.0, 0.0);
                worldRenderer.setOpaqueVertexColorFromRgb(0);
                worldRenderer.addVertexWithUV(i2 + 1, k1 + l1 + 1, 0.0, 0.0, 1.0);
                worldRenderer.addVertexWithUV(j2 - 1, k1 + l1 + 1, 0.0, 1.0, 1.0);
                worldRenderer.addVertexWithUV(j2 - 1, k1 - 1, 0.0, 1.0, 0.0);
                worldRenderer.addVertexWithUV(i2 + 1, k1 - 1, 0.0, 0.0, 0.0);
                instance.draw();
                GlStateManager.enableTexture2D();
            }

            this.drawSlot(j1, i, k1, l1, k, l);
            j1++;
            if (astring != null) {
                break;
            }
        }
    }

    protected void method_80c3ce64938e63de8c07(int i, int j) {}

    public boolean getEnabled() {
        return this.enabled;
    }

    protected int getScrollBarX() {
        return this.width / 2 + 124;
    }

    protected void bindAmountScrolled() {
        int i = this.cR();
        if (i < 0) {
            i /= 2;
        }

        if (!this.kS && i < 0) {
            i = 0;
        }

        this.amountScrolled = MathHelper.clamp(this.amountScrolled, 0.0F, i);
    }

    public void scrollBy(int i) {
        this.amountScrolled += i;
        this.bindAmountScrolled();
        this.initialClickY = -2.0F;
    }

    protected abstract void drawSlot(int i, int j, int k, int l, int i1, int j1);

    protected abstract int getSize();

    protected void method_98d37317de9aef781741(int i, int j) {}

    protected void overlayBackground(int i, int j, int k, int l) {
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float fx = 32.0F;
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexColorFromRgbAndAlpha(4210752, l);
        worldRenderer.addVertexWithUV(this.left, j, 0.0, 0.0, j / fx);
        worldRenderer.addVertexWithUV(this.left + this.width, j, 0.0, this.width / fx, j / fx);
        worldRenderer.setVertexColorFromRgbAndAlpha(4210752, k);
        worldRenderer.addVertexWithUV(this.left + this.width, i, 0.0, this.width / fx, i / fx);
        worldRenderer.addVertexWithUV(this.left, i, 0.0, 0.0, i / fx);
        instance.draw();
    }

    protected void drawListHeader(int i, int j, Tessellator tessellator) {}

    protected abstract void drawBackground();

    protected int getContentHeight() {
        return this.getSize() * this.slotHeight + this.headerPadding;
    }

    public void setSlotXBoundsFromLeft(int leftValue) {
        this.left = leftValue;
        this.right = leftValue + this.width;
    }

    protected void setHasListHeader(boolean hasListHeaderValue, int headerPaddingValue) {
        this.hasListHeader = hasListHeaderValue;
        this.headerPadding = headerPaddingValue;
        if (!hasListHeaderValue) {
            this.headerPadding = 0;
        }
    }

    public int cR() {
        return Math.max(0, this.getContentHeight() - (this.bottom - this.top - 4));
    }

    public void handleMouseInput() {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.isMouseYWithinSlotBounds(this.mouseY)) {
            label100:
            {
                if (Mouse.isButtonDown(0) && this.getEnabled()) {
                    if (this.initialClickY == -1.0F) {
                        label94:
                        {
                            byte b0 = 1;
                            if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
                                label90:
                                {
                                    int i = this.width / 2 - this.cM() / 2;
                                    int j = this.width / 2 + this.cM() / 2;
                                    int k =
                                            this.mouseY
                                                    - this.top
                                                    - this.headerPadding
                                                    + (int) this.amountScrolled
                                                    - 4;
                                    int l = k / this.slotHeight;
                                    if (this.mouseX >= i
                                            && this.mouseX <= j
                                            && l >= 0
                                            && k >= 0
                                            && l < this.getSize()) {
                                        byte b2;
                                        label83:
                                        {
                                            if (l == this.selectedElement) {
                                                long j2 =
                                                        MinecraftClient.getSystemTime()
                                                                - this.lastClicked;

                                                if (j2 < 250L) {
                                                    b2 = 1;
                                                    break label83;
                                                }
                                            }

                                            b2 = 0;
                                        }

                                        byte b1 = b2;
                                        this.elementClicked(
                                                l,
                                                remix.compat.compiler.JvmBooleanSlots.fromInt(b1),
                                                this.mouseX,
                                                this.mouseY);
                                        this.selectedElement = l;
                                        this.lastClicked = MinecraftClient.getSystemTime();
                                        if (astring == null) {
                                            break label90;
                                        }
                                    }

                                    if (this.mouseX >= i && this.mouseX <= j && k < 0) {
                                        this.method_80c3ce64938e63de8c07(
                                                this.mouseX - i,
                                                this.mouseY
                                                        - this.top
                                                        + (int) this.amountScrolled
                                                        - 4);
                                        b0 = 0;
                                    }
                                }

                                {
                                    int scrollBarX = this.getScrollBarX();
                                    int i1 = scrollBarX + 6;
                                    if (this.mouseX >= scrollBarX && this.mouseX <= i1) {
                                        this.scrollMultiplier = -1.0F;
                                        int j1 = this.cR();
                                        if (j1 < 1) {
                                            j1 = 1;
                                        }

                                        int k1 =
                                                (int)
                                                        ((float)
                                                                        ((this.bottom - this.top)
                                                                                * (this.bottom
                                                                                        - this.top))
                                                                / this.getContentHeight());
                                        k1 = MathHelper.clamp(k1, 32, this.bottom - this.top - 8);
                                        this.scrollMultiplier =
                                                this.scrollMultiplier
                                                        / ((float) (this.bottom - this.top - k1)
                                                                / j1);
                                        if (!(astring == null)) {

                                            this.scrollMultiplier = 1.0F;
                                        }
                                    } else {

                                        this.scrollMultiplier = 1.0F;
                                    }
                                }

                                {
                                    if (b0 != 0) {
                                        this.initialClickY = this.mouseY;
                                        if (!(astring == null)) {

                                            this.initialClickY = -2.0F;
                                        }
                                    } else {

                                        this.initialClickY = -2.0F;
                                    }
                                }

                                if (astring == null) {
                                    break label94;
                                }
                            }

                            this.initialClickY = -2.0F;
                        }

                        if (astring == null) {
                            break label100;
                        }
                    }

                    if (!(this.initialClickY >= 0.0F)) {
                        break label100;
                    }

                    this.amountScrolled =
                            this.amountScrolled
                                    - (this.mouseY - this.initialClickY) * this.scrollMultiplier;
                    this.initialClickY = this.mouseY;
                    if (astring == null) {
                        break label100;
                    }
                }

                this.initialClickY = -1.0F;
            }

            int l1 = Mouse.getEventDWheel();
            if (l1 != 0) {
                {
                    if (l1 > 0) {
                        l1 = -1;
                        if (!(astring == null)) {

                            if (l1 < 0) {
                                l1 = 1;
                            }
                        }
                    } else {

                        if (l1 < 0) {
                            l1 = 1;
                        }
                    }
                }

                this.amountScrolled = this.amountScrolled + l1 * this.slotHeight / 2;
            }
        }
    }

    protected abstract void elementClicked(int i, boolean flag, int j, int k);

    public GuiSlot(
            MinecraftClient minecraftClient,
            int rightValue,
            int j,
            int k,
            int l,
            int slotHeightValue) {
        this.selectedElement = -1;
        this.lR = true;
        this.showSelectionBox = true;
        this.enabled = true;
        this.mc = minecraftClient;
        this.width = rightValue;
        this.height = j;
        this.top = k;
        this.bottom = l;
        this.slotHeight = slotHeightValue;
        this.left = 0;
        this.right = rightValue;
    }

    public boolean isMouseYWithinSlotBounds(int i) {
        return i >= this.top
                && i <= this.bottom
                && this.mouseX >= this.left
                && this.mouseX <= this.right;
    }

    protected void method_a8908bd0f12e21ee1ad3(int i, int j, int k) {}

    public void setEnabled(boolean enabledValue) {
        this.enabled = enabledValue;
    }

    public int cM() {
        return 220;
    }

    protected abstract boolean isSelected(int i);

    public void setDimensions(int rightValue, int j, int k, int l) {
        this.width = rightValue;
        this.height = j;
        this.top = k;
        this.bottom = l;
        this.left = 0;
        this.right = rightValue;
    }

    public void drawScreen(int mouseXValue, int j, float f1) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.lR) {
            this.mouseX = mouseXValue;
            this.mouseY = j;
            this.drawBackground();
            int k = this.getScrollBarX();
            int l = k + 6;
            this.bindAmountScrolled();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            if (!(this instanceof AltAccountSlotList)) {
                this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                float fx = 32.0F;
                worldRenderer.startDrawingQuads();
                worldRenderer.setOpaqueVertexColorFromRgb(2105376);
                worldRenderer.addVertexWithUV(
                        this.left,
                        this.bottom,
                        0.0,
                        this.left / fx,
                        (this.bottom + (int) this.amountScrolled) / fx);
                worldRenderer.addVertexWithUV(
                        this.right,
                        this.bottom,
                        0.0,
                        this.right / fx,
                        (this.bottom + (int) this.amountScrolled) / fx);
                worldRenderer.addVertexWithUV(
                        this.right,
                        this.top,
                        0.0,
                        this.right / fx,
                        (this.top + (int) this.amountScrolled) / fx);
                worldRenderer.addVertexWithUV(
                        this.left,
                        this.top,
                        0.0,
                        this.left / fx,
                        (this.top + (int) this.amountScrolled) / fx);
                instance.draw();
            }

            int i2 = this.left + this.width / 2 - this.cM() / 2 + 2;
            int i1 = this.top + 4 - (int) this.amountScrolled;
            if (this.hasListHeader) {
                this.drawListHeader(i2, i1, instance);
            }

            byte b0;
            {
                this.drawSelectionBox(i2, i1, mouseXValue, j);
                GlStateManager.disableDepth();
                b0 = 4;
                if (!(this instanceof AltAccountSlotList)) {
                    this.overlayBackground(0, this.top, 255, 255);
                    this.overlayBackground(this.bottom, this.height, 255, 255);
                    if (!(astring == null)) {

                        Gui.drawRect(0.0, this.bottom, this.right, this.bottom + 54, -14145496);
                        Gui.drawRect(0.0, 0.0, this.right, 27.0, -14145496);
                    }
                } else {

                    Gui.drawRect(0.0, this.bottom, this.right, this.bottom + 54, -14145496);
                    Gui.drawRect(0.0, 0.0, this.right, 27.0, -14145496);
                }
            }

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();
            worldRenderer.startDrawingQuads();
            worldRenderer.setVertexColorFromRgbAndAlpha(0, 0);
            worldRenderer.addVertexWithUV(this.left, this.top + b0, 0.0, 0.0, 1.0);
            worldRenderer.addVertexWithUV(this.right, this.top + b0, 0.0, 1.0, 1.0);
            worldRenderer.setVertexColorFromRgbAndAlpha(0, 255);
            worldRenderer.addVertexWithUV(this.right, this.top, 0.0, 1.0, 0.0);
            worldRenderer.addVertexWithUV(this.left, this.top, 0.0, 0.0, 0.0);
            instance.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.setVertexColorFromRgbAndAlpha(0, 255);
            worldRenderer.addVertexWithUV(this.left, this.bottom, 0.0, 0.0, 1.0);
            worldRenderer.addVertexWithUV(this.right, this.bottom, 0.0, 1.0, 1.0);
            worldRenderer.setVertexColorFromRgbAndAlpha(0, 0);
            worldRenderer.addVertexWithUV(this.right, this.bottom - b0, 0.0, 1.0, 0.0);
            worldRenderer.addVertexWithUV(this.left, this.bottom - b0, 0.0, 0.0, 0.0);
            instance.draw();
            int j1 = this.cR();
            label27:
            if (j1 > 0) {
                int k1 =
                        (this.bottom - this.top)
                                * (this.bottom - this.top)
                                / this.getContentHeight();
                k1 = MathHelper.clamp(k1, 32, this.bottom - this.top - 8);
                int l1 = (int) this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;
                if (l1 < this.top) {
                    l1 = this.top;
                }

                worldRenderer.startDrawingQuads();
                worldRenderer.setVertexColorFromRgbAndAlpha(0, 255);
                worldRenderer.addVertexWithUV(k, this.bottom, 0.0, 0.0, 1.0);
                worldRenderer.addVertexWithUV(l, this.bottom, 0.0, 1.0, 1.0);
                worldRenderer.addVertexWithUV(l, this.top, 0.0, 1.0, 0.0);
                worldRenderer.addVertexWithUV(k, this.top, 0.0, 0.0, 0.0);
                instance.draw();
                if (this instanceof AltAccountSlotList) {
                    Gui.drawRect(
                            k + 2,
                            l1 + 1,
                            l - 2,
                            l1 + k1 - 1,
                            new Color(-14145496).brighter().getRGB());
                    if (astring == null) {
                        break label27;
                    }
                }

                worldRenderer.startDrawingQuads();
                worldRenderer.setVertexColorFromRgbAndAlpha(8421504, 255);
                worldRenderer.addVertexWithUV(k, l1 + k1, 0.0, 0.0, 1.0);
                worldRenderer.addVertexWithUV(l, l1 + k1, 0.0, 1.0, 1.0);
                worldRenderer.addVertexWithUV(l, l1, 0.0, 1.0, 0.0);
                worldRenderer.addVertexWithUV(k, l1, 0.0, 0.0, 0.0);
                instance.draw();
                worldRenderer.startDrawingQuads();
                worldRenderer.setVertexColorFromRgbAndAlpha(12632256, 255);
                worldRenderer.addVertexWithUV(k, l1 + k1 - 1, 0.0, 0.0, 1.0);
                worldRenderer.addVertexWithUV(l - 1, l1 + k1 - 1, 0.0, 1.0, 1.0);
                worldRenderer.addVertexWithUV(l - 1, l1, 0.0, 1.0, 0.0);
                worldRenderer.addVertexWithUV(k, l1, 0.0, 0.0, 0.0);
                instance.draw();
            }

            this.method_98d37317de9aef781741(mouseXValue, j);
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }

    public void setShowSelectionBox(boolean showSelectionBoxValue) {
        this.showSelectionBox = showSelectionBoxValue;
    }

    public int getAmountScrolled() {
        return (int) this.amountScrolled;
    }

    public void actionPerformed(GuiButton guiButton) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.enabled) {
            if (guiButton.id == this.scrollUpButtonID) {
                this.amountScrolled = this.amountScrolled - this.slotHeight * 2 / 3;
                this.initialClickY = -2.0F;
                this.bindAmountScrolled();
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == this.scrollDownButtonID) {
                this.amountScrolled = this.amountScrolled + this.slotHeight * 2 / 3;
                this.initialClickY = -2.0F;
                this.bindAmountScrolled();
            }
        }
    }

    public int getSlotHeight() {
        return this.slotHeight;
    }

    public void registerScrollButtons(int i, int j) {
        this.scrollUpButtonID = i;
        this.scrollDownButtonID = j;
    }

    public int getSlotIndexFromScreenCoords(int i, int j) {
        int k = this.left + this.width / 2 - this.cM() / 2;
        int l = this.left + this.width / 2 + this.cM() / 2;
        int i1 = j - this.top - this.headerPadding + (int) this.amountScrolled - 4;
        int j1 = i1 / this.slotHeight;
        return i < this.getScrollBarX()
                        && i >= k
                        && i <= l
                        && j1 >= 0
                        && i1 >= 0
                        && j1 < this.getSize()
                ? j1
                : -1;
    }
}
