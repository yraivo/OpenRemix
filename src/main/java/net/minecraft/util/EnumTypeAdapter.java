package net.minecraft.util;

import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.Vec3i;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;

public class EnumTypeAdapter extends TypeAdapter {
    HashMap hashMap;
    EnumTypeAdapterFactory enumTypeAdapterFactory;

    public void write(JsonWriter jsonwriter, Object object) throws IOException {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (object == null) {
            jsonwriter.nullValue();
            if (text != null) {
                return;
            }
        }

        jsonwriter.value(EnumTypeAdapterFactory.method_658e93ef7379212de812(this.enumTypeAdapterFactory, object));
    }

    public Object read(JsonReader jsonreader) throws IOException {
        if (jsonreader.peek() == JsonToken.NULL) {
            jsonreader.nextNull();
            return null;
        } else {
            return this.hashMap.get(jsonreader.nextString());
        }
    }

    public EnumTypeAdapter(EnumTypeAdapterFactory aYcValue, HashMap hashmap) {
        this.enumTypeAdapterFactory = aYcValue;
        this.hashMap = hashmap;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }
}
