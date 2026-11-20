package net.minecraft.util;

import com.google.common.collect.Maps;

import java.util.Map;

public enum SessionType {
    LEGACY("LEGACY", 0, "legacy"),
    MOJANG("MOJANG", 1, "mojang");
    private static SessionType[] acY;

    private String sessionType;
    private static Map SESSION_TYPES;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    SessionType(String text, int j, String textValue) {
        this.sessionType = textValue;
    }

    // region String decoding support

    // endregion

    public static SessionType setSessionType(String text) {
        return (SessionType) net.minecraft.util.SessionType.SESSION_TYPES.get(text.toLowerCase());
    }

    static {
        acY = new SessionType[] {net.minecraft.util.SessionType.LEGACY, net.minecraft.util.SessionType.MOJANG};
        net.minecraft.util.SessionType.SESSION_TYPES = Maps.newHashMap();
        for (SessionType class_004fd4fa4b60464b93e9 : values()) {
            net.minecraft.util.SessionType.SESSION_TYPES.put(
                    class_004fd4fa4b60464b93e9.sessionType, class_004fd4fa4b60464b93e9);
        }
    }
}
