package net.minecraft.entity.ai.attributes;

public abstract class BaseAttribute implements IAttribute {

    private IAttribute field_180373_a;
    private double defaultValue;
    private String unlocalizedName;
    private boolean shouldWatch;
    private static String h;

    @Override
    public int hashCode() {
        return this.unlocalizedName.hashCode();
    }

    public String getAttributeUnlocalizedName() {
        return this.unlocalizedName;
    }

    public BaseAttribute setShouldWatch(boolean shouldWatchValue) {
        this.shouldWatch = shouldWatchValue;
        return this;
    }

    public IAttribute func_180372_d() {
        return this.field_180373_a;
    }

    public boolean getShouldWatch() {
        return this.shouldWatch;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof IAttribute
                && this.unlocalizedName.equals(((IAttribute) object).getAttributeUnlocalizedName());
    }

    // region String decoding support

    // endregion

    static {
        h = "Name cannot be null!";
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    protected BaseAttribute(IAttribute bsJValue, String text, double defaultValueValue) {
        this.field_180373_a = bsJValue;
        this.unlocalizedName = text;
        this.defaultValue = defaultValueValue;
        if (text == null) {
            throw new IllegalArgumentException(h);
        }
    }

    // region String decoding support

    // endregion

    public double getDefaultValue() {
        return this.defaultValue;
    }
}
