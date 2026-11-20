package net.minecraft.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;

class GuiScreenCustomizePresetsListPreset extends GuiSlot {
    public int x;
    GuiScreenCustomizePresets avj;

    protected int getSize() {
        return GuiScreenCustomizePresets.access$000().size();
    }

    public GuiScreenCustomizePresetsListPreset(GuiScreenCustomizePresets avjValue) {
        super(avjValue.mc, avjValue.width, avjValue.height, 80, avjValue.height - 32, 38);
        this.avj = avjValue;
        this.x = -1;
    }

    protected void drawSlot(int i, int j, int k, int l, int i1, int j1) {
        GuiScreenCustomizePresetsInfo guiScreenCustomizePresetsInfo =
                (GuiScreenCustomizePresetsInfo) GuiScreenCustomizePresets.access$000().get(i);
        this.method_6f3d2309a95aaa52bcdb(j, k, guiScreenCustomizePresetsInfo.amq);
        this.avj.fontRendererObj.drawString(
                guiScreenCustomizePresetsInfo.a, j + 32 + 10, k + 14, 16777215);
    }

    protected void drawBackground() {}

    protected void elementClicked(int i, boolean flag, int j, int k) {
        this.x = i;
        this.avj.method_8d9b536bcdc9991c48fb();
        GuiScreenCustomizePresets.access$200(this.avj)
                .setText(
                        ((GuiScreenCustomizePresetsInfo)
                                        GuiScreenCustomizePresets.access$000()
                                                .get(
                                                        GuiScreenCustomizePresets.access$100(
                                                                        this.avj)
                                                                .x))
                                .arn.toString());
    }

    protected boolean isSelected(int i) {
        return i == this.x;
    }

    private void method_6f3d2309a95aaa52bcdb(int i, int j, ResourceLocation resourceLocation) {
        int k = i + 5;
        this.avj.method_f418b0f77f1897435d21(k - 1, k + 32, j - 1, -2039584);
        this.avj.method_f418b0f77f1897435d21(k - 1, k + 32, j + 32, -6250336);
        this.avj.method_366505046587ede28c94(k - 1, j - 1, j + 32, -2039584);
        this.avj.method_366505046587ede28c94(k + 32, j - 1, j + 32, -6250336);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(resourceLocation);

        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(k + 0, j + 32, 0.0, 0.0, 1.0);
        worldRenderer.addVertexWithUV(k + 32, j + 32, 0.0, 1.0, 1.0);
        worldRenderer.addVertexWithUV(k + 32, j + 0, 0.0, 1.0, 0.0);
        worldRenderer.addVertexWithUV(k + 0, j + 0, 0.0, 0.0, 0.0);
        instance.draw();
    }
}
