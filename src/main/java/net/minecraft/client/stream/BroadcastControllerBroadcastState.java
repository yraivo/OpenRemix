package net.minecraft.client.stream;

public enum BroadcastControllerBroadcastState {
    Uninitialized("Uninitialized", 0),
    Initialized("Initialized", 1),
    Authenticating("Authenticating", 2),
    Authenticated("Authenticated", 3),
    LoggingIn("LoggingIn", 4),
    LoggedIn("LoggedIn", 5),
    FindingIngestServer("FindingIngestServer", 6),
    ReceivedIngestServers("ReceivedIngestServers", 7),
    ReadyToBroadcast("ReadyToBroadcast", 8),
    Starting("Starting", 9),
    Broadcasting("Broadcasting", 10),
    Stopping("Stopping", 11),
    Paused("Paused", 12),
    IngestTesting("IngestTesting", 13);

    private static BroadcastControllerBroadcastState[] akW;

    static {
        akW =
                new BroadcastControllerBroadcastState[] {
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.Uninitialized,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.Initialized,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.Authenticating,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.Authenticated,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.LoggingIn,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.LoggedIn,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.FindingIngestServer,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.ReceivedIngestServers,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.ReadyToBroadcast,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.Starting,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.Broadcasting,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.Stopping,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.Paused,
                    net.minecraft.client.stream.BroadcastControllerBroadcastState.IngestTesting
                };
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    BroadcastControllerBroadcastState(String text, int j) {}
}
