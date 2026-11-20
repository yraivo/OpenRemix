package remix.ui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.compat.vecmath.Vector2f;
import remix.ui.account.AltManagerScreen;
import remix.ui.animation.AnimationInterpolator;
import remix.ui.font.ClientFontRenderer;
import remix.util.network.NordVpnProxyManager;
import remix.util.render.Render2D3DUtils;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class RemixMainMenuScreen extends GuiScreen {
    private static final String MENU_TITLE = "Remix 1.7.1";
    private int backgroundColor;
    private List<MainMenuIconButton> list;
    private AnimationInterpolator animationInterpolator;
    public static boolean xQ;
    private final ResourceLocation resourceLocation =
            new ResourceLocation("remix/gui/menu/Logo.png");

    private static List<String> xU;

    private boolean menuExpanded;
    public static boolean oK;
    private RemixClient remixClient;

    protected void keyTyped(char c0, int i) throws IOException {
        if (i != 0 && i != 1) {
            super.keyTyped(c0, i);
        }
    }

    static MinecraftClient getMinecraftClientInstance(RemixMainMenuScreen remixMainMenuScreen) {
        return remixMainMenuScreen.mc;
    }

    static RemixClient getRemixClient(RemixMainMenuScreen remixMainMenuScreen) {
        return remixMainMenuScreen.remixClient;
    }

    public void drawScreen(int i, int j, float f) {
        Entity[] entityArray = NordVpnProxyManager.getLegacyProxyFlowState();

        Entity[] entityArrayValue = entityArray;
        ClientFontRenderer clientFontRenderer = RemixClient.fonts.comfortaa24;
        Color color =
                new Color(((ClientServices) this.remixClient.getClientServices()).getThemeColor());
        float f1 = this.animationInterpolator.stepX();
        if (f1 < 0.5) {
            f1 = 0.0F;
        }

        Gui.drawRect(0.0, 0.0, this.width, this.height, this.backgroundColor);
        Gui.drawRect(
                this.width / 2.0 - f1,
                this.height / 2.0F + 44.0F,
                this.width / 2.0 + f1,
                this.height / 2.0 + 46.5,
                ((ClientServices) this.remixClient.getClientServices()).getThemeColor());
        Gui.drawRect(
                this.width / 2.0 - f1,
                this.height / 2.0F - 44.0F,
                this.width / 2.0 + f1,
                this.height / 2.0 - 46.5,
                ((ClientServices) this.remixClient.getClientServices()).getThemeColor());
        clientFontRenderer.drawString(
                MENU_TITLE,
                this.width / 2.0F - clientFontRenderer.getStringWidth(MENU_TITLE) / 2.0F,
                this.height - clientFontRenderer.getHeight() - 5.0F + 40.0F - f1 / 4.0F,
                -1);
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        Render2D3DUtils.drawLine2D(
                this.width / 2.0F + f1,
                this.height / 2.0F - 47.5F,
                this.width,
                this.height / 2.0F + 47.5F);
        Render2D3DUtils.drawTextureModalWithPalette(
                this.resourceLocation,
                this.width / 2.0 - 47.5 + f1,
                this.height / 2 - 47,
                190.0F,
                190.0F,
                new int[] {color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()});
        Render2D3DUtils.drawLine2D(
                0.0F,
                this.height / 2.0F - 47.5F,
                this.width / 2.0F - f1,
                this.height / 2.0F + 47.5F);
        Render2D3DUtils.drawTextureModalWithPalette(
                this.resourceLocation,
                this.width / 2.0 - 47.5 - f1,
                this.height / 2.0 - 47.0,
                190.0F,
                190.0F,
                new int[] {color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()});
        Render2D3DUtils.drawLine2D(
                this.width / 2.0F - f1,
                this.height / 2.0F - 47.5F,
                this.width / 2.0F + f1,
                this.height / 2.0F + 47.5F);

        for (MainMenuIconButton mainMenuIconButton : this.list) {
            mainMenuIconButton.draw(i, j);
            if (entityArrayValue != null) {
                break;
            }
        }

        GL11.glDisable(3089);
        GL11.glPopMatrix();
        super.drawScreen(i, j, f);
    }

    static AnimationInterpolator getAnimationInterpolator(RemixMainMenuScreen remixMainMenuScreen) {
        return remixMainMenuScreen.animationInterpolator;
    }

    public void initGui() {
        super.initGui();
        this.list = new ArrayList<>();
        this.list.add(
                new MainMenuIconButton(
                        this, "Singleplayer", new GuiSelectWorld(this), 0, 0, 50, 50));
        this.list.add(
                new MainMenuIconButton(
                        this, "Multiplayer", new GuiMultiplayer(this), 0, 0, 50, 50));
        Entity[] entityArray = NordVpnProxyManager.getLegacyProxyFlowState();
        this.list.add(
                new MainMenuIconButton(
                        this,
                        "Settings",
                        new GuiOptions(this, this.mc.gameSettings),
                        0,
                        0,
                        50,
                        50));
        this.list.add(
                new MainMenuIconButton(this, "Accounts", new AltManagerScreen(this), 0, 0, 50, 50));
        this.list.add(new MainMenuIconButton(this, "Exit", null, 0, 0, 50, 50));
        this.animationInterpolator =
                new AnimationInterpolator(
                        10.0F, new Vector2f(0.0F, 0.0F), new Vector2f(0.0F, 0.0F));
        float f = this.width / 2.0F - 132.0F;
        Iterator iterator = this.list.iterator();
        Entity[] entityArrayValue = entityArray;

        while (iterator.hasNext()) {
            MainMenuIconButton mainMenuIconButton = (MainMenuIconButton) iterator.next();
            mainMenuIconButton.setX(f);
            mainMenuIconButton.setY(this.height / 2.0F - 12.0F);
            f += 60.0F;
            if (entityArrayValue != null) {
                break;
            }
        }
    }


    public RemixMainMenuScreen(RemixClient remixClient) {
        this.backgroundColor = new Color(28, 28, 28).getRGB();
        this.remixClient = remixClient;
    }

    protected void mouseClicked(int i, int j, int i1) {
        Entity[] entityArray = NordVpnProxyManager.getLegacyProxyFlowState();

        Entity[] entityArrayValue = entityArray;
        int k =
                i >= this.width / 2 - 47 - this.animationInterpolator.stepX()
                                && i <= this.width / 2 - this.animationInterpolator.stepX()
                                && j >= this.height / 2 - 47 - this.animationInterpolator.stepX()
                                && j <= this.height / 2 + 47
                        ? 1
                        : 0;
        int l =
                i >= this.width / 2 + this.animationInterpolator.stepX()
                                && i <= this.width / 2 + 47 + this.animationInterpolator.stepX()
                                && j >= this.height / 2 - 47 - this.animationInterpolator.stepX()
                                && j <= this.height / 2 + 47
                        ? 1
                        : 0;
        if (k == 0 && l == 0) {
            for (MainMenuIconButton mainMenuIconButton : this.list) {
                if (i >= this.width / 2 - (int) this.animationInterpolator.stepX()
                        && j >= this.height / 2 - 47
                        && i <= this.width / 2 + (int) this.animationInterpolator.stepX()
                        && j <= this.height / 2 + 47
                        && mainMenuIconButton.isHovered()) {
                    mainMenuIconButton.onClick();
                }

                if (entityArrayValue != null) {
                    break;
                }
            }
        } else {
            this.menuExpanded =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.menuExpanded ? 1 : 0));
            this.animationInterpolator.setTargetPosition(
                    new Vector2f(this.menuExpanded ? 160.0F : 0.0F, 0.0F));
        }
    }

    static MinecraftClient getMinecraftClient(RemixMainMenuScreen remixMainMenuScreen) {
        return remixMainMenuScreen.mc;
    }
}
