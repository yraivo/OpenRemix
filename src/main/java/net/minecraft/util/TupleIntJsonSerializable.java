package net.minecraft.util;

public class TupleIntJsonSerializable {
    private int integerValue;
    private IJsonSerializable jsonSerializableValue;

    public void setIntegerValue(int integerValueValue) {
        this.integerValue = integerValueValue;
    }

    public int getIntegerValue() {
        return this.integerValue;
    }

    public void setJsonSerializableValue(IJsonSerializable bGHValue) {
        this.jsonSerializableValue = bGHValue;
    }

    public IJsonSerializable getJsonSerializableValue() {
        return this.jsonSerializableValue;
    }
}
