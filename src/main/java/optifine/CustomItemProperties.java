package optifine;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.client.resources.model.SimpleBakedModelBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmorArmorMaterial;
import net.minecraft.util.Facing;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

public class CustomItemProperties {
    public TextureAtlasSprite sprite;
    public Map<String, String> mapTextures;
    public String basePath;
    public RangeListInt enchantmentLevels;
    public int weight;
    public NbtTagValue[] nbtTagValues;
    public Map mapTextureLocations;
    public int damageMask;
    private int textureWidth;
    public float duration;
    public RangeListInt stackSize;

    public int layer;
    public int[] items;
    public float speed;

    public static final int B = 0;
    public boolean damagePercent;
    public Map<String, IBakedModel> mapModels;
    private int textureHeight;
    public static final int C = 1;
    public RangeListInt damage;
    public int type;
    public Map mapSprites;
    public static final int D = 2;
    public static final int E = 3;
    public int blend;
    public String texture;
    public ResourceLocation textureLocation;
    public float rotation;
    public String name = null;
    private static String[] packedTextFragments;
    public IBakedModel model;
    public RangeListInt enchantmentIds;

    private String getMapTexture(Map<String, String> map, String text, String textValue) {
        if (map == null) {
            return textValue;
        }

        String textValue2 = (String) map.get(text);
        return textValue2 == null ? textValue : textValue2;
    }

    private float parseFloat(String text, float f) {
        if (text == null) {
            return f;
        } else {
            text = text.trim();
            float f1 = OptiFineConfig.parseFloat(text, Float.MIN_VALUE);
            if (f1 == Float.MIN_VALUE) {
                OptiFineConfig.warn("Invalid float: " + text);
                return f;
            } else {
                return f1;
            }
        }
    }

    private static IBakedModel makeBakedModel(
            TextureMap textureMap,
            ItemModelGenerator itemModelGenerator,
            String[] astring,
            boolean flag) {
        ModelBlock modelBlock = makeModelBlock(astring);
        ModelBlock modelBlockValue = itemModelGenerator.makeItemModel(textureMap, modelBlock);
        return bakeModel(textureMap, modelBlockValue, flag);
    }

    private static BakedQuad makeBakedQuad(
            BlockPart blockPart,
            BlockPartFace blockPartFace,
            TextureAtlasSprite textureAtlasSprite,
            Facing facing,
            ModelRotation modelRotation,
            boolean flag) {
        FaceBakery faceBakery = new FaceBakery();
        return faceBakery.makeBakedQuad(
                blockPart.positionFrom,
                blockPart.positionTo,
                blockPartFace,
                textureAtlasSprite,
                facing,
                modelRotation,
                blockPart.partRotation,
                flag,
                blockPart.shade);
    }

    public float getTextureWidth(TextureManager textureManager) {
        if (this.textureWidth <= 0) {
            if (this.textureLocation != null) {
                ITextureObject texture = textureManager.getTexture(this.textureLocation);
                int glTextureId = texture.getGlTextureId();
                int j = GlStateManager.getBoundTexture();
                GlStateManager.bindTexture(glTextureId);
                this.textureWidth = GL11.glGetTexLevelParameteri(3553, 0, 4096);
                GlStateManager.bindTexture(j);
            }

            if (this.textureWidth <= 0) {
                this.textureWidth = 16;
            }
        }

        return this.textureWidth;
    }

    private int parseInt(String text, int i) {
        if (text == null) {
            return i;
        } else {
            text = text.trim();
            int j = OptiFineConfig.parseInt(text, Integer.MIN_VALUE);
            if (j == Integer.MIN_VALUE) {
                OptiFineConfig.warn("Invalid integer: " + text);
                return i;
            } else {
                return j;
            }
        }
    }

