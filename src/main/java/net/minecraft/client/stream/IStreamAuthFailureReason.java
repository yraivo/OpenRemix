package net.minecraft.client.stream;

public enum IStreamAuthFailureReason {
    ERROR("ERROR", 0),
    INVALID_TOKEN("INVALID_TOKEN", 1);
    private static IStreamAuthFailureReason[] alF;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        alF =
                new IStreamAuthFailureReason[] {
                    net.minecraft.client.stream.IStreamAuthFailureReason.ERROR,
                    net.minecraft.client.stream.IStreamAuthFailureReason.INVALID_TOKEN
                };
    }

    IStreamAuthFailureReason(String text, int j) {}
}
