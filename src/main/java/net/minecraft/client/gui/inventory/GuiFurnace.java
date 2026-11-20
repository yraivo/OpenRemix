package net.minecraft.client.gui.inventory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

public class GuiFurnace extends GuiContainer {
    private IInventory tileFurnace;

    private static ResourceLocation furnaceGuiTextures;
    private InventoryPlayer playerInventory;

    protected void drawGuiContainerBackgroundLayer(float f, int i1, int j1) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.inventory.GuiFurnace.furnaceGuiTextures);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        if (TileEntityFurnace.isBurning(this.tileFurnace)) {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
    }

    // region String decoding support

    // endregion

    private int getCookProgressScaled(int i) {
        int j = this.tileFurnace.getField(2);
        int k = this.tileFurnace.getField(3);
        return k != 0 && j != 0 ? j * i / k : 0;
    }

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String text = this.tileFurnace.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(
                text, this.xSize / 2 - this.fontRendererObj.getStringWidth(text) / 2, 6, 4210752);
        this.fontRendererObj.drawString(
                this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.gui.inventory.GuiFurnace.furnaceGuiTextures = new ResourceLocation("textures/gui/container/furnace.png");
    }

    public GuiFurnace(InventoryPlayer auaValue, IInventory atMValue) {
        super(new ContainerFurnace(auaValue, atMValue));
        this.playerInventory = auaValue;
        this.tileFurnace = atMValue;
    }

    private int getBurnLeftScaled(int i) {
        int j = this.tileFurnace.getField(1);
        if (j == 0) {
            j = 200;
        }

        return this.tileFurnace.getField(0) * i / j;
    }
}
