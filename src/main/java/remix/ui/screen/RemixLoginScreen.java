package remix.ui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import remix.client.ClientServices;
import remix.client.ClientStartup;
import remix.client.RemixClient;
import remix.compat.vecmath.Vector2f;
import remix.ui.animation.AnimationInterpolator;
import remix.ui.widget.CustomGuiTextField;
import remix.util.network.NordVpnProxyManager;
import remix.util.render.Render2D3DUtils;
import remix.util.render.WorldRenderUtils;

import java.awt.Color;

public class RemixLoginScreen extends GuiScreen {

    private static ResourceLocation userIconTexture;
    private boolean buttonToggleState;
    private int borderAnimationSide;
    private AnimationInterpolator panelInsetAnimation;
    protected int borderAnimationProgress;
    private CustomGuiTextField customGuiTextField;
    private static String[] packedTextFragments;
    private int previousBorderProgress;
    private AnimationInterpolator formExpansionAnimation;
    private ScaledResolution scaledResolution;

    private boolean formCollapsed = true;
    private static ResourceLocation loginIconTexture;
    private AnimationInterpolator formVerticalAnimation;

    public void actionPerformed(GuiButton guiButton) {
        Entity[] entityArray = NordVpnProxyManager.getLegacyProxyFlowState();
        switch (guiButton.id) {
            case 0:
                try {
                    RemixClient.INSTANCE.handleLegacyRemoteLicenseCallback(
                            this.customGuiTextField.getText());
                    break;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    this.mc.shutdown();
                    if (entityArray == null) {
                        break;
                    }
                }
            case 1:
                this.mc.shutdown();
                if (entityArray == null) {
                    break;
                }
            case 2:
                this.buttonToggleState =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!this.buttonToggleState ? 1 : 0));
                this.updateScreen();
        }
    }

    static {
        remix.ui.screen.RemixLoginScreen.packedTextFragments = new String[3];
        remix.ui.screen.RemixLoginScreen.packedTextFragments[0] = new String("");
        remix.ui.screen.RemixLoginScreen.userIconTexture =
                new ResourceLocation("remix/gui/menu/User.png");
        remix.ui.screen.RemixLoginScreen.loginIconTexture =
                new ResourceLocation("remix/gui/menu/Login.png");
    }

    public void mouseClicked(int i, int j, int k) {
        try {
            if (!this.formCollapsed) {
                super.mouseClicked(i, j, k);
            }

            if (i >= this.width / 2 - 47 - this.formExpansionAnimation.stepX()
                            && i <= this.width / 2 - this.formExpansionAnimation.stepX()
                            && j >= this.height / 2 - 47 - this.formExpansionAnimation.stepX()
                            && j <= this.height / 2 + 47
                    || i >= this.width / 2 + this.formExpansionAnimation.stepX()
                            && i <= this.width / 2 + 47 + this.formExpansionAnimation.stepX()
                            && j >= this.height / 2 - 47 - this.formExpansionAnimation.stepX()
                            && j <= this.height / 2 + 47) {
                this.formExpansionAnimation.setTargetPosition(
                        new Vector2f(this.formCollapsed ? 160.0F : 0.0F, this.height / 4));
                this.panelInsetAnimation.setTargetPosition(
                        new Vector2f(this.formCollapsed ? 10.0F : 0.0F, 100.0F));
                this.formVerticalAnimation.setTargetPosition(
                        new Vector2f(
                                0.0F,
                                this.formCollapsed
                                        ? this.customGuiTextField.yPosition
                                                - RemixClient.fonts.comfortaa24.getHeight()
                                        : -RemixClient.fonts.comfortaa24.getHeight()));
                this.formCollapsed =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!this.formCollapsed ? 1 : 0));
            }

            if (!this.formCollapsed) {
                this.customGuiTextField.mouseClicked(i, j, k);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void drawScreen(int i, int j, float f) {
        this.scaledResolution =
                new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        int k = ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor();
        float f1 = (int) this.formExpansionAnimation.stepX();
        Entity[] entityArray = NordVpnProxyManager.getLegacyProxyFlowState();
        Color color = new Color(28, 28, 28);
        Gui.drawRect(0.0, 0.0, this.width, this.height, color.getRGB());
        float f2 = this.formVerticalAnimation.stepY();
        Entity[] entityArrayValue = entityArray;
        if (this.formExpansionAnimation.getTargetPosition().x == 160.0F
                || Math.round(this.formExpansionAnimation.stepX()) > 0.5) {
            WorldRenderUtils.drawComplexGradientRect(
                    this.width / 2.0F - f1,
                    f2 / 3.0F,
                    this.width / 2.0F + f1,
                    this.height - f2 / 3.0F,
                    2.0F,
                    k,
                    color.darker().getRGB(),
                    color.darker().getRGB());
        }

        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        byte b0;
        byte b1;
        byte b2;
        {
            f3 = this.height / 4 + 9;
            f4 = this.width / 2 - 120 - 5 - 2;
            f5 = f3 - 5.0F + 26.0F;
            f6 = this.width / 2 - 120 + 12;
            f7 = f3 + 40.0F;
            f8 = f4 - 1.0F;
            f9 = f3 - 5.0F - 2.0F - 3.0F + 30.0F;
            b0 = 23;
            b1 = 23;
            b2 = 1;

            byte b4 = 23;

            this.borderAnimationProgress++;
            if (this.borderAnimationProgress >= b4) {
                if (this.borderAnimationSide == 4) {
                    this.borderAnimationSide = 0;
                }

                this.borderAnimationSide++;
                this.previousBorderProgress = this.borderAnimationProgress;
                this.borderAnimationProgress = 0;
            }

            this.previousBorderProgress--;
            if (!(entityArrayValue == null)) {

                this.borderAnimationProgress--;
                this.previousBorderProgress++;
                if (this.borderAnimationProgress <= 0) {
                    this.borderAnimationProgress = 0;
                }

                if (this.previousBorderProgress >= 0) {
                    this.previousBorderProgress = 0;
                }

                if (this.borderAnimationSide == 5) {
                    this.borderAnimationSide = 1;
                }
            }
        }

        Color color1 = new Color(k);
        RemixClient.fonts.comfortaa24.drawString(
                "Remix Login",
                this.width / 2 - RemixClient.fonts.comfortaa24.getStringWidth("Remix Login") / 2,
                -RemixClient.fonts.comfortaa24.getHeight() - 15.0F + f1 / 4.0F,
                -1);
        RemixClient.fonts.roboto18.drawString(
                "UID", this.width / 2 - 100, this.formVerticalAnimation.stepY(), 10526880);
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        int[] aint =
                new int[] {color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()};
        Render2D3DUtils.drawLine2D(
                this.width / 2.0F + f1,
                this.height / 2.0F - 47.5F,
                this.width,
                this.height / 2.0F + 47.5F);
        Render2D3DUtils.drawTextureModalWithPalette(
                remix.ui.screen.RemixLoginScreen.loginIconTexture,
                (float) (this.width / 2.0F - 47.5 + f1),
                this.height / 2 - 47,
                190.0F,
                190.0F,
                aint);
        Render2D3DUtils.drawLine2D(
                0.0F,
                this.height / 2.0F - 47.5F,
                this.width / 2.0F - f1,
                this.height / 2.0F + 47.5F);
        Render2D3DUtils.drawTextureModalWithPalette(
                remix.ui.screen.RemixLoginScreen.loginIconTexture,
                (float) (this.width / 2.0F - 47.5 - f1),
                this.height / 2 - 47,
                190.0F,
                190.0F,
                aint);
        Render2D3DUtils.drawLine2D(
                this.width / 2.0F - f1, 0.0F, this.width / 2.0F + f1, this.height);
        WorldRenderUtils.drawFilledBox(f4, f5, f6 + 2.0F, f7 + 2.0F, 1056964607);
        WorldRenderUtils.drawRoundedOutline(f4 + 2.0F, f5 + 2.0F, f6, f7, 1.5F, k, k);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        WorldRenderUtils.drawCustomTexture(
                this.width / 2
                        - 120
                        - 4
                        - RemixClient.fonts.roboto18.getStringWidth(packedTextFragments[0]) / 2.0F
                        - 1.0000095F,
                f3 - 4.0F + 30.0F - 7.0F + 3.0F,
                remix.ui.screen.RemixLoginScreen.userIconTexture,
                18,
                18);
        switch (this.borderAnimationSide) {
            case 1:
                WorldRenderUtils.drawFilledBox(
                        f8, f9 + b0 - this.borderAnimationProgress, f8 + b2, f9, k);
                WorldRenderUtils.drawFilledBox(
                        f8, f9, f8 + this.borderAnimationProgress, f9 + b2, k);
                if (entityArrayValue == null) {
                    break;
                }
            case 2:
                WorldRenderUtils.drawFilledBox(
                        f8 + this.borderAnimationProgress, f9, f8 + b1, f9 + b2, k);
                WorldRenderUtils.drawFilledBox(
                        f8 + b1 - b2, f9, f8 + b1, f9 + this.borderAnimationProgress, k);
                if (entityArrayValue == null) {
                    break;
                }
            case 3:
                WorldRenderUtils.drawFilledBox(
                        f8 + b1 - b2, f9 + this.borderAnimationProgress, f8 + b1, f9 + b0, k);
                WorldRenderUtils.drawFilledBox(
                        f8 + b1, f9 + b0, f8 + b1 - this.borderAnimationProgress, f9 + b0 - b2, k);
                if (entityArrayValue == null) {
                    break;
                }
            case 4:
                WorldRenderUtils.drawFilledBox(
                        f8 + b1 - this.borderAnimationProgress, f9 + b0, f8, f9 + b0 - b2, k);
                WorldRenderUtils.drawFilledBox(
                        f8, f9 + b0, f8 + b2, f9 + b0 - this.borderAnimationProgress, k);
        }

        super.drawScreen(i, j, f);
        this.customGuiTextField.drawTextBox();
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public void onResize(MinecraftClient minecraftClient, int i, int j) {
        this.formExpansionAnimation =
                new AnimationInterpolator(
                        10.0F, new Vector2f(0.0F, 0.0F), new Vector2f(0.0F, 0.0F));
        this.panelInsetAnimation =
                new AnimationInterpolator(
                        10.0F, new Vector2f(0.0F, 0.0F), new Vector2f(0.0F, 0.0F));
        this.formVerticalAnimation.setTargetPosition(
                new Vector2f(0.0F, -RemixClient.fonts.comfortaa24.getHeight()));
        this.formCollapsed = true;
        super.onResize(minecraftClient, i, j);
    }

    public void updateScreen() {
        this.customGuiTextField.updateCursorCounter();
        ScaledResolution scaledResolution =
                new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        if (this.scaledResolution.getHeight() != scaledResolution.getHeight()
                && this.scaledResolution.getWidth() != scaledResolution.getWidth()) {
            this.formCollapsed = true;
            this.formExpansionAnimation.setTargetPosition(new Vector2f(0.0F, 0.0F));
        }

        super.updateScreen();
    }

    public void initGui() {
        ClientStartup.enter(this);
    }

    public RemixLoginScreen() {
        this.borderAnimationSide = 1;
        this.buttonToggleState = true;
    }

    public void keyTyped(char c0, int i) {
        Entity[] entityArray = NordVpnProxyManager.getLegacyProxyFlowState();
        this.customGuiTextField.textboxKeyTyped(c0, i);
        Entity[] entityArrayValue = entityArray;
        if (i == 15) {
            this.customGuiTextField.setFocused(!this.customGuiTextField.isFocused());
            if (entityArrayValue == null) {
                return;
            }
        }

        if (i == 28) {
            try {
                RemixClient.INSTANCE.handleLegacyRemoteLicenseCallback(
                        this.customGuiTextField.getText());
            } catch (Exception exception) {
                this.mc.shutdown();
            }
        }
    }
}