    public void updateModel(TextureMap textureMap, ItemModelGenerator itemModelGenerator) {
        String[] astring = this.getModelTextures();
        int j = ReflectorMethod.f();
        boolean flag = this.aY();
        int i = j;
        this.model = makeBakedModel(textureMap, itemModelGenerator, astring, flag);
        if (this.type == 1 && this.mapTextures != null) {
            for (java.util.Iterator<?> __recoveryIterator171_6630 =
                            (this.mapTextures.keySet()).iterator();
                    (__recoveryIterator171_6630).hasNext(); ) {
                String text = (String) (__recoveryIterator171_6630).next();
                String textValue = ((java.lang.String) this.mapTextures.get(text));
                String textValue2 = StrUtils.removePrefix(text, "texture.");
                if (textValue2.startsWith("bow") || textValue2.startsWith("fishing_rod")) {
                    String[] astring1 = new String[] {textValue};
                    IBakedModel iBakedModel =
                            makeBakedModel(textureMap, itemModelGenerator, astring1, flag);
                    if (this.mapModels == null) {
                        this.mapModels = new HashMap<>();
                    }

                    this.mapModels.put(textValue2, iBakedModel);
                }

                if (i != 0) {
                    break;
                }
            }
        }
    }

    private int[] parseItems(String text, String textValue) {
        int i = ReflectorMethod.cR();
        if (text == null) {
            text = textValue;
        }

        if (text == null) {
            return null;
        }

        text = text.trim();
        TreeSet treeset = new TreeSet();
        String[] astring = OptiFineConfig.tokenize(text, packedTextFragments[4]);
        int k = 0;

        while (k < astring.length) {
            label76:
            {
                String textValue2 = astring[k];
                int j = OptiFineConfig.parseInt(textValue2, -1);
                if (j >= 0) {
                    treeset.add(new Integer(j));
                    if (i != 0) {
                        break label76;
                    }
                }

                if (textValue2.contains(packedTextFragments[5])) {
                    String[] astring1 = OptiFineConfig.tokenize(textValue2, packedTextFragments[6]);
                    if (astring1.length == 2) {
                        int l = OptiFineConfig.parseInt(astring1[0], -1);
                        int i1 = OptiFineConfig.parseInt(astring1[1], -1);
                        if (l >= 0 && i1 >= 0) {
                            int j1 = Math.min(l, i1);
                            int k1 = Math.max(l, i1);
                            int l1 = j1;

                            do {
                                if (l1 > k1 && i != 0) {
                                    break label76;
                                }

                                treeset.add(new Integer(l1));
                                l1++;
                            } while (i != 0);
                        }
                    }
                }

                Item item = Item.getByNameOrId(textValue2);
                if (item == null) {
                    OptiFineConfig.warn("Item not found: " + textValue2);
                    if (i != 0) {
                        break label76;
                    }
                }

                int j2 = Item.getIdFromItem(item);
                if (j2 < 0) {
                    OptiFineConfig.warn("Item not found: " + textValue2);
                    if (i != 0) {
                        break label76;
                    }
                }

                treeset.add(new Integer(j2));
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        Integer[] ainteger = ((java.lang.Integer[]) treeset.toArray(new Integer[treeset.size()]));
        int[] aint = new int[ainteger.length];
        int i2 = 0;

        while (i2 < aint.length) {
            aint[i2] = ainteger[i2];
            i2++;
            if (i == 0) {
                break;
            }
        }

        return aint;
    }

    private static String method_89cf14e7925c09ce22d5(
            String text,
            String textValue,
            String textValue2,
            String textValue3,
            String textValue4,
            int i,
            Map<String, String> map) {
        if (text == null) {
            text = textValue;
        }

        if (text == null) {
            text = textValue2;
        }

        if (text != null) {
            String textValue5 = ".png";
            if (text.endsWith(textValue5)) {
                text = text.substring(0, text.length() - textValue5.length());
            }

            return jr(text, textValue4);
        } else {
            if (i == 3) {
                return null;
            }

            if (map != null) {
                String textValue6 = (String) map.get("texture.bow_standby");
                if (textValue6 != null) {
                    return textValue6;
                }
            }

            String textValue7 = textValue3;
            int j = textValue3.lastIndexOf(47);
            if (j >= 0) {
                textValue7 = textValue3.substring(j + 1);
            }

            int k = textValue7.lastIndexOf(46);
            if (k >= 0) {
                textValue7 = textValue7.substring(0, k);
            }

            return jr(textValue7, textValue4);
        }
    }

    private NbtTagValue[] parseNbtTagValues(Properties properties) {
        int j = ReflectorMethod.cR();
        String text = "nbt.";
        Map map = getMatchingProperties(properties, text);
        int i = j;
        if (map.size() <= 0) {
            return null;
        }

        ArrayList arrayList = new ArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_11529 = (map.keySet()).iterator();
                (__recoveryIterator171_11529).hasNext(); ) {
            String textValue = (String) (__recoveryIterator171_11529).next();
            String textValue2 = (String) map.get(textValue);
            String textValue3 = textValue.substring(text.length());
            NbtTagValue nbtTagValue = new NbtTagValue(textValue3, textValue2);
            arrayList.add(nbtTagValue);
            if (i == 0) {
                break;
            }
        }

        return ((optifine.NbtTagValue[]) arrayList.toArray(new NbtTagValue[arrayList.size()]));
    }

    private static Map parseTextures(Properties properties, String text) {
        int j = ReflectorMethod.f();
        String textValue = "texture.";
        int i = j;
        Map map = getMatchingProperties(properties, textValue);
        if (map.size() <= 0) {
            return null;
        }

        Set set = map.keySet();
        LinkedHashMap linkedhashmap = new LinkedHashMap();

        for (java.util.Iterator<?> __recoveryIterator171_12411 = (set).iterator();
                (__recoveryIterator171_12411).hasNext(); ) {
            String textValue2 = (String) (__recoveryIterator171_12411).next();
            String textValue3 = (String) map.get(textValue2);
            textValue3 = jr(textValue3, text);
            linkedhashmap.put(textValue2, textValue3);
            if (i != 0) {
                break;
            }
        }

        return linkedhashmap;
    }

    public CustomItemProperties(Properties properties, String text) {
        int j = ReflectorMethod.f();
        this.basePath = null;
        this.type = 1;
        this.items = null;
        this.texture = null;
        this.mapTextures = null;
        this.damage = null;
        this.damagePercent = false;
        this.damageMask = 0;
        this.stackSize = null;
        this.enchantmentIds = null;
        this.enchantmentLevels = null;
        this.nbtTagValues = null;
        this.blend = 1;
        this.speed = 0.0F;
        this.rotation = 0.0F;
        this.layer = 0;
        this.duration = 1.0F;
        this.weight = 0;
        this.textureLocation = null;
        this.mapTextureLocations = null;
        this.sprite = null;
        this.mapSprites = null;
        this.model = null;
        this.mapModels = null;
        this.textureWidth = 0;
        int i = j;
        this.textureHeight = 0;
        this.name = parseName(text);
        this.basePath = parseBasePath(text);
        this.type = this.parseType(properties.getProperty("type"));
        this.items =
                this.parseItems(
                        properties.getProperty("items"), properties.getProperty("matchItems"));
        this.mapTextures = parseTextures(properties, this.basePath);
        this.texture =
                method_89cf14e7925c09ce22d5(
                        properties.getProperty("texture"),
                        properties.getProperty("tile"),
                        properties.getProperty("source"),
                        text,
                        this.basePath,
                        this.type,
                        this.mapTextures);
        String property = properties.getProperty("damage");
        if (property != null) {
            this.damagePercent = property.contains(packedTextFragments[0]);
            property.replace(packedTextFragments[1], packedTextFragments[2]);
            this.damage = this.parseRangeListInt(property);
            this.damageMask = this.parseInt(properties.getProperty("damageMask"), 0);
        }

        this.stackSize = this.parseRangeListInt(properties.getProperty("stackSize"));
        this.enchantmentIds = this.parseRangeListInt(properties.getProperty("enchantmentIDs"));
        this.enchantmentLevels =
                this.parseRangeListInt(properties.getProperty("enchantmentLevels"));
        this.nbtTagValues = this.parseNbtTagValues(properties);
        this.blend = Blender.parseBlend(properties.getProperty("blend"));
        this.speed = this.parseFloat(properties.getProperty("speed"), 0.0F);
        this.rotation = this.parseFloat(properties.getProperty("rotation"), 0.0F);
        this.layer = this.parseInt(properties.getProperty("layer"), 0);
        this.weight = this.parseInt(properties.getProperty("weight"), 0);
        this.duration = this.parseFloat(properties.getProperty("duration"), 1.0F);
        if (i != 0) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }
    }

