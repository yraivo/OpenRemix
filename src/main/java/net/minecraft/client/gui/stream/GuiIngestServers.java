package net.minecraft.client.gui.stream;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiIngestServers extends GuiScreen {

    private GuiScreen yQ;

    private GuiIngestServersServerList arL;
    private static String[] packedTextFragments;
    private String jR;

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.arL.handleMouseInput();
    }

    public void onGuiClosed() {
        if (this.mc.getTwitchStream().func_152908_z()) {
            this.mc.getTwitchStream().func_152932_y().dn();
        }
    }

    static FontRenderer method_a1efcbeabb58358b8059(
            GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }

    static FontRenderer method_7509dddc6d0e84ac2997(
            GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.arL.drawScreen(i, j, f);
        this.drawCenteredString(this.fontRendererObj, this.jR, this.width / 2, 20, 16777215);
        super.drawScreen(i, j, f);
    }

    static {
        net.minecraft.client.gui.stream.GuiIngestServers.packedTextFragments = new String[2];
        net.minecraft.client.gui.stream.GuiIngestServers.packedTextFragments[0] = new String("");
    }

    static FontRenderer method_1435fe9714efa69f1d05(
            GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }

    public void initGui() {
        this.jR = I18n.format("options.stream.ingest.title", new Object[0]);
        this.arL = new GuiIngestServersServerList(this, this.mc);
        if (!this.mc.getTwitchStream().func_152908_z()) {
            this.mc.getTwitchStream().func_152909_x();
        }

        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 - 155,
                        this.height - 24 - 6,
                        150,
                        20,
                        I18n.format("gui.done", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        2,
                        this.width / 2 + 5,
                        this.height - 24 - 6,
                        150,
                        20,
                        I18n.format("options.stream.ingest.reset", new Object[0])));
    }

    static FontRenderer method_3ea8e428df2aa1ab9424(
            GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        int i = MathHelper.method_7eec03da329a017ec143();
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.mc.displayGuiScreen(this.yQ);
                if (i == 0) {
                    return;
                }
            }

            this.mc.gameSettings.streamPreferredServer = packedTextFragments[0];
            this.mc.gameSettings.saveOptions();
        }
    }

    static FontRenderer method_c9fa548cec92936fa3a3(
            GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }

    static FontRenderer method_5e1972305b79d9c0acc5(
            GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }

    static FontRenderer method_725330cb229d31588846(
            GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }

    static FontRenderer method_1cc115e2e34b0b1d4059(
            GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public GuiIngestServers(GuiScreen guiScreen) {
        this.yQ = guiScreen;
    }
}
