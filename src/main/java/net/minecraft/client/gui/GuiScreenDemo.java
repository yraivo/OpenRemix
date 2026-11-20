package net.minecraft.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;

public class GuiScreenDemo extends GuiScreen {
    private static ResourceLocation yg;
    private static Logger logger;

    public void drawDefaultBackground() {
        super.drawDefaultBackground();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(yg);
        int i = (this.width - 248) / 2;
        int j = (this.height - 166) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, 248, 166);
    }

    static {
        net.minecraft.client.gui.GuiScreenDemo.logger = LogManager.getLogger();
        yg = new ResourceLocation("textures/gui/demo_background.png");
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        switch (guiButton.id) {
            case 1:
                guiButton.enabled = false;

                try {
                    Class oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop").invoke(null);
                    oclass.getMethod("browse", URI.class)
                            .invoke(object, new URI("http://www.minecraft.net/store?source=demo"));
                    break;
                } catch (Throwable throwable3) {
                    Throwable throwable = throwable3;

                    try {
                        net.minecraft.client.gui.GuiScreenDemo.logger.error(
                                "Couldn't open link", throwable);
                        if (astring == null) {
                            break;
                        }
                    } catch (Throwable throwable2) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(
                                method_b7e52da193241b30710d(throwable2));
                    }
                }
            case 2:
                try {
                    this.mc.displayGuiScreen((GuiScreen) null);
                    this.mc.setIngameFocus();
                } catch (Throwable throwable1) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(
                            method_b7e52da193241b30710d(throwable1));
                }
        }
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public void initGui() {
        this.buttonList.clear();
        byte b0 = -16;
        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 - 116,
                        this.height / 2 + 62 + b0,
                        114,
                        20,
                        I18n.format("demo.help.buy", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        2,
                        this.width / 2 + 2,
                        this.height / 2 + 62 + b0,
                        114,
                        20,
                        I18n.format("demo.help.later", new Object[0])));
    }

    public void updateScreen() {
        super.updateScreen();
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        int k = (this.width - 248) / 2 + 10;
        int l = (this.height - 166) / 2 + 8;
        this.fontRendererObj.drawString(
                I18n.format("demo.help.title", new Object[0]), k, l, 2039583);
        l += 12;
        GameSettings gameSettings = this.mc.gameSettings;
        this.fontRendererObj.drawString(
                I18n.format(
                        "demo.help.movementShort",
                        new Object[] {
                            GameSettings.getKeyDisplayString(
                                    gameSettings.keyBindForward.getKeyCode()),
                            GameSettings.getKeyDisplayString(gameSettings.keyBindLeft.getKeyCode()),
                            GameSettings.getKeyDisplayString(gameSettings.keyBindBack.getKeyCode()),
                            GameSettings.getKeyDisplayString(gameSettings.keyBindRight.getKeyCode())
                        }),
                k,
                l,
                5197647);
        this.fontRendererObj.drawString(
                I18n.format("demo.help.movementMouse", new Object[0]), k, l + 12, 5197647);
        this.fontRendererObj.drawString(
                I18n.format(
                        "demo.help.jump",
                        new Object[] {
                            GameSettings.getKeyDisplayString(gameSettings.keyBindJump.getKeyCode())
                        }),
                k,
                l + 24,
                5197647);
        this.fontRendererObj.drawString(
                I18n.format(
                        "demo.help.inventory",
                        new Object[] {
                            GameSettings.getKeyDisplayString(
                                    gameSettings.keyBindInventory.getKeyCode())
                        }),
                k,
                l + 36,
                5197647);
        this.fontRendererObj.drawSplitString(
                I18n.format("demo.help.fullWrapped", new Object[0]), k, l + 68, 218, 2039583);
        super.drawScreen(i, j, f);
    }
}
