package net.minecraft.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class GuiFlatPresetsListSlot extends GuiSlot {
    GuiFlatPresets arr;
    public int x;

    private void method_c7b0b427bd040f446b4c(int i, int j, Item item, int k) {
        this.method_6b802a8dba45a02bfb5a(i + 1, j + 1);
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        this.arr.itemRender.renderItemModelIntoGUI(new ItemStack(item, 1, k), i + 2, j + 2);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }

    protected void elementClicked(int i, boolean flag, int j, int k) {
        this.x = i;
        this.arr.method_8d9b536bcdc9991c48fb();
        GuiFlatPresets.access$600(this.arr)
                .setText(
                        ((GuiFlatPresetsLayerItem)
                                        GuiFlatPresets.access$400()
                                                .get(GuiFlatPresets.access$500(this.arr).x))
                                .d);
    }

    private void method_6b802a8dba45a02bfb5a(int i, int j) {
        this.method_ef7b4c24eda6910a0350(i, j, 0, 0);
    }

    protected void drawSlot(int i, int j, int k, int l, int i1, int j1) {
        GuiFlatPresetsLayerItem guiFlatPresetsLayerItem =
                (GuiFlatPresetsLayerItem) GuiFlatPresets.access$400().get(i);
        this.method_c7b0b427bd040f446b4c(
                j, k, guiFlatPresetsLayerItem.aso, guiFlatPresetsLayerItem.cz);
        this.arr.fontRendererObj.drawString(guiFlatPresetsLayerItem.c, j + 18 + 5, k + 6, 16777215);
    }

    private void method_ef7b4c24eda6910a0350(int i, int j, int k, int l) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Gui.statIcons);

        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(
                i + 0, j + 18, this.arr.zLevel, (k + 0) * 0.0078125F, (l + 18) * 0.0078125F);
        worldRenderer.addVertexWithUV(
                i + 18, j + 18, this.arr.zLevel, (k + 18) * 0.0078125F, (l + 18) * 0.0078125F);
        worldRenderer.addVertexWithUV(
                i + 18, j + 0, this.arr.zLevel, (k + 18) * 0.0078125F, (l + 0) * 0.0078125F);
        worldRenderer.addVertexWithUV(
                i + 0, j + 0, this.arr.zLevel, (k + 0) * 0.0078125F, (l + 0) * 0.0078125F);
        instance.draw();
    }

    protected void drawBackground() {}

    protected int getSize() {
        return GuiFlatPresets.access$400().size();
    }

    public GuiFlatPresetsListSlot(GuiFlatPresets arrValue) {
        super(arrValue.mc, arrValue.width, arrValue.height, 80, arrValue.height - 37, 24);
        this.arr = arrValue;
        this.x = -1;
    }

    protected boolean isSelected(int i) {
        return i == this.x;
    }
}
