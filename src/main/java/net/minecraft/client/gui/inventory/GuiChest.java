package net.minecraft.client.gui.inventory;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.combat.AuraModule;
import remix.module.combat.TPAuraModule;
import remix.module.player.ChestAuraModule;

import java.io.IOException;

public class GuiChest extends GuiContainer {

    private IInventory lowerChestInventory;
    private IInventory upperChestInventory;
    private static ResourceLocation CHEST_GUI_TEXTURE;

    private int inventoryRows;

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRendererObj.drawString(
                this.lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(
                this.upperChestInventory.getDisplayName().getUnformattedText(),
                8,
                this.ySize - 96 + 2,
                4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int k, int l) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc
                .getTextureManager()
                .bindTexture(net.minecraft.client.gui.inventory.GuiChest.CHEST_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }

    public void onGuiClosed() {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        ChestAuraModule chestAura = ClientServices.moduleManager.chestAura;
        if (chestAura.isEnabled()) {
            chestAura.lastOpenedChest = null;
            chestAura.openTimer.reset();
        }

        super.onGuiClosed();
    }

    static {
        net.minecraft.client.gui.inventory.GuiChest.CHEST_GUI_TEXTURE =
                new ResourceLocation("textures/gui/container/generic_54.png");
    }

    public void drawScreen(int i, int j, float f) {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        ChestAuraModule chestAura = ClientServices.moduleManager.chestAura;
        if (!chestAura.isEnabled()
                || !chestAura.hideChestSetting.asBoolean()
                || chestAura.lastOpenedChest == null) {
            super.drawScreen(i, j, f);
        }
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.id == 69) {
            AuraModule aura = ClientServices.moduleManager.aura;

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (aura.isEnabled()) {
                    aura.toggle();
                }
            } catch (IOException ioexception2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(ioexception2);
            }
        }

        if (guiButton.id == 70) {
            TPAuraModule tpAura = ClientServices.moduleManager.tpAura;

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (tpAura.isEnabled()) {
                    tpAura.toggle();
                }
            } catch (IOException ioexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(ioexception1);
            }
        }

        if (guiButton.id == 71) {
            ChestAuraModule chestAura = ClientServices.moduleManager.chestAura;

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (chestAura.isEnabled()) {
                    chestAura.toggle();
                }
            } catch (IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(ioexception);
            }
        }
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(69, this.width - 5 - 100, 5, 100, 20, "Disable Aura"));
        this.buttonList.add(
                new GuiButton(70, this.width - 5 - 100, 25, 100, 20, "Disable TP Aura"));
        this.buttonList.add(
                new GuiButton(71, this.width - 5 - 100, 45, 100, 20, "Disable Chest Aura"));
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        ChestAuraModule chestAura = ClientServices.moduleManager.chestAura;
        if (chestAura.isEnabled() && chestAura.lastOpenedChest != null) {
            chestAura.openedChests.add(chestAura.lastOpenedChest);
            this.mc.inGameHasFocus = true;
            this.mc.mouseHelper.grabMouseCursor();
            this.mc.leftClickCounter = 10000;
        }
    }

    public GuiChest(IInventory atLValue, IInventory atMValue) {
        super(new ContainerChest(atLValue, atMValue, MinecraftClient.getMinecraft().player));
        this.upperChestInventory = atLValue;
        this.lowerChestInventory = atMValue;
        this.allowUserInput = false;
        short short1 = 222;
        int i = short1 - 108;
        this.inventoryRows = atMValue.getSizeInventory() / 9;
        this.ySize = i + this.inventoryRows * 18;
    }
}
