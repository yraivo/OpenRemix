package net.minecraft.client.stream;

public enum ChatControllerChatState {
    Uninitialized("Uninitialized", 0),
    Initializing("Initializing", 1),
    Initialized("Initialized", 2),
    ShuttingDown("ShuttingDown", 3);

    private static ChatControllerChatState[] alk;

    static {
        alk =
                new ChatControllerChatState[] {
                    net.minecraft.client.stream.ChatControllerChatState.Uninitialized,
                    net.minecraft.client.stream.ChatControllerChatState.Initializing,
                    net.minecraft.client.stream.ChatControllerChatState.Initialized,
                    net.minecraft.client.stream.ChatControllerChatState.ShuttingDown
                };
    }

    // region String decoding support

    // endregion

    ChatControllerChatState(String text, int j) {}

    // region String decoding support

    // endregion

}
