package remix.ui.screen;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import remix.client.ClientServices;
import remix.compat.vecmath.Vector2f;
import remix.util.network.NordVpnProxyManager;
import remix.util.render.Render2D3DUtils;

import java.awt.Color;

class MainMenuIconButton {
    private float x;
    private ResourceLocation resourceLocation;

    private boolean hovered;
    private float height;

    private float hoverAnimationProgress;
    private float width;
    private float y;
    RemixMainMenuScreen remixMainMenuScreen;
    private GuiScreen guiScreen;


    MainMenuIconButton(
            RemixMainMenuScreen remixMainMenuScreenValue,
            String text,
            GuiScreen guiScreen,
            int i,
            int j,
            int k,
            int l) {
        this.remixMainMenuScreen = remixMainMenuScreenValue;
        this.guiScreen = guiScreen;
        this.x = i;
        this.y = j;
        this.width = k;
        this.height = l;
        this.resourceLocation = new ResourceLocation("remix/gui/menu/" + text + ".png");
    }

    public void draw(int i, int j) {
        this.hovered =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (i >= this.x - this.width / 2.0F
                                        && j >= this.y - this.height / 2.0F
                                        && i <= this.x + this.width / 2.0F
                                        && j <= this.y + this.height / 2.0F
                                ? 1
                                : 0));
        this.hoverAnimationProgress =
                Math.max(
                        Math.min(this.hoverAnimationProgress + (this.hovered ? 0.2F : -0.2F), 1.0F),
                        0.0F);
        Render2D3DUtils.drawCircle(
                this.x + this.width / 4.0F,
                this.y + this.width / 4.0F,
                this.width / 2.0F - 5.0F + this.hoverAnimationProgress,
                ((ClientServices)
                                RemixMainMenuScreen.getRemixClient(this.remixMainMenuScreen)
                                        .getClientServices())
                        .getThemeColor());
        Render2D3DUtils.drawCircle(
                this.x + this.width / 4.0F,
                this.y + this.width / 4.0F,
                this.width / 2.0F - 6.0F + this.hoverAnimationProgress,
                new Color(28, 28, 28).getRGB());
        Render2D3DUtils.drawIcon(this.resourceLocation, this.x, this.y, this.width, this.height);
    }

    public void onClick() {
        Entity[] entityArray = NordVpnProxyManager.getLegacyProxyFlowState();
        if (this.hovered) {
            if (this.guiScreen != null) {
                RemixMainMenuScreen.getAnimationInterpolator(this.remixMainMenuScreen)
                        .setTargetPosition(new Vector2f(0.0F, 0.0F));
                RemixMainMenuScreen.getMinecraftClient(this.remixMainMenuScreen)
                        .displayGuiScreen(this.guiScreen);
                if (entityArray == null) {
                    return;
                }
            }

            RemixMainMenuScreen.getMinecraftClientInstance(this.remixMainMenuScreen).shutdown();
        }
    }

    public void setX(float f) {
        this.x = f;
    }

    public boolean isHovered() {
        return this.hovered;
    }

    public void setY(float f) {
        this.y = f;
    }
}
