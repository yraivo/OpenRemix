package net.minecraft.client.stream;

import com.google.common.collect.Maps;
import com.google.gson.Gson;

import java.util.Map;

public class Metadata {
    private Map payload;

    private String name;

    private static Gson aAL;
    private String description;

    public void O(String text, String textValue) {
        if (this.payload == null) {
            this.payload = Maps.newHashMap();
        }

        if (this.payload.size() > 50) {
            throw new IllegalArgumentException("Metadata payload is full, cannot add more to it!");
        }

        if (text == null) {
            throw new IllegalArgumentException("Metadata payload key cannot be null!");
        }

        if (text.length() > 255) {
            throw new IllegalArgumentException("Metadata payload key is too long!");
        }

        if (textValue == null) {
            throw new IllegalArgumentException("Metadata payload value cannot be null!");
        }

        if (textValue.length() > 255) {
            throw new IllegalArgumentException("Metadata payload value is too long!");
        }

        this.payload.put(text, textValue);
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("name", this.name)
                .add("description", this.description)
                .add("data", this.method_c60693f457aa70be7160())
                .toString();
    }

    public Metadata(String text) {
        this(text, (String) null);
    }

    public void setDescription(String text) {
        this.description = text;
    }

    public String method_c60693f457aa70be7160() {
        return this.payload != null && !this.payload.isEmpty() ? aAL.toJson(this.payload) : null;
    }

    public Metadata(String text, String textValue) {
        this.name = text;
        this.description = textValue;
    }

    public String method_e0e3092a59fee58fae52() {
        return this.description == null ? this.name : this.description;
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public String getName() {
        return this.name;
    }

    static {
        aAL = new Gson();
    }
}
