package net.minecraft.server.management;

import net.minecraft.server.management.UserListEntry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public final class UserListSaveFileFormatParameterizedType implements ParameterizedType {
    @Override
    public Type getOwnerType() {
        return null;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] {UserListEntry.class};
    }

    @Override
    public Type getRawType() {
        return List.class;
    }
}
