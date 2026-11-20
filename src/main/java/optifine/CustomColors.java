package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

public class CustomColors {
    private static CustomColormap[] lightMapsColorsRgb;
    private static int[] mapColorsOriginal;
    private static CustomColormap stemPumpkinColors;
    private static CustomColormap waterColors;
    private static CustomColormap foliageBirchColors;
    private static CustomColormap xpOrbColors;
    private static CustomColormap swampGrassColors;
    private static BlockState BLOCK_STATE_DIRT;
    private static int[] potionColors;
    private static CustomColorFader underwaterColorFader;
    private static int bossTextColor;
    private static CustomColorizer COLORIZER_FOLIAGE_BIRCH;
    private static float[][] sheepColors;
    private static CustomColormap redstoneColors;
    private static CustomColorFader skyColorFader;
    private static Vec3 fogColorEnd;
    private static CustomColormap myceliumParticleColors;
    private static CustomColormap underwaterColors;
    private static CustomColorizer COLORIZER_FOLIAGE;
    private static CustomColormap stemMelonColors;
    private static CustomColorizer COLORIZER_GRASS;
    private static float[][] torchRgbs;
    private static int particleWaterColor;
    private static CustomColorizer COLORIZER_FOLIAGE_PINE;
    private static int signTextColor;
    private static int expBarTextColor;
    private static Vec3 skyColorEnd;

    private static CustomColormap foliagePineColors;
    public static Random random;
    private static boolean useDefaultGrassFoliageColors;
    private static int[] spawnEggSecondaryColors;
    private static int lilyPadColor;
    private static CustomColormap skyColors;
    private static CustomColorFader fogColorFader;
    private static CustomColorizer COLORIZER_WATER;
    private static int[] textColors;
    private static Vec3 fogColorNether;

    private static int[] spawnEggPrimaryColors;
    private static CustomColormap swampFoliageColors;
    private static CustomColormap stemColors;
    private static int lightmapMinDimensionId;
    private static float[][] sunRgbs;
    private static float[][] wolfCollarColors;
    private static int particlePortalColor;
    private static CustomColormap fogColors;
    private static CustomColormap[][] blockColormaps;
    private static BlockState BLOCK_STATE_WATER;
    private static CustomColormap[] colorsBlockColormaps;

