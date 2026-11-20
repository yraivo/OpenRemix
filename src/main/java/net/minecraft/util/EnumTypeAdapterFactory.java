package net.minecraft.util;

import net.minecraft.util.EnumTypeAdapter;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Locale;

public class EnumTypeAdapterFactory implements TypeAdapterFactory {
    public static String method_658e93ef7379212de812(
            EnumTypeAdapterFactory enumTypeAdapterFactory, Object object) {
        return enumTypeAdapterFactory.method_d37d60bf5a97582a42f9(object);
    }

    public TypeAdapter create(Gson gson, TypeToken typetoken) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Class oclass = typetoken.getRawType();
        String textValue = text;
        if (!oclass.isEnum()) {
            return null;
        }

        HashMap hashmap = Maps.newHashMap();

        for (Object object : oclass.getEnumConstants()) {
            hashmap.put(this.method_d37d60bf5a97582a42f9(object), object);
            if (textValue == null) {
                break;
            }
        }

        return new EnumTypeAdapter(this, hashmap);
    }

    private String method_d37d60bf5a97582a42f9(Object object) {
        return object instanceof Enum
                ? ((Enum) object).name().toLowerCase(Locale.US)
                : object.toString().toLowerCase(Locale.US);
    }
}
