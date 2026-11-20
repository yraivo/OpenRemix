package net.minecraft.client.gui.achievement;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatusEnumState;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiStats extends GuiScreen implements IProgressMeter {

    protected String screenTitle = "Select world";
    private StatFileWriter atg;
    private GuiStatsStatsMobsList mobStats;
    protected GuiScreen parentScreen;
    private GuiSlot displaySlot;
    private GuiStatsStatsBlock blockStats;
    private boolean doesGuiPauseGame = true;

    private GuiStatsStatsGeneral generalStats;
    private GuiStatsStatsItem itemStats;

    public void createButtons() {
        ResourceLocation.cB();
        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 + 4,
                        this.height - 28,
                        150,
                        20,
                        I18n.format("gui.done", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 - 160,
                        this.height - 52,
                        80,
                        20,
                        I18n.format("stat.generalButton", new Object[0])));
        GuiButton guiButton;
        this.buttonList.add(
                guiButton =
                        new GuiButton(
                                2,
                                this.width / 2 - 80,
                                this.height - 52,
                                80,
                                20,
                                I18n.format("stat.blocksButton", new Object[0])));
        GuiButton guiButtonValue;
        this.buttonList.add(
                guiButtonValue =
                        new GuiButton(
                                3,
                                this.width / 2,
                                this.height - 52,
                                80,
                                20,
                                I18n.format("stat.itemsButton", new Object[0])));
        GuiButton guiButtonValue2;
        this.buttonList.add(
                guiButtonValue2 =
                        new GuiButton(
                                4,
                                this.width / 2 + 80,
                                this.height - 52,
                                80,
                                20,
                                I18n.format("stat.mobsButton", new Object[0])));
        if (this.blockStats.getSize() == 0) {
            guiButton.enabled = false;
        }

        if (this.itemStats.getSize() == 0) {
            guiButtonValue.enabled = false;
        }

        if (this.mobStats.getSize() == 0) {
            guiButtonValue2.enabled = false;
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ResourceLocation.b(new Entity[2]);
        }
    }

    static FontRenderer method_fc02cfeae2f71c669f44(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    static void method_990a434a36afc3fcb2ef(
            GuiStats guiStats, double d0, double d1, double d2, double d3, int i, int j) {
        guiStats.drawGradientRect(d0, d1, d2, d3, i, j);
    }

    public void method_784e95624d6d70fe7f50() {
        this.generalStats = new GuiStatsStatsGeneral(this, this.mc);
        this.generalStats.registerScrollButtons(1, 1);
        this.itemStats = new GuiStatsStatsItem(this, this.mc);
        this.itemStats.registerScrollButtons(1, 1);
        this.blockStats = new GuiStatsStatsBlock(this, this.mc);
        this.blockStats.registerScrollButtons(1, 1);
        this.mobStats = new GuiStatsStatsMobsList(this, this.mc);
        this.mobStats.registerScrollButtons(1, 1);
    }

    static void access$400(GuiStats guiStats, int i, int j, int k, int l) {
        guiStats.drawSprite(i, j, k, l);
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        ResourceLocation.cB();

        if (this.doesGuiPauseGame) {
            this.drawDefaultBackground();
            this.drawCenteredString(
                    this.fontRendererObj,
                    I18n.format("multiplayer.downloadingStats", new Object[0]),
                    this.width / 2,
                    this.height / 2,
                    16777215);
            FontRenderer apkValue = this.fontRendererObj;
            String[] astring = net.minecraft.client.gui.IProgressMeter.lanSearchStates;
            long k = MinecraftClient.getSystemTime();

            this.drawCenteredString(
                    apkValue,
                    astring[(int) (k / 150L % net.minecraft.client.gui.IProgressMeter.lanSearchStates.length)],
                    this.width / 2,
                    this.height / 2 + this.fontRendererObj.FONT_HEIGHT * 2,
                    16777215);

            return;
        }

        this.displaySlot.drawScreen(i, j, f);
        this.drawCenteredString(
                this.fontRendererObj, this.screenTitle, this.width / 2, 20, 16777215);
        super.drawScreen(i, j, f);
    }

    static FontRenderer method_4f4b0d0c4dd7acaa818c(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    static FontRenderer access$2200(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    static FontRenderer access$1700(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    public void doneLoading() {
        if (this.doesGuiPauseGame) {
            this.method_784e95624d6d70fe7f50();
            this.createButtons();
            this.displaySlot = this.generalStats;
            this.doesGuiPauseGame = false;
        }
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) throws IOException {
        ResourceLocation.cB();

        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                this.mc.displayGuiScreen(this.parentScreen);

                return;
            }

            if (guiButton.id == 1) {
                this.displaySlot = this.generalStats;

                return;
            }

            if (guiButton.id == 3) {
                this.displaySlot = this.itemStats;

                return;
            }

            if (guiButton.id == 2) {
                this.displaySlot = this.blockStats;

                return;
            }

            if (guiButton.id == 4) {
                this.displaySlot = this.mobStats;

                return;
            }

            this.displaySlot.actionPerformed(guiButton);
        }
    }


    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (this.displaySlot != null) {
            this.displaySlot.handleMouseInput();
        }
    }

    static FontRenderer method_67a8da4dc8a72a6d62b3(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    public static StatFileWriter access$100(GuiStats guiStats) {
        return guiStats.atg;
    }

    static FontRenderer access$000(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    static void access$1500(GuiStats guiStats, int i, int j, Item item) {
        guiStats.drawStatsScreen(i, j, item);
    }

    public GuiStats(GuiScreen guiScreen, StatFileWriter atgValue) {
        this.parentScreen = guiScreen;
        this.atg = atgValue;
    }

    static FontRenderer method_0ebf035ab3137d8a150c(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    private void drawButtonBackground(int i, int j) {
        this.drawSprite(i, j, 0, 0);
    }

    static FontRenderer access$1900(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    static FontRenderer access$300(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    @Override
    public void initGui() {
        this.screenTitle = I18n.format("gui.stats", new Object[0]);
        this.doesGuiPauseGame = true;
        this.mc
                .getNetworkHandler()
                .addToSendQueue(
                        new C16PacketClientStatus(C16PacketClientStatusEnumState.REQUEST_STATS));
    }

    static FontRenderer access$1800(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    static void method_cbd02ac2e0461207835b(
            GuiStats guiStats, double d0, double d1, double d2, double d3, int i, int j) {
        guiStats.drawGradientRect(d0, d1, d2, d3, i, j);
    }

    static FontRenderer method_e155aae9999a3100be3b(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return !this.doesGuiPauseGame;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    static FontRenderer method_0698556f3b5f6b9e7f8e(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    static FontRenderer access$200(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    static FontRenderer access$2100(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    private void drawStatsScreen(int i, int j, Item item) {
        this.drawButtonBackground(i + 1, j + 1);
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        this.itemRender.renderItemModelIntoGUI(new ItemStack(item, 1, 0), i + 2, j + 2);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }

    static FontRenderer method_59f8adf188812b0ee5f1(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    private void drawSprite(int i, int j, int k, int l) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.Gui.statIcons);

        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(
                i + 0, j + 18, this.zLevel, (k + 0) * 0.0078125F, (l + 18) * 0.0078125F);
        worldRenderer.addVertexWithUV(
                i + 18, j + 18, this.zLevel, (k + 18) * 0.0078125F, (l + 18) * 0.0078125F);
        worldRenderer.addVertexWithUV(
                i + 18, j + 0, this.zLevel, (k + 18) * 0.0078125F, (l + 0) * 0.0078125F);
        worldRenderer.addVertexWithUV(
                i + 0, j + 0, this.zLevel, (k + 0) * 0.0078125F, (l + 0) * 0.0078125F);
        instance.draw();
    }

    static FontRenderer method_bcc5bf2a4a47a9926a6e(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    static FontRenderer access$2000(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }

    static FontRenderer method_fbaa1a1dbafd33505ab4(GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
}
