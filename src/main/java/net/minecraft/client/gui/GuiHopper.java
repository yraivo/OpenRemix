package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiHopper extends GuiContainer {
    private IInventory playerInventory;
    private IInventory hopperInventory;

    private static ResourceLocation HOPPER_GUI_TEXTURE;

    static {
        net.minecraft.client.gui.GuiHopper.HOPPER_GUI_TEXTURE =
                new ResourceLocation("textures/gui/container/hopper.png");
    }

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRendererObj.drawString(
                this.hopperInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(
                this.playerInventory.getDisplayName().getUnformattedText(),
                8,
                this.ySize - 96 + 2,
                4210752);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public GuiHopper(InventoryPlayer atLValue, IInventory atMValue) {
        super(new ContainerHopper(atLValue, atMValue, MinecraftClient.getMinecraft().player));
        this.playerInventory = atLValue;
        this.hopperInventory = atMValue;
        this.allowUserInput = false;
        this.ySize = 133;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int k, int l) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.GuiHopper.HOPPER_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}
