package net.minecraft.client.audio;

import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.client.audio.SoundListSoundEntryType;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.Validate;

import java.lang.reflect.Type;

public class SoundListSerializer implements JsonDeserializer {

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.deserializeSoundList(jsonelement, type, jsondeserializationcontext);
    }

    public SoundList deserializeSoundList(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = JsonUtils.getJsonObject(jsonelement, "entry");
        SoundList soundList = new SoundList();
        int l = SoundHandler.getOpaqueControlState();
        soundList.setReplaceExisting(
                JsonUtils.getBoolean(jsonobject, "replace", false));
        SoundCategory soundCategory =
                SoundCategory.getCategory(
                        JsonUtils.getString(
                                jsonobject, "category", SoundCategory.MASTER.getCategoryName()));
        soundList.setSoundCategory(soundCategory);
        int i = l;
        Validate.notNull(soundCategory, "Invalid category", new Object[0]);
        if (jsonobject.has("sounds")) {
            JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "sounds");
            int j = 0;

            while (j < jsonarray.size()) {
                SoundListSoundEntry soundListSoundEntry;
                label62:
                {
                    JsonElement jsonelement1 = jsonarray.get(j);
                    soundListSoundEntry = new SoundListSoundEntry();
                    if (JsonUtils.isString(jsonelement1)) {
                        soundListSoundEntry.setSoundEntryName(
                                JsonUtils.getString(jsonelement1, "sound"));
                        if (i == 0) {
                            break label62;
                        }

                        Entity.setLegacyGlobalFlowMarker(new String[5]);
                    }

                    JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonelement1, "sound");
                    soundListSoundEntry.setSoundEntryName(
                            JsonUtils.getString(jsonobject1, "name"));
                    if (jsonobject1.has("type")) {
                        SoundListSoundEntryType soundListSoundEntryType =
                                SoundListSoundEntryType.an(
                                        JsonUtils.getString(jsonobject1, "type"));
                        Validate.notNull(soundListSoundEntryType, "Invalid type", new Object[0]);
                        soundListSoundEntry.setSoundEntryType(
                                soundListSoundEntryType);
                    }

                    if (jsonobject1.has("volume")) {
                        float floatValue = JsonUtils.getFloat(jsonobject1, "volume");
                        Validate.isTrue(floatValue > 0.0F, "Invalid volume", new Object[0]);
                        soundListSoundEntry.setSoundEntryVolume(floatValue);
                    }

                    if (jsonobject1.has("pitch")) {
                        float floatValueValue = JsonUtils.getFloat(jsonobject1, "pitch");
                        Validate.isTrue(floatValueValue > 0.0F, "Invalid pitch", new Object[0]);
                        soundListSoundEntry.setSoundEntryPitch(floatValueValue);
                    }

                    if (jsonobject1.has("weight")) {
                        int k = JsonUtils.getInt(jsonobject1, "weight");
                        Validate.isTrue(k > 0, "Invalid weight", new Object[0]);
                        soundListSoundEntry.setSoundEntryWeight(k);
                    }

                    if (jsonobject1.has("stream")) {
                        soundListSoundEntry.setStreaming(
                                JsonUtils.getBoolean(jsonobject1, "stream"));
                    }
                }

                soundList.getSoundList().add(soundListSoundEntry);
                j++;
                if (i != 0) {
                    break;
                }
            }
        }

        return soundList;
    }


}