    private static Pair<CustomColormap[], Integer> parseLightMapPacks() {
        String text = "mcpatcher/lightmap/world";
        String textValue = ".png";
        String[] astring = ResUtils.collectFiles(text, textValue);
        HashMap hashmap = new HashMap();
        int i2 = ReflectorMethod.cR();
        int k = 0;
        int i = i2;

        while (k < astring.length) {
            {
                String textValue2 = astring[k];
                String textValue3 = StrUtils.removePrefixSuffix(textValue2, text, textValue);
                int j = OptiFineConfig.parseInt(textValue3, Integer.MIN_VALUE);
                if (j == Integer.MIN_VALUE) {
                    warn("Invalid dimension ID: " + textValue3 + ", path: " + textValue2);
                    if (!(i != 0)) {

                        hashmap.put(j, textValue2);
                    }
                } else {

                    hashmap.put(j, textValue2);
                }
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        Set set = hashmap.keySet();
        Integer[] ainteger = ((java.lang.Integer[]) set.toArray(new Integer[set.size()]));
        Arrays.sort(ainteger);
        if (ainteger.length <= 0) {
            return new ImmutablePair(null, 0);
        }

        int l1 = ainteger[0];
        int k1 = ainteger[ainteger.length - 1];
        int l = k1 - l1 + 1;
        CustomColormap[] customColormapArray = new CustomColormap[l];
        int i1 = 0;

        while (i1 < ainteger.length) {
            Integer integer = ainteger[i1];
            String textValue4 = (String) hashmap.get(integer);
            CustomColormap customColormap = getCustomColors(textValue4, -1, -1);
            if (customColormap != null) {
                if (customColormap.getWidth() < 16) {
                    warn(
                            "Invalid lightmap width: "
                                    + customColormap.getWidth()
                                    + ", path: "
                                    + textValue4);
                    if (!(i != 0)) {

                        int j1 = integer - l1;
                        customColormapArray[j1] = customColormap;
                    }
                } else {

                    int j1 = integer - l1;
                    customColormapArray[j1] = customColormap;
                }
            }

            i1++;
            if (i == 0) {
                break;
            }
        }

        return new ImmutablePair(customColormapArray, l1);
    }

    public static void updatePortalFX(EntityFX entityFX) {
        if (optifine.CustomColors.particlePortalColor >= 0) {
            int particlePortalColorValue = optifine.CustomColors.particlePortalColor;
            int j = particlePortalColorValue >> 16 & 0xFF;
            int k = particlePortalColorValue >> 8 & 0xFF;
            int l = particlePortalColorValue & 0xFF;
            float f = j / 255.0F;
            float f1 = k / 255.0F;
            float f2 = l / 255.0F;
            entityFX.setRBGColorF(f, f1, f2);
        }
    }

    public static Vec3 getWorldFogColor(
            Vec3 vec3, ClientWorld clientWorld, Entity entity, float f) {
        int k = ReflectorMethod.cR();
        int j = clientWorld.provider.getDimensionId();
        int i = k;
        switch (j) {
            case 0:
                MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
                vec3 =
                        getSkyColor(
                                vec3,
                                minecraftClient.world,
                                entity.posX,
                                entity.posY + 1.0,
                                entity.posZ);
                if (i != 0) {
                    break;
                }
            case 1:
                vec3 = getSkyColorEnd(vec3);
        }

        return vec3;
    }

    public static int getFluidColor(
            IBlockAccess iBlockAccess,
            BlockState blockState,
            BlockPos blockPos,
            RenderEnv renderEnv) {
        Block block = blockState.getBlock();
        Object object = getBlockColormap(blockState);
        if (object == null && block.getMaterial() == Material.water) {
            object = optifine.CustomColors.COLORIZER_WATER;
        }

        return object == null
                ? block.colorMultiplier(iBlockAccess, blockPos)
                : (OptiFineConfig.isSmoothBiomes() && !((CustomColorizer) object).isColorConstant()
                        ? getSmoothColorMultiplier(
                                iBlockAccess,
                                blockPos,
                                (CustomColorizer) object,
                                renderEnv.getColorizerBlockPosM())
                        : ((CustomColorizer) object).getColor(iBlockAccess, blockPos));
    }

    private static void dbg(String text) {
        OptiFineConfig.dbg("CustomColors: " + text);
    }

    public static boolean updateLightmap(World world, float f, int[] aint, boolean flag) {
        int i = ReflectorMethod.cR();
        if (world == null) {
            return false;
        }

        if (optifine.CustomColors.lightMapsColorsRgb == null) {
            return false;
        }

        int j = world.provider.getDimensionId();
        int k = j - optifine.CustomColors.lightmapMinDimensionId;
        if (k >= 0 && k < optifine.CustomColors.lightMapsColorsRgb.length) {
            CustomColormap customColormap = optifine.CustomColors.lightMapsColorsRgb[k];
            if (customColormap == null) {
                return false;
            }

            int l = customColormap.getHeight();
            if (flag && l < 64) {
                return false;
            }

            int width = customColormap.getWidth();
            if (width < 16) {
                warn("Invalid lightmap width: " + width + " for dimension: " + j);
                optifine.CustomColors.lightMapsColorsRgb[k] = null;
                return false;
            }

            int j1 = 0;
            if (flag) {
                j1 = width * 16 * 2;
            }

            float f1 = 1.1666666F * (world.getSunBrightness(1.0F) - 0.2F);
            if (world.getLastLightningBolt() > 0) {
                f1 = 1.0F;
            }

            f1 = OptiFineConfig.limitTo1(f1);
            float f2 = f1 * (width - 1);
            float f3 = OptiFineConfig.limitTo1(f + 0.5F) * (width - 1);
            float f4 = OptiFineConfig.limitTo1(OptiFineConfig.getGameSettings().gammaSetting);
            int k1 = f4 > 1.0E-4F ? 1 : 0;
            float[][] afloat = customColormap.getColorsRgb();
            getLightMapColumn(afloat, f2, j1, width, optifine.CustomColors.sunRgbs);
            getLightMapColumn(afloat, f3, j1 + 16 * width, width, optifine.CustomColors.torchRgbs);
            float[] afloat1 = new float[3];
            int l1 = 0;

            while (l1 < 16) {
                int i2 = 0;

                while (true) {
                    if (i2 < 16) {
                        int j2 = 0;

                        while (j2 < 3) {
                            float f5 =
                                    OptiFineConfig.limitTo1(
                                            optifine.CustomColors.sunRgbs[l1][j2]
                                                    + optifine.CustomColors.torchRgbs[i2][j2]);
                            if (k1 != 0) {
                                float f6 = 1.0F - f5;
                                f6 = 1.0F - f6 * f6 * f6 * f6;
                                f5 = f4 * f6 + (1.0F - f4) * f5;
                            }

                            afloat1[j2] = f5;
                            j2++;
                            if (i == 0) {
                                break;
                            }
                        }

                        j2 = (int) (afloat1[0] * 255.0F);
                        int k2 = (int) (afloat1[1] * 255.0F);
                        int l2 = (int) (afloat1[2] * 255.0F);
                        aint[l1 * 16 + i2] = 0xFF000000 | j2 << 16 | k2 << 8 | l2;
                        i2++;
                        if (i != 0) {
                            continue;
                        }
                    }

                    l1++;
                    if (i == 0) {
                        return true;
                    }
                    break;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static void updateWaterFX(
            EntityFX entityFX,
            IBlockAccess iBlockAccess,
            double p171_2,
            double p171_3,
            double p171_4) {
        try {
            if (optifine.CustomColors.waterColors == null
                    && optifine.CustomColors.blockColormaps == null) {
                return;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception));
        }

        net.minecraft.util.BlockPos blockPos =
                new net.minecraft.util.BlockPos(p171_2, p171_3, p171_4);
        optifine.RenderEnv instance =
                optifine.RenderEnv.getInstance(
                        iBlockAccess, optifine.CustomColors.BLOCK_STATE_WATER, blockPos);
        int i =
                optifine.CustomColors.getFluidColor(
                        iBlockAccess, optifine.CustomColors.BLOCK_STATE_WATER, blockPos, instance);
        int j = i >> 16 & 0xFF;
        int k = i >> 8 & 0xFF;
        int l = i & 0xFF;
        float f = j / 255.0F;
        float f1 = k / 255.0F;
        float f2 = l / 255.0F;
        if (optifine.CustomColors.particleWaterColor >= 0) {
            int i1 = optifine.CustomColors.particleWaterColor >> 16 & 0xFF;
            int j1 = optifine.CustomColors.particleWaterColor >> 8 & 0xFF;
            int k1 = optifine.CustomColors.particleWaterColor & 0xFF;
            f *= i1 / 255.0F;
            f1 *= j1 / 255.0F;
            f2 *= k1 / 255.0F;
        }

        entityFX.setRBGColorF(f, f1, f2);
    }

    private static int getPotionId(String text) {
        int i = ReflectorMethod.f();
        if (text.equals("potion.water")) {
            return 0;
        }

        Potion[] bzT = Potion.potionTypes;
        int j = 0;

        while (j < bzT.length) {
            Potion potion = bzT[j];
            if (potion != null && potion.getName().equals(text)) {
                return potion.getId();
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return -1;
    }

    public static int method_e0b8a2f32478f200146b(int p171_0, int p171_1) {
        try {
            if (optifine.CustomColors.potionColors == null) {
                return p171_1;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception));
        }

        try {
            if (p171_0 < 0 || p171_0 >= optifine.CustomColors.potionColors.length) {
                return p171_1;
            }
        } catch (java.lang.NumberFormatException numberformatexception2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception2));
        }

        int k = optifine.CustomColors.potionColors[p171_0];

        try {
            if (k < 0) {
                return p171_1;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception1));
        }

        return k;
    }

    static {
        optifine.CustomColors.waterColors = null;
        optifine.CustomColors.foliagePineColors = null;
        optifine.CustomColors.foliageBirchColors = null;
        optifine.CustomColors.swampFoliageColors = null;
        optifine.CustomColors.swampGrassColors = null;
        optifine.CustomColors.colorsBlockColormaps = null;
        optifine.CustomColors.blockColormaps = (CustomColormap[][]) null;
        optifine.CustomColors.skyColors = null;
        optifine.CustomColors.skyColorFader = new CustomColorFader();
        optifine.CustomColors.fogColors = null;
        optifine.CustomColors.fogColorFader = new CustomColorFader();
        optifine.CustomColors.underwaterColors = null;
        optifine.CustomColors.underwaterColorFader = new CustomColorFader();
        optifine.CustomColors.lightMapsColorsRgb = null;
        optifine.CustomColors.lightmapMinDimensionId = 0;
        optifine.CustomColors.sunRgbs = new float[16][3];
        optifine.CustomColors.torchRgbs = new float[16][3];
        optifine.CustomColors.redstoneColors = null;
        optifine.CustomColors.xpOrbColors = null;
        optifine.CustomColors.stemColors = null;
        optifine.CustomColors.stemMelonColors = null;
        optifine.CustomColors.stemPumpkinColors = null;
        optifine.CustomColors.myceliumParticleColors = null;
        optifine.CustomColors.useDefaultGrassFoliageColors = true;
        optifine.CustomColors.particleWaterColor = -1;
        optifine.CustomColors.particlePortalColor = -1;
        optifine.CustomColors.lilyPadColor = -1;
        optifine.CustomColors.expBarTextColor = -1;
        optifine.CustomColors.bossTextColor = -1;
        optifine.CustomColors.signTextColor = -1;
        optifine.CustomColors.fogColorNether = null;
        optifine.CustomColors.fogColorEnd = null;
        optifine.CustomColors.skyColorEnd = null;
        optifine.CustomColors.spawnEggPrimaryColors = null;
        optifine.CustomColors.spawnEggSecondaryColors = null;
        optifine.CustomColors.wolfCollarColors = (float[][]) null;
        optifine.CustomColors.sheepColors = (float[][]) null;
        optifine.CustomColors.textColors = null;
        optifine.CustomColors.mapColorsOriginal = null;
        optifine.CustomColors.potionColors = null;
        optifine.CustomColors.BLOCK_STATE_DIRT = Blocks.dirt.getDefaultState();
        optifine.CustomColors.BLOCK_STATE_WATER = Blocks.water.getDefaultState();
        optifine.CustomColors.random = new Random();
        optifine.CustomColors.COLORIZER_GRASS = new CustomColorsColorizerGrassIColorizer();
        optifine.CustomColors.COLORIZER_FOLIAGE = new CustomColorsColorizerFoliageIColorizer();
        optifine.CustomColors.COLORIZER_FOLIAGE_PINE =
                new CustomColorsColorizerFoliagePineIColorizer();
        optifine.CustomColors.COLORIZER_FOLIAGE_BIRCH =
                new CustomColorsColorizerFoliageBirchIColorizer();
        optifine.CustomColors.COLORIZER_WATER = new CustomColorsColorizerWaterIColorizer();
    }

    private static CustomColormap[] readCustomColormaps(Properties properties, String text) {
        ArrayList arrayList = new ArrayList();
        String textValue = "palette.block.";
        HashMap hashmap = new HashMap();
        int l = ReflectorMethod.cR();
        Set set = properties.keySet();
        Iterator iterator = set.iterator();
        int i = l;

        while (iterator.hasNext()) {
            String textValue2 = (String) iterator.next();
            String property = properties.getProperty(textValue2);
            if (textValue2.startsWith(textValue)) {
                hashmap.put(textValue2, property);
            }

            if (i == 0) {
                break;
            }
        }

        String[] astring =
                ((java.lang.String[]) hashmap.keySet().toArray(new String[hashmap.size()]));
        int j = 0;

        while (j < astring.length) {
            label64:
            {
                String textValue3 = astring[j];
                String propertyValue = properties.getProperty(textValue3);
                dbg("Block palette: " + textValue3 + " = " + propertyValue);
                String textValue4 = textValue3.substring(textValue.length());
                String textValue5 = TextureUtils.getBasePath(text);
                textValue4 = TextureUtils.fixResourcePath(textValue4, textValue5);
                CustomColormap customColormap = getCustomColors(textValue4, 256, 256);
                if (customColormap == null) {
                    warn("Colormap not found: " + textValue4);
                    if (i != 0) {
                        break label64;
                    }
                }

                ConnectedParser connectedParser = new ConnectedParser("CustomColors");
                MatchBlock[] matchBlockArray = connectedParser.parseMatchBlocks(propertyValue);
                if (matchBlockArray != null && matchBlockArray.length > 0) {
                    int k = 0;

                    while (k < matchBlockArray.length) {
                        MatchBlock matchBlock = matchBlockArray[k];
                        customColormap.addMatchBlock(matchBlock);
                        k++;
                        if (i == 0) {
                            break;
                        }
                    }

                    arrayList.add(customColormap);
                    if (i != 0) {
                        break label64;
                    }
                }

                warn("Invalid match blocks: " + propertyValue);
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return arrayList.size() <= 0
                ? null
                : ((optifine.CustomColormap[])
                        arrayList.toArray(new CustomColormap[arrayList.size()]));
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public static int getColorFromItemStack(ItemStack itemStack, int i, int j) {
        if (itemStack == null) {
            return j;
        }

        Item item = itemStack.getItem();
        return item == null
                ? j
                : (item instanceof ItemMonsterPlacer
                        ? getSpawnEggColor((ItemMonsterPlacer) item, itemStack, i, j)
                        : j);
    }

    public static int getColorMultiplier(
            BakedQuad bakedQuad,
            Block block,
            IBlockAccess iBlockAccess,
            BlockPos blockPos,
            RenderEnv renderEnv) {
        int i = ReflectorMethod.cR();
        if (optifine.CustomColors.blockColormaps != null) {
            BlockState blockState = renderEnv.getBlockState();
            if (!bakedQuad.hasTintIndex()) {
                if (block == Blocks.grass) {
                    blockState = optifine.CustomColors.BLOCK_STATE_DIRT;
                }

                if (block == Blocks.redstone_wire) {
                    return -1;
                }
            }

            if (block == Blocks.double_plant && renderEnv.getMetadata() >= 8) {
                blockPos = blockPos.offsetDown();
                blockState = iBlockAccess.getBlockState(blockPos);
            }

            CustomColormap customColormap = getBlockColormap(blockState);
            if (customColormap != null) {
                if (OptiFineConfig.isSmoothBiomes() && !customColormap.isColorConstant()) {
                    return getSmoothColorMultiplier(
                            iBlockAccess,
                            blockPos,
                            customColormap,
                            renderEnv.getColorizerBlockPosM());
                }

                return customColormap.getColor(iBlockAccess, blockPos);
            }
        }

        if (!bakedQuad.hasTintIndex()) {
            return -1;
        }

        if (block == Blocks.waterlily) {
            return getLilypadColorMultiplier(iBlockAccess, blockPos);
        }

        if (block == Blocks.redstone_wire) {
            return getRedstoneColor(renderEnv.getBlockState());
        }

        if (block instanceof BlockStem) {
            return getStemColorMultiplier(block, iBlockAccess, blockPos, renderEnv);
        }

        if (optifine.CustomColors.useDefaultGrassFoliageColors) {
            return -1;
        }

        int j = renderEnv.getMetadata();
        if (block != Blocks.grass && block != Blocks.tallgrass && block != Blocks.double_plant) {
            if (block == Blocks.double_plant) {
                CustomColorizer customColorizer = optifine.CustomColors.COLORIZER_GRASS;
                if (j < 8) {
                    return OptiFineConfig.isSmoothBiomes() && !customColorizer.isColorConstant()
                            ? getSmoothColorMultiplier(
                                    iBlockAccess,
                                    blockPos,
                                    customColorizer,
                                    renderEnv.getColorizerBlockPosM())
                            : customColorizer.getColor(iBlockAccess, blockPos);
                }

                blockPos = blockPos.offsetDown();
                if (i != 0) {
                    return OptiFineConfig.isSmoothBiomes() && !customColorizer.isColorConstant()
                            ? getSmoothColorMultiplier(
                                    iBlockAccess,
                                    blockPos,
                                    customColorizer,
                                    renderEnv.getColorizerBlockPosM())
                            : customColorizer.getColor(iBlockAccess, blockPos);
                }
            }

            if (block == Blocks.leaves) {
                switch (j & 3) {
                    case 0:
                        CustomColorizer customColorizerValue =
                                optifine.CustomColors.COLORIZER_FOLIAGE;
                        if (i != 0) {
                            return OptiFineConfig.isSmoothBiomes()
                                            && !customColorizerValue.isColorConstant()
                                    ? getSmoothColorMultiplier(
                                            iBlockAccess,
                                            blockPos,
                                            customColorizerValue,
                                            renderEnv.getColorizerBlockPosM())
                                    : customColorizerValue.getColor(iBlockAccess, blockPos);
                        }
                    case 1:
                        CustomColorizer customColorizerValue2 =
                                optifine.CustomColors.COLORIZER_FOLIAGE_PINE;
                        if (i != 0) {
                            return OptiFineConfig.isSmoothBiomes()
                                            && !customColorizerValue2.isColorConstant()
                                    ? getSmoothColorMultiplier(
                                            iBlockAccess,
                                            blockPos,
                                            customColorizerValue2,
                                            renderEnv.getColorizerBlockPosM())
                                    : customColorizerValue2.getColor(iBlockAccess, blockPos);
                        }
                    case 2:
                        CustomColorizer customColorizerValue3 =
                                optifine.CustomColors.COLORIZER_FOLIAGE_BIRCH;
                        if (i != 0) {
                            return OptiFineConfig.isSmoothBiomes()
                                            && !customColorizerValue3.isColorConstant()
                                    ? getSmoothColorMultiplier(
                                            iBlockAccess,
                                            blockPos,
                                            customColorizerValue3,
                                            renderEnv.getColorizerBlockPosM())
                                    : customColorizerValue3.getColor(iBlockAccess, blockPos);
                        }
                    default:
                        CustomColorizer customColorizerValue4 =
                                optifine.CustomColors.COLORIZER_FOLIAGE;
                        if (i != 0) {
                            return OptiFineConfig.isSmoothBiomes()
                                            && !customColorizerValue4.isColorConstant()
                                    ? getSmoothColorMultiplier(
                                            iBlockAccess,
                                            blockPos,
                                            customColorizerValue4,
                                            renderEnv.getColorizerBlockPosM())
                                    : customColorizerValue4.getColor(iBlockAccess, blockPos);
                        }
                }
            }

            if (block == Blocks.leaves2) {
                CustomColorizer customColorizerValue5 = optifine.CustomColors.COLORIZER_FOLIAGE;
                if (i != 0) {
                    return OptiFineConfig.isSmoothBiomes()
                                    && !customColorizerValue5.isColorConstant()
                            ? getSmoothColorMultiplier(
                                    iBlockAccess,
                                    blockPos,
                                    customColorizerValue5,
                                    renderEnv.getColorizerBlockPosM())
                            : customColorizerValue5.getColor(iBlockAccess, blockPos);
                }
            }

            if (block != Blocks.vine) {
                return -1;
            }

            CustomColorizer customColorizerValue6 = optifine.CustomColors.COLORIZER_FOLIAGE;
            if (i != 0) {
                return OptiFineConfig.isSmoothBiomes() && !customColorizerValue6.isColorConstant()
                        ? getSmoothColorMultiplier(
                                iBlockAccess,
                                blockPos,
                                customColorizerValue6,
                                renderEnv.getColorizerBlockPosM())
                        : customColorizerValue6.getColor(iBlockAccess, blockPos);
            }
        }

        CustomColorizer customColorizerValue7 = optifine.CustomColors.COLORIZER_GRASS;
        return OptiFineConfig.isSmoothBiomes() && !customColorizerValue7.isColorConstant()
                ? getSmoothColorMultiplier(
                        iBlockAccess,
                        blockPos,
                        customColorizerValue7,
                        renderEnv.getColorizerBlockPosM())
                : customColorizerValue7.getColor(iBlockAccess, blockPos);
    }

    public static int getSignTextColor(int i) {
        return optifine.CustomColors.signTextColor < 0 ? i : optifine.CustomColors.signTextColor;
    }

    private static void readColorProperties(String text) {
        try {
            ResourceLocation resourceLocation = new ResourceLocation(text);
            InputStream inputstream = OptiFineConfig.getResourceStream(resourceLocation);
            if (inputstream == null) {
                return;
            }

            dbg("Loading " + text);
            Properties properties = new Properties();
            properties.load(inputstream);
            inputstream.close();
            optifine.CustomColors.particleWaterColor =
                    readColor(properties, new String[] {"particle.water", "drop.water"});
            optifine.CustomColors.particlePortalColor = readColor(properties, "particle.portal");
            optifine.CustomColors.lilyPadColor = readColor(properties, "lilypad");
            optifine.CustomColors.expBarTextColor = readColor(properties, "text.xpbar");
            optifine.CustomColors.bossTextColor = readColor(properties, "text.boss");
            optifine.CustomColors.signTextColor = readColor(properties, "text.sign");
            optifine.CustomColors.fogColorNether = readColorVec3(properties, "fog.nether");
            optifine.CustomColors.fogColorEnd = readColorVec3(properties, "fog.end");
            optifine.CustomColors.skyColorEnd = readColorVec3(properties, "sky.end");
            optifine.CustomColors.colorsBlockColormaps = readCustomColormaps(properties, text);
            optifine.CustomColors.spawnEggPrimaryColors =
                    readSpawnEggColors(properties, text, "egg.shell.", "Spawn egg shell");
            optifine.CustomColors.spawnEggSecondaryColors =
                    readSpawnEggColors(properties, text, "egg.spots.", "Spawn egg spot");
            optifine.CustomColors.wolfCollarColors =
                    readDyeColors(properties, text, "collar.", "Wolf collar");
            optifine.CustomColors.sheepColors = readDyeColors(properties, text, "sheep.", "Sheep");
            optifine.CustomColors.textColors =
                    readTextColors(properties, text, "text.code.", "Text");
            int[] aint = readMapColors(properties, text, "map.", "Map");
            if (aint != null) {
                if (optifine.CustomColors.mapColorsOriginal == null) {
                    optifine.CustomColors.mapColorsOriginal = getMapColors();
                }

                setMapColors(aint);
            }

            optifine.CustomColors.potionColors =
                    readPotionColors(properties, text, "potion.", "Potion");
        } catch (FileNotFoundException filenotfoundexception) {
            return;
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    private static int[] readSpawnEggColors(
            Properties properties, String text, String textValue, String textValue2) {
        ArrayList arrayList = new ArrayList();
        int j1 = ReflectorMethod.f();
        Set set = properties.keySet();
        int i = j1;
        int j = 0;

        for (java.util.Iterator<?> __recoveryIterator171_39006 = (set).iterator();
                (__recoveryIterator171_39006).hasNext(); ) {
            String textValue3 = (String) (__recoveryIterator171_39006).next();
            String property = properties.getProperty(textValue3);
            if (textValue3.startsWith(textValue)) {
                String textValue4 = StrUtils.removePrefix(textValue3, textValue);
                int k = i(textValue4);
                int l = parseColor(property);
                if (k >= 0 && l >= 0) {
                    while (arrayList.size() <= k) {
                        arrayList.add(-1);
                        if (i != 0) {
                            break;
                        }
                    }

                    arrayList.set(k, l);
                    j++;
                    if (!(i == 0)) {

                        warn("Invalid spawn egg color: " + textValue3 + " = " + property);
                    }
                } else {

                    warn("Invalid spawn egg color: " + textValue3 + " = " + property);
                }
            }

            if (i != 0) {
                break;
            }
        }

        if (j <= 0) {
            return null;
        }

        dbg(textValue2 + " colors: " + j);
        int[] aint = new int[arrayList.size()];
        int i1 = 0;

        while (i1 < aint.length) {
            aint[i1] = (Integer) arrayList.get(i1);
            i1++;
            if (i != 0) {
                break;
            }
        }

        return aint;
    }

    private static Vec3 getFogColorNether(Vec3 vec3) {
        return optifine.CustomColors.fogColorNether == null
                ? vec3
                : optifine.CustomColors.fogColorNether;
    }

    private static float[] getDyeColors(
            EnumDyeColor enumDyeColor, float[][] afloat, float[] afloat1) {
        if (afloat == null) {
            return afloat1;
        }

        if (enumDyeColor == null) {
            return afloat1;
        }

        float[] afloat2 = afloat[enumDyeColor.ordinal()];
        return afloat2 == null ? afloat1 : afloat2;
    }

    private static Vec3 readColorVec3(Properties properties, String text) {
        int i = optifine.CustomColors.readColor(properties, text);

        try {
            if (i < 0) {
                return null;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception));
        }

        int j = i >> 16 & 0xFF;
        int k = i >> 8 & 0xFF;
        int l = i & 0xFF;
        float f = j / 255.0F;
        float f1 = k / 255.0F;
        float f2 = l / 255.0F;
        return new net.minecraft.util.Vec3(f, f1, f2);
    }

    private static int parseColor(String text) {
        try {
            if (text == null) {
                return -1;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception1));
        }

        text = text.trim();

        try {
            return java.lang.Integer.parseInt(text, 16) & 16777215;
        } catch (java.lang.NumberFormatException numberformatexception) {
            return -1;
        }
    }

    private static void warn(String text) {
        OptiFineConfig.warn("CustomColors: " + text);
    }

    public static float[] getWolfCollarColors(EnumDyeColor enumDyeColor, float[] afloat) {
        return getDyeColors(enumDyeColor, optifine.CustomColors.wolfCollarColors, afloat);
    }

    public static void updateUseDefaultGrassFoliageColors() {
        optifine.CustomColors.useDefaultGrassFoliageColors =
                (optifine.CustomColors.foliageBirchColors == null
                        && optifine.CustomColors.foliagePineColors == null
                        && optifine.CustomColors.swampGrassColors == null
                        && optifine.CustomColors.swampFoliageColors == null
                        && optifine.OptiFineConfig.isSwampColors()
                        && optifine.OptiFineConfig.isSmoothBiomes());
    }

    private static void addToList(CustomColormap customColormap, List list, int p171_2) {
        int j = optifine.ReflectorMethod.cR();

        while (p171_2 >= list.size()) {
            list.add(null);
            if (j == 0) {
                break;
            }
        }

        java.lang.Object object = (java.util.List) list.get(p171_2);
        if (object == null) {
            object = new java.util.ArrayList();
            list.set(p171_2, object);
        }

        ((java.util.List) object).add(customColormap);
    }

    private static int readColor(Properties properties, String text) {
        String property = properties.getProperty(text);
        if (property == null) {
            return -1;
        } else {
            property = property.trim();
            int i = parseColor(property);
            if (i < 0) {
                warn("Invalid color: " + text + " = " + property);
                return i;
            } else {
                dbg(text + " = " + property);
                return i;
            }
        }
    }

    private static int getLilypadColorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return optifine.CustomColors.lilyPadColor < 0
                ? Blocks.waterlily.colorMultiplier(iBlockAccess, blockPos)
                : optifine.CustomColors.lilyPadColor;
    }

    public static void update() {
        optifine.CustomColors.waterColors = null;
        optifine.CustomColors.foliageBirchColors = null;
        optifine.CustomColors.foliagePineColors = null;
        optifine.CustomColors.swampGrassColors = null;
        optifine.CustomColors.swampFoliageColors = null;
        optifine.CustomColors.skyColors = null;
        optifine.CustomColors.fogColors = null;
        optifine.CustomColors.underwaterColors = null;
        optifine.CustomColors.redstoneColors = null;
        optifine.CustomColors.xpOrbColors = null;
        optifine.CustomColors.stemColors = null;
        optifine.CustomColors.myceliumParticleColors = null;
        optifine.CustomColors.lightMapsColorsRgb = null;
        optifine.CustomColors.particleWaterColor = -1;
        optifine.CustomColors.particlePortalColor = -1;
        optifine.CustomColors.lilyPadColor = -1;
        optifine.CustomColors.expBarTextColor = -1;
        optifine.CustomColors.bossTextColor = -1;
        optifine.CustomColors.signTextColor = -1;
        optifine.CustomColors.fogColorNether = null;
        optifine.CustomColors.fogColorEnd = null;
        optifine.CustomColors.skyColorEnd = null;
        optifine.CustomColors.colorsBlockColormaps = null;
        optifine.CustomColors.blockColormaps = (CustomColormap[][]) null;
        optifine.CustomColors.useDefaultGrassFoliageColors = true;
        optifine.CustomColors.spawnEggPrimaryColors = null;
        optifine.CustomColors.spawnEggSecondaryColors = null;
        optifine.CustomColors.wolfCollarColors = (float[][]) null;
        optifine.CustomColors.sheepColors = (float[][]) null;
        optifine.CustomColors.textColors = null;
        setMapColors(optifine.CustomColors.mapColorsOriginal);
        optifine.CustomColors.potionColors = null;
        PotionHelper.clearPotionColorCache();
        String text = "mcpatcher/colormap/";
        String[] astring = new String[] {"water.png", "watercolorX.png"};
        optifine.CustomColors.waterColors = getCustomColors(text, astring, 256, 256);
        updateUseDefaultGrassFoliageColors();
        if (OptiFineConfig.isCustomColors()) {
            String[] astring1 = new String[] {"pine.png", "pinecolor.png"};
            optifine.CustomColors.foliagePineColors = getCustomColors(text, astring1, 256, 256);
            String[] astring2 = new String[] {"birch.png", "birchcolor.png"};
            optifine.CustomColors.foliageBirchColors = getCustomColors(text, astring2, 256, 256);
            String[] astring3 = new String[] {"swampgrass.png", "swampgrasscolor.png"};
            optifine.CustomColors.swampGrassColors = getCustomColors(text, astring3, 256, 256);
            String[] astring4 = new String[] {"swampfoliage.png", "swampfoliagecolor.png"};
            optifine.CustomColors.swampFoliageColors = getCustomColors(text, astring4, 256, 256);
            String[] astring5 = new String[] {"sky0.png", "skycolor0.png"};
            optifine.CustomColors.skyColors = getCustomColors(text, astring5, 256, 256);
            String[] astring6 = new String[] {"fog0.png", "fogcolor0.png"};
            optifine.CustomColors.fogColors = getCustomColors(text, astring6, 256, 256);
            String[] astring7 = new String[] {"underwater.png", "underwatercolor.png"};
            optifine.CustomColors.underwaterColors = getCustomColors(text, astring7, 256, 256);
            String[] astring8 = new String[] {"redstone.png", "redstonecolor.png"};
            optifine.CustomColors.redstoneColors = getCustomColors(text, astring8, 16, 1);
            optifine.CustomColors.xpOrbColors = getCustomColors(text + "xporb.png", -1, -1);
            String[] astring9 = new String[] {"stem.png", "stemcolor.png"};
            optifine.CustomColors.stemColors = getCustomColors(text, astring9, 8, 1);
            optifine.CustomColors.stemPumpkinColors =
                    getCustomColors(text + "pumpkinstem.png", 8, 1);
            optifine.CustomColors.stemMelonColors = getCustomColors(text + "melonstem.png", 8, 1);
            String[] astring10 = new String[] {"myceliumparticle.png", "myceliumparticlecolor.png"};
            optifine.CustomColors.myceliumParticleColors = getCustomColors(text, astring10, -1, -1);
            Pair pair = parseLightMapPacks();
            optifine.CustomColors.lightMapsColorsRgb = (CustomColormap[]) pair.getLeft();
            optifine.CustomColors.lightmapMinDimensionId = (Integer) pair.getRight();
            readColorProperties("mcpatcher/color.properties");
            optifine.CustomColors.blockColormaps =
                    readBlockColormaps(
                            new String[] {text + "custom/", text + "blocks/"},
                            optifine.CustomColors.colorsBlockColormaps,
                            256,
                            256);
            updateUseDefaultGrassFoliageColors();
        }
    }

    private static int method_5e0b3ac585811586194a(String text, int i) {
        try {
            InputStream inputstream = OptiFineConfig.getResourceStream(new ResourceLocation(text));
            if (inputstream == null) {
                return i;
            }

            BufferedImage bufferedimage = ImageIO.read(inputstream);
            inputstream.close();
            return bufferedimage == null ? i : bufferedimage.getHeight();
        } catch (IOException ioexception) {
            return i;
        }
    }

    private static void getLightMapColumn(
            float[][] afloat, float f, int i, int j, float[][] afloat1) {
        int j2 = ReflectorMethod.f();
        int l = (int) Math.floor(f);
        int k = j2;
        int i1 = (int) Math.ceil(f);
        if (l == i1) {
            int j1 = 0;

            while (j1 < 16) {
                float[] afloat2 = afloat[i + j1 * j + l];
                float[] afloat3 = afloat1[j1];
                int k1 = 0;

                while (k1 < 3) {
                    afloat3[k1] = afloat2[k1];
                    k1++;
                    if (k != 0) {
                        break;
                    }
                }

                j1++;
                if (k != 0) {
                    break;
                }
            }

            if (k == 0) {
                return;
            }
        }

        float f1 = 1.0F - (f - l);
        float f2 = 1.0F - (i1 - f);
        int i2 = 0;

        while (i2 < 16) {
            float[] afloat6 = afloat[i + i2 * j + l];
            float[] afloat4 = afloat[i + i2 * j + i1];
            float[] afloat5 = afloat1[i2];
            int l1 = 0;

            while (l1 < 3) {
                afloat5[l1] = afloat6[l1] * f1 + afloat4[l1] * f2;
                l1++;
                if (k != 0) {
                    break;
                }
            }

            i2++;
            if (k != 0) {
                break;
            }
        }
    }

    public static int getDurabilityColor(int p171_0) {
        try {
            if (optifine.CustomColors.expBarTextColor < 0) {
                return p171_0;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception));
        }

        return optifine.CustomColors.expBarTextColor;
    }

    private static CustomColormap getBlockColormap(BlockState blockState) {
        int i = ReflectorMethod.cR();
        if (optifine.CustomColors.blockColormaps == null) {
            return null;
        }

        if (!(blockState instanceof BlockStateBase)) {
            return null;
        }

        BlockStateBase blockStateBase = (BlockStateBase) blockState;
        int j = blockStateBase.getBlockId();
        if (j >= 0 && j < optifine.CustomColors.blockColormaps.length) {
            CustomColormap[] customColormapArray = optifine.CustomColors.blockColormaps[j];
            if (customColormapArray == null) {
                return null;
            }

            int k = 0;

            while (k < customColormapArray.length) {
                CustomColormap customColormap = customColormapArray[k];
                if (customColormap.matchesBlock(blockStateBase)) {
                    return customColormap;
                }

                k++;
                if (i == 0) {
                    break;
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static float[] getSheepColors(EnumDyeColor enumDyeColor, float[] afloat) {
        return getDyeColors(enumDyeColor, optifine.CustomColors.sheepColors, afloat);
    }

    public static int getXpOrbColor(float f) {
        if (optifine.CustomColors.xpOrbColors == null) {
            return -1;
        }

        int i =
                (int)
                        ((MathHelper.sin(f) + 1.0F)
                                * (optifine.CustomColors.xpOrbColors.getLength() - 1)
                                / 2.0);
        return optifine.CustomColors.xpOrbColors.getColor(i);
    }

    private static int[] getMapColors() {
        MapColor[] aVQ = MapColor.mapColorArray;
        int[] aint = new int[aVQ.length];
        int k = ReflectorMethod.f();
        Arrays.fill(aint, -1);
        int i = k;
        int j = 0;

        while (j < aVQ.length && j < aint.length) {
            MapColor mapColor = aVQ[j];
            if (mapColor != null) {
                aint[j] = mapColor.colorValue;
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return aint;
    }

    private static Vec3 getSkyColorEnd(Vec3 vec3) {
        return optifine.CustomColors.skyColorEnd == null ? vec3 : optifine.CustomColors.skyColorEnd;
    }

    public static CustomColormap getWaterColors() {
        return optifine.CustomColors.waterColors;
    }

    private static int i(String text) {
        try {
            if (text == null) {
                return -1;
            }
        } catch (java.lang.NumberFormatException numberformatexception2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception2));
        }

        int i = net.minecraft.entity.EntityList.getIDFromString(text);

        try {
            if (i < 0) {
                return -1;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception1));
        }

        java.lang.String textValue = net.minecraft.entity.EntityList.getStringFromID(i);

        try {
            if (!optifine.OptiFineConfig.equals(text, textValue)) {
                return -1;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception));
        }

        return i;
    }

    private static int getSmoothColorMultiplier(
            IBlockAccess iBlockAccess,
            BlockPos blockPos,
            CustomColorizer customColorizer,
            BlockPosM blockPosM) {
        int j = 0;
        int k = 0;
        int l = 0;
        int x = blockPos.getX();
        int i3 = ReflectorMethod.f();
        int j1 = blockPos.getY();
        int i = i3;
        int k1 = blockPos.getZ();
        BlockPosM blockPosMValue = blockPosM;
        int l1 = x - 1;

        label21:
        while (l1 <= x + 1) {
            int i2 = k1 - 1;

            while (true) {
                if (i2 <= k1 + 1) {
                    blockPosMValue.setXyz(l1, j1, i2);
                    int j2 = customColorizer.getColor(iBlockAccess, blockPosMValue);
                    j += j2 >> 16 & 0xFF;
                    k += j2 >> 8 & 0xFF;
                    l += j2 & 0xFF;
                    i2++;
                    if (i == 0) {
                        continue;
                    }
                }

                l1++;
                if (i != 0) {
                    break label21;
                }
                break;
            }
        }

        l1 = j / 9;
        int k2 = k / 9;
        int l2 = l / 9;
        return l1 << 16 | k2 << 8 | l2;
    }

    private static void addToBlockList(CustomColormap customColormap, List list) {
        int l = ReflectorMethod.f();
        int[] aint = customColormap.kS();
        int i = l;
        if (aint != null && aint.length > 0) {
            int j = 0;

            while (j < aint.length) {
                {
                    int k = aint[j];
                    if (k < 0) {
                        warn("Invalid block ID: " + k);
                        if (!(i == 0)) {

                            addToList(customColormap, list, k);
                        }
                    } else {

                        addToList(customColormap, list, k);
                    }
                }

                j++;
                if (i != 0) {
                    break;
                }
            }

            if (i == 0) {
                return;
            }
        }

        warn("No match blocks: " + OptiFineConfig.arrayToString(aint));
    }

    private static CustomColormap[][] blockListToArray(List list) {
        int k = ReflectorMethod.f();
        CustomColormap[][] customColormapArrayArray = new CustomColormap[list.size()][];
        int j = 0;
        int i = k;

        while (j < list.size()) {
            List listValue = (List) list.get(j);
            if (listValue != null) {
                CustomColormap[] customColormapArray =
                        ((optifine.CustomColormap[])
                                listValue.toArray(new CustomColormap[listValue.size()]));
                customColormapArrayArray[j] = customColormapArray;
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return customColormapArrayArray;
    }

    private static int w(String text) {
        return text == null
                ? -1
                : (text.equals("air")
                        ? MapColor.stoneColor.colorIndex
                        : (text.equals("grass")
                                ? MapColor.aVA.colorIndex
                                : (text.equals("sand")
                                        ? MapColor.sandColor.colorIndex
                                        : (text.equals("cloth")
                                                ? MapColor.aVo.colorIndex
                                                : (text.equals("tnt")
                                                        ? MapColor.tntColor.colorIndex
                                                        : (text.equals("ice")
                                                                ? MapColor.aaf.colorIndex
                                                                : (text.equals("iron")
                                                                        ? MapColor.ironColor
                                                                                .colorIndex
                                                                        : (text.equals("foliage")
                                                                                ? MapColor.aVH
                                                                                        .colorIndex
                                                                                : (text.equals(
                                                                                                "snow")
                                                                                        ? MapColor
                                                                                                .snowColor
                                                                                                .colorIndex
                                                                                        : (text
                                                                                                        .equals(
                                                                                                                "clay")
                                                                                                ? MapColor
                                                                                                        .aVq
                                                                                                        .colorIndex
                                                                                                : (text
                                                                                                                .equals(
                                                                                                                        "dirt")
                                                                                                        ? MapColor
                                                                                                                .dirtColor
                                                                                                                .colorIndex
                                                                                                        : (text
                                                                                                                        .equals(
                                                                                                                                "stone")
                                                                                                                ? MapColor
                                                                                                                        .aVs
                                                                                                                        .colorIndex
                                                                                                                : (text
                                                                                                                                .equals(
                                                                                                                                        "water")
                                                                                                                        ? MapColor
                                                                                                                                .waterColor
                                                                                                                                .colorIndex
                                                                                                                        : (text
                                                                                                                                        .equals(
                                                                                                                                                "wood")
                                                                                                                                ? MapColor
                                                                                                                                        .woodColor
                                                                                                                                        .colorIndex
                                                                                                                                : (text
                                                                                                                                                .equals(
                                                                                                                                                        "quartz")
                                                                                                                                        ? MapColor
                                                                                                                                                .quartzColor
                                                                                                                                                .colorIndex
                                                                                                                                        : (text
                                                                                                                                                        .equals(
                                                                                                                                                                "adobe")
                                                                                                                                                ? MapColor
                                                                                                                                                        .adobeColor
                                                                                                                                                        .colorIndex
                                                                                                                                                : (text
                                                                                                                                                                .equals(
                                                                                                                                                                        "magenta")
                                                                                                                                                        ? MapColor
                                                                                                                                                                .magentaColor
                                                                                                                                                                .colorIndex
                                                                                                                                                        : (text
                                                                                                                                                                        .equals(
                                                                                                                                                                                "lightBlue")
                                                                                                                                                                ? MapColor
                                                                                                                                                                        .lightBlueColor
                                                                                                                                                                        .colorIndex
                                                                                                                                                                : (text
                                                                                                                                                                                .equals(
                                                                                                                                                                                        "light_blue")
                                                                                                                                                                        ? MapColor
                                                                                                                                                                                .lightBlueColor
                                                                                                                                                                                .colorIndex
                                                                                                                                                                        : (text
                                                                                                                                                                                        .equals(
                                                                                                                                                                                                "yellow")
                                                                                                                                                                                ? MapColor
                                                                                                                                                                                        .yellowColor
                                                                                                                                                                                        .colorIndex
                                                                                                                                                                                : (text
                                                                                                                                                                                                .equals(
                                                                                                                                                                                                        "lime")
                                                                                                                                                                                        ? MapColor
                                                                                                                                                                                                .limeColor
                                                                                                                                                                                                .colorIndex
                                                                                                                                                                                        : (text
                                                                                                                                                                                                        .equals(
                                                                                                                                                                                                                "pink")
                                                                                                                                                                                                ? MapColor
                                                                                                                                                                                                        .pinkColor
                                                                                                                                                                                                        .colorIndex
                                                                                                                                                                                                : (text
                                                                                                                                                                                                                .equals(
                                                                                                                                                                                                                        "gray")
                                                                                                                                                                                                        ? MapColor
                                                                                                                                                                                                                .grayColor
                                                                                                                                                                                                                .colorIndex
                                                                                                                                                                                                        : (text
                                                                                                                                                                                                                        .equals(
                                                                                                                                                                                                                                "silver")
                                                                                                                                                                                                                ? MapColor
                                                                                                                                                                                                                        .silverColor
                                                                                                                                                                                                                        .colorIndex
                                                                                                                                                                                                                : (text
                                                                                                                                                                                                                                .equals(
                                                                                                                                                                                                                                        "cyan")
                                                                                                                                                                                                                        ? MapColor
                                                                                                                                                                                                                                .cyanColor
                                                                                                                                                                                                                                .colorIndex
                                                                                                                                                                                                                        : (text
                                                                                                                                                                                                                                        .equals(
                                                                                                                                                                                                                                                "purple")
                                                                                                                                                                                                                                ? MapColor
                                                                                                                                                                                                                                        .purpleColor
                                                                                                                                                                                                                                        .colorIndex
                                                                                                                                                                                                                                : (text
                                                                                                                                                                                                                                                .equals(
                                                                                                                                                                                                                                                        "blue")
                                                                                                                                                                                                                                        ? MapColor
                                                                                                                                                                                                                                                .blueColor
                                                                                                                                                                                                                                                .colorIndex
                                                                                                                                                                                                                                        : (text
                                                                                                                                                                                                                                                        .equals(
                                                                                                                                                                                                                                                                "brown")
                                                                                                                                                                                                                                                ? MapColor
                                                                                                                                                                                                                                                        .brownColor
                                                                                                                                                                                                                                                        .colorIndex
                                                                                                                                                                                                                                                : (text
                                                                                                                                                                                                                                                                .equals(
                                                                                                                                                                                                                                                                        "green")
                                                                                                                                                                                                                                                        ? MapColor
                                                                                                                                                                                                                                                                .greenColor
                                                                                                                                                                                                                                                                .colorIndex
                                                                                                                                                                                                                                                        : (text
                                                                                                                                                                                                                                                                        .equals(
                                                                                                                                                                                                                                                                                "red")
                                                                                                                                                                                                                                                                ? MapColor
                                                                                                                                                                                                                                                                        .redColor
                                                                                                                                                                                                                                                                        .colorIndex
                                                                                                                                                                                                                                                                : (text
                                                                                                                                                                                                                                                                                .equals(
                                                                                                                                                                                                                                                                                        "black")
                                                                                                                                                                                                                                                                        ? MapColor
                                                                                                                                                                                                                                                                                .blackColor
                                                                                                                                                                                                                                                                                .colorIndex
                                                                                                                                                                                                                                                                        : (text
                                                                                                                                                                                                                                                                                        .equals(
                                                                                                                                                                                                                                                                                                "gold")
                                                                                                                                                                                                                                                                                ? MapColor
                                                                                                                                                                                                                                                                                        .goldColor
                                                                                                                                                                                                                                                                                        .colorIndex
                                                                                                                                                                                                                                                                                : (text
                                                                                                                                                                                                                                                                                                .equals(
                                                                                                                                                                                                                                                                                                        "diamond")
                                                                                                                                                                                                                                                                                        ? MapColor
                                                                                                                                                                                                                                                                                                .diamondColor
                                                                                                                                                                                                                                                                                                .colorIndex
                                                                                                                                                                                                                                                                                        : (text
                                                                                                                                                                                                                                                                                                        .equals(
                                                                                                                                                                                                                                                                                                                "lapis")
                                                                                                                                                                                                                                                                                                ? MapColor
                                                                                                                                                                                                                                                                                                        .lapisColor
                                                                                                                                                                                                                                                                                                        .colorIndex
                                                                                                                                                                                                                                                                                                : (text
                                                                                                                                                                                                                                                                                                                .equals(
                                                                                                                                                                                                                                                                                                                        "emerald")
                                                                                                                                                                                                                                                                                                        ? MapColor
                                                                                                                                                                                                                                                                                                                .emeraldColor
                                                                                                                                                                                                                                                                                                                .colorIndex
                                                                                                                                                                                                                                                                                                        : (text
                                                                                                                                                                                                                                                                                                                        .equals(
                                                                                                                                                                                                                                                                                                                                "obsidian")
                                                                                                                                                                                                                                                                                                                ? MapColor
                                                                                                                                                                                                                                                                                                                        .obsidianColor
                                                                                                                                                                                                                                                                                                                        .colorIndex
                                                                                                                                                                                                                                                                                                                : (text
                                                                                                                                                                                                                                                                                                                                .equals(
                                                                                                                                                                                                                                                                                                                                        "netherrack")
                                                                                                                                                                                                                                                                                                                        ? MapColor
                                                                                                                                                                                                                                                                                                                                .netherrackColor
                                                                                                                                                                                                                                                                                                                                .colorIndex
                                                                                                                                                                                                                                                                                                                        : -1)))))))))))))))))))))))))))))))))))));
    }

    private static CustomColormap[][] readBlockColormaps(
            String[] astring, CustomColormap[] customColormapArray, int i, int j) {
        int i1 = ReflectorMethod.f();
        String[] astring1 = ResUtils.collectFiles(astring, new String[] {".properties"});
        int k = i1;
        Arrays.sort(astring1);
        ArrayList arrayList = new ArrayList();
        int l = 0;

        while (l < astring1.length) {
            String text = astring1[l];
            dbg("Block colormap: " + text);

            try {
                label47:
                {
                    ResourceLocation resourceLocation = new ResourceLocation("minecraft", text);
                    InputStream inputstream = OptiFineConfig.getResourceStream(resourceLocation);
                    if (inputstream == null) {
                        warn("File not found: " + text);
                        if (k == 0) {
                            break label47;
                        }
                    }

                    Properties properties = new Properties();
                    properties.load(inputstream);
                    CustomColormap customColormap = new CustomColormap(properties, text, i, j);
                    if (customColormap.isValid(text) && customColormap.isValidMatchBlocks(text)) {
                        addToBlockList(customColormap, arrayList);
                    }
                }
            } catch (FileNotFoundException filenotfoundexception) {
                warn("File not found: " + text);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            l++;
            if (k != 0) {
                break;
            }
        }

        if (customColormapArray != null) {
            l = 0;

            while (l < customColormapArray.length) {
                CustomColormap customColormapValue = customColormapArray[l];
                addToBlockList(customColormapValue, arrayList);
                l++;
                if (k != 0) {
                    break;
                }
            }
        }

        return arrayList.size() <= 0 ? (CustomColormap[][]) null : blockListToArray(arrayList);
    }

    public static CustomColormap getFoliagePineColors() {
        return optifine.CustomColors.foliagePineColors;
    }

    public static int method_46b7d87f71f96e2a0dba(int p171_0) {
        try {
            if (optifine.CustomColors.bossTextColor < 0) {
                return p171_0;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception));
        }

        return optifine.CustomColors.bossTextColor;
    }

    private static int[] readTextColors(
            Properties properties, String text, String textValue, String textValue2) {
        int[] aint = new int[32];
        Arrays.fill(aint, -1);
        int i1 = ReflectorMethod.f();
        int j = 0;
        Set set = properties.keySet();
        Iterator iterator = set.iterator();
        int i = i1;

        while (iterator.hasNext()) {
            String textValue3 = (String) iterator.next();
            String property = properties.getProperty(textValue3);
            if (textValue3.startsWith(textValue)) {
                String textValue4 = StrUtils.removePrefix(textValue3, textValue);
                int k = OptiFineConfig.parseInt(textValue4, -1);
                int l = parseColor(property);
                if (k >= 0 && k < aint.length && l >= 0) {
                    aint[k] = l;
                    j++;
                    if (!(i == 0)) {

                        warn("Invalid color: " + textValue3 + " = " + property);
                    }
                } else {

                    warn("Invalid color: " + textValue3 + " = " + property);
                }
            }

            if (i != 0) {
                break;
            }
        }

        if (j <= 0) {
            return null;
        }

        dbg(textValue2 + " colors: " + j);
        return aint;
    }

    private static int[] readMapColors(
            Properties properties, String text, String textValue, String textValue2) {
        int i1 = ReflectorMethod.f();
        int[] aint = new int[MapColor.mapColorArray.length];
        Arrays.fill(aint, -1);
        int j = 0;
        Set set = properties.keySet();
        Iterator iterator = set.iterator();
        int i = i1;

        while (iterator.hasNext()) {
            String textValue3 = (String) iterator.next();
            String property = properties.getProperty(textValue3);
            if (textValue3.startsWith(textValue)) {
                String textValue4 = StrUtils.removePrefix(textValue3, textValue);
                int k = w(textValue4);
                int l = parseColor(property);
                if (k >= 0 && k < aint.length && l >= 0) {
                    aint[k] = l;
                    j++;
                    if (!(i == 0)) {

                        warn("Invalid color: " + textValue3 + " = " + property);
                    }
                } else {

                    warn("Invalid color: " + textValue3 + " = " + property);
                }
            }

            if (i != 0) {
                break;
            }
        }

        if (j <= 0) {
            return null;
        }

        dbg(textValue2 + " colors: " + j);
        return aint;
    }

    public static CustomColormap getSwampGrassColors() {
        return optifine.CustomColors.swampGrassColors;
    }

    public static BiomeGenBase getColorBiome(IBlockAccess iBlockAccess, BlockPos blockPos) {
        net.minecraft.world.biome.BiomeGenBase biomeGenForCoords =
                iBlockAccess.getBiomeGenForCoords(blockPos);

        try {
            if (biomeGenForCoords != net.minecraft.world.biome.BiomeGenBase.swampland
                    || optifine.OptiFineConfig.isSwampColors()) {
                return biomeGenForCoords;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception));
        }

        return net.minecraft.world.biome.BiomeGenBase.plains;
    }

    private static Vec3 getFogColorEnd(Vec3 vec3) {
        return optifine.CustomColors.fogColorEnd == null ? vec3 : optifine.CustomColors.fogColorEnd;
    }

    private static float[][] readDyeColors(
            Properties properties, String text, String textValue, String textValue2) {
        EnumDyeColor[] enumDyeColorArray = EnumDyeColor.values();
        HashMap hashmap = new HashMap();
        int i1 = ReflectorMethod.cR();
        int j = 0;
        int i = i1;

        while (j < enumDyeColorArray.length) {
            EnumDyeColor enumDyeColor = enumDyeColorArray[j];
            hashmap.put(enumDyeColor.getName(), enumDyeColor);
            j++;
            if (i == 0) {
                break;
            }
        }

        float[][] afloat1 = new float[enumDyeColorArray.length][];
        int l = 0;

        for (java.util.Iterator<?> __recoveryIterator171_128896 = (properties.keySet()).iterator();
                (__recoveryIterator171_128896).hasNext(); ) {
            String textValue3 = (String) (__recoveryIterator171_128896).next();
            String property = properties.getProperty(textValue3);
            if (textValue3.startsWith(textValue)) {
                String textValue4 = StrUtils.removePrefix(textValue3, textValue);
                if (textValue4.equals("lightBlue")) {
                    textValue4 = "light_blue";
                }

                EnumDyeColor enumDyeColorValue = (EnumDyeColor) hashmap.get(textValue4);
                int k = parseColor(property);
                if (enumDyeColorValue != null && k >= 0) {
                    float[] afloat =
                            new float[] {
                                (k >> 16 & 0xFF) / 255.0F,
                                (k >> 8 & 0xFF) / 255.0F,
                                (k & 0xFF) / 255.0F
                            };
                    afloat1[enumDyeColorValue.ordinal()] = afloat;
                    l++;
                    if (!(i != 0)) {

                        warn("Invalid color: " + textValue3 + " = " + property);
                    }
                } else {

                    warn("Invalid color: " + textValue3 + " = " + property);
                }
            }

            if (i == 0) {
                break;
            }
        }

        if (l <= 0) {
            return (float[][]) null;
        }

        dbg(textValue2 + " colors: " + l);
        return afloat1;
    }

    private static Vec3 getFogColor(
            Vec3 vec3, IBlockAccess iBlockAccess, double d0, double d1, double d2) {
        if (optifine.CustomColors.fogColors == null) {
            return vec3;
        }

        int colorSmooth =
                optifine.CustomColors.fogColors.getColorSmooth(iBlockAccess, d0, d1, d2, 3);
        int j = colorSmooth >> 16 & 0xFF;
        int k = colorSmooth >> 8 & 0xFF;
        int l = colorSmooth & 0xFF;
        float f = j / 255.0F;
        float f1 = k / 255.0F;
        float f2 = l / 255.0F;
        float f3 = (float) vec3.x / 0.753F;
        float f4 = (float) vec3.y / 0.8471F;
        float f5 = (float) vec3.z;
        f *= f3;
        f1 *= f4;
        f2 *= f5;
        return optifine.CustomColors.fogColorFader.getColor(f, f1, f2);
    }

    public static void updateMyceliumFX(EntityFX entityFX) {
        if (optifine.CustomColors.myceliumParticleColors != null) {
            int colorRandom = optifine.CustomColors.myceliumParticleColors.getColorRandom();
            int j = colorRandom >> 16 & 0xFF;
            int k = colorRandom >> 8 & 0xFF;
            int l = colorRandom & 0xFF;
            float f = j / 255.0F;
            float f1 = k / 255.0F;
            float f2 = l / 255.0F;
            entityFX.setRBGColorF(f, f1, f2);
        }
    }

    public static void updateReddustFX(
            EntityFX entityFX,
            IBlockAccess iBlockAccess,
            double p171_2,
            double p171_3,
            double p171_4) {
        if (optifine.CustomColors.redstoneColors != null) {
            net.minecraft.block.state.BlockState blockState =
                    iBlockAccess.getBlockState(
                            new net.minecraft.util.BlockPos(p171_2, p171_3, p171_4));
            int redstoneLevel = optifine.CustomColors.getRedstoneLevel(blockState, 15);
            int j = optifine.CustomColors.redstoneColors.getColor(redstoneLevel);
            int k = j >> 16 & 0xFF;
            int l = j >> 8 & 0xFF;
            int i1 = j & 0xFF;
            float f = k / 255.0F;
            float f1 = l / 255.0F;
            float f2 = i1 / 255.0F;
            entityFX.setRBGColorF(f, f1, f2);
        }
    }

    public static Vec3 getUnderwaterColor(
            IBlockAccess iBlockAccess, double d0, double d1, double d2) {
        if (optifine.CustomColors.underwaterColors == null) {
            return null;
        }

        int colorSmooth =
                optifine.CustomColors.underwaterColors.getColorSmooth(iBlockAccess, d0, d1, d2, 3);
        int j = colorSmooth >> 16 & 0xFF;
        int k = colorSmooth >> 8 & 0xFF;
        int l = colorSmooth & 0xFF;
        float f = j / 255.0F;
        float f1 = k / 255.0F;
        float f2 = l / 255.0F;
        return optifine.CustomColors.underwaterColorFader.getColor(f, f1, f2);
    }

    private static int getSpawnEggColor(
            ItemMonsterPlacer itemMonsterPlacer, ItemStack itemStack, int p171_2, int p171_3) {
        int k = itemStack.getItemDamage();

        int[] aint1;
        label53:
        {
            try {
                if (p171_2 == 0) {
                    aint1 = optifine.CustomColors.spawnEggPrimaryColors;
                    break label53;
                }
            } catch (java.lang.NumberFormatException numberformatexception3) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        optifine.CustomColors.a(numberformatexception3));
            }

            aint1 = optifine.CustomColors.spawnEggSecondaryColors;
        }

        int[] aint = aint1;

        try {
            if (aint == null) {
                return p171_3;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception));
        }

        try {
            if (k < 0 || k >= aint.length) {
                return p171_3;
            }
        } catch (java.lang.NumberFormatException numberformatexception2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception2));
        }

        int l = aint[k];

        try {
            if (l < 0) {
                return p171_3;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception1));
        }

        return l;
    }

    private static int readColor(Properties properties, String[] astring) {
        int l = ReflectorMethod.cR();
        int j = 0;
        int i = l;

        while (j < astring.length) {
            String text = astring[j];
            int k = readColor(properties, text);
            if (k >= 0) {
                return k;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return -1;
    }

    public static CustomColormap getSwampFoliageColors() {
        return optifine.CustomColors.swampFoliageColors;
    }

    private static int getStemColorMultiplier(
            Block block, IBlockAccess iBlockAccess, BlockPos blockPos, RenderEnv renderEnv) {
        CustomColormap stemColorsValue = optifine.CustomColors.stemColors;
        if (block == Blocks.pumpkin_stem && optifine.CustomColors.stemPumpkinColors != null) {
            stemColorsValue = optifine.CustomColors.stemPumpkinColors;
        }

        if (block == Blocks.melon_stem && optifine.CustomColors.stemMelonColors != null) {
            stemColorsValue = optifine.CustomColors.stemMelonColors;
        }

        if (stemColorsValue == null) {
            return -1;
        }

        int metadata = renderEnv.getMetadata();
        return stemColorsValue.getColor(metadata);
    }

    private static void setMapColors(int[] aint) {
        int i = ReflectorMethod.cR();
        if (aint != null) {
            MapColor[] aVQ = MapColor.mapColorArray;
            int j = 0;

            while (j < aVQ.length && j < aint.length) {
                MapColor mapColor = aVQ[j];
                if (mapColor != null) {
                    int k = aint[j];
                    if (k >= 0) {
                        mapColor.colorValue = k;
                    }
                }

                j++;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    public static Vec3 resolveDimensionFogColor(
            Vec3 vec3, ClientWorld clientWorld, Entity entity, float f) {
        int k = ReflectorMethod.f();
        int j = clientWorld.provider.getDimensionId();
        int i = k;
        switch (j) {
            case -1:
                vec3 = getFogColorNether(vec3);
                if (i == 0) {
                    break;
                }
            case 0:
                MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
                vec3 =
                        getFogColor(
                                vec3,
                                minecraftClient.world,
                                entity.posX,
                                entity.posY + 1.0,
                                entity.posZ);
                if (i == 0) {
                    break;
                }
            case 1:
                vec3 = getFogColorEnd(vec3);
        }

        return vec3;
    }

    private static int getRedstoneColor(BlockState blockState) {
        try {
            if (optifine.CustomColors.redstoneColors == null) {
                return -1;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception));
        }

        int redstoneLevel = optifine.CustomColors.getRedstoneLevel(blockState, 15);
        return optifine.CustomColors.redstoneColors.getColor(redstoneLevel);
    }

    public static CustomColormap getFoliageBirchColors() {
        return optifine.CustomColors.foliageBirchColors;
    }

    private static int[] readPotionColors(
            Properties properties, String text, String textValue, String textValue2) {
        int[] aint = new int[Potion.potionTypes.length];
        Arrays.fill(aint, -1);
        int j = 0;
        Set set = properties.keySet();
        int i1 = ReflectorMethod.cR();
        Iterator iterator = set.iterator();
        int i = i1;

        while (iterator.hasNext()) {
            String textValue3 = (String) iterator.next();
            String property = properties.getProperty(textValue3);
            if (textValue3.startsWith(textValue)) {
                int k = getPotionId(textValue3);
                int l = parseColor(property);
                if (k >= 0 && k < aint.length && l >= 0) {
                    aint[k] = l;
                    j++;
                    if (!(i != 0)) {

                        warn("Invalid color: " + textValue3 + " = " + property);
                    }
                } else {

                    warn("Invalid color: " + textValue3 + " = " + property);
                }
            }

            if (i == 0) {
                break;
            }
        }

        if (j <= 0) {
            return null;
        }

        dbg(textValue2 + " colors: " + j);
        return aint;
    }

    public static int getCustomTextColor(int p171_0, int p171_1) {
        try {
            if (optifine.CustomColors.textColors == null) {
                return p171_1;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception));
        }

        try {
            if (p171_0 < 0 || p171_0 >= optifine.CustomColors.textColors.length) {
                return p171_1;
            }
        } catch (java.lang.NumberFormatException numberformatexception2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception2));
        }

        int k = optifine.CustomColors.textColors[p171_0];

        try {
            if (k < 0) {
                return p171_1;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception1));
        }

        return k;
    }

    public static CustomColormap getCustomColors(String text, int i, int j) {
        int k = ReflectorMethod.f();

        try {
            ResourceLocation resourceLocation = new ResourceLocation(text);
            if (!OptiFineConfig.hasResource(resourceLocation)) {
                return null;
            }

            Properties properties;
            String textValue;
            {
                dbg("Colormap " + text);
                properties = new Properties();
                textValue = StrUtils.replaceSuffix(text, ".png", ".properties");
                ResourceLocation resourceLocationValue = new ResourceLocation(textValue);
                if (OptiFineConfig.hasResource(resourceLocationValue)) {
                    InputStream inputstream =
                            OptiFineConfig.getResourceStream(resourceLocationValue);
                    properties.load(inputstream);
                    inputstream.close();
                    dbg("Colormap properties: " + textValue);
                    if (!(k == 0)) {

                        properties.put("format", "vanilla");
                        properties.put("source", text);
                        textValue = text;
                    }
                } else {

                    properties.put("format", "vanilla");
                    properties.put("source", text);
                    textValue = text;
                }
            }

            CustomColormap customColormap = new CustomColormap(properties, textValue, i, j);
            return !customColormap.isValid(textValue) ? null : customColormap;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Vec3 getSkyColor(
            Vec3 vec3, IBlockAccess iBlockAccess, double d0, double d1, double d2) {
        if (optifine.CustomColors.skyColors == null) {
            return vec3;
        }

        int colorSmooth =
                optifine.CustomColors.skyColors.getColorSmooth(iBlockAccess, d0, d1, d2, 3);
        int j = colorSmooth >> 16 & 0xFF;
        int k = colorSmooth >> 8 & 0xFF;
        int l = colorSmooth & 0xFF;
        float f = j / 255.0F;
        float f1 = k / 255.0F;
        float f2 = l / 255.0F;
        float f3 = (float) vec3.x / 0.5F;
        float f4 = (float) vec3.y / 0.66275F;
        float f5 = (float) vec3.z;
        f *= f3;
        f1 *= f4;
        f2 *= f5;
        return optifine.CustomColors.skyColorFader.getColor(f, f1, f2);
    }

    private static int getRedstoneLevel(BlockState blockState, int p171_1) {
        net.minecraft.block.Block block = blockState.getBlock();

        try {
            if (!(block instanceof net.minecraft.block.BlockRedstoneWire)) {
                return p171_1;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception1));
        }

        java.lang.Comparable comparable =
                blockState.getValue(net.minecraft.block.BlockRedstoneWire.POWER);

        try {
            if (!(comparable instanceof java.lang.Integer)) {
                return p171_1;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.CustomColors.a(numberformatexception));
        }

        java.lang.Integer integer = (java.lang.Integer) comparable;
        return integer;
    }

    private static CustomColormap getCustomColors(String text, String[] astring, int i, int j) {
        int i1 = ReflectorMethod.f();
        int l = 0;
        int k = i1;

        while (l < astring.length) {
            String textValue = astring[l];
            textValue = text + textValue;
            CustomColormap customColormap = getCustomColors(textValue, i, j);
            if (customColormap != null) {
                return customColormap;
            }

            l++;
            if (k != 0) {
                break;
            }
        }

        return null;
    }
}
