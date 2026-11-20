package remix.compat.render;

import net.minecraft.util.Facing;
import remix.compat.vecmath.Matrix4f;

public interface ITransformation {
    int transformVertexIndex(Facing facing, int i);

    Matrix4f getMatrix();

    Facing rotate(Facing facing);
}
