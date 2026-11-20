package net.minecraft.client.gui.inventory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiDispenser extends GuiContainer {
    private static ResourceLocation dispenserGuiTextures;
    public IInventory dispenserInventory;
    private InventoryPlayer playerInventory;

    // region String decoding support

    // endregion

    protected void drawGuiContainerBackgroundLayer(float f, int k, int l) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.inventory.GuiDispenser.dispenserGuiTextures);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    // region String decoding support

    // endregion

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String text = this.dispenserInventory.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(
                text, this.xSize / 2 - this.fontRendererObj.getStringWidth(text) / 2, 6, 4210752);
        this.fontRendererObj.drawString(
                this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    public GuiDispenser(InventoryPlayer auaValue, IInventory atMValue) {
        super(new ContainerDispenser(auaValue, atMValue));
        this.playerInventory = auaValue;
        this.dispenserInventory = atMValue;
    }

    static {
        net.minecraft.client.gui.inventory.GuiDispenser.dispenserGuiTextures = new ResourceLocation("textures/gui/container/dispenser.png");
    }
}
