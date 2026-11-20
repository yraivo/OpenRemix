package shadersmod.client;

import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElementEnumType;
import net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage;

public class SVertexFormatElement extends VertexFormatElement {
    int sUsage;

    public SVertexFormatElement(
            int i, VertexFormatElementEnumType vertexFormatElementEnumType, int j) {
        super(0, vertexFormatElementEnumType, VertexFormatElementEnumUseage.PADDING, j);
        this.sUsage = i;
    }
}
