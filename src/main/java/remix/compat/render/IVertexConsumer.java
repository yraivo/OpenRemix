package remix.compat.render;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.Facing;

public interface IVertexConsumer {
    VertexFormat lx();

    void put(int i, float... f);

    void setQuadColored();

    void method_a4bcfe686dfb14d08471(Facing facing);

    void setQuadTint(int i);
}
