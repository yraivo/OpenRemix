package optifine;

import net.minecraft.block.properties.IProperty;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import remix.util.math.MathUtils2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class ConnectedProperties {
    public int method;
    public int connect;
    public static final int oT = 128;
    public int[] sumWeights;
    public static final int B = 5;
    public static final int lD = 60;
    public static final int lF = 4;
    public static final int H = 1;
    public static final int BU = 16;
    public static final int y = 2;
    public String name = null;
    public BiomeGenBase[] biomes;
    public static final int xZ = 9;

    public static final int kh = 3;
    public static final int V = 2;
    public static final int BX = 63;
    public static final int BY = 6;
    public String basePath = null;
    public static final int nc = 1;
    public TextureAtlasSprite[] tileIcons;
    public static final int ni = 2;
    public int[] weights;
    public static final int T = 128;
    public static final int oU = 2;
    public String[] matchTiles;
    public boolean innerSeams;
    public TextureAtlasSprite[] matchTileIcons;
    public String[] tiles;
    public static final int x = 1;
    private static String[] packedTextFragments;
    public MatchBlock[] matchBlocks = null;
    public static final int A = 4;
    public int[] metadatas = null;
    public static final int yt = 0;
    public int height;
    public int sumAllWeights;
    public int minHeight;
    public static final int Ce = 32;
    public int renderPass;
    public static final int C = 6;
    public static final int L = 1;
    public static final int w = 0;
    public static final int Cf = 128;
    public int symmetry;
    public int maxHeight;

    public int faces;
    public static final int yK = 3;
    public static final int Ch = 8;
    public int width;
    public static final int E = 8;
    public static final int D = 7;

    private int getMax(int[] aint, int i) {
        int j = ReflectorMethod.cR();
        if (aint == null) {
            return i;
        }

        int k = 0;

        while (k < aint.length) {
            int l = aint[k];
            if (l > i) {
                i = l;
            }

            k++;
            if (j == 0) {
                break;
            }
        }

        return i;
    }

    public static IProperty getProperty(String text, Collection collection) {
        for (java.util.Iterator<?> typedIterator384 =
                        ((java.util.Iterator) (((java.util.Collection) (collection)).iterator()));
                ((boolean) (((java.util.Iterator) (typedIterator384)).hasNext())); ) {
            net.minecraft.block.properties.IProperty iProperty =
                    (net.minecraft.block.properties.IProperty)
                            ((java.lang.Object) (((java.util.Iterator) (typedIterator384)).next()));
            if (text.equals(iProperty.getName())) {
                return iProperty;
            }
        }

        return null;
    }

    private boolean isValidVertical(String text) {
        if (this.tiles == null) {
            OptiFineConfig.warn("No tiles defined for vertical: " + text);
            return false;
        } else if (this.tiles.length != 4) {
            OptiFineConfig.warn("Invalid tiles, must be exactly 4: " + text);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidHorizontalVertical(String text) {
        if (this.tiles == null) {
            OptiFineConfig.warn("No tiles defined for horizontal+vertical: " + text);
            return false;
        } else if (this.tiles.length != 7) {
            OptiFineConfig.warn("Invalid tiles, must be exactly 7: " + text);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidRandom(String text) {
        int i = ReflectorMethod.cR();
        if (this.tiles != null && this.tiles.length > 0) {
            if (this.weights != null) {
                if (this.weights.length > this.tiles.length) {
                    OptiFineConfig.warn(
                            "More weights defined than tiles, trimming weights: " + text);
                    int[] aint = new int[this.tiles.length];
                    System.arraycopy(this.weights, 0, aint, 0, aint.length);
                    this.weights = aint;
                }

                if (this.weights.length < this.tiles.length) {
                    OptiFineConfig.warn(
                            "Less weights defined than tiles, expanding weights: " + text);
                    int[] aint1 = new int[this.tiles.length];
                    System.arraycopy(this.weights, 0, aint1, 0, this.weights.length);
                    int j = MathUtils2.getAverage(this.weights);
                    int k = this.weights.length;

                    while (k < aint1.length) {
                        aint1[k] = j;
                        k++;
                        if (i == 0) {
                            break;
                        }
                    }

                    this.weights = aint1;
                }

                this.sumWeights = new int[this.weights.length];
                int sumAllWeightsValue = 0;
                int l = 0;

                while (l < this.weights.length) {
                    sumAllWeightsValue += this.weights[l];
                    this.sumWeights[l] = sumAllWeightsValue;
                    l++;
                    if (i == 0) {
                        break;
                    }
                }

                this.sumAllWeights = sumAllWeightsValue;
                if (this.sumAllWeights <= 0) {
                    OptiFineConfig.warn("Invalid sum of all weights: " + sumAllWeightsValue);
                    this.sumAllWeights = 1;
                }
            }

            return true;
        } else {
            OptiFineConfig.warn("Tiles not defined: " + text);
            return false;
        }
    }

    private static String method_d8a9960b65c6157c90f8(String text) {
        int i = text.lastIndexOf(47);
        return i < 0 ? optifine.ConnectedProperties.packedTextFragments[1] : text.substring(0, i);
    }

    private MatchBlock[] detectMatchBlocks() {
        int k = ReflectorMethod.cR();
        int[] aint = this.c();
        int i = k;
        if (aint == null) {
            return null;
        }

        MatchBlock[] matchBlockArray = new MatchBlock[aint.length];
        int j = 0;

        while (j < matchBlockArray.length) {
            matchBlockArray[j] = new MatchBlock(aint[j]);
            j++;
            if (i == 0) {
                break;
            }
        }

        return matchBlockArray;
    }

    static {
        optifine.ConnectedProperties.packedTextFragments = new String[8];
        optifine.ConnectedProperties.packedTextFragments[0] = new String(" ");
        optifine.ConnectedProperties.packedTextFragments[1] = new String("");
        optifine.ConnectedProperties.packedTextFragments[2] = new String("-");
        optifine.ConnectedProperties.packedTextFragments[3] = new String("-");
        optifine.ConnectedProperties.packedTextFragments[4] = new String("/");
        optifine.ConnectedProperties.packedTextFragments[5] = new String("/");
        optifine.ConnectedProperties.packedTextFragments[6] = new String("/");
    }

    private static int parseSymmetry(String text) {
        if (text == null) {
            return 1;
        }

        if (text.equals("opposite")) {
            return 2;
        }

        if (text.equals("all")) {
            return 6;
        }

        OptiFineConfig.warn("Unknown symmetry: " + text);
        return 1;
    }

    private boolean isValidHorizontal(String text) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("12-15");
        }

        if (this.tiles.length != 4) {
            OptiFineConfig.warn("Invalid tiles, must be exactly 4: " + text);
            return false;
        } else {
            return true;
        }
    }

    private String[] parseMatchTiles(String text) {
        int i = ReflectorMethod.cR();
        if (text == null) {
            return null;
        }

        String[] astring = OptiFineConfig.tokenize(text, packedTextFragments[0]);
        int j = 0;

        while (j < astring.length) {
            String textValue = astring[j];
            if (textValue.endsWith(".png")) {
                textValue = textValue.substring(0, textValue.length() - 4);
            }

            textValue = TextureUtils.fixResourcePath(textValue, this.basePath);
            astring[j] = textValue;
            j++;
            if (i == 0) {
                break;
            }
        }

        return astring;
    }

    private static String method_0442347320b0b56aa8a0(String text) {
        java.lang.String textValue = text;
        int i = text.lastIndexOf(47);
        if (i >= 0) {
            textValue = text.substring(i + 1);
        }

        int j = textValue.lastIndexOf(46);
        if (j >= 0) {
            textValue = textValue.substring(0, j);
        }

        return textValue;
    }

    private static int parseFaces(String text) {
        int i = ReflectorMethod.cR();
        if (text == null) {
            return 63;
        }

        String[] astring = OptiFineConfig.tokenize(text, " ,");
        int j = 0;
        int k = 0;

        while (k < astring.length) {
            String textValue = astring[k];
            int l = parseFace(textValue);
            j |= l;
            k++;
            if (i == 0) {
                break;
            }
        }

        return j;
    }

    private static TextureAtlasSprite[] registerIcons(String[] astring, TextureMap textureMap) {
        int i = ReflectorMethod.cR();
        if (astring == null) {
            return null;
        }

        ArrayList arrayList = new ArrayList();
        int j = 0;

        while (j < astring.length) {
            String text = astring[j];
            ResourceLocation resourceLocation = new ResourceLocation(text);
            String textValue = resourceLocation.getResourceDomain();
            String textValue2 = resourceLocation.getResourcePath();
            if (!textValue2.contains(packedTextFragments[6])) {
                textValue2 = "textures/blocks/" + textValue2;
            }

            String textValue3 = textValue2 + ".png";
            ResourceLocation resourceLocationValue = new ResourceLocation(textValue, textValue3);
            boolean hasResource = OptiFineConfig.hasResource(resourceLocationValue);
            if (!hasResource) {
                OptiFineConfig.warn("File not found: " + textValue3);
            }

            String textValue4 = "textures/";
            String textValue5 = textValue2;
            if (textValue2.startsWith(textValue4)) {
                textValue5 = textValue2.substring(textValue4.length());
            }

            ResourceLocation resourceLocationValue2 = new ResourceLocation(textValue, textValue5);
            TextureAtlasSprite textureAtlasSprite =
                    textureMap.registerSprite(resourceLocationValue2);
            arrayList.add(textureAtlasSprite);
            j++;
            if (i == 0) {
                break;
            }
        }

        return ((net.minecraft.client.renderer.texture.TextureAtlasSprite[])
                arrayList.toArray(new TextureAtlasSprite[arrayList.size()]));
    }

    private static int parseConnect(String text) {
        if (text == null) {
            return 0;
        }

        if (text.equals("block")) {
            return 1;
        }

        if (text.equals("tile")) {
            return 2;
        }

        if (text.equals("material")) {
            return 3;
        }

        OptiFineConfig.warn("Unknown connect: " + text);
        return 128;
    }

    public boolean matchesBiome(BiomeGenBase biomeGenBase) {
        return optifine.Matches.biome(biomeGenBase, this.biomes);
    }

    private int[] c() {
        int i = ReflectorMethod.f();
        if (!this.name.startsWith("block")) {
            return null;
        }

        int j = "block".length();
        int k = j;

        while (k < this.name.length()) {
            char c0 = this.name.charAt(k);
            if (c0 < '0' || c0 > '9' && i == 0) {
                break;
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        if (k == j) {
            return null;
        }

        String text = this.name.substring(j, k);
        int l = OptiFineConfig.parseInt(text, -1);
        return l < 0 ? null : new int[] {l};
    }

    private static int parseFace(String text) {
        text = text.toLowerCase();
        if (text.equals("bottom") || text.equals("down")) {
            return 1;
        }

        if (text.equals("top") || text.equals("up")) {
            return 2;
        }

        if (text.equals("north")) {
            return 4;
        }

        if (text.equals("south")) {
            return 8;
        }

        if (text.equals("east")) {
            return 32;
        }

        if (text.equals("west")) {
            return 16;
        }

        if (text.equals("sides")) {
            return 60;
        }

        if (text.equals("all")) {
            return 63;
        }

        OptiFineConfig.warn("Unknown face: " + text);
        return 128;
    }

    private boolean isValidFixed(String text) {
        if (this.tiles == null) {
            OptiFineConfig.warn("Tiles not defined: " + text);
            return false;
        } else if (this.tiles.length != 1) {
            OptiFineConfig.warn("Number of tiles should be 1 for method: fixed.");
            return false;
        } else {
            return true;
        }
    }

    public boolean isValid(String text) {
        if (this.name == null || this.name.length() <= 0) {
            OptiFineConfig.warn("No name found: " + text);
            return false;
        }

        if (this.basePath == null) {
            OptiFineConfig.warn("No base path found: " + text);
            return false;
        }

        if (this.matchBlocks == null) {
            this.matchBlocks = this.detectMatchBlocks();
        }

        if (this.matchTiles == null && this.matchBlocks == null) {
            this.matchTiles = this.detectMatchTiles();
        }

        if (this.matchBlocks == null && this.matchTiles == null) {
            OptiFineConfig.warn("No matchBlocks or matchTiles specified: " + text);
            return false;
        }

        if (this.method == 0) {
            OptiFineConfig.warn("No method: " + text);
            return false;
        }

        if (this.tiles != null && this.tiles.length > 0) {
            if (this.connect == 0) {
                this.connect = this.detectConnect();
            }

            if (this.connect == 128) {
                OptiFineConfig.warn("Invalid connect in: " + text);
                return false;
            }

            if (this.renderPass > 0) {
                OptiFineConfig.warn("Render pass not supported: " + this.renderPass);
                return false;
            }

            if ((this.faces & 128) != 0) {
                OptiFineConfig.warn("Invalid faces in: " + text);
                return false;
            }

            if ((this.symmetry & 128) != 0) {
                OptiFineConfig.warn("Invalid symmetry in: " + text);
                return false;
            }

            switch (this.method) {
                case 1:
                    return this.isValidCtm(text);
                case 2:
                    return this.isValidHorizontal(text);
                case 3:
                    return this.isValidTop(text);
                case 4:
                    return this.isValidRandom(text);
                case 5:
                    return this.isValidRepeat(text);
                case 6:
                    return this.isValidVertical(text);
                case 7:
                    return this.isValidFixed(text);
                case 8:
                    return this.isValidHorizontalVertical(text);
                case 9:
                    return this.isValidVerticalHorizontal(text);
                default:
                    OptiFineConfig.warn("Unknown method: " + text);
                    return false;
            }
        } else {
            OptiFineConfig.warn("No tiles specified: " + text);
            return false;
        }
    }

    private static TextureAtlasSprite getIcon(String text) {
        TextureMap textureMapBlocks = MinecraftClient.getMinecraft().getTextureMapBlocks();
        TextureAtlasSprite textureAtlasSprite = textureMapBlocks.getSpriteSafe(text);
        return textureAtlasSprite != null
                ? textureAtlasSprite
                : textureMapBlocks.getSpriteSafe("blocks/" + text);
    }

    public boolean matchesIcon(TextureAtlasSprite textureAtlasSprite) {
        return Matches.sprite(textureAtlasSprite, this.matchTileIcons);
    }

    // region String decoding support

    // endregion

    private boolean isValidRepeat(String text) {
        if (this.tiles == null) {
            OptiFineConfig.warn("Tiles not defined: " + text);
            return false;
        }

        if (this.width <= 0 || this.width > 16) {
            OptiFineConfig.warn("Invalid width: " + text);
            return false;
        }

        if (this.height > 0 && this.height <= 16) {
            if (this.tiles.length != this.width * this.height) {
                OptiFineConfig.warn("Number of tiles does not equal width x height: " + text);
                return false;
            } else {
                return true;
            }
        } else {
            OptiFineConfig.warn("Invalid height: " + text);
            return false;
        }
    }

    public ConnectedProperties(Properties properties, String text) {
        this.matchTiles = null;
        this.method = 0;
        this.tiles = null;
        this.connect = 0;
        this.faces = 63;
        this.biomes = null;
        this.minHeight = 0;
        this.maxHeight = 1024;
        this.renderPass = 0;
        this.innerSeams = false;
        this.width = 0;
        this.height = 0;
        this.weights = null;
        this.symmetry = 1;
        this.sumWeights = null;
        this.sumAllWeights = 1;
        this.matchTileIcons = null;
        this.tileIcons = null;
        ConnectedParser connectedParser = new ConnectedParser("ConnectedTextures");
        this.name = connectedParser.parseName(text);
        this.basePath = connectedParser.parseBasePath(text);
        this.matchBlocks = connectedParser.parseMatchBlocks(properties.getProperty("matchBlocks"));
        this.metadatas = connectedParser.parseIntList(properties.getProperty("metadata"));
        this.matchTiles = this.parseMatchTiles(properties.getProperty("matchTiles"));
        this.method = parseMethod(properties.getProperty("method"));
        this.tiles = this.parseTileNames(properties.getProperty("tiles"));
        this.connect = parseConnect(properties.getProperty("connect"));
        this.faces = parseFaces(properties.getProperty("faces"));
        this.biomes = connectedParser.c(properties.getProperty("biomes"));
        this.minHeight = connectedParser.parseIntOrDefault(properties.getProperty("minHeight"), -1);
        this.maxHeight =
                connectedParser.parseIntOrDefault(properties.getProperty("maxHeight"), 1024);
        this.renderPass = connectedParser.parseInt(properties.getProperty("renderPass"));
        this.innerSeams = ConnectedParser.parseBoolean(properties.getProperty("innerSeams"));
        this.width = connectedParser.parseInt(properties.getProperty("width"));
        this.height = connectedParser.parseInt(properties.getProperty("height"));
        this.weights = connectedParser.parseIntList(properties.getProperty("weights"));
        this.symmetry = parseSymmetry(properties.getProperty("symmetry"));
    }

    // region String decoding support

    // endregion

    public void updateIcons(TextureMap textureMap) {
        if (this.matchTiles != null) {
            this.matchTileIcons =
                    optifine.ConnectedProperties.registerIcons(this.matchTiles, textureMap);
        }

        if (this.tiles != null) {
            this.tileIcons = optifine.ConnectedProperties.registerIcons(this.tiles, textureMap);
        }
    }

    private String[] detectMatchTiles() {
        net.minecraft.client.renderer.texture.TextureAtlasSprite textureAtlasSprite = optifine.ConnectedProperties.getIcon(this.name);
        return textureAtlasSprite == null ? null : new java.lang.String[] {this.name};
    }

    private boolean isValidCtm(String text) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("0-11 16-27 32-43 48-58");
        }

        if (this.tiles.length < 47) {
            OptiFineConfig.warn("Invalid tiles, must be at least 47: " + text);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidVerticalHorizontal(String text) {
        if (this.tiles == null) {
            OptiFineConfig.warn("No tiles defined for vertical+horizontal: " + text);
            return false;
        } else if (this.tiles.length != 7) {
            OptiFineConfig.warn("Invalid tiles, must be exactly 7: " + text);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidTop(String text) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("66");
        }

        if (this.tiles.length != 1) {
            OptiFineConfig.warn("Invalid tiles, must be exactly 1: " + text);
            return false;
        } else {
            return true;
        }
    }

    private int detectConnect() {
        return this.matchBlocks != null ? 1 : (this.matchTiles != null ? 2 : 128);
    }

    private String[] parseTileNames(String text) {
        int i = ReflectorMethod.f();
        if (text == null) {
            return null;
        }

        ArrayList arrayList = new ArrayList();
        String[] astring = OptiFineConfig.tokenize(text, " ,");
        int j = 0;

        while (j < astring.length) {
            label72:
            {
                String textValue = astring[j];
                if (textValue.contains(packedTextFragments[2])) {
                    String[] astring1 = OptiFineConfig.tokenize(textValue, packedTextFragments[3]);
                    if (astring1.length == 2) {
                        int k = OptiFineConfig.parseInt(astring1[0], -1);
                        int l = OptiFineConfig.parseInt(astring1[1], -1);
                        if (k >= 0 && l >= 0) {
                            if (k <= l) {
                                int i1 = k;

                                do {
                                    if (i1 > l && i == 0) {
                                        break label72;
                                    }

                                    arrayList.add(String.valueOf(i1));
                                    i1++;
                                } while (i == 0);
                            }

                            OptiFineConfig.warn(
                                    "Invalid interval: " + textValue + ", when parsing: " + text);
                            if (i == 0) {
                                break label72;
                            }
                        }
                    }
                }

                arrayList.add(textValue);
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        String[] astring2 = ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
        int j1 = 0;

        while (j1 < astring2.length) {
            String textValue2 = astring2[j1];
            textValue2 = TextureUtils.fixResourcePath(textValue2, this.basePath);
            if (!textValue2.startsWith(this.basePath)
                    && !textValue2.startsWith("textures/")
                    && !textValue2.startsWith("mcpatcher/")) {
                textValue2 = this.basePath + packedTextFragments[4] + textValue2;
            }

            if (textValue2.endsWith(".png")) {
                textValue2 = textValue2.substring(0, textValue2.length() - 4);
            }

            String textValue3 = "textures/blocks/";
            if (textValue2.startsWith(textValue3)) {
                textValue2 = textValue2.substring(textValue3.length());
            }

            if (textValue2.startsWith(packedTextFragments[5])) {
                textValue2 = textValue2.substring(1);
            }

            astring2[j1] = textValue2;
            j1++;
            if (i != 0) {
                break;
            }
        }

        return astring2;
    }

    private static int parseMethod(String text) {
        if (text == null) {
            return 1;
        } else if (text.equals("ctm") || text.equals("glass")) {
            return 1;
        } else if (text.equals("horizontal") || text.equals("bookshelf")) {
            return 2;
        } else if (text.equals("vertical")) {
            return 6;
        } else if (text.equals("top")) {
            return 3;
        } else if (text.equals("random")) {
            return 4;
        } else if (text.equals("repeat")) {
            return 5;
        } else if (text.equals("fixed")) {
            return 7;
        } else if (text.equals("horizontal+vertical") || text.equals("h+v")) {
            return 8;
        } else if (!text.equals("vertical+horizontal") && !text.equals("v+h")) {
            OptiFineConfig.warn("Unknown method: " + text);
            return 0;
        } else {
            return 9;
        }
    }

    public boolean matchesBlockId(int p171_0) {
        return optifine.Matches.blockId(p171_0, this.matchBlocks);
    }

    @Override
    public String toString() {
        return "CTM name: "
                + this.name
                + ", basePath: "
                + this.basePath
                + ", matchBlocks: "
                + OptiFineConfig.arrayToString(this.matchBlocks)
                + ", matchTiles: "
                + OptiFineConfig.arrayToString(this.matchTiles);
    }

    public boolean matchesBlock(int p171_0, int p171_1) {
        return !optifine.Matches.block(p171_0, p171_1, this.matchBlocks)
                ? false
                : optifine.Matches.metadata(p171_1, this.metadatas);
    }

    public int getMetadataMax() {
        byte b0 = -1;
        int l = ReflectorMethod.cR();
        int j = this.getMax(this.metadatas, b0);
        int i = l;
        if (this.matchBlocks != null) {
            int k = 0;

            while (k < this.matchBlocks.length) {
                MatchBlock matchBlock = this.matchBlocks[k];
                j = this.getMax(matchBlock.getMetadatas(), j);
                k++;
                if (i == 0) {
                    break;
                }
            }
        }

        return j;
    }
}
