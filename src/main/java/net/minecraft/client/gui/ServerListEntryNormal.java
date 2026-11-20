package net.minecraft.client.gui;

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerListEntryNormal implements GuiListExtendedIGuiListEntry {
    private static String[] packedTextFragments;
    private static ResourceLocation SERVER_SELECTION_BUTTONS;

    private static ThreadPoolExecutor avK;
    private GuiMultiplayer avL;
    private DynamicTexture avM;
    private MinecraftClient mc;
    private ResourceLocation sO;

    private ServerData avN;
    private static ResourceLocation UNKNOWN_SERVER;
    private long wc;
    private static Logger logger;
    private String te;

    public static GuiMultiplayer access$100(ServerListEntryNormal serverListEntryNormal) {
        return serverListEntryNormal.avL;
    }

    public ServerData getServerData() {
        return this.avN;
    }

    public boolean mousePressed(int i, int j1, int k1, int l1, int j, int k) {
        if (j <= 32) {
            if (j < 32 && j > 16 && this.aY()) {
                this.avL.selectServer(i);
                this.avL.connectToSelected();
                return true;
            }

            if (j < 16 && k < 16 && this.avL.canMoveServerUp(this, i)) {
                this.avL.moveServerUp(this, i, GuiScreen.isShiftKeyDown());
                return true;
            }

            if (j < 16 && k > 16 && this.avL.canMoveServerDown(this, i)) {
                this.avL.moveServerDown(this, i, GuiScreen.isShiftKeyDown());
                return true;
            }
        }

        this.avL.selectServer(i);
        long l = MinecraftClient.getSystemTime() - this.wc;

        if (l < 250L) {
            this.avL.connectToSelected();
        }

        this.wc = MinecraftClient.getSystemTime();
        return false;
    }

    static {
        net.minecraft.client.gui.ServerListEntryNormal.packedTextFragments = new String[5];
        net.minecraft.client.gui.ServerListEntryNormal.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.ServerListEntryNormal.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.ServerListEntryNormal.packedTextFragments[2] = new String(")");
        net.minecraft.client.gui.ServerListEntryNormal.logger = LogManager.getLogger();
        avK =
                new ScheduledThreadPoolExecutor(
                        5,
                        new ThreadFactoryBuilder()
                                .setNameFormat("Server Pinger #%d")
                                .setDaemon(true)
                                .build());
        net.minecraft.client.gui.ServerListEntryNormal.UNKNOWN_SERVER =
                new ResourceLocation("textures/misc/unknown_server.png");
        net.minecraft.client.gui.ServerListEntryNormal.SERVER_SELECTION_BUTTONS =
                new ResourceLocation("textures/gui/server_selection.png");
    }

    private boolean aY() {
        return true;
    }

    public void setSelected(int i, int j, int k) {}

    public static ServerData access$000(ServerListEntryNormal serverListEntryNormal) {
        return serverListEntryNormal.avN;
    }

    protected ServerListEntryNormal(GuiMultiplayer avLValue, ServerData avNValue) {
        this.avL = avLValue;
        this.avN = avNValue;
        this.mc = MinecraftClient.getMinecraft();
        this.sO = new ResourceLocation("servers/" + avNValue.serverIP + "/icon");
        this.avM = (DynamicTexture) this.mc.getTextureManager().getTexture(this.sO);
    }

    private void method_784e95624d6d70fe7f50() {
        String[] astring = Gui.getLegacyGuiRenderMarker();

        try {
            if (this.avN.getBase64EncodedIconData() == null) {
                this.mc.getTextureManager().deleteTexture(this.sO);
                this.avM = null;
                if (astring == null) {
                    return;
                }
            }
        } catch (Exception exception4) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception4));
        }

        ByteBuf bytebuf =
                Unpooled.copiedBuffer(this.avN.getBase64EncodedIconData(), Charsets.UTF_8);
        ByteBuf bytebuf1 = io.netty.handler.codec.base64.Base64.decode(bytebuf);

        BufferedImage bufferedimage;
        label113:
        {
            try {
                bufferedimage = TextureUtil.readBufferedImage(new ByteBufInputStream(bytebuf1));

                byte b0;
                label98:
                {
                    try {
                        if (bufferedimage.getWidth() == 64) {
                            b0 = 1;
                            break label98;
                        }
                    } catch (Exception exception1) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(a(exception1));
                    }

                    b0 = 0;
                }

                label106:
                {
                    try {
                        Validate.validState(
                                remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                                "Must be 64 pixels wide",
                                new Object[0]);
                        if (bufferedimage.getHeight() == 64) {
                            b0 = 1;
                            break label106;
                        }
                    } catch (Exception exception2) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(a(exception2));
                    }

                    b0 = 0;
                }

                Validate.validState(
                        remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                        "Must be 64 pixels high",
                        new Object[0]);
                break label113;
            } catch (Exception exception3) {
                net.minecraft.client.gui.ServerListEntryNormal.logger.error(
                        "Invalid icon for server "
                                + this.avN.serverName
                                + " ("
                                + this.avN.serverIP
                                + packedTextFragments[2],
                        exception3);
                this.avN.setBase64EncodedIconData((String) null);
            } finally {
                bytebuf.release();
                bytebuf1.release();
            }

            return;
        }

        try {
            if (this.avM == null) {
                this.avM = new DynamicTexture(bufferedimage.getWidth(), bufferedimage.getHeight());
                this.mc.getTextureManager().loadTexture(this.sO, this.avM);
            }
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
        }

        bufferedimage.getRGB(
                0,
                0,
                bufferedimage.getWidth(),
                bufferedimage.getHeight(),
                this.avM.getTextureData(),
                0,
                bufferedimage.getWidth());
        this.avM.updateDynamicTexture();
    }

    public void mouseReleased(int i, int j, int k, int l, int i1, int j1) {}

    protected void method_6f3d2309a95aaa52bcdb(int i, int j, ResourceLocation resourceLocation) {
        this.mc.getTextureManager().bindTexture(resourceLocation);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture(i, j, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
        GlStateManager.disableBlend();
    }

    public void drawEntry(int i, int j, int k, int l, int i5, int i1, int j1, boolean flag) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (!this.avN.ie) {
            this.avN.ie = true;

            this.avN.pingToServer = -2L;
            this.avN.serverMOTD = packedTextFragments[0];
            this.avN.populationInfo = packedTextFragments[1];
            avK.submit(new DrawEntryRunnable(this));
        }

        int k1 = this.avN.version > 47 ? 1 : 0;
        int l1 = this.avN.version < 47 ? 1 : 0;
        int i2 = k1 == 0 && l1 == 0 ? 0 : 1;
        this.mc.fontRendererObj.drawString(this.avN.serverName, j + 32 + 3, k + 1, 16777215);
        List list =
                this.mc.fontRendererObj.listFormattedStringToWidth(this.avN.serverMOTD, l - 32 - 2);
        int j2 = 0;

        while (j2 < Math.min(list.size(), 2)) {
            this.mc.fontRendererObj.drawString(
                    (String) list.get(j2),
                    j + 32 + 3,
                    k + 12 + this.mc.fontRendererObj.FONT_HEIGHT * j2,
                    8421504);
            j2++;
            if (astring != null) {
                break;
            }
        }

        String text =
                i2 != 0
                        ? EnumChatFormatting.DARK_RED + this.avN.gameVersion
                        : this.avN.populationInfo;
        int k2 = this.mc.fontRendererObj.getStringWidth(text);
        this.mc.fontRendererObj.drawString(text, j + l - k2 - 15 - 2, k + 1, 8421504);
        byte b0 = 0;
        String textValue = null;
        int l2;
        String textValue2;
        if (i2 != 0) {
            l2 = 5;
            textValue2 = k1 != 0 ? "Client out of date!" : "Server out of date!";
            textValue = this.avN.playerList;
        } else {
            label179:
            {
                if (this.avN.ie) {

                    if (this.avN.pingToServer != -2L) {
                        label181:
                        {
                            if (this.avN.pingToServer < 0L) {
                                l2 = 5;
                                if (astring == null) {
                                    break label181;
                                }
                            }

                            if (this.avN.pingToServer < 150L) {
                                l2 = 0;
                                if (astring == null) {
                                    break label181;
                                }
                            }

                            if (this.avN.pingToServer < 300L) {
                                l2 = 1;
                                if (astring == null) {
                                    break label181;
                                }
                            }

                            if (this.avN.pingToServer < 600L) {
                                l2 = 2;
                                if (astring == null) {
                                    break label181;
                                }
                            }

                            if (this.avN.pingToServer < 1000L) {
                                l2 = 3;
                                if (astring == null) {
                                    break label181;
                                }
                            }

                            l2 = 4;
                        }

                        if (this.avN.pingToServer < 0L) {
                            textValue2 = "(no connection)";
                            if (astring == null) {
                                break label179;
                            }
                        }

                        textValue2 = this.avN.pingToServer + "ms";
                        textValue = this.avN.playerList;
                        break label179;
                    }
                }

                b0 = 1;
                long k4 = MinecraftClient.getSystemTime();

                k4 = k4 / 100L + i * 2;

                l2 = (int) (k4 & 7L);
                if (l2 > 4) {
                    l2 = 8 - l2;
                }

                textValue2 = "Pinging...";
            }
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Gui.icons);
        Gui.drawModalRectWithCustomSizedTexture(
                j + l - 15, k, b0 * 10, 176 + l2 * 8, 10, 8, 256.0F, 256.0F);
        if (this.avN.getBase64EncodedIconData() != null
                && !this.avN.getBase64EncodedIconData().equals(this.te)) {
            this.te = this.avN.getBase64EncodedIconData();
            this.method_784e95624d6d70fe7f50();
            this.avL.getServerList().saveServerList();
        }

        {
            if (this.avM != null) {
                this.method_6f3d2309a95aaa52bcdb(j, k, this.sO);
                if (!(astring == null)) {

                    this.method_6f3d2309a95aaa52bcdb(
                            j, k, net.minecraft.client.gui.ServerListEntryNormal.UNKNOWN_SERVER);
                }
            } else {

                this.method_6f3d2309a95aaa52bcdb(
                        j, k, net.minecraft.client.gui.ServerListEntryNormal.UNKNOWN_SERVER);
            }
        }

        {
            int i3 = i1 - j;
            int j3 = j1 - k;
            if (i3 >= l - 15 && i3 <= l - 5 && j3 >= 0 && j3 <= 8) {
                this.avL.setHoveringText(textValue2);
                if (!(astring == null)) {

                    if (i3 >= l - k2 - 15 - 2 && i3 <= l - 15 - 2 && j3 >= 0 && j3 <= 8) {
                        this.avL.setHoveringText(textValue);
                    }
                }
            } else {

                if (i3 >= l - k2 - 15 - 2 && i3 <= l - 15 - 2 && j3 >= 0 && j3 <= 8) {
                    this.avL.setHoveringText(textValue);
                }
            }
        }

        if (this.mc.gameSettings.touchscreen || flag) {
            int k3;
            int l3;
            this.mc
                    .getTextureManager()
                    .bindTexture(
                            net.minecraft.client.gui.ServerListEntryNormal
                                    .SERVER_SELECTION_BUTTONS);
            Gui.drawRect(j, k, j + 32, k + 32, -1601138544);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            k3 = i1 - j;
            l3 = j1 - k;
            if (this.aY()) {
                if (k3 < 32 && k3 > 16) {
                    Gui.drawModalRectWithCustomSizedTexture(
                            j, k, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    if (!(astring == null)) {

                        Gui.drawModalRectWithCustomSizedTexture(
                                j, k, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                } else {

                    Gui.drawModalRectWithCustomSizedTexture(
                            j, k, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            }

            if (this.avL.canMoveServerUp(this, i)) {
                if (k3 < 16 && l3 < 16) {
                    Gui.drawModalRectWithCustomSizedTexture(
                            j, k, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    if (!(astring == null)) {

                        Gui.drawModalRectWithCustomSizedTexture(
                                j, k, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                } else {

                    Gui.drawModalRectWithCustomSizedTexture(
                            j, k, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            }

            if (this.avL.canMoveServerDown(this, i)) {
                if (k3 < 16 && l3 > 16) {
                    Gui.drawModalRectWithCustomSizedTexture(
                            j, k, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    if (astring == null) {
                        return;
                    }
                }

                Gui.drawModalRectWithCustomSizedTexture(j, k, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    // Helper used only by this class.
    public static class DrawEntryRunnable implements java.lang.Runnable {

        net.minecraft.client.gui.ServerListEntryNormal avP;

        public DrawEntryRunnable(net.minecraft.client.gui.ServerListEntryNormal avPValue) {
            this.avP = avPValue;
        }

        @java.lang.Override
        public void run() {
            try {
                net.minecraft.client.gui.ServerListEntryNormal.access$100(this.avP)
                        .getOldServerPinger()
                        .ping(net.minecraft.client.gui.ServerListEntryNormal.access$000(this.avP));
            } catch (java.net.UnknownHostException unknownhostexception) {
                net.minecraft.client.multiplayer.ServerData serverData =
                        net.minecraft.client.gui.ServerListEntryNormal.access$000(this.avP);

                serverData.pingToServer = -1L;
                net.minecraft.client.gui.ServerListEntryNormal.access$000(this.avP).serverMOTD =
                        net.minecraft.util.EnumChatFormatting.DARK_RED + "Can't resolve hostname";
            } catch (java.lang.Exception exception) {
                net.minecraft.client.multiplayer.ServerData serverDataValue =
                        net.minecraft.client.gui.ServerListEntryNormal.access$000(this.avP);

                serverDataValue.pingToServer = -1L;
                net.minecraft.client.gui.ServerListEntryNormal.access$000(this.avP).serverMOTD =
                        net.minecraft.util.EnumChatFormatting.DARK_RED + "Can't connect to server.";
            }
        }


    }
}
