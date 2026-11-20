package net.minecraft.client.renderer.chunk;

public enum ChunkCompileTaskGeneratorStatus {
    PENDING("PENDING", 0, "PENDING", 0),
    COMPILING("COMPILING", 1, "COMPILING", 1),
    UPLOADING("UPLOADING", 2, "UPLOADING", 2),
    DONE("DONE", 3, "DONE", 3);
    private static ChunkCompileTaskGeneratorStatus[] aQA;

    // region String decoding support

    // endregion

    static {
        aQA =
                new ChunkCompileTaskGeneratorStatus[] {
                    net.minecraft.client.renderer.chunk.ChunkCompileTaskGeneratorStatus.PENDING,
                    net.minecraft.client.renderer.chunk.ChunkCompileTaskGeneratorStatus.COMPILING,
                    net.minecraft.client.renderer.chunk.ChunkCompileTaskGeneratorStatus.UPLOADING,
                    net.minecraft.client.renderer.chunk.ChunkCompileTaskGeneratorStatus.DONE
                };
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    ChunkCompileTaskGeneratorStatus(String text, int j, String textValue, int k) {}
}
