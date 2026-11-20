package shadersmod.client;

import net.minecraft.client.renderer.vertex.VertexFormatElementEnumType;

public class SVertexAttrib {
    public int offset;
    public int index;
    public VertexFormatElementEnumType type;
    public int count;

    public SVertexAttrib(
            int i, int j, VertexFormatElementEnumType vertexFormatElementEnumType) {
        this.index = i;
        this.count = j;
        this.type = vertexFormatElementEnumType;
    }
}
