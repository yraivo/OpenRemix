package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConnectedTextures {
    private static final int mF = 0;

    private static final int mu = 4;
    private static final int tB = 5;
    private static final int ef = 0;
    private static final int sR = 3;
    private static final int f = 1;
    private static final int rW = 2;
    private static ConnectedProperties[][] tileProperties;
    private static Map[] spriteQuadMaps;
    private static TextureAtlasSprite emptySprite;
    public static BlockState AIR_DEFAULT_STATE;
    private static int[] ctmIndexes;
    private static ConnectedProperties[][] blockProperties;

    private static String[] propSuffixes;
    private static String[] packedTextFragments;
    private static final int mE = 1;
    private static final int Ao = 2;
    private static boolean multipass;

    private static BakedQuad makeSpriteQuad(
            BakedQuad bakedQuad, TextureAtlasSprite textureAtlasSprite) {
        int k = ReflectorMethod.f();
        int[] aint = (int[]) bakedQuad.getVertexData().clone();
        TextureAtlasSprite textureAtlasSpriteValue = bakedQuad.getSprite();
        int i = k;
        int j = 0;

        while (j < 4) {
            fixVertex(aint, j, textureAtlasSpriteValue, textureAtlasSprite);
            j++;
            if (i != 0) {
                break;
            }
        }

        return new BakedQuad(
                aint, bakedQuad.getTintIndex(), bakedQuad.getFace(), textureAtlasSprite);
    }

    // region String decoding support

    // endregion

    private static String[] getDefaultCtmPaths() {
        int k = ReflectorMethod.f();
        ArrayList arrayList = new ArrayList();
        String text = "mcpatcher/ctm/default/";
        int i = k;
        if (OptiFineConfig.isFromDefaultResourcePack(
                new ResourceLocation("textures/blocks/glass.png"))) {
            arrayList.add(text + "glass.properties");
            arrayList.add(text + "glasspane.properties");
        }

        if (OptiFineConfig.isFromDefaultResourcePack(
                new ResourceLocation("textures/blocks/bookshelf.png"))) {
            arrayList.add(text + "bookshelf.properties");
        }

        if (OptiFineConfig.isFromDefaultResourcePack(
                new ResourceLocation("textures/blocks/sandstone_normal.png"))) {
            arrayList.add(text + "sandstone.properties");
        }

        String[] astring =
                new String[] {
                    "white",
                    "orange",
                    "magenta",
                    "light_blue",
                    "yellow",
                    "lime",
                    "pink",
                    "gray",
                    "silver",
                    "cyan",
                    "purple",
                    "blue",
                    "brown",
                    "green",
                    "red",
                    "black"
                };
        int j = 0;

        while (j < astring.length) {
            String textValue = astring[j];
            if (OptiFineConfig.isFromDefaultResourcePack(
                    new ResourceLocation("textures/blocks/glass_" + textValue + ".png"))) {
                arrayList.add(
                        text + j + "_glass_" + textValue + "/glass_" + textValue + ".properties");
                arrayList.add(
                        text
                                + j
                                + "_glass_"
                                + textValue
                                + "/glass_pane_"
                                + textValue
                                + ".properties");
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    static {
        optifine.ConnectedTextures.packedTextFragments = new String[28];
        optifine.ConnectedTextures.packedTextFragments[1] = new String("");
        optifine.ConnectedTextures.packedTextFragments[2] = new String("a");
        optifine.ConnectedTextures.packedTextFragments[3] = new String("b");
        optifine.ConnectedTextures.packedTextFragments[4] = new String("c");
        optifine.ConnectedTextures.packedTextFragments[5] = new String("d");
        optifine.ConnectedTextures.packedTextFragments[6] = new String("e");
        optifine.ConnectedTextures.packedTextFragments[7] = new String("f");
        optifine.ConnectedTextures.packedTextFragments[8] = new String("g");
        optifine.ConnectedTextures.packedTextFragments[9] = new String("h");
        optifine.ConnectedTextures.packedTextFragments[10] = new String("i");
        optifine.ConnectedTextures.packedTextFragments[11] = new String("j");
        optifine.ConnectedTextures.packedTextFragments[12] = new String("k");
        optifine.ConnectedTextures.packedTextFragments[13] = new String("l");
        optifine.ConnectedTextures.packedTextFragments[14] = new String("m");
        optifine.ConnectedTextures.packedTextFragments[15] = new String("n");
        optifine.ConnectedTextures.packedTextFragments[16] = new String("o");
        optifine.ConnectedTextures.packedTextFragments[17] = new String("p");
        optifine.ConnectedTextures.packedTextFragments[18] = new String("q");
        optifine.ConnectedTextures.packedTextFragments[19] = new String("r");
        optifine.ConnectedTextures.packedTextFragments[20] = new String("s");
        optifine.ConnectedTextures.packedTextFragments[21] = new String("t");
        optifine.ConnectedTextures.packedTextFragments[22] = new String("u");
        optifine.ConnectedTextures.packedTextFragments[23] = new String("v");
        optifine.ConnectedTextures.packedTextFragments[24] = new String("w");
        optifine.ConnectedTextures.packedTextFragments[25] = new String("x");
        optifine.ConnectedTextures.packedTextFragments[26] = new String("y");
        optifine.ConnectedTextures.packedTextFragments[27] = new String("z");
        optifine.ConnectedTextures.spriteQuadMaps = null;
        optifine.ConnectedTextures.blockProperties = (ConnectedProperties[][]) null;
        optifine.ConnectedTextures.tileProperties = (ConnectedProperties[][]) null;
        optifine.ConnectedTextures.multipass = false;
        optifine.ConnectedTextures.propSuffixes =
                new String[] {
                    packedTextFragments[1],
                    packedTextFragments[2],
                    packedTextFragments[3],
                    packedTextFragments[4],
                    packedTextFragments[5],
                    packedTextFragments[6],
                    packedTextFragments[7],
                    packedTextFragments[8],
                    packedTextFragments[9],
                    packedTextFragments[10],
                    packedTextFragments[11],
                    packedTextFragments[12],
                    packedTextFragments[13],
                    packedTextFragments[14],
                    packedTextFragments[15],
                    packedTextFragments[16],
                    packedTextFragments[17],
                    packedTextFragments[18],
                    packedTextFragments[19],
                    packedTextFragments[20],
                    packedTextFragments[21],
                    packedTextFragments[22],
                    packedTextFragments[23],
                    packedTextFragments[24],
                    packedTextFragments[25],
                    packedTextFragments[26],
                    packedTextFragments[27]
                };
        optifine.ConnectedTextures.ctmIndexes =
                new int[] {
                    0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 12, 13, 14, 15, 16, 17, 18,
                    19, 20, 21, 22, 23, 0, 0, 0, 0, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35,
                    0, 0, 0, 0, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 0, 0, 0, 0, 0
                };
        optifine.ConnectedTextures.AIR_DEFAULT_STATE = Blocks.air.getDefaultState();
        optifine.ConnectedTextures.emptySprite = null;
    }

    private static TextureAtlasSprite getConnectedTextureHorizontalVertical(
            ConnectedProperties connectedProperties,
            IBlockAccess iBlockAccess,
            BlockState blockState,
            BlockPos blockPos,
            int p171_4,
            int p171_5,
            TextureAtlasSprite textureAtlasSprite,
            int p171_7) {
        net.minecraft.client.renderer.texture.TextureAtlasSprite[] textureAtlasSpriteArray =
                connectedProperties.tileIcons;
        net.minecraft.client.renderer.texture.TextureAtlasSprite textureAtlasSpriteValue =
                optifine.ConnectedTextures.getConnectedTextureHorizontal(
                        connectedProperties,
                        iBlockAccess,
                        blockState,
                        blockPos,
                        p171_4,
                        p171_5,
                        textureAtlasSprite,
                        p171_7);
        if (textureAtlasSpriteValue != null
                && textureAtlasSpriteValue != textureAtlasSprite
                && textureAtlasSpriteValue != textureAtlasSpriteArray[3]) {
            return textureAtlasSpriteValue;
        }

        net.minecraft.client.renderer.texture.TextureAtlasSprite textureAtlasSpriteValue2 =
                optifine.ConnectedTextures.getConnectedTextureVerticalBoth(
                        connectedProperties,
                        iBlockAccess,
                        blockState,
                        blockPos,
                        p171_4,
                        p171_5,
                        textureAtlasSprite,
                        p171_7);
        return textureAtlasSpriteValue2 == textureAtlasSpriteArray[0]
                ? textureAtlasSpriteArray[4]
                : (textureAtlasSpriteValue2 == textureAtlasSpriteArray[1]
                        ? textureAtlasSpriteArray[5]
                        : (textureAtlasSpriteValue2 == textureAtlasSpriteArray[2]
                                ? textureAtlasSpriteArray[6]
                                : textureAtlasSpriteValue2));
    }

    private static TextureAtlasSprite getConnectedTextureVertical(
            ConnectedProperties connectedProperties,
            IBlockAccess iBlockAccess,
            BlockState blockState,
            BlockPos blockPos,
            int i,
            int j,
            TextureAtlasSprite textureAtlasSprite,
            int k) {
        int i1 = ReflectorMethod.cR();
        byte b0 = 0;
        int l = i1;
        switch (i) {
            case 0:
                if (j == 1 || j == 0) {
                    return null;
                }

                b0 =
                        remix.compat.compiler.JvmBooleanSlots.toByte(
                                isNeighbour(
                                        connectedProperties,
                                        iBlockAccess,
                                        blockState,
                                        blockPos.offsetUp(),
                                        j,
                                        textureAtlasSprite,
                                        k));
                if (l != 0) {
                    break;
                }
            case 1:
                if (j == 3 || j == 2) {
                    return null;
                }

                b0 =
                        remix.compat.compiler.JvmBooleanSlots.toByte(
                                isNeighbour(
                                        connectedProperties,
                                        iBlockAccess,
                                        blockState,
                                        blockPos.offsetSouth(),
                                        j,
                                        textureAtlasSprite,
                                        k));
                if (l != 0) {
                    break;
                }
            case 2:
                if (j == 5 || j == 4) {
                    return null;
                }

                b0 =
                        remix.compat.compiler.JvmBooleanSlots.toByte(
                                isNeighbour(
                                        connectedProperties,
                                        iBlockAccess,
                                        blockState,
                                        blockPos.offsetEast(),
                                        j,
                                        textureAtlasSprite,
                                        k));
        }

        return b0 != 0 ? connectedProperties.tileIcons[0] : null;
    }

    private static void addToBlockList(ConnectedProperties connectedProperties, List list) {
        int i = ReflectorMethod.cR();
        if (connectedProperties.matchBlocks != null) {
            int j = 0;

            while (j < connectedProperties.matchBlocks.length) {
                {
                    int k = connectedProperties.matchBlocks[j].getBlockId();
                    if (k < 0) {
                        OptiFineConfig.warn("Invalid block ID: " + k);
                        if (!(i != 0)) {

                            addToList(connectedProperties, list, k);
                        }
                    } else {

                        addToList(connectedProperties, list, k);
                    }
                }

                j++;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    public static int getSide(Facing facing) {
        if (facing == null) {
            return -1;
        }

        switch (facing) {
            case DOWN:
                return 0;
            case UP:
                return 1;
            case EAST:
                return 5;
            case WEST:
                return 4;
            case NORTH:
                return 2;
            case SOUTH:
                return 3;
            default:
                return -1;
        }
    }

    private static void addToTileList(ConnectedProperties connectedProperties, List list) {
        int i = ReflectorMethod.f();
        if (connectedProperties.matchTileIcons != null) {
            int j = 0;

            while (j < connectedProperties.matchTileIcons.length) {
                label33:
                {
                    TextureAtlasSprite textureAtlasSprite = connectedProperties.matchTileIcons[j];
                    if (!(textureAtlasSprite instanceof TextureAtlasSprite)) {
                        OptiFineConfig.warn(
                                "TextureAtlasSprite is not TextureAtlasSprite: "
                                        + textureAtlasSprite
                                        + ", name: "
                                        + textureAtlasSprite.getIconName());
                        if (i == 0) {
                            break label33;
                        }
                    }

                    int k = textureAtlasSprite.getIndexInMap();
                    if (k < 0) {
                        OptiFineConfig.warn(
                                "Invalid tile ID: "
                                        + k
                                        + ", icon: "
                                        + textureAtlasSprite.getIconName());
                        if (i == 0) {
                            break label33;
                        }
                    }

                    addToList(connectedProperties, list, k);
                }

                j++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    private static int fixSideByAxis(int p171_0, int p171_1) {
        switch (p171_1) {
            case 0:
                return p171_0;
            case 1:
                switch (p171_0) {
                    case 0:
                        return 2;
                    case 1:
                        return 3;
                    case 2:
                        return 1;
                    case 3:
                        return 0;
                    default:
                        return p171_0;
                }
            case 2:
                switch (p171_0) {
                    case 0:
                        return 4;
                    case 1:
                        return 5;
                    case 2:
                    case 3:
                    default:
                        return p171_0;
                    case 4:
                        return 1;
                    case 5:
                        return 0;
                }
            default:
                return p171_0;
        }
    }

    private static TextureAtlasSprite getConnectedTextureFixed(
            ConnectedProperties connectedProperties) {
        return connectedProperties.tileIcons[0];
    }

    private static TextureAtlasSprite getNeighbourIcon(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, int i) {
        int l = ReflectorMethod.f();
        blockState = blockState.getBlock().getActualState(blockState, iBlockAccess, blockPos);
        int j = l;
        IBakedModel iBakedModel =
                MinecraftClient.getMinecraft()
                        .getBlockRendererDispatcher()
                        .getBlockModelShapes()
                        .getModelForState(blockState);
        if (iBakedModel == null) {
            return null;
        }

        Facing facing = getFacing(i);
        List faceQuads = iBakedModel.getFaceQuads(facing);
        if (faceQuads.size() > 0) {
            BakedQuad bakedQuad = (BakedQuad) faceQuads.get(0);
            return bakedQuad.getSprite();
        }

        List listValue = iBakedModel.getGeneralQuads();
        int k = 0;

        while (k < listValue.size()) {
            BakedQuad bakedQuadValue = (BakedQuad) listValue.get(k);
            if (bakedQuadValue.getFace() == facing) {
                return bakedQuadValue.getSprite();
            }

            k++;
            if (j != 0) {
                break;
            }
        }

        return null;
    }

    public static TextureAtlasSprite getConnectedTextureSingle(
            IBlockAccess iBlockAccess,
            BlockState blockState,
            BlockPos blockPos,
            Facing facing,
            TextureAtlasSprite textureAtlasSprite,
            boolean p171_5,
            RenderEnv renderEnv) {
        int l1 = optifine.ReflectorMethod.f();
        net.minecraft.block.Block block = blockState.getBlock();
        int i = l1;
        if (!(blockState instanceof net.minecraft.block.state.BlockStateBase)) {
            return textureAtlasSprite;
        }

        net.minecraft.block.state.BlockStateBase blockStateBase =
                (net.minecraft.block.state.BlockStateBase) blockState;
        if (optifine.ConnectedTextures.tileProperties != null) {
            int j = textureAtlasSprite.getIndexInMap();
            if (j >= 0 && j < optifine.ConnectedTextures.tileProperties.length) {
                optifine.ConnectedProperties[] connectedPropertiesArray =
                        optifine.ConnectedTextures.tileProperties[j];
                if (connectedPropertiesArray != null) {
                    int k = optifine.ConnectedTextures.getSide(facing);
                    int l = 0;

                    while (l < connectedPropertiesArray.length) {
                        optifine.ConnectedProperties connectedProperties =
                                connectedPropertiesArray[l];
                        if (connectedProperties != null
                                && connectedProperties.matchesBlockId(
                                        blockStateBase.getBlockId())) {
                            net.minecraft.client.renderer.texture.TextureAtlasSprite
                                    textureAtlasSpriteValue =
                                            optifine.ConnectedTextures.getConnectedTexture(
                                                    connectedProperties,
                                                    iBlockAccess,
                                                    blockStateBase,
                                                    blockPos,
                                                    k,
                                                    textureAtlasSprite,
                                                    renderEnv);
                            if (textureAtlasSpriteValue != null) {
                                return textureAtlasSpriteValue;
                            }
                        }

                        l++;
                        if (i != 0) {
                            break;
                        }
                    }
                }
            }
        }

        if (optifine.ConnectedTextures.blockProperties != null && p171_5) {
            int i1 = renderEnv.method_9b417d27949cba33a0d0();
            if (i1 >= 0 && i1 < optifine.ConnectedTextures.blockProperties.length) {
                optifine.ConnectedProperties[] connectedPropertiesArrayValue =
                        optifine.ConnectedTextures.blockProperties[i1];
                if (connectedPropertiesArrayValue != null) {
                    int j1 = optifine.ConnectedTextures.getSide(facing);
                    int k1 = 0;

                    while (k1 < connectedPropertiesArrayValue.length) {
                        optifine.ConnectedProperties connectedPropertiesValue =
                                connectedPropertiesArrayValue[k1];
                        if (connectedPropertiesValue != null
                                && connectedPropertiesValue.matchesIcon(textureAtlasSprite)) {
                            net.minecraft.client.renderer.texture.TextureAtlasSprite
                                    textureAtlasSpriteValue2 =
                                            optifine.ConnectedTextures.getConnectedTexture(
                                                    connectedPropertiesValue,
                                                    iBlockAccess,
                                                    blockStateBase,
                                                    blockPos,
                                                    j1,
                                                    textureAtlasSprite,
                                                    renderEnv);
                            if (textureAtlasSpriteValue2 != null) {
                                return textureAtlasSpriteValue2;
                            }
                        }

                        k1++;
                        if (i != 0) {
                            break;
                        }
                    }
                }
            }
        }

        return textureAtlasSprite;
    }

    private static TextureAtlasSprite getConnectedTexture(
            ConnectedProperties connectedProperties,
            IBlockAccess iBlockAccess,
            BlockStateBase blockStateBase,
            BlockPos blockPos,
            int i,
            TextureAtlasSprite textureAtlasSprite,
            RenderEnv renderEnv) {
        int j = 0;
        int k = blockStateBase.getMetadata();
        int l = k;
        Block block = blockStateBase.getBlock();
        if (block instanceof BlockRotatedPillar) {
            j = getWoodAxis(i, k);
            if (connectedProperties.getMetadataMax() <= 3) {
                l = k & 3;
            }
        }

        if (block instanceof BlockQuartz) {
            j = getQuartzAxis(i, k);
            if (connectedProperties.getMetadataMax() <= 2 && l > 2) {
                l = 2;
            }
        }

        if (!connectedProperties.matchesBlock(blockStateBase.getBlockId(), l)) {
            return null;
        }

        if (i >= 0 && connectedProperties.faces != 63) {
            int i1 = i;
            if (j != 0) {
                i1 = fixSideByAxis(i, j);
            }

            if ((1 << i1 & connectedProperties.faces) == 0) {
                return null;
            }
        }

        int j1 = blockPos.getY();
        if (j1 >= connectedProperties.minHeight && j1 <= connectedProperties.maxHeight) {
            if (connectedProperties.biomes != null) {
                BiomeGenBase biomeGenForCoords = iBlockAccess.getBiomeGenForCoords(blockPos);
                if (!connectedProperties.matchesBiome(biomeGenForCoords)) {
                    return null;
                }
            }

            switch (connectedProperties.method) {
                case 1:
                    return getConnectedTextureCtm(
                            connectedProperties,
                            iBlockAccess,
                            blockStateBase,
                            blockPos,
                            j,
                            i,
                            textureAtlasSprite,
                            k,
                            renderEnv);
                case 2:
                    return getConnectedTextureHorizontal(
                            connectedProperties,
                            iBlockAccess,
                            blockStateBase,
                            blockPos,
                            j,
                            i,
                            textureAtlasSprite,
                            k);
                case 3:
                    return getConnectedTextureVertical(
                            connectedProperties,
                            iBlockAccess,
                            blockStateBase,
                            blockPos,
                            j,
                            i,
                            textureAtlasSprite,
                            k);
                case 4:
                    return getConnectedTextureRandom(connectedProperties, blockPos, i);
                case 5:
                    return getConnectedTextureRepeat(connectedProperties, blockPos, i);
                case 6:
                    return getConnectedTextureVerticalBoth(
                            connectedProperties,
                            iBlockAccess,
                            blockStateBase,
                            blockPos,
                            j,
                            i,
                            textureAtlasSprite,
                            k);
                case 7:
                    return getConnectedTextureFixed(connectedProperties);
                case 8:
                    return getConnectedTextureHorizontalVertical(
                            connectedProperties,
                            iBlockAccess,
                            blockStateBase,
                            blockPos,
                            j,
                            i,
                            textureAtlasSprite,
                            k);
                case 9:
                    return getConnectedTextureVerticalHorizontal(
                            connectedProperties,
                            iBlockAccess,
                            blockStateBase,
                            blockPos,
                            j,
                            i,
                            textureAtlasSprite,
                            k);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    private static TextureAtlasSprite getConnectedTextureVerticalBoth(
            ConnectedProperties connectedProperties,
            IBlockAccess iBlockAccess,
            BlockState blockState,
            BlockPos blockPos,
            int i,
            int j,
            TextureAtlasSprite textureAtlasSprite,
            int k) {
        int i1 = ReflectorMethod.cR();
        byte b0 = 0;
        int l = i1;
        byte b1 = 0;
        switch (i) {
            case 0:
                if (j == 1 || j == 0) {
                    return null;
                }

                b0 =
                        remix.compat.compiler.JvmBooleanSlots.toByte(
                                isNeighbour(
                                        connectedProperties,
                                        iBlockAccess,
                                        blockState,
                                        blockPos.offsetDown(),
                                        j,
                                        textureAtlasSprite,
                                        k));
                b1 =
                        remix.compat.compiler.JvmBooleanSlots.toByte(
                                isNeighbour(
                                        connectedProperties,
                                        iBlockAccess,
                                        blockState,
                                        blockPos.offsetUp(),
                                        j,
                                        textureAtlasSprite,
                                        k));
                if (l != 0) {
                    break;
                }
            case 1:
                if (j == 3 || j == 2) {
                    return null;
                }

                b0 =
                        remix.compat.compiler.JvmBooleanSlots.toByte(
                                isNeighbour(
                                        connectedProperties,
                                        iBlockAccess,
                                        blockState,
                                        blockPos.offsetSouth(),
                                        j,
                                        textureAtlasSprite,
                                        k));
                b1 =
                        remix.compat.compiler.JvmBooleanSlots.toByte(
                                isNeighbour(
                                        connectedProperties,
                                        iBlockAccess,
                                        blockState,
                                        blockPos.offsetNorth(),
                                        j,
                                        textureAtlasSprite,
                                        k));
                if (l != 0) {
                    break;
                }
            case 2:
                if (j == 5 || j == 4) {
                    return null;
                }

                b0 =
                        remix.compat.compiler.JvmBooleanSlots.toByte(
                                isNeighbour(
                                        connectedProperties,
                                        iBlockAccess,
                                        blockState,
                                        blockPos.offsetWest(),
                                        j,
                                        textureAtlasSprite,
                                        k));
                b1 =
                        remix.compat.compiler.JvmBooleanSlots.toByte(
                                isNeighbour(
                                        connectedProperties,
                                        iBlockAccess,
                                        blockState,
                                        blockPos.offsetEast(),
                                        j,
                                        textureAtlasSprite,
                                        k));
        }

        if (b0 != 0) {
            if (b1 != 0) {
                byte b3 = 1;
                if (l != 0) {
                    return connectedProperties.tileIcons[b3];
                }
            }

            byte b4 = 2;
            if (l != 0) {
                return connectedProperties.tileIcons[b4];
            }
        }

        if (b1 != 0) {
            byte b5 = 0;
            if (l != 0) {
                return connectedProperties.tileIcons[b5];
            }
        }

        byte b6 = 3;
        return connectedProperties.tileIcons[b6];
    }

    private static TextureAtlasSprite getConnectedTextureCtm(
            ConnectedProperties connectedProperties,
            IBlockAccess iBlockAccess,
            BlockState blockState,
            BlockPos blockPos,
            int i1,
            int i,
            TextureAtlasSprite textureAtlasSprite,
            int j,
            RenderEnv renderEnv) {
        int l = ReflectorMethod.cR();
        boolean[] aboolean = renderEnv.getBorderFlags();
        int k = l;
        switch (i) {
            case 0:
                aboolean[0] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetWest(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[1] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetEast(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[2] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetNorth(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[3] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetSouth(),
                                i,
                                textureAtlasSprite,
                                j);
                if (k != 0) {
                    break;
                }
            case 1:
                aboolean[0] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetWest(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[1] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetEast(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[2] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetSouth(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[3] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetNorth(),
                                i,
                                textureAtlasSprite,
                                j);
                if (k != 0) {
                    break;
                }
            case 2:
                aboolean[0] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetEast(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[1] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetWest(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[2] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetDown(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[3] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetUp(),
                                i,
                                textureAtlasSprite,
                                j);
                if (k != 0) {
                    break;
                }
            case 3:
                aboolean[0] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetWest(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[1] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetEast(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[2] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetDown(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[3] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetUp(),
                                i,
                                textureAtlasSprite,
                                j);
                if (k != 0) {
                    break;
                }
            case 4:
                aboolean[0] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetNorth(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[1] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetSouth(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[2] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetDown(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[3] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetUp(),
                                i,
                                textureAtlasSprite,
                                j);
                if (k != 0) {
                    break;
                }
            case 5:
                aboolean[0] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetSouth(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[1] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetNorth(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[2] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetDown(),
                                i,
                                textureAtlasSprite,
                                j);
                aboolean[3] =
                        isNeighbour(
                                connectedProperties,
                                iBlockAccess,
                                blockState,
                                blockPos.offsetUp(),
                                i,
                                textureAtlasSprite,
                                j);
        }

        byte b0;
        label754:
        {
            b0 = 0;
            if ((remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[0])
                            & (!aboolean[1] ? 1 : 0)
                            & (!aboolean[2] ? 1 : 0)
                            & (!aboolean[3] ? 1 : 0))
                    != 0) {
                b0 = 3;
                if (k != 0) {
                    break label754;
                }
            }

            if (((!aboolean[0] ? 1 : 0)
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[1])
                            & (!aboolean[2] ? 1 : 0)
                            & (!aboolean[3] ? 1 : 0))
                    != 0) {
                b0 = 1;
                if (k != 0) {
                    break label754;
                }
            }

            if (((!aboolean[0] ? 1 : 0)
                            & (!aboolean[1] ? 1 : 0)
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[2])
                            & (!aboolean[3] ? 1 : 0))
                    != 0) {
                b0 = 12;
                if (k != 0) {
                    break label754;
                }
            }

            if (((!aboolean[0] ? 1 : 0)
                            & (!aboolean[1] ? 1 : 0)
                            & (!aboolean[2] ? 1 : 0)
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[3]))
                    != 0) {
                b0 = 36;
                if (k != 0) {
                    break label754;
                }
            }

            if ((remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[0] & aboolean[1])
                            & (!aboolean[2] ? 1 : 0)
                            & (!aboolean[3] ? 1 : 0))
                    != 0) {
                b0 = 2;
                if (k != 0) {
                    break label754;
                }
            }

            if (((!aboolean[0] ? 1 : 0)
                            & (!aboolean[1] ? 1 : 0)
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[2])
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[3]))
                    != 0) {
                b0 = 24;
                if (k != 0) {
                    break label754;
                }
            }

            if ((remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[0])
                            & (!aboolean[1] ? 1 : 0)
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[2])
                            & (!aboolean[3] ? 1 : 0))
                    != 0) {
                b0 = 15;
                if (k != 0) {
                    break label754;
                }
            }

            if ((remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[0])
                            & (!aboolean[1] ? 1 : 0)
                            & (!aboolean[2] ? 1 : 0)
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[3]))
                    != 0) {
                b0 = 39;
                if (k != 0) {
                    break label754;
                }
            }

            if (((!aboolean[0] ? 1 : 0)
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[1])
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[2])
                            & (!aboolean[3] ? 1 : 0))
                    != 0) {
                b0 = 13;
                if (k != 0) {
                    break label754;
                }
            }

            if (((!aboolean[0] ? 1 : 0)
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[1])
                            & (!aboolean[2] ? 1 : 0)
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[3]))
                    != 0) {
                b0 = 37;
                if (k != 0) {
                    break label754;
                }
            }

            if (((!aboolean[0] ? 1 : 0)
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[1])
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[2])
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[3]))
                    != 0) {
                b0 = 25;
                if (k != 0) {
                    break label754;
                }
            }

            if ((remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[0])
                            & (!aboolean[1] ? 1 : 0)
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[2])
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[3]))
                    != 0) {
                b0 = 27;
                if (k != 0) {
                    break label754;
                }
            }

            if ((remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[0] & aboolean[1])
                            & (!aboolean[2] ? 1 : 0)
                            & remix.compat.compiler.JvmBooleanSlots.toInt(aboolean[3]))
                    != 0) {
                b0 = 38;
                if (k != 0) {
                    break label754;
                }
            }

            if ((remix.compat.compiler.JvmBooleanSlots.toInt(
                                    aboolean[0] & aboolean[1] & aboolean[2])
                            & (!aboolean[3] ? 1 : 0))
                    != 0) {
                b0 = 14;
                if (k != 0) {
                    break label754;
                }
            }

            if (aboolean[0] & aboolean[1] & aboolean[2] & aboolean[3]) {
                b0 = 26;
            }
        }

        if (b0 == 0) {
            return connectedProperties.tileIcons[b0];
        }

        if (!OptiFineConfig.iX()) {
            return connectedProperties.tileIcons[b0];
        }

        switch (i) {
            case 0:
                aboolean[0] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetEast().offsetNorth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[1] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetWest().offsetNorth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[2] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetEast().offsetSouth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[3] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetWest().offsetSouth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                if (k != 0) {
                    break;
                }
            case 1:
                aboolean[0] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetEast().offsetSouth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[1] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetWest().offsetSouth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[2] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetEast().offsetNorth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[3] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetWest().offsetNorth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                if (k != 0) {
                    break;
                }
            case 2:
                aboolean[0] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetWest().offsetDown(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[1] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetEast().offsetDown(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[2] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetWest().offsetUp(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[3] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetEast().offsetUp(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                if (k != 0) {
                    break;
                }
            case 3:
                aboolean[0] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetEast().offsetDown(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[1] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetWest().offsetDown(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[2] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetEast().offsetUp(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[3] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetWest().offsetUp(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                if (k != 0) {
                    break;
                }
            case 4:
                aboolean[0] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetDown().offsetSouth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[1] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetDown().offsetNorth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[2] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetUp().offsetSouth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[3] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetUp().offsetNorth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                if (k != 0) {
                    break;
                }
            case 5:
                aboolean[0] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetDown().offsetNorth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[1] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetDown().offsetSouth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[2] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetUp().offsetNorth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
                aboolean[3] =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!isNeighbour(
                                                connectedProperties,
                                                iBlockAccess,
                                                blockState,
                                                blockPos.offsetUp().offsetSouth(),
                                                i,
                                                textureAtlasSprite,
                                                j)
                                        ? 1
                                        : 0));
        }

        if (b0 == 13 && aboolean[0]) {
            b0 = 4;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 15 && aboolean[1]) {
            b0 = 5;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 37 && aboolean[2]) {
            b0 = 16;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 39 && aboolean[3]) {
            b0 = 17;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 14 && aboolean[0] && aboolean[1]) {
            b0 = 7;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 25 && aboolean[0] && aboolean[2]) {
            b0 = 6;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 27 && aboolean[3] && aboolean[1]) {
            b0 = 19;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 38 && aboolean[3] && aboolean[2]) {
            b0 = 18;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 14 && !aboolean[0] && aboolean[1]) {
            b0 = 31;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 25 && aboolean[0] && !aboolean[2]) {
            b0 = 30;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 27 && !aboolean[3] && aboolean[1]) {
            b0 = 41;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 38 && aboolean[3] && !aboolean[2]) {
            b0 = 40;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 14 && aboolean[0] && !aboolean[1]) {
            b0 = 29;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 25 && !aboolean[0] && aboolean[2]) {
            b0 = 28;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 27 && aboolean[3] && !aboolean[1]) {
            b0 = 43;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 38 && !aboolean[3] && aboolean[2]) {
            b0 = 42;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3]) {
            b0 = 46;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3]) {
            b0 = 9;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3]) {
            b0 = 21;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3]) {
            b0 = 8;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3]) {
            b0 = 20;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3]) {
            b0 = 11;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3]) {
            b0 = 22;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3]) {
            b0 = 23;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3]) {
            b0 = 10;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3]) {
            b0 = 34;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3]) {
            b0 = 35;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3]) {
            b0 = 32;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3]) {
            b0 = 33;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3]) {
            b0 = 44;
            if (k != 0) {
                return connectedProperties.tileIcons[b0];
            }
        }

        if (b0 == 26 && !aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3]) {
            b0 = 45;
        }

        return connectedProperties.tileIcons[b0];
    }

    private static TextureAtlasSprite getConnectedTextureHorizontal(
            ConnectedProperties connectedProperties,
            IBlockAccess iBlockAccess,
            BlockState blockState,
            BlockPos blockPos,
            int p171_4,
            int p171_5,
            TextureAtlasSprite textureAtlasSprite,
            int p171_7) {
        int l;
        byte b0;
        byte flag;
        int i1 = optifine.ReflectorMethod.f();
        b0 = 0;
        l = i1;
        flag = 0;
        label68:
        switch (p171_4) {
            case 0:
                switch (p171_5) {
                    case 0:
                    case 1:
                        return null;
                    case 2:
                        b0 =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetEast(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        flag =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetWest(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        if (l == 0) {
                            break label68;
                        }
                    case 3:
                        b0 =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetWest(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        flag =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetEast(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        if (l == 0) {
                            break label68;
                        }
                    case 4:
                        b0 =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetNorth(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        flag =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetSouth(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        if (l == 0) {
                            break label68;
                        }
                    case 5:
                        b0 =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetSouth(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        flag =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetNorth(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                    default:
                        if (l == 0) {
                            break label68;
                        }
                }
            case 1:
                switch (p171_5) {
                    case 0:
                        b0 =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetWest(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        flag =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetEast(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        if (l == 0) {
                            break label68;
                        }
                    case 1:
                        b0 =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetWest(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        flag =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetEast(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        if (l != 0) {
                            return null;
                        }
                        break label68;
                    case 2:
                    case 3:
                        return null;
                    case 4:
                        b0 =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetDown(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        flag =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetUp(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        if (l == 0) {
                            break label68;
                        }
                    case 5:
                        b0 =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetUp(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        flag =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetDown(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                    default:
                        if (l == 0) {
                            break label68;
                        }
                }
            case 2:
                switch (p171_5) {
                    case 0:
                        b0 =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetNorth(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        flag =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetSouth(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        if (l == 0) {
                            break;
                        }
                    case 1:
                        b0 =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetNorth(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        flag =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetSouth(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        if (l == 0) {
                            break;
                        }
                    case 2:
                        b0 =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetDown(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        flag =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetUp(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        if (l == 0) {
                            break;
                        }
                    case 3:
                        b0 =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetUp(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        flag =
                                (byte)
                                        (optifine.ConnectedTextures.isNeighbour(
                                                        connectedProperties,
                                                        iBlockAccess,
                                                        blockState,
                                                        blockPos.offsetDown(),
                                                        p171_5,
                                                        textureAtlasSprite,
                                                        p171_7)
                                                ? 1
                                                : 0);
                        if (l != 0) {
                            return null;
                        }
                        break;
                    case 4:
                    case 5:
                        return null;
                }
        }

        if (b0 != 0) {
            if (flag != 0) {
                byte b1 = 1;
                if (l == 0) {
                    return connectedProperties.tileIcons[b1];
                }
            }

            byte b2 = 2;
            if (l == 0) {
                return connectedProperties.tileIcons[b2];
            }
        }

        if (flag != 0) {
            byte b3 = 0;
            if (l == 0) {
                return connectedProperties.tileIcons[b3];
            }
        }

        byte b4 = 3;
        return connectedProperties.tileIcons[b4];
    }

    private static FileNotFoundException method_fd3f0eb1dd2c178d63ce(
            FileNotFoundException filenotfoundexception) {
        return filenotfoundexception;
    }

    private static TextureAtlasSprite getConnectedTextureRandom(
            ConnectedProperties connectedProperties, BlockPos blockPos, int i) {
        int j = ReflectorMethod.cR();
        if (connectedProperties.tileIcons.length == 1) {
            return connectedProperties.tileIcons[0];
        }

        int k = i / connectedProperties.symmetry * connectedProperties.symmetry;
        int l = OptiFineConfig.getRandom(blockPos, k) & 2147483647;
        int i1 = 0;
        if (connectedProperties.weights == null) {
            i1 = l % connectedProperties.tileIcons.length;
            if (j != 0) {
                return connectedProperties.tileIcons[i1];
            }
        }

        int j1 = l % connectedProperties.sumAllWeights;
        int[] aint = connectedProperties.sumWeights;
        int k1 = 0;

        while (k1 < aint.length) {
            if (j1 < aint[k1]) {
                i1 = k1;
                if (j != 0) {
                    break;
                }
            }

            k1++;
            if (j == 0) {
                break;
            }
        }

        return connectedProperties.tileIcons[i1];
    }

    public static void updateIcons(TextureMap textureMap, IResourcePack iResourcePack) {
        String[] astring =
                ResUtils.collectFiles(
                        iResourcePack, "mcpatcher/ctm/", ".properties", getDefaultCtmPaths());
        Arrays.sort(astring);
        int k = ReflectorMethod.f();
        List list = makePropertyList(optifine.ConnectedTextures.tileProperties);
        int i = k;
        List listValue = makePropertyList(optifine.ConnectedTextures.blockProperties);
        int j = 0;

        while (j < astring.length) {
            String text = astring[j];
            OptiFineConfig.dbg("ConnectedTextures: " + text);

            try {
                label50:
                {
                    ResourceLocation resourceLocation = new ResourceLocation(text);
                    InputStream inputstream = iResourcePack.getInputStream(resourceLocation);

                    try {
                        if (false)
                            throw (java.io.FileNotFoundException)
                                    null; // Original JVM checked handler; no emitted instructions.
                        if (inputstream == null) {
                            OptiFineConfig.warn("ConnectedTextures file not found: " + text);
                            if (i == 0) {
                                break label50;
                            }
                        }
                    } catch (FileNotFoundException filenotfoundexception1) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(
                                method_fd3f0eb1dd2c178d63ce(filenotfoundexception1));
                    }

                    Properties properties = new Properties();
                    properties.load(inputstream);
                    ConnectedProperties connectedProperties =
                            new ConnectedProperties(properties, text);

                    try {
                        if (false)
                            throw (java.io.FileNotFoundException)
                                    null; // Original JVM checked handler; no emitted instructions.
                        if (connectedProperties.isValid(text)) {
                            connectedProperties.updateIcons(textureMap);
                            addToTileList(connectedProperties, list);
                            addToBlockList(connectedProperties, listValue);
                        }
                    } catch (FileNotFoundException filenotfoundexception) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(
                                method_fd3f0eb1dd2c178d63ce(filenotfoundexception));
                    }
                }
            } catch (FileNotFoundException filenotfoundexception2) {
                OptiFineConfig.warn("ConnectedTextures file not found: " + text);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        optifine.ConnectedTextures.blockProperties = propertyListToArray(listValue);
        optifine.ConnectedTextures.tileProperties = propertyListToArray(list);
        optifine.ConnectedTextures.multipass = detectMultipass();
        OptiFineConfig.dbg("Multipass connected textures: " + optifine.ConnectedTextures.multipass);
    }

    private static boolean isNeighbour(
            ConnectedProperties connectedProperties,
            IBlockAccess iBlockAccess,
            BlockState blockState,
            BlockPos blockPos,
            int i,
            TextureAtlasSprite textureAtlasSprite,
            int j) {
        BlockState blockStateValue = iBlockAccess.getBlockState(blockPos);
        if (blockState == blockStateValue) {
            return true;
        }

        if (connectedProperties.connect == 2) {
            if (blockStateValue == null) {
                return false;
            }

            if (blockStateValue == optifine.ConnectedTextures.AIR_DEFAULT_STATE) {
                return false;
            }

            TextureAtlasSprite textureAtlasSpriteValue =
                    getNeighbourIcon(iBlockAccess, blockPos, blockStateValue, i);
            return textureAtlasSpriteValue == textureAtlasSprite;
        } else {
            return connectedProperties.connect == 3
                    ? (blockStateValue == null
                            ? false
                            : (blockStateValue == optifine.ConnectedTextures.AIR_DEFAULT_STATE
                                    ? false
                                    : blockStateValue.getBlock().getMaterial()
                                            == blockState.getBlock().getMaterial()))
                    : false;
        }
    }

    private static BakedQuad getQuad(
            TextureAtlasSprite textureAtlasSprite,
            Block block,
            BlockState blockState,
            BakedQuad bakedQuad) {
        if (optifine.ConnectedTextures.spriteQuadMaps == null) {
            return bakedQuad;
        }

        int indexInMap = textureAtlasSprite.getIndexInMap();
        if (indexInMap >= 0 && indexInMap < optifine.ConnectedTextures.spriteQuadMaps.length) {
            Map map = optifine.ConnectedTextures.spriteQuadMaps[indexInMap];
            if (map == null) {
                map = new IdentityHashMap(1);
                optifine.ConnectedTextures.spriteQuadMaps[indexInMap] = map;
            }

            BakedQuad bakedQuadValue = (BakedQuad) map.get(bakedQuad);
            if (bakedQuadValue == null) {
                bakedQuadValue = makeSpriteQuad(bakedQuad, textureAtlasSprite);
                map.put(bakedQuad, bakedQuadValue);
            }

            return bakedQuadValue;
        } else {
            return bakedQuad;
        }
    }

    public static void method_33ebcaa086ed5d44dbe2(TextureMap textureMap) {
        optifine.ConnectedTextures.blockProperties = (ConnectedProperties[][]) null;
        int k = ReflectorMethod.f();
        optifine.ConnectedTextures.tileProperties = (ConnectedProperties[][]) null;
        int i = k;
        optifine.ConnectedTextures.spriteQuadMaps = null;
        if (OptiFineConfig.isConnectedTextures()) {
            IResourcePack[] iResourcePackArray = OptiFineConfig.getResourcePacks();
            int j = iResourcePackArray.length - 1;

            while (j >= 0) {
                IResourcePack iResourcePack = iResourcePackArray[j];
                updateIcons(textureMap, iResourcePack);
                j--;
                if (i != 0) {
                    break;
                }
            }

            updateIcons(textureMap, OptiFineConfig.getDefaultResourcePack());
            ResourceLocation resourceLocation = new ResourceLocation("mcpatcher/ctm/default/empty");
            optifine.ConnectedTextures.emptySprite = textureMap.registerSprite(resourceLocation);
            optifine.ConnectedTextures.spriteQuadMaps =
                    new Map[textureMap.getRegisteredSpriteCount() + 1];
            if (optifine.ConnectedTextures.blockProperties.length <= 0) {
                optifine.ConnectedTextures.blockProperties = (ConnectedProperties[][]) null;
            }

            if (optifine.ConnectedTextures.tileProperties.length <= 0) {
                optifine.ConnectedTextures.tileProperties = (ConnectedProperties[][]) null;
            }
        }
    }

    private static boolean detectMultipass() {
        ArrayList arrayList = new ArrayList();
        int l = ReflectorMethod.f();
        int j = 0;
        int i = l;

        while (j < optifine.ConnectedTextures.tileProperties.length) {
            ConnectedProperties[] connectedPropertiesArray =
                    optifine.ConnectedTextures.tileProperties[j];
            if (connectedPropertiesArray != null) {
                arrayList.addAll(Arrays.asList(connectedPropertiesArray));
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        j = 0;

        while (j < optifine.ConnectedTextures.blockProperties.length) {
            ConnectedProperties[] connectedPropertiesArrayValue =
                    optifine.ConnectedTextures.blockProperties[j];
            if (connectedPropertiesArrayValue != null) {
                arrayList.addAll(Arrays.asList(connectedPropertiesArrayValue));
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        ConnectedProperties[] connectedPropertiesArrayValue2 =
                ((optifine.ConnectedProperties[])
                        arrayList.toArray(new ConnectedProperties[arrayList.size()]));
        HashSet hashset = new HashSet();
        HashSet hashset1 = new HashSet();
        int k = 0;

        while (k < connectedPropertiesArrayValue2.length) {
            ConnectedProperties connectedProperties = connectedPropertiesArrayValue2[k];
            if (connectedProperties.matchTileIcons != null) {
                hashset.addAll(Arrays.asList(connectedProperties.matchTileIcons));
            }

            if (connectedProperties.tileIcons != null) {
                hashset1.addAll(Arrays.asList(connectedProperties.tileIcons));
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        hashset.retainAll(hashset1);
        return !hashset.isEmpty();
    }

    private static Facing getFacing(int p171_0) {
        switch (p171_0) {
            case 0:
                return net.minecraft.util.Facing.DOWN;
            case 1:
                return net.minecraft.util.Facing.UP;
            case 2:
                return net.minecraft.util.Facing.NORTH;
            case 3:
                return net.minecraft.util.Facing.SOUTH;
            case 4:
                return net.minecraft.util.Facing.WEST;
            case 5:
                return net.minecraft.util.Facing.EAST;
            default:
                return net.minecraft.util.Facing.UP;
        }
    }

    public static TextureAtlasSprite getCtmTexture(
            ConnectedProperties connectedProperties,
            int p171_1,
            TextureAtlasSprite textureAtlasSprite) {
        if (connectedProperties.method != 1) {
            return textureAtlasSprite;
        } else if (p171_1 >= 0 && p171_1 < optifine.ConnectedTextures.ctmIndexes.length) {
            int j = optifine.ConnectedTextures.ctmIndexes[p171_1];
            net.minecraft.client.renderer.texture.TextureAtlasSprite[] textureAtlasSpriteArray =
                    connectedProperties.tileIcons;
            return j >= 0 && j < textureAtlasSpriteArray.length
                    ? textureAtlasSpriteArray[j]
                    : textureAtlasSprite;
        } else {
            return textureAtlasSprite;
        }
    }

    private static TextureAtlasSprite getConnectedTextureMultiPass(
            IBlockAccess iBlockAccess,
            BlockState blockState,
            BlockPos blockPos,
            Facing facing,
            TextureAtlasSprite textureAtlasSprite,
            RenderEnv renderEnv) {
        int k = ReflectorMethod.f();
        TextureAtlasSprite textureAtlasSpriteValue =
                getConnectedTextureSingle(
                        iBlockAccess,
                        blockState,
                        blockPos,
                        facing,
                        textureAtlasSprite,
                        true,
                        renderEnv);
        int i = k;
        if (!optifine.ConnectedTextures.multipass) {
            return textureAtlasSpriteValue;
        }

        if (textureAtlasSpriteValue == textureAtlasSprite) {
            return textureAtlasSpriteValue;
        }

        TextureAtlasSprite textureAtlasSpriteValue2 = textureAtlasSpriteValue;
        int j = 0;

        while (j < 3) {
            TextureAtlasSprite textureAtlasSpriteValue3 =
                    getConnectedTextureSingle(
                            iBlockAccess,
                            blockState,
                            blockPos,
                            facing,
                            textureAtlasSpriteValue2,
                            false,
                            renderEnv);
            if (textureAtlasSpriteValue3 == textureAtlasSpriteValue2 && i == 0) {
                break;
            }

            textureAtlasSpriteValue2 = textureAtlasSpriteValue3;
            j++;
            if (i != 0) {
                break;
            }
        }

        return textureAtlasSpriteValue2;
    }

    private static void method_da74056da9488939287a(TextureMap textureMap) {}

    public static int getPaneTextureIndex(
            boolean p171_0, boolean p171_1, boolean p171_2, boolean p171_3) {
        return p171_1 && p171_0
                ? (p171_2 ? (p171_3 ? 34 : 50) : (p171_3 ? 18 : 2))
                : (p171_1 && !p171_0
                        ? (p171_2 ? (p171_3 ? 35 : 51) : (p171_3 ? 19 : 3))
                        : (!p171_1 && p171_0
                                ? (p171_2 ? (p171_3 ? 33 : 49) : (p171_3 ? 17 : 1))
                                : (p171_2 ? (p171_3 ? 32 : 48) : (p171_3 ? 16 : 0))));
    }

    private static int getWoodAxis(int p171_0, int p171_1) {
        int k = (p171_1 & 12) >> 2;
        switch (k) {
            case 1:
                return 2;
            case 2:
                return 1;
            default:
                return 0;
        }
    }

    public static int getReversePaneTextureIndex(int p171_0) {
        int j = p171_0 % 16;
        return j == 1 ? p171_0 + 2 : (j == 3 ? p171_0 - 2 : p171_0);
    }

    private static ConnectedProperties[][] propertyListToArray(List list) {
        int k = ReflectorMethod.f();
        ConnectedProperties[][] connectedPropertiesArrayArray =
                new ConnectedProperties[list.size()][];
        int i = k;
        int j = 0;

        while (j < list.size()) {
            List listValue = (List) list.get(j);
            if (listValue != null) {
                ConnectedProperties[] connectedPropertiesArray =
                        ((optifine.ConnectedProperties[])
                                listValue.toArray(new ConnectedProperties[listValue.size()]));
                connectedPropertiesArrayArray[j] = connectedPropertiesArray;
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return connectedPropertiesArrayArray;
    }

    private static void fixVertex(
            int[] p171_0,
            int p171_1,
            TextureAtlasSprite textureAtlasSprite,
            TextureAtlasSprite textureAtlasSpriteValue) {
        int j = p171_0.length / 4;
        int k = j * p171_1;
        float f = java.lang.Float.intBitsToFloat(p171_0[k + 4]);
        float f1 = java.lang.Float.intBitsToFloat(p171_0[k + 4 + 1]);
        double spriteU16 = textureAtlasSprite.getSpriteU16(f);
        double spriteV16 = textureAtlasSprite.getSpriteV16(f1);
        p171_0[k + 4] =
                java.lang.Float.floatToRawIntBits(
                        textureAtlasSpriteValue.getInterpolatedU(spriteU16));
        p171_0[k + 4 + 1] =
                java.lang.Float.floatToRawIntBits(
                        textureAtlasSpriteValue.getInterpolatedV(spriteV16));
    }

    private static int getQuartzAxis(int p171_0, int p171_1) {
        switch (p171_1) {
            case 3:
                return 2;
            case 4:
                return 1;
            default:
                return 0;
        }
    }

    // region String decoding support

    // endregion

    public static synchronized BakedQuad getConnectedTexture(
            IBlockAccess iBlockAccess,
            BlockState blockState,
            BlockPos blockPos,
            BakedQuad bakedQuad,
            RenderEnv renderEnv) {
        TextureAtlasSprite textureAtlasSprite = bakedQuad.getSprite();
        if (textureAtlasSprite == null) {
            return bakedQuad;
        }

        Block block = blockState.getBlock();
        Facing facing = bakedQuad.getFace();
        if (block instanceof BlockPane
                && textureAtlasSprite.getIconName().startsWith("minecraft:blocks/glass_pane_top")) {
            BlockState blockStateValue =
                    iBlockAccess.getBlockState(blockPos.offset(bakedQuad.getFace()));
            if (blockStateValue == blockState) {
                return getQuad(
                        optifine.ConnectedTextures.emptySprite, block, blockState, bakedQuad);
            }
        }

        TextureAtlasSprite textureAtlasSpriteValue =
                getConnectedTextureMultiPass(
                        iBlockAccess, blockState, blockPos, facing, textureAtlasSprite, renderEnv);
        return textureAtlasSpriteValue == textureAtlasSprite
                ? bakedQuad
                : getQuad(textureAtlasSpriteValue, block, blockState, bakedQuad);
    }

    private static TextureAtlasSprite getConnectedTextureRepeat(
            ConnectedProperties connectedProperties, BlockPos blockPos, int i) {
        int j = ReflectorMethod.cR();
        if (connectedProperties.tileIcons.length == 1) {
            return connectedProperties.tileIcons[0];
        }

        int k = blockPos.getX();
        int l = blockPos.getY();
        int z = blockPos.getZ();
        int j1 = 0;
        int k1 = 0;
        switch (i) {
            case 0:
                j1 = k;
                k1 = z;
                if (j != 0) {
                    break;
                }
            case 1:
                j1 = k;
                k1 = z;
                if (j != 0) {
                    break;
                }
            case 2:
                j1 = -k - 1;
                k1 = -l;
                if (j != 0) {
                    break;
                }
            case 3:
                j1 = k;
                k1 = -l;
                if (j != 0) {
                    break;
                }
            case 4:
                j1 = z;
                k1 = -l;
                if (j != 0) {
                    break;
                }
            case 5:
                j1 = -z - 1;
                k1 = -l;
        }

        j1 %= connectedProperties.width;
        k1 %= connectedProperties.height;
        if (j1 < 0) {
            j1 += connectedProperties.width;
        }

        if (k1 < 0) {
            k1 += connectedProperties.height;
        }

        int l1 = k1 * connectedProperties.width + j1;
        return connectedProperties.tileIcons[l1];
    }

    private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(
            ConnectedProperties connectedProperties,
            IBlockAccess iBlockAccess,
            BlockState blockState,
            BlockPos blockPos,
            int p171_4,
            int p171_5,
            TextureAtlasSprite textureAtlasSprite,
            int p171_7) {
        net.minecraft.client.renderer.texture.TextureAtlasSprite[] textureAtlasSpriteArray =
                connectedProperties.tileIcons;
        net.minecraft.client.renderer.texture.TextureAtlasSprite textureAtlasSpriteValue =
                optifine.ConnectedTextures.getConnectedTextureVerticalBoth(
                        connectedProperties,
                        iBlockAccess,
                        blockState,
                        blockPos,
                        p171_4,
                        p171_5,
                        textureAtlasSprite,
                        p171_7);
        if (textureAtlasSpriteValue != null
                && textureAtlasSpriteValue != textureAtlasSprite
                && textureAtlasSpriteValue != textureAtlasSpriteArray[3]) {
            return textureAtlasSpriteValue;
        }

        net.minecraft.client.renderer.texture.TextureAtlasSprite textureAtlasSpriteValue2 =
                optifine.ConnectedTextures.getConnectedTextureHorizontal(
                        connectedProperties,
                        iBlockAccess,
                        blockState,
                        blockPos,
                        p171_4,
                        p171_5,
                        textureAtlasSprite,
                        p171_7);
        return textureAtlasSpriteValue2 == textureAtlasSpriteArray[0]
                ? textureAtlasSpriteArray[4]
                : (textureAtlasSpriteValue2 == textureAtlasSpriteArray[1]
                        ? textureAtlasSpriteArray[5]
                        : (textureAtlasSpriteValue2 == textureAtlasSpriteArray[2]
                                ? textureAtlasSpriteArray[6]
                                : textureAtlasSpriteValue2));
    }

    private static void addToList(ConnectedProperties connectedProperties, List list, int i) {
        int j = ReflectorMethod.f();

        while (i >= list.size()) {
            list.add(null);
            if (j != 0) {
                break;
            }
        }

        List listValue = (List) list.get(i);
        if (listValue == null) {
            listValue = new ArrayList();
            list.set(i, listValue);
        }

        listValue.add(connectedProperties);
    }

    private static List makePropertyList(ConnectedProperties[][] connectedPropertiesArrayArray) {
        int k = optifine.ReflectorMethod.cR();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i = k;
        if (connectedPropertiesArrayArray != null) {
            int j = 0;

            while (j < connectedPropertiesArrayArray.length) {
                optifine.ConnectedProperties[] connectedPropertiesArray =
                        connectedPropertiesArrayArray[j];
                java.util.ArrayList arrayListValue = null;
                if (connectedPropertiesArray != null) {
                    arrayListValue =
                            new java.util.ArrayList<>(
                                    java.util.Arrays.asList(connectedPropertiesArray));
                }

                arrayList.add(arrayListValue);
                j++;
                if (i == 0) {
                    break;
                }
            }
        }

        return arrayList;
    }
}
