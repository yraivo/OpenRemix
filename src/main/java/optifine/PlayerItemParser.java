package optifine;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class PlayerItemParser {
    public static String Cv;
    public static String Cs;
    public static String vI;
    public static String EH;
    public static String ks;
    public static String Ba;
    public static String b;
    public static String te;
    public static String d;
    public static String h;
    public static String oF;
    public static String g;
    public static String Cx;
    public static String mC;
    public static String kE;
    public static String f;
    public static String s;
    private static JsonParser jsonParser;
    public static String e;

    public static String kG;

    public static String sT;
    public static String c;
    public static String kK;
    private static String[] packedTextFragments;

    public static PlayerItemModel parseItemModel(JsonObject jsonobject) {
        int k = ReflectorMethod.f();
        String sx = Json.getString(jsonobject, "type");
        int i = k;
        if (!OptiFineConfig.equals(sx, "PlayerItem")) {
            throw new JsonParseException("Unknown model type: " + sx);
        }

        int[] aint = Json.parseIntArray(jsonobject.get("textureSize"), 2);
        checkNull(aint, "Missing texture size");
        Dimension dimension = new Dimension(aint[0], aint[1]);
        boolean flag = Json.getBoolean(jsonobject, "usePlayerTexture", false);
        JsonArray jsonarray = (JsonArray) jsonobject.get("models");
        checkNull(jsonarray, "Missing elements");
        HashMap hashmap = new HashMap();
        ArrayList arrayList = new ArrayList();
        new ArrayList();
        int j = 0;

        while (j < jsonarray.size()) {
            label67:
            {
                JsonObject jsonobject1 = (JsonObject) jsonarray.get(j);
                String text = Json.getString(jsonobject1, "baseId");
                if (text != null) {
                    JsonObject jsonobject2 = (JsonObject) hashmap.get(text);
                    if (jsonobject2 == null) {
                        OptiFineConfig.warn("BaseID not found: " + text);
                        if (i == 0) {
                            break label67;
                        }
                    }

                    for (java.util.Iterator<?> __recoveryIterator171_3737 =
                                    (jsonobject2.entrySet()).iterator();
                            (__recoveryIterator171_3737).hasNext(); ) {
                        Entry entry = (Entry) (__recoveryIterator171_3737).next();
                        if (!jsonobject1.has((String) entry.getKey())) {
                            jsonobject1.add(
                                    (String) entry.getKey(), (JsonElement) entry.getValue());
                        }

                        if (i != 0) {
                            break;
                        }
                    }
                }

                String textValue = Json.getString(jsonobject1, "id");
                if (textValue != null) {
                    if (!hashmap.containsKey(textValue)) {
                        hashmap.put(textValue, jsonobject1);
                        if (!(i == 0)) {

                            OptiFineConfig.warn("Duplicate model ID: " + textValue);
                        }
                    } else {

                        OptiFineConfig.warn("Duplicate model ID: " + textValue);
                    }
                }

                PlayerItemRenderer playerItemRenderer = parseItemRenderer(jsonobject1, dimension);
                if (playerItemRenderer != null) {
                    arrayList.add(playerItemRenderer);
                }
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        PlayerItemRenderer[] playerItemRendererArray =
                ((optifine.PlayerItemRenderer[])
                        arrayList.toArray(new PlayerItemRenderer[arrayList.size()]));
        return new PlayerItemModel(dimension, flag, playerItemRendererArray);
    }

    private static void checkNull(Object object, String text) {
        if (object == null) {
            throw new JsonParseException(text);
        }
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    private static PlayerItemRenderer parseItemRenderer(
            JsonObject jsonobject, Dimension dimension) {
        String sx = Json.getString(jsonobject, "type");
        if (!OptiFineConfig.equals(sx, "ModelBox")) {
            OptiFineConfig.warn("Unknown model type: " + sx);
            return null;
        } else {
            String text = Json.getString(jsonobject, "attachTo");
            int i = parseAttachModel(text);
            float fx = Json.getFloat(jsonobject, "scale", 1.0F);
            ModelPlayerItem modelPlayerItem = new ModelPlayerItem();
            modelPlayerItem.textureWidth = dimension.width;
            modelPlayerItem.textureHeight = dimension.height;
            ModelRenderer modelRenderer = parseModelRenderer(jsonobject, modelPlayerItem);
            return new PlayerItemRenderer(i, fx, modelRenderer);
        }
    }

    private static ResourceLocation F(String text) {
        int i = text.indexOf(58);
        if (i < 0) {
            return new ResourceLocation(text);
        }

        String textValue = text.substring(0, i);
        String textValue2 = text.substring(i + 1);
        return new ResourceLocation(textValue, textValue2);
    }

    static {
        optifine.PlayerItemParser.packedTextFragments = new String[8];
        optifine.PlayerItemParser.packedTextFragments[0] = new String("");
        optifine.PlayerItemParser.packedTextFragments[1] = new String("x");
        optifine.PlayerItemParser.packedTextFragments[2] = new String("y");
        optifine.PlayerItemParser.packedTextFragments[3] = new String("z");
        optifine.PlayerItemParser.packedTextFragments[4] = new String("");
        optifine.PlayerItemParser.packedTextFragments[5] = new String("u");
        optifine.PlayerItemParser.packedTextFragments[6] = new String("v");
        mC = "translate";
        e = "models";
        Ba = "submodel";
        s = "textureOffset";
        h = "type";
        sT = "invertAxis";
        c = "textureSize";
        ks = "boxes";
        b = "type";
        kE = "scale";
        oF = "submodels";
        g = "baseId";
        kK = "sprites";
        EH = "ModelBox";
        d = "usePlayerTexture";
        vI = "mirrorTexture";
        Cv = "coordinates";
        te = "attachTo";
        kG = "rotate";
        Cs = "sizeAdd";
        Cx = "PlayerItem";
        f = "id";
        optifine.PlayerItemParser.jsonParser = new JsonParser();
    }

    private static int parseAttachModel(String text) {
        if (text == null) {
            return 0;
        }

        if (text.equals("body")) {
            return 0;
        }

        if (text.equals("head")) {
            return 1;
        }

        if (text.equals("leftArm")) {
            return 2;
        }

        if (text.equals("rightArm")) {
            return 3;
        }

        if (text.equals("leftLeg")) {
            return 4;
        }

        if (text.equals("rightLeg")) {
            return 5;
        }

        if (text.equals("cape")) {
            return 6;
        }

        OptiFineConfig.warn("Unknown attachModel: " + text);
        return 0;
    }

    private static ModelRenderer parseModelRenderer(JsonObject jsonobject, ModelBase modelBase) {
        int j1 = ReflectorMethod.cR();
        ModelRenderer modelRenderer = new ModelRenderer(modelBase);
        String sx = Json.getString(jsonobject, "invertAxis", packedTextFragments[0]).toLowerCase();
        int i = j1;
        boolean flag = sx.contains(packedTextFragments[1]);
        boolean flag1 = sx.contains(packedTextFragments[2]);
        boolean flag2 = sx.contains(packedTextFragments[3]);
        float[] afloat =
                Json.parseFloatArray(jsonobject.get("translate"), 3, new float[3]);
        if (flag) {
            afloat[0] = -afloat[0];
        }

        if (flag1) {
            afloat[1] = -afloat[1];
        }

        if (flag2) {
            afloat[2] = -afloat[2];
        }

        float[] afloat1 =
                Json.parseFloatArray(jsonobject.get("rotate"), 3, new float[3]);
        int j = 0;

        while (j < afloat1.length) {
            afloat1[j] = afloat1[j] / 180.0F * (float) Math.PI;
            j++;
            if (i == 0) {
                break;
            }
        }

        if (flag) {
            afloat1[0] = -afloat1[0];
        }

        if (flag1) {
            afloat1[1] = -afloat1[1];
        }

        if (flag2) {
            afloat1[2] = -afloat1[2];
        }

        modelRenderer.setRotationPoint(afloat[0], afloat[1], afloat[2]);
        modelRenderer.rotateAngleX = afloat1[0];
        modelRenderer.rotateAngleY = afloat1[1];
        modelRenderer.rotateAngleZ = afloat1[2];
        String text =
                Json.getString(jsonobject, "mirrorTexture", packedTextFragments[4]).toLowerCase();
        boolean flag3 = text.contains(packedTextFragments[5]);
        boolean flag4 = text.contains(packedTextFragments[6]);
        if (flag3) {
            modelRenderer.mirror = true;
        }

        if (flag4) {
            modelRenderer.mirrorV = true;
        }

        JsonArray jsonarray = jsonobject.getAsJsonArray("boxes");
        if (jsonarray != null) {
            int k = 0;

            while (k < jsonarray.size()) {
                JsonObject jsonobject1 = jsonarray.get(k).getAsJsonObject();
                int[] aint = Json.parseIntArray(jsonobject1.get("textureOffset"), 2);
                if (aint == null) {
                    throw new JsonParseException("Texture offset not specified");
                }

                float[] afloat2 =
                        Json.parseFloatArray(jsonobject1.get("coordinates"), 6);
                if (afloat2 == null) {
                    throw new JsonParseException("Coordinates not specified");
                }

                if (flag) {
                    afloat2[0] = -afloat2[0] - afloat2[3];
                }

                if (flag1) {
                    afloat2[1] = -afloat2[1] - afloat2[4];
                }

                if (flag2) {
                    afloat2[2] = -afloat2[2] - afloat2[5];
                }

                float fx = Json.getFloat(jsonobject1, "sizeAdd", 0.0F);
                modelRenderer.setTextureOffset(aint[0], aint[1]);
                modelRenderer.addBox(
                        afloat2[0],
                        afloat2[1],
                        afloat2[2],
                        (int) afloat2[3],
                        (int) afloat2[4],
                        (int) afloat2[5],
                        fx);
                k++;
                if (i == 0) {
                    break;
                }
            }
        }

        JsonArray jsonarray1 = jsonobject.getAsJsonArray("sprites");
        if (jsonarray1 != null) {
            int l = 0;

            while (l < jsonarray1.size()) {
                JsonObject jsonobject3 = jsonarray1.get(l).getAsJsonObject();
                int[] aint1 = Json.parseIntArray(jsonobject3.get("textureOffset"), 2);
                if (aint1 == null) {
                    throw new JsonParseException("Texture offset not specified");
                }

                float[] afloat3 =
                        Json.parseFloatArray(jsonobject3.get("coordinates"), 6);
                if (afloat3 == null) {
                    throw new JsonParseException("Coordinates not specified");
                }

                if (flag) {
                    afloat3[0] = -afloat3[0] - afloat3[3];
                }

                if (flag1) {
                    afloat3[1] = -afloat3[1] - afloat3[4];
                }

                if (flag2) {
                    afloat3[2] = -afloat3[2] - afloat3[5];
                }

                float floatValue = Json.getFloat(jsonobject3, "sizeAdd", 0.0F);
                modelRenderer.setTextureOffset(aint1[0], aint1[1]);
                modelRenderer.addSprite(
                        afloat3[0],
                        afloat3[1],
                        afloat3[2],
                        (int) afloat3[3],
                        (int) afloat3[4],
                        (int) afloat3[5],
                        floatValue);
                l++;
                if (i == 0) {
                    break;
                }
            }
        }

        JsonObject jsonobject2 = (JsonObject) jsonobject.get("submodel");
        if (jsonobject2 != null) {
            ModelRenderer modelRendererValue = parseModelRenderer(jsonobject2, modelBase);
            modelRenderer.addChild(modelRendererValue);
        }

        JsonArray jsonarray2 = (JsonArray) jsonobject.get("submodels");
        if (jsonarray2 != null) {
            int i1 = 0;

            while (i1 < jsonarray2.size()) {
                JsonObject jsonobject4 = (JsonObject) jsonarray2.get(i1);
                ModelRenderer modelRendererValue2 = parseModelRenderer(jsonobject4, modelBase);
                modelRenderer.addChild(modelRendererValue2);
                i1++;
                if (i == 0) {
                    break;
                }
            }
        }

        return modelRenderer;
    }
}
