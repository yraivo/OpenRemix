package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import remix.client.ClientServices;
import remix.module.combat.AuraModule;
import remix.module.combat.TPAuraModule;
import remix.module.player.ChestAuraModule;

import java.io.IOException;

public class GuiCrafting extends GuiContainer {

    private static ResourceLocation craftingTableGuiTextures;

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(69, this.width - 5 - 100, 5, 100, 20, "Disable Aura"));
        this.buttonList.add(
                new GuiButton(70, this.width - 5 - 100, 25, 100, 20, "Disable TP Aura"));
        this.buttonList.add(
                new GuiButton(71, this.width - 5 - 100, 45, 100, 20, "Disable Chest Aura"));
    }

    protected void drawGuiContainerBackgroundLayer(float f, int k, int l) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc
                .getTextureManager()
                .bindTexture(
                        net.minecraft.client.gui.inventory.GuiCrafting.craftingTableGuiTextures);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
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

    static {
        net.minecraft.client.gui.inventory.GuiCrafting.craftingTableGuiTextures =
                new ResourceLocation("textures/gui/container/crafting_table.png");
    }

    public GuiCrafting(InventoryPlayer inventoryPlayer, World world) {
        this(inventoryPlayer, world, BlockPos.ORIGIN);
    }

    public GuiCrafting(InventoryPlayer inventoryPlayer, World world, BlockPos blockPos) {
        super(new ContainerWorkbench(inventoryPlayer, world, blockPos));
    }

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRendererObj.drawString(
                I18n.format("container.crafting", new Object[0]), 28, 6, 4210752);
        this.fontRendererObj.drawString(
                I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }
}
