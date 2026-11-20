package net.minecraft.client.renderer.chunk;

public enum ChunkCompileTaskGeneratorType {
    REBUILD_CHUNK("REBUILD_CHUNK", 0, "REBUILD_CHUNK", 0),
    RESORT_TRANSPARENCY("RESORT_TRANSPARENCY", 1, "RESORT_TRANSPARENCY", 1);
    private static ChunkCompileTaskGeneratorType[] aQG;

    static {
        aQG =
                new ChunkCompileTaskGeneratorType[] {
                    net.minecraft.client.renderer.chunk.ChunkCompileTaskGeneratorType.REBUILD_CHUNK,
                    net.minecraft.client.renderer.chunk.ChunkCompileTaskGeneratorType.RESORT_TRANSPARENCY
                };
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    ChunkCompileTaskGeneratorType(String text, int j, String textValue, int k) {}
}
