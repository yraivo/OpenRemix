package net.minecraft.client.gui.stream;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.stream.IngestServerTester;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import tv.twitch.broadcast.IngestServer;

class GuiIngestServersServerList extends GuiSlot {
    GuiIngestServers avw;

    private static String[] packedTextFragments;

    protected void elementClicked(int i, boolean flag, int j, int k) {
        this.mc.gameSettings.streamPreferredServer =
                this.mc.getTwitchStream().func_152925_v()[i].serverUrl;
        this.mc.gameSettings.saveOptions();
    }

    protected int getSize() {
        return this.mc.getTwitchStream().func_152925_v().length;
    }

    protected void drawBackground() {}

    protected void drawSlot(int i, int j, int k, int j1, int k1, int l1) {
        IngestServer ingestserver;
        Object object;
        String text;
        String textValue;
        label37:
        {
            int i1 = MathHelper.method_7eec03da329a017ec143();
            ingestserver = this.mc.getTwitchStream().func_152925_v()[i];
            int l = i1;
            object = ingestserver.serverUrl.replaceAll("\\{stream_key\\}", packedTextFragments[0]);
            text = (int) ingestserver.bitrateKbps + " kbps";
            textValue = null;
            IngestServerTester ingestServerTester = this.mc.getTwitchStream().func_152932_y();
            if (ingestServerTester != null) {
                if (ingestserver == ingestServerTester.func_153040_c()) {
                    object = EnumChatFormatting.GREEN + (String) object;
                    text = (int) (ingestServerTester.aJ() * 100.0F) + packedTextFragments[1];
                    if (l == 0) {
                        break label37;
                    }
                }

                if (i < ingestServerTester.func_153028_p()) {
                    if (ingestserver.bitrateKbps != 0.0F) {
                        break label37;
                    }

                    text = EnumChatFormatting.RED + "Down!";
                    if (l == 0) {
                        break label37;
                    }
                }

                text = EnumChatFormatting.OBFUSCATED + "1234" + EnumChatFormatting.RESET + " kbps";
                if (l == 0) {
                    break label37;
                }
            }

            if (ingestserver.bitrateKbps == 0.0F) {
                text = EnumChatFormatting.RED + "Down!";
            }
        }

        j -= 15;
        if (this.isSelected(i)) {
            textValue = EnumChatFormatting.BLUE + "(Preferred)";
        } else if (ingestserver.defaultServer) {
            textValue = EnumChatFormatting.GREEN + "(Default)";
        }

        this.avw.drawString(
                GuiIngestServers.method_5e1972305b79d9c0acc5(this.avw),
                ingestserver.serverName,
                j + 2,
                k + 5,
                16777215);
        this.avw.drawString(
                GuiIngestServers.method_7509dddc6d0e84ac2997(this.avw),
                (String) object,
                j + 2,
                k
                        + GuiIngestServers.method_725330cb229d31588846(this.avw)
                                .FONT_HEIGHT
                        + 5
                        + 3,
                3158064);
        this.avw.drawString(
                GuiIngestServers.method_1435fe9714efa69f1d05(this.avw),
                text,
                this.getScrollBarX()
                        - 5
                        - GuiIngestServers.method_3ea8e428df2aa1ab9424(this.avw)
                                .getStringWidth(text),
                k + 5,
                8421504);
        if (textValue != null) {
            this.avw.drawString(
                    GuiIngestServers.method_c9fa548cec92936fa3a3(this.avw),
                    textValue,
                    this.getScrollBarX()
                            - 5
                            - GuiIngestServers.method_1cc115e2e34b0b1d4059(this.avw)
                                    .getStringWidth(textValue),
                    k
                            + 5
                            + 3
                            + GuiIngestServers.method_a1efcbeabb58358b8059(this.avw)
                                    .FONT_HEIGHT,
                    8421504);
        }
    }

    protected boolean isSelected(int i) {
        return this.mc
                .getTwitchStream()
                .func_152925_v()[i]
                .serverUrl
                .equals(this.mc.gameSettings.streamPreferredServer);
    }

    public GuiIngestServersServerList(
            GuiIngestServers avwValue, MinecraftClient minecraftClient) {
        super(
                minecraftClient,
                avwValue.width,
                avwValue.height,
                32,
                avwValue.height - 35,
                (int) (minecraftClient.fontRendererObj.FONT_HEIGHT * 3.5));
        this.avw = avwValue;
        this.setShowSelectionBox(false);
    }

    protected int getScrollBarX() {
        return super.getScrollBarX() + 15;
    }

    static {
        net.minecraft.client.gui.stream.GuiIngestServersServerList.packedTextFragments = new String[4];
        net.minecraft.client.gui.stream.GuiIngestServersServerList.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.stream.GuiIngestServersServerList.packedTextFragments[1] = new String("%");
    }
}
