package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomColormap implements CustomColorizer {
    private static String[] packedTextFragments;
    public static String Cs;
    private int format;
    private float[][] colorsRgb;
    private int color;
    public static String s;
    private static final int jM = 1;
    public String name = null;
    private int[] colors;
    public String basePath = null;
    private int yOffset;
    private static final int rC = 0;
    public static String Cv;
    private MatchBlock[] matchBlocks;
    private String source;
    private int height;
    public static String Cx;

    public static String oF;
    public static String Ba;
    private static final int rW = -1;
    private static final int rz = 2;
    private int yVariance;
    private int width;

    private MatchBlock getMatchBlock(int i) {
        int j = ReflectorMethod.f();
        if (this.matchBlocks == null) {
            return null;
        }

        int k = 0;

        while (k < this.matchBlocks.length) {
            MatchBlock matchBlock = this.matchBlocks[k];
            if (matchBlock.getBlockId() == i) {
                return matchBlock;
            }

            k++;
            if (j != 0) {
                break;
            }
        }

        return null;
    }

    private static String parseTexture(String text, String textValue, String textValue2) {
        if (text != null) {
            String textValue3 = ".png";
            if (text.endsWith(textValue3)) {
                text = text.substring(0, text.length() - textValue3.length());
            }

            return fixTextureName(text, textValue2);
        } else {
            String textValue4 = textValue;
            int i = textValue.lastIndexOf(47);
            if (i >= 0) {
                textValue4 = textValue.substring(i + 1);
            }

            int j = textValue4.lastIndexOf(46);
            if (j >= 0) {
                textValue4 = textValue4.substring(0, j);
            }

            return fixTextureName(textValue4, textValue2);
        }
    }

    // region String decoding support

    // endregion

    public int getColor(BiomeGenBase biomeGenBase, BlockPos blockPos) {
        return this.format == 0
                ? this.getColorVanilla(biomeGenBase, blockPos)
                : (this.format == 1 ? this.getColorGrid(biomeGenBase, blockPos) : this.color);
    }

    public int getLength() {
        return this.format == 2 ? 1 : this.colors.length;
    }

    private int parseFormat(String text) {
        if (text == null) {
            return 0;
        }

        if (text.equals("vanilla")) {
            return 0;
        }

        if (text.equals("grid")) {
            return 1;
        }

        if (text.equals("fixed")) {
            return 2;
        }

        warn("Unknown format: " + text);
        return -1;
    }

    public int getColorSmooth(IBlockAccess iBlockAccess, double d0, double d1, double d2, int i) {
        int j = ReflectorMethod.f();
        if (this.format == 2) {
            return this.color;
        }

        int k = MathHelper.floor(d0);
        int l = MathHelper.floor(d1);
        int i1 = MathHelper.floor(d2);
        int j1 = 0;
        int k1 = 0;
        int l1 = 0;
        int i2 = 0;
        BlockPosM blockPosM = new BlockPosM(0, 0, 0);
        int j2 = k - i;

        label25:
        while (j2 <= k + i) {
            int k2 = i1 - i;

            while (true) {
                if (k2 <= i1 + i) {
                    blockPosM.setXyz(j2, l, k2);
                    int l2 = this.getColor(iBlockAccess, blockPosM);
                    j1 += l2 >> 16 & 0xFF;
                    k1 += l2 >> 8 & 0xFF;
                    l1 += l2 & 0xFF;
                    i2++;
                    k2++;
                    if (j == 0) {
                        continue;
                    }
                }

                j2++;
                if (j != 0) {
                    break label25;
                }
                break;
            }
        }

        j2 = j1 / i2;
        int i3 = k1 / i2;
        int j3 = l1 / i2;
        return j2 << 16 | i3 << 8 | j3;
    }

    public boolean isValid(String text) {
        int i = ReflectorMethod.f();
        if (this.format != 0 && this.format != 1) {
            if (this.format != 2) {
                return false;
            }

            if (this.color >= 0) {
                return true;
            }

            this.color = 16777215;
            if (i == 0) {
                return true;
            }
        }

        if (this.source == null) {
            warn("Source not defined: " + text);
            return false;
        }

        this.readColors();
        if (this.colors == null) {
            return false;
        }

        if (this.color < 0) {
            if (this.format == 0) {
                this.color = this.getColor(127, 127);
            }

            if (this.format == 1) {
                this.color = this.getColorGrid(BiomeGenBase.plains, new BlockPos(0, 64, 0));
            }
        }

        return true;
    }

    public float[][] getColorsRgb() {
        if (this.colorsRgb == null) {
            this.colorsRgb = optifine.CustomColormap.toRgb(this.colors);
        }

        return this.colorsRgb;
    }

    private static float[][] toRgb(int[] aint) {
        float[][] afloat = new float[aint.length][3];
        int l = ReflectorMethod.cR();
        int j = 0;
        int i = l;

        while (j < aint.length) {
            int k = aint[j];
            float fx = (k >> 16 & 0xFF) / 255.0F;
            float f1 = (k >> 8 & 0xFF) / 255.0F;
            float f2 = (k & 0xFF) / 255.0F;
            float[] afloat1 = afloat[j];
            afloat1[0] = fx;
            afloat1[1] = f1;
            afloat1[2] = f2;
            j++;
            if (i == 0) {
                break;
            }
        }

        return afloat;
    }

    private static String fixTextureName(String text, String textValue) {
        text = TextureUtils.fixResourcePath(text, textValue);
        if (!text.startsWith(textValue)
                && !text.startsWith("textures/")
                && !text.startsWith("mcpatcher/")) {
            text = textValue + packedTextFragments[3] + text;
        }

        if (text.endsWith(".png")) {
            text = text.substring(0, text.length() - 4);
        }

        String textValue2 = "textures/blocks/";
        if (text.startsWith(textValue2)) {
            text = text.substring(textValue2.length());
        }

        if (text.startsWith(packedTextFragments[4])) {
            text = text.substring(1);
        }

        return text;
    }

    private static void warn(String text) {
        OptiFineConfig.warn("CustomColors: " + text);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void method_98d37317de9aef781741(int i, int j) {
        int l = ReflectorMethod.f();
        MatchBlock matchBlock = this.getMatchBlock(i);
        int k = l;
        if (matchBlock != null) {
            if (j < 0) {
                return;
            }

            matchBlock.a(j);
            if (k == 0) {
                return;
            }
        }

        this.addMatchBlock(new MatchBlock(i, j));
    }

    public boolean isColorConstant() {
        return this.format == 2;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    private static void dbg(String text) {
        OptiFineConfig.dbg("CustomColors: " + text);
    }

    public boolean isValidMatchBlocks(String text) {
        if (this.matchBlocks == null) {
            this.matchBlocks = this.detectMatchBlocks();
            if (this.matchBlocks == null) {
                warn("Match blocks not defined: " + text);
                return false;
            }
        }

        return true;
    }

    public int getColor(IBlockAccess iBlockAccess, BlockPos blockPos) {
        net.minecraft.world.biome.BiomeGenBase biomeGenBase = optifine.CustomColors.getColorBiome(iBlockAccess, blockPos);
        return this.getColor(biomeGenBase, blockPos);
    }

    public int getColor(int p171_0) {
        p171_0 = optifine.OptiFineConfig.limit(p171_0, 0, this.colors.length);
        return this.colors[p171_0] & 16777215;
    }

    private int getColorVanilla(BiomeGenBase biomeGenBase, BlockPos blockPos) {
        double d0 = MathHelper.clamp(biomeGenBase.getFloatTemperature(blockPos), 0.0F, 1.0F);
        double d1 = MathHelper.clamp(biomeGenBase.getFloatRainfall(), 0.0F, 1.0F);
        d1 *= d0;
        int i = (int) ((1.0 - d0) * (this.width - 1));
        int j = (int) ((1.0 - d1) * (this.height - 1));
        return this.getColor(i, j);
    }

    private void readColors() {
        try {
            this.colors = null;
            if (this.source == null) {
                return;
            }

            String sx = this.source + ".png";
            ResourceLocation resourceLocation = new ResourceLocation(sx);
            InputStream inputstream = OptiFineConfig.getResourceStream(resourceLocation);
            if (inputstream == null) {
                return;
            }

            BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
            if (bufferedimage == null) {
                return;
            }

            int width = bufferedimage.getWidth();
            int j = bufferedimage.getHeight();
            int k = this.width >= 0 && this.width != width ? 0 : 1;
            int l = this.height >= 0 && this.height != j ? 0 : 1;
            if (k == 0 || l == 0) {
                dbg(
                        "Non-standard palette size: "
                                + width
                                + packedTextFragments[0]
                                + j
                                + ", should be: "
                                + this.width
                                + packedTextFragments[1]
                                + this.height
                                + ", path: "
                                + sx);
            }

            this.width = width;
            this.height = j;
            if (this.width <= 0 || this.height <= 0) {
                warn(
                        "Invalid palette size: "
                                + width
                                + packedTextFragments[2]
                                + j
                                + ", path: "
                                + sx);
                return;
            }

            this.colors = new int[width * j];
            bufferedimage.getRGB(0, 0, width, j, this.colors, 0, width);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    // region String decoding support

    // endregion

    public CustomColormap(Properties properties, String text, int widthValue, int j) {
        this.format = -1;
        this.matchBlocks = null;
        this.source = null;
        this.color = -1;
        this.yVariance = 0;
        this.yOffset = 0;
        this.width = 0;
        this.height = 0;
        this.colors = null;
        this.colorsRgb = (float[][]) null;
        ConnectedParser connectedParser = new ConnectedParser("Colormap");
        this.name = connectedParser.parseName(text);
        this.basePath = connectedParser.parseBasePath(text);
        this.format = this.parseFormat(properties.getProperty("format"));
        this.matchBlocks = connectedParser.parseMatchBlocks(properties.getProperty("blocks"));
        this.source = parseTexture(properties.getProperty("source"), text, this.basePath);
        this.color =
                ConnectedParser.parseColor(properties.getProperty("color"), -1);
        this.yVariance = connectedParser.parseIntOrDefault(properties.getProperty("yVariance"), 0);
        this.yOffset = connectedParser.parseIntOrDefault(properties.getProperty("yOffset"), 0);
        this.width = widthValue;
        this.height = j;
    }

    private int getColorGrid(BiomeGenBase biomeGenBase, BlockPos blockPos) {
        int aLv = biomeGenBase.biomeID;
        int j = blockPos.getY() - this.yOffset;
        if (this.yVariance > 0) {
            int k = blockPos.getX() << 16 + blockPos.getZ();
            int l = OptiFineConfig.intHash(k);
            int i1 = this.yVariance * 2 + 1;
            int j1 = (l & 0xFF) % i1 - this.yVariance;
            j += j1;
        }

        return this.getColor(aLv, j);
    }

    private MatchBlock[] detectMatchBlocks() {
        Block block = Block.getBlockFromName(this.name);
        if (block != null) {
            return new MatchBlock[] {new MatchBlock(Block.getIdFromBlock(block))};
        }

        Pattern pattern = Pattern.compile("^block([0-9]+).*$");
        Matcher matcher = pattern.matcher(this.name);
        if (matcher.matches()) {
            String sx = matcher.group(1);
            int i = OptiFineConfig.parseInt(sx, -1);
            if (i >= 0) {
                return new MatchBlock[] {new MatchBlock(i)};
            }
        }

        ConnectedParser connectedParser = new ConnectedParser("Colormap");
        MatchBlock[] matchBlockArray = connectedParser.parseMatchBlock(this.name);
        return matchBlockArray != null ? matchBlockArray : null;
    }

    public int getColor(int p171_0, int p171_1) {
        p171_0 = optifine.OptiFineConfig.limit(p171_0, 0, this.width - 1);
        p171_1 = optifine.OptiFineConfig.limit(p171_1, 0, this.height - 1);
        return this.colors[p171_1 * this.width + p171_0] & 16777215;
    }

    public boolean matchesBlock(BlockStateBase blockStateBase) {
        return optifine.Matches.block(blockStateBase, this.matchBlocks);
    }

    @Override
    public String toString() {
        return packedTextFragments[5]
                + this.basePath
                + packedTextFragments[6]
                + this.name
                + ", blocks: "
                + OptiFineConfig.arrayToString(this.matchBlocks)
                + ", source: "
                + this.source;
    }

    public int[] kS() {
        int i = optifine.ReflectorMethod.cR();
        if (this.matchBlocks == null) {
            return null;
        }

        java.util.HashSet hashset = new java.util.HashSet();
        int j = 0;

        while (j < this.matchBlocks.length) {
            optifine.MatchBlock matchBlock = this.matchBlocks[j];
            if (matchBlock.getBlockId() >= 0) {
                hashset.add(matchBlock.getBlockId());
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        java.lang.Integer[] ainteger =
                ((java.lang.Integer[])
                        ((java.lang.Object[])
                                (((java.util.HashSet) (hashset))
                                        .toArray(
                                                ((java.lang.Object[])
                                                        (new java.lang.Integer
                                                                [hashset.size()]))))));
        int[] aint = new int[ainteger.length];
        int k = 0;

        while (k < ainteger.length) {
            aint[k] = ainteger[k];
            k++;
            if (i == 0) {
                break;
            }
        }

        return aint;
    }

    public void addMatchBlock(MatchBlock matchBlock) {
        if (this.matchBlocks == null) {
            this.matchBlocks = new optifine.MatchBlock[0];
        }

        this.matchBlocks =
                (optifine.MatchBlock[])
                        optifine.OptiFineConfig.addObjectToArray(this.matchBlocks, matchBlock);
    }

    public int getColorRandom() {
        if (this.format == 2) {
            return this.color;
        }

        int i = optifine.CustomColors.random.nextInt(this.colors.length);
        return this.colors[i];
    }

    static {
        optifine.CustomColormap.packedTextFragments = new String[9];
        optifine.CustomColormap.packedTextFragments[0] = new String("x");
        optifine.CustomColormap.packedTextFragments[1] = new String("x");
        optifine.CustomColormap.packedTextFragments[2] = new String("x");
        optifine.CustomColormap.packedTextFragments[3] = new String("/");
        optifine.CustomColormap.packedTextFragments[4] = new String("/");
        optifine.CustomColormap.packedTextFragments[5] = new String("");
        optifine.CustomColormap.packedTextFragments[6] = new String("/");
        s = "source";
        Ba = "format";
        oF = "blocks";
        Cv = "color";
        Cx = "yOffset";
        Cs = "yVariance";
    }
}
