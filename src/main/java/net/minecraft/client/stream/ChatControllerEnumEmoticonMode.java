package net.minecraft.client.stream;

public enum ChatControllerEnumEmoticonMode {
    None("None", 0),
    Url("Url", 1),
    TextureAtlas("TextureAtlas", 2);
    private static ChatControllerEnumEmoticonMode[] alz;

    ChatControllerEnumEmoticonMode(String text, int j) {}

    // region String decoding support

    // endregion

    static {
        alz =
                new ChatControllerEnumEmoticonMode[] {
                    net.minecraft.client.stream.ChatControllerEnumEmoticonMode.None,
                    net.minecraft.client.stream.ChatControllerEnumEmoticonMode.Url,
                    net.minecraft.client.stream.ChatControllerEnumEmoticonMode.TextureAtlas
                };
    }

    // region String decoding support

    // endregion

}
