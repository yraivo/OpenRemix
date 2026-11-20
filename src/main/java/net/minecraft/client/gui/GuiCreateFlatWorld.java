package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiCreateFlatWorld extends GuiScreen {
    private String jT;

    private String flatWorldTitle;
    private GuiCreateWorld createWorldGui;

    private GuiCreateFlatWorldDetails createFlatWorldListSlotGui;
    private GuiButton anZ;
    private String jU;
    private GuiButton apD;
    private FlatGeneratorInfo theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
    private GuiButton anU;

    static FlatGeneratorInfo access$400(GuiCreateFlatWorld guiCreateFlatWorld) {
        return guiCreateFlatWorld.theFlatGeneratorInfo;
    }


    public GuiCreateFlatWorld(GuiCreateWorld apAValue, String text) {
        this.createWorldGui = apAValue;
        this.method_be01d1eb5e587c583ed6(text);
    }

    public void initGui() {
        this.buttonList.clear();
        this.flatWorldTitle = I18n.format("createWorld.customize.flat.title", new Object[0]);
        this.jU = I18n.format("createWorld.customize.flat.tile", new Object[0]);
        this.jT = I18n.format("createWorld.customize.flat.height", new Object[0]);
        this.createFlatWorldListSlotGui = new GuiCreateFlatWorldDetails(this);
        this.buttonList.add(
                this.anZ =
                        new GuiButton(
                                2,
                                this.width / 2 - 154,
                                this.height - 52,
                                100,
                                20,
                                I18n.format("createWorld.customize.flat.addLayer", new Object[0])
                                        + " (NYI)"));
        this.buttonList.add(
                this.anU =
                        new GuiButton(
                                3,
                                this.width / 2 - 50,
                                this.height - 52,
                                100,
                                20,
                                I18n.format("createWorld.customize.flat.editLayer", new Object[0])
                                        + " (NYI)"));
        this.buttonList.add(
                this.apD =
                        new GuiButton(
                                4,
                                this.width / 2 - 155,
                                this.height - 52,
                                150,
                                20,
                                I18n.format(
                                        "createWorld.customize.flat.removeLayer", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 - 155,
                        this.height - 28,
                        150,
                        20,
                        I18n.format("gui.done", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        5,
                        this.width / 2 + 5,
                        this.height - 52,
                        150,
                        20,
                        I18n.format("createWorld.customize.presets", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 + 5,
                        this.height - 28,
                        150,
                        20,
                        I18n.format("gui.cancel", new Object[0])));
        this.anZ.visible = this.anU.visible = false;
        this.theFlatGeneratorInfo.l();
        this.ha();
    }

    public String method_c60693f457aa70be7160() {
        return this.theFlatGeneratorInfo.toString();
    }

    public void ha() {
        boolean enabled = this.dL();
        this.apD.enabled = enabled;
        this.anU.enabled = enabled;
        this.anU.enabled = false;
        this.anZ.enabled = false;
    }

    public void method_be01d1eb5e587c583ed6(String text) {
        this.theFlatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(text);
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.createFlatWorldListSlotGui.drawScreen(i, j, f);
        this.drawCenteredString(
                this.fontRendererObj, this.flatWorldTitle, this.width / 2, 8, 16777215);
        int k = this.width / 2 - 92 - 16;
        this.drawString(this.fontRendererObj, this.jU, k, 32, 16777215);
        this.drawString(
                this.fontRendererObj,
                this.jT,
                k + 2 + 213 - this.fontRendererObj.getStringWidth(this.jT),
                32,
                16777215);
        super.drawScreen(i, j, f);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    private boolean dL() {
        return this.createFlatWorldListSlotGui.x > -1
                && this.createFlatWorldListSlotGui.x
                        < this.theFlatGeneratorInfo.getFlatLayers().size();
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.createFlatWorldListSlotGui.handleMouseInput();
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        label36:
        {
            String[] astring1 = Gui.getLegacyGuiRenderMarker();
            int i =
                    this.theFlatGeneratorInfo.getFlatLayers().size()
                            - this.createFlatWorldListSlotGui.x
                            - 1;
            String[] astring = astring1;
            if (guiButton.id == 1) {
                this.mc.displayGuiScreen(this.createWorldGui);
                if (astring == null) {
                    break label36;
                }
            }

            if (guiButton.id == 0) {
                this.createWorldGui.chunkProviderSettingsJson = this.method_c60693f457aa70be7160();
                this.mc.displayGuiScreen(this.createWorldGui);
                if (astring == null) {
                    break label36;
                }
            }

            if (guiButton.id == 5) {
                this.mc.displayGuiScreen(new GuiFlatPresets(this));
                if (astring == null) {
                    break label36;
                }
            }

            if (guiButton.id == 4 && this.dL()) {
                this.theFlatGeneratorInfo.getFlatLayers().remove(i);
                this.createFlatWorldListSlotGui.x =
                        Math.min(
                                this.createFlatWorldListSlotGui.x,
                                this.theFlatGeneratorInfo.getFlatLayers().size() - 1);
            }
        }

        this.theFlatGeneratorInfo.l();
        this.ha();
    }
}
