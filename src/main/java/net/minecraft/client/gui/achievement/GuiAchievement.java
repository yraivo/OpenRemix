package net.minecraft.client.gui.achievement;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;

public class GuiAchievement extends Gui {
    private long notificationTime;
    private static ResourceLocation achievementBg;
    private int width;
    private String achievementTitle;
    private int height;

    private String achievementDescription;

    private Achievement theAchievement;

    private boolean permanentNotification;
    private RenderItem renderItem;
    private MinecraftClient mc;

    public void updateAchievementWindow() {
        ResourceLocation.cB();

        if (this.theAchievement != null) {

            if (this.notificationTime != 0L && MinecraftClient.getMinecraft().player != null) {
                double d0 = (MinecraftClient.getSystemTime() - this.notificationTime) / 3000.0;
                if (!this.permanentNotification) {
                    if (d0 < 0.0 || d0 > 1.0) {

                        this.notificationTime = 0L;
                        return;
                    }
                } else if (d0 > 0.5) {
                    d0 = 0.5;
                }

                this.updateAchievementWindowScale();
                GlStateManager.disableDepth();
                GlStateManager.depthMask(false);
                double d1 = d0 * 2.0;
                if (d1 > 1.0) {
                    d1 = 2.0 - d1;
                }

                d1 *= 4.0;
                d1 = 1.0 - d1;
                if (d1 < 0.0) {
                    d1 = 0.0;
                }

                int i;
                int j;
                {
                    d1 *= d1;
                    d1 *= d1;
                    i = this.width - 160;
                    j = 0 - (int) (d1 * 36.0);
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.enableTexture2D();
                    this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.achievement.GuiAchievement.achievementBg);
                    GlStateManager.disableLighting();
                    this.drawTexturedModalRect(i, j, 96, 202, 160, 32);
                    if (this.permanentNotification) {
                        this.mc.fontRendererObj.drawSplitString(this.achievementDescription, i + 30, j + 7, 120, -1);

                        
                    } else {

                        this.mc.fontRendererObj.drawString(this.achievementTitle, i + 30, j + 7, -256);
                        this.mc.fontRendererObj.drawString(this.achievementDescription, i + 30, j + 18, -1);
                    }
                }

                RenderHelper.enableGUIStandardItemLighting();
                GlStateManager.disableLighting();
                GlStateManager.enableRescaleNormal();
                GlStateManager.enableColorMaterial();
                GlStateManager.enableLighting();
                this.renderItem.renderItemAndEffectIntoGUI(this.theAchievement.theItemStack, i + 8, j + 8);
                GlStateManager.disableLighting();
                GlStateManager.depthMask(true);
                GlStateManager.enableDepth();
            }
        }
    }

    static {
        net.minecraft.client.gui.achievement.GuiAchievement.achievementBg = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    }

    public GuiAchievement(MinecraftClient aoXValue) {
        this.mc = aoXValue;
        this.renderItem = aoXValue.getRenderItem();
    }

    private void updateAchievementWindowScale() {
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        this.width = this.mc.displayWidth;
        this.height = this.mc.displayHeight;
        ScaledResolution scaledResolution =
                new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        this.width = scaledResolution.getWidth();
        this.height = scaledResolution.getHeight();
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, this.width, this.height, 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
    }

    public void displayUnformattedAchievement(Achievement aslValue) {
        this.achievementTitle = aslValue.getStatName().getUnformattedText();
        this.achievementDescription = aslValue.getDescription();
        long systemTime = MinecraftClient.getSystemTime();

        this.notificationTime = systemTime + 2500L;
        this.theAchievement = aslValue;
        this.permanentNotification = true;
    }

    public void displayAchievement(Achievement aslValue) {
        this.achievementTitle = I18n.format("achievement.get", new Object[0]);
        this.achievementDescription = aslValue.getStatName().getUnformattedText();
        this.notificationTime = MinecraftClient.getSystemTime();
        this.theAchievement = aslValue;
        this.permanentNotification = false;
    }

    public void clearAchievements() {
        this.theAchievement = null;

        this.notificationTime = 0L;
    }
}
