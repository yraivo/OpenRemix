package net.minecraft.client.gui.stream;

import com.google.common.collect.Lists;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.NullStream;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.SessionType;
import net.minecraft.util.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import tv.twitch.ErrorCode;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GuiStreamUnavailable extends GuiScreen {
    private GuiStreamUnavailableReason arP;
    private List arQ;

    private GuiScreen parentScreen;
    private final ChatComponent arR =
            new ChatComponentTranslation("stream.unavailable.title", new Object[0]);
    private static Logger anR;
    private List rn;

    private static String[] packedTextFragments;

    static {
        net.minecraft.client.gui.stream.GuiStreamUnavailable.packedTextFragments = new String[3];
        net.minecraft.client.gui.stream.GuiStreamUnavailable.packedTextFragments[0] =
                new String("");
        anR = LogManager.getLogger();
    }

    @Override
    public void initGui() {
        int i = MathHelper.method_7eec03da329a017ec143();
        if (this.arQ.isEmpty()) {
            this.arQ.addAll(
                    this.fontRendererObj.listFormattedStringToWidth(
                            this.arP.uD().getFormattedText(), (int) (this.width * 0.75F)));
            if (this.rn != null) {
                this.arQ.add(packedTextFragments[0]);

                for (java.util.Iterator<?> __recoveryIterator171_6020 = (this.rn).iterator();
                        (__recoveryIterator171_6020).hasNext(); ) {
                    ChatComponentTranslation chatComponentTranslation =
                            (ChatComponentTranslation) (__recoveryIterator171_6020).next();
                    this.arQ.add(chatComponentTranslation.getUnformattedTextForChat());
                    if (i != 0) {
                        break;
                    }
                }
            }
        }

        if (this.arP.vT() != null) {
            this.buttonList.add(
                    new GuiButton(
                            0,
                            this.width / 2 - 155,
                            this.height - 50,
                            150,
                            20,
                            I18n.format("gui.cancel", new Object[0])));
            this.buttonList.add(
                    new GuiButton(
                            1,
                            this.width / 2 - 155 + 160,
                            this.height - 50,
                            150,
                            20,
                            I18n.format(this.arP.vT().getFormattedText(), new Object[0])));
            if (i == 0) {
                return;
            }
        }

        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 - 75,
                        this.height - 50,
                        150,
                        20,
                        I18n.format("gui.cancel", new Object[0])));
    }

    @Override
    public void onGuiClosed() {}

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) throws IOException {
        int i = MathHelper.method_beea3e16fe3ec0995eb9();
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                switch (this.arP) {
                    case ACCOUNT_NOT_BOUND:
                    case FAILED_TWITCH_AUTH:
                        this.method_be01d1eb5e587c583ed6("https://account.mojang.com/me/settings");
                        if (i != 0) {
                            break;
                        }
                    case ACCOUNT_NOT_MIGRATED:
                        this.method_be01d1eb5e587c583ed6("https://account.mojang.com/migrate");
                        if (i != 0) {
                            break;
                        }
                    case UNSUPPORTED_OS_MAC:
                        this.method_be01d1eb5e587c583ed6("http://www.apple.com/osx/");
                        if (i != 0) {
                            break;
                        }
                    case UNKNOWN:
                    case LIBRARY_FAILURE:
                    case INITIALIZATION_FAILURE:
                        this.method_be01d1eb5e587c583ed6("http://bugs.mojang.com/browse/MC");
                }
            }

            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    private void method_be01d1eb5e587c583ed6(String text) {
        try {
            Class oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke(null);
            oclass.getMethod("browse", URI.class).invoke(object, new URI(text));
        } catch (Throwable throwable) {
            anR.error("Couldn't open link", throwable);
        }
    }

    public GuiStreamUnavailable(
            GuiScreen guiScreen, GuiStreamUnavailableReason guiStreamUnavailableReason) {
        this(guiScreen, guiStreamUnavailableReason, (List) null);
    }

    public GuiStreamUnavailable(
            GuiScreen anPValue, GuiStreamUnavailableReason arPValue, List list) {
        this.arQ = Lists.newArrayList();
        this.parentScreen = anPValue;
        this.arP = arPValue;
        this.rn = list;
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        int i1 = MathHelper.method_beea3e16fe3ec0995eb9();
        int l =
                Math.max(
                        (int)
                                (this.height * 0.85 / 2.0
                                        - this.arQ.size()
                                                * this.fontRendererObj.FONT_HEIGHT
                                                / 2.0F),
                        50);
        this.drawCenteredString(
                this.fontRendererObj,
                this.arR.getFormattedText(),
                this.width / 2,
                l - this.fontRendererObj.FONT_HEIGHT * 2,
                16777215);
        Iterator iterator = this.arQ.iterator();
        int k = i1;

        while (iterator.hasNext()) {
            String text = (String) iterator.next();
            this.drawCenteredString(this.fontRendererObj, text, this.width / 2, l, 10526880);
            l += this.fontRendererObj.FONT_HEIGHT;
            if (k == 0) {
                break;
            }
        }

        super.drawScreen(i, j, f);
    }

    public static void showUnavailableScreen(GuiScreen guiScreen) {
        MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
        int j = MathHelper.method_7eec03da329a017ec143();
        IStream twitchStream = minecraftClient.getTwitchStream();
        int i = j;
        if (!OpenGlHelper.framebufferSupported) {
            ArrayList arrayList = Lists.newArrayList();
            arrayList.add(
                    new ChatComponentTranslation(
                            "stream.unavailable.no_fbo.version",
                            new Object[] {GL11.glGetString(7938)}));
            arrayList.add(
                    new ChatComponentTranslation(
                            "stream.unavailable.no_fbo.blend",
                            new Object[] {GLContext.getCapabilities().GL_EXT_blend_func_separate}));
            arrayList.add(
                    new ChatComponentTranslation(
                            "stream.unavailable.no_fbo.arb",
                            new Object[] {GLContext.getCapabilities().GL_ARB_framebuffer_object}));
            arrayList.add(
                    new ChatComponentTranslation(
                            "stream.unavailable.no_fbo.ext",
                            new Object[] {GLContext.getCapabilities().GL_EXT_framebuffer_object}));
            minecraftClient.displayGuiScreen(
                    new GuiStreamUnavailable(
                            guiScreen, GuiStreamUnavailableReason.NO_FBO, arrayList));
            if (i == 0) {
                return;
            }
        }

        if (twitchStream instanceof NullStream) {
            if (((NullStream) twitchStream)
                    .func_152937_a()
                    .getMessage()
                    .contains("Can't load AMD 64-bit .dll on a IA 32-bit platform")) {
                minecraftClient.displayGuiScreen(
                        new GuiStreamUnavailable(
                                guiScreen, GuiStreamUnavailableReason.LIBRARY_ARCH_MISMATCH));
                if (i == 0) {
                    return;
                }
            }

            minecraftClient.displayGuiScreen(
                    new GuiStreamUnavailable(
                            guiScreen, GuiStreamUnavailableReason.LIBRARY_FAILURE));
            if (i == 0) {
                return;
            }
        }

        if (!twitchStream.func_152928_D()
                && twitchStream.func_152912_E() == ErrorCode.TTV_EC_OS_TOO_OLD) {
            switch (Util.getOSType()) {
                case WINDOWS:
                    minecraftClient.displayGuiScreen(
                            new GuiStreamUnavailable(
                                    guiScreen, GuiStreamUnavailableReason.UNSUPPORTED_OS_WINDOWS));
                    if (i == 0) {
                        return;
                    }
                case OSX:
                    minecraftClient.displayGuiScreen(
                            new GuiStreamUnavailable(
                                    guiScreen, GuiStreamUnavailableReason.UNSUPPORTED_OS_MAC));
                    if (i == 0) {
                        return;
                    }
                default:
                    minecraftClient.displayGuiScreen(
                            new GuiStreamUnavailable(
                                    guiScreen, GuiStreamUnavailableReason.UNSUPPORTED_OS_OTHER));
                    if (i == 0) {
                        return;
                    }
            }
        }

        if (!minecraftClient.getTwitchDetails().containsKey("twitch_access_token")) {
            if (minecraftClient.getSession().getSessionType() == SessionType.LEGACY) {
                minecraftClient.displayGuiScreen(
                        new GuiStreamUnavailable(
                                guiScreen, GuiStreamUnavailableReason.ACCOUNT_NOT_MIGRATED));
                if (i == 0) {
                    return;
                }
            }

            minecraftClient.displayGuiScreen(
                    new GuiStreamUnavailable(
                            guiScreen, GuiStreamUnavailableReason.ACCOUNT_NOT_BOUND));
            if (i == 0) {
                return;
            }
        }

        if (!twitchStream.func_152913_F()) {
            switch (twitchStream.func_152918_H()) {
                case INVALID_TOKEN:
                    minecraftClient.displayGuiScreen(
                            new GuiStreamUnavailable(
                                    guiScreen, GuiStreamUnavailableReason.FAILED_TWITCH_AUTH));
                    if (i == 0) {
                        return;
                    }
                case ERROR:
                default:
                    minecraftClient.displayGuiScreen(
                            new GuiStreamUnavailable(
                                    guiScreen,
                                    GuiStreamUnavailableReason.FAILED_TWITCH_AUTH_ERROR));
                    if (i == 0) {
                        return;
                    }
            }
        }

        if (twitchStream.func_152912_E() != null) {
            List list =
                    Arrays.asList(
                            new ChatComponentTranslation(
                                    "stream.unavailable.initialization_failure.extra",
                                    new Object[] {
                                        ErrorCode.getString(twitchStream.func_152912_E())
                                    }));
            minecraftClient.displayGuiScreen(
                    new GuiStreamUnavailable(
                            guiScreen, GuiStreamUnavailableReason.INITIALIZATION_FAILURE, list));
            if (i == 0) {
                return;
            }
        }

        minecraftClient.displayGuiScreen(
                new GuiStreamUnavailable(guiScreen, GuiStreamUnavailableReason.UNKNOWN));
    }
}
