package net.minecraft.server.management;

import com.google.gson.JsonObject;

public class UserListEntry {
    private Object value;

    public UserListEntry(Object bydValue) {
        this.value = bydValue;
    }

    Object getValue() {
        return this.value;
    }

    boolean hasBanExpired() {
        return false;
    }

    protected UserListEntry(Object bydValue, JsonObject jsonobject) {
        this.value = bydValue;
    }

    protected void onSerialization(JsonObject jsonobject) {}
}