    public IBakedModel method_e7b450baa135db45c9ac(ModelResourceLocation modelResourceLocation) {
        if (modelResourceLocation != null && this.mapTextures != null) {
            String text = modelResourceLocation.getResourcePath();
            if (this.mapModels != null) {
                IBakedModel iBakedModel =
                        ((net.minecraft.client.resources.model.IBakedModel)
                                this.mapModels.get(text));
                if (iBakedModel != null) {
                    return iBakedModel;
                }
            }
        }

        return this.model;
    }

    private ResourceLocation getTextureLocation(String text) {
        if (text == null) {
            return null;
        }

        ResourceLocation resourceLocation = new ResourceLocation(text);
        String textValue = resourceLocation.getResourceDomain();
        String textValue2 = resourceLocation.getResourcePath();
        if (!textValue2.contains(packedTextFragments[14])) {
            textValue2 = "textures/blocks/" + textValue2;
        }

        String textValue3 = textValue2 + ".png";
        ResourceLocation resourceLocationValue = new ResourceLocation(textValue, textValue3);
        boolean hasResource = OptiFineConfig.hasResource(resourceLocationValue);
        if (!hasResource) {
            OptiFineConfig.warn("File not found: " + textValue3);
        }

        return resourceLocationValue;
    }

    private boolean aY() {
        return true;
    }

