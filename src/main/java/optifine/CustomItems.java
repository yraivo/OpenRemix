package optifine;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CustomItems {
    public static final int f = 16384;
    public static String h;
    public static String kG;
    public static String mC;
    private static boolean useGlint;
    private static String[] packedTextFragments;
    private static Map mapPotionIds;
    public static String sT;

    public static final int Ao = 63;
    private static CustomItemProperties[][] enchantmentProperties;
    private static int[] AF;
    private static CustomItemProperties[][] itemProperties;

    private static ItemModelGenerator itemModelGenerator;
    private static int[][] EMPTY_INT2_ARRAY;
    public static String te;
    public static String vI;

    public static boolean isUseGlint() {
        return optifine.CustomItems.useGlint;
    }

    private static int method_7e9ca755b89de09da85b(String text) {
        String textValue = "potion." + text;
        Potion[] bzT = Potion.potionTypes;
        int k = ReflectorMethod.f();
        int j = 0;
        int i = k;

        while (j < bzT.length) {
            Potion potion = bzT[j];
            if (potion != null) {
                String textValue2 = potion.getName();
                if (textValue.equals(textValue2)) {
                    return potion.getId();
                }
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return -1;
    }

    private static List makePropertyList(CustomItemProperties[][] customItemPropertiesArrayArray) {
        int k = ReflectorMethod.cR();
        ArrayList arrayList = new ArrayList();
        int i = k;
        if (customItemPropertiesArrayArray != null) {
            int j = 0;

            while (j < customItemPropertiesArrayArray.length) {
                CustomItemProperties[] customItemPropertiesArray =
                        customItemPropertiesArrayArray[j];
                ArrayList arrayListValue = null;
                if (customItemPropertiesArray != null) {
                    arrayListValue = new ArrayList<>(Arrays.asList(customItemPropertiesArray));
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

    public static void updateIcons(TextureMap textureMap) {
        int k = ReflectorMethod.f();
        optifine.CustomItems.itemProperties = (CustomItemProperties[][]) null;
        int i = k;
        optifine.CustomItems.enchantmentProperties = (CustomItemProperties[][]) null;
        optifine.CustomItems.useGlint = true;
        if (OptiFineConfig.isCustomItems()) {
            readCitProperties("mcpatcher/cit.properties");
            IResourcePack[] iResourcePackArray = OptiFineConfig.getResourcePacks();
            int j = iResourcePackArray.length - 1;

            while (j >= 0) {
                IResourcePack iResourcePack = iResourcePackArray[j];
                method_0a6fd1714e0efc15d8b7(textureMap, iResourcePack);
                j--;
                if (i != 0) {
                    break;
                }
            }

            method_0a6fd1714e0efc15d8b7(textureMap, OptiFineConfig.getDefaultResourcePack());
            if (optifine.CustomItems.itemProperties.length <= 0) {
                optifine.CustomItems.itemProperties = (CustomItemProperties[][]) null;
            }

            if (optifine.CustomItems.enchantmentProperties.length <= 0) {
                optifine.CustomItems.enchantmentProperties = (CustomItemProperties[][]) null;
            }
        }
    }

    private static Map method_9b099f0135744742c93b(IResourcePack iResourcePack, boolean flag) {
        int k = ReflectorMethod.cR();
        HashMap hashmap = new HashMap();
        int i = k;
        String text = flag ? "splash/" : "normal/";
        String[] astring =
                new String[] {"mcpatcher/cit/potion/" + text, "mcpatcher/cit/Potion/" + text};
        String[] astring1 = new String[] {".png"};
        String[] astring2 = ResUtils.collectFiles(iResourcePack, astring, astring1);
        int j = 0;

        while (j < astring2.length) {
            String textValue = astring2[j];
            String textValue2 = StrUtils.removePrefixSuffix(textValue, astring, astring1);
            Properties properties = method_f84109a7e9586b3ffa44(textValue2, flag, textValue);
            if (properties != null) {
                String textValue3 = StrUtils.removeSuffix(textValue, astring1) + ".properties";
                CustomItemProperties customItemProperties =
                        new CustomItemProperties(properties, textValue3);
                hashmap.put(textValue3, customItemProperties);
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return hashmap;
    }

    private static void method_0a6fd1714e0efc15d8b7(
            TextureMap textureMap, IResourcePack iResourcePack) {
        String[] astring =
                ResUtils.collectFiles(
                        iResourcePack, "mcpatcher/cit/", ".properties", (String[]) null);
        int l = ReflectorMethod.cR();
        Map map = makeAutoImageProperties(iResourcePack);
        int i = l;
        if (map.size() > 0) {
            Set set = map.keySet();
            String[] astring1 = ((java.lang.String[]) set.toArray(new String[set.size()]));
            astring = (String[]) OptiFineConfig.addObjectsToArray(astring, astring1);
        }

        Arrays.sort(astring);
        List list = makePropertyList(optifine.CustomItems.itemProperties);
        List listValue = makePropertyList(optifine.CustomItems.enchantmentProperties);
        int j = 0;

        while (j < astring.length) {
            String text = astring[j];
            OptiFineConfig.dbg("CustomItems: " + text);

            label98:
            {
                CustomItemProperties customItemProperties;
                label97:
                {
                    InputStream inputstream;
                    label96:
                    {
                        try {
                            customItemProperties = null;
                            if (map.containsKey(text)) {
                                customItemProperties = (CustomItemProperties) map.get(text);
                            }

                            if (customItemProperties != null) {
                                break label97;
                            }

                            ResourceLocation resourceLocation = new ResourceLocation(text);
                            inputstream = iResourcePack.getInputStream(resourceLocation);
                            if (inputstream != null) {
                                break label96;
                            }

                            OptiFineConfig.warn("CustomItems file not found: " + text);
                        } catch (FileNotFoundException filenotfoundexception5) {
                            OptiFineConfig.warn("CustomItems file not found: " + text);
                            break label98;
                        } catch (Exception exception2) {
                            exception2.printStackTrace();
                            break label98;
                        }

                        if (i != 0) {
                            break label98;
                        }
                    }

                    try {
                        Properties properties = new Properties();
                        properties.load(inputstream);
                        customItemProperties = new CustomItemProperties(properties, text);
                    } catch (FileNotFoundException filenotfoundexception4) {
                        OptiFineConfig.warn("CustomItems file not found: " + text);
                        break label98;
                    } catch (Exception exception1) {
                        exception1.printStackTrace();
                        break label98;
                    }
                }

                try {
                    if (false)
                        throw (java.io.FileNotFoundException)
                                null; // Original JVM checked handler; no emitted instructions.
                    try {
                        if (false)
                            throw (java.io.FileNotFoundException)
                                    null; // Original JVM checked handler; no emitted instructions.
                        if (customItemProperties.isValid(text)) {
                            customItemProperties.updateIcons(textureMap);
                            addToItemList(customItemProperties, list);
                            method_5f839dd87721dd7dc5f4(customItemProperties, listValue);
                        }
                    } catch (FileNotFoundException filenotfoundexception2) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(
                                method_fd3f0eb1dd2c178d63ce(filenotfoundexception2));
                    }
                } catch (FileNotFoundException filenotfoundexception3) {
                    OptiFineConfig.warn("CustomItems file not found: " + text);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        optifine.CustomItems.itemProperties = propertyListToArray(list);
        optifine.CustomItems.enchantmentProperties = propertyListToArray(listValue);
        Comparator comparator = getPropertiesComparator();
        int k = 0;

        while (k < optifine.CustomItems.itemProperties.length) {
            CustomItemProperties[] customItemPropertiesArray =
                    optifine.CustomItems.itemProperties[k];

            try {
                if (false)
                    throw (java.io.FileNotFoundException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (customItemPropertiesArray != null) {
                    Arrays.sort(customItemPropertiesArray, comparator);
                }
            } catch (FileNotFoundException filenotfoundexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_fd3f0eb1dd2c178d63ce(filenotfoundexception1));
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        k = 0;

        while (k < optifine.CustomItems.enchantmentProperties.length) {
            CustomItemProperties[] customItemPropertiesArrayValue =
                    optifine.CustomItems.enchantmentProperties[k];

            try {
                if (false)
                    throw (java.io.FileNotFoundException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (customItemPropertiesArrayValue != null) {
                    Arrays.sort(customItemPropertiesArrayValue, comparator);
                }
            } catch (FileNotFoundException filenotfoundexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_fd3f0eb1dd2c178d63ce(filenotfoundexception));
            }

            k++;
            if (i == 0) {
                break;
            }
        }
    }

    public static boolean renderCustomArmorEffect(
            LivingEntity livingEntity,
            ItemStack itemStack,
            ModelBase modelBase,
            float f,
            float f1,
            float f10,
            float f2,
            float f3,
            float f4,
            float f5) {
        int i = ReflectorMethod.f();
        if (optifine.CustomItems.enchantmentProperties == null) {
            return false;
        }

        if (OptiFineConfig.isShaders() && Shaders.isShadowPass) {
            return false;
        }

        if (itemStack == null) {
            return false;
        }

        int[][] aint = getEnchantmentIdLevels(itemStack);
        if (aint.length <= 0) {
            return false;
        }

        HashSet hashset = null;
        byte b0 = 0;
        TextureManager textureManager = OptiFineConfig.getTextureManager();
        int j = 0;

        while (j < aint.length) {
            int k = aint[j][0];
            if (k >= 0 && k < optifine.CustomItems.enchantmentProperties.length) {
                CustomItemProperties[] customItemPropertiesArray =
                        optifine.CustomItems.enchantmentProperties[k];
                if (customItemPropertiesArray != null) {
                    int l = 0;

                    while (l < customItemPropertiesArray.length) {
                        CustomItemProperties customItemProperties = customItemPropertiesArray[l];
                        if (hashset == null) {
                            hashset = new HashSet();
                        }

                        if (hashset.add(k)
                                && matchesProperties(customItemProperties, itemStack, aint)
                                && customItemProperties.textureLocation != null) {
                            textureManager.bindTexture(customItemProperties.textureLocation);
                            float textureWidth =
                                    customItemProperties.getTextureWidth(textureManager);
                            if (b0 == 0) {
                                b0 = 1;
                                if (OptiFineConfig.isShaders()) {
                                    ShadersRender.renderEnchantedGlintBegin();
                                }

                                GlStateManager.enableBlend();
                                GlStateManager.depthFunc(514);
                                GlStateManager.depthMask(false);
                            }

                            Blender.setupBlend(customItemProperties.blend, 1.0F);
                            GlStateManager.disableLighting();
                            GlStateManager.matrixMode(5890);
                            GlStateManager.loadIdentity();
                            GlStateManager.rotate(customItemProperties.rotation, 0.0F, 0.0F, 1.0F);
                            float f7 = textureWidth / 8.0F;
                            GlStateManager.scale(f7, f7 / 2.0F, f7);
                            float f9 = customItemProperties.speed;
                            long systemTime = MinecraftClient.getSystemTime();

                            float f8 = f9 * (float) (systemTime % 3000L) / 3000.0F / 8.0F;
                            GlStateManager.translate(0.0F, f8, 0.0F);
                            GlStateManager.matrixMode(5888);
                            modelBase.render(livingEntity, f, f1, f2, f3, f4, f5);
                        }

                        l++;
                        if (i != 0) {
                            break;
                        }
                    }
                }
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        if (b0 != 0) {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthMask(true);
            GlStateManager.depthFunc(515);
            GlStateManager.disableBlend();
            if (OptiFineConfig.isShaders()) {
                ShadersRender.renderEnchantedGlintEnd();
            }
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    private static void addToList(CustomItemProperties customItemProperties, List list, int i) {
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

        listValue.add(customItemProperties);
    }

    private static Comparator getPropertiesComparator() {
        return new GetPropertiesComparator();
    }

    static {
        optifine.CustomItems.packedTextFragments = new String[7];
        optifine.CustomItems.packedTextFragments[0] = new String("");
        optifine.CustomItems.packedTextFragments[1] = new String(" ");
        optifine.CustomItems.packedTextFragments[2] = new String("");
        optifine.CustomItems.packedTextFragments[3] = new String("");
        optifine.CustomItems.packedTextFragments[4] = new String("");
        optifine.CustomItems.packedTextFragments[5] = new String("_");
        te = "texture.potion_bottle_splash";
        sT = "texture.potion_bottle_drinkable";
        mC = "items/potion_bottle_splash";
        h = "texture.potion_overlay";
        kG = "items/potion_bottle_drinkable";
        vI = "items/potion_overlay";
        optifine.CustomItems.itemProperties = (CustomItemProperties[][]) null;
        optifine.CustomItems.enchantmentProperties = (CustomItemProperties[][]) null;
        optifine.CustomItems.mapPotionIds = null;
        optifine.CustomItems.itemModelGenerator = new ItemModelGenerator();
        optifine.CustomItems.useGlint = true;
        AF = new int[0];
        optifine.CustomItems.EMPTY_INT2_ARRAY = new int[0][];
    }

    public static IBakedModel method_f331dfdf5561f570d3e1(
            ItemStack itemStack,
            IBakedModel iBakedModel,
            ModelResourceLocation modelResourceLocation) {
        if (iBakedModel.isGui3d()) {
            return iBakedModel;
        }

        if (optifine.CustomItems.itemProperties == null) {
            return iBakedModel;
        }

        CustomItemProperties customItemProperties = getCustomItemProperties(itemStack, 1);
        return customItemProperties == null
                ? iBakedModel
                : customItemProperties.method_e7b450baa135db45c9ac(modelResourceLocation);
    }

    public static boolean bindCustomArmorTexture(ItemStack itemStack, int i, String text) {
        if (optifine.CustomItems.itemProperties == null) {
            return false;
        }

        ResourceLocation resourceLocation = getCustomArmorLocation(itemStack, i, text);
        if (resourceLocation == null) {
            return false;
        }

        OptiFineConfig.getTextureManager().bindTexture(resourceLocation);
        return true;
    }

    private static void addToItemList(CustomItemProperties customItemProperties, List list) {
        int i = ReflectorMethod.f();
        if (customItemProperties.items != null) {
            int j = 0;

            while (j < customItemProperties.items.length) {
                {
                    int k = customItemProperties.items[j];
                    if (k <= 0) {
                        OptiFineConfig.warn("Invalid item ID: " + k);
                        if (!(i == 0)) {

                            addToList(customItemProperties, list, k);
                        }
                    } else {

                        addToList(customItemProperties, list, k);
                    }
                }

                j++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    private static Map getMapPotionIds() {
        if (optifine.CustomItems.mapPotionIds == null) {
            optifine.CustomItems.mapPotionIds = new LinkedHashMap();
            optifine.CustomItems.mapPotionIds.put("water", new int[] {0});
            optifine.CustomItems.mapPotionIds.put("awkward", new int[] {16});
            optifine.CustomItems.mapPotionIds.put("thick", new int[] {32});
            optifine.CustomItems.mapPotionIds.put("potent", new int[] {48});
            optifine.CustomItems.mapPotionIds.put("regeneration", getPotionIds(1));
            optifine.CustomItems.mapPotionIds.put("moveSpeed", getPotionIds(2));
            optifine.CustomItems.mapPotionIds.put("fireResistance", getPotionIds(3));
            optifine.CustomItems.mapPotionIds.put("poison", getPotionIds(4));
            optifine.CustomItems.mapPotionIds.put("heal", getPotionIds(5));
            optifine.CustomItems.mapPotionIds.put("nightVision", getPotionIds(6));
            optifine.CustomItems.mapPotionIds.put("clear", getPotionIds(7));
            optifine.CustomItems.mapPotionIds.put("bungling", getPotionIds(23));
            optifine.CustomItems.mapPotionIds.put("charming", getPotionIds(39));
            optifine.CustomItems.mapPotionIds.put("rank", getPotionIds(55));
            optifine.CustomItems.mapPotionIds.put("weakness", getPotionIds(8));
            optifine.CustomItems.mapPotionIds.put("damageBoost", getPotionIds(9));
            optifine.CustomItems.mapPotionIds.put("moveSlowdown", getPotionIds(10));
            optifine.CustomItems.mapPotionIds.put("diffuse", getPotionIds(11));
            optifine.CustomItems.mapPotionIds.put("smooth", getPotionIds(27));
            optifine.CustomItems.mapPotionIds.put("refined", getPotionIds(43));
            optifine.CustomItems.mapPotionIds.put("acrid", getPotionIds(59));
            optifine.CustomItems.mapPotionIds.put("harm", getPotionIds(12));
            optifine.CustomItems.mapPotionIds.put("waterBreathing", getPotionIds(13));
            optifine.CustomItems.mapPotionIds.put("invisibility", getPotionIds(14));
            optifine.CustomItems.mapPotionIds.put("thin", getPotionIds(15));
            optifine.CustomItems.mapPotionIds.put("debonair", getPotionIds(31));
            optifine.CustomItems.mapPotionIds.put("sparkling", getPotionIds(47));
            optifine.CustomItems.mapPotionIds.put("stinky", getPotionIds(63));
        }

        return optifine.CustomItems.mapPotionIds;
    }

    private static FileNotFoundException method_fd3f0eb1dd2c178d63ce(
            FileNotFoundException filenotfoundexception) {
        return filenotfoundexception;
    }

    public static void updateModels() {
        int i = ReflectorMethod.f();
        if (optifine.CustomItems.itemProperties != null) {
            int j = 0;

            while (j < optifine.CustomItems.itemProperties.length) {
                CustomItemProperties[] customItemPropertiesArray =
                        optifine.CustomItems.itemProperties[j];
                if (customItemPropertiesArray != null) {
                    int k = 0;

                    while (k < customItemPropertiesArray.length) {
                        CustomItemProperties customItemProperties = customItemPropertiesArray[k];
                        if (customItemProperties != null && customItemProperties.type == 1) {
                            TextureMap textureMapBlocks =
                                    MinecraftClient.getMinecraft().getTextureMapBlocks();
                            customItemProperties.updateModel(
                                    textureMapBlocks, optifine.CustomItems.itemModelGenerator);
                        }

                        k++;
                        if (i != 0) {
                            break;
                        }
                    }
                }

                j++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    private static Properties method_f84109a7e9586b3ffa44(
            String text, boolean flag, String textValue) {
        int i = ReflectorMethod.f();
        if (StrUtils.endsWith(text, new String[] {"_n", "_s"})) {
            return null;
        }

        if (text.equals("empty") && !flag) {
            int idFromItem = Item.getIdFromItem(Items.glass_bottle);
            Properties properties = new Properties();
            properties.put("type", "item");
            properties.put("items", packedTextFragments[0] + idFromItem);
            return properties;
        }

        int j = Item.getIdFromItem(Items.potionitem);
        int[] aint = (int[]) getMapPotionIds().get(text);
        if (aint == null) {
            OptiFineConfig.warn("Potion not found for image: " + textValue);
            return null;
        }

        StringBuffer stringbuffer = new StringBuffer();
        int k = 0;

        while (k < aint.length) {
            int l = aint[k];
            if (flag) {
                l |= 16384;
            }

            if (k > 0) {
                stringbuffer.append(packedTextFragments[1]);
            }

            stringbuffer.append(l);
            k++;
            if (i != 0) {
                break;
            }
        }

        short short1 = 16447;
        Properties properties1 = new Properties();
        properties1.put("type", "item");
        properties1.put("items", packedTextFragments[2] + j);
        properties1.put("damage", packedTextFragments[3] + stringbuffer.toString());
        properties1.put("damageMask", packedTextFragments[4] + short1);
        if (flag) {
            properties1.put("texture.potion_bottle_splash", text);
            if (i == 0) {
                return properties1;
            }
        }

        properties1.put("texture.potion_bottle_drinkable", text);
        return properties1;
    }

    private static CustomItemProperties[][] propertyListToArray(List list) {
        CustomItemProperties[][] customItemPropertiesArrayArray =
                new CustomItemProperties[list.size()][];
        int k = ReflectorMethod.cR();
        int j = 0;
        int i = k;

        while (j < list.size()) {
            List listValue = (List) list.get(j);
            if (listValue != null) {
                CustomItemProperties[] customItemPropertiesArray =
                        ((optifine.CustomItemProperties[])
                                listValue.toArray(new CustomItemProperties[listValue.size()]));
                Arrays.sort(customItemPropertiesArray, new CustomItemsComparator());
                customItemPropertiesArrayArray[j] = customItemPropertiesArray;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return customItemPropertiesArrayArray;
    }

    private static void method_5f839dd87721dd7dc5f4(
            CustomItemProperties customItemProperties, List list) {
        int i = ReflectorMethod.cR();
        if (customItemProperties.type == 2 && customItemProperties.enchantmentIds != null) {
            int j = 0;

            while (j < 256) {
                if (customItemProperties.enchantmentIds.isInRange(j)) {
                    addToList(customItemProperties, list, j);
                }

                j++;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    private static int[] getPotionIds(int i) {
        return new int[] {i, i + 16, i + 32, i + 48};
    }

    private static int[][] getEnchantmentIdLevels(ItemStack itemStack) {
        int k = ReflectorMethod.f();
        Item item = itemStack.getItem();
        int i = k;
        NBTTagList nbtTagList =
                item == Items.enchanted_book
                        ? Items.enchanted_book.getEnchantments(itemStack)
                        : itemStack.getEnchantmentTagList();
        if (nbtTagList != null && nbtTagList.tagCount() > 0) {
            int[][] aint = new int[nbtTagList.tagCount()][2];
            int j = 0;

            while (j < nbtTagList.tagCount()) {
                CompoundTag compoundTag = nbtTagList.getCompoundTagAt(j);
                short short1 = compoundTag.getShort("id");
                short short2 = compoundTag.getShort("lvl");
                aint[j][0] = short1;
                aint[j][1] = short2;
                j++;
                if (i != 0) {
                    break;
                }
            }

            return aint;
        } else {
            return optifine.CustomItems.EMPTY_INT2_ARRAY;
        }
    }

    private static Map makeAutoImageProperties(IResourcePack iResourcePack) {
        HashMap hashmap = new HashMap();
        hashmap.putAll(method_9b099f0135744742c93b(iResourcePack, false));
        hashmap.putAll(method_9b099f0135744742c93b(iResourcePack, true));
        return hashmap;
    }

    private static CustomItemProperties getCustomItemProperties(ItemStack itemStack, int i) {
        int j = ReflectorMethod.cR();
        if (optifine.CustomItems.itemProperties == null) {
            return null;
        }

        if (itemStack == null) {
            return null;
        }

        Item item = itemStack.getItem();
        int k = Item.getIdFromItem(item);
        if (k >= 0 && k < optifine.CustomItems.itemProperties.length) {
            CustomItemProperties[] customItemPropertiesArray =
                    optifine.CustomItems.itemProperties[k];
            if (customItemPropertiesArray != null) {
                int l = 0;

                while (l < customItemPropertiesArray.length) {
                    CustomItemProperties customItemProperties = customItemPropertiesArray[l];
                    if (customItemProperties.type == i
                            && matchesProperties(customItemProperties, itemStack, (int[][]) null)) {
                        return customItemProperties;
                    }

                    l++;
                    if (j == 0) {
                        break;
                    }
                }
            }
        }

        return null;
    }

    public static boolean renderCustomEffect(
            RenderItem renderItem, ItemStack itemStack, IBakedModel iBakedModel) {
        int i = ReflectorMethod.f();
        if (optifine.CustomItems.enchantmentProperties == null) {
            return false;
        }

        if (itemStack == null) {
            return false;
        }

        int[][] aint = getEnchantmentIdLevels(itemStack);
        if (aint.length <= 0) {
            return false;
        }

        HashSet hashset = null;
        byte b0 = 0;
        TextureManager textureManager = OptiFineConfig.getTextureManager();
        int j = 0;

        while (j < aint.length) {
            int k = aint[j][0];
            if (k >= 0 && k < optifine.CustomItems.enchantmentProperties.length) {
                CustomItemProperties[] customItemPropertiesArray =
                        optifine.CustomItems.enchantmentProperties[k];
                if (customItemPropertiesArray != null) {
                    int l = 0;

                    while (l < customItemPropertiesArray.length) {
                        CustomItemProperties customItemProperties = customItemPropertiesArray[l];
                        if (hashset == null) {
                            hashset = new HashSet();
                        }

                        if (hashset.add(k)
                                && matchesProperties(customItemProperties, itemStack, aint)
                                && customItemProperties.textureLocation != null) {
                            textureManager.bindTexture(customItemProperties.textureLocation);
                            float fx = customItemProperties.getTextureWidth(textureManager);
                            if (b0 == 0) {
                                b0 = 1;
                                GlStateManager.depthMask(false);
                                GlStateManager.depthFunc(514);
                                GlStateManager.disableLighting();
                                GlStateManager.matrixMode(5890);
                            }

                            Blender.setupBlend(customItemProperties.blend, 1.0F);
                            GlStateManager.pushMatrix();
                            GlStateManager.scale(fx / 2.0F, fx / 2.0F, fx / 2.0F);
                            float f2 = customItemProperties.speed;
                            long systemTime = MinecraftClient.getSystemTime();

                            float f1 = f2 * (float) (systemTime % 3000L) / 3000.0F / 8.0F;
                            GlStateManager.translate(f1, 0.0F, 0.0F);
                            GlStateManager.rotate(customItemProperties.rotation, 0.0F, 0.0F, 1.0F);
                            renderItem.renderModel(iBakedModel, -1);
                            GlStateManager.popMatrix();
                        }

                        l++;
                        if (i != 0) {
                            break;
                        }
                    }
                }
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        if (b0 != 0) {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            textureManager.bindTexture(TextureMap.locationBlocksTexture);
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    private static boolean matchesProperties(
            CustomItemProperties customItemProperties, ItemStack itemStack, int[][] aint) {
        int i2 = ReflectorMethod.f();
        Item item = itemStack.getItem();
        int i = i2;
        if (customItemProperties.damage != null) {
            int j = itemStack.getMetadata();
            if (customItemProperties.damageMask != 0) {
                j &= customItemProperties.damageMask;
            }

            if (customItemProperties.damagePercent) {
                int k = item.getMaxDamage();
                j = (int) ((double) (j * 100) / k);
            }

            if (!customItemProperties.damage.isInRange(j)) {
                return false;
            }
        }

        if (customItemProperties.stackSize != null
                && !customItemProperties.stackSize.isInRange(itemStack.stackSize)) {
            return false;
        }

        int[][] aint1 = aint;
        if (customItemProperties.enchantmentIds != null) {
            if (aint == null) {
                aint1 = getEnchantmentIdLevels(itemStack);
            }

            byte b0 = 0;
            int i1 = 0;

            while (i1 < aint1.length) {
                int l = aint1[i1][0];
                if (customItemProperties.enchantmentIds.isInRange(l)) {
                    b0 = 1;
                    if (i == 0) {
                        break;
                    }
                }

                i1++;
                if (i != 0) {
                    break;
                }
            }

            if (b0 == 0) {
                return false;
            }
        }

        if (customItemProperties.enchantmentLevels != null) {
            if (aint1 == null) {
                aint1 = getEnchantmentIdLevels(itemStack);
            }

            byte b1 = 0;
            int j1 = 0;

            while (j1 < aint1.length) {
                int l1 = aint1[j1][1];
                if (customItemProperties.enchantmentLevels.isInRange(l1)) {
                    b1 = 1;
                    if (i == 0) {
                        break;
                    }
                }

                j1++;
                if (i != 0) {
                    break;
                }
            }

            if (b1 == 0) {
                return false;
            }
        }

        if (customItemProperties.nbtTagValues != null) {
            CompoundTag tag = itemStack.getTag();
            int k1 = 0;

            while (k1 < customItemProperties.nbtTagValues.length) {
                NbtTagValue nbtTagValue = customItemProperties.nbtTagValues[k1];
                if (!nbtTagValue.matches(tag)) {
                    return false;
                }

                k1++;
                if (i != 0) {
                    break;
                }
            }
        }

        return true;
    }

    private static void readCitProperties(String text) {
        try {
            ResourceLocation resourceLocation = new ResourceLocation(text);
            InputStream inputstream = OptiFineConfig.getResourceStream(resourceLocation);
            if (inputstream == null) {
                return;
            }

            OptiFineConfig.dbg("CustomItems: Loading " + text);
            Properties properties = new Properties();
            properties.load(inputstream);
            inputstream.close();
            optifine.CustomItems.useGlint =
                    OptiFineConfig.parseBoolean(properties.getProperty("useGlint"), true);
        } catch (FileNotFoundException filenotfoundexception) {
            return;
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    private static ResourceLocation getCustomArmorLocation(
            ItemStack itemStack, int i, String text) {
        CustomItemProperties customItemProperties = getCustomItemProperties(itemStack, 3);
        if (customItemProperties == null) {
            return null;
        }

        if (customItemProperties.mapTextureLocations == null) {
            return null;
        }

        Item item = itemStack.getItem();
        if (!(item instanceof ItemArmor)) {
            return null;
        }

        ItemArmor itemArmor = (ItemArmor) item;
        String textValue = itemArmor.getArmorMaterial().getName();
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("texture.");
        stringbuffer.append(textValue);
        stringbuffer.append("_layer_");
        stringbuffer.append(i);
        if (text != null) {
            stringbuffer.append(packedTextFragments[5]);
            stringbuffer.append(text);
        }

        String textValue2 = stringbuffer.toString();
        return (ResourceLocation) customItemProperties.mapTextureLocations.get(textValue2);
    }

    // Helper used only by this class.
    public static class CustomItemsComparator implements java.util.Comparator {
        @java.lang.Override
        public int compare(java.lang.Object p171_0, java.lang.Object p171_1) {
            optifine.CustomItemProperties customItemProperties =
                    (optifine.CustomItemProperties) p171_0;
            optifine.CustomItemProperties customItemPropertiesValue =
                    (optifine.CustomItemProperties) p171_1;
            return customItemProperties.weight != customItemPropertiesValue.weight
                    ? customItemPropertiesValue.weight - customItemProperties.weight
                    : (!optifine.OptiFineConfig.equals(
                                    customItemProperties.basePath,
                                    customItemPropertiesValue.basePath)
                            ? customItemProperties.basePath.compareTo(
                                    customItemPropertiesValue.basePath)
                            : customItemProperties.name.compareTo(customItemPropertiesValue.name));
        }
    }

    // Helper used only by this class.
    static final class GetPropertiesComparator implements java.util.Comparator {
        @java.lang.Override
        public int compare(java.lang.Object p171_0, java.lang.Object p171_1) {
            optifine.CustomItemProperties customItemProperties =
                    (optifine.CustomItemProperties) p171_0;
            optifine.CustomItemProperties customItemPropertiesValue =
                    (optifine.CustomItemProperties) p171_1;
            return customItemProperties.layer != customItemPropertiesValue.layer
                    ? customItemProperties.layer - customItemPropertiesValue.layer
                    : (customItemProperties.weight != customItemPropertiesValue.weight
                            ? customItemPropertiesValue.weight - customItemProperties.weight
                            : (!customItemProperties.basePath.equals(
                                            customItemPropertiesValue.basePath)
                                    ? customItemProperties.basePath.compareTo(
                                            customItemPropertiesValue.basePath)
                                    : customItemProperties.name.compareTo(
                                            customItemPropertiesValue.name)));
        }
    }
}
