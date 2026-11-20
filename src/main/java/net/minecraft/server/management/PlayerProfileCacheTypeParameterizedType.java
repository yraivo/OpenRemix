package net.minecraft.server.management;

import net.minecraft.server.management.PlayerProfileCacheProfileEntry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public final class PlayerProfileCacheTypeParameterizedType implements ParameterizedType {
    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] {PlayerProfileCacheProfileEntry.class};
    }

    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