    public void updateIcons(TextureMap textureMap) {
        int i = ReflectorMethod.cR();
        if (this.texture != null) {
            this.textureLocation = this.getTextureLocation(this.texture);
            if (this.type == 1) {
                ResourceLocation resourceLocation = this.getSpriteLocation(this.textureLocation);
                this.sprite = textureMap.registerSprite(resourceLocation);
            }
        }

        if (this.mapTextures != null) {
            this.mapTextureLocations = new HashMap();
            this.mapSprites = new HashMap();

            for (java.util.Iterator<?> __recoveryIterator171_37759 =
                            (this.mapTextures.keySet()).iterator();
                    (__recoveryIterator171_37759).hasNext(); ) {
                String text = (String) (__recoveryIterator171_37759).next();
                String textValue = ((java.lang.String) this.mapTextures.get(text));
                ResourceLocation resourceLocationValue = this.getTextureLocation(textValue);
                this.mapTextureLocations.put(text, resourceLocationValue);
                if (this.type == 1) {
                    ResourceLocation resourceLocationValue2 =
                            this.getSpriteLocation(resourceLocationValue);
                    TextureAtlasSprite textureAtlasSprite =
                            textureMap.registerSprite(resourceLocationValue2);
                    this.mapSprites.put(text, textureAtlasSprite);
                }

                if (i == 0) {
                    break;
                }
            }
        }
    }

