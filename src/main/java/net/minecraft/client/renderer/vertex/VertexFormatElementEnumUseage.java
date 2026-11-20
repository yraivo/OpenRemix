package net.minecraft.client.renderer.vertex;

public enum VertexFormatElementEnumUseage {
    POSITION("POSITION", 0, "Position"),
    NORMAL("NORMAL", 1, "Normal"),
    COLOR("COLOR", 2, "Vertex Color"),
    UV("UV", 3, "UV"),
    MATRIX("MATRIX", 4, "Bone Matrix"),
    BLEND_WEIGHT("BLEND_WEIGHT", 5, "Blend Weight"),
    PADDING("PADDING", 6, "Padding");
    private static VertexFormatElementEnumUseage[] aRb;

    private String displayName;

    VertexFormatElementEnumUseage(String text, int j, String textValue) {
        this.displayName = textValue;
    }

    // region String decoding support

    // endregion

    static {
        aRb =
                new VertexFormatElementEnumUseage[] {
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage.POSITION,
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage.NORMAL,
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage.COLOR,
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage.UV,
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage.MATRIX,
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage.BLEND_WEIGHT,
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage.PADDING
                };
    }

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

}
