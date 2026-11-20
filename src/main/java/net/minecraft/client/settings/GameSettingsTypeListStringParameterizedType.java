package net.minecraft.client.settings;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public final class GameSettingsTypeListStringParameterizedType implements ParameterizedType {
    @Override
    public Type getOwnerType() {
        return null;
    }

    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] {String.class};
    }
}
