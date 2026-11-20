package net.minecraft.client.gui.inventory;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import remix.client.ClientServices;
import remix.module.combat.AuraModule;
import remix.module.combat.TPAuraModule;
import remix.module.player.ChestAuraModule;

import java.io.IOException;

public class GuiInventory extends InventoryEffectRenderer {

    private float oldMouseX;
    private float oldMouseY;

    public void updateScreen() {
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.player));
        }

        this.updateActivePotionEffects();
    }


    protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRendererObj.drawString(
                I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
    }

    public static void drawEntityOnScreen(
            int i, int j, int k, float f, float f1, LivingEntity livingEntity) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(i, j, 50.0F);
        GlStateManager.scale(-k, k, k);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float aIW = livingEntity.renderYawOffset;
        float rotationYaw = livingEntity.rotationYaw;
        float rotationPitch = livingEntity.rotationPitch;
        float f5 = livingEntity.prevRotationYawHead;
        float beO = livingEntity.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan(f1 / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
        livingEntity.renderYawOffset = (float) Math.atan(f / 40.0F) * 20.0F;
        livingEntity.rotationYaw = (float) Math.atan(f / 40.0F) * 40.0F;
        livingEntity.rotationPitch = -((float) Math.atan(f1 / 40.0F)) * 20.0F;
        int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
        livingEntity.rotationYawHead = livingEntity.rotationYaw;
        livingEntity.prevRotationYawHead = livingEntity.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager renderManager = MinecraftClient.getMinecraft().getRenderManager();
        renderManager.setPlayerViewY(180.0F);
        renderManager.setRenderShadow(false);
        renderManager.renderEntityWithPosYaw(livingEntity, 0.0, 0.0, 0.0, 0.0F, 1.0F);
        renderManager.setRenderShadow(true);
        livingEntity.renderYawOffset = aIW;
        livingEntity.rotationYaw = rotationYaw;
        livingEntity.rotationPitch = rotationPitch;
        livingEntity.prevRotationYawHead = f5;
        livingEntity.rotationYawHead = beO;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        int[] aint = aint1;
        if (aint != null) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (guiButton.id == 0) {
                this.mc.displayGuiScreen(
                        new GuiAchievements(this, this.mc.player.getStatFileWriter()));
            }
        } catch (IOException ioexception3) {
            throw remix.compat.compiler.JvmThrowTypes.flow(ioexception3);
        }

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (guiButton.id == 1) {
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.player.getStatFileWriter()));
            }
        } catch (IOException ioexception4) {
            throw remix.compat.compiler.JvmThrowTypes.flow(ioexception4);
        }

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

    public void drawScreen(int arIValue, int j, float f) {
        super.drawScreen(arIValue, j, f);
        this.oldMouseX = arIValue;
        this.oldMouseY = j;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int k, int l) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc
                .getTextureManager()
                .bindTexture(net.minecraft.client.gui.inventory.GuiContainer.inventoryBackground);
        int guiLeftValue = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(guiLeftValue, j, 0, 0, this.xSize, this.ySize);
        drawEntityOnScreen(
                guiLeftValue + 51,
                j + 75,
                30,
                guiLeftValue + 51 - this.oldMouseX,
                j + 75 - 50 - this.oldMouseY,
                this.mc.player);
    }

    public void initGui() {
        this.buttonList.clear();
        int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
        this.buttonList.add(new GuiButton(69, this.width - 5 - 100, 5, 100, 20, "Disable Aura"));
        int[] aint = aint1;
        this.buttonList.add(
                new GuiButton(70, this.width - 5 - 100, 25, 100, 20, "Disable TP Aura"));
        this.buttonList.add(
                new GuiButton(71, this.width - 5 - 100, 45, 100, 20, "Disable Chest Aura"));
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.player));
            if (aint == null) {
                return;
            }
        }

        super.initGui();
    }

    public GuiInventory(PlayerEntity playerEntity) {
        super(playerEntity.inventoryContainer);
        this.allowUserInput = true;
    }
}
