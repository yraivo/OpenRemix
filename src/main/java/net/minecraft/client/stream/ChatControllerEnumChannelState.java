package net.minecraft.client.stream;

public enum ChatControllerEnumChannelState {
    Created("Created", 0),
    Connecting("Connecting", 1),
    Connected("Connected", 2),
    Disconnecting("Disconnecting", 3),
    Disconnected("Disconnected", 4);

    private static ChatControllerEnumChannelState[] alo;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    ChatControllerEnumChannelState(String text, int j) {}

    // region String decoding support

    // endregion

    static {
        alo =
                new ChatControllerEnumChannelState[] {
                    net.minecraft.client.stream.ChatControllerEnumChannelState.Created,
                    net.minecraft.client.stream.ChatControllerEnumChannelState.Connecting,
                    net.minecraft.client.stream.ChatControllerEnumChannelState.Connected,
                    net.minecraft.client.stream.ChatControllerEnumChannelState.Disconnecting,
                    net.minecraft.client.stream.ChatControllerEnumChannelState.Disconnected
                };
    }
}
