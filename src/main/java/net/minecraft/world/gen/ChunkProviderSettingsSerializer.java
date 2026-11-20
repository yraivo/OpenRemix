package net.minecraft.world.gen;

import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.world.biome.BiomeGenBase;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ChunkProviderSettingsSerializer implements JsonDeserializer, JsonSerializer {

    public JsonElement serialize(
            Object object, Type type, JsonSerializationContext jsonserializationcontext) {
        return this.method_a29d83b3edcdf30772ee(
                (ChunkProviderSettingsFactory) object, type, jsonserializationcontext);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public JsonElement method_a29d83b3edcdf30772ee(
            ChunkProviderSettingsFactory chunkProviderSettingsFactory,
            Type type,
            JsonSerializationContext jsonserializationcontext) {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("coordinateScale", chunkProviderSettingsFactory.coordinateScale);
        jsonobject.addProperty("heightScale", chunkProviderSettingsFactory.heightScale);
        jsonobject.addProperty("lowerLimitScale", chunkProviderSettingsFactory.lowerLimitScale);
        jsonobject.addProperty("upperLimitScale", chunkProviderSettingsFactory.upperLimitScale);
        jsonobject.addProperty("depthNoiseScaleX", chunkProviderSettingsFactory.depthNoiseScaleX);
        jsonobject.addProperty("depthNoiseScaleZ", chunkProviderSettingsFactory.depthNoiseScaleZ);
        jsonobject.addProperty(
                "depthNoiseScaleExponent", chunkProviderSettingsFactory.depthNoiseScaleExponent);
        jsonobject.addProperty("mainNoiseScaleX", chunkProviderSettingsFactory.mainNoiseScaleX);
        jsonobject.addProperty("mainNoiseScaleY", chunkProviderSettingsFactory.mainNoiseScaleY);
        jsonobject.addProperty("mainNoiseScaleZ", chunkProviderSettingsFactory.mainNoiseScaleZ);
        jsonobject.addProperty("baseSize", chunkProviderSettingsFactory.baseSize);
        jsonobject.addProperty("stretchY", chunkProviderSettingsFactory.stretchY);
        jsonobject.addProperty("biomeDepthWeight", chunkProviderSettingsFactory.biomeDepthWeight);
        jsonobject.addProperty("biomeDepthOffset", chunkProviderSettingsFactory.biomeDepthOffset);
        jsonobject.addProperty("biomeScaleWeight", chunkProviderSettingsFactory.biomeScaleWeight);
        jsonobject.addProperty("biomeScaleOffset", chunkProviderSettingsFactory.biomeScaleOffset);
        jsonobject.addProperty("seaLevel", chunkProviderSettingsFactory.seaLevel);
        jsonobject.addProperty("useCaves", chunkProviderSettingsFactory.useCaves);
        jsonobject.addProperty("useDungeons", chunkProviderSettingsFactory.useDungeons);
        jsonobject.addProperty("dungeonChance", chunkProviderSettingsFactory.dungeonChance);
        jsonobject.addProperty("useStrongholds", chunkProviderSettingsFactory.useStrongholds);
        jsonobject.addProperty("useVillages", chunkProviderSettingsFactory.useVillages);
        jsonobject.addProperty("useMineShafts", chunkProviderSettingsFactory.useMineShafts);
        jsonobject.addProperty("useTemples", chunkProviderSettingsFactory.useTemples);
        jsonobject.addProperty("useMonuments", chunkProviderSettingsFactory.useMonuments);
        jsonobject.addProperty("useRavines", chunkProviderSettingsFactory.useRavines);
        jsonobject.addProperty("useWaterLakes", chunkProviderSettingsFactory.useWaterLakes);
        jsonobject.addProperty("waterLakeChance", chunkProviderSettingsFactory.waterLakeChance);
        jsonobject.addProperty("useLavaLakes", chunkProviderSettingsFactory.useLavaLakes);
        jsonobject.addProperty("lavaLakeChance", chunkProviderSettingsFactory.lavaLakeChance);
        jsonobject.addProperty("useLavaOceans", chunkProviderSettingsFactory.useLavaOceans);
        jsonobject.addProperty("fixedBiome", chunkProviderSettingsFactory.fixedBiome);
        jsonobject.addProperty("biomeSize", chunkProviderSettingsFactory.biomeSize);
        jsonobject.addProperty("riverSize", chunkProviderSettingsFactory.riverSize);
        jsonobject.addProperty("dirtSize", chunkProviderSettingsFactory.dirtSize);
        jsonobject.addProperty("dirtCount", chunkProviderSettingsFactory.dirtCount);
        jsonobject.addProperty("dirtMinHeight", chunkProviderSettingsFactory.dirtMinHeight);
        jsonobject.addProperty("dirtMaxHeight", chunkProviderSettingsFactory.dirtMaxHeight);
        jsonobject.addProperty("gravelSize", chunkProviderSettingsFactory.gravelSize);
        jsonobject.addProperty("gravelCount", chunkProviderSettingsFactory.gravelCount);
        jsonobject.addProperty("gravelMinHeight", chunkProviderSettingsFactory.gravelMinHeight);
        jsonobject.addProperty("gravelMaxHeight", chunkProviderSettingsFactory.gravelMaxHeight);
        jsonobject.addProperty("graniteSize", chunkProviderSettingsFactory.graniteSize);
        jsonobject.addProperty("graniteCount", chunkProviderSettingsFactory.graniteCount);
        jsonobject.addProperty("graniteMinHeight", chunkProviderSettingsFactory.graniteMinHeight);
        jsonobject.addProperty("graniteMaxHeight", chunkProviderSettingsFactory.graniteMaxHeight);
        jsonobject.addProperty("dioriteSize", chunkProviderSettingsFactory.dioriteSize);
        jsonobject.addProperty("dioriteCount", chunkProviderSettingsFactory.dioriteCount);
        jsonobject.addProperty("dioriteMinHeight", chunkProviderSettingsFactory.dioriteMinHeight);
        jsonobject.addProperty("dioriteMaxHeight", chunkProviderSettingsFactory.dioriteMaxHeight);
        jsonobject.addProperty("andesiteSize", chunkProviderSettingsFactory.andesiteSize);
        jsonobject.addProperty("andesiteCount", chunkProviderSettingsFactory.andesiteCount);
        jsonobject.addProperty("andesiteMinHeight", chunkProviderSettingsFactory.andesiteMinHeight);
        jsonobject.addProperty("andesiteMaxHeight", chunkProviderSettingsFactory.andesiteMaxHeight);
        jsonobject.addProperty("coalSize", chunkProviderSettingsFactory.coalSize);
        jsonobject.addProperty("coalCount", chunkProviderSettingsFactory.coalCount);
        jsonobject.addProperty("coalMinHeight", chunkProviderSettingsFactory.coalMinHeight);
        jsonobject.addProperty("coalMaxHeight", chunkProviderSettingsFactory.coalMaxHeight);
        jsonobject.addProperty("ironSize", chunkProviderSettingsFactory.ironSize);
        jsonobject.addProperty("ironCount", chunkProviderSettingsFactory.ironCount);
        jsonobject.addProperty("ironMinHeight", chunkProviderSettingsFactory.ironMinHeight);
        jsonobject.addProperty("ironMaxHeight", chunkProviderSettingsFactory.ironMaxHeight);
        jsonobject.addProperty("goldSize", chunkProviderSettingsFactory.goldSize);
        jsonobject.addProperty("goldCount", chunkProviderSettingsFactory.goldCount);
        jsonobject.addProperty("goldMinHeight", chunkProviderSettingsFactory.goldMinHeight);
        jsonobject.addProperty("goldMaxHeight", chunkProviderSettingsFactory.goldMaxHeight);
        jsonobject.addProperty("redstoneSize", chunkProviderSettingsFactory.redstoneSize);
        MapGenBase.b();
        jsonobject.addProperty("redstoneCount", chunkProviderSettingsFactory.redstoneCount);
        jsonobject.addProperty("redstoneMinHeight", chunkProviderSettingsFactory.redstoneMinHeight);
        jsonobject.addProperty("redstoneMaxHeight", chunkProviderSettingsFactory.redstoneMaxHeight);
        jsonobject.addProperty("diamondSize", chunkProviderSettingsFactory.diamondSize);
        jsonobject.addProperty("diamondCount", chunkProviderSettingsFactory.diamondCount);
        jsonobject.addProperty("diamondMinHeight", chunkProviderSettingsFactory.diamondMinHeight);
        jsonobject.addProperty("diamondMaxHeight", chunkProviderSettingsFactory.diamondMaxHeight);
        jsonobject.addProperty("lapisSize", chunkProviderSettingsFactory.lapisSize);
        jsonobject.addProperty("lapisCount", chunkProviderSettingsFactory.lapisCount);
        jsonobject.addProperty("lapisCenterHeight", chunkProviderSettingsFactory.lapisCenterHeight);
        jsonobject.addProperty("lapisSpread", chunkProviderSettingsFactory.lapisSpread);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            MapGenBase.b(new String[4]);
        }

        return jsonobject;
    }

    public ChunkProviderSettingsFactory method_4a5618381532262c6406(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        MapGenBase.b();

        JsonObject jsonobject = jsonelement.getAsJsonObject();

        ChunkProviderSettingsFactory chunkProviderSettingsFactory =
                new ChunkProviderSettingsFactory();

        try {
            {
                chunkProviderSettingsFactory.coordinateScale =
                        JsonUtils.getFloat(
                                jsonobject,
                                "coordinateScale",
                                chunkProviderSettingsFactory.coordinateScale);
                chunkProviderSettingsFactory.heightScale =
                        JsonUtils.getFloat(
                                jsonobject,
                                "heightScale",
                                chunkProviderSettingsFactory.heightScale);
                chunkProviderSettingsFactory.lowerLimitScale =
                        JsonUtils.getFloat(
                                jsonobject,
                                "lowerLimitScale",
                                chunkProviderSettingsFactory.lowerLimitScale);
                chunkProviderSettingsFactory.upperLimitScale =
                        JsonUtils.getFloat(
                                jsonobject,
                                "upperLimitScale",
                                chunkProviderSettingsFactory.upperLimitScale);
                chunkProviderSettingsFactory.depthNoiseScaleX =
                        JsonUtils.getFloat(
                                jsonobject,
                                "depthNoiseScaleX",
                                chunkProviderSettingsFactory.depthNoiseScaleX);
                chunkProviderSettingsFactory.depthNoiseScaleZ =
                        JsonUtils.getFloat(
                                jsonobject,
                                "depthNoiseScaleZ",
                                chunkProviderSettingsFactory.depthNoiseScaleZ);
                chunkProviderSettingsFactory.depthNoiseScaleExponent =
                        JsonUtils.getFloat(
                                jsonobject,
                                "depthNoiseScaleExponent",
                                chunkProviderSettingsFactory.depthNoiseScaleExponent);
                chunkProviderSettingsFactory.mainNoiseScaleX =
                        JsonUtils.getFloat(
                                jsonobject,
                                "mainNoiseScaleX",
                                chunkProviderSettingsFactory.mainNoiseScaleX);
                chunkProviderSettingsFactory.mainNoiseScaleY =
                        JsonUtils.getFloat(
                                jsonobject,
                                "mainNoiseScaleY",
                                chunkProviderSettingsFactory.mainNoiseScaleY);
                chunkProviderSettingsFactory.mainNoiseScaleZ =
                        JsonUtils.getFloat(
                                jsonobject,
                                "mainNoiseScaleZ",
                                chunkProviderSettingsFactory.mainNoiseScaleZ);
                chunkProviderSettingsFactory.baseSize =
                        JsonUtils.getFloat(
                                jsonobject, "baseSize", chunkProviderSettingsFactory.baseSize);
                chunkProviderSettingsFactory.stretchY =
                        JsonUtils.getFloat(
                                jsonobject, "stretchY", chunkProviderSettingsFactory.stretchY);
                chunkProviderSettingsFactory.biomeDepthWeight =
                        JsonUtils.getFloat(
                                jsonobject,
                                "biomeDepthWeight",
                                chunkProviderSettingsFactory.biomeDepthWeight);
                chunkProviderSettingsFactory.biomeDepthOffset =
                        JsonUtils.getFloat(
                                jsonobject,
                                "biomeDepthOffset",
                                chunkProviderSettingsFactory.biomeDepthOffset);
                chunkProviderSettingsFactory.biomeScaleWeight =
                        JsonUtils.getFloat(
                                jsonobject,
                                "biomeScaleWeight",
                                chunkProviderSettingsFactory.biomeScaleWeight);
                chunkProviderSettingsFactory.biomeScaleOffset =
                        JsonUtils.getFloat(
                                jsonobject,
                                "biomeScaleOffset",
                                chunkProviderSettingsFactory.biomeScaleOffset);
                chunkProviderSettingsFactory.seaLevel =
                        JsonUtils.getInt(
                                jsonobject, "seaLevel", chunkProviderSettingsFactory.seaLevel);
                chunkProviderSettingsFactory.useCaves =
                        JsonUtils.getBoolean(
                                jsonobject, "useCaves", chunkProviderSettingsFactory.useCaves);
                chunkProviderSettingsFactory.useDungeons =
                        JsonUtils.getBoolean(
                                jsonobject,
                                "useDungeons",
                                chunkProviderSettingsFactory.useDungeons);
                chunkProviderSettingsFactory.dungeonChance =
                        JsonUtils.getInt(
                                jsonobject,
                                "dungeonChance",
                                chunkProviderSettingsFactory.dungeonChance);
                chunkProviderSettingsFactory.useStrongholds =
                        JsonUtils.getBoolean(
                                jsonobject,
                                "useStrongholds",
                                chunkProviderSettingsFactory.useStrongholds);
                chunkProviderSettingsFactory.useVillages =
                        JsonUtils.getBoolean(
                                jsonobject,
                                "useVillages",
                                chunkProviderSettingsFactory.useVillages);
                chunkProviderSettingsFactory.useMineShafts =
                        JsonUtils.getBoolean(
                                jsonobject,
                                "useMineShafts",
                                chunkProviderSettingsFactory.useMineShafts);
                chunkProviderSettingsFactory.useTemples =
                        JsonUtils.getBoolean(
                                jsonobject, "useTemples", chunkProviderSettingsFactory.useTemples);
                chunkProviderSettingsFactory.useMonuments =
                        JsonUtils.getBoolean(
                                jsonobject,
                                "useMonuments",
                                chunkProviderSettingsFactory.useMonuments);
                chunkProviderSettingsFactory.useRavines =
                        JsonUtils.getBoolean(
                                jsonobject, "useRavines", chunkProviderSettingsFactory.useRavines);
                chunkProviderSettingsFactory.useWaterLakes =
                        JsonUtils.getBoolean(
                                jsonobject,
                                "useWaterLakes",
                                chunkProviderSettingsFactory.useWaterLakes);
                chunkProviderSettingsFactory.waterLakeChance =
                        JsonUtils.getInt(
                                jsonobject,
                                "waterLakeChance",
                                chunkProviderSettingsFactory.waterLakeChance);
                chunkProviderSettingsFactory.useLavaLakes =
                        JsonUtils.getBoolean(
                                jsonobject,
                                "useLavaLakes",
                                chunkProviderSettingsFactory.useLavaLakes);
                chunkProviderSettingsFactory.lavaLakeChance =
                        JsonUtils.getInt(
                                jsonobject,
                                "lavaLakeChance",
                                chunkProviderSettingsFactory.lavaLakeChance);
                chunkProviderSettingsFactory.useLavaOceans =
                        JsonUtils.getBoolean(
                                jsonobject,
                                "useLavaOceans",
                                chunkProviderSettingsFactory.useLavaOceans);
                chunkProviderSettingsFactory.fixedBiome =
                        JsonUtils.getInt(
                                jsonobject, "fixedBiome", chunkProviderSettingsFactory.fixedBiome);
                if (chunkProviderSettingsFactory.fixedBiome < 38
                        && chunkProviderSettingsFactory.fixedBiome >= -1) {
                    if (!(chunkProviderSettingsFactory.fixedBiome < BiomeGenBase.hell.biomeID)) {

                        chunkProviderSettingsFactory.fixedBiome += 2;


                    }
                } else {

                    chunkProviderSettingsFactory.fixedBiome = -1;
                }
            }

            chunkProviderSettingsFactory.biomeSize =
                    JsonUtils.getInt(
                            jsonobject, "biomeSize", chunkProviderSettingsFactory.biomeSize);
            chunkProviderSettingsFactory.riverSize =
                    JsonUtils.getInt(
                            jsonobject, "riverSize", chunkProviderSettingsFactory.riverSize);
            chunkProviderSettingsFactory.dirtSize =
                    JsonUtils.getInt(
                            jsonobject, "dirtSize", chunkProviderSettingsFactory.dirtSize);
            chunkProviderSettingsFactory.dirtCount =
                    JsonUtils.getInt(
                            jsonobject, "dirtCount", chunkProviderSettingsFactory.dirtCount);
            chunkProviderSettingsFactory.dirtMinHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "dirtMinHeight",
                            chunkProviderSettingsFactory.dirtMinHeight);
            chunkProviderSettingsFactory.dirtMaxHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "dirtMaxHeight",
                            chunkProviderSettingsFactory.dirtMaxHeight);
            chunkProviderSettingsFactory.gravelSize =
                    JsonUtils.getInt(
                            jsonobject, "gravelSize", chunkProviderSettingsFactory.gravelSize);
            chunkProviderSettingsFactory.gravelCount =
                    JsonUtils.getInt(
                            jsonobject, "gravelCount", chunkProviderSettingsFactory.gravelCount);
            chunkProviderSettingsFactory.gravelMinHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "gravelMinHeight",
                            chunkProviderSettingsFactory.gravelMinHeight);
            chunkProviderSettingsFactory.gravelMaxHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "gravelMaxHeight",
                            chunkProviderSettingsFactory.gravelMaxHeight);
            chunkProviderSettingsFactory.graniteSize =
                    JsonUtils.getInt(
                            jsonobject, "graniteSize", chunkProviderSettingsFactory.graniteSize);
            chunkProviderSettingsFactory.graniteCount =
                    JsonUtils.getInt(
                            jsonobject, "graniteCount", chunkProviderSettingsFactory.graniteCount);
            chunkProviderSettingsFactory.graniteMinHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "graniteMinHeight",
                            chunkProviderSettingsFactory.graniteMinHeight);
            chunkProviderSettingsFactory.graniteMaxHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "graniteMaxHeight",
                            chunkProviderSettingsFactory.graniteMaxHeight);
            chunkProviderSettingsFactory.dioriteSize =
                    JsonUtils.getInt(
                            jsonobject, "dioriteSize", chunkProviderSettingsFactory.dioriteSize);
            chunkProviderSettingsFactory.dioriteCount =
                    JsonUtils.getInt(
                            jsonobject, "dioriteCount", chunkProviderSettingsFactory.dioriteCount);
            chunkProviderSettingsFactory.dioriteMinHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "dioriteMinHeight",
                            chunkProviderSettingsFactory.dioriteMinHeight);
            chunkProviderSettingsFactory.dioriteMaxHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "dioriteMaxHeight",
                            chunkProviderSettingsFactory.dioriteMaxHeight);
            chunkProviderSettingsFactory.andesiteSize =
                    JsonUtils.getInt(
                            jsonobject, "andesiteSize", chunkProviderSettingsFactory.andesiteSize);
            chunkProviderSettingsFactory.andesiteCount =
                    JsonUtils.getInt(
                            jsonobject,
                            "andesiteCount",
                            chunkProviderSettingsFactory.andesiteCount);
            chunkProviderSettingsFactory.andesiteMinHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "andesiteMinHeight",
                            chunkProviderSettingsFactory.andesiteMinHeight);
            chunkProviderSettingsFactory.andesiteMaxHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "andesiteMaxHeight",
                            chunkProviderSettingsFactory.andesiteMaxHeight);
            chunkProviderSettingsFactory.coalSize =
                    JsonUtils.getInt(
                            jsonobject, "coalSize", chunkProviderSettingsFactory.coalSize);
            chunkProviderSettingsFactory.coalCount =
                    JsonUtils.getInt(
                            jsonobject, "coalCount", chunkProviderSettingsFactory.coalCount);
            chunkProviderSettingsFactory.coalMinHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "coalMinHeight",
                            chunkProviderSettingsFactory.coalMinHeight);
            chunkProviderSettingsFactory.coalMaxHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "coalMaxHeight",
                            chunkProviderSettingsFactory.coalMaxHeight);
            chunkProviderSettingsFactory.ironSize =
                    JsonUtils.getInt(
                            jsonobject, "ironSize", chunkProviderSettingsFactory.ironSize);
            chunkProviderSettingsFactory.ironCount =
                    JsonUtils.getInt(
                            jsonobject, "ironCount", chunkProviderSettingsFactory.ironCount);
            chunkProviderSettingsFactory.ironMinHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "ironMinHeight",
                            chunkProviderSettingsFactory.ironMinHeight);
            chunkProviderSettingsFactory.ironMaxHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "ironMaxHeight",
                            chunkProviderSettingsFactory.ironMaxHeight);
            chunkProviderSettingsFactory.goldSize =
                    JsonUtils.getInt(
                            jsonobject, "goldSize", chunkProviderSettingsFactory.goldSize);
            chunkProviderSettingsFactory.goldCount =
                    JsonUtils.getInt(
                            jsonobject, "goldCount", chunkProviderSettingsFactory.goldCount);
            chunkProviderSettingsFactory.goldMinHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "goldMinHeight",
                            chunkProviderSettingsFactory.goldMinHeight);
            chunkProviderSettingsFactory.goldMaxHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "goldMaxHeight",
                            chunkProviderSettingsFactory.goldMaxHeight);
            chunkProviderSettingsFactory.redstoneSize =
                    JsonUtils.getInt(
                            jsonobject, "redstoneSize", chunkProviderSettingsFactory.redstoneSize);
            chunkProviderSettingsFactory.redstoneCount =
                    JsonUtils.getInt(
                            jsonobject,
                            "redstoneCount",
                            chunkProviderSettingsFactory.redstoneCount);
            chunkProviderSettingsFactory.redstoneMinHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "redstoneMinHeight",
                            chunkProviderSettingsFactory.redstoneMinHeight);
            chunkProviderSettingsFactory.redstoneMaxHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "redstoneMaxHeight",
                            chunkProviderSettingsFactory.redstoneMaxHeight);
            chunkProviderSettingsFactory.diamondSize =
                    JsonUtils.getInt(
                            jsonobject, "diamondSize", chunkProviderSettingsFactory.diamondSize);
            chunkProviderSettingsFactory.diamondCount =
                    JsonUtils.getInt(
                            jsonobject, "diamondCount", chunkProviderSettingsFactory.diamondCount);
            chunkProviderSettingsFactory.diamondMinHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "diamondMinHeight",
                            chunkProviderSettingsFactory.diamondMinHeight);
            chunkProviderSettingsFactory.diamondMaxHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "diamondMaxHeight",
                            chunkProviderSettingsFactory.diamondMaxHeight);
            chunkProviderSettingsFactory.lapisSize =
                    JsonUtils.getInt(
                            jsonobject, "lapisSize", chunkProviderSettingsFactory.lapisSize);
            chunkProviderSettingsFactory.lapisCount =
                    JsonUtils.getInt(
                            jsonobject, "lapisCount", chunkProviderSettingsFactory.lapisCount);
            chunkProviderSettingsFactory.lapisCenterHeight =
                    JsonUtils.getInt(
                            jsonobject,
                            "lapisCenterHeight",
                            chunkProviderSettingsFactory.lapisCenterHeight);
            chunkProviderSettingsFactory.lapisSpread =
                    JsonUtils.getInt(
                            jsonobject, "lapisSpread", chunkProviderSettingsFactory.lapisSpread);
        } catch (Exception exception) {
        }

        return chunkProviderSettingsFactory;
    }


    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.method_4a5618381532262c6406(jsonelement, type, jsondeserializationcontext);
    }
}
