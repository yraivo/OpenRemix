package net.minecraft.entity.player;

public enum EntityPlayerEnumChatVisibility {
    FULL("FULL", 0, 0, "options.chat.visibility.full"),
    SYSTEM("SYSTEM", 1, 1, "options.chat.visibility.system"),
    HIDDEN("HIDDEN", 2, 2, "options.chat.visibility.hidden");
    private static EntityPlayerEnumChatVisibility[] agr;

    private static EntityPlayerEnumChatVisibility[] ID_LOOKUP;

    private int chatVisibility;
    private String resourceKey;

    // region String decoding support

    // endregion

    public int getChatVisibility() {
        return this.chatVisibility;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        net.minecraft.entity.player.EntityPlayerEnumChatVisibility.ID_LOOKUP =
                new EntityPlayerEnumChatVisibility[values().length];
        agr =
                new EntityPlayerEnumChatVisibility[] {
                    net.minecraft.entity.player.EntityPlayerEnumChatVisibility.FULL,
                    net.minecraft.entity.player.EntityPlayerEnumChatVisibility.SYSTEM,
                    net.minecraft.entity.player.EntityPlayerEnumChatVisibility.HIDDEN
                };
        for (EntityPlayerEnumChatVisibility class_5ce38f16f9db8f493df8 : values()) {
            net.minecraft.entity.player.EntityPlayerEnumChatVisibility.ID_LOOKUP[
                            class_5ce38f16f9db8f493df8.chatVisibility] =
                    class_5ce38f16f9db8f493df8;
        }
    }

    public String getResourceKey() {
        return this.resourceKey;
    }

    EntityPlayerEnumChatVisibility(String text, int k, int j, String textValue) {
        this.chatVisibility = j;
        this.resourceKey = textValue;
    }

    public static EntityPlayerEnumChatVisibility getEnumChatVisibility(int i) {
        return net.minecraft.entity.player.EntityPlayerEnumChatVisibility.ID_LOOKUP[
                i % net.minecraft.entity.player.EntityPlayerEnumChatVisibility.ID_LOOKUP.length];
    }
}
