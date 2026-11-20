package net.minecraft.server.network;

public enum NetHandlerLoginServerLoginState {
    HELLO("HELLO", 0),
    KEY("KEY", 1),
    AUTHENTICATING("AUTHENTICATING", 2),
    READY_TO_ACCEPT("READY_TO_ACCEPT", 3),
    ACCEPTED("ACCEPTED", 4);
    private static NetHandlerLoginServerLoginState[] Wt;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    NetHandlerLoginServerLoginState(String text, int j) {}

    static {
        Wt =
                new NetHandlerLoginServerLoginState[] {
                    net.minecraft.server.network.NetHandlerLoginServerLoginState.HELLO, net.minecraft.server.network.NetHandlerLoginServerLoginState.KEY, net.minecraft.server.network.NetHandlerLoginServerLoginState.AUTHENTICATING, net.minecraft.server.network.NetHandlerLoginServerLoginState.READY_TO_ACCEPT, net.minecraft.server.network.NetHandlerLoginServerLoginState.ACCEPTED
                };
    }
}
