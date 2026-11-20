package net.minecraft.block.properties;

import net.minecraft.entity.Entity;

public abstract class PropertyHelper implements IProperty {

    private static boolean valid;
    private String name;
    private Class valueClass;

    public Class getValueClass() {
        return this.valueClass;
    }

    static {
        setValid(true);
    }

    public static void setValid(boolean flag) {
        net.minecraft.block.properties.PropertyHelper.valid = flag;
    }

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = isValid();
        return !flag;
    }

    @Override
    public String toString() {
        boolean flag = method_d87039b07bd6961907f2();
        String text =
                com.google.common.base.MoreObjects.toStringHelper(this)
                        .add("name", this.name)
                        .add("clazz", this.valueClass)
                        .add("values", this.getAllowedValues())
                        .toString();
        if (flag) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }

        return text;
    }

    public static boolean isValid() {
        return net.minecraft.block.properties.PropertyHelper.valid;
    }

    protected PropertyHelper(String text, Class oclass) {
        this.valueClass = oclass;
        this.name = text;
    }

    // region String decoding support

    // endregion

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object != null && this.getClass() == object.getClass()) {
            PropertyHelper propertyHelper = (PropertyHelper) object;
            return this.valueClass.equals(propertyHelper.valueClass)
                    && this.name.equals(propertyHelper.name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 * this.valueClass.hashCode() + this.name.hashCode();
    }

    // region String decoding support

    // endregion
}
