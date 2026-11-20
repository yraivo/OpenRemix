package net.minecraft.util;

public interface IRegistry extends Iterable {
    void putObject(Object object, Object object1);

    Object getObject(Object object);
}
