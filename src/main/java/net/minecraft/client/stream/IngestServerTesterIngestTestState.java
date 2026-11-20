package net.minecraft.client.stream;

public enum IngestServerTesterIngestTestState {
    Uninitalized("Uninitalized", 0),
    Starting("Starting", 1),
    ConnectingToServer("ConnectingToServer", 2),
    TestingServer("TestingServer", 3),
    DoneTestingServer("DoneTestingServer", 4),
    Finished("Finished", 5),
    Cancelling("Cancelling", 6),
    Cancelled("Cancelled", 7),
    Failed("Failed", 8);
    private static IngestServerTesterIngestTestState[] alR;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    IngestServerTesterIngestTestState(String text, int j) {}

    static {
        alR =
                new IngestServerTesterIngestTestState[] {
                    net.minecraft.client.stream.IngestServerTesterIngestTestState.Uninitalized,
                    net.minecraft.client.stream.IngestServerTesterIngestTestState.Starting,
                    net.minecraft.client.stream.IngestServerTesterIngestTestState.ConnectingToServer,
                    net.minecraft.client.stream.IngestServerTesterIngestTestState.TestingServer,
                    net.minecraft.client.stream.IngestServerTesterIngestTestState.DoneTestingServer,
                    net.minecraft.client.stream.IngestServerTesterIngestTestState.Finished,
                    net.minecraft.client.stream.IngestServerTesterIngestTestState.Cancelling,
                    net.minecraft.client.stream.IngestServerTesterIngestTestState.Cancelled,
                    net.minecraft.client.stream.IngestServerTesterIngestTestState.Failed
                };
    }
}
