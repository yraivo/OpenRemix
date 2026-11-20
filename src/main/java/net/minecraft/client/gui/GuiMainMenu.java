package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.client.gui.GuiScreen;

import com.google.common.collect.Lists;

import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
    private Object threadLock;
    private static Random RANDOM;
    private ResourceLocation backgroundTexture;
    private GuiButton buttonResetDemo;
    private int BX;
    private int lD;
    private String openGLWarning1;
    private int BU;
    private String openGLWarningLink;
    private int Ce;
    private int lF;
    private String openGLWarning2;
    private int Ch;
    private int panoramaTimer;
    private String splashText;

    private static ResourceLocation minecraftTitleTextures;
    public static String En;
    private static String[] packedTextFragments;
    private static ResourceLocation[] titlePanoramaPaths;
    private GuiButton realmsButton;
    private boolean lJ;
    private static Logger logger;
    private DynamicTexture viewportTexture;
    private static AtomicInteger anL;
    private float updateCounter;
    private static ResourceLocation splashTexts;

    private void method_80c3ce64938e63de8c07(int i, int j) {
        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 - 100,
                        i,
                        I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        2,
                        this.width / 2 - 100,
                        i + j * 1,
                        I18n.format("menu.multiplayer", new Object[0])));
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    static {
        net.minecraft.client.gui.GuiMainMenu.packedTextFragments = new String[2];
        net.minecraft.client.gui.GuiMainMenu.packedTextFragments[0] = new String("");
        anL = new AtomicInteger(0);
        net.minecraft.client.gui.GuiMainMenu.logger = LogManager.getLogger();
        net.minecraft.client.gui.GuiMainMenu.RANDOM = new Random();
        net.minecraft.client.gui.GuiMainMenu.splashTexts = new ResourceLocation("texts/splashes.txt");
        net.minecraft.client.gui.GuiMainMenu.minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
        net.minecraft.client.gui.GuiMainMenu.titlePanoramaPaths =
                new ResourceLocation[] {
                    new ResourceLocation("textures/gui/title/background/panorama_0.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_1.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_2.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_3.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_4.png"),
                    new ResourceLocation("textures/gui/title/background/panorama_5.png")
                };
        En =
                "Please click "
                        + EnumChatFormatting.UNDERLINE
                        + "here"
                        + EnumChatFormatting.RESET
                        + " for more information.";
    }

    public void updateScreen() {
        this.panoramaTimer++;
    }

    protected void keyTyped(char c0, int i) throws IOException {}

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (guiButton.id == 5) {
            this.mc.displayGuiScreen(
                    new GuiLanguage(
                            this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (guiButton.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (guiButton.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (guiButton.id == 4) {
            this.mc.shutdown();
        }

        if (guiButton.id == 11) {
            this.mc.launchIntegratedServer(
                    "Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }

        if (guiButton.id == 12) {
            ISaveFormat saveLoader = this.mc.getSaveLoader();
            WorldInfo worldInfo = saveLoader.getWorldInfo("Demo_World");
            if (worldInfo != null) {
                GuiYesNo guiYesNo =
                        GuiSelectWorld.method_20e491b2eb2fdcb36a99(
                                this, worldInfo.getWorldName(), 12);
                this.mc.displayGuiScreen(guiYesNo);
            }
        }
    }

    private void drawPanorama(int k, int l, float f) {
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        String[] astring = astring1;
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        byte b0 = 8;
        int i = 0;

        label41:
        while (i < b0 * b0) {
            GlStateManager.pushMatrix();
            float f1 = ((float) (i % b0) / b0 - 0.5F) / 64.0F;
            float f2 = ((float) (i / b0) / b0 - 0.5F) / 64.0F;
            float f3 = 0.0F;
            GlStateManager.translate(f1, f2, f3);
            GlStateManager.rotate(
                    MathHelper.sin((this.panoramaTimer + f) / 400.0F) * 25.0F + 20.0F,
                    1.0F,
                    0.0F,
                    0.0F);
            GlStateManager.rotate(-(this.panoramaTimer + f) * 0.1F, 0.0F, 1.0F, 0.0F);
            int j = 0;

            while (true) {
                if (j < 6) {
                    GlStateManager.pushMatrix();
                    if (j == 1) {
                        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                    }

                    if (j == 2) {
                        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                    }

                    if (j == 3) {
                        GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                    }

                    if (j == 4) {
                        GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                    }

                    if (j == 5) {
                        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                    }

                    this.mc
                            .getTextureManager()
                            .bindTexture(net.minecraft.client.gui.GuiMainMenu.titlePanoramaPaths[j]);
                    worldRenderer.startDrawingQuads();
                    worldRenderer.setVertexColorFromRgbAndAlpha(16777215, 255 / (i + 1));
                    float f4 = 0.0F;
                    worldRenderer.addVertexWithUV(-1.0, -1.0, 1.0, 0.0F + f4, 0.0F + f4);
                    worldRenderer.addVertexWithUV(1.0, -1.0, 1.0, 1.0F - f4, 0.0F + f4);
                    worldRenderer.addVertexWithUV(1.0, 1.0, 1.0, 1.0F - f4, 1.0F - f4);
                    worldRenderer.addVertexWithUV(-1.0, 1.0, 1.0, 0.0F + f4, 1.0F - f4);
                    instance.draw();
                    GlStateManager.popMatrix();
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                GlStateManager.popMatrix();
                GlStateManager.colorMask(true, true, true, false);
                i++;
                if (astring != null) {
                    break label41;
                }
                break;
            }
        }

        worldRenderer.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    private void rotateAndBlurSkybox(float f1) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator instance = Tessellator.getInstance();
        String[] astring = astring1;
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        GlStateManager.disableAlpha();
        byte b0 = 3;
        int i = 0;

        while (i < b0) {
            worldRenderer.setVertexColorFromFloats(1.0F, 1.0F, 1.0F, 1.0F / (i + 1));
            int j = this.width;
            int k = this.height;
            float f = (i - b0 / 2) / 256.0F;
            worldRenderer.addVertexWithUV(j, k, this.zLevel, 0.0F + f, 1.0);
            worldRenderer.addVertexWithUV(j, 0.0, this.zLevel, 1.0F + f, 1.0);
            worldRenderer.addVertexWithUV(0.0, 0.0, this.zLevel, 1.0F + f, 0.0);
            worldRenderer.addVertexWithUV(0.0, k, this.zLevel, 0.0F + f, 0.0);
            i++;
            if (astring != null) {
                break;
            }
        }

        instance.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    public void drawScreen(int i, int j, float f) {
        GlStateManager.disableAlpha();
        this.drawPanorama(i, j, f);
        this.rotateAndBlurSkybox(f);
        this.rotateAndBlurSkybox(f);
        this.rotateAndBlurSkybox(f);
        this.rotateAndBlurSkybox(f);
        this.rotateAndBlurSkybox(f);
        this.rotateAndBlurSkybox(f);
        this.rotateAndBlurSkybox(f);
        super.drawScreen(i, j, f);
    }

    private void addDemoButtons(int i, int j) {
        this.buttonList.add(
                new GuiButton(
                        11, this.width / 2 - 100, i, I18n.format("menu.playdemo", new Object[0])));
        this.buttonList.add(
                this.buttonResetDemo =
                        new GuiButton(
                                12,
                                this.width / 2 - 100,
                                i + j * 1,
                                I18n.format("menu.resetdemo", new Object[0])));
        ISaveFormat saveLoader = this.mc.getSaveLoader();
        WorldInfo worldInfo = saveLoader.getWorldInfo("Demo_World");
        if (worldInfo == null) {
            this.buttonResetDemo.enabled = false;
        }
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        super.mouseClicked(i, j, k);
        Object anBValue = this.threadLock;
        synchronized (this.threadLock) {
            if (this.openGLWarning1.length() > 0
                    && i >= this.BU
                    && i <= this.lD
                    && j >= this.Ce
                    && j <= this.BX) {
                GuiConfirmOpenLink guiConfirmOpenLink =
                        new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiConfirmOpenLink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiConfirmOpenLink);
            }
        }
    }

    private void renderSkybox(int i, int j, float f) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(i, j, f);
        this.rotateAndBlurSkybox(f);
        this.rotateAndBlurSkybox(f);
        this.rotateAndBlurSkybox(f);
        this.rotateAndBlurSkybox(f);
        this.rotateAndBlurSkybox(f);
        this.rotateAndBlurSkybox(f);
        this.rotateAndBlurSkybox(f);
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        float f1 = this.width > this.height ? 120.0F / this.width : 120.0F / this.height;
        float f2 = this.height * f1 / 256.0F;
        float f3 = this.width * f1 / 256.0F;
        worldRenderer.setVertexColorFromFloats(1.0F, 1.0F, 1.0F, 1.0F);
        int k = this.width;
        int l = this.height;
        worldRenderer.addVertexWithUV(0.0, l, this.zLevel, 0.5F - f2, 0.5F + f3);
        worldRenderer.addVertexWithUV(k, l, this.zLevel, 0.5F - f2, 0.5F - f3);
        worldRenderer.addVertexWithUV(k, 0.0, this.zLevel, 0.5F + f2, 0.5F - f3);
        worldRenderer.addVertexWithUV(0.0, 0.0, this.zLevel, 0.5F + f2, 0.5F + f3);
        instance.draw();
    }

    public void confirmClicked(boolean flag, int i) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (flag && i == 12) {
            ISaveFormat saveLoader = this.mc.getSaveLoader();
            saveLoader.flushCache();
            saveLoader.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
            if (astring == null) {
                return;
            }
        }

        if (i == 13) {
            if (flag) {
                try {
                    Class oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop").invoke(null);
                    oclass.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
                } catch (Throwable throwable) {
                    net.minecraft.client.gui.GuiMainMenu.logger.error("Couldn't open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    public void initGui() {
        String[] astring;
        label61:
        {
            String[] astring1 = Gui.getLegacyGuiRenderMarker();
            this.viewportTexture = new DynamicTexture(256, 256);
            astring = astring1;
            this.backgroundTexture =
                    this.mc
                            .getTextureManager()
                            .getDynamicTextureLocation("background", this.viewportTexture);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            if (calendar.get(2) + 1 == 11 && calendar.get(5) == 9) {
                this.splashText = "Happy birthday, ez!";
                if (astring == null) {
                    break label61;
                }
            }

            if (calendar.get(2) + 1 == 6 && calendar.get(5) == 1) {
                this.splashText = "Happy birthday, Notch!";
                if (astring == null) {
                    break label61;
                }
            }

            if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
                this.splashText = "Merry X-mas!";
                if (astring == null) {
                    break label61;
                }
            }

            if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
                this.splashText = "Happy new year!";
                if (astring == null) {
                    break label61;
                }
            }

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
                this.splashText = "OOoooOOOoooo! Spooky!";
            }
        }

        int i;
        {
            i = this.height / 4 + 48;
            if (this.mc.isDemo()) {
                this.addDemoButtons(i, 24);
                if (!(astring == null)) {

                    this.method_80c3ce64938e63de8c07(i, 24);
                }
            } else {

                this.method_80c3ce64938e63de8c07(i, 24);
            }
        }

        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 - 100,
                        i + 72 + 12,
                        98,
                        20,
                        I18n.format("menu.options", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        4,
                        this.width / 2 + 2,
                        i + 72 + 12,
                        98,
                        20,
                        I18n.format("menu.quit", new Object[0])));
        this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, i + 72 + 12));
        Object anBValue = this.threadLock;
        synchronized (this.threadLock) {
            this.Ch = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.lF = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int j = Math.max(this.Ch, this.lF);
            this.BU = (this.width - j) / 2;
            this.Ce = ((GuiButton) this.buttonList.get(0)).yPosition - 24;
            this.lD = this.BU + j;
            this.BX = this.Ce + 24;
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public GuiMainMenu() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.lJ = true;
        String[] astring = astring1;
        this.threadLock = new Object();
        this.openGLWarning2 = En;
        this.splashText = "missingno";
        BufferedReader bufferedreader = null;

        try {
            ArrayList arrayList = Lists.newArrayList();
            bufferedreader =
                    new BufferedReader(
                            new InputStreamReader(
                                    MinecraftClient.getMinecraft()
                                            .getResourceManager()
                                            .getResource(net.minecraft.client.gui.GuiMainMenu.splashTexts)
                                            .getInputStream(),
                                    Charsets.UTF_8));

            String text;
            while ((text = bufferedreader.readLine()) != null) {
                text = text.trim();
                if (!text.isEmpty()) {
                    arrayList.add(text);
                    if (astring != null) {
                        break;
                    }
                }
            }

            if (!arrayList.isEmpty()) {
                do {
                    this.splashText =
                            (String)
                                    arrayList.get(
                                            net.minecraft.client.gui.GuiMainMenu.RANDOM.nextInt(arrayList.size()));
                } while (this.splashText.hashCode() == 125780783);
            }
        } catch (IOException ioexception1) {
        } finally {
            if (bufferedreader != null) {
                try {
                    bufferedreader.close();
                } catch (IOException ioexception) {
                }
            }
        }

        this.updateCounter = net.minecraft.client.gui.GuiMainMenu.RANDOM.nextFloat();
        this.openGLWarning1 = packedTextFragments[0];
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }
}
