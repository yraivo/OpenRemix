package net.minecraft.client.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GLAllocation {

    public static IntBuffer createDirectIntBuffer(int i) {
        return createDirectByteBuffer(i << 2).asIntBuffer();
    }

    public static synchronized ByteBuffer createDirectByteBuffer(int i) {
        return ByteBuffer.allocateDirect(i).order(ByteOrder.nativeOrder());
    }

    public static FloatBuffer createDirectFloatBuffer(int i) {
        return createDirectByteBuffer(i << 2).asFloatBuffer();
    }

    public static synchronized void b(int i) {
        GL11.glDeleteLists(i, 1);
    }

    public static synchronized void deleteDisplayLists(int i, int j) {
        GL11.glDeleteLists(i, j);
    }


    public static synchronized int generateDisplayLists(int i) {
        int j = GL11.glGenLists(i);
        if (j == 0) {
            int k = GL11.glGetError();
            String text = "No error code reported";
            if (k != 0) {
                text = GLU.gluErrorString(k);
            }

            throw new IllegalStateException(
                    "glGenLists returned an ID of 0 for a count of "
                            + i
                            + ", GL error ("
                            + k
                            + "): "
                            + text);
        } else {
            return j;
        }
    }
}
