package net.minecraft.client.renderer.vertex;

public enum VertexFormatElementEnumType {
    FLOAT("FLOAT", 0, 4, "Float", 5126),
    UBYTE("UBYTE", 1, 1, "Unsigned Byte", 5121),
    BYTE("BYTE", 2, 1, "Byte", 5120),
    USHORT("USHORT", 3, 2, "Unsigned Short", 5123),
    SHORT("SHORT", 4, 2, "Short", 5122),
    UINT("UINT", 5, 4, "Unsigned Int", 5125),
    INT("INT", 6, 4, "Int", 5124);
    private static VertexFormatElementEnumType[] aQO;

    private int size;
    private String displayName;

    private int glConstant;

    VertexFormatElementEnumType(String text, int l, int j, String textValue, int k) {
        this.size = j;
        this.displayName = textValue;
        this.glConstant = k;
    }

    static {
        aQO =
                new VertexFormatElementEnumType[] {
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumType.FLOAT,
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumType.UBYTE,
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumType.BYTE,
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumType.USHORT,
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumType.SHORT,
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumType.UINT,
                    net.minecraft.client.renderer.vertex.VertexFormatElementEnumType.INT
                };
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public int getSize() {
        return this.size;
    }

    public int getGlConstant() {
        return this.glConstant;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

}
