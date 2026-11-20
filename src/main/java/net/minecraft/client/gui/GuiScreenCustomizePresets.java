package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.ChunkProviderSettingsFactory;
import net.minecraft.client.gui.GuiScreen;

import com.google.common.collect.Lists;

import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.List;

public class GuiScreenCustomizePresets extends GuiScreen {
    private GuiButton aow;
    protected String ki = "Customize World Presets";
    private GuiScreenCustomizePresetsListPreset aox;

    private static List yP;
    private String zc;
    private GuiTextField xK;

    private GuiCustomizeWorldScreen aoz;

    private String yz;

    static {
        yP = Lists.newArrayList();
        ChunkProviderSettingsFactory class_a2e6caf2addfa25f41dd =
                ChunkProviderSettingsFactory.jsonToFactory(
                        "{ \"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":8.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":0.5, \"biomeScaleWeight\":2.0, \"biomeScaleOffset\":0.375, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":255 }");
        ResourceLocation class_cea9c5a2d35fea90a902 =
                new ResourceLocation("textures/gui/presets/water.png");
        yP.add(
                new GuiScreenCustomizePresetsInfo(
                        I18n.format(
                                "createWorld.customize.custom.preset.waterWorld", new Object[0]),
                        class_cea9c5a2d35fea90a902,
                        class_a2e6caf2addfa25f41dd));
        class_a2e6caf2addfa25f41dd =
                ChunkProviderSettingsFactory.jsonToFactory(
                        "{\"coordinateScale\":3000.0, \"heightScale\":6000.0, \"upperLimitScale\":250.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
        class_cea9c5a2d35fea90a902 = new ResourceLocation("textures/gui/presets/isles.png");
        yP.add(
                new GuiScreenCustomizePresetsInfo(
                        I18n.format("createWorld.customize.custom.preset.isleLand", new Object[0]),
                        class_cea9c5a2d35fea90a902,
                        class_a2e6caf2addfa25f41dd));
        class_a2e6caf2addfa25f41dd =
                ChunkProviderSettingsFactory.jsonToFactory(
                        "{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":5.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":1.0, \"biomeScaleWeight\":4.0, \"biomeScaleOffset\":1.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
        class_cea9c5a2d35fea90a902 = new ResourceLocation("textures/gui/presets/delight.png");
        yP.add(
                new GuiScreenCustomizePresetsInfo(
                        I18n.format(
                                "createWorld.customize.custom.preset.caveDelight", new Object[0]),
                        class_cea9c5a2d35fea90a902,
                        class_a2e6caf2addfa25f41dd));
        class_a2e6caf2addfa25f41dd =
                ChunkProviderSettingsFactory.jsonToFactory(
                        "{\"coordinateScale\":738.41864, \"heightScale\":157.69133, \"upperLimitScale\":801.4267, \"lowerLimitScale\":1254.1643, \"depthNoiseScaleX\":374.93652, \"depthNoiseScaleZ\":288.65228, \"depthNoiseScaleExponent\":1.2092624, \"mainNoiseScaleX\":1355.9908, \"mainNoiseScaleY\":745.5343, \"mainNoiseScaleZ\":1183.464, \"baseSize\":1.8758626, \"stretchY\":1.7137525, \"biomeDepthWeight\":1.7553768, \"biomeDepthOffset\":3.4701107, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":2.535211, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
        class_cea9c5a2d35fea90a902 = new ResourceLocation("textures/gui/presets/madness.png");
        yP.add(
                new GuiScreenCustomizePresetsInfo(
                        I18n.format("createWorld.customize.custom.preset.mountains", new Object[0]),
                        class_cea9c5a2d35fea90a902,
                        class_a2e6caf2addfa25f41dd));
        class_a2e6caf2addfa25f41dd =
                ChunkProviderSettingsFactory.jsonToFactory(
                        "{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":1000.0, \"mainNoiseScaleY\":3000.0, \"mainNoiseScaleZ\":1000.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":20 }");
        class_cea9c5a2d35fea90a902 = new ResourceLocation("textures/gui/presets/drought.png");
        yP.add(
                new GuiScreenCustomizePresetsInfo(
                        I18n.format("createWorld.customize.custom.preset.drought", new Object[0]),
                        class_cea9c5a2d35fea90a902,
                        class_a2e6caf2addfa25f41dd));
        class_a2e6caf2addfa25f41dd =
                ChunkProviderSettingsFactory.jsonToFactory(
                        "{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":2.0, \"lowerLimitScale\":64.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":6 }");
        class_cea9c5a2d35fea90a902 = new ResourceLocation("textures/gui/presets/chaos.png");
        yP.add(
                new GuiScreenCustomizePresetsInfo(
                        I18n.format("createWorld.customize.custom.preset.caveChaos", new Object[0]),
                        class_cea9c5a2d35fea90a902,
                        class_a2e6caf2addfa25f41dd));
        class_a2e6caf2addfa25f41dd =
                ChunkProviderSettingsFactory.jsonToFactory(
                        "{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":true, \"seaLevel\":40 }");
        class_cea9c5a2d35fea90a902 = new ResourceLocation("textures/gui/presets/luck.png");
        yP.add(
                new GuiScreenCustomizePresetsInfo(
                        I18n.format("createWorld.customize.custom.preset.goodLuck", new Object[0]),
                        class_cea9c5a2d35fea90a902,
                        class_a2e6caf2addfa25f41dd));
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        switch (guiButton.id) {
            case 0:
                this.aoz.method_be01d1eb5e587c583ed6(this.xK.getText());
                this.mc.displayGuiScreen(this.aoz);
                if (astring == null) {
                    break;
                }
            case 1:
                this.mc.displayGuiScreen(this.aoz);
        }
    }

    public void updateScreen() {
        this.xK.updateCursorCounter();
        super.updateScreen();
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.aox.handleMouseInput();
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        this.xK.mouseClicked(i, j, k);
        super.mouseClicked(i, j, k);
    }

    protected void keyTyped(char c0, int i) throws IOException {
        if (!this.xK.textboxKeyTyped(c0, i)) {
            super.keyTyped(c0, i);
        }
    }

    static GuiScreenCustomizePresetsListPreset access$100(
            GuiScreenCustomizePresets guiScreenCustomizePresets) {
        return guiScreenCustomizePresets.aox;
    }

    public void method_8d9b536bcdc9991c48fb() {
        this.aow.enabled = this.method_b417d7500ec59d69e373();
    }

    static GuiTextField access$200(GuiScreenCustomizePresets guiScreenCustomizePresets) {
        return guiScreenCustomizePresets.xK;
    }

    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.ki = I18n.format("createWorld.customize.custom.presets.title", new Object[0]);
        this.yz = I18n.format("createWorld.customize.presets.share", new Object[0]);
        this.zc = I18n.format("createWorld.customize.presets.list", new Object[0]);
        this.xK = new GuiTextField(2, this.fontRendererObj, 50, 40, this.width - 100, 20);
        this.aox = new GuiScreenCustomizePresetsListPreset(this);
        this.xK.setMaxStringLength(2000);
        this.xK.setText(this.aoz.method_c60693f457aa70be7160());
        this.buttonList.add(
                this.aow =
                        new GuiButton(
                                0,
                                this.width / 2 - 102,
                                this.height - 27,
                                100,
                                20,
                                I18n.format(
                                        "createWorld.customize.presets.select", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 + 3,
                        this.height - 27,
                        100,
                        20,
                        I18n.format("gui.cancel", new Object[0])));
        this.method_8d9b536bcdc9991c48fb();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    private boolean method_b417d7500ec59d69e373() {
        return this.aox.x > -1 && this.aox.x < yP.size() || this.xK.getText().length() > 1;
    }

    static List access$000() {
        return yP;
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.aox.drawScreen(i, j, f);
        this.drawCenteredString(this.fontRendererObj, this.ki, this.width / 2, 8, 16777215);
        this.drawString(this.fontRendererObj, this.yz, 50, 30, 10526880);
        this.drawString(this.fontRendererObj, this.zc, 50, 70, 10526880);
        this.xK.drawTextBox();
        super.drawScreen(i, j, f);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public GuiScreenCustomizePresets(GuiCustomizeWorldScreen aozValue) {
        super();
        this.aoz = aozValue;
    }
}
