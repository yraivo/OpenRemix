package net.minecraft.client.gui;

import net.minecraft.block.BlockTallGrassEnumType;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.client.gui.GuiScreen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GuiFlatPresets extends GuiScreen {
    private GuiCreateFlatWorld parentScreen;
    private String presetsShare;

    private static List FLAT_WORLD_PRESETS;
    private String jT;

    private String presetsTitle;
    private GuiTextField aqc;
    private GuiFlatPresetsListSlot aqd;
    private GuiButton anZ;

    protected void mouseClicked(int i, int j, int k) throws IOException {
        this.aqc.mouseClicked(i, j, k);
        super.mouseClicked(i, j, k);
    }

    public void updateScreen() {
        this.aqc.updateCursorCounter();
        super.updateScreen();
    }

    private static void method_5ecf8064405c2845ab35(
            String text,
            Item item,
            BiomeGenBase biomeGenBase,
            FlatLayerInfo... flatLayerInfoArray) {
        method_1e741ddd197dd8d7cb51(text, item, 0, biomeGenBase, (List) null, flatLayerInfoArray);
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.id == 0 && this.method_b417d7500ec59d69e373()) {
            this.parentScreen.method_be01d1eb5e587c583ed6(this.aqc.getText());
            this.mc.displayGuiScreen(this.parentScreen);
            if (astring == null) {
                return;
            }
        }

        if (guiButton.id == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    protected void keyTyped(char c0, int i) throws IOException {
        if (!this.aqc.textboxKeyTyped(c0, i)) {
            super.keyTyped(c0, i);
        }
    }

    static {
        net.minecraft.client.gui.GuiFlatPresets.FLAT_WORLD_PRESETS = Lists.newArrayList();
        method_24b5fcc687fb65181fec(
                "Classic Flat",
                Item.getItemFromBlock(Blocks.grass),
                BiomeGenBase.plains,
                Arrays.asList("village"),
                new FlatLayerInfo(1, Blocks.grass),
                new FlatLayerInfo(2, Blocks.dirt),
                new FlatLayerInfo(1, Blocks.bedrock));
        method_24b5fcc687fb65181fec(
                "Tunnelers' Dream",
                Item.getItemFromBlock(Blocks.stone),
                BiomeGenBase.extremeHills,
                Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"),
                new FlatLayerInfo(1, Blocks.grass),
                new FlatLayerInfo(5, Blocks.dirt),
                new FlatLayerInfo(230, Blocks.stone),
                new FlatLayerInfo(1, Blocks.bedrock));
        method_24b5fcc687fb65181fec(
                "Water World",
                Items.water_bucket,
                BiomeGenBase.deepOcean,
                Arrays.asList("biome_1", "oceanmonument"),
                new FlatLayerInfo(90, Blocks.water),
                new FlatLayerInfo(5, Blocks.sand),
                new FlatLayerInfo(5, Blocks.dirt),
                new FlatLayerInfo(5, Blocks.stone),
                new FlatLayerInfo(1, Blocks.bedrock));
        method_1e741ddd197dd8d7cb51(
                "Overworld",
                Item.getItemFromBlock(Blocks.tallgrass),
                BlockTallGrassEnumType.GRASS.getMeta(),
                BiomeGenBase.plains,
                Arrays.asList(
                        "village",
                        "biome_1",
                        "decoration",
                        "stronghold",
                        "mineshaft",
                        "dungeon",
                        "lake",
                        "lava_lake"),
                new FlatLayerInfo(1, Blocks.grass),
                new FlatLayerInfo(3, Blocks.dirt),
                new FlatLayerInfo(59, Blocks.stone),
                new FlatLayerInfo(1, Blocks.bedrock));
        method_24b5fcc687fb65181fec(
                "Snowy Kingdom",
                Item.getItemFromBlock(Blocks.snow_layer),
                BiomeGenBase.icePlains,
                Arrays.asList("village", "biome_1"),
                new FlatLayerInfo(1, Blocks.snow_layer),
                new FlatLayerInfo(1, Blocks.grass),
                new FlatLayerInfo(3, Blocks.dirt),
                new FlatLayerInfo(59, Blocks.stone),
                new FlatLayerInfo(1, Blocks.bedrock));
        method_24b5fcc687fb65181fec(
                "Bottomless Pit",
                Items.feather,
                BiomeGenBase.plains,
                Arrays.asList("village", "biome_1"),
                new FlatLayerInfo(1, Blocks.grass),
                new FlatLayerInfo(3, Blocks.dirt),
                new FlatLayerInfo(2, Blocks.cobblestone));
        method_24b5fcc687fb65181fec(
                "Desert",
                Item.getItemFromBlock(Blocks.sand),
                BiomeGenBase.desert,
                Arrays.asList(
                        "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"),
                new FlatLayerInfo(8, Blocks.sand),
                new FlatLayerInfo(52, Blocks.sandstone),
                new FlatLayerInfo(3, Blocks.stone),
                new FlatLayerInfo(1, Blocks.bedrock));
        method_5ecf8064405c2845ab35(
                "Redstone Ready",
                Items.redstone,
                BiomeGenBase.desert,
                new FlatLayerInfo(52, Blocks.sandstone),
                new FlatLayerInfo(3, Blocks.stone),
                new FlatLayerInfo(1, Blocks.bedrock));
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.aqd.drawScreen(i, j, f);
        this.drawCenteredString(
                this.fontRendererObj, this.presetsTitle, this.width / 2, 8, 16777215);
        this.drawString(this.fontRendererObj, this.presetsShare, 50, 30, 10526880);
        this.drawString(this.fontRendererObj, this.jT, 50, 70, 10526880);
        this.aqc.drawTextBox();
        super.drawScreen(i, j, f);
    }

    public void method_8d9b536bcdc9991c48fb() {
        boolean enabled = this.method_b417d7500ec59d69e373();
        this.anZ.enabled = enabled;
    }

    private static void method_24b5fcc687fb65181fec(
            String text,
            Item item,
            BiomeGenBase biomeGenBase,
            List list,
            FlatLayerInfo... flatLayerInfoArray) {
        method_1e741ddd197dd8d7cb51(text, item, 0, biomeGenBase, list, flatLayerInfoArray);
    }

    static GuiTextField access$600(GuiFlatPresets guiFlatPresets) {
        return guiFlatPresets.aqc;
    }

    public GuiFlatPresets(GuiCreateFlatWorld aqaValue) {
        this.parentScreen = aqaValue;
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    static List access$400() {
        return net.minecraft.client.gui.GuiFlatPresets.FLAT_WORLD_PRESETS;
    }

    static GuiFlatPresetsListSlot access$500(GuiFlatPresets guiFlatPresets) {
        return guiFlatPresets.aqd;
    }

    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.presetsTitle = I18n.format("createWorld.customize.presets.title", new Object[0]);
        this.presetsShare = I18n.format("createWorld.customize.presets.share", new Object[0]);
        this.jT = I18n.format("createWorld.customize.presets.list", new Object[0]);
        this.aqc = new GuiTextField(2, this.fontRendererObj, 50, 40, this.width - 100, 20);
        this.aqd = new GuiFlatPresetsListSlot(this);
        this.aqc.setMaxStringLength(1230);
        this.aqc.setText(this.parentScreen.method_c60693f457aa70be7160());
        this.buttonList.add(
                this.anZ =
                        new GuiButton(
                                0,
                                this.width / 2 - 155,
                                this.height - 28,
                                150,
                                20,
                                I18n.format(
                                        "createWorld.customize.presets.select", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 + 5,
                        this.height - 28,
                        150,
                        20,
                        I18n.format("gui.cancel", new Object[0])));
        this.method_8d9b536bcdc9991c48fb();
    }

    private static void method_1e741ddd197dd8d7cb51(
            String text,
            Item item,
            int i,
            BiomeGenBase biomeGenBase,
            List list,
            FlatLayerInfo... flatLayerInfoArray) {
        FlatGeneratorInfo flatGeneratorInfo = new FlatGeneratorInfo();

        for (int j = flatLayerInfoArray.length - 1; j >= 0; j--) {
            flatGeneratorInfo.getFlatLayers().add(flatLayerInfoArray[j]);
        }

        flatGeneratorInfo.setBiome(biomeGenBase.biomeID);
        flatGeneratorInfo.l();
        if (list != null) {
            for (java.util.Iterator<?> __recoveryIterator171_22370 = (list).iterator();
                    (__recoveryIterator171_22370).hasNext(); ) {
                String textValue = (String) (__recoveryIterator171_22370).next();
                flatGeneratorInfo.getWorldFeatures().put(textValue, Maps.newHashMap());
            }
        }

        net.minecraft.client.gui.GuiFlatPresets.FLAT_WORLD_PRESETS.add(
                new GuiFlatPresetsLayerItem(item, i, text, flatGeneratorInfo.toString()));
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.aqd.handleMouseInput();
    }

    private boolean method_b417d7500ec59d69e373() {
        return this.aqd.x > -1 && this.aqd.x < net.minecraft.client.gui.GuiFlatPresets.FLAT_WORLD_PRESETS.size()
                || this.aqc.getText().length() > 1;
    }
}