    private String[] getModelTextures() {
        if (this.type == 1 && this.items.length == 1) {
            Item item = Item.getItemById(this.items[0]);
            if (item == Items.potionitem
                    && this.damage != null
                    && this.damage.getCountRanges() > 0) {
                RangeInt range = this.damage.getRange(0);
                int min = range.getMin();
                int j = (min & 16384) != 0 ? 1 : 0;
                String text =
                        this.getMapTexture(
                                this.mapTextures, "texture.potion_overlay", "items/potion_overlay");
                String textValue = null;
                if (j != 0) {
                    textValue =
                            this.getMapTexture(
                                    this.mapTextures,
                                    "texture.potion_bottle_splash",
                                    "items/potion_bottle_splash");
                } else {
                    textValue =
                            this.getMapTexture(
                                    this.mapTextures,
                                    "texture.potion_bottle_drinkable",
                                    "items/potion_bottle_drinkable");
                }

                return new String[] {text, textValue};
            }

            if (item instanceof ItemArmor) {
                ItemArmor itemArmor = (ItemArmor) item;
                if (itemArmor.getArmorMaterial() == ItemArmorArmorMaterial.LEATHER) {
                    String textValue2 = "leather";
                    String textValue3 = "helmet";
                    if (itemArmor.armorType == 0) {
                        textValue3 = "helmet";
                    }

                    if (itemArmor.armorType == 1) {
                        textValue3 = "chestplate";
                    }

                    if (itemArmor.armorType == 2) {
                        textValue3 = "leggings";
                    }

                    if (itemArmor.armorType == 3) {
                        textValue3 = "boots";
                    }

                    String textValue4 = textValue2 + packedTextFragments[15] + textValue3;
                    String textValue5 =
                            this.getMapTexture(
                                    this.mapTextures,
                                    "texture." + textValue4,
                                    "items/" + textValue4);
                    String textValue6 =
                            this.getMapTexture(
                                    this.mapTextures,
                                    "texture." + textValue4 + "_overlay",
                                    "items/" + textValue4 + "_overlay");
                    return new String[] {textValue5, textValue6};
                }
            }
        }

        return new String[] {this.texture};
    }

    private static IBakedModel bakeModel(
            TextureMap textureMap, ModelBlock modelBlock, boolean flag) {
        int j = ReflectorMethod.cR();
        ModelRotation modelRotation = ModelRotation.X0_Y0;
        byte b0 = 0;
        int i = j;
        TextureAtlasSprite textureAtlasSprite =
                textureMap.getSpriteSafe(modelBlock.resolveTextureName("particle"));
        SimpleBakedModelBuilder simpleBakedModelBuilder =
                new SimpleBakedModelBuilder(modelBlock).setTexture(textureAtlasSprite);

        for (java.util.Iterator<?> __recoveryIterator171_46034 =
                        (modelBlock.getElements()).iterator();
                (__recoveryIterator171_46034).hasNext(); ) {
            BlockPart blockPart = (BlockPart) (__recoveryIterator171_46034).next();
            Iterator iterator = blockPart.mapFaces.keySet().iterator();

            while (true) {
                if (iterator.hasNext()) {
                    Facing facing = (Facing) iterator.next();
                    BlockPartFace blockPartFace = (BlockPartFace) blockPart.mapFaces.get(facing);
                    if (!flag) {
                        blockPartFace =
                                new BlockPartFace(
                                        blockPartFace.cullFace,
                                        -1,
                                        blockPartFace.texture,
                                        blockPartFace.blockFaceUV);
                    }

                    {
                        TextureAtlasSprite textureAtlasSpriteValue =
                                textureMap.getSpriteSafe(
                                        modelBlock.resolveTextureName(blockPartFace.texture));
                        BakedQuad bakedQuad =
                                makeBakedQuad(
                                        blockPart,
                                        blockPartFace,
                                        textureAtlasSpriteValue,
                                        facing,
                                        modelRotation,
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b0));
                        if (blockPartFace.cullFace == null) {
                            simpleBakedModelBuilder.addGeneralQuad(bakedQuad);
                            if (!(i != 0)) {

                                simpleBakedModelBuilder.addFaceQuad(
                                        modelRotation.rotateFace(blockPartFace.cullFace),
                                        bakedQuad);
                            }
                        } else {

                            simpleBakedModelBuilder.addFaceQuad(
                                    modelRotation.rotateFace(blockPartFace.cullFace), bakedQuad);
                        }
                    }

                    if (i != 0) {
                        continue;
                    }
                }

                if (i == 0) {
                    return simpleBakedModelBuilder.makeBakedModel();
                }
                break;
            }
        }

