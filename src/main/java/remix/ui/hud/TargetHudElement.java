package remix.ui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import remix.ui.hud.HudElement;
import remix.util.render.Render2D3DUtils;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.combat.AuraModule;

import org.lwjgl.opengl.GL11;

import java.awt.Color;

public final class TargetHudElement implements HudElement {
    private AuraModule auraModule;
    private static String pingUnitSuffix;
    private static int panelBorderColor;
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    private void drawPlayerHead(
            float f,
            float f1,
            int i,
            int j,
            AbstractClientPlayer abstractClientPlayer) {
        ResourceLocation resourceLocation = abstractClientPlayer.getLocationSkin();
        MinecraftClient.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GL11.glEnable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawScaledCustomSizeModalRect(f, f1, 8.0F, 8.0F, 8.0F, 8.0F, i / 2, j / 2, 64.0F, 64.0F);
        GL11.glDisable(3042);
    }

    // region String decoding support

    // endregion

    public void render(ScaledResolution scaledResolution) {
        WatermarkHudElement.isWatermarkEnabled();

        if (this.auraModule == null) {
            RemixClient.INSTANCE.getClientServices();
            this.auraModule = ClientServices.moduleManager.aura;
        }

        LivingEntity currentTarget = this.auraModule.currentTarget;
        if (this.auraModule.isEnabled() && currentTarget instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) currentTarget;
            FontRenderer font = ec.fontRendererObj;
            String name = currentTarget.getName();

            float width = scaledResolution.getWidth();
            float height = scaledResolution.getHeight();
            float centerX = width / 2.0F;
            float panelCenterY = height / 2.0F + 50.0F;

            float left = centerX - 70.0F;
            float top = panelCenterY - 25.0F;
            float right = centerX + 70.0F;
            float bottom = panelCenterY + 25.0F;
            Render2D3DUtils.drawShadow(left, top, right, bottom, 0.2F);
            Render2D3DUtils.drawBorderedRect(
                    left, top, right, bottom, 2.0, remix.ui.hud.TargetHudElement.panelBorderColor, -15592942);

            float headLeft = left + 5.0F;
            float headTop = top + 5.0F;
            float headRight = headLeft + 32.0F;
            float headBottom = headTop + 32.0F;
            float nameY = top + 5.0F;
            float nameX = headRight + 3.0F;
            font.drawStringWithShadow(name, nameX, nameY, -1);
            Gui.drawRect(headLeft, headTop, headRight, headBottom, Integer.MAX_VALUE);
            this.drawPlayerHead(
                    headLeft + 1.0F, headTop + 1.0F, 60, 60, (AbstractClientPlayer) playerEntity);
            float slotX = headRight + 3.0F;
            float armorLeft = slotX;
            float equipmentY = nameY + 11.0F;
            float equipmentBottom = equipmentY + 15.0F;
            ItemStack[] equipment = playerEntity.inventory.armorInventory;
            RenderItem renderItem = ec.getRenderItem();

            GL11.glPushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            int armorIndex = 3;

            while (armorIndex >= 0) {
                ItemStack itemStack = equipment[armorIndex];
                Gui.drawRect(slotX, equipmentY, slotX + 16.0F, equipmentY + 16.0F, -13027015);
                Render2D3DUtils.drawBorder(
                        slotX - 1.0F,
                        equipmentY - 1.0F,
                        slotX + 16.0F + 1.0F,
                        equipmentY + 16.0F + 1.0F,
                        1.0,
                        Integer.MIN_VALUE);
                if (itemStack != null) {
                    renderItem.renderItemModelIntoGUI(itemStack, (int) slotX, (int) equipmentY);
                    renderItem.renderItemOverlayIntoGUI(
                            font, itemStack, (int) slotX, (int) equipmentY, packedTextFragments[0]);
                }

                slotX += 19.0F;
                armorIndex--;
            }

            RenderHelper.disableStandardItemLighting();
            GL11.glPopMatrix();

            float displayHealth = this.getDisplayHealth(currentTarget);

            float healthWidth = 135.0F * (displayHealth / currentTarget.getMaxHealth());
            int healthColor = this.getHealthBarColor(currentTarget);
            Gui.drawRect(
                    left + 5.0F, top + Math.round(40.0F), right - 5.0F, bottom - 5.0F, -9892341);
            Gui.drawRect(
                    left + 5.0F, top + Math.round(40.0F), left + healthWidth, bottom - 5.0F, healthColor);
            if (healthWidth != 135.0F) {
                Gui.drawRect(
                        left + healthWidth - (displayHealth != 0.0F ? 1 : 0),
                        top + Math.round(40.0F),
                        left + healthWidth,
                        bottom - 5.0F,
                        new Color(healthColor).darker().getRGB());
            }

            float armorRight = right - 5.0F;
            double totalArmorValue = currentTarget.getTotalArmorValue();
            float armorWidth = armorRight - armorLeft;
            double filledArmorWidth = armorWidth * (totalArmorValue / 20.0);
            Gui.drawRect(armorLeft, equipmentBottom + 4.0F, armorLeft + armorWidth, equipmentBottom + 6.0F, -16777092);
            Gui.drawRect(armorLeft, equipmentBottom + 4.0F, armorLeft + filledArmorWidth, equipmentBottom + 6.0F, -16776961);
            PlayerInfo playerInfo = ec.getNetworkHandler().getPlayerInfo(name);
            if (playerInfo != null) {
                String text = playerInfo.getResponseTime() + remix.ui.hud.TargetHudElement.pingUnitSuffix;
                ec.ingameGUI
                        .getTabList()
                        .drawPing((int) armorRight, 0, (int) equipmentY + 2, playerInfo);
                byte fontScale = 2;
                GlStateManager.scale(0.5, 0.5, 0.5);
                font.drawStringWithShadow(
                        text, (armorRight - font.getStringWidth(text) / 2) * fontScale, (equipmentY + 12.0F) * fontScale, -1);
                GlStateManager.scale(2.0F, 2.0F, 2.0F);
            }
        }
    }

    private float getDisplayHealth(LivingEntity livingEntity) {
        int i = (int) Math.ceil(livingEntity.getHealth());
        int j = (int) (i + 0.5F);
        return j;
    }

    // region String decoding support

    // endregion

    static {
        remix.ui.hud.TargetHudElement.packedTextFragments = new String[2];
        remix.ui.hud.TargetHudElement.packedTextFragments[0] = new String("");
        remix.ui.hud.TargetHudElement.packedTextFragments[1] = new String("/#");
        remix.ui.hud.TargetHudElement.pingUnitSuffix = "ms";
        remix.ui.hud.TargetHudElement.panelBorderColor = new Color(28, 28, 28).getRGB();
    }

    private int getHealthBarColor(LivingEntity livingEntity) {
        float health = livingEntity.getHealth();
        float maxHealth = livingEntity.getMaxHealth();
        float f2 = Math.max(0.0F, Math.min(health, maxHealth) / maxHealth);
        return Render2D3DUtils.hsvToRgb(f2 / 3.0F, 1.0F, 1.0F)
                | 0xFF000000;
    }
}
