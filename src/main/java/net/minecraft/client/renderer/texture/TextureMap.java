package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

import optifine.ConnectedTextures;
import optifine.CustomItems;
import optifine.OptiFineConfig;
import optifine.Reflector;
import optifine.ReflectorForge;
import optifine.TextureUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import shadersmod.client.ShadersTex;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class TextureMap extends AbstractTexture implements ITickableTextureObject {
    private Map mapRegisteredSprites;
    private static Logger logger;
    private boolean skipFirst;

    private static boolean ENABLE_SKIP;
    private int nO;
    private int mipmapLevels;
    public static ResourceLocation LOCATION_MISSING_TEXTURE;
    public int atlasHeight;
    private int iconGridCountX;
    private static String[] packedTextFragments;
    private String basePath;
    private TextureAtlasSprite missingImage;
    private List listAnimatedSprites;
    private double iconGridSizeU;
    public static ResourceLocation locationBlocksTexture;
    private int jE;
    private IIconCreator iconCreator;
    private Map mapUploadedSprites;
    private double iconGridSizeV;
    private TextureAtlasSprite[] iconGrid = null;

    public int atlasWidth;

    public TextureAtlasSprite getSpriteSafe(String text) {
        ResourceLocation resourceLocation = new ResourceLocation(text);
        return (TextureAtlasSprite) this.mapRegisteredSprites.get(resourceLocation.toString());
    }

    private void initMissingImage() {
        int minSpriteSize = this.getMinSpriteSize();
        int[] aint = this.getMissingImageData(minSpriteSize);
        this.missingImage.b(minSpriteSize);
        this.missingImage.method_571f52593fafd8dfba61(minSpriteSize);
        int[][] aint1 = new int[this.mipmapLevels + 1][];
        aint1[0] = aint;
        this.missingImage.setFramesTextureData(
                com.google.common.collect.Lists.newArrayList(new int[][][] {aint1}));
        this.missingImage.setIndexInMap(0);
    }

    private int detectMinimumSpriteSize(Map map, IResourceManager iResourceManager, int i) {
        int j3 = TextureManager.method_beea3e16fe3ec0995eb9();
        HashMap hashmap = new HashMap();
        Set set = map.entrySet();
        int j = j3;

        for (java.util.Iterator<?> __recoveryIterator171_3174 = (set).iterator();
                (__recoveryIterator171_3174).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_3174).next();
            TextureAtlasSprite value = (TextureAtlasSprite) entry.getValue();
            ResourceLocation resourceLocation = new ResourceLocation(value.getIconName());
            ResourceLocation resourceLocationValue =
                    this.completeResourceLocation(resourceLocation, 0);
            if (!value.method_39e557b24f43f8bcfd13(iResourceManager, resourceLocation)) {
                try {
                    IResource resource = iResourceManager.getResource(resourceLocationValue);
                    if (resource != null) {
                        InputStream inputstream = resource.getInputStream();
                        if (inputstream != null) {
                            Dimension dimension = TextureUtils.getImageSize(inputstream, "png");
                            if (dimension != null) {
                                int k = dimension.width;
                                int l = MathHelper.roundUpToPowerOfTwo(k);
                                if (!hashmap.containsKey(l)) {
                                    hashmap.put(l, 1);
                                    if (!(j == 0)) {

                                        int i1 = (Integer) hashmap.get(l);
                                        hashmap.put(l, i1 + 1);
                                    }
                                } else {

                                    int i1 = (Integer) hashmap.get(l);
                                    hashmap.put(l, i1 + 1);
                                }
                            }
                        }
                    }
                } catch (Exception exception) {
                }
            }

            if (j != 0) {
                break;
            }
        }

        int k1 = 0;
        Set set1 = hashmap.keySet();
        TreeSet treeset = new TreeSet(set1);

        for (java.util.Iterator<?> __recoveryIterator171_5259 = (treeset).iterator();
                (__recoveryIterator171_5259).hasNext(); ) {
            int l1 = ((java.lang.Integer) (__recoveryIterator171_5259).next()).intValue();
            int j2 = (Integer) hashmap.get(l1);
            k1 += j2;
            if (j != 0) {
                break;
            }
        }

        int l2 = 16;
        int i2 = 0;
        int k2 = k1 * i / 100;

        for (java.util.Iterator<?> __recoveryIterator171_5494 = (treeset).iterator();
                (__recoveryIterator171_5494).hasNext(); ) {
            int i3 = ((java.lang.Integer) (__recoveryIterator171_5494).next()).intValue();
            int j1 = (Integer) hashmap.get(i3);
            i2 += j1;
            if (i3 > l2) {
                l2 = i3;
            }

            if (i2 > k2) {
                return l2;
            }
        }

        return l2;
    }

    private int detectMaxMipmapLevel(Map map, IResourceManager iResourceManager) {
        int i = this.detectMinimumSpriteSize(map, iResourceManager, 20);
        if (i < 16) {
            i = 16;
        }

        i = MathHelper.roundUpToPowerOfTwo(i);
        if (i > 16) {
            OptiFineConfig.log("Sprite size: " + i);
        }

        int j = MathHelper.floorLog2(i);
        if (j < 4) {
            j = 4;
        }

        return j;
    }

    // region String decoding support

    // endregion

    public boolean aR() {
        int boundTexture = net.minecraft.client.renderer.GlStateManager.getBoundTexture();
        int j = this.getGlTextureId();

        try {
            if (boundTexture == j) {
                return true;
            }
        } catch (java.lang.RuntimeException runtimeexception) {
            throw remix.compat.compiler.JvmThrowTypes.unchecked(
                    net.minecraft.client.renderer.texture.TextureMap.a(runtimeexception));
        }

        return false;
    }

    public TextureAtlasSprite registerSprite(ResourceLocation resourceLocation) {
        if (resourceLocation == null) {
            throw new IllegalArgumentException("Location cannot be null!");
        }

        TextureAtlasSprite textureAtlasSprite =
                (TextureAtlasSprite) this.mapRegisteredSprites.get(resourceLocation.toString());
        if (textureAtlasSprite == null) {
            textureAtlasSprite = TextureAtlasSprite.makeAtlasSprite(resourceLocation);
            this.mapRegisteredSprites.put(resourceLocation.toString(), textureAtlasSprite);
            if (textureAtlasSprite instanceof TextureAtlasSprite
                    && textureAtlasSprite.getIndexInMap() < 0) {
                textureAtlasSprite.setIndexInMap(this.mapRegisteredSprites.size());
            }
        }

        return textureAtlasSprite;
    }

    public TextureMap(String text, boolean flag) {
        this(text, (IIconCreator) null, flag);
    }

    public TextureMap(String text) {
        this(text, (IIconCreator) null);
    }

    private boolean isTerrainAnimationActive(TextureAtlasSprite textureAtlasSprite) {
        return ((textureAtlasSprite == optifine.TextureUtils.iconWaterStill
                        || textureAtlasSprite == optifine.TextureUtils.iconWaterFlow
                ? (optifine.OptiFineConfig.W_boolean())
                : (textureAtlasSprite == optifine.TextureUtils.iconLavaStill
                                || textureAtlasSprite == optifine.TextureUtils.iconLavaFlow
                        ? (optifine.OptiFineConfig.isAnimatedLava())
                        : (textureAtlasSprite == optifine.TextureUtils.iconFireLayer0
                                        || textureAtlasSprite
                                                == optifine.TextureUtils.iconFireLayer1
                                ? (optifine.OptiFineConfig.isAnimatedFire())
                                : (textureAtlasSprite == optifine.TextureUtils.iconPortal
                                        ? (optifine.OptiFineConfig.isAnimatedPortal())
                                        : (textureAtlasSprite != optifine.TextureUtils.iconClock
                                                        && textureAtlasSprite
                                                                != optifine.TextureUtils.iconCompass
                                                ? (optifine.OptiFineConfig.isAnimatedTerrain())
                                                : true))))));
    }

    private boolean isAbsoluteLocation(ResourceLocation resourceLocation) {
        String text = resourceLocation.getResourcePath();
        return this.isAbsoluteLocationPath(text);
    }

    public boolean setTextureEntry(String text, TextureAtlasSprite textureAtlasSprite) {
        if (!((boolean)
                (((java.util.Map) (this.mapRegisteredSprites))
                        .containsKey(((java.lang.Object) (text)))))) {
            ((java.util.Map) (this.mapRegisteredSprites))
                    .put(((java.lang.Object) (text)), ((java.lang.Object) (textureAtlasSprite)));
            if (textureAtlasSprite.getIndexInMap() < 0) {
                textureAtlasSprite.setIndexInMap(
                        ((int) (((java.util.Map) (this.mapRegisteredSprites)).size())));
            }

            return true;
        } else {
            return false;
        }
    }

    public void tick() {
        this.updateAnimations();
    }

    public void updateAnimations() {
        int i = TextureManager.method_7eec03da329a017ec143();
        if (OptiFineConfig.isShaders()) {
            ShadersTex.updatingTex = this.getMultiTexID();
        }

        TextureUtil.bindTexture(this.getGlTextureId());

        for (java.util.Iterator<?> __recoveryIterator171_31865 =
                        (this.listAnimatedSprites).iterator();
                (__recoveryIterator171_31865).hasNext(); ) {
            TextureAtlasSprite textureAtlasSprite =
                    (TextureAtlasSprite) (__recoveryIterator171_31865).next();
            if (this.isTerrainAnimationActive(textureAtlasSprite)) {
                textureAtlasSprite.updateAnimation();
            }

            if (i == 0) {
                break;
            }
        }

        if (OptiFineConfig.isMultiTexture()) {
            for (java.util.Iterator<?> __recoveryIterator171_32198 =
                            (this.listAnimatedSprites).iterator();
                    (__recoveryIterator171_32198).hasNext(); ) {
                TextureAtlasSprite textureAtlasSpriteValue =
                        (TextureAtlasSprite) (__recoveryIterator171_32198).next();
                if (this.isTerrainAnimationActive(textureAtlasSpriteValue)) {
                    TextureAtlasSprite aAo = textureAtlasSpriteValue.aAo;
                    if (aAo != null) {
                        if (textureAtlasSpriteValue == TextureUtils.iconClock
                                || textureAtlasSpriteValue == TextureUtils.iconCompass) {
                            aAo.rC = textureAtlasSpriteValue.rC;
                        }

                        textureAtlasSpriteValue.nG();
                        aAo.updateAnimation();
                    }
                }

                if (i == 0) {
                    break;
                }
            }

            TextureUtil.bindTexture(this.getGlTextureId());
        }

        if (OptiFineConfig.isShaders()) {
            ShadersTex.updatingTex = null;
        }
    }

    public TextureMap(String text, IIconCreator iIconCreator) {
        this(text, iIconCreator, false);
    }

    private int getMinSpriteSize() {
        int i = 1 << this.mipmapLevels;
        if (i < 8) {
            i = 8;
        }

        return i;
    }

    public TextureMap(String text, IIconCreator aGJValue, boolean flag) {
        this.nO = -1;
        this.iconGridCountX = -1;
        this.jE = -1;
        this.iconGridSizeU = -1.0;
        this.iconGridSizeV = -1.0;
        this.skipFirst = false;
        this.atlasWidth = 0;
        this.atlasHeight = 0;
        this.listAnimatedSprites = Lists.newArrayList();
        this.mapRegisteredSprites = Maps.newHashMap();
        this.mapUploadedSprites = Maps.newHashMap();
        this.missingImage = new TextureAtlasSprite("missingno");
        this.basePath = text;
        this.iconCreator = aGJValue;
        this.skipFirst =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (flag && net.minecraft.client.renderer.texture.TextureMap.ENABLE_SKIP
                                ? 1
                                : 0));
    }

    private void method_80c3ce64938e63de8c07(int i, int j) {
        int k2 = TextureManager.method_beea3e16fe3ec0995eb9();
        this.iconGridCountX = -1;
        this.jE = -1;
        int k = k2;
        this.iconGrid = null;
        if (this.nO > 0) {
            this.iconGridCountX = i / this.nO;
            this.jE = j / this.nO;
            this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.jE];
            this.iconGridSizeU = 1.0 / this.iconGridCountX;
            this.iconGridSizeV = 1.0 / this.jE;

            for (java.util.Iterator<?> __recoveryIterator171_35530 =
                            (this.mapUploadedSprites.values()).iterator();
                    (__recoveryIterator171_35530).hasNext(); ) {
                TextureAtlasSprite textureAtlasSprite =
                        (TextureAtlasSprite) (__recoveryIterator171_35530).next();
                double d0 = 0.5 / i;
                double d1 = 0.5 / j;
                double d2 =
                        Math.min(textureAtlasSprite.getMinU(), textureAtlasSprite.getMaxU()) + d0;
                double d3 =
                        Math.min(textureAtlasSprite.getMinV(), textureAtlasSprite.getMaxV()) + d1;
                double d4 =
                        Math.max(textureAtlasSprite.getMinU(), textureAtlasSprite.getMaxU()) - d0;
                double d5 =
                        Math.max(textureAtlasSprite.getMinV(), textureAtlasSprite.getMaxV()) - d1;
                int l = (int) (d2 / this.iconGridSizeU);
                int i1 = (int) (d3 / this.iconGridSizeV);
                int j1 = (int) (d4 / this.iconGridSizeU);
                int k1 = (int) (d5 / this.iconGridSizeV);
                int l1 = l;

                while (l1 <= j1) {
                    label50:
                    {
                        if (l1 >= 0 && l1 < this.iconGridCountX) {
                            int i2 = i1;

                            while (i2 <= k1) {
                                {
                                    if (i2 >= 0 && i2 < this.iconGridCountX) {
                                        int j2 = i2 * this.iconGridCountX + l1;
                                        this.iconGrid[j2] = textureAtlasSprite;
                                        if (!(k == 0)) {

                                            OptiFineConfig.warn(
                                                    "Invalid grid V: "
                                                            + i2
                                                            + ", icon: "
                                                            + textureAtlasSprite.getIconName());
                                        }
                                    } else {

                                        OptiFineConfig.warn(
                                                "Invalid grid V: "
                                                        + i2
                                                        + ", icon: "
                                                        + textureAtlasSprite.getIconName());
                                    }
                                }

                                i2++;
                                if (k != 0) {
                                    break;
                                }
                            }

                            if (k == 0) {
                                break label50;
                            }
                        }

                        OptiFineConfig.warn(
                                "Invalid grid U: "
                                        + l1
                                        + ", icon: "
                                        + textureAtlasSprite.getIconName());
                    }

                    l1++;
                    if (k != 0) {
                        break;
                    }
                }

                if (k != 0) {
                    break;
                }
            }
        }
    }

    private boolean isAbsoluteLocationPath(String text) {
        String textValue = text.toLowerCase();
        return textValue.startsWith("mcpatcher/") || textValue.startsWith("optifine/");
    }

    public TextureAtlasSprite getMissingSprite() {
        return this.missingImage;
    }

    public void loadSprites(IResourceManager iResourceManager, IIconCreator iIconCreator) {
        this.mapRegisteredSprites.clear();
        iIconCreator.registerSprites(this);
        if (this.mipmapLevels >= 4) {
            this.mipmapLevels =
                    this.detectMaxMipmapLevel(this.mapRegisteredSprites, iResourceManager);
            OptiFineConfig.log("Mipmap levels: " + this.mipmapLevels);
        }

        this.initMissingImage();
        this.cj();
        this.loadTextureAtlas(iResourceManager);
    }

    public TextureAtlasSprite getTextureExtry(String text) {
        ResourceLocation resourceLocation = new ResourceLocation(text);
        return (TextureAtlasSprite) this.mapRegisteredSprites.get(resourceLocation.toString());
    }

    public ResourceLocation completeResourceLocation(ResourceLocation resourceLocation, int i) {
        return this.isAbsoluteLocation(resourceLocation)
                ? (i == 0
                        ? new ResourceLocation(
                                resourceLocation.getResourceDomain(),
                                resourceLocation.getResourcePath() + ".png")
                        : new ResourceLocation(
                                resourceLocation.getResourceDomain(),
                                resourceLocation.getResourcePath() + "mipmap" + i + ".png"))
                : (i == 0
                        ? new ResourceLocation(
                                resourceLocation.getResourceDomain(),
                                String.format(
                                        "%s/%s%s",
                                        this.basePath, resourceLocation.getResourcePath(), ".png"))
                        : new ResourceLocation(
                                resourceLocation.getResourceDomain(),
                                String.format(
                                        "%s/mipmaps/%s.%d%s",
                                        this.basePath,
                                        resourceLocation.getResourcePath(),
                                        i,
                                        ".png")));
    }

    public TextureAtlasSprite getIconByUV(double p171_0, double p171_1) {
        if (this.iconGrid == null) {
            return null;
        }

        int i = (int) (p171_0 / this.iconGridSizeU);
        int j = (int) (p171_1 / this.iconGridSizeV);
        int k = j * this.iconGridCountX + i;
        return k >= 0 && k <= this.iconGrid.length ? this.iconGrid[k] : null;
    }

    public int getRegisteredSpriteCount() {
        return this.mapRegisteredSprites.size();
    }

    public void loadTexture(IResourceManager iResourceManager) throws IOException {
        ShadersTex.resManager = iResourceManager;
        if (this.iconCreator != null) {
            this.loadSprites(iResourceManager, this.iconCreator);
        }
    }

    private int[] getMissingImageData(int p171_0) {
        java.awt.image.BufferedImage bufferedimage = new java.awt.image.BufferedImage(16, 16, 2);
        bufferedimage.setRGB(
                0,
                0,
                16,
                16,
                net.minecraft.client.renderer.texture.TextureUtil.missingTextureData,
                0,
                16);
        java.awt.image.BufferedImage bufferedimage1 =
                optifine.TextureUtils.scaleToPowerOfTwo(bufferedimage, p171_0);
        int[] aint = new int[p171_0 * p171_0];
        bufferedimage1.getRGB(0, 0, p171_0, p171_0, aint, 0, p171_0);
        return aint;
    }

    public TextureAtlasSprite getAtlasSprite(String text) {
        net.minecraft.client.renderer.texture.TextureAtlasSprite textureAtlasSprite =
                (net.minecraft.client.renderer.texture.TextureAtlasSprite)
                        this.mapUploadedSprites.get(text);
        if (textureAtlasSprite == null) {
            textureAtlasSprite = this.missingImage;
        }

        return textureAtlasSprite;
    }

    public void loadTextureAtlas(IResourceManager iResourceManager) {
        int l3 = TextureManager.method_beea3e16fe3ec0995eb9();
        ShadersTex.resManager = iResourceManager;
        int i = l3;
        OptiFineConfig.dbg("Multitexture: " + OptiFineConfig.isMultiTexture());
        if (OptiFineConfig.isMultiTexture()) {
            for (java.util.Iterator<?> __recoveryIterator171_44327 =
                            (this.mapUploadedSprites.values()).iterator();
                    (__recoveryIterator171_44327).hasNext(); ) {
                TextureAtlasSprite textureAtlasSprite =
                        (TextureAtlasSprite) (__recoveryIterator171_44327).next();
                textureAtlasSprite.ns();
                if (i != 0) {
                    Entity.setLegacyGlobalFlowMarker(new String[5]);
                    break;
                }
            }
        }

        ConnectedTextures.method_33ebcaa086ed5d44dbe2(this);
        CustomItems.updateIcons(this);
        int i2 = MinecraftClient.getGLMaximumTextureSize();
        Stitcher stitcher = new Stitcher(i2, i2, true, 0, this.mipmapLevels);
        this.mapUploadedSprites.clear();
        this.listAnimatedSprites.clear();
        int j = Integer.MAX_VALUE;
        Reflector.callVoid(Reflector.Hz, new Object[] {this});
        int k = this.getMinSpriteSize();
        this.nO = k;
        int l = 1 << this.mipmapLevels;
        Iterator iterator = this.mapRegisteredSprites.entrySet().iterator();

        while (iterator.hasNext() && !this.skipFirst) {
            label240:
            {
                Entry entry = (Entry) iterator.next();
                TextureAtlasSprite value = (TextureAtlasSprite) entry.getValue();
                ResourceLocation resourceLocation = new ResourceLocation(value.getIconName());
                ResourceLocation resourceLocationValue =
                        this.completeResourceLocation(resourceLocation, 0);
                if (value.method_39e557b24f43f8bcfd13(iResourceManager, resourceLocation)) {
                    if (!value.method_379bd37280898b7acc5d(iResourceManager, resourceLocation)) {
                        j = Math.min(j, Math.min(value.getIconWidth(), value.getIconHeight()));
                        stitcher.addSprite(value);
                    }

                    OptiFineConfig.dbg("Custom loader: " + value);
                    if (i == 0) {
                        break label240;
                    }
                }

                try {
                    IResource iResource =
                            ShadersTex.loadResource(iResourceManager, resourceLocationValue);
                    BufferedImage[] abufferedimage = new BufferedImage[1 + this.mipmapLevels];
                    abufferedimage[0] = TextureUtil.readBufferedImage(iResource.getInputStream());
                    if (abufferedimage != null) {
                        int width = abufferedimage[0].getWidth();
                        if (width < k || this.mipmapLevels > 0) {
                            abufferedimage[0] =
                                    this.mipmapLevels > 0
                                            ? TextureUtils.scaleToPowerOfTwo(abufferedimage[0], k)
                                            : TextureUtils.scaleMinTo(abufferedimage[0], k);
                            int j1 = abufferedimage[0].getWidth();
                            label192:
                            if (j1 != width) {
                                if (!TextureUtils.isPowerOfTwo(width)) {
                                    OptiFineConfig.log(
                                            "Scaled non power of 2: "
                                                    + value.getIconName()
                                                    + ", "
                                                    + width
                                                    + " -> "
                                                    + j1);
                                    if (i == 0) {
                                        break label192;
                                    }
                                }

                                OptiFineConfig.log(
                                        "Scaled too small texture: "
                                                + value.getIconName()
                                                + ", "
                                                + width
                                                + " -> "
                                                + j1);
                            }
                        }
                    }

                    TextureMetadataSection metadata =
                            (TextureMetadataSection) iResource.getMetadata("texture");
                    if (metadata != null) {
                        List list = metadata.getListMipmaps();
                        if (!list.isEmpty()) {
                            int l1 = abufferedimage[0].getWidth();
                            int k1 = abufferedimage[0].getHeight();
                            if (MathHelper.roundUpToPowerOfTwo(l1) != l1
                                    || MathHelper.roundUpToPowerOfTwo(k1) != k1) {
                                throw new RuntimeException(
                                        "Unable to load extra miplevels, source-texture is not"
                                            + " power of two");
                            }
                        }

                        for (java.util.Iterator<?> __recoveryIterator171_49281 = (list).iterator();
                                (__recoveryIterator171_49281).hasNext(); ) {
                            int k3 =
                                    ((java.lang.Integer) (__recoveryIterator171_49281).next())
                                            .intValue();
                            if (k3 > 0
                                    && k3 < abufferedimage.length - 1
                                    && abufferedimage[k3] == null) {
                                ResourceLocation resourceLocationValue2 =
                                        this.completeResourceLocation(resourceLocation, k3);

                                try {
                                    abufferedimage[k3] =
                                            TextureUtil.readBufferedImage(
                                                    ShadersTex.loadResource(
                                                                    iResourceManager,
                                                                    resourceLocationValue2)
                                                            .getInputStream());
                                } catch (IOException ioexception) {
                                    net.minecraft.client.renderer.texture.TextureMap.logger.error(
                                            "Unable to load miplevel {} from: {}",
                                            new Object[] {k3, resourceLocationValue2, ioexception});
                                }

                                if (i != 0) {
                                    break;
                                }
                            }
                        }
                    }

                    AnimationMetadataSection animationMetadataSection =
                            (AnimationMetadataSection) iResource.getMetadata("animation");
                    value.loadSprite(abufferedimage, animationMetadataSection);
                } catch (RuntimeException runtimeexception) {
                    net.minecraft.client.renderer.texture.TextureMap.logger.error(
                            "Unable to parse metadata from " + resourceLocationValue,
                            runtimeexception);
                    ReflectorForge.FMLClientHandler_trackBrokenTexture(
                            resourceLocationValue, runtimeexception.getMessage());
                    continue;
                } catch (IOException ioexception1) {
                    net.minecraft.client.renderer.texture.TextureMap.logger.error(
                            "Using missing texture, unable to load "
                                    + resourceLocationValue
                                    + ", "
                                    + ioexception1.getClass().getName());
                    ReflectorForge.FMLClientHandler_trackMissingTexture(resourceLocationValue);
                    continue;
                }

                j = Math.min(j, Math.min(value.getIconWidth(), value.getIconHeight()));
                int i3 =
                        Math.min(
                                Integer.lowestOneBit(value.getIconWidth()),
                                Integer.lowestOneBit(value.getIconHeight()));
                if (i3 < l) {
                    net.minecraft.client.renderer.texture.TextureMap.logger.warn(
                            "Texture {} with size {}x{} limits mip level from {} to {}",
                            new Object[] {
                                resourceLocationValue,
                                value.getIconWidth(),
                                value.getIconHeight(),
                                MathHelper.floorLog2(l),
                                MathHelper.floorLog2(i3)
                            });
                    l = i3;
                }

                stitcher.addSprite(value);
            }

            if (i != 0) {
                break;
            }
        }

        int k2 = Math.min(j, l);
        int l2 = MathHelper.floorLog2(k2);
        if (l2 < 0) {
            l2 = 0;
        }

        if (l2 < this.mipmapLevels) {
            net.minecraft.client.renderer.texture.TextureMap.logger.info(
                    "{}: dropping miplevel from {} to {}, because of minimum power of two: {}",
                    new Object[] {this.basePath, this.mipmapLevels, l2, k2});
            this.mipmapLevels = l2;
        }

        Iterator iteratorValue = this.mapRegisteredSprites.values().iterator();

        while (iteratorValue.hasNext() && !this.skipFirst) {
            TextureAtlasSprite textureAtlasSpriteValue = (TextureAtlasSprite) iteratorValue.next();

            try {
                textureAtlasSpriteValue.generateMipmaps(this.mipmapLevels);
            } catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Applying mipmap");
                CrashReportCategory crashReportCategory =
                        crashReport.makeCategory("Sprite being mipmapped");
                crashReportCategory.addCrashSectionCallable(
                        "Sprite name",
                        new TextureMapSpriteNameCallable(this, textureAtlasSpriteValue));
                crashReportCategory.addCrashSectionCallable(
                        "Sprite size",
                        new TextureMapSpriteSizeCallable(this, textureAtlasSpriteValue));
                crashReportCategory.addCrashSectionCallable(
                        "Sprite frames",
                        new TextureMapSpriteFramesCallable(this, textureAtlasSpriteValue));
                crashReportCategory.addCrashSection("Mipmap levels", this.mipmapLevels);
                throw new ReportedException(crashReport);
            }

            if (i != 0) {
                break;
            }
        }

        this.missingImage.generateMipmaps(this.mipmapLevels);
        stitcher.addSprite(this.missingImage);
        this.skipFirst = false;

        try {
            stitcher.cj();
        } catch (StitcherException stitcherException) {
            throw stitcherException;
        }

        {
            net.minecraft.client.renderer.texture.TextureMap.logger.info(
                    "Created: {}x{} {}-atlas",
                    new Object[] {
                        stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), this.basePath
                    });
            if (OptiFineConfig.isShaders()) {
                ShadersTex.allocateTextureMap(
                        this.getGlTextureId(),
                        this.mipmapLevels,
                        stitcher.getCurrentWidth(),
                        stitcher.getCurrentHeight(),
                        stitcher,
                        this);
                if (!(i == 0)) {

                    TextureUtil.allocateTextureImpl(
                            this.getGlTextureId(),
                            this.mipmapLevels,
                            stitcher.getCurrentWidth(),
                            stitcher.getCurrentHeight());
                }
            } else {

                TextureUtil.allocateTextureImpl(
                        this.getGlTextureId(),
                        this.mipmapLevels,
                        stitcher.getCurrentWidth(),
                        stitcher.getCurrentHeight());
            }
        }

        HashMap hashmap = Maps.newHashMap(this.mapRegisteredSprites);

        for (java.util.Iterator<?> __recoveryIterator171_56394 =
                        (stitcher.getStichSlots()).iterator();
                (__recoveryIterator171_56394).hasNext(); ) {
            TextureAtlasSprite textureAtlasSpriteValue2 =
                    (TextureAtlasSprite) (__recoveryIterator171_56394).next();
            if (OptiFineConfig.isShaders()) {
                ShadersTex.setIconName(
                        ShadersTex.setSprite(textureAtlasSpriteValue2).getIconName());
            }

            String text = textureAtlasSpriteValue2.getIconName();
            hashmap.remove(text);
            this.mapUploadedSprites.put(text, textureAtlasSpriteValue2);

            try {
                label150:
                {
                    if (OptiFineConfig.isShaders()) {
                        ShadersTex.uploadTexSubForLoadAtlas(
                                textureAtlasSpriteValue2.getFrameTextureData(0),
                                textureAtlasSpriteValue2.getIconWidth(),
                                textureAtlasSpriteValue2.getIconHeight(),
                                textureAtlasSpriteValue2.getOriginX(),
                                textureAtlasSpriteValue2.getOriginY(),
                                false,
                                false);
                        if (i == 0) {
                            break label150;
                        }
                    }

                    TextureUtil.uploadTextureMipmap(
                            textureAtlasSpriteValue2.getFrameTextureData(0),
                            textureAtlasSpriteValue2.getIconWidth(),
                            textureAtlasSpriteValue2.getIconHeight(),
                            textureAtlasSpriteValue2.getOriginX(),
                            textureAtlasSpriteValue2.getOriginY(),
                            false,
                            false);
                }
            } catch (Throwable throwable1) {
                CrashReport crashReportValue =
                        CrashReport.makeCrashReport(throwable1, "Stitching texture atlas");
                CrashReportCategory crashReportCategoryValue =
                        crashReportValue.makeCategory("Texture being stitched together");
                crashReportCategoryValue.addCrashSection("Atlas path", this.basePath);
                crashReportCategoryValue.addCrashSection("Sprite", textureAtlasSpriteValue2);
                throw new ReportedException(crashReportValue);
            }

            if (textureAtlasSpriteValue2.dL()) {
                this.listAnimatedSprites.add(textureAtlasSpriteValue2);
            }

            if (i != 0) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_58997 = (hashmap.values()).iterator();
                (__recoveryIterator171_58997).hasNext(); ) {
            TextureAtlasSprite textureAtlasSpriteValue3 =
                    (TextureAtlasSprite) (__recoveryIterator171_58997).next();
            textureAtlasSpriteValue3.copyFrom(this.missingImage);
            if (i != 0) {
                break;
            }
        }

        if (OptiFineConfig.isMultiTexture()) {
            int j3 = stitcher.getCurrentWidth();
            int j2 = stitcher.getCurrentHeight();

            for (java.util.Iterator<?> __recoveryIterator171_59435 =
                            (stitcher.getStichSlots()).iterator();
                    (__recoveryIterator171_59435).hasNext(); ) {
                TextureAtlasSprite textureAtlasSpriteValue4 =
                        (TextureAtlasSprite) (__recoveryIterator171_59435).next();
                textureAtlasSpriteValue4.sheetWidth = j3;
                textureAtlasSpriteValue4.sheetHeight = j2;
                textureAtlasSpriteValue4.yK = this.mipmapLevels;
                TextureAtlasSprite aAo = textureAtlasSpriteValue4.aAo;
                if (aAo != null) {
                    aAo.sheetWidth = j3;
                    aAo.sheetHeight = j2;
                    aAo.yK = this.mipmapLevels;
                    textureAtlasSpriteValue4.nG();
                    byte b0 = 0;
                    byte b1 = 1;
                    TextureUtil.uploadTextureMipmap(
                            aAo.getFrameTextureData(0),
                            aAo.getIconWidth(),
                            aAo.getIconHeight(),
                            aAo.getOriginX(),
                            aAo.getOriginY(),
                            remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                            remix.compat.compiler.JvmBooleanSlots.fromInt(b1));
                }

                if (i != 0) {
                    break;
                }
            }

            OptiFineConfig.getMinecraft()
                    .getTextureManager()
                    .bindTexture(
                            net.minecraft.client.renderer.texture.TextureMap.locationBlocksTexture);
        }

        Reflector.callVoid(Reflector.Jd, new Object[] {this});
        this.method_80c3ce64938e63de8c07(stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
        if (OptiFineConfig.equals(System.getProperty("saveTextureMap"), "true")) {
            OptiFineConfig.dbg("Exporting texture map to: " + this.basePath + "_x.png");
            TextureUtil.saveGlTexture(
                    this.basePath.replaceAll(packedTextFragments[0], packedTextFragments[1]),
                    this.getGlTextureId(),
                    this.mipmapLevels,
                    stitcher.getCurrentWidth(),
                    stitcher.getCurrentHeight());
        }
    }

    static {
        net.minecraft.client.renderer.texture.TextureMap.packedTextFragments = new String[3];
        net.minecraft.client.renderer.texture.TextureMap.packedTextFragments[0] = new String("/");
        net.minecraft.client.renderer.texture.TextureMap.packedTextFragments[1] = new String("_");
        net.minecraft.client.renderer.texture.TextureMap.logger = LogManager.getLogger();
        net.minecraft.client.renderer.texture.TextureMap.LOCATION_MISSING_TEXTURE =
                new ResourceLocation("missingno");
        net.minecraft.client.renderer.texture.TextureMap.locationBlocksTexture =
                new ResourceLocation("textures/atlas/blocks.png");
        net.minecraft.client.renderer.texture.TextureMap.ENABLE_SKIP =
                Boolean.parseBoolean(System.getProperty("fml.skipFirstTextureLoad", "true"));
    }

    public void setMipmapLevels(int mipmapLevelsValue) {
        this.mipmapLevels = mipmapLevelsValue;
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}
