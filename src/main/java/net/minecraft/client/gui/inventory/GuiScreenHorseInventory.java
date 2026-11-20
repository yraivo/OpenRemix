package net.minecraft.client.gui.inventory;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiScreenHorseInventory extends GuiContainer {
    private float mousePosx;
    private IInventory playerInventory;
    private static ResourceLocation horseGuiTextures;
    private float mousePosY;
    private EntityHorse horseEntity;
    private IInventory horseInventory;

    public void drawScreen(int mousePosxValue, int j, float f) {
        this.mousePosx = mousePosxValue;
        this.mousePosY = j;
        super.drawScreen(mousePosxValue, j, f);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int k, int l) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.inventory.GuiScreenHorseInventory.horseGuiTextures);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        if (this.horseEntity.isChested()) {
            this.drawTexturedModalRect(i + 79, j + 17, 0, this.ySize, 90, 54);
        }

        if (this.horseEntity.canWearArmor()) {
            this.drawTexturedModalRect(i + 7, j + 35, 0, this.ySize + 54, 18, 18);
        }

        GuiInventory.drawEntityOnScreen(
                i + 51,
                j + 60,
                17,
                i + 51 - this.mousePosx,
                j + 75 - 50 - this.mousePosY,
                this.horseEntity);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.gui.inventory.GuiScreenHorseInventory.horseGuiTextures =
                new ResourceLocation("textures/gui/container/horse.png");
    }

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRendererObj.drawString(
                this.horseInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(
                this.playerInventory.getDisplayName().getUnformattedText(),
                8,
                this.ySize - 96 + 2,
                4210752);
    }

    // region String decoding support

    // endregion

    public GuiScreenHorseInventory(IInventory atLValue, IInventory atMValue, EntityHorse augValue) {
        super(
                new ContainerHorseInventory(
                        atLValue, atMValue, augValue, MinecraftClient.getMinecraft().player));
        this.playerInventory = atLValue;
        this.horseInventory = atMValue;
        this.horseEntity = augValue;
        this.allowUserInput = false;
    }
}
