package net.minecraft.world.gen.layer;

public enum GenLayerEdgeMode {
    COOL_WARM("COOL_WARM", 0),
    HEAT_ICE("HEAT_ICE", 1),
    SPECIAL("SPECIAL", 2);

    private static GenLayerEdgeMode[] aiC;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    GenLayerEdgeMode(String text, int j) {}

    static {
        aiC =
                new GenLayerEdgeMode[] {
                    net.minecraft.world.gen.layer.GenLayerEdgeMode.COOL_WARM,
                    net.minecraft.world.gen.layer.GenLayerEdgeMode.HEAT_ICE,
                    net.minecraft.world.gen.layer.GenLayerEdgeMode.SPECIAL
                };
    }

    // region String decoding support

    // endregion

}
