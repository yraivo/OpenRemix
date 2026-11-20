package optifine;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class NaturalTextures {

    private static String[] packedTextFragments;
    private static NaturalProperties[] propertiesByIndex;

    public static NaturalProperties getNaturalProperties(TextureAtlasSprite textureAtlasSprite) {
        if (!(textureAtlasSprite instanceof TextureAtlasSprite)) {
            return null;
        }

        int indexInMap = textureAtlasSprite.getIndexInMap();
        return indexInMap >= 0 && indexInMap < optifine.NaturalTextures.propertiesByIndex.length
                ? optifine.NaturalTextures.propertiesByIndex[indexInMap]
                : null;
    }

    public static BakedQuad getNaturalTexture(BlockPos blockPos, BakedQuad bakedQuad) {
        TextureAtlasSprite textureAtlasSprite = bakedQuad.getSprite();
        if (textureAtlasSprite == null) {
            return bakedQuad;
        }

        NaturalProperties naturalProperties = getNaturalProperties(textureAtlasSprite);
        if (naturalProperties == null) {
            return bakedQuad;
        }

        int side = ConnectedTextures.getSide(bakedQuad.getFace());
        int j = OptiFineConfig.getRandom(blockPos, side);
        int k = 0;
        byte b0 = 0;
        if (naturalProperties.rotation > 1) {
            k = j & 3;
        }

        if (naturalProperties.rotation == 2) {
            k = k / 2 * 2;
        }

        if (naturalProperties.flip) {
            b0 = (byte) ((j & 4) != 0 ? 1 : 0);
        }

        return naturalProperties.getQuad(
                bakedQuad, k, remix.compat.compiler.JvmBooleanSlots.fromInt(b0));
    }

    public static void update() {
        int l = ReflectorMethod.f();
        optifine.NaturalTextures.propertiesByIndex = new NaturalProperties[0];
        int i = l;
        if (OptiFineConfig.isNaturalTextures()) {
            String text = "optifine/natural.properties";

            try {
                ResourceLocation resourceLocation = new ResourceLocation(text);
                if (!OptiFineConfig.hasResource(resourceLocation)) {
                    OptiFineConfig.dbg("NaturalTextures: configuration \"" + text + "\" not found");
                    return;
                }

                boolean flag;
                ArrayList arrayList;
                String[] astring;
                {
                    flag = OptiFineConfig.isFromDefaultResourcePack(resourceLocation);
                    InputStream inputstream = OptiFineConfig.getResourceStream(resourceLocation);
                    arrayList = new ArrayList(256);
                    String textValue = OptiFineConfig.readInputStream(inputstream);
                    inputstream.close();
                    astring = OptiFineConfig.tokenize(textValue, "\n\r");
                    if (flag) {
                        OptiFineConfig.dbg(
                                "Natural Textures: Parsing default configuration \""
                                        + text
                                        + packedTextFragments[0]);
                        OptiFineConfig.dbg(
                                "Natural Textures: Valid only for textures from default resource"
                                    + " pack");
                        if (!(i == 0)) {

                            OptiFineConfig.dbg(
                                    "Natural Textures: Parsing configuration \""
                                            + text
                                            + packedTextFragments[1]);
                        }
                    } else {

                        OptiFineConfig.dbg(
                                "Natural Textures: Parsing configuration \""
                                        + text
                                        + packedTextFragments[1]);
                    }
                }

                TextureMap textureMap = TextureUtils.getTextureMapBlocks();
                int j = 0;

                while (j < astring.length) {
                    String textValue2 = astring[j].trim();
                    label66:
                    if (!textValue2.startsWith(packedTextFragments[2])) {
                        String[] astring1 =
                                OptiFineConfig.tokenize(textValue2, packedTextFragments[3]);
                        if (astring1.length != 2) {
                            OptiFineConfig.warn(
                                    "Natural Textures: Invalid \""
                                            + text
                                            + "\" line: "
                                            + textValue2);
                            if (i == 0) {
                                break label66;
                            }
                        }

                        String textValue3 = astring1[0].trim();
                        String textValue4 = astring1[1].trim();
                        TextureAtlasSprite textureAtlasSprite =
                                textureMap.getSpriteSafe("minecraft:blocks/" + textValue3);
                        if (textureAtlasSprite == null) {
                            OptiFineConfig.warn(
                                    "Natural Textures: Texture not found: \""
                                            + text
                                            + "\" line: "
                                            + textValue2);
                            if (i == 0) {
                                break label66;
                            }
                        }

                        int k = textureAtlasSprite.getIndexInMap();
                        if (k < 0) {
                            OptiFineConfig.warn(
                                    "Natural Textures: Invalid \""
                                            + text
                                            + "\" line: "
                                            + textValue2);
                            if (i == 0) {
                                break label66;
                            }
                        }

                        if (flag
                                && !OptiFineConfig.isFromDefaultResourcePack(
                                        new ResourceLocation(
                                                "textures/blocks/" + textValue3 + ".png"))) {
                            return;
                        }

                        NaturalProperties naturalProperties = new NaturalProperties(textValue4);
                        if (naturalProperties.isValid()) {
                            while (arrayList.size() <= k) {
                                arrayList.add(null);
                                if (i != 0) {
                                    break;
                                }
                            }

                            arrayList.set(k, naturalProperties);
                            OptiFineConfig.dbg(
                                    "NaturalTextures: " + textValue3 + " = " + textValue4);
                        }
                    }

                    j++;
                    if (i != 0) {
                        break;
                    }
                }

                optifine.NaturalTextures.propertiesByIndex =
                        ((optifine.NaturalProperties[])
                                arrayList.toArray(new NaturalProperties[arrayList.size()]));
            } catch (FileNotFoundException filenotfoundexception) {
                OptiFineConfig.warn("NaturalTextures: configuration \"" + text + "\" not found");
                return;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    static {
        optifine.NaturalTextures.packedTextFragments = new String[6];
        optifine.NaturalTextures.packedTextFragments[0] = new String("\"");
        optifine.NaturalTextures.packedTextFragments[1] = new String("\"");
        optifine.NaturalTextures.packedTextFragments[2] = new String("#");
        optifine.NaturalTextures.packedTextFragments[3] = new String("=");
        optifine.NaturalTextures.propertiesByIndex = new NaturalProperties[0];
    }

    private static FileNotFoundException method_fd3f0eb1dd2c178d63ce(
            FileNotFoundException filenotfoundexception) {
        return filenotfoundexception;
    }
}
