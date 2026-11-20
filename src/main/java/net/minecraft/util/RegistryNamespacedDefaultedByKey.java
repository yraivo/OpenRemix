package net.minecraft.util;

import org.apache.commons.lang3.Validate;

public class RegistryNamespacedDefaultedByKey extends RegistryNamespaced {
    private Object defaultValue;
    private Object defaultValueKey;

    public Object getObjectById(int i) {
        Object objectById = super.getObjectById(i);
        return objectById == null ? this.defaultValue : objectById;
    }

    public RegistryNamespacedDefaultedByKey(Object bDQValue) {
        this.defaultValueKey = bDQValue;
    }

    public Object getObject(Object object) {
        Object objectValue = super.getObject(object);
        return objectValue == null ? this.defaultValue : objectValue;
    }

    public void validateKey() {
        Validate.notNull(this.defaultValueKey);
    }

    public void register(int i, Object object, Object defaultValueValue) {
        if (this.defaultValueKey.equals(object)) {
            this.defaultValue = defaultValueValue;
        }

        super.register(i, object, defaultValueValue);
    }
}