        return simpleBakedModelBuilder.makeBakedModel();
    }

    private static String parseName(String text) {
        String textValue = text;
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

    private int parseType(String text) {
        if (text == null) {
            return 1;
        }

        if (text.equals("item")) {
            return 1;
        }

        if (text.equals("enchantment")) {
            return 2;
        }

        if (text.equals("armor")) {
            return 3;
        }

        OptiFineConfig.warn("Unknown method: " + text);
        return 0;
    }

    private static Map getMatchingProperties(Properties properties, String text) {
        int j = ReflectorMethod.cR();
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        Set set = properties.keySet();
        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_49429 = (set).iterator();
                (__recoveryIterator171_49429).hasNext(); ) {
            String textValue = (String) (__recoveryIterator171_49429).next();
            String property = properties.getProperty(textValue);
            if (textValue.startsWith(text)) {
                linkedhashmap.put(textValue, property);
            }

            if (i == 0) {
                break;
            }
        }

        return linkedhashmap;
    }

    @Override
    public String toString() {
        return packedTextFragments[17]
                + this.basePath
                + packedTextFragments[18]
                + this.name
                + ", type: "
                + this.type
                + ", items: ["
                + OptiFineConfig.arrayToString(this.items)
                + "], textture: "
                + this.texture;
    }

    private RangeListInt parseRangeListInt(String text) {
        int i = ReflectorMethod.f();
        if (text == null) {
            return null;
        }

        String[] astring = OptiFineConfig.tokenize(text, packedTextFragments[9]);
        RangeListInt rangeListInt = new RangeListInt();
        int j = 0;

        while (j < astring.length) {
            String textValue = astring[j];
            RangeInt rangeInt = this.parseRangeInt(textValue);
            if (rangeInt == null) {
                OptiFineConfig.warn("Invalid range list: " + text);
                return null;
            }

            rangeListInt.addRange(rangeInt);
            j++;
            if (i != 0) {
                break;
            }
        }

        return rangeListInt;
    }

    private static String parseBasePath(String text) {
        int i = text.lastIndexOf(47);
        return i < 0 ? packedTextFragments[3] : text.substring(0, i);
    }

    static {
        optifine.CustomItemProperties.packedTextFragments = new String[20];
        optifine.CustomItemProperties.packedTextFragments[0] = new String("%");
        optifine.CustomItemProperties.packedTextFragments[1] = new String("%");
        optifine.CustomItemProperties.packedTextFragments[2] = new String("");
        optifine.CustomItemProperties.packedTextFragments[3] = new String("");
        optifine.CustomItemProperties.packedTextFragments[4] = new String(" ");
        optifine.CustomItemProperties.packedTextFragments[5] = new String("-");
        optifine.CustomItemProperties.packedTextFragments[6] = new String("-");
        optifine.CustomItemProperties.packedTextFragments[7] = new String("/");
        optifine.CustomItemProperties.packedTextFragments[8] = new String("/");
        optifine.CustomItemProperties.packedTextFragments[9] = new String(" ");
        optifine.CustomItemProperties.packedTextFragments[10] = new String("-");
        optifine.CustomItemProperties.packedTextFragments[11] = new String("");
        optifine.CustomItemProperties.packedTextFragments[12] = new String("-");
        optifine.CustomItemProperties.packedTextFragments[13] = new String("-");
        optifine.CustomItemProperties.packedTextFragments[14] = new String("/");
        optifine.CustomItemProperties.packedTextFragments[15] = new String("_");
        optifine.CustomItemProperties.packedTextFragments[16] = new String("\"");
        optifine.CustomItemProperties.packedTextFragments[17] = new String("");
        optifine.CustomItemProperties.packedTextFragments[18] = new String("/");
    }

    public float getTextureHeight(TextureManager textureManager) {
        if (this.textureHeight <= 0) {
            if (this.textureLocation != null) {
                ITextureObject texture = textureManager.getTexture(this.textureLocation);
                int glTextureId = texture.getGlTextureId();
                int j = GlStateManager.getBoundTexture();
                GlStateManager.bindTexture(glTextureId);
                this.textureHeight = GL11.glGetTexLevelParameteri(3553, 0, 4097);
                GlStateManager.bindTexture(j);
            }

            if (this.textureHeight <= 0) {
                this.textureHeight = 16;
            }
        }

        return this.textureHeight;
    }

    private static ModelBlock makeModelBlock(String[] astring) {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("{\"parent\": \"builtin/generated\",\"textures\": {");
        int k = ReflectorMethod.cR();
        int j = 0;
        int i = k;

        while (j < astring.length) {
            String text = astring[j];
            if (j > 0) {
                stringbuffer.append(", ");
            }

            stringbuffer.append("\"layer" + j + "\": \"" + text + packedTextFragments[16]);
            j++;
            if (i == 0) {
                break;
            }
        }

        stringbuffer.append("}}");
        String textValue = stringbuffer.toString();
        return ModelBlock.deserialize(textValue);
    }

    public boolean isValid(String text) {
        if (this.name == null || this.name.length() <= 0) {
            OptiFineConfig.warn("No name found: " + text);
            return false;
        } else if (this.basePath == null) {
            OptiFineConfig.warn("No base path found: " + text);
            return false;
        } else if (this.type == 0) {
            OptiFineConfig.warn("No type defined: " + text);
            return false;
        } else if ((this.type == 1 || this.type == 3) && this.items == null) {
            OptiFineConfig.warn("No items defined: " + text);
            return false;
        } else if (this.texture == null && this.mapTextures == null) {
            OptiFineConfig.warn("No texture specified: " + text);
            return false;
        } else if (this.type == 2 && this.enchantmentIds == null) {
            OptiFineConfig.warn("No enchantmentIDs specified: " + text);
            return false;
        } else {
            return true;
        }
    }

    private static String jr(String text, String textValue) {
        text = TextureUtils.fixResourcePath(text, textValue);
        if (!text.startsWith(textValue)
                && !text.startsWith("textures/")
                && !text.startsWith("mcpatcher/")) {
            text = textValue + packedTextFragments[7] + text;
        }

        if (text.endsWith(".png")) {
            text = text.substring(0, text.length() - 4);
        }

        String textValue2 = "textures/blocks/";
        if (text.startsWith(textValue2)) {
            text = text.substring(textValue2.length());
        }

        if (text.startsWith(packedTextFragments[8])) {
            text = text.substring(1);
        }

        return text;
    }

    private ResourceLocation getSpriteLocation(ResourceLocation resourceLocation) {
        String text = resourceLocation.getResourcePath();
        text = StrUtils.removePrefix(text, "textures/");
        text = StrUtils.removeSuffix(text, ".png");
        return new ResourceLocation(resourceLocation.getResourceDomain(), text);
    }

    private RangeInt parseRangeInt(String text) {
        int i = ReflectorMethod.cR();
        if (text == null) {
            return null;
        }

        text = text.trim();
        int j =
                text.length()
                        - text.replace(packedTextFragments[10], packedTextFragments[11]).length();
        if (j > 1) {
            OptiFineConfig.warn("Invalid range: " + text);
            return null;
        }

        String[] astring = OptiFineConfig.tokenize(text, "- ");
        int[] aint = new int[astring.length];
        int k = 0;

        while (k < astring.length) {
            String textValue = astring[k];
            int l = OptiFineConfig.parseInt(textValue, -1);
            if (l < 0) {
                OptiFineConfig.warn("Invalid range: " + text);
                return null;
            }

            aint[k] = l;
            k++;
            if (i == 0) {
                break;
            }
        }

        if (aint.length == 1) {
            k = aint[0];
            if (text.startsWith(packedTextFragments[12])) {
                return new RangeInt(0, k);
            } else {
                return text.endsWith(packedTextFragments[13])
                        ? new RangeInt(k, 255)
                        : new RangeInt(k, k);
            }
        } else if (aint.length == 2) {
            k = Math.min(aint[0], aint[1]);
            int i1 = Math.max(aint[0], aint[1]);
            return new RangeInt(k, i1);
        } else {
            OptiFineConfig.warn("Invalid range: " + text);
            return null;
        }
    }
}
