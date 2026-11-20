package net.minecraft.util;

public class RegistryDefaulted extends RegistrySimple {
    private Object defaultObject;

    public Object getObject(Object object) {
        Object objectValue = super.getObject(object);
        return objectValue == null ? this.defaultObject : objectValue;
    }

    public RegistryDefaulted(Object aZpValue) {
        this.defaultObject = aZpValue;
    }
}
