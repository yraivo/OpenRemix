package optifine;

import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.biome.BiomeGenBase;

public class Matches {
    public static boolean biome(BiomeGenBase biomeGenBase, BiomeGenBase[] biomeGenBaseArray) {
        int i = ReflectorMethod.f();
        if (biomeGenBaseArray == null) {
            return true;
        }

        int j = 0;

        while (j < biomeGenBaseArray.length) {
            if (biomeGenBaseArray[j] == biomeGenBase) {
                return true;
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return false;
    }

    public static boolean block(int i, int j, MatchBlock[] matchBlockArray) {
        int k = ReflectorMethod.f();
        if (matchBlockArray == null) {
            return true;
        }

        int l = 0;

        while (l < matchBlockArray.length) {
            MatchBlock matchBlock = matchBlockArray[l];
            if (matchBlock.matches(i, j)) {
                return true;
            }

            l++;
            if (k != 0) {
                break;
            }
        }

        return false;
    }

    public static boolean sprite(
            TextureAtlasSprite textureAtlasSprite, TextureAtlasSprite[] textureAtlasSpriteArray) {
        int i = optifine.ReflectorMethod.cR();
        if (textureAtlasSpriteArray == null) {
            return true;
        }

        int j = 0;

        while (j < textureAtlasSpriteArray.length) {
            if (textureAtlasSpriteArray[j] == textureAtlasSprite) {
                return true;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return false;
    }

    public static boolean blockId(int p171_0, MatchBlock[] matchBlockArray) {
        int j = optifine.ReflectorMethod.f();
        if (matchBlockArray == null) {
            return true;
        }

        int k = 0;

        while (k < matchBlockArray.length) {
            optifine.MatchBlock matchBlock = matchBlockArray[k];
            if (matchBlock.getBlockId() == p171_0) {
                return true;
            }

            k++;
            if (j != 0) {
                break;
            }
        }

        return false;
    }

    public static boolean block(BlockStateBase blockStateBase, MatchBlock[] matchBlockArray) {
        int i = optifine.ReflectorMethod.cR();
        if (matchBlockArray == null) {
            return true;
        }

        int j = 0;

        while (j < matchBlockArray.length) {
            optifine.MatchBlock matchBlock = matchBlockArray[j];
            if (matchBlock.matches(blockStateBase)) {
                return true;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return false;
    }

    public static boolean metadata(int i, int[] aint) {
        int j = ReflectorMethod.cR();
        if (aint == null) {
            return true;
        }

        int k = 0;

        while (k < aint.length) {
            if (aint[k] == i) {
                return true;
            }

            k++;
            if (j == 0) {
                break;
            }
        }

        return false;
    }
}
