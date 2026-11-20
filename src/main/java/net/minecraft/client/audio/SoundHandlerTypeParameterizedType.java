package net.minecraft.client.audio;

import net.minecraft.client.audio.SoundList;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public final class SoundHandlerTypeParameterizedType implements ParameterizedType {
    @Override
    public Type getRawType() {
        return Map.class;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] {String.class, SoundList.class};
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
