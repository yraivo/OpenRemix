package net.minecraft.client.gui.inventory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiBrewingStand extends GuiContainer {
    private InventoryPlayer playerInventory;
    private static ResourceLocation brewingStandGuiTextures;
    private IInventory tileBrewingStand;

    protected void drawGuiContainerBackgroundLayer(float f, int j1, int k1) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.inventory.GuiBrewingStand.brewingStandGuiTextures);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        int[] aint = aint1;
        int k = this.tileBrewingStand.getField(0);
        if (k > 0) {
            int l = (int) (28.0F * (1.0F - k / 400.0F));
            if (l > 0) {
                this.drawTexturedModalRect(i + 97, j + 16, 176, 0, 9, l);
            }

            int i1 = k / 2 % 7;
            switch (i1) {
                case 0:
                    l = 29;
                    if (aint == null) {
                        break;
                    }
                case 1:
                    l = 24;
                    if (aint == null) {
                        break;
                    }
                case 2:
                    l = 20;
                    if (aint == null) {
                        break;
                    }
                case 3:
                    l = 16;
                    if (aint == null) {
                        break;
                    }
                case 4:
                    l = 11;
                    if (aint == null) {
                        break;
                    }
                case 5:
                    l = 6;
                    if (aint == null) {
                        break;
                    }
                case 6:
                    l = 0;
            }

            if (l > 0) {
                this.drawTexturedModalRect(i + 65, j + 14 + 29 - l, 185, 29 - l, 12, l);
            }
        }
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.gui.inventory.GuiBrewingStand.brewingStandGuiTextures = new ResourceLocation("textures/gui/container/brewing_stand.png");
    }

    // region String decoding support

    // endregion

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String text = this.tileBrewingStand.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(
                text, this.xSize / 2 - this.fontRendererObj.getStringWidth(text) / 2, 6, 4210752);
        this.fontRendererObj.drawString(
                this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    public GuiBrewingStand(InventoryPlayer auaValue, IInventory atMValue) {
        super(new ContainerBrewingStand(auaValue, atMValue));
        this.playerInventory = auaValue;
        this.tileBrewingStand = atMValue;
    }
}
