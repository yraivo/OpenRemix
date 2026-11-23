package remix.util.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/** A render-thread snapshot, captured once per projection batch, never reused across frames. */
public final class ScreenProjection {
    private final FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
    private final FloatBuffer projection = BufferUtils.createFloatBuffer(16);
    private final IntBuffer viewport = BufferUtils.createIntBuffer(16);
    private final FloatBuffer window = BufferUtils.createFloatBuffer(3);
    private int displayHeight;

    public void capture() {
        this.modelView.clear();
        this.projection.clear();
        this.viewport.clear();
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, this.modelView);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, this.projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, this.viewport);
        this.displayHeight = Display.getHeight();
    }

    public boolean project(double x, double y, double z, double[] result) {
        this.window.clear();
        if (!GLU.gluProject((float) x, (float) y, (float) z,
                this.modelView, this.projection, this.viewport, this.window)) return false;
        result[0] = this.window.get(0);
        result[1] = this.displayHeight - this.window.get(1);
        result[2] = this.window.get(2);
        return true;
    }
}
